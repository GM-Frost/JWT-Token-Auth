package com.JWTPractice.JWTUsers.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

	private String token;
   private String firstname;
    private String lastname;
    private String email;


}
