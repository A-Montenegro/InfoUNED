package es.infouned.solicitudesInformacionBBDD;

import java.sql.ResultSet;
import java.sql.SQLException;

import es.infouned.estudios.Asignatura;
import es.infouned.estudios.Titulacion;


public class SolicitudEstadisticaRendimientoAsignatura extends SolicitudInformacion{
	
	private String nombreParametroEstadistico;
	private Titulacion titulacion;
	private Asignatura asignatura;
	
	public SolicitudEstadisticaRendimientoAsignatura(Titulacion titulacion, Asignatura asignatura, String nombreParametroEstadistico){
		super();
		this.titulacion = titulacion;
		this.asignatura = asignatura;
		this.nombreParametroEstadistico = nombreParametroEstadistico;
		sustitucionesConsultaSQL.put("idTitulacionObjetivo", String.valueOf(titulacion.getIdTitulacion()));
		sustitucionesConsultaSQL.put("idAsignaturaObjetivo", asignatura.getIdAsignatura());
		sustitucionesConsultaSQL.put("nombreParametroEstadistico", nombreParametroEstadistico);
		sustitucionesConsultaSQL.put("limiteFilas", "10");
	}
	
	public String generarCadenaRespuesta(String saltoDeLinea){
		conexionBaseDeDatos.abrirConexion();
		ResultSet resultSet = generarResultSetConsultaSQL("SolicitudEstadisticaRendimientoAsignaturaPorIdAsignatura");
		String cadenaRespuesta = new String("");
		String complementoDeLinea = ""; // TO-DO: El valor de complementoDeLinea ha de ser calculado a partir de 'nombreParametroEstadistico' para obtener la cadeena adecuada en cada caso. 
		try {
			if (resultSet.next() == false) {
				cadenaRespuesta = "No se han encontrado estad�sticas de " + nombreParametroEstadistico + " para la asignatura " + asignatura.getNombreAsignatura()
						  + " de la titulaci�n " + titulacion.getNombreTitulacion() + " en la base de datos.";
		    } else {
		        do {
					String cursoAcademico = resultSet.getString(1);
					String  valorParametroEstadistico = String.format("%.02f", resultSet.getFloat(2)) ;
					cadenaRespuesta += "-Curso " + cursoAcademico + ": " + valorParametroEstadistico + "." + complementoDeLinea + saltoDeLinea;
		        }while (resultSet.next());
				cadenaRespuesta = "Las estad�sticas de " + nombreParametroEstadistico + " para la asignatura " + asignatura.getNombreAsignatura()
						  + " de la titulaci�n " + titulacion.getNombreTitulacion()  + " en los �ltimos a�os son las siguientes:" + saltoDeLinea + cadenaRespuesta;
		    }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		conexionBaseDeDatos.cerrarConexion();
		return cadenaRespuesta;
	}
}