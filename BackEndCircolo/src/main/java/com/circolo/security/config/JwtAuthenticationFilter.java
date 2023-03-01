
package com.circolo.security.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/*
 *  Questa è la classe che si occupa di intercettare le richieste hhtp,
 *	vogliamo che il filtro sia attivo ad ogni richiesta hhtp,
 *
 */


@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtService jwtService; // per estrarre email dal token per check su db
  /*UserDetailsService
   * è un interfaccia dentro il framework di spring security, ho diverse
   * implementazioni ma vogliamo farne una nostra perché vogliamo 
   * prendere l'user dal database.
   * Adesso implementiamo o forniamo un bean di tipo UserDetailsService oppure 
   * creiamo una classe che implementa l'interfaccia e mettiamo
   * Service o Component annotazione così diventa un gestore di bean 
   * e spring sarà in grado di inserirlo...
   * 
   * Creo un classe ApplicationConfig che prende tutte le configurazioni
   * come Bean ed anche altro  
   */
  private final UserDetailsService userDetailsService;

  @Override
  protected void doFilterInternal(
     @NonNull HttpServletRequest request,
     @NonNull HttpServletResponse response,
     @NonNull FilterChain filterChain
  ) throws ServletException, IOException {
	  
	  /* Quando facciamo una chiamata dobbiamo passare 
	   * JWT token /bearer token dentro la testa
	   * ---> dobbiamo estrarre la testa. JWT token inizia con Bearer
	   * 
	   */
	  
    final String authHeader = request.getHeader("Authorization");
    final String jwt;
    final String userEmail; // definito per il check sul db, lo estraggo la JWT  token
    
    /*
     *  primo check non passato, quindi la request e la response vengon
     *	delegati al prossimo filtro, quindi non continuo con l'esecuzione 
     */
    if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }
    
    jwt = authHeader.substring(7); // è un JWT ma senza l'inizio del token
    userEmail = jwtService.extractUsername(jwt);
    
    // sono riuscito ad estrarre la mail dal token e controllo che user non è stato ancora autenticato(== null)
    
    if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
    	
    	// quindi ora controllo che la mail è dentro il DB perché non l'ho ancora controllato
    	// posso chiamare anche solo User perché estende UserDetails
    	
      UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
      
      // Controllo se il token è ancora valido 
      
      if (jwtService.isTokenValid(jwt, userDetails)) {
    	  // Classe di cui ha bisogno spring per aggiornare la security context
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
            userDetails,
            null,
            userDetails.getAuthorities()
        );
        authToken.setDetails(
            new WebAuthenticationDetailsSource().buildDetails(request)
        );
        SecurityContextHolder.getContext().setAuthentication(authToken);
      }
    }
    filterChain.doFilter(request, response);
  }
}