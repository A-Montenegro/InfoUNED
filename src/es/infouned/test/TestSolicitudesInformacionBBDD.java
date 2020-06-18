package es.infouned.test;

import static org.junit.Assert.assertTrue;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Stack;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import es.infouned.baseDeDatos.ConexionBaseDeDatos;
import es.infouned.baseDeDatos.ConexionMySQL;
import es.infouned.baseDeDatos.InstruccionSelect;
import es.infouned.estudios.Asignatura;
import es.infouned.estudios.Estudio.TipoEstudio;
import es.infouned.estudios.IndicadorOrdenamiento;
import es.infouned.estudios.NivelEstudios;
import es.infouned.estudios.NivelEstudios.NombreNivelEstudios;
import es.infouned.estudios.ParametroEstadistico;
import es.infouned.estudios.Titulacion;
import es.infouned.principal.Configuracion;
import es.infouned.solicitudesInformacionBBDD.FactoriaDeSolicitudInformacion;
import es.infouned.solicitudesInformacionBBDD.FactoriaDeSolicitudInformacion.NombreParametro;
import es.infouned.solicitudesInformacionBBDD.FactoriaDeSolicitudInformacion.TipoSolicitud;
import es.infouned.solicitudesInformacionBBDD.SolicitudInformacion;

/**
 * Clase de pruebas unitarias destinadas a probar el correcto funcionamento de las consultas a la base de datos.
 * @author Alberto Mart�nez Montenegro
 *  
 */
public class TestSolicitudesInformacionBBDD {
	
	@BeforeAll
	private static void inicializarPropiedades() {
		String rutaFicheroConfiguracion = "/config.properties";
		Configuracion.establecerPropiedadesConfiguracionAPartirDeFichero(rutaFicheroConfiguracion);
	}
	
	@AfterAll
	private static void eliminarConfiguracion() {
		Configuracion.eliminarTodo();
	}
	
