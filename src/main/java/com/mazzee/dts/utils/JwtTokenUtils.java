package com.mazzee.dts.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;

import com.mazzee.dts.dto.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

public class JwtTokenUtils {
	private final static Logger LOGGER = LoggerFactory.getLogger(JwtTokenUtils.class);
	@Value("${dts.jwt.secret.key}")
	private String secretKey;
	@Value("${dts.jwt.secret.issuer}")
	private String tokenIssuer;

	public JwtTokenUtils() {
		super();
	}

//	generate token
	public String generateToken(User user) {
		LOGGER.info("Generating JWT token for {}", user.getUserName());
		String jwtToken = null;
		Map<String, Object> claims = new HashMap<>();
		jwtToken = doGenerateToken(claims, user.getUserName());
		LOGGER.info("JWT token generated for user {} token {}", user.getUserName(), jwtToken);
		return jwtToken;
	}

	private String doGenerateToken(Map<String, Object> claims, String subject) {
		String jwtToken = Jwts.builder().addClaims(claims).setSubject(subject).setIssuedAt(new Date())
				.setIssuer(tokenIssuer).signWith(SignatureAlgorithm.HS256, secretKey).compact();
		return jwtToken;
	}

//	valdate token
	public boolean isValidToken(String token, UserDetails userDetails) {
		LOGGER.info("Validating JWT token for user {} and token {}", userDetails.getUsername(), token);
		boolean isTokenValid = false;
		final BiPredicate<String, String> biPredicate = (s1, s2) -> s1.equalsIgnoreCase(s2);
		String userName = getUserNameFromToken(token);
		if (!DtsUtils.isNullOrEmpty(userName)) {
			isTokenValid = biPredicate.test(userName, userDetails.getUsername());
		}
		LOGGER.info("user {} is token valid {}", userDetails.getUsername(), isTokenValid);
		return isTokenValid;
	}

//	get claims from token
	public String getUserNameFromToken(String token) {
		String userName = getClaimFromToken(token, Claims::getSubject);
		return userName;
	}

	public String getIssuerFromToken(String token) {
		String issuerName = getClaimFromToken(token, Claims::getIssuer);
		return issuerName;
	}

	private <T> T getClaimFromToken(String token, Function<Claims, T> claimResolver) {
		Claims claims = getAllClaimsFromToken(token);
		return claimResolver.apply(claims);
	}

	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
	}

}
