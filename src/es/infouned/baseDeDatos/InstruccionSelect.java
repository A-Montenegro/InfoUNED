package es.infouned.baseDeDatos;

import static org.junit.Assert.assertFalse;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Clase que simboliza la entidad de una instrucción SQL del tipo 'SELECT'
 * @author Alberto Martínez Montenegro
 *
 */
public class InstruccionSelect extends Sql{
	private ResultSet resultSet=null;
	
	public InstruccionSelect(ConexionBaseDeDatos conexion) {
		super(conexion);	
	}
	
	/**
	 * Método que ejecuta una consulta SQL y guarda los resultados en el campo 'resultSet' de esta clase.
	 */
	public void ejecutarConsulta(String cadenaDeTextoInstruccion) {
		try {
			Statement instruccionSQL = conexion.getConexion().createStatement();
			resultSet = instruccionSQL.executeQuery(cadenaDeTextoInstruccion);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Para efectuar una llamada a este método es OBLIGATORIO que antes se haya efectuado una consulta SQL mediante el método 'ejecutarConsulta' de esta misma clase.
	 * Este método devuelve el resultSet con los resultados de la última consulta que se ha ejecutado mediante el método 'ejecutarConsulta'.
	 * El assert comprueba que el resultSet no sea nulo, ya que si fuese así significaría que no se ha ejecutado previamente el método 'ejecutarConsulta'.
	 * @return
	 */
	public ResultSet getResultSet() {
		assertFalse(resultSet == null); 
		return resultSet;
	}
}
