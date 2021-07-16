/**
 * 
 */
package com.mazzee.dts.service;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mazzee.dts.dto.User;
import com.mazzee.dts.repo.UserRepo;

/**
 * @author mazhar
 *
 */
@Service
public class UserService {

	private UserRepo userRepo;

	@Autowired
	public void setUserRepo(UserRepo userRepo) {
		this.userRepo = userRepo;
	}

	public Optional<User> login(final User user) {
		Predicate<String> emptyOrNullPredicate = s -> s == null || s.length() == 0;
		Optional<User> loggedInUser = Optional.empty();
		if (Objects.nonNull(user)) {
			String password = user.getPassword();
			String userName = user.getUserName();
			String email = user.getEmail();
			String mobileNo = user.getMobileNo();
			if (emptyOrNullPredicate.negate().test(password)) {
				if (emptyOrNullPredicate.negate().test(userName)) {
					loggedInUser = userRepo.getUserByUserNameAndPassword(userName, password);
				}
				if (emptyOrNullPredicate.negate().test(email)) {
					loggedInUser = userRepo.getUserByEmailAndPassword(email, password);
				}
				if (emptyOrNullPredicate.negate().test(mobileNo)) {
					loggedInUser = userRepo.getUserByMobileNoAndPassword(mobileNo, password);
				}
			}
		}
		return loggedInUser;

	}

}
