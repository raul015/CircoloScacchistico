package com.circolo.security.iscrizione;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IscrizioneRequestModifyPunteggio {

	private Integer id_utente;
	private Integer id_torneo;
	private Integer id_iscrizione;
	private Double punteggio;
}
