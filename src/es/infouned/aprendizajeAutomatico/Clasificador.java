package es.infouned.aprendizajeAutomatico;

/**
 * 
 * Interfaz que establece los métodos que ha de tener la subclase que la implemente, representa un clasificador de instancias genérico, ha de ser implementada por un clasificador concreto (Naive Bayes, J48, árboles de decisión, etc.).
 * @author Alberto Martínez Montenegro
 */
public interface Clasificador {
	String clasificarInstancia(String instancia);
	double obtenerDistribucionClasificacionInstancia(String instancia);
	// TO-DO Valorar si procede o no la existencia de esta interfaz.
}
