package es.infouned.enlaces;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Clase que se encarga de crear un enlace a través de sockets con los usuarios
 * que interactúen con el chatbot a través de la página web.
 * @author Alberto Martínez Montenegro
 *
 */
public class EnlaceWeb {
	ServerSocket servidorSocket;
	Socket socket;
	
    public EnlaceWeb(int puerto) {
        try {
        	servidorSocket = new ServerSocket(puerto);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void iniciarSocketServidor() {
        while(true) {	
	        try {
	        	// Escuchando en el puerto correspondiente a la espera de que un 
	        	// cliente intente comunicarse con el socket.
	            socket = servidorSocket.accept();   
	        } catch (IOException excepcionIO) {
	        	excepcionIO.printStackTrace();
	            return;
	        } 
	        // Un cliente se ha comunicado a través del socket, se ejecutan las acciones
	        // necesarias en un nuevo hilo de ejecución.
	        new HiloEjecucionClienteEnlaceWeb(socket).start();
        }
    }
}
