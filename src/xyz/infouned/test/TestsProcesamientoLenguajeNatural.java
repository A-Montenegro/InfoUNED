package xyz.infouned.test;



import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import xyz.infouned.configuracion.PropiedadesConfiguracion;
import xyz.infouned.configuracion.PropiedadesListaDeConsultasSQL;
import xyz.infouned.procesamientoLenguajeNatural.DistanciaDeLevenshtein;
import xyz.infouned.procesamientoLenguajeNatural.ProcesadorLenguajeNatural;
import xyz.infouned.procesamientoLenguajeNatural.ProcesadorLenguajeNaturalStanford;


public class TestsProcesamientoLenguajeNatural {
	
	@BeforeAll
	private static void inicializarPropiedades() {
		String rutaFicheroConfiguracion = "/config.properties";
		PropiedadesConfiguracion.establecerPropiedadesConfiguracionAPartirDeFichero(rutaFicheroConfiguracion);
		String rutaFicheroConsultasSQL = "/consultasSQL.properties";
	    PropiedadesListaDeConsultasSQL.establecerPropiedadesDeListaDeConsultasSQLAPartirDeFichero(rutaFicheroConsultasSQL);
	}
	
	@Test
	public void testDistanciaLevenshtein() {
		assertTrue(DistanciaDeLevenshtein.calcularDistanciaDeLevenshtein("infrm�tico", "inform�tica") == 2);
	}
	
	@Test
	public void testAnalisisStanfordNLP(){
		String rutaFicheroConfigunarcionStanfordNLP = "/StanfordCoreNLP-spanish.properties";
		ProcesadorLenguajeNatural procesadorLenguajeNatural = new ProcesadorLenguajeNaturalStanford(rutaFicheroConfigunarcionStanfordNLP);
		String textoObjetivoDeAnalisis = "La Escuela pretende ofertar a la sociedad un t�tulo competitivo que se fundamenta en las indicaciones de Resoluci�n de 8 de Junio de 2009,"
								+ " de la Secretar�a General de Universidades (BOE de 4 de agosto de 2009) y se orienta, por un lado, a aprender la asignatura programaci�n y estructuras de datos avanzadas."
								+ "Se caracteriza esta orientaci�n por su especial incidencia en los fundamentos. "
								+ "Pero por otro lado, tambi�n hacia el tradicional (en Espa�a) inform�tico generalista, "
								+ "de amplia formaci�n que le permita desempe�ar diferentes papeles o perfiles profesionales "
								+ "tal y como la describe el profesor Albert Einstein elaborado por la Conferencia de Decanos y Directores de Centros Universitarios de Inform�tica (CODDI). "
								+ "As�, se intensifica su formaci�n en competencias comunes para los inform�ticos, pero tambi�n competencias propias de otros perfiles, "
								+ "particularmente en ingenier�a de computadores y en ingenier�a del software. El profesional Graduado/a en Ingenier�a Inform�tica es capaz de satisfacer "
								+ "por tanto una demanda en el marco empresarial, avalada por un referente reconocido como es la Association for Computing Machinery (ACM), "
								+ "a todas las escalas laborales y cuenta tambi�n con una s�lida formaci�n en GESTI�N DE EMPRESAS INFORM�TICAS. �Cual es la dificultad de la asignatura estrategias de programaci�n y estructuras de datos?"
								+ "El a�o pasado murieron muchas abejas haciendo el proyecto de fin de grado.";
		procesadorLenguajeNatural.procesarTextoObjetivoDeAnalisis(textoObjetivoDeAnalisis);
	    /** Descomentar la siguiente l�nea para visualizar por salida est�ndar **/
	    //System.out.println(cadenaDeTextoAcumulativa);
	    System.out.println(procesadorLenguajeNatural.obtenerAnaliticaVisualDeTexto("\n"));
	    assertTrue(procesadorLenguajeNatural.obtenerFrase(0).getTextoFrase().equals("La Escuela pretende ofertar a la sociedad un t�tulo competitivo que se fundamenta en las indicaciones de "
									    		+ "Resoluci�n de 8 de Junio de 2009, de la Secretar�a General de Universidades (BOE de 4 de agosto de 2009) y se orienta, "
									    		+ "por un lado, a aprender la asignatura programaci�n y estructuras de datos avanzadas ."));
	}	
}