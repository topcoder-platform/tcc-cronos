1. Since the stress reviewer and winner have the different scripts, it is necessary to setup a new clear single database for stress testcase.
It's not quite clear here on what has to be done. There are some tables from the db schema which are changed. 

After creating the db tables for unit and stress databases there must be runned test_files/create_tables.sql(for unit tests database)
and test_files/Stress/create_db.sql(for stress tests database).
Also execute test_files/idgenerator.sql for unit tests database.

2. Other testcases can be run together.

3. The first database with "empty" for name is required too.
