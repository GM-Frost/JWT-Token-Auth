package com.JWTPractice.JWTUsers.config;



import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	private static final String SECRET_KEY = "nkLxHu9h1qkBusO+zia4hdur5ILZbroqF1kV65tasUa4X0Nxnb7n9Wbwp7hgMjRR\r\n"
			+ "";
	
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}
	
	//EXTRACT TOKEN ONLY FROM USER DETAILS
	public String generateToken(UserDetails userDetails) {
		return generateToken(new HashMap<>(),userDetails);
	}
	
	//EXTRACT Single Claim to pass
	public <T> T extractClaim(String token, Function<Claims,T>claimsResolver){
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}
	
	//GENERATING TOKEN
	public String generateToken(Map<String, Object>extractClaims, UserDetails userDetails) {
		return Jwts
				.builder()
				.setClaims(extractClaims)
				.setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+1000*60*24))//token validity 24hrs + 1000millisecond
				.signWith(getSignInKey(), SignatureAlgorithm.HS256)
				.compact(); 
	}
	
	//Method to validate Token
	public boolean isTokenValid(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
	}
	
	//Method for isTokenExpired
	public boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}
	
	//Method for extractExpiration
	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}
	
	//Creating Claims to be returned with Token
	private Claims extractAllClaims(String token) {
		return Jwts
				.parserBuilder()
				.setSigningKey(getSignInKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}

	private Key getSignInKey() {
		
		byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(keyBytes);
	}


}
