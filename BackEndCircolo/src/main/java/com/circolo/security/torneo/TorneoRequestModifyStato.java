package com.circolo.security.torneo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TorneoRequestModifyStato {

	private Integer id_torneo;
	
	@Getter
	private boolean in_corso;
	
}
