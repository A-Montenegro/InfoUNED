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
		assertTrue(DistanciaDeLevenshtein.calcularDistanciaDeLevenshtein("infrm�tico", "inform�tica") == 2);
		Stack<String> variantes = DistanciaDeLevenshtein.construirVariantes("Hola");
		for(String variante: variantes) {
			System.out.println(variante);
		}
		System.out.println(variantes.size());
	}

	@Test
	public void testGeneralTextoLargo(){
		String textoObjetivoDeAnalisis = ProcesamientoDeTexto.normalizarTexto("La Escuela pretende ofertar a la sociedad un t�tulo competitivo que se fundamenta en las indicaciones de Resoluci�n de 8 de Junio de 2009,"
								+ " de la Secretar�a General de Universidades (BOE de 4 de agosto de 2009) y se orienta, por un lado, a aprender la asignatura programaci�n y estructuras de datos avanzadas."
								+ "Se caracteriza esta orientaci�n por su especial incidencia en los fundamentos. "
								+ "Pero por otro lado, tambi�n hacia el tradicional (en Espa�a) inform�tico generalista, "
								+ "de amplia formaci�n que le permita desempe�ar diferentes papeles o perfiles profesionales "
								+ "tal y como la describe el profesor Albert Einstein elaborado por la Conferencia de Decanos y Directores de Centros Universitarios de Inform�tica (CODDI). "
								+ "As�, se intensifica su formaci�n en competencias comunes para los inform�ticos, pero tambi�n competencias propias de otros perfiles, "
								+ "particularmente en ingenier�a de computadores y en ingenier�a del software. El profesional del grado en Ingenier�a Inform�tica es capaz de satisfacer "
								+ "por tanto una demanda en el marco empresarial, avalada por un referente reconocido como es la Association for Computing Machinery (ACM), "
								+ "a todas las escalas laborales y cuenta tambi�n con una s�lida formaci�n en GESTI�N DE EMPRESAS INFORM�TICAS. �Cual es la dificultad de la asignatura estrategias de programaci�n y estructuras de datos?"
								+ "El a�o pasado murieron muchas abejas haciendo el proyecto de fin de grado.");
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
		
		textoObjetivoDeAnalisis = ProcesamientoDeTexto.normalizarTexto("En esta frase no hay ning�n estudio aludido.");
		procesadorLenguajeNatural.procesarTextoObjetivoDeAnalisis(textoObjetivoDeAnalisis);
		estudiosAludidos = procesadorLenguajeNatural.obtenerFrase(0).getEstudiosAludidos();
		assertTrue(estudiosAludidos.size() == 0);
		
		textoObjetivoDeAnalisis = ProcesamientoDeTexto.normalizarTexto("�Cual es la asignatura m�s dificil del grado en ingenier�a en tecnolog�as de la informaci�n?");
		procesadorLenguajeNatural.procesarTextoObjetivoDeAnalisis(textoObjetivoDeAnalisis);
		estudiosAludidos = procesadorLenguajeNatural.obtenerFrase(0).getEstudiosAludidos();
		nombrePrimerEstudioAludido = estudiosAludidos.get(0).getNombre();
		assertTrue(nombrePrimerEstudioAludido.equals("GRADO EN INGENIER�A EN TECNOLOG�AS DE LA INFORMACI�N"));
		
		textoObjetivoDeAnalisis = ProcesamientoDeTexto.normalizarTexto("�Se aprueba f�cilmente la asignatura fundamentos matem�ticos de la inform�tica de la titulaci�n grado en ingenier�a inform�tica?");
		procesadorLenguajeNatural.procesarTextoObjetivoDeAnalisis(textoObjetivoDeAnalisis);
		estudiosAludidos = procesadorLenguajeNatural.obtenerFrase(0).getEstudiosAludidos();
		assertTrue(estudiosAludidos.size() == 2);
		nombrePrimerEstudioAludido = estudiosAludidos.get(0).getNombre();
		nombreSegundoEstudioAludido = estudiosAludidos.get(1).getNombre();
		assertTrue(nombrePrimerEstudioAludido.equals("FUNDAMENTOS MATEM�TICOS DE LA INFORM�TICA"));
		assertTrue(nombreSegundoEstudioAludido.equals("GRADO EN INGENIER�A INFORM�TICA"));
		
		textoObjetivoDeAnalisis = ProcesamientoDeTexto.normalizarTexto("En el primer cuatrimestre del grado en psicologia nos encontramos asignaturas en con diversos niveles de dificultad, como introduccion al analisis de datos, PSICOLOG�A DE LA MOTIVACI�N, FUNDAMENTOS DE PSICOBIOLOG�A, PSICOLOG�A DE LA ATENCI�N, PSICOLOG�A DE LA EMOCI�N.");
		procesadorLenguajeNatural.procesarTextoObjetivoDeAnalisis(textoObjetivoDeAnalisis);
		estudiosAludidos = procesadorLenguajeNatural.obtenerFrase(0).getEstudiosAludidos();
		nombrePrimerEstudioAludido = estudiosAludidos.get(0).getNombre();
		nombreSegundoEstudioAludido = estudiosAludidos.get(1).getNombre();
		nombreTercerEstudioAludido = estudiosAludidos.get(2).getNombre();
		nombreCuartoEstudioAludido = estudiosAludidos.get(3).getNombre();
		nombreQuintoEstudioAludido = estudiosAludidos.get(4).getNombre();
		nombreSextoEstudioAludido = estudiosAludidos.get(5).getNombre();
		assertTrue(nombrePrimerEstudioAludido.equals("GRADO EN PSICOLOG�A"));
		assertTrue(nombreSegundoEstudioAludido.equals("INTRODUCCI�N AL AN�LISIS DE DATOS"));
		assertTrue(nombreTercerEstudioAludido.equals("PSICOLOG�A DE LA MOTIVACI�N"));
		assertTrue(nombreCuartoEstudioAludido.equals("FUNDAMENTOS DE PSICOBIOLOG�A"));
		assertTrue(nombreQuintoEstudioAludido.equals("PSICOLOG�A DE LA ATENCI�N"));
		assertTrue(nombreSextoEstudioAludido.equals("PSICOLOG�A DE LA EMOCI�N"));
	}
	
	
	
}