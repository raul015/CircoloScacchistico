package com.circolo.security.user;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.Collection;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.circolo.security.iscrizione.Iscrizione;


/* Per evirare che ci siano tanti metodi utilizzo Lombok annotations
 *  Data  --> get and setter
 * 	Builder --> aiuta a buildare il mio oggetto usando il design-pattern builder 
 * NoArgsConstructor --> per il design pattern builder, abbiamo
 * sempre bisogno di tutti gli args 
 * AllArgsConstructor -->  per l'user class
 * Entity -> user class diventa una entità
 * 
 * 
 * L'applicazione userà un oggetto chiamato UserDetails 
 * e questo è un interfaccia che contiene  molti metodi ( da implementare),
 * ed ogni volta che lavoro con spring security ho bisogno
 * di garantire che tu hai bisogno di fornire UserDetails object
 * per fare uno spring security facile da usare 
 * 
 */


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_user")
public class User implements UserDetails {
		
  @Id
  @GeneratedValue
  private Integer id_user;  
  
  private String firstname;
  
  private String lastname;
  
 
  private String email;
  
  private String password;

  @Enumerated(EnumType.STRING)
  private Role role;
  
  User(String firstname,String lastname, String email, Role role,String password){
	  this.firstname = firstname;
	  this.lastname = lastname;
	  this.email = email;
	  this.password = password;
	  this.role = role;
	  	    }
  
  
  // mi restituisce una lista di Role, quindi definisco un role di
  //sopra 
  
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(role.name()));
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
  //			cascade = CascadeType.ALL,
	//		orphanRemoval = true
  
	@OneToMany(mappedBy = "user",
			fetch = FetchType.LAZY,
			targetEntity = Iscrizione.class,
			cascade = CascadeType.ALL)
	
		private List<Iscrizione> iscrizioni; // inizializzo la lista di iscrizioni...

	
	
	// In questo modo inserisci le iscrizioni qua dentro e le prendo per ogni utente...
	
	public void   AddtIscrizioni(Iscrizione iscrizione){
		 iscrizioni.add(iscrizione);
	}
	
	public List<Iscrizione> getIscrizioni(){
		return iscrizioni;
	}

	public void deleteIscrizioni(Iscrizione iscrizione){
		
		iscrizioni.remove(iscrizione);
		System.out.println("è avvenuta una rimozione dalla lista iscrizioni");
		
	}
  

}