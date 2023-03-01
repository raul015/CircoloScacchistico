package com.circolo.security.partita;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.transaction.Transactional;


@RestController
@RequestMapping(path="/api/v1/Partita")
public class PartitaController {
	
	
	private final PartitaService partitaService;
	
	@Autowired
	public PartitaController(PartitaService partitaService) {
		this.partitaService = partitaService;
	}
	
	@GetMapping("/Get")
	public ResponseEntity<List<Partita>>getIscrizione(){
		return ResponseEntity.ok(partitaService.getPartite());
	}
	
	@PostMapping("/GetByTorneoId")
	public ResponseEntity<List<Partita>> getPartitaById(@RequestBody PartitaRequestByIdTorneo richiesta){
		
		return ResponseEntity.ok(partitaService.getPartitaByIdTorneo(richiesta));
	}
	
	
	@PostMapping("/Nuovo")
	public ResponseEntity<PartitaResponse> newPartita(@RequestBody Partita partita){
		
		return ResponseEntity.ok(partitaService.addNewPartita(partita));
	}
	
	@PostMapping("/NuovaListaPartite")
	public ResponseEntity<PartitaResponse> listaPartite(@RequestBody List<Partita> partita){
		
		return ResponseEntity.ok(partitaService.addListaPartite(partita));
	}
	
	
	@PostMapping("/AggiuntaAutomatica")
	
	public ResponseEntity<PartitaResponse> aggiuntaAutomatica(@RequestBody PartitaRequestByIdTorneo richiesta){
		
		return ResponseEntity.ok(partitaService.aggiuntaAutomatica(richiesta));
	}
	
	@Transactional
	@PutMapping("/ModifyRisultato")
	public ResponseEntity<PartitaResponse> risultatoPartita(@RequestBody PartitaRequestRisultato richiesta){
		
		return ResponseEntity.ok(partitaService.risultatoPartita(richiesta));	
	}
	
}
