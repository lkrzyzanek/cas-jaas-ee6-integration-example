package com.krzyzanek.casjaas.filter;

import org.apache.commons.codec.binary.Base64;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Libor Krzyzanek
 */
public class BasicAuthenticationFilter implements Filter {

	protected Logger log = Logger.getLogger(BasicAuthenticationFilter.class.getName());

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;

		// Check http basic authentication first
		Enumeration<String> authentication = request.getHeaders("Authorization");

		while (authentication.hasMoreElements()) {
			String auth = authentication.nextElement();
			if (!auth.startsWith("Basic")) {
				continue;
			}
			String hash = auth.substring(6);

			// Alternatively use org.jboss.resteasy.util.Base64
			byte[] decoded = Base64.decodeBase64(hash);
			String usernamePassword = new String(decoded);

			int colon = usernamePassword.indexOf(':');
			if (colon > 0) {
				String username = usernamePassword.substring(0, colon);
				String password = usernamePassword.substring(colon + 1, usernamePassword.length());

				log.log(Level.FINE, "Requiring Basic Authentication for username: {0}", username);
				request.login(username, password);

			}
		}
		chain.doFilter(req, resp);
	}

	@Override
	public void destroy() {

	}
}
