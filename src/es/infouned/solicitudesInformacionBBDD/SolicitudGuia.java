package es.infouned.solicitudesInformacionBBDD;

import es.infouned.estudios.Asignatura;
import es.infouned.estudios.Titulacion;

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
		if(fragmentoURLGuiasTitulaciones.equals("") && fragmentoURLGuiasAsignaturas.equals("")) return "No se dispone de información adicional para esa titulación/asignatura.";
		String cadenaRespuesta= new String();
		if(asignatura == null) {
			cadenaRespuesta = "Si desea información sobre la titulación " + titulacion.getNombre() + 
							  ", en la siguiente guía de estudio encontrará todos los detalles:" + saltoDeLinea +
							  "http://portal.uned.es/" + fragmentoURLGuiasTitulaciones + "/GenerarPDFGuia?c=2020&idT=" + titulacion.getIdTitulacion() + fragmentoURLTerminacion;
		}
		else {
			cadenaRespuesta = "Si desea información sobre la asignatura " + asignatura.getNombre() + " de la titulación " +
					  		  titulacion.getNombre() + ", en la siguiente guía de estudio encontrará todos los detalles:" + saltoDeLinea +
					  		  "http://portal.uned.es/" + fragmentoURLGuiasAsignaturas +"/PDFGuiaPublica?idA=" + asignatura.getIdAsignatura() + 
					  		  "&c=2020&idT=" + titulacion.getIdTitulacion();
		}
		return cadenaRespuesta;
	}
}