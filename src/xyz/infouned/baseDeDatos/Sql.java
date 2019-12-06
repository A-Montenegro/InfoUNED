package xyz.infouned.baseDeDatos;

/**
 * Clase abstracta diseñada para que los distintos tipos de operaciones SQL hereden de ella, pudiendo gestionar la conexión y obligando a implementar los métodos abstractos.
 * @author Alberto Martínez Montenegro
 *
 */
public abstract class Sql {
	protected ConexionBaseDeDatos conexion;
	
	public Sql(ConexionBaseDeDatos conexion) {
		this.conexion = conexion;
	}
	
	public abstract void ejecutarConsulta(String cadenaDeTextoInstruccion);
	
}
