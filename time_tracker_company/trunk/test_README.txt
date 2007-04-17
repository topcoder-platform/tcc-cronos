1. Modify the the path for dependencies in "build.xml";

2. ifxjdbc, ifxjdbcx, ifxlang, ifxlsupp, ifxsqlj and ifxtools are libs of jdbc driver for informix,
   you can firstly install the informix jdbc driver, and find them under the lib directory.

3. This component would use informix, make sure you select "Log" as "Buffered_log" while you creating the testing
   database. As buffered transaction logging is needed in this component.

4. Create database in your informix server.
   Run the "test_files\sql\create_db.sql" to create tables, and then run "test_files\sql\id_generator.sql", 
   "test_files\sql\accuracy\idseq.sql" and "test_files\sql\stresstests\id_sequences.sql" to insert records
   into id_sequences table for the ID Generator;

5. Modify the db connection setting in "test_files/DBConnectionFactory_Config.xml", "test_files/stresstests/config.xml" and
   "test_files/accuracy/config.xml".

NOTE:
The component can be run under both Java 1.4 and 1.5 as required in CS.
