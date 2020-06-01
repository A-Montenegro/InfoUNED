package es.infouned.procesamientoLenguajeNatural;

import java.util.ArrayList;

/**
 * Interfaz que simboliza un procesador del lenguaje natural. Se encarga de especificar los m�todos que deben ser implementados.
 * @author Alberto Mart�nez Montenegro
 * 
 */
public interface ProcesadorLenguajeNatural {
	void procesarTextoObjetivoDeAnalisis(String textoObjetivoDeAnalisis);
	public ArrayList<Frase> obtenerFrases();
	public Frase obtenerFrase(int indiceFrase);
	public String obtenerAnaliticaVisualDeTexto(String saltoDeLinea);
}
