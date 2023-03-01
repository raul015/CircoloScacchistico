package com.circolo.security.iscrizione;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IscrizioneRepository extends JpaRepository<Iscrizione,Integer>{
	
	// Qui posso implementare una ricerca per i vari ID (Forse), Poi forse cambio questi metodi...
	
}
