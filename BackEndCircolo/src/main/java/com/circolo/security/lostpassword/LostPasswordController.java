package com.circolo.security.lostpassword;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/api/v1/change-password")// Sono fuori auth --> Avrò bisogno di un tokena Beare per inviare le richieste Http
public class LostPasswordController {
	
	private final LostPasswordService lostPasswordService;
	
	@Autowired
	public LostPasswordController(LostPasswordService lostPasswordService) {
		// TODO Auto-generated constructor stub
		this.lostPasswordService = lostPasswordService;
	}
	
	// Qui inserisco le API per cambiare Password
	
	  @Transactional
	  @PutMapping
	  public ResponseEntity<LostPasswordResponse> changepassword(
	      @RequestBody LostPasswordRequest request
	  ) {
	    return ResponseEntity.ok(lostPasswordService.changepassword(request));
	  }
	  


}
