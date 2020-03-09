package es.infouned.configuracion;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Clase que se encarga de cargar las propiedades de configuraci�n de un fichero a partir de su ruta.
 * @author Alberto Mart�nez Montenegro
 *
 */
public class PropiedadesConfiguracion {
	private static Properties propiedadesConfiguracion = new Properties();
	
	/**
	 * M�todo que carga las propiedades de configuraci�n (usuarios, constrase�as, par�metros, etc.) que son necesarias para la conexi�n a la base de datos, el enlace con Facebook, el enlace con Telegram, etc.
	 * Se le debe pasar como par�metro la ruta del fichero de configuraci�n.
	 * @param rutaFicheroConfiguracion
	 */
    public static void establecerPropiedadesConfiguracionAPartirDeFichero(String rutaFicheroConfiguracion) {
    	try {
    		InputStream inputStreamConfiguracion = PropiedadesConfiguracion.class.getResourceAsStream(rutaFicheroConfiguracion); 
    		propiedadesConfiguracion.load(inputStreamConfiguracion);
		} catch (IOException e) {
			System.out.println("No se ha podido acceder al fichero de configuraci�n del sistema('" + 
								rutaFicheroConfiguracion + "'), la ejecuci�n no puede continuar.");
			e.printStackTrace();
		}
    }
    
    public static String getPropiedad(String idPropiedad) {
    	return propiedadesConfiguracion.getProperty(idPropiedad);
    }
}
