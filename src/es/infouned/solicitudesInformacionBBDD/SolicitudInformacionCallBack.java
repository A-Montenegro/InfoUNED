package es.infouned.solicitudesInformacionBBDD;

import java.util.ArrayList;

public class SolicitudInformacionCallBack extends SolicitudInformacion{
	private ArrayList<String> opciones;
	private String origenConversacion;
	
	public SolicitudInformacionCallBack(String origenConversacion, ArrayList<String> opciones){
		super();
		this.opciones = opciones;
		this.origenConversacion = origenConversacion;
	}
	
	public String generarCadenaRespuesta(String saltoDeLinea){
		String cadenaRespuesta = new String("");
		for(String opcion: opciones) {
			cadenaRespuesta += opcion + saltoDeLinea;
		}
		return cadenaRespuesta;
	}

}
