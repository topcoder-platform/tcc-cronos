Dear Reviewer,

Please follow the following steps before testing

1.Setup the TC database locally by following the instructions here http://apps.topcoder.com/wiki/display/docs/TopCoder+Databases+Setup+Guide

2.Run test_files/scripts.sql.

3.Configure your database connection details in test_files/hibernate.cfg.xml, test_files/com/topcoder/db/dbconnectionfactory/DBConnectionFactoryImpl.xml

4.Please strictly use the libraries provided in test_files and test_reflib.

5. Do not forget run mock email server.
I use smtp4dev.

6. To run demo on Jboss, run ant deploy_to_jboss, and copy /conf(include folder) and valid-simple-comment.txt to jboss's bin directory

Thanx
Happy Reviewing.
