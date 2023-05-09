package com.circolo.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.circolo.security.user.UserRepository;

/*
 * Con la notazione Configuration all'avvio della applicazione spring 
 *  riprenderà la classe e prova a implementare  e amministrare
 *   tutti e Beans
 *  che dichiaro in questa classe  ApplicationConfig,
 *  @RequiredArgsConstructor -> per amministrare qualcosa
 *  
 */


@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

	// Perchè devo prendere l'user dal Db mi serve repository
  private final UserRepository repository;

  @Bean
  public UserDetailsService userDetailsService() {
    return username -> repository.findByEmail(username)
        .orElseThrow(() -> new UsernameNotFoundException("Non è stato trovate l'utente"));
  }
  
  /*
   * AuthenticationProvider è il Data Access Object che è responsabile
   * di andare a prendere UserDetails e codificare la password
   * 
   * 
   */
  
  /* ProviderManager delega ad una lista di
   * istanze di AuthenticationProvider.
   * Ogni AuthenticationProvider ha l'opportunità di indicare
   * che l'autenticazione deve avere successo, fallire
   * o indicare che non può prendere una decisione 
   * e consentire a un AuthenticationProvider downstream 
   * di decidere.
   * 
   * In pratica ogni AuthenticationProvider
   *  sa come eseguire uno specifico tipo di autenticazione
   */
  
  
  /*AuthenticationProvider 
   * Puoi inserire più istanze di AuthenticationProviders 
   * in ProviderManager. Ogni AuthenticationProvider 
   * esegue un tipo specifico di autenticazione. 
   * Ad esempio, DaoAuthenticationProvider supporta
   *  l'autenticazione basata su nome utente/password,
   *   mentre JwtAuthenticationProvider supporta l'autenticazione
   *    di un token JWT
   * 
   */
  /* DaoAuthenticationProvider è un implementazione
   *  AuthenticationProvider  che utilizza UserDetailService e 
   *  PasswordEncoder per autenticare username e password
   * 
   * 
   */
  @Bean
  public AuthenticationProvider authenticationProvider() { // setto il tipo di AuthenticationProvider che in questo caso è di tipo DAO
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    //Diciamo quale DetailsService utilizzare per prendere informazioni
    // sul nostro user perhcé avremmo tante implementazioni di 
    //User details
    
    // Dao ha bisogno di username e password...
    authProvider.setUserDetailsService(userDetailsService());
    authProvider.setPasswordEncoder(passwordEncoder());
    return authProvider;
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
    return config.getAuthenticationManager(); // mi dice che tipo di autenticazione sto utilizzando
  }

  @Bean
  public PasswordEncoder passwordEncoder() { // Utile per AuthenticationProvider
    return new BCryptPasswordEncoder();
  }

}