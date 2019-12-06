package xyz.infouned.procesamientoLenguajeNatural;

import java.util.ArrayList;


public interface ProcesadorLenguajeNatural {
	void procesarTextoObjetivoDeAnalisis(String textoObjetivoDeAnalisis);
	public ArrayList<Frase> obtenerFrases();
	public Frase obtenerFrase(int indiceFrase);
	public String obtenerAnaliticaVisualDeTexto(String saltoDeLinea);
}
