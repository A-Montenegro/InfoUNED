package es.infouned.solicitudesInformacionBBDD;

import es.infouned.estudios.Asignatura;
import es.infouned.estudios.Titulacion;

/**
 * Clase que se encarga de generar las cadenas de texto finales que componen las respuestas del chatbot cuando la consulta del usuario est� relacionada con la gu�a de la asignatura.
 * @author Alberto Mart�nez Montenegro
 * 
 */
public class SolicitudGuia extends SolicitudInformacion{
	private Titulacion titulacion;
	private Asignatura asignatura;
	private String fragmentoURLGuiasTitulaciones;
	private String fragmentoURLGuiasAsignaturas;
	private String fragmentoURLTerminacion;
	
	
	public SolicitudGuia(Titulacion titulacion, Asignatura asignatura){
		super();
		this.titulacion = titulacion;
		this.asignatura = asignatura;
		establecerFragmentosURL();
	}
	
	public SolicitudGuia(Titulacion titulacion){
		super();
		this.titulacion = titulacion;
		this.asignatura = null;
		establecerFragmentosURL();
	}
	
	private void establecerFragmentosURL() {
		fragmentoURLTerminacion = "";
		switch(titulacion.getNivelEstudios()) {
		case "GRADO":
			fragmentoURLGuiasTitulaciones = "GestionGuiastitulacionesGrado";
			fragmentoURLGuiasAsignaturas = "GuiasAsignaturasGrados";
			break;
		case "MASTER":
			fragmentoURLGuiasTitulaciones = "GestionGuiasTitulacionesPosgrado";
			fragmentoURLGuiasAsignaturas = "GuiasAsignaturasMaster";
			fragmentoURLTerminacion = "01";
			break;
		default:
			fragmentoURLGuiasTitulaciones = "";
			fragmentoURLGuiasAsignaturas = "";
		}
	}
	
	public String generarCadenaRespuesta(String saltoDeLinea){
		if(fragmentoURLGuiasTitulaciones.equals("") && fragmentoURLGuiasAsignaturas.equals("")) return "No se dispone de informaci�n adicional para esa titulaci�n/asignatura.";
		String cadenaRespuesta= new String();
		if(asignatura == null) {
			cadenaRespuesta = "Si desea informaci�n sobre la titulaci�n " + titulacion.getNombre() + 
							  ", en la siguiente gu�a de estudio encontrar� todos los detalles:" + saltoDeLinea +
							  "http://portal.uned.es/" + fragmentoURLGuiasTitulaciones + "/GenerarPDFGuia?c=2020&idT=" + titulacion.getIdTitulacion() + fragmentoURLTerminacion;
		}
		else {
			cadenaRespuesta = "Si desea informaci�n sobre la asignatura " + asignatura.getNombre() + " de la titulaci�n " +
					  		  titulacion.getNombre() + ", en la siguiente gu�a de estudio encontrar� todos los detalles:" + saltoDeLinea +
					  		  "http://portal.uned.es/" + fragmentoURLGuiasAsignaturas +"/PDFGuiaPublica?idA=" + asignatura.getIdAsignatura() + 
					  		  "&c=2020&idT=" + titulacion.getIdTitulacion();
		}
		return cadenaRespuesta;
	}
}