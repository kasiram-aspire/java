package com.example.SpringbootExercise.services;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
    private String key="";
    private JwtService ()
    {
    	try {   //A secret key is generated dynamically using KeyGenerator for HMAC-SHA256.
			KeyGenerator keygen=KeyGenerator.getInstance("HmacSHA256");
			SecretKey sk=keygen.generateKey();
			key=Base64.getEncoder().encodeToString(sk.getEncoded());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
    }
	public String generateToken(String username) {
		//token generation
		Map<String,Object> claims= new HashMap<>();
		claims.put("role", "USER"); // Assign the role to JWT
		return Jwts.builder()
				   .claims()
				   .add(claims)
				   .subject(username)
				   .issuedAt(new Date(System.currentTimeMillis()))
				   .expiration(new Date(System.currentTimeMillis()+60*30*1000))//expired in 30 mins
				   .and()
				   .signWith(getkey())
				   .compact();
				
		//return "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6ImthaWYiLCJpYXQiOjE1MTYyMzkwMjJ9.xFau9prxmzcFQTe5ju66WgCiptxPOFWualNJmCT5UfU";
	}

	private  SecretKey getkey() {           //Retrieves the secret key by decoding the Base64-encoded key.
		byte[] keyBytes=Decoders.BASE64.decode(key);
		return Keys.hmacShaKeyFor(keyBytes);
	}
	  public String extractUserName(String token) { //Extracting Username from Token
	        // extract the username from jwt token
	        return extractClaim(token, Claims::getSubject);
	    }

	    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
           
	        final Claims claims = extractAllClaims(token);
	        return claimResolver.apply(claims);
	    }

	    private Claims extractAllClaims(String token) {
	        return Jwts.parser()
	                .verifyWith(getkey())
	                .build()
	                .parseSignedClaims(token)
	                .getPayload();
	    }

	    public boolean validateToken(String token, UserDetails userDetails) {
	    	////Token Validation(extract username from token chek with user.getusername())
	        final String userName = extractUserName(token);
	        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
	    }
	    
	   // extractExpiration() gets the expiration date from the token.
//    	isTokenExpired() compares the expiration date with the current time.
	    private boolean isTokenExpired(String token) {   
	        return extractExpiration(token).before(new Date());
	    }

	    private Date extractExpiration(String token) {
	        return extractClaim(token, Claims::getExpiration);
	    }
	    public String refreshToken(String token) {
	        Claims claims = extractAllClaims(token); // Extract existing claims
	        String username = claims.getSubject();

	        return Jwts.builder()
	                .claims(claims) // Keep old claims
	                .subject(username)
	                .issuedAt(new Date(System.currentTimeMillis()))
	                .expiration(new Date(System.currentTimeMillis() + 30 * 60 * 1000)) // Extend expiration
	                .signWith(getkey())
	                .compact();
	    }

}
