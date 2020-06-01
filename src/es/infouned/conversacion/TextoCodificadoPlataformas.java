package es.infouned.conversacion;


import es.infouned.conversacion.Conversacion.OrigenConversacion;

/**
 * Esta clase ofrece métodos estáticos que generan cadenas de texto especiales que son diferentes en cada plataforma, como por ejemplo, saltos de línea.
 * @author Alberto Martínez Montenegro
 * 
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
}
