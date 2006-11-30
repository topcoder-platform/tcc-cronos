TO REVIEWERS,

This file describes the things you need to setup in order to run and test this submission.

-------------------------------
 1. Application Server Issues
-------------------------------

This component was developed using JBoss 4.0.4, as it is specified in the QA Environment
section in the requirements specification. JBoss is freely available at http://www.jboss.org

It is highly recommended that you use JBoss 4.0.4 to run and test this component, because
other application servers are not supported by this submission (there was no requirement
to support other application servers).


-----------------------------
 2. Setting up the Database
-----------------------------

This component was tested with SQL Server 2000 and PostgreSQL 8. If you do not have access
to a SQL Server 2000 (and greater) installation, you are encouraged to use PostgreSQL 8.
PostgreSQL is open-source and freely available at http://www.postgresql.org

Database setup steps:

- Execute one of the DDL files provided in the conf/ddl/ directory to setup the database
  tables. There are two DDL files, one for SQL Server 2000 (and greater) and one for
  PostgreSQL 8. 

- Edit the conf/ejb/*-ds.xml files as appropriate to match your database environment.
  You need to configure the DataSource JNDI name, the JDBC driver class name, the
  connection URI and the database username and password. There are two *-ds.xml files:
  conf/ejb/mssql-ds.xml for SQL Server 2000 (and greater), and conf/ejb/postgres-ds.xml
  for PostgreSQL 8. Currently, these *-ds.xml files are setup with the following database
  configuration:

      Host: localhost
      Database: topcoder
      User: tcsdeveloper
      Password: tcsdeveloper

   Make sure that the driver class name in the *-ds.xml files is correct. I have seen some SQL
   Server configurations use XXX.jdbc.sqlserver.XXX, while others (newer ones) XXX.sqlserver.jdbc.XXX
   (notice how 'sqlserver' and 'jdbc' are swapped around). Also, some older SQL Server JDBC drivers
   might use "jdbc:microsoft:sqlserver://XXX" for the connection URI, while the newer driver seems
   to use "jdbc:sqlserver://XXX" (note the omision of ":microsoft:").

- Place the *-ds.xml file corresponding to your DBMS (which you edited as described above) in
  the JBOSS_HOME/server/default/deploy/ directory, where JBOSS_HOME is the path to your
  JBoss 4.0.4 installation.

- Download the latest JDBC driver for your DBMS, and place the JDBC driver JAR in the
  JBOSS_HOME/server/default/lib/ directory, where JBOSS_HOME is the path to you JBoss 4.0.4
  installation.

  The JDBC driver for SQL Server may be downloaded at http://msdn.microsoft.com/data/ref/jdbc
  The JDBC driver for PostgreSQL 8 may be downloaded at http://jdbc.postgresql.org

- Edit the conf/dbconnectionfactory.xml to reflect your environment. You only need to specify
  the JNDI name to your database (as specified in the *-ds.xml file), and the JNP hostname and
  port (usually jnp://localhost:1099). Please leave the connection name as "topcoderDB", as the
  test configuration files for the DAO classes refer to that name.

Other database issues:

- If you are using SQL Server, please ensure that the QUOTED_IDENTIFIER option is set to ON.
  This is the default in SQL Server 2000 and possibly later versions as well. The QUOTED_IDENTIFIER
  option allows us to use the T-SQL keyword, user, as a table name by surrounding it in
  double-quotations like so: "user" (see conf/ddl/create_tables-sqlserver.sql). 

- When populating the database with test data, please ensure that it contains no user profiles
  with ID's in the range 9223372036854775760 to 9223372036854775807. Otherwise, clashes may
  occur while executing the unit tests, resulting in failed tests cases. Also ensure that there
  are no confirmation messages in the database with the addresses, "daounittest@topcodersoftware.com",
  "IDontExist", "me1@tc.com", "me2@tc.com", "me3@tc.com", "inthere@tc.com" and "notinthere@tc.com".
  To make things completely fail-safe, it is recommended that an empty database be used when running
  the test cases.


--------------------------
 3. Testing the Component
--------------------------

REQUIREMENTS:

In order to test the component, you need to download the binary releases of Cactus 1.7.2 for J2EE 1.3
(http://jakarta.apache.org/cactus) and Cargo 0.8 (http://cargo.codehaus.org). Cargo is used to start
and stop the JBoss 4 container from the build script, as Cactus currently does not support JBoss
4 in Ant.

When downloading Cargo, make sure you download both cargo-core-uberjar-0.8.jar and cargo-ant-0.8.jar.

Later versions of Cactus and Cargo may work as well, but this is cannot be ascertained.

OVERVIEW:

A cactified EAR file will be created in the build/ directory by the 'ant compile_tests' command.
The contents of the EAR file will looks this:

user_profile_persistence-tests-cactified.ear
 |--> META-INF
 |     |--> application.xml                                (from test_files/)
 |     |--> ...
 |
 |--> com/topcoder/util/config/ConfigManager.properties    (from test_files/)
 |
 |--> lib
 |     |--> *.jar                                          (dependency JAR files)
 |
 |--> cactus.war                                           (cactified WAR)
 |     |--> META-INF
 |     |     |--> *
 |     |--> WEB-INF
 |     |     |--> classes/com/orpheus/user/persistence/*   (compiled test cases)
 |     |     |--> lib/*.jar                                (Cactus and JUnit JAR files)
 |     |     |--> web.xml                                  (Web deployment descriptor from test_files/)
 |     |--> jndi.properties                                (JNDI properties for remote clients; from test_files/)
 |     |--> jspRedirector.jsp                              (Cactus test case redirector; automatically generated)
 |
 |--> user_profile_persistence.jar                         (the component JAR)
 |     |--> META-INF
 |     |     |--> ejb-jar.xml                              (EJB deployment descriptor from test_files/)
 |     |     |--> jboss.xml                                (JBoss deployment descriptor from test_files/)
 |     |     |--> *
 |     |--> com/orpheus/user/persistence/*                 (compiled component classes)
 |
 |--> test_conf
 |     |--> *.xml                                          (test configuration files from test_files/test_conf/)

The cactified WAR will contain all the test code, whether they be normal JUnit test cases, or Cactus
tests, as well as the Cactus and JUnit JAR's required to execute the tests.

To ensure that the component has access to to the component dependency JAR's in the EAR file, the
dependencies should be specified in the test_files/application.xml file. This has already been set-up,
but if you need to add more dependency JAR's, please edit that file as required.

Make sure the configuration in the test_files/jndi.properties is correct. This configuration is used
by the remote clients.

In order to execute the tests, simply execute 'ant test'. Do not start or stop JBoss manually. Cargo
will take care of that for you automatically from within the build script. The JBoss output will be
logged to the log/container-output.log file. If any errors occur while testing the component, please
check this log file to find out what happened.

While running the tests, you might note the following message being printed at the console.

    [cargo] Press Ctrl-C to stop the container...
    
This is normal. Please ignore this message, and DO NOT press Ctrl-C. This message indicates that the
container is running and that the test cases are being executed. It may take a while for the container
to start and for the test cases finish executing (approximately 1.5 to 2 minutes in total on my machine).

See section 4 in this README file for information on how to set-up Cactus and Cargo in the build script.

DEALING WITH TEST CONFIGURATION FILES:

If you need to access test configuration files within your test cases, put them somewhere in the
test_files/test_conf/ directory to ensure that they are included in the cactified EAR. To load the
test configuration files in the test code, reference them as "test_conf/XXX".

JNDI SETTINGS FOR REMOTE CLIENTS:

These are specified in test_files/jndi.properties files. Please edit if necessary to match your
environment.

PLAN B (VERY IMPORTANT!):

It seems that Cargo versions 0.8 and earlier contain a bug that prevents it from starting JBoss 4
correctly on some platforms. This was apparently fixed in version 0.9, which has not yet been released.
See the message the at the URL below for details.

http://article.gmane.org/gmane.comp.java.cargo.devel/2921

The message only mentions Solaris, but I got the error on Gentoo Linux i686 as well. Fortunately,
I did NOT get the error on Windows XP (which you are probably using) and Gentoo Linux AMD64.
If you do, however, get the error (there will be message in log/container-output.log about an
error in the jboss-service.xml file), please test the component as follows:

- Execute 'ant compile_tests' only (and not 'ant tests').
- Manually deploy the tests by copying 'build/user_logic_persistence-tests-cactified.ear' to
  'JBOSS_HOME/server/default/deploy/', where JBOSS_HOME is you JBoss installation path.
- Start JBoss
- Open a web browser, and go to the following link:  
   
  http://[hostname]:[port]/userlogicpersistence/ServletTestRunner?suite=com.orpheus.user.persistence.AllTests

  Replace '[hostname]' and '[port]' in the above link with the hostname and port JBoss is listening on,
  respectively. Ignore the scrolling messages in the JBoss console (unless they are unexpected errors,
  of course).
  
  The test results will be outputted as XML, with the test summary somewhere near the top like this:
  
  <testsuites>
  <testsuite name="com.orpheus.user.persistence.AllTests" tests="578" failures="0" errors="0" time="...">
  ...
  </testsuite>
  </testsuites>


------------------------------------
 4. Setting up the Ant Build Script
------------------------------------

A number of modifications need to be made to the standard build script provided with the
development distribution in order to compile and test this submission. You are encouranged to use
the already modified build script provided in the docs/ directory. The required modifications are
as follows:

- Download the Compression Utility (required by the Simple Cache component) from the TopCoder
  software catalog, and define a property for its JAR:

    <!-- TCS Dependencies -->
    ...
    <property name="compression_utility.version" value="2.0.1"/>
    <property name="compression_utility.jar.name" value="compression_utility.jar"/>
    <property name="compression_utility.path" value="compression_utility/${compression_utility.version}"/>
    <property name="compression_utility.jar" value="${tcs_libdir}/${compression_utility.path}/${compression_utility.jar.name}"/>
    ...

  You might also need the Memory Usage 1.0 component, as it is listed as a dependency in the Simple
  Cache component specification. I did not seem to need it while testing the component, but if you do,
  please download it from the TopCoder software catalog, define a property for it as follows:

    <!-- TCS Dependencies -->
    ...
    <property name="memory_usage.version" value="1.0"/>
    <property name="memory_usage.jar.name" value="memory_usage.jar"/>
    <property name="memory_usage.path" value="memory_usage/${memory_usage}"/>
    <property name="memory_usage.jar" value="${tcs_libdir}/${memory_usage.path}/${memory_usage.jar.name}"/>
    ...
    
  Note: You still need the JAR's of all the other TopCoder dependencies listed in the component specification.
  Compression Utility and Memory Usage are only mentioned here, because those components were not included
  in the development distribution.

- Define the JBoss J2EE server and client library properties:
 
    <!-- 3rd Party Dependencies  -->
    ...
    <property name="j2ee.jar" value="${ext_libdir}/jboss/4.0.4/server/default/lib/jboss-j2ee.jar"/>
    <property name="j2ee-client.jar" value="${ext_libdir}/jboss/4.0.4/client/jbossall-client.jar" />
    ...

- Set-up the "buildlibs" path to include the Compression Utility JAR, the J2EE server library and
  possibly the Memory Usage JAR. Do not add the J2EE client library.
    
     <path id="buildlibs">
        ...
        <pathelement location="${j2ee.jar}"/>
        ...
        <pathelement location="${compression_utility.jar}"/>
        <pathelement location="${memory_usage.jar}"/> <!-- ONLY if you really need it -->
    </path>

- Define the Cactus library property, create a "cactuslibs" path and define the cactus
  tasks:

    <!-- 3rd Party Dependencies  -->
    ...
    <property name="cactus.jar" value="${ext_libdir}/cactus/1.7.2/j2ee1.3/cactus-1.7.2.jar"/>
    ...

    <!-- Cactus libraries -->
    <path id="cactuslibs">
        <pathelement location="${ext_libdir}/cactus/1.7.2/j2ee1.3/aspectjrt-1.2.1.jar"/>
        <pathelement location="${ext_libdir}/cactus/1.7.2/j2ee1.3/cargo-0.5.jar"/>
        <pathelement location="${ext_libdir}/cactus/1.7.2/j2ee1.3/commons-httpclient-2.0.2.jar"/>
        <pathelement location="${ext_libdir}/cactus/1.7.2/j2ee1.3/commons-logging-1.0.4.jar"/>
        <pathelement location="${ext_libdir}/cactus/1.7.2/j2ee1.3/junit-3.8.1.jar"/>
    </path>

    <!-- Define the Cactus tasks -->
    <taskdef resource="cactus.tasks">
        <classpath>
            <pathelement location="${cactus.jar}"/>
            <pathelement location="${ext_libdir}/cactus/1.7.2/j2ee1.3/cactus-ant-1.7.2.jar"/>
            <path refid="cactuslibs"/>
        </classpath>
    </taskdef>

- Add the Cactus JAR to the test.build.classpath path:
    
    <path id="test.build.classpath">
        ...
        <pathelement location="${cactus.jar}"/>
        ...
    </path>

- Define the Cargo tasks:
    
    <!-- Define the Cargo tasks -->
    <taskdef resource="cargo.tasks">
        <classpath>
            <pathelement location="${ext_libdir}/cargo/0.8/cargo-core-uberjar-0.8.jar"/>
            <pathelement location="${ext_libdir}/cargo/0.8/cargo-ant-0.8.jar"/>
        </classpath>
    </taskdef>

- Modify the "dist" target so that the test_files/ejb-jar.xml and test_files/jboss.xml files are put in
  the META-INF directory of the component JAR.
    
    <target name="dist" depends="compile">
        <mkdir dir="${dist_lib}/${component_path}"/>
        <jar jarfile="${component.jar}" basedir="${build_classdir}">
            <metainf dir="${testfiles}" includes="ejb-jar.xml, jboss.xml"/>
        </jar>
    </target>

- Change the "compile_tests" target to the following. Make sure that the target copies all the dependency
  JAR's to build/lib.

    <target name="compile_tests" depends="compile, dist">
        <mkdir dir="${build_testclassdir}"/>
        <javac srcdir="${javatests}" destdir="${build_testclassdir}" includes="${packagedir}/**" debug="true" verbose="${verbose}">
            <classpath refid="test.build.classpath" />
        </javac>
        
        <!-- Copy the dependency JAR's to build/lib -->
        <mkdir dir="${builddir}/lib"/>
        <copy file="${configmanager.jar}" todir="${builddir}/lib"/>
        <copy file="${base_exception.jar}" todir="${builddir}/lib"/>
        <copy file="${object_factory.jar}" todir="${builddir}/lib"/>
        <copy file="${simple_cache.jar}" todir="${builddir}/lib"/>
        <copy file="${logging_wrapper.jar}" todir="${builddir}/lib"/>
        <copy file="${user_profile.jar}" todir="${builddir}/lib"/>
        <copy file="${user_profile_manager.jar}" todir="${builddir}/lib"/>
        <copy file="${email_confirmation.jar}" todir="${builddir}/lib"/>
        <copy file="${db_connection_factory.jar}" todir="${builddir}/lib"/>
        <copy file="${typesafe_enum.jar}" todir="${builddir}/lib"/>
        <copy file="${email_address_validator.jar}" todir="${builddir}/lib"/>
        <copy file="${random_string_generator.jar}" todir="${builddir}/lib"/>
        <copy file="${data_validation.jar}" todir="${builddir}/lib"/>
        <copy file="${generic_event_manager.jar}" todir="${builddir}/lib"/>
        <copy file="${id_generator.jar}" todir="${builddir}/lib"/>
        <copy file="${compression_utility.jar}" todir="${builddir}/lib"/>
        ...

        <!--
          Create the component EAR file containing the component JAR, the
          dependency JAR's, and the configuration files in test_files/test_conf/ 
        -->
        <ear earfile="${builddir}/${distfilename}-tests.ear"
             appxml="${testfiles}/application.xml">
            <fileset file="${component.jar}"/>
            <fileset dir="${builddir}">
                <filename name="lib/*.jar"/>
            </fileset>
            <fileset dir="${testfiles}">
                <filename name="com/**"/>
            </fileset>
            <fileset dir="${testfiles}">
                <filename name="test_conf/**"/>
            </fileset>
        </ear>

        <!--
           Cactify the EAR by adding a Cactus WAR containing all the component
           test cases and test configuration
         -->
        <cactifyear srcfile="${builddir}/${distfilename}-tests.ear"
                    destfile="${builddir}/${distfilename}-tests-cactified.ear">
            <cactuswar version="2.3" context="userlogicpersistence" mergewebxml="${testfiles}/web.xml">
                <classes dir="${build_testclassdir}"/>
                <fileset file="${testfiles}/jndi.properties"/> 
             </cactuswar>
        </cactifyear>
    </target>

- Define the following Cargo container configuration properties, and configure as appropriate.
  The "container.name" property must be "jboss4x" if you are using JBoss 4. The "container.port"
  is the port the container uses, while "container.jnp.port" is the JNP listening port (usually 1099).
    
    <!-- Container configuration -->
    <property name="container.name" value="jboss4x"/>
    <property name="container.port" value="12388"/>
    <property name="container.jnp.port" value="1099"/>
    <property name="container.hostname" value="localhost"/>
    <property name="container.installdir" value="${ext_libdir}/jboss/4.0.4"/>
    <property name="container.log" value="${testlogdir}/container-output.log"/>

- Change the "test" task to the following:

    <target name="test" depends="compile_tests">
        <mkdir dir="${testlogdir}"/>
        <cactus earfile="${builddir}/${distfilename}-tests-cactified.ear" fork="yes" haltonerror="off">
            <classpath>
		    <path refid="test.build.classpath" />
                <path refid="cactuslibs" />
                <pathelement location="${j2ee-client.jar}"/>
            </classpath>
            <containerset>
                <generic name="${container.name}"
                         startuptarget="start_container" 
                         shutdowntarget="stop_container"
                         port="${container.port}" />
            </containerset>
            <formatter type="plain" usefile="true" />
            <formatter type="xml" usefile="true" />
            <test name="${package}.AllTests" todir="${testlogdir}" />
        </cactus>
    </target>

- Define the "start_container" and "stop_container" tasks that will be called by the "test" task:
    
    <!-- Start the container -->
    <target name="start_container">
        <delete dir="${builddir}/cargo" />
        <mkdir dir="${builddir}/cargo"/>
        <echo message="NOTE: Please ignore the 'Press Ctrl-C' message!"/>
        <echo message="Starting container..."/>
        <cargo containerId="${container.name}"
               home="${container.installdir}"
               output="${container.log}" 
               action="start">
         <configuration home="${builddir}/cargo">
            <property name="cargo.servlet.port" value="${container.port}"/>
            <property name="cargo.logging" value="high"/>
            <property name="cargo.rmi.port" value="${container.jnp.port}"/>
            <deployable type="ear" file="${builddir}/${distfilename}-tests-cactified.ear"/>
         </configuration>
        </cargo>
    </target>
    
    <!-- Stop the container -->    
    <target name="stop_container">
        <cargo containerId="${container.name}" home="${container.installdir}" action="stop">
         <configuration home="${builddir}/cargo">
            <property name="cargo.servlet.port" value="${container.port}"/>
            <property name="cargo.rmi.port" value="${container.jnp.port}"/>
         </configuration>
        </cargo>
    </target>

- FINAL NOTES:

  1) Make sure the "ext_libdir" property is pointing to the directory where Cactus, Cargo
     and JBoss are located/installed. This is normally done in topcoder_global.properties.
     Alternatively, manually configure the Cactus, Cargo and JBoss properties to point to
     paths matching your environment.
     
  2) Make sure that JUnit is accessible to Ant. Normally, the JUnit JAR should be put in
     the ANT_HOME/lib directory, where ANT_HOME is the path to your Ant installation.
  
  3) Make sure the "testfiles" is in the "buildlibs" path:
       
    <path id="buildlibs">
        ...
        <pathelement location="${testfiles}"/>
    </path>

---
Thank you for reading!
