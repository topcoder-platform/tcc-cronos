INSERT INTO project (project_id) VALUES (1);
INSERT INTO project (project_id) VALUES (2);
INSERT INTO project (project_id) VALUES (3);
INSERT INTO project (project_id) VALUES (4);
INSERT INTO project (project_id) VALUES (5);

INSERT INTO rboard_user (user_id, project_type_id, catalog_id, status_id, immune_ind) VALUES (1, 1, 1, 1, 1);
INSERT INTO rboard_user (user_id, project_type_id, catalog_id, status_id, immune_ind) VALUES (2, 1, 1, 1, 1);
INSERT INTO rboard_user (user_id, project_type_id, catalog_id, status_id, immune_ind) VALUES (3, 2, 1, 1, 1);
INSERT INTO rboard_user (user_id, project_type_id, catalog_id, status_id, immune_ind) VALUES (4, 2, 1, 1, 1);

INSERT INTO project_type_lu (project_type_id, name, description, create_user, create_date, modify_user, modify_date, is_generic, review_system_version) VALUES (1, 'More', 'More projects of this type', 'System', CURRENT, 'System', CURRENT, 't', 1);
INSERT INTO project_type_lu (project_type_id, name, description, create_user, create_date, modify_user, modify_date, is_generic, review_system_version) VALUES (2, 'Less', 'Less projects of this type', 'System', CURRENT, 'System', CURRENT, 't', 2);
INSERT INTO project_type_lu (project_type_id, name, description, create_user, create_date, modify_user, modify_date, is_generic, review_system_version) VALUES (3, 'No', 'No project of this type', 'System', CURRENT, 'System', CURRENT, 't', 3);
