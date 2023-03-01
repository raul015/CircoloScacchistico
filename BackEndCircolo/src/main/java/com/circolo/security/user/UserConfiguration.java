package com.circolo.security.user;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;


import lombok.RequiredArgsConstructor;


@Configuration
@RequiredArgsConstructor
public class UserConfiguration {
	
	  private final PasswordEncoder passwordEncoder;
	
	/*
	 * Verra runnato dopo che l'applicazione viene runnata 
	 * Utilizzo l'interfaccia per avere i metodi per fare
	 * delle operazioni....
	 */
	
	@Bean
	CommandLineRunner commandLineRunnerUtent(UserRepository repository){		
	
		return args-> {
						
			User raul = new User(
					"Raul",
					"Luizaga",
					"raul01597@gmail.com",
					 Role.ADMIN,
					passwordEncoder.encode("password")
					);
			
			User emanuele = new User(
					"Emauele",
					"Rizzi",
					"e.rizzi@studenti.unibg.it",
					 Role.ADMIN,
					 passwordEncoder.encode("password")
					);
			
			User forfait = new User(
					"forfait",
					"forfait",
					"forfait",
					 Role.USER,
					 passwordEncoder.encode("forfait")
					);
			
			
/*	Ultimo passo è implementare l'algoritmo di generazione dei turni del 
 * torneo, di conseguenza inserisco manualmente gli ipotetici User all'interno
 * del circolo scacchistico
 * 
 * 
 */
			

			// Inserisco 10 giocatori per il primo torneo...
			
			User giambalvo = new User(
					"Domenico",
					"Giambalvo",
					"giambalvo@gmail.com",
					 Role.USER,
					 passwordEncoder.encode("domenico")
					); //1
			
			User vehat = new User(
					"Adi",
					"Vehat",
					"vehat@gmail.com",
					 Role.USER,
					 passwordEncoder.encode("adi")
					); //2
			
			User bisceglia = new User(
					"Marco",
					"Bisceglia",
					"bisceglia@gmail.com",
					 Role.USER,
					 passwordEncoder.encode("marco")
					); //3
			
			User carnovale = new User(
					"Domenico",
					"Carnovale",
					"carnovale@gmail.com",
					 Role.USER,
					 passwordEncoder.encode("domenico")
					); //4
			
			User nava = new User(
					"Lorenzo",
					"Nava",
					"nava@gmail.com",
					 Role.USER,
					 passwordEncoder.encode("lorenzo")
					); //5
			
			User lazzari = new User(
					"Filippo",
					"Lazzari",
					"lazzari@gmail.com",
					 Role.USER,
					 passwordEncoder.encode("filippo")
					); //6
			
			User zraiba = new User(
					"Youssef",
					"Zraiba",
					"zraiba@gmail.com",
					 Role.USER,
					 passwordEncoder.encode("youssef")
					); //7
			
			User mirra = new User(
					"Giuseppe",
					"Mirra",
					"mirra@gmail.com",
					 Role.USER,
					 passwordEncoder.encode("giuseppe")
					); //8
			
			User morescalchi = new User(
					"Giulio",
					"Morescalchi",
					"morescalchi@gmail.com",
					 Role.USER,
					 passwordEncoder.encode("giulio")
					); //9
			
			User pasolini = new User(
					"Michele",
					"Pasolini",
					"pasolini@gmail.com",
					 Role.USER,
					 passwordEncoder.encode("michele")
					); //10
			
			
			
			
			
			
			// quanto invoco saveAll viene aggiunto al database
			repository.saveAll(
					List.of(raul,
							emanuele,
							forfait,
							giambalvo,
							vehat,
							bisceglia,
							carnovale,
							nava,
							lazzari,
							zraiba,
							mirra,
							morescalchi,
							pasolini
							)
					);
				
		};
		
		
		
	}
}