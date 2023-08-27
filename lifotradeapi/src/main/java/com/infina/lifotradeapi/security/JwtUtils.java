package com.infina.lifotradeapi.security;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.github.cdimascio.dotenv.Dotenv;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {
	
	Dotenv dotenv = Dotenv.load();
	private final String jwtSigningKey = dotenv.get("TOKEN_KEY");
	
	// Const a eklenicek
	private final static long expiresIn = 12;

	Key key = Keys.hmacShaKeyFor(jwtSigningKey.getBytes());

		
	public String extractUserName(String token) {
		return extractClaim(token, Claims :: getSubject);
	}

	public Date extractExpiration(String token) {
		return extractClaim(token, Claims :: getExpiration);
	}
	
	public boolean hasClaim(String token, String claimName) {
		final Claims claims = extractAllClaims(token);
		return claims.get(claimName) != null;
	}
	
	public <T> T extractClaim(String token,Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}
	
	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
	}
	
	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}
	
	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		return createToken(claims, userDetails);	
	}
	
	public String generateToken(UserDetails userDetails, Map<String, Object> claims) {
		return createToken(claims, userDetails);	
	}
	
	private String createToken(Map<String, Object> claims, UserDetails userDetails ) {
		return Jwts.builder().setClaims(claims).setSubject(userDetails.getUsername())
				.claim("authorities", userDetails.getAuthorities())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(expiresIn)))
				.signWith(key).compact();
			
	}
	
	public Boolean isTokenValid(String token, UserDetails userDetails) {
		final String username = extractUserName(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
	
	
}
