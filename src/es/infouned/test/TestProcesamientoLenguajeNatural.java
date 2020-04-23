package es.infouned.test;



import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Stack;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import es.infouned.estudios.Estudio;
import es.infouned.principal.Configuracion;
import es.infouned.procesamientoLenguajeNatural.DistanciaDeLevenshtein;
import es.infouned.procesamientoLenguajeNatural.ProcesadorLenguajeNatural;
import es.infouned.utilidades.ProcesamientoDeTexto;


public class TestProcesamientoLenguajeNatural {
	private static ProcesadorLenguajeNatural procesadorLenguajeNatural;
	
	@BeforeAll
	private static void inicializarPropiedades() {
		String rutaFicheroConfiguracion = "/config.properties";
		Configuracion.establecerPropiedadesConfiguracionAPartirDeFichero(rutaFicheroConfiguracion);
		Configuracion.iniciarProcesadorLenguajeNatural();
		procesadorLenguajeNatural = Configuracion.getProcesadorLenguajeNatural();
	}
	
	@AfterAll
	private static void eliminarConfiguracion() {
		Configuracion.eliminarTodo();
	}
	
	@Test
	public void testDistanciaLevenshtein() {
		assertTrue(DistanciaDeLevenshtein.calcularDistanciaDeLevenshtein("infrmático", "informática") == 2);
		Stack<String> variantes = DistanciaDeLevenshtein.construirVariantes("Hola");
		for(String variante: variantes) {
			System.out.println(variante);
		}
		System.out.println(variantes.size());
	}

