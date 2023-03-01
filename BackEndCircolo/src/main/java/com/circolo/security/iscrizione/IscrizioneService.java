package com.circolo.security.iscrizione;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.circolo.security.torneo.Torneo;
import com.circolo.security.torneo.TorneoRepository;
import com.circolo.security.user.User;
import com.circolo.security.user.UserRepository;




@Service
public class IscrizioneService {

	private final IscrizioneRepository iscrizioneRepository;
	private final TorneoRepository torneoRepository;
	private final UserRepository userRepository;
	

	@Autowired
	public IscrizioneService(IscrizioneRepository iscrizioneRepository, TorneoRepository torneoRepository, UserRepository userRepository) {
		this.iscrizioneRepository =  iscrizioneRepository;
		this.torneoRepository = torneoRepository;
		this.userRepository = userRepository;

	}
	
	// Mi restituisce la lista di iscrizioni...
	
	public List<Iscrizione> getIscrizione(){
		return iscrizioneRepository.findAll();
	}
	
	
//	public List<Iscrizione> getIscrizioneByIdUtent(IscrizioneRequestById richiesta){
	
	public IscrizioneRequestInfo getIscrizioneByIdUtent(IscrizioneRequestById richiesta){
		
	    User user = userRepository.findById(richiesta.getId()).orElseThrow(()-> new RuntimeException("User non esiste"));
	  
	    
	    List<Iscrizione> lista_iscrizioni = new ArrayList<>();
	    List<Torneo> lista_tornei = new ArrayList<>();
	    lista_iscrizioni = user.getIscrizioni();
	    
	    for(int i = 0; i<lista_iscrizioni.size();i++){
	    	lista_tornei.add(lista_iscrizioni.get(i).getTorneo());
	    	
	    	System.out.println("è stato trovato il torneo con id : "+ lista_iscrizioni.get(i).getTorneo().getId_torneo());
	    	
	    }
	    
	    IscrizioneRequestInfo info = new IscrizioneRequestInfo();
//	    info.setLista_iscrizioni(lista_iscrizioni);
	    info.setLista_tornei(lista_tornei);
	    
		return info;
	}
	
	
	public IscrizioneResponse getUtenteByIscrizione(IscrizioneRequestById richiesta){
		
//		Optional<User>  user = userRepository.findById(iscrizione.getUser().getId_user());
		Iscrizione iscrizione = iscrizioneRepository.findById(richiesta.getId()).orElseThrow(()-> new RuntimeException("Non è stata trovata questa iscrizione"));	
	    User user = userRepository.findById(iscrizione.getUser().getId_user()).orElseThrow(()-> new RuntimeException("User non esiste")) ;
		
	    
	    return  IscrizioneResponse.builder()
				.risposta("user : " + user.getId_user().toString() + " " +
						 user.getFirstname()+ " " +
						 user.getLastname() + " " +
						 user.getEmail() +  " ")
				.build();
	}
		
	
	//, readOnly=true, noRollbackFor=Exception.class
	//@Transactional(propagation=Propagation.REQUIRED) // Non cancellare! , probemi risolti inserendo nella mia classe di interesse JsonBackReference
	public IscrizioneResponse  addNewIscrizione(Iscrizione iscrizione) { 
		
		
		List<Iscrizione> lista_iscrizioni = new ArrayList<>();
		lista_iscrizioni = getIscrizione();
				
	    Torneo torneo = torneoRepository.findById(iscrizione.getTorneo().getId_torneo()).orElseThrow(()-> new RuntimeException("Torneo non esiste"));
	    User user = userRepository.findById(iscrizione.getUser().getId_user()).orElseThrow(()-> new RuntimeException("User non esiste")) ;
	    
	    if(torneo.isIn_corso()== true) {
	    	
			return IscrizioneResponse.builder()
					.risposta("Torneo in corso, non sono più ammesse iscrizioni per questo torneo...")
					.build();
	    	
	    	
	    }
	    		boolean presente = false;
		
		for(int i = 0; i<lista_iscrizioni.size();i++) {
			
			if(lista_iscrizioni.get(i).getUser().getId_user().equals(iscrizione.getUser().getId_user())){
				
				if(lista_iscrizioni.get(i).getTorneo().getId_torneo().equals(iscrizione.getTorneo().getId_torneo()))
						presente = true;
			}

				
		}
		
		
		if(presente == true) {
			
//			torneo.setTurno_attuale(0);
			
			return IscrizioneResponse.builder()
					.risposta("L'utente si è già registrato a questo torneo ")
					.build();
		}
		
//		System.out.println(iscrizione.getTorneo().getId_torneo());
//		System.out.println(iscrizione.getUser().getId_user());
//		System.out.println(iscrizione.getTorneo());
//		System.out.println(iscrizione.getUser());
		
		torneo.AddtIscrizioni(iscrizione);
		user.AddtIscrizioni(iscrizione);
		
		iscrizione.setTorneo(torneo);
		iscrizione.setUser(user);
		
//		System.out.println(iscrizione.getTorneo().getNome_torneo());
//		System.out.println(iscrizione.getUser().getFirstname());
//		
//		
		iscrizioneRepository.save(iscrizione);
		
		return IscrizioneResponse.builder()
				.risposta("Iscrizione inserita nel DB")
				.build();
			}
	
	
	
