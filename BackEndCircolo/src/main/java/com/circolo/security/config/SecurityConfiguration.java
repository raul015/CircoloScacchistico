package com.circolo.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/*
 * Abbiamo bisogno di dire a Spring quale configurazione
 * vogliamo usare per far funzionare tutto questo ,
 * creiamo filtro ->UserDetailsServer - >validation ->
 * update ContextHolder, ci manca come legarli,
 * perché creiamo un filtro ma questo non è ancora 
 * stato usato... 
 * 
 * Questo è il senso di questa classe 
 * 
 * EnableWebSecurity -> quando runno si cercherà il SecurityFilterCHain
 * 
 */

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

	// Qui sono presenti i due filtri di autenticazione che utilizzo 
	
  private final JwtAuthenticationFilter jwtAuthFilter;
  private final AuthenticationProvider authenticationProvider;
  
  /* Quando parliamo di crere un account e fare login,
   * Per creare k'account non ho bisogno di JWT token perché in quel momento
   * noi creeremo a user account e faremo richiesta e richiederemo un token
   * dopo aver create l'account.
   * Lo stesso vale quando vogliamo fare Login, non abbiamo bisogno di 
   * passare il token JWT come parametro perché non lo abbiamo ancora,
   * così in questo caso parliamo di whitelisting : funzione che permette
   * di stilare un eleneco di utenti/servizi a cui è permesso eseguire 
   * determinate operazioni
   * 
   * 
   * Qui implemento la whitelist...
   * -	csrf -> disabilitato
   * -	autorizzo HTTP request 
   * -	dopo aver autorizzato chiamo requestMatchers che prende una lista di
   * 	stringhe  e una lista di modelli che rappresenteranno il modello
   * 	della nostra appicazione
   * -	permette tutte le liste presenti in requestMatchers
   * -	ogni richiesta voglio che sia autenticata
   * -	Quando implementiamo un filtro vogliamo che per ogni richiesta
   * 	ogni request deve essere autenticata, quindi non potremo
   * 	salvare authentication STATE o session STATE, questo ci aiuta 
   * 	ad assicurare che ogni richiesta dovrà essere autenticata
   * 
   * -	Scelta dell'AuthenticationProvider che voglio usare 
   */

  // Catena di filtri utilizzati
  
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf()
        .disable()
        .authorizeHttpRequests()
        .requestMatchers("/api/v1/auth/**")
        .permitAll()
        .anyRequest()
        .authenticated()
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authenticationProvider(authenticationProvider)
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }
}