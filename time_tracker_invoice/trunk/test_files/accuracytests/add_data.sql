
-------------------------------------------------------------------------------------------------------
--  sequences used
INSERT INTO id_sequences (name,next_block_start,block_size,exhausted) VALUES('com.topcoder.security.authorization.Action',100,20,0);
INSERT INTO id_sequences (name,next_block_start,block_size,exhausted) VALUES('com.topcoder.security.authorization.ActionContext',100,20,0);
INSERT INTO id_sequences (name,next_block_start,block_size,exhausted) VALUES('com.topcoder.security.authorization.Principal',100,20,0);
INSERT INTO id_sequences (name,next_block_start,block_size,exhausted) VALUES('com.topcoder.security.authorization.SecurityRole',100,20,0);
INSERT INTO id_sequences (name,next_block_start,block_size,exhausted) VALUES('TimeTrackerID',60000001,20,0);
INSERT INTO id_sequences (name,next_block_start,block_size,exhausted) VALUES('TimeTrackerUser',50000001,20,0);
INSERT INTO id_sequences (name,next_block_start,block_size,exhausted) VALUES('com.topcoder.timetracker.user.User',100,20,0);
INSERT INTO id_sequences (name,next_block_start,block_size,exhausted) VALUES('com.topcoder.timetracker.entry.time.RejectReason',1,20,0);
INSERT INTO id_sequences (name,next_block_start,block_size,exhausted) VALUES('com.topcoder.timetracker.entry.time.Company',1,20,0);
INSERT INTO id_sequences (name,next_block_start,block_size,exhausted) VALUES('com.topcoder.timetracker.entry.time.RejectEmail',1,20,0);
INSERT INTO id_sequences (name,next_block_start,block_size,exhausted) VALUES('com.topcoder.timetracker.entry.time.TaskType',500000,20,0);
INSERT INTO id_sequences (name,next_block_start,block_size,exhausted) VALUES('com.topcoder.timetracker.entry.time.TimeEntry',600000,20,0);
INSERT INTO id_sequences (name,next_block_start,block_size,exhausted) VALUES('com.topcoder.timetracker.entry.time.TimeStatus',700000000,20,0);
INSERT INTO id_sequences (name,next_block_start,block_size,exhausted) VALUES('com.topcoder.timetracker.entry.time.Rate',1,20,0);
INSERT INTO id_sequences (name,next_block_start,block_size,exhausted) VALUES('com.topcoder.timetracker.invoice',200,1,0);

-------------------------------------------------------------------------------------------------------
-- Required roles for Time Tracker application
INSERT INTO role (role_id, role_name) VALUES (1, 'Contractor');
INSERT INTO role (role_id, role_name) VALUES (2, 'Employee');
INSERT INTO role (role_id, role_name) VALUES (3, 'Manager');
INSERT INTO role (role_id, role_name) VALUES (4, 'Administrator');
INSERT INTO role (role_id, role_name) VALUES (5, 'Super User');

-- Required actions for Time Tracker application
INSERT INTO action (action_id, class_name, action_name)
    VALUES (1, 'com.topcoder.security.authorization.persistence.GeneralAction', 'Login');
INSERT INTO action (action_id, class_name, action_name)
    VALUES (2, 'com.topcoder.security.authorization.persistence.GeneralAction', 'Enter Time');
INSERT INTO action (action_id, class_name, action_name)
    VALUES (3, 'com.topcoder.security.authorization.persistence.GeneralAction', 'Enter Expense');
INSERT INTO action (action_id, class_name, action_name)
    VALUES (4, 'com.topcoder.security.authorization.persistence.GeneralAction', 'Project Report');
INSERT INTO action (action_id, class_name, action_name)
    VALUES (5, 'com.topcoder.security.authorization.persistence.GeneralAction', 'Employee Report');
INSERT INTO action (action_id, class_name, action_name)
    VALUES (6, 'com.topcoder.security.authorization.persistence.GeneralAction', 'Personal Employee Report');
