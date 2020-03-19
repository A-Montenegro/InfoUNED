package es.infouned.procesamientoLenguajeNatural;

import java.util.ArrayList;

import es.infouned.estudios.Estudio;


public class Frase {
	private String textoFrase;
	private ArrayList<String> tokens;
	private ArrayList<String> posTags;
	private ArrayList<String> nerTags;
	private ArrayList<String> relaciones;
	private String clasificacion;
	private ArrayList<Estudio> estudiosAludidos;
	
	public Frase() {
		textoFrase = new String();
		tokens = new ArrayList<String>();
		posTags = new ArrayList<String>();
		nerTags = new ArrayList<String>();
		relaciones = new ArrayList<String>();
		estudiosAludidos = new ArrayList<Estudio>();
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
	
	public String getClasificacion() {
		return clasificacion;
	}
	
	public ArrayList<Estudio> getEstudiosAludidos() {
		return estudiosAludidos;
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
	
	public void setClasificacion(String clasificacion) {
		this.clasificacion = clasificacion;
	}
	
	public void setEstudiosAludidos(ArrayList<Estudio> estudiosAludidos) {
		this.estudiosAludidos = estudiosAludidos;
	}

}
