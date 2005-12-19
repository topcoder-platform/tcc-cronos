<?xml version="1.0" encoding="UTF-8" ?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output indent="yes" method="xml"/>
	<xsl:template match="component">
		<project>
			<xsl:attribute name="name">
				<xsl:value-of select="@name"/>
			</xsl:attribute>
			<xsl:attribute name="default">
				<xsl:text>dist</xsl:text>
			</xsl:attribute>
			<xsl:attribute name="basedir">
				<xsl:text>.</xsl:text>
			</xsl:attribute>
			<xsl:text>

			</xsl:text>
			<property file="../topcoder_global.properties"/>
			<xsl:text>

			</xsl:text>
			<xsl:comment>COMPONENT PARAMETERS</xsl:comment>
			<xsl:text>
			</xsl:text>
			<property>
				<xsl:attribute name="name"><xsl:text>component</xsl:text></xsl:attribute>
				<xsl:attribute name="value"><xsl:value-of select="translate(@name, ' ', '_')"/></xsl:attribute>
			</property>
			<xsl:text>
			</xsl:text>
			<property>
				<xsl:attribute name="name"><xsl:text>package</xsl:text></xsl:attribute>
				<xsl:attribute name="value"><xsl:value-of select="@package"/></xsl:attribute>
			</property>
			<xsl:text>
			</xsl:text>
			<property>
				<xsl:attribute name="name"><xsl:text>packagedir</xsl:text></xsl:attribute>
				<xsl:attribute name="value"><xsl:value-of select="translate(@package, '.', '/')"/></xsl:attribute>
			</property>
			<xsl:text>
			</xsl:text>
			<property>
				<xsl:attribute name="name"><xsl:text>distfilename</xsl:text></xsl:attribute>
				<xsl:attribute name="value"><xsl:value-of select="translate(translate(@name, ' ', '_'), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz')"/></xsl:attribute>
			</property>
			<xsl:text>
			</xsl:text>
			<property>
				<xsl:attribute name="name"><xsl:text>component_version</xsl:text></xsl:attribute>
				<xsl:attribute name="value"><xsl:value-of select="@version"/></xsl:attribute>
			</property>
			<xsl:text>
			</xsl:text>
			<property>
				<xsl:attribute name="name"><xsl:text>component_path</xsl:text></xsl:attribute>
				<xsl:attribute name="value"><xsl:text>${distfilename}/${component_version}</xsl:text></xsl:attribute>
			</property>
			<xsl:text>

			</xsl:text>
			<xsl:comment> DIRECTORY SETUP </xsl:comment>
			<xsl:text>
			</xsl:text>
			<property>
				<xsl:attribute name="name"><xsl:text>srcdir</xsl:text></xsl:attribute>
				<xsl:attribute name="value"><xsl:text>src</xsl:text></xsl:attribute>
			</property>
			<xsl:text>
			</xsl:text>
			<property>
				<xsl:attribute name="name"><xsl:text>tcs_libdir</xsl:text></xsl:attribute>
				<xsl:attribute name="value"><xsl:text>lib/tcs</xsl:text></xsl:attribute>
			</property>
			<xsl:text>
			</xsl:text>
			<property>
				<xsl:attribute name="name"><xsl:text>ext_libdir</xsl:text></xsl:attribute>
				<xsl:attribute name="value"><xsl:text>lib</xsl:text></xsl:attribute>
			</property>
			<xsl:text>
			</xsl:text>
			<property>
				<xsl:attribute name="name"><xsl:text>jar_tcs_libdir</xsl:text></xsl:attribute>
				<xsl:attribute name="value"><xsl:text>lib/tcs</xsl:text></xsl:attribute>
			</property>
			<xsl:text>
			</xsl:text>
			<property>
				<xsl:attribute name="name"><xsl:text>jar_ext_libdir</xsl:text></xsl:attribute>
				<xsl:attribute name="value"><xsl:text>lib</xsl:text></xsl:attribute>
			</property>
			<xsl:text>
			</xsl:text>
			<property>
				<xsl:attribute name="name"><xsl:text>docsdir</xsl:text></xsl:attribute>
				<xsl:attribute name="value"><xsl:text>docs</xsl:text></xsl:attribute>
			</property>
			<xsl:text>
			</xsl:text>
			<property>
				<xsl:attribute name="name"><xsl:text>configdir</xsl:text></xsl:attribute>
				<xsl:attribute name="value"><xsl:text>conf</xsl:text></xsl:attribute>
			</property>
			<xsl:text>
			</xsl:text>
			<property>
				<xsl:attribute name="name"><xsl:text>testlogdir</xsl:text></xsl:attribute>
				<xsl:attribute name="value"><xsl:text>log</xsl:text></xsl:attribute>
			</property>
			<xsl:text>
			</xsl:text>
			<property>
				<xsl:attribute name="name"><xsl:text>testfiles</xsl:text></xsl:attribute>
				<xsl:attribute name="value"><xsl:text>test_files</xsl:text></xsl:attribute>
			</property>
			<xsl:text>
			</xsl:text>
			<property>
				<xsl:attribute name="name"><xsl:text>javadocsdir</xsl:text></xsl:attribute>
				<xsl:attribute name="value"><xsl:text>${docsdir}/javadocs</xsl:text></xsl:attribute>
			</property>
			<xsl:text>
			</xsl:text>
			<property>
				<xsl:attribute name="name"><xsl:text>webdir</xsl:text></xsl:attribute>
				<xsl:attribute name="value"><xsl:text>web</xsl:text></xsl:attribute>
			</property>
			<xsl:text>
			</xsl:text>
			<property>
				<xsl:attribute name="name"><xsl:text>reports</xsl:text></xsl:attribute>
				<xsl:attribute name="value"><xsl:text>reports</xsl:text></xsl:attribute>
			</property>
			<xsl:text>

			</xsl:text>
			<property>
				<xsl:attribute name="name"><xsl:text>javasrc</xsl:text></xsl:attribute>
				<xsl:attribute name="value"><xsl:text>${srcdir}/java</xsl:text></xsl:attribute>
			</property>
			<xsl:text>
			</xsl:text>
			<property>
				<xsl:attribute name="name"><xsl:text>javamain</xsl:text></xsl:attribute>
				<xsl:attribute name="value"><xsl:text>${javasrc}/main</xsl:text></xsl:attribute>
			</property>
			<xsl:text>
			</xsl:text>
			<property>
				<xsl:attribute name="name"><xsl:text>javatests</xsl:text></xsl:attribute>
				<xsl:attribute name="value"><xsl:text>${javasrc}/tests</xsl:text></xsl:attribute>
			</property>
			<xsl:text>

			</xsl:text>
			<property>
				<xsl:attribute name="name"><xsl:text>builddir</xsl:text></xsl:attribute>
				<xsl:attribute name="value"><xsl:text>build</xsl:text></xsl:attribute>
			</property>
			<xsl:text>
			</xsl:text>
			<property>
				<xsl:attribute name="name"><xsl:text>build_classdir</xsl:text></xsl:attribute>
				<xsl:attribute name="value"><xsl:text>${builddir}/classes</xsl:text></xsl:attribute>
			</property>
			<xsl:text>
			</xsl:text>
			<property>
				<xsl:attribute name="name"><xsl:text>build_testclassdir</xsl:text></xsl:attribute>
				<xsl:attribute name="value"><xsl:text>${builddir}/testClasses</xsl:text></xsl:attribute>
			</property>
			<xsl:text>
			</xsl:text>
			<property>
				<xsl:attribute name="name"><xsl:text>build_targetclassdir</xsl:text></xsl:attribute>
				<xsl:attribute name="value"><xsl:text>${builddir}/targetclasses</xsl:text></xsl:attribute>
			</property>
			<xsl:text>
			</xsl:text>
			<property>
				<xsl:attribute name="name"><xsl:text>build_distdir</xsl:text></xsl:attribute>
				<xsl:attribute name="value"><xsl:text>${builddir}/dist</xsl:text></xsl:attribute>
			</property>
			<xsl:text>
			</xsl:text>
			<property>
				<xsl:attribute name="name"><xsl:text>build_docsdir</xsl:text></xsl:attribute>
				<xsl:attribute name="value"><xsl:text>${builddir}/${docsdir}</xsl:text></xsl:attribute>
			</property>
			<xsl:text>
			</xsl:text>
			<property>
				<xsl:attribute name="name"><xsl:text>build_javadocsdir</xsl:text></xsl:attribute>
				<xsl:attribute name="value"><xsl:text>${builddir}/${javadocsdir}</xsl:text></xsl:attribute>
			</property>
			<xsl:text>
			</xsl:text>
			<property>
				<xsl:attribute name="name"><xsl:text>build_webdir</xsl:text></xsl:attribute>
				<xsl:attribute name="value"><xsl:text>${builddir}/${webdir}</xsl:text></xsl:attribute>
			</property>
			<xsl:text>
			</xsl:text>
			<property>
				<xsl:attribute name="name"><xsl:text>build_lib</xsl:text></xsl:attribute>
				<xsl:attribute name="value"><xsl:text>${builddir}/lib</xsl:text></xsl:attribute>
			</property>
			<xsl:text>
			</xsl:text>
			<property>
				<xsl:attribute name="name"><xsl:text>build_tcsdistdir</xsl:text></xsl:attribute>
				<xsl:attribute name="value"><xsl:text>${build_distdir}/${distfilename}-${component_version}</xsl:text></xsl:attribute>
			</property>
			<xsl:text>

			</xsl:text>
			<xsl:comment> EXECUTION TAGS </xsl:comment>
			<xsl:text>
			</xsl:text>
			<property>
				<xsl:attribute name="name"><xsl:text>debug</xsl:text></xsl:attribute>
				<xsl:attribute name="value"><xsl:text>off</xsl:text></xsl:attribute>
			</property>
			<xsl:text>
			</xsl:text>
			<property>
				<xsl:attribute name="name"><xsl:text>verbose</xsl:text></xsl:attribute>
				<xsl:attribute name="value"><xsl:text>no</xsl:text></xsl:attribute>
			</property>
			<xsl:text>

			</xsl:text>
			<xsl:comment> COMPONENT DISTRIBUTION STRUCTURE </xsl:comment>
			<xsl:text>
			</xsl:text>
			<property>
				<xsl:attribute name="name"><xsl:text>dist_conf</xsl:text></xsl:attribute>
				<xsl:attribute name="value"><xsl:text>${build_tcsdistdir}/${configdir}</xsl:text></xsl:attribute>
			</property>
			<xsl:text>
			</xsl:text>
			<property>
				<xsl:attribute name="name"><xsl:text>dist_lib</xsl:text></xsl:attribute>
				<xsl:attribute name="value"><xsl:text>${build_distdir}/lib/tcs</xsl:text></xsl:attribute>
			</property>
			<xsl:text>
			</xsl:text>
			<property>
				<xsl:attribute name="name"><xsl:text>dist_src</xsl:text></xsl:attribute>
				<xsl:attribute name="value"><xsl:text>${build_tcsdistdir}/${srcdir}</xsl:text></xsl:attribute>
			</property>
			<xsl:text>
			</xsl:text>
			<property>
				<xsl:attribute name="name"><xsl:text>dist_docs</xsl:text></xsl:attribute>
				<xsl:attribute name="value"><xsl:text>${build_tcsdistdir}/${docsdir}</xsl:text></xsl:attribute>
			</property>
			<xsl:text>
			</xsl:text>
			<property>
				<xsl:attribute name="name"><xsl:text>dist_javadocs</xsl:text></xsl:attribute>
				<xsl:attribute name="value"><xsl:text>${build_tcsdistdir}/${javadocsdir}</xsl:text></xsl:attribute>
			</property>
			<xsl:text>
			</xsl:text>
			<property>
				<xsl:attribute name="name"><xsl:text>dist_testfiles</xsl:text></xsl:attribute>
				<xsl:attribute name="value"><xsl:text>${build_tcsdistdir}/${testfiles}</xsl:text></xsl:attribute>
			</property>
			<xsl:text>

			</xsl:text>
			<xsl:comment> NAME FOR .JAR AND .WAR FILES </xsl:comment>
			<xsl:text>
			</xsl:text>
			<property>
				<xsl:attribute name="name"><xsl:text>component.jar</xsl:text></xsl:attribute>
				<xsl:attribute name="value"><xsl:text>${dist_lib}/${component_path}/${distfilename}.jar</xsl:text></xsl:attribute>
			</property>
			<xsl:text>
			</xsl:text>
			<property>
				<xsl:attribute name="name"><xsl:text>javadoc.jar</xsl:text></xsl:attribute>
				<xsl:attribute name="value"><xsl:text>javadocs.jar</xsl:text></xsl:attribute>
			</property>
			<xsl:text>
			</xsl:text>
			<property>
				<xsl:attribute name="name"><xsl:text>component.tests.jar</xsl:text></xsl:attribute>
				<xsl:attribute name="value"><xsl:text>${dist_lib}/${distfilename}_tests.jar</xsl:text></xsl:attribute>
			</property>
			<xsl:text>
			</xsl:text>
			<property>
				<xsl:attribute name="name"><xsl:text>component.war</xsl:text></xsl:attribute>
				<xsl:attribute name="value"><xsl:text>${dist_examples}/${distfilename}.war</xsl:text></xsl:attribute>
			</property>
			<xsl:text>
			</xsl:text>
			<property>
				<xsl:attribute name="name"><xsl:text>component.dist.jar</xsl:text></xsl:attribute>
				<xsl:attribute name="value"><xsl:text>${build_distdir}/${distfilename}-${component_version}.jar</xsl:text></xsl:attribute>
			</property>
			<xsl:text>
			</xsl:text>
			<property>
				<xsl:attribute name="name"><xsl:text>dev_submission.jar</xsl:text></xsl:attribute>
				<xsl:attribute name="value"><xsl:text>${component}_${component_version}_dev_submission.jar</xsl:text></xsl:attribute>
			</property>
			<xsl:text>
			</xsl:text>
			<property>
				<xsl:attribute name="name"><xsl:text>design_submission.jar</xsl:text></xsl:attribute>
				<xsl:attribute name="value"><xsl:text>${component}_${component_version}_design_submission.jar</xsl:text></xsl:attribute>
			</property>
			<xsl:text>
			</xsl:text>
			<property>
				<xsl:attribute name="name"><xsl:text>dev_dist.jar</xsl:text></xsl:attribute>
				<xsl:attribute name="value"><xsl:text>${component}_${component_version}_dev_dist.jar</xsl:text></xsl:attribute>
			</property>
			<xsl:text>
			</xsl:text>
			<property>
				<xsl:attribute name="name"><xsl:text>design_dist.jar</xsl:text></xsl:attribute>
				<xsl:attribute name="value"><xsl:text>${component}_${component_version}_design_dist.jar</xsl:text></xsl:attribute>
			</property>
			<xsl:text>

			</xsl:text>
			<xsl:comment> DOCUMENT PACKAGE </xsl:comment>
			<xsl:text>
			</xsl:text>
			<property>
				<xsl:attribute name="name"><xsl:text>dist_docpackage</xsl:text></xsl:attribute>
				<xsl:attribute name="value"><xsl:text>${builddir}/doc_package</xsl:text></xsl:attribute>
			</property>
			<xsl:text>
			</xsl:text>
			<property>
				<xsl:attribute name="name"><xsl:text>docpackage.jar</xsl:text></xsl:attribute>
				<xsl:attribute name="value"><xsl:text>${distfilename}_docs.jar</xsl:text></xsl:attribute>
			</property>
			<xsl:text>

			</xsl:text>
			<xsl:comment> Java Locations </xsl:comment>
			<xsl:text>
			</xsl:text>
	    <property>
	    	<xsl:attribute name="name"><xsl:text>java_1_3_bootclasspath</xsl:text></xsl:attribute>
				<xsl:attribute name="value"><xsl:text>c:\program files\JavaSoft\JRE\1.3.1\lib\rt.jar</xsl:text></xsl:attribute>
	    </property>
			<xsl:text>

			</xsl:text>
			<xsl:comment> TCS Dependencies </xsl:comment>
			<xsl:apply-templates select="dependencies"/>
			<xsl:text>

			</xsl:text>
			<xsl:comment> 3rd Party Dependencies </xsl:comment>						
			<xsl:apply-templates select="external_dependencies"/>

			<xsl:text>

			</xsl:text>
			<path>
				<xsl:attribute name="id"><xsl:text>buildlibs</xsl:text></xsl:attribute>
				<xsl:apply-templates select="dependencies/dependent_component"/>
				<xsl:apply-templates select="external_dependencies/external_dependency"/>
			<xsl:text>
			</xsl:text>
			</path>

			<!-- Copy the rest of build.xml -->
			
		
    	<xsl:text disable-output-escaping="yes"><![CDATA[ 
    		
	    <path id="test.build.classpath">
	        <pathelement location="${build_testclassdir}"/>
	        <pathelement location="${build_classdir}"/>
	        <path refid="buildlibs"/>
	    </path>
	
	    <path id="runtime.classpath">
	        <pathelement location="${build_classdir}"/>
	        <path refid="buildlibs"/>
	    </path>
	
	    <target name="compile">
	        <mkdir dir="${build_classdir}"/>
	        <javac srcdir="${javamain}" destdir="${build_classdir}" includes="${packagedir}/**" debug="true" verbose="${verbose}">
	            <classpath refid="buildlibs" />
	        </javac>
	    </target>
	
	    <target name="compile_targets">
	        <!-- test compile against 1.3 -->
	        <mkdir dir="${build_targetclassdir}"/>
	        <mkdir dir="${javatests}"/>
	        <javac srcdir="${javamain}"
	               destdir="${build_targetclassdir}"
	               includes="${packagedir}/**"
		       debug="true"
	               verbose="${verbose}"
	               target="1.3"
	               bootclasspath="${java_1_3_bootclasspath}"
	               extdirs=""
	               >
	            <classpath refid="buildlibs" />
	        </javac>
	
	        <!-- compile test cases -->
	        <javac srcdir="${javatests}"
	               destdir="${build_targetclassdir}"
	               includes="${packagedir}/**"
		       debug="true"
	               verbose="${verbose}"
	               target="1.3"
	               bootclasspath="${java_1_3_bootclasspath}"
	               extdirs=""
	               >
	            <classpath refid="buildlibs" />
	        </javac>
	        <delete dir="${build_targetclassdir}"/>
	    </target>
	
	    <target name="compile_tests" depends="compile">
	        <mkdir dir="${build_testclassdir}"/>
	        <javac srcdir="${javatests}" destdir="${build_testclassdir}" includes="${packagedir}/**" debug="true" verbose="${verbose}">
	            <classpath refid="test.build.classpath" />
	        </javac>
	    </target>
	
	    <target name="test" depends="compile_tests">
	        <mkdir dir="${testlogdir}"/>
	        <junit fork="true" haltonerror="false">
	            <classpath refid="test.build.classpath"/>
	            <test name="${package}.AllTests" todir="${testlogdir}">
	                <formatter type="plain" usefile="true"/>
	                <formatter type="xml" usefile="true"/>
	            </test>
	        </junit>
	    </target>
	
	    <target name="reports" depends="test">
	        <mkdir dir="${reports}"/>
	
	        <junitreport todir="${reports}">
	          <fileset dir="${testlogdir}">
	            <include name="*.xml"/>
	          </fileset>
	          <report format="frames" todir="${reports}"/>
	        </junitreport>
	        
	        <echo>The execution of reports is complete.  Reports are available in /${reports}</echo>
	    </target>
	
	    <target name="dist" depends="compile">
	        <mkdir dir="${dist_lib}/${component_path}"/>
	        <jar jarfile="${component.jar}" basedir="${build_classdir}" />
	    </target>
	
	    <target name="dist_tests" depends="compile_tests">
	        <mkdir dir="${dist_lib}"/>
	        <jar jarfile="${component.tests.jar}" basedir="${build_testclassdir}" />
	    </target>
	
	    <target name="javadoc" depends="compile">
	        <mkdir dir="${dist_javadocs}" />
	        <javadoc packagenames="${package}.*"
	            sourcepath="${javamain}"
	            classpath="${build_classdir}"
	            classpathref="buildlibs"
	            destdir="${dist_javadocs}"
	            windowtitle="Topcoder Software"
	            header="&lt;table border=0 cellpadding=0 cellspacing=2&gt;&lt;tr&gt;&lt;td&gt;&lt;font class=tcoder2&gt;&#091; &lt;/font&gt;&lt;font class=tcoder1&gt;TOP&lt;/font&gt;&lt;font class=tcoder2&gt;CODER &lt;/font&gt;&lt;font class=tcoder2&gt;&#093;&lt;/font&gt;&lt;/td&gt;&lt;td&gt;&lt;font class=tcoder4&gt;&#153;&lt;/font&gt;&lt;/td&gt;&lt;/tr&gt;&lt;tr&gt;&lt;td class=tcoder3 align=center&gt;&lt;font class=tcoder3&gt;SOFTWARE&lt;/font&gt;&lt;/td&gt;&lt;/tr&gt;&lt;/table&gt;"
	            footer="&lt;table border=0 cellpadding=0 cellspacing=2&gt;&lt;tr&gt;&lt;td&gt;&lt;font class=tcoder2&gt;&#091; &lt;/font&gt;&lt;font class=tcoder1&gt;TOP&lt;/font&gt;&lt;font class=tcoder2&gt;CODER &lt;/font&gt;&lt;font class=tcoder2&gt;&#093;&lt;/font&gt;&lt;/td&gt;&lt;td&gt;&lt;font class=tcoder4&gt;&#153;&lt;/font&gt;&lt;/td&gt;&lt;/tr&gt;&lt;tr&gt;&lt;td class=tcoder3 align=center&gt;&lt;font class=tcoder3&gt;SOFTWARE&lt;/font&gt;&lt;/td&gt;&lt;/tr&gt;&lt;/table&gt;"
	            bottom="&lt;font class=tcoder5&gt;Contact TopCoder Software at &lt;a href='http://www.topcodersoftware.com'&gt;www.topcodersoftware.com&lt;/a&gt;&lt;/font&gt;"
	            stylesheetfile="${javadocsdir}/stylesheet.css"
	            verbose="${verbose}">
	            <tag name="copyright" description="Copyright:" scope="types"/>
			</javadoc>
	    </target>
	
	    <target name="clean">
	        <delete dir="${builddir}"/>
	    </target>
	
	    <target name="deploy" depends="dist">
	    </target>
	
	    <target name="main" depends="deploy,test">
	    </target>
	
	    <!-- ************************************************************************** -->
	    <!-- ************ REMOVE EVERYTHING BELOW HERE FOR THE DISTRIBUTION ************* -->
	    <!-- ************************************************************************** -->
	
	    <target name="dist_tcs" depends="clean,dist,javadoc">
	        <!-- define tcs distribution format -->
	<!-- uncomment if configuration files exist.
	        <mkdir dir="${dist_conf}"/>
	        <copy todir="${dist_conf}">
	            <fileset dir="${configdir}" />
	        </copy>
	-->
	
	        <mkdir dir="${dist_src}"/>
	        <copy todir="${dist_src}">
	            <fileset dir="${srcdir}" />
	        </copy>
	
	        <copy todir="${dist_docs}" >
	            <fileset dir="${docsdir}">
	                <include name="${component}_Class_Diagram*"/>
	                <include name="${component}_Use_Case_Diagram*"/>
	                <include name="${component}_Sequence_Diagram*"/>
	                <include name="${component}_Requirements_Specification*"/>
	                <include name="${component}_Component_Specification*"/>
	                <include name="${component}.zargo"/>
	                <include name="${component}.zuml"/>
	            </fileset>
	        </copy>
	
	        <mkdir dir="${dist_javadocs}"/>
	        <copy todir="${dist_javadocs}">
	            <fileset dir="${javadocsdir}" />
	        </copy>
	
	        <antcall target="package_docs" />
	
	<!-- uncomment if test files exist.
	        <mkdir dir="${dist_testfiles}"/>
	        <copy todir="${dist_testfiles}">
	            <fileset dir="${testfiles}" />
	        </copy>
	-->
	        <copy file="build_dist.xml" tofile="${build_tcsdistdir}/build.xml" />
	        <copy file="README" tofile="${build_tcsdistdir}/README" />
	        <jar jarfile="${component.dist.jar}" basedir="${build_distdir}" excludes="*.jar"/>
	        
	        <antcall target="move_to_tcs"/>
	    </target>
	
	    <target name="package_docs" depends="javadoc">
	        <mkdir dir="${dist_docpackage}" />
	    
	        <jar jarfile="${dist_docpackage}/${javadoc.jar}" basedir="${javadocsdir}" />
	        
	        <copy todir="${dist_docpackage}" >
	            <fileset dir="${docsdir}">
	                <include name="${component}_Class_Diagram*"/>
	                <include name="${component}_Use_Case_Diagram*"/>
	                <include name="${component}_Sequence_Diagram*"/>
	                <include name="${component}_Requirements_Specification.pdf"/>
	                <include name="${component}_Component_Specification.pdf"/>
	            </fileset>
	        </copy>
	        
	        <jar jarfile="${builddir}/${docpackage.jar}" basedir="${dist_docpackage}" />
	    </target>
	    
	    <target name="move_to_tcs">
	        <mkdir dir="${tcs_libdir}/${component_path}"/>
	        <mkdir dir="${tcs_libdir}/${component_path}/dist"/>
	
	        <copy file="${component.jar}" todir="${tcs_libdir}/${component_path}"/>
	        <copy file="${component.dist.jar}" todir="${tcs_libdir}/${component_path}/dist"/>
	    </target>
	
	    <target name="dev_submission" depends="test">
	        <jar jarfile="${dev_submission.jar}"
	             basedir="."
	             includes="${configdir}/**,${javamain}/**/*.java,${javatests}/${packagedir}/**,${testlogdir}/**,${testfiles}/**,${docsdir}/**"
	             excludes="${javatests}/${packagedir}/AllTests.java,${javatests}/${packagedir}/stresstests/*,${javatests}/${packagedir}/failuretests/*,
	                       ${javatests}/${packagedir}/accuracytests/*"
	        />
	    </target>
	
	    <target name="design_submission">
	        <jar jarfile="${design_submission.jar}"
	             basedir="."
	             includes="${configdir}/**,${javamain}/**,${docsdir}/**,${testfiles}/**"
	        />
	    </target>
	
	    <target name="dev_dist">
	        <!-- copy TCS Dependencies -->
	        <!--
	        <mkdir dir="${jar_tcs_libdir}/${configmanager.path}"/>
	        <copy file="${configmanager.jar}" todir="${jar_tcs_libdir}/${configmanager.path}" />
	        -->
	        
	        <jar jarfile="${dev_dist.jar}"
	             basedir="."
	             includes="build.xml,
	                       ${configdir}/**,
	                       ${srcdir}/**,
	                       ${docsdir}/**,
	                       ${jar_tcs_libdir}/**,
	                       ${testfiles}/**"
	        />
	
	        <!--
	        <delete dir="${jar_tcs_libdir}"/>
	        -->
	    </target>
	
	    <target name="design_dist">
	        <jar jarfile="${design_dist.jar}"
	             basedir="."
	             includes="build.xml,
	                       ${configdir}/**,
	                       ${srcdir}/**,
	                       ${docsdir}/**,
	                       ${jar_tcs_libdir}/**,
	                       ${testfiles}/**"
	        />
	    </target>
	
	    <!-- ************************************************************************** -->
	    <!-- ************************ END REMOVE EVERYTHING ***************************** -->
	    <!-- ************************************************************************** -->
]]></xsl:text>			
		</project>
	</xsl:template>

	<xsl:template match="dependencies">
  	<xsl:for-each select="dependent_component">
  		<xsl:variable name="comp_name" select="translate(translate(@name, ' ', '_'), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz')"/>
  		<xsl:text>
			</xsl:text>
  		<property>
				<xsl:attribute name="name"><xsl:value-of select="concat($comp_name, '.version')"/></xsl:attribute>
				<xsl:attribute name="value"><xsl:value-of select="@version"/></xsl:attribute>
			</property>
			<xsl:text>
			</xsl:text>
			<property>
				<xsl:attribute name="name"><xsl:value-of select="concat($comp_name, '.jar.name')"/></xsl:attribute>
				<xsl:attribute name="value"><xsl:value-of select="concat($comp_name, '.jar')"/></xsl:attribute>
			</property>
			<xsl:text>
			</xsl:text>
			<property>
				<xsl:attribute name="name"><xsl:value-of select="concat($comp_name, '.path')"/></xsl:attribute>
				<xsl:attribute name="value"><xsl:value-of select="concat($comp_name, '/${', $comp_name, '.version}')"/></xsl:attribute>
			</property>
			<xsl:text>
			</xsl:text>
			<property>
				<xsl:attribute name="name"><xsl:value-of select="concat($comp_name, '.jar')"/></xsl:attribute>
				<xsl:attribute name="value"><xsl:value-of select="concat('${tcs_libdir}/${', $comp_name, '.path}/${', $comp_name, '.jar.name}')"/></xsl:attribute>
			</property>
			<xsl:text>
			</xsl:text>
  	</xsl:for-each>
	</xsl:template>

	<xsl:template match="external_dependencies">			
		<xsl:for-each select="external_dependency">
			<xsl:text>
			</xsl:text>
			<property>
				<xsl:attribute name="name"><xsl:value-of select="@filename"/></xsl:attribute>
				<xsl:attribute name="value"><xsl:value-of select="concat('${ext_libdir}/', @filename)"/></xsl:attribute>
			</property>
		</xsl:for-each>
	</xsl:template>


	<xsl:template match="dependencies/dependent_component">
		<xsl:variable name="comp_name" select="translate(translate(@name, ' ', '_'), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz')"/>
				<xsl:text>
				</xsl:text>
		<pathelement>
				<xsl:attribute name="location"><xsl:value-of select="concat('${', $comp_name, '.jar}')"/></xsl:attribute>
		</pathelement>
	</xsl:template>

	<xsl:template match="external_dependencies/external_dependency">
				<xsl:text>
				</xsl:text>
		<pathelement>
				<xsl:attribute name="location"><xsl:value-of select="concat('${', @filename, '}')"/></xsl:attribute>
		</pathelement>
	</xsl:template>

</xsl:stylesheet>
