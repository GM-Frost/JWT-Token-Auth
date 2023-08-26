package com.JWTPractice.JWTUsers.repo;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.JWTPractice.JWTUsers.model.JWTUsers;

public interface UsersRepo extends MongoRepository<JWTUsers, String>{

	//Find User by Email
	Optional<JWTUsers> findByEmail(String email);

}
