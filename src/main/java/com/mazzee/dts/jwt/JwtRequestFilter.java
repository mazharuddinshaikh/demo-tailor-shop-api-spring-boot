package com.mazzee.dts.jwt;

import java.io.IOException;
import java.util.Objects;
import java.util.function.BiPredicate;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.mazzee.dts.utils.DtsUtils;

/**
 * @author Admin
 * @version 1.0.0
 * @since 1.0.0
 *
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter {
	private final static Logger LOGGER = LoggerFactory.getLogger(JwtRequestFilter.class);
	private static final String BEARER = "Bearer ";
	private static final String AUTHORIZATION_HEADER = "Authorization";

	private JwtTokenUtils jwtTokenUtils;
	private UserDetailsService UserDetailsService;

	@Autowired
	public void setJwtTokenUtils(JwtTokenUtils jwtTokenUtils) {
		this.jwtTokenUtils = jwtTokenUtils;
	}

	@Autowired
	public void setUserDetailsService(UserDetailsService userDetailsService) {
		UserDetailsService = userDetailsService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		final String authorizationHeader = request.getHeader(AUTHORIZATION_HEADER);
		final BiPredicate<String, String> biPredicate = (s1, s2) -> s1.equalsIgnoreCase(s2);
		String userNameOfToken = null;
		String jwtToken = null;
		boolean isValidToken = false;

		if (!DtsUtils.isNullOrEmpty(authorizationHeader) && authorizationHeader.startsWith(BEARER)) {
			jwtToken = authorizationHeader.substring(7);
			userNameOfToken = jwtTokenUtils.getUserNameFromToken(jwtToken);
		}

		if (!DtsUtils.isNullOrEmpty(userNameOfToken)) {
			LOGGER.info("Validating JWT token for user {} and token {}", userNameOfToken, jwtToken);
			UserDetails userDetails = UserDetailsService.loadUserByUsername(userNameOfToken);
			if (Objects.nonNull(userDetails)) {
				isValidToken = biPredicate.test(userNameOfToken, userDetails.getUsername());
			}
			LOGGER.info("Is valid token {} for user {}", isValidToken, userNameOfToken);
			if (isValidToken) {
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}
		filterChain.doFilter(request, response);
	}

}
