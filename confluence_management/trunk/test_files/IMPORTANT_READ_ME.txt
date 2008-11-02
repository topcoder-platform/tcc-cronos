Dear reviewers:
please follow this instruction,you will successfully install the confluence and start up and set the account for test and at last run my all the test cases.

1 download the confluence 2.9.2 
http://www.atlassian.com/software/confluence/

2 install the confluence 
unzip the download file
2.1. Download "JDK 5.0 Update 16" from http://java.sun.com/javase/downloads/index_jdk5.jsp.
   Note: The latest version may have increased, but the most important thing is to get the SDK version. Not the JRE.

2.2. Set the JAVA_HOME variable to where you installed Java. Full instructions here: http://confluence.atlassian.com/x/vyI

2.3. Edit confluence\WEB-INF\classes\confluence-init.properties and set the confluence.home property to a directory of your choice. 
   e.g. confluence.home=D:/TC/dev/confluence_management_1.0.0/materials/confluence-2.9/data

   This directory is where Confluence will store its configuration information,
   indexes and attachments.

2.4. Run bin/startup.sh (*nix) or bin\startup.bat (Windows). Check that there are no errors on the console.  See below for troubleshooting advice.

2.5. Point your browser at http://localhost:8080/  You should see the Confluence Setup Wizard.
2.6  regist a user first on confluence and get a Confluence Personal License using http://www.atlassian.com/software/confluence/personal-wiki.jsp
2.7 install the confluence
2.8 create the admin account.If you set the user name - tcsdeveloper and password - TCSDEVELOPER, then you may not modify the user name and password in the test.see below for more information about modify the user name,password and url for test

3 then you may modify some test files for pass all my test cases
  3.1 DefaultConfluenceManagerTest2 -> CONFLUENCE_URL      the url for the wsdl on your confluence , if not success replace the localhost with your ip
      DefaultConfluenceManagerTest2 -> USER_NAME           user name of your account  created above
      DefaultConfluenceManagerTest2 -> PASSWORD            password of your account  created above
  3.2 Demo-> CONFLUENCE_URL      the url for the wsdl on your confluence , if not success replace the localhost with your ip
      Demo-> USER_NAME           user name of your account  created above
      Demo-> PASSWORD            password of your account  created above

4 put all the jars in ${your confluence directory}\confluence\WEB-INF\lib into test_reflib
5 of course note the tc dependency        
thank you for reviewing