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
 * @author Alberto Martínez Montenegro
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
		Titulacion titulacion = new Titulacion(6103,"GRADO EN QUÍMICA","GRADO");
		HashMap<NombreParametro,Object> parametros = new HashMap<NombreParametro,Object>();
		parametros.put(NombreParametro.TITULACION, titulacion);
		SolicitudInformacion solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion(TipoSolicitud.PRECIOSTITULACION, parametros);
		String respuesta = solicitudInformacion.generarCadenaRespuesta("\n");
		assertTrue(respuesta.equals("El precio de las asignaturas de la titulación GRADO EN QUÍMICA es de:\n" + 
				"-20,48€ por cada crédito ECTS la primera vez que el alumno se matricula de la asignatura.\n" + 
				"-30,60€ por cada crédito ECTS la segunda vez que el alumno se matricula de la asignatura.\n" + 
				"-67,32€ por cada crédito ECTS la tercera vez que el alumno se matricula de la asignatura.\n" + 
				"-92,82€ por cada crédito ECTS la cuarta y las sucesivas veces que el alumno se matricule de la asignatura.\n"));
	}
	
	@Test
	public void testMensajeConversacionSobreMatriculadosTitulacion(){
		Titulacion titulacion = new Titulacion(7102,"GRADO EN INGENIERÍA EN TECNOLOGÍAS DE LA INFORMACIÓN","GRADO");
		HashMap<NombreParametro,Object> parametros = new HashMap<NombreParametro,Object>();
		parametros.put(NombreParametro.TITULACION, titulacion);
		SolicitudInformacion solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion(TipoSolicitud.MATRICULADOSTITULACION, parametros);
		String respuesta = solicitudInformacion.generarCadenaRespuesta("\n");
		assertTrue(respuesta.equals("El número de matriculados en la titulación GRADO EN INGENIERÍA EN TECNOLOGÍAS DE LA INFORMACIÓN en los últimos años son los siguientes:\n" + 
				"-Curso 2019 - 2020: 958 matriculados.\n" + 
				"-Curso 2018 - 2019: 1009 matriculados.\n" + 
				"-Curso 2017 - 2018: 971 matriculados.\n" + 
				"-Curso 2016 - 2017: 1073 matriculados.\n" + 
				"-Curso 2015 - 2016: 1165 matriculados.\n"));
	}
	
	@Test
	public void testMensajeConversacionSobreMatriculadosAsignatura(){
		Titulacion titulacion = new Titulacion(7101,"GRADO EN INGENIERÍA INFORMÁTICA","GRADO");
		Asignatura asignatura = new Asignatura("7101102-","FUNDAMENTOS MATEMÁTICOS DE LA INFORMÁTICA",6);
		HashMap<NombreParametro,Object> parametros = new HashMap<NombreParametro,Object>();
		parametros.put(NombreParametro.TITULACION, titulacion);
		parametros.put(NombreParametro.ASIGNATURA, asignatura);
		SolicitudInformacion solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion(TipoSolicitud.MATRICULADOSASIGNATURA, parametros);
		String respuesta = solicitudInformacion.generarCadenaRespuesta("\n");
		assertTrue(respuesta.equals("El número de matriculados en la asignatura FUNDAMENTOS MATEMÁTICOS DE LA INFORMÁTICA de la titulación GRADO EN INGENIERÍA INFORMÁTICA en los últimos años son los siguientes:\n" + 
				"-Curso 2019 - 2020: 872 matriculados.\n" + 
				"-Curso 2018 - 2019: 799 matriculados.\n" + 
				"-Curso 2017 - 2018: 754 matriculados.\n" + 
				"-Curso 2016 - 2017: 765 matriculados.\n" + 
				"-Curso 2015 - 2016: 846 matriculados.\n"));
	}
	
	@Test
	public void testMensajeConversacionSobreValoracionEstudiantilTitulacion(){
		Titulacion titulacion = new Titulacion(7101,"GRADO EN INGENIERÍA INFORMÁTICA","GRADO");
		HashMap<NombreParametro,Object> parametros = new HashMap<NombreParametro,Object>();
		parametros.put(NombreParametro.TITULACION, titulacion);
		parametros.put(NombreParametro.PARAMETROESTADISTICOTITULACION, new ParametroEstadistico(TipoEstudio.TITULACION, "VALORACION_ESTUDIANTIL", "VALORACION_ESTUDIANTIL", new Stack<String>()));
		SolicitudInformacion solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion(TipoSolicitud.ESTADISTICARENDIMIENTOTITULACION, parametros);
		String respuesta = solicitudInformacion.generarCadenaRespuesta("\n");
		assertTrue(respuesta.equals("Las estadísticas de VALORACION_ESTUDIANTIL para la titulación GRADO EN INGENIERÍA INFORMÁTICA en los últimos años son las siguientes:\n" + 
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
		assertTrue(respuesta.equals("Durante el último curso académico registrado (2018 - 2019), estos fueron los estudios de GRADO que obtuvieron menores resultados en cuanto a VALORACION_ESTUDIANTIL :\n" + 
				"-La titulación GRADO EN INGENIERÍA EN TECNOLOGÍAS INDUSTRIALES obtuvo unos resultados de 58,45.\n" + 
				"-La titulación GRADO EN INGENIERÍA MECÁNICA obtuvo unos resultados de 59,53.\n" + 
				"-La titulación GRADO EN ING. EN  ELECTRÓNICA INDUSTRIAL Y AUTOMÁTICA obtuvo unos resultados de 59,94.\n" + 
				"-La titulación GRADO EN INGENIERÍA ELÉCTRICA obtuvo unos resultados de 60,74.\n" + 
				"-La titulación GRADO EN FÍSICA obtuvo unos resultados de 64,51.\n" + 
				"-La titulación GRADO EN MATEMÁTICAS obtuvo unos resultados de 64,92.\n" + 
				"-La titulación GRADO EN INGENIERÍA EN TECNOLOGÍAS DE LA INFORMACIÓN obtuvo unos resultados de 65,15.\n" + 
				"-La titulación GRADO EN QUÍMICA obtuvo unos resultados de 65,59.\n" + 
				"-La titulación GRADO EN CIENCIA POLÍTICA Y DE LA ADMINISTRACIÓN obtuvo unos resultados de 65,62.\n" + 
				"-La titulación GRADO EN TRABAJO SOCIAL obtuvo unos resultados de 65,89.\n"));
	}
	
	@Test
	public void testMensajeConversacionSobreEstadisticaRendimientoTitulacion(){
		Titulacion titulacion = new Titulacion(7101,"GRADO EN INGENIERÍA INFORMÁTICA","GRADO");
		HashMap<NombreParametro,Object> parametros = new HashMap<NombreParametro,Object>();
		parametros.put(NombreParametro.TITULACION, titulacion);
		parametros.put(NombreParametro.PARAMETROESTADISTICOTITULACION, new ParametroEstadistico(TipoEstudio.TITULACION, "TASA_EXITOS", "TASA_EXITOS", new Stack<String>()));
		SolicitudInformacion solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion(TipoSolicitud.ESTADISTICARENDIMIENTOTITULACION, parametros);
		String respuesta = solicitudInformacion.generarCadenaRespuesta("\n");
		assertTrue(respuesta.equals("Las estadísticas de TASA_EXITOS para la titulación GRADO EN INGENIERÍA INFORMÁTICA en los últimos años son las siguientes:\n" + 
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
		assertTrue(respuesta.equals("Durante el último curso académico registrado (2018 - 2019), estos fueron los estudios de GRADO que obtuvieron mayores resultados en cuanto a TASA_EXITOS :\n" + 
				"-La titulación GRADO EN FILOSOFÍA obtuvo unos resultados de 92,84.\n" + 
				"-La titulación GRADO EN ANTROPOLOGÍA SOCIAL Y CULTURAL obtuvo unos resultados de 92,19.\n" + 
				"-La titulación GRADO EN HISTORIA DEL ARTE obtuvo unos resultados de 92,08.\n" + 
				"-La titulación GRADO EN LENGUA Y LITERATURA ESPAÑOLAS obtuvo unos resultados de 91,11.\n" + 
				"-La titulación GRADO EN GEOGRAFÍA E HISTORIA obtuvo unos resultados de 90,56.\n" + 
				"-La titulación GRADO EN CC. JURÍDICAS DE LAS ADMINISTRACIONES PÚBLICAS obtuvo unos resultados de 90,33.\n" + 
				"-La titulación GRADO EN ESTUDIOS INGLESES: LENGUA, LITERATURA Y CULTURA obtuvo unos resultados de 89,39.\n" + 
				"-La titulación GRADO EN EDUCACIÓN SOCIAL obtuvo unos resultados de 89,37.\n" + 
				"-La titulación GRADO EN CIENCIA POLÍTICA Y DE LA ADMINISTRACIÓN obtuvo unos resultados de 88,76.\n" + 
				"-La titulación GRADO EN PEDAGOGÍA obtuvo unos resultados de 88,67.\n"));
	}
	
	@Test
	public void testMensajeConversacionSobreEstadisticaRendimientoAsignatura(){
		Titulacion titulacion = new Titulacion(6201,"GRADO EN PSICOLOGÍA","GRADO");
		Asignatura asignatura = new Asignatura("62011014","FUNDAMENTOS DE PSICOBIOLOGÍA",6);
		HashMap<NombreParametro,Object> parametros = new HashMap<NombreParametro,Object>();
		parametros.put(NombreParametro.TITULACION, titulacion);
		parametros.put(NombreParametro.ASIGNATURA, asignatura);
		parametros.put(NombreParametro.PARAMETROESTADISTICOASIGNATURA, new ParametroEstadistico(TipoEstudio.ASIGNATURA, "PORCENTAJE_TASA_SOBRESALIENTES", "PORCENTAJE_TASA_SOBRESALIENTES", new Stack<String>()));
		SolicitudInformacion solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion(TipoSolicitud.ESTADISTICARENDIMIENTOASIGNATURA, parametros);
		String respuesta = solicitudInformacion.generarCadenaRespuesta("\n");
		assertTrue(respuesta.equals("Las estadísticas de PORCENTAJE_TASA_SOBRESALIENTES para la asignatura FUNDAMENTOS DE PSICOBIOLOGÍA de la titulación GRADO EN PSICOLOGÍA en los últimos años son las siguientes:\n" + 
				"-Curso 2018-2019: 0,36.\n" + 
				"-Curso 2017-2018: 0,69.\n" + 
				"-Curso 2016-2017: 0,74.\n" + 
				"-Curso 2015-2016: 0,85.\n"));
	}
	
	@Test
	public void testMensajeConversacionSobreEstadisticaRendimientoTopAsignatura(){
		Titulacion titulacion = new Titulacion(7101,"GRADO EN INGENIERÍA INFORMÁTICA","GRADO");
		HashMap<NombreParametro,Object> parametros = new HashMap<NombreParametro,Object>();
		parametros.put(NombreParametro.TITULACION, titulacion);
		parametros.put(NombreParametro.PARAMETROESTADISTICOASIGNATURA, new ParametroEstadistico(TipoEstudio.ASIGNATURA, "PORCENTAJE_TASA_EXITO", "PORCENTAJE_TASA_EXITO", new Stack<String>()));
		parametros.put(NombreParametro.ORDENAMIENTO, new IndicadorOrdenamiento("menores", "MENOR", new Stack<String>()));
		parametros.put(NombreParametro.CRITERIOSCONSULTASQL, new Stack<String>());
		SolicitudInformacion solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion(TipoSolicitud.ESTADISTICARENDIMIENTOTOPASIGNATURA, parametros);
		String respuesta = solicitudInformacion.generarCadenaRespuesta("\n");
		assertTrue(respuesta.equals("Durante el último curso académico registrado (2018-2019), estas fueron las asignaturas de la titulación GRADO EN INGENIERÍA INFORMÁTICA que obtuvieron menores resultados en las estadísticas de PORCENTAJE_TASA_EXITO:\n" + 
				"-La asignatura FUNDAMENTOS DE CONTROL AUTOMÁTICO obtuvo unos resultados de 0,00.\n" + 
				"-La asignatura TRATAMIENTO DIGITAL DE SEÑALES obtuvo unos resultados de 0,00.\n" + 
				"-La asignatura FUNDAMENTOS FÍSICOS DE LA INFORMÁTICA obtuvo unos resultados de 37,34.\n" + 
				"-La asignatura REDES DE COMPUTADORES obtuvo unos resultados de 50,00.\n" + 
				"-La asignatura INGENIERÍA DE COMPUTADORES I obtuvo unos resultados de 55,95.\n" + 
				"-La asignatura INGENIERÍA DE COMPUTADORES II obtuvo unos resultados de 61,24.\n" + 
				"-La asignatura BASES DE DATOS obtuvo unos resultados de 63,20.\n" + 
				"-La asignatura PROGRAMACIÓN Y ESTRUCTURAS DE DATOS AVANZADAS obtuvo unos resultados de 66,12.\n" + 
				"-La asignatura FUNDAMENTOS DE PROGRAMACIÓN obtuvo unos resultados de 68,68.\n" + 
				"-La asignatura FUNDAMENTOS MATEMÁTICOS DE LA INFORMÁTICA obtuvo unos resultados de 70,65.\n"));
	}
	
	@Test
	public void testMensajeConversacionSobreInformacionGenerica(){
		HashMap<NombreParametro,Object> parametros = new HashMap<NombreParametro,Object>();
		parametros.put(NombreParametro.IDINFORMACIONGENERICA, "matriculaAdmisionPorInternet");
		SolicitudInformacion solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion(TipoSolicitud.INFORMACIONGENERICA,parametros);
		String respuesta = solicitudInformacion.generarCadenaRespuesta("\n");
		assertTrue(respuesta.equals("Para matricularte de cualquier estudio impartido en la UNED, puedes realizar el trámite a través de Internet siguiendo el siguiente enlace:\r\n" + 
				"https://app.uned.es/portal/admision-matricula-por-internet\n"));
	}
	
}
