1. all dependecy interop dll already given in ./test_files/dll
   You also can build with ./test_files/idl/build.bat

2. demo is given ./test_files/demo
   and script object javascript is given in msie/demo.html

   also to run demo, you should add your config file in preload.xml 
   and put the preload.xml in directory like C:\Documents and Settings\conf 

3. register/unregister to the demo using ./test_files/idl/reg.bat and ./test_files/idl/unreg.bat
 Note: you should need to change the directory in reg.bat and unreg.bat
  I don't composite the build.bat, build the demo and reg.bat into one, as the reviewer required.
  for both are seperate functionality.

4. please make sure your are connected to the internet.

5. You may need to change the assembly location in config files.

6. Problems in running unit test.
    Since some test case will use the web browser control, I didn't know
Why when the first running the web browser control don't do as expected.
You are recommend to use nunit-gui.exe to run the test. And maybe 
the first running time will be blocked for the web browser control,
you need to stop and then re-start again.

7. And last, Please make sure your IE don't install some other plugins for test.

Thanks