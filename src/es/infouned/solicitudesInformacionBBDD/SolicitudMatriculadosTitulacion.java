package es.infouned.solicitudesInformacionBBDD;

import java.sql.ResultSet;
import java.sql.SQLException;

import es.infouned.estudios.Titulacion;


public class SolicitudMatriculadosTitulacion extends SolicitudInformacion{
	
	private Titulacion titulacion;
	
	public SolicitudMatriculadosTitulacion(Titulacion titulacion){
		super();
		this.titulacion = titulacion;
		sustitucionesConsultaSQL.put("idTitulacionObjetivo", String.valueOf(titulacion.getIdTitulacion()));
		sustitucionesConsultaSQL.put("limiteFilas", "5");
	}
	
	public String generarCadenaRespuesta(String saltoDeLinea){
		conexionBaseDeDatos.abrirConexion();
		ResultSet resultSet = generarResultSetConsultaSQL("SolicitudMatriculadosTitulacionPorIdTitulacion");
		String cadenaRespuesta = new String("");
		try {
			if (resultSet.next() == false) {
				cadenaRespuesta = "No hay registro de matriculas para la titulación " + titulacion.getNombreTitulacion() + " en la base de datos.";
			} else {
				do {
					String cursoAcademico = resultSet.getString(1);
					int  matriculados = resultSet.getInt(2);
					cadenaRespuesta += "-Curso " + cursoAcademico + ": " + matriculados + " matriculados."+ saltoDeLinea;
				} while(resultSet.next());
				cadenaRespuesta = "El número de matriculados en la titulación " + titulacion.getNombreTitulacion()
								  + " en los últimos años son los siguientes:" + saltoDeLinea + cadenaRespuesta;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		conexionBaseDeDatos.cerrarConexion();
		return cadenaRespuesta;
	}
}