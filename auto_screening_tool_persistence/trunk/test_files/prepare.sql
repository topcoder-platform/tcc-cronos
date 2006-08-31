INSERT INTO id_sequences (name, next_block_start, block_size, exhausted) VALUES ('screening_result_id_seq', 1, 1, 0);

INSERT INTO project_category_lu (project_category_id) VALUES (1);
INSERT INTO project_category_lu (project_category_id) VALUES (2);
INSERT INTO project_category_lu (project_category_id) VALUES (3);
INSERT INTO project_category_lu (project_category_id) VALUES (4);

INSERT INTO project (project_id, project_category_id) VALUES (1, 1);
INSERT INTO project (project_id, project_category_id) VALUES (2, 2);
INSERT INTO project (project_id, project_category_id) VALUES (3, 3);
INSERT INTO project (project_id, project_category_id) VALUES (4, 1);
INSERT INTO project (project_id, project_category_id) VALUES (5, 2);

INSERT INTO resource (resource_id) VALUES (1);
INSERT INTO resource (resource_id) VALUES (2);
INSERT INTO resource (resource_id) VALUES (3);
INSERT INTO resource (resource_id) VALUES (4);
INSERT INTO resource (resource_id) VALUES (5);
INSERT INTO resource (resource_id) VALUES (6);
INSERT INTO resource (resource_id) VALUES (7);
INSERT INTO resource (resource_id) VALUES (8);
INSERT INTO resource (resource_id) VALUES (9);
INSERT INTO resource (resource_id) VALUES (10);

