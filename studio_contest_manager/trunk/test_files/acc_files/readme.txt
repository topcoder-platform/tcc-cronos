Hello,
     1:) The component config manager 2.1.3 and DBConnectionFactory 1.1 (Configuration API 1.0 ) 
should be added for compile and run my test case. As I have to get sql connection, I have no idea,
but add these jars. You have to change to InformixDBConnectionFactory.xml configuration parameter
to your database before running accuracy test cases.

     2:) There is also a class TestOne.java which is not included in the test case.
This class is used for running and testing one method. The ContestManagerBean has too many apis.
If running all test cases togather, it will take more than one min. So I create TestOne class.
In TestOne class, the setup is the same with TestContestManagerBeanAccuracy class. So when you needs
debug, you can copy that test case to TestOne.java and running that single one.