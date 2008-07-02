1. Initialize your Informix database. 
2. Execute the following sql scripts to set up the databases. 
-test_files\DRSchema.ddl
-test_files\id_sequences.sql

3. Update the following configuration files, you need to modify the connection string for 
databases. 
-test_files\hibernate.cfg.xml
-test_files\com\topcoder\db\connectionfactory\DBConnectionFactoryImpl.xml