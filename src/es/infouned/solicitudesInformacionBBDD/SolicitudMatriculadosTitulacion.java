package es.infouned.solicitudesInformacionBBDD;

import java.sql.ResultSet;
import java.sql.SQLException;

import es.infouned.estudios.Titulacion;

/**
 * Clase que se encarga de generar las cadenas de texto finales que componen las respuestas del chatbot cuando la consulta del usuario est� relacionada con el n�mero de matriculados de una titulaci�n.
 * @author Alberto Mart�nez Montenegro
 * 
 */
public class SolicitudMatriculadosTitulacion extends SolicitudInformacion{
	
	private Titulacion titulacion;
	private final int prioridad = 6;

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
				cadenaRespuesta = "No hay registro de matriculas para la titulaci�n " + titulacion.getNombre() + " en la base de datos.";
			} else {
				do {
					String cursoAcademico = resultSet.getString(1);
					int  matriculados = resultSet.getInt(2);
					cadenaRespuesta += "-Curso " + cursoAcademico + ": " + matriculados + " matriculados."+ saltoDeLinea;
				} while(resultSet.next());
				cadenaRespuesta = "El n�mero de matriculados en la titulaci�n " + titulacion.getNombre()
								  + " en los �ltimos a�os son los siguientes:" + saltoDeLinea + cadenaRespuesta;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		conexionBaseDeDatos.cerrarConexion();
		return cadenaRespuesta;
	}
	
	public int getPrioridad() {
		return prioridad;
	}
}