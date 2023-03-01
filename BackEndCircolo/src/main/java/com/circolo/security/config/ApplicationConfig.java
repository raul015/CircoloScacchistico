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
   */

  @Bean
  public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    //Diciamo quale DetailsService utilizzare per prendere informazioni
    // sul nostro user perhcé avremmo tante implementazioni di 
    //User details
    authProvider.setUserDetailsService(userDetailsService());
    authProvider.setPasswordEncoder(passwordEncoder());
    return authProvider;
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
    return config.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

}