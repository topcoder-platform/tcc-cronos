readme.txt
----------

After unjarring this submission, this is what you should do to test this submission

1. Please review the forum; there was a change to some of the constructors and
	some exception declarations were added.  Also, the User id field was changed
	from a long to an int (and therefore in the DDL as well; see step 4)

2a. Create an Informix (hopefully 9.4) server.  Create a database in that server WITH TRANSACTION
   LOGGING ENABLED.  This is required for the Authorization 2.0 component.  In my submitted
   configuration files, the following parameters were used (you may save yourself some work
   if you use the same values)
    
        server name:    informix_server
        database name:  topcoder
        username:       informix
        password:       informixtco2004
        computer name:  CompaqLaptop (you will likely not be able to mimic this, but that's ok)
        port:           1526
      
2b.Create another Informix database named 'badtopcoder' with all the same parameters as above.
   Do not create any tables in this database.

3. Modify the following files in test_files to reference the paramaters you used when you set up
   your database in step 2a/2b. You will also have to change the server name "CompaqLaptop" to your
   server's name or IP address:
   
        TimeTrackerUserBadClass.xml
        TimeTrackerUserBadConnString.xml
        TimeTrackerUserBadDefaultConnection.xml
        TimeTrackerUserBadUserStorexml
        TimeTrackerUserConfig.xml
        TimeTrackerUserWrongDatabaes.xml: all above parameters EXCEPT that the database name
                    should be the 'badtopcoder' database.

4. In the 'topcoder' database [or whatever you called it in step 2], run the DDL script
   docs/Time_Tracker_User.sql.  This will create the tables that this component is required to use.

5. In the 'topcoder' database [or whatever you called it in step 2], run the DDL script
   test_files/authorization_schema.sql. This creates the tables needed for the Authorization component, 
   and inserts the Roles required by the Time Tracker User component.
   
6. In the 'topcoder' database [or whatever you called it in step 2], run the DDL script
   test_files/id_generator.sql.  This will create the table needed for the ID Generator 3.0
   component, and insert the required row for this component.

7. Modify your build.xml file to include the following jars in the runtime classpath. 
   These jars were not included in the original development distribution but they
   are required for the Authorization and Authentication factory components.  No 
   additional components were added to this submission by this developer.
   
        ifxjdbc.jar (included in this submission)
        authorization.jar    (Version 2.0.1, NOT Version 2.0; available on the Time Tracker User forum)
        logging_wrapper.jar  (needed for Authorization)
        typesafe_enum.jar    (needed for Authorization)
        simple_cache.jar     (needed for Authorization)

   You may also have to possibly add xml jars such as:
        xerces.jar
        xercesImpl.jar
        xml-apis.jar
        xmiParser-apis.jar
   But this depends on your local machine's configuration and what version of 
   Java you are using.
        
8. ant test
