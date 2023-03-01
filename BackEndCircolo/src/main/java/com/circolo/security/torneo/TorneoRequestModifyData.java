package com.circolo.security.torneo;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TorneoRequestModifyData{
	
	  private Integer id_torneo;
	  
	  @JsonFormat(pattern="dd-MM-yyyy HH:mm")
	  private LocalDateTime data_torneo;

}
