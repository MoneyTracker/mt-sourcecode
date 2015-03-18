package com.maqs.moneytracker.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.maqs.moneytracker.model.User;

@Component
public class LoggedInChecker {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	public User getLoggedInUser() {
		User user = null;
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		logger.debug("getLoggedInUser() authentication: " + authentication);
		if (authentication != null) {
			Object principal = authentication.getPrincipal();
			if (principal instanceof MyUserDetails) {
				MyUserDetails userDetails = (MyUserDetails) principal;
				user = userDetails.getUser();
			}
		}
		logger.debug("getLoggedInUser(): " + user);
		return user;
	}
}
