1. Modify the the path for dependencies in "build.xml";
   Modify the jboss_home value in the "build.xml".

2. ifxjdbc, ifxjdbcx, ifxlang, ifxlsupp, ifxsqlj and ifxtools are libs of jdbc driver for informix,
   you can firstly install the informix jdbc driver, and find them under the lib directory.

3. junitejb is used for test.
   You can download it from http://sourceforge.net/projects/junitejb/

4. Make sure all the name of the dependencies should be the same as "test_files\ear\META-INF\application.xml"
   listed.
   For example, in application.xml the name of the log4j is "log4j.jar", but if you define the dependency like
   <property name="log4j.jar" value="${ext_libdir}/log4j/1.2.11/log4j-1.2.11.jar"/>,
   error would occur during the jboss deployment.

5. This component would use informix, make sure you select "Log" as "Buffered_log" while you creating the testing
   database. As buffered transaction logging is needed in this component.

6. Run the "create_db.sql" under folder "test_files/sql" to create tables;
   Run the "id_generator.sql" under folder "test_files/sql" to insert values into the id_sequences table, which
   would be used in the ID Generator component.

7. Modify the db connection setting in "test_files/DBConnectionFactory_Config.xml" and "test_files/accuracy/config.xml".

8. Before "ant test", you should run "ant deploy" first.
   In the deployment, the audit.ear would be created with your re-newed configure files; and it would be copied to the
   "JBOSS_HOME/server/default/deploy".
   (If the audit.ear has not been copied to the directory automatically, you should do it yourself.)
   After "ant deploy", you should run the jboss.

9. Then you can test the component. :)


NOTE:
The component can be run under both Java 1.4 and 1.5 as required in CS.
