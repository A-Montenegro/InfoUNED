package es.infouned.test;


import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import es.infouned.conversacion.Conversacion;
import es.infouned.conversacion.Conversacion.OrigenConversacion;
import es.infouned.principal.Configuracion;

/**
 *  Clase de pruebas unitarias destinadas a probar el correcto funcionamento de las conversaciones.
 * @author Alberto Martínez Montenegro
 *  
 */
public class TestConversacion {

	@BeforeAll
	private static void inicializarPropiedades() {
		String rutaFicheroConfiguracion = "/config.properties";
		Configuracion.establecerPropiedadesConfiguracionAPartirDeFichero(rutaFicheroConfiguracion);
		Configuracion.iniciarProcesadorLenguajeNatural();
	}
	
	@AfterAll
	private static void eliminarConfiguracion() {
		Configuracion.eliminarTodo();
	}

	@Test
    public void testConversacionGuias(){		
    	Conversacion conversacion = new Conversacion("123456789", OrigenConversacion.TELEGRAM);
    	conversacion.procesarTextoRecibido("Necesito información general sobre el grado en psicología");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("Si desea información sobre la titulación GRADO EN PSICOLOGÍA, en la siguiente guía de estudio encontrará todos los detalles:\n" + 
    			"http://portal.uned.es/GestionGuiastitulacionesGrado/GenerarPDFGuia?c=2020&idT=6201"));
    	conversacion.procesarTextoRecibido("Necesito información general sobre el MÁSTER UNIVERSITARIO EN INGENIERÍA INFORMÁTICA");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("Si desea información sobre la titulación MÁSTER UNIVERSITARIO EN INGENIERÍA INFORMÁTICA, en la siguiente guía de estudio encontrará todos los detalles:\n" + 
    			"http://portal.uned.es/GestionGuiasTitulacionesPosgrado/GenerarPDFGuia?c=2020&idT=310601"));
    	conversacion.procesarTextoRecibido("Necesito información general sobre la asignatura estrategias de programación y estructuras de datos avanzadas");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("La asignatura ESTRATEGIAS DE PROGRAMACIÓN Y ESTRUCTURAS DE DATOS puede pertenecer a distintas titulaciones, seleccione a continuación la correcta:\n" + 
    			"__BOTON_CALLBACK__GRADO EN INGENIERÍA INFORMÁTICA__BOTON_CALLBACK__GRADO EN INGENIERÍA EN TECNOLOGÍAS DE LA INFORMACIÓN__BOTON_CALLBACK__Mi consulta no estaba relacionada con eso"));
    	conversacion.procesarTextoRecibido("GRADO EN INGENIERÍA INFORMÁTICA");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("Si desea información sobre la asignatura ESTRATEGIAS DE PROGRAMACIÓN Y ESTRUCTURAS DE DATOS de la titulación GRADO EN INGENIERÍA INFORMÁTICA, en la siguiente guía de estudio encontrará todos los detalles:\n" + 
    			"http://portal.uned.es/GuiasAsignaturasGrados/PDFGuiaPublica?idA=71901043&c=2020&idT=7101"));
    	conversacion.procesarTextoRecibido("Necesito información general de MÉTODOS MATEMÁTICOS de grado en ingeniería informática");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("Según mis datos, la asignatura que ha nombrado en su mensaje, no pertenece a la titulación GRADO EN INGENIERÍA INFORMÁTICA.\n" + 
    			"Por favor, intente expresar su consulta de otra forma, prestando especial atención al nombre de los estudios de los cuales desea información.\n"));

	}
	
	@Test
    public void testConversacionPrecios(){		
    	Conversacion conversacion = new Conversacion("123456789", OrigenConversacion.TELEGRAM);
    	conversacion.procesarTextoRecibido("¿Cuanto cuesta el grado en psicología?");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("El precio de las asignaturas de la titulación GRADO EN PSICOLOGÍA es de:\n" + 
    			"-15,95€ por cada crédito ECTS la primera vez que el alumno se matricula de la asignatura.\n" + 
    			"-22,95€ por cada crédito ECTS la segunda vez que el alumno se matricula de la asignatura.\n" + 
    			"-50,49€ por cada crédito ECTS la tercera vez que el alumno se matricula de la asignatura.\n" + 
    			"-69,62€ por cada crédito ECTS la cuarta y las sucesivas veces que el alumno se matricule de la asignatura.\n"));
    	conversacion.procesarTextoRecibido("¿Cuanto cuesta estudiar en la uned?");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("Si deseas información sobre los precios de una titulación, debes especificar claramente el nombre de la misma.\n"));
    	conversacion.procesarTextoRecibido("¿Cuanto cuestan las asignaturas del grado en geografía e historia?");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("El precio de las asignaturas de la titulación GRADO EN GEOGRAFÍA E HISTORIA es de:\n" + 
    			"-13,00€ por cada crédito ECTS la primera vez que el alumno se matricula de la asignatura.\n" + 
    			"-18,97€ por cada crédito ECTS la segunda vez que el alumno se matricula de la asignatura.\n" + 
    			"-41,74€ por cada crédito ECTS la tercera vez que el alumno se matricula de la asignatura.\n" + 
    			"-57,55€ por cada crédito ECTS la cuarta y las sucesivas veces que el alumno se matricule de la asignatura.\n"));
	}

	@Test
    public void testConversacionEstadisticaRendimiento(){		
    	Conversacion conversacion = new Conversacion("123456789", OrigenConversacion.TELEGRAM);
    	conversacion.procesarTextoRecibido("¿Cual es la nota media de grado en ingeniería informática?");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("Las estadísticas de NOTA MEDIA para la titulación GRADO EN INGENIERÍA INFORMÁTICA en los últimos años son las siguientes:\n" + 
    			"-Curso 2018 - 2019: 7,05.\n" + 
    			"-Curso 2017 - 2018: 7,03.\n" + 
    			"-Curso 2016 - 2017: 7,08.\n" + 
    			"-Curso 2015 - 2016: 7,04.\n"));
    	conversacion.procesarTextoRecibido("¿Cual es la nota media de programación orientada a objetos?");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("La asignatura PROGRAMACIÓN ORIENTADA A OBJETOS puede pertenecer a distintas titulaciones, seleccione a continuación la correcta:\n" + 
    			"__BOTON_CALLBACK__GRADO EN INGENIERÍA INFORMÁTICA__BOTON_CALLBACK__GRADO EN INGENIERÍA EN TECNOLOGÍAS DE LA INFORMACIÓN__BOTON_CALLBACK__Mi consulta no estaba relacionada con eso"));
    	conversacion.procesarTextoRecibido("GRADO EN INGENIERÍA EN TECNOLOGÍAS DE LA INFORMACIÓN");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("Las estadísticas de NOTA MEDIA para la asignatura PROGRAMACIÓN ORIENTADA A OBJETOS de la titulación GRADO EN INGENIERÍA EN TECNOLOGÍAS DE LA INFORMACIÓN en los últimos años son las siguientes:\n" + 
    			"-Curso 2018-2019: 6,60.\n" + 
    			"-Curso 2017-2018: 6,18.\n" + 
    			"-Curso 2016-2017: 6,20.\n" + 
    			"-Curso 2015-2016: 6,11.\n"));
    	conversacion.procesarTextoRecibido("¿Cual es la nota media de HISTORIA DE LA ALTA EDAD MODERNA perteneciente al GRADO EN GEOGRAFÍA E HISTORIA?");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("Las estadísticas de NOTA MEDIA para la asignatura HISTORIA DE LA ALTA EDAD MODERNA de la titulación GRADO EN GEOGRAFÍA E HISTORIA en los últimos años son las siguientes:\n" + 
    			"-Curso 2018-2019: 6,73.\n" + 
    			"-Curso 2017-2018: 6,63.\n" + 
    			"-Curso 2016-2017: 6,98.\n" + 
    			"-Curso 2015-2016: 7,08.\n"));
    	conversacion.procesarTextoRecibido("¿Está bien valorada la asignatura ética y legislación en el grado en ingeniería informática?");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("Las estadísticas de VALORACIÓN ESTUDIANTIL para la asignatura ÉTICA Y LEGISLACIÓN de la titulación GRADO EN INGENIERÍA INFORMÁTICA en los últimos años son las siguientes:\n" + 
    			"-Curso 2018-2019: 53,09.\n" + 
    			"-Curso 2017-2018: 54,89.\n" + 
    			"-Curso 2016-2017: 41,49.\n" + 
    			"-Curso 2015-2016: 50,60.\n"));
    	conversacion.procesarTextoRecibido("¿Está bien valorada la asignatura fundamentos de programación?");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("La asignatura FUNDAMENTOS DE PROGRAMACIÓN puede pertenecer a distintas titulaciones, seleccione a continuación la correcta:\n" + 
    			"__BOTON_CALLBACK__GRADO EN INGENIERÍA INFORMÁTICA__BOTON_CALLBACK__GRADO EN INGENIERÍA EN TECNOLOGÍAS DE LA INFORMACIÓN__BOTON_CALLBACK__Mi consulta no estaba relacionada con eso"));
    	conversacion.procesarTextoRecibido("GRADO EN INGENIERÍA INFORMÁTICA");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("Las estadísticas de VALORACIÓN ESTUDIANTIL para la asignatura FUNDAMENTOS DE PROGRAMACIÓN de la titulación GRADO EN INGENIERÍA INFORMÁTICA en los últimos años son las siguientes:\n" + 
    			"-Curso 2018-2019: 65,83.\n" + 
    			"-Curso 2017-2018: 68,47.\n" + 
    			"-Curso 2016-2017: 62,07.\n" + 
    			"-Curso 2015-2016: 57,88.\n"));
    	conversacion.procesarTextoRecibido("¿Los alumnos están descontentos con la titulación de grado en psicología");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("Las estadísticas de VALORACIÓN ESTUDIANTIL para la titulación GRADO EN PSICOLOGÍA en los últimos años son las siguientes:\n" + 
    			"-Curso 2018 - 2019: 69,35.\n" + 
    			"-Curso 2017 - 2018: 67,76.\n" + 
    			"-Curso 2016 - 2017: 65,73.\n" + 
    			"-Curso 2015 - 2016: 78,69.\n"));
    	conversacion.procesarTextoRecibido("Cual es la nota media de estadística");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("La asignatura nombrada puede referirse a distintas titulaciones, seleccione a continuación la correcta:\n"
    			+ "__BOTON_CALLBACK__ING. TÉCNICA EN INFORMÁTICA DE SISTEMAS (PLAN 2000)__BOTON_CALLBACK__LICENCIATURA EN CIENCIAS FÍSICAS__BOTON_CALLBACK__LICENCIATURA EN CIENCIAS AMBIENTALES__BOTON_CALLBACK__GRADO EN INGENIERÍA INFORMÁTICA__BOTON_CALLBACK__GRADO EN INGENIERÍA EN TECNOLOGÍAS DE LA INFORMACIÓN__BOTON_CALLBACK__GRADO EN INGENIERÍA ELÉCTRICA__BOTON_CALLBACK__GRADO EN ING. EN  ELECTRÓNICA INDUSTRIAL Y AUTOMÁTICA__BOTON_CALLBACK__GRADO EN INGENIERÍA MECÁNICA__BOTON_CALLBACK__GRADO EN INGENIERÍA EN TECNOLOGÍAS INDUSTRIALES__BOTON_CALLBACK__Mi consulta no estaba relacionada con eso"));
    	conversacion.procesarTextoRecibido("GRADO EN INGENIERÍA INFORMÁTICA");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("Las estadísticas de NOTA MEDIA para la asignatura ESTADÍSTICA (ING.INFORMÁTICA/ING.TI) de la titulación GRADO EN INGENIERÍA INFORMÁTICA en los últimos años son las siguientes:\n" + 
    			"-Curso 2018-2019: 6,23.\n" + 
    			"-Curso 2017-2018: 6,49.\n" + 
    			"-Curso 2016-2017: 6,11.\n" + 
    			"-Curso 2015-2016: 6,50.\n"));
    	conversacion.procesarTextoRecibido("¿Es muy difícil la asignatura de TERMODINÁMICA del grado en ingeniería eléctrica?");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("Las estadísticas de TASA DE SUSPENSOS para la asignatura TERMODINÁMICA (I. ELÉCTRICA/I. ELECTRÓNICA) de la titulación GRADO EN INGENIERÍA ELÉCTRICA en los últimos años son las siguientes:\n" + 
    			"-Curso 2017-2018: 73,33.\n" + 
    			"-Curso 2016-2017: 40,91.\n" + 
    			"-Curso 2015-2016: 30,43.\n"));
    	conversacion.procesarTextoRecibido("¿Cual es la nota media de base de datos?");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("Durante el último curso académico registrado (2018 - 2019), estos fueron los estudios de GRADO que obtuvieron mayores resultados en cuanto a NOTA MEDIA :\n" + 
    			"-La titulación GRADO EN FILOSOFÍA obtuvo unos resultados de 7,62.\n" + 
    			"-La titulación GRADO EN ANTROPOLOGÍA SOCIAL Y CULTURAL obtuvo unos resultados de 7,54.\n" + 
    			"-La titulación GRADO EN ESTUDIOS INGLESES: LENGUA, LITERATURA Y CULTURA obtuvo unos resultados de 7,34.\n" + 
    			"-La titulación GRADO EN LENGUA Y LITERATURA ESPAÑOLAS obtuvo unos resultados de 7,32.\n" + 
    			"-La titulación GRADO EN CIENCIAS AMBIENTALES obtuvo unos resultados de 7,29.\n" + 
    			"-La titulación GRADO EN CC. JURÍDICAS DE LAS ADMINISTRACIONES PÚBLICAS obtuvo unos resultados de 7,26.\n" + 
    			"-La titulación GRADO EN HISTORIA DEL ARTE obtuvo unos resultados de 7,26.\n" + 
    			"-La titulación GRADO EN TRABAJO SOCIAL obtuvo unos resultados de 7,20.\n" + 
    			"-La titulación GRADO EN INGENIERÍA EN TECNOLOGÍAS DE LA INFORMACIÓN obtuvo unos resultados de 7,14.\n" + 
    			"-La titulación GRADO EN GEOGRAFÍA E HISTORIA obtuvo unos resultados de 7,13.\n"));
    	conversacion.procesarTextoRecibido("¿Cual es la nota media de bases de datos?");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("La asignatura BASES DE DATOS puede pertenecer a distintas titulaciones, seleccione a continuación la correcta:\n" + 
    			"__BOTON_CALLBACK__GRADO EN INGENIERÍA INFORMÁTICA__BOTON_CALLBACK__GRADO EN INGENIERÍA EN TECNOLOGÍAS DE LA INFORMACIÓN__BOTON_CALLBACK__Mi consulta no estaba relacionada con eso"));
	}
	
	@Test
    public void testConversacionEstadisticaRendimientoTop(){		
    	Conversacion conversacion = new Conversacion("123456789", OrigenConversacion.TELEGRAM);
    	conversacion.procesarTextoRecibido("¿Cual es la asignatura más fácil del grado en derecho?");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("Durante el último curso académico registrado (2018-2019), estas fueron las asignaturas de la titulación GRADO EN DERECHO que obtuvieron mayores resultados en las estadísticas de TASA DE ÉXITO:\n" + 
    			"-La asignatura TRABAJO FIN DE GRADO (DERECHO) obtuvo unos resultados de 99,89.\n" + 
    			"-La asignatura DERECHO DEL CONSUMO obtuvo unos resultados de 98,21.\n" + 
    			"-La asignatura DERECHO PROCESAL I.2: ESPECIAL obtuvo unos resultados de 94,32.\n" + 
    			"-La asignatura DERECHO PROCESAL I.1: GENERAL obtuvo unos resultados de 94,18.\n" + 
    			"-La asignatura FILOSOFÍA DEL DERECHO obtuvo unos resultados de 93,98.\n" + 
    			"-La asignatura DERECHO MERCANTIL IV: TÍTULOS-VALORES, INSTRUMENTOS DE PAGO Y CRÉDITO Y DERECHO CONCURSAL obtuvo unos resultados de 93,71.\n" + 
    			"-La asignatura TEORÍA DEL ESTADO CONSTITUCIONAL obtuvo unos resultados de 92,86.\n" + 
    			"-La asignatura DERECHO CIVIL III: DERECHOS REALES E HIPOTECARIO obtuvo unos resultados de 92,65.\n" + 
    			"-La asignatura DERECHO ADMINISTRATIVO IV obtuvo unos resultados de 92,25.\n" + 
    			"-La asignatura DERECHO CIVIL IV: DERECHO DE SUCESIONES obtuvo unos resultados de 90,26.\n"));
    	conversacion.procesarTextoRecibido("¿Cual es la asignatura más dificil de cuarto curso del grado en ingenieria electrica?");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("Durante el último curso académico registrado (2018-2019), estas fueron las asignaturas de la titulación GRADO EN INGENIERÍA ELÉCTRICA que cumplen el criterio de  PERTENECER AL CUARTO CURSO que obtuvieron mayores resultados en las estadísticas de TASA DE SUSPENSOS:\n" + 
    			"-La asignatura ACCIONAMIENTO Y CONTROL DE MÁQUINAS ELÉCTRICAS obtuvo unos resultados de 28,57.\n" + 
    			"-La asignatura INGENIERÍA DEL MEDIO AMBIENTE obtuvo unos resultados de 25,00.\n" + 
    			"-La asignatura SISTEMAS FOTOVOLTAICOS obtuvo unos resultados de 20,00.\n" + 
    			"-La asignatura OFICINA TÉCNICA Y PROYECTOS (I. ELÉCTRICA) obtuvo unos resultados de 16,67.\n" + 
    			"-La asignatura GENERACIÓN DE ENERGÍA ELÉCTRICA obtuvo unos resultados de 11,11.\n" + 
    			"-La asignatura ANÁLISIS Y OPERACIÓN DE SISTEMAS ELÉCTRICOS obtuvo unos resultados de 8,33.\n" + 
    			"-La asignatura ANÁLISIS DEL ENTORNO Y ADMINISTRACIÓN DE EMPRESAS obtuvo unos resultados de 0,00.\n" + 
    			"-La asignatura AUTOMÓVILES Y FERROCARRILES obtuvo unos resultados de 0,00.\n" + 
    			"-La asignatura CONSTRUCCIÓN Y ARQUITECTURA INDUSTRIAL obtuvo unos resultados de 0,00.\n" + 
    			"-La asignatura CONTROL AVANZADO DE SISTEMAS ELÉCTRICOS obtuvo unos resultados de 0,00.\n"));
    	conversacion.procesarTextoRecibido("¿Cual es el grado mas dificil?");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("Durante el último curso académico registrado (2018 - 2019), estos fueron los estudios de GRADO que obtuvieron mayores resultados en cuanto a PORCENTAJE DE SUSPENSOS :\n" + 
    			"-La titulación GRADO EN INGENIERÍA EN TECNOLOGÍAS INDUSTRIALES obtuvo unos resultados de 32,40.\n" + 
    			"-La titulación GRADO EN INGENIERÍA ELÉCTRICA obtuvo unos resultados de 31,02.\n" + 
    			"-La titulación GRADO EN ING. EN  ELECTRÓNICA INDUSTRIAL Y AUTOMÁTICA obtuvo unos resultados de 30,79.\n" + 
    			"-La titulación GRADO EN INGENIERÍA MECÁNICA obtuvo unos resultados de 28,07.\n" + 
    			"-La titulación GRADO EN ADMINISTRACIÓN Y DIRECCIÓN DE EMPRESAS obtuvo unos resultados de 26,13.\n" + 
    			"-La titulación GRADO EN FÍSICA obtuvo unos resultados de 25,41.\n" + 
    			"-La titulación GRADO EN QUÍMICA obtuvo unos resultados de 24,59.\n" + 
    			"-La titulación GRADO EN PSICOLOGÍA obtuvo unos resultados de 24,43.\n" + 
    			"-La titulación GRADO EN INGENIERÍA INFORMÁTICA obtuvo unos resultados de 24,22.\n" + 
    			"-La titulación GRADO EN MATEMÁTICAS obtuvo unos resultados de 23,74.\n"));
    	conversacion.procesarTextoRecibido("¿Cual es el máster mejor valorada por los alumnos?");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("Durante el último curso académico registrado (2018 - 2019), estos fueron los estudios de MASTER que obtuvieron mayores resultados en cuanto a VALORACIÓN ESTUDIANTIL :\n" + 
    			"-La titulación MÁSTER UNIVERSITARIO EN INVESTIGACIÓN EN PSICOLOGÍA obtuvo unos resultados de 94,37.\n" + 
    			"-La titulación MÁSTER UNIVERSITARIO EN COMUNICACIÓN, REDES Y GESTIÓN DE CONTENIDOS obtuvo unos resultados de 92,10.\n" + 
    			"-La titulación MÁSTER UNIVERSITARIO EN HACIENDA PÚBLICA Y ADMINISTRACIÓN FINANCIERA Y TRIBUTARIA obtuvo unos resultados de 85,72.\n" + 
    			"-La titulación MÁSTER UNIVERSITARIO EN ANÁLISIS GRAMATICAL Y ESTILÍSTICO DEL ESPAÑOL obtuvo unos resultados de 84,75.\n" + 
    			"-La titulación MÁSTER UNIVERSITARIO EN INVESTIGACIÓN EN PSICOLOGÍA (PLAN 2016) obtuvo unos resultados de 82,69.\n" + 
    			"-La titulación MÁSTER UNIVERSITARIO EN INVESTIGACIÓN EN INGENIERÍA DE SOFTWARE Y SISTEMAS INFORMÁTICOS obtuvo unos resultados de 81,87.\n" + 
    			"-La titulación MÁSTER UNIVERSITARIO EN ESTUDIOS LITERARIOS Y CULTURALES INGLESES  Y SU PROYECCIÓN SOCIAL obtuvo unos resultados de 80,80.\n" + 
    			"-La titulación MÁSTER UNIVERSITARIO EN ESTRATEGIAS Y TECNOLOGÍAS PARA LA FUNCIÓN DOCENTE EN LA SOCIEDAD MULTICULTURAL obtuvo unos resultados de 80,10.\n" + 
    			"-La titulación MÁSTER UNIVERSITARIO EURO-LATINOAMERICANO EN EDUCACIÓN INTERCULTURAL obtuvo unos resultados de 79,81.\n" + 
    			"-La titulación MÁSTER UNIVERSITARIO EN LINGÜÍSTICA INGLESA APLICADA obtuvo unos resultados de 79,60.\n"));
	}
	
	@Test
    public void testConversacionMatriculados(){		
    	Conversacion conversacion = new Conversacion("123456789", OrigenConversacion.TELEGRAM);
    	conversacion.procesarTextoRecibido("¿Cuanta gente se matricula de fundamentos de psicobiología en el grado en psicología?");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("El número de matriculados en la asignatura FUNDAMENTOS DE PSICOBIOLOGÍA de la titulación GRADO EN PSICOLOGÍA en los últimos años son los siguientes:\n" + 
    			"-Curso 2019 - 2020: 7934 matriculados.\n" + 
    			"-Curso 2018 - 2019: 7233 matriculados.\n" + 
    			"-Curso 2017 - 2018: 7002 matriculados.\n" + 
    			"-Curso 2016 - 2017: 7281 matriculados.\n" + 
    			"-Curso 2015 - 2016: 8302 matriculados.\n")); 
    	conversacion.procesarTextoRecibido("¿Cuanta gente se matricula de fundamentos de psicobiología en el grado en ingeniería informática?");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("La asignatura FUNDAMENTOS DE PSICOBIOLOGÍA no forma parte de la titulación GRADO EN INGENIERÍA INFORMÁTICA.\n"
    			+ "Por favor, intente expresar su consulta de otra forma, prestando especial atención al nombre de los estudios de los cuales desea información.\n")); 
    	conversacion.procesarTextoRecibido("¿Cuantos hacen la matrícula de inglés");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("El número de matriculados en la titulación INGLÉS en los últimos años son los siguientes:\n" + 
    			"-Curso 2019 - 2020: 6861 matriculados.\n" + 
    			"-Curso 2018 - 2019: 7690 matriculados.\n" + 
    			"-Curso 2017 - 2018: 9750 matriculados.\n" + 
    			"-Curso 2016 - 2017: 10629 matriculados.\n" + 
    			"-Curso 2015 - 2016: 12588 matriculados.\n"));
    	conversacion.procesarTextoRecibido("¿Cuanta gente se matricula de química, matemáticas y esas cosas?");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("Si deseas información sobre el número de matriculados de una determinada titulación o asignatura, debes nombrar sólo la titulación y la asignatura sobre las que quieres esa información.\n"));
    	conversacion.procesarTextoRecibido("¿Cuantas personas se matriculan de fundamentos de psicobiología");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("El número de matriculados en la asignatura FUNDAMENTOS DE PSICOBIOLOGÍA de la titulación GRADO EN PSICOLOGÍA en los últimos años son los siguientes:\n" + 
    			"-Curso 2019 - 2020: 7934 matriculados.\n" + 
    			"-Curso 2018 - 2019: 7233 matriculados.\n" + 
    			"-Curso 2017 - 2018: 7002 matriculados.\n" + 
    			"-Curso 2016 - 2017: 7281 matriculados.\n" + 
    			"-Curso 2015 - 2016: 8302 matriculados.\n"));
    	conversacion.procesarTextoRecibido("¿Cuanta gente se matricula de fundamentos de psicobiología en el grado en psicología?");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("El número de matriculados en la asignatura FUNDAMENTOS DE PSICOBIOLOGÍA de la titulación GRADO EN PSICOLOGÍA en los últimos años son los siguientes:\n" + 
    			"-Curso 2019 - 2020: 7934 matriculados.\n" + 
    			"-Curso 2018 - 2019: 7233 matriculados.\n" + 
    			"-Curso 2017 - 2018: 7002 matriculados.\n" + 
    			"-Curso 2016 - 2017: 7281 matriculados.\n" + 
    			"-Curso 2015 - 2016: 8302 matriculados.\n"));
    	conversacion.procesarTextoRecibido("¿Cuanta gente se matricula en grado en ingeniería informática, grado en psicología, fundamentos de psicobiología en el grado en ingeniería informática?");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("Si deseas información sobre el número de matriculados de una determinada titulación o asignatura, debes nombrar sólo la titulación y la asignatura sobre las que quieres esa información.\n"));
    	conversacion.procesarTextoRecibido("¿Cuanta gente se matricula de fundamentos de psicobiología en el grado en ingeniería informática?");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("La asignatura FUNDAMENTOS DE PSICOBIOLOGÍA no forma parte de la titulación GRADO EN INGENIERÍA INFORMÁTICA.\n" + 
    			"Por favor, intente expresar su consulta de otra forma, prestando especial atención al nombre de los estudios de los cuales desea información.\n"));
	}
	
	@Test
    public void testConversacionCallBack(){		
    	Conversacion conversacion = new Conversacion("123456789", OrigenConversacion.TELEGRAM);
    	conversacion.procesarTextoRecibido("informacion de contacto");
    	conversacion.procesarTextoRecibido("Informaeral");
    	conversacion.procesarTextoRecibido("Información general");
    	conversacion.procesarTextoRecibido("Necesito informacion de contacto");
    	conversacion.procesarTextoRecibido("Mi consulta no estaba relacionada con eso");
    	conversacion.procesarTextoRecibido("Cuanta gente se matricula de programacion y estructuras de datos avanzadas grado en ingeniería informática");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("El número de matriculados en la asignatura PROGRAMACIÓN Y ESTRUCTURAS DE DATOS AVANZADAS de la titulación GRADO EN INGENIERÍA INFORMÁTICA en los últimos años son los siguientes:\n" + 
    			"-Curso 2019 - 2020: 289 matriculados.\n" + 
    			"-Curso 2018 - 2019: 273 matriculados.\n" + 
    			"-Curso 2017 - 2018: 275 matriculados.\n" + 
    			"-Curso 2016 - 2017: 260 matriculados.\n" + 
    			"-Curso 2015 - 2016: 323 matriculados.\n")); 
    	conversacion.procesarTextoRecibido("Cuanta gente se matricula de estrategias de programacion y estructuras de datos");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("La asignatura ESTRATEGIAS DE PROGRAMACIÓN Y ESTRUCTURAS DE DATOS puede pertenecer a distintas titulaciones, seleccione a continuación la correcta:\n" + 
    			"__BOTON_CALLBACK__GRADO EN INGENIERÍA INFORMÁTICA__BOTON_CALLBACK__GRADO EN INGENIERÍA EN TECNOLOGÍAS DE LA INFORMACIÓN__BOTON_CALLBACK__Mi consulta no estaba relacionada con eso")); 
    	conversacion.procesarTextoRecibido("Cuanta gente se matricula de estrategias de programacion y estructuras de datos");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("La asignatura ESTRATEGIAS DE PROGRAMACIÓN Y ESTRUCTURAS DE DATOS puede pertenecer a distintas titulaciones, seleccione a continuación la correcta:\n" + 
    			"__BOTON_CALLBACK__GRADO EN INGENIERÍA INFORMÁTICA__BOTON_CALLBACK__GRADO EN INGENIERÍA EN TECNOLOGÍAS DE LA INFORMACIÓN__BOTON_CALLBACK__Mi consulta no estaba relacionada con eso")); 
    	conversacion.procesarTextoRecibido("Mi consulta no estaba relacionada con eso");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("Siento no haberte entendido correctamente, ¿Podrías repetir tu consulta?\n")); 
    	conversacion.procesarTextoRecibido("Cuanta gente se matricula de estrategias de programacion y estructuras de datos");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("La asignatura ESTRATEGIAS DE PROGRAMACIÓN Y ESTRUCTURAS DE DATOS puede pertenecer a distintas titulaciones, seleccione a continuación la correcta:\n" + 
    			"__BOTON_CALLBACK__GRADO EN INGENIERÍA INFORMÁTICA__BOTON_CALLBACK__GRADO EN INGENIERÍA EN TECNOLOGÍAS DE LA INFORMACIÓN__BOTON_CALLBACK__Mi consulta no estaba relacionada con eso")); 
    	conversacion.procesarTextoRecibido("1");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("El número de matriculados en la asignatura ESTRATEGIAS DE PROGRAMACIÓN Y ESTRUCTURAS DE DATOS de la titulación GRADO EN INGENIERÍA INFORMÁTICA en los últimos años son los siguientes:\n" + 
    			"-Curso 2019 - 2020: 856 matriculados.\n" + 
    			"-Curso 2018 - 2019: 842 matriculados.\n" + 
    			"-Curso 2017 - 2018: 782 matriculados.\n" + 
    			"-Curso 2016 - 2017: 837 matriculados.\n" + 
    			"-Curso 2015 - 2016: 942 matriculados.\n")); 
	}
	
}
