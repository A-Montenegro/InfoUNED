package es.infouned.principal;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import es.infouned.enlaces.EnlaceFacebook;
import es.infouned.enlaces.EnlaceTelegram;
import es.infouned.enlaces.EnlaceWeb;

/**
 * 
 * @author Alberto Martínez Montenegro
 * Clase principal que se encarga de iniciar todos los servicios de la aplicación.
 *
 */
public class Main {
	
	private static final String rutaFicheroConfiguracion = "/config.properties";
	
    public static void main(String[] args) {
    	Configuracion.establecerPropiedadesConfiguracionAPartirDeFichero(rutaFicheroConfiguracion);
    	redirigirSalidaErroresAFicheroLog();
    	Configuracion.iniciarProcesadorLenguajeNatural();
    	iniciarBotFacebook();
    	iniciarBotTelegram();
    	iniciarEnlaceWeb();
    }
	
    private static void redirigirSalidaErroresAFicheroLog(){
		try {
			String rutaFicheroErrorLog = Configuracion.getPropiedad("rutaFicheroErrorLog");
			PrintStream ficheroErrorLog = new PrintStream(rutaFicheroErrorLog);
			System.setErr(ficheroErrorLog);
		} catch (FileNotFoundException e) {
			System.out.println("No se ha podido acceder al archivo 'ErrorLog.log' los errores serán mostrados por salida estándar.");
		}
    } 
    
	private static void iniciarBotFacebook() {
	    try {
	    	new EnlaceFacebook(rutaFicheroConfiguracion);
		} catch (IOException excepcionFacebookIO) {
			excepcionFacebookIO.printStackTrace();
		}
	}
	
	private static void iniciarBotTelegram() {
        try {
            ApiContextInitializer.init();
        	TelegramBotsApi botsApi = new TelegramBotsApi();
            botsApi.registerBot(new EnlaceTelegram());
        } catch (TelegramApiException excepcionTelegramAPI) {
        	excepcionTelegramAPI.printStackTrace();
        }
	}
	
	private static void iniciarEnlaceWeb() {
		int puertoSocketWeb = Integer.parseInt(Configuracion.getPropiedad("puertoSocketWeb"));
	    EnlaceWeb enlaceWeb = new EnlaceWeb(puertoSocketWeb);
	    enlaceWeb.iniciarSocketServidor();
	}

}