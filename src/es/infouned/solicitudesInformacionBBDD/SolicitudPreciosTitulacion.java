package es.infouned.solicitudesInformacionBBDD;

import java.sql.ResultSet;
import java.sql.SQLException;

import es.infouned.estudios.Titulacion;

/**
 * Clase que se encarga de generar las cadenas de texto finales que componen las respuestas del chatbot cuando la consulta del usuario est� relacionada con el precio de una titulaci�n.
 * @author Alberto Mart�nez Montenegro
 * 
 */
public class SolicitudPreciosTitulacion extends SolicitudInformacion{
	private Titulacion titulacion;
	private final int prioridad = 8;

	public SolicitudPreciosTitulacion(Titulacion titulacion){
		super();
		this.titulacion = titulacion;
		sustitucionesConsultaSQL.put("idTitulacionObjetivo", String.valueOf(titulacion.getIdTitulacion()));
	}
	
	public String generarCadenaRespuesta(String saltoDeLinea){
		conexionBaseDeDatos.abrirConexion();
		ResultSet resultSet = generarResultSetConsultaSQL("SolicitudPreciosTitulacionPorIdTitulacion");
		String cadenaRespuesta = new String("");
		try {
			if(resultSet.next() == false) {
				cadenaRespuesta = "No se han encontrado precios para la titulaci�n " + titulacion.getIdTitulacion() + " en la base de datos.";
			}else {
				String precioMatricula1 = String.format("%.02f", resultSet.getFloat(1)) ;
				String precioMatricula2 = String.format("%.02f", resultSet.getFloat(2)) ;
				String precioMatricula3 = String.format("%.02f", resultSet.getFloat(3)) ;
				String precioMatricula4 = String.format("%.02f", resultSet.getFloat(4)) ;
				cadenaRespuesta = "El precio de las asignaturas de la titulaci�n " + titulacion.getNombre() + " es de:" + saltoDeLinea
						+ "-" + precioMatricula1 +"� por cada cr�dito ECTS la primera vez que el alumno se matricula de la asignatura." + saltoDeLinea
						+ "-" + precioMatricula2 +"� por cada cr�dito ECTS la segunda vez que el alumno se matricula de la asignatura." + saltoDeLinea
						+ "-" + precioMatricula3 +"� por cada cr�dito ECTS la tercera vez que el alumno se matricula de la asignatura." + saltoDeLinea
						+ "-" + precioMatricula4 +"� por cada cr�dito ECTS la cuarta y las sucesivas veces que el alumno se matricule de la asignatura."+ saltoDeLinea; 
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
