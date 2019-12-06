package xyz.infouned.solicitudesInformacionBBDD;

import java.sql.ResultSet;
import java.sql.SQLException;

import xyz.infouned.estudios.Asignatura;
import xyz.infouned.estudios.Titulacion;


public class SolicitudMatriculadosAsignatura extends SolicitudInformacion{
	
	private Titulacion titulacion;
	private Asignatura asignatura;
	
	public SolicitudMatriculadosAsignatura(Titulacion titulacion, Asignatura asignatura){
		super();
		this.titulacion = titulacion;
		this.asignatura = asignatura;
		sustitucionesConsultaSQL.put("idTitulacionObjetivo", String.valueOf(titulacion.getIdTitulacion()));
		sustitucionesConsultaSQL.put("idAsignaturaObjetivo", asignatura.getIdAsignatura());
		sustitucionesConsultaSQL.put("limiteFilas", "5");
	}
	
	public String generarCadenaRespuesta(String saltoDeLinea){
		conexionBaseDeDatos.abrirConexion();
		ResultSet resultSet = generarResultSetConsultaSQL("SolicitudMatriculadosAsignaturaPorIdAsignatura");
		String cadenaRespuesta = new String("");
		try {
			if (resultSet.next() == false) {
				cadenaRespuesta = "No hay registro de matriculas para la asignatura " + asignatura.getNombreAsignatura() +
								  " de la titulaci�n " + titulacion.getNombreTitulacion() + " en la base de datos.";
			} else {
				do {
					String cursoAcademico = resultSet.getString(1);
					int  matriculados = resultSet.getInt(2);
					cadenaRespuesta += "-Curso " + cursoAcademico + ": " + matriculados + " matriculados."+ saltoDeLinea;
				} while(resultSet.next());
				cadenaRespuesta = "El n�mero de matriculados en la asignatura " + asignatura.getNombreAsignatura() + " de la titulaci�n "
						 + titulacion.getNombreTitulacion() + " en los �ltimos a�os son los siguientes:" + saltoDeLinea + cadenaRespuesta;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		conexionBaseDeDatos.cerrarConexion();
		return cadenaRespuesta;
	}
}
