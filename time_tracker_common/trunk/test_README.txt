1. Modify the the path for dependencies in "build.xml";

2. ifxjdbc is the lib of jdbc driver for informix,
   you can firstly install the informix jdbc driver, and find them under the lib directory.

3. This component would use informix, make sure you select "Log" as "Buffered_log" while you creating the testing
   database. As buffered transaction logging is needed in this component.

4. Create database named "tcs" and "meEmpty" in your informix server.
   1). For "tcs", run the "test_files\Time_Tracker_Common_DDL.sql" to create tables,
       and then run "test_files\database.sql" to insert records into id_sequences table for the ID Generator;
   2). Keep the "meEmpty" empty, as it would be used in the failure tests.

5. Modify the db connection setting in "test_files/DBConnectionFactory.xml" and
   "test_files/UnitTests/DBConnectionFactory_Error.xml".


NOTE:
The component can be run under both Java 1.4 and 1.5 as required in CS.
