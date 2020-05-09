package es.infouned.procesamientoLenguajeNatural;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Stack;

import edu.stanford.nlp.ie.util.RelationTriple;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreSentence;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import es.infouned.estructurasDeDatos.Par;
import es.infouned.estudios.CriterioConsultaSQL;
import es.infouned.estudios.Estudio;
import es.infouned.estudios.Estudio.TipoEstudio;
import es.infouned.estudios.FactoriaEstudio;
import es.infouned.estudios.IndicadorOrdenamiento;
import es.infouned.estudios.NivelEstudios;
import es.infouned.estudios.ParametroEstadistico;
import es.infouned.principal.Configuracion;
import es.infouned.utilidades.ProcesamientoDeTexto;

/**
 * Clase que carga los modelos de procesamiento de lenguaje natural necesarios para hacer funcionar el StanfordCoreNLP.
 * Una vez cargados, mediante el campo 'pipeline', que no es más que una instancia de la clase 'StanfordCoreNLP', podremos hacer uso de las funcionalidades que nos ofrece.
 * @author Alberto Martínez Montenegro
 *
 */
public class ProcesadorLenguajeNaturalStanford implements ProcesadorLenguajeNatural{
	private StanfordCoreNLP pipeline;
	private Properties propiedadesNLP;
	private CoreDocument documentoNucleo;
	private ArrayList<Frase> frases;
	
	public ProcesadorLenguajeNaturalStanford(String rutaFicheroConfigunarcionStanfordNLP) {
		propiedadesNLP = new Properties();
		cargarPropiedades(rutaFicheroConfigunarcionStanfordNLP);
	    pipeline = new StanfordCoreNLP(propiedadesNLP);
	}
	
	private  void cargarPropiedades(String rutaFicheroConfigunarcionStanfordNLP) {
    	try {
    		InputStream inputStreamConfiguracion = ProcesadorLenguajeNaturalStanford.class.getResourceAsStream(rutaFicheroConfigunarcionStanfordNLP); 
    		propiedadesNLP.load(inputStreamConfiguracion);
		} catch (IOException e) {
			System.out.println("No se ha podido acceder al fichero de configuración NLP('" + 
					rutaFicheroConfigunarcionStanfordNLP + "'), la ejecución no puede continuar.");
			e.printStackTrace();
		}
	}

	//@SuppressWarnings({ "unchecked", "rawtypes" })
	public void procesarTextoObjetivoDeAnalisis(String textoObjetivoDeAnalisis) {
	    documentoNucleo = new CoreDocument(textoObjetivoDeAnalisis);
	    pipeline.annotate(documentoNucleo);
	    frases = new ArrayList<Frase>();
	    for(CoreSentence coreSentence: documentoNucleo.sentences()) {
	    	Frase frase = new Frase();
	    	frase.setTextoFrase(coreSentence.text());
			ArrayList<String> tokens = new ArrayList<String>();
			for(CoreLabel token: coreSentence.tokens()) {
				String[] divisionGuionToken = token.toString().split("-");
				tokens.add(divisionGuionToken[0]);
			}
	    	frase.setTokens(tokens);
	    	frase.setPosTags((ArrayList<String>) coreSentence.posTags());
	    	frase.setNerTags((ArrayList<String>) coreSentence.nerTags());
	    	ArrayList<String> relations = new ArrayList<String>();
			for(RelationTriple relation: coreSentence.relations()) {
				relations.add(relation.toString());
			}
	    	frase.setRelaciones(relations);
	    	Par<ArrayList<ParametroEstadistico>,ArrayList<ParametroEstadistico>> parametrosEstadisticosAludidos = obtenerParametrosEstadisticosAludidos(frase.getTextoFrase());
	    	frase.setParametrosEstadisticosTitulacionAludidos(parametrosEstadisticosAludidos.getObjeto1());
	    	frase.setParametrosEstadisticosAsignaturaAludidos(parametrosEstadisticosAludidos.getObjeto2());
	    	Stack<IndicadorOrdenamiento> indicadoresOrdenamiento = obtenerIndicadoresOrdenamientoAludidos(frase.getTextoFrase());
	    	frase.setIndicadoresOrdenamientoAludidos(indicadoresOrdenamiento);
	    	Stack<NivelEstudios> nivelesEstudiosAludidos = obtenerNivelesEstudiosAludidos(frase.getTextoFrase());
	    	frase.setNivelesEstudiosAludidos(nivelesEstudiosAludidos);
	    	Stack<CriterioConsultaSQL> criteriosConsultaSQLAludidos = obtenerCriteriosConsultaSQLAludidos(frase.getTextoFrase());
	    	frase.setCriteriosConsultaSQLAludidos(criteriosConsultaSQLAludidos);
	    	ArrayList<Estudio> vectorEstudiosAludidos = obtenerVectorEstudiosAludidosDesdeNerTags((ArrayList<String>) coreSentence.nerTags());
	    	frase.setVectorEstudiosAludidos(vectorEstudiosAludidos);
	    	ArrayList<Estudio> estudiosAludidos = new ArrayList<Estudio>();
	    	for(Estudio estudioAludido: vectorEstudiosAludidos) {
	    		if(estudioAludido!=null && !estudiosAludidos.contains(estudioAludido)) {
	    			estudiosAludidos.add(estudioAludido);
	    		}
	    	}
	    	frase.setEstudiosAludidos(estudiosAludidos);
	    	frases.add(frase);
	    }
	}
	
