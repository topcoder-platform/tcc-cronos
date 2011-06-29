INSERT INTO project_catalog_lu (project_catalog_id, name, create_user, create_date, modify_user, modify_date) VALUES (1, 'Design', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_catalog_lu (project_catalog_id, name, create_user, create_date, modify_user, modify_date) VALUES (2, 'Development', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_catalog_lu (project_catalog_id, name, create_user, create_date, modify_user, modify_date) VALUES (3, 'UI Development', 'System', CURRENT, 'System', CURRENT);

INSERT INTO project_group_category_lu (project_group_category_id, project_catalog_id, name, create_user, create_date, modify_user, modify_date) VALUES (1, 1, 'Conceptualization', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_group_category_lu (project_group_category_id, project_catalog_id, name, create_user, create_date, modify_user, modify_date) VALUES (2, 1, 'Architecture', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_group_category_lu (project_group_category_id, project_catalog_id, name, create_user, create_date, modify_user, modify_date) VALUES (3, 1, 'Specification', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_group_category_lu (project_group_category_id, project_catalog_id, name, create_user, create_date, modify_user, modify_date) VALUES (4, 1, 'Component Design', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_group_category_lu (project_group_category_id, project_catalog_id, name, create_user, create_date, modify_user, modify_date) VALUES (5, 1, 'Test Scenarios', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_group_category_lu (project_group_category_id, project_catalog_id, name, create_user, create_date, modify_user, modify_date) VALUES (6, 2, 'Assembly', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_group_category_lu (project_group_category_id, project_catalog_id, name, create_user, create_date, modify_user, modify_date) VALUES (7, 2, 'Test Suites', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_group_category_lu (project_group_category_id, project_catalog_id, name, create_user, create_date, modify_user, modify_date) VALUES (8, 2, 'Component Development', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_group_category_lu (project_group_category_id, project_catalog_id, name, create_user, create_date, modify_user, modify_date) VALUES (9, 3, 'UI Prototype', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_group_category_lu (project_group_category_id, project_catalog_id, name, create_user, create_date, modify_user, modify_date) VALUES (10, 3, 'RIA Build', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_group_category_lu (project_group_category_id, project_catalog_id, name, create_user, create_date, modify_user, modify_date) VALUES (11, 3, 'Content Creation', 'System', CURRENT, 'System', CURRENT);

INSERT INTO project_category_lu (project_category_id, project_group_category_id, name, create_user, create_date, modify_user, modify_date) VALUES (101, 1, 'Conceptualization', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_category_lu (project_category_id, project_group_category_id, name, create_user, create_date, modify_user, modify_date) VALUES (102, 2, 'Module Architecture', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_category_lu (project_category_id, project_group_category_id, name, create_user, create_date, modify_user, modify_date) VALUES (103, 2, 'System Architecture', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_category_lu (project_category_id, project_group_category_id, name, create_user, create_date, modify_user, modify_date) VALUES (104, 3, 'Specification', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_category_lu (project_category_id, project_group_category_id, name, create_user, create_date, modify_user, modify_date) VALUES (105, 4, 'Component Design', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_category_lu (project_category_id, project_group_category_id, name, create_user, create_date, modify_user, modify_date) VALUES (106, 5, 'Test Scenarios', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_category_lu (project_category_id, project_group_category_id, name, create_user, create_date, modify_user, modify_date) VALUES (107, 6, 'Module Assembly', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_category_lu (project_category_id, project_group_category_id, name, create_user, create_date, modify_user, modify_date) VALUES (108, 6, 'System Assembly', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_category_lu (project_category_id, project_group_category_id, name, create_user, create_date, modify_user, modify_date) VALUES (109, 6, 'Bug Fix Assembly', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_category_lu (project_category_id, project_group_category_id, name, create_user, create_date, modify_user, modify_date) VALUES (110, 6, 'Prototype Assembly', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_category_lu (project_category_id, project_group_category_id, name, create_user, create_date, modify_user, modify_date) VALUES (111, 7, 'Test Suites', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_category_lu (project_category_id, project_group_category_id, name, create_user, create_date, modify_user, modify_date) VALUES (112, 8, 'Component Development', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_category_lu (project_category_id, project_group_category_id, name, create_user, create_date, modify_user, modify_date) VALUES (113, 9, 'UI Prototype', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_category_lu (project_category_id, project_group_category_id, name, create_user, create_date, modify_user, modify_date) VALUES (114, 10, 'RIA Build', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_category_lu (project_category_id, project_group_category_id, name, create_user, create_date, modify_user, modify_date) VALUES (115, 11, 'Content Creation', 'System', CURRENT, 'System', CURRENT);

INSERT INTO project_status_lu (project_status_id, name, create_user, create_date, modify_user, modify_date) VALUES (1, 'Active', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_status_lu (project_status_id, name, create_user, create_date, modify_user, modify_date) VALUES (2, 'Draft', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_status_lu (project_status_id, name, create_user, create_date, modify_user, modify_date) VALUES (3, 'Deleted', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_status_lu (project_status_id, name, create_user, create_date, modify_user, modify_date) VALUES (4, 'Cancelled - Failed Review', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_status_lu (project_status_id, name, create_user, create_date, modify_user, modify_date) VALUES (5, 'Cancelled - Failed Screening', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_status_lu (project_status_id, name, create_user, create_date, modify_user, modify_date) VALUES (6, 'Cancelled - Zero Submissions', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_status_lu (project_status_id, name, create_user, create_date, modify_user, modify_date) VALUES (7, 'Completed', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_status_lu (project_status_id, name, create_user, create_date, modify_user, modify_date) VALUES (8, 'Cancelled - Winner Unresponsive', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_status_lu (project_status_id, name, create_user, create_date, modify_user, modify_date) VALUES (9, 'Cancelled - Client Request', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_status_lu (project_status_id, name, create_user, create_date, modify_user, modify_date) VALUES (10, 'Cancelled - Requirements Infeasible', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_status_lu (project_status_id, name, create_user, create_date, modify_user, modify_date) values (11, 'Cancelled - Zero Registrations', 'System', CURRENT, 'System', CURRENT);

INSERT INTO technology_types (technology_type_id, technology_name, description, status_id) VALUES (1, 'J2EE', 'Java 2 Enterprise Edition', 1);
INSERT INTO technology_types (technology_type_id, technology_name, description, status_id) VALUES (2, 'Java', 'Java programming language', 1);
INSERT INTO technology_types (technology_type_id, technology_name, description, status_id) VALUES (3, 'JavaBean', 'JavaBean', 1);
INSERT INTO technology_types (technology_type_id, technology_name, description, status_id) VALUES (4, 'EJB', 'Enterprise Java Beans', 1);
INSERT INTO technology_types (technology_type_id, technology_name, description, status_id) VALUES (5, 'JSP', 'Java ServerPages', 1);
INSERT INTO technology_types (technology_type_id, technology_name, description, status_id) VALUES (6, 'Servlet', 'Java Servlet technology', 1);
INSERT INTO technology_types (technology_type_id, technology_name, description, status_id) VALUES (7, 'Applet', 'Java Applets', 1);
INSERT INTO technology_types (technology_type_id, technology_name, description, status_id) VALUES (8, 'Java Application', 'Java Applications', 1);
INSERT INTO technology_types (technology_type_id, technology_name, description, status_id) VALUES (9, 'JMS', 'Java Messaging Service', 1);
INSERT INTO technology_types (technology_type_id, technology_name, description, status_id) VALUES (10, 'Web Services', 'Web Services', 1);
INSERT INTO technology_types (technology_type_id, technology_name, description, status_id) VALUES (11, '.NET', 'Microsoft .NET Framework', 1);
INSERT INTO technology_types (technology_type_id, technology_name, description, status_id) VALUES (12, 'VB', 'Microsoft Visual Basic programming language', 1);
INSERT INTO technology_types (technology_type_id, technology_name, description, status_id) VALUES (13, 'C++', 'C++ programming language', 1);
INSERT INTO technology_types (technology_type_id, technology_name, description, status_id) VALUES (14, 'COM', 'Microsoft Component Object Model', 1);
INSERT INTO technology_types (technology_type_id, technology_name, description, status_id) VALUES (15, 'XML', 'Extensible Markup Language', 1);
INSERT INTO technology_types (technology_type_id, technology_name, description, status_id) VALUES (16, 'XSL', 'XML Style Sheets', 1);
INSERT INTO technology_types (technology_type_id, technology_name, description, status_id) VALUES (17, 'JDBC', 'Java DataBase Connectivity', 1);

INSERT INTO project (project_id, project_status_id, project_category_id, tc_direct_project_id, create_user, create_date, modify_user, modify_date) VALUES (1, 1, 105, 1, 'System', CURRENT, 'System', CURRENT);
INSERT INTO project (project_id, project_status_id, project_category_id, tc_direct_project_id, create_user, create_date, modify_user, modify_date) VALUES (2, 2, 105, 2, 'System', CURRENT, 'System', CURRENT);
INSERT INTO project (project_id, project_status_id, project_category_id, tc_direct_project_id, create_user, create_date, modify_user, modify_date) VALUES (3, 7, 112, 3, 'System', CURRENT, 'System', CURRENT);
INSERT INTO project (project_id, project_status_id, project_category_id, tc_direct_project_id, create_user, create_date, modify_user, modify_date) VALUES (4, 1, 108, 4, 'System', CURRENT, 'System', CURRENT);
INSERT INTO project (project_id, project_status_id, project_category_id, tc_direct_project_id, create_user, create_date, modify_user, modify_date) VALUES (5, 11, 101, 5, 'System', CURRENT, 'System', CURRENT);
INSERT INTO project (project_id, project_status_id, project_category_id, tc_direct_project_id, create_user, create_date, modify_user, modify_date) VALUES (6, 1, 108, 6, 'System', CURRENT, 'System', CURRENT);
INSERT INTO project (project_id, project_status_id, project_category_id, tc_direct_project_id, create_user, create_date, modify_user, modify_date) VALUES (7, 1, 101, 7, 'System', CURRENT, 'System', CURRENT);

INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date) VALUES (1, 6, 'test project 1', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date) VALUES (2, 6, 'test project 2', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date) VALUES (3, 6, 'test project 3', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date) VALUES (4, 6, 'test project 4', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date) VALUES (5, 6, 'test project 5', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date) VALUES (1, 7, '1.0', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date) VALUES (2, 7, '1.0', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date) VALUES (3, 7, '1.0', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date) VALUES (4, 7, '1.1', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date) VALUES (5, 7, '2.0', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date) VALUES (1, 2, 'winner 1', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date) VALUES (2, 2, 'winner 2', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date) VALUES (3, 2, 'winner 3', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date) VALUES (4, 2, 'winner 4', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date) VALUES (5, 2, 'winner 5', 'System', CURRENT, 'System', CURRENT);

INSERT INTO project_result (user_id, project_id, placed, final_score) VALUES (1, 1, 1, 100.0);
INSERT INTO project_result (user_id, project_id, placed, final_score) VALUES (2, 2, 1, 95.0);
INSERT INTO project_result (user_id, project_id, placed, final_score) VALUES (3, 3, 1, 90.0);
INSERT INTO project_result (user_id, project_id, placed, final_score) VALUES (4, 4, 1, 85.0);
INSERT INTO project_result (user_id, project_id, placed, final_score) VALUES (5, 5, 1, 80.0);

INSERT INTO resource (resource_id, project_id, resource_role_id, project_phase_id, create_user, create_date, modify_user, modify_date) VALUES (1, 1, 2, 2, 'System', CURRENT, 'System', CURRENT);
INSERT INTO resource (resource_id, project_id, resource_role_id, project_phase_id, create_user, create_date, modify_user, modify_date) VALUES (2, 2, 1, 1, 'System', CURRENT, 'System', CURRENT);
INSERT INTO resource (resource_id, project_id, resource_role_id, project_phase_id, create_user, create_date, modify_user, modify_date) VALUES (3, 2, 1, 1, 'System', CURRENT, 'System', CURRENT);
INSERT INTO resource (resource_id, project_id, resource_role_id, project_phase_id, create_user, create_date, modify_user, modify_date) VALUES (4, 3, 1, 1, 'System', CURRENT, 'System', CURRENT);
INSERT INTO resource (resource_id, project_id, resource_role_id, project_phase_id, create_user, create_date, modify_user, modify_date) VALUES (5, 3, 1, 1, 'System', CURRENT, 'System', CURRENT);

INSERT INTO submission (submission_id, upload_id, submission_type_id, submission_status_id, screening_score, create_user, create_date, modify_user, modify_date) VALUES (1, 1, 1, 1, 100.0, 'System', CURRENT, 'System', CURRENT);
INSERT INTO submission (submission_id, upload_id, submission_type_id, submission_status_id, screening_score, create_user, create_date, modify_user, modify_date) VALUES (2, 2, 1, 1, 100.0, 'System', CURRENT, 'System', CURRENT);
INSERT INTO submission (submission_id, upload_id, submission_type_id, submission_status_id, screening_score, create_user, create_date, modify_user, modify_date) VALUES (3, 3, 1, 1, 100.0, 'System', CURRENT, 'System', CURRENT);
INSERT INTO submission (submission_id, upload_id, submission_type_id, submission_status_id, screening_score, create_user, create_date, modify_user, modify_date) VALUES (4, 4, 1, 1, 100.0, 'System', CURRENT, 'System', CURRENT);
INSERT INTO submission (submission_id, upload_id, submission_type_id, submission_status_id, screening_score, create_user, create_date, modify_user, modify_date) VALUES (5, 5, 1, 1, 100.0, 'System', CURRENT, 'System', CURRENT);
INSERT INTO submission (submission_id, upload_id, submission_type_id, submission_status_id, screening_score, create_user, create_date, modify_user, modify_date) VALUES (6, 6, 1, 1, 100.0, 'System', CURRENT, 'System', CURRENT);
INSERT INTO submission (submission_id, upload_id, submission_type_id, submission_status_id, screening_score, create_user, create_date, modify_user, modify_date) VALUES (7, 7, 1, 1, 100.0, 'System', CURRENT, 'System', CURRENT);
INSERT INTO submission (submission_id, upload_id, submission_type_id, submission_status_id, screening_score, create_user, create_date, modify_user, modify_date) VALUES (8, 8, 1, 1, 100.0, 'System', CURRENT, 'System', CURRENT);

INSERT INTO upload (upload_id, project_id, resource_id, upload_type_id, create_user, create_date, modify_user, modify_date) VALUES (1, 2, 1, 1, 'System', CURRENT, 'System', CURRENT);
INSERT INTO upload (upload_id, project_id, resource_id, upload_type_id, create_user, create_date, modify_user, modify_date) VALUES (2, 2, 2, 1, 'System', CURRENT, 'System', CURRENT);
INSERT INTO upload (upload_id, project_id, resource_id, upload_type_id, create_user, create_date, modify_user, modify_date) VALUES (3, 3, 3, 1, 'System', CURRENT, 'System', CURRENT);
INSERT INTO upload (upload_id, project_id, resource_id, upload_type_id, create_user, create_date, modify_user, modify_date) VALUES (4, 4, 4, 1, 'System', CURRENT, 'System', CURRENT);
INSERT INTO upload (upload_id, project_id, resource_id, upload_type_id, create_user, create_date, modify_user, modify_date) VALUES (5, 5, 5, 1, 'System', CURRENT, 'System', CURRENT);
INSERT INTO upload (upload_id, project_id, resource_id, upload_type_id, create_user, create_date, modify_user, modify_date) VALUES (6, 6, 3, 1, 'System', CURRENT, 'System', CURRENT);
INSERT INTO upload (upload_id, project_id, resource_id, upload_type_id, create_user, create_date, modify_user, modify_date) VALUES (7, 7, 4, 1, 'System', CURRENT, 'System', CURRENT);
INSERT INTO upload (upload_id, project_id, resource_id, upload_type_id, create_user, create_date, modify_user, modify_date) VALUES (8, 7, 5, 1, 'System', CURRENT, 'System', CURRENT);

INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, create_user, create_date, modify_user, modify_date) VALUES (1, 1, 1, 2, DATETIME (2011-8-20 12:00:00) YEAR TO FRACTION, DATETIME (2011-8-20 18:00:00) YEAR TO FRACTION, NULL, NULL, 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, create_user, create_date, modify_user, modify_date) VALUES (2, 2, 1, 2, DATETIME (2011-8-21 12:00:00) YEAR TO FRACTION, DATETIME (2011-8-21 18:00:00) YEAR TO FRACTION, NULL, NULL, 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, create_user, create_date, modify_user, modify_date) VALUES (3, 3, 1, 2, DATETIME (2011-8-22 12:00:00) YEAR TO FRACTION, DATETIME (2011-8-22 18:00:00) YEAR TO FRACTION, NULL, NULL, 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, create_user, create_date, modify_user, modify_date) VALUES (4, 4, 1, 2, DATETIME (2011-8-23 12:00:00) YEAR TO FRACTION, DATETIME (2011-8-23 18:00:00) YEAR TO FRACTION, NULL, NULL, 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, create_user, create_date, modify_user, modify_date) VALUES (5, 5, 1, 2, DATETIME (2011-8-24 12:00:00) YEAR TO FRACTION, DATETIME (2011-8-23 18:00:00) YEAR TO FRACTION, NULL, NULL, 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, create_user, create_date, modify_user, modify_date) VALUES (6, 1, 2, 2, DATETIME (2011-8-20 18:00:00) YEAR TO FRACTION, DATETIME (2011-8-21 18:00:00) YEAR TO FRACTION, NULL, NULL, 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, create_user, create_date, modify_user, modify_date) VALUES (7, 2, 2, 2, DATETIME (2011-8-21 18:00:00) YEAR TO FRACTION, DATETIME (2011-8-22 18:00:00) YEAR TO FRACTION, NULL, NULL, 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, create_user, create_date, modify_user, modify_date) VALUES (8, 3, 2, 2, DATETIME (2011-8-22 18:00:00) YEAR TO FRACTION, DATETIME (2011-8-23 18:00:00) YEAR TO FRACTION, NULL, NULL, 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, create_user, create_date, modify_user, modify_date) VALUES (9, 4, 2, 2, DATETIME (2011-8-23 18:00:00) YEAR TO FRACTION, DATETIME (2011-8-24 18:00:00) YEAR TO FRACTION, NULL, NULL, 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, create_user, create_date, modify_user, modify_date) VALUES (10, 5, 2, 2, DATETIME (2011-8-24 18:00:00) YEAR TO FRACTION, DATETIME (2011-8-25 18:00:00) YEAR TO FRACTION, NULL, NULL, 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, create_user, create_date, modify_user, modify_date) VALUES (11, 6, 1, 2, DATETIME (2011-5-24 18:00:00) YEAR TO FRACTION, DATETIME (2011-5-25 18:00:00) YEAR TO FRACTION, NULL, NULL, 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, create_user, create_date, modify_user, modify_date) VALUES (12, 6, 2, 2, DATETIME (2011-5-24 18:00:00) YEAR TO FRACTION, DATETIME (2011-5-25 18:00:00) YEAR TO FRACTION, NULL, NULL, 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, create_user, create_date, modify_user, modify_date) VALUES (13, 6, 3, 2, DATETIME (2011-5-24 18:00:00) YEAR TO FRACTION, DATETIME (2011-5-25 18:00:00) YEAR TO FRACTION, NULL, NULL, 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, create_user, create_date, modify_user, modify_date) VALUES (14, 6, 4, 2, DATETIME (2011-5-24 18:00:00) YEAR TO FRACTION, DATETIME (2011-5-25 18:00:00) YEAR TO FRACTION, NULL, NULL, 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, create_user, create_date, modify_user, modify_date) VALUES (15, 7, 1, 2, DATETIME (2011-5-24 18:00:00) YEAR TO FRACTION, DATETIME (2011-5-25 18:00:00) YEAR TO FRACTION, NULL, NULL, 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, create_user, create_date, modify_user, modify_date) VALUES (16, 7, 2, 2, DATETIME (2011-5-24 18:00:00) YEAR TO FRACTION, DATETIME (2011-5-25 18:00:00) YEAR TO FRACTION, NULL, NULL, 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, create_user, create_date, modify_user, modify_date) VALUES (17, 7, 3, 2, DATETIME (2011-5-24 18:00:00) YEAR TO FRACTION, DATETIME (2011-5-25 18:00:00) YEAR TO FRACTION, NULL, NULL, 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, create_user, create_date, modify_user, modify_date) VALUES (18, 7, 4, 2, DATETIME (2011-5-24 18:00:00) YEAR TO FRACTION, DATETIME (2011-5-25 18:00:00) YEAR TO FRACTION, NULL, NULL, 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, create_user, create_date, modify_user, modify_date) VALUES (19, 1, 11, 2, DATETIME (2011-8-25 18:00:00) YEAR TO FRACTION, DATETIME (2011-8-26 18:00:00) YEAR TO FRACTION, NULL, NULL, 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, create_user, create_date, modify_user, modify_date) VALUES (20, 2, 11, 2, DATETIME (2011-8-26 18:00:00) YEAR TO FRACTION, DATETIME (2011-8-27 18:00:00) YEAR TO FRACTION, NULL, NULL, 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, create_user, create_date, modify_user, modify_date) VALUES (21, 3, 11, 2, DATETIME (2011-8-27 18:00:00) YEAR TO FRACTION, DATETIME (2011-8-28 18:00:00) YEAR TO FRACTION, NULL, NULL, 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, create_user, create_date, modify_user, modify_date) VALUES (22, 4, 11, 2, DATETIME (2011-8-28 18:00:00) YEAR TO FRACTION, DATETIME (2011-8-29 18:00:00) YEAR TO FRACTION, NULL, NULL, 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, create_user, create_date, modify_user, modify_date) VALUES (23, 5, 11, 2, DATETIME (2011-8-29 18:00:00) YEAR TO FRACTION, DATETIME (2011-8-30 18:00:00) YEAR TO FRACTION, NULL, NULL, 'System', CURRENT, 'System', CURRENT);

INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date) VALUES (1, 1, '1', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date) VALUES (2, 1, '2', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date) VALUES (3, 1, '3', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date) VALUES (4, 1, '4', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date) VALUES (5, 1, '5', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date) VALUES (6, 1, '6', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date) VALUES (7, 1, '7', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date) VALUES (1, 36, '500', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date) VALUES (2, 36, '1000', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date) VALUES (3, 36, '600', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date) VALUES (4, 36, '800', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date) VALUES (5, 36, '300', 'System', CURRENT, 'System', CURRENT);

INSERT INTO comp_technology (comp_tech_id, comp_vers_id, technology_type_id) VALUES (1, 1, 1);
INSERT INTO comp_technology (comp_tech_id, comp_vers_id, technology_type_id) VALUES (2, 2, 1);
INSERT INTO comp_technology (comp_tech_id, comp_vers_id, technology_type_id) VALUES (3, 3, 1);
INSERT INTO comp_technology (comp_tech_id, comp_vers_id, technology_type_id) VALUES (4, 4, 1);
INSERT INTO comp_technology (comp_tech_id, comp_vers_id, technology_type_id) VALUES (5, 5, 1);

INSERT INTO comp_versions (comp_vers_id, component_id, phase_id, create_time, modify_date) VALUES (6, 1, 112, CURRENT, CURRENT);
INSERT INTO comp_versions (comp_vers_id, component_id, phase_id, create_time, modify_date) VALUES (7, 2, 112, CURRENT, CURRENT);

INSERT INTO comp_version_dates (comp_version_dates_id, comp_vers_id, phase_id, status_id, create_time, modify_date) VALUES (1, 6, 112, 301, CURRENT, CURRENT);
INSERT INTO comp_version_dates (comp_version_dates_id, comp_vers_id, phase_id, status_id, create_time, modify_date) VALUES (2, 7, 112, 301, CURRENT, CURRENT);

INSERT INTO comp_catalog (component_id, root_category_id, component_name, create_time, modify_date) VALUES (1, 1, 'test component 1', CURRENT, CURRENT);
INSERT INTO comp_catalog (component_id, root_category_id, component_name, create_time, modify_date) VALUES (2, 1, 'test component 2', CURRENT, CURRENT);

INSERT INTO rboard_payment (project_id, phase_id, primary_ind, amount) VALUES (6, 14, 1, 300.0);
INSERT INTO rboard_payment (project_id, phase_id, primary_ind, amount) VALUES (6, 14, 0, 200.0);
INSERT INTO rboard_payment (project_id, phase_id, primary_ind, amount) VALUES (7, 18, 1, 500.0);
INSERT INTO rboard_payment (project_id, phase_id, primary_ind, amount) VALUES (7, 18, 0, 300.0);

INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date) VALUES (14, 6, '3', 'System', CURRENT, 'System', CURRENT);
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date) VALUES (18, 6, '2', 'System', CURRENT, 'System', CURRENT);

INSERT INTO rboard_application (user_id, project_id, phase_id) VALUES (1, 6, 112);
INSERT INTO rboard_application (user_id, project_id, phase_id) VALUES (1, 7, 112);
