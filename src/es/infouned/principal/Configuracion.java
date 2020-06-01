package es.infouned.principal;

import static org.junit.Assert.assertFalse;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Stack;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;


import es.infouned.aprendizajeAutomatico.Clasificador;
import es.infouned.aprendizajeAutomatico.ClasificadorNaiveBayes;
import es.infouned.baseDeDatos.ConexionBaseDeDatos;
import es.infouned.baseDeDatos.ConexionMySQL;
import es.infouned.conversacion.Conversacion;
import es.infouned.conversacion.Conversacion.OrigenConversacion;
import es.infouned.estudios.CriterioConsultaSQL;
import es.infouned.estudios.Estudio.TipoEstudio;
import es.infouned.estudios.IndicadorOrdenamiento;
import es.infouned.estudios.NivelEstudios;
import es.infouned.estudios.NivelEstudios.NombreNivelEstudios;
import es.infouned.estudios.ParametroEstadistico;
import es.infouned.procesamientoLenguajeNatural.ProcesadorLenguajeNatural;
import es.infouned.procesamientoLenguajeNatural.ProcesadorLenguajeNaturalStanford;
import es.infouned.utilidades.ProcesamientoDeTexto;

/**
 * Clase que simboliza la configuración del sistema. Se encarga de crear, contener y facilitar todas las herramientas necesarias para que funcione el chatbot.
 * @author Alberto Martínez Montenegro
 * 
 */
public class Configuracion {
	private static ArrayList<Conversacion> conversaciones =  new ArrayList<Conversacion>();
	private static ProcesadorLenguajeNatural procesadorLenguajeNatural;
	private static Clasificador clasificador;
	private static Properties propiedadesConfiguracion = new Properties();
	private static Properties propiedadesListaDeConsultasSQL = new Properties();
	private static ConexionBaseDeDatos conexionBaseDeDatos= new ConexionMySQL();
	private static ArrayList<ParametroEstadistico> parametrosEstadisticos = new ArrayList<ParametroEstadistico>();
	private static Stack<NivelEstudios> nivelesEstudios = new Stack<NivelEstudios>();
	private static Stack<IndicadorOrdenamiento> indicadoresOrdenamiento = new Stack<IndicadorOrdenamiento>();
	private static Stack<CriterioConsultaSQL> criteriosConsultaSQL = new Stack<CriterioConsultaSQL>();
	
