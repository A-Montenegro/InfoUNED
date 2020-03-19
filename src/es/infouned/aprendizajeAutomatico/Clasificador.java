package es.infouned.aprendizajeAutomatico;

/**
 * 
 * Interfaz que establece los métodos que ha de tener la la subclase que la implemente, representa una clasificador de instancias genérico, ha de ser implementada por un clasificador concretro (Naive Bayes, J48, árboles de decisión, etc)
 * @author Alberto Martínez Montenegro
 */
public interface Clasificador {
	String clasificarInstancia(String instancia);
	// TO-DO Valorar si procede o no la existencia de esta interfaz.
}
