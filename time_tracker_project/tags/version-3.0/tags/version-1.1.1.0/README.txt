Steps to run unit tests
-----------------------
1. Execute the DDL insert script (test_files/insert.sql) to create the tables and some predefined entries.
2. Edit the database configuration (test_files/timetrackerproject.xml).
3. Execute 'ant test' within the directory that the distribution was extracted to.
4. Execute the DDL delete script (test_files/delete.sql) to clean up the tables.
