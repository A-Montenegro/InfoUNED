package es.infouned.enlaces;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import es.infouned.conversacion.Conversacion;
import es.infouned.conversacion.HistoricoConversaciones;
import es.infouned.principal.Configuracion;

/**
 * 
 * @author Alberto Martínez Montenegro
 *
 */
 public class EnlaceTelegram extends TelegramLongPollingBot { 
	private String botUserName;
	private String botToken;
	
	public EnlaceTelegram() {
		obtenerDatosBotDesdePropiedadesConfiguracion();
	}
	
	/***
	 *  Método de la API TelegramBots que es invocado cuando se recibe un mensaje de Telegram de cualquier tipo.
	 */
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
        	procesarTextoRecibido(update);
        }
    }
   
    @Override
    public String getBotUsername() {
        return botUserName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }
    
    private void obtenerDatosBotDesdePropiedadesConfiguracion() {
    	botUserName = Configuracion.getPropiedad("telegram_botUserName");
    	botToken = Configuracion.getPropiedad("telegram_botToken");
    }
    
    public void procesarTextoRecibido(Update update) {
    	String chat_id = update.getMessage().getChatId().toString();
        String textoMensaje = update.getMessage().getText();
		Conversacion conversacion= HistoricoConversaciones.obtenerConversacion(chat_id, "Telegram");
		conversacion.procesarTextoRecibido(textoMensaje);
        String respuestaBot= conversacion.obtenerRespuestaActual();
        enviarMensaje(respuestaBot, chat_id);
    }
 
    public void enviarMensaje(String textoAEnviar, String chat_id) {
        SendMessage message = new SendMessage();
        message.setChatId(chat_id);
        message.setText(textoAEnviar);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
 
    
}