package com.circolo.security.partita;

import com.circolo.security.iscrizione.IscrizioneResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PartitaResponse {
	
	private String risposta;
	
}
