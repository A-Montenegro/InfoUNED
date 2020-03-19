package es.infouned.baseDeDatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import es.infouned.principal.Configuracion;

/**
 * Clase que implementa a la interfaz ConexionBaseDeDatos ofreciendo una conexión MySQL
 * @author Alberto Martínez Montenegro
 *
 */
public class ConexionMySQL implements ConexionBaseDeDatos{
	private Connection conexion;
	
	/**
	 * Método que inicializa la conexión, la clase PropiedadesConfiguracion debe haber cargado previamente las propiedades necesarias (Debe hacerse desde la clase Main).
	 */
	public void abrirConexion(){  	
		String urlConexion = Configuracion.getPropiedad("url_conexion_mysql");
		String usuarioMySQL = Configuracion.getPropiedad("usuario_base_de_datos_mysql");
		String passwordMySQL = Configuracion.getPropiedad("password_base_de_datos_mysql");
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conexion = DriverManager.getConnection(urlConexion, usuarioMySQL, passwordMySQL);
		}catch(ClassNotFoundException | SQLException excepcionSQL) {
			excepcionSQL.printStackTrace();
		}
	}
	
	public Connection getConexion() {
		return conexion;
	}
	
	public void cerrarConexion() {
		try {
			conexion.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}  