package com.maqs.moneytracker.web.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StringUtils;

import com.maqs.moneytracker.security.LoggedInChecker;

/**
 * Authentication filter for REST services
 */
public class RestUsernamePasswordAuthenticationFilter extends
		UsernamePasswordAuthenticationFilter {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private LoggedInChecker loggedInChecker;

	public RestUsernamePasswordAuthenticationFilter() {
		logger.debug(getClass() + " is getting added");
	}

	@Override
	protected boolean requiresAuthentication(HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			logger.debug("auth from session: " + session.getAttribute("SPRING_SECURITY_CONTEXT"));
		}
		
		logger.debug("auth: " + SecurityContextHolder.getContext().getAuthentication());
		boolean requires = (StringUtils.hasText(obtainUsername(request)) && StringUtils
				.hasText(obtainPassword(request)))
				&& SecurityContextHolder.getContext().getAuthentication() == null;
		logger.debug("requiresAuthentication: " + requires);
		return requires;
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request,
			HttpServletResponse response, Authentication authResult)
			throws IOException, ServletException {
		super.successfulAuthentication(request, response, authResult);

		System.out.println("==successful login==");
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException failed)
			throws IOException, ServletException {
		super.unsuccessfulAuthentication(request, response, failed);

		System.out.println("==failed login==");
	}
}