	@Test
	public void testConsultaSQL() {
		ConexionBaseDeDatos conexionBaseDeDatos = new ConexionMySQL();
		conexionBaseDeDatos.abrirConexion();
		InstruccionSelect instruccionSelect = new InstruccionSelect(conexionBaseDeDatos);
		HashMap<String, String> sustituciones = new HashMap<String, String>();
		sustituciones.put("idTitulacionObjetivo", "6103");
		String cadenaTextoConsultaSQL = Configuracion.obtenerConsultaSQL("SolicitudPreciosTitulacionPorIdTitulacion");
    	for(Entry<String, String> entradaDelHashMap : sustituciones.entrySet()) {
    		cadenaTextoConsultaSQL = cadenaTextoConsultaSQL.replaceAll(entradaDelHashMap.getKey(), entradaDelHashMap.getValue());
    	}
		instruccionSelect.ejecutarConsulta(cadenaTextoConsultaSQL);
		ResultSet resultSet = instruccionSelect.getResultSet();
		try {
			while(resultSet.next()) {
				Float precioMatricula1 = resultSet.getFloat(1);
				Float precioMatricula2 = resultSet.getFloat(2);
				Float precioMatricula3 = resultSet.getFloat(3);
				Float precioMatricula4 = resultSet.getFloat(4);
				assertTrue(precioMatricula1 == 20.48f);
				assertTrue(precioMatricula2 == 30.6f);
				assertTrue(precioMatricula3 == 67.32f);
				assertTrue(precioMatricula4 == 92.82f);
		}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		conexionBaseDeDatos.cerrarConexion();
	}
	
	@Test
	public void SalidaPorConsolaSolicitudInformacionAleatoria(){
		HashMap<NombreParametro,Object> parametros = new HashMap<NombreParametro,Object>();
		SolicitudInformacion solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion(TipoSolicitud.ALEATORIA, parametros);
		String respuesta = solicitudInformacion.generarCadenaRespuesta("\n");
		System.out.println("\n\n\n" + respuesta);
	} 
	
	@Test
	public void testMensajeConversacionSobrePrecios(){
		Titulacion titulacion = new Titulacion(6103,"GRADO EN QU�MICA","GRADO");
		HashMap<NombreParametro,Object> parametros = new HashMap<NombreParametro,Object>();
		parametros.put(NombreParametro.TITULACION, titulacion);
		SolicitudInformacion solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion(TipoSolicitud.PRECIOSTITULACION, parametros);
		String respuesta = solicitudInformacion.generarCadenaRespuesta("\n");
		assertTrue(respuesta.equals("El precio de las asignaturas de la titulaci�n GRADO EN QU�MICA es de:\n" + 
				"-20,48� por cada cr�dito ECTS la primera vez que el alumno se matricula de la asignatura.\n" + 
				"-30,60� por cada cr�dito ECTS la segunda vez que el alumno se matricula de la asignatura.\n" + 
				"-67,32� por cada cr�dito ECTS la tercera vez que el alumno se matricula de la asignatura.\n" + 
				"-92,82� por cada cr�dito ECTS la cuarta y las sucesivas veces que el alumno se matricule de la asignatura.\n"));
	}
	
	@Test
	public void testMensajeConversacionSobreMatriculadosTitulacion(){
		Titulacion titulacion = new Titulacion(7102,"GRADO EN INGENIER�A EN TECNOLOG�AS DE LA INFORMACI�N","GRADO");
		HashMap<NombreParametro,Object> parametros = new HashMap<NombreParametro,Object>();
		parametros.put(NombreParametro.TITULACION, titulacion);
		SolicitudInformacion solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion(TipoSolicitud.MATRICULADOSTITULACION, parametros);
		String respuesta = solicitudInformacion.generarCadenaRespuesta("\n");
		assertTrue(respuesta.equals("El n�mero de matriculados en la titulaci�n GRADO EN INGENIER�A EN TECNOLOG�AS DE LA INFORMACI�N en los �ltimos a�os son los siguientes:\n" + 
				"-Curso 2019 - 2020: 958 matriculados.\n" + 
				"-Curso 2018 - 2019: 1009 matriculados.\n" + 
				"-Curso 2017 - 2018: 971 matriculados.\n" + 
				"-Curso 2016 - 2017: 1073 matriculados.\n" + 
				"-Curso 2015 - 2016: 1165 matriculados.\n"));
	}
	
	@Test
	public void testMensajeConversacionSobreMatriculadosAsignatura(){
		Titulacion titulacion = new Titulacion(7101,"GRADO EN INGENIER�A INFORM�TICA","GRADO");
		Asignatura asignatura = new Asignatura("7101102-","FUNDAMENTOS MATEM�TICOS DE LA INFORM�TICA",6);
		HashMap<NombreParametro,Object> parametros = new HashMap<NombreParametro,Object>();
		parametros.put(NombreParametro.TITULACION, titulacion);
		parametros.put(NombreParametro.ASIGNATURA, asignatura);
		SolicitudInformacion solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion(TipoSolicitud.MATRICULADOSASIGNATURA, parametros);
		String respuesta = solicitudInformacion.generarCadenaRespuesta("\n");
		assertTrue(respuesta.equals("El n�mero de matriculados en la asignatura FUNDAMENTOS MATEM�TICOS DE LA INFORM�TICA de la titulaci�n GRADO EN INGENIER�A INFORM�TICA en los �ltimos a�os son los siguientes:\n" + 
				"-Curso 2019 - 2020: 872 matriculados.\n" + 
				"-Curso 2018 - 2019: 799 matriculados.\n" + 
				"-Curso 2017 - 2018: 754 matriculados.\n" + 
				"-Curso 2016 - 2017: 765 matriculados.\n" + 
				"-Curso 2015 - 2016: 846 matriculados.\n"));
	}
	
	@Test
	public void testMensajeConversacionSobreValoracionEstudiantilTitulacion(){
		Titulacion titulacion = new Titulacion(7101,"GRADO EN INGENIER�A INFORM�TICA","GRADO");
		HashMap<NombreParametro,Object> parametros = new HashMap<NombreParametro,Object>();
		parametros.put(NombreParametro.TITULACION, titulacion);
		parametros.put(NombreParametro.PARAMETROESTADISTICOTITULACION, new ParametroEstadistico(TipoEstudio.TITULACION, "VALORACION_ESTUDIANTIL", "VALORACION_ESTUDIANTIL", new Stack<String>()));
		SolicitudInformacion solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion(TipoSolicitud.ESTADISTICARENDIMIENTOTITULACION, parametros);
		String respuesta = solicitudInformacion.generarCadenaRespuesta("\n");
		assertTrue(respuesta.equals("Las estad�sticas de VALORACION_ESTUDIANTIL para la titulaci�n GRADO EN INGENIER�A INFORM�TICA en los �ltimos a�os son las siguientes:\n" + 
				"-Curso 2018 - 2019: 66,53.\n" + 
				"-Curso 2017 - 2018: 67,11.\n" + 
				"-Curso 2016 - 2017: 64,82.\n" + 
				"-Curso 2015 - 2016: 73,98.\n"));
	}
	
	@Test
	public void testMensajeConversacionSobreTopValoracionesEstudiantiles(){
		HashMap<NombreParametro,Object> parametros = new HashMap<NombreParametro,Object>();
		parametros.put(NombreParametro.PARAMETROESTADISTICOTITULACION, new ParametroEstadistico(TipoEstudio.TITULACION, "VALORACION_ESTUDIANTIL", "VALORACION_ESTUDIANTIL", new Stack<String>()));
		parametros.put(NombreParametro.NIVELESTUDIOS, new NivelEstudios(NombreNivelEstudios.GRADO, new Stack<String>()));
		parametros.put(NombreParametro.ORDENAMIENTO, new IndicadorOrdenamiento("menores", "MENOR", new Stack<String>()));
		SolicitudInformacion solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion(TipoSolicitud.ESTADISTICARENDIMIENTOTOPTITULACION, parametros);
		String respuesta = solicitudInformacion.generarCadenaRespuesta("\n");
		assertTrue(respuesta.equals("Durante el �ltimo curso acad�mico registrado (2018 - 2019), estos fueron los estudios de GRADO que obtuvieron menores resultados en cuanto a VALORACION_ESTUDIANTIL :\n" + 
				"-La titulaci�n GRADO EN INGENIER�A EN TECNOLOG�AS INDUSTRIALES obtuvo unos resultados de 58,45.\n" + 
				"-La titulaci�n GRADO EN INGENIER�A MEC�NICA obtuvo unos resultados de 59,53.\n" + 
				"-La titulaci�n GRADO EN ING. EN  ELECTR�NICA INDUSTRIAL Y AUTOM�TICA obtuvo unos resultados de 59,94.\n" + 
				"-La titulaci�n GRADO EN INGENIER�A EL�CTRICA obtuvo unos resultados de 60,74.\n" + 
				"-La titulaci�n GRADO EN F�SICA obtuvo unos resultados de 64,51.\n" + 
				"-La titulaci�n GRADO EN MATEM�TICAS obtuvo unos resultados de 64,92.\n" + 
				"-La titulaci�n GRADO EN INGENIER�A EN TECNOLOG�AS DE LA INFORMACI�N obtuvo unos resultados de 65,15.\n" + 
				"-La titulaci�n GRADO EN QU�MICA obtuvo unos resultados de 65,59.\n" + 
				"-La titulaci�n GRADO EN CIENCIA POL�TICA Y DE LA ADMINISTRACI�N obtuvo unos resultados de 65,62.\n" + 
				"-La titulaci�n GRADO EN TRABAJO SOCIAL obtuvo unos resultados de 65,89.\n"));
	}
	
	@Test
	public void testMensajeConversacionSobreEstadisticaRendimientoTitulacion(){
		Titulacion titulacion = new Titulacion(7101,"GRADO EN INGENIER�A INFORM�TICA","GRADO");
		HashMap<NombreParametro,Object> parametros = new HashMap<NombreParametro,Object>();
		parametros.put(NombreParametro.TITULACION, titulacion);
		parametros.put(NombreParametro.PARAMETROESTADISTICOTITULACION, new ParametroEstadistico(TipoEstudio.TITULACION, "TASA_EXITOS", "TASA_EXITOS", new Stack<String>()));
		SolicitudInformacion solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion(TipoSolicitud.ESTADISTICARENDIMIENTOTITULACION, parametros);
		String respuesta = solicitudInformacion.generarCadenaRespuesta("\n");
		assertTrue(respuesta.equals("Las estad�sticas de TASA_EXITOS para la titulaci�n GRADO EN INGENIER�A INFORM�TICA en los �ltimos a�os son las siguientes:\n" + 
				"-Curso 2018 - 2019: 78,19.\n" + 
				"-Curso 2017 - 2018: 80,17.\n" + 
				"-Curso 2016 - 2017: 78,16.\n" + 
				"-Curso 2015 - 2016: 78,47.\n"));
	}

	@Test
	public void testMensajeConversacionSobreTopEstadisticaRendimientoTitulacion(){
		HashMap<NombreParametro,Object> parametros = new HashMap<NombreParametro,Object>();
		parametros.put(NombreParametro.NIVELESTUDIOS, new NivelEstudios(NombreNivelEstudios.GRADO, new Stack<String>()));
		parametros.put(NombreParametro.PARAMETROESTADISTICOTITULACION, new ParametroEstadistico(TipoEstudio.TITULACION, "TASA_EXITOS", "TASA_EXITOS", new Stack<String>()));
		parametros.put(NombreParametro.ORDENAMIENTO, new IndicadorOrdenamiento("mayores", "MAYOR", new Stack<String>()));
		SolicitudInformacion solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion(TipoSolicitud.ESTADISTICARENDIMIENTOTOPTITULACION, parametros);
		String respuesta = solicitudInformacion.generarCadenaRespuesta("\n");
		assertTrue(respuesta.equals("Durante el �ltimo curso acad�mico registrado (2018 - 2019), estos fueron los estudios de GRADO que obtuvieron mayores resultados en cuanto a TASA_EXITOS :\n" + 
				"-La titulaci�n GRADO EN FILOSOF�A obtuvo unos resultados de 92,84.\n" + 
				"-La titulaci�n GRADO EN ANTROPOLOG�A SOCIAL Y CULTURAL obtuvo unos resultados de 92,19.\n" + 
				"-La titulaci�n GRADO EN HISTORIA DEL ARTE obtuvo unos resultados de 92,08.\n" + 
				"-La titulaci�n GRADO EN LENGUA Y LITERATURA ESPA�OLAS obtuvo unos resultados de 91,11.\n" + 
				"-La titulaci�n GRADO EN GEOGRAF�A E HISTORIA obtuvo unos resultados de 90,56.\n" + 
				"-La titulaci�n GRADO EN CC. JUR�DICAS DE LAS ADMINISTRACIONES P�BLICAS obtuvo unos resultados de 90,33.\n" + 
				"-La titulaci�n GRADO EN ESTUDIOS INGLESES: LENGUA, LITERATURA Y CULTURA obtuvo unos resultados de 89,39.\n" + 
				"-La titulaci�n GRADO EN EDUCACI�N SOCIAL obtuvo unos resultados de 89,37.\n" + 
				"-La titulaci�n GRADO EN CIENCIA POL�TICA Y DE LA ADMINISTRACI�N obtuvo unos resultados de 88,76.\n" + 
				"-La titulaci�n GRADO EN PEDAGOG�A obtuvo unos resultados de 88,67.\n"));
	}
	
	@Test
	public void testMensajeConversacionSobreEstadisticaRendimientoAsignatura(){
		Titulacion titulacion = new Titulacion(6201,"GRADO EN PSICOLOG�A","GRADO");
		Asignatura asignatura = new Asignatura("62011014","FUNDAMENTOS DE PSICOBIOLOG�A",6);
		HashMap<NombreParametro,Object> parametros = new HashMap<NombreParametro,Object>();
		parametros.put(NombreParametro.TITULACION, titulacion);
		parametros.put(NombreParametro.ASIGNATURA, asignatura);
		parametros.put(NombreParametro.PARAMETROESTADISTICOASIGNATURA, new ParametroEstadistico(TipoEstudio.ASIGNATURA, "PORCENTAJE_TASA_SOBRESALIENTES", "PORCENTAJE_TASA_SOBRESALIENTES", new Stack<String>()));
		SolicitudInformacion solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion(TipoSolicitud.ESTADISTICARENDIMIENTOASIGNATURA, parametros);
		String respuesta = solicitudInformacion.generarCadenaRespuesta("\n");
		assertTrue(respuesta.equals("Las estad�sticas de PORCENTAJE_TASA_SOBRESALIENTES para la asignatura FUNDAMENTOS DE PSICOBIOLOG�A de la titulaci�n GRADO EN PSICOLOG�A en los �ltimos a�os son las siguientes:\n" + 
				"-Curso 2018-2019: 0,36.\n" + 
				"-Curso 2017-2018: 0,69.\n" + 
				"-Curso 2016-2017: 0,74.\n" + 
				"-Curso 2015-2016: 0,85.\n"));
	}
	
	@Test
	public void testMensajeConversacionSobreEstadisticaRendimientoTopAsignatura(){
		Titulacion titulacion = new Titulacion(7101,"GRADO EN INGENIER�A INFORM�TICA","GRADO");
		HashMap<NombreParametro,Object> parametros = new HashMap<NombreParametro,Object>();
		parametros.put(NombreParametro.TITULACION, titulacion);
		parametros.put(NombreParametro.PARAMETROESTADISTICOASIGNATURA, new ParametroEstadistico(TipoEstudio.ASIGNATURA, "PORCENTAJE_TASA_EXITO", "PORCENTAJE_TASA_EXITO", new Stack<String>()));
		parametros.put(NombreParametro.ORDENAMIENTO, new IndicadorOrdenamiento("menores", "MENOR", new Stack<String>()));
		parametros.put(NombreParametro.CRITERIOSCONSULTASQL, new Stack<String>());
		SolicitudInformacion solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion(TipoSolicitud.ESTADISTICARENDIMIENTOTOPASIGNATURA, parametros);
		String respuesta = solicitudInformacion.generarCadenaRespuesta("\n");
		assertTrue(respuesta.equals("Durante el �ltimo curso acad�mico registrado (2018-2019), estas fueron las asignaturas de la titulaci�n GRADO EN INGENIER�A INFORM�TICA que obtuvieron menores resultados en las estad�sticas de PORCENTAJE_TASA_EXITO:\n" + 
				"-La asignatura FUNDAMENTOS DE CONTROL AUTOM�TICO obtuvo unos resultados de 0,00.\n" + 
				"-La asignatura TRATAMIENTO DIGITAL DE SE�ALES obtuvo unos resultados de 0,00.\n" + 
				"-La asignatura FUNDAMENTOS F�SICOS DE LA INFORM�TICA obtuvo unos resultados de 37,34.\n" + 
				"-La asignatura REDES DE COMPUTADORES obtuvo unos resultados de 50,00.\n" + 
				"-La asignatura INGENIER�A DE COMPUTADORES I obtuvo unos resultados de 55,95.\n" + 
				"-La asignatura INGENIER�A DE COMPUTADORES II obtuvo unos resultados de 61,24.\n" + 
				"-La asignatura BASES DE DATOS obtuvo unos resultados de 63,20.\n" + 
				"-La asignatura PROGRAMACI�N Y ESTRUCTURAS DE DATOS AVANZADAS obtuvo unos resultados de 66,12.\n" + 
				"-La asignatura FUNDAMENTOS DE PROGRAMACI�N obtuvo unos resultados de 68,68.\n" + 
				"-La asignatura FUNDAMENTOS MATEM�TICOS DE LA INFORM�TICA obtuvo unos resultados de 70,65.\n"));
	}
	
	@Test
	public void testMensajeConversacionSobreInformacionGenerica(){
		HashMap<NombreParametro,Object> parametros = new HashMap<NombreParametro,Object>();
		parametros.put(NombreParametro.IDINFORMACIONGENERICA, "matriculaAdmisionPorInternet");
		SolicitudInformacion solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion(TipoSolicitud.INFORMACIONGENERICA,parametros);
		String respuesta = solicitudInformacion.generarCadenaRespuesta("\n");
		assertTrue(respuesta.equals("Para matricularte de cualquier estudio impartido en la UNED, puedes realizar el tr�mite a trav�s de Internet siguiendo el siguiente enlace:\r\n" + 
				"https://app.uned.es/portal/admision-matricula-por-internet\n"));
	}
	
}
