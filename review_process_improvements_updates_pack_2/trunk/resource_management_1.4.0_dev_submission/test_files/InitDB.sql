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

INSERT INTO average_review_statistics (id, accuracy, coverage, timeline_reliability, total_evaluation_coefficient, reviewer_id,  eligibility_points, project_id,  competition_type_id, create_user, create_date, modify_user, modify_date) VALUES (1, 0.9, 0.9, 0.9, 0.9, 1, 2.0, 1, 1, 'topcoder', CURRENT, 'topcoder', CURRENT);

INSERT INTO average_review_statistics (id, accuracy, coverage, timeline_reliability, total_evaluation_coefficient, reviewer_id, eligibility_points, project_id,  competition_type_id, create_user, create_date, modify_user, modify_date) VALUES (2, 0.8, 0.8, 0.8, 0.8, 2, 2.0, 1, 1, 'topcoder', CURRENT, 'topcoder', CURRENT);

INSERT INTO average_review_statistics (id, accuracy, coverage, timeline_reliability, total_evaluation_coefficient, reviewer_id, eligibility_points, project_id,  competition_type_id, create_user, create_date, modify_user, modify_date) VALUES (3, 0.8, 0.8, 0.8, 0.8, 3, 2.0, 2, 3, 'topcoder', CURRENT, 'topcoder', CURRENT);

INSERT INTO average_review_statistics (id, accuracy, coverage, timeline_reliability, total_evaluation_coefficient, reviewer_id, eligibility_points, project_id,  competition_type_id, create_user, create_date, modify_user, modify_date) VALUES (4, 0.7, 0.7, 0.7, 0.7, 1, 2.0, 2, 1, 'topcoder', CURRENT, 'topcoder', CURRENT);

INSERT INTO history_statistics (id, accuracy, coverage, timeline_reliability, total_evaluation_coefficient, reviewer_id, eligibility_points, project_id,  competition_type_id, create_user, create_date, modify_user, modify_date) VALUES (1, 0.9, 0.9, 0.9, 0.9, 1, 2.0, 1, 1, 'topcoder', CURRENT, 'topcoder', CURRENT);

INSERT INTO history_statistics (id, accuracy, coverage, timeline_reliability, total_evaluation_coefficient, reviewer_id, eligibility_points, project_id,  competition_type_id, create_user, create_date, modify_user, modify_date) VALUES (2, 0.7, 0.8, 0.8, 0.8, 2, 2.0, 1, 1, 'topcoder', CURRENT, 'topcoder', CURRENT);

INSERT INTO history_statistics (id, accuracy, coverage, timeline_reliability, total_evaluation_coefficient, reviewer_id, eligibility_points, project_id,  competition_type_id, create_user, create_date, modify_user, modify_date) VALUES (3, 0.8, 0.8, 0.8, 0.8, 1, 2.0, 2, 1, 'topcoder', CURRENT, 'topcoder', CURRENT);

INSERT INTO history_statistics (id, accuracy, coverage, timeline_reliability, total_evaluation_coefficient, reviewer_id, eligibility_points, project_id,  competition_type_id, create_user, create_date, modify_user, modify_date) VALUES (4, 0.6, 0.8, 0.8, 0.8, 2, 2.0, 2, 1, 'topcoder', CURRENT, 'topcoder', CURRENT);

INSERT INTO history_statistics (id, accuracy, coverage, timeline_reliability, total_evaluation_coefficient, reviewer_id, eligibility_points, project_id,  competition_type_id, create_user, create_date, modify_user, modify_date) VALUES (5, 0.6, 0.8, 0.8, 0.8, 3, 2.0, 3, 1, 'topcoder', CURRENT, 'topcoder', CURRENT);
