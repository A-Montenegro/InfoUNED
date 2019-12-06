package xyz.infouned.solicitudesInformacionBBDD;

import static org.junit.Assert.assertTrue;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Stack;

import xyz.infouned.estudios.Titulacion;


public class SolicitudValoracionEstudiantilTopAsignatura extends SolicitudInformacion{
	private Titulacion titulacion;
	private String ordenamiento;
	
	public SolicitudValoracionEstudiantilTopAsignatura(Titulacion titulacion, String ordenamiento, Stack<String> criteriosConsultaSQL){
		super();
		assertTrue(ordenamiento.equals("menores") || ordenamiento.equals("mayores"));
		this.titulacion = titulacion;
		this.ordenamiento = ordenamiento;
		sustitucionesConsultaSQL.put("idTitulacionObjetivo", String.valueOf(titulacion.getIdTitulacion()));
		String cadenaTextoCriteriosConsultaSQL = transcribirTextoCriteriosASQL(criteriosConsultaSQL) ;
		sustitucionesConsultaSQL.put("criteriosConsultaSQL", cadenaTextoCriteriosConsultaSQL);
		sustitucionesConsultaSQL.put("ordenamientoAscendenteODescendente", transcribirTextoOrdenamientoASQL(ordenamiento));
		sustitucionesConsultaSQL.put("limiteFilas", "10");
	}	
	
	public String generarCadenaRespuesta(String saltoDeLinea){
		conexionBaseDeDatos.abrirConexion();
		ResultSet resultSet = generarResultSetConsultaSQL("SolicitudValoracionEstudiantilTopAsignatura");
		String cadenaRespuesta = new String("");
		String cursoAcademico = new String("");
		boolean esPrimeraIteracion = true;
		try {
			if(resultSet.next() == false) {
				cadenaRespuesta = "No se han encontrado asignaturas con registros de valoración estudiantil para la titulación " + 
								   titulacion.getNombreTitulacion() + " que cumplan los requisitos especificados en la base de datos.";
			} else {
				do {
					if (esPrimeraIteracion) {
						cursoAcademico = resultSet.getString(1);
						esPrimeraIteracion = false;
					}
					String nombreAsignatura = resultSet.getString(2);
					String valoracionEstudiantil = String.format("%.02f", resultSet.getFloat(3)) ;
					cadenaRespuesta += "-La asignatura " + nombreAsignatura + " obtuvo una calificación de " + valoracionEstudiantil
							+ " sobre 100." + saltoDeLinea;
				} while(resultSet.next());
				cadenaRespuesta = "Durante el último curso académico registrado (" + cursoAcademico
						  + "), estas fueron las asignaturas de la titulación " + titulacion.getNombreTitulacion() + " que " + ordenamiento
						  + " calificaciones obtuvieron según las encuestas de los estudiantes:"
						  + saltoDeLinea + cadenaRespuesta;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		conexionBaseDeDatos.cerrarConexion();
		return cadenaRespuesta;
	}

}