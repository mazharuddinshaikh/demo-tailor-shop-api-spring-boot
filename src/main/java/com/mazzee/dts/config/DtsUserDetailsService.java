package com.mazzee.dts.config;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.mazzee.dts.dto.User;
import com.mazzee.dts.repo.UserRepo;
import com.mazzee.dts.service.UserService;

/*
 * Class used by spring security 
 * to authenticate user
 */
@Service
public class DtsUserDetailsService implements UserDetailsService {

	private UserService userService;

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		Optional<User> optionalUser = userService.getUserByUserName(userName);
		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			return new org.springframework.security.core.userdetails.User(userName, new BCryptPasswordEncoder().encode(user.getPassword()), true, true, true, true,
					new ArrayList<>());
		}
		return null;
	}
}
