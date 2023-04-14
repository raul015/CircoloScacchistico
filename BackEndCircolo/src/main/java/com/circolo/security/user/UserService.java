package com.circolo.security.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.circolo.security.iscrizione.Iscrizione;
import com.circolo.security.torneo.Torneo;

@Service
public class UserService {
	
	// Ancora da completare...
	
	private final UserRepository userRepository;
	
	@Autowired
	public UserService(UserRepository userRepository) {
		
		this.userRepository = userRepository;
	}
	
	
	public List<User> getUser(){
		// Mi ritornerà una lista di solo Tornei
		return userRepository.findAll();
	}
	
	
//  PRIMA VERSIONE TEMPORANEA PER LA RICERCA DI UN SINGOLO USER...
//	public   User getSingoloUser(Integer id) {  
//		return userRepository.findById(id).get();	
//		}

	public User getSingoloUser(UserRequestById richiesta) {
		
		return userRepository.findById(richiesta.getId()).get();

	}
	
	public UserResponse eliminaUser(UserRequestById richiesta) {
		
		
		 User user = userRepository.findById(richiesta.getId()).orElseThrow(()-> new RuntimeException("User con questo id non esiste"));
		
		 List<Iscrizione> lista_iscrizioni = new ArrayList<>();
		 lista_iscrizioni = user.getIscrizioni();
		 
//		 for(int i = 0; i<;i++){
//			 
//		 }
			 
		 for(int i = 0; i<lista_iscrizioni.size();i++) {
			 
			 Torneo torneo = lista_iscrizioni.get(i).getTorneo(); // torneo della iscrizione da eliminare
			 
			 torneo.deleteIscrizioni(lista_iscrizioni.get(i));	// elimino tale iscrizione da torneo
			 
			 lista_iscrizioni.get(i).deleteUser();
			 
		 }
		 
		 userRepository.delete(user);
		 
		return UserResponse.builder()
				.risposta("User eliminato")
				.build();
	}
	
	
	



		
}
