package com.krzyzanek.casjaas;

import org.jasig.cas.client.jaas.CasLoginModule;
import org.jasig.cas.client.util.CommonUtils;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Libor Krzyzanek
 */
public class CustomCasLoginModule extends CasLoginModule {

	protected Logger log = Logger.getLogger(CustomCasLoginModule.class.getName());

	@Override
	protected boolean preLogin() {
		log.log(Level.FINEST, "Check if service is http(s)");
		final NameCallback serviceCallback = new NameCallback("service");
		final PasswordCallback ticketCallback = new PasswordCallback("ticket", false);
		try {
			this.callbackHandler.handle(new Callback[]{ticketCallback, serviceCallback});
		} catch (final IOException e) {
			logger.info("Login failed due to IO exception in callback handler: {}", e);
			return false;
		} catch (final UnsupportedCallbackException e) {
			logger.info("Login failed due to unsupported callback: {}", e);
			return false;
		}

		if (CommonUtils.isBlank(serviceCallback.getName())) {
			log.log(Level.FINE, "No Service passed. No check performed");
			return true;
		}

		if (serviceCallback.getName().startsWith("http://") || serviceCallback.getName().startsWith("https://")) {
			return true;
		} else {
			log.log(Level.FINE, "Service (username) {0} does not start with http(s)://. Skip this login module", serviceCallback.getName());
			return false;
		}
	}
}
