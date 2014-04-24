CAS JAAS Integration Java EE 6 Example
======================================

This example shows how Java EE 6 app (JBoss AS 7 resp. EAP 6.2) can be integrated with CAS Single Sign On server.

In Addition to the CAS integration it shows how to write simple JAAS Login Module.

After deployment you get:

* / - main page with information about currently logged in user
* Filter which examine HTTP Basic authentication. If present then triggers JAAS login.
* /login/cas - single point for login to CAS server
* /secured-servlet - secured servlet via @ServletSecurity annotation


Configuration
-------------
Add Security Domain `CasSecurityDomain` to AS. See JBoss AS 7 / EAP 6.2 example in [.openshift/config/standalone.xml](.openshift/config/standalone.xml)


Resources
---------

* [Configure_Authentication_in_a_Security_Domain](https://access.redhat.com/site/documentation/en-US/JBoss_Enterprise_Application_Platform/6.2/html/Security_Guide/Configure_Authentication_in_a_Security_Domain.html)
* [jaas integration](https://wiki.jasig.org/display/casc/jaas+integration)
* [CASC-174](https://issues.jasig.org/browse/CASC-174)
