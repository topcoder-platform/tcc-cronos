
INSERT INTO company(company_id, name, passcode, creation_date, creation_user, modification_date, modification_user) VALUES (5, 'AOL', 'rsa_pass', TODAY, 'accuracy_user', TODAY, 'accuracy_user');
INSERT INTO project(project_id, name, company_id, description, start_date, end_date, creation_date, creation_user, modification_date, modification_user) VALUES (1, 'XMPP Gateway', 5, 'Extn. Message Presence', TODAY, TODAY, TODAY, 'accuracy_user', TODAY, 'accuracy_user');
INSERT INTO client(client_id, name, company_id, creation_date, creation_user, modification_date, modification_user) VALUES (3, 'Netscape', 5, TODAY, 'accuracy_user', TODAY, 'accuracy_user');
INSERT INTO account_status(account_status_id, description, creation_date, creation_user, modification_date, modification_user) values (1, 'dummy account', TODAY, 'accuracy_user', TODAY, 'accuracy_user');
INSERT INTO user_account(user_account_id, company_id, account_status_id, user_name, password, creation_date, creation_user, modification_date, modification_user) values (6, 5, 1, 'accuracy_tester', 'pass', TODAY, 'accuracy_user', TODAY, 'accuracy_user');

INSERT INTO application_area(app_area_id, description, creation_date, creation_user, modification_date, modification_user) VALUES (1, 'Expense', TODAY, 'accuracy_user', TODAY, 'accuracy_user');
INSERT INTO application_area(app_area_id, description, creation_date, creation_user, modification_date, modification_user) VALUES (2, 'Fixed Billing', TODAY, 'accuracy_user', TODAY, 'accuracy_user');
INSERT INTO application_area(app_area_id, description, creation_date, creation_user, modification_date, modification_user) VALUES (3, 'Time', TODAY, 'accuracy_user', TODAY, 'accuracy_user');
INSERT INTO application_area(app_area_id, description, creation_date, creation_user, modification_date, modification_user) VALUES (4, 'Client', TODAY, 'accuracy_user', TODAY, 'accuracy_user');
INSERT INTO application_area(app_area_id, description, creation_date, creation_user, modification_date, modification_user) VALUES (5, 'Company', TODAY, 'accuracy_user', TODAY, 'accuracy_user');
INSERT INTO application_area(app_area_id, description, creation_date, creation_user, modification_date, modification_user) VALUES (6, 'Project', TODAY, 'accuracy_user', TODAY, 'accuracy_user');
INSERT INTO application_area(app_area_id, description, creation_date, creation_user, modification_date, modification_user) VALUES (7, 'User', TODAY, 'accuracy_user', TODAY, 'accuracy_user');
INSERT INTO application_area(app_area_id, description, creation_date, creation_user, modification_date, modification_user) VALUES (8, 'Invoice', TODAY, 'accuracy_user', TODAY, 'accuracy_user');
INSERT INTO application_area(app_area_id, description, creation_date, creation_user, modification_date, modification_user) VALUES (9, 'Notification', TODAY, 'accuracy_user', TODAY, 'accuracy_user');
INSERT INTO application_area(app_area_id, description, creation_date, creation_user, modification_date, modification_user) VALUES (10, 'Configuration', TODAY, 'accuracy_user', TODAY, 'accuracy_user');