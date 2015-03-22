package com.maqs.moneytracker.security;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import com.maqs.moneytracker.common.Constansts;
import com.maqs.moneytracker.common.service.exception.ServiceException;

public class CustomAuthenticationToken implements Authentication {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = -8482729479409817604L;

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private String username;
	
	private Object principal;
    
    private Object details;    
    
    private final TokenManager tokenManager;
    
    private Collection<GrantedAuthority> authorities;
    
    private boolean authenticated;
    
    public CustomAuthenticationToken(String token, TokenManager tokenManager) throws ServiceException {
    	this.tokenManager = tokenManager;
        populateDetails(token);        
    }
 
    @SuppressWarnings("unchecked")
	private void populateDetails(String token) throws ServiceException {
    	if (tokenManager.valid(token)) {
    		MyUserDetails userDetails = (MyUserDetails) tokenManager
    				.getUserFromToken(token);
    		details = userDetails;
    		principal = userDetails;
    		username = userDetails.getUsername();
    		authorities = (Collection<GrantedAuthority>) userDetails.getAuthorities();
    		logger.debug("populateDetails: " +  userDetails);
    	} else {
    		throw new AuthenticationServiceException("Either your token is invalid or it is expired, please login again");
    	}
	}

	@Override
    public Object getCredentials() {
        return Constansts.EMPTY_STRING;
    }
 
	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		return authorities;
	}
	
    @Override
    public Object getPrincipal() {
        return principal;
    }
    
    @Override
    public Object getDetails() {
    	return details;
    }

	@Override
	public String getName() {
		return username;
	}

	@Override
	public boolean isAuthenticated() {
		return authenticated;
	}

	@Override
	public void setAuthenticated(boolean authenticated) throws IllegalArgumentException {
		this.authenticated = authenticated;
	}
}
