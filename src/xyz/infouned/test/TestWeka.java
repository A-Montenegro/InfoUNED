package xyz.infouned.test;

import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import org.junit.Test;
import weka.classifiers.meta.FilteredClassifier;

public class TestWeka{

	@Test
    public void test() throws Exception{
		FilteredClassifier clasificadorFiltrado = (FilteredClassifier) weka.core.SerializationHelper.read("modeloEntrenado.model");
		DataSource estructuraDataSet = new DataSource("estructuraDataSet.arff");
		Instances dataset = estructuraDataSet.getDataSet();
	    dataset.setClassIndex(dataset.numAttributes()-1);
	    for (Instance inst: dataset) {
	    	double result = clasificadorFiltrado.classifyInstance(inst);
	    	System.out.println(inst.toString() + " ----> " + obtenerNombreClase((int) result));
	    }
	    double[] instanceValue1 = new double[dataset.numAttributes()];
        instanceValue1[0] = dataset.attribute(0).addStringValue("enología es la disciplina que estudia");
        dataset.add(new DenseInstance(1.0, instanceValue1));
	    double result = clasificadorFiltrado.classifyInstance(dataset.firstInstance());
    	System.out.println("\n\nExtra: " + dataset.firstInstance().toString() + " ----> " + obtenerNombreClase((int) result));
	}
	
	private String obtenerNombreClase(int indice) {
		switch (indice) {
		case 0:
			return "saludos";
		case 1:
			return "despedidas";
		case 2:
			return "informativas";
		default:
			return "error";
		}
	}
}