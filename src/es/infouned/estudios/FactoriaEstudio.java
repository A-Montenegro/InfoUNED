package es.infouned.estudios;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import es.infouned.baseDeDatos.ConexionBaseDeDatos;
import es.infouned.baseDeDatos.InstruccionSelect;
import es.infouned.principal.Configuracion;

/**
 * Clase que se encarga de la construcción de objetos de clase Estudio mediante métodos estáticos.
 * @author Alberto Martínez Montenegro
 *
 */
public class FactoriaEstudio {
	private  static ConexionBaseDeDatos conexionBaseDeDatos = Configuracion.getConexionBaseDeDatos();
	
	public static Titulacion crearTitulacionPorConsultaSQL(int idTitulacion) {
		conexionBaseDeDatos.abrirConexion();
		InstruccionSelect instruccionSelect =  new InstruccionSelect(conexionBaseDeDatos);
		String cadenaTextoConsultaSQL = Configuracion.obtenerConsultaSQL("SolicitudDatosTitulacion");
		cadenaTextoConsultaSQL = cadenaTextoConsultaSQL.replaceAll("idTitulacionObjetivo", String.valueOf(idTitulacion));
		System.out.println(cadenaTextoConsultaSQL);
		instruccionSelect.ejecutarConsulta(cadenaTextoConsultaSQL);
		ResultSet resultSet = instruccionSelect.getResultSet();
		String nombreTitulacion = null;
		String nivelEstudiosTitulacion = null;
		try {
			assert (resultSet.isBeforeFirst());
			resultSet.next();
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
		System.out.println(cadenaTextoConsultaSQL);
		instruccionSelect.ejecutarConsulta(cadenaTextoConsultaSQL);
		ResultSet resultSet = instruccionSelect.getResultSet();
		String nombreAsignatura = null;
		Float creditosAsignatura = null;
		try {
			assert (resultSet.isBeforeFirst());
			resultSet.next();
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

	public static AsignaturaBorrosa crearAsignaturaBorrosaPorConsultaSQL(String idsAsignaturas) {
		ArrayList<Asignatura> listaAsignaturas = new ArrayList<Asignatura>();
		String[] idsAsignaturasSeparadas = idsAsignaturas.split("_");
		conexionBaseDeDatos.abrirConexion();
		for(String idAsignatura : idsAsignaturasSeparadas) {
			InstruccionSelect instruccionSelect =  new InstruccionSelect(conexionBaseDeDatos);
			String cadenaTextoConsultaSQL = Configuracion.obtenerConsultaSQL("SolicitudDatosAsignatura");
			cadenaTextoConsultaSQL = cadenaTextoConsultaSQL.replaceAll("idAsignaturaObjetivo", idAsignatura);
			System.out.println(cadenaTextoConsultaSQL);
			instruccionSelect.ejecutarConsulta(cadenaTextoConsultaSQL);
			ResultSet resultSet = instruccionSelect.getResultSet();
			String nombreAsignatura = null;
			Float creditosAsignatura = null;
			try {
				assert (resultSet.isBeforeFirst());
				resultSet.next();
				nombreAsignatura = resultSet.getNString("NOMBRE_ASIGNATURA");
				creditosAsignatura = resultSet.getFloat("CREDITOS_ASIGNATURA");
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
			assert (nombreAsignatura != null && creditosAsignatura != null);
			Asignatura asignatura = new Asignatura(idAsignatura, nombreAsignatura, creditosAsignatura);
			listaAsignaturas.add(asignatura);
		}
		conexionBaseDeDatos.cerrarConexion();
		AsignaturaBorrosa asignaturaBorrosa = new AsignaturaBorrosa(idsAsignaturas, listaAsignaturas);
		return asignaturaBorrosa;
	}
	
	public static ArrayList<Estudio> obtenerPosiblesTitulacionesDesdeEstudio(Estudio estudio){
		assert(estudio instanceof Asignatura || estudio instanceof AsignaturaBorrosa);
		if(estudio instanceof Asignatura) {
			Asignatura asignatura = (Asignatura) estudio;
			return obtenerPosiblesTitulacionesDesdeIdAsignatura(asignatura.getIdAsignatura());
		}
		else {
			AsignaturaBorrosa asignaturaBorrosa = (AsignaturaBorrosa) estudio;
			String[] idsAsignaturas = asignaturaBorrosa.getIdsAsignaturas().split("_");
			ArrayList<Estudio> posiblesTitulacionesAludidas = new ArrayList<Estudio>();
			for(String idAsignatura : idsAsignaturas) {
				posiblesTitulacionesAludidas.addAll(obtenerPosiblesTitulacionesDesdeIdAsignatura(idAsignatura));
			}
			return posiblesTitulacionesAludidas;
		}
	}
	
	
	public static ArrayList<Estudio> obtenerPosiblesTitulacionesDesdeIdAsignatura(String idAsignatura) {
		ArrayList<Estudio> posiblesTitulaciones = new ArrayList<Estudio>();
		conexionBaseDeDatos.abrirConexion();
		InstruccionSelect instruccionSelect =  new InstruccionSelect(conexionBaseDeDatos);
		String cadenaTextoConsultaSQL = Configuracion.obtenerConsultaSQL("SolicitudIdTitulacionDesdeAsignatura");
		cadenaTextoConsultaSQL = cadenaTextoConsultaSQL.replaceAll("idAsignaturaObjetivo", idAsignatura);
		System.out.println(cadenaTextoConsultaSQL);
		instruccionSelect.ejecutarConsulta(cadenaTextoConsultaSQL);
		ResultSet resultSet = instruccionSelect.getResultSet();
		int idTitulacion;
		try {
			assert (resultSet.isBeforeFirst());
			while(resultSet.next()) {
				idTitulacion = resultSet.getInt("ID_TITULACION");
				Titulacion titulacion = crearTitulacionPorConsultaSQL(idTitulacion);
				posiblesTitulaciones.add(titulacion);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		assert (posiblesTitulaciones.size()>0);
		conexionBaseDeDatos.cerrarConexion();
		return posiblesTitulaciones;
	}
	
	public static boolean existeCombinacionValida(Titulacion titulacion, AsignaturaBorrosa asignaturaBorrosa){
		String[] idsAsignaturasSeparadas = asignaturaBorrosa.getIdsAsignaturas().split("_");
		for(String idAsignatura : idsAsignaturasSeparadas) {
			if(comprobarCombinacionValidaPorId(titulacion.getIdTitulacion(), idAsignatura)) return true;
		}
		return false;
	}
	
	public static boolean esCombinacionValida(Titulacion titulacion, Asignatura asignatura){
		if(asignatura == null) return false;
		return comprobarCombinacionValidaPorId(titulacion.getIdTitulacion(), asignatura.getIdAsignatura());
	}
	
	private static boolean comprobarCombinacionValidaPorId(int idTitulacion, String idAsignatura) {
		boolean esCombinacionValida = false;
		conexionBaseDeDatos.abrirConexion();
		InstruccionSelect instruccionSelect =  new InstruccionSelect(conexionBaseDeDatos);
		String cadenaTextoConsultaSQL = Configuracion.obtenerConsultaSQL("SolicitudExisteCombinatoriaTitulacionAsignatura");
		cadenaTextoConsultaSQL = cadenaTextoConsultaSQL.replaceAll("idTitulacionObjetivo", String.valueOf(idTitulacion));
		cadenaTextoConsultaSQL = cadenaTextoConsultaSQL.replaceAll("idAsignaturaObjetivo", idAsignatura);
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
	
	public static Asignatura transformarAsignaturaBorrosaEnAsignatura(Titulacion titulacion, AsignaturaBorrosa asignaturaBorrosa) {
		Asignatura asignatura = null;
		String[] idsAsignaturasSeparadas = asignaturaBorrosa.getIdsAsignaturas().split("_");
		for(String idAsignatura : idsAsignaturasSeparadas) {
			if(comprobarCombinacionValidaPorId(titulacion.getIdTitulacion(), idAsignatura)) {
				asignatura = crearAsignaturaPorConsultaSQL(idAsignatura);
			}
		}
		return asignatura;
	}
}
