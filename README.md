CAS JAAS Integration Java EE 6 Example
======================================

This example shows how Java EE 6 app (JBoss AS 7 resp. EAP 6.2) can be integrated with CAS Single Sign On server.

In Addition to the CAS integration it shows how to write simple JAAS Login Module.

Example demonstrates securing:
* JAX-RS REST via @PermitAll resp. @RolesAllowed annotations
* Servlet via @ServletSecurity

After deployment you get:

* / - main page with information about currently logged in user
* Filter which examine HTTP Basic authentication. If present then triggers JAAS login.
* /login/cas - single point for login to CAS server
* /secured-servlet - secured servlet via @ServletSecurity annotation
* /rest/test/public - public REST api
* /rest/test/secure - secured REST api


Test Snippets
-------------
Test secured content without authentication

		curl http://localhost:8080/rest/test/secure
		output:
		401

Test secured content with authentication

		curl -u username:password http://localhost:8080/rest/test/secure
		output:
		OK, principal from security context: username

Test public content

		curl http://localhost:8080/rest/test/public
		output:
		OK PUBLIC


Configuration
-------------
Add Security Domain `CasSecurityDomain` to AS. See JBoss AS 7 / EAP 6.2 example in [.openshift/config/standalone.xml](.openshift/config/standalone.xml)


Resources
---------

* [Configure_Authentication_in_a_Security_Domain](https://access.redhat.com/site/documentation/en-US/JBoss_Enterprise_Application_Platform/6.2/html/Security_Guide/Configure_Authentication_in_a_Security_Domain.html)
* [jaas integration](https://wiki.jasig.org/display/casc/jaas+integration)
* [CASC-174](https://issues.jasig.org/browse/CASC-174)
* [Securing JAX-RS and RESTeasy](https://docs.jboss.org/resteasy/2.0.0.GA/userguide/html/Securing_JAX-RS_and_RESTeasy.html)
