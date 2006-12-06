The test cases are based on a Java applet. User should first copy the "applet/.java.policy" file to the home directory. This will allow the applet having full access to the Java system. After that, use Firefox to open "applet/Test.html". The test cases should run automatically. The test result can be seen in the log directory under the component root directory.

WARNING: After test, please make sure that ".java.policy" is deleted from your home directory. Otherwise, all Java applets can access your system completely. This is dangerous!

NOTE: Do not try to replace rss_generator.jar with the real RSS Generator 2.0 component. The provided jar file is mocked specially for the testing purpose.