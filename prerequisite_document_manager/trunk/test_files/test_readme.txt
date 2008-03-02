1. Initialize your Informix database. 
2. Execute the following sql scripts to set up the databases. 
-test_files\create.sql

3. Update the following configuration files, you need to modify the connection string for 
databases. 
-test_files\InformixDBConnectionFactory.xml
-test_files\informix-ds.xml
-test_files\stresstests\InformixDBConnectionFactory.xml

4. Update the informix-ds.xml file in test_files/prerequisite_document_manager.ear
Deploy EJB test_files/prerequisite_document_manager.ear into $JBoss_Home\server\default\deploy.

5. Modify the "build-dependencies.xml" for dependencies. 

PS: I comment the failure tests in AllTests.java, because it uses the JNDI name is different unit tests.
So when run FailureTests, you should do the below deploy:
Update the informix-ds.xml file in test_files/failure/prerequisite_document_manager.ear
Deploy EJB test_files/failure/prerequisite_document_manager.ear into $JBoss_Home\server\default\deploy.