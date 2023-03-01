package com.circolo.security.iscrizione;

import com.circolo.security.torneo.Torneo;
import com.circolo.security.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder.Default;

//@IdClass(IscrizioneKey.class)

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_iscrizione")
public class Iscrizione {
	
	
//	@EmbeddedId
//	private IscrizioneKey id_iscrizione;
	 
//
//	@Id
//	@Column(insertable=false, updatable=false,)
//	private Integer userid;
//	
//	@Id
//	@Column(insertable=false, updatable=false)
//	private Integer torneoid;
//	
	
	
	// Provo ad utilizzare un solo Id
	
	@Id
	@GeneratedValue
	private Integer id_iscrizione;

//	@ManyToOne
//	@JoinColumn(name = "userid",referencedColumnName = "id_user",insertable=false, updatable=false)
//referencedColumnName = "id_user",
	
	@ManyToOne(cascade = CascadeType.PERSIST,
			targetEntity = User.class,
			fetch = FetchType.LAZY)
	
	@JoinColumn(name="userid")
	private User user;
	
	
	@JsonBackReference(value="something")
	public User getUser(){
		return user;
	}
	
	@JsonBackReference(value="something")
	public void setUser(User user){
		this.user = user;
	}
	
	@JsonBackReference(value="something")
	public void deleteUser(){
		this.user = null;
		System.out.println("User della iscrizione " + id_iscrizione + "è stato eliminato");
	}
	
//referencedColumnName = "id_torneo"
//	@ManyToOne
//	@JoinColumn(name = "torneoid",referencedColumnName = "id_torneo",insertable=false, updatable=false)
	
	@ManyToOne(cascade= CascadeType.PERSIST,
			targetEntity = Torneo.class,
			fetch = FetchType.LAZY)
	@JoinColumn(name="torneoid")
	private Torneo torneo;
	
	@JsonBackReference(value ="something1")
	public Torneo getTorneo(){
		return torneo;
	}
	
	@JsonBackReference(value="something1") // senza questo mi ritorna degli errori strani, per ora sembra una buona soluzione 
	public void setTorneo(Torneo torneo) {
		this.torneo = torneo;
	}
	
	
	@JsonBackReference(value="something1")
	public void deleteTorneo(){
		this.torneo = null;
		System.out.println("Il torneo dalla iscrizione " + id_iscrizione + "è stato eliminato");
	}
	
	@Column(precision= 2)
	@Default
	private Double punteggio =  0.0;
	
}
