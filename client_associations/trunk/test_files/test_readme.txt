Steps to run through the tests: 

1. Initialize your informix services. 
2. Execute the following sql scripts to set up the databases. 
-test_files\create.sql
-test_files\failure\create.sql
3. Update the following configuration files, you need to modify the connection string for 
databases. 
-test_files/hibernate.cfg.xml
-test_files/informix-ds.xml

4. Initialize your jboss-4.2.1.GA
Update jboss.properties according your jboss path.

5. Copy test_files/client_associations.ear files to
   ${jboss.home}/server/${jboss.server.config}/deploy directory
6. Start the JBoss
7. Modify the the path for dependencies in "build.xml"
10. Please add the test_files folder into class path

PS: I comment out the AccuracyTests, because the accuracy tests the remote ejb should be deployed with jndi name "ejb/remote/cabean", 
it is different from the jndi name of unit tests, but the ejb deploy can only one jndi name. So can not run all tests in once.
And I test the accuracy tests alone, and all pass.
