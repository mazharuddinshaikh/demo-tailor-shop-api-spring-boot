package com.mazzee.dts.jwt;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mazzee.dts.dto.ApiResponse;

/**
 * @author Admin
 * @version 1.0.0
 * @since 1.0.0
 *
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
	private final static Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationEntryPoint.class);

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws ServletException {
		LOGGER.error("Unauthorized user: {}", authException.getMessage());

		ObjectMapper mapper = new ObjectMapper();
		try (PrintWriter writer = response.getWriter()) {
//			ApiError error = new ApiError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized user");
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			ApiResponse<String> errorResponse = new ApiResponse<>();
//			errorResponse.setResult(error);
			errorResponse.setHttpStatus(HttpServletResponse.SC_UNAUTHORIZED);
			errorResponse.setMessage("You are not authorized to access this resource");
			writer.print(mapper.writeValueAsString(errorResponse));
		} catch (IOException e) {
			LOGGER.error("Exception occured while writing error response for unauthorized user");
			try {
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
						"You are not authorized to access this resource");
			} catch (IOException e1) {
				LOGGER.error("Exception occured while writing error response for unauthorized user");
			}
		}
	}

}
