package es.infouned.conversacion;

import java.util.ArrayList;

import es.infouned.aprendizajeAutomatico.Clasificador;
import es.infouned.principal.Configuracion;
import es.infouned.procesamientoLenguajeNatural.Frase;
import es.infouned.procesamientoLenguajeNatural.ProcesadorLenguajeNatural;

public class Mensaje {
	private String texto;
	private ArrayList<Frase> frases;
	
	public Mensaje(String textoRecibido) {
		this.texto = textoRecibido;
		procesarLenguajeNatural();
		clasificarFrases();
		
	}

	private void clasificarFrases() {
		Clasificador clasificador = Configuracion.getClasificador();
		for(Frase frase: frases) {
			String textoFrase = frase.getTextoFrase();
			String clasificacionFrase = clasificador.clasificarInstancia(textoFrase);
			frase.setClasificacion(clasificacionFrase);
		}
	}
	
	private void procesarLenguajeNatural() {
		ProcesadorLenguajeNatural procesadorLenguajeNatural = Configuracion.getProcesadorLenguajeNatural();
		procesadorLenguajeNatural.procesarTextoObjetivoDeAnalisis(texto);
		frases = procesadorLenguajeNatural.obtenerFrases();
	}
	
	public String getTexto() {
		return texto;
	}
	
	public ArrayList<Frase> getFrases() {
		return frases;
	}
}
