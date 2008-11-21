Hello reviewer:

1. @author tag has format TCSDESIGNER, TCSDEVELOPER. This format to ensure coding style
   does not brake checkstyle configuration. In the final fix (if i win), i will change this
   into real name of designer and developer.

2. In Demo section, @author format will equal with main source (TCSDESIGNER, TCSDEVELOPER).
   It will brake checkstyle configuration, but many previous reviewer suggest me to do this.

3. To run my java tests you should put needed classes into path.
   I show which jar you need in build-dependencies.
   To test servlet, I use easyMock, you should add its library.

4. To run js tests. You should put copy fold topcoder into
   TOMCAT_HOME/webapps and run TOMCAT.
   The fold topcoder is put in test_files.

5. About Demo
   I write the demo in js not in java. Because this is servlet component.
   We should run it in container. So it hard to write demo in java.
   But In my Demo, the js is client, it will connect to server.
   So we also can show how to use this component.

6. The CS's demo has been updated.

7. The screen copy of test cases result are put in test_files folder for your reference.

8. http://forums.topcoder.com/?module=Thread&threadID=624622&start=0
   Here is still a problem which is not solved.
   I hope we can fix it in final fix. And do not cut the points about it.

OK, happy review.