INSERT INTO action (action_id, class_name, action_name)
    VALUES (7, 'com.topcoder.security.authorization.persistence.GeneralAction', 'Client Report');
INSERT INTO action (action_id, class_name, action_name)
    VALUES (8, 'com.topcoder.security.authorization.persistence.GeneralAction', 'Approve Any Contractor Time');
INSERT INTO action (action_id, class_name, action_name)
    VALUES (9, 'com.topcoder.security.authorization.persistence.GeneralAction', 'Approve Managed Contractor Time');
INSERT INTO action (action_id, class_name, action_name)
    VALUES (10, 'com.topcoder.security.authorization.persistence.GeneralAction', 'Add Any Project');
INSERT INTO action (action_id, class_name, action_name)
    VALUES (11, 'com.topcoder.security.authorization.persistence.GeneralAction', 'Add Managed Project');
INSERT INTO action (action_id, class_name, action_name)
    VALUES (12, 'com.topcoder.security.authorization.persistence.GeneralAction', 'Edit Any Project');
INSERT INTO action (action_id, class_name, action_name)
    VALUES (13, 'com.topcoder.security.authorization.persistence.GeneralAction', 'Edit Managed Project');
INSERT INTO action (action_id, class_name, action_name)
    VALUES (14, 'com.topcoder.security.authorization.persistence.GeneralAction', 'Delete Any Project');
INSERT INTO action (action_id, class_name, action_name)
    VALUES (15, 'com.topcoder.security.authorization.persistence.GeneralAction', 'Delete Managed Project');
INSERT INTO action (action_id, class_name, action_name)
    VALUES (16, 'com.topcoder.security.authorization.persistence.GeneralAction', 'Add Client');
INSERT INTO action (action_id, class_name, action_name)
    VALUES (17, 'com.topcoder.security.authorization.persistence.GeneralAction', 'Edit Client');
INSERT INTO action (action_id, class_name, action_name)
    VALUES (18, 'com.topcoder.security.authorization.persistence.GeneralAction', 'Delete Client');
INSERT INTO action (action_id, class_name, action_name)
    VALUES (19, 'com.topcoder.security.authorization.persistence.GeneralAction', 'Edit User');

-- Required action context for Time Tracker application
INSERT INTO action_context (action_context_id, class_name, action_context_name, action_context_parent_id)
    VALUES (1, 'com.topcoder.security.authorization.persistence.GeneralActionContext', 'Time Tracker', 1);

-- Required action role context for Time Tracker application

-- For Contractor role
INSERT INTO role_action_context_permission (role_id, action_id, action_context_id, permission) VALUES (1, 1, 1, 1);
INSERT INTO role_action_context_permission (role_id, action_id, action_context_id, permission) VALUES (1, 2, 1, 1);
INSERT INTO role_action_context_permission (role_id, action_id, action_context_id, permission) VALUES (1, 3, 1, 1);
INSERT INTO role_action_context_permission (role_id, action_id, action_context_id, permission) VALUES (1, 4, 1, 0);
INSERT INTO role_action_context_permission (role_id, action_id, action_context_id, permission) VALUES (1, 5, 1, 0);
INSERT INTO role_action_context_permission (role_id, action_id, action_context_id, permission) VALUES (1, 6, 1, 1);
INSERT INTO role_action_context_permission (role_id, action_id, action_context_id, permission) VALUES (1, 7, 1, 0);
INSERT INTO role_action_context_permission (role_id, action_id, action_context_id, permission) VALUES (1, 8, 1, 0);
INSERT INTO role_action_context_permission (role_id, action_id, action_context_id, permission) VALUES (1, 9, 1, 0);
INSERT INTO role_action_context_permission (role_id, action_id, action_context_id, permission) VALUES (1, 10, 1, 0);
INSERT INTO role_action_context_permission (role_id, action_id, action_context_id, permission) VALUES (1, 11, 1, 0);
INSERT INTO role_action_context_permission (role_id, action_id, action_context_id, permission) VALUES (1, 12, 1, 0);
INSERT INTO role_action_context_permission (role_id, action_id, action_context_id, permission) VALUES (1, 13, 1, 0);
INSERT INTO role_action_context_permission (role_id, action_id, action_context_id, permission) VALUES (1, 14, 1, 0);
INSERT INTO role_action_context_permission (role_id, action_id, action_context_id, permission) VALUES (1, 15, 1, 0);
INSERT INTO role_action_context_permission (role_id, action_id, action_context_id, permission) VALUES (1, 16, 1, 0);
INSERT INTO role_action_context_permission (role_id, action_id, action_context_id, permission) VALUES (1, 17, 1, 0);
INSERT INTO role_action_context_permission (role_id, action_id, action_context_id, permission) VALUES (1, 18, 1, 0);
INSERT INTO role_action_context_permission (role_id, action_id, action_context_id, permission) VALUES (1, 19, 1, 0);

