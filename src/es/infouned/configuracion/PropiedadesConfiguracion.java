package es.infouned.configuracion;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Clase que se encarga de cargar las propiedades de configuración de un fichero a partir de su ruta.
 * @author Alberto Martínez Montenegro
 *
 */
public class PropiedadesConfiguracion {
	private static Properties propiedadesConfiguracion = new Properties();
	
	/**
	 * Método que carga las propiedades de configuración (usuarios, constraseñas, parámetros, etc.) que son necesarias para la conexión a la base de datos, el enlace con Facebook, el enlace con Telegram, etc.
	 * Se le debe pasar como parámetro la ruta del fichero de configuración.
	 * @param rutaFicheroConfiguracion
	 */
    public static void establecerPropiedadesConfiguracionAPartirDeFichero(String rutaFicheroConfiguracion) {
    	try {
    		InputStream inputStreamConfiguracion = PropiedadesConfiguracion.class.getResourceAsStream(rutaFicheroConfiguracion); 
    		propiedadesConfiguracion.load(inputStreamConfiguracion);
		} catch (IOException e) {
			System.out.println("No se ha podido acceder al fichero de configuración del sistema('" + 
								rutaFicheroConfiguracion + "'), la ejecución no puede continuar.");
			e.printStackTrace();
		}
    }
    
    public static String getPropiedad(String idPropiedad) {
    	return propiedadesConfiguracion.getProperty(idPropiedad);
    }
}
