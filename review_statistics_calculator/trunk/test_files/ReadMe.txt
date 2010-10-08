Run all sql files in the following order to set up testing environment. It's assumed that the database name is topcoder.
Change the name of the database if necessary.

1) 01_tcs_catalog_main_schema.sql (may not be necessary if running in real environment) 
2) 02_tcs_catalog_data_setup.sql (may not be necessary if running in real environment)
3) create_schema.sql

To clean up the environment run the clean_DB.sql script (will delete data inserted by insert_DB.sql). Run this only if 
necessary (clean up is done inside test).

Change the ConnectionFactory.xml and db_connection_factory.xml files to the actual database information to be used 
(update host, port, database name, server name, username and password).

