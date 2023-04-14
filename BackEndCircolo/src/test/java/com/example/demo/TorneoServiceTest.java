package com.example.demo;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.assertj.core.util.Arrays;
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

import com.circolo.security.iscrizione.IscrizioneService;
import com.circolo.security.partita.PartitaRepository;
import com.circolo.security.torneo.Torneo;
import com.circolo.security.torneo.TorneoDeleteRequest;
import com.circolo.security.torneo.TorneoRepository;
import com.circolo.security.torneo.TorneoRequestListaIscrizioni;
import com.circolo.security.torneo.TorneoRequestModifyData;
import com.circolo.security.torneo.TorneoRequestModifyLuogo;
import com.circolo.security.torneo.TorneoRequestModifyNTurni;
import com.circolo.security.torneo.TorneoRequestModifyName;
import com.circolo.security.torneo.TorneoRequestModifyStato;
import com.circolo.security.torneo.TorneoRequestModifyTurno;
import com.circolo.security.torneo.TorneoResponse;
import com.circolo.security.torneo.TorneoService;
import com.circolo.security.user.UserRepository;



@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class TorneoServiceTest {
	
	
	@Mock
	private TorneoRepository torneoRepository;
	
	private IscrizioneService iscrizioneService;
	private UserRepository userRepository;
	private PartitaRepository partitaRepository;
	
	@InjectMocks
	private TorneoService underTest;
	
	@BeforeEach
	void setUp() {
		
		underTest = new TorneoService(torneoRepository,iscrizioneService,userRepository,partitaRepository);
	}
	
	@Test
	void getTorneo() {
		
		//when
		underTest.getTorneo();	
		//then
		verify(torneoRepository).findAll();
	}
	
	@Test
	@Disabled
	void getListaIscrizioniTorneo() {
		
	}
	
	
	@Test
	@Disabled
	void getSingoloTorneo() {
		
	}
	
	
	
	@Test
	void addNewTorneo() {
		
		// Given
		
		String data = "13-04-2023 10:30";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime formatDateTime= LocalDateTime.parse(data, formatter);
        String nome = "Excelsior";
        String luogo = "Casa Giovane";
        Integer turni = 5;
        
        Torneo testTorneo = new Torneo(
        		nome,formatDateTime,luogo,turni);
        
        
        
        // When 
        
        underTest.addNewTorneo(testTorneo);

        
        // Then
        
        ArgumentCaptor<Torneo> torneoArgumentCaptor =
        		ArgumentCaptor.forClass(Torneo.class);
        
        verify(torneoRepository)
        	.save(torneoArgumentCaptor.capture());
        
        	
        Torneo catturato = torneoArgumentCaptor.getValue();
        assertThat(catturato).isEqualTo(testTorneo);
        
       		
	}
	
	@Test
	void modifyDataTorneo() {
		
		String data = "13-04-2023 10:30";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime formatDateTime= LocalDateTime.parse(data, formatter);
        String nome = "Excelsior";
        String luogo = "Casa Giovane";
        Integer turni = 5;
        
        Torneo testTorneo = new Torneo(
        		nome,formatDateTime,luogo,turni);
        
        testTorneo.setId_torneo(1);
        underTest.addNewTorneo(testTorneo);
        
        ArgumentCaptor<Torneo> torneoArgumentCaptor =
        		ArgumentCaptor.forClass(Torneo.class);
        verify(torneoRepository)
    	.save(torneoArgumentCaptor.capture());
        
        
        
		String data_nuova = "15-04-2023 10:30";
        LocalDateTime formatDateTime_nuova= LocalDateTime.parse(data_nuova, formatter);


        
        TorneoRequestModifyData modifica = new TorneoRequestModifyData();

        modifica.setId_torneo(1);
        modifica.setData_torneo(formatDateTime_nuova);
        
        underTest.modifyDataTorneo(modifica);
        
        Integer catturato_id = torneoArgumentCaptor.getValue().getId_torneo();
        assertThat(catturato_id).isEqualTo(modifica.getId_torneo());
        
        LocalDateTime catturato_data = torneoArgumentCaptor.getValue().getData_torneo();
        
        assertThat(catturato_data).isNotEqualTo(modifica.getData_torneo());
		
	}
	
	@Test
	void modifyDataTorneoEquals() {
		
		String data = "13-04-2023 10:30";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime formatDateTime= LocalDateTime.parse(data, formatter);
        String nome = "Excelsior";
        String luogo = "Casa Giovane";
        Integer turni = 5;
        
        Torneo testTorneo = new Torneo(
        		nome,formatDateTime,luogo,turni);
        
        testTorneo.setId_torneo(1);
        underTest.addNewTorneo(testTorneo);
        
        ArgumentCaptor<Torneo> torneoArgumentCaptor =
        		ArgumentCaptor.forClass(Torneo.class);
        verify(torneoRepository)
    	.save(torneoArgumentCaptor.capture());
        
        
        
		String data_nuova = "13-04-2023 10:30";
        LocalDateTime formatDateTime_nuova= LocalDateTime.parse(data_nuova, formatter);


        
        TorneoRequestModifyData modifica = new TorneoRequestModifyData();

        modifica.setId_torneo(1);
        modifica.setData_torneo(formatDateTime_nuova);
        
        underTest.modifyDataTorneo(modifica);
        
        Integer catturato_id = torneoArgumentCaptor.getValue().getId_torneo();
        assertThat(catturato_id).isEqualTo(modifica.getId_torneo());
        
        LocalDateTime catturato_data = torneoArgumentCaptor.getValue().getData_torneo();
        
        assertThat(catturato_data).isEqualTo(modifica.getData_torneo());
		
		
	}

	
	@Test
	void modifyLuogo() {
		
		String data = "13-04-2023 10:30";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime formatDateTime= LocalDateTime.parse(data, formatter);
        String nome = "Excelsior";
        String luogo = "Casa Giovane";
        Integer turni = 5;
        
        Torneo testTorneo = new Torneo(
        		nome,formatDateTime,luogo,turni);
        
        testTorneo.setId_torneo(1);
        underTest.addNewTorneo(testTorneo);
        
        ArgumentCaptor<Torneo> torneoArgumentCaptor =
        		ArgumentCaptor.forClass(Torneo.class);
        verify(torneoRepository)
    	.save(torneoArgumentCaptor.capture());
        
        TorneoRequestModifyLuogo modifica = new TorneoRequestModifyLuogo();
        modifica.setId_torneo(1);
        modifica.setLuogo("Milano");
        
        underTest.modifyLuogo(modifica);
        
        Integer catturato_id = torneoArgumentCaptor.getValue().getId_torneo();        
        String catturato_luogo = torneoArgumentCaptor.getValue().getLuogo();
        
        assertThat(catturato_id).isEqualTo(modifica.getId_torneo());
        assertThat(catturato_luogo).isNotEqualTo(modifica.getLuogo());
		
	}
	
	@Test
	void modifyNTurni() {
		
		String data = "13-04-2023 10:30";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime formatDateTime= LocalDateTime.parse(data, formatter);
        String nome = "Excelsior";
        String luogo = "Casa Giovane";
        Integer turni = 5;
        
        Torneo testTorneo = new Torneo(
        		nome,formatDateTime,luogo,turni);
        
        testTorneo.setId_torneo(1);
        underTest.addNewTorneo(testTorneo);
        
        ArgumentCaptor<Torneo> torneoArgumentCaptor =
        		ArgumentCaptor.forClass(Torneo.class);
        verify(torneoRepository)
    	.save(torneoArgumentCaptor.capture());
        
   
        TorneoRequestModifyNTurni modifica = new TorneoRequestModifyNTurni();

        modifica.setId_torneo(1);
        modifica.setNumero_turni(7);
        
        underTest.modifyNTurni(modifica);
        
        Integer catturato_id = torneoArgumentCaptor.getValue().getId_torneo();
        assertThat(catturato_id).isEqualTo(modifica.getId_torneo());
        
        Integer catturato_turni = torneoArgumentCaptor.getValue().getNumero_turni();
        
        assertThat(catturato_turni).isNotEqualTo(modifica.getNumero_turni());
		
		
	}
	
	@Test
	void modifyTurno() {
		
		
		String data = "13-04-2023 10:30";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime formatDateTime= LocalDateTime.parse(data, formatter);
        String nome = "Excelsior";
        String luogo = "Casa Giovane";
        Integer turni = 5;
        
        Torneo testTorneo = new Torneo(
        		nome,formatDateTime,luogo,turni);
        
        testTorneo.setId_torneo(1);
        underTest.addNewTorneo(testTorneo);
        
        ArgumentCaptor<Torneo> torneoArgumentCaptor =
        		ArgumentCaptor.forClass(Torneo.class);
        verify(torneoRepository)
    	.save(torneoArgumentCaptor.capture());
        
   
        TorneoRequestModifyTurno modifica = new TorneoRequestModifyTurno();

        modifica.setId_torneo(1);
        modifica.setTurno_attuale(3);
        
        underTest.modifyTurno(modifica);
        
        Integer catturato_id = torneoArgumentCaptor.getValue().getId_torneo();
        assertThat(catturato_id).isEqualTo(modifica.getId_torneo());
        
        Integer catturato_turno = torneoArgumentCaptor.getValue().getTurno_attuale();
        
        assertThat(catturato_turno).isNotEqualTo(modifica.getTurno_attuale());
		
	}
	
	
	@Test
	void modifyNome() {
		
		String data = "13-04-2023 10:30";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime formatDateTime= LocalDateTime.parse(data, formatter);
        String nome = "Excelsior";
        String luogo = "Casa Giovane";
        Integer turni = 5;
        
        Torneo testTorneo = new Torneo(
        		nome,formatDateTime,luogo,turni);
        
        testTorneo.setId_torneo(1);
        underTest.addNewTorneo(testTorneo);
        
        ArgumentCaptor<Torneo> torneoArgumentCaptor =
        		ArgumentCaptor.forClass(Torneo.class);
        verify(torneoRepository)
    	.save(torneoArgumentCaptor.capture());
        
   
        TorneoRequestModifyName modifica = new TorneoRequestModifyName();

        modifica.setId_torneo(1);
        modifica.setNome_torneo("Nuovo Nome");
        
        underTest.modifyNome(modifica);
        
        Integer catturato_id = torneoArgumentCaptor.getValue().getId_torneo();
        assertThat(catturato_id).isEqualTo(modifica.getId_torneo());
        
        String catturato_nome = torneoArgumentCaptor.getValue().getNome_torneo();
        
        assertThat(catturato_nome).isNotEqualTo(modifica.getNome_torneo());
		
	}
	
	@Test
	void modifyStato() {
		
		String data = "13-04-2023 10:30";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime formatDateTime= LocalDateTime.parse(data, formatter);
        String nome = "Excelsior";
        String luogo = "Casa Giovane";
        Integer turni = 5;
        
        Torneo testTorneo = new Torneo(
        		nome,formatDateTime,luogo,turni);
        
        testTorneo.setId_torneo(1);
        underTest.addNewTorneo(testTorneo);
        
        ArgumentCaptor<Torneo> torneoArgumentCaptor =
        		ArgumentCaptor.forClass(Torneo.class);
        verify(torneoRepository)
    	.save(torneoArgumentCaptor.capture());
        
   
        TorneoRequestModifyStato modifica = new TorneoRequestModifyStato();

        modifica.setId_torneo(1);
        modifica.setIn_corso(true);
        
        underTest.modifyStato(modifica);
        
        Integer catturato_id = torneoArgumentCaptor.getValue().getId_torneo();
        assertThat(catturato_id).isEqualTo(modifica.getId_torneo());
        
        boolean catturato_stato = torneoArgumentCaptor.getValue().isIn_corso();
        
        assertThat(catturato_stato).isNotEqualTo(modifica.isIn_corso());
		
	}

	
	@Test
	@Disabled
	void generaPartiteTurno() {
		
	}
	
	@Test
	@Disabled
	void classifica() {
		
	}
	
	@Test
	@Disabled
	void deleteTorneo() {
		
	}

}
