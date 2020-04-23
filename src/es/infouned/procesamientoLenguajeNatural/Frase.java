package es.infouned.procesamientoLenguajeNatural;

import java.util.ArrayList;
import java.util.Stack;

import es.infouned.estudios.CriterioConsultaSQL;
import es.infouned.estudios.Estudio;
import es.infouned.estudios.IndicadorOrdenamiento;
import es.infouned.estudios.NivelEstudios;
import es.infouned.estudios.ParametroEstadistico;


public class Frase {
	private String textoFrase;
	private ArrayList<String> tokens;
	private ArrayList<String> posTags;
	private ArrayList<String> nerTags;
	private ArrayList<String> relaciones;
	private ArrayList<ParametroEstadistico> parametrosEstadisticosTitulacionAludidos;
	private ArrayList<ParametroEstadistico> parametrosEstadisticosAsignaturaAludidos;
	private String clasificacion;
	private ArrayList<Estudio> vectorEstudiosAludidos;
	private ArrayList<Estudio> estudiosAludidos;
	private Stack<NivelEstudios> nivelesEstudiosAludidos;
	private Stack<IndicadorOrdenamiento> indicadoresOrdenamientoAludidos;
	private Stack<CriterioConsultaSQL> criteriosConsultaSQLAludidos;

	public Frase() {
		textoFrase = new String();
		tokens = new ArrayList<String>();
		posTags = new ArrayList<String>();
		nerTags = new ArrayList<String>();
		relaciones = new ArrayList<String>();
		parametrosEstadisticosTitulacionAludidos = new ArrayList<ParametroEstadistico>();
		parametrosEstadisticosAsignaturaAludidos = new ArrayList<ParametroEstadistico>();
		clasificacion = new String();
		vectorEstudiosAludidos = new ArrayList<Estudio>();
		estudiosAludidos = new ArrayList<Estudio>();
		indicadoresOrdenamientoAludidos = new Stack<IndicadorOrdenamiento>();
		nivelesEstudiosAludidos = new Stack<NivelEstudios>();
		criteriosConsultaSQLAludidos = new Stack<CriterioConsultaSQL>();	
	}
	
	public Stack<NivelEstudios> getNivelesEstudiosAludidos() {
		return nivelesEstudiosAludidos;
	}

	public void setNivelesEstudiosAludidos(Stack<NivelEstudios> nivelesEstudiosAludidos) {
		this.nivelesEstudiosAludidos = nivelesEstudiosAludidos;
	}

	public String getTextoFrase() {
		return textoFrase;
	}
	
	public ArrayList<String> getTokens(){
		return tokens;
	}
	
	public ArrayList<String> getPosTags(){
		return posTags;
	}
	
	public ArrayList<String> getNerTags(){
		return nerTags;
	}
	
	public ArrayList<String> getRelaciones(){
		return relaciones;
	}
	
	public ArrayList<ParametroEstadistico> getParametrosEstadisticosTitulacionAludidos(){
		return parametrosEstadisticosTitulacionAludidos;
	}	

	public ArrayList<ParametroEstadistico> getParametrosEstadisticosAsignaturaAludidos(){
		return parametrosEstadisticosAsignaturaAludidos;
	}	
	
	public String getClasificacion() {
		return clasificacion;
	}
	
	public ArrayList<Estudio> getVectorEstudiosAludidos() {
		return vectorEstudiosAludidos;
	}

	public ArrayList<Estudio> getEstudiosAludidos() {
		return estudiosAludidos;
	}
	
	public int getNumeroEstudiosAludidos() {
		return estudiosAludidos.size();
	}
	
	public Stack<IndicadorOrdenamiento> getIndicadoresOrdenamientoAludidos() {
		return indicadoresOrdenamientoAludidos;
	}

	public void setIndicadoresOrdenamientoAludidos(Stack<IndicadorOrdenamiento> indicadoresOrdenamientoAludidos) {
		this.indicadoresOrdenamientoAludidos = indicadoresOrdenamientoAludidos;
	}

	public Stack<CriterioConsultaSQL> getCriteriosConsultaSQLAludidos() {
		return criteriosConsultaSQLAludidos;
	}

	public void setCriteriosConsultaSQLAludidos(Stack<CriterioConsultaSQL> criteriosConsultaSQLAludidos) {
		this.criteriosConsultaSQLAludidos = criteriosConsultaSQLAludidos;
	}
	
	public void setTextoFrase(String textoFrase) {
		this.textoFrase = textoFrase;
	}
	
	public void setTokens(ArrayList<String> tokens){
		this.tokens = tokens;
	}
	
	public void setPosTags(ArrayList<String> posTags){
		this.posTags = posTags;
	}
	
	public void setNerTags(ArrayList<String> nerTags){
		this.nerTags = nerTags;
	}
	
	public void setRelaciones(ArrayList<String> relaciones){
		this.relaciones = relaciones;
	}
	
	public void setParametrosEstadisticosTitulacionAludidos(
			ArrayList<ParametroEstadistico> parametrosEstadisticosTitulacionAludidos) {
		this.parametrosEstadisticosTitulacionAludidos = parametrosEstadisticosTitulacionAludidos;
	}

	public void setParametrosEstadisticosAsignaturaAludidos(
			ArrayList<ParametroEstadistico> parametrosEstadisticosAsignaturaAludidos) {
		this.parametrosEstadisticosAsignaturaAludidos = parametrosEstadisticosAsignaturaAludidos;
	}

	public void setClasificacion(String clasificacion) {
		this.clasificacion = clasificacion;
	}
	
	public void setVectorEstudiosAludidos(ArrayList<Estudio> vectorEstudiosAludidos) {
		this.vectorEstudiosAludidos = vectorEstudiosAludidos;
	}

	public void setEstudiosAludidos(ArrayList<Estudio> estudiosAludidos) {
		this.estudiosAludidos = estudiosAludidos;
	}

}
