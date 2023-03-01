package com.circolo.security.iscrizione;

import java.util.ArrayList;
import java.util.List;

import com.circolo.security.torneo.Torneo;



public class IscrizioneRequestInfo {
	
//	private List<Iscrizione> lista_iscrizioni = new ArrayList<>();
	private List<Torneo> lista_tornei = new ArrayList<>();
	
	
//	public List<Iscrizione> getLista_iscrizioni() {
//		return lista_iscrizioni;
//	}
//	public void setLista_iscrizioni(List<Iscrizione> lista_iscrizioni) {
//		this.lista_iscrizioni = lista_iscrizioni;
//	}
	public List<Torneo> getLista_tornei() {
		return lista_tornei;
	}
	public void setLista_tornei(List<Torneo> lista_tornei) {
		this.lista_tornei = lista_tornei;
	}
	


}
