package com.maqs.moneytracker.web.security;

import java.io.IOException;
import java.text.MessageFormat;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.maqs.moneytracker.security.CustomAuthenticationToken;
import com.maqs.moneytracker.security.TokenManager;
import com.maqs.moneytracker.services.UserService;

public class CustomTokenAuthenticationFilter extends
		AbstractAuthenticationProcessingFilter {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private TokenManager tokenManager;

	@Autowired
	private UserService userService;

	public CustomTokenAuthenticationFilter(String defaultFilterProcessesUrl) {
		super(defaultFilterProcessesUrl);
		super.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(
				defaultFilterProcessesUrl));	
	}

	@Override
	public void setAuthenticationManager(
			AuthenticationManager authenticationManager) {
		super.setAuthenticationManager(authenticationManager);
	}
	
	public final String HEADER_SECURITY_TOKEN = "tn";

	@Override
	protected boolean requiresAuthentication(HttpServletRequest request,
			HttpServletResponse response) {
		/*Enumeration<String> headers = request.getHeaderNames();
		while (headers.hasMoreElements()) {
			String header = headers.nextElement();
			System.out.println(header + " " + request.getHeader(header));
		}*/
		String token = request.getHeader(HEADER_SECURITY_TOKEN);
		boolean requires = token != null;
		logger.info("requiresAuthentication:" + requires + " token: " + token);
		return requires;
	}
	/**
	 * Attempt to authenticate request - basically just pass over to another
	 * method to authenticate request headers
	 */
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request,
			HttpServletResponse response) throws AuthenticationException,
			IOException, ServletException {
		String token = request.getHeader(HEADER_SECURITY_TOKEN);
		Authentication userAuthenticationToken = authUserByToken(token);
		if (userAuthenticationToken == null) {
			throw new AuthenticationServiceException(MessageFormat.format(
					"Error | {0}", "Bad Token"));
		}
		SecurityContextHolder.getContext().setAuthentication(userAuthenticationToken);
		return userAuthenticationToken;
	}

	/**
	 * authenticate the user based on token
	 * 
	 * @return
	 */
	private Authentication authUserByToken(String token) {
		if (token == null) {
			return null;
		}
		Authentication authToken = null;
		try {			
			authToken = new CustomAuthenticationToken(token, tokenManager);				
		} catch (Exception e) {
			throw new AuthenticationServiceException(e.getMessage(), e);
		}
		return authToken;
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		logger.debug(getClass() + " filter request...");
		HttpServletResponse httpResponse=(HttpServletResponse)res;
        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpResponse.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        httpResponse.setHeader("Access-Control-Allow-Headers", "Origin,tn, X-Requested-With, Content-Type, Accept, Authorization,access_token");
        httpResponse.setHeader("Access-Control-Expose-Headers", "WWW-Authenticate,tn,status,Authorization,access_token");
        
        HttpServletRequest httpRequest = (HttpServletRequest)req;
        if (httpRequest.getMethod().equals("OPTIONS")) {
            try {
            	httpResponse.getWriter().print("OK");
            	httpResponse.getWriter().flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
		super.doFilter(req, res, chain);
	}

}