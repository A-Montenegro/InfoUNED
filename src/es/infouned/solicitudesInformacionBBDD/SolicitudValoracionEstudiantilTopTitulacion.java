package es.infouned.solicitudesInformacionBBDD;

import static org.junit.Assert.assertTrue;

import java.sql.ResultSet;
import java.sql.SQLException;


public class SolicitudValoracionEstudiantilTopTitulacion extends SolicitudInformacion{
	
	private String ordenamiento;
	private String nombreNivelEstudios;
	
	public SolicitudValoracionEstudiantilTopTitulacion(String nombreNivelEstudios, String ordenamiento){
		super();
		assertTrue(ordenamiento.equals("menores") || ordenamiento.equals("mayores"));
		this.ordenamiento = ordenamiento;
		this.nombreNivelEstudios = nombreNivelEstudios;
		sustitucionesConsultaSQL.put("nombreNivelEstudios", nombreNivelEstudios);
		sustitucionesConsultaSQL.put("ordenamientoAscendenteODescendente", transcribirTextoOrdenamientoASQL(ordenamiento));
		sustitucionesConsultaSQL.put("limiteFilas", "10");
	}	
	
	public String generarCadenaRespuesta(String saltoDeLinea){
		conexionBaseDeDatos.abrirConexion();
		ResultSet resultSet = generarResultSetConsultaSQL("SolicitudValoracionEstudiantilTopTitulacion");
		String cadenaRespuesta = new String("");
		String cursoAcademico = new String("");
		boolean esPrimeraIteracion = true;
		try {
			if(resultSet.next() == false) {
				cadenaRespuesta = "No se han encontrado datos de valoraciónes estudiantiles para el nivel de estudios " + nombreNivelEstudios + "en la base de datos.";
			} else {
				do {
					if (esPrimeraIteracion) {
						cursoAcademico = resultSet.getString(1);
						esPrimeraIteracion = false;
					}
					String nombreTitulacion = resultSet.getString(2);
					String  valoracionEstudiantil = String.format("%.02f", resultSet.getFloat(3)) ;
					cadenaRespuesta += "-La titulación " + nombreTitulacion + " obtuvo una calificación de " + valoracionEstudiantil
							+ " sobre 100." + saltoDeLinea;
				} while(resultSet.next());
				cadenaRespuesta = "Durante el último curso académico registrado (" + cursoAcademico
						  + "), estas fueron los estudios de " + nombreNivelEstudios.toUpperCase() + " que " + ordenamiento
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