-- For Employee role
INSERT INTO role_action_context_permission (role_id, action_id, action_context_id, permission) VALUES (2, 1, 1, 1);
INSERT INTO role_action_context_permission (role_id, action_id, action_context_id, permission) VALUES (2, 2, 1, 1);
INSERT INTO role_action_context_permission (role_id, action_id, action_context_id, permission) VALUES (2, 3, 1, 1);
INSERT INTO role_action_context_permission (role_id, action_id, action_context_id, permission) VALUES (2, 4, 1, 0);
INSERT INTO role_action_context_permission (role_id, action_id, action_context_id, permission) VALUES (2, 5, 1, 0);
INSERT INTO role_action_context_permission (role_id, action_id, action_context_id, permission) VALUES (2, 6, 1, 1);
INSERT INTO role_action_context_permission (role_id, action_id, action_context_id, permission) VALUES (2, 7, 1, 0);
INSERT INTO role_action_context_permission (role_id, action_id, action_context_id, permission) VALUES (2, 8, 1, 0);
INSERT INTO role_action_context_permission (role_id, action_id, action_context_id, permission) VALUES (2, 9, 1, 0);
INSERT INTO role_action_context_permission (role_id, action_id, action_context_id, permission) VALUES (2, 10, 1, 0);
INSERT INTO role_action_context_permission (role_id, action_id, action_context_id, permission) VALUES (2, 11, 1, 0);
INSERT INTO role_action_context_permission (role_id, action_id, action_context_id, permission) VALUES (2, 12, 1, 0);
INSERT INTO role_action_context_permission (role_id, action_id, action_context_id, permission) VALUES (2, 13, 1, 0);
INSERT INTO role_action_context_permission (role_id, action_id, action_context_id, permission) VALUES (2, 14, 1, 0);
INSERT INTO role_action_context_permission (role_id, action_id, action_context_id, permission) VALUES (2, 15, 1, 0);
INSERT INTO role_action_context_permission (role_id, action_id, action_context_id, permission) VALUES (2, 16, 1, 0);
INSERT INTO role_action_context_permission (role_id, action_id, action_context_id, permission) VALUES (2, 17, 1, 0);
INSERT INTO role_action_context_permission (role_id, action_id, action_context_id, permission) VALUES (2, 18, 1, 0);
INSERT INTO role_action_context_permission (role_id, action_id, action_context_id, permission) VALUES (2, 19, 1, 0);

