package com.circolo.security.partita;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.circolo.security.iscrizione.Iscrizione;
import com.circolo.security.torneo.Torneo;
import com.circolo.security.torneo.TorneoRepository;
import com.circolo.security.user.User;
import com.circolo.security.user.UserRepository;


@Service
public class PartitaService {
	
	
	private final PartitaRepository partitaRepository;
	private final TorneoRepository torneoRepository;
	private final UserRepository userRepository;

	
	@Autowired
	public PartitaService(PartitaRepository partitaRepository, TorneoRepository torneoRepository, UserRepository userRepository) {
	
		this.partitaRepository = partitaRepository;
		this.torneoRepository = torneoRepository;
		this.userRepository = userRepository;

	}
	
	// Mi restituisce la lista di iscrizioni...
	
	public List<Partita> getPartite(){
		return partitaRepository.findAll();
	}
	
	
	public List<Partita> getPartitaByIdTorneo(PartitaRequestByIdTorneo richiesta){
		
		Torneo torneo =  torneoRepository.findById(richiesta.getIdtorneo())
	            .orElseThrow(()-> new RuntimeException("Torneo con questo id non esiste nel database"));
		
		
		List<Partita> lista_partite_torneo = new ArrayList<>();
		lista_partite_torneo = torneo.getPartite();			
		return lista_partite_torneo;
	}
	
	public PartitaResponse  addNewPartita(Partita partita) { 

				
	    Torneo torneo = torneoRepository.findById(partita.getTorneo().getId_torneo()).orElseThrow(()-> new RuntimeException("Torneo non esiste"));
	    
	    if(torneo.isIn_corso() == false) {
	    	
			return PartitaResponse.builder()
					.risposta("Non è stato possibile creare la partita perché il torneo non è ancora stato avviato...")
					.build();
	    }
	    
	    
	    List<Partita> lista_partite = new ArrayList<>();
		lista_partite = torneo.getPartite();
		
		for(int i = 0; i< lista_partite.size();i++) {
			
			if(lista_partite.get(i).getTurno().equals(partita.getTurno())) {
				
				if(lista_partite.get(i).getId_sfidante1().equals(partita.getId_sfidante1())){
					
					return PartitaResponse.builder()
							.risposta("Partita con questi sfidanti già presenti nel database")
							.build();

				}
					
					
				if(lista_partite.get(i).getId_sfidante2().equals(partita.getId_sfidante2())){
					
					return PartitaResponse.builder()
							.risposta("Partita con questi sfidanti già presenti nel database")
							.build();		

				}
			}
		}
	    
		torneo.AddPartite(partita);		
		partita.setTorneo(torneo);
		
		partitaRepository.save(partita);
		
		return PartitaResponse.builder()
				.risposta("Partita " + partita.getId_partita()+ " nel DB")
				.build();
			}
	
	public PartitaResponse addListaPartite(List<Partita> partite){
		
		for(int i = 0 ; i<partite.size();i++){
			addNewPartita(partite.get(i));
		}
		
		return PartitaResponse.builder()
				.risposta("Lista partite inserite nel DB")
				.build();
		
	}
	
