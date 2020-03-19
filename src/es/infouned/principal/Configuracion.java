package es.infouned.principal;

import static org.junit.Assert.assertFalse;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;
import es.infouned.aprendizajeAutomatico.Clasificador;
import es.infouned.aprendizajeAutomatico.ClasificadorNaiveBayes;
import es.infouned.conversacion.Conversacion;
import es.infouned.procesamientoLenguajeNatural.ProcesadorLenguajeNatural;
import es.infouned.procesamientoLenguajeNatural.ProcesadorLenguajeNaturalStanford;

public class Configuracion {
	private static ArrayList<Conversacion> conversaciones =  new ArrayList<Conversacion>();
	private static ProcesadorLenguajeNatural procesadorLenguajeNatural;
	private static Clasificador clasificador;
	private static Properties propiedadesConfiguracion = new Properties();
	private static Properties propiedadesListaDeConsultasSQL = new Properties();
	
	/**
	 * Este m�todo devuelve una conversaci�n a partir de su chat_id, si no hay ninguna conversaci�n con el chat_id que se le pase como par�metro devolver� una nueva conversaci�n.
	 * Se trata de un m�todo 'synchronized', por lo no podr� ser invocado de nuevo si ya est� en uso hasta que finalice su ejecuci�n.
	 * @param chat_id
	 * @param origen
	 * @return
	 */
    public synchronized static Conversacion obtenerConversacion(String chat_id, String origen) {
    	for(Conversacion conversacion : conversaciones) {
    		String idConversacion = conversacion.getIdConversacion();
    		if(idConversacion.equals(chat_id)) {
    			return conversacion;
    		}
    	}
    	Conversacion conversacion  = new Conversacion(chat_id, origen);
    	conversaciones.add(conversacion);
    	return conversacion;
    }
    
	/**
	 * M�todo que carga las propiedades de configuraci�n (usuarios, constrase�as, par�metros, etc.) que son necesarias para la conexi�n a la base de datos, el enlace con Facebook, el enlace con Telegram, etc.
	 * Se le debe pasar como par�metro la ruta del fichero de configuraci�n.
	 * @param rutaFicheroConfiguracion
	 */
    public static void establecerPropiedadesConfiguracionAPartirDeFichero(String rutaFicheroConfiguracion) {
    	try {
    		InputStream inputStreamConfiguracion = Configuracion.class.getResourceAsStream(rutaFicheroConfiguracion); 
    		propiedadesConfiguracion.load(inputStreamConfiguracion);
		} catch (IOException e) {
			System.out.println("No se ha podido acceder al fichero de configuraci�n del sistema('" + 
								rutaFicheroConfiguracion + "'), la ejecuci�n no puede continuar.");
			e.printStackTrace();
		}
    	String rutaFicheroConsultasSQL = getPropiedad("rutaFicheroConsultasSQL");
    	establecerPropiedadesDeListaDeConsultasSQLAPartirDeFichero(rutaFicheroConsultasSQL);
    	String rutaFicheroModeloAprendizajeAutomaticoEntrenado = getPropiedad("rutaFicheroModeloAprendizajeAutomaticoEntrenado");
    	String rutaFicheroEstructuraDataSet = getPropiedad("rutaFicheroEstructuraDataSet");
    	establecerClasificador("naiveBayes", rutaFicheroModeloAprendizajeAutomaticoEntrenado, rutaFicheroEstructuraDataSet);	
    }
    
    public static void iniciarProcesadorLenguajeNatural() {
    	String rutaFicheroConfigunarcionStanfordNLP = getPropiedad("rutaFicheroConfigunarcionStanfordNLP");
    	iniciarProcesadorLenguajeNatural("procesadorLenguajeNaturalStanford", rutaFicheroConfigunarcionStanfordNLP);
    }
    
	/**
	 * 
	 * M�todo que carga el listado con todas las posibles consultas SQL.
	 * Se le debe pasar como par�metro la ruta del fichero.
	 * @param rutaFicheroListaDeConsultasSQL
	 */
    public static void establecerPropiedadesDeListaDeConsultasSQLAPartirDeFichero(String rutaFicheroListaDeConsultasSQL) {
    	try {
    		InputStream inputStreamListaDeConsultasSQL = Configuracion.class.getResourceAsStream(rutaFicheroListaDeConsultasSQL); 
    		propiedadesListaDeConsultasSQL.load(inputStreamListaDeConsultasSQL);
		} catch (IOException e) {
			System.out.println("No se ha podido acceder al fichero de consultas SQL('" + 
								rutaFicheroListaDeConsultasSQL + "'), la ejecuci�n no puede continuar.");
			e.printStackTrace();
		}
    }
    
	public static void iniciarProcesadorLenguajeNatural(String nombreProcesadorLenguajeNatural, String rutaFicheroConfigunarcionNLP) {
		switch(nombreProcesadorLenguajeNatural) {
			case "procesadorLenguajeNaturalStanford":
				procesadorLenguajeNatural = new ProcesadorLenguajeNaturalStanford(rutaFicheroConfigunarcionNLP);
			break;
			default:
				procesadorLenguajeNatural = new ProcesadorLenguajeNaturalStanford(rutaFicheroConfigunarcionNLP);
			break;
		}
	}

	public static void establecerClasificador(String nombreClasificador, String rutaFicheroModeloAprendizajeAutomaticoEntrenado, String rutaFicheroEstructuraDataSet) {
		switch(nombreClasificador) {
			case "naiveBayes":
				clasificador = new ClasificadorNaiveBayes(rutaFicheroModeloAprendizajeAutomaticoEntrenado, rutaFicheroEstructuraDataSet);
			break;
			default:
				clasificador = new ClasificadorNaiveBayes(rutaFicheroModeloAprendizajeAutomaticoEntrenado, rutaFicheroEstructuraDataSet);
			break;
		}
	}
	
    public static ArrayList<Conversacion> getConversaciones(){
    	return conversaciones;
    }
    
    public static String getPropiedad(String idPropiedad) {
    	return propiedadesConfiguracion.getProperty(idPropiedad);
    }
    
    public static String obtenerConsultaSQL(String identificadorConsulta) {
    	assertFalse(propiedadesListaDeConsultasSQL.getProperty(identificadorConsulta) == null);
    	return propiedadesListaDeConsultasSQL.getProperty(identificadorConsulta);
    }
    
	public static ProcesadorLenguajeNatural getProcesadorLenguajeNatural() {
		return procesadorLenguajeNatural;
	}
	
	public static Clasificador getClasificador() {
		return clasificador;
	}
	
}
