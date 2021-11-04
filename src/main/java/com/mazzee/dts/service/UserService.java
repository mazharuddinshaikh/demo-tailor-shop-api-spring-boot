/**
 * 
 */
package com.mazzee.dts.service;

import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mazzee.dts.controller.UserController;
import com.mazzee.dts.dto.User;
import com.mazzee.dts.repo.UserRepo;
import com.mazzee.dts.utils.DtsUtils;

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

	@Autowired
	public void setUserRepo(UserRepo userRepo) {
		this.userRepo = userRepo;
	}

	public Optional<User> login(final User user) {
		Optional<User> loggedInUser = Optional.empty();
		if (Objects.nonNull(user)) {
			String password = user.getPassword();
			String userName = user.getUserName();
			String email = user.getEmail();
			String mobileNo = user.getMobileNo();
			if (!DtsUtils.isNullOrEmpty(password)) {
				if (!DtsUtils.isNullOrEmpty(userName)) {
					LOGGER.info("Login with userName - {}", user.getUserName());
					loggedInUser = userRepo.getUserByUserNameAndPassword(userName, password);
				}
				if (!DtsUtils.isNullOrEmpty(email) && !loggedInUser.isPresent()) {
					LOGGER.info("Login with email - {}", user.getEmail());
					loggedInUser = userRepo.getUserByEmailAndPassword(email, password);
				}
				if (!DtsUtils.isNullOrEmpty(mobileNo) && !loggedInUser.isPresent()) {
					LOGGER.info("Login with mobileNo - {}", user.getMobileNo());
					loggedInUser = userRepo.getUserByMobileNoAndPassword(mobileNo, password);
				}
			} else {
				LOGGER.info("Password missing");
			}
		}
		return loggedInUser;
	}

	public Optional<User> getUserByUserName(final String userName) {
		Optional<User> user = Optional.empty();
		if (!DtsUtils.isNullOrEmpty(userName)) {
			user = userRepo.getUserByUserNameOrEmailOrMobileNo(userName);
		}
		return user;
	}

}
