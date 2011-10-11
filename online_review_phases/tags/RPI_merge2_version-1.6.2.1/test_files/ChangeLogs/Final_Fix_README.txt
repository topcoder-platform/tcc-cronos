
Hi Reviewer

Have a good day,

Make sure to use the SQL script in ./test_files/sqls/merge-all.sql to initialized the database tables for the package. I have corrected some entries in it in order for this submission not to generated lots of Error due to missing tables and columns.

There is a helper file in doing the DB initialization that can be use under Win32 environment at ./test_files/sqls/init_db.bat

Please edit ./build.properties if you want to use your own jar files, instead of the provided jar files in ./libs/*. Please note that the provided library came from Contest forum and recomended for use for this package. Its details were all in 'build-dependencies.xml'

The build.xml is slightly modified to include the "build.properties" instead of topcoder_global.properties to make use of the provided jar files in ./libs/*

Before running the test, please make sure you edit ./test_files/informix.properties to set the related Informix IDS server settings on your environment.

After editing informix.properties, please run 'ant update-config' to transfer your local configuration into the XML files in ./test_files/* ; this is accomplished in the provided 'build-override.xml'

Here are the following item that was fix by this submission

A) Changes:
1) src/java/main/*.java basically is checkstyle 5.3 clean except for some acceptable warning

2) src/java/tests/*.java have some many warnings that was existed and time consuming to fix.

3) all mockup in src\java\tests\com\cronos\onlinereview\phases\failuretests\mock\*.java now fully defined and Exception handling has been generalized to make the code shorter.

4) For all changes, please refer to included change logs in ./test_files/ChangeLogs/Final_Fix_ChangeLog.txt

B) Scorecard

Q1.1.1
	All TODO and FIXME tag removed and implemented

Q1.1.2
	HashMap<String, Object>() been use in mockup class, also in ../stresstests/FinalFixPhaseHandlerTest.java

Q1.1.3
	All Failure and Error has been fix.

Q3.1.2
	Most of the source base code are checkstyle checked.

Q4.1.1
	All those file that was previously modified and have a TCSASSEMBLER author now updated to TMALBONPH

Q6.1.1
	All test now run without Failure and Error.
