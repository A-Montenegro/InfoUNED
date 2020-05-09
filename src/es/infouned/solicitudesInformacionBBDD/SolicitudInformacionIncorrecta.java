package es.infouned.solicitudesInformacionBBDD;


import es.infouned.estudios.Asignatura;
import es.infouned.estudios.AsignaturaBorrosa;
import es.infouned.estudios.Titulacion;

public class SolicitudInformacionIncorrecta extends SolicitudInformacion{
	private String tipoIncorreccion;
	private Titulacion titulacion;
	private Asignatura asignatura;
	@SuppressWarnings("unused")
	private AsignaturaBorrosa asignaturaBorrosa;
	
	public SolicitudInformacionIncorrecta(String tipoIncorreccion, Titulacion titulacion, Asignatura asignatura){
		super();
		this.tipoIncorreccion = tipoIncorreccion;
		this.titulacion = titulacion;
		this.asignatura = asignatura;
		this.asignaturaBorrosa = null;
	}

	public SolicitudInformacionIncorrecta(String tipoIncorreccion, Titulacion titulacion, AsignaturaBorrosa asignaturaBorrosa){
		super();
		this.tipoIncorreccion = tipoIncorreccion;
		this.titulacion = titulacion;
		this.asignatura = null;
		this.asignaturaBorrosa = asignaturaBorrosa;
	}
	
	public String generarCadenaRespuesta(String saltoDeLinea){
		String cadenaRespuesta = new String("");
		switch(tipoIncorreccion) {
		case "asignaturaNoPerteneceATitulacion":
			if(asignatura != null) {
				cadenaRespuesta = "La asignatura " + asignatura.getNombre() + " no forma parte de la titulaci�n " + titulacion.getNombre() + "." + saltoDeLinea  +
				"Por favor, intente expresar su consulta de otra forma, prestando especial atenci�n al nombre de los estudios de los cuales desea informaci�n." + saltoDeLinea;
			}
			else {
				cadenaRespuesta = "Seg�n mis datos, la asignatura que ha nombrado en su mensaje, no pertenece a la titulaci�n " + titulacion.getNombre() + "." + saltoDeLinea  +
				"Por favor, intente expresar su consulta de otra forma, prestando especial atenci�n al nombre de los estudios de los cuales desea informaci�n." + saltoDeLinea;
			}
			break;
		case "asignaturaBorrosaNoPerteneceATitulacion":
			cadenaRespuesta = "Seg�n mis datos, la asignatura que ha nombrado en su mensaje, no pertenece a la titulaci�n " + titulacion.getNombre() + "." + saltoDeLinea  +
			"Por favor, intente expresar su consulta de otra forma, prestando especial atenci�n al nombre de los estudios de los cuales desea informaci�n." + saltoDeLinea;
			break;
		}
		
		return cadenaRespuesta;
	}

}