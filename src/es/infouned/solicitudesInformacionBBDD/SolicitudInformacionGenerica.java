package es.infouned.solicitudesInformacionBBDD;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SolicitudInformacionGenerica extends SolicitudInformacion{

	public SolicitudInformacionGenerica(String idInformacionGenerica){
		super();
		sustitucionesConsultaSQL.put("idInformacionGenerica", idInformacionGenerica);
	}
	
	public String generarCadenaRespuesta(String saltoDeLinea){
		conexionBaseDeDatos.abrirConexion();
		ResultSet resultSet = generarResultSetConsultaSQL("SolicitudInformacionGenerica");
		String cadenaRespuesta = new String("");
		try {
			if (resultSet.next() == false) {
				cadenaRespuesta = "Se ha producido un error, no se ha encontrado el identificador de solicitud de información genérica.";
			} else {
				cadenaRespuesta = resultSet.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		conexionBaseDeDatos.cerrarConexion();
		return cadenaRespuesta;
	}
}
