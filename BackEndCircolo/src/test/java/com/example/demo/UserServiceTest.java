package com.example.demo;

import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

import com.circolo.security.torneo.Torneo;
import com.circolo.security.user.Role;
import com.circolo.security.user.User;
import com.circolo.security.user.UserRepository;
import com.circolo.security.user.UserRequestById;
import com.circolo.security.user.UserService;

@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)

public class UserServiceTest {
	
	@Mock
	private UserRepository userRepository;
	
	@InjectMocks
	private UserService underTest;
	
	@BeforeEach
	void setUp() {
		
		underTest = new UserService(userRepository);
	}
	
	@Test
	void getUser() {
		
		//when
		underTest.getUser();	
		//then
		verify(userRepository).findAll();
	}
	
	
}
