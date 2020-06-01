package es.infouned.enlaces;

import static spark.Spark.get;
import static spark.Spark.post;
import java.io.IOException;
import java.util.HashMap;

import com.clivern.racter.BotPlatform;
import com.clivern.racter.receivers.webhook.MessageReceivedWebhook;

import com.clivern.racter.senders.templates.MessageTemplate;

import es.infouned.conversacion.Conversacion;
import es.infouned.conversacion.Conversacion.OrigenConversacion;
import es.infouned.conversacion.HistoricoConversaciones;
import es.infouned.utilidades.ProcesamientoDeTexto;

/**
 * Esta clse se encarga de establecer comunicación con la plataforma Facebook, usando la clase Conversacion para obtener las respuestas que deben facilitar.
 * @author Alberto Martínez Montenegro
 * 
 * 
 */
public class EnlaceFacebook{
	String rutaFicheroConfiguracion;
	BotPlatform platform;
	
	public EnlaceFacebook(String rutaFicheroConfiguracion) throws IOException{
		this.rutaFicheroConfiguracion = rutaFicheroConfiguracion;
		escucharPeticionesEntrantesGET();
		escucharPeticionesEntrantesPOST();
	}
	
	/**
	 * Método que escucha las peticiones GET que se reciben. Estas peticiones se usan para establecer y validar los Webhooks.
	 */
	private void escucharPeticionesEntrantesGET() { 
        get("/", (request, response) -> { 
        	platform = new BotPlatform(rutaFicheroConfiguracion);
            platform.getVerifyWebhook().setHubMode(( request.queryParams("hub.mode") != null ) ? request.queryParams("hub.mode") : "");
            platform.getVerifyWebhook().setHubVerifyToken(( request.queryParams("hub.verify_token") != null ) ? request.queryParams("hub.verify_token") : "");
            platform.getVerifyWebhook().setHubChallenge(( request.queryParams("hub.challenge") != null ) ? request.queryParams("hub.challenge") : "");
            if( platform.getVerifyWebhook().challenge() ){
                platform.finish();
                response.status(200);
                return ( request.queryParams("hub.challenge") != null ) ? request.queryParams("hub.challenge") : "";
            }
            platform.finish();
            response.status(403);
            return "El TOKEN de verificación no coincide";
        });
	}
	
	/**
	 * Método que escucha las peticiones POST que se reciben. Estas peticiones se usan para la recepción y envío de mensajes.
	 */
	private void escucharPeticionesEntrantesPOST() {
        post("/", (request, response) -> {
            String body = request.body();
            platform = new BotPlatform(rutaFicheroConfiguracion);
            platform.getBaseReceiver().set(body).parse();
            HashMap<String, MessageReceivedWebhook> messages = (HashMap<String, MessageReceivedWebhook>) platform.getBaseReceiver().getMessages();
            for (MessageReceivedWebhook message : messages.values()) {
                String user_id = (message.hasUserId()) ? message.getUserId() : "";
                String message_text = (message.hasMessageText()) ? message.getMessageText() : "";
                if(message.hasQuickReplyPayload()) message_text = message.getQuickReplyPayload();
                MessageTemplate message_tpl = platform.getBaseSender().getMessageTemplate();
                Conversacion conversacion= HistoricoConversaciones.obtenerConversacion(user_id, OrigenConversacion.FACEBOOK);
    	        message_tpl.setRecipientId(message.getUserId());
    	        conversacion.procesarTextoRecibido(message_text);
    	        String respuestaBot= conversacion.obtenerRespuestaActual();
    	        respuestaBot = ProcesamientoDeTexto.sustituirSaltosDeLineaPorCaracteresEspeciales(respuestaBot, "\\u000A");
    	        if(respuestaBot.contains("__BOTON_CALLBACK__")) {
    	        	String partesTextoAEnviar[] = respuestaBot.split("__BOTON_CALLBACK__");
    		        String cabeceraTexto = partesTextoAEnviar[0];
    		        for (int indicePartes = 1; indicePartes < partesTextoAEnviar.length; indicePartes++) {
    		        	message_tpl.setQuickReply("text", String.valueOf(indicePartes), partesTextoAEnviar[indicePartes], "");
    		        	cabeceraTexto += indicePartes + " ) " + partesTextoAEnviar[indicePartes] + "\\u000A" ;
    		        }
    		        message_tpl.setMessageText(cabeceraTexto);
    		        message_tpl.setNotificationType("REGULAR");
        	        platform.getBaseSender().send(message_tpl);
        	        
    	        }
    	        else {
    	        	message_tpl.setMessageText(respuestaBot);
    	        	message_tpl.setNotificationType("REGULAR");
        	        platform.getBaseSender().send(message_tpl);
    	        }
    	        
                return "ok";
            }
            return "bla";
        });
	}
}