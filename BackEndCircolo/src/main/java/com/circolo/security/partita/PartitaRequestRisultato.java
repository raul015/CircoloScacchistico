package com.circolo.security.partita;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PartitaRequestRisultato {

	private Integer id_partita;	
	private Double punti_bianco;
	private Double punti_nero;
	
}
