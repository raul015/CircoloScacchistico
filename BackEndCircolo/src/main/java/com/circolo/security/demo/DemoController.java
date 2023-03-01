package com.circolo.security.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/demo-controller")
public class DemoController {

  @GetMapping
  public ResponseEntity<String> sayHello() {
    return ResponseEntity.ok("Ciao dalla richiesta http get, Token valido");
  }

  @PostMapping
  public ResponseEntity<String> PasswordDimenticata() {
    return ResponseEntity.ok("Ciao dalla richiesta http get, Token valido");
  }
}