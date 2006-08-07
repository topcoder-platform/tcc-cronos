To run the tests:
1. Include necessary jars, refer to "build.xml".
2. Do "ant test".

For assembly:
1. The developement scope of this component is "com.topcoder.management.phase.autopilot", other packages are stubs of developing components.
2. The stress tests do not have its own mock classes but ask some mock classes to run, and the developer has used some exact classes in dependency components ("com.topcoder.management.project.ProjectManagerImpl" and "com.topcoder.management.phase.DefaultPhaseManager") for mock purpose. This may cause problems in the assembling process, beware. See "test_files/StressTests/AutoPilot.xml" for the mock classes used.