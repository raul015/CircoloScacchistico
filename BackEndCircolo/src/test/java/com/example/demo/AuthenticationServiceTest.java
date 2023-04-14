package com.example.demo;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.circolo.security.auth.AuthenticationRequest;
import com.circolo.security.auth.AuthenticationService;
import com.circolo.security.auth.RegisterRequest;
import com.circolo.security.config.JwtService;
import com.circolo.security.user.User;
import com.circolo.security.user.UserRepository;

@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class AuthenticationServiceTest {

	

	
	@Mock
	private UserRepository userRepository;
	@Mock
	private PasswordEncoder passwordEncoder;
	@Mock
	private JwtService jwtService;
	@Mock
	private AuthenticationManager authenticationManager;

	  
	@InjectMocks
	private AuthenticationService underTest;
	
	@BeforeEach
	void setUp() {
		
		underTest = new AuthenticationService(
				userRepository,
				passwordEncoder,
				jwtService,
				 authenticationManager
				);
	
	}
	

	// Questo test non fa nulla
	
	
	/*
	 * 
	 *  public RegisterResponse register(RegisterRequest request) {
		public AuthenticationResponse authenticate(AuthenticationRequest request) {
  		public PasswordResponse password(PasswordRequest request) {

	 */
	@Test
	void register(){
		

	    RegisterRequest richiesta = new RegisterRequest(
	    		"raul",
	    		"luizaga",
	    		"raul01597@gmail.com",
	    		"1997015");
		
		underTest.register(richiesta);
		
        ArgumentCaptor<User> userArgumentCaptor =
        		ArgumentCaptor.forClass(User.class);
        
        verify(userRepository)
    	.save(userArgumentCaptor.capture());
        
        verify(userRepository).findByEmail("raul01597@gmail.com");

	}
	
	@Test
	void registerEmailAlreadyExist(){
		

	    RegisterRequest richiesta = new RegisterRequest(
	    		"raul",
	    		"luizaga",
	    		"raul01597@gmail.com",
	    		"1997015qwiqoiwq");
	    
		
		underTest.register(richiesta);
		
        ArgumentCaptor<User> userArgumentCaptor =
        		ArgumentCaptor.forClass(User.class);
        
        verify(userRepository)
    	.save(userArgumentCaptor.capture());
        
        verify(userRepository).findByEmail("raul01597A@gmail.com");

	}
			
}
