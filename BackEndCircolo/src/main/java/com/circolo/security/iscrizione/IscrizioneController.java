package com.circolo.security.iscrizione;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



import jakarta.transaction.Transactional;

@RestController
@RequestMapping(path="/api/v1/Iscrizione")
public class IscrizioneController {
	
	private final IscrizioneService iscrizioneService;
	
	@Autowired
	public IscrizioneController(IscrizioneService iscrizioneService) {
		this.iscrizioneService = iscrizioneService;
	}
	
	
	
	@GetMapping("/Get")
	public ResponseEntity<List<Iscrizione>>getIscrizione(){
		return ResponseEntity.ok(iscrizioneService.getIscrizione());
	}
	
	
//	public ResponseEntity<List<Iscrizione>> getIscrizioneByIdUtent(@RequestBody IscrizioneRequestById richiesta){
	@PostMapping("/GetByUserId")
	public ResponseEntity<IscrizioneRequestInfo> getIscrizioneByIdUtent(@RequestBody IscrizioneRequestById richiesta){

		return ResponseEntity.ok(iscrizioneService.getIscrizioneByIdUtent(richiesta));
	}
	
	// Definisco le mie richieste Http per la gestione dei tornei
	
	//@Transactional	// Questo perché vado a modificare alcuni campi presenti nel databse 
	
	@PostMapping("/Nuovo")
	public ResponseEntity<IscrizioneResponse> newTorneo(@RequestBody Iscrizione iscrizione){
		
		return ResponseEntity.ok(iscrizioneService.addNewIscrizione(iscrizione));
	}
	
	@PostMapping("/CercaById")
	public ResponseEntity<IscrizioneResponse> cercaIscrizione(@RequestBody IscrizioneRequestById richiesta ){
		
		return ResponseEntity.ok(iscrizioneService.getUtenteByIscrizione(richiesta));
	}
	
	
	@Transactional
	@PutMapping("/AggiornaPunteggio")
	public ResponseEntity<IscrizioneResponse> modifyDataTorneo(@RequestBody IscrizioneRequestModifyPunteggio richiesta){
		
		return ResponseEntity.ok(iscrizioneService.modifyPunteggio(richiesta));
	}
	
	
	@PostMapping("/RiempiTorneo")
	public ResponseEntity<IscrizioneResponse> newTorneo(@RequestBody IscrizioneRequestById richiesta){
		
		return ResponseEntity.ok(iscrizioneService.RiempiTorneo(richiesta));
	}
	
	@DeleteMapping("/Elimina")
	public ResponseEntity<IscrizioneResponse> deleteIscrizione(@RequestBody IscrizioneDeleteRequest richiesta){
		
		return ResponseEntity.ok(iscrizioneService.deleteIscrizione(richiesta));
	}
	
	@Transactional
	@DeleteMapping("/EliminaIscrizione")
	public ResponseEntity<IscrizioneResponse> EliminaTorneo(@RequestBody IscrizioneRequestById richiesta){
		
		return ResponseEntity.ok(iscrizioneService.EliminaTorneo(richiesta));
	}
	  

}
