package es.infouned.conversacion;

import java.util.ArrayList;

import es.infouned.conversacion.Conversacion.OrigenConversacion;

/**
 * El objetivo de esta clase es almacenar las diferentes conversaciones disponibles en tiempo de ejecución
 * @author Alberto Martínez Montenegro
 *
 */
public class HistoricoConversaciones {
	private static ArrayList<Conversacion> conversaciones =  new ArrayList<Conversacion>();
	
	/**
	 * Este método devuelve una conversación a partir de su chat_id, si no hay ninguna conversación con el chat_id que se le pase como parámetro devolverá una nueva conversación.
	 * Se trata de un método 'synchronized', por lo no podrá ser invocado de nuevo si ya está en uso hasta que finalice su ejecución.
	 * @param chat_id
	 * @param origen
	 * @return
	 */
    public synchronized static Conversacion obtenerConversacion(String chat_id, OrigenConversacion origen) {
    	for(Conversacion conversacion : conversaciones) {
    		String idConversacion = conversacion.getIdConversacion();
    		if(idConversacion.equals(chat_id)) {
    			return conversacion;
    		}
    	}
    	Conversacion conversacion  = new Conversacion(chat_id, origen);
    	conversaciones.add(conversacion);
    	return conversacion;
    }
    
    public static ArrayList<Conversacion> getConversaciones(){
    	return conversaciones;
    }
}
