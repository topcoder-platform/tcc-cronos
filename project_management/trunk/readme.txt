step to run tests:

1. Start informix and create a database (with option "WITH LOG"), then initialize the database with "doc/project_management.sql".

2. Modify connection setting in "test_files/config.xml", "test_files/accuracytests/config.xml", "test_files/stresstests/config.xml" and "test_files/failuretests/config.xml".

3. Ant test.