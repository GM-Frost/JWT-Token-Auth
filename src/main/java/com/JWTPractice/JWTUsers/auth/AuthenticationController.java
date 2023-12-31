package com.JWTPractice.JWTUsers.auth;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "*", allowedHeaders = { "*" })
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
	
	private final AuthenticationService service;

	
	@PostMapping("/register")
	public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request){
		return ResponseEntity.ok(service.register(request));
	}
	

	@PostMapping("/authenticate")
	public ResponseEntity<?> register(@RequestBody AuthenticationRequest request){
		try {
			return ResponseEntity.ok(service.authenticate(request));
		}catch(Exception e) {

			 return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Credentials");
			 
			
		}
		
	}
}
