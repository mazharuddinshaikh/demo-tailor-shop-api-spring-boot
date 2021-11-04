package com.mazzee.dts.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.mazzee.dts.dto.User;

/*
 * Class used by spring security 
 * to authenticate user
 */
/**
 * @author Admin
 * @version 1.0.0
 * @since 1.0.0
 *
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
			return new org.springframework.security.core.userdetails.User(userName,
					new BCryptPasswordEncoder().encode(user.getPassword()), true, true, true, true, new ArrayList<>());
		}
		return null;
	}
}
