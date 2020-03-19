package es.infouned.estudios;

import java.sql.ResultSet;
import java.sql.SQLException;
import es.infouned.baseDeDatos.ConexionBaseDeDatos;
import es.infouned.baseDeDatos.ConexionMySQL;
import es.infouned.baseDeDatos.InstruccionSelect;
import es.infouned.principal.Configuracion;

public class FactoriaEstudio {
	private  static ConexionBaseDeDatos conexionBaseDeDatos = new ConexionMySQL();
	
	public static Titulacion crearTitulacionPorConsultaSQL(int idTitulacion) {
		conexionBaseDeDatos.abrirConexion();
		InstruccionSelect instruccionSelect =  new InstruccionSelect(conexionBaseDeDatos);
		String cadenaTextoConsultaSQL = Configuracion.obtenerConsultaSQL("SolicitudDatosTitulacion");
		cadenaTextoConsultaSQL.replaceAll("idTitulacionObjetivo", String.valueOf(idTitulacion));
		instruccionSelect.ejecutarConsulta(cadenaTextoConsultaSQL);
		ResultSet resultSet = instruccionSelect.getResultSet();
		String nombreTitulacion = null;
		String nivelEstudiosTitulacion = null;
		try {
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
		cadenaTextoConsultaSQL.replaceAll("idAsignaturaObjetivo", idAsignatura);
		instruccionSelect.ejecutarConsulta(cadenaTextoConsultaSQL);
		ResultSet resultSet = instruccionSelect.getResultSet();
		String nombreAsignatura = null;
		Float creditosAsignatura = null;
		try {
			nombreAsignatura = resultSet.getNString("NOMBRE_ASIGNATURA");
			creditosAsignatura = resultSet.getFloat(0);
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		assert (nombreAsignatura != null && creditosAsignatura != null);
		Asignatura asignatura = new Asignatura(idAsignatura, nombreAsignatura, creditosAsignatura);
		conexionBaseDeDatos.cerrarConexion();
		return asignatura;
	}	
}
