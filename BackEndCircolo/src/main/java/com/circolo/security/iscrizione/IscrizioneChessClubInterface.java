package com.circolo.security.iscrizione;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.transaction.Transactional;

public interface IscrizioneChessClubInterface {
	
	@GetMapping("/Get")
	public ResponseEntity<List<Iscrizione>>getIscrizione();
	
//	public ResponseEntity<List<Iscrizione>> getIscrizioneByIdUtent(@RequestBody IscrizioneRequestById richiesta){
	@PostMapping("/GetByUserId")
	public ResponseEntity<IscrizioneRequestInfo> getIscrizioneByIdUtent(@RequestBody IscrizioneRequestById richiesta);
	
	// Definisco le mie richieste Http per la gestione dei tornei
	
	//@Transactional	// Questo perché vado a modificare alcuni campi presenti nel databse 
	
	@PostMapping("/Nuovo")
	public ResponseEntity<IscrizioneResponse> newTorneo(@RequestBody Iscrizione iscrizione);
	
	@PostMapping("/CercaById")
	public ResponseEntity<IscrizioneResponse> cercaIscrizione(@RequestBody IscrizioneRequestById richiesta );
	
	@Transactional
	@PutMapping("/AggiornaPunteggio")
	public ResponseEntity<IscrizioneResponse> modifyDataTorneo(@RequestBody IscrizioneRequestModifyPunteggio richiesta);
	
	@PostMapping("/RiempiTorneo")
	public ResponseEntity<IscrizioneResponse> newTorneo(@RequestBody IscrizioneRequestById richiesta);
	
	@DeleteMapping("/Elimina")
	public ResponseEntity<IscrizioneResponse> deleteIscrizione(@RequestBody IscrizioneDeleteRequest richiesta);
	
	@Transactional
	@DeleteMapping("/EliminaIscrizione")
	public ResponseEntity<IscrizioneResponse> EliminaTorneo(@RequestBody IscrizioneRequestById richiesta);
	  
}
