package com.circolo.security.auth;

import com.circolo.security.config.JwtService;
import com.circolo.security.user.Role;
import com.circolo.security.user.User;
import com.circolo.security.user.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
  private final UserRepository repository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  public RegisterResponse register(RegisterRequest request) {
	  
    var user = User.builder()
        .firstname(request.getFirstname())
        .lastname(request.getLastname())
        .email(request.getEmail())
        .password(passwordEncoder.encode(request.getPassword()))
        .role(Role.USER)
        .build();
    
    if(!repository.findByEmail(request.getEmail()).isPresent()	){
    	
        repository.save(user);
        
        var jwtToken = jwtService.generateToken(user);
        return RegisterResponse.builder()
            .token(jwtToken)
            .successo("success")
            .build();
    }
    
    return RegisterResponse.builder()
        .token("l'utente che hai inserito è già registrato")
        .successo("fault")
        .build();
  }

  public AuthenticationResponse authenticate(AuthenticationRequest request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            request.getEmail(),
            request.getPassword()
        )
    );
    var user = repository.findByEmail(request.getEmail())
        .orElseThrow();
  
    var jwtToken = jwtService.generateToken(user);
    return AuthenticationResponse.builder()
    	.id_user(user.getId_user())
        .token(jwtToken)
        .role(user.getRole())
        .firstname(user.getFirstname())
        .lastname(user.getLastname())
        .build();
  }  
  
  public PasswordResponse password(PasswordRequest request) {
	  
	    if(repository.findByEmail(request.getEmail()).isPresent()){
	    	
		    var user = repository.findByEmail(request.getEmail())
		            .orElseThrow();
		    
		    if(user.getFirstname().equalsIgnoreCase(request.getFirstname())  && user.getLastname().equalsIgnoreCase(request.getLastname()) ) {
		    	
		    		
				    var jwtToken = jwtService.generateToken(user);
				    return PasswordResponse.builder()
				            .token(jwtToken)
				            .successo("success")
				            .build();   	
		    	
		    }
		    
		    else {
	
		    	
		    	if(user.getFirstname().equalsIgnoreCase(request.getFirstname())) {
		    		
				    return PasswordResponse.builder()
				            .token("nome inserito non corretto")
				            .successo("fault")
				            .build();   	
		    		
		    	}
		    	else {
		    		
				    return PasswordResponse.builder()
				            .token("cognome inserito non corretto")
				            .successo("fault")
				            .build();   
		    	}
		    	
		    	
		    }
		    
		   
		        
		    

	    }
	    
	    
	  
	    return PasswordResponse.builder()
	            .token("la mail inserita non è stata trovata")
	            .successo("fault")
	            .build();

	    
	  }
}