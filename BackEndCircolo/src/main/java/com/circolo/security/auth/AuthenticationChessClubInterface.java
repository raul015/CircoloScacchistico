package com.circolo.security.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface AuthenticationChessClubInterface {

	
	  @PostMapping("/register")
	  public ResponseEntity<RegisterResponse> register(
	      @RequestBody RegisterRequest request
	  );
	  
	  @PostMapping("/authenticate")
	  public ResponseEntity<AuthenticationResponse> authenticate(
	      @RequestBody AuthenticationRequest request
	  );
	  
	  
	  @PostMapping("/password")
	  public ResponseEntity<PasswordResponse> password(
	      @RequestBody PasswordRequest request
	  );
}
