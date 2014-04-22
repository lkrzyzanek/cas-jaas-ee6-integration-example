package org.jasig.cas.client.ee6.authentication;

import org.jasig.cas.client.jaas.AssertionPrincipal;
import org.jasig.cas.client.util.AbstractCasFilter;
import org.jasig.cas.client.util.CommonUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.GeneralSecurityException;

/**
 * Java EE 6 (Servlet 3) replacement of org.jasig.cas.client.jboss.authentication.WebAuthenticationFilter
 * <br/>
 * see https://github.com/Jasig/java-cas-client/blob/master/cas-client-integration-jboss/src/main/java/org/jasig/cas/client/jboss/authentication/WebAuthenticationFilter.java
 * <br/>
 * see https://issues.jasig.org/browse/CASC-174
 *
 * @author Libor Krzyzanek
 */
public class JavaEE6AuthenticationFilter extends AbstractCasFilter {

	/**
	 * Specify whether the filter should redirect the user agent after a
	 * successful validation to remove the ticket parameter from the query
	 * string.
	 */
	private boolean redirectAfterValidation = false;

	@Override
	protected void initInternal(FilterConfig filterConfig) throws ServletException {
		super.initInternal(filterConfig);

		setRedirectAfterValidation(parseBoolean(getPropertyFromInitParams(filterConfig, "redirectAfterValidation", "true")));
		logger.trace("Setting redirectAfterValidation parameter: " + this.redirectAfterValidation);
	}

	public void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse,
						 final FilterChain chain) throws IOException, ServletException {
		final HttpServletRequest request = (HttpServletRequest) servletRequest;
		final HttpServletResponse response = (HttpServletResponse) servletResponse;
		final HttpSession session = request.getSession();
		final String ticket = CommonUtils.safeGetParameter(request, getArtifactParameterName());



		if (session != null && session.getAttribute(CONST_CAS_ASSERTION) == null && ticket != null) {
			try {
				final String service = constructServiceUrl(request, response);
				logger.debug("Attempting CAS ticket validation with service={} and ticket={}", service, ticket);

				try {
					logger.debug("service: " + service + ", ticket: " + ticket);
					request.login(service, ticket);
				} catch (ServletException exception) {
					logger.debug("HttpServletRequest authentication failed.", exception);
					throw new GeneralSecurityException("HttpServletRequest authentication failed.");
				}

				if (request.getUserPrincipal() instanceof AssertionPrincipal) {
					final AssertionPrincipal principal = (AssertionPrincipal) request.getUserPrincipal();
					logger.debug("Installing CAS assertion into session.");
					request.getSession().setAttribute(CONST_CAS_ASSERTION, principal.getAssertion());

					// Added redirectAfterValidation. see Cas20ProxyReceivingTicketValidationFilter implementation
					if (this.redirectAfterValidation) {
						logger.debug("Redirecting after successful ticket validation.");
						response.sendRedirect(constructServiceUrl(request, response));
						return;
					}
					response.sendRedirect(constructServiceUrl(request, response));
				} else {
					logger.debug("Aborting -- principal is not of type AssertionPrincipal");
					throw new GeneralSecurityException(
							"JBoss Web authentication did not produce CAS AssertionPrincipal.");
				}
			} catch (final GeneralSecurityException e) {
				response.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
			}
		} else if (session != null && request.getUserPrincipal() == null) {
			// There is evidence that in some cases the principal can disappear
			// in JBoss despite a valid session.
			// This block forces consistency between principal and assertion.
			logger.debug("User principal not found.  Removing CAS assertion from session to force re-authentication.");
			session.removeAttribute(CONST_CAS_ASSERTION);
		}
		chain.doFilter(request, response);
	}

	public boolean isRedirectAfterValidation() {
		return redirectAfterValidation;
	}

	public void setRedirectAfterValidation(boolean redirectAfterValidation) {
		this.redirectAfterValidation = redirectAfterValidation;
	}
}
