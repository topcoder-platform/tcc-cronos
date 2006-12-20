
  Installation Requirements
  -------------------------
  Microsoft .NET Framework 1.1  <http://msdn.microsoft.com/netframework/productinfo>
  NAnt 0.84 (http://nant.sourceforge.net/)
  NDoc 1.3 (http://ndoc.sourceforge.net/)
  NUnit 2.1  <http://sourceforge.net/projects/nunit>
  NUnit2Report 1.2+ (http://nunit2report.sourceforge.net/)
  
  Note: The nunit bin and nant bin directories should be added to your path. 
        Also you will want to change the Nunit property to point to the 
        directory where your NUnit dll resides

        <property name="Nunit" value="C:\Program Files\NUnit V2.1\bin\nunit.framework.dll"/>
  
        To configure NUnitreport please follow the following directions:
        Copy the following files from the NUnitreport bin directory to the Nant 
        bin directory. 
            - NUnit-Frame.xsl 
            - NUnit-NoFrame.xsl 
            - toolkit.xsl 
            - i18n.xsl 
            - Traductions.xml 
            - NAnt.NUnit2ReportTasks.dll 


  Compilation Requirements
  ------------------------
  In addition to the general installation requirements, compiling the component
  from source requires Microsoft's IDL compiler (midl.exe), and its ActiveX and
  TLB packagers (aximp.exe and tlbimp.exe, respectively).  The former is
  available in the Microsoft Platform SDK, among other places, and the latter
  two in the Microsoft .Net SDK.

  
  TopCoder Software Environment Configuration
  -------------------------------------------
  TopCoder Software has defined a directory structure to promote component reuse
  and facilitate Component Based Development.
  
  Steps to setting up your environment:
  1- Designate a directory on your file system to be used as your TCS_HOME.
     (i.e. c:\tcs or /etc/home/user/tcs)        
  2- All TopCoder Software components are distributed with NAnt 
     based build scripts and NUnit (http://nunit.org) based test cases.  Please 
     properly install and configure these tools before working with TopCoder 
     Software components.
     Note: Be sure to change your Nunit reference (see requirements above)


  Component directory Structure
  ------------------------------------------
  The directory layout for each component is set up as follows:
  /build               - binary related files required to run the components
  /conf                - configuration data required by the component
  /docs                - component documentation
  /docs/java_docs      - contains javadoc formatted documentation
  /docs/xml            - xml documentation
  /docs/test_results   - NUnit test reports in html format
  /log                 - log files generated from test suite execution
  /src                 - source code for the component
  /test_files          - source code for the component test cases
 

  TopCoder Software NAnt Build Targets
  ------------------------------------------
  Each component comes with an NAnt build script with the following targets:
  compile         - compiles component source into the /build directory
  compile_tests   - compiles component test source code, depends on 'compile'
  test            - runs test cases and outputs nunit results into /log, 
                    depends on 'compile' and 'compile_test'
  nunitreport     - generates nunit reports into /reports, depends on 'test'
                    Must have nunit-console installed in your path.  This 
                    executable can be found in the NUnit bin directory.                    
  dist            - generates a binary distribution for deployment, depends on
                    'compile'
  clean           - deletes all compiled code in /build
  deploy          - depends on 'dist'
  main            - depends on 'deploy' and 'test'

  
  
  Thanks for using TopCoder Software components!
  
  The TopCoder Software Team
  service@topcodersoftware.com
  

 
