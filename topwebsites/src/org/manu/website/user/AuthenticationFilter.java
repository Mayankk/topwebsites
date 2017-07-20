package org.manu.website.user;


import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Authentication Filter class which is the servlet filter which filters all the rest calls
 * which are made and validates the user credentials
 * @author mkurra
 *
 */
public class AuthenticationFilter implements javax.servlet.Filter {
	private Logger logger =
				LogManager.getLogger(AuthenticationFilter.class);
	
	@Override
	public void doFilter(ServletRequest request,
						 ServletResponse response,
						 FilterChain filter) 
						 throws IOException, ServletException {
		
		if (request instanceof HttpServletRequest) {
			HttpServletRequest httpServletRequest = (HttpServletRequest) request;
			String authCredentials = httpServletRequest
					.getHeader(AuthenticationService.AUTHENTICATION_HEADER);
			
			// better injected
			AuthenticationService authenticationService = new AuthenticationService();

			boolean authenticationStatus = 
					authenticationService.authenticate(authCredentials);

			if (authenticationStatus) {
				filter.doFilter(request, response);
			} else {
				if (response instanceof HttpServletResponse) {
					HttpServletResponse httpServletResponse = (HttpServletResponse) response;
					httpServletResponse
							.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				}
			}
		}
	}

	@Override
	public void destroy() {
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}
}

