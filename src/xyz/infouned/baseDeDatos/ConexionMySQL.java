package xyz.infouned.baseDeDatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import xyz.infouned.configuracion.PropiedadesConfiguracion;

/**
 * Clase que implementa a la interfaz ConexionBaseDeDatos ofreciendo una conexi�n MySQL
 * @author Alberto Mart�nez Montenegro
 *
 */
public class ConexionMySQL implements ConexionBaseDeDatos{
	private Connection conexion;
	
	/**
	 * M�todo que inicializa la conexi�n, la clase PropiedadesConfiguracion debe haber cargado previamente las propiedades necesarias (Debe hacerse desde la clase Main).
	 */
	public void abrirConexion(){  	
		String urlConexion = PropiedadesConfiguracion.getPropiedad("url_conexion_mysql");
		String usuarioMySQL = PropiedadesConfiguracion.getPropiedad("usuario_base_de_datos_mysql");
		String passwordMySQL = PropiedadesConfiguracion.getPropiedad("password_base_de_datos_mysql");
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