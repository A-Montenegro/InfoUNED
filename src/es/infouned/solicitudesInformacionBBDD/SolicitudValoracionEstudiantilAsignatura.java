package es.infouned.solicitudesInformacionBBDD;

import java.sql.ResultSet;
import java.sql.SQLException;

import es.infouned.estudios.Asignatura;
import es.infouned.estudios.Titulacion;


public class SolicitudValoracionEstudiantilAsignatura extends SolicitudInformacion{
	private Titulacion titulacion;
	private Asignatura asignatura;
	
	public SolicitudValoracionEstudiantilAsignatura(Titulacion titulacion, Asignatura asignatura){
		super();
		this.titulacion = titulacion;
		this.asignatura = asignatura;
		sustitucionesConsultaSQL.put("idTitulacionObjetivo", String.valueOf(titulacion.getIdTitulacion()));
		sustitucionesConsultaSQL.put("idAsignaturaObjetivo", asignatura.getIdAsignatura());
		sustitucionesConsultaSQL.put("limiteFilas", "5");
	}
	
	public String generarCadenaRespuesta(String saltoDeLinea){
		conexionBaseDeDatos.abrirConexion();
		ResultSet resultSet = generarResultSetConsultaSQL("SolicitudValoracionEstudiantilPorIdAsignatura");
		String cadenaRespuesta = new String();
		try {
			if (resultSet.next() == false) {
				cadenaRespuesta = "No hay información sobre las valoraciones estudiantiles de la asignatura " + asignatura.getNombreAsignatura() + " en la base de datos.";
			} else {
				do {
					String cursoAcademico = resultSet.getString(1);
					String valoracionEstudiantil = String.format("%.02f", resultSet.getFloat(2)) ;
					cadenaRespuesta += "-Curso " + cursoAcademico + ": " + valoracionEstudiantil + " puntos sobre 100."+ saltoDeLinea;
				} while(resultSet.next());
				cadenaRespuesta = "Las calificaciones que ha obtenido la asignatura " + asignatura.getNombreAsignatura() + " de la titulación " + titulacion.getNombreTitulacion() + " en los últimos años según los cuestionarios"
						+ " de satisfacción de los estudiantes son las siguientes:" + saltoDeLinea + cadenaRespuesta;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		conexionBaseDeDatos.cerrarConexion();
		return cadenaRespuesta;
	}
}