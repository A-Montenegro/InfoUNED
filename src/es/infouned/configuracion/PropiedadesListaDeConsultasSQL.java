package es.infouned.configuracion;

import static org.junit.Assert.assertFalse;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Clase que se encarga de cargar las diferentes consultas SQL desde un fichero a partir de su ruta.
 * Estas consultas SQL contienen ciertas palabras clave que deben ser sustituidas.
 * @author Alberto Martínez Montenegro
 *
 */
public class PropiedadesListaDeConsultasSQL {
	private static Properties propiedadesListaDeConsultasSQL = new Properties();;
	
	/**
	 * 
	 * Método que carga el listado con todas las posibles consultas SQL.
	 * Se le debe pasar como parámetro la ruta del fichero.
	 * @param rutaFicheroListaDeConsultasSQL
	 */
    public static void establecerPropiedadesDeListaDeConsultasSQLAPartirDeFichero(String rutaFicheroListaDeConsultasSQL) {
    	try {
    		InputStream inputStreamListaDeConsultasSQL = PropiedadesListaDeConsultasSQL.class.getResourceAsStream(rutaFicheroListaDeConsultasSQL); 
    		propiedadesListaDeConsultasSQL.load(inputStreamListaDeConsultasSQL);
		} catch (IOException e) {
			System.out.println("No se ha podido acceder al fichero de consultas SQL('" + 
								rutaFicheroListaDeConsultasSQL + "'), la ejecución no puede continuar.");
			e.printStackTrace();
		}
    }
    
    public static String obtenerConsultaSQL(String identificadorConsulta) {
    	assertFalse(propiedadesListaDeConsultasSQL.getProperty(identificadorConsulta) == null);
    	return propiedadesListaDeConsultasSQL.getProperty(identificadorConsulta);
    }
    
}