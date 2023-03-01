package com.circolo.security.partita;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.circolo.security.user.User;

@Repository
public interface PartitaRepository  extends JpaRepository<Partita,Integer> {
	
	// Qui posso fare i findBy a mio piacimento se necessario...

	  Optional<List<Partita>> findByTurno(Integer turno);

}
