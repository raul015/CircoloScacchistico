package com.circolo.security.partita;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.transaction.Transactional;

public interface PartitaChessClubInterface {

	
	@GetMapping("/Get")
	public ResponseEntity<List<Partita>>getIscrizione();
	@PostMapping("/GetByTorneoId")
	public ResponseEntity<List<Partita>> getPartitaById(@RequestBody PartitaRequestByIdTorneo richiesta);
	
	
	@PostMapping("/Nuovo")
	public ResponseEntity<PartitaResponse> newPartita(@RequestBody Partita partita);
	
	@PostMapping("/NuovaListaPartite")
	public ResponseEntity<PartitaResponse> listaPartite(@RequestBody List<Partita> partita);
	
	
	@PostMapping("/AggiuntaAutomatica")
	
	public ResponseEntity<PartitaResponse> aggiuntaAutomatica(@RequestBody PartitaRequestByIdTorneo richiesta);
	@Transactional
	@PutMapping("/ModifyRisultato")
	public ResponseEntity<PartitaResponse> risultatoPartita(@RequestBody PartitaRequestRisultato richiesta);
	
}
