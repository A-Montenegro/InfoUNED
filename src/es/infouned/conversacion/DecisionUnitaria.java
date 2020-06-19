package es.infouned.conversacion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

import es.infouned.conversacion.CallBack.TipoCallBack;
import es.infouned.conversacion.Conversacion.OrigenConversacion;
import es.infouned.estudios.Asignatura;
import es.infouned.estudios.AsignaturaBorrosa;
import es.infouned.estudios.CriterioConsultaSQL;
import es.infouned.estudios.Estudio;
import es.infouned.estudios.FactoriaEstudio;
import es.infouned.estudios.IndicadorOrdenamiento;
import es.infouned.estudios.NivelEstudios;
import es.infouned.estudios.ParametroEstadistico;
import es.infouned.estudios.Titulacion;
import es.infouned.procesamientoLenguajeNatural.Frase;
import es.infouned.solicitudesInformacionBBDD.FactoriaDeSolicitudInformacion;
import es.infouned.solicitudesInformacionBBDD.SolicitudInformacion;
import es.infouned.solicitudesInformacionBBDD.FactoriaDeSolicitudInformacion.NombreParametro;
import es.infouned.solicitudesInformacionBBDD.FactoriaDeSolicitudInformacion.TipoSolicitud;

/**
 * Esta clase usa todos los datos que recibe en su constructor para generar la instancia de SolicitudInformacion correcta.
 * @author Alberto Martínez Montenegro
 * 
 */
public class DecisionUnitaria {
	private String clasificacion;
	private int numeroEstudiosAludidos;
	private ArrayList<Estudio> estudiosAludidos;
	private ArrayList<ParametroEstadistico> parametrosEstadisiticosTitulacionAludidos;
	private ArrayList<ParametroEstadistico> parametrosEstadisiticosAsignaturaAludidos;
	private IndicadorOrdenamiento indicadorOrdenamientoPrincipal;
	private NivelEstudios nivelEstudiosPrincipal;
	private Stack<CriterioConsultaSQL> criteriosConsultaSQLAludidos;
	private Titulacion titulacionAludida;
	private Asignatura asignaturaAludida;
	private AsignaturaBorrosa asignaturaBorrosaAludida;
	private HashMap<NombreParametro,Object> parametros;
	private CallBack callBack;
	private OrigenConversacion origenConversacion;
	
	public DecisionUnitaria(Frase frase, CallBack callBack, OrigenConversacion origenConversacion) {
		this.clasificacion = frase.getClasificacion();
		this.numeroEstudiosAludidos = frase.getNumeroEstudiosAludidos();
		this.estudiosAludidos = frase.getEstudiosAludidos();
		this.parametrosEstadisiticosTitulacionAludidos = frase.getParametrosEstadisticosTitulacionAludidos();
		this.parametrosEstadisiticosAsignaturaAludidos = frase.getParametrosEstadisticosAsignaturaAludidos();
		this.indicadorOrdenamientoPrincipal = frase.getIndicadorOrdenamientoPrincipal();
		this.nivelEstudiosPrincipal = frase.getNivelEstudiosPrincipal();
		this.criteriosConsultaSQLAludidos = frase.getCriteriosConsultaSQLAludidos();
		this.titulacionAludida = null;
		this.asignaturaAludida = null;
		this.asignaturaBorrosaAludida = null;
		this.parametros = new HashMap<NombreParametro,Object>();
		this.callBack = callBack;
		this.origenConversacion = origenConversacion;
	}
	
