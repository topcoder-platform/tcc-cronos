
1. Tests execution

Setting up the QA environment is a little bit complex, due to there are 3 files containing
database configuration (one for the IDGenerator, one for the EAR, one for the tests-suite), and
two EAR files (IDGenerator.ear and catalog_services.ear).

The IDGenerator.ear is necessary for Catalog Services component, as it provides
IDGenerator instance (which is used by Hibernate mapping, and without it catalog_services.ear won't start).

1. Add directory ./test_files to classpath.
2. Add libraries from JBoss 4.2 and ./test_files/lib to classpath. E.g.:

 <property file="./jboss.properties"/>

 <path id="jboss.classpath">
        <fileset dir="${jboss.home}/lib">
            <include name="**/*.jar"/>
        </fileset>
        <fileset dir="${jboss.home}/server/${jboss.server.config}/lib">
            <include name="**/*.jar"/>
        </fileset>
        <fileset dir="${jboss.home}/server/${jboss.server.config}/deploy/ejb3.deployer">
            <include name="*.jar"/>
        </fileset>
        <fileset dir="${jboss.home}/server/${jboss.server.config}/deploy/jboss-aop-jdk50.deployer">
            <include name="*.jar"/>
        </fileset>
 </path>

 <path id="tcs.classpath">
      <fileset dir="${tcs_libdir}">
          <include name="**/*.jar"/>
      </fileset>
 </path>

 <path id="buildlibs">
        <pathelement location="${junit.jar}"/>
        <path refid="jboss.classpath"/>
        <pathelement location="${configdir}"/>
        <pathelement location="${testfiles}"/>
        <path refid="tcs.classpath"/>
 </path>

jboss.properties can be like this:

jboss.home=/projects/sofware/jboss-4.2.0-GA
jboss.server.config=all

3. Execute ./test_files/sql/create.ddl against Informix database instance.
4. Execute ./test_files/sql/id_generator.sql against Informix database instance.
5. 
   Update ./test_files/META-INF/persistence.xml and 
    ./conf/IDGenerator/dbconnectionfactory.xml
    ./conf/informix-ds.xml
   according the QA environment's database properties.
   (you also can update these files inside 
  	test_files/build/ear/IDGenerator.ear/id_generator_ejb.jar/META-INF, 
        test_files/build/ear/catalog_services.ear/catalog_services_ejb.jar/META-INF)
   whether to rebuild id_generator.ear and catalog_services.ear files, with the following tasks:


    <target name="ejbjarIDGen" depends="distIDGen">
        <jar jarfile="${dist_lib}/${component_path}/IDGenerator.ear">
            <fileset dir="${dist_lib}/${component_path}">
                <include name="id_generator_ejb.jar"/>
            </fileset>
            <fileset dir="conf/IDGenerator">
                <include name="META-INF/application.xml"/>
            </fileset>
            <fileset dir="test_files/lib">
                <include name="base_exception.jar"/>
                <include name="configuration_manager.jar"/>
                <include name="configuration_api.jar"/>
                <include name="db_connection_factory.jar"/>
            </fileset>
        </jar>
        <copy file="${dist_lib}/${component_path}/IDGenerator.ear"
              todir="${jboss.home}/server/${jboss.server.config}/deploy"/>
    </target>

    <target name="ejbjar" depends="dist">
        <jar jarfile="${component.ear}">
            <fileset dir="${dist_lib}/${component_path}">
                <include name="${distfilename}_ejb.jar"/>
            </fileset>
            <fileset dir="conf/ComponentService">
                <include name="META-INF/application.xml"/>
                <include name="informix-ds.xml"/>
            </fileset>
            <fileset dir="test_files/lib">
                <include name="catalog_entities.jar"/>
                <include name="base_exception.jar"/>
                <include name="id_generator.jar"/>
            </fileset>
        </jar>
        <copy file="${component.ear}"
              todir="${jboss.home}/server/${jboss.server.config}/deploy"/>
    </target>

    <target name="distIDGen" depends="compile">
        <mkdir dir="${dist_lib}/${component_path}"/>
        <copy file="test_files/lib/id_generator.jar" tofile="${dist_lib}/${component_path}/id_generator_ejb.jar"/>
        <jar jarfile="${dist_lib}/${component_path}/id_generator_ejb.jar" update="true" basedir="conf/IDGenerator">
        </jar>
    </target>

    <target name="dist" depends="compile">
        <mkdir dir="${dist_lib}/${component_path}"/>
        <jar jarfile="${dist_lib}/${component_path}/${distfilename}_ejb.jar" basedir="${build_classdir}">
            <fileset dir="conf/ComponentService">
                <include name="META-INF/*.xml"/>
            </fileset>
        </jar>
    </target>

6. Copy Informix jdbc.jar library to ${jboss.home}/server/${jboss.server.config}/lib directory.
7. Copy IDGenerator.ear and component_service.ear files to
   ${jboss.home}/server/${jboss.server.config}/deploy directory (if you didn't use ejbjar and ejbjarIDGen tasks)
8. Start the JBoss
9. To execute tests against mockups use 'ant test' task (to build coverage report)
10. To execute tests againsts JBoss deployment use task without Cobertura instrumented classes on classpath,
    and with -Dtest.against.mocks=false property set. E.g.

   <target name="remotetest" depends="compile_tests">
        <mkdir dir="${testlogdir}"/>

        <junit fork="true" haltonerror="false" maxmemory="256m">
            <sysproperty key="test.against.mocks" value="false"/>
            <classpath location="./test_files"/>
            <classpath refid="test.build.classpath"/>
            <classpath location="${build_testclassdir}"/>

            <test name="${package}.AllTests" todir="${testlogdir}">
                <formatter type="plain" usefile="true"/>
                <formatter type="xml" usefile="true"/>
            </test>

        </junit>
    </target>
   

I attached my build.xml file in ./test_files/build which contains the tasks above.
May be you need to alter it to make working on your local environment (e.g. set correct cobertura classpath etc.)
This file to be executed supposed to be in the component base folder (not inside ./test_files/build)

----------------------------------------------------------------------------------------------------

2. The demo
The demo is in the DemoTest.java file - exactly as it's in the CS (which is updated),
but with assertion sections surrounded with appropriate comments (to check the Demo really works).
(also caru is added to authors of this class as the designer originated the Demo).

