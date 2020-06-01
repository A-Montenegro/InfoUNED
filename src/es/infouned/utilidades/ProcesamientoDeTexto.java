package es.infouned.utilidades;

import java.text.Normalizer;
import java.util.Stack;


import es.infouned.estructurasDeDatos.Par;
import es.infouned.estudios.CriterioConsultaSQL;

/**
 * Clase que se utiliza para procesar texto eliminando o añadiendo ciertos caracteres. Utiliza métodos estáticos para ofrecer diversas operaciones de transformación de texto.
 * @author Alberto Martínez Montenegro
 * 
 */
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
				if(iteracion == criteriosConsultaSQL.size()) separador = " y";
				if(iteracion != 1 && iteracion != criteriosConsultaSQL.size()) separador = ",";
				if(iteracion == 1) separador = "";
				cadenaTextoCriteriosSQL += separador + " " + criterioConsultaSQL.getNombre();
				iteracion++;
			}
		}
		return cadenaTextoCriteriosSQL;
	}
	
	public static String anadirHiperVinculosEnlacesHtml(String cadenaDeTextoSinVinculos) {
		String cadenaDeTextoConVinculos = new String(cadenaDeTextoSinVinculos);
		Par<String, String> cadenasDeTexto = new Par<String, String>(cadenaDeTextoConVinculos, cadenaDeTextoSinVinculos);
		cadenasDeTexto = procesarVinculos(cadenasDeTexto, "http://");
		cadenasDeTexto = procesarVinculos(cadenasDeTexto, "https://");
		return cadenasDeTexto.getObjeto1();
	}
	
	public static String sustituirSaltosDeLineaPorCaracteresEspeciales(String cadenaDeTexto, String caracteresEspeciales) {
    	if(cadenaDeTexto.contains("\r\n")) {
    		String partesTextoRespuesta[] = cadenaDeTexto.split("\r\n");
    		cadenaDeTexto = partesTextoRespuesta[0];
	        for (int indicePartes = 1; indicePartes < partesTextoRespuesta.length; indicePartes++) {
	        	cadenaDeTexto += partesTextoRespuesta[indicePartes] + caracteresEspeciales;
	        }
	    }
    	return cadenaDeTexto;
	}
	
	private static Par<String, String> procesarVinculos(Par<String, String> cadenasDeTexto, String caracteresClaveVinculo) {
		String cadenaDeTextoConVinculos = cadenasDeTexto.getObjeto1();
		String cadenaDeTextoSinVinculos = cadenasDeTexto.getObjeto2();
		while(cadenaDeTextoSinVinculos.contains(caracteresClaveVinculo)) {
			int indiceComienzoHiperVinculo = cadenaDeTextoSinVinculos.indexOf(caracteresClaveVinculo) + caracteresClaveVinculo.length() - 1;
			char caracterAnalizado;
			String cadenaHiperVinculo = new String(caracteresClaveVinculo);
			caracterAnalizado = cadenaDeTextoSinVinculos.charAt(indiceComienzoHiperVinculo);
			while(caracterAnalizado != ' ' && indiceComienzoHiperVinculo + 1 < cadenaDeTextoSinVinculos.length()) {
				indiceComienzoHiperVinculo ++;
				caracterAnalizado = cadenaDeTextoSinVinculos.charAt(indiceComienzoHiperVinculo);
				cadenaHiperVinculo += caracterAnalizado;
			}
			cadenaDeTextoSinVinculos = cadenaDeTextoSinVinculos.replace(cadenaHiperVinculo, "");
			cadenaDeTextoConVinculos = cadenaDeTextoConVinculos.replace(cadenaHiperVinculo, generarHiperVinculo(cadenaHiperVinculo));
		}
		return new Par<String, String>(cadenaDeTextoConVinculos, cadenaDeTextoSinVinculos);
	}
	
	private static String generarHiperVinculo(String cadenaHiperVinculo) {
		String hiperVinculo = new String();
		hiperVinculo = "<a href='" + cadenaHiperVinculo + "' style='cursor:pointer;' target='_blank'>" + cadenaHiperVinculo + "</a>";
		return hiperVinculo;
	}

}