	public IscrizioneResponse modifyPunteggio(IscrizioneRequestModifyPunteggio richiesta){
		
//			
//			return IscrizioneResponse.builder()
//					.risposta("il torneo con questo id non è stato trovato, per questo"
//							+ "	motivo non è stato possibile modificare la data")
//					.build();
//			
		
//			var user = userRepository.findById(richiesta.getId_utente()).orElseThrow();
//			
//			var torneo = torneoRepository.findById(richiesta.getId_torneo())
//					.orElseThrow();
//			
			var iscrizione = iscrizioneRepository.findById(richiesta.getId_iscrizione()).orElseThrow();
			
			Double punteggio_parziale = iscrizione.getPunteggio();
			
			// Devo sommare i due valori 
			
			iscrizione.setPunteggio(punteggio_parziale + richiesta.getPunteggio());
				
			return IscrizioneResponse.builder()
					.risposta("Il punteggio del torneo è stato cambiato correttamente")
					.build();	
	
}
	
	
	
	
public IscrizioneResponse deleteIscrizione(IscrizioneDeleteRequest richiesta){ // Adesso devo eliminare il torneo con un determinato id
		
		Optional<Iscrizione> iscrizioneOptional = iscrizioneRepository.findById(richiesta.getId_partita());

		
		if(iscrizioneOptional.isPresent()){
			
			iscrizioneRepository.delete(iscrizioneOptional.get());
			
			return IscrizioneResponse.builder()
					.risposta("l'iscrizione è stata eliminata")
					.build();	
		
		}
		
		
		return IscrizioneResponse.builder()
				.risposta("iscrizione con questo id non è stato trovato...")
				.build();
		
	}
	
	
	
	