	/**
	 * Este método devuelve una conversación a partir de su chat_id, si no hay ninguna conversación con el chat_id que se le pase como parámetro devolverá una nueva conversación.
	 * Se trata de un método 'synchronized', por lo no podrá ser invocado de nuevo si ya está en uso hasta que finalice su ejecución.
	 * @param chat_id
	 * @param origen
	 * @return
	 */
    public synchronized static Conversacion obtenerConversacion(String chat_id, OrigenConversacion origen) {
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
	 * Método que carga las propiedades de configuración (usuarios, constraseñas, parámetros, etc.) que son necesarias para la conexión a la base de datos, el enlace con Facebook, el enlace con Telegram, etc.
	 * Se le debe pasar como parámetro la ruta del fichero de configuración.
	 * @param rutaFicheroConfiguracion
	 */
    public static void establecerPropiedadesConfiguracionAPartirDeFichero(String rutaFicheroConfiguracion) {
    	try {
    		InputStream inputStreamConfiguracion = Configuracion.class.getResourceAsStream(rutaFicheroConfiguracion); 
    		propiedadesConfiguracion.load(inputStreamConfiguracion);
		} catch (IOException e) {
			System.out.println("No se ha podido acceder al fichero de configuración del sistema('" + 
								rutaFicheroConfiguracion + "'), la ejecución no puede continuar.");
			e.printStackTrace();
		}
    	String rutaFicheroConsultasSQL = getPropiedad("rutaFicheroConsultasSQL");
    	establecerPropiedadesDeListaDeConsultasSQLAPartirDeFichero(rutaFicheroConsultasSQL);
    	String rutaFicheroModeloAprendizajeAutomaticoEntrenado = getPropiedad("rutaFicheroModeloAprendizajeAutomaticoEntrenado");
    	String rutaFicheroEstructuraDataSet = getPropiedad("rutaFicheroEstructuraDataSet");
    	establecerClasificador("naiveBayes", rutaFicheroModeloAprendizajeAutomaticoEntrenado, rutaFicheroEstructuraDataSet);
    	String rutaFicheroPalabrasClave = getPropiedad("rutaFicheroPalabrasClave");
    	establecerParametrosEstadisticosAPartirDeFichero(rutaFicheroPalabrasClave);
    }
    
    public static void iniciarProcesadorLenguajeNatural() {
    	String rutaFicheroConfigunarcionStanfordNLP = getPropiedad("rutaFicheroConfigunarcionStanfordNLP");
    	iniciarProcesadorLenguajeNatural("procesadorLenguajeNaturalStanford", rutaFicheroConfigunarcionStanfordNLP);
    }
    
	/**
	 * 
	 * Método que carga el listado con todas las posibles consultas SQL.
	 * Se le debe pasar como parámetro la ruta del fichero.
	 * @param rutaFicheroListaDeConsultasSQL
	 */
    public static void establecerPropiedadesDeListaDeConsultasSQLAPartirDeFichero(String rutaFicheroListaDeConsultasSQL) {
    	try {
    		InputStream inputStreamListaDeConsultasSQL = Configuracion.class.getResourceAsStream(rutaFicheroListaDeConsultasSQL); 
    		propiedadesListaDeConsultasSQL.load(inputStreamListaDeConsultasSQL);
		} catch (IOException e) {
			System.out.println("No se ha podido acceder al fichero de consultas SQL('" + 
								rutaFicheroListaDeConsultasSQL + "'), la ejecución no puede continuar.");
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
	
    public static void establecerParametrosEstadisticosAPartirDeFichero(String rutaFicheroPalabrasClave) {
    	try {
    		InputStream inputStreamPalabrasClave = Configuracion.class.getResourceAsStream(rutaFicheroPalabrasClave); 
    		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();  
    		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();  
    		Document document = documentBuilder.parse(inputStreamPalabrasClave);  
    		document.getDocumentElement().normalize();  
    		Node node;
    		Element eElement;
    		TipoEstudio tipoEstudio;
			String literal;	
			String nombre;
    		NodeList nodeList = document.getElementsByTagName("parametroEstadistico");  
    		for (int itr = 0; itr < nodeList.getLength(); itr++) {  
    			Stack<String> nomenclaturas = new Stack<String>();
	    		node = nodeList.item(itr);  
	    		if (node.getNodeType() == Node.ELEMENT_NODE) {  
		    		eElement = (Element) node;  
		    		literal = eElement.getElementsByTagName("literal").item(0).getTextContent();
    				nombre = eElement.getElementsByTagName("nombre").item(0).getTextContent();
    				String textoTipoEstudio = ProcesamientoDeTexto.normalizarTexto(eElement.getElementsByTagName("tipoEstudio").item(0).getTextContent()).toUpperCase();
        			tipoEstudio = TipoEstudio.valueOf(textoTipoEstudio);
        			int indice = 0;
        			while(eElement.getElementsByTagName("nomenclatura").item(indice)!=null) {
        				nomenclaturas.push(eElement.getElementsByTagName("nomenclatura").item(indice).getTextContent());
        				indice++;
        			}
        			ParametroEstadistico parametroEstadistico = new ParametroEstadistico(tipoEstudio, literal, nombre, nomenclaturas);
        			parametrosEstadisticos.add(parametroEstadistico);
	    		}  
    		} 
    		
    		nodeList = document.getElementsByTagName("indicadorOrdenamiento");  
    		for (int itr = 0; itr < nodeList.getLength(); itr++) {  
    			Stack<String> nomenclaturas = new Stack<String>();
	    		node = nodeList.item(itr);  
	    		if (node.getNodeType() == Node.ELEMENT_NODE) {  
		    		eElement = (Element) node;  
    				literal = eElement.getElementsByTagName("literal").item(0).getTextContent();
    				nombre = eElement.getElementsByTagName("nombre").item(0).getTextContent();
        			int indice = 0;
        			while(eElement.getElementsByTagName("nomenclatura").item(indice)!=null) {
        				nomenclaturas.push(eElement.getElementsByTagName("nomenclatura").item(indice).getTextContent());
        				indice++;
        			}
        			IndicadorOrdenamiento indicadorOrdenamiento = new IndicadorOrdenamiento(literal, nombre, nomenclaturas);
        			indicadoresOrdenamiento.add(indicadorOrdenamiento);
	    		}  
    		} 

    		nodeList = document.getElementsByTagName("nivelEstudios");  
    		for (int itr = 0; itr < nodeList.getLength(); itr++) {  
    			Stack<String> nomenclaturas = new Stack<String>();
	    		node = nodeList.item(itr);  
	    		if (node.getNodeType() == Node.ELEMENT_NODE) {  
		    		eElement = (Element) node;  
    				nombre = eElement.getElementsByTagName("nombre").item(0).getTextContent();
        			int indice = 0;
        			while(eElement.getElementsByTagName("nomenclatura").item(indice)!=null) {
        				nomenclaturas.push(eElement.getElementsByTagName("nomenclatura").item(indice).getTextContent());
        				indice++;
        			}
        			String nombreNormalizadoMayusculas = ProcesamientoDeTexto.normalizarTexto(nombre).toUpperCase();
        			NivelEstudios nivelEstudios = new NivelEstudios(NombreNivelEstudios.valueOf(nombreNormalizadoMayusculas), nomenclaturas);
        			nivelesEstudios.add(nivelEstudios);
	    		}  
    		} 
    		
    		nodeList = document.getElementsByTagName("criterioConsultaSQL");  
    		for (int itr = 0; itr < nodeList.getLength(); itr++) {  
    			Stack<String> nomenclaturas = new Stack<String>();
	    		node = nodeList.item(itr);  
	    		if (node.getNodeType() == Node.ELEMENT_NODE) {  
		    		eElement = (Element) node;  
		    		literal = eElement.getElementsByTagName("literal").item(0).getTextContent();
    				nombre = eElement.getElementsByTagName("nombre").item(0).getTextContent();
        			int indice = 0;
        			while(eElement.getElementsByTagName("nomenclatura").item(indice)!=null) {
        				nomenclaturas.push(eElement.getElementsByTagName("nomenclatura").item(indice).getTextContent());
        				indice++;
        			}
        			CriterioConsultaSQL criterioConsultaSQL = new CriterioConsultaSQL(literal, nombre, nomenclaturas);
        			criteriosConsultaSQL.add(criterioConsultaSQL);
	    		}  
    		} 
		} catch (IOException e) {
			System.out.println("No se ha podido acceder al fichero XML de parámetros estadísticos ('" + 
					rutaFicheroPalabrasClave + "'), debido a una excepción de tipo IOException.");
			e.printStackTrace();
		}
    	catch (ParserConfigurationException e) {
    		System.out.println("No se ha podido acceder al fichero XML de parámetros estadísticos ('" + 
    				rutaFicheroPalabrasClave + "'), debido a una excepción de tipo ParserConfigurationException.");
			e.printStackTrace();
		}
    	catch (SAXException e) {
    		System.out.println("No se ha podido acceder al fichero XML de parámetros estadísticos ('" + 
    				rutaFicheroPalabrasClave + "'), debido a una excepción de tipo SAXException.");
			e.printStackTrace();
		}
    }
	
    public static Stack<IndicadorOrdenamiento> getIndicadoresOrdenamiento() {
		return indicadoresOrdenamiento;
	}

	public static Stack<CriterioConsultaSQL> getCriteriosConsultaSQL() {
		return criteriosConsultaSQL;
	}

	public static ArrayList<ParametroEstadistico> getParametrosEstadisticos() {
		return parametrosEstadisticos;
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
	
	public static ConexionBaseDeDatos getConexionBaseDeDatos() {
		return conexionBaseDeDatos;
	}
	
	public static Stack<NivelEstudios> getNivelesEstudios() {
		return nivelesEstudios;
	}

	public static void setNivelesEstudios(Stack<NivelEstudios> nivelesEstudios) {
		Configuracion.nivelesEstudios = nivelesEstudios;
	}

	
	public static void eliminarTodo() {
		conversaciones =  new ArrayList<Conversacion>();
		procesadorLenguajeNatural = null;
		clasificador = null;
		propiedadesConfiguracion = new Properties();
		propiedadesListaDeConsultasSQL = new Properties();
		conexionBaseDeDatos= new ConexionMySQL();
		parametrosEstadisticos = new ArrayList<ParametroEstadistico>();
		indicadoresOrdenamiento = new Stack<IndicadorOrdenamiento>();
		criteriosConsultaSQL = new Stack<CriterioConsultaSQL>();
	}
}
