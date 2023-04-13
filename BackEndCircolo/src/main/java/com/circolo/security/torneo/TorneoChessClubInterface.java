package com.circolo.security.torneo;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.circolo.security.iscrizione.Iscrizione;

import jakarta.transaction.Transactional;

public interface TorneoChessClubInterface {

	
	@GetMapping("/Get")
	public  ResponseEntity<List<Torneo>>  getTornei();
	
	 @PostMapping("/GetTorneo")
		public ResponseEntity<Torneo> getTorneo(@RequestBody TorneoRequestListaIscrizioni torneo);
	  // Voglio una richiesta che mi dia la lista delle iscrizioni per questo torneo...
	  
	  @PostMapping("/GetIscrizioni")
	  public  ResponseEntity<List<Iscrizione>>  getListaIscrizioniTorneo(@RequestBody TorneoRequestListaIscrizioni torneo);
	  

	  
	// Definisco le mie richieste Http per la gestione dei tornei
	
	@PostMapping("/Nuovo")
	public ResponseEntity<TorneoResponse> newTorneo(@RequestBody Torneo torneo);
	
	@Transactional
	@DeleteMapping("/Elimina")
	public ResponseEntity<TorneoResponse> deleteTorneo(@RequestBody TorneoDeleteRequest torneo);
	
	// Inizio della serie di mofiche del campo torneo 
	
	
	
	  @Transactional
	  @PutMapping("/ModificaData")
	public ResponseEntity<TorneoResponse> modifyDataTorneo(@RequestBody TorneoRequestModifyData torneo);
	  
	  
	  @Transactional
	  @PutMapping("/ModificaLuogo")
	public ResponseEntity<TorneoResponse> modifyLuogo(@RequestBody TorneoRequestModifyLuogo torneo);
	  
	  @Transactional
	  @PutMapping("/ModificaNTurni")
	public ResponseEntity<TorneoResponse> modifyNTurni(@RequestBody TorneoRequestModifyNTurni torneo);
	  
	  
	  @Transactional
	  @PutMapping("/ModificaTurno")
	public ResponseEntity<TorneoResponse> modifyTurno(@RequestBody TorneoRequestModifyTurno torneo);
	  
	  @Transactional
	  @PutMapping("/ModificaNome")
	public ResponseEntity<TorneoResponse> modifyNome(@RequestBody TorneoRequestModifyName torneo);
	  
	  @Transactional
	  @PutMapping("/ModificaStato")
	public ResponseEntity<TorneoResponse> modifyStato(@RequestBody TorneoRequestModifyStato torneo);
	  @Transactional
	  @PutMapping("/GeneraTurno")
	  public ResponseEntity<TorneoResponse> generateTurno(@RequestBody TorneoRequestModifyTurno richiesta);
	  
	  //classifica
	  
	  @PostMapping("/Classifica")
	  public ResponseEntity<List<ClassificaResponse>> classifica(@RequestBody TorneoRequestModifyTurno richiesta);
	 

}
