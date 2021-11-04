/**
 * 
 */
package com.mazzee.dts.controller;

import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mazzee.dts.dto.ApiError;
import com.mazzee.dts.dto.User;
import com.mazzee.dts.exception.UserException;
import com.mazzee.dts.jwt.JwtTokenUtils;
import com.mazzee.dts.service.UserService;
import com.mazzee.dts.utils.DtsUtils;

/**
 * Class define all API related to users
 * 
 * @author mazhar
 * @version 1.0.0
 * @since 1.0.0
 *
 */
@RestController
@RequestMapping("api/v1/user")
public class UserController {
	private final static Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	private UserService userService;
	private JwtTokenUtils JwtTokenUtils;

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Autowired
	public void setJwtTokenUtils(JwtTokenUtils jwtTokenUtils) {
		JwtTokenUtils = jwtTokenUtils;
	}

	@PostMapping(value = "/login", consumes = { MediaType.APPLICATION_FORM_URLENCODED_VALUE })
	public ResponseEntity<User> login(User user) throws UserException {
		String jwtToken = null;
		LOGGER.info("Login initiated");
		ResponseEntity<User> responseEntity = null;
		Supplier<UserException> userExceptionSupplier = () -> {
			UserException exception = new UserException(
					new ApiError(HttpStatus.FORBIDDEN.value(), "User name / password is incorrect"));
			exception.setUser(user);
			return exception;
		};
		User loggedInUser = userService.login(user).orElseThrow(userExceptionSupplier);
		jwtToken = JwtTokenUtils.generateToken(loggedInUser);
		if (!DtsUtils.isNullOrEmpty(jwtToken)) {
			loggedInUser.setAuthenticationToken(jwtToken);
		}
		LOGGER.info("User found - {}", loggedInUser.getUserName());
		responseEntity = ResponseEntity.ok().body(loggedInUser);
		return responseEntity;
	}

}
