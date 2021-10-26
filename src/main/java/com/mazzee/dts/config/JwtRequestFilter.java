package com.mazzee.dts.config;

import java.io.IOException;

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
import com.mazzee.dts.utils.JwtTokenUtils;

import io.jsonwebtoken.SignatureException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
	private final static Logger LOGGER = LoggerFactory.getLogger(JwtRequestFilter.class);

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
		final String authorizationHeader = request.getHeader("Authorization");
		String userName = null;
		String jwtToken = null;
		boolean isValidToken = false;

		if (!DtsUtils.isNullOrEmpty(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
			jwtToken = authorizationHeader.substring(7);
			try {
				userName = jwtTokenUtils.getUserNameFromToken(jwtToken);
			} catch (SignatureException e) {
				LOGGER.error("Invalid jwt token {}", jwtToken);
				userName = null;
			}

		}
		if (!DtsUtils.isNullOrEmpty(userName)) {
			UserDetails userDeatils = UserDetailsService.loadUserByUsername(userName);
			isValidToken = jwtTokenUtils.isValidToken(jwtToken, userDeatils);
			if (isValidToken) {
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDeatils, null, userDeatils.getAuthorities());
				usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}
		filterChain.doFilter(request, response);
	}

}
