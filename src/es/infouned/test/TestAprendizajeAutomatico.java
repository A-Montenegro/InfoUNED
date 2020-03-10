package es.infouned.test;

import static org.junit.Assert.assertTrue;
import org.junit.Test;
import es.infouned.aprendizajeAutomatico.ClasificadorNaiveBayes;

public class TestAprendizajeAutomatico{

	@Test
    public void testNaiveBayes() throws Exception{
		ClasificadorNaiveBayes clasificadorNaiveBayes = new ClasificadorNaiveBayes("modeloNaiveBayesEntrenado.model","estructuraDataSet.arff");
		String clasificacionInstancia = clasificadorNaiveBayes.clasificarInstancia("Cuanto cuesta estudiar ingener�a inform�tica");
		assertTrue(clasificacionInstancia.contentEquals("solicitudPreciosTitulacion"));
	}
	
}