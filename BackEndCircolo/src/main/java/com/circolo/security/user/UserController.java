package com.circolo.security.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping(path="/api/v1/User")

public class UserController {

	private final UserService userService;
	
	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping("/Get")
	public ResponseEntity<List<User>> getUser(){
		
		return ResponseEntity.ok(userService.getUser());
		
	}
	
	@PostMapping("/GetUser")
	public ResponseEntity<User> getSingleUser(@RequestBody UserRequestById richiesta){
		return ResponseEntity.ok(userService.getSingoloUser(richiesta));
				
	}
	
	@Transactional
	@DeleteMapping("/Delete")
	public ResponseEntity<UserResponse> EliminaUser(@RequestBody UserRequestById richiesta){
		
		return ResponseEntity.ok(userService.eliminaUser(richiesta));
		
	}

}
