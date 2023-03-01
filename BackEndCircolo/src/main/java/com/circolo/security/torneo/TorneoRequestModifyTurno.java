package com.circolo.security.torneo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class TorneoRequestModifyTurno {
	
	private Integer id_torneo;
	private Integer turno_attuale;

}
