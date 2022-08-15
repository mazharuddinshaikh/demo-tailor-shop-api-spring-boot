/**
 * 
 */
package com.mazzee.dts.controller;

import java.util.Objects;
import java.util.Optional;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mazzee.dts.dto.ApiError;
import com.mazzee.dts.dto.ApiResponse;
import com.mazzee.dts.dto.UserDto;
import com.mazzee.dts.entity.User;
import com.mazzee.dts.exception.DtsException;
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
@RequestMapping("api/user/")
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

	@PostMapping(value = "v1/signin", consumes = { MediaType.APPLICATION_FORM_URLENCODED_VALUE })
	public ResponseEntity<UserDto> signin(@RequestParam("userName") String userName,
			@RequestParam("password") String password) throws UserException {
		String jwtToken = null;
		LOGGER.info("Signin");
		ResponseEntity<UserDto> responseEntity = null;
		Supplier<UserException> userExceptionSupplier = () -> {
			UserException exception = new UserException(
					new ApiError(HttpStatus.FORBIDDEN.value(), "User name / password is incorrect"));
			exception.setUserDto(new UserDto(userName));
			return exception;
		};
		UserDto loggedInUser = userService.signin(userName, password).orElseThrow(userExceptionSupplier);
		LOGGER.info("User found userName - {}", loggedInUser.getUserName());
		jwtToken = JwtTokenUtils.generateToken(loggedInUser);
		if (!DtsUtils.isNullOrEmpty(jwtToken)) {
			loggedInUser.setAuthenticationToken(jwtToken);
		}
		responseEntity = ResponseEntity.ok().body(loggedInUser);
		return responseEntity;
	}

	@PostMapping(value = "v1/signup", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_FORM_URLENCODED_VALUE })
	public ResponseEntity<ApiResponse<UserDto>> signup(@RequestBody UserDto user) throws DtsException {
		LOGGER.info("Creating new user");
		String message = null;
		ResponseEntity<ApiResponse<UserDto>> responseEntity = null;
		String validationMessage = null;
		ApiError apiError = null;
		if (Objects.nonNull(user)) {
			LOGGER.info("Validating user details");
			validationMessage = userService.getValidatedDto(user);
		}
		if (!DtsUtils.isNullOrEmpty(validationMessage)) {
			LOGGER.info("Validating failed");
			apiError = new ApiError(HttpStatus.BAD_REQUEST.value(), validationMessage);
			throw new DtsException(apiError);
		}
		LOGGER.info("All validation passes. Adding user to database");
		user = userService.addUser(user);
		if (Objects.nonNull(user)) {
			message = "User created Successfully ";
			LOGGER.info(message + "{}", user.getUserName());
			ApiResponse<UserDto> userResponse = new ApiResponse<>();
			userResponse.setHttpStatus(HttpStatus.OK.value());
			userResponse.setMessage(message);
			userResponse.setResult(user);
			responseEntity = ResponseEntity.ok().body(userResponse);
		}
		return responseEntity;
	}

	@PostMapping(value = "v1/updatePassword", consumes = { MediaType.APPLICATION_FORM_URLENCODED_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ApiResponse<String>> updatePassword(@RequestParam("userName") String userName,
			@RequestParam("oldPassword") String oldPassword, @RequestParam("newPassword") String newPassword)
			throws DtsException {
		LOGGER.info("Update password");
		String message = null;
		ResponseEntity<ApiResponse<String>> responseEntity = null;
		String validationMessage = null;
		ApiError apiError = null;
		validationMessage = userService.validateChangePassword(userName, oldPassword, newPassword);
		if (!DtsUtils.isNullOrEmpty(validationMessage)) {
			apiError = new ApiError(HttpStatus.BAD_REQUEST.value(), validationMessage);
			throw new DtsException(apiError);
		}
		message = userService.updatePassword(userName, oldPassword, newPassword);
		if (!DtsUtils.isNullOrEmpty(message)) {
			ApiResponse<String> userResponse = new ApiResponse<>();
			userResponse.setHttpStatus(HttpStatus.OK.value());
			userResponse.setMessage(message);
			responseEntity = ResponseEntity.ok().body(userResponse);
		}
		return responseEntity;
	}

	@PostMapping(value = "v1/updateUser", consumes = { MediaType.APPLICATION_FORM_URLENCODED_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ApiResponse<UserDto>> updateUser(@RequestBody UserDto userDto) throws DtsException {
		LOGGER.info("Update User");
		String message = null;
		ResponseEntity<ApiResponse<UserDto>> responseEntity = null;
		ApiError apiError = null;
		boolean isExistingEmail = false;
		boolean isExistingMobile = false;
		String jwtToken = null;
		Optional<User> userOptional = userService.getUserByUserName(userDto.getUserName());
		if (!userOptional.isPresent()) {
			apiError = new ApiError(HttpStatus.BAD_REQUEST.value(), "Invalid user");
			throw new DtsException(apiError);
		}
		if (!DtsUtils.isNullOrEmpty(userDto.getEmail())) {
			isExistingEmail = userService.isExistingEmail(userDto.getUserName(), userDto.getEmail());
		}
		if (isExistingEmail) {
			LOGGER.info("Duplicate email");
			apiError = new ApiError(HttpStatus.BAD_REQUEST.value(),
					"Email id is already in use! Please use another email id");
			throw new DtsException(apiError);
		}
		if (!DtsUtils.isNullOrEmpty(userDto.getMobileNo())) {
			isExistingMobile = userService.isExistingMobile(userDto.getUserName(), userDto.getMobileNo());
		}
		if (isExistingMobile) {
			LOGGER.info("Duplicate mobile number");
			apiError = new ApiError(HttpStatus.BAD_REQUEST.value(),
					"Mobile number is already in use! Please use another mobile number");
			throw new DtsException(apiError);
		}
		UserDto updatedUser = userService.updateUser(userDto);
		if (Objects.isNull(updatedUser)) {
			LOGGER.info("Something went wrong! user not updated");
			apiError = new ApiError(HttpStatus.BAD_REQUEST.value(), "Something went wrong! User not updated");
			throw new DtsException(apiError);
		}
		jwtToken = JwtTokenUtils.generateToken(updatedUser);
		if (!DtsUtils.isNullOrEmpty(jwtToken)) {
			updatedUser.setAuthenticationToken(jwtToken);
		}
		message = "User updated successfully";
		LOGGER.info("User {} updated successfully", userDto.getUserName());
		ApiResponse<UserDto> userResponse = new ApiResponse<>();
		userResponse.setHttpStatus(HttpStatus.OK.value());
		userResponse.setMessage(message);
		userResponse.setResult(updatedUser);
		responseEntity = ResponseEntity.ok().body(userResponse);
		return responseEntity;
	}

	@PostMapping(value = "v1/updateShop", consumes = { MediaType.APPLICATION_FORM_URLENCODED_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ApiResponse<UserDto>> updateShop(@RequestBody UserDto userDto) throws DtsException {
		LOGGER.info("Update shop");
		String message = null;
		String jwtToken = null;
		ResponseEntity<ApiResponse<UserDto>> responseEntity = null;
		ApiError apiError = null;
		Optional<User> userOptional = userService.getUserByUserName(userDto.getUserName());
		if (!userOptional.isPresent()) {
			apiError = new ApiError(HttpStatus.BAD_REQUEST.value(), "Invalid user");
			throw new DtsException(apiError);
		}
		UserDto updatedUser = userService.updateShop(userDto);
		if (Objects.nonNull(userDto)) {
			jwtToken = JwtTokenUtils.generateToken(updatedUser);
			if (!DtsUtils.isNullOrEmpty(jwtToken)) {
				updatedUser.setAuthenticationToken(jwtToken);
			}
			message = "Shop details updated successfully";
			LOGGER.info("User {} shop updated successfully", userDto.getUserName());
			ApiResponse<UserDto> userResponse = new ApiResponse<>();
			userResponse.setHttpStatus(HttpStatus.OK.value());
			userResponse.setMessage(message);
			userResponse.setResult(updatedUser);
			responseEntity = ResponseEntity.ok().body(userResponse);
		}
		return responseEntity;
	}

	@PostMapping(value = "v1/forgotPassword", consumes = { MediaType.APPLICATION_FORM_URLENCODED_VALUE })
	public ResponseEntity<ApiResponse<String>> forgotPassword(
			@RequestParam(value = "userName", required = false) String userName,
			@RequestParam(value = "email", required = false) String email) throws DtsException {
		LOGGER.info("Forgot password sending default password to user");
		String message = null;
		ResponseEntity<ApiResponse<String>> responseEntity = null;
		ApiError apiError = null;
		boolean isEmailSent = false;
		Optional<User> user = Optional.empty();
		String defaultPassword = null;
		String emailMessage = null;
		boolean isPasswordUpdated = false;

		if (DtsUtils.isNullOrEmpty(userName) && DtsUtils.isNullOrEmpty(email)) {
			apiError = new ApiError(HttpStatus.BAD_REQUEST.value(),
					"Please provide your registered user name or email id");
			throw new DtsException(apiError);
		}

		if (!DtsUtils.isNullOrEmpty(userName)) {
			user = userService.getUserByUserName(userName);
		}
		if (!DtsUtils.isNullOrEmpty(email) && !user.isPresent()) {
			user = userService.getUserByEmail(email);
		}

		defaultPassword = userService.getDefaultPassword();
		emailMessage = "Hello user you new password is " + defaultPassword
				+ " Please change your default password after login";
		if (!user.isPresent()) {
			apiError = new ApiError(HttpStatus.BAD_REQUEST.value(),
					"User name / Email not found! Please provide correct User name / Email");
			throw new DtsException(apiError);
		}
		isEmailSent = userService.sendEmailToUser(user.get().getEmail(), emailMessage);
		userName = user.get().getUserName();
		if (isEmailSent) {
			isPasswordUpdated = userService.updatePassword(userName, defaultPassword);
		}

		if (isEmailSent && isPasswordUpdated) {
			message = "New password has been sent on your registered email id";
		} else {
			apiError = new ApiError(HttpStatus.BAD_REQUEST.value(),
					"Something went wrong while sending new password! Please retry");
			throw new DtsException(apiError);
		}

		if (!DtsUtils.isNullOrEmpty(message)) {
			ApiResponse<String> userResponse = new ApiResponse<>();
			userResponse.setHttpStatus(HttpStatus.OK.value());
			userResponse.setMessage(message);
			responseEntity = ResponseEntity.ok().body(userResponse);
		}
		return responseEntity;
	}

}
