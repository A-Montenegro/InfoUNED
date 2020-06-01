package es.infouned.solicitudesInformacionBBDD;

import java.sql.ResultSet;
import java.sql.SQLException;

import es.infouned.estudios.ParametroEstadistico;
import es.infouned.estudios.Titulacion;

/**
 * Clase que se encarga de generar las cadenas de texto finales que componen las respuestas del chatbot cuando la consulta del usuario está relacionada con el rendimiento de una titulación.
 * @author Alberto Martínez Montenegro
 * 
 */
public class SolicitudEstadisticaRendimientoTitulacion extends SolicitudInformacion{
	
	private ParametroEstadistico parametroEstadistico;
	private Titulacion titulacion;
	
	public SolicitudEstadisticaRendimientoTitulacion(Titulacion titulacion, ParametroEstadistico parametroEstadistico){
		super();
		this.titulacion = titulacion;
		this.parametroEstadistico = parametroEstadistico;
		sustitucionesConsultaSQL.put("idTitulacionObjetivo", String.valueOf(titulacion.getIdTitulacion()));
		sustitucionesConsultaSQL.put("nombreParametroEstadistico", parametroEstadistico.getLiteral());
		sustitucionesConsultaSQL.put("limiteFilas", "10");
	}
	
	public String generarCadenaRespuesta(String saltoDeLinea){
		conexionBaseDeDatos.abrirConexion();
		ResultSet resultSet = generarResultSetConsultaSQL("SolicitudEstadisticaRendimientoTitulacionPorIdTitulacion");
		String cadenaRespuesta = new String("");
		String complementoDeLinea = ""; // TO-DO: El valor de complementoDeLinea ha de ser calculado a partir de 'nombreParametroEstadistico' para obtener la cadeena adecuada en cada caso. 
		try {
			if (resultSet.next() == false) {
				cadenaRespuesta = "No se han encontrado estadísticas de " + parametroEstadistico.getNombre() + " para la titulación " + titulacion.getNombre()
						  + " en la base de datos.";
		    } else {
		        do {
					String cursoAcademico = resultSet.getString(1);
					String  valorParametroEstadistico = String.format("%.02f", resultSet.getFloat(2)) ;
					cadenaRespuesta += "-Curso " + cursoAcademico + ": " + valorParametroEstadistico + "." + complementoDeLinea + saltoDeLinea;
		        }while (resultSet.next());
				cadenaRespuesta = "Las estadísticas de " + parametroEstadistico.getNombre() + " para la titulación " + titulacion.getNombre()
								+ " en los últimos años son las siguientes:" + saltoDeLinea + cadenaRespuesta;
		    }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		conexionBaseDeDatos.cerrarConexion();
		return cadenaRespuesta;
	}
}