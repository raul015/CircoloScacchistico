package com.circolo.security.email;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface EmailChessClubInterface {
	
	  @PostMapping("/api/v1/auth/send-email")
	  public ResponseEntity<EmailResponse> sendemail(
	      @RequestBody EmailRequest request
	  );

}
