1. Modify the the path for dependencies in "build.xml";

2. ifxjdbc, ifxjdbcx, ifxlang, ifxlsupp, ifxsqlj and ifxtools are libs of jdbc driver for informix,
   you can firstly install the informix jdbc driver, and find them under the lib directory.

3. This component would use informix, make sure you select "Log" as "Buffered_log" while you creating the testing
   database. As buffered transaction logging is needed in this component.

4. Create database in your informix server.
   As the unit test and accuracy test use the different db schema, you need to create 2 database instances,
   Run the "test_files\UnitTestSchema.sql" to create tables for unit test; and "test_files\accuracytests\schema.sql" for accuracy test.

5. Modify the db connection setting in "test_files/DBConnectionFactory.xml", "test_files/stresstests/DBConnectionFactory.xml",
   "test_files/failure/config.xml" and "test_files/accuracy/dbconfig.xml".

NOTE:
The component can be run under both Java 1.4 and 1.5 as required in CS.