INSERT INTO resource_info_type_lu(resource_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
VALUES(1, 'External Reference ID', 'External Reference ID', 'System', CURRENT, 'User', CURRENT);

INSERT INTO resource_info (resource_id, resource_info_type_id, value) VALUES(1, 1, '1');
INSERT INTO resource_info (resource_id, resource_info_type_id, value) VALUES(2, 1, '2');
INSERT INTO resource_info (resource_id, resource_info_type_id, value) VALUES(3, 1, '3');
INSERT INTO resource_info (resource_id, resource_info_type_id, value) VALUES(4, 1, '4');
INSERT INTO resource_info (resource_id, resource_info_type_id, value) VALUES(5, 1, '5');
INSERT INTO resource_info (resource_id, resource_info_type_id, value) VALUES(6, 1, '6');
INSERT INTO resource_info (resource_id, resource_info_type_id, value) VALUES(7, 1, '7');
INSERT INTO resource_info (resource_id, resource_info_type_id, value) VALUES(8, 1, '8');
INSERT INTO resource_info (resource_id, resource_info_type_id, value) VALUES(9, 1, '9');
INSERT INTO resource_info (resource_id, resource_info_type_id, value) VALUES(10, 1, '10');

INSERT INTO upload (upload_id, project_id, resource_id, parameter) VALUES(1, 5, 10, 'fileId1');
INSERT INTO upload (upload_id, project_id, resource_id, parameter) VALUES(2, 4, 9, 'fileId2');
INSERT INTO upload (upload_id, project_id, resource_id, parameter) VALUES(3, 3, 8, 'fileId3');
INSERT INTO upload (upload_id, project_id, resource_id, parameter) VALUES(4, 2, 7, 'fileId4');
INSERT INTO upload (upload_id, project_id, resource_id, parameter) VALUES(5, 1, 6, 'fileId5');
INSERT INTO upload (upload_id, project_id, resource_id, parameter) VALUES(6, 5, 5, 'fileId6');
INSERT INTO upload (upload_id, project_id, resource_id, parameter) VALUES(7, 4, 4, 'fileId7');
INSERT INTO upload (upload_id, project_id, resource_id, parameter) VALUES(8, 3, 3, 'fileId8');
INSERT INTO upload (upload_id, project_id, resource_id, parameter) VALUES(9, 2, 2, 'fileId9');
INSERT INTO upload (upload_id, project_id, resource_id, parameter) VALUES(10, 1, 1, 'fileId10');

INSERT INTO screening_status_lu (screening_status_id, name, description, create_user, create_date, modify_user, modify_date)
VALUES(1, 'Pending', 'unit testing', 'System', CURRENT, 'User', CURRENT);
INSERT INTO screening_status_lu (screening_status_id, name, description, create_user, create_date, modify_user, modify_date)
VALUES(2, 'Screening', 'unit testing', 'System', CURRENT, 'User', CURRENT);
INSERT INTO screening_status_lu (screening_status_id, name, description, create_user, create_date, modify_user, modify_date)
VALUES(3, 'Failed', 'unit testing', 'System', CURRENT, 'User', CURRENT);
INSERT INTO screening_status_lu (screening_status_id, name, description, create_user, create_date, modify_user, modify_date)
VALUES(4, 'Passed', 'unit testing', 'System', CURRENT, 'User', CURRENT);
INSERT INTO screening_status_lu (screening_status_id, name, description, create_user, create_date, modify_user, modify_date)
VALUES(5, 'Passed with Warning', 'unit testing', 'System', CURRENT, 'User', CURRENT);

INSERT INTO response_severity_lu (response_severity_id, name, description, create_user, create_date, modify_user, modify_date)
VALUES(1, 'severity1', 'unit testing', 'System', CURRENT, 'User', CURRENT);
INSERT INTO response_severity_lu (response_severity_id, name, description, create_user, create_date, modify_user, modify_date)
VALUES(2, 'severity2', 'unit testing', 'System', CURRENT, 'User', CURRENT);
INSERT INTO response_severity_lu (response_severity_id, name, description, create_user, create_date, modify_user, modify_date)
VALUES(3, 'severity3', 'unit testing', 'System', CURRENT, 'User', CURRENT);

INSERT INTO screening_response_lu (screening_response_id, response_severity_id, response_code, response_text, create_user, create_date, modify_user, modify_date)
VALUES(1, 3, 'response1', 'unit testing', 'System', CURRENT, 'User', CURRENT);
INSERT INTO screening_response_lu (screening_response_id, response_severity_id, response_code, response_text, create_user, create_date, modify_user, modify_date)
VALUES(2, 3, 'response2', 'unit testing', 'System', CURRENT, 'User', CURRENT);
INSERT INTO screening_response_lu (screening_response_id, response_severity_id, response_code, response_text, create_user, create_date, modify_user, modify_date)
VALUES(3, 2, 'response3', 'unit testing', 'System', CURRENT, 'User', CURRENT);
INSERT INTO screening_response_lu (screening_response_id, response_severity_id, response_code, response_text, create_user, create_date, modify_user, modify_date)
VALUES(4, 2, 'response4', 'unit testing', 'System', CURRENT, 'User', CURRENT);
INSERT INTO screening_response_lu (screening_response_id, response_severity_id, response_code, response_text, create_user, create_date, modify_user, modify_date)
VALUES(5, 1, 'response5', 'unit testing', 'System', CURRENT, 'User', CURRENT);
INSERT INTO screening_response_lu (screening_response_id, response_severity_id, response_code, response_text, create_user, create_date, modify_user, modify_date)
VALUES(6, 1, 'response6', 'unit testing', 'System', CURRENT, 'User', CURRENT);

INSERT INTO screening_task (screening_task_id, upload_id, screening_status_id, create_user, create_date, modify_user, modify_date)
VALUES(1, 10, 1, 'System', CURRENT, 'User', CURRENT);
INSERT INTO screening_task (screening_task_id, upload_id, screening_status_id, create_user, create_date, modify_user, modify_date)
VALUES(2, 9, 1, 'System', CURRENT, 'User', CURRENT);
INSERT INTO screening_task (screening_task_id, upload_id, screening_status_id, create_user, create_date, modify_user, modify_date)
VALUES(3, 8, 1, 'System', CURRENT, 'User', CURRENT);
INSERT INTO screening_task (screening_task_id, upload_id, screening_status_id, create_user, create_date, modify_user, modify_date)
VALUES(4, 7, 1, 'System', CURRENT, 'User', CURRENT);
INSERT INTO screening_task (screening_task_id, upload_id, screening_status_id, screener_id, create_user, create_date, modify_user, modify_date)
VALUES(5, 6, 2, 1, 'System', CURRENT, 'User', CURRENT);
INSERT INTO screening_task (screening_task_id, upload_id, screening_status_id, screener_id, create_user, create_date, modify_user, modify_date)
VALUES(6, 5, 2, 2, 'System', CURRENT, 'User', CURRENT);
INSERT INTO screening_task (screening_task_id, upload_id, screening_status_id, screener_id, create_user, create_date, modify_user, modify_date)
VALUES(7, 4, 3, 3, 'System', CURRENT, 'User', CURRENT);
INSERT INTO screening_task (screening_task_id, upload_id, screening_status_id, screener_id, create_user, create_date, modify_user, modify_date)
VALUES(8, 3, 4, 4, 'System', CURRENT, 'User', CURRENT);
INSERT INTO screening_task (screening_task_id, upload_id, screening_status_id, screener_id, create_user, create_date, modify_user, modify_date)
VALUES(9, 2, 5, 5, 'System', CURRENT, 'User', CURRENT);
INSERT INTO screening_task (screening_task_id, upload_id, screening_status_id, screener_id, create_user, create_date, modify_user, modify_date)
VALUES(10, 1, 4, 6, 'System', CURRENT, 'User', CURRENT);

INSERT INTO user (user_id, handle, first_name, last_name, status) VALUES (1, 'dev1', 'first1', 'last10', 'OK');
INSERT INTO user (user_id, handle, first_name, last_name, status) VALUES (2, 'dev2', 'first2', 'last9', 'OK');
INSERT INTO user (user_id, handle, first_name, last_name, status) VALUES (3, 'dev3', 'first3', 'last8', 'OK');
INSERT INTO user (user_id, handle, first_name, last_name, status) VALUES (4, 'dev4', 'first4', 'last7', 'OK');
INSERT INTO user (user_id, handle, first_name, last_name, status) VALUES (5, 'dev5', 'first5', 'last6', 'OK');
INSERT INTO user (user_id, handle, first_name, last_name, status) VALUES (6, 'dev6', 'first6', 'last5', 'OK');
INSERT INTO user (user_id, handle, first_name, last_name, status) VALUES (7, 'dev7', 'first7', 'last4', 'OK');
INSERT INTO user (user_id, handle, first_name, last_name, status) VALUES (8, 'dev8', 'first8', 'last3', 'OK');
INSERT INTO user (user_id, handle, first_name, last_name, status) VALUES (9, 'dev9', 'first9', 'last2', 'OK');
INSERT INTO user (user_id, handle, first_name, last_name, status) VALUES (10, 'dev10', 'first10', 'last1', 'OK');

INSERT INTO email (email_id, user_id, address, primary_ind) VALUES (1, 1, 'dev1@topcoder.com', 1);
INSERT INTO email (email_id, user_id, address, primary_ind) VALUES (2, 1, 'alternative1_dev1@topcoder.com', 0);
INSERT INTO email (email_id, user_id, address, primary_ind) VALUES (3, 1, 'alternative2_dev1@topcoder.com', 0);
INSERT INTO email (email_id, user_id, address, primary_ind) VALUES (4, 2, 'dev2@topcoder.com', 1);
INSERT INTO email (email_id, user_id, address, primary_ind) VALUES (5, 2, 'alternative1_dev2@topcoder.com', 0);
INSERT INTO email (email_id, user_id, address, primary_ind) VALUES (6, 2, 'alternative2_dev2@topcoder.com', 0);
INSERT INTO email (email_id, user_id, address, primary_ind) VALUES (7, 3, 'dev3@topcoder.com', 1);
INSERT INTO email (email_id, user_id, address, primary_ind) VALUES (8, 3, 'alternative1_dev3@topcoder.com', 0);
INSERT INTO email (email_id, user_id, address, primary_ind) VALUES (9, 3, 'alternative2_dev3@topcoder.com', 0);
INSERT INTO email (email_id, user_id, address, primary_ind) VALUES (10, 4, 'dev4@topcoder.com', 1);
INSERT INTO email (email_id, user_id, address, primary_ind) VALUES (11, 4, 'alternative1_dev4@topcoder.com', 0);
INSERT INTO email (email_id, user_id, address, primary_ind) VALUES (12, 4, 'alternative2_dev4@topcoder.com', 0);
INSERT INTO email (email_id, user_id, address, primary_ind) VALUES (13, 5, 'dev5@topcoder.com', 1);
INSERT INTO email (email_id, user_id, address, primary_ind) VALUES (14, 5, 'alternative1_dev5@topcoder.com', 0);
INSERT INTO email (email_id, user_id, address, primary_ind) VALUES (15, 5, 'alternative2_dev5@topcoder.com', 0);
INSERT INTO email (email_id, user_id, address, primary_ind) VALUES (16, 6, 'dev6@topcoder.com', 1);
INSERT INTO email (email_id, user_id, address, primary_ind) VALUES (17, 7, 'dev7@topcoder.com', 1);
INSERT INTO email (email_id, user_id, address, primary_ind) VALUES (18, 8, 'dev8@topcoder.com', 1);
INSERT INTO email (email_id, user_id, address, primary_ind) VALUES (19, 9, 'dev9@topcoder.com', 1);
INSERT INTO email (email_id, user_id, address, primary_ind) VALUES (20, 10, 'dev10@topcoder.com', 1);
