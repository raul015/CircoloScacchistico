package com.circolo.security.torneo;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.circolo.security.algorithm.MergeSort;
import com.circolo.security.iscrizione.Iscrizione;
import com.circolo.security.iscrizione.IscrizioneService;
import com.circolo.security.partita.Partita;
import com.circolo.security.partita.PartitaRepository;
import com.circolo.security.user.UserRepository;

import io.micrometer.common.lang.NonNull;


@Service
public class TorneoService {
	
	
	private final TorneoRepository torneoRepository;
	private final IscrizioneService iscrizioneService;
	private final UserRepository userRepository;
	private final PartitaRepository partitaRepository;
	
	
	@Autowired
	public TorneoService(TorneoRepository torneoRepository, IscrizioneService iscrizioneService, UserRepository userRepository, PartitaRepository partitaRepository) {
		this.torneoRepository =  torneoRepository;
		this.iscrizioneService = iscrizioneService;
		this.userRepository = userRepository;
		this.partitaRepository = partitaRepository;
	}
	
	
	
	public List<Torneo> getTorneo(){
		// Mi ritornerà una lista di solo Tornei
		return torneoRepository.findAll();
	}
	
	
	// Metodo per ottenere la lista di iscrizioni per ogni torneo
	
	public List<Iscrizione> getListaIscrizioniTorneo(TorneoRequestListaIscrizioni richiesta){
		
		return torneoRepository.findById(richiesta.getId_torneo()).get().getIscrizioni();
		
	}
	
	
	
	public Torneo getSingoloTorneo(TorneoRequestListaIscrizioni richiesta){
		
		return torneoRepository.findById(richiesta.getId_torneo()).get();
	}
	

	
	public TorneoResponse  addNewTorneo(Torneo torneo) { 
		/*
		 * Sto utilizzando un oggetto repository ed utilizando una query presente
		 * al suo interno, ovvero la ricerca tramite le mail 
		 */
//		Optional<Torneo> torneoOptional = torneoRepository.findTorneoById(torneo.getId());
		
		List<Torneo> lista_tornei = new ArrayList<>();
		lista_tornei = getTorneo();
		
		boolean presente = false;
		for(int i = 0; i<lista_tornei.size();i++) {
			
			if(lista_tornei.get(i).getNome_torneo().equals(torneo.getNome_torneo())) {
				presente = true;
			}
				
		}
		
		if(presente == true) {
			
//			torneo.setTurno_attuale(0);
			return TorneoResponse.builder()
					.risposta("Il torneo inserito ha il nome di un altro torneo già esistente, per favore cambiare nome")
					.build();
		}
		
		torneoRepository.save(torneo);
		
		return TorneoResponse.builder()
				.risposta("Il torneo è stato creato con successo")
				.build();

	}
	
	
	public TorneoResponse deleteTorneo(TorneoDeleteRequest torneo){
		
		// Adesso devo eliminare il torneo con un determinato id
			
		
	    Torneo torneo_ = torneoRepository.findById(torneo.getId_torneo()).orElseThrow(()-> new RuntimeException("Torneo non esiste"));
	    List<Iscrizione> lista_iscrizioni = iscrizioneService.getIscrizione();
	    
		// Optional<Torneo> torneoOptional = torneoRepository.findById(torneo.getId_torneo());
		
//		if(torneoOptional.isPresent()){
			
		//	Torneo torneo_ = torneoOptional.get();
	    
	    for(int i = 0; i<lista_iscrizioni.size();i++){
	    	
	    	if(lista_iscrizioni.get(i).getTorneo().getId_torneo().equals(torneo.getId_torneo())){
	    		
	    		torneo_.deleteIscrizioni(lista_iscrizioni.get(i));
	    		lista_iscrizioni.get(i).deleteTorneo();
	    	}
	    	
	    }
//			torneo_.deleteIscrizioni(torneo);
//			torneoRepository.delete(torneoOptional.get());
			torneoRepository.delete(torneo_);
			
			return TorneoResponse.builder()
					.risposta("il torneo è stato eliminato")
					.build();	
		
//		}
		
		
//		return TorneoResponse.builder()
//				.risposta("il torneo con questo id non è stato trovato...")
//				.build();
//		
	}
	
	
	public TorneoResponse modifyDataTorneo(TorneoRequestModifyData torneo){
		
		
		
		if(!torneoRepository.findById(torneo.getId_torneo()).isPresent()) {
			
			return TorneoResponse.builder()
					.risposta("il torneo con questo id non è stato trovato, per questo"
							+ "	motivo non è stato possibile modificare la data")
					.build();
			
		}
					
		var user = torneoRepository.findById(torneo.getId_torneo())
		            .orElseThrow();
		    
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

		String toParse = torneo.getData_torneo().toString();
		    
		LocalDateTime date =  LocalDateTime.parse(toParse,formatter);
		user.setData_torneo(date);
		   
			return TorneoResponse.builder()
					.risposta("La data del torneo è stata modificata")
					.build();	
		
	}

	
	
	
	public TorneoResponse modifyLuogo(TorneoRequestModifyLuogo torneo){
		

		if(!torneoRepository.findById(torneo.getId_torneo()).isPresent()) {
			
			return TorneoResponse.builder()
					.risposta("il torneo con questo id non è stato trovato, per questo"
							+ "	motivo non è stato possibile modificare il luogo")
					.build();
			
		}
					
		var user = torneoRepository.findById(torneo.getId_torneo())
		            .orElseThrow();
		    
		user.setLuogo(torneo.getLuogo());
		   
			return TorneoResponse.builder()
					.risposta("Il luogo del torneo è stata modificato")
					.build();	
		
	}
	
	
	
