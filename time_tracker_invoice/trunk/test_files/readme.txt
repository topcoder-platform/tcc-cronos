Steps to make in order to run the tests:

1. Three database should be created for accuracy, unit and stress testcase.
2. Execute on each one of them the sql script file test_files/create_db.sql
3. On test_files/stress and test_files/ you'll find another scripts to run the the corresponding created database.
   There are no scripts to execute for accuracy database because it is done automatically in the accuracy tests.
4. Updated the jdbc URL, username and password on the .xml files from test_files (they are a lot)

Good luck.