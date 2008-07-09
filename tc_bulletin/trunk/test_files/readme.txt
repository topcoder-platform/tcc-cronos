1. use tc_bulletin.ddl to create the tables
2. use id_sequences.sql to setup ID Generator settings in database
3. modify these files, to set up the database connection string:
   test_files\config.xml
   test_files\com\topcoder\db\connectionfactory\DBConnectionFactoryImpl.xml
   test_files\failure\config.xml
   test_files\failure\emptyConnectionName.xml
   test_files\failure\emptyIDGenerator.xml
