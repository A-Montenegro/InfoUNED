package es.infouned.conversacion;

import java.util.ArrayList;
import es.infouned.aprendizajeAutomatico.Clasificador;
import es.infouned.principal.Configuracion;
import es.infouned.procesamientoLenguajeNatural.Frase;
import es.infouned.procesamientoLenguajeNatural.ProcesadorLenguajeNatural;
import es.infouned.utilidades.ProcesamientoDeTexto;

/**
 * La clase Mensaje simboliza un mensaje entrante que un determinado usuario envía al chatbot.
 * Se encarga de aplicar las operaciones necesarias para transformar el texto entrante procedente del usuario en una colección de clases Frase.
 * @author Alberto Martínez Montenegro
 * 
 */
public class Mensaje {
	private String texto;
	private ArrayList<Frase> frases;

	public Mensaje(String textoRecibido) {
		this.texto = textoRecibido;
		procesarLenguajeNatural();
		clasificarFrases();
	}
	
	private void procesarLenguajeNatural() {
		ProcesadorLenguajeNatural procesadorLenguajeNatural = Configuracion.getProcesadorLenguajeNatural();
		String textoNormalizado = ProcesamientoDeTexto.normalizarTexto(texto);
		procesadorLenguajeNatural.procesarTextoObjetivoDeAnalisis(textoNormalizado);
		frases = procesadorLenguajeNatural.obtenerFrases();
	}
	
	private void clasificarFrases() {
		Clasificador clasificador = Configuracion.getClasificador();
		for(Frase frase: frases) {
			String textoFraseSinEstudiosAludidos = frase.obtenerTextoFraseSinEstudiosAludidos();
			String clasificacionFrase = clasificador.clasificarInstancia(textoFraseSinEstudiosAludidos);
			double distribucionCalisficacionFrase = clasificador.obtenerDistribucionClasificacionInstancia(textoFraseSinEstudiosAludidos);
			frase.setClasificacion(clasificacionFrase);
			frase.setDistribucionClasificacion(distribucionCalisficacionFrase);
		}
	}
	
	public String getTexto() {
		return texto;
	}
	
	public ArrayList<Frase> getFrases() {
		return frases;
	}
}



