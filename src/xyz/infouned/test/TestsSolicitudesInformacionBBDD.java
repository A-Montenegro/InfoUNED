package xyz.infouned.test;

import static org.junit.Assert.assertTrue;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Stack;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import xyz.infouned.baseDeDatos.ConexionBaseDeDatos;
import xyz.infouned.baseDeDatos.ConexionMySQL;
import xyz.infouned.baseDeDatos.InstruccionSelect;
import xyz.infouned.configuracion.PropiedadesConfiguracion;
import xyz.infouned.configuracion.PropiedadesListaDeConsultasSQL;
import xyz.infouned.conversacion.Conversacion;
import xyz.infouned.estudios.Asignatura;
import xyz.infouned.estudios.Titulacion;
import xyz.infouned.solicitudesInformacionBBDD.FactoriaDeSolicitudInformacion;
import xyz.infouned.solicitudesInformacionBBDD.SolicitudInformacion;


public class TestsSolicitudesInformacionBBDD {
	
	@BeforeAll
	private static void inicializarPropiedades() {
		String rutaFicheroConfiguracion = "/config.properties";
		PropiedadesConfiguracion.establecerPropiedadesConfiguracionAPartirDeFichero(rutaFicheroConfiguracion);
		String rutaFicheroConsultasSQL = "/consultasSQL.properties";
	    PropiedadesListaDeConsultasSQL.establecerPropiedadesDeListaDeConsultasSQLAPartirDeFichero(rutaFicheroConsultasSQL);
	}
	
