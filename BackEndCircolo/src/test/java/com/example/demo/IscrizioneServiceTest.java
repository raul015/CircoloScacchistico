package com.example.demo;

import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import com.circolo.security.iscrizione.Iscrizione;
import com.circolo.security.iscrizione.IscrizioneRepository;
import com.circolo.security.iscrizione.IscrizioneRequestById;
import com.circolo.security.iscrizione.IscrizioneService;
import com.circolo.security.partita.PartitaRepository;
import com.circolo.security.torneo.Torneo;
import com.circolo.security.torneo.TorneoRepository;
import com.circolo.security.user.Role;
import com.circolo.security.user.User;
import com.circolo.security.user.UserRepository;

@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class IscrizioneServiceTest {
	
	@Mock
	private IscrizioneRepository iscrizioneRepository;
	@Mock
	private TorneoRepository torneoRepository;
	@Mock
	private UserRepository userRepository;
	@Mock
	private PartitaRepository partitaRepository;
	@Mock
	private IscrizioneService iscrizioneService;
	
		
	@InjectMocks
	private IscrizioneService underTest;
	
	@BeforeEach
	void setUp() {
		
		underTest = new IscrizioneService(
				iscrizioneRepository,
				torneoRepository,
				userRepository
				);
		
	}

	@Test
	void get(){
		
		underTest.getIscrizione();	
		//then
		verify(iscrizioneRepository).findAll();
		
	}
	
	@Test
	void getIscrizioneById(){
		
		String data = "13-04-2023 10:30";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime formatDateTime= LocalDateTime.parse(data, formatter);
        String nome = "Excelsior";
        String luogo = "Casa Giovane";
        Integer turni = 5;
        Torneo torneo = new Torneo(
        		nome,formatDateTime,luogo,turni);
        torneo.setId_torneo(1);
        
        torneoRepository.save(torneo);  
		
        ArgumentCaptor<Torneo> torneoArgumentCaptor =
        		ArgumentCaptor.forClass(Torneo.class);
        verify(torneoRepository)
     	.save(torneoArgumentCaptor.capture());
     
        
        
		User raul = new User(
				"Raul",
				"Luizaga",
				"raul01597@gmail.com",
				 Role.ADMIN,
				"bella"
				);
		raul.setId_user(1);
		userRepository.save(raul);
		
		
        ArgumentCaptor<User> userArgumentCaptor =
        		ArgumentCaptor.forClass(User.class);
        verify(userRepository)
     	.save(userArgumentCaptor.capture());
        
		
		Iscrizione iscrizione = new Iscrizione();
		iscrizione.setTorneo(torneo);
		iscrizione.setUser(raul);
		iscrizione.setId_iscrizione(1);
		
		
		iscrizioneRepository.save(iscrizione);
        ArgumentCaptor<Iscrizione> iscrizioneArgumentCaptor =
        		ArgumentCaptor.forClass(Iscrizione.class);
        
        
        verify(iscrizioneRepository)
    	.save(iscrizioneArgumentCaptor.capture());
		
		IscrizioneRequestById request = new IscrizioneRequestById();
		request.setId(1);
		iscrizioneRepository.findById(request.getId());
		
	}
	
	
	@Test
	void addNewIscrizione(){
		
		
		String data = "13-04-2023 10:30";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime formatDateTime= LocalDateTime.parse(data, formatter);
        String nome = "Excelsior";
        String luogo = "Casa Giovane";
        Integer turni = 5;
        Torneo torneo = new Torneo(
        		nome,formatDateTime,luogo,turni);
        torneo.setId_torneo(1);
        
        torneoRepository.save(torneo);  
		
        ArgumentCaptor<Torneo> torneoArgumentCaptor =
        		ArgumentCaptor.forClass(Torneo.class);
        verify(torneoRepository)
     	.save(torneoArgumentCaptor.capture());
     
        
        
		User raul = new User(
				"Raul",
				"Luizaga",
				"raul01597@gmail.com",
				 Role.ADMIN,
				"bella"
				);
		raul.setId_user(1);
		userRepository.save(raul);
		
		
        ArgumentCaptor<User> userArgumentCaptor =
        		ArgumentCaptor.forClass(User.class);
        verify(userRepository)
     	.save(userArgumentCaptor.capture());
        
		
		Iscrizione iscrizione = new Iscrizione();
		iscrizione.setTorneo(torneo);
		iscrizione.setUser(raul);
		iscrizione.setId_iscrizione(1);
		
		
		iscrizioneRepository.save(iscrizione);
        ArgumentCaptor<Iscrizione> iscrizioneArgumentCaptor =
        		ArgumentCaptor.forClass(Iscrizione.class);
        
        
        verify(iscrizioneRepository)
    	.save(iscrizioneArgumentCaptor.capture());
    		
	}
}
