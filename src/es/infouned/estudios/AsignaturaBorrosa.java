package es.infouned.estudios;

import java.util.ArrayList;

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