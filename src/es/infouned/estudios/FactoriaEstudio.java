package es.infouned.estudios;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import es.infouned.baseDeDatos.ConexionBaseDeDatos;
import es.infouned.baseDeDatos.InstruccionSelect;
import es.infouned.principal.Configuracion;

public class FactoriaEstudio {
	private  static ConexionBaseDeDatos conexionBaseDeDatos = Configuracion.getConexionBaseDeDatos();
	
	public static Titulacion crearTitulacionPorConsultaSQL(int idTitulacion) {
		conexionBaseDeDatos.abrirConexion();
		InstruccionSelect instruccionSelect =  new InstruccionSelect(conexionBaseDeDatos);
		String cadenaTextoConsultaSQL = Configuracion.obtenerConsultaSQL("SolicitudDatosTitulacion");
		cadenaTextoConsultaSQL = cadenaTextoConsultaSQL.replaceAll("idTitulacionObjetivo", String.valueOf(idTitulacion));
		instruccionSelect.ejecutarConsulta(cadenaTextoConsultaSQL);
		ResultSet resultSet = instruccionSelect.getResultSet();
		String nombreTitulacion = null;
		String nivelEstudiosTitulacion = null;
		try {
			assert (resultSet.next());
			nombreTitulacion = resultSet.getNString("NOMBRE_TITULACION");
			nivelEstudiosTitulacion = resultSet.getNString("NIVEL_ESTUDIOS_TITULACION");
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		assert (nombreTitulacion != null && nivelEstudiosTitulacion != null);
		Titulacion titulacion = new Titulacion(idTitulacion, nombreTitulacion, nivelEstudiosTitulacion);
		conexionBaseDeDatos.cerrarConexion();
		return titulacion;
	}	
	
	public static Asignatura crearAsignaturaPorConsultaSQL(String idAsignatura) {
		conexionBaseDeDatos.abrirConexion();
		InstruccionSelect instruccionSelect =  new InstruccionSelect(conexionBaseDeDatos);
		String cadenaTextoConsultaSQL = Configuracion.obtenerConsultaSQL("SolicitudDatosAsignatura");
		cadenaTextoConsultaSQL = cadenaTextoConsultaSQL.replaceAll("idAsignaturaObjetivo", idAsignatura);
		instruccionSelect.ejecutarConsulta(cadenaTextoConsultaSQL);
		ResultSet resultSet = instruccionSelect.getResultSet();
		String nombreAsignatura = null;
		Float creditosAsignatura = null;
		try {
			assert (resultSet.next());
			nombreAsignatura = resultSet.getNString("NOMBRE_ASIGNATURA");
			creditosAsignatura = resultSet.getFloat("CREDITOS_ASIGNATURA");
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		assert (nombreAsignatura != null && creditosAsignatura != null);
		Asignatura asignatura = new Asignatura(idAsignatura, nombreAsignatura, creditosAsignatura);
		conexionBaseDeDatos.cerrarConexion();
		return asignatura;
	}
	
	public static ArrayList<Estudio> obtenerPosiblesTitulacionesDesdeIdAsignatura(String idAsignatura) {
		ArrayList<Estudio> posiblesTitulaciones = new ArrayList<Estudio>();
		conexionBaseDeDatos.abrirConexion();
		InstruccionSelect instruccionSelect =  new InstruccionSelect(conexionBaseDeDatos);
		String cadenaTextoConsultaSQL = Configuracion.obtenerConsultaSQL("SolicitudIdTitulacionDesdeAsignatura");
		cadenaTextoConsultaSQL = cadenaTextoConsultaSQL.replaceAll("idAsignaturaObjetivo", idAsignatura);
		instruccionSelect.ejecutarConsulta(cadenaTextoConsultaSQL);
		ResultSet resultSet = instruccionSelect.getResultSet();
		int idTitulacion;
		try {
			assert (resultSet.next());
			do {
				idTitulacion = resultSet.getInt("ID_TITULACION");
				Titulacion titulacion = crearTitulacionPorConsultaSQL(idTitulacion);
				posiblesTitulaciones.add(titulacion);
			}
			while(resultSet.next());
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		assert (posiblesTitulaciones.size()>0);
		conexionBaseDeDatos.cerrarConexion();
		return posiblesTitulaciones;
	}
	
	public static boolean esCombinacionValida(Titulacion titulacion, Asignatura asignatura){
		boolean esCombinacionValida = false;
		conexionBaseDeDatos.abrirConexion();
		InstruccionSelect instruccionSelect =  new InstruccionSelect(conexionBaseDeDatos);
		String cadenaTextoConsultaSQL = Configuracion.obtenerConsultaSQL("SolicitudExisteCombinatoriaTitulacionAsignatura");
		cadenaTextoConsultaSQL = cadenaTextoConsultaSQL.replaceAll("idTitulacionObjetivo", String.valueOf(titulacion.getIdTitulacion()));
		cadenaTextoConsultaSQL = cadenaTextoConsultaSQL.replaceAll("idAsignaturaObjetivo", asignatura.getIdAsignatura());
		instruccionSelect.ejecutarConsulta(cadenaTextoConsultaSQL);
		ResultSet resultSet = instruccionSelect.getResultSet();
		try {
			if(resultSet.next()) {
				esCombinacionValida = true;
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		conexionBaseDeDatos.cerrarConexion();
		return esCombinacionValida;
	}
}
