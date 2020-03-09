package es.infouned.baseDeDatos;

import java.sql.Connection;


/**
 * 
 * Interfaz que establece los métodos que ha de tener la la subclase que la implemente, representa una conexión con una base de datos.
 * @author Alberto Martínez Montenegro
 */
public interface ConexionBaseDeDatos {
	void abrirConexion();
	Connection getConexion();
	void cerrarConexion();
}
