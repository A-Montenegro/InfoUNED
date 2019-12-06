package xyz.infouned.enlaces;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import xyz.infouned.conversacion.Conversacion;
import xyz.infouned.conversacion.HistoricoConversaciones;

public class HiloEjecucionClienteEnlaceWeb extends Thread {
    protected Socket socket;

    public HiloEjecucionClienteEnlaceWeb(Socket clientSocket) {
        this.socket = clientSocket;
    }

    public void run() {
    	try {
    		
	        BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
	        String chat_id = entrada.readLine(); //En la primera l�nea del mensaje env�ado figura la direcci�n IP p�blica del cliente, que se usar� como chat_id.
			Conversacion conversacion= HistoricoConversaciones.obtenerConversacion(chat_id, "Web");
	        String textoMensaje= new String("");
	        String lineaTexto;
	        while(entrada.ready()){
	        	lineaTexto = entrada.readLine();
	        	textoMensaje += lineaTexto + "\n";
	        }
	        conversacion.procesarMensaje(textoMensaje);
	        String respuestaBot = conversacion.obtenerRespuestaActual();
	        DataOutputStream salida = new DataOutputStream((socket.getOutputStream()));
	        salida.writeUTF(respuestaBot);
	        socket.close();
	        
    	} catch (IOException e) {
    		
    		e.printStackTrace();
    		
    	}
        
    }
}
