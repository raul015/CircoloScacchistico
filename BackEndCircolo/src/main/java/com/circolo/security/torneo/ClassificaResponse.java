package com.circolo.security.torneo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClassificaResponse {
	private Integer idtorneo;
	private Integer iduser;
	private String firstname;
	private double punteggio;
}
