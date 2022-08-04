package com.mazzee.dts.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.mazzee.dts.dto.UserDto;
import com.mazzee.dts.utils.DtsUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * @author Admin
 * @version 1.0.0
 * @since 1.0.0
 *
 */
public final class JwtTokenUtils {
	private final static Logger LOGGER = LoggerFactory.getLogger(JwtTokenUtils.class);
	@Value("${dts.jwt.secret.key}")
	private String secretKey;
	@Value("${dts.jwt.secret.issuer}")
	private String tokenIssuer;

	public JwtTokenUtils() {
		super();
	}

	/**
	 * @param user
	 * @return jwt token for given user object
	 */
	public String generateToken(UserDto user) {
		LOGGER.info("Generating JWT token for {}", user.getUserName());
		String jwtToken = null;
		Map<String, Object> claims = new HashMap<>();
		jwtToken = doGenerateToken(claims, user.getUserName());
		if (!DtsUtils.isNullOrEmpty(jwtToken)) {
			jwtToken = "Bearer " + jwtToken;
		}
		return jwtToken;
	}

	/**
	 * @param claims
	 * @param subject
	 * @return jwt token by adding claims and subject
	 */
	private String doGenerateToken(Map<String, Object> claims, String subject) {
		String jwtToken = null;
		try {
			jwtToken = Jwts.builder().addClaims(claims).setSubject(subject).setIssuedAt(new Date())
					.setIssuer(tokenIssuer).signWith(SignatureAlgorithm.HS256, secretKey).compact();
		} catch (final MalformedJwtException e) {
			LOGGER.error("token constructed incorrectly {} ", e);
		}

		return jwtToken;
	}

	/**
	 * @param token
	 * @return user name from token
	 */
	public String getUserNameFromToken(String token) {
		String userName = null;
		userName = getClaimFromToken(token, Claims::getSubject);
		return userName;
	}

	/**
	 * @param token
	 * @return issuer name from token
	 */
	public String getIssuerFromToken(String token) {
		String issuerName = null;
		issuerName = getClaimFromToken(token, Claims::getIssuer);
		return issuerName;
	}

	/**
	 * @param <T>
	 * @param token
	 * @param claimResolver
	 * @return single claim from token
	 */
	private <T> T getClaimFromToken(String token, Function<Claims, T> claimResolver) {
		T t = null;
		Claims claims = getAllClaimsFromToken(token);
		if (Objects.nonNull(claims)) {
			try {
				t = claimResolver.apply(claims);
			} catch (final JwtException e) {
				LOGGER.error("Exception occured while retrieving claim from token {}", e);
			}
		}
		return t;
	}

	/**
	 * @param token
	 * @return all claims from token
	 */
	private Claims getAllClaimsFromToken(String token) {
		Claims claims = null;
		try {
			claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
		} catch (final JwtException e) {
			LOGGER.info("Invalid jwt token {} exception {} ", token, e);
		}
		return claims;
	}

}
