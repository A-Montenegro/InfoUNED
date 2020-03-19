package es.infouned.aprendizajeAutomatico;

/**
 * 
 * Interfaz que establece los m�todos que ha de tener la la subclase que la implemente, representa una clasificador de instancias gen�rico, ha de ser implementada por un clasificador concretro (Naive Bayes, J48, �rboles de decisi�n, etc)
 * @author Alberto Mart�nez Montenegro
 */
public interface Clasificador {
	String clasificarInstancia(String instancia);
	// TO-DO Valorar si procede o no la existencia de esta interfaz.
}
