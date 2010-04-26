Steps to run through the tests: 

1. Initialize your informix services. 
2. Execute the following sql scripts to set up the databases. 
-test_files\sql\create_seq.sql
-test_files\sql\schema_create.sql
3. Update the following configuration files, you need to modify the connection string for 
databases. 
-test_files\hibernate.cfg.xml
4. Modify the the path for dependencies in "build-dependencies.xml"