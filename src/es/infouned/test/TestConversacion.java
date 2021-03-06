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
 * @author Alberto Mart�nez Montenegro
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
    public void testConversacionPrioridades(){		
    	Conversacion conversacion = new Conversacion("123456789", OrigenConversacion.TELEGRAM);
    	conversacion.procesarTextoRecibido("Hola. Que tal. Cual es la nota media de turismo. Gracias. Quiero la guia de estad�stica. Gracias.Gracias.");
	}
	
	@Test
    public void testConversacionGuias(){		
    	Conversacion conversacion = new Conversacion("123456789", OrigenConversacion.TELEGRAM);
    	conversacion.procesarTextoRecibido("Necesito informaci�n general sobre el grado en psicolog�a");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("Si deseas informaci�n sobre la titulaci�n GRADO EN PSICOLOG�A, en la siguiente gu�a de estudio encontrar�s todos los detalles:\n" + 
    			"http://portal.uned.es/GestionGuiastitulacionesGrado/GenerarPDFGuia?c=2020&idT=6201"));
    	conversacion.procesarTextoRecibido("Necesito informaci�n general sobre el M�STER UNIVERSITARIO EN INGENIER�A INFORM�TICA");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("Si deseas informaci�n sobre la titulaci�n M�STER UNIVERSITARIO EN INGENIER�A INFORM�TICA, en la siguiente gu�a de estudio encontrar�s todos los detalles:\n" + 
    			"http://portal.uned.es/GestionGuiasTitulacionesPosgrado/GenerarPDFGuia?c=2020&idT=310601"));
    	conversacion.procesarTextoRecibido("Necesito informaci�n general sobre la asignatura estrategias de programaci�n y estructuras de datos avanzadas");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("La asignatura ESTRATEGIAS DE PROGRAMACI�N Y ESTRUCTURAS DE DATOS puede pertenecer a distintas titulaciones, seleccione a continuaci�n la correcta:\n" + 
    			"__BOTON_CALLBACK__GRADO EN INGENIER�A INFORM�TICA__BOTON_CALLBACK__GRADO EN INGENIER�A EN TECNOLOG�AS DE LA INFORMACI�N__BOTON_CALLBACK__Mi consulta no estaba relacionada con eso"));
    	conversacion.procesarTextoRecibido("GRADO EN INGENIER�A INFORM�TICA");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("Si deseas informaci�n sobre la asignatura ESTRATEGIAS DE PROGRAMACI�N Y ESTRUCTURAS DE DATOS de la titulaci�n GRADO EN INGENIER�A INFORM�TICA, en la siguiente gu�a de estudio encontrar�s todos los detalles:\n" + 
    			"http://portal.uned.es/GuiasAsignaturasGrados/PDFGuiaPublica?idA=71901043&c=2020&idT=7101"));
    	conversacion.procesarTextoRecibido("Necesito informaci�n general de M�TODOS MATEM�TICOS de grado en ingenier�a inform�tica");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("Seg�n mis datos, la asignatura que has nombrado en tu mensaje, no pertenece a la titulaci�n GRADO EN INGENIER�A INFORM�TICA.\n" + 
    			"Por favor, intenta expresar tu consulta de otra forma, prestando especial atenci�n al nombre de los estudios de los cuales deseas informaci�n.\n"));

	}
	
	@Test
    public void testConversacionPrecios(){		
    	Conversacion conversacion = new Conversacion("123456789", OrigenConversacion.TELEGRAM);
    	conversacion.procesarTextoRecibido("�Cuanto cuesta el grado en psicolog�a?");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("El precio de las asignaturas de la titulaci�n GRADO EN PSICOLOG�A es de:\n" + 
    			"-15,95� por cada cr�dito ECTS la primera vez que el alumno se matricula de la asignatura.\n" + 
    			"-22,95� por cada cr�dito ECTS la segunda vez que el alumno se matricula de la asignatura.\n" + 
    			"-50,49� por cada cr�dito ECTS la tercera vez que el alumno se matricula de la asignatura.\n" + 
    			"-69,62� por cada cr�dito ECTS la cuarta y las sucesivas veces que el alumno se matricule de la asignatura.\n"));
    	conversacion.procesarTextoRecibido("�Cuanto cuesta estudiar en la uned?");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("Si deseas informaci�n sobre los precios de una titulaci�n, debes especificar claramente el nombre de la misma.\n"));
    	conversacion.procesarTextoRecibido("�Cuanto cuestan las asignaturas del grado en geograf�a e historia?");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("El precio de las asignaturas de la titulaci�n GRADO EN GEOGRAF�A E HISTORIA es de:\n" + 
    			"-13,00� por cada cr�dito ECTS la primera vez que el alumno se matricula de la asignatura.\n" + 
    			"-18,97� por cada cr�dito ECTS la segunda vez que el alumno se matricula de la asignatura.\n" + 
    			"-41,74� por cada cr�dito ECTS la tercera vez que el alumno se matricula de la asignatura.\n" + 
    			"-57,55� por cada cr�dito ECTS la cuarta y las sucesivas veces que el alumno se matricule de la asignatura.\n"));
	}

	@Test
    public void testConversacionEstadisticaRendimiento(){		
    	Conversacion conversacion = new Conversacion("123456789", OrigenConversacion.TELEGRAM);
    	conversacion.procesarTextoRecibido("�Cual es la nota media de grado en ingenier�a inform�tica?");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("Las estad�sticas de NOTA MEDIA para la titulaci�n GRADO EN INGENIER�A INFORM�TICA en los �ltimos a�os son las siguientes:\n" + 
    			"-Curso 2018 - 2019: 7,05.\n" + 
    			"-Curso 2017 - 2018: 7,03.\n" + 
    			"-Curso 2016 - 2017: 7,08.\n" + 
    			"-Curso 2015 - 2016: 7,04.\n"));
    	conversacion.procesarTextoRecibido("�Cual es la nota media de programaci�n orientada a objetos?");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("La asignatura PROGRAMACI�N ORIENTADA A OBJETOS puede pertenecer a distintas titulaciones, seleccione a continuaci�n la correcta:\n" + 
    			"__BOTON_CALLBACK__GRADO EN INGENIER�A INFORM�TICA__BOTON_CALLBACK__GRADO EN INGENIER�A EN TECNOLOG�AS DE LA INFORMACI�N__BOTON_CALLBACK__Mi consulta no estaba relacionada con eso"));
    	conversacion.procesarTextoRecibido("GRADO EN INGENIER�A EN TECNOLOG�AS DE LA INFORMACI�N");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("Las estad�sticas de NOTA MEDIA para la asignatura PROGRAMACI�N ORIENTADA A OBJETOS de la titulaci�n GRADO EN INGENIER�A EN TECNOLOG�AS DE LA INFORMACI�N en los �ltimos a�os son las siguientes:\n" + 
    			"-Curso 2018-2019: 6,60.\n" + 
    			"-Curso 2017-2018: 6,18.\n" + 
    			"-Curso 2016-2017: 6,20.\n" + 
    			"-Curso 2015-2016: 6,11.\n"));
    	conversacion.procesarTextoRecibido("�Cual es la nota media de HISTORIA DE LA ALTA EDAD MODERNA perteneciente al GRADO EN GEOGRAF�A E HISTORIA?");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("Las estad�sticas de NOTA MEDIA para la asignatura HISTORIA DE LA ALTA EDAD MODERNA de la titulaci�n GRADO EN GEOGRAF�A E HISTORIA en los �ltimos a�os son las siguientes:\n" + 
    			"-Curso 2018-2019: 6,73.\n" + 
    			"-Curso 2017-2018: 6,63.\n" + 
    			"-Curso 2016-2017: 6,98.\n" + 
    			"-Curso 2015-2016: 7,08.\n"));
    	conversacion.procesarTextoRecibido("�Est� bien valorada la asignatura �tica y legislaci�n en el grado en ingenier�a inform�tica?");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("Las estad�sticas de VALORACI�N ESTUDIANTIL para la asignatura �TICA Y LEGISLACI�N de la titulaci�n GRADO EN INGENIER�A INFORM�TICA en los �ltimos a�os son las siguientes:\n" + 
    			"-Curso 2018-2019: 53,09.\n" + 
    			"-Curso 2017-2018: 54,89.\n" + 
    			"-Curso 2016-2017: 41,49.\n" + 
    			"-Curso 2015-2016: 50,60.\n"));
    	conversacion.procesarTextoRecibido("�Est� bien valorada la asignatura fundamentos de programaci�n?");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("La asignatura FUNDAMENTOS DE PROGRAMACI�N puede pertenecer a distintas titulaciones, seleccione a continuaci�n la correcta:\n" + 
    			"__BOTON_CALLBACK__GRADO EN INGENIER�A INFORM�TICA__BOTON_CALLBACK__GRADO EN INGENIER�A EN TECNOLOG�AS DE LA INFORMACI�N__BOTON_CALLBACK__Mi consulta no estaba relacionada con eso"));
    	conversacion.procesarTextoRecibido("GRADO EN INGENIER�A INFORM�TICA");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("Las estad�sticas de VALORACI�N ESTUDIANTIL para la asignatura FUNDAMENTOS DE PROGRAMACI�N de la titulaci�n GRADO EN INGENIER�A INFORM�TICA en los �ltimos a�os son las siguientes:\n" + 
    			"-Curso 2018-2019: 65,83.\n" + 
    			"-Curso 2017-2018: 68,47.\n" + 
    			"-Curso 2016-2017: 62,07.\n" + 
    			"-Curso 2015-2016: 57,88.\n"));
    	conversacion.procesarTextoRecibido("�Los alumnos est�n descontentos con la titulaci�n de grado en psicolog�a");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("Las estad�sticas de VALORACI�N ESTUDIANTIL para la titulaci�n GRADO EN PSICOLOG�A en los �ltimos a�os son las siguientes:\n" + 
    			"-Curso 2018 - 2019: 69,35.\n" + 
    			"-Curso 2017 - 2018: 67,76.\n" + 
    			"-Curso 2016 - 2017: 65,73.\n" + 
    			"-Curso 2015 - 2016: 78,69.\n"));
    	conversacion.procesarTextoRecibido("Cual es la nota media de estad�stica");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("La asignatura nombrada puede referirse a distintas titulaciones, seleccione a continuaci�n la correcta:\n"
    			+ "__BOTON_CALLBACK__ING. T�CNICA EN INFORM�TICA DE SISTEMAS (PLAN 2000)__BOTON_CALLBACK__LICENCIATURA EN CIENCIAS F�SICAS__BOTON_CALLBACK__LICENCIATURA EN CIENCIAS AMBIENTALES__BOTON_CALLBACK__GRADO EN INGENIER�A INFORM�TICA__BOTON_CALLBACK__GRADO EN INGENIER�A EN TECNOLOG�AS DE LA INFORMACI�N__BOTON_CALLBACK__GRADO EN INGENIER�A EL�CTRICA__BOTON_CALLBACK__GRADO EN ING. EN  ELECTR�NICA INDUSTRIAL Y AUTOM�TICA__BOTON_CALLBACK__GRADO EN INGENIER�A MEC�NICA__BOTON_CALLBACK__GRADO EN INGENIER�A EN TECNOLOG�AS INDUSTRIALES__BOTON_CALLBACK__Mi consulta no estaba relacionada con eso"));
    	conversacion.procesarTextoRecibido("GRADO EN INGENIER�A INFORM�TICA");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("Las estad�sticas de NOTA MEDIA para la asignatura ESTAD�STICA (ING.INFORM�TICA/ING.TI) de la titulaci�n GRADO EN INGENIER�A INFORM�TICA en los �ltimos a�os son las siguientes:\n" + 
    			"-Curso 2018-2019: 6,23.\n" + 
    			"-Curso 2017-2018: 6,49.\n" + 
    			"-Curso 2016-2017: 6,11.\n" + 
    			"-Curso 2015-2016: 6,50.\n"));
    	conversacion.procesarTextoRecibido("�Es muy dif�cil la asignatura de TERMODIN�MICA del grado en ingenier�a el�ctrica?");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("Las estad�sticas de TASA DE SUSPENSOS para la asignatura TERMODIN�MICA (I. EL�CTRICA/I. ELECTR�NICA) de la titulaci�n GRADO EN INGENIER�A EL�CTRICA en los �ltimos a�os son las siguientes:\n" + 
    			"-Curso 2017-2018: 73,33.\n" + 
    			"-Curso 2016-2017: 40,91.\n" + 
    			"-Curso 2015-2016: 30,43.\n"));
    	conversacion.procesarTextoRecibido("�Cual es la nota media de base de datos?");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("Durante el �ltimo curso acad�mico registrado (2018 - 2019), estos fueron los estudios de GRADO que obtuvieron mayores resultados en cuanto a NOTA MEDIA :\n" + 
    			"-La titulaci�n GRADO EN FILOSOF�A obtuvo unos resultados de 7,62.\n" + 
    			"-La titulaci�n GRADO EN ANTROPOLOG�A SOCIAL Y CULTURAL obtuvo unos resultados de 7,54.\n" + 
    			"-La titulaci�n GRADO EN ESTUDIOS INGLESES: LENGUA, LITERATURA Y CULTURA obtuvo unos resultados de 7,34.\n" + 
    			"-La titulaci�n GRADO EN LENGUA Y LITERATURA ESPA�OLAS obtuvo unos resultados de 7,32.\n" + 
    			"-La titulaci�n GRADO EN CIENCIAS AMBIENTALES obtuvo unos resultados de 7,29.\n" + 
    			"-La titulaci�n GRADO EN CC. JUR�DICAS DE LAS ADMINISTRACIONES P�BLICAS obtuvo unos resultados de 7,26.\n" + 
    			"-La titulaci�n GRADO EN HISTORIA DEL ARTE obtuvo unos resultados de 7,26.\n" + 
    			"-La titulaci�n GRADO EN TRABAJO SOCIAL obtuvo unos resultados de 7,20.\n" + 
    			"-La titulaci�n GRADO EN INGENIER�A EN TECNOLOG�AS DE LA INFORMACI�N obtuvo unos resultados de 7,14.\n" + 
    			"-La titulaci�n GRADO EN GEOGRAF�A E HISTORIA obtuvo unos resultados de 7,13.\n"));
    	conversacion.procesarTextoRecibido("�Cu�les son las asignaturas con mayor nota media del primer cuatrimestre del primer curso de Ingenier�a inform�tica?");
    	System.out.println(conversacion.obtenerRespuestaActual());
    	conversacion.procesarTextoRecibido("�Cual es la nota media de bases de datos?");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("La asignatura BASES DE DATOS puede pertenecer a distintas titulaciones, seleccione a continuaci�n la correcta:\n" + 
    			"__BOTON_CALLBACK__GRADO EN INGENIER�A INFORM�TICA__BOTON_CALLBACK__GRADO EN INGENIER�A EN TECNOLOG�AS DE LA INFORMACI�N__BOTON_CALLBACK__Mi consulta no estaba relacionada con eso"));

	}
	
	@Test
    public void testConversacionEstadisticaRendimientoTop(){		
    	Conversacion conversacion = new Conversacion("123456789", OrigenConversacion.TELEGRAM);
    	conversacion.procesarTextoRecibido("�Cual es la asignatura m�s f�cil del grado en derecho?");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("Durante el �ltimo curso acad�mico registrado (2018-2019), estas fueron las asignaturas de la titulaci�n GRADO EN DERECHO que obtuvieron mayores resultados en las estad�sticas de TASA DE �XITO:\n" + 
    			"-La asignatura TRABAJO FIN DE GRADO (DERECHO) obtuvo unos resultados de 99,89.\n" + 
    			"-La asignatura DERECHO DEL CONSUMO obtuvo unos resultados de 98,21.\n" + 
    			"-La asignatura DERECHO PROCESAL I.2: ESPECIAL obtuvo unos resultados de 94,32.\n" + 
    			"-La asignatura DERECHO PROCESAL I.1: GENERAL obtuvo unos resultados de 94,18.\n" + 
    			"-La asignatura FILOSOF�A DEL DERECHO obtuvo unos resultados de 93,98.\n" + 
    			"-La asignatura DERECHO MERCANTIL IV: T�TULOS-VALORES, INSTRUMENTOS DE PAGO Y CR�DITO Y DERECHO CONCURSAL obtuvo unos resultados de 93,71.\n" + 
    			"-La asignatura TEOR�A DEL ESTADO CONSTITUCIONAL obtuvo unos resultados de 92,86.\n" + 
    			"-La asignatura DERECHO CIVIL III: DERECHOS REALES E HIPOTECARIO obtuvo unos resultados de 92,65.\n" + 
    			"-La asignatura DERECHO ADMINISTRATIVO IV obtuvo unos resultados de 92,25.\n" + 
    			"-La asignatura DERECHO CIVIL IV: DERECHO DE SUCESIONES obtuvo unos resultados de 90,26.\n"));
    	conversacion.procesarTextoRecibido("�Cual es la asignatura m�s dificil de cuarto curso del grado en ingenieria electrica?");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("Durante el �ltimo curso acad�mico registrado (2018-2019), estas fueron las asignaturas de la titulaci�n GRADO EN INGENIER�A EL�CTRICA que cumplen el criterio de  PERTENECER AL CUARTO CURSO que obtuvieron mayores resultados en las estad�sticas de TASA DE SUSPENSOS:\n" + 
    			"-La asignatura ACCIONAMIENTO Y CONTROL DE M�QUINAS EL�CTRICAS obtuvo unos resultados de 28,57.\n" + 
    			"-La asignatura INGENIER�A DEL MEDIO AMBIENTE obtuvo unos resultados de 25,00.\n" + 
    			"-La asignatura SISTEMAS FOTOVOLTAICOS obtuvo unos resultados de 20,00.\n" + 
    			"-La asignatura OFICINA T�CNICA Y PROYECTOS (I. EL�CTRICA) obtuvo unos resultados de 16,67.\n" + 
    			"-La asignatura GENERACI�N DE ENERG�A EL�CTRICA obtuvo unos resultados de 11,11.\n" + 
    			"-La asignatura AN�LISIS Y OPERACI�N DE SISTEMAS EL�CTRICOS obtuvo unos resultados de 8,33.\n" + 
    			"-La asignatura AN�LISIS DEL ENTORNO Y ADMINISTRACI�N DE EMPRESAS obtuvo unos resultados de 0,00.\n" + 
    			"-La asignatura AUTOM�VILES Y FERROCARRILES obtuvo unos resultados de 0,00.\n" + 
    			"-La asignatura CONSTRUCCI�N Y ARQUITECTURA INDUSTRIAL obtuvo unos resultados de 0,00.\n" + 
    			"-La asignatura CONTROL AVANZADO DE SISTEMAS EL�CTRICOS obtuvo unos resultados de 0,00.\n"));
    	conversacion.procesarTextoRecibido("�Cual es el grado mas dificil?");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("Durante el �ltimo curso acad�mico registrado (2018 - 2019), estos fueron los estudios de GRADO que obtuvieron mayores resultados en cuanto a PORCENTAJE DE SUSPENSOS :\n" + 
    			"-La titulaci�n GRADO EN INGENIER�A EN TECNOLOG�AS INDUSTRIALES obtuvo unos resultados de 32,40.\n" + 
    			"-La titulaci�n GRADO EN INGENIER�A EL�CTRICA obtuvo unos resultados de 31,02.\n" + 
    			"-La titulaci�n GRADO EN ING. EN  ELECTR�NICA INDUSTRIAL Y AUTOM�TICA obtuvo unos resultados de 30,79.\n" + 
    			"-La titulaci�n GRADO EN INGENIER�A MEC�NICA obtuvo unos resultados de 28,07.\n" + 
    			"-La titulaci�n GRADO EN ADMINISTRACI�N Y DIRECCI�N DE EMPRESAS obtuvo unos resultados de 26,13.\n" + 
    			"-La titulaci�n GRADO EN F�SICA obtuvo unos resultados de 25,41.\n" + 
    			"-La titulaci�n GRADO EN QU�MICA obtuvo unos resultados de 24,59.\n" + 
    			"-La titulaci�n GRADO EN PSICOLOG�A obtuvo unos resultados de 24,43.\n" + 
    			"-La titulaci�n GRADO EN INGENIER�A INFORM�TICA obtuvo unos resultados de 24,22.\n" + 
    			"-La titulaci�n GRADO EN MATEM�TICAS obtuvo unos resultados de 23,74.\n"));
    	conversacion.procesarTextoRecibido("�Cual es el m�ster mejor valorada por los alumnos?");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("Durante el �ltimo curso acad�mico registrado (2018 - 2019), estos fueron los estudios de MASTER que obtuvieron mayores resultados en cuanto a VALORACI�N ESTUDIANTIL :\n" + 
    			"-La titulaci�n M�STER UNIVERSITARIO EN INVESTIGACI�N EN PSICOLOG�A obtuvo unos resultados de 94,37.\n" + 
    			"-La titulaci�n M�STER UNIVERSITARIO EN COMUNICACI�N, REDES Y GESTI�N DE CONTENIDOS obtuvo unos resultados de 92,10.\n" + 
    			"-La titulaci�n M�STER UNIVERSITARIO EN HACIENDA P�BLICA Y ADMINISTRACI�N FINANCIERA Y TRIBUTARIA obtuvo unos resultados de 85,72.\n" + 
    			"-La titulaci�n M�STER UNIVERSITARIO EN AN�LISIS GRAMATICAL Y ESTIL�STICO DEL ESPA�OL obtuvo unos resultados de 84,75.\n" + 
    			"-La titulaci�n M�STER UNIVERSITARIO EN INVESTIGACI�N EN PSICOLOG�A (PLAN 2016) obtuvo unos resultados de 82,69.\n" + 
    			"-La titulaci�n M�STER UNIVERSITARIO EN INVESTIGACI�N EN INGENIER�A DE SOFTWARE Y SISTEMAS INFORM�TICOS obtuvo unos resultados de 81,87.\n" + 
    			"-La titulaci�n M�STER UNIVERSITARIO EN ESTUDIOS LITERARIOS Y CULTURALES INGLESES  Y SU PROYECCI�N SOCIAL obtuvo unos resultados de 80,80.\n" + 
    			"-La titulaci�n M�STER UNIVERSITARIO EN ESTRATEGIAS Y TECNOLOG�AS PARA LA FUNCI�N DOCENTE EN LA SOCIEDAD MULTICULTURAL obtuvo unos resultados de 80,10.\n" + 
    			"-La titulaci�n M�STER UNIVERSITARIO EURO-LATINOAMERICANO EN EDUCACI�N INTERCULTURAL obtuvo unos resultados de 79,81.\n" + 
    			"-La titulaci�n M�STER UNIVERSITARIO EN LING��STICA INGLESA APLICADA obtuvo unos resultados de 79,60.\n"));
	}
	
	@Test
    public void testConversacionMatriculados(){		
    	Conversacion conversacion = new Conversacion("123456789", OrigenConversacion.TELEGRAM);
    	conversacion.procesarTextoRecibido("�Cuanta gente se matricula de fundamentos de psicobiolog�a en el grado en psicolog�a?");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("El n�mero de matriculados en la asignatura FUNDAMENTOS DE PSICOBIOLOG�A de la titulaci�n GRADO EN PSICOLOG�A en los �ltimos a�os son los siguientes:\n" + 
    			"-Curso 2019 - 2020: 7934 matriculados.\n" + 
    			"-Curso 2018 - 2019: 7233 matriculados.\n" + 
    			"-Curso 2017 - 2018: 7002 matriculados.\n" + 
    			"-Curso 2016 - 2017: 7281 matriculados.\n" + 
    			"-Curso 2015 - 2016: 8302 matriculados.\n")); 
    	conversacion.procesarTextoRecibido("�Cuanta gente se matricula de fundamentos de psicobiolog�a en el grado en ingenier�a inform�tica?");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("La asignatura FUNDAMENTOS DE PSICOBIOLOG�A no forma parte de la titulaci�n GRADO EN INGENIER�A INFORM�TICA.\n"
    			+ "Por favor, intenta expresar tu consulta de otra forma, prestando especial atenci�n al nombre de los estudios de los cuales deseas informaci�n.\n")); 
    	conversacion.procesarTextoRecibido("�Cuantos hacen la matr�cula de ingl�s");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("El n�mero de matriculados en la titulaci�n INGL�S en los �ltimos a�os son los siguientes:\n" + 
    			"-Curso 2019 - 2020: 6861 matriculados.\n" + 
    			"-Curso 2018 - 2019: 7690 matriculados.\n" + 
    			"-Curso 2017 - 2018: 9750 matriculados.\n" + 
    			"-Curso 2016 - 2017: 10629 matriculados.\n" + 
    			"-Curso 2015 - 2016: 12588 matriculados.\n"));
    	conversacion.procesarTextoRecibido("�Cuanta gente se matricula de qu�mica, matem�ticas y esas cosas?");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("Si deseas informaci�n sobre el n�mero de matriculados de una determinada titulaci�n o asignatura, debes nombrar s�lo la titulaci�n y la asignatura sobre las que quieres esa informaci�n.\n"));
    	conversacion.procesarTextoRecibido("�Cuantas personas se matriculan de fundamentos de psicobiolog�a");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("El n�mero de matriculados en la asignatura FUNDAMENTOS DE PSICOBIOLOG�A de la titulaci�n GRADO EN PSICOLOG�A en los �ltimos a�os son los siguientes:\n" + 
    			"-Curso 2019 - 2020: 7934 matriculados.\n" + 
    			"-Curso 2018 - 2019: 7233 matriculados.\n" + 
    			"-Curso 2017 - 2018: 7002 matriculados.\n" + 
    			"-Curso 2016 - 2017: 7281 matriculados.\n" + 
    			"-Curso 2015 - 2016: 8302 matriculados.\n"));
    	conversacion.procesarTextoRecibido("�Cuanta gente se matricula de fundamentos de psicobiolog�a en el grado en psicolog�a?");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("El n�mero de matriculados en la asignatura FUNDAMENTOS DE PSICOBIOLOG�A de la titulaci�n GRADO EN PSICOLOG�A en los �ltimos a�os son los siguientes:\n" + 
    			"-Curso 2019 - 2020: 7934 matriculados.\n" + 
    			"-Curso 2018 - 2019: 7233 matriculados.\n" + 
    			"-Curso 2017 - 2018: 7002 matriculados.\n" + 
    			"-Curso 2016 - 2017: 7281 matriculados.\n" + 
    			"-Curso 2015 - 2016: 8302 matriculados.\n"));
    	conversacion.procesarTextoRecibido("�Cuanta gente se matricula en grado en ingenier�a inform�tica, grado en psicolog�a, fundamentos de psicobiolog�a en el grado en ingenier�a inform�tica?");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("Si deseas informaci�n sobre el n�mero de matriculados de una determinada titulaci�n o asignatura, debes nombrar s�lo la titulaci�n y la asignatura sobre las que quieres esa informaci�n.\n"));
    	conversacion.procesarTextoRecibido("�Cuanta gente se matricula de fundamentos de psicobiolog�a en el grado en ingenier�a inform�tica?");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("La asignatura FUNDAMENTOS DE PSICOBIOLOG�A no forma parte de la titulaci�n GRADO EN INGENIER�A INFORM�TICA.\n" + 
    			"Por favor, intenta expresar tu consulta de otra forma, prestando especial atenci�n al nombre de los estudios de los cuales deseas informaci�n.\n"));
	}
	
	@Test
    public void testConversacionCallBack(){		
    	Conversacion conversacion = new Conversacion("123456789", OrigenConversacion.TELEGRAM);
    	conversacion.procesarTextoRecibido("informacion de contacto");
    	conversacion.procesarTextoRecibido("Informaeral");
    	conversacion.procesarTextoRecibido("Informaci�n general");
    	conversacion.procesarTextoRecibido("Necesito informacion de contacto");
    	conversacion.procesarTextoRecibido("Mi consulta no estaba relacionada con eso");
    	conversacion.procesarTextoRecibido("Cuanta gente se matricula de programacion y estructuras de datos avanzadas grado en ingenier�a inform�tica");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("El n�mero de matriculados en la asignatura PROGRAMACI�N Y ESTRUCTURAS DE DATOS AVANZADAS de la titulaci�n GRADO EN INGENIER�A INFORM�TICA en los �ltimos a�os son los siguientes:\n" + 
    			"-Curso 2019 - 2020: 289 matriculados.\n" + 
    			"-Curso 2018 - 2019: 273 matriculados.\n" + 
    			"-Curso 2017 - 2018: 275 matriculados.\n" + 
    			"-Curso 2016 - 2017: 260 matriculados.\n" + 
    			"-Curso 2015 - 2016: 323 matriculados.\n")); 
    	conversacion.procesarTextoRecibido("Cuanta gente se matricula de estrategias de programacion y estructuras de datos");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("La asignatura ESTRATEGIAS DE PROGRAMACI�N Y ESTRUCTURAS DE DATOS puede pertenecer a distintas titulaciones, seleccione a continuaci�n la correcta:\n" + 
    			"__BOTON_CALLBACK__GRADO EN INGENIER�A INFORM�TICA__BOTON_CALLBACK__GRADO EN INGENIER�A EN TECNOLOG�AS DE LA INFORMACI�N__BOTON_CALLBACK__Mi consulta no estaba relacionada con eso")); 
    	conversacion.procesarTextoRecibido("Cuanta gente se matricula de estrategias de programacion y estructuras de datos");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("La asignatura ESTRATEGIAS DE PROGRAMACI�N Y ESTRUCTURAS DE DATOS puede pertenecer a distintas titulaciones, seleccione a continuaci�n la correcta:\n" + 
    			"__BOTON_CALLBACK__GRADO EN INGENIER�A INFORM�TICA__BOTON_CALLBACK__GRADO EN INGENIER�A EN TECNOLOG�AS DE LA INFORMACI�N__BOTON_CALLBACK__Mi consulta no estaba relacionada con eso")); 
    	conversacion.procesarTextoRecibido("Mi consulta no estaba relacionada con eso");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("Siento no haberte entendido correctamente, �Podr�as repetir tu consulta?\n")); 
    	conversacion.procesarTextoRecibido("Cuanta gente se matricula de estrategias de programacion y estructuras de datos");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("La asignatura ESTRATEGIAS DE PROGRAMACI�N Y ESTRUCTURAS DE DATOS puede pertenecer a distintas titulaciones, seleccione a continuaci�n la correcta:\n" + 
    			"__BOTON_CALLBACK__GRADO EN INGENIER�A INFORM�TICA__BOTON_CALLBACK__GRADO EN INGENIER�A EN TECNOLOG�AS DE LA INFORMACI�N__BOTON_CALLBACK__Mi consulta no estaba relacionada con eso")); 
    	conversacion.procesarTextoRecibido("1");
    	assertTrue(conversacion.obtenerRespuestaActual().equals("El n�mero de matriculados en la asignatura ESTRATEGIAS DE PROGRAMACI�N Y ESTRUCTURAS DE DATOS de la titulaci�n GRADO EN INGENIER�A INFORM�TICA en los �ltimos a�os son los siguientes:\n" + 
    			"-Curso 2019 - 2020: 856 matriculados.\n" + 
    			"-Curso 2018 - 2019: 842 matriculados.\n" + 
    			"-Curso 2017 - 2018: 782 matriculados.\n" + 
    			"-Curso 2016 - 2017: 837 matriculados.\n" + 
    			"-Curso 2015 - 2016: 942 matriculados.\n")); 
	}
	
}
