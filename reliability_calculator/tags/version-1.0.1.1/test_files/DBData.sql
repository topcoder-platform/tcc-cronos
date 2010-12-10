INSERT INTO 'informix'.project_info(project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date) VALUES(1001, 13, 'Yes', 'admin', CURRENT, 'admin', CURRENT);
INSERT INTO 'informix'.project_info(project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date) VALUES(1002, 13, 'Yes', 'admin', CURRENT, 'admin', CURRENT);
INSERT INTO 'informix'.project_info(project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date) VALUES(1003, 13, 'Yes', 'admin', CURRENT, 'admin', CURRENT);
INSERT INTO 'informix'.project_info(project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date) VALUES(1004, 13, 'Yes', 'admin', CURRENT, 'admin', CURRENT);
INSERT INTO 'informix'.project_info(project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date) VALUES(1005, 13, 'Yes', 'admin', CURRENT, 'admin', CURRENT);

INSERT INTO 'informix'.component_inquiry(component_inquiry_id, user_id, agreed_to_terms, rating, create_time, project_id) VALUES(1, 123456, 1, 999, TO_DATE('2010-01-06 09:12', '%Y-%m-%d %H:%M'), 1001);
INSERT INTO 'informix'.component_inquiry(component_inquiry_id, user_id, agreed_to_terms, rating, create_time, project_id) VALUES(2, 123456, 1, 999, TO_DATE('2010-01-08 09:25', '%Y-%m-%d %H:%M'), 1002);
INSERT INTO 'informix'.component_inquiry(component_inquiry_id, user_id, agreed_to_terms, rating, create_time, project_id) VALUES(3, 123456, 1, 999, TO_DATE('2010-01-14 10:30', '%Y-%m-%d %H:%M'), 1003);
INSERT INTO 'informix'.component_inquiry(component_inquiry_id, user_id, agreed_to_terms, rating, create_time, project_id) VALUES(4, 123456, 1, 999, TO_DATE('2010-01-16 09:47', '%Y-%m-%d %H:%M'), 1004);
INSERT INTO 'informix'.component_inquiry(component_inquiry_id, user_id, agreed_to_terms, rating, create_time, project_id) VALUES(5, 123456, 1, 999, TO_DATE('2010-01-18 11:29', '%Y-%m-%d %H:%M'), 1005);

INSERT INTO 'informix'.project(project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date) VALUES(1001, 3, 1, 'admin', CURRENT, 'admin', CURRENT);
INSERT INTO 'informix'.project(project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date) VALUES(1002, 3, 1, 'admin', CURRENT, 'admin', CURRENT);
INSERT INTO 'informix'.project(project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date) VALUES(1003, 3, 1, 'admin', CURRENT, 'admin', CURRENT);
INSERT INTO 'informix'.project(project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date) VALUES(1004, 3, 1, 'admin', CURRENT, 'admin', CURRENT);
INSERT INTO 'informix'.project(project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date) VALUES(1005, 3, 1, 'admin', CURRENT, 'admin', CURRENT);

INSERT INTO 'informix'.project_phase(project_phase_id, project_id, phase_type_id, phase_status_id, scheduled_start_time, scheduled_end_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date) VALUES(1, 1001, 2, 3, TO_DATE('2010-01-06 09:12', '%Y-%m-%d %H:%M'), TO_DATE('2010-01-10 09:19', '%Y-%m-%d %H:%M'), TO_DATE('2010-01-10 09:00', '%Y-%m-%d %H:%M'), 4, 'admin', CURRENT, 'admin', CURRENT);
INSERT INTO 'informix'.project_phase(project_phase_id, project_id, phase_type_id, phase_status_id, scheduled_start_time, scheduled_end_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date) VALUES(2, 1002, 2, 3, TO_DATE('2010-01-08 09:25', '%Y-%m-%d %H:%M'), TO_DATE('2010-01-12 09:25', '%Y-%m-%d %H:%M'), TO_DATE('2010-01-12 09:25', '%Y-%m-%d %H:%M'), 4, 'admin', CURRENT, 'admin', CURRENT);
INSERT INTO 'informix'.project_phase(project_phase_id, project_id, phase_type_id, phase_status_id, scheduled_start_time, scheduled_end_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date) VALUES(3, 1003, 2, 3, TO_DATE('2010-01-14 10:30', '%Y-%m-%d %H:%M'), TO_DATE('2010-01-18 10:30', '%Y-%m-%d %H:%M'), TO_DATE('2010-01-18 10:30', '%Y-%m-%d %H:%M'), 4, 'admin', CURRENT, 'admin', CURRENT);
INSERT INTO 'informix'.project_phase(project_phase_id, project_id, phase_type_id, phase_status_id, scheduled_start_time, scheduled_end_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date) VALUES(4, 1004, 2, 3, TO_DATE('2010-01-16 09:47', '%Y-%m-%d %H:%M'), TO_DATE('2010-01-20 09:47', '%Y-%m-%d %H:%M'), TO_DATE('2010-01-20 09:47', '%Y-%m-%d %H:%M'), 4, 'admin', CURRENT, 'admin', CURRENT);
INSERT INTO 'informix'.project_phase(project_phase_id, project_id, phase_type_id, phase_status_id, scheduled_start_time, scheduled_end_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date) VALUES(5, 1005, 2, 3, TO_DATE('2010-01-18 11:29', '%Y-%m-%d %H:%M'), TO_DATE('2010-01-22 11:29', '%Y-%m-%d %H:%M'), TO_DATE('2010-01-22 09:00', '%Y-%m-%d %H:%M'), 4, 'admin', CURRENT, 'admin', CURRENT);

