package com.circolo.security.partita;

import java.time.LocalDateTime;
import java.util.List;

import com.circolo.security.iscrizione.Iscrizione;
import com.circolo.security.torneo.Torneo;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.CascadeType;
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

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_partita")
public class Partita {
	
	@Id
	@GeneratedValue
	private Integer id_partita;
	
	private Integer id_sfidante1;
	private Integer id_sfidante2;
	
	private Integer turno;
	
	private Double risultato_sfidante1;
	private Double risultato_sfidante2;
	
	
	public Partita(Integer id_sfidante1, Integer id_sfidante2, Integer turno, Double risultato_sfidante1,
			Double risultato_sfidante2) {
		
		this.id_sfidante1 = id_sfidante1;
		this.id_sfidante2 = id_sfidante2;
		this.turno = turno;
		this.risultato_sfidante1 = risultato_sfidante1;
		this.risultato_sfidante2 = risultato_sfidante2;
	
	}
	
	
	@ManyToOne(cascade= CascadeType.ALL,
				fetch = FetchType.LAZY)
	@JoinColumn(name="torneoid")
	private Torneo torneo;
	
	
	@JsonBackReference(value ="something")
	public Torneo getTorneo(){
		return torneo;
	}
	
	@JsonBackReference(value="something") // senza questo mi ritorna degli errori strani, per ora sembra una buona soluzione 
	public void setTorneo(Torneo torneo) {
		this.torneo = torneo;
	}
	

}
