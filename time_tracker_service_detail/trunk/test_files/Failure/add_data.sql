
INSERT INTO time_status (time_status_id, Description, creation_Date, creation_User, modification_Date, modification_User)
    VALUES (1, 'Entered', CURRENT, 'System', CURRENT, 'System');

INSERT INTO time_status (time_status_id, Description, creation_Date, creation_User, modification_Date, modification_User)
    VALUES (2, 'Pending', CURRENT, 'System', CURRENT, 'System');

INSERT INTO time_status (time_status_id, Description, creation_Date, creation_User, modification_Date, modification_User)
    VALUES (3, 'Approved', CURRENT, 'System', CURRENT, 'System');

INSERT INTO time_status (time_status_id, Description, creation_Date, creation_User, modification_Date, modification_User)
    VALUES (4, 'Rejected', CURRENT, 'System', CURRENT, 'System');


insert into company(company_id, name, passcode, creation_date,
       creation_user,
       modification_date,
       modification_user) values(1, 'The Company', 'test', CURRENT, '', CURRENT, '');