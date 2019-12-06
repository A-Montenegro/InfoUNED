package xyz.infouned.solicitudesInformacionBBDD;

import java.sql.ResultSet;
import java.sql.SQLException;

import xyz.infouned.estudios.Titulacion;


public class SolicitudEstadisticaRendimientoTitulacion extends SolicitudInformacion{
	
	private String nombreParametroEstadistico;
	private Titulacion titulacion;
	
	public SolicitudEstadisticaRendimientoTitulacion(Titulacion titulacion, String nombreParametroEstadistico){
		super();
		this.titulacion = titulacion;
		this.nombreParametroEstadistico = nombreParametroEstadistico;
		sustitucionesConsultaSQL.put("idTitulacionObjetivo", String.valueOf(titulacion.getIdTitulacion()));
		sustitucionesConsultaSQL.put("nombreParametroEstadistico", nombreParametroEstadistico);
		sustitucionesConsultaSQL.put("limiteFilas", "10");
	}
	
	public String generarCadenaRespuesta(String saltoDeLinea){
		conexionBaseDeDatos.abrirConexion();
		ResultSet resultSet = generarResultSetConsultaSQL("SolicitudEstadisticaRendimientoTitulacionPorIdTitulacion");
		String cadenaRespuesta = new String("");
		String complementoDeLinea = ""; // TO-DO: El valor de complementoDeLinea ha de ser calculado a partir de 'nombreParametroEstadistico' para obtener la cadeena adecuada en cada caso. 
		try {
			if (resultSet.next() == false) {
				cadenaRespuesta = "No se han encontrado estadísticas de " + nombreParametroEstadistico + " para la titulación " + titulacion.getNombreTitulacion()
						  + " en la base de datos.";
		    } else {
		        do {
					String cursoAcademico = resultSet.getString(1);
					String  valorParametroEstadistico = String.format("%.02f", resultSet.getFloat(2)) ;
					cadenaRespuesta += "-Curso " + cursoAcademico + ": " + valorParametroEstadistico + "." + complementoDeLinea + saltoDeLinea;
		        }while (resultSet.next());
				cadenaRespuesta = "Las estadísticas de " + nombreParametroEstadistico + " para la titulación " + titulacion.getNombreTitulacion()
								+ " en los últimos años son las siguientes:" + saltoDeLinea + cadenaRespuesta;
		    }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		conexionBaseDeDatos.cerrarConexion();
		return cadenaRespuesta;
	}
}