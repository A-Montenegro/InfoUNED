package es.infouned.enlaces;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import es.infouned.conversacion.Conversacion;
import es.infouned.conversacion.HistoricoConversaciones;

public class HiloEjecucionClienteEnlaceWeb extends Thread {
    protected Socket socket;

    public HiloEjecucionClienteEnlaceWeb(Socket clientSocket) {
        this.socket = clientSocket;
    }

    public void run() {
    	try {
    		
	        BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
	        String direccionIP = entrada.readLine(); //En la primera l�nea del mensaje env�ado figura la direcci�n IP p�blica del cliente.
	        String chat_id = entrada.readLine(); //En la segunda l�nea del mensaje env�ado figura el token javascript, que se usar� como chat_id.
			Conversacion conversacion= HistoricoConversaciones.obtenerConversacion(chat_id, "Web");
	        String textoRecibido= new String("");
	        String lineaTexto;
	        while(entrada.ready()){
	        	lineaTexto = entrada.readLine();
	        	textoRecibido += lineaTexto + "\n";
	        }
	        conversacion.procesarTextoRecibido(textoRecibido);
	        String respuestaBot = conversacion.obtenerRespuestaActual();
	        DataOutputStream salida = new DataOutputStream((socket.getOutputStream()));
	        salida.writeUTF(respuestaBot);
	        socket.close();
	        
    	} catch (IOException e) {
    		
    		e.printStackTrace();
    		
    	}
        
    }
}