	@Test
	public void testConsultaSQL() {
		ConexionBaseDeDatos conexionBaseDeDatos = new ConexionMySQL();
		conexionBaseDeDatos.abrirConexion();
		InstruccionSelect instruccionSelect = new InstruccionSelect(conexionBaseDeDatos);
		HashMap<String, String> sustituciones = new HashMap<String, String>();
		sustituciones.put("idTitulacionObjetivo", "6103");
		String cadenaTextoConsultaSQL = PropiedadesListaDeConsultasSQL.obtenerConsultaSQL("SolicitudPreciosTitulacionPorIdTitulacion");
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
	
	/**
	@Test
	public void SalidaPorConsolaSolicitudInformacionAleatoria(){
		SolicitudInformacion solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion("aleatoria");
		String respuesta = solicitudInformacion.generarCadenaRespuesta("\n");
		System.out.println("\n\n\n" + respuesta);
	} 
	**/
	
	@Test
	public void testMensajeConversacionSobrePrecios(){
		Titulacion titulacion = new Titulacion(6103,"GRADO EN QUÍMICA","GRADO");
		SolicitudInformacion solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion("preciosTitulacion", titulacion);
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
		SolicitudInformacion solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion("matriculadosTitulacion", titulacion);
		String respuesta = solicitudInformacion.generarCadenaRespuesta("\n");
		assertTrue(respuesta.equals("El número de matriculados en la titulación GRADO EN INGENIERÍA EN TECNOLOGÍAS DE LA INFORMACIÓN en los últimos años son los siguientes:\n" + 
				"-Curso 2017 - 2018: 970 matriculados.\n" + 
				"-Curso 2016 - 2017: 1073 matriculados.\n" + 
				"-Curso 2015 - 2016: 1169 matriculados.\n"));
	}
	
	@Test
	public void testMensajeConversacionSobreMatriculadosAsignatura(){
		Titulacion titulacion = new Titulacion(7101,"GRADO EN INGENIERÍA INFORMÁTICA","GRADO");
		Asignatura asignatura = new Asignatura("7101102-","FUNDAMENTOS MATEMÁTICOS DE LA INFORMÁTICA",6);
		SolicitudInformacion solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion("matriculadosAsignatura", titulacion, asignatura);
		String respuesta = solicitudInformacion.generarCadenaRespuesta("\n");
		assertTrue(respuesta.equals("El número de matriculados en la asignatura FUNDAMENTOS MATEMÁTICOS DE LA INFORMÁTICA de la titulación GRADO EN INGENIERÍA INFORMÁTICA en los últimos años son los siguientes:\n" + 
				"-Curso 2018 - 2019: 991 matriculados.\n" + 
				"-Curso 2017 - 2018: 758 matriculados.\n" + 
				"-Curso 2016 - 2017: 766 matriculados.\n" + 
				"-Curso 2015 - 2016: 847 matriculados.\n" + 
				"-Curso 2014 - 2015: 829 matriculados.\n"));
	}
	
	@Test
	public void testMensajeConversacionSobreValoracionEstudiantilTitulacion(){
		Titulacion titulacion = new Titulacion(7101,"GRADO EN INGENIERÍA INFORMÁTICA","GRADO");
		SolicitudInformacion solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion("valoracionEstudiantilTitulacion", titulacion);
		String respuesta = solicitudInformacion.generarCadenaRespuesta("\n");
		assertTrue(respuesta.equals("Las calificaciones que ha obtenido la titulación GRADO EN INGENIERÍA INFORMÁTICA en los últimos años según los cuestionarios de satisfacción de los estudiantes son las siguientes:\n" + 
				"-Curso 2017 - 2018: 67,11 puntos sobre 100.\n" + 
				"-Curso 2016 - 2017: 64,82 puntos sobre 100.\n" + 
				"-Curso 2015 - 2016: 73,98 puntos sobre 100.\n"));
	}
	
	@Test
	public void testMensajeConversacionSobreTopValoracionesEstudiantiles(){
		SolicitudInformacion solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion("valoracionEstudiantilTopTitulacion", "grado", "menores");
		String respuesta = solicitudInformacion.generarCadenaRespuesta("\n");
		assertTrue(respuesta.equals("Durante el último curso académico registrado (2017 - 2018), estas fueron los estudios de GRADO que menores calificaciones obtuvieron según las encuestas de los estudiantes:\n" + 
				"-La titulación GRADO EN INGENIERÍA EN TECNOLOGÍAS INDUSTRIALES obtuvo una calificación de 56,25 sobre 100.\n" + 
				"-La titulación GRADO EN INGENIERÍA MECÁNICA obtuvo una calificación de 62,33 sobre 100.\n" + 
				"-La titulación GRADO EN CRIMINOLOGÍA obtuvo una calificación de 62,43 sobre 100.\n" + 
				"-La titulación GRADO EN FÍSICA obtuvo una calificación de 64,31 sobre 100.\n" + 
				"-La titulación GRADO EN CIENCIA POLÍTICA Y DE LA ADMINISTRACIÓN obtuvo una calificación de 64,31 sobre 100.\n" + 
				"-La titulación GRADO EN ING. EN  ELECTRÓNICA INDUSTRIAL Y AUTOMÁTICA obtuvo una calificación de 64,75 sobre 100.\n" + 
				"-La titulación GRADO EN ADMINISTRACIÓN Y DIRECCIÓN DE EMPRESAS obtuvo una calificación de 65,34 sobre 100.\n" + 
				"-La titulación GRADO EN SOCIOLOGÍA obtuvo una calificación de 66,02 sobre 100.\n" + 
				"-La titulación GRADO EN CIENCIAS AMBIENTALES obtuvo una calificación de 66,20 sobre 100.\n" + 
				"-La titulación GRADO EN MATEMÁTICAS obtuvo una calificación de 66,30 sobre 100.\n"));
	}
	
	@Test
	public void testMensajeConversacionSobreEstadisticaRendimientoTitulacion(){
		Titulacion titulacion = new Titulacion(7101,"GRADO EN INGENIERÍA INFORMÁTICA","GRADO");
		SolicitudInformacion solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion("estadisticaRendimientoTitulacion", titulacion, "TASA_EXITOS");
		String respuesta = solicitudInformacion.generarCadenaRespuesta("\n");
		assertTrue(respuesta.equals("Las estadísticas de TASA_EXITOS para la titulación GRADO EN INGENIERÍA INFORMÁTICA en los últimos años son las siguientes:\n" + 
				"-Curso 2017 - 2018: 80,17.\n" + 
				"-Curso 2016 - 2017: 78,16.\n" + 
				"-Curso 2015 - 2016: 78,47.\n"));
	}

	@Test
	public void testMensajeConversacionSobreTopEstadisticaRendimientoTitulacion(){
		SolicitudInformacion solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion("estadisticaRendimientoTopTitulacion", "grado", "TASA_EXITOS", "mayores");
		String respuesta = solicitudInformacion.generarCadenaRespuesta("\n");
		assertTrue(respuesta.equals("Durante el último curso académico registrado (2017 - 2018), estas fueron los estudios de GRADO que obtuvieron mayores resultados en cuanto a TASA_EXITOS :\n" + 
				"-La titulación GRADO EN FILOSOFÍA obtuvo unos resultados de 92,00.\n" + 
				"-La titulación GRADO EN HISTORIA DEL ARTE obtuvo unos resultados de 91,88.\n" + 
				"-La titulación GRADO EN ANTROPOLOGÍA SOCIAL Y CULTURAL obtuvo unos resultados de 90,15.\n" + 
				"-La titulación GRADO EN GEOGRAFÍA E HISTORIA obtuvo unos resultados de 90,15.\n" + 
				"-La titulación GRADO EN ESTUDIOS INGLESES: LENGUA, LITERATURA Y CULTURA obtuvo unos resultados de 90,12.\n" + 
				"-La titulación GRADO EN SOCIOLOGÍA obtuvo unos resultados de 89,71.\n" + 
				"-La titulación GRADO EN CC. JURÍDICAS DE LAS ADMINISTRACIONES PÚBLICAS obtuvo unos resultados de 89,65.\n" + 
				"-La titulación GRADO EN PEDAGOGÍA obtuvo unos resultados de 89,62.\n" + 
				"-La titulación GRADO EN EDUCACIÓN SOCIAL obtuvo unos resultados de 89,45.\n" + 
				"-La titulación GRADO EN TRABAJO SOCIAL obtuvo unos resultados de 88,29.\n"));
	}
	
	@Test
	public void testMensajeConversacionSobreEstadisticaRendimientoAsignatura(){
		Titulacion titulacion = new Titulacion(6201,"GRADO EN PSICOLOGÍA","GRADO");
		Asignatura asignatura = new Asignatura("62011014","FUNDAMENTOS DE PSICOBIOLOGÍA",6);
		SolicitudInformacion solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion("estadisticaRendimientoAsignatura", titulacion, asignatura, "PORCENTAJE_TASA_SOBRESALIENTES");
		String respuesta = solicitudInformacion.generarCadenaRespuesta("\n");
		assertTrue(respuesta.equals("Las estadísticas de PORCENTAJE_TASA_SOBRESALIENTES para la asignatura FUNDAMENTOS DE PSICOBIOLOGÍA de la titulación GRADO EN PSICOLOGÍA en los últimos años son las siguientes:\n" + 
				"-Curso 2017-2018: 0,69.\n" + 
				"-Curso 2016-2017: 0,74.\n" + 
				"-Curso 2015-2016: 0,85.\n"));
	}
	
	@Test
	public void testMensajeConversacionSobreTopEstadisticaRendimientoAsignatura(){
		Titulacion titulacion = new Titulacion(7101,"GRADO EN INGENIERÍA INFORMÁTICA","GRADO");
		SolicitudInformacion solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion("estadisticaRendimientoTopAsignatura", titulacion, "PORCENTAJE_TASA_EXITO", "menores", new Stack<String>());
		String respuesta = solicitudInformacion.generarCadenaRespuesta("\n");
		assertTrue(respuesta.equals("Durante el último curso académico registrado (2017-2018), estas fueron las asignaturas de la titulación GRADO EN INGENIERÍA INFORMÁTICA que obtuvieron menores resultados en las estadísticas de PORCENTAJE_TASA_EXITO:\n" + 
				"-La asignatura FUNDAMENTOS FÍSICOS DE LA INFORMÁTICA obtuvo unos resultados de 38,36.\n" + 
				"-La asignatura DISEÑO DEL SOFTWARE obtuvo unos resultados de 53,24.\n" + 
				"-La asignatura FUNDAMENTOS MATEMÁTICOS DE LA INFORMÁTICA obtuvo unos resultados de 55,95.\n" + 
				"-La asignatura FUNDAMENTOS DE SISTEMAS DIGITALES obtuvo unos resultados de 59,39.\n" + 
				"-La asignatura INGENIERÍA DE COMPUTADORES I obtuvo unos resultados de 66,67.\n" + 
				"-La asignatura BASES DE DATOS obtuvo unos resultados de 71,43.\n" + 
				"-La asignatura SISTEMAS DISTRIBUIDOS obtuvo unos resultados de 74,73.\n" + 
				"-La asignatura FUNDAMENTOS DE PROGRAMACIÓN obtuvo unos resultados de 75,23.\n" + 
				"-La asignatura TEORÍA DE LOS LENGUAJES DE PROGRAMACIÓN obtuvo unos resultados de 77,27.\n" + 
				"-La asignatura FUNDAMENTOS DE INTELIGENCIA ARTIFICIAL obtuvo unos resultados de 77,30.\n"));
	}
	
	@Test
	public void testMensajesGenericosFacebook(){
		Conversacion conversacion = new Conversacion("1234","Facebook");
		System.out.println(conversacion.obtenerMensajeGenericoFacebook("Hola"));
		System.out.println(conversacion.obtenerMensajeGenericoFacebook("Quiero saber cuántos alumnos están matriculados de la asignatura Fundamentos matemáticos de la informática en el grado en ingeniería informática."));
		System.out.println(conversacion.obtenerMensajeGenericoFacebook("Ok, gracias."));
		System.out.println(conversacion.obtenerMensajeGenericoFacebook("¿cual es la asignatura más difícil del grado en derecho?"));
		
	}
	
}
