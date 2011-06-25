Dear Reviewer,

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


--------------------------------------------------------------------------------
Note:
1.about http://apps.topcoder.com/forums/?module=Thread&threadID=711493&start=0
The reviewer do not reply me yet. So I just throw Exception it out

2.about demo
http://apps.topcoder.com/forums/?module=Thread&threadID=711614&start=0

It's really hard to deploy the component to JBoss.
The problem is not this component, is its dependencies.

You can see my demo api, I can use all action by Spring.
So it should be easy to put them to web.
But the dependencies are really complex, and there is no guidline.

You can also see http://apps.topcoder.com/forums/?module=Thread&threadID=711324&start=0, no one answer me.
So I can just write a framework, include full configuration file, and ant script.

-----------------------------------------------------------------------------------


Thanx
Happy Reviewing.