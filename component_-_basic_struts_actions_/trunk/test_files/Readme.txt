Please follow the following steps before testing

1.Setup the TC database locally by following the instructions here http://apps.topcoder.com/wiki/display/docs/TopCoder+Databases+Setup+Guide

2.Run test_files/scripts.sql

3.Insert the temp data for testing, run test_files/insert.sql

4.Configure your database connection details in test_files/hibernate.cfg.xml, test_files/com/topcoder/db/dbconnectionfactory/DBConnectionFactoryImpl.xml

5.Run test_files/DevNullSmtp.jar before testing, it make a email server

6.After testing, run test_files/delete.sql to clear the data

--------------------------------------------------------------------------------
about demo for Jboss

Please use ant deploy_to_jboss to do that.

And copy test_files/mailBodyTemplate.txt to %Jboss%/bin, because action need read file in server.

Do not forget run test_files/DevNullSmtp.jar!
