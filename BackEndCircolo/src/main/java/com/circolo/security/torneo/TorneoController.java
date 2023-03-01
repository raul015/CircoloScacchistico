package com.circolo.security.torneo;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.circolo.security.iscrizione.Iscrizione;

import jakarta.transaction.Transactional;


@RestController
@RequestMapping(path="/api/v1/Torneo")
public class TorneoController {

	private final  TorneoService torneoSevice;
	
	
	@Autowired
	public TorneoController(TorneoService torneoService) {
		this.torneoSevice = torneoService;
	}
	
	@GetMapping("/Get")
	public  ResponseEntity<List<Torneo>>  getTornei(){
		
	 
		return  ResponseEntity.ok(torneoSevice.getTorneo());
	}
	
	 @PostMapping("/GetTorneo")
		public ResponseEntity<Torneo> getTorneo(@RequestBody TorneoRequestListaIscrizioni torneo){
			return ResponseEntity.ok(torneoSevice.getSingoloTorneo(torneo));
		}
	
	  // Voglio una richiesta che mi dia la lista delle iscrizioni per questo torneo...
	  
	  @PostMapping("/GetIscrizioni")
	  public  ResponseEntity<List<Iscrizione>>  getListaIscrizioniTorneo(@RequestBody TorneoRequestListaIscrizioni torneo){
		
		  return    ResponseEntity.ok(torneoSevice.getListaIscrizioniTorneo(torneo));  
	  }
	  

	  
	// Definisco le mie richieste Http per la gestione dei tornei
	
	@PostMapping("/Nuovo")
	public ResponseEntity<TorneoResponse> newTorneo(@RequestBody Torneo torneo){
		
		return ResponseEntity.ok(torneoSevice.addNewTorneo(torneo));
	}
	
	
	@Transactional
	@DeleteMapping("/Elimina")
	public ResponseEntity<TorneoResponse> deleteTorneo(@RequestBody TorneoDeleteRequest torneo){
		
		return ResponseEntity.ok(torneoSevice.deleteTorneo(torneo));
	}
	
	
	// Inizio della serie di mofiche del campo torneo 
	
	
	
	  @Transactional
	  @PutMapping("/ModificaData")
	public ResponseEntity<TorneoResponse> modifyDataTorneo(@RequestBody TorneoRequestModifyData torneo){
		
		return ResponseEntity.ok(torneoSevice.modifyDataTorneo(torneo));
	}
	  
	  
	  @Transactional
	  @PutMapping("/ModificaLuogo")
	public ResponseEntity<TorneoResponse> modifyLuogo(@RequestBody TorneoRequestModifyLuogo torneo){
		
		return ResponseEntity.ok(torneoSevice.modifyLuogo(torneo));
	}
	  
	  @Transactional
	  @PutMapping("/ModificaNTurni")
	public ResponseEntity<TorneoResponse> modifyNTurni(@RequestBody TorneoRequestModifyNTurni torneo){
		
		return ResponseEntity.ok(torneoSevice.modifyNTurni(torneo));
	}
	  
	  
	  @Transactional
	  @PutMapping("/ModificaTurno")
	public ResponseEntity<TorneoResponse> modifyTurno(@RequestBody TorneoRequestModifyTurno torneo){
		
		return ResponseEntity.ok(torneoSevice.modifyTurno(torneo));
	}
	  
	  @Transactional
	  @PutMapping("/ModificaNome")
	public ResponseEntity<TorneoResponse> modifyNome(@RequestBody TorneoRequestModifyName torneo){
		
		return ResponseEntity.ok(torneoSevice.modifyNome(torneo));
	}
	  
	  @Transactional
	  @PutMapping("/ModificaStato")
	public ResponseEntity<TorneoResponse> modifyStato(@RequestBody TorneoRequestModifyStato torneo){
		
		return ResponseEntity.ok(torneoSevice.modifyStato(torneo));
	}
	  @Transactional
	  @PutMapping("/GeneraTurno")
	  public ResponseEntity<TorneoResponse> generateTurno(@RequestBody TorneoRequestModifyTurno richiesta){
		  
		  return ResponseEntity.ok(torneoSevice.generaPartiteTurno(richiesta));
	  }
	  
	  
	  //classifica
	  
	  @PostMapping("/Classifica")
	  public ResponseEntity<List<ClassificaResponse>> classifica(@RequestBody TorneoRequestModifyTurno richiesta){

		  return ResponseEntity.ok(torneoSevice.classifica(richiesta));
		  
	  }
	 

	  
}