	public IscrizioneResponse RiempiTorneo(IscrizioneRequestById richiesta) {
		
		
	    Torneo torneo = torneoRepository.findById(richiesta.getId()).orElseThrow(()-> new RuntimeException("Torneo id["+richiesta.getId()+"] non esiste"));
	    
	    
	    
	    User giambalvo = userRepository.findByEmail("giambalvo@gmail.com").orElseThrow(()-> new RuntimeException("User non esiste"));
	 
	    Iscrizione iscrizione_giambalvo = new Iscrizione();
	    iscrizione_giambalvo.setTorneo(torneo);
	    iscrizione_giambalvo.setUser(giambalvo);
	    
	    User vehat = userRepository.findByEmail("vehat@gmail.com").orElseThrow(()-> new RuntimeException("User giambalvo non esiste"));
	    
	    Iscrizione iscrizione_vehat = new Iscrizione();
	    iscrizione_vehat.setTorneo(torneo);
	    iscrizione_vehat.setUser(vehat);
	    
	    User bisceglia = userRepository.findByEmail("bisceglia@gmail.com").orElseThrow(()-> new RuntimeException("User vehat non esiste"));
	    
	    Iscrizione iscrizione_bisceglia = new Iscrizione();
	    iscrizione_bisceglia.setTorneo(torneo);
	    iscrizione_bisceglia.setUser(bisceglia);
	    
	    User carnovale = userRepository.findByEmail("carnovale@gmail.com").orElseThrow(()-> new RuntimeException("User bisceglia non esiste"));
	    
	    Iscrizione iscrizione_carnovale = new Iscrizione();
	    iscrizione_carnovale.setTorneo(torneo);
	    iscrizione_carnovale.setUser(carnovale);
	    
	    User nava = userRepository.findByEmail("nava@gmail.com").orElseThrow(()-> new RuntimeException("User carnovale non esiste"));
	    
	    Iscrizione iscrizione_nava = new Iscrizione();
	    iscrizione_nava.setTorneo(torneo);
	    iscrizione_nava.setUser(nava);
	    
	    User lazzari = userRepository.findByEmail("lazzari@gmail.com").orElseThrow(()-> new RuntimeException("User lazzari non esiste"));
	    
	    Iscrizione iscrizione_lazzari = new Iscrizione();
	    iscrizione_lazzari.setTorneo(torneo);
	    iscrizione_lazzari.setUser(lazzari);
	    
	    User zraiba = userRepository.findByEmail("zraiba@gmail.com").orElseThrow(()-> new RuntimeException("User zraibanon esiste"));
	    
	    Iscrizione iscrizione_zraiba = new Iscrizione();
	    iscrizione_zraiba.setTorneo(torneo);
	    iscrizione_zraiba.setUser(zraiba);
	    
	    User mirra = userRepository.findByEmail("mirra@gmail.com").orElseThrow(()-> new RuntimeException("User mirra non esiste"));
	    
	    Iscrizione iscrizione_mirra = new Iscrizione();
	    iscrizione_mirra.setTorneo(torneo);
	    iscrizione_mirra.setUser(mirra);
	    
	    User morescalchi = userRepository.findByEmail("morescalchi@gmail.com").orElseThrow(()-> new RuntimeException("User morescalchi non esiste"));
	    
	    Iscrizione iscrizione_morescalchi = new Iscrizione();
	    iscrizione_morescalchi.setTorneo(torneo);
	    iscrizione_morescalchi.setUser(morescalchi);
	    
	    User pasolini = userRepository.findByEmail("pasolini@gmail.com").orElseThrow(()-> new RuntimeException("User pasolini non esiste"));
	    Iscrizione iscrizione_pasolini = new Iscrizione();
	    iscrizione_pasolini.setTorneo(torneo);
	    iscrizione_pasolini.setUser(pasolini);

	    
		iscrizioneRepository.saveAll(
				List.of(
						iscrizione_giambalvo,
						iscrizione_vehat,
						iscrizione_bisceglia,
						iscrizione_carnovale,
						iscrizione_nava,
						iscrizione_lazzari,
						iscrizione_zraiba,
						iscrizione_mirra,
						iscrizione_morescalchi,
						iscrizione_pasolini
						)
				);
		
		
		return IscrizioneResponse.builder()
				.risposta("Il torneo è stato riempito correttamente...")
				.build();	
	}
	
	

	public IscrizioneResponse EliminaTorneo(IscrizioneRequestById richiesta){
			
		Iscrizione iscrizione = iscrizioneRepository.findById(richiesta.getId()).orElseThrow(()-> new RuntimeException("La iscrizione non è stata trovata"));
		
		System.out.println("id iscrizione : " + iscrizione.getId_iscrizione());
		
	    User user = userRepository.findById(iscrizione.getUser().getId_user()).orElseThrow(()-> new RuntimeException("User non esiste")) ;
//		
	    // Se arrivo fino a qua vuol dire che iscrizione e user esistono...
	    // Controllo se il torneo è in corsco
		
		System.out.println("id user con questa iscrizione : " +  user.getId_user());

	    Torneo torneo = torneoRepository.findById(iscrizione.getTorneo().getId_torneo()).orElseThrow(()-> new RuntimeException("Il torneo non è stato trovato"));
	    
	    if(torneo.isIn_corso() == true){
		    
			return IscrizioneResponse.builder()
					.risposta("Il torneo è già in corso, non puoi eliminare la tua iscrizione")
					.build();	
	    	
	    }
	    
//	    torneo.deleteIscrizioni(iscrizione);
//	    user.deleteIscrizioni(iscrizione);
//	    iscrizioneRepository.delete(iscrizione);
	    
	    // vengono eliminati sia il torne che l'user
	    iscrizioneRepository.deleteById(richiesta.getId());
	   
	    // Elimino iscrizioni e tutte le sue dipendenze....
	    
	    // Devo eliminare dallatabella iscrizione l'iscrizione e
	    
		return IscrizioneResponse.builder()
				.risposta("La iscrizione al torneo è avvenuta con successo...")
				.build();	
	}
	
	
}
	

