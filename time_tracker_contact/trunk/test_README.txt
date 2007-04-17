1. Modify the the path for dependencies in "build.xml";

2. ifxjdbc, ifxjdbcx, ifxlang, ifxlsupp, ifxsqlj and ifxtools are libs of jdbc driver for informix,
   you can firstly install the informix jdbc driver, and find them under the lib directory.

3. This component would use informix, make sure you select "Log" as "Buffered_log" while you creating the testing
   database. As buffered transaction logging is needed in this component.

4. Create database in your informix server.
   Execute the "test_files\audit_tables.sql" and "test_files\contact&adress_tables.sql" in your database.

5. Modify the db connection setting in
   "test_files\UnitTests\DBConnectionFactory.xml",
   "test_files\UnitTests\Informix.properties",
   "test_files\accuracytests\config.xml",
   "test_files\failure\config.xml",
   "test_files\stresstests\config.xml",

NOTE:
The component can be run under both Java 1.4 and 1.5 as required in CS.