INSERT INTO 'informix'.project_phase(project_phase_id, project_id, phase_type_id, phase_status_id, scheduled_start_time, scheduled_end_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date) VALUES(6, 1002, 3, 1, TO_DATE('2010-01-12 09:25', '%Y-%m-%d %H:%M'), TO_DATE('2010-01-13 09:25', '%Y-%m-%d %H:%M'), TO_DATE('2010-01-13 09:25', '%Y-%m-%d %H:%M'), 1, 'admin', CURRENT, 'admin', CURRENT);
INSERT INTO 'informix'.project_phase(project_phase_id, project_id, phase_type_id, phase_status_id, scheduled_start_time, scheduled_end_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date) VALUES(7, 1003, 3, 1, TO_DATE('2010-01-18 10:30', '%Y-%m-%d %H:%M'), TO_DATE('2010-01-19 10:30', '%Y-%m-%d %H:%M'), TO_DATE('2010-01-18 12:31', '%Y-%m-%d %H:%M'), 1, 'admin', CURRENT, 'admin', CURRENT);
INSERT INTO 'informix'.project_phase(project_phase_id, project_id, phase_type_id, phase_status_id, scheduled_start_time, scheduled_end_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date) VALUES(8, 1004, 3, 1, TO_DATE('2010-01-20 09:47', '%Y-%m-%d %H:%M'), TO_DATE('2010-01-21 09:47', '%Y-%m-%d %H:%M'), TO_DATE('2010-01-21 09:47', '%Y-%m-%d %H:%M'), 1, 'admin', CURRENT, 'admin', CURRENT);

INSERT INTO 'informix'.project_phase(project_phase_id, project_id, phase_type_id, phase_status_id, scheduled_start_time, scheduled_end_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date) VALUES(9, 1002, 6, 1, TO_DATE('2010-01-13 09:25', '%Y-%m-%d %H:%M'), TO_DATE('2010-01-15 09:25', '%Y-%m-%d %H:%M'), TO_DATE('2010-01-15 16:24', '%Y-%m-%d %H:%M'), 1, 'admin', CURRENT, 'admin', CURRENT);
INSERT INTO 'informix'.project_phase(project_phase_id, project_id, phase_type_id, phase_status_id, scheduled_start_time, scheduled_end_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date) VALUES(10, 1004, 6, 1, TO_DATE('2010-01-21 09:47', '%Y-%m-%d %H:%M'), TO_DATE('2010-01-23 09:47', '%Y-%m-%d %H:%M'), TO_DATE('2010-01-24 21:48', '%Y-%m-%d %H:%M'), 1, 'admin', CURRENT, 'admin', CURRENT);

INSERT INTO 'informix'.project_result(user_id, project_id, passed_review_ind) VALUES(123456, 1001, 0);
INSERT INTO 'informix'.project_result(user_id, project_id, passed_review_ind) VALUES(123456, 1002, 1);
INSERT INTO 'informix'.project_result(user_id, project_id, passed_review_ind) VALUES(123456, 1003, 0);
INSERT INTO 'informix'.project_result(user_id, project_id, passed_review_ind) VALUES(123456, 1004, 1);
INSERT INTO 'informix'.project_result(user_id, project_id, passed_review_ind) VALUES(123456, 1005, 0);

INSERT INTO 'informix'.upload(upload_id, project_id, resource_id, upload_type_id, upload_status_id, parameter, create_user, create_date, modify_user, modify_date) VALUES(1, 1002, 1, 1, 1, '', 'admin', CURRENT, 'admin', CURRENT);
INSERT INTO 'informix'.upload(upload_id, project_id, resource_id, upload_type_id, upload_status_id, parameter, create_user, create_date, modify_user, modify_date) VALUES(2, 1003, 2, 1, 1, '', 'admin', CURRENT, 'admin', CURRENT);
INSERT INTO 'informix'.upload(upload_id, project_id, resource_id, upload_type_id, upload_status_id, parameter, create_user, create_date, modify_user, modify_date) VALUES(3, 1004, 3, 1, 1, '', 'admin', CURRENT, 'admin', CURRENT);


INSERT INTO 'informix'.submission(submission_id, upload_id, submission_status_id, final_score, submission_type_id, create_user, create_date, modify_user, modify_date) VALUES(1, 1, 3, 91, 1, 'admin', CURRENT, 'admin', CURRENT);
INSERT INTO 'informix'.submission(submission_id, upload_id, submission_status_id, final_score, submission_type_id, create_user, create_date, modify_user, modify_date) VALUES(2, 2, 2, 50, 1, 'admin', CURRENT, 'admin', CURRENT);
INSERT INTO 'informix'.submission(submission_id, upload_id, submission_status_id, final_score, submission_type_id, create_user, create_date, modify_user, modify_date) VALUES(3, 3, 3, 93, 1, 'admin', CURRENT, 'admin', CURRENT);

INSERT INTO 'informix'.resource(resource_id, resource_role_id, create_user, create_date, modify_user, modify_date) VALUES(1, 1, 'admin', CURRENT, 'admin', CURRENT);
INSERT INTO 'informix'.resource(resource_id, resource_role_id, create_user, create_date, modify_user, modify_date) VALUES(2, 2, 'admin', CURRENT, 'admin', CURRENT);
INSERT INTO 'informix'.resource(resource_id, resource_role_id, create_user, create_date, modify_user, modify_date) VALUES(3, 3, 'admin', CURRENT, 'admin', CURRENT);

INSERT INTO 'informix'.resource_info(resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date) VALUES(1, 1, 123456, 'admin', CURRENT, 'admin', CURRENT);
INSERT INTO 'informix'.resource_info(resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date) VALUES(2, 1, 123456, 'admin', CURRENT, 'admin', CURRENT);
INSERT INTO 'informix'.resource_info(resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date) VALUES(3, 1, 123456, 'admin', CURRENT, 'admin', CURRENT);
