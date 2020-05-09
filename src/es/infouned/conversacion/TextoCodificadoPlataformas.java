package es.infouned.conversacion;

import java.util.ArrayList;

import es.infouned.conversacion.Conversacion.OrigenConversacion;

/**
 * 
 * @author Alberto Martínez Montenegro
 *
 */
public class TextoCodificadoPlataformas {
	
	public static String obtenerSaltoDeLinea(OrigenConversacion origenConversacion) {
		String saltoDeLinea = new String("");
		switch(origenConversacion) {
			case FACEBOOK:
				saltoDeLinea  = "\\u000A";
			break;
			case TELEGRAM:
				saltoDeLinea = "\n";
			break;
			case WEB: 
				saltoDeLinea = " <br>";
			break;
		}
		return saltoDeLinea;
	}
	
	public static String obtenerMensajeOpcionesCallBack(OrigenConversacion origenConversacion, ArrayList<String> opciones) {
		String mensajeOpcionesCallBack = new String("");
		switch(origenConversacion) {
			case FACEBOOK:
				mensajeOpcionesCallBack  = "\\u000A";
			break;
			case TELEGRAM:
				mensajeOpcionesCallBack = "\n";
			break;
			case WEB: 
				mensajeOpcionesCallBack = "<br>";
			break;
		}
		return mensajeOpcionesCallBack;
	}
}
