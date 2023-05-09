package com.circolo.security.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/*Utilizzano per estrarre informazioni dal token come username/email
 * 
 * i Claims sono i payload ovvero la parte dei token contenente i dati
 * di interesse in formato JSON
 */

@Service
public class JwtService {

  private static final String SECRET_KEY = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";

  // Subject è la mail o username del mio user
  
  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }
   // Function necessita di Claims e T che è il tipo che voglio ritornare
  
  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  public String generateToken(UserDetails userDetails) {
    return generateToken(new HashMap<>(), userDetails);
  }

  /*
   * Devo mappare le stringhe con oggetti che rappresentano i claims
   * Userdetails che vogliamo generare all'interno del token
   */
  public String generateToken(
      Map<String, Object> extraClaims,
      UserDetails userDetails
  ) {
	  // Dopo setClaims(extraClaims) ho i claims che posso estrarre per i miei controlli
    return Jwts	
        .builder()
        .setClaims(extraClaims)
        .setSubject(userDetails.getUsername())
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
        .signWith(getSignInKey(), SignatureAlgorithm.HS256)
        .compact();
  }
  

  /*
   * Ho bisogno di Userdetails perché  devo controllare se questo token
   * appartiene a questo user
   */
  public boolean isTokenValid(String token, UserDetails userDetails) {
    final String username = extractUsername(token);
    return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
  }

  private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  private Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }
  
  /*
   * SignInKey è segreto ed è usato come firma digitale del JWT 
   * utilizzato per creare la signature di JWT il quale verifica
   * l'integrita del payload.
   * 
   * 
   * estraggo tutti i claims-> tutti i dati quindi nome, cognome
   *  ed un altro metodo ecc..
   *  un claim per esempio è nome
   * che consente cdi estrarre un singolo claim
   */

  private Claims extractAllClaims(String token) {
    return Jwts
        .parserBuilder()
        .setSigningKey(getSignInKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  private Key getSignInKey() {
    byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
    return Keys.hmacShaKeyFor(keyBytes);
  }
}