	@Test
	public void testGeneralTextoLargo(){
		String textoObjetivoDeAnalisis = ProcesamientoDeTexto.normalizarTexto("La Escuela pretende ofertar a la sociedad un título competitivo que se fundamenta en las indicaciones de Resolución de 8 de Junio de 2009,"
								+ " de la Secretaría General de Universidades (BOE de 4 de agosto de 2009) y se orienta, por un lado, a aprender la asignatura programación y estructuras de datos avanzadas."
								+ "Se caracteriza esta orientación por su especial incidencia en los fundamentos. "
								+ "Pero por otro lado, también hacia el tradicional (en España) informático generalista, "
								+ "de amplia formación que le permita desempeñar diferentes papeles o perfiles profesionales "
								+ "tal y como la describe el profesor Albert Einstein elaborado por la Conferencia de Decanos y Directores de Centros Universitarios de Informática (CODDI). "
								+ "Así, se intensifica su formación en competencias comunes para los informáticos, pero también competencias propias de otros perfiles, "
								+ "particularmente en ingeniería de computadores y en ingeniería del software. El profesional del grado en Ingeniería Informática es capaz de satisfacer "
								+ "por tanto una demanda en el marco empresarial, avalada por un referente reconocido como es la Association for Computing Machinery (ACM), "
								+ "a todas las escalas laborales y cuenta también con una sólida formación en GESTIÓN DE EMPRESAS INFORMÁTICAS. ¿Cual es la dificultad de la asignatura estrategias de programación y estructuras de datos?"
								+ "El año pasado murieron muchas abejas haciendo el proyecto de fin de grado.");
		procesadorLenguajeNatural.procesarTextoObjetivoDeAnalisis(textoObjetivoDeAnalisis);
		System.out.println(procesadorLenguajeNatural.obtenerAnaliticaVisualDeTexto("\n"));
	    assertTrue(procesadorLenguajeNatural.obtenerFrase(2).getTextoFrase().equals("pero por otro lado, tambien hacia el tradicional (en espana) informatico generalista, de amplia formacion que le permita desempenar diferentes papeles o perfiles profesionales tal y como la describe el profesor albert einstein elaborado por la conferencia de decanos y directores de centros universitarios de informatica (coddi)."));
	}
	
	
	@Test
	public void testDeteccionEstudios(){
		ArrayList<Estudio> estudiosAludidos;
		String textoObjetivoDeAnalisis;
		String nombrePrimerEstudioAludido;
		String nombreSegundoEstudioAludido;
		String nombreTercerEstudioAludido;
		String nombreCuartoEstudioAludido;
		String nombreQuintoEstudioAludido;
		String nombreSextoEstudioAludido;
		
		textoObjetivoDeAnalisis = ProcesamientoDeTexto.normalizarTexto("En esta frase no hay ningún estudio aludido.");
		procesadorLenguajeNatural.procesarTextoObjetivoDeAnalisis(textoObjetivoDeAnalisis);
		estudiosAludidos = procesadorLenguajeNatural.obtenerFrase(0).getEstudiosAludidos();
		assertTrue(estudiosAludidos.size() == 0);
		
		textoObjetivoDeAnalisis = ProcesamientoDeTexto.normalizarTexto("¿Cual es la asignatura más dificil del grado en ingeniería en tecnologías de la información?");
		procesadorLenguajeNatural.procesarTextoObjetivoDeAnalisis(textoObjetivoDeAnalisis);
		estudiosAludidos = procesadorLenguajeNatural.obtenerFrase(0).getEstudiosAludidos();
		nombrePrimerEstudioAludido = estudiosAludidos.get(0).getNombre();
		assertTrue(nombrePrimerEstudioAludido.equals("GRADO EN INGENIERÍA EN TECNOLOGÍAS DE LA INFORMACIÓN"));
		
		textoObjetivoDeAnalisis = ProcesamientoDeTexto.normalizarTexto("¿Se aprueba fácilmente la asignatura fundamentos matemáticos de la informática de la titulación grado en ingeniería informática?");
		procesadorLenguajeNatural.procesarTextoObjetivoDeAnalisis(textoObjetivoDeAnalisis);
		estudiosAludidos = procesadorLenguajeNatural.obtenerFrase(0).getEstudiosAludidos();
		assertTrue(estudiosAludidos.size() == 2);
		nombrePrimerEstudioAludido = estudiosAludidos.get(0).getNombre();
		nombreSegundoEstudioAludido = estudiosAludidos.get(1).getNombre();
		assertTrue(nombrePrimerEstudioAludido.equals("FUNDAMENTOS MATEMÁTICOS DE LA INFORMÁTICA"));
		assertTrue(nombreSegundoEstudioAludido.equals("GRADO EN INGENIERÍA INFORMÁTICA"));
		
		textoObjetivoDeAnalisis = ProcesamientoDeTexto.normalizarTexto("En el primer cuatrimestre del grado en psicologia nos encontramos asignaturas en con diversos niveles de dificultad, como introduccion al analisis de datos, PSICOLOGÍA DE LA MOTIVACIÓN, FUNDAMENTOS DE PSICOBIOLOGÍA, PSICOLOGÍA DE LA ATENCIÓN, PSICOLOGÍA DE LA EMOCIÓN.");
		procesadorLenguajeNatural.procesarTextoObjetivoDeAnalisis(textoObjetivoDeAnalisis);
		estudiosAludidos = procesadorLenguajeNatural.obtenerFrase(0).getEstudiosAludidos();
		nombrePrimerEstudioAludido = estudiosAludidos.get(0).getNombre();
		nombreSegundoEstudioAludido = estudiosAludidos.get(1).getNombre();
		nombreTercerEstudioAludido = estudiosAludidos.get(2).getNombre();
		nombreCuartoEstudioAludido = estudiosAludidos.get(3).getNombre();
		nombreQuintoEstudioAludido = estudiosAludidos.get(4).getNombre();
		nombreSextoEstudioAludido = estudiosAludidos.get(5).getNombre();
		assertTrue(nombrePrimerEstudioAludido.equals("GRADO EN PSICOLOGÍA"));
		assertTrue(nombreSegundoEstudioAludido.equals("INTRODUCCIÓN AL ANÁLISIS DE DATOS"));
		assertTrue(nombreTercerEstudioAludido.equals("PSICOLOGÍA DE LA MOTIVACIÓN"));
		assertTrue(nombreCuartoEstudioAludido.equals("FUNDAMENTOS DE PSICOBIOLOGÍA"));
		assertTrue(nombreQuintoEstudioAludido.equals("PSICOLOGÍA DE LA ATENCIÓN"));
		assertTrue(nombreSextoEstudioAludido.equals("PSICOLOGÍA DE LA EMOCIÓN"));
	}
	
	
	
}