	public TorneoResponse modifyNTurni(TorneoRequestModifyNTurni torneo){
		
		
		
		if(!torneoRepository.findById(torneo.getId_torneo()).isPresent()) {
			
			return TorneoResponse.builder()
					.risposta("il torneo con questo id non è stato trovato, per questo"
							+ "	motivo non è stato possibile cambiare il numero dei turni")
					.build();
			
		}
					
		var user = torneoRepository.findById(torneo.getId_torneo())
		            .orElseThrow();
		    
		user.setNumero_turni(torneo.getNumero_turni());
		   
			return TorneoResponse.builder()
					.risposta("Il numero dei turni del tornoeo è stato modificato")
					.build();	
		
	}
	
	
	public TorneoResponse modifyTurno(TorneoRequestModifyTurno richiesta){
		
		if(!torneoRepository.findById(richiesta.getId_torneo()).isPresent()) {
			
			return TorneoResponse.builder()
					.risposta("il torneo con questo id non è stato trovato, per questo"
							+ "	motivo non è stato possibile cambiare il turno attuale")
					.build();
			
		}
		
					
		var torneo = torneoRepository.findById(richiesta.getId_torneo())
		            .orElseThrow();
				
		
//		if(richiesta.getTurno_attuale().equals(torneo.getTurno_attuale())) {
//			
//			return TorneoResponse.builder()
//					.risposta("Turno inserito uguale a quello attuale,cambia turno...")
//					.build();
//			
//		}
		
		// Il torneo esiste, ora controllo che sono stati riempiti tutti ....
		
		// Controlli se è stato già avviato e controllo sulle partite che siano tutte finite ...

		List<Partita> lista_partite = new ArrayList<>();
		
		lista_partite = torneo.getPartite();
		
		// Adesso che ho la lista delle partite di questo torneo, controllo gli id ...
		
		for(int i = 0; i<lista_partite.size();i++){
			

			if((lista_partite.get(i).getRisultato_sfidante1()== null ) || (lista_partite.get(i).getRisultato_sfidante2()== null)){ // Il risultato non è ancora stato cambiato...
				
				System.out.println(" ris1: " + lista_partite.get(i).getRisultato_sfidante1() + "  ris2: " + lista_partite.get(i).getRisultato_sfidante2());
				
				return TorneoResponse.builder()
						.risposta("Il turno non può essere modificato a causa della partita " + lista_partite.get(i).getId_partita())
						.build();	
				
			}		
			
		}
		
		// Sono fuori dal for, quindi tutti i risultati delle partite sono coerenti e riempite
		
		torneo.setTurno_attuale(richiesta.getTurno_attuale());
		   
			return TorneoResponse.builder()
					.risposta("Il turno attuale è stato modificato")
					.build();	
		
	}
	
	
	public TorneoResponse modifyNome(TorneoRequestModifyName richiesta){
		
		if(!torneoRepository.findById(richiesta.getId_torneo()).isPresent()) {
			
			return TorneoResponse.builder()
					.risposta("il torneo con questo id non è stato trovato, per questo"
							+ "	motivo non è stato possibile modificare il nome del torneo")
					.build();
			
		}
					
		var torneo = torneoRepository.findById(richiesta.getId_torneo())
		            .orElseThrow();
		    
		torneo.setNome_torneo(richiesta.getNome_torneo());
		   
			return TorneoResponse.builder()
					.risposta("Il nome del torneo è stata modificato")
					.build();	
		
	}
	
	
	
