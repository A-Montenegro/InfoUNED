package es.infouned.conversacion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

import es.infouned.procesamientoLenguajeNatural.Frase;
import es.infouned.solicitudesInformacionBBDD.FactoriaDeSolicitudInformacion;
import es.infouned.solicitudesInformacionBBDD.FactoriaDeSolicitudInformacion.NombreParametro;
import es.infouned.solicitudesInformacionBBDD.FactoriaDeSolicitudInformacion.TipoSolicitud;
import es.infouned.solicitudesInformacionBBDD.SolicitudInformacion;

/**
 * Esta clase simboliza una conversación individual, a través de uno los canales disponibles.
 * @author Alberto Martínez Montenegro
 *
 */
public class Conversacion {
	private String chatID;
	private OrigenConversacion origenConversacion;
	private String saltoDeLinea;
	private Stack<Mensaje> mensajes;
	private String respuestaActual;
	private CallBack callBack;
	
	public Conversacion(String idConversacion, OrigenConversacion origen) {
		this.chatID = idConversacion;
		this.origenConversacion = origen;
		this.saltoDeLinea = TextoCodificadoPlataformas.obtenerSaltoDeLinea(origen);
		mensajes = new Stack<Mensaje>();
		respuestaActual = new String("");
		this.callBack= new CallBack();
	}
	
	public void procesarTextoRecibido(String textoRecibido) {
		Mensaje mensaje = new Mensaje(textoRecibido);
		mensajes.push(mensaje);
		SolicitudInformacion solicitudInformacion;
		if(callBack.getCallBackPendiente()) {
			solicitudInformacion = recuperarSolicitudInformacionPorCallBack();
		}
		else {
			solicitudInformacion = inferirSolicitudInformacion();
		}
		respuestaActual = solicitudInformacion.generarCadenaRespuesta(saltoDeLinea);
	}
	
	private SolicitudInformacion recuperarSolicitudInformacionPorCallBack() {
		Mensaje ultimoMensaje = mensajes.peek();
		SolicitudInformacion respuestaCallBack = callBack.getSolicitudInformacionConRespuesta(ultimoMensaje);
		return respuestaCallBack;
	}
	
	private SolicitudInformacion inferirSolicitudInformacion() {
		Mensaje ultimoMensaje = mensajes.peek();
		ArrayList<SolicitudInformacion> listasolicitudesInformacion = new ArrayList<SolicitudInformacion>();
		for (Frase frase: ultimoMensaje.getFrases()) {
			DecisionUnitaria decisionUnitaria = new DecisionUnitaria(frase, callBack, origenConversacion);
			SolicitudInformacion solicitudInformacion = decisionUnitaria.decidirSolicitudInformacion();
			listasolicitudesInformacion.add(solicitudInformacion);
		}
		if(!listasolicitudesInformacion.isEmpty()) return listasolicitudesInformacion.get(0);
		HashMap<NombreParametro,Object> parametros = new HashMap<NombreParametro,Object>();
		parametros.put(NombreParametro.IDINFORMACIONGENERICA, "mensajeSinFrases");
		return FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion(TipoSolicitud.INFORMACIONGENERICA, parametros);
	}
	
	public String obtenerRespuestaActual() {
		return respuestaActual;
	}
	
	public String getIdConversacion() {
		return chatID;
	}
	
	public enum OrigenConversacion{
		WEB,
		TELEGRAM,
		FACEBOOK
	}
}
