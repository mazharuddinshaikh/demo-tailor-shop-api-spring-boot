/**
 * 
 */
package com.mazzee.dts.service;

import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import com.mazzee.dts.controller.UserController;
import com.mazzee.dts.dto.UserDto;
import com.mazzee.dts.entity.User;
import com.mazzee.dts.exception.DtsException;
import com.mazzee.dts.mapper.UserDtoMapper;
import com.mazzee.dts.repo.UserRepo;
import com.mazzee.dts.utils.DtsUtils;

//mazhar.shaikh1997@gmail.com    mazabd@123
/**
 * @author mazhar
 * @version 1.0.0
 * @since 1.0.0
 *
 */
@Service
public class UserService {
	private final static Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	private UserRepo userRepo;
	private UserDtoMapper userDtoMapper;
	private MailSender mailSender;

	@Autowired
	public void setUserRepo(UserRepo userRepo) {
		this.userRepo = userRepo;
	}

	@Autowired
	public void setUserDtoMapper(UserDtoMapper userDtoMapper) {
		this.userDtoMapper = userDtoMapper;
	}

	@Autowired
	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}

	public Optional<UserDto> signin(final UserDto userDto) {
		Optional<UserDto> loggedInUserDto = Optional.empty();
		Optional<User> loggedInUser = Optional.empty();
		if (Objects.nonNull(userDto)) {
			String password = userDto.getPassword();
			String userName = userDto.getUserName();
			String email = userDto.getEmail();
			String mobileNo = userDto.getMobileNo();
			if (!DtsUtils.isNullOrEmpty(password)) {
				if (!DtsUtils.isNullOrEmpty(userName)) {
					LOGGER.info("Login with userName - {}", userDto.getUserName());
					loggedInUser = userRepo.getUserByUserNameAndPassword(userName, password);
				}
				if (!DtsUtils.isNullOrEmpty(email) && !loggedInUser.isPresent()) {
					LOGGER.info("Login with email - {}", userDto.getEmail());
					loggedInUser = userRepo.getUserByEmailAndPassword(email, password);
				}
				if (!DtsUtils.isNullOrEmpty(mobileNo) && !loggedInUser.isPresent()) {
					LOGGER.info("Login with mobileNo - {}", userDto.getMobileNo());
					loggedInUser = userRepo.getUserByMobileNoAndPassword(mobileNo, password);
				}
			} else {
				LOGGER.info("Password missing");
			}
		}
		if (loggedInUser.isPresent()) {
			loggedInUserDto = Optional.ofNullable(getDtoFromUser(loggedInUser.get()));
		}
		return loggedInUserDto;
	}

	public Optional<UserDto> signin(final String userName, final String password) {
		Optional<UserDto> loggedInUserDto = Optional.empty();
		Optional<User> loggedInUser = Optional.empty();
		if (!DtsUtils.isNullOrEmpty(password)) {
			if (!DtsUtils.isNullOrEmpty(userName)) {
				LOGGER.info("Login with {}", userName);
				loggedInUser = userRepo.getUserByUserNameOrEmailORMobileNoAndPassword(userName, password);
			}
		} else {
			LOGGER.info("Password missing");
		}
		if (loggedInUser.isPresent()) {
			loggedInUserDto = Optional.ofNullable(getDtoFromUser(loggedInUser.get()));
		}
		return loggedInUserDto;
	}

	public Optional<User> getUserByUserName(final String userName) {
		Optional<User> user = Optional.empty();
		if (!DtsUtils.isNullOrEmpty(userName)) {
			user = userRepo.getUserByUserName(userName);
		}
		return user;
	}

	public Optional<User> getUserByMobileNo(final String mobileNo) {
		Optional<User> user = Optional.empty();
		if (!DtsUtils.isNullOrEmpty(mobileNo)) {
			user = userRepo.getUserByMobileNo(mobileNo);
		}
		return user;
	}

	public Optional<User> getUserByUserId(final int userId) {
		LOGGER.info("Get user bu user id");
		Optional<User> user = Optional.empty();
		user = userRepo.getUserByUserId(userId);
		return user;
	}

	public Optional<User> getUserByEmail(final String email) {
		Optional<User> user = Optional.empty();
		if (!DtsUtils.isNullOrEmpty(email)) {
			user = userRepo.getUserByEmail(email);
		}
		return user;
	}

	public UserDto addUser(UserDto userDto) throws DtsException {
		if (Objects.nonNull(userDto)) {
			User user = getUserFromDto(userDto);
			LOGGER.info("Adding new user");
			user = userRepo.save(user);
			if (Objects.nonNull(user)) {
				userDto = getDtoFromUser(user);
			}
		}
		return userDto;
	}

	public String getValidatedDto(UserDto userDto) {
//		String validtionMessage = null;
//		validating firstname middlename or lastname must present
		if (DtsUtils.isNullOrEmpty(userDto.getFirstName()) && DtsUtils.isNullOrEmpty(userDto.getMiddleName())
				&& DtsUtils.isNullOrEmpty(userDto.getLastName())) {
			LOGGER.info("User's frst middle or last name is missing");
			return "Please enter valid name of user";

		}
//		validating mobileNo
		if (DtsUtils.isNullOrEmpty(userDto.getMobileNo())) {
			LOGGER.info("Mobile no not present");
			return "Please enter valid mobile numebr";

		} else {
			Optional<User> mobileNo = getUserByMobileNo(userDto.getMobileNo());
			if (mobileNo.isPresent()) {
				LOGGER.info("Mobile number is already present");
				return "Mobile number is already in use! Please use another mobile number";
			}
		}

//		validating email
		if (!DtsUtils.isNullOrEmpty(userDto.getEmail())) {
			if (!DtsUtils.isValidEmail(userDto.getEmail())) {
				LOGGER.info("Invalid email address");
				return "Please enter valid email address";
			}
//	validate if email address is already in use
			Optional<User> emailAddress = getUserByEmail(userDto.getEmail());
			if (emailAddress.isPresent()) {
				LOGGER.info("Email address already present");
				return "Email id is already in use! Please use another email id";
			}
		} else {
			return "Please enter email address";
		}
// validating these fields
		if (DtsUtils.isNullOrEmpty(userDto.getUserName())
				|| userDto.getUserName().length() < DtsUtils.MINIMUM_PASSWORD_LENGTH) {
			LOGGER.info("Invalid user name");
			return "User name must be 6 or more character long! Please enter valid user name";
		} else {
//	validate user name if it is not already used
			Optional<User> userName = userRepo.getUserByUserName(userDto.getUserName());
			if (userName.isPresent()) {
				return "User name is already in use! Please use another user name";
			}
		}

		if (DtsUtils.isNullOrEmpty(userDto.getPassword())
				|| userDto.getPassword().length() < DtsUtils.MINIMUM_PASSWORD_LENGTH) {
			LOGGER.info("Invalid password");
			return "Password must be " + DtsUtils.MINIMUM_PASSWORD_LENGTH
					+ " character long! Please enter valid password";
		}
		if (DtsUtils.isNullOrEmpty(userDto.getShopName())) {
			LOGGER.info("Invalid shop name");
			return "Please enter valid shop name";
		}
		return null;
	}

	public String updatePassword(String userName, String oldPassword, String newPassword) throws DtsException {
		LOGGER.info("Update password");
		String message = null;
		User user = null;
		Optional<User> userOptional = userRepo.getUserByUserName(userName);
		if (userOptional.isPresent()) {
			user = userOptional.get();
			user.setPassword(newPassword);
			user = userRepo.save(user);
		}
		if (Objects.nonNull(user)) {
			message = "Password updated successfully";
		}
		return message;
	}

	public String validateChangePassword(String userName, String oldPassword, String newPassword) {
		if (DtsUtils.isNullOrEmpty(oldPassword)) {
			return "Old password doesnt match! please enter correct password";
		}
		if (!isValidPassword(newPassword)) {
			return "Password must be " + DtsUtils.MINIMUM_PASSWORD_LENGTH
					+ " character long! Please enter valid password";
		}
		Optional<User> userOptional = userRepo.getUserByUserName(userName);

		if (!userOptional.isPresent()) {
			return "Invalid user";
		}
		if (!userOptional.get().getPassword().equals(oldPassword)) {
			return "Old password doesnt match! please enter correct password";
		}
		return null;
	}

	private boolean isValidPassword(String password) {
		return !DtsUtils.isNullOrEmpty(password) && password.length() >= DtsUtils.MINIMUM_PASSWORD_LENGTH;
	}

	public UserDto updateUser(UserDto userDto) {
		UserDto updatedUser = null;
		String userName = userDto.getUserName();
		Optional<User> userOptional = userRepo.getUserByUserName(userName);
		if (userOptional.isPresent()) {
			User user = userOptional.get();
			if (!DtsUtils.isNullOrEmpty(userDto.getFirstName())) {
				user.setFirstName(userDto.getFirstName());
			}
			if (!DtsUtils.isNullOrEmpty(userDto.getMiddleName())) {
				user.setMiddleName(userDto.getMiddleName());
			}
			if (!DtsUtils.isNullOrEmpty(userDto.getLastName())) {
				user.setLastName(userDto.getLastName());
			}

			if (!DtsUtils.isNullOrEmpty(userDto.getMobileNo())) {
				user.setMobileNo(userDto.getMobileNo());
			}
			if (!DtsUtils.isNullOrEmpty(userDto.getEmail())) {
				user.setEmail(userDto.getEmail());
			}
			user = userRepo.save(user);
			if (Objects.nonNull(user)) {
				updatedUser = userDtoMapper.getDtoFromUser(user);
			}
		}
		return updatedUser;
	}

	public UserDto updateShop(UserDto userDto) {
		UserDto updatedUser = null;
		String userName = userDto.getUserName();
		Optional<User> userOptional = userRepo.getUserByUserName(userName);
		if (userOptional.isPresent()) {
			User user = userOptional.get();
			if (!DtsUtils.isNullOrEmpty(userDto.getShopName())) {
				user.setShopName(userDto.getShopName());
			}
			if (!DtsUtils.isNullOrEmpty(userDto.getAlternateMobileNo())) {
				user.setAlternateMobileNo(userDto.getAlternateMobileNo());
			}
			if (!DtsUtils.isNullOrEmpty(userDto.getAddress())) {
				user.setAddress(userDto.getAddress());
			}
			user = userRepo.save(user);
			updatedUser = userDtoMapper.getDtoFromUser(user);
		}
		return updatedUser;
	}

	public boolean isExistingEmail(String userName, String email) {
		boolean isExistingEmail = false;
		Optional<User> user = getUserByEmail(email);
		if (user.isPresent() && !user.get().getUserName().equals(userName)) {
			isExistingEmail = true;
		}
		return isExistingEmail;
	}

	public boolean isExistingMobile(String userName, String mobile) {
		boolean isExistingMobile = false;
		Optional<User> user = getUserByMobileNo(mobile);
		if (user.isPresent() && !user.get().getUserName().equals(userName)) {
			isExistingMobile = true;
		}
		return isExistingMobile;
	}

	public boolean sendEmailToUser(String receiverMailId, String emailMessage) {
		boolean isEmailSentSuccessfully = false;
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(receiverMailId);
		mailMessage.setText(emailMessage);
		mailMessage.setSubject("DigiTailors - Default Password");
		LOGGER.info("Sending default password to {} email", receiverMailId);
		mailSender.send(mailMessage);
		LOGGER.info("Default password sent to {} email", receiverMailId);
		isEmailSentSuccessfully = true;

		return isEmailSentSuccessfully;
	}

	public UserDto getDtoFromUser(User user) {
		UserDto userDto = null;
		if (Objects.nonNull(user)) {
			userDto = userDtoMapper.getDtoFromUser(user);
		}
		return userDto;
	}

	public User getUserFromDto(UserDto userDto) {
		User user = null;
		if (Objects.nonNull(userDto)) {
			user = userDtoMapper.getUserFromDto(userDto);
		}
		return user;
	}

	public boolean updatePassword(String userName, String password) {
		boolean isPasswordUpdated = false;
		Optional<User> user = userRepo.getUserByUserName(userName);
		if (user.isPresent() && isValidPassword(password)) {
			User updateUser = user.get();
			updateUser.setPassword(password);
			updateUser = userRepo.save(updateUser);
			isPasswordUpdated = Objects.nonNull(updateUser);
		}
		return isPasswordUpdated;

	}

	public String getDefaultPassword() {
		return "989898";
	}
}
