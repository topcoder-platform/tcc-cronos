I've used JIRA 3.13 (standard and enterprise versions) for testing.

1. Run JIRA system.

2. Enable RPC plugin (General Configuration / Allow remote API calls).
Read details at http://confluence.atlassian.com/display/JIRA/Creating+a+SOAP+Client

3. Fetch WSDL file for your instance of JIRA.
Corresponding descriptor can be found at http://your_installation/rpc/soap/jirasoapservice-v2?wsdl
Save descriptor as local 'jirasoapservice-v2.wsdl' file.

4. Generate Java classes used to interact with remote service. We expect that Apache Axis
is utilized at this step. Original designer's suggestion was based on the demo SOAP client
distribution that can be taken at http://repository.atlassian.com/jira-rpc-samples/distributions.
This demo uses Axis 1.3 to generate Java classes.

I've examined newer versions of Axis toolkit:
    a) version 1.4  generates exactly the same code as version 1.3
    b) Apache Axis2 fails to generate sources due to the following bug with service's WSDL:
       http://jira.atlassian.com/browse/JRA-12152

So please use Axis 1.3 or Axis 1.4 to generate Java sources (I've used 1.4).

You can reuse my 'test_files/build-axis.xml' to generate and compile stubs:
    * create directory (for example call it axis)
    * copy 'jirasoapservice-v2.wsdl' file to this directory
    * copy 'build-axis.xml' to this directory
    * change path to AXIS_HOME directory and used URL in 'build-axis.xml' file
    * type 'ant -f build-axis.xml jar' 
Directory 'dist' will contain required file 'generated.jar' that you have to add to the classpath.

Note that I don't use atlassian-jira-rpc-plugin because it relies on JIRA server-side classes
(located in %JIRA_HOME%\atlassian-jira\WEB-INF\classes directory). Thus generated.jar also
contains com.atlassian.jira.rpc.* classes.

5. You need two users for testing. The first one will be used to check accuracy (he must
have rights to administer and browse projects) and the second will be used to check
authorization issues (he must NOT have rights to administer and browse projects).

I've created user 'root' who is member of jira-administrators, jira-developers, jira-users.
And I've creted group 'jira-guests' with only one member 'guest'.

Credentianals of both users are configurable. Please take a look at my 'build-dev.xml' and
last topic of http://www.topcoder.com/wiki/display/tc/Component+Development+Member+Guidelines

Here is the full list of configuration parameters:

Property        Description             Default value
-----------------------------------------------------
tcj-url         JIRA URL                http://localhost:8090/rpc/soap/jirasoapservice-v2

tcj-user        user name               root

tcj-pwd         user password           123

tcj-guest       guest name              guest

tcj-guest-pwd   guest password          guest

tcj-prefix      prefix used in keys     TCJ
                of all created
                test projects
                (projects starting
                with this prefix are
                removed after each
                test)


6. Create permission schemes with default names (i've used copy of default permission scheme).
Make sure that root user has rights to administer and browse projects in all default
permission schemes.

7. If notification is enabled then add notification schemes with default names.

8. If you use JIRA Enterprise then add issue security schemes with default names.

***
Good luck!
