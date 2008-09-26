Please read README.txt first.
Since the service bean will run in EJB3 container, so it is a simple POJO when developing, you can create the service bean in programitic way easily and run the test in JUnit.

I provider a demo with real environment, please follow the steps to setup:
-copy jivebase.jar and mock.jar to %JBOSS_HOME%\server\default\lib
NOTE:
mock.jar is in test_files
jivebase.jar can be found http://forums.topcoder.com/?module=Thread&threadID=606259&mc=1#943481

-copy test_files\demo1.1\MockJiveForumService.jar (folder) to %JBOSS_HOME%\server\default\deploy

-start the JBoss, my version is 4.2.2GA

-run the client class in test_files\demo1.1\MockJiveServiceClient
make sure jndi.properties and all necessary JBoss client jars are in the classpath.
The client related class is in %JBOSS_HOME%\client\, usually use jbossall-client.jar is enough.

You will see the output:
Watching: true
Watching: false
Watching: false
Role: NO_ACCESS
New category id: 1

IMPORTANT NOTE:I think this service bean should be stateful,since the deadline is approach, please left this issue to final fix. I change all the stateless to statefull.

There are two demos:
1. com.topcoder.forum.service.ejb.mock.MockJiveDemo
2. test_files/demo1.1/MockJiveServiceClient.java

Please note that "if entityId.contains(id)" is useless since it is Set<Long> collection.

Thanks for your review :)
