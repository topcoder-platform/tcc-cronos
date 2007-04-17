1. execute the audit_tables.sql/contact&adress_tables.sql in informix
2. modify the UnitTests.DBConnectionFactory.xml/Informix.properties
              accuracytests/config.xml
              failure/config
              stresstests/config
              
              the db_connection_factory configuration.
   They can point to the same database

3. mockejb-0.6-beta2/mockrunner-0.3.7 is used in test case

4. Test case will print out lots of the exception stack trace.

   
