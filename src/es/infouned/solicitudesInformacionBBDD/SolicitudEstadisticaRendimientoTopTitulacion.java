package es.infouned.solicitudesInformacionBBDD;

import static org.junit.Assert.assertTrue;

import java.sql.ResultSet;
import java.sql.SQLException;


public class SolicitudEstadisticaRendimientoTopTitulacion extends SolicitudInformacion{
	
	private String ordenamiento;
	private String nombreParametroEstadistico;
	private String nombreNivelEstudios;
	
	public SolicitudEstadisticaRendimientoTopTitulacion(String nombreNivelEstudios, String nombreParametroEstadistico, String ordenamiento){
		super();
		assertTrue(ordenamiento.equals("menores") || ordenamiento.equals("mayores"));
		this.ordenamiento = ordenamiento;
		this.nombreParametroEstadistico = nombreParametroEstadistico;
		this.nombreNivelEstudios = nombreNivelEstudios;
		sustitucionesConsultaSQL.put("nombreNivelEstudios", nombreNivelEstudios);
		sustitucionesConsultaSQL.put("nombreParametroEstadistico", nombreParametroEstadistico);
		sustitucionesConsultaSQL.put("ordenamientoAscendenteODescendente", transcribirTextoOrdenamientoASQL(ordenamiento));
		sustitucionesConsultaSQL.put("limiteFilas", "10");
	}	
	
	public String generarCadenaRespuesta(String saltoDeLinea){
		conexionBaseDeDatos.abrirConexion();
		ResultSet resultSet = generarResultSetConsultaSQL("SolicitudEstadisticaRendimientoTopTitulacion");
		String cadenaRespuesta = new String("");
		String cursoAcademico = new String("");
		String complementoDeLinea = ""; // TO-DO: El valor de complementoDeLinea ha de ser calculado a partir de 'nombreParametroEstadistico' para obtener la cadena de texto adecuada en cada caso. 
		boolean esPrimeraIteracion = true;
		try {
			if (resultSet.next() == false) {
				cadenaRespuesta = "No se han encontrado titulaciones con estad�sticas de rendimiento de " + nombreParametroEstadistico + " en la base de datos.";
			} else {
				do {
					if (esPrimeraIteracion) {
						cursoAcademico = resultSet.getString(1);
						esPrimeraIteracion = false;
					}
					String nombreTitulacion = resultSet.getString(2);
					String  valorParametroEstadistico = String.format("%.02f", resultSet.getFloat(3)) ;
					cadenaRespuesta += "-La titulaci�n " + nombreTitulacion + " obtuvo unos resultados de " + valorParametroEstadistico
							+ "." + complementoDeLinea + saltoDeLinea;
				} while(resultSet.next());
				cadenaRespuesta = "Durante el �ltimo curso acad�mico registrado (" + cursoAcademico
						  + "), estas fueron los estudios de " + nombreNivelEstudios.toUpperCase() + " que obtuvieron " + ordenamiento
						  + " resultados en cuanto a " + nombreParametroEstadistico + " :"
						  + saltoDeLinea + cadenaRespuesta;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		conexionBaseDeDatos.cerrarConexion();
		return cadenaRespuesta;
	}
}