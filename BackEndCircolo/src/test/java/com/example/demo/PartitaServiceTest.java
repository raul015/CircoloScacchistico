package com.example.demo;

import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

import com.circolo.security.partita.PartitaRepository;
import com.circolo.security.partita.PartitaService;
import com.circolo.security.torneo.Torneo;
import com.circolo.security.torneo.TorneoRepository;
import com.circolo.security.user.UserRepository;

@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class PartitaServiceTest {
	
	@Mock
	private TorneoRepository torneoRepository;
	@Mock
	private UserRepository userRepository;
	@Mock
	private PartitaRepository partitaRepository;
		
	@InjectMocks
	private PartitaService underTest;
	
	@BeforeEach
	void setUp() {
		
		underTest = new PartitaService(
				partitaRepository,
				torneoRepository,
				userRepository
				);
		
	}
	
	
	@Test
	void getPartite(){
		
		//when
		underTest.getPartite();	
		//then
		verify(partitaRepository).findAll();
		
	}
	
	@Test
	@Disabled
	void getPartitaByIdTorneo(){
		
	}
	
	@Test
	@Disabled
	void addNewPartita() {
		
	}

}