-- For Manager role
INSERT INTO role_action_context_permission (role_id, action_id, action_context_id, permission) VALUES (3, 1, 1, 1);
INSERT INTO role_action_context_permission (role_id, action_id, action_context_id, permission) VALUES (3, 2, 1, 1);
INSERT INTO role_action_context_permission (role_id, action_id, action_context_id, permission) VALUES (3, 3, 1, 1);
INSERT INTO role_action_context_permission (role_id, action_id, action_context_id, permission) VALUES (3, 4, 1, 1);
INSERT INTO role_action_context_permission (role_id, action_id, action_context_id, permission) VALUES (3, 5, 1, 1);
INSERT INTO role_action_context_permission (role_id, action_id, action_context_id, permission) VALUES (3, 6, 1, 1);
INSERT INTO role_action_context_permission (role_id, action_id, action_context_id, permission) VALUES (3, 7, 1, 0);
INSERT INTO role_action_context_permission (role_id, action_id, action_context_id, permission) VALUES (3, 8, 1, 0);
INSERT INTO role_action_context_permission (role_id, action_id, action_context_id, permission) VALUES (3, 9, 1, 1);
INSERT INTO role_action_context_permission (role_id, action_id, action_context_id, permission) VALUES (3, 10, 1, 0);
INSERT INTO role_action_context_permission (role_id, action_id, action_context_id, permission) VALUES (3, 11, 1, 1);
INSERT INTO role_action_context_permission (role_id, action_id, action_context_id, permission) VALUES (3, 12, 1, 0);
INSERT INTO role_action_context_permission (role_id, action_id, action_context_id, permission) VALUES (3, 13, 1, 1);
INSERT INTO role_action_context_permission (role_id, action_id, action_context_id, permission) VALUES (3, 14, 1, 0);
INSERT INTO role_action_context_permission (role_id, action_id, action_context_id, permission) VALUES (3, 15, 1, 1);
INSERT INTO role_action_context_permission (role_id, action_id, action_context_id, permission) VALUES (3, 16, 1, 0);
INSERT INTO role_action_context_permission (role_id, action_id, action_context_id, permission) VALUES (3, 17, 1, 0);
INSERT INTO role_action_context_permission (role_id, action_id, action_context_id, permission) VALUES (3, 18, 1, 0);
INSERT INTO role_action_context_permission (role_id, action_id, action_context_id, permission) VALUES (3, 19, 1, 1);

-- For Administrator role
INSERT INTO role_action_context_permission (role_id, action_id, action_context_id, permission) VALUES (4, 1, 1, 1);
INSERT INTO role_action_context_permission (role_id, action_id, action_context_id, permission) VALUES (4, 2, 1, 1);
INSERT INTO role_action_context_permission (role_id, action_id, action_context_id, permission) VALUES (4, 3, 1, 1);
INSERT INTO role_action_context_permission (role_id, action_id, action_context_id, permission) VALUES (4, 4, 1, 1);
INSERT INTO role_action_context_permission (role_id, action_id, action_context_id, permission) VALUES (4, 5, 1, 1);
INSERT INTO role_action_context_permission (role_id, action_id, action_context_id, permission) VALUES (4, 6, 1, 1);
INSERT INTO role_action_context_permission (role_id, action_id, action_context_id, permission) VALUES (4, 7, 1, 1);
INSERT INTO role_action_context_permission (role_id, action_id, action_context_id, permission) VALUES (4, 8, 1, 1);
INSERT INTO role_action_context_permission (role_id, action_id, action_context_id, permission) VALUES (4, 9, 1, 0);
INSERT INTO role_action_context_permission (role_id, action_id, action_context_id, permission) VALUES (4, 10, 1, 1);
INSERT INTO role_action_context_permission (role_id, action_id, action_context_id, permission) VALUES (4, 11, 1, 0);
INSERT INTO role_action_context_permission (role_id, action_id, action_context_id, permission) VALUES (4, 12, 1, 1);
INSERT INTO role_action_context_permission (role_id, action_id, action_context_id, permission) VALUES (4, 13, 1, 0);
INSERT INTO role_action_context_permission (role_id, action_id, action_context_id, permission) VALUES (4, 14, 1, 1);
INSERT INTO role_action_context_permission (role_id, action_id, action_context_id, permission) VALUES (4, 15, 1, 0);
INSERT INTO role_action_context_permission (role_id, action_id, action_context_id, permission) VALUES (4, 16, 1, 1);
INSERT INTO role_action_context_permission (role_id, action_id, action_context_id, permission) VALUES (4, 17, 1, 1);
INSERT INTO role_action_context_permission (role_id, action_id, action_context_id, permission) VALUES (4, 18, 1, 1);
INSERT INTO role_action_context_permission (role_id, action_id, action_context_id, permission) VALUES (4, 19, 1, 1);


-- The standard time statuses

