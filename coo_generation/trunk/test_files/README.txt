There is something need to be upadted but not in the requirement:

1. table third_party_library has third_party_library_id (can not be null), but in DBComponentManager, 
UPDATE_SQL: "INSERT INTO third_party_library "
            + "(name,version,url,license,usage_comments,path,alias,notes,category) VALUES (?,?,?,?,?,?,?,?,?)"
It must be wrong since third_party_library_id is missing.. Also, we should update the sql and change the excel file.

2. I attached my dml statement, I am sorry but it is hard to insert valid data since there are two many constraints and 
triggers.My suggestion is to remove all the constraints and triggers, insert some valid data.
Here what I do testing is to insert data into TC vm on one time init, so I remove the invocation for clean.sql and insert.sql
this shall be fixed.

3. The testings that not passed are all related above. By the way, it's better to use OOO 2.4, I always got some strange
problem using 3.0.. I posted on the forum.

For review, please refer to:
PDFCOOReportSerializerTest.java
BaseCOOReportSerializerTest.java
These include some new testing cases.

Please take a look at the AllTest.bmp in ./log, I am not sure whether ant shows some testing cases has not been passed, but
in my IDE, they already passed, please take a look at! I appreciated Great thanks!!

Good Luck!