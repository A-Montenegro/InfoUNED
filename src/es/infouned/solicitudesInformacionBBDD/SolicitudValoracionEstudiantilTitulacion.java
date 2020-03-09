package es.infouned.solicitudesInformacionBBDD;

import java.sql.ResultSet;
import java.sql.SQLException;

import es.infouned.estudios.Titulacion;


public class SolicitudValoracionEstudiantilTitulacion extends SolicitudInformacion{
	private Titulacion titulacion;
	
	public SolicitudValoracionEstudiantilTitulacion(Titulacion titulacion){
		super();
		this.titulacion = titulacion;
		sustitucionesConsultaSQL.put("idTitulacionObjetivo", String.valueOf(titulacion.getIdTitulacion()));
		sustitucionesConsultaSQL.put("limiteFilas", "5");
	}
	
	public String generarCadenaRespuesta(String saltoDeLinea){
		conexionBaseDeDatos.abrirConexion();
		ResultSet resultSet = generarResultSetConsultaSQL("SolicitudValoracionEstudiantilPorIdTitulacion");
		String cadenaRespuesta = new String();
		try {
			if (resultSet.next() == false) {
				cadenaRespuesta = "No hay información sobre las valoraciones estudiantiles de la titulación " + titulacion.getNombreTitulacion() + " en la base de datos.";
			} else {
				do {
					String cursoAcademico = resultSet.getString(1);
					String valoracionEstudiantil = String.format("%.02f", resultSet.getFloat(2)) ;
					cadenaRespuesta += "-Curso " + cursoAcademico + ": " + valoracionEstudiantil + " puntos sobre 100."+ saltoDeLinea;
				} while(resultSet.next());
				cadenaRespuesta = "Las calificaciones que ha obtenido la titulación " + titulacion.getNombreTitulacion() + " en los últimos años según los cuestionarios"
						+ " de satisfacción de los estudiantes son las siguientes:" + saltoDeLinea + cadenaRespuesta;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		conexionBaseDeDatos.cerrarConexion();
		return cadenaRespuesta;
	}
}