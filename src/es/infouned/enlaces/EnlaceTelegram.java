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
        if ((update.hasMessage() && update.getMessage().hasText()) || update.hasCallbackQuery()){
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
    	System.out.println("Chat id: " + chat_id);
    	System.out.println("Mensaje recibido: " + textoMensaje);
		Conversacion conversacion= HistoricoConversaciones.obtenerConversacion(chat_id, OrigenConversacion.TELEGRAM);
		conversacion.procesarTextoRecibido(textoMensaje);
        String respuestaBot= conversacion.obtenerRespuestaActual();
        enviarMensaje(respuestaBot, chat_id);
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