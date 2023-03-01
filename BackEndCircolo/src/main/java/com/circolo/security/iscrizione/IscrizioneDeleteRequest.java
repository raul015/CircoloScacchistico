package com.circolo.security.iscrizione;

import com.circolo.security.torneo.TorneoDeleteRequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IscrizioneDeleteRequest {
	
	private Integer id_partita;

}
