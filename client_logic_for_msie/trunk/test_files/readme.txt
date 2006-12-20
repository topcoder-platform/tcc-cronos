Prerequisites for running the demo:

1. The demo is in test_files\demo, and the script object javascript is in test_files\msie\demo.html.  To run it, you should add your config file in preload.xml 
and put the preload.xml in a directory such as C:\Documents and Settings\conf 

2. register/unregister to the demo using test_files\Demo\bin\reg.bat and test_files\Demo\bin\unreg.bat, with this (test_files) directory as the current working directory.

3. Ensure that you are connected to the Internet.

4. You may need to change the assembly location in config files.


Notes about running the unit tests:

1. You should set the http://localhost/msie to the directory test_files/msie 
   and make sure your iis is running

2. Some test cases use the WebBrowser control, which seems sometimes to not
   behave as expected on first running.  It is recommend that nunit-gui.exe be
   used to run the tests; if the first run is blocked then stop and restart.

3. If the tests fail then it may be a result of one or more conflicting
   plug-ins installed in your IE.  Try removing other plug-ins and testing
   again.

4. When using nant to run the tests, make sure the resource file is specified
   correctly in default.build

5. Change the AssemblyKeyFile property in the demo's AssemblyInfo.cs as
   appropriate for your local directory structure