	public PartitaResponse aggiuntaAutomatica(PartitaRequestByIdTorneo richiesta){
				
		Torneo torneo = torneoRepository.findById(richiesta.getIdtorneo()).orElseThrow(()-> new RuntimeException("Torneo con id["+ richiesta.getIdtorneo()+"] non esiste"));
		
	    if(torneo.isIn_corso() == false) {
	    	
			return PartitaResponse.builder()
					.risposta("Non è stato possibile creare la partita perché il torneo non è ancora stato avviato, sei ancora allo stato 0")
					.build();
	    }
	    
	    
		Integer turno_torneo = torneo.getTurno_attuale();
		ArrayList<Partita> partite_turno = new ArrayList<>();
		
		if(turno_torneo == 1) {	//Questo vuol dire che non è stato ancora generato un turno e tutti sono a 0 punti
			
			List<Iscrizione> partecipanti = new ArrayList<>();
			
			partecipanti = torneo.getIscrizioni(); // Ho popolato la lista dei partecipanti...
			
			Collections.shuffle(partecipanti); // Ho un ordinamento randomico
			
			for(int i = 0; i<partecipanti.size();i=i+2){
				
				// Qui prendo i e i+1 poi al ciclo succevviso prendere i+2 ed i+2+1
				
				Partita partita = new Partita();
				
				partita.setId_sfidante1(partecipanti.get(i).getUser().getId_user());
				partita.setId_sfidante2(partecipanti.get(i+1).getUser().getId_user());
				partita.setTorneo(partecipanti.get(i).getTorneo());
				partita.setTurno(turno_torneo);
				
				partite_turno.add(partita);
				
				torneo.AddPartite(partita);	// Così torneo tiene traccia che sono state aggiunte queste partite
						
				partitaRepository.save(partita);
			}
			
			// La lista delle partite di questo turno è stata riempita...
								
		}
		
		
		return PartitaResponse.builder()
				.risposta("E' stato effettuato un inserimento di partite automatico...")
				.build();
	}

	
	
	
	public PartitaResponse risultatoPartita(PartitaRequestRisultato richiesta) {
		
		Double vincita = 1.0 ;
		Double perdita = 0.0 ;
		Double pareggio = 0.5;
		
		/*	Qui cambio la singola partita tramite id :
		 * 
		 *  
		 *  Valore iniziale duelli : null  , se null non è stato inserito il risultato
		 *  						!null  , sto modificando un risultato che è già stato modificato almeno una volta
		 */
		
		// Controllo sui punti inserito
		
		
		Partita partita = partitaRepository.findById(richiesta.getId_partita()).orElseThrow(()-> new RuntimeException("Torneo con id["+ richiesta.getId_partita()+"] non esiste"));

		User sfidante_1 = userRepository.findById(partita.getId_sfidante1()).orElseThrow(()-> new RuntimeException("Sfidante con id["+ partita.getId_sfidante1()+"] non esiste"));
		User sfidante_2 = userRepository.findById(partita.getId_sfidante2()).orElseThrow(()-> new RuntimeException("Sfidante con id["+ partita.getId_sfidante2()+"] non esiste"));
		
		Torneo torneo = partita.getTorneo();
		
		List<Iscrizione> lista_iscrizioni = torneo.getIscrizioni();
		
		
		if(richiesta.getPunti_bianco().equals(pareggio)&& richiesta.getPunti_nero().equals(pareggio)){
			
			System.out.println("Parita con id["+ richiesta.getId_partita() +"] modificato per la prima volta");
		
			partita.setRisultato_sfidante1(richiesta.getPunti_bianco());
			partita.setRisultato_sfidante2(richiesta.getPunti_nero());
						
			
			for(int i = 0; i<lista_iscrizioni.size();i++) {
				
				if(lista_iscrizioni.get(i).getUser().getId_user().equals(sfidante_1.getId_user())) {
										
					double punteggio_1 = lista_iscrizioni.get(i).getPunteggio();
					lista_iscrizioni.get(i).setPunteggio(punteggio_1 + pareggio);
										
				}
				
				if(lista_iscrizioni.get(i).getUser().getId_user().equals(sfidante_2.getId_user())) {
					
					double punteggio_2 = lista_iscrizioni.get(i).getPunteggio();
					lista_iscrizioni.get(i).setPunteggio(punteggio_2 + pareggio);
				}
			
			}
			
			
			
			return PartitaResponse.builder()
					.risposta("E' stato inserito il risultato della partita " + richiesta.getId_partita() + 
							" \n  bianco e nero pareggiano")
					.build();
			
			
		}
		
		else if(richiesta.getPunti_bianco().equals(vincita) && richiesta.getPunti_nero().equals(perdita)){
			
			System.out.println("Parita con id["+ richiesta.getId_partita() +"] modificato per la prima volta");
			
			partita.setRisultato_sfidante1(richiesta.getPunti_bianco());
			partita.setRisultato_sfidante2(richiesta.getPunti_nero());
			
			
			for(int i = 0; i<lista_iscrizioni.size();i++) {
				
				if(lista_iscrizioni.get(i).getUser().getId_user().equals(sfidante_1.getId_user())) {
										
					double punteggio_1 = lista_iscrizioni.get(i).getPunteggio();
					lista_iscrizioni.get(i).setPunteggio(punteggio_1 + vincita);
					}
					
				
				if(lista_iscrizioni.get(i).getUser().getId_user().equals(sfidante_2.getId_user())) {
					
					double punteggio_2 = lista_iscrizioni.get(i).getPunteggio();
					lista_iscrizioni.get(i).setPunteggio(punteggio_2 + perdita);
				}
			
			}
			
			return PartitaResponse.builder()
					.risposta("E' stato inserito il risultato della partita " + richiesta.getId_partita() + 
							" \n  il bianco vince +1 ")
					.build();
			
			
		}
		
		
		else if(richiesta.getPunti_bianco().equals(perdita) && richiesta.getPunti_nero().equals(vincita)){
			
			System.out.println("Parita con id["+ richiesta.getId_partita() +"] modificato per la prima volta");
			
			partita.setRisultato_sfidante1(richiesta.getPunti_bianco());
			partita.setRisultato_sfidante2(richiesta.getPunti_nero());
			
			for(int i = 0; i<lista_iscrizioni.size();i++) {
				
				if(lista_iscrizioni.get(i).getUser().getId_user().equals(sfidante_1.getId_user())) {
										
					double punteggio_1 = lista_iscrizioni.get(i).getPunteggio();
					lista_iscrizioni.get(i).setPunteggio(punteggio_1 + perdita);
					}
					
				
				if(lista_iscrizioni.get(i).getUser().getId_user().equals(sfidante_2.getId_user())) {
					
					double punteggio_2 = lista_iscrizioni.get(i).getPunteggio();
					lista_iscrizioni.get(i).setPunteggio(punteggio_2 + vincita);
				}
			
			}
			
			return PartitaResponse.builder()
					.risposta("E' stato inserito il risultato della partita " + richiesta.getId_partita() + 
							" \n  il nero vince +1 ")
					.build();	
		}
		
		else {
			
			// Punti inserito non validi...
			
			return PartitaResponse.builder()
					.risposta("Non è stato possibile inserire il risultato, valori non ammissibili...")
					.build();
			
		}
		
		
		
			

	
				

	}

}
