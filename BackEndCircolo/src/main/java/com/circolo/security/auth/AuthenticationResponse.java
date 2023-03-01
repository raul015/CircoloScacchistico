package com.circolo.security.auth;


import com.circolo.security.user.Role;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

  private Integer id_user;
  private String token;
  
  @Enumerated(EnumType.STRING)
  private  Role role;
  
  private String firstname;
  private String lastname;
  

}