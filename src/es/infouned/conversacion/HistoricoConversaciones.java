package es.infouned.conversacion;

import java.util.ArrayList;

import es.infouned.conversacion.Conversacion.OrigenConversacion;

/**
 * El objetivo de esta clase es almacenar las diferentes conversaciones disponibles en tiempo de ejecuci�n
 * @author Alberto Mart�nez Montenegro
 *
 */
public class HistoricoConversaciones {
	private static ArrayList<Conversacion> conversaciones =  new ArrayList<Conversacion>();
	
	/**
	 * Este m�todo devuelve una conversaci�n a partir de su chat_id, si no hay ninguna conversaci�n con el chat_id que se le pase como par�metro devolver� una nueva conversaci�n.
	 * Se trata de un m�todo 'synchronized', por lo no podr� ser invocado de nuevo si ya est� en uso hasta que finalice su ejecuci�n.
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
