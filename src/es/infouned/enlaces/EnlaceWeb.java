package es.infouned.enlaces;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Clase que se encarga de crear un enlace a trav�s de sockets con los usuarios
 * que interact�en con el chatbot a trav�s de la p�gina web.
 * @author Alberto Mart�nez Montenegro
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
	        // Un cliente se ha comunicado a trav�s del socket, se ejecutan las acciones
	        // necesarias en un nuevo hilo de ejecuci�n.
	        new HiloEjecucionClienteEnlaceWeb(socket).start();
        }
    }
}
