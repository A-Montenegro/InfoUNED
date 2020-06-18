package es.infouned.solicitudesInformacionBBDD;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Clase que se encarga de generar las cadenas de texto finales que componen las respuestas del chatbot cuando la consulta del usuario está relacionada con información genérica.
 * @author Alberto Martínez Montenegro
 * 
 */
public class SolicitudInformacionGenerica extends SolicitudInformacion{
	
	private String idInformacionGenerica;

	public SolicitudInformacionGenerica(String idInformacionGenerica){
		super();
		this.idInformacionGenerica = idInformacionGenerica;
		sustitucionesConsultaSQL.put("idInformacionGenerica", idInformacionGenerica);
	}
	
	public String generarCadenaRespuesta(String saltoDeLinea){
		conexionBaseDeDatos.abrirConexion();
		ResultSet resultSet = generarResultSetConsultaSQL("SolicitudInformacionGenerica");
		String cadenaRespuesta = new String("");
		try {
			if (resultSet.next() == false) {
				cadenaRespuesta = "Se ha producido un error, no se ha encontrado el identificador de solicitud de información genérica '" + idInformacionGenerica + "'";
			} else {
				cadenaRespuesta = resultSet.getString(1) + saltoDeLinea;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		conexionBaseDeDatos.cerrarConexion();
		return cadenaRespuesta;
	}

	public int getPrioridad() {
		switch(idInformacionGenerica) {
		case "matriculaAdmisionPorInternet":
			return 3;
		case "saludo":
			return 2;
		default:
			return 1;
		}
	}

}
