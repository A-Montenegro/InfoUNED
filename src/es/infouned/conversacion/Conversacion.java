package es.infouned.conversacion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

import es.infouned.estudios.Asignatura;
import es.infouned.estudios.CriterioConsultaSQL;
import es.infouned.estudios.Estudio;
import es.infouned.estudios.FactoriaEstudio;
import es.infouned.estudios.IndicadorOrdenamiento;
import es.infouned.estudios.NivelEstudios;
import es.infouned.estudios.ParametroEstadistico;
import es.infouned.estudios.Titulacion;
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
	private String origenConversacion;
	private String saltoDeLinea;
	private Stack<Mensaje> mensajes;
	private String respuestaActual;
	private Interlocutor interlocutor;
	private CallBack callBack;
	
	public Conversacion(String idConversacion, String origen) {
		this.chatID = idConversacion;
		this.origenConversacion = origen;
		this.saltoDeLinea = TextoCodificadoPlataformas.obtenerSaltoDeLinea(origen);
		this.interlocutor = new Interlocutor();
		interlocutor.setNombre("Berto");
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
		int numeroFrasesMensaje = ultimoMensaje.getFrases().size();
		ArrayList<SolicitudInformacion> listasolicitudesInformacion = new ArrayList<SolicitudInformacion>();
		for (Frase frase: ultimoMensaje.getFrases()) {
			SolicitudInformacion solicitudInformacion = inferirSolicitudInformacionFraseUnitaria(frase);
			listasolicitudesInformacion.add(solicitudInformacion);
		}
		return listasolicitudesInformacion.get(0);
	}

	private SolicitudInformacion inferirSolicitudInformacionFraseUnitaria(Frase frase) {
		String clasificacion = frase.getClasificacion();
		int numeroEstudiosAludidos = frase.getNumeroEstudiosAludidos();
		ArrayList<Estudio> estudiosAludidos = frase.getEstudiosAludidos();
		ArrayList<ParametroEstadistico> parametrosEstadisiticosTitulacionAludidos = frase.getParametrosEstadisticosTitulacionAludidos();
		ArrayList<ParametroEstadistico> parametrosEstadisiticosAsignaturaAludidos = frase.getParametrosEstadisticosAsignaturaAludidos();
		Stack<NivelEstudios> nivelesEstudiosAludidos = frase.getNivelesEstudiosAludidos();
		Stack<IndicadorOrdenamiento> indicadoresOrdenamientoAludidos = frase.getIndicadoresOrdenamientoAludidos();
		Stack<CriterioConsultaSQL> criteriosConsultaSQLAludidos = frase.getCriteriosConsultaSQLAludidos();
		SolicitudInformacion solicitudInformacion = null;
		Titulacion titulacionAludida = null;
		Asignatura asignaturaAludida = null;
		HashMap<NombreParametro,Object> parametros = new HashMap<NombreParametro,Object>();
		switch (clasificacion) {
		
		
		case "saludo":
		case "despedida":
		case "informacionContacto":
			parametros.put(NombreParametro.IDINFORMACIONGENERICA, clasificacion);
			solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion(TipoSolicitud.INFORMACIONGENERICA, parametros);
			break;
			
			
		case "informacionMatricula":
			parametros.put(NombreParametro.IDINFORMACIONGENERICA, "matriculaAdmisionPorInternet");
			solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion(TipoSolicitud.INFORMACIONGENERICA, parametros);
			break;
			
			
		case "solicitudEstadisticaRendimiento":
			switch(numeroEstudiosAludidos) {
			case 0:
				parametros.put(NombreParametro.IDINFORMACIONGENERICA, "solicitudRendimientoSinAlusiones");
				solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion(TipoSolicitud.INFORMACIONGENERICA, parametros);
				break;
			case 1:
				if(estudiosAludidos.get(0) instanceof Titulacion) {
					titulacionAludida = (Titulacion) estudiosAludidos.get(0);
					switch(parametrosEstadisiticosTitulacionAludidos.size()) {
					case 0:
						parametros.put(NombreParametro.IDINFORMACIONGENERICA, "solicitudRendimientoTitulacionSinParametroEstadistico");
						solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion(TipoSolicitud.INFORMACIONGENERICA, parametros);
						break;
					case 1:
						parametros.put(NombreParametro.TITULACION, titulacionAludida);
						parametros.put(NombreParametro.PARAMETROESTADISTICOTITULACION, parametrosEstadisiticosTitulacionAludidos.get(0));
						solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion(TipoSolicitud.ESTADISTICARENDIMIENTOTITULACION, parametros);
						break;
					default:
						parametros.put(NombreParametro.IDINFORMACIONGENERICA, "solicitudRendimientoTitulacionConVariosParametrosEstadisticos");
						solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion(TipoSolicitud.INFORMACIONGENERICA, parametros);
						break;
					}
				} else {
					asignaturaAludida = (Asignatura) estudiosAludidos.get(0);
					switch(parametrosEstadisiticosAsignaturaAludidos.size()) {
					case 0:
						parametros.put(NombreParametro.IDINFORMACIONGENERICA, "solicitudRendimientoAsignaturaSinParametroEstadistico");
						solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion(TipoSolicitud.INFORMACIONGENERICA, parametros);
						break;
					case 1:
						ArrayList<Estudio> posiblesTitulacionesAludidas = FactoriaEstudio.obtenerPosiblesTitulacionesDesdeIdAsignatura(asignaturaAludida.getIdAsignatura());
						if (posiblesTitulacionesAludidas.size() > 1) {
							HashMap<NombreParametro,Object> parametrosCallBack = new HashMap<NombreParametro,Object>();
							parametrosCallBack.put(NombreParametro.ASIGNATURA, asignaturaAludida);
							parametrosCallBack.put(NombreParametro.PARAMETROESTADISTICOASIGNATURA, parametrosEstadisiticosAsignaturaAludidos.get(0));
							solicitudInformacion = generarSolicitudInformacionCallBack(TipoSolicitud.ESTADISTICARENDIMIENTOASIGNATURA, parametrosCallBack, posiblesTitulacionesAludidas);
						}
						else {
							titulacionAludida = (Titulacion) posiblesTitulacionesAludidas.get(0);
							parametros.put(NombreParametro.TITULACION, titulacionAludida);
							parametros.put(NombreParametro.ASIGNATURA, asignaturaAludida);
							parametros.put(NombreParametro.PARAMETROESTADISTICOASIGNATURA, parametrosEstadisiticosAsignaturaAludidos.get(0));
							solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion(TipoSolicitud.ESTADISTICARENDIMIENTOASIGNATURA, parametros);
						}
						break;
					default:
						parametros.put(NombreParametro.IDINFORMACIONGENERICA, "solicitudRendimientoAsignaturaConVariosParametrosEstadisticos");
						solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion(TipoSolicitud.INFORMACIONGENERICA, parametros);
						break;
					}
				}
				break;
			case 2:
				if(contarNumeroTitulaciones(estudiosAludidos) == 1) { // Hay una titulación y una asignatura aludidas.
					extraerTitulacionesDesdeEstudios(estudiosAludidos);
					titulacionAludida = (Titulacion) extraerTitulacionesDesdeEstudios(estudiosAludidos).get(0);
					asignaturaAludida = (Asignatura) extraerAsignaturasDesdeEstudios(estudiosAludidos).get(0);
					parametros.put(NombreParametro.TITULACION, titulacionAludida);
					parametros.put(NombreParametro.ASIGNATURA, asignaturaAludida);
					if(FactoriaEstudio.esCombinacionValida(titulacionAludida, asignaturaAludida)) {
						if(parametrosEstadisiticosAsignaturaAludidos.size() == 0) {
							parametros.put(NombreParametro.IDINFORMACIONGENERICA, "solicitudRendimientoAsignaturaSinParametroEstadistico");
							solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion(TipoSolicitud.INFORMACIONGENERICA, parametros);
						}
						if(parametrosEstadisiticosAsignaturaAludidos.size() == 1) {
							parametros.put(NombreParametro.PARAMETROESTADISTICOASIGNATURA, parametrosEstadisiticosAsignaturaAludidos.get(0));
							solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion(TipoSolicitud.ESTADISTICARENDIMIENTOASIGNATURA, parametros);
						}
						if(parametrosEstadisiticosAsignaturaAludidos.size() > 1) {
							parametros.put(NombreParametro.IDINFORMACIONGENERICA, "solicitudRendimientoAsignaturaConVariosParametrosEstadisticos");
							solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion(TipoSolicitud.INFORMACIONGENERICA, parametros);
						}
					}
					else {
						solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion(TipoSolicitud.ASIGNATURANOPERTENECIENTEATITULACION, parametros);
					}
				}
				else { //Hay dos titulaciones o dos asignaturas aludidas.
					parametros.put(NombreParametro.IDINFORMACIONGENERICA, "solicitudEstadisticaRendimientoDemasiadasAlusiones");
					solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion(TipoSolicitud.INFORMACIONGENERICA, parametros);
				}
				break;
			default:
				parametros.put(NombreParametro.IDINFORMACIONGENERICA, "solicitudEstadisticaRendimientoDemasiadasAlusiones");
				solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion(TipoSolicitud.INFORMACIONGENERICA, parametros);
				break;
			}
			break;
			
			
		case "solicitudEstadisticaRendimientoTop":
			switch(numeroEstudiosAludidos) {
			case 0:
				switch(parametrosEstadisiticosTitulacionAludidos.size()) {
				case 0:
					parametros.put(NombreParametro.IDINFORMACIONGENERICA, "solicitudRendimientoTitulacionTopSinParametroEstadistico");
					solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion(TipoSolicitud.INFORMACIONGENERICA, parametros);
					break;
				case 1:
					parametros.put(NombreParametro.ORDENAMIENTO, indicadoresOrdenamientoAludidos.firstElement());
					parametros.put(NombreParametro.NIVELESTUDIOS, nivelesEstudiosAludidos.firstElement());
					parametros.put(NombreParametro.PARAMETROESTADISTICOTITULACION, parametrosEstadisiticosTitulacionAludidos.get(0));
					solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion(TipoSolicitud.ESTADISTICARENDIMIENTOTOPTITULACION, parametros);
					break;
				default:
					parametros.put(NombreParametro.IDINFORMACIONGENERICA, "solicitudRendimientoTitulacionTopConVariosParametrosEstadisticos");
					solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion(TipoSolicitud.INFORMACIONGENERICA, parametros);
					break;
				}
				break;
			case 1:
				if(estudiosAludidos.get(0) instanceof Titulacion) {
					titulacionAludida = (Titulacion) estudiosAludidos.get(0);
					switch(parametrosEstadisiticosAsignaturaAludidos.size()) {
					case 0:
						if(parametrosEstadisiticosTitulacionAludidos.size() ==1) {
							parametros.put(NombreParametro.TITULACION, titulacionAludida);
							parametros.put(NombreParametro.PARAMETROESTADISTICOTITULACION, parametrosEstadisiticosTitulacionAludidos.get(0));
							parametros.put(NombreParametro.ORDENAMIENTO, indicadoresOrdenamientoAludidos.get(0));
							solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion(TipoSolicitud.ESTADISTICARENDIMIENTOTOPASIGNATURA, parametros);
						}
						else {
							parametros.put(NombreParametro.IDINFORMACIONGENERICA, "solicitudRendimientoTitulacionTopSinParametroEstadistico");
							solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion(TipoSolicitud.INFORMACIONGENERICA, parametros);
						}
						break;
					case 1:
						parametros.put(NombreParametro.TITULACION, titulacionAludida);
						parametros.put(NombreParametro.PARAMETROESTADISTICOASIGNATURA, parametrosEstadisiticosAsignaturaAludidos.get(0));
						parametros.put(NombreParametro.ORDENAMIENTO, indicadoresOrdenamientoAludidos.get(0));
						parametros.put(NombreParametro.CRITERIOSCONSULTASQL, criteriosConsultaSQLAludidos);
						solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion(TipoSolicitud.ESTADISTICARENDIMIENTOTOPASIGNATURA, parametros);
						break;
					default:
						parametros.put(NombreParametro.IDINFORMACIONGENERICA, "solicitudRendimientoTitulacionTopConVariosParametrosEstadisticos");
						solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion(TipoSolicitud.INFORMACIONGENERICA, parametros);
						break;
					}
				} else {
					asignaturaAludida = (Asignatura) estudiosAludidos.get(0);
					switch(parametrosEstadisiticosAsignaturaAludidos.size()) {
					case 0:
						parametros.put(NombreParametro.IDINFORMACIONGENERICA, "solicitudRendimientoTopAsignaturaSinParametroEstadistico");
						solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion(TipoSolicitud.INFORMACIONGENERICA, parametros);
						break;
					case 1:
						ArrayList<Estudio> posiblesTitulacionesAludidas = FactoriaEstudio.obtenerPosiblesTitulacionesDesdeIdAsignatura(asignaturaAludida.getIdAsignatura());
						if (posiblesTitulacionesAludidas.size() > 1) {
							HashMap<NombreParametro,Object> parametrosCallBack = new HashMap<NombreParametro,Object>();
							parametrosCallBack.put(NombreParametro.ASIGNATURA, asignaturaAludida);
							parametrosCallBack.put(NombreParametro.PARAMETROESTADISTICOASIGNATURA, parametrosEstadisiticosAsignaturaAludidos.get(0));
							solicitudInformacion = generarSolicitudInformacionCallBack(TipoSolicitud.ESTADISTICARENDIMIENTOTOPASIGNATURA, parametrosCallBack, posiblesTitulacionesAludidas);
						}
						else {
							titulacionAludida = (Titulacion) posiblesTitulacionesAludidas.get(0);
							parametros.put(NombreParametro.TITULACION, titulacionAludida);
							parametros.put(NombreParametro.ASIGNATURA, asignaturaAludida);
							parametros.put(NombreParametro.PARAMETROESTADISTICOASIGNATURA, parametrosEstadisiticosAsignaturaAludidos.get(0));
							solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion(TipoSolicitud.ESTADISTICARENDIMIENTOTOPASIGNATURA, parametros);
						}
						break;
					default:
						parametros.put(NombreParametro.IDINFORMACIONGENERICA, "solicitudRendimientoAsignaturaConVariosParametrosEstadisticos");
						solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion(TipoSolicitud.INFORMACIONGENERICA, parametros);
						break;
					}
				}
				break;
			case 2:
				if(contarNumeroTitulaciones(estudiosAludidos) == 1) { // Hay una titulación y una asignatura aludidas.
					extraerTitulacionesDesdeEstudios(estudiosAludidos);
					titulacionAludida = (Titulacion) extraerTitulacionesDesdeEstudios(estudiosAludidos).get(0);
					asignaturaAludida = (Asignatura) extraerAsignaturasDesdeEstudios(estudiosAludidos).get(0);
					parametros.put(NombreParametro.TITULACION, titulacionAludida);
					parametros.put(NombreParametro.ASIGNATURA, asignaturaAludida);
					if(FactoriaEstudio.esCombinacionValida(titulacionAludida, asignaturaAludida)) {
						if(parametrosEstadisiticosAsignaturaAludidos.size() == 0) {
							parametros.put(NombreParametro.IDINFORMACIONGENERICA, "solicitudRendimientoTopAsignaturaSinParametroEstadistico");
							solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion(TipoSolicitud.INFORMACIONGENERICA, parametros);
						}
						if(parametrosEstadisiticosAsignaturaAludidos.size() == 1) {
							parametros.put(NombreParametro.PARAMETROESTADISTICOASIGNATURA, parametrosEstadisiticosAsignaturaAludidos.get(0));
							solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion(TipoSolicitud.ESTADISTICARENDIMIENTOASIGNATURA, parametros);
						}
						if(parametrosEstadisiticosAsignaturaAludidos.size() > 1) {
							parametros.put(NombreParametro.IDINFORMACIONGENERICA, "solicitudRendimientoAsignaturaConVariosParametrosEstadisticos");
							solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion(TipoSolicitud.INFORMACIONGENERICA, parametros);
						}
						solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion(TipoSolicitud.ESTADISTICARENDIMIENTOTOPASIGNATURA, parametros);
					}
					else {
						solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion(TipoSolicitud.ASIGNATURANOPERTENECIENTEATITULACION, parametros);
					}
				}
				else { //Hay dos titulaciones o dos asignaturas aludidas.
					parametros.put(NombreParametro.IDINFORMACIONGENERICA, "solicitudEstadisticaRendimientoTopDemasiadasAlusiones");
					solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion(TipoSolicitud.INFORMACIONGENERICA, parametros);
				}
				break;
			default:
				parametros.put(NombreParametro.IDINFORMACIONGENERICA, "solicitudEstadisticaRendimientoTopDemasiadasAlusiones");
				solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion(TipoSolicitud.INFORMACIONGENERICA, parametros);
				break;
			}
			break;
			
			
		case "solicitudMatriculados":
			switch(numeroEstudiosAludidos) {
			case 0:
				parametros.put(NombreParametro.IDINFORMACIONGENERICA, "solicitudMatriculadosSinAlusiones");
				solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion(TipoSolicitud.INFORMACIONGENERICA, parametros);
				break;
			case 1:
				if(estudiosAludidos.get(0) instanceof Titulacion) {
					titulacionAludida = (Titulacion) estudiosAludidos.get(0);
					parametros.put(NombreParametro.TITULACION, titulacionAludida);
					solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion(TipoSolicitud.MATRICULADOSTITULACION, parametros);
				} else {
					asignaturaAludida = (Asignatura) estudiosAludidos.get(0);
					ArrayList<Estudio> posiblesTitulacionesAludidas = FactoriaEstudio.obtenerPosiblesTitulacionesDesdeIdAsignatura(asignaturaAludida.getIdAsignatura());
					if (posiblesTitulacionesAludidas.size() > 1) {
						HashMap<NombreParametro,Object> parametrosCallBack = new HashMap<NombreParametro,Object>();
						parametrosCallBack.put(NombreParametro.ASIGNATURA, asignaturaAludida);
						solicitudInformacion = generarSolicitudInformacionCallBack(TipoSolicitud.MATRICULADOSASIGNATURA, parametrosCallBack, posiblesTitulacionesAludidas);
					}
					else {
						titulacionAludida = (Titulacion) posiblesTitulacionesAludidas.get(0);
						parametros.put(NombreParametro.TITULACION, titulacionAludida);
						parametros.put(NombreParametro.ASIGNATURA, asignaturaAludida);
						solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion(TipoSolicitud.MATRICULADOSASIGNATURA, parametros);
					}
				}
				break;
			case 2:
				if(contarNumeroTitulaciones(estudiosAludidos) == 1) { // Hay una titulación y una asignatura aludidas.
					extraerTitulacionesDesdeEstudios(estudiosAludidos);
					titulacionAludida = (Titulacion) extraerTitulacionesDesdeEstudios(estudiosAludidos).get(0);
					asignaturaAludida = (Asignatura) extraerAsignaturasDesdeEstudios(estudiosAludidos).get(0);
					parametros.put(NombreParametro.TITULACION, titulacionAludida);
					parametros.put(NombreParametro.ASIGNATURA, asignaturaAludida);
					if(FactoriaEstudio.esCombinacionValida(titulacionAludida, asignaturaAludida)) {
						solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion(TipoSolicitud.MATRICULADOSASIGNATURA, parametros);
					}
					else {
						solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion(TipoSolicitud.ASIGNATURANOPERTENECIENTEATITULACION, parametros);
					}
				}
				else { //Hay dos titulaciones o dos asignaturas aludidas.
					HashMap<NombreParametro,Object> parametrosCallBack = new HashMap<NombreParametro,Object>();
					solicitudInformacion = generarSolicitudInformacionCallBack(TipoSolicitud.MATRICULADOSTITULACION, parametrosCallBack, estudiosAludidos);
				}
				break;
			default:
				parametros.put(NombreParametro.IDINFORMACIONGENERICA, "solicitudMatriculadosDemasiadasAlusiones");
				solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion(TipoSolicitud.INFORMACIONGENERICA, parametros);
				break;
			}
			break;
			
			
		case "solicitudPreciosTitulacion":
			ArrayList<Titulacion> titulacionesAludidas =  extraerTitulacionesDesdeEstudios(estudiosAludidos); 
			if(titulacionesAludidas.size() > 0) {
				if(titulacionesAludidas.size() >1) {
					HashMap<NombreParametro,Object> parametrosCallBack = new HashMap<NombreParametro,Object>();
					solicitudInformacion = generarSolicitudInformacionCallBack(TipoSolicitud.PRECIOSTITULACION, parametrosCallBack, titulacionesAludidas);
				}
				else {
					
					titulacionAludida = titulacionesAludidas.get(0);
					parametros.put(NombreParametro.TITULACION, titulacionAludida);
					solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion(TipoSolicitud.PRECIOSTITULACION, parametros);
				}
			}
			else {
				parametros.put(NombreParametro.IDINFORMACIONGENERICA, "solicitudPreciosSinAlusiones");
				solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion(TipoSolicitud.INFORMACIONGENERICA, parametros);
			}
			break;
			
			
		case "solicitudValoracionEstudiantil":
			switch(numeroEstudiosAludidos) {
			case 0:
				parametros.put(NombreParametro.IDINFORMACIONGENERICA, "solicitudValoracionEstudiantilSinAlusiones");
				solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion(TipoSolicitud.INFORMACIONGENERICA, parametros);
				break;
			case 1:
				if(estudiosAludidos.get(0) instanceof Titulacion) {
					titulacionAludida = (Titulacion) estudiosAludidos.get(0);
					parametros.put(NombreParametro.TITULACION, titulacionAludida);
					solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion(TipoSolicitud.VALORACIONESTUDIANTILTITULACION, parametros);
				} else {
					asignaturaAludida = (Asignatura) estudiosAludidos.get(0);
					ArrayList<Estudio> posiblesTitulacionesAludidas = FactoriaEstudio.obtenerPosiblesTitulacionesDesdeIdAsignatura(asignaturaAludida.getIdAsignatura());
					if (posiblesTitulacionesAludidas.size() > 1) {
						HashMap<NombreParametro,Object> parametrosCallBack = new HashMap<NombreParametro,Object>();
						parametrosCallBack.put(NombreParametro.ASIGNATURA, asignaturaAludida);
						solicitudInformacion = generarSolicitudInformacionCallBack(TipoSolicitud.VALORACIONESTUDIANTILASIGNATURA, parametrosCallBack, posiblesTitulacionesAludidas);
					}
					else {
						titulacionAludida = (Titulacion) posiblesTitulacionesAludidas.get(0);
						parametros.put(NombreParametro.TITULACION, titulacionAludida);
						parametros.put(NombreParametro.ASIGNATURA, asignaturaAludida);
						solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion(TipoSolicitud.VALORACIONESTUDIANTILASIGNATURA, parametros);
					}
				}
				break;
			case 2:
				if(contarNumeroTitulaciones(estudiosAludidos) == 1) { // Hay una titulación y una asignatura aludidas.
					extraerTitulacionesDesdeEstudios(estudiosAludidos);
					titulacionAludida = (Titulacion) extraerTitulacionesDesdeEstudios(estudiosAludidos).get(0);
					asignaturaAludida = (Asignatura) extraerAsignaturasDesdeEstudios(estudiosAludidos).get(0);
					parametros.put(NombreParametro.TITULACION, titulacionAludida);
					parametros.put(NombreParametro.ASIGNATURA, asignaturaAludida);
					if(FactoriaEstudio.esCombinacionValida(titulacionAludida, asignaturaAludida)) {
						solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion(TipoSolicitud.VALORACIONESTUDIANTILASIGNATURA, parametros);
					}
					else {
						solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion(TipoSolicitud.ASIGNATURANOPERTENECIENTEATITULACION, parametros);
					}
				}
				else { //Hay dos titulaciones o dos asignaturas aludidas.
					parametros.put(NombreParametro.IDINFORMACIONGENERICA, "solicitudValoracionEstudiantilDemasiadasAlusiones");
					solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion(TipoSolicitud.INFORMACIONGENERICA, parametros);
				}
				break;
			default:
				parametros.put(NombreParametro.IDINFORMACIONGENERICA, "solicitudValoracionEstudiantilDemasiadasAlusiones");
				solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion(TipoSolicitud.INFORMACIONGENERICA, parametros);
				break;
			}
			break;
			
			
		case "solicitudValoracionEstudiantilTop":
			//solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion(TipoSolicitud.INFORMACIONGENERICA, "saludo");
			break;
			
			
		default:
			throw new IllegalArgumentException("La clasificación de la cadena de texto examinada no encaja con ninguna de las opciones codificadas.");
		}
		if(solicitudInformacion==null) {
			System.out.println("ERROR con " + clasificacion);
		}
		assert (solicitudInformacion!=null);
		return solicitudInformacion;
	}
	
	@SuppressWarnings("unchecked")
	private SolicitudInformacion generarSolicitudInformacionCallBack(TipoSolicitud tipoSolicitudInformacionPendiente, HashMap<NombreParametro,Object> parametrosCallBack, ArrayList<?> posiblesEstudiosAludidos) {
		callBack.setCallBackPendiente(true);
		callBack.setOrigenConversacion(origenConversacion);
		callBack.setTipoSolicitudInformacionPendiente(tipoSolicitudInformacionPendiente);
		callBack.setParametros(parametrosCallBack);
		callBack.setPosiblesEstudiosAludidos((ArrayList<Estudio>) posiblesEstudiosAludidos);
		ArrayList<String> opciones = crearListaOpciones((ArrayList<Estudio>)posiblesEstudiosAludidos);
		HashMap<NombreParametro,Object> parametros = new HashMap<NombreParametro,Object>();
		parametros.put(NombreParametro.ORIGENCONVERSACION, origenConversacion);
		parametros.put(NombreParametro.OPCIONES, opciones);
		return FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion(TipoSolicitud.CALLBACK, parametros);
	}
	
	private ArrayList<String> crearListaOpciones(ArrayList<Estudio> posiblesEstudiosAludidos){
		ArrayList<String> opciones = new ArrayList<String>();
		for(Estudio estudio: posiblesEstudiosAludidos) {
			opciones.add(estudio.getNombre());
		}
		opciones.add("Mi consulta no estaba relacionada con eso");
		return opciones;
	}

	private ArrayList<Titulacion> extraerTitulacionesDesdeEstudios(ArrayList<Estudio> estudios) {
		ArrayList<Titulacion> titulaciones = new ArrayList<Titulacion>();
		for(Estudio estudio: estudios) {
			if (estudio instanceof Titulacion) {
				titulaciones.add((Titulacion) estudio);
			}
		}
		return titulaciones;
	}
	
	private ArrayList<Asignatura> extraerAsignaturasDesdeEstudios(ArrayList<Estudio> estudios) {
		ArrayList<Asignatura> asignaturas = new ArrayList<Asignatura>();
		for(Estudio estudio: estudios) {
			if (estudio instanceof Asignatura) {
				asignaturas.add((Asignatura) estudio);
			}
		}
		return asignaturas;
	}
	
	private int contarNumeroTitulaciones(ArrayList<Estudio> estudios) {
		int numeroDeTitulaciones = 0;
		for(Estudio estudio: estudios) {
			if (estudio instanceof Titulacion) {
				numeroDeTitulaciones++;
			}
		}
		return numeroDeTitulaciones;
	}
	
	private int contarNumeroAsignaturas(ArrayList<Estudio> estudios) {
		int numeroDeAsignaturas = 0;
		for(Estudio estudio: estudios) {
			if (estudio instanceof Asignatura) {
				numeroDeAsignaturas++;
			}
		}
		return numeroDeAsignaturas;
	}
	
	
	public String obtenerRespuestaActual() {
		return respuestaActual;
	}
	
	public String getIdConversacion() {
		return chatID;
	}
	
}