INSERT INTO time_status (time_status_id, Description, creation_Date, creation_User, modification_Date, modification_User)
    VALUES (1, 'Entered', CURRENT, 'System', CURRENT, 'System');

INSERT INTO time_status (time_status_id, Description, creation_Date, creation_User, modification_Date, modification_User)
    VALUES (2, 'Pending', CURRENT, 'System', CURRENT, 'System');

INSERT INTO time_status (time_status_id, Description, creation_Date, creation_User, modification_Date, modification_User)
    VALUES (3, 'Approved', CURRENT, 'System', CURRENT, 'System');

INSERT INTO time_status (time_status_id, Description, creation_Date, creation_User, modification_Date, modification_User)
    VALUES (4, 'Rejected', CURRENT, 'System', CURRENT, 'System');


-- The standard expense statuses

INSERT INTO expense_status (expense_status_id, Description, creation_Date, creation_User, modification_Date, modification_User)
    VALUES (1, 'Entered', CURRENT, 'System', CURRENT, 'System');

INSERT INTO expense_status (expense_status_id, Description, creation_Date, creation_User, modification_Date, modification_User)
    VALUES (2, 'Pending', CURRENT, 'System', CURRENT, 'System');

INSERT INTO expense_status (expense_status_id, Description, creation_Date, creation_User, modification_Date, modification_User)
    VALUES (3, 'Approved', CURRENT, 'System', CURRENT, 'System');

INSERT INTO expense_status (expense_status_id, Description, creation_Date, creation_User, modification_Date, modification_User)
    VALUES (4, 'Rejected', CURRENT, 'System', CURRENT, 'System');


INSERT INTO principal (principal_id, principal_name) VALUES (1, 'admin');

INSERT INTO principal_role VALUES (1, 4);

insert into company(company_id, name, passcode, creation_date,
       creation_user,
       modification_date,
       modification_user) values(1, 'The Company', 'test', CURRENT, '', CURRENT, '');


insert into account_status (account_status_id,
       description,
       creation_date,
       creation_user,
       modification_date,
       modification_user) VALUES(1, 'active', CURRENT, '', CURRENT, '');

insert into account_status (account_status_id,
       description,
       creation_date,
       creation_user,
       modification_date,
       modification_user) VALUES(0, 'inactive', CURRENT, '', CURRENT, '');


insert into user_account(user_account_id, company_id, user_name, password, account_status_id, creation_date,
       creation_user,
       modification_date,
       modification_user) values(1, 1, 'admin', 'tc_super', 1, CURRENT, '', CURRENT, '');


insert into state_name (
       state_name_id,
       name,
       abbreviation,
       creation_date,
       creation_user,
       modification_date,
       modification_user) VALUES (1, 'New York', 'NY', CURRENT, '', CURRENT, '');


insert into address (
       address_id,
       line1,
       line2,
       city,
       state_name_id,
       country_name_id,
       zip_code,
       creation_date,
       creation_user,
       modification_date,
       modification_user) VALUES (1, 'street', 'appt', 'city', 1, 1, 10028, CURRENT, '', CURRENT, '');


insert into contact (
       contact_id,
       first_name,
       last_name,
       phone,
       email,
       creation_date,
       creation_user,
       modification_date,
       modification_user) 
VALUES (1, 'contact', 'person', '1234567890', 'contact@person.net', CURRENT, '', CURRENT, '');

insert into address_relation(
     entity_id ,
     address_type_id ,
     creation_date ,
     creation_user ,
     modification_date ,
     modification_user ,
     address_id
) values (1, 4, CURRENT, '', CURRENT, '', 1);

insert into contact_relation(
     entity_id ,
     creation_date ,
     creation_user ,
     modification_date ,
     modification_user ,
     contact_id ,
     contact_type_id
) values (1, CURRENT, '', CURRENT, '', 1, 4);



insert into invoice_status(
     invoice_status_id ,
     description ,
     creation_date ,
     creation_user ,
     modification_date ,
     modification_user
) values (11, 'description', CURRENT, 'user', CURRENT, 'use');