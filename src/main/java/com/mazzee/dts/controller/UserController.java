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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mazzee.dts.dto.User;
import com.mazzee.dts.service.UserService;
import com.mazzee.dts.utils.ApiError;
import com.mazzee.dts.utils.UserException;

/**
 * @author mazhar
 *
 */
@RestController
@RequestMapping("user")
public class UserController {
	private final static Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	private UserService userService;

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@PostMapping(value = "/login", consumes = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<User> login(@RequestBody User user) throws UserException {
		LOGGER.info("Login initiated");
		ResponseEntity<User> responseEntity = null;
		Supplier<UserException> userExceptionSupplier = () -> {
			UserException exception = new UserException(
					new ApiError(HttpStatus.FORBIDDEN.value(), "User name / password is incorrect"));
			exception.setUser(user);
			return exception;
		};
		User loggedInUser = userService.login(user).orElseThrow(userExceptionSupplier);
		LOGGER.info("User found - {}", loggedInUser.getUserName());
		responseEntity = ResponseEntity.ok().body(loggedInUser);
		return responseEntity;
	}

}
