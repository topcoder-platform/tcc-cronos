
INSERT INTO 'informix'.tc_direct_project(project_id, name, user_id, create_date) VALUES(1, 'Project 1', 1, CURRENT);
INSERT INTO 'informix'.tc_direct_project(project_id, name, user_id, create_date) VALUES(2, 'Project 2', 1, CURRENT);

INSERT INTO 'informix'.project(project_id, project_status_id,project_category_id,create_user,create_date,modify_user,modify_date,tc_direct_project_id) VALUES(1, 7, 1, 'admin', CURRENT, 'admin', CURRENT, 1);
INSERT INTO 'informix'.project(project_id, project_status_id,project_category_id,create_user,create_date,modify_user,modify_date,tc_direct_project_id) VALUES(2, 7, 1, 'admin', CURRENT, 'admin', CURRENT, 2);