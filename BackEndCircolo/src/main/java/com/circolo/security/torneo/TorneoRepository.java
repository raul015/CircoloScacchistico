package com.circolo.security.torneo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TorneoRepository extends JpaRepository<Torneo,Integer>{

	//Optional<Torneo> findTorneoById_Torneo(Integer id_torneo);

	
}