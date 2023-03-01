package com.circolo.security.torneo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.circolo.security.iscrizione.Iscrizione;
import com.circolo.security.partita.Partita;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_torneo")

public class Torneo {
		
	  @Id
	  @GeneratedValue
	  private Integer id_torneo; 
	  
	  
	  private String nome_torneo;
	  

	  @JsonFormat(pattern="dd-MM-yyyy HH:mm")
	  @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
	  private LocalDateTime data_torneo;
	  
	  private String luogo;
	  
	  private Integer numero_turni;
	  
	  @Default
	  private  Integer turno_attuale = 0;
	  
	  @Default 
	  private boolean in_corso = false;
	  
	  Torneo(String nome_torneo,LocalDateTime data_torneo, String luogo, Integer numero_turni){
		  
		  this.nome_torneo = nome_torneo;
		  this.data_torneo = data_torneo;
		  this.luogo = luogo;
		  this.numero_turni = numero_turni;
		  
		  turno_attuale = 0;
		  in_corso = false;
		  
		  iscrizioni = new ArrayList<>();
		  partite = new ArrayList<>();
		 
	  }
	  
	  // Ogni torneo ha più iscrizioni ha più iscrizioni, tocca a me gestirli, Lista inizializzata
	  
		@OneToMany(mappedBy = "torneo",
				fetch = FetchType.LAZY,
				targetEntity = Iscrizione.class,
				cascade = CascadeType.PERSIST)
		private List<Iscrizione> iscrizioni;
		
		@OneToMany(
				mappedBy = "torneo",
				fetch = FetchType.LAZY,
				targetEntity = Partita.class,
				cascade = CascadeType.ALL)
		private List<Partita> partite;

		
		
		public void   AddtIscrizioni(Iscrizione iscrizione_){	
			
			System.out.println("-----------------------");
			System.out.println("Stai aggiungendo un elemento nella lista iscrizioni");
			System.out.println("-----------------------");

			iscrizioni.add(iscrizione_);
		}
		
		public List<Iscrizione> getIscrizioni(){
			return iscrizioni;
		}
		
		public void deleteIscrizioni(Iscrizione iscrizione){
						
			iscrizioni.remove(iscrizione);
			System.out.println("è avvenuta una rimozione dalla lista iscrizioni");
			
		}
		
		public void AddPartite(Partita partita_) {
			
			System.out.println("-----------------------");
			System.out.println("Stai aggiungendo un elemento nella lista partite");
			System.out.println("-----------------------");

			partite.add(partita_);
			
		}
		
		public void setPartita(Partita partita,int i){
			
			partite.get(i).setId_sfidante1(partita.getId_sfidante1());
			partite.get(i).setId_sfidante2(partita.getId_sfidante2());
			partite.get(i).setRisultato_sfidante1(null);
			partite.get(i).setRisultato_sfidante2(null);



		}
	
		public List<Partita> getPartite(){
			return partite;
		}
	
	

}
