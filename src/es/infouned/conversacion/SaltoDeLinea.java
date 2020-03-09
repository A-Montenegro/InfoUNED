package es.infouned.conversacion;

/**
 * 
 * @author Alberto Martínez Montenegro
 *
 */
public class SaltoDeLinea {
	
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
}
