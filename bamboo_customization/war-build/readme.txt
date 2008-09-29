---------------------------------------------------------------------
JIRA 3.11-#288 README
---------------------------------------------------------------------

Thank you for downloading JIRA 3.11 - WAR (Webapp ARchive) version.

This version is intended to be used as a WAR deployed in a J2EE
application server (such as Orion, Tomcat, Jetty, JBoss etc).

You can also download a standalone version which is bundled with
Jakarta Tomcat and will run out of the box. It is a lot easier to 
setup and you can always migrate your data between JIRA instances 
very easily using the XML export/import.

REQUIREMENTS
------------
You will need a JDK (Java Development Kit) 1.4 or above.  JIRA can be built
(and run) with JDK 1.3, but some extra jars must be downloaded and copied to
tools/ant/lib/:

http://ibiblio.org/maven/xerces/jars/xercesImpl-2.4.0.jar
http://ibiblio.org/maven/xml-apis/jars/xml-apis-1.0.b2.jar


BRIEF INSTALL GUIDE
-------------------
For the impatient, here's a brief install guide:

1. edit 'edit-webapp/WEB-INF/classes/entityengine.xml' for your database and
   app server (see below).

2. Optionally place any custom source files that should be compiled into the
    src/ directory. (Remember to create the appropriate package directories)

3. run 'build war' (builds a WAR) or 'build ear' (builds an EAR)
4. configure your servlet container as described at

   http://www.atlassian.com/software/jira/docs/v3.11/servers/index.html

   JIRA relies on the servlet container to provide a database connection
   factory (javax.sql.DataSource) and Transaction Manager
   (javax.transaction.UserTransaction).  See etc/jira.xml for a sample
   configuration.

5. deploy the resulting WAR or EAR in your application server.


For details on editing entityengine.xml, see

   http://www.atlassian.com/software/jira/docs/v3.11/entityengine.html


EDITING CONFIGURATION FILES
---------------------------
The build process works by copying everything in webapp/ to a temporary
directory, then overwriting those files with everything from edit-webapp/.

This enables you to store edited files (eg. configuration files) in
edit-webapp/, and preserve these changes when you update JIRA.

To edit a particular configuration file:

 - locate the file you want to edit in webapp/
 - copy it to the same location under edit-webapp/
   (i.e. if it was webapp/WEB-INF/classes/osuser.xml, copy it to
   edit-webapp/WEB-INF/classes/osuser.xml)
 - edit the file
 - rebuild the WAR using 'build war'

Most files you might want to edit are in webapp/WEB-INF/classes.
Please ask on the mailing list before editing a file if you are unsure
what the file does.

Some commonly edited files (in WEB-INF/classes):

 - entityengine.xml -- Edit for your server to change the transaction
   factory and configure your data source link.
 - osuser.xml -- Edit to configure the user management in JIRA.
 - templates/*.vm -- Edit to configure JIRA's outgoing email. These are
   standard Velocity templates.
 - log4j.properties -- Edit to adjust the JIRA logging levels.  In JIRA
   3 and above, you should rather edit log levels through the web
   interface.


CUSTOM SOURCE FILES
-------------------
If you would like to extend JIRA's functionality, you can get your source
files compiled as part of the build process. Just place your source files
in the src/ directory (the directory is empty by default). Remember to create
the required sub-directories under the src directory to match the package
names of your source files, as otherwise the build process will fail. The
files are compiled into the WEB-INF/classes directory of the web application.


UPGRADING
---------
To upgrade JIRA, you should proceed as if making a clean install, and then
import an XML backup of your old data.  The process is described at:

http://www.atlassian.com/software/jira/docs/v3.11/upgrading.html

When upgrading, please use your old entityengine.xml as a reference, not a
replacement.  The syntax may have changed.


ERRORS
------
If you encounter any problems, please create a support request at:
http://support.atlassian.com


QUESTIONS?
----------
Questions? Try the docs at:

   http://www.atlassian.com/software/jira/docs

or ask on the forums at:

   http://forums.atlassian.com/index.jspa


-----------------------------------------------------------
Happy issue tracking and thank you for using JIRA!
- The Atlassian Team
-----------------------------------------------------------
