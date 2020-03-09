package es.infouned.solicitudesInformacionBBDD;

import static org.junit.Assert.assertTrue;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Stack;

import es.infouned.estudios.Titulacion;


public class SolicitudEstadisticaRendimientoTopAsignatura extends SolicitudInformacion{
	
	private Titulacion titulacion;
	private String ordenamiento;
	private String nombreParametroEstadistico;
	
	public SolicitudEstadisticaRendimientoTopAsignatura(Titulacion titulacion, String nombreParametroEstadistico, String ordenamiento, Stack<String> criteriosConsultaSQL){
		super();
		assertTrue(ordenamiento.equals("menores") || ordenamiento.equals("mayores"));
		this.titulacion = titulacion;
		this.ordenamiento = ordenamiento;
		this.nombreParametroEstadistico = nombreParametroEstadistico;
		sustitucionesConsultaSQL.put("idTitulacionObjetivo", String.valueOf(titulacion.getIdTitulacion()));
		sustitucionesConsultaSQL.put("nombreParametroEstadistico", nombreParametroEstadistico);
		String cadenaTextoCriteriosConsultaSQL = transcribirTextoCriteriosASQL(criteriosConsultaSQL) ;
		sustitucionesConsultaSQL.put("criteriosConsultaSQL", cadenaTextoCriteriosConsultaSQL);
		sustitucionesConsultaSQL.put("ordenamientoAscendenteODescendente", transcribirTextoOrdenamientoASQL(ordenamiento));
		sustitucionesConsultaSQL.put("limiteFilas", "10");
	}	
	
	public String generarCadenaRespuesta(String saltoDeLinea){
		conexionBaseDeDatos.abrirConexion();
		ResultSet resultSet = generarResultSetConsultaSQL("SolicitudEstadisticaRendimientoTopAsignatura");
		String cadenaRespuesta = new String("");
		String cursoAcademico = new String("");
		String complementoDeLinea = ""; // TO-DO: El valor de complementoDeLinea ha de ser calculado a partir de 'nombreParametroEstadistico' para obtener la cadeena adecuada en cada caso. 
		boolean esPrimeraIteracion = true;
		try {
			if(resultSet.next() == false) {
				cadenaRespuesta = "No se han encontrado asignaturas con estadísticas de " + nombreParametroEstadistico + " para la titulación " + titulacion.getNombreTitulacion() + 
				" según los requisitos especificados en la base de datos.";
			} else {
				do {
					if(esPrimeraIteracion) {
						cursoAcademico = resultSet.getString(1);
						esPrimeraIteracion = false;
					}
					String nombreAsignatura = resultSet.getString(2);
					String  valorParametroEstadistico = String.format("%.02f", resultSet.getFloat(3)) ;
					cadenaRespuesta += "-La asignatura " + nombreAsignatura+ " obtuvo unos resultados de " + valorParametroEstadistico
							+ complementoDeLinea + "." + saltoDeLinea;
				} while (resultSet.next());
				cadenaRespuesta = "Durante el último curso académico registrado (" + cursoAcademico
						  + "), estas fueron las asignaturas de la titulación " + titulacion.getNombreTitulacion() + " que obtuvieron " + ordenamiento
						  + " resultados en las estadísticas de " + nombreParametroEstadistico + ":"
						  + saltoDeLinea + cadenaRespuesta;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		conexionBaseDeDatos.cerrarConexion();
		return cadenaRespuesta;
	}
}