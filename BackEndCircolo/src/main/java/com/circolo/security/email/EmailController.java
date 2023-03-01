package com.circolo.security.email;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
public class EmailController {
	
	private final EmailServiceImplementation service;
	
	
	  @PostMapping("/api/v1/auth/send-email")
	  public ResponseEntity<EmailResponse> sendemail(
	      @RequestBody EmailRequest request
	  ) {
	    return ResponseEntity.ok(service.sendEmail(request));
	  }

}



