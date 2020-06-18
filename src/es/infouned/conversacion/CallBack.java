package es.infouned.conversacion;

import java.util.ArrayList;
import java.util.HashMap;
import es.infouned.conversacion.Conversacion.OrigenConversacion;
import es.infouned.estudios.Asignatura;
import es.infouned.estudios.AsignaturaBorrosa;
import es.infouned.estudios.Estudio;
import es.infouned.estudios.FactoriaEstudio;
import es.infouned.estudios.Titulacion;
import es.infouned.solicitudesInformacionBBDD.FactoriaDeSolicitudInformacion;
import es.infouned.solicitudesInformacionBBDD.FactoriaDeSolicitudInformacion.NombreParametro;
import es.infouned.solicitudesInformacionBBDD.FactoriaDeSolicitudInformacion.TipoSolicitud;
import es.infouned.solicitudesInformacionBBDD.SolicitudInformacion;

/**
 * La clase CallBack permite dejar una conversación a la espera de una respuesta concreta del usuario.
 * @author Alberto Martínez Montenegro
 * 
 */
public class CallBack {
	private boolean callBackPendiente;
	private TipoCallBack tipoCallBack;
	private OrigenConversacion origenConversacion;
	private TipoSolicitud tipoSolicitudInformacionPendiente;
	private HashMap<NombreParametro, Object> parametros;
	private ArrayList<Estudio> posiblesEstudiosAludidos;
	private ArrayList<String> posiblesOpciones;
	
	public CallBack() {
		this.callBackPendiente=false;
	}
	
	public boolean getCallBackPendiente() {
		return callBackPendiente;
	}

	public void setCallBackPendiente (boolean callBackPendiente) {
		this.callBackPendiente = callBackPendiente;
	}
	
	public void setOrigenConversacion(OrigenConversacion origenConversacion) {
		this.origenConversacion = origenConversacion;
	}
	
	public void setTipoSolicitudInformacionPendiente(TipoSolicitud tipoSolicitudInformacionPendiente) {
		this.tipoSolicitudInformacionPendiente = tipoSolicitudInformacionPendiente;
	}
	
	public void setTipoCallBack(TipoCallBack tipoCallBack) {
		this.tipoCallBack = tipoCallBack;
	}
	
	public void setParametros(HashMap<NombreParametro, Object> parametros) {
		this.parametros = parametros;
	}
	
	public void setPosiblesEstudiosAludidos(ArrayList<Estudio> posiblesEstudiosAludidos) {
		this.posiblesEstudiosAludidos = posiblesEstudiosAludidos;
	}
	
	public void setPosiblesOpciones(ArrayList<String> posiblesOpciones) {
		this.posiblesOpciones = posiblesOpciones;
	}

