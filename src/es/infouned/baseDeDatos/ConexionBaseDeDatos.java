package es.infouned.baseDeDatos;

import java.sql.Connection;


/**
 * 
 * Interfaz que establece los m�todos que ha de tener la la subclase que la implemente, representa una conexi�n con una base de datos.
 * @author Alberto Mart�nez Montenegro
 */
public interface ConexionBaseDeDatos {
	void abrirConexion();
	Connection getConexion();
	void cerrarConexion();
}
