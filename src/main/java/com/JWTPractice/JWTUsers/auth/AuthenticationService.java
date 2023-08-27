package com.JWTPractice.JWTUsers.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.JWTPractice.JWTUsers.config.JwtService;
import com.JWTPractice.JWTUsers.model.JWTUsers;
import com.JWTPractice.JWTUsers.model.Role;
import com.JWTPractice.JWTUsers.repo.UsersRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

	private final UsersRepo repository;

	private final PasswordEncoder passwordEncoder;

	private final JwtService jwtService;

	// Authenticate user based on username and password

	private final AuthenticationManager authenticationManager;

	public AuthenticationResponse register(RegisterRequest request) {

		var user = JWTUsers.builder().firstname(request.getFirstname()).lastname(request.getLastname())
				.email(request.getEmail()).password(passwordEncoder.encode(request.getPassword())).role(Role.USER)
				.build();

		repository.save(user);

		var jwtToken = jwtService.generateToken(user);

		return AuthenticationResponse.builder()
				.token(jwtToken)
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .email(user.getEmail())
                .build();
	}

	public AuthenticationResponse authenticate(AuthenticationRequest request) {

	
			authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

			var user = repository.findByEmail(request.getEmail()).orElseThrow();
			var jwtToken = jwtService.generateToken(user);
		

			return AuthenticationResponse.builder().token(jwtToken)
	                .firstname(user.getFirstname())
	                .lastname(user.getLastname())
	                .email(user.getEmail())
	                .build();
	
		
	}

}
