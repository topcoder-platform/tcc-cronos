# Database 'tcs_catalog';
INSERT INTO tcs_catalog:'informix'.late_deliverable(late_deliverable_id, project_phase_id, resource_id, deliverable_id, deadline, create_date, forgive_ind, last_notified) VALUES(1, 101, 1001, 4, TO_DATE('2010-11-22 09:05:00', '%Y-%m-%d %H:%M:%S'), TO_DATE('2010-11-20 09:05:00', '%Y-%m-%d %H:%M:%S'), 0, TO_DATE('2010-11-20 09:05:00', '%Y-%m-%d %H:%M:%S'));
INSERT INTO tcs_catalog:'informix'.late_deliverable(late_deliverable_id, project_phase_id, resource_id, deliverable_id, deadline, create_date, forgive_ind, last_notified) VALUES(2, 102, 1002, 3, TO_DATE('2010-11-25 12:00:00', '%Y-%m-%d %H:%M:%S'), TO_DATE('2010-11-23 12:00:00', '%Y-%m-%d %H:%M:%S'), 0, TO_DATE('2010-11-23 12:00:00', '%Y-%m-%d %H:%M:%S'));

INSERT INTO tcs_catalog:'informix'.project(project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date, tc_direct_project_id) VALUES(100000, 7, 1, 'admin', CURRENT, 'admin', CURRENT, 1);
INSERT INTO tcs_catalog:'informix'.project(project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date, tc_direct_project_id) VALUES(100001, 1, 1, 'admin', CURRENT, 'admin', CURRENT, 2);

INSERT INTO tcs_catalog:'informix'.project_phase(project_phase_id, project_id, phase_type_id, phase_status_id, scheduled_start_time, scheduled_end_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date) VALUES(101, 100000, 2, 3, TO_DATE('2010-11-22 09:05:00', '%Y-%m-%d %H:%M:%S'), TO_DATE('2010-11-22 09:05:00', '%Y-%m-%d %H:%M:%S'), TO_DATE('2010-11-23 09:05:00', '%Y-%m-%d %H:%M:%S'), 4, 'admin', CURRENT, 'admin', CURRENT);
INSERT INTO tcs_catalog:'informix'.project_phase(project_phase_id, project_id, phase_type_id, phase_status_id, scheduled_start_time, scheduled_end_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date) VALUES(102, 100001, 2, 3, TO_DATE('2010-11-25 12:00:00', '%Y-%m-%d %H:%M:%S'), TO_DATE('2010-11-25 12:00:00', '%Y-%m-%d %H:%M:%S'), TO_DATE('2010-11-26 12:00:00', '%Y-%m-%d %H:%M:%S'), 4, 'admin', CURRENT, 'admin', CURRENT);

INSERT INTO tcs_catalog:'informix'.resource(resource_id, resource_role_id, project_id, project_phase_id, create_user, create_date, modify_user, modify_date) VALUES(1001, 4, 100000, 101, 'admin', CURRENT, 'admin', CURRENT);
INSERT INTO tcs_catalog:'informix'.resource(resource_id, resource_role_id, project_id, project_phase_id, create_user, create_date, modify_user, modify_date) VALUES(1002, 3, 100001, 102, 'admin', CURRENT, 'admin', CURRENT);
INSERT INTO tcs_catalog:'informix'.resource(resource_id, resource_role_id, project_id, project_phase_id, create_user, create_date, modify_user, modify_date) VALUES(1003, 13, 100000, NULL, 'admin', CURRENT, 'admin', CURRENT);

INSERT INTO tcs_catalog:'informix'.resource_info(resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date) VALUES(1001, 1, 1, 'admin', CURRENT, 'admin', CURRENT);
INSERT INTO tcs_catalog:'informix'.resource_info(resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date) VALUES(1002, 1, 2, 'admin', CURRENT, 'admin', CURRENT);
INSERT INTO tcs_catalog:'informix'.resource_info(resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date) VALUES(1003, 1, 3, 'admin', CURRENT, 'admin', CURRENT);

INSERT INTO tcs_catalog:'informix'.resource_info(resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date) VALUES(1001, 2, 'user1', 'admin', CURRENT, 'admin', CURRENT);
INSERT INTO tcs_catalog:'informix'.resource_info(resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date) VALUES(1002, 2, 'user2', 'admin', CURRENT, 'admin', CURRENT);
INSERT INTO tcs_catalog:'informix'.resource_info(resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date) VALUES(1003, 2, 'user2', 'admin', CURRENT, 'admin', CURRENT);

# Database 'corporate_oltp';
INSERT INTO corporate_oltp:'informix'.tc_direct_project(project_id, name, user_id, create_date) VALUES(1, 'Project 1', 3, CURRENT);
INSERT INTO corporate_oltp:'informix'.tc_direct_project(project_id, name, user_id, create_date) VALUES(2, 'Project 2', 3, CURRENT);

INSERT INTO corporate_oltp:'informix'.user_permission_grant(user_permission_grant_id, user_id, resource_id, permission_type_id) VALUES(1, 3, 1, 1);
INSERT INTO corporate_oltp:'informix'.user_permission_grant(user_permission_grant_id, user_id, resource_id, permission_type_id) VALUES(2, 3, 2, 1);