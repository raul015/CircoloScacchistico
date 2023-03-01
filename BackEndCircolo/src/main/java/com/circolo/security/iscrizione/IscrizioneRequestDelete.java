package com.circolo.security.iscrizione;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IscrizioneRequestDelete {
	
	private Integer id_user;
	private Integer id_torneo;

}
