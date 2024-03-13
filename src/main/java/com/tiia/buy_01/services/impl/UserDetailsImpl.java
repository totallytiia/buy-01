package com.tiia.buy_01.services.impl;

import com.tiia.buy_01.exceptions.InvalidUserException;
import com.tiia.buy_01.model.User;
import com.tiia.buy_01.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsImpl implements UserDetailsService {

	@Autowired
	private UserService userService;

	@Override
	public User loadUserByUsername(String name) {

		User returnValue = null;
		Optional<User> userOpt = Optional.ofNullable(userService.getUserByEmail(name));
		if (userOpt.isEmpty()) {
			Error error = new Error("Invalid user");
			throw new InvalidUserException(error);
		} else {
			returnValue = userOpt.get();
		}

		return returnValue;

	}
}
