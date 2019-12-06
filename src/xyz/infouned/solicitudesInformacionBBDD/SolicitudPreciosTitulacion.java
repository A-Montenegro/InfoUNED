package xyz.infouned.solicitudesInformacionBBDD;

import java.sql.ResultSet;
import java.sql.SQLException;

import xyz.infouned.estudios.Titulacion;

public class SolicitudPreciosTitulacion extends SolicitudInformacion{
	private Titulacion titulacion;
	
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
				cadenaRespuesta = "No se han encontrado precios para la titulación " + titulacion.getIdTitulacion() + " en la base de datos.";
			}else {
				String precioMatricula1 = String.format("%.02f", resultSet.getFloat(1)) ;
				String precioMatricula2 = String.format("%.02f", resultSet.getFloat(2)) ;
				String precioMatricula3 = String.format("%.02f", resultSet.getFloat(3)) ;
				String precioMatricula4 = String.format("%.02f", resultSet.getFloat(4)) ;
				cadenaRespuesta = "El precio de las asignaturas de la titulación " + titulacion.getNombreTitulacion() + " es de:" + saltoDeLinea
						+ "-" + precioMatricula1 +"€ por cada crédito ECTS la primera vez que el alumno se matricula de la asignatura." + saltoDeLinea
						+ "-" + precioMatricula2 +"€ por cada crédito ECTS la segunda vez que el alumno se matricula de la asignatura." + saltoDeLinea
						+ "-" + precioMatricula3 +"€ por cada crédito ECTS la tercera vez que el alumno se matricula de la asignatura." + saltoDeLinea
						+ "-" + precioMatricula4 +"€ por cada crédito ECTS la cuarta y las sucesivas veces que el alumno se matricule de la asignatura."+ saltoDeLinea; 
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		conexionBaseDeDatos.cerrarConexion();
		return cadenaRespuesta;
	}
}
