package com.JWTPractice.JWTUsers.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.JWTPractice.JWTUsers.repo.UsersRepo;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

	
	//Fetching User from the Database
	private final UsersRepo repository;
	
	
	//Create bean of type userDetailsService
	@Bean
	public UserDetailsService userDetailsService() {
		
			//Lambda Expression
			return username -> repository.findByEmail(username).orElseThrow(()->new UsernameNotFoundException("User not found"));
		
	};
	
	//Authentication Provider to fetch user details and encrypt password
	@Bean
	public AuthenticationProvider authenticationProvider() {
		
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		//password encoder
		authProvider.setPasswordEncoder(passwordEncoder());
		
		return authProvider;
	}
	
	//Authenticate user who only use Username & Password
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
		return config.getAuthenticationManager();
	}
	
	//Create Password Encoder Method
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
