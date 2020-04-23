package es.infouned.utilidades;

import java.text.Normalizer;
import java.util.Stack;

import es.infouned.estudios.CriterioConsultaSQL;

public class ProcesamientoDeTexto {
	
	public static String normalizarTexto(String texto) {
		String textoNormalizado = Normalizer.normalize(texto, Normalizer.Form.NFD);
		textoNormalizado = textoNormalizado.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
		textoNormalizado = textoNormalizado.replaceAll("\\¿", "");
		textoNormalizado = textoNormalizado.replaceAll("\\?", "");
		String textoNormalizadoEnMinusculas = textoNormalizado.toLowerCase();
		textoNormalizadoEnMinusculas = textoNormalizadoEnMinusculas.replaceAll(" al ", " a el ");
		return textoNormalizadoEnMinusculas;
	}
	
	public static int contarPalabras(String texto) {
		String textoNormalizado = normalizarTexto(texto);
		String partesTexto[] = textoNormalizado.split(" ");
		int numeroPalabras = partesTexto.length;
		return numeroPalabras;
	}
	
	public static String componerCadenaTextoCriteriosSQL(Stack<CriterioConsultaSQL> criteriosConsultaSQL) {
		String cadenaTextoCriteriosSQL= new String("");
		if(!criteriosConsultaSQL.isEmpty()) {
			cadenaTextoCriteriosSQL = " que cumplen el criterio de ";
			String separador= new String("");
			int iteracion = 1;
			for(CriterioConsultaSQL criterioConsultaSQL: criteriosConsultaSQL) {
				if(iteracion == criteriosConsultaSQL.size()) separador = "y";
				if(iteracion != 1 && iteracion != criteriosConsultaSQL.size()) separador = ",";
				if(iteracion == 1) separador = "";
				cadenaTextoCriteriosSQL += separador + " " + criterioConsultaSQL.getNombre();
				iteracion++;
			}
		}
		return cadenaTextoCriteriosSQL;
	}
}
