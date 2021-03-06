package es.infouned.solicitudesInformacionBBDD;

import java.sql.ResultSet;
import java.sql.SQLException;

import es.infouned.estudios.Asignatura;
import es.infouned.estudios.ParametroEstadistico;
import es.infouned.estudios.Titulacion;

/**
 * Clase que se encarga de generar las cadenas de texto finales que componen las respuestas del chatbot cuando la consulta del usuario est� relacionada con el rendimiento de una asignatura.
 * @author Alberto Mart�nez Montenegro
 * 
 */
public class SolicitudEstadisticaRendimientoAsignatura extends SolicitudInformacion{
	
	private ParametroEstadistico parametroEstadistico;
	private Titulacion titulacion;
	private Asignatura asignatura;
	private final int prioridad = 12;
	
	public SolicitudEstadisticaRendimientoAsignatura(Titulacion titulacion, Asignatura asignatura, ParametroEstadistico parametroEstadistico){
		super();
		this.titulacion = titulacion;
		this.asignatura = asignatura;
		this.parametroEstadistico = parametroEstadistico;
		sustitucionesConsultaSQL.put("idTitulacionObjetivo", String.valueOf(titulacion.getIdTitulacion()));
		sustitucionesConsultaSQL.put("idAsignaturaObjetivo", asignatura.getIdAsignatura());
		sustitucionesConsultaSQL.put("nombreParametroEstadistico", parametroEstadistico.getLiteral());
		sustitucionesConsultaSQL.put("limiteFilas", "10");
	}
	
	public String generarCadenaRespuesta(String saltoDeLinea){
		conexionBaseDeDatos.abrirConexion();
		ResultSet resultSet = generarResultSetConsultaSQL("SolicitudEstadisticaRendimientoAsignaturaPorIdAsignatura");
		String cadenaRespuesta = new String("");
		String complementoDeLinea = ""; // TO-DO: El valor de complementoDeLinea ha de ser calculado a partir de 'nombreParametroEstadistico' para obtener la cadeena adecuada en cada caso. 
		try {
			if (resultSet.next() == false) {
				cadenaRespuesta = "No se han encontrado estad�sticas de " + parametroEstadistico.getNombre() + " para la asignatura " + asignatura.getNombre()
						  + " de la titulaci�n " + titulacion.getNombre() + " en la base de datos.";
		    } else {
		        do {
					String cursoAcademico = resultSet.getString(1);
					String  valorParametroEstadistico = String.format("%.02f", resultSet.getFloat(2)) ;
					cadenaRespuesta += "-Curso " + cursoAcademico + ": " + valorParametroEstadistico + "." + complementoDeLinea + saltoDeLinea;
		        }while (resultSet.next());
				cadenaRespuesta = "Las estad�sticas de " + parametroEstadistico.getNombre() + " para la asignatura " + asignatura.getNombre()
						  + " de la titulaci�n " + titulacion.getNombre()  + " en los �ltimos a�os son las siguientes:" + saltoDeLinea + cadenaRespuesta;
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