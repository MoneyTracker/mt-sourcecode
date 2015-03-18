package com.maqs.moneytracker.web.security;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class AuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public AuthSuccessHandler() {
        super();
    }

    @PostConstruct
    public void afterPropertiesSet() {
//        setRedirectStrategy(new NoRedirectStrategy());
    }
    
    /*@Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
    	logger.debug("onAuthenticationSuccess()");
    	super.onAuthenticationSuccess(request, response, authentication);
        Object principal = authentication.getPrincipal();
        User user = null;
        if (principal instanceof MyUserDetails) {
        	MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
        	user = userDetails.getUser();
        } else {
        	user = new User();
        	response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        	user.setEmail("anonymous");
        }    
        logger.info(user.getEmail() + " got connected ");
    }*/
    
    protected class NoRedirectStrategy implements RedirectStrategy {
        @Override
        public void sendRedirect(HttpServletRequest request,
                HttpServletResponse response, String url) throws IOException {
        	logger.debug("No Redirect: URL: " + url);
        	
        }

    }
    
    @Override
    protected String determineTargetUrl(HttpServletRequest request,
            HttpServletResponse response) {
        String context = request.getContextPath();
        String fullURL = request.getRequestURI();
        String url = fullURL.substring(fullURL.indexOf(context)+context.length());
        return url;
    }
 
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
            HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        String url = determineTargetUrl(request,response);
        request.getRequestDispatcher(url).forward(request, response);
    }
}
