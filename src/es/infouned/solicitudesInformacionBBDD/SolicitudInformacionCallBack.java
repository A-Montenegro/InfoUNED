package es.infouned.solicitudesInformacionBBDD;

import java.util.ArrayList;
import java.util.HashMap;
import es.infouned.conversacion.CallBack.TipoCallBack;
import es.infouned.conversacion.Conversacion.OrigenConversacion;
import es.infouned.estudios.Asignatura;
import es.infouned.solicitudesInformacionBBDD.FactoriaDeSolicitudInformacion.NombreParametro;

/**
 * Clase que se encarga de generar las cadenas de texto finales que componen las respuestas del chatbot cuando la consulta del usuario queda pendiente de CallBack.
 * @author Alberto Mart�nez Montenegro
 * 
 */
public class SolicitudInformacionCallBack extends SolicitudInformacion{
	private ArrayList<String> opciones;
	private OrigenConversacion origenConversacion;
	private TipoCallBack tipoCallBack;
	private HashMap<NombreParametro,Object> parametrosCallBack;
	private final int prioridad = 5;

	public SolicitudInformacionCallBack(TipoCallBack tipoCallBack, HashMap<NombreParametro,Object> parametrosCallBack, OrigenConversacion origenConversacion, ArrayList<String> opciones){
		super();
		this.opciones = opciones;
		this.origenConversacion = origenConversacion;
		this.tipoCallBack = tipoCallBack;
		this.parametrosCallBack = parametrosCallBack;
	}

	
	public String generarCadenaRespuesta(String saltoDeLinea){
		String cadenaRespuesta = new String("");
		switch(tipoCallBack) {
		case TITULACIONDESCONOCIDAPARAASIGNATURA:
			Asignatura asiganturaAludida = (Asignatura) parametrosCallBack.get(NombreParametro.ASIGNATURA);
			cadenaRespuesta += "La asignatura " + asiganturaAludida.getNombre() + " puede pertenecer a distintas titulaciones, seleccione a continuaci�n la correcta:" + saltoDeLinea;
			for(String opcion: opciones) {
				cadenaRespuesta += generarStringOpcionClickable(opcion);
			}
			break;
		case TITULACIONDESCONOCIDAPARAASIGNATURABORROSA:
			cadenaRespuesta += "La asignatura nombrada puede referirse a distintas titulaciones, seleccione a continuaci�n la correcta:" + saltoDeLinea;
			for(String opcion: opciones) {
				cadenaRespuesta += generarStringOpcionClickable(opcion);
			}
			break;
		case SOLICITUDINFORMACIONCONTACTO:
			cadenaRespuesta += "Selecciona qu� tipo de informaci�n de contacto deseas conocer:" + saltoDeLinea;
			for(String opcion: opciones) {
				cadenaRespuesta += generarStringOpcionClickable(opcion);
			}
			break;
		case SOLICITUDINFORMACIONCUID:
			cadenaRespuesta += "Selecciona qu� tipo de informaci�n sobre el Centro Universitario de Idiomas (CUID) deseas conocer:" + saltoDeLinea;
			for(String opcion: opciones) {
				cadenaRespuesta += generarStringOpcionClickable(opcion);
			}
			break;
		default:
			throw new IllegalArgumentException("El tipo de CallBack no encaja con ninguna de las opciones codificadas en el m�todo generarCadenaRespuesta.");
		}
		return cadenaRespuesta;
	}
	
	public String generarStringOpcionClickable(String opcion){
		String stringOpcionClickable = new String();
		switch(origenConversacion) {
		case WEB:
			stringOpcionClickable += "<a onclick=\"enviarMensaje('" + opcion + "')\" style='cursor:pointer;'>" + opcion + "</a><br>";
			break;
		case FACEBOOK:
			stringOpcionClickable += "__BOTON_CALLBACK__" + opcion;
			break;
		case TELEGRAM:
			stringOpcionClickable += "__BOTON_CALLBACK__" + opcion;
			break;
		}
		return stringOpcionClickable;
	}
	
	public int getPrioridad() {
		return prioridad;
	}

}