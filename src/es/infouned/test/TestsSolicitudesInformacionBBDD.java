package es.infouned.test;

import static org.junit.Assert.assertTrue;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Stack;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import es.infouned.baseDeDatos.ConexionBaseDeDatos;
import es.infouned.baseDeDatos.ConexionMySQL;
import es.infouned.baseDeDatos.InstruccionSelect;
import es.infouned.estudios.Asignatura;
import es.infouned.estudios.Titulacion;
import es.infouned.principal.Configuracion;
import es.infouned.solicitudesInformacionBBDD.FactoriaDeSolicitudInformacion;
import es.infouned.solicitudesInformacionBBDD.SolicitudInformacion;


public class TestsSolicitudesInformacionBBDD {
	
	@BeforeAll
	private static void inicializarPropiedades() {
		String rutaFicheroConfiguracion = "/config.properties";
		Configuracion.establecerPropiedadesConfiguracionAPartirDeFichero(rutaFicheroConfiguracion);
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
		SolicitudInformacion solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion("matriculadosAsignatura", titulacion, asignatura);
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
		SolicitudInformacion solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion("valoracionEstudiantilTitulacion", titulacion);
		String respuesta = solicitudInformacion.generarCadenaRespuesta("\n");
		assertTrue(respuesta.equals("Las calificaciones que ha obtenido la titulación GRADO EN INGENIERÍA INFORMÁTICA en los últimos años según los cuestionarios de satisfacción de los estudiantes son las siguientes:\n" + 
				"-Curso 2018 - 2019: 66,53 puntos sobre 100.\n" + 
				"-Curso 2017 - 2018: 67,11 puntos sobre 100.\n" + 
				"-Curso 2016 - 2017: 64,82 puntos sobre 100.\n" + 
				"-Curso 2015 - 2016: 73,98 puntos sobre 100.\n"));
	}
	
	@Test
	public void testMensajeConversacionSobreTopValoracionesEstudiantiles(){
		SolicitudInformacion solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion("valoracionEstudiantilTopTitulacion", "grado", "menores");
		String respuesta = solicitudInformacion.generarCadenaRespuesta("\n");
		assertTrue(respuesta.equals("Durante el último curso académico registrado (2018 - 2019), estas fueron los estudios de GRADO que menores calificaciones obtuvieron según las encuestas de los estudiantes:\n" + 
				"-La titulación GRADO EN INGENIERÍA EN TECNOLOGÍAS INDUSTRIALES obtuvo una calificación de 58,45 sobre 100.\n" + 
				"-La titulación GRADO EN INGENIERÍA MECÁNICA obtuvo una calificación de 59,53 sobre 100.\n" + 
				"-La titulación GRADO EN ING. EN  ELECTRÓNICA INDUSTRIAL Y AUTOMÁTICA obtuvo una calificación de 59,94 sobre 100.\n" + 
				"-La titulación GRADO EN INGENIERÍA ELÉCTRICA obtuvo una calificación de 60,74 sobre 100.\n" + 
				"-La titulación GRADO EN FÍSICA obtuvo una calificación de 64,51 sobre 100.\n" + 
				"-La titulación GRADO EN MATEMÁTICAS obtuvo una calificación de 64,92 sobre 100.\n" + 
				"-La titulación GRADO EN INGENIERÍA EN TECNOLOGÍAS DE LA INFORMACIÓN obtuvo una calificación de 65,15 sobre 100.\n" + 
				"-La titulación GRADO EN QUÍMICA obtuvo una calificación de 65,59 sobre 100.\n" + 
				"-La titulación GRADO EN CIENCIA POLÍTICA Y DE LA ADMINISTRACIÓN obtuvo una calificación de 65,62 sobre 100.\n" + 
				"-La titulación GRADO EN TRABAJO SOCIAL obtuvo una calificación de 65,89 sobre 100.\n"));
	}
	
	@Test
	public void testMensajeConversacionSobreEstadisticaRendimientoTitulacion(){
		Titulacion titulacion = new Titulacion(7101,"GRADO EN INGENIERÍA INFORMÁTICA","GRADO");
		SolicitudInformacion solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion("estadisticaRendimientoTitulacion", titulacion, "TASA_EXITOS");
		String respuesta = solicitudInformacion.generarCadenaRespuesta("\n");
		assertTrue(respuesta.equals("Las estadísticas de TASA_EXITOS para la titulación GRADO EN INGENIERÍA INFORMÁTICA en los últimos años son las siguientes:\n" + 
				"-Curso 2018 - 2019: 78,19.\n" + 
				"-Curso 2017 - 2018: 80,17.\n" + 
				"-Curso 2016 - 2017: 78,16.\n" + 
				"-Curso 2015 - 2016: 78,47.\n"));
	}

	@Test
	public void testMensajeConversacionSobreTopEstadisticaRendimientoTitulacion(){
		SolicitudInformacion solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion("estadisticaRendimientoTopTitulacion", "grado", "TASA_EXITOS", "mayores");
		String respuesta = solicitudInformacion.generarCadenaRespuesta("\n");
		assertTrue(respuesta.equals("Durante el último curso académico registrado (2018 - 2019), estas fueron los estudios de GRADO que obtuvieron mayores resultados en cuanto a TASA_EXITOS :\n" + 
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
		SolicitudInformacion solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion("estadisticaRendimientoAsignatura", titulacion, asignatura, "PORCENTAJE_TASA_SOBRESALIENTES");
		String respuesta = solicitudInformacion.generarCadenaRespuesta("\n");
		assertTrue(respuesta.equals("Las estadísticas de PORCENTAJE_TASA_SOBRESALIENTES para la asignatura FUNDAMENTOS DE PSICOBIOLOGÍA de la titulación GRADO EN PSICOLOGÍA en los últimos años son las siguientes:\n" + 
				"-Curso 2018-2019: 0,36.\n" + 
				"-Curso 2017-2018: 0,69.\n" + 
				"-Curso 2016-2017: 0,74.\n" + 
				"-Curso 2015-2016: 0,85.\n"));
	}
	
	@Test
	public void testMensajeConversacionSobreTopEstadisticaRendimientoAsignatura(){
		Titulacion titulacion = new Titulacion(7101,"GRADO EN INGENIERÍA INFORMÁTICA","GRADO");
		SolicitudInformacion solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion("estadisticaRendimientoTopAsignatura", titulacion, "PORCENTAJE_TASA_EXITO", "menores", new Stack<String>());
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
		SolicitudInformacion solicitudInformacion = FactoriaDeSolicitudInformacion.obtenerSolicitudInformacion("informacionGenerica","matriculaAdmisionPorInternet");
		String respuesta = solicitudInformacion.generarCadenaRespuesta("\n");
		assertTrue(respuesta.equals("Para matricularse de cualquier estudio impartido en la UNED, puede realizar el trámite a través de Internet siguiendo el siguiente enlace:\r\n" + 
				"https://app.uned.es/portal/admision-matricula-por-internet"));
	}
	
}
