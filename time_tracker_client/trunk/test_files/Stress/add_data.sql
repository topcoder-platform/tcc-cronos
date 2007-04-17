
-------------------------------------------------------------------------------------------------------
--  sequences used
INSERT INTO id_sequences (name,next_block_start,block_size,exhausted) VALUES('com.topcoder.security.authorization.Action',100,20,0);
INSERT INTO id_sequences (name,next_block_start,block_size,exhausted) VALUES('com.topcoder.security.authorization.ActionContext',100,20,0);
INSERT INTO id_sequences (name,next_block_start,block_size,exhausted) VALUES('com.topcoder.security.authorization.Principal',100,20,0);
INSERT INTO id_sequences (name,next_block_start,block_size,exhausted) VALUES('com.topcoder.security.authorization.SecurityRole',100,20,0);
INSERT INTO id_sequences (name,next_block_start,block_size,exhausted) VALUES('TimeTrackerID',60000001,20,0);
INSERT INTO id_sequences (name,next_block_start,block_size,exhausted) VALUES('TimeTrackerUser',50000001,20,0);
INSERT INTO id_sequences (name,next_block_start,block_size,exhausted) VALUES('com.topcoder.timetracker.user.User',100,20,0);
INSERT INTO id_sequences (name,next_block_start,block_size,exhausted) VALUES('com.topcoder.timetracker.project.Project',100,1,0);
INSERT INTO id_sequences (name,next_block_start,block_size,exhausted) VALUES('PaymentTermGenerator',100,20,0);
INSERT INTO id_sequences (name,next_block_start,block_size,exhausted) VALUES('com.topcoder.timetracker.entry.time.RejectReason',1,20,0);
INSERT INTO id_sequences (name,next_block_start,block_size,exhausted) VALUES('com.topcoder.timetracker.entry.time.Company',1,20,0);
INSERT INTO id_sequences (name,next_block_start,block_size,exhausted) VALUES('com.topcoder.timetracker.entry.time.RejectEmail',1,20,0);
INSERT INTO id_sequences (name,next_block_start,block_size,exhausted) VALUES('com.topcoder.timetracker.entry.time.TaskType',500000,20,0);
INSERT INTO id_sequences (name,next_block_start,block_size,exhausted) VALUES('com.topcoder.timetracker.entry.time.TimeEntry',600000,20,0);
INSERT INTO id_sequences (name,next_block_start,block_size,exhausted) VALUES('com.topcoder.timetracker.entry.time.TimeStatus',700000000,20,0);
INSERT INTO id_sequences (name,next_block_start,block_size,exhausted) VALUES('com.topcoder.timetracker.entry.time.Rate',1,20,0);


-------------------------------------------------------------------------------------------------------

insert into company(company_id, name, passcode, creation_date,
       creation_user,
       modification_date,
       modification_user) values(1, 'The Company', 'test', CURRENT, '', CURRENT, '');

insert into payment_terms(
     payment_terms_id ,
     description ,
     creation_date ,
     creation_user ,
     modification_date ,
     modification_user ,
     active ,
     term
) values (1, 'payment', CURRENT, USER, CURRENT, USER, 1, 1);

insert into project(
     project_id ,
     name ,
     company_id ,
     active ,
     sales_tax ,
     payment_terms_id ,
     description ,
     start_date ,
     end_date ,
     creation_date ,
     creation_user ,
     modification_date ,
     modification_user
) values (1, 'time tracker', 1, 1, 0.35, 1, 'time tracker', CURRENT, CURRENT, CURRENT, '', CURRENT, '');