	public SolicitudInformacion decidirSolicitudInformacion() {	
		SolicitudInformacion solicitudInformacion = null;
		switch (clasificacion) {
			case "saludo":
			case "despedida":
			case "noClasificable":
				parametros.put(NombreParametro.IDINFORMACIONGENERICA, clasificacion);
				solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion(TipoSolicitud.INFORMACIONGENERICA, parametros);
				break;
			case "informacionContacto":	
				solicitudInformacion = generarSolicitudInformacionCallBack(TipoCallBack.SOLICITUDINFORMACIONCONTACTO);
				break;
			case "solicitudCUID":
				solicitudInformacion = generarSolicitudInformacionCallBack(TipoCallBack.SOLICITUDINFORMACIONCUID);
				break;
			case "informacionMatricula":
				parametros.put(NombreParametro.IDINFORMACIONGENERICA, "matriculaAdmisionPorInternet");
				solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion(TipoSolicitud.INFORMACIONGENERICA, parametros);
				break;				
			case "solicitudEstadisticaRendimiento":
				switch(numeroEstudiosAludidos) {
				case 0:
					switch(parametrosEstadisiticosTitulacionAludidos.size()) {
					case 0:
						parametros.put(NombreParametro.IDINFORMACIONGENERICA, "solicitudRendimientoTitulacionTopSinParametroEstadistico");
						solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion(TipoSolicitud.INFORMACIONGENERICA, parametros);
						break;
					case 1:
						parametros.put(NombreParametro.ORDENAMIENTO, indicadorOrdenamientoPrincipal);
						parametros.put(NombreParametro.NIVELESTUDIOS, nivelEstudiosPrincipal);
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
						switch(parametrosEstadisiticosTitulacionAludidos.size()) {
						case 0:
							if(parametrosEstadisiticosAsignaturaAludidos.size() == 1) {
								parametros.put(NombreParametro.TITULACION, titulacionAludida);
								parametros.put(NombreParametro.PARAMETROESTADISTICOASIGNATURA, parametrosEstadisiticosAsignaturaAludidos.get(0));
								parametros.put(NombreParametro.ORDENAMIENTO, indicadorOrdenamientoPrincipal);
								parametros.put(NombreParametro.CRITERIOSCONSULTASQL, criteriosConsultaSQLAludidos);
								solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion(TipoSolicitud.ESTADISTICARENDIMIENTOTOPASIGNATURA, parametros);
							}
							else {
								parametros.put(NombreParametro.IDINFORMACIONGENERICA, "solicitudRendimientoTitulacionSinParametroEstadistico");
								solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion(TipoSolicitud.INFORMACIONGENERICA, parametros);
							}
							break;
						case 1:
							if(indicadorOrdenamientoPrincipal.getNombre().equals("NO_DETECTADO")) {
								parametros.put(NombreParametro.TITULACION, titulacionAludida);
								parametros.put(NombreParametro.PARAMETROESTADISTICOTITULACION, parametrosEstadisiticosTitulacionAludidos.get(0));
								solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion(TipoSolicitud.ESTADISTICARENDIMIENTOTITULACION, parametros);
							}
							else {
								if(parametrosEstadisiticosAsignaturaAludidos.size() == 1) {
									parametros.put(NombreParametro.TITULACION, titulacionAludida);
									parametros.put(NombreParametro.PARAMETROESTADISTICOASIGNATURA, parametrosEstadisiticosAsignaturaAludidos.get(0));
									parametros.put(NombreParametro.ORDENAMIENTO, indicadorOrdenamientoPrincipal);
									parametros.put(NombreParametro.CRITERIOSCONSULTASQL, criteriosConsultaSQLAludidos);
									solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion(TipoSolicitud.ESTADISTICARENDIMIENTOTOPASIGNATURA, parametros);
								}
								else {
									parametros.put(NombreParametro.IDINFORMACIONGENERICA, "solicitudRendimientoAsignaturaSinParametroEstadistico");
									solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion(TipoSolicitud.INFORMACIONGENERICA, parametros);
								}
							}
							break;
						default:
							parametros.put(NombreParametro.IDINFORMACIONGENERICA, "solicitudRendimientoTitulacionConVariosParametrosEstadisticos");
							solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion(TipoSolicitud.INFORMACIONGENERICA, parametros);
							break;
						}
					} else {
						switch(parametrosEstadisiticosAsignaturaAludidos.size()) {
						case 0:
							parametros.put(NombreParametro.IDINFORMACIONGENERICA, "solicitudRendimientoAsignaturaSinParametroEstadistico");
							solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion(TipoSolicitud.INFORMACIONGENERICA, parametros);
							break;
						case 1:
							ArrayList<Estudio> posiblesTitulacionesAludidas = FactoriaEstudio.obtenerPosiblesTitulacionesDesdeEstudio(estudiosAludidos.get(0));
							if(estudiosAludidos.get(0) instanceof AsignaturaBorrosa) {
								asignaturaBorrosaAludida = (AsignaturaBorrosa) estudiosAludidos.get(0);
								if (posiblesTitulacionesAludidas.size() > 1) {
									HashMap<NombreParametro,Object> parametrosCallBack = new HashMap<NombreParametro,Object>();
									parametrosCallBack.put(NombreParametro.ASIGNATURABORROSA, asignaturaBorrosaAludida);
									parametrosCallBack.put(NombreParametro.PARAMETROESTADISTICOASIGNATURA, parametrosEstadisiticosAsignaturaAludidos.get(0));
									solicitudInformacion = generarSolicitudInformacionCallBack(TipoSolicitud.ESTADISTICARENDIMIENTOASIGNATURA, TipoCallBack.TITULACIONDESCONOCIDAPARAASIGNATURABORROSA, parametrosCallBack, posiblesTitulacionesAludidas);
								}
								else {
									titulacionAludida = (Titulacion) posiblesTitulacionesAludidas.get(0);
									parametros.put(NombreParametro.TITULACION, titulacionAludida);
									parametros.put(NombreParametro.ASIGNATURABORROSA, asignaturaBorrosaAludida);
									parametros.put(NombreParametro.PARAMETROESTADISTICOASIGNATURA, parametrosEstadisiticosAsignaturaAludidos.get(0));
									solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion(TipoSolicitud.ESTADISTICARENDIMIENTOASIGNATURA, parametros);
								}
							}
							else {
								asignaturaAludida = (Asignatura) estudiosAludidos.get(0);
								if (posiblesTitulacionesAludidas.size() > 1) {
									HashMap<NombreParametro,Object> parametrosCallBack = new HashMap<NombreParametro,Object>();
									parametrosCallBack.put(NombreParametro.ASIGNATURA, asignaturaAludida);
									parametrosCallBack.put(NombreParametro.PARAMETROESTADISTICOASIGNATURA, parametrosEstadisiticosAsignaturaAludidos.get(0));
									solicitudInformacion = generarSolicitudInformacionCallBack(TipoSolicitud.ESTADISTICARENDIMIENTOASIGNATURA, TipoCallBack.TITULACIONDESCONOCIDAPARAASIGNATURA, parametrosCallBack, posiblesTitulacionesAludidas);
								}
								else {
									titulacionAludida = (Titulacion) posiblesTitulacionesAludidas.get(0);
									parametros.put(NombreParametro.TITULACION, titulacionAludida);
									parametros.put(NombreParametro.ASIGNATURA, asignaturaAludida);
									parametros.put(NombreParametro.PARAMETROESTADISTICOASIGNATURA, parametrosEstadisiticosAsignaturaAludidos.get(0));
									solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion(TipoSolicitud.ESTADISTICARENDIMIENTOASIGNATURA, parametros);
								}
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
						titulacionAludida = (Titulacion) extraerTitulacionesDesdeEstudios(estudiosAludidos).get(0);
						if(contarNumeroAsignaturasBorrosas(estudiosAludidos) == 1) {
							asignaturaAludida = FactoriaEstudio.transformarAsignaturaBorrosaEnAsignatura(titulacionAludida, (AsignaturaBorrosa) estudiosAludidos.get(0));
						}
						else {
							asignaturaAludida = (Asignatura) extraerAsignaturasDesdeEstudios(estudiosAludidos).get(0);
						}
						if(FactoriaEstudio.esCombinacionValida(titulacionAludida, asignaturaAludida)) {
							if(parametrosEstadisiticosAsignaturaAludidos.size() == 0) {
								parametros.put(NombreParametro.IDINFORMACIONGENERICA, "solicitudRendimientoAsignaturaSinParametroEstadistico");
								solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion(TipoSolicitud.INFORMACIONGENERICA, parametros);
							}
							if(parametrosEstadisiticosAsignaturaAludidos.size() == 1) {
								parametros.put(NombreParametro.TITULACION, titulacionAludida);
								parametros.put(NombreParametro.ASIGNATURA, asignaturaAludida);
								parametros.put(NombreParametro.PARAMETROESTADISTICOASIGNATURA, parametrosEstadisiticosAsignaturaAludidos.get(0));
								solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion(TipoSolicitud.ESTADISTICARENDIMIENTOASIGNATURA, parametros);
							}
							if(parametrosEstadisiticosAsignaturaAludidos.size() > 1) {
								parametros.put(NombreParametro.IDINFORMACIONGENERICA, "solicitudRendimientoAsignaturaConVariosParametrosEstadisticos");
								solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion(TipoSolicitud.INFORMACIONGENERICA, parametros);
							}
						}
						else {
							parametros.put(NombreParametro.TITULACION, titulacionAludida);
							parametros.put(NombreParametro.ASIGNATURA, asignaturaAludida);
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
						ArrayList<Estudio> posiblesTitulacionesAludidas = FactoriaEstudio.obtenerPosiblesTitulacionesDesdeEstudio(estudiosAludidos.get(0));
						if(estudiosAludidos.get(0) instanceof AsignaturaBorrosa) {
							asignaturaBorrosaAludida = (AsignaturaBorrosa) estudiosAludidos.get(0);
							if (posiblesTitulacionesAludidas.size() > 1) {
								HashMap<NombreParametro,Object> parametrosCallBack = new HashMap<NombreParametro,Object>();
								parametrosCallBack.put(NombreParametro.ASIGNATURABORROSA, asignaturaBorrosaAludida);
								solicitudInformacion = generarSolicitudInformacionCallBack(TipoSolicitud.MATRICULADOSASIGNATURA, TipoCallBack.TITULACIONDESCONOCIDAPARAASIGNATURABORROSA, parametrosCallBack, posiblesTitulacionesAludidas);
							}
							else {
								titulacionAludida = (Titulacion) posiblesTitulacionesAludidas.get(0);
								parametros.put(NombreParametro.TITULACION, titulacionAludida);
								parametros.put(NombreParametro.ASIGNATURABORROSA, asignaturaBorrosaAludida);
								solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion(TipoSolicitud.MATRICULADOSASIGNATURA, parametros);
							}
						}
						else {
							asignaturaAludida = (Asignatura) estudiosAludidos.get(0);
							if (posiblesTitulacionesAludidas.size() > 1) {
								HashMap<NombreParametro,Object> parametrosCallBack = new HashMap<NombreParametro,Object>();
								parametrosCallBack.put(NombreParametro.ASIGNATURA, asignaturaAludida);
								solicitudInformacion = generarSolicitudInformacionCallBack(TipoSolicitud.MATRICULADOSASIGNATURA, TipoCallBack.TITULACIONDESCONOCIDAPARAASIGNATURA, parametrosCallBack, posiblesTitulacionesAludidas);
							}
							else {
								titulacionAludida = (Titulacion) posiblesTitulacionesAludidas.get(0);
								parametros.put(NombreParametro.TITULACION, titulacionAludida);
								parametros.put(NombreParametro.ASIGNATURA, asignaturaAludida);
								solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion(TipoSolicitud.MATRICULADOSASIGNATURA, parametros);
							}
						}
					}
					break;
				case 2:
					if(contarNumeroTitulaciones(estudiosAludidos) == 1) { // Hay una titulación y una asignatura aludidas.
						extraerTitulacionesDesdeEstudios(estudiosAludidos);
						titulacionAludida = (Titulacion) extraerTitulacionesDesdeEstudios(estudiosAludidos).get(0);
						if(contarNumeroAsignaturasBorrosas(estudiosAludidos) == 1) {
							asignaturaAludida = FactoriaEstudio.transformarAsignaturaBorrosaEnAsignatura(titulacionAludida, (AsignaturaBorrosa) estudiosAludidos.get(0));
						}
						else {
							asignaturaAludida = (Asignatura) extraerAsignaturasDesdeEstudios(estudiosAludidos).get(0);
						}
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
						parametros.put(NombreParametro.IDINFORMACIONGENERICA, "solicitudMatriculadosDemasiadasAlusiones");
						solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion(TipoSolicitud.INFORMACIONGENERICA, parametros);
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
						solicitudInformacion = generarSolicitudInformacionCallBack(TipoSolicitud.PRECIOSTITULACION, TipoCallBack.TITULACIONDESCONOCIDAPARAASIGNATURA, parametrosCallBack, titulacionesAludidas);
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
			
			case "solicitudGuia":
				switch(numeroEstudiosAludidos) {
				case 0:
					parametros.put(NombreParametro.IDINFORMACIONGENERICA, "solicitudGuiaAsignaturaSinAlusiones");
					solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion(TipoSolicitud.INFORMACIONGENERICA, parametros);
					break;
				case 1:
					if(estudiosAludidos.get(0) instanceof Titulacion) {
						titulacionAludida = (Titulacion) estudiosAludidos.get(0);
						parametros.put(NombreParametro.TITULACION, titulacionAludida);
						solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion(TipoSolicitud.GUIAASIGNATURA, parametros);
					} else {
						ArrayList<Estudio> posiblesTitulacionesAludidas = FactoriaEstudio.obtenerPosiblesTitulacionesDesdeEstudio(estudiosAludidos.get(0));
						if(estudiosAludidos.get(0) instanceof Asignatura) {
						asignaturaAludida = (Asignatura) estudiosAludidos.get(0);
							if (posiblesTitulacionesAludidas.size() > 1) {
								HashMap<NombreParametro,Object> parametrosCallBack = new HashMap<NombreParametro,Object>();
								parametrosCallBack.put(NombreParametro.ASIGNATURA, asignaturaAludida);
								solicitudInformacion = generarSolicitudInformacionCallBack(TipoSolicitud.GUIAASIGNATURA, TipoCallBack.TITULACIONDESCONOCIDAPARAASIGNATURA, parametrosCallBack, posiblesTitulacionesAludidas);
							}
							else {
								titulacionAludida = (Titulacion) posiblesTitulacionesAludidas.get(0);
								parametros.put(NombreParametro.TITULACION, titulacionAludida);
								parametros.put(NombreParametro.ASIGNATURA, asignaturaAludida);
								solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion(TipoSolicitud.GUIAASIGNATURA, parametros);
							}
						}
						else {
							asignaturaBorrosaAludida = (AsignaturaBorrosa) estudiosAludidos.get(0);
							if (posiblesTitulacionesAludidas.size() > 1) {
								HashMap<NombreParametro,Object> parametrosCallBack = new HashMap<NombreParametro,Object>();
								parametrosCallBack.put(NombreParametro.ASIGNATURABORROSA, asignaturaBorrosaAludida);
								solicitudInformacion = generarSolicitudInformacionCallBack(TipoSolicitud.GUIAASIGNATURA, TipoCallBack.TITULACIONDESCONOCIDAPARAASIGNATURABORROSA, parametrosCallBack, posiblesTitulacionesAludidas);
							}
							else {
								titulacionAludida = (Titulacion) posiblesTitulacionesAludidas.get(0);
								parametros.put(NombreParametro.TITULACION, titulacionAludida);
								parametros.put(NombreParametro.ASIGNATURABORROSA, asignaturaBorrosaAludida);
								solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion(TipoSolicitud.GUIAASIGNATURA, parametros);
							}
						}
					}
					break;
				case 2:
					if(contarNumeroTitulaciones(estudiosAludidos) == 1) { // Hay una titulación y una asignatura aludidas.
						titulacionAludida = (Titulacion) extraerTitulacionesDesdeEstudios(estudiosAludidos).get(0);
						if(contarNumeroAsignaturasBorrosas(estudiosAludidos) == 1) {
							asignaturaAludida = FactoriaEstudio.transformarAsignaturaBorrosaEnAsignatura(titulacionAludida, (AsignaturaBorrosa) estudiosAludidos.get(0));
						}
						else {
							asignaturaAludida = (Asignatura) extraerAsignaturasDesdeEstudios(estudiosAludidos).get(0);
						}	
						parametros.put(NombreParametro.TITULACION, titulacionAludida);
						parametros.put(NombreParametro.ASIGNATURA, asignaturaAludida);
						if(FactoriaEstudio.esCombinacionValida(titulacionAludida, asignaturaAludida)) {
							solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion(TipoSolicitud.GUIAASIGNATURA, parametros);
						}
						else {
							solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion(TipoSolicitud.ASIGNATURANOPERTENECIENTEATITULACION, parametros);
						}
					}
					else { //Hay dos titulaciones o dos asignaturas aludidas.
						parametros.put(NombreParametro.IDINFORMACIONGENERICA, "solicitudGuiaAsignaturaDemasiadasAlusiones");
						solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion(TipoSolicitud.INFORMACIONGENERICA, parametros);
					}
					break;
				default:
					parametros.put(NombreParametro.IDINFORMACIONGENERICA, "solicitudGuiaAsignaturaDemasiadasAlusiones");
					solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion(TipoSolicitud.INFORMACIONGENERICA, parametros);
					break;
				}
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
	private SolicitudInformacion generarSolicitudInformacionCallBack(TipoSolicitud tipoSolicitudInformacionPendiente, TipoCallBack tipoCallBack, HashMap<NombreParametro,Object> parametrosCallBack, ArrayList<?> posiblesEstudiosAludidos) {
		if(!callBack.getCallBackPendiente()) {
			callBack.setOrigenConversacion(origenConversacion);
			callBack.setTipoSolicitudInformacionPendiente(tipoSolicitudInformacionPendiente);
			callBack.setTipoCallBack(tipoCallBack);
			callBack.setParametros(parametrosCallBack);
			callBack.setPosiblesEstudiosAludidos((ArrayList<Estudio>) posiblesEstudiosAludidos);
			callBack.setCallBackPendiente(true);
		}
		ArrayList<String> opciones = crearListaOpciones((ArrayList<Estudio>)posiblesEstudiosAludidos);
		HashMap<NombreParametro,Object> parametros = new HashMap<NombreParametro,Object>();
		parametros.put(NombreParametro.ORIGENCONVERSACION, origenConversacion);
		parametros.put(NombreParametro.OPCIONES, opciones);
		parametros.put(NombreParametro.TIPOCALLBACK, tipoCallBack);
		parametros.put(NombreParametro.PARAMETROSCALLBACK, parametrosCallBack);
		return FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion(TipoSolicitud.CALLBACK, parametros);
	}
	
	private SolicitudInformacion generarSolicitudInformacionCallBack(TipoCallBack tipoCallBack) {
		ArrayList<String> posiblesOpciones = generarPosiblesOpciones(tipoCallBack);
		if(!callBack.getCallBackPendiente()) {
			callBack.setOrigenConversacion(origenConversacion);
			callBack.setTipoSolicitudInformacionPendiente(TipoSolicitud.INFORMACIONGENERICA);
			callBack.setTipoCallBack(tipoCallBack);
			callBack.setParametros(new HashMap<NombreParametro,Object>());
			callBack.setPosiblesOpciones(posiblesOpciones);
			callBack.setCallBackPendiente(true);
		}
		HashMap<NombreParametro,Object> parametros = new HashMap<NombreParametro,Object>();
		parametros.put(NombreParametro.ORIGENCONVERSACION, origenConversacion);
		parametros.put(NombreParametro.OPCIONES, posiblesOpciones);
		parametros.put(NombreParametro.TIPOCALLBACK, tipoCallBack);
		parametros.put(NombreParametro.PARAMETROSCALLBACK, new HashMap<NombreParametro,Object>());
		return FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion(TipoSolicitud.CALLBACK, parametros);
	}
	
	private ArrayList<String> generarPosiblesOpciones(TipoCallBack tipoCallBack){
		ArrayList<String> posiblesOpciones = new ArrayList<String>();
		switch(tipoCallBack) {
		case SOLICITUDINFORMACIONCONTACTO:
			posiblesOpciones.add("Contactar con la UNED");
			posiblesOpciones.add("Localización");
			posiblesOpciones.add("Quejas y sugerencias");
			posiblesOpciones.add("Mi consulta no estaba relacionada con eso");
			break;
		case SOLICITUDINFORMACIONCUID:
			posiblesOpciones.add("Cursos de idiomas ofrecidos");
			posiblesOpciones.add("Cursos modalidad semipresencial");
			posiblesOpciones.add("Cursos modalidad en línea");
			posiblesOpciones.add("Matrícula, precios y plazos");
			posiblesOpciones.add("Red de Centros asociados");
			posiblesOpciones.add("Reconocimientos");
			posiblesOpciones.add("Certificados");
			posiblesOpciones.add("Metodología");
			posiblesOpciones.add("Evaluación");
			posiblesOpciones.add("FAQ");
			posiblesOpciones.add("Contacto");
			posiblesOpciones.add("Mi consulta no estaba relacionada con eso");
			break;
		default:
			throw new IllegalArgumentException("El tipo de CallBack no encaja con ninguna de las opciones codificadas en el método generarPosiblesOpciones.");
		}
		return posiblesOpciones;
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
	
	private int contarNumeroAsignaturasBorrosas(ArrayList<Estudio> estudios) {
		int numeroDeAsignaturasBorrosas = 0;
		for(Estudio estudio: estudios) {
			if (estudio instanceof AsignaturaBorrosa) {
				numeroDeAsignaturasBorrosas++;
			}
		}
		return numeroDeAsignaturasBorrosas;
	}
}
