Steps to run through the tests: 

1. Initialize your informix services. 
2. Execute the following sql scripts to set up the databases. 
-test_files\sql\create.ddl
-test_files\sql\id_generator.sql
3. Update the following configuration files, you need to modify the connection string for 
databases. 
-test_files\META-INF\persistence.xml 
-conf\IDGenerator\META-INF\dbconnectionfactory.xml
-conf\informix-ds.xml
-conf\ComponentService\informix-ds.xml

4. Initialize your jboss-4.2.1.GA
Update jboss.properties according your jboss path.

5. you also need update these files inside according your environment's database.
test_files/build/ear/IDGenerator.ear/id_generator_ejb.jar/META-INF, 
test_files/build/ear/catalog_services.ear/catalog_services_ejb.jar/META-INF

6. Copy Informix jdbc.jar library to ${jboss.home}/server/${jboss.server.config}/lib directory.
7. Copy IDGenerator.ear and component_service.ear files to
   ${jboss.home}/server/${jboss.server.config}/deploy directory
8. Start the JBoss
9. Modify the the path for dependencies in "build.xml"
10. Please add the test_files and conf folder into class path