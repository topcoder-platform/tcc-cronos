1. This component use MockEJB to test the session bean. You can download MockEJB from 
http://mockejb.sourceforge.net/

Note, they are put under /test_files so you can use them directly.

2. There are many indirectly dependence components, so I attach my build.xml to help
you to know what components are used by this component.

3. This component runs under informix database, the jdbc library is put under the /test_files as well.

Note, please set up the database environment before you run the tests.

Typically, you should create a "tt_project" database and then use the "test_files/create_db.sql" to 
set up the tables. And create a "empty" database (Note, no tables should be added to this database, 
it is for failure tests).
Modify the configuration file accroding to your database setting.

Please use "test_files/create_db.sql" to set up the database instead of the one provided by PM.
The difference is all the foreign key constraints are removed by me because I have to mock the 
dependence components.

"test_files/add_data.sql" and "test_files/clear_data.sql" will be executed by the test cases
automatically so there is no need to execute them before run the tests.

4. MockEJB is used so no application server is needed. You can run the tests directly after the 
database is ready.

5. Be patient. The tests may cost some time and please wait to see the result. It would be better
to use the Eclipse or the graphic runner to run the tests and you can know what's going on with 
the test more intuitionistic.
FYI, in my compute, the test cases take 7 minutes and 29 seconds.

Thanks for your review! Have Fun!