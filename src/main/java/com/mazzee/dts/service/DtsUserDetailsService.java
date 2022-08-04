package com.mazzee.dts.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.mazzee.dts.entity.User;

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
		String password = null;
		boolean enabled = true;
		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = true;
		Collection<? extends GrantedAuthority> authorities = new ArrayList<>();
		Optional<User> optionalUser = userService.getUserByUserName(userName);
		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			password = user.getPassword();
			return new org.springframework.security.core.userdetails.User(userName,
					new BCryptPasswordEncoder().encode(password), enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		}
		return null;
	}
}
