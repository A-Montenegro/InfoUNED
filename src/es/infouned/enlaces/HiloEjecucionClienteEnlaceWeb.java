package es.infouned.enlaces;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import es.infouned.conversacion.Conversacion;
import es.infouned.conversacion.Conversacion.OrigenConversacion;
import es.infouned.conversacion.HistoricoConversaciones;
import es.infouned.utilidades.ProcesamientoDeTexto;

/**
 * Esta clase simboliza un hilo de ejecución indvidual que se encarga de procesar un mensaje recibido a través de la web y generar una respuesta.
 * @author Alberto Martínez Montenegro
 * 
 */
public class HiloEjecucionClienteEnlaceWeb extends Thread {
    protected Socket socket;

    public HiloEjecucionClienteEnlaceWeb(Socket clientSocket) {
        this.socket = clientSocket;
    }

    public void run() {
    	try {
	        BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
	        String direccionIP = entrada.readLine(); //En la primera línea del mensaje envíado figura la dirección IP pública del cliente.
	        String chat_id = entrada.readLine(); //En la segunda línea del mensaje envíado figura el token javascript, que se usará como chat_id.
	        System.out.println("IP: " + direccionIP);
	        System.out.println("ChatID: " + chat_id);
			Conversacion conversacion= HistoricoConversaciones.obtenerConversacion(chat_id, OrigenConversacion.WEB);
	        String textoRecibido= new String("");
	        String lineaTexto;
	        while(entrada.ready()){
	        	lineaTexto = entrada.readLine();
	        	textoRecibido += lineaTexto + "\n";
	        }
	        int segundos = 1;
	        Thread.sleep(segundos * 1000);
	        textoRecibido = textoRecibido.substring(0, textoRecibido.length()-1);
	        System.out.println("TextoRecibido: " + textoRecibido);
	        conversacion.procesarTextoRecibido(textoRecibido);
	        String respuestaBot = conversacion.obtenerRespuestaActual();
	        respuestaBot  = ProcesamientoDeTexto.anadirHiperVinculosEnlacesHtml(respuestaBot);
	        respuestaBot  = ProcesamientoDeTexto.sustituirSaltosDeLineaPorCaracteresEspeciales(respuestaBot, "<br>");
	        DataOutputStream salida = new DataOutputStream((socket.getOutputStream()));
	        salida.writeUTF(respuestaBot);
	        socket.close();
    	} catch (IOException e) {
    		e.printStackTrace();
    		
    	}
    	catch (InterruptedException e) {
    		e.printStackTrace();
    	}
        
    }
}
