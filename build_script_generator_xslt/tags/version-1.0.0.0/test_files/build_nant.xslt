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
			<tstamp verbose="true" pattern="dd-MM-yyyy" property="build.date" />
			<xsl:text>
			</xsl:text>
	    <sysinfo verbose="true"/>
			<xsl:text>

			</xsl:text>			
			<property>
				<xsl:attribute name="name"><xsl:text>component</xsl:text></xsl:attribute>
				<xsl:attribute name="value"><xsl:value-of select="translate(@name, ' ', '_')"/></xsl:attribute>
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
				<xsl:attribute name="name"><xsl:text>namespace</xsl:text></xsl:attribute>
				<xsl:attribute name="value"><xsl:value-of select="translate(@package, '.', '\')"/></xsl:attribute>
			</property>
			<xsl:text>
			</xsl:text>			
			<property>
				<xsl:attribute name="name"><xsl:text>namespace_file</xsl:text></xsl:attribute>
				<xsl:attribute name="value"><xsl:value-of select="@package"/></xsl:attribute>
			</property>
			<xsl:text>
			
			</xsl:text>			
			<property>
				<xsl:attribute name="name"><xsl:text>component_version</xsl:text></xsl:attribute>
				<xsl:attribute name="value"><xsl:value-of select="@version"/></xsl:attribute>
			</property>
			<xsl:text>

			</xsl:text>
			<xsl:comment> REFERENCE TO NUNIT </xsl:comment>
			<xsl:text>
			</xsl:text>
			<property value="D:\dev\NUnit V2.1\bin\nunit.framework.dll" name="Nunit"/>
			<xsl:text>
			</xsl:text>
    	<property value="D:\dev\NDoc 1.3\bin\net\1.1\NDocConsole.exe" name="NDoc"/>
    	<xsl:text>

			</xsl:text>
			<xsl:comment> REFERENCE TO NUNIT </xsl:comment>			
			<xsl:text>
			
			</xsl:text>
			<property value="Copyright (c) 2005, TopCoder, Inc. All rights reserved." name="copyright"/>
			<xsl:text>
			
			
			</xsl:text>
			<property value="true" name="debug"/>
			<xsl:text>
			</xsl:text>
    	<property value="true"  name="verbose"/>
    	<xsl:text>
			</xsl:text>
    	<property>
				<xsl:attribute name="name"><xsl:text>component_path</xsl:text></xsl:attribute>
				<xsl:attribute name="value"><xsl:text>${distfilename}\${component_version}</xsl:text></xsl:attribute>
			</property>
			<xsl:text>

			</xsl:text>
    	<xsl:comment> directories </xsl:comment>			
    	<xsl:text>    	
			</xsl:text>
    	<property value="build"  name="builddir"/>
    	<xsl:text>    	
			</xsl:text>
	    <property value="conf"  name="configdir"/>
	    <xsl:text>    	
			</xsl:text>
	    <property value="log"  name="testlogdir"/>
	    <xsl:text>    	
			</xsl:text>
	    <property value="test_files"  name="testfiles"/>
	    <xsl:text>    	
			</xsl:text>
	    <property value="..\tcs" name="tcs"/>
	    <xsl:text>    	
			</xsl:text>
	    <property>
				<xsl:attribute name="name"><xsl:text>ext_bin</xsl:text></xsl:attribute>
				<xsl:attribute name="value"><xsl:text>${tcs}\bin</xsl:text></xsl:attribute>
			</property>
	    <xsl:text>    	
			</xsl:text>
			<property>
				<xsl:attribute name="name"><xsl:text>tcs_bin</xsl:text></xsl:attribute>
				<xsl:attribute name="value"><xsl:text>${ext_bin}\tcs</xsl:text></xsl:attribute>
			</property>
	    <xsl:text>    	
	    
			</xsl:text>			
			<xsl:comment> NUNIT's output needs to go back to the root these next two properties should correspond </xsl:comment>						
			<xsl:text>			
			</xsl:text>			
			<property>
				<xsl:attribute name="name"><xsl:text>build_classdir</xsl:text></xsl:attribute>
				<xsl:attribute name="value"><xsl:text>${builddir}\classes</xsl:text></xsl:attribute>
			</property>
			<xsl:text>
			</xsl:text>
			<property>
				<xsl:attribute name="name"><xsl:text>root_from_build_classdir</xsl:text></xsl:attribute>
				<xsl:attribute name="value"><xsl:text>..\..</xsl:text></xsl:attribute>
			</property>
			<xsl:text>
			</xsl:text>
			<property>
				<xsl:attribute name="name"><xsl:text>TestOutputXml</xsl:text></xsl:attribute>
				<xsl:attribute name="value"><xsl:text>${testlogdir}\${namespace_file}.Test.dll-results.xml</xsl:text></xsl:attribute>
			</property>
			<xsl:text>
			</xsl:text>
			<property>
				<xsl:attribute name="name"><xsl:text>TestOutputText</xsl:text></xsl:attribute>
				<xsl:attribute name="value"><xsl:text>${testlogdir}\${namespace_file}.Test.dll-results.txt</xsl:text></xsl:attribute>
			</property>
			<xsl:text>
			
			</xsl:text>
			<xsl:comment> 3rd Party Dependencies </xsl:comment>
			<xsl:apply-templates select="external_dependencies"/>
			<xsl:text>

			</xsl:text>
			<xsl:comment> TCS Dependencies </xsl:comment>
			<xsl:apply-templates select="dependencies"/>					

			<!-- copy the rest of default.build here -->
			<xsl:text disable-output-escaping="yes"><![CDATA[ 
			
		<!--   ****************************************************  -->

    <property name="build_testclassdir" value="${builddir}\testClasses\" />
    <property name="build_distdir" value="${builddir}\dist" />
    <property name="build_tcsdistdir" value="${build_distdir}\${distfilename}-${component_version}" />
    <property name="build_docsdir" value="${builddir}\docs" />
    <property name="testdocsresult_dir" value="${build_docsdir}\testResults" />

    <property name="build_xmldocsdir" value="${build_docsdir}\xmldoc\" />
    <property name="build_javadocsdir" value="${build_docsdir}\javadocs" />
    <property name="build_msdndir" value="${build_docsdir}\msdn" />


    <property name="srcdir" value="src" />
    <property name="dotnetsrc" value="${srcdir}\csharp" />
    <property name="dotnetmain" value="${dotnetsrc}\main\${namespace}" />
    <property name="dotnettests" value="${dotnetsrc}\tests\${namespace}" />
    <property name="docsdir" value="docs" />



    <!-- Component distribution structure -->
    <property name="dist_conf" value="${build_tcsdistdir}\conf" />
    <property name="dist_test_files" value="${build_tcsdistdir}\test_files" />
    <property name="dist_bin" value="${build_distdir}\bin" />
    <property name="dist_examples" value="${build_tcsdistdir}\examples" />
    <property name="dist_src" value="${build_tcsdistdir}\src" />
    <property name="dist_docs" value="${build_tcsdistdir}\docs" />
    <property name="test_results" value="${dist_docs}\test_results" />
    <property name="java_docs" value="${dist_docs}\java_docs" />
    <property name="xml_docs" value="${dist_docs}\xml" />
    <property name="source_xml_docs" value="${xml_docs}\source" />
    <property name="test_xml_docs" value="${xml_docs}\tests" />
    <property name="component.zip" value="${dist_bin}\${component_path}\${distfilename}.zip"/>
    <property name="component_version_name.zip" value="${distfilename}-${component_version}.zip"/>
    <property name="component.dist.zip" value="${build_distdir}\${component_version_name.zip}"/>


    <property name="design_dist.zip" value="${distfilename}_${component_version}_design_dist.zip"/>
    <property name="dev_dist.zip" value="${distfilename}_${component_version}_dev_dist.zip"/>

    <!-- builds -->
    <property name="design_submission.zip" value="${distfilename}_${component_version}_design_submission.zip"/>
    <property name="dev_submission.zip" value="${distfilename}_${component_version}_dev_submission.zip"/>


    <target name="dist_docs">
        <mkdir dir="${source_xml_docs}"/>
        <mkdir dir="${test_xml_docs}"/>
        <mkdir dir="${test_results}"/>
        <mkdir dir="${java_docs}"/>
        <mkdir dir="${build_javadocsdir}"/>

        <copy file="${build_docsdir}\${namespace_file}.xml" todir="${source_xml_docs}"/>
        <copy file="${build_docsdir}\${namespace_file}.Test.xml" todir="${test_xml_docs}"/>

        <copy todir="${test_results}">
            <fileset basedir="${testdocsresult_dir}" defaultexcludes="true">
                <includes name="**/*"/>
                <excludes name="**/*.xml"/>
            </fileset>
        </copy>
        <copy todir="${java_docs}">
            <fileset basedir="${build_javadocsdir}" defaultexcludes="true">
                <includes name="**/*"/>
                <excludes name="**/*.xml"/>
            </fileset>
        </copy>

        <copy file="${build_msdndir}\${distfilename}.chm" todir="${dist_docs}"/>
    </target>
    
    <target name="compile" >
	    <mkdir dir="${build_classdir}"/>
	    <mkdir dir="${build_docsdir}"/>
		<csc target="library" output="${build_classdir}\${namespace_file}.dll" doc="${build_classdir}\${namespace_file}.xml" >
			<sources>
				<includes name="${dotnetmain}\**.cs"/>
			</sources>
			<references>
]]></xsl:text>			
				
				<!-- includes -->
				<xsl:for-each select="dependencies/dependent_component">
					<xsl:variable name="comp_name" select="translate(translate(@name, ' ', '_'), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz')"/>
  			<xsl:text>
				</xsl:text>			
					<includes>					
						<xsl:attribute name="name"><xsl:value-of select="concat($comp_name, '.dll')"/></xsl:attribute>
					</includes>
				</xsl:for-each>
				
				
				<xsl:for-each select="external_dependencies/external_dependency">
				<xsl:text>
				</xsl:text>
					<includes>
						<xsl:attribute name="name"><xsl:value-of select="@filename"/></xsl:attribute>						
					</includes>
				</xsl:for-each>
	
				
				
		<xsl:text disable-output-escaping="yes"><![CDATA[ 
		
			</references>
		</csc>
		<copy file="${build_classdir}\${namespace_file}.xml" todir="${build_docsdir}" />
		</target>

    <target name="clean">
        <mkdir dir="${builddir}"/>
        <mkdir dir="${dist_docs}"/>
			<delete verbose="${verbose}">
		        <fileset defaultexcludes="false" >
		            <includes name="${builddir}/**/**"/>
		            <includes name="*.zip"/>
		            <includes name="${dist_docs}/**.zip"/>
		        </fileset>
			</delete>
    </target>

    <target name="compile_tests" depends="compile">
        <mkdir dir="${build_testclassdir}"/>

        <echo message="Please make sure that Nunit 2.0 is installed."/>
        <echo message="The NUnit property is currently pointing to: ${Nunit}."/>
        <echo message="If this is incorrect please adjust the Nunit property to point to the correct location."/>

        <csc target="library" doc="${build_classdir}\${namespace_file}.Test.xml"
                              output="${build_classdir}\${namespace_file}.Test.dll"
                              failonerror="true">
            <sources>
            	<includes name="${dotnetmain}\**.cs" />
                <includes name="${dotnettests}\**.cs" />
            </sources>
            <references>
								<includes name="${Nunit}"/>
]]></xsl:text>

				<!-- includes -->
				<xsl:for-each select="dependencies/dependent_component">
					<xsl:variable name="comp_name" select="translate(translate(@name, ' ', '_'), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz')"/>
				  			<xsl:text>
								</xsl:text>			
					<includes>					
						<xsl:attribute name="name"><xsl:value-of select="concat($comp_name, '.dll')"/></xsl:attribute>
					</includes>
				</xsl:for-each>
				
				
				<xsl:for-each select="external_dependencies/external_dependency">
								<xsl:text>
								</xsl:text>
					<includes>
						<xsl:attribute name="name"><xsl:value-of select="@filename"/></xsl:attribute>						
					</includes>
				</xsl:for-each>

		<xsl:text disable-output-escaping="yes"><![CDATA[ 
						</references>
        </csc>
        <copy file="${build_classdir}\${namespace_file}.Test.xml" todir="${build_docsdir}" />
    </target>

    <target name="test" depends="compile_tests">
        <mkdir dir="${testlogdir}"/>
]]></xsl:text>
				
				<!-- copy files -->
				<xsl:for-each select="dependencies/dependent_component">
					<xsl:variable name="comp_name" select="translate(translate(@name, ' ', '_'), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz')"/>
	  			<xsl:text>
					</xsl:text>			
					<copy>					
						<xsl:attribute name="file"><xsl:value-of select="concat($comp_name, '.dll')"/></xsl:attribute>
						<xsl:attribute name="todir"><xsl:text>${build_classdir}</xsl:text></xsl:attribute>						
					</copy>
				</xsl:for-each>
				
				
		<xsl:text disable-output-escaping="yes"><![CDATA[ 
		
				<echo message="Please make sure that nunit-console is in your path."/>
        <echo message="This file can be found in the NUnit bin directory."/>

        <nunit2 verbose="true">
            <formatter type="Xml" usefile="true" outputdir="${testlogdir}" extension=".xml"/>
            <formatter type="Plain" usefile="true" outputdir="${testlogdir}" extension=".txt"/>
            <test assemblyname="${build_classdir}\${namespace_file}.Test.dll"/>
        </nunit2>

        <!--
            <echo message="nunit-console /assembly:${build_classdir}\${namespace_file}.Test.dll /xml:${root_from_build_classdir}\${TestOutput}"/>
            <exec program="nunit-console" commandline="/assembly:${build_classdir}\${namespace_file}.Test.dll /xml:${root_from_build_classdir}\${testlogdir}\${namespace_file}.TestResult.xml" />
        -->
    </target>

    <target name="dist" depends="compile_tests">
        <mkdir dir="${dist_bin}/${component_path}"/>
        <!--<copy file="${build_classdir}\${namespace_file}.dll" todir="${dist_bin}" />-->

        <zip zipfile="${component.zip}">
            <fileset basedir="${build_classdir}" defaultexcludes="true">
                <includes name="**/*.dll"/>
                <excludes name="**/*Test.dll"/>
            </fileset>
        </zip>
    </target>


    <target name="generate_docs" depends="javadoc,msdn" />


    <target name="deploy" depends="dist">
    </target>

    <target name="main" depends="deploy,test">
    </target>


	<target name="nunitreport" depends="test">
		<!-- By default the report is in english, format is noframes by default -->
		<mkdir dir="${testdocsresult_dir}"/>
		<nunit2report format="frames" opendesc="yes"  todir="${testdocsresult_dir}"  >
			<fileset>
				<includes name="${TestOutputXml}" />
			</fileset>
			<summaries>
				<includes name="${build_docsdir}\${namespace_file}.Test.xml" />
			</summaries>
		</nunit2report>

		<echo message="NUnit frame report generated."/>
	</target>

    <target name="javadoc" depends="compile_tests">
        <mkdir dir="${build_javadocsdir}"/>
        <echo message="Please make sure that NDoc is installed."/>
        <echo message="The NDoc console executable variable is currently pointing to: ${NDoc}."/>


        <exec program="${NDoc}" verbose="true" workingdir="." commandline="-verbose -documenter=JavaDoc -Title=${component}
                        -OutputDirectory=${build_javadocsdir}
                        ${build_classdir}\${namespace_file}.Test.dll,${build_classdir}\${namespace_file}.Test.xml" />
    </target>

    <target name="msdn" depends="compile_tests">
        <mkdir dir="${build_msdndir}"/>
        <echo message="Requires Microsoft's HTML Help Compiler (hhc.exe) is in your path."/>
        <ndoc >
            <assemblies>
                <includes name="${build_classdir}\${namespace_file}.Test.dll"/>
            </assemblies>
            <summaries >
                <includes name="${build_classdir}\${namespace_file}.Test.xml"/>
            </summaries>
            <documenters>
                <documenter name="MSDN">
                    <property name="OutputDirectory" value="${build_msdndir}" />
                    <property name="HtmlHelpName" value="${distfilename}" />
                    <property name="HtmlHelpCompilerFilename" value="hhc.exe" />
                    <property name="IncludeFavorites" value="False" />
                    <property name="Title" value="${component}" />
                    <property name="SplitTOCs" value="False" />
                    <property name="DefaulTOC" value="" />
                    <property name="ShowVisualBasic" value="False" />
                    <property name="ShowMissingSummaries" value="False" />
                    <property name="ShowMissingRemarks" value="False" />
                    <property name="ShowMissingParams" value="False" />
                    <property name="ShowMissingReturns" value="False" />
                    <property name="ShowMissingValues" value="False" />
                    <property name="DocumentInternals" value="False" />
                    <property name="DocumentProtected" value="False" />
                    <property name="DocumentPrivates" value="False" />
                    <property name="DocumentEmptyNamespaces" value="False" />
                    <property name="IncludeAssemblyVersion" value="True" />
                    <property name="CopyrightText" value="${copyright}" />
                    <property name="CopyrightHref" value="" />
                    <property name="AutoDocumentConstructors" value="True" />
                 </documenter>
            </documenters>
        </ndoc>
    </target>


    <!-- ************************************************************************** -->
    <!-- ************ REMOVE EVERYTHING BELOW HERE FOR THE DISTRIBUTION ************* -->
    <!-- ************************************************************************** -->


    <target name="design_dist">

        <zip zipfile="${design_dist.zip}"   >
            <fileset defaultexcludes="true">
                <includes name="${configdir}/**"/>
                <includes name="default.build"/>
                <includes name="${srcdir}/**"/>
                <includes name="${docsdir}/**"/>
                <includes name="${testfiles}/**"/>
            </fileset>
        </zip>
    </target>

    <target name="dev_dist">

        <zip zipfile="${dev_dist.zip}"   >
            <fileset defaultexcludes="true">
                <includes name="${configdir}/**"/>
                <includes name="default.build"/>
                <includes name="${srcdir}/**"/>
                <includes name="${docsdir}/**"/>
                <includes name="${testfiles}/**"/>
]]></xsl:text>
				<!-- includes -->
				<xsl:for-each select="dependencies/dependent_component">
					<xsl:variable name="comp_name" select="translate(translate(@name, ' ', '_'), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz')"/>
				  			<xsl:text>
								</xsl:text>			
					<includes>					
						<xsl:attribute name="name"><xsl:value-of select="concat($comp_name, '.dll')"/></xsl:attribute>
					</includes>
				</xsl:for-each>
		<xsl:text disable-output-escaping="yes"><![CDATA[ 
				    </fileset>
        </zip>
    </target>

    <target name="design_submission" >
        <zip zipfile="${design_submission.zip}"   >
            <fileset defaultexcludes="true">
                <includes name="${configdir}/**"/>
                <includes name="${srcdir}/**"/>
                <includes name="${docsdir}/**"/>
                <includes name="${testfiles}/**"/>
            </fileset>
        </zip>
    </target>

    <target name="dev_submission" depends="test">
        <zip zipfile="${dev_submission.zip}">
            <fileset defaultexcludes="true">
                <includes name="${configdir}/**"/>
                <includes name="${srcdir}/**"/>
                <includes name="${docsdir}/**" />
                <includes name="${testfiles}/**"/>
                <includes name="${testlogdir}/**" />
            </fileset>
        </zip>
    </target>

    <target name="dist_tcs" depends="create_dist">
        <mkdir dir="${tcs_bin}" />
        <mkdir dir="${tcs_bin}\${distfilename}" />
        <mkdir dir="${tcs_bin}\${distfilename}\${component_version}" />
        <mkdir dir="${tcs_bin}\${distfilename}\${component_version}\dist" />
        <copy file="${component.dist.zip}" todir="${tcs_bin}\${component_path}\dist" />
        <copy file="${build_classdir}\${namespace_file}.dll" todir="${tcs_bin}\${component_path}" />
    </target>

    <target name="create_dist" depends="generate_docs,dist,dist_docs">
        <!-- define tcs distribution format -->
        <mkdir dir="${dist_conf}"/>
        <mkdir dir="${dist_src}"/>
        <mkdir dir="${dist_test_files}"/>


        <copy todir="${dist_src}">
            <fileset basedir="${srcdir}" defaultexcludes="true">
                <includes name="**/*.cs"/>
            </fileset>
        </copy>
        <copy todir="${dist_conf}">
            <fileset basedir="${configdir}" defaultexcludes="true">
                <includes name="**/*"/>
            </fileset>
        </copy>
        <copy todir="${dist_test_files}">
            <fileset basedir="${testfiles}" defaultexcludes="true">
                <includes name="**/*"/>
            </fileset>
        </copy>

        <copy todir="${dist_docs}">
            <fileset basedir="${docsdir}" defaultexcludes="true">
                <includes name="${component}_Class_Diagram*"/>
                <includes name="${component}_Use_Case_Diagram*"/>
                <includes name="${component}_Sequence_Diagram*"/>
                <includes name="${component}_Requirements_Specification*"/>
                <includes name="${component}_Component_Specification*"/>
                <includes name="${component}.zargo"/>
                <includes name="${component}.zuml"/>
            </fileset>
        </copy>

        <copy file="default_dist.build" tofile="${build_tcsdistdir}/default.build" />
        <copy file="README.txt" tofile="${build_tcsdistdir}/README.txt" />

        <zip zipfile="${component.dist.zip}">
            <fileset basedir="${build_distdir}" defaultexcludes="true">
                <excludes name="${component_version_name.zip}"/>
                <includes name="**/*"/>
             </fileset>
        </zip>
    </target>


    <!-- ************************************************************************** -->
    <!-- ******************** END REMOVE EVERYTHING ******************************* -->
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
				<xsl:attribute name="name"><xsl:value-of select="concat($comp_name, '.dll')"/></xsl:attribute>
				<xsl:attribute name="value"><xsl:value-of select="concat('${tcs_bin}\', $comp_name, '\', @version, '\', @package, '.dll' )"/></xsl:attribute>
			</property>
			
  	</xsl:for-each>
	</xsl:template>

	<xsl:template match="external_dependencies">
		<xsl:for-each select="external_dependency">
			<xsl:text>
			</xsl:text>
			<property>
				<xsl:attribute name="name"><xsl:value-of select="@filename"/></xsl:attribute>
				<xsl:attribute name="value"><xsl:value-of select="concat('${ext_bin}\', @filename)"/></xsl:attribute>
			</property>
		</xsl:for-each>
	</xsl:template>
	
</xsl:stylesheet>
