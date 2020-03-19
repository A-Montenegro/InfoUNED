package es.infouned.aprendizajeAutomatico;

import weka.classifiers.meta.FilteredClassifier;
import weka.core.DenseInstance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class ClasificadorNaiveBayes implements Clasificador{
	private FilteredClassifier clasificadorNaiveBayes;
	private DataSource estructuraDataSet;
	private Instances dataset;
	
	public ClasificadorNaiveBayes(String rutaModeloEntrenado, String rutaFicheroEstructuraDataSet) {
		try {
			this.clasificadorNaiveBayes = (FilteredClassifier) weka.core.SerializationHelper.read(rutaModeloEntrenado.substring(1, rutaModeloEntrenado.length()));
			this.estructuraDataSet = new DataSource(rutaFicheroEstructuraDataSet.substring(1, rutaFicheroEstructuraDataSet.length()));
			this.dataset = estructuraDataSet.getDataSet();
		    dataset.setClassIndex(dataset.numAttributes()-1);
		}catch(Exception excepcion) {
			excepcion.printStackTrace();
		}
	}
	
	public String clasificarInstancia(String instancia) {
	    double[] valorInstancia = new double[dataset.numAttributes()];
	    valorInstancia[0] = dataset.attribute(0).addStringValue(instancia);
        dataset.add(new DenseInstance(1.0, valorInstancia));
	    double resultadoClasificacion=-1;
		try {
			resultadoClasificacion = clasificadorNaiveBayes.classifyInstance(dataset.firstInstance());
		} catch (Exception excepcion) {
			// TODO Auto-generated catch block
			excepcion.printStackTrace();
		}
		String nombreClase = obtenerNombreClase((int) resultadoClasificacion);
    	return nombreClase;
	}
	
	private String obtenerNombreClase(int indice) {
		switch (indice) {
		case 0:
			return "saludo";
		case 1:
			return "despedida";
		case 2:
			return "informacionContacto";
		case 3:
			return "informacionMatricula";
		case 4:
			return "solicitudEstadisticaRendimientoAsignatura";
		case 5:
			return "solicitudEstadisticaRendimientoTitulacion";
		case 6:
			return "solicitudEstadisticaRendimientoTopAsignatura";
		case 7:
			return "solicitudEstadisticaRendimientoTopTitulacion";
		case 8:
			return "solicitudInformacionGenerica";
		case 9:
			return "solicitudMatriculadosAsignatura";
		case 10:
			return "solicitudMatriculadosTitulacion";
		case 11:
			return "solicitudPreciosTitulacion";
		case 12:
			return "solicitudValoracionEstudiantilAsignatura";
		case 13:
			return "solicitudValoracionEstudiantilTitulacion";
		case 14:
			return "solicitudValoracionEstudiantilTopAsignatura";
		case 15:
			return "solicitudValoracionEstudiantilTopTitulacion";
		default:
			return "ERROR";
		}
	}
}
