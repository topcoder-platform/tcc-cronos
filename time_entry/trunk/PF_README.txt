1. Please modify the connection configuration in config.xml, AccuracyTests\config.xml and failure\config.xml.

2. logging wrapper, Database Abstraction, typesafe enum, class associations and data validation components are indirectly reference by this component.

3. This component use MockEJB to test the session bean. You can download MockEJB from 
http://mockejb.sourceforge.net/

Note, they are put under /test_files so you can use them directly.

4. There are many indirectly dependence components, so I attach my build.xml to help
you to know what components are used by this component.

5. This component runs under informix database, the jdbc library is put under the /test_files as well.

Note, please set up the database environment before you run the tests.

Typically, you should create a "tt_time_entry" database and then use the "test_files/create_db.sql" to 
set up the tables. And create a "empty" database (Note, no tables should be added to this database, 
it is for failure tests).
Modify the configuration file accroding to your database setting.

Please use "test_files/create_db.sql" to set up the database instead of the one provided by PM.
The difference is all the foreign key constraints are removed by me because I have to mock the 
dependence components.

"test_files/add_data.sql" and "test_files/clear_data.sql" will be executed by the test cases
automatically so there is no need to execute them before run the tests.

6. MockEJB is used so no application server is needed. You can run the tests directly after the 
database is ready.

