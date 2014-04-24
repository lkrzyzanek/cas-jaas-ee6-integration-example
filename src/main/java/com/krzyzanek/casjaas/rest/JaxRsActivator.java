/*
 * JBoss, Home of Professional Open Source
 * Copyright 2012 Red Hat Inc. and/or its affiliates and other contributors
 * as indicated by the @authors tag. All rights reserved.
 */
package com.krzyzanek.casjaas.rest;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * A class extending {@link javax.ws.rs.core.Application} and annotated with @ApplicationPath is the Java EE 6 "no XML" approach to
 * activating JAX-RS.
 *
 * <p>
 * Resources are served relative to the servlet path specified in the {@link javax.ws.rs.ApplicationPath} annotation.
 * </p>
 *
 * @author Libor Krzyzanek
 */
@ApplicationPath("/rest")
public class JaxRsActivator extends Application {
	/* class body intentionally left blank */
}
