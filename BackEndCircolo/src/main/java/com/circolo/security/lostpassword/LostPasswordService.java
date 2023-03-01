package com.circolo.security.lostpassword;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.circolo.security.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LostPasswordService {
		
	@Autowired
	private final UserRepository userRepository; // Per interrogare il Database
	  private final PasswordEncoder passwordEncoder;
	
	public LostPasswordResponse changepassword(LostPasswordRequest request) {
		

		
	  //  Optional<User> user_prova = userRepository.findByEmail(request.getEmail());
	    
	    if(!userRepository.findByEmail(request.getEmail()).isPresent()){
	    	
	    	// faccio cose
			return LostPasswordResponse.builder()
					.successo("fault")
					.build();

	    }

	    var user = userRepository.findByEmail(request.getEmail())
	            .orElseThrow();
	    user.setPassword(passwordEncoder.encode(request.getPassword()));
	   
		return LostPasswordResponse.builder()
				.successo("success")
				.build();
	    
	}
	
	

}
