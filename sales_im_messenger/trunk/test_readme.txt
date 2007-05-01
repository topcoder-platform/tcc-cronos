Steps in order to execute the tests:
- create in your informix 10 db the a database with transaction support
	create database sales_im_messenger with log;
- create the tables and populate id_sequences table by executing
  test_files/Sales_IM_Messenger_DDL.sql file on the newly created database
- update the property values jdbc_url, username, password for the
  configuration of db_connection_factory in the test_files

Run the tests. Running the tests will take long ~ 300s so don't worry if you'll wait longer.
Good luck.