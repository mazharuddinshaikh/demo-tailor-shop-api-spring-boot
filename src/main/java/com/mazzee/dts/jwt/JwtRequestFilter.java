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
	private static final Logger LOGGER = LoggerFactory.getLogger(JwtRequestFilter.class);
	private static final String BEARER = "Bearer ";
	private static final String AUTHORIZATION_HEADER = "Authorization";

	private JwtTokenUtils jwtTokenUtils;
	private UserDetailsService userDetailsService;

	@Autowired
	public void setJwtTokenUtils(JwtTokenUtils jwtTokenUtils) {
		this.jwtTokenUtils = jwtTokenUtils;
	}

	@Autowired
	public void setUserDetailsService(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		final String authorizationHeader = request.getHeader(AUTHORIZATION_HEADER);
		final BiPredicate<String, String> biPredicate = String::equalsIgnoreCase;
		String userNameOfToken = null;
		String jwtToken = null;
		boolean isValidToken = false;

		if (!DtsUtils.isNullOrEmpty(authorizationHeader) && authorizationHeader.startsWith(BEARER)) {
			jwtToken = authorizationHeader.substring(7);
			userNameOfToken = jwtTokenUtils.getUserNameFromToken(jwtToken);
		}

		if (!DtsUtils.isNullOrEmpty(userNameOfToken)) {
			UserDetails userDetails = userDetailsService.loadUserByUsername(userNameOfToken);
			if (Objects.nonNull(userDetails)) {
				isValidToken = biPredicate.test(userNameOfToken, userDetails.getUsername());
			}

			if (isValidToken) {
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			} else {
				LOGGER.info("Is valid token {} for user {}", isValidToken, userNameOfToken);
			}
		}
		filterChain.doFilter(request, response);
	}

}
