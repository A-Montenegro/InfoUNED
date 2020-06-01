package es.infouned.estudios;

import java.util.ArrayList;

/**
 * Clase que simboliza una asignatura ambigua. Su ambig�edad se debe a que comparte nombre con otras asignaturas diferentes.
 * @author Alberto Mart�nez Montenegro
 *
 */
public class AsignaturaBorrosa extends Estudio{
	private String idsAsignaturas;
	private ArrayList<Asignatura> asignaturasPosibles;
	
	
	public AsignaturaBorrosa(String idAsignatura, ArrayList<Asignatura> asignaturasPosibles) {
		super("Asignatura borrosa");
		this.idsAsignaturas = idAsignatura;
		this.asignaturasPosibles = asignaturasPosibles;	
	}

	public String getIdsAsignaturas() {
		return idsAsignaturas;
	}
	
	public ArrayList<Asignatura> getAsignaturasPosibles(){
		return asignaturasPosibles;
	}
}