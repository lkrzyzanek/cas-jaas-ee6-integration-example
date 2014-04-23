package com.krzyzanek.casjaas;

import org.jboss.security.SimpleGroup;
import org.jboss.security.auth.spi.UsernamePasswordLoginModule;

import javax.security.auth.login.LoginException;
import java.security.acl.Group;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Custom login module. Simple extension to UsernamePasswordLoginModule
 *
 * @author Libor Krzyzanek
 */
public class CustomLoginModule extends UsernamePasswordLoginModule {

	protected Logger log = Logger.getLogger(CustomLoginModule.class.getName());


	@Override
	protected String getUsersPassword() throws LoginException {
		log.log(Level.FINE, "Get password");
		return "password";
	}

	@Override
	protected Group[] getRoleSets() throws LoginException {
		log.log(Level.FINE, "Get RoleSets");
		Group[] groups = new Group[1];
		// RoleGroup
		groups[0] = new SimpleGroup("Roles");
		return groups;
	}
}
