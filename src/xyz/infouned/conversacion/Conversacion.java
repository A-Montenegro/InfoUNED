package xyz.infouned.conversacion;

import java.util.Stack;

import xyz.infouned.estudios.Asignatura;
import xyz.infouned.estudios.Titulacion;
import xyz.infouned.principal.Main;
import xyz.infouned.procesamientoLenguajeNatural.ProcesadorLenguajeNatural;
import xyz.infouned.solicitudesInformacionBBDD.FactoriaDeSolicitudInformacion;
import xyz.infouned.solicitudesInformacionBBDD.SolicitudInformacion;





/**
 * Esta clase simboliza una conversación individual, a través de uno los canales disponibles.
 * @author Alberto Martínez Montenegro
 *
 */
public class Conversacion {
	private String chatID;
	private String saltoDeLinea;
	private Stack<String> mensajes;
	private String respuestaActual;
	private Interlocutor interlocutor;
	
	public Conversacion(String idConversacion, String origen) {
		this.chatID = idConversacion;
		this.saltoDeLinea = SaltoDeLinea.obtenerSaltoDeLinea(origen);
		this.interlocutor = new Interlocutor();
		interlocutor.setNombre("Berto");
		mensajes = new Stack<String>();
		respuestaActual = new String("");
	}
	
	public void procesarMensaje(String mensajeRecibido) {
		mensajes.push(mensajeRecibido);
		//primerProcesoUltimoMensaje(mensajeRecibido);
		//segundoProcesoUltimoMensaje(mensajeRecibido);
		//tercerProcesoUltimoMensaje(mensajeRecibido);
		if(mensajeRecibido.charAt(0) == '_') {
			ProcesadorLenguajeNatural procesadorLenguajeNatural = Main.getProcesadorLenguajeNatural();
			procesadorLenguajeNatural.procesarTextoObjetivoDeAnalisis(mensajeRecibido);
			respuestaActual= procesadorLenguajeNatural.obtenerAnaliticaVisualDeTexto(saltoDeLinea);
		} else {
			respuestaActual = obtenerMensajeGenerico(mensajeRecibido);
		}
	}
	
	public String obtenerRespuestaActual() {
		return respuestaActual;
	}
	
	public String getIdConversacion() {
		return chatID;
	}
	
	//Provisional
	public String obtenerMensajeGenerico(String mensajeRecibido) {
		String mensajeGenerico = new String();
		switch(mensajeRecibido) {
		case "Hola":
			mensajeGenerico = "Hola, soy InfoUNED, un chatbot que puede darte información sobre los estudios impartidos en la UNED. ¿En qué puedo ayudarte?";
		break;
		case "Quiero saber cuántos alumnos están matriculados de la asignatura Fundamentos matemáticos de la informática en el grado en ingeniería informática.":
			Titulacion titulacion = new Titulacion(7101, "GRADO EN INGENIERÍA INFORMÁTICA", "GRADO");
			Asignatura asignatura = new Asignatura("7101102-", "Fundamentos matemáticos de la informática");
			SolicitudInformacion solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion("matriculadosAsignatura", titulacion, asignatura);
			mensajeGenerico = solicitudInformacion.generarCadenaRespuesta(saltoDeLinea);
		break;
		case "Ok, gracias.":
			mensajeGenerico = "No hay de qué.";
		break;
		case "¿Cuales son las asignaturas más fáciles del grado en derecho?":
			titulacion = new Titulacion(6602, "GRADO EN DERECHO", "GRADO");
			solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion("estadisticaRendimientoTopAsignatura", titulacion, "PORCENTAJE_TASA_EXITO", "mayores", new Stack<String>());
			mensajeGenerico = solicitudInformacion.generarCadenaRespuesta(saltoDeLinea);
		break;
		default:
			solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion("aleatoria");
			mensajeGenerico = solicitudInformacion.generarCadenaRespuesta(saltoDeLinea);
		break;
		}
		return mensajeGenerico;
	}
	
}