	public SolicitudInformacion getSolicitudInformacionConRespuesta(Mensaje ultimoMensaje) {
		SolicitudInformacion solicitudInformacion;
		String textoMensaje = ultimoMensaje.getTexto();
		switch(tipoCallBack) {
		case TITULACIONDESCONOCIDAPARAASIGNATURA:
		case TITULACIONDESCONOCIDAPARAASIGNATURABORROSA:
			Estudio estudioSeleccionado = null;
			for(Estudio estudio: posiblesEstudiosAludidos) {
				if(estudio.getNombre().equals(textoMensaje)) estudioSeleccionado = estudio;
			}
			int opcionNumerica = parseStrToInt(textoMensaje);
			if(opcionNumerica!= 0 && opcionNumerica <= posiblesEstudiosAludidos.size()) estudioSeleccionado = posiblesEstudiosAludidos.get(opcionNumerica - 1);
			NombreParametro nombreTipoEstudio;
			if(estudioSeleccionado != null) { //Respuesta válida
				if(estudioSeleccionado instanceof Titulacion) {
					nombreTipoEstudio = NombreParametro.TITULACION;
				}
				else {
					if(estudioSeleccionado instanceof Asignatura) {
						nombreTipoEstudio = NombreParametro.ASIGNATURA;
					}
					else {
						nombreTipoEstudio = NombreParametro.ASIGNATURABORROSA;
					}		
				}		
				parametros.put(nombreTipoEstudio, estudioSeleccionado);
				transformarAsignaturasBorrosasEnAsignaturas();
				solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion(tipoSolicitudInformacionPendiente, parametros);
				callBackPendiente = false;
			}
			else { //Respuesta incorrecta
				if(textoMensaje.equals("Mi consulta no estaba relacionada con eso") || parseStrToInt(textoMensaje) == posiblesEstudiosAludidos.size() + 1) {
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
					parametrosCallback.put(NombreParametro.TIPOCALLBACK, tipoCallBack);
					parametrosCallback.put(NombreParametro.PARAMETROSCALLBACK, parametros);
					parametrosCallback.put(NombreParametro.ORIGENCONVERSACION, origenConversacion);
					parametrosCallback.put(NombreParametro.OPCIONES, opciones);
					solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion(TipoSolicitud.CALLBACK, parametrosCallback);
				}
			}
			break;
		case SOLICITUDINFORMACIONCONTACTO:
		case SOLICITUDINFORMACIONCUID:
			String opcionSeleccionada = null;
			for(String opcion: posiblesOpciones) {
				if(opcion.equals(textoMensaje)) opcionSeleccionada = opcion;
			}
			opcionNumerica = parseStrToInt(textoMensaje);
			if(opcionNumerica!= 0 && opcionNumerica <= posiblesOpciones.size()) opcionSeleccionada = posiblesOpciones.get(opcionNumerica - 1);
			if(opcionSeleccionada != null) { //Respuesta válida	
				if(textoMensaje.equals("Mi consulta no estaba relacionada con eso") || parseStrToInt(textoMensaje) == posiblesOpciones.size() + 1) {
					parametros.clear();
					parametros.put(NombreParametro.IDINFORMACIONGENERICA, "callBackInterrumpido");
					solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion(TipoSolicitud.INFORMACIONGENERICA, parametros);
					callBackPendiente = false;
				}
				else {
					String idInformacionGenerica = convertirOpcionEnIdInformacionGenerica(opcionSeleccionada);
					parametros.put(NombreParametro.IDINFORMACIONGENERICA, idInformacionGenerica);
					solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion(tipoSolicitudInformacionPendiente, parametros);
					callBackPendiente = false;
				}
			}
			else { //Respuesta incorrecta
					HashMap<NombreParametro,Object> parametrosCallback = new HashMap<NombreParametro,Object>();
					ArrayList<String> opciones = new ArrayList<String>();
					for(String opcion: posiblesOpciones) {
						opciones.add(opcion);
					}
					parametrosCallback.put(NombreParametro.TIPOCALLBACK, tipoCallBack);
					parametrosCallback.put(NombreParametro.PARAMETROSCALLBACK, parametros);
					parametrosCallback.put(NombreParametro.ORIGENCONVERSACION, origenConversacion);
					parametrosCallback.put(NombreParametro.OPCIONES, opciones);
					solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion(TipoSolicitud.CALLBACK, parametrosCallback);
			}
			break;
		default:
			
			throw new IllegalArgumentException("La tipo de CallBack no encaja con ninguna de las opciones codificadas en el método getSolicitudInformacionConRespuesta.");
		}
		return solicitudInformacion;
	}
	
	private void transformarAsignaturasBorrosasEnAsignaturas() {
		if (parametros.containsKey(NombreParametro.ASIGNATURABORROSA)){
			Titulacion titulacion = (Titulacion) parametros.get(NombreParametro.TITULACION);
			AsignaturaBorrosa asignaturaBorrosa = (AsignaturaBorrosa) parametros.get(NombreParametro.ASIGNATURABORROSA);
			Asignatura asignatura = FactoriaEstudio.transformarAsignaturaBorrosaEnAsignatura(titulacion, asignaturaBorrosa);
			parametros.remove(NombreParametro.ASIGNATURABORROSA);
			parametros.put(NombreParametro.ASIGNATURA, asignatura);
		}

	}
	
	private String convertirOpcionEnIdInformacionGenerica(String opcionSeleccionada) {
		switch(opcionSeleccionada) {
		case "Contactar con la UNED":
			return "informacionContactoUNED";
		case "Localización":
			return "informacionContactoLocalizacion";
		case "Quejas y sugerencias":
			return "informacionContactoQuejas";	
		case "Cursos de idiomas ofrecidos":
			return "informacionCUIDtodos";
		case "Cursos modalidad semipresencial":
			return "informacionCUIDSemipresencial";
		case "Cursos modalidad en línea":
			return "informacionCUIDEnLinea";
		case "Matrícula, precios y plazos":
			return "informacionCUIDMatricula";
		case "Red de Centros asociados":
			return "informacionCUIDCA";		
		case "Reconocimientos":
			return "informacionCUIDReconocimientos";
		case "Certificados":
			return "informacionCUIDCertificados";
		case "Metodología":
			return "informacionCUIDMetodologia";
		case "Evaluación":
			return "informacionCUIDEvaluación";
		case "FAQ":
			return "informacionCUIDFAQ";
		case "E-Oral":
			return "informacionCUIDEOral";
		case "Contacto":
			return "informacionCUIDContacto";
		default:
			throw new IllegalArgumentException("La opción " + opcionSeleccionada + "no encaja con ninguna de las codificadas.");
		}
	}
	
	private static int parseStrToInt(String str) {
        if (str.matches("\\d+")) {
            return Integer.parseInt(str);
        } else {
            return 0;
        }
    }
	
	
	public enum TipoCallBack{
		SOLICITUDINFORMACIONCONTACTO,
		SOLICITUDINFORMACIONCUID,
		TITULACIONDESCONOCIDAPARAASIGNATURA,
		TITULACIONDESCONOCIDAPARAASIGNATURABORROSA
	}
}
