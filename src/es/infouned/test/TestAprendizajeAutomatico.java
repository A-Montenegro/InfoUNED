package es.infouned.test;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import es.infouned.aprendizajeAutomatico.Clasificador;
import es.infouned.aprendizajeAutomatico.ClasificadorNaiveBayes;
import es.infouned.principal.Configuracion;
import es.infouned.utilidades.ProcesamientoDeTexto;

public class TestAprendizajeAutomatico{

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
    public void testNaiveBayes(){		
		Clasificador clasificadorNaiveBayes = Configuracion.getClasificador();
		assertTrue(clasificadorNaiveBayes instanceof ClasificadorNaiveBayes);
		assertTrue(clasificadorNaiveBayes.clasificarInstancia(ProcesamientoDeTexto.normalizarTexto("Cuanta gente se matricula de fundamentos matematicos de la informatica")).equals("solicitudMatriculados"));
		assertTrue(clasificadorNaiveBayes.clasificarInstancia(ProcesamientoDeTexto.normalizarTexto("cual es la asignatura mas dificil de cuarto curso de ingeniería informática")).equals("solicitudEstadisticaRendimientoTop"));
		assertTrue(clasificadorNaiveBayes.clasificarInstancia(ProcesamientoDeTexto.normalizarTexto("cual es el email para hablar con vosotros")).equals("informacionContacto"));
		assertTrue(clasificadorNaiveBayes.clasificarInstancia(ProcesamientoDeTexto.normalizarTexto("como hay que hacer para hacer la matricula online")).equals("informacionMatricula"));
		assertTrue(clasificadorNaiveBayes.clasificarInstancia(ProcesamientoDeTexto.normalizarTexto("la gente suele dejar la carrera")).equals("solicitudEstadisticaRendimiento"));
		assertTrue(clasificadorNaiveBayes.clasificarInstancia(ProcesamientoDeTexto.normalizarTexto("los estudiantes de la asignatura estan satisfechos")).equals("solicitudValoracionEstudiantil"));
		assertTrue(clasificadorNaiveBayes.clasificarInstancia(ProcesamientoDeTexto.normalizarTexto("¿Cual es la duración media el grado en turismo?")).equals("solicitudEstadisticaRendimiento"));
		assertTrue(clasificadorNaiveBayes.clasificarInstancia(ProcesamientoDeTexto.normalizarTexto("¿Cuanto cuestan las asignaturas del grado en geografía e historia?")).equals("solicitudPreciosTitulacion"));
		assertTrue(clasificadorNaiveBayes.clasificarInstancia(ProcesamientoDeTexto.normalizarTexto("¿Cual es la nota media de HISTORIA DE LA ALTA EDAD MODERNA perteneciente al GRADO EN GEOGRAFÍA E HISTORIA?")).equals("solicitudEstadisticaRendimiento"));	
	}
	

}