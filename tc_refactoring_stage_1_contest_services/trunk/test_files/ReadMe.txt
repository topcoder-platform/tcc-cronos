 steps to test the Unit tests:

   +) To run my test, you should set your environment first.
   Please add folder test_files into your class path.

   *) Change the config about database to suit your environment.
      These files need be updated: ApplicationContext.xml

   +) Then use createDB.sql to create tables

   +) Then use insert.sql to insert data for tests

   +) Use the ant deploy_to_tomcat to see demo

   +) After testing, use delete.sql to delete all data
 

steps to test the accuracy tests:

1. Configure database connection in application context.
2. Create database with "accuracytests/schema.sql".
3. Populate database with "accuracytests/populate.sql".
4. Run tests.

Clear database with "accuracytests/clear.sql".