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
		assertTrue(DistanciaDeLevenshtein.calcularDistanciaDeLevenshtein("infrmático", "informática") == 2);
	}
	
	@Test
	public void testAnalisisStanfordNLP(){
		String rutaFicheroConfigunarcionStanfordNLP = "/StanfordCoreNLP-spanish.properties";
		ProcesadorLenguajeNatural procesadorLenguajeNatural = new ProcesadorLenguajeNaturalStanford(rutaFicheroConfigunarcionStanfordNLP);
		String textoObjetivoDeAnalisis = "La Escuela pretende ofertar a la sociedad un título competitivo que se fundamenta en las indicaciones de Resolución de 8 de Junio de 2009,"
								+ " de la Secretaría General de Universidades (BOE de 4 de agosto de 2009) y se orienta, por un lado, a aprender la asignatura programación y estructuras de datos avanzadas."
								+ "Se caracteriza esta orientación por su especial incidencia en los fundamentos. "
								+ "Pero por otro lado, también hacia el tradicional (en España) informático generalista, "
								+ "de amplia formación que le permita desempeñar diferentes papeles o perfiles profesionales "
								+ "tal y como la describe el profesor Albert Einstein elaborado por la Conferencia de Decanos y Directores de Centros Universitarios de Informática (CODDI). "
								+ "Así, se intensifica su formación en competencias comunes para los informáticos, pero también competencias propias de otros perfiles, "
								+ "particularmente en ingeniería de computadores y en ingeniería del software. El profesional Graduado/a en Ingeniería Informática es capaz de satisfacer "
								+ "por tanto una demanda en el marco empresarial, avalada por un referente reconocido como es la Association for Computing Machinery (ACM), "
								+ "a todas las escalas laborales y cuenta también con una sólida formación en GESTIÓN DE EMPRESAS INFORMÁTICAS. ¿Cual es la dificultad de la asignatura estrategias de programación y estructuras de datos?"
								+ "El año pasado murieron muchas abejas haciendo el proyecto de fin de grado.";
		procesadorLenguajeNatural.procesarTextoObjetivoDeAnalisis(textoObjetivoDeAnalisis);
	    /** Descomentar la siguiente línea para visualizar por salida estándar **/
	    //System.out.println(cadenaDeTextoAcumulativa);
	    System.out.println(procesadorLenguajeNatural.obtenerAnaliticaVisualDeTexto("\n"));
	    assertTrue(procesadorLenguajeNatural.obtenerFrase(0).getTextoFrase().equals("La Escuela pretende ofertar a la sociedad un título competitivo que se fundamenta en las indicaciones de "
									    		+ "Resolución de 8 de Junio de 2009, de la Secretaría General de Universidades (BOE de 4 de agosto de 2009) y se orienta, "
									    		+ "por un lado, a aprender la asignatura programación y estructuras de datos avanzadas ."));
	}	
}