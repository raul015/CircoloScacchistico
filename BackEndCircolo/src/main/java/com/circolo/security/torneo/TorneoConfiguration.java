package com.circolo.security.torneo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class TorneoConfiguration {
		
	@Bean
	CommandLineRunner commandLineRunnerTorneo(TorneoRepository repository){
		
		return args->{
			
			
	        String data_1 = "15-05-2023 10:30";
	        String data_2 = "15-06-2023 09:30";

	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

	        LocalDateTime formatDateTime_1= LocalDateTime.parse(data_1, formatter);
	        LocalDateTime formatDateTime_2= LocalDateTime.parse(data_2, formatter);

			Torneo torneo_1 = new Torneo(
					"Primo torneo",
					formatDateTime_1,
					"Circolo Scacchistico",
					5
					);
			
			Torneo torneo_2 = new Torneo(
					"Secondo torneo",
					formatDateTime_2,
					"Circolo Scacchistico",
					5
					);
			
			
			repository.saveAll(
					List.of(torneo_1,torneo_2)
					);
			
				
		};
	}
	

}
