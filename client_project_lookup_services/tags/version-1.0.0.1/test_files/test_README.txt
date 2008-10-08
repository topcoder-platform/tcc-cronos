Here are basic sequence to test the component:

1. Correct "jboss_home" and "jboss_config_name" in build-dependencies.xml.

2. Steps to let JBoss know about TopCoder JBoss Login Module:

  2.1. Add following fragment to %JBOSS_HOME%/server/default/conf/login-config.xml into <policy> section:

<!-- ============================ TopCoder JBoss Login Module =============================-->
<application-policy name="topcoder-security">
<authentication>
<login-module code="com.topcoder.security.auth.module.JBossLoginModule"
          flag="required">
         <module-option name="fileName">F:\jboss-4.2.2.GA\server\default\deploy\client_project_lookup_services.ear\config.properties</module-option>
         <module-option name="configNamespace">DemoNamespace</module-option>
         <module-option name="password-stacking">
              useFirstPass
          </module-option>
          <module-option name="unauthenticatedIdentity">anonymous</module-option>
       </login-module>
</authentication>
</application-policy>

Of course, fileName value should be changed corresponding to your JBoss location.
In general, it's config.properties from the directory where component is deployed.

  2.2. Copy all libs from test_files/libs_for_login_module to %JBOSS_HOME%/server/default/lib

3. Run "ant deployEAR" target.

4. Start JBoss (I used 4.2.2.GA)

5. Run "ant test" or "ant coveragereport".

PLEASE NOTE: Cobertura 1.8 uses older log4j than JBoss does.
To correctly test and cover the submission you should replace cobertura's log4j jar with log4j-1.2.15.jar. You can find it in /test_files


