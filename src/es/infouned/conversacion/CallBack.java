package es.infouned.conversacion;

import java.util.ArrayList;
import java.util.HashMap;

import es.infouned.estudios.Estudio;
import es.infouned.estudios.Titulacion;
import es.infouned.solicitudesInformacionBBDD.FactoriaDeSolicitudInformacion;
import es.infouned.solicitudesInformacionBBDD.FactoriaDeSolicitudInformacion.NombreParametro;
import es.infouned.solicitudesInformacionBBDD.FactoriaDeSolicitudInformacion.TipoSolicitud;
import es.infouned.solicitudesInformacionBBDD.SolicitudInformacion;

public class CallBack {
	private boolean callBackPendiente;
	private String origenConversacion;
	private TipoSolicitud tipoSolicitudInformacionPendiente;
	private HashMap<NombreParametro, Object> parametros;
	private ArrayList<Estudio> posiblesEstudiosAludidos;
	
	
	public CallBack() {
		this.callBackPendiente=false;
	}
	
	public boolean getCallBackPendiente() {
		return callBackPendiente;
	}

	public void setCallBackPendiente (boolean callBackPendiente) {
		this.callBackPendiente = callBackPendiente;
	}
	
	public void setOrigenConversacion(String origenConversacion) {
		this.origenConversacion = origenConversacion;
	}
	
	public void setTipoSolicitudInformacionPendiente(TipoSolicitud tipoSolicitudInformacionPendiente) {
		this.tipoSolicitudInformacionPendiente = tipoSolicitudInformacionPendiente;
	}
	
	public void setParametros(HashMap<NombreParametro, Object> parametros) {
		this.parametros = parametros;
	}
	
	public void setPosiblesEstudiosAludidos(ArrayList<Estudio> posiblesEstudiosAludidos) {
		this.posiblesEstudiosAludidos = posiblesEstudiosAludidos;
	}

	public SolicitudInformacion getSolicitudInformacionConRespuesta(Mensaje ultimoMensaje) {
		SolicitudInformacion solicitudInformacion;
		String textoMensaje = ultimoMensaje.getTexto();
		Estudio estudioSeleccionado = null;
		for(Estudio estudio: posiblesEstudiosAludidos) {
			if(estudio.getNombre().equals(textoMensaje)) {
				estudioSeleccionado = estudio;
			}
		}
		NombreParametro nombreTipoEstudio;
		if(estudioSeleccionado != null) { //Respuesta válida
			if(estudioSeleccionado instanceof Titulacion) {
				nombreTipoEstudio = NombreParametro.TITULACION;
			}
			else {
				nombreTipoEstudio = NombreParametro.ASIGNATURA;
			}
			parametros.put(nombreTipoEstudio, estudioSeleccionado);
			solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion(tipoSolicitudInformacionPendiente, parametros);
			callBackPendiente = false;
		}
		else { //Respuesta incorrecta
			if(textoMensaje.equals("Mi consulta no estaba relacionada con eso")) {
				parametros.clear();
				parametros.put(NombreParametro.IDINFORMACIONGENERICA, "callBackInterrumpido");
				solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion(TipoSolicitud.INFORMACIONGENERICA, parametros);
				callBackPendiente = false;
			}
			else {
				HashMap<NombreParametro,Object> parametrosCallback = new HashMap<NombreParametro,Object>();
				ArrayList<String> opciones = new ArrayList<String>();
				for(Estudio estudio: posiblesEstudiosAludidos) {
					opciones.add(estudio.getNombre());
				}
				opciones.add("Mi consulta no estaba relacionada con eso");
				parametrosCallback.put(NombreParametro.ORIGENCONVERSACION, origenConversacion);
				parametrosCallback.put(NombreParametro.OPCIONES, opciones);
				solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion(TipoSolicitud.CALLBACK, parametrosCallback);
			}
		}
		return solicitudInformacion;
	}
	
	
	
	
	
}
