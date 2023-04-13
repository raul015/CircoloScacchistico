package com.circolo.security.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/*
 * L'implementazione la deleghiamo alla class
 * AuthenticationService
 * 
 */
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController implements AuthenticationChessClubInterface{

  private final AuthenticationService service;

  @PostMapping("/register")
  public ResponseEntity<RegisterResponse> register(
      @RequestBody RegisterRequest request
  ) {
    return ResponseEntity.ok(service.register(request));
  }
  
  @PostMapping("/authenticate")
  public ResponseEntity<AuthenticationResponse> authenticate(
      @RequestBody AuthenticationRequest request
  ) {
    return ResponseEntity.ok(service.authenticate(request));
  }
  
  
  @PostMapping("/password")
  public ResponseEntity<PasswordResponse> password(
      @RequestBody PasswordRequest request
  ) {
    return ResponseEntity.ok(service.password(request));
  }
  


}