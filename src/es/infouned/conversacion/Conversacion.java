package es.infouned.conversacion;

import java.util.ArrayList;
import java.util.Stack;

import es.infouned.aprendizajeAutomatico.Clasificador;
import es.infouned.estudios.Asignatura;
import es.infouned.estudios.Titulacion;
import es.infouned.principal.Configuracion;
import es.infouned.principal.Main;
import es.infouned.procesamientoLenguajeNatural.Frase;
import es.infouned.procesamientoLenguajeNatural.ProcesadorLenguajeNatural;
import es.infouned.solicitudesInformacionBBDD.FactoriaDeSolicitudInformacion;
import es.infouned.solicitudesInformacionBBDD.SolicitudInformacion;





/**
 * Esta clase simboliza una conversación individual, a través de uno los canales disponibles.
 * @author Alberto Martínez Montenegro
 *
 */
public class Conversacion {
	private String chatID;
	private String saltoDeLinea;
	private Stack<Mensaje> mensajes;
	private String respuestaActual;
	private Interlocutor interlocutor;
	
	public Conversacion(String idConversacion, String origen) {
		this.chatID = idConversacion;
		this.saltoDeLinea = SaltoDeLinea.obtenerSaltoDeLinea(origen);
		this.interlocutor = new Interlocutor();
		interlocutor.setNombre("Berto");
		mensajes = new Stack<Mensaje>();
		respuestaActual = new String("");
	}
	
	public void procesarTextoRecibido(String textoRecibido) {
		Mensaje mensaje = new Mensaje(textoRecibido);
		mensajes.push(mensaje);
		SolicitudInformacion solicitudInformacion = inferirSolicitudInformacion();
		respuestaActual = solicitudInformacion.generarCadenaRespuesta(saltoDeLinea);
	}
	
	private SolicitudInformacion inferirSolicitudInformacion() {
		Mensaje ultimoMensaje = mensajes.firstElement();
		int numeroFrasesMensaje = ultimoMensaje.getFrases().size();
		for (Frase frase: ultimoMensaje.getFrases()) {
			
		}

	}

	
	public String obtenerRespuestaActual() {
		return respuestaActual;
	}
	
	public String getIdConversacion() {
		return chatID;
	}
	
}