	public TorneoResponse modifyStato(TorneoRequestModifyStato richiesta){
				
		if(!torneoRepository.findById(richiesta.getId_torneo()).isPresent()) {
			
			return TorneoResponse.builder()
					.risposta("il torneo con questo id non è stato trovato, per questo"
							+ "	motivo non è stato possibile modificare il nome del torneo")
					.build();
			
		}
				
		var torneo = torneoRepository.findById(richiesta.getId_torneo())
		    .orElseThrow();
			
		
		if(richiesta.isIn_corso()== false) {
			
			torneo.setIn_corso(richiesta.isIn_corso());
			
				return TorneoResponse.builder()
						.risposta("Lo stato del  torneo è stata modificato, in_corso = " +torneo.isIn_corso())
						.build();	
		}

		// Controllo che il numero di partecipanti sia pari e non dispari...
		
		if(torneo.getIscrizioni().size()==0) {
			
			return TorneoResponse.builder()
					.risposta("Non è possibile avviare il torneo con 0 partecipanti")
					.build();	
			
		}
		
		if(torneo.getIscrizioni().size()%2 != 0){
			
			// Ho il torneo e l'user che mi interessano
			var forfait = userRepository.findByEmail("forfait").orElseThrow();
		
			Iscrizione iscrizione = new Iscrizione();
			
			iscrizione.setUser(forfait);
			iscrizione.setTorneo(torneo);			

			iscrizioneService.addNewIscrizione(iscrizione);
							
		}
		
		torneo.setIn_corso(richiesta.isIn_corso());
		torneo.setTurno_attuale(1);
		   
		
			return TorneoResponse.builder()
					.risposta("Lo stato del  torneo è stata modificato, in_corso = " +torneo.isIn_corso())
					.build();	
		
	}
	
	
	
	
	public TorneoResponse generaPartiteTurno(TorneoRequestModifyTurno richiesta){
		
		Torneo torneo =  torneoRepository.findById(richiesta.getId_torneo()).orElseThrow(()-> new RuntimeException("Torneo con questo id non esiste nel database"));
		
		// Devo controllare che per questa richiesta di generazione partite del turno non ci sia già stata, anzi devo sovrascriverle
		// nel caso
		
		if(torneo.isIn_corso() == false) {
			
			return TorneoResponse.builder()
					.risposta("Il torneo non è in corso, non puoi generare turni")
					.build();	
			
		}

		if(torneo.getNumero_turni() < richiesta.getTurno_attuale() || richiesta.getTurno_attuale() <= 0 ) {
			
			return TorneoResponse.builder()
					.risposta("Il torneo inizia i turni da 1 a " + torneo.getNumero_turni() + "...")
					.build();	
		}
		
		// Da verificare ....
		
		if(torneo.getTurno_attuale() != richiesta.getTurno_attuale()){
			
			return TorneoResponse.builder()
					.risposta("Il turno del torneo non combacia con il turno richiesto...")
					.build();	
			
		}
		
		List<Partita> partite_torneo = new ArrayList<>();
		
		List<Partita> lista_replace = new ArrayList<>();
		
		partite_torneo = torneo.getPartite();
		
		boolean presente = false;
		
		for(int i =0 ; i<partite_torneo.size(); i++){
			
			if(partite_torneo.get(i).getTurno().equals(richiesta.getTurno_attuale())) {
				
				presente = true;
				System.out.println("sono già state generate partite per questo turno");
				

				if(partite_torneo.get(i).getRisultato_sfidante1() == null || partite_torneo.get(i).getRisultato_sfidante2()== null) {
					System.out.println("non faccio nulla");
				}
				else {
					
					return TorneoResponse.builder()
					.risposta("Impossibile cambiare turno perché alcune partite sono già terminate")
					.build();	
				}
				
				}	

		}
		
			
		List<Iscrizione> partecipanti = new ArrayList<>();
		partecipanti = torneo.getIscrizioni();
		
		if(presente == false) { 
						
			// La mia lista è stata ordinata in base al punteggio... Ora posso generare il nuovo turno... con le nuove partite
			
			if(torneo.getTurno_attuale() == 1) {
				
				Collections.shuffle(partecipanti); // Ho un ordinamento randomico
			}
				
				
			else {
								
				MergeSort lista_merge = new MergeSort(partecipanti); 
				lista_merge.sortGivenArray();
			}
			
			for(int i = 0; i<partecipanti.size();i=i+2){
					
				// Qui prendo i e i+1 poi al ciclo succevviso prendere i+2 ed i+2+1
					
				Partita partita = new Partita();
			
				partita.setId_sfidante1(partecipanti.get(i).getUser().getId_user());
				partita.setId_sfidante2(partecipanti.get(i+1).getUser().getId_user());
				partita.setTorneo(partecipanti.get(i).getTorneo());
				partita.setTurno(richiesta.getTurno_attuale());	
				partita.setTorneo(torneo);
							
				torneo.AddPartite(partita);
				partite_torneo.add(partita);
	
				// Così torneo tiene traccia che sono state aggiunte queste partite
					
				partitaRepository.save(partita);
					
				}
					
			return TorneoResponse.builder()
					.risposta("Sono state create le partite per il primo turno")
					.build();	
		
						
		}
		
		
		else {
			
				if(torneo.getTurno_attuale() == 1) {
							
					Collections.shuffle(partecipanti); // Ho un ordinamento randomico				
				}
			
				else
				{
					
					MergeSort lista_merge = new MergeSort(partecipanti); // Ordino in base al punteggio dei partecipanti...
					lista_merge.sortGivenArray();
				}
				
				for(int i = 0; i<partecipanti.size();i=i+2){
					
					// Qui prendo i e i+1 poi al ciclo succevviso prendere i+2 ed i+2+1
					
					Partita partita = new Partita();
			
					partita.setId_sfidante1(partecipanti.get(i).getUser().getId_user());
					partita.setId_sfidante2(partecipanti.get(i+1).getUser().getId_user());
					partita.setTorneo(partecipanti.get(i).getTorneo());
					partita.setTurno(richiesta.getTurno_attuale());
					
					lista_replace.add(partita);
					
				}
				
				int j =0;
				
				for(int i = 0; i<partite_torneo.size();i++) {
					
					if(partite_torneo.get(i).getTurno().equals(richiesta.getTurno_attuale())){
						
						torneo.setPartita(lista_replace.get(j),i);

						j++;
						
						System.out.println("modifica delle partite");
		
					}
					
				}	
				
			return TorneoResponse.builder()
			.risposta("le partite del turno sono state generate correttamente")
			.build();	
				
				
			}
			
			

	}
	
	
	public List<ClassificaResponse> classifica(TorneoRequestModifyTurno richiesta){
		// RICHIESTA.	id_torneo , turno_attuale
		
		List<ClassificaResponse> classifica = new ArrayList<>();
		
		Torneo torneo =  torneoRepository.findById(richiesta.getId_torneo()).orElseThrow(()-> new RuntimeException("Torneo con questo id non esiste nel database"));
					
		List<Iscrizione> partecipanti = new ArrayList<>();
		partecipanti = torneo.getIscrizioni();
											
		MergeSort lista_merge = new MergeSort(partecipanti); 
		lista_merge.sortGivenArray(); // Lista partecipanti aggiornata...
		
		for(int i=0;i<partecipanti.size();i++) {
			
			ClassificaResponse locale = new ClassificaResponse();
			locale.setIdtorneo(torneo.getId_torneo());
			locale.setIduser(partecipanti.get(i).getUser().getId_user());
			locale.setFirstname(partecipanti.get(i).getUser().getFirstname());
			locale.setPunteggio(partecipanti.get(i).getPunteggio());
			
			classifica.add(locale);
			// Verifica che sono stati inseriti in ordine
			System.out.println("posizione [" + i +"] -> " + locale.getIduser() );

		}
		
		return classifica;

	}

	}
	

