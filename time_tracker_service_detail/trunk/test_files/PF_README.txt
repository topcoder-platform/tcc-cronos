This submission needs:
- DbUnit 2.2
- MockEJB 0.6 beta 2
for the testing

Please change all connection configuration in the configuration files and in the
unit test.

Please include test_files and test_files/mock.jar in the class path.

The dump of informix database is available in test_files/dump. The dump is used by winner.

For all test cases, pls use the scripts.sql under test_files to set up database. 

MockRunner is used for some test cases, since accuracy test cases have some problem with
the final fix submission, I just fix this by add MockRunner.

Due to the change of one class before updating svn, one test case provided by winner failed,
I fixed this.
 
All 321 test cases have run successfully.

