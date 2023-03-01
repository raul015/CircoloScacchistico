package com.circolo.security.torneo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TorneoRequestModifyLuogo {
	
	private Integer id_torneo;
	private String luogo;

}
