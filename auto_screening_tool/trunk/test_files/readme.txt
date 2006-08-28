Dear review,

     In order to run unit test cases,

     1. modify test_files/dbconfig.xml according to your database setting.

     2. run test_files/schema.sql to create tables.

     Some Notes:

     3. the log created by LogFactory will be printed to both console and test_files/screening.log

     4. for simplicity, LocalFileUpload is used instead of RemoteFileUpload in the configuration file. If you want to use RemoteFileUpload, please config the IPServer component and File System Server component manually.

     4.1 remoteFileUpload property name has been changed to fileUpload. It's more general, since there are other kinds of fileUpload like LocalFileUpload, MemoryFileUpload.

     5. all the dependent jar files are included in the lib and test_files directory.

     6. the persistence.informix package is also implemented, making the testing more easy and practical.

     Some dependent components are modified since there are some bugs in them:

     6. File Upload 2.0. the final fix version is used.

     7. User Project Data Store 1.0. the final fix version is used.

     8. Executable Wrapper 1.0. it's modified.

     in SynchronousExecutor.java:
         if (exitValue != 0 || errorOutput.length() != 0) {
            throw new ExecutionException("Command doesn't exist "
                + "or exits abnormally");

     is removed. Since check style returns non-zero code.

     9. XMI Parser 1.1. it's recompiled. since the given jar file seems to be crashed.

     10. Object Factory 2.0. it's modified to support Polymorphism. please refer to https://software.topcoder.com/forum/c_forum_message.jsp?f=22404052&r=22800839.

     11. Job Scheduler 1.0. it's modified. there is a bug when creating a new config file.

     12. all the modified files are included in fixed_files.

Thanks.