	private Par<ArrayList<ParametroEstadistico>,ArrayList<ParametroEstadistico>> obtenerParametrosEstadisticosAludidos(String textoFrase){
		ArrayList<ParametroEstadistico> parametrosEstadisticosTitulacionAludidos = new ArrayList<ParametroEstadistico>();
		ArrayList<ParametroEstadistico> parametrosEstadisticosAsignaturaAludidos = new ArrayList<ParametroEstadistico>();
		for (ParametroEstadistico parametroEstadistico: Configuracion.getParametrosEstadisticos()) {
				for(String nomenclatura: parametroEstadistico.getNomenclaturas()) {
					String textoFraseNormalizado = ProcesamientoDeTexto.normalizarTexto(textoFrase);
					String nomenclaturaNormalizada = ProcesamientoDeTexto.normalizarTexto(nomenclatura);
					if(textoFraseNormalizado.contains(nomenclaturaNormalizada)) {
						if(parametroEstadistico.getTipoEstudio() == TipoEstudio.AMBIGUO || parametroEstadistico.getTipoEstudio() == TipoEstudio.TITULACION) parametrosEstadisticosTitulacionAludidos.add(parametroEstadistico);
						if(parametroEstadistico.getTipoEstudio() == TipoEstudio.AMBIGUO || parametroEstadistico.getTipoEstudio() == TipoEstudio.ASIGNATURA) parametrosEstadisticosAsignaturaAludidos.add(parametroEstadistico);
						break;
					}
				}
		}
		Par<ArrayList<ParametroEstadistico>,ArrayList<ParametroEstadistico>> parametrosEstadisticosAludidos = new Par<ArrayList<ParametroEstadistico>,ArrayList<ParametroEstadistico>>(parametrosEstadisticosTitulacionAludidos, parametrosEstadisticosAsignaturaAludidos);
		return parametrosEstadisticosAludidos;
	}
	
	private Stack<IndicadorOrdenamiento> obtenerIndicadoresOrdenamientoAludidos(String textoFrase){
		Stack<IndicadorOrdenamiento> indicadoresOrdenamiento = new Stack<IndicadorOrdenamiento>();
		for (IndicadorOrdenamiento indicadorOrdenamiento: Configuracion.getIndicadoresOrdenamiento()) {
				for(String nomenclatura: indicadorOrdenamiento.getNomenclaturas()) {
					String textoFraseNormalizado = ProcesamientoDeTexto.normalizarTexto(textoFrase);
					String nomenclaturaNormalizada = ProcesamientoDeTexto.normalizarTexto(nomenclatura);
					if(textoFraseNormalizado.contains(nomenclaturaNormalizada)) {
						indicadoresOrdenamiento.add(indicadorOrdenamiento);
						break;
					}
				}
		}
		return indicadoresOrdenamiento;
	}

	private Stack<NivelEstudios> obtenerNivelesEstudiosAludidos(String textoFrase){
		Stack<NivelEstudios> nivelesEstudios = new Stack<NivelEstudios>();
		String textoFraseNormalizado = ProcesamientoDeTexto.normalizarTexto(textoFrase);
		for (NivelEstudios nivelEstudios: Configuracion.getNivelesEstudios()) {
				for(String nomenclatura: nivelEstudios.getNomenclaturas()) {
					String nomenclaturaNormalizada = ProcesamientoDeTexto.normalizarTexto(nomenclatura);
					if(textoFraseNormalizado.contains(nomenclaturaNormalizada)) {
						nivelesEstudios.push(nivelEstudios);
						break;
					}
				}
		}
		return nivelesEstudios;
	}
	
	private Stack<CriterioConsultaSQL> obtenerCriteriosConsultaSQLAludidos(String textoFrase){
		Stack<CriterioConsultaSQL> criteriosConsultaSQLAludidos = new Stack<CriterioConsultaSQL>();
		for (CriterioConsultaSQL criterioConsultaSQL: Configuracion.getCriteriosConsultaSQL()) {
				for(String nomenclatura: criterioConsultaSQL.getNomenclaturas()) {
					String textoFraseNormalizado = ProcesamientoDeTexto.normalizarTexto(textoFrase);
					String nomenclaturaNormalizada = ProcesamientoDeTexto.normalizarTexto(nomenclatura);
					if(textoFraseNormalizado.contains(nomenclaturaNormalizada)) {
						criteriosConsultaSQLAludidos.add(criterioConsultaSQL);
						break;
					}
				}
		}
		return criteriosConsultaSQLAludidos;
	}
	
