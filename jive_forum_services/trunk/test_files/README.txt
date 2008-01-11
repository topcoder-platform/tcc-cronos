1. About Jive Forum
The PM has provided jivebase.jar and jiveforums.jar, but he didn't provide the instruction how to install it.
(I think it's impossible to install it.) 

In order to test with the Jive Forum, I replace the jiveforums.jar's ForumFactory class, and provides a lot
of mocking classes, and pack them into test_files/mock.jar. So please use test_files/mock.jar to replace
the JiveForum.jar to run my test cases. The mock classes's source files is under test_files/src.

Note: Make sure that: DO NOT use the JiveForums.jar the PM provids. USE mock.jar

Note Again: The jive forum is a web application, we can run it in junit console. So using mock class is the only
way to test it in junit.

2. Dependent components
Some jboss' jars are used to run the test cases, see build.xml.

3. About demo
As the demo depends on jbossall-client.jar, and it is not compatible with other server side jar
(jboss-j2ee.jar, jboss-ejb3x.jar, jboss-jaxrpc.jar). So I separate the demo part from the Unit Test.

How to use demo, see test_files/UsingDemo.txt;

4.