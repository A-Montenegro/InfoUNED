package es.infouned.conversacion;

import java.util.ArrayList;

/**
 * 
 * @author Alberto Martínez Montenegro
 *
 */
public class TextoCodificadoPlataformas {
	
	public static String obtenerSaltoDeLinea(String origenConversacion) {
		String saltoDeLinea = new String("");
		switch(origenConversacion) {
			case "Facebook":
				saltoDeLinea  = "\\u000A";
			break;
			case "Telegram":
				saltoDeLinea = "\n";
			break;
			case "Web": 
				saltoDeLinea = "<br>";
			break;
		}
		return saltoDeLinea;
	}
	
	public static String obtenerMensajeOpcionesCallBack(String origenConversacion, ArrayList<String> opciones) {
		String mensajeOpcionesCallBack = new String("");
		switch(origenConversacion) {
			case "Facebook":
				mensajeOpcionesCallBack  = "\\u000A";
			break;
			case "Telegram":
				mensajeOpcionesCallBack = "\n";
			break;
			case "Web": 
				mensajeOpcionesCallBack = "<br>";
			break;
		}
		return mensajeOpcionesCallBack;
	}
}
