package xyz.infouned.baseDeDatos;

/**
 * Clase abstracta dise�ada para que los distintos tipos de operaciones SQL hereden de ella, pudiendo gestionar la conexi�n y obligando a implementar los m�todos abstractos.
 * @author Alberto Mart�nez Montenegro
 *
 */
public abstract class Sql {
	protected ConexionBaseDeDatos conexion;
	
	public Sql(ConexionBaseDeDatos conexion) {
		this.conexion = conexion;
	}
	
	public abstract void ejecutarConsulta(String cadenaDeTextoInstruccion);
	
}
