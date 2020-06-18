package es.infouned.aprendizajeAutomatico;

import java.util.ArrayList;

import weka.classifiers.meta.FilteredClassifier;
import weka.core.DenseInstance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

/**
 * Clase que simboliza un clasificador Naive Bayes, su implementación del método clasificarInstancia() le permite catalogar cadenas de texto a partir de un modelo entrenado.
 * @author Alberto Martínez Montenegro
 * 
 *
 */
public class ClasificadorNaiveBayes implements Clasificador{
	private FilteredClassifier clasificadorNaiveBayes;
	private DataSource estructuraDataSet;
	private Instances dataset;
	private ArrayList<String> nombresClases;
	private final double umbralClasificacion = 0.29;
	
	public ClasificadorNaiveBayes(String rutaModeloEntrenado, String rutaFicheroEstructuraDataSet) {
		try {
			this.clasificadorNaiveBayes = (FilteredClassifier) weka.core.SerializationHelper.read(rutaModeloEntrenado.substring(1, rutaModeloEntrenado.length()));
			this.estructuraDataSet = new DataSource(rutaFicheroEstructuraDataSet.substring(1, rutaFicheroEstructuraDataSet.length()));
			this.dataset = estructuraDataSet.getDataSet();
			generarNombresClases();
		    dataset.setClassIndex(dataset.numAttributes()-1);
		}catch(Exception excepcion) {
			excepcion.printStackTrace();
		}
	}
	
	private void generarNombresClases() {
		String cadenaTextoEstructura = null;
		try {
			cadenaTextoEstructura = estructuraDataSet.getStructure().toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		String[] divisionAbreLlave = cadenaTextoEstructura.split("\\{");
		String[] divisionCierraLlave = divisionAbreLlave[1].split("\\}");
		String[] divisionComas = divisionCierraLlave[0].split(",");
		ArrayList<String> nombresClases = new ArrayList<String>();
		for(String nombresClase: divisionComas) {
			nombresClases.add(nombresClase);
		}
		this.nombresClases = nombresClases;
	}
	
	public String clasificarInstancia(String instancia) {
	    double[] valorInstancia = new double[dataset.numAttributes()];
	    valorInstancia[0] = dataset.attribute(0).addStringValue(instancia);
        dataset.add(new DenseInstance(1.0, valorInstancia));
        double distribucionClasificacion=-1;
	    double resultadoClasificacion=-1;
		try {
			resultadoClasificacion = clasificadorNaiveBayes.classifyInstance(dataset.firstInstance());
			distribucionClasificacion = clasificadorNaiveBayes.distributionForInstance(dataset.firstInstance())[(int)resultadoClasificacion];
		} catch (Exception excepcion) {
			// TODO Auto-generated catch block
			excepcion.printStackTrace();
		}
		dataset.remove(0);
		if(distribucionClasificacion < umbralClasificacion) {
			return "noClasificable";
		}
		else {
			String nombreClase = nombresClases.get((int) resultadoClasificacion);
	    	return nombreClase;
		}
	}
	
	public double obtenerDistribucionClasificacionInstancia(String instancia) {
	    double[] valorInstancia = new double[dataset.numAttributes()];
	    valorInstancia[0] = dataset.attribute(0).addStringValue(instancia);
        dataset.add(new DenseInstance(1.0, valorInstancia));
	    double distribucionClasificacion=-1;
	    double resultadoClasificacion=-1;
		try {
			resultadoClasificacion = clasificadorNaiveBayes.classifyInstance(dataset.firstInstance());
			distribucionClasificacion = clasificadorNaiveBayes.distributionForInstance(dataset.firstInstance())[(int)resultadoClasificacion];
		} catch (Exception excepcion) {
			// TODO Auto-generated catch block
			excepcion.printStackTrace();
		}
		dataset.remove(0);
    	return distribucionClasificacion;
	}
}
