1: run the create_db.sql under folder test_files/sql
2: update the config files for DBConnectionFactory(note accuracytests, Failuretests, stress have the o
dependent config files, these should be modified) to adjust the database settings
3: mockejb is used to test.
4: add the dependency.jar under /test_files into the classpath before running the unit test.

All 1043 test cases have run successfully. It takes a very long time to finish.

If there are some codes can not compile, you should include some jar files from the jboss client directory.