	private ArrayList<Estudio> obtenerVectorEstudiosAludidosDesdeNerTags(ArrayList<String> nerTags){	
    	ArrayList<Estudio> vectorEstudiosAludidos = new ArrayList<Estudio>();
    	Estudio estudio = null;
    	String anterior= new String();
    	for(int indiceNerTag = 0; indiceNerTag < nerTags.size(); indiceNerTag++) {
    		if(anterior.equals(nerTags.get(indiceNerTag))) {
    			vectorEstudiosAludidos.add(estudio);
    		}
    		else {		
	    		anterior = new String(nerTags.get(indiceNerTag));
	    		String[] partesNerTag = nerTags.get(indiceNerTag).split("_");
	    		if(partesNerTag.length > 2 && partesNerTag[0].equals("INFOUNED")) {
		    			switch(partesNerTag[1]) {
		    			case "TITULACION":
		    				assert (partesNerTag.length == 3);
		    				int idTitulacion = Integer.parseInt(partesNerTag[2]);
		    				estudio = FactoriaEstudio.crearTitulacionPorConsultaSQL(idTitulacion);
		    			break;
		    			case "ASIGNATURA":
							String idAsignatura= partesNerTag[2];
		    				if(partesNerTag.length > 3) {
		    					for (int indicePartes = 3; indicePartes < partesNerTag.length; indicePartes++) {
		    						idAsignatura += "_" + partesNerTag[indicePartes];
		    					}
		    					estudio = FactoriaEstudio.crearAsignaturaBorrosaPorConsultaSQL(idAsignatura);
		    				}
		    				else {
		    					estudio = FactoriaEstudio.crearAsignaturaPorConsultaSQL(idAsignatura);
		    				}
		    			break;
		    			}
		    			vectorEstudiosAludidos.add(estudio);
	    		}
	    		else {
	    			vectorEstudiosAludidos.add(null);
	    		}
    		}
    	}    	
    	return vectorEstudiosAludidos;
	}

	public ArrayList<Frase> obtenerFrases(){
		return frases;
	}
	
	public Frase obtenerFrase(int indiceFrase){
		return frases.get(indiceFrase);
	}
	
	public String obtenerAnaliticaVisualDeTexto(String saltoDeLinea) {
	  	String cadenaDeTextoResultados = new String();
	    for (int indiceFrase=0; indiceFrase < obtenerFrases().size(); indiceFrase++) {
	    	Frase frase = obtenerFrases().get(indiceFrase);
		    for (int indiceToken=0; indiceToken < frase.getTokens().size(); indiceToken++) {
		    	String token = frase.getTokens().get(indiceToken);
		    	cadenaDeTextoResultados += "El Token número " + indiceToken + " es: " + token + saltoDeLinea;
		    }
		    String textoFrase = frase.getTextoFrase();
		    cadenaDeTextoResultados += "El texto de la frase a analizar es: ";
		    cadenaDeTextoResultados += textoFrase;
		    cadenaDeTextoResultados += saltoDeLinea;
		    ArrayList<String> posTags =  frase.getPosTags();
		    cadenaDeTextoResultados += "POS Tags: ";
		    cadenaDeTextoResultados += posTags;
		    cadenaDeTextoResultados += saltoDeLinea;
		    List<String> nerTags = frase.getNerTags();
		    cadenaDeTextoResultados += "NER tags: ";
		    cadenaDeTextoResultados += nerTags;
		    cadenaDeTextoResultados += saltoDeLinea;
		    ArrayList<Estudio> vectorEstudiosAludidos = frase.getVectorEstudiosAludidos();
		    int numeroEstudiosAludidos = frase.getNumeroEstudiosAludidos();
		    cadenaDeTextoResultados += "Estudios aludidos: ";
		    cadenaDeTextoResultados += vectorEstudiosAludidos;
		    cadenaDeTextoResultados += saltoDeLinea;
		    cadenaDeTextoResultados += "Número de estudios aludidos: ";
		    cadenaDeTextoResultados += numeroEstudiosAludidos;
		    cadenaDeTextoResultados += saltoDeLinea;
		    ArrayList<String> relations = frase.getRelaciones();
		    if (relations.size() > 0) {
			    cadenaDeTextoResultados +="Ejemplo relación: ";
			    cadenaDeTextoResultados += relations.get(0);
			    cadenaDeTextoResultados += saltoDeLinea;
		    }
	    }
	    return cadenaDeTextoResultados;
	}
}
