package es.infouned.enlaces;

import static spark.Spark.get;
import static spark.Spark.post;
import java.io.IOException;
import java.util.HashMap;
import com.clivern.racter.BotPlatform;
import com.clivern.racter.receivers.webhook.MessageReceivedWebhook;
import com.clivern.racter.senders.templates.MessageTemplate;

import es.infouned.conversacion.Conversacion;
import es.infouned.conversacion.HistoricoConversaciones;

/**
 * 
 * @author Alberto Martínez Montenegro
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
                MessageTemplate message_tpl = platform.getBaseSender().getMessageTemplate();
                Conversacion conversacion= HistoricoConversaciones.obtenerConversacion(user_id, "Facebook");
    	        message_tpl.setRecipientId(message.getUserId());
    	        conversacion.procesarMensaje(message_text);
    	        String respuestaBot= conversacion.obtenerRespuestaActual();
    	        message_tpl.setMessageText(respuestaBot);
    	        message_tpl.setNotificationType("REGULAR");
    	        platform.getBaseSender().send(message_tpl);
                return "ok";
            }
            return "bla";
        });
	}
}