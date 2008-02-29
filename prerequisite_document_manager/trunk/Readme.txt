Setup
------------------------------------------------------
Database

1. install Informix and make sure the database is started.
2. create a database named 'document' in Informix.
3. use 'dbaccess document <path>/test_files/create.sql' to create database. it will create tables and sequences. Note, you can use 'dbaccess document <path>/test_files/drop.sql' to drop tables and sequences.
4. change the ip address and port in test_files/informix-ds.xml and test_files/InformixDBConnectionFactory.xml.

Here, database is ready.

JBoss

1. Download and install JBoss 4.2.2.

Build.xml

1. there are some settings need to be adjusted according to your environment.
like
1.1 "configuration for checkstyles"
1.2 "codertura task definitation"
1.3 <property name="jboss_home" value="D:/Servers/AppServers/jboss-4.2.2.GA" />


Now, all the setup is ready. Let's start.

1. start Jboss 4.2.2
2. use 'ant deployEAR', to package ear file and deploy to jboss, a latest built is alredy saved as test_files/prerequisite_document_manager.ear.
3. use 'ant test' to run the test cases.
4. 'ant undeployEAR' is used to delete the ear file in JBoss. and 'ant package.ejb' is used to package the ejb jar.

This is the most simplified process I can offer you:)

Other Notes
------------------------------------------------------------------------

1. About the coverage report, there are some issue with using codertura with jboss. So the coverage report is incorrect. I covered all the case for testing.
