Before run the unit test, please set up the database first.
  1.1 If you use Informix, please use the schema_create.sql and create.sql. 
  If you use mysql, please use contest_mysql.sql. All of these scripts are 
  under test_files directory.
  1.2 Modifiy the test_files/hibernate.cfg.xml to your environment.
  1.3 all mappings are put under test_files/mappings, they can be auto detected by persistence of hibernate.
  1.4 we need to deploy ContestManager.jar.rar under test_files/demo to run accuracy tests and stress tests and demo.
      unzip the ContestManager.jar.rar, and then copy it to the jboss delopy directory. Then configure the datasource.
      Also, copy mysql-ds.xml or informix-ds.xml to deploy directory. Start jboss.
  1.5 A demo is provided in test_files/demo. run the demo/ejbtest. 
  1.6 refer the included build.xml for necessary libraries
