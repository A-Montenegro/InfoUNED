package es.infouned.procesamientoLenguajeNatural;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import edu.stanford.nlp.ie.util.RelationTriple;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreSentence;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

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
				tokens.add(token.toString());
			}
	    	frase.setTokens(tokens);
	    	frase.setPosTags((ArrayList<String>)coreSentence.posTags());
	    	frase.setNerTags((ArrayList<String>)coreSentence.nerTags());
	    	ArrayList<String> relations = new ArrayList<String>();
			for(RelationTriple relation: coreSentence.relations()) {
				relations.add(relation.toString());
			}
	    	frase.setRelaciones(relations);
	    	frases.add(frase);
	    }
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
		    ArrayList<String> relations = frase.getRelaciones();
		    if (relations.size() >0) {
			    cadenaDeTextoResultados +="Ejemplo relación: ";
			    cadenaDeTextoResultados += relations.get(0);
			    cadenaDeTextoResultados += saltoDeLinea;
		    }
	    }
	    return cadenaDeTextoResultados;
	}
}
