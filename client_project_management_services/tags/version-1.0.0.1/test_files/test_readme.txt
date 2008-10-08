1. Initialize your Informix database. 
2. Execute the following sql scripts to set up the databases. 
-test_files\schema.sql

3. Update the following configuration files, you need to modify the connection string for 
databases. 
-test_files\bean-config\config\db_config.xml
-test_files\bean-config\META-INF\application.xml
-test_files\bean-config\META-INF\hibernate.cfg.xml
-test_files\bean-config\informix-ds.xml
-test_files\config\db_config.xml

4. JBoss server that is used for this component is JBoss 4.2.3.
   Developer choose this server caused by in this version, developer can avoid
   'multiple context root' problem while perform deployment.

5. While perform testing for this component, following issue should be consider.
	a. configure db_config.xml, JBoss.properties, and informix-dx.xml based on
	   your own configuration.
	b. See documentation of JBossLoginModule component.
	   This component using MBean dynamic configuration. Add following script into jboss-service.xml.
	  	<mbean code="com.topcoder.security.auth.module.JBossLoginModuleDemoConfig"
      		name="jboss.security:service=JBossLoginModuleDemoConfig">
      		<attribute name="LoginConfig">jboss.security:service=XMLLoginConfig</attribute>
      		<attribute name="SecurityManager">jboss.security:service=JaasSecurityManager</attribute>
   		</mbean>
	c. run 'ant deployEAR', start JBoss server, run any test.