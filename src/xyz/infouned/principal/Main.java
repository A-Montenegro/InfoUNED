package xyz.infouned.principal;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import xyz.infouned.configuracion.PropiedadesConfiguracion;
import xyz.infouned.configuracion.PropiedadesListaDeConsultasSQL;
import xyz.infouned.enlaces.EnlaceFacebook;
import xyz.infouned.enlaces.EnlaceTelegram;
import xyz.infouned.enlaces.EnlaceWeb;
import xyz.infouned.procesamientoLenguajeNatural.ProcesadorLenguajeNatural;
import xyz.infouned.procesamientoLenguajeNatural.ProcesadorLenguajeNaturalStanford;

/**
 * 
 * @author Alberto Martínez Montenegro
 * Clase principal que se encarga de iniciar todos los servicios de la aplicación.
 *
 */
public class Main {
	
	private static final String rutaFicheroConfiguracion = "/config.properties";
	private static final String rutaFicheroConsultasSQL = "/consultasSQL.properties";
	private static final String rutaFicheroConfigunarcionStanfordNLP = "/StanfordCoreNLP-spanish.properties";
	private static final int puertoSocketWeb = 4568;
	private static ProcesadorLenguajeNatural procesadorLenguajeNatural;
	
    public static void main(String[] args) {
    	redirigirSalidaErroresAFicheroLog();
    	PropiedadesConfiguracion.establecerPropiedadesConfiguracionAPartirDeFichero(rutaFicheroConfiguracion);
    	PropiedadesListaDeConsultasSQL.establecerPropiedadesDeListaDeConsultasSQLAPartirDeFichero(rutaFicheroConsultasSQL);
    	procesadorLenguajeNatural = new ProcesadorLenguajeNaturalStanford(rutaFicheroConfigunarcionStanfordNLP);
    	iniciarBotFacebook();
    	iniciarBotTelegram();
    	iniciarEnlaceWeb();
    }
	
    private static void redirigirSalidaErroresAFicheroLog(){
		try {
			PrintStream ficheroErrorLog = new PrintStream("/home/pi/Desktop/Arranque/ErrorLog.log");
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
	    EnlaceWeb enlaceWeb = new EnlaceWeb(puertoSocketWeb);
	    enlaceWeb.iniciarSocketServidor();
	}
	
	public static ProcesadorLenguajeNatural getProcesadorLenguajeNatural() {
		return procesadorLenguajeNatural;
	}
}