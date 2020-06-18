package es.infouned.enlaces;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import es.infouned.conversacion.Conversacion;
import es.infouned.conversacion.Conversacion.OrigenConversacion;
import es.infouned.conversacion.HistoricoConversaciones;
import es.infouned.principal.Configuracion;

/**
 * Esta clse se encarga de establecer comunicación con la plataforma Telegram, usando la clase Conversacion para obtener las respuestas que deben facilitar.
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
        if ((update.hasMessage() && update.getMessage().hasText()) || update.hasCallbackQuery()){
        	procesarTextoRecibido(update);
        }
    }
   
	/***
	 *  Método que devuelve el nombre de usuario del bot, que ha sido leído del fichero de propiedades.
	 */
    @Override
    public String getBotUsername() {
        return botUserName;
    }

    /***
	 *  Método que devuelve el token de autentificación del bot, que ha sido leído del fichero de propiedades.
	 */
    @Override
    public String getBotToken() {
        return botToken;
    }
    
    private void obtenerDatosBotDesdePropiedadesConfiguracion() {
    	botUserName = Configuracion.getPropiedad("telegram_botUserName");
    	botToken = Configuracion.getPropiedad("telegram_botToken");
    }
    
    public void procesarTextoRecibido(Update update) {
    	String chat_id = new String();
    	String textoMensaje = new String();
    	if(update.hasCallbackQuery()) {
    		chat_id = update.getCallbackQuery().getMessage().getChat().getId().toString();
    		textoMensaje = update.getCallbackQuery().getData().toString();
    	}
    	else {
    		chat_id = update.getMessage().getChatId().toString();
    		textoMensaje = update.getMessage().getText();
    	}
		Conversacion conversacion= HistoricoConversaciones.obtenerConversacion(chat_id, OrigenConversacion.TELEGRAM);
		if(textoMensaje.length() < 400) {
			conversacion.procesarTextoRecibido(textoMensaje);
	        String respuestaBot= conversacion.obtenerRespuestaActual();
	        enviarMensaje(respuestaBot, chat_id);
		}
	    else {
	        enviarMensaje("El mensaje que me has escrito es demasiado largo para que yo pueda entenderlo. Recuerda que funciono mejor si me haces preguntas concretas y evitas los párrafos largos.", chat_id);
	    }
    }
 
    private void enviarMensaje(String textoAEnviar, String chat_id) {
        SendMessage message = new SendMessage();
        message.setChatId(chat_id);
        if(textoAEnviar.contains("__BOTON_CALLBACK__")) {
        	String partesTextoAEnviar[] = textoAEnviar.split("__BOTON_CALLBACK__");
        	ReplyKeyboardMarkup markupInline = new ReplyKeyboardMarkup();
        	markupInline.setOneTimeKeyboard(true);
	        List<KeyboardRow> rowsInline = new ArrayList<>();
	        for(int indiceOpciones = 1; indiceOpciones < partesTextoAEnviar.length; indiceOpciones++) {
	        	KeyboardRow keyboardRow = new KeyboardRow();
	        	keyboardRow.add(new KeyboardButton().setText(partesTextoAEnviar[indiceOpciones]));
		        rowsInline.add(keyboardRow);
	        }
	        markupInline.setKeyboard(rowsInline);
	        markupInline.setResizeKeyboard(true);
	        message.setReplyMarkup(markupInline);
	        message.setText(partesTextoAEnviar[0]);
        }
        else {
        	message.setText(textoAEnviar);
        }
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
 
    
}