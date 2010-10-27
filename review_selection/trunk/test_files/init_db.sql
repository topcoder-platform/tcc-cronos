INSERT INTO project_type_lu (project_type_id, name, description, create_user, create_date, modify_user, modify_date)
VALUES (1, 'More', 'More projects of this type', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_type_lu (project_type_id, name, description, create_user, create_date, modify_user, modify_date)
VALUES (2, 'Less', 'Less projects of this type', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_type_lu (project_type_id, name, description, create_user, create_date, modify_user, modify_date)
VALUES (3, 'No', 'No project of this type', 'System', CURRENT, 'System', CURRENT);

INSERT INTO project_category_lu (project_category_id, project_type_id, name, description, create_user, create_date, modify_user, modify_date)
VALUES (1, 1, 'Java', 'Java', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_category_lu (project_category_id, project_type_id, name, description, create_user, create_date, modify_user, modify_date)
VALUES (2, 1, 'DotNet', 'DotNet', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_category_lu (project_category_id, project_type_id, name, description, create_user, create_date, modify_user, modify_date)
VALUES (3, 2, 'C++', 'C++', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_category_lu (project_category_id, project_type_id, name, description, create_user, create_date, modify_user, modify_date)
VALUES (4, 1, 'Java', 'Java', 'System', CURRENT, 'System', CURRENT);

INSERT INTO project_info_type_lu (project_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
VALUES (1, 'name', 'name', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_info_type_lu (project_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
VALUES (2, 'designer', 'designer', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_info_type_lu (project_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
VALUES (3, 'developer', 'developer', 'System', CURRENT, 'System', CURRENT);

INSERT INTO project (project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date)
VALUES (1, 7, 1, 'A', CURRENT, 'System', CURRENT);

INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
VALUES (1, 1, 'Configuration Manager', 'A', CURRENT, 'System', CURRENT);
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
VALUES (1, 2, 'isv', 'A', CURRENT, 'A', CURRENT);
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
VALUES (1, 3, 'WishingBone', 'A', CURRENT, 'A', CURRENT);


INSERT INTO phase_type_lu (phase_type_id, name, description, create_user, create_date, modify_user, modify_date)
VALUES (3, 'screening', 'screening', 'System', CURRENT, 'System', CURRENT);
INSERT INTO phase_type_lu (phase_type_id, name, description, create_user, create_date, modify_user, modify_date)
VALUES (14, 'primaryReviewEvaluation', 'primaryReviewEvaluation', 'System', CURRENT, 'System', CURRENT);
INSERT INTO phase_type_lu (phase_type_id, name, description, create_user, create_date, modify_user, modify_date)
VALUES (17, 'primaryReviewAppealsResponse', 'primaryReviewAppealsResponse', 'System', CURRENT, 'System', CURRENT);
INSERT INTO phase_type_lu (phase_type_id, name, description, create_user, create_date, modify_user, modify_date)
VALUES (10, 'finalReview', 'finalReview', 'System', CURRENT, 'System', CURRENT);
INSERT INTO phase_type_lu (phase_type_id, name, description, create_user, create_date, modify_user, modify_date)
VALUES (18, 'aggregationReview', 'aggregationReview', 'System', CURRENT, 'System', CURRENT);
INSERT INTO phase_type_lu (phase_type_id, name, description, create_user, create_date, modify_user, modify_date)
VALUES (13, 'secondaryReviewerReview', 'secondaryReviewerReview', 'System', CURRENT, 'System', CURRENT);

INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, scheduled_start_time, scheduled_end_time, duration, create_user, create_date, modify_user, modify_date, actual_start_time, actual_end_time)
VALUES (1, 1, 3, 3, '2010-10-9 20:0:0', '2010-10-10 20:0:0', 24, 'System', CURRENT, 'System', CURRENT, '2010-10-9 20:0:0','2010-10-10 20:0:0');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, scheduled_start_time, scheduled_end_time, duration, create_user, create_date, modify_user, modify_date, actual_start_time, actual_end_time)
VALUES (2, 1, 13, 3, '2010-10-10 20:0:0', '2010-10-11 20:0:0', 24, 'System', CURRENT, 'System', CURRENT, '2010-10-10 20:0:0','2010-10-11 20:0:0');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, scheduled_start_time, scheduled_end_time, duration, create_user, create_date, modify_user, modify_date, actual_start_time, actual_end_time)
VALUES (3, 1, 14, 3, '2010-10-11 20:0:0', '2010-10-12 20:0:0', 24, 'System', CURRENT, 'System', CURRENT, '2010-10-11 20:0:0','2010-10-12 20:0:0');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, scheduled_start_time, scheduled_end_time, duration, create_user, create_date, modify_user, modify_date, actual_start_time, actual_end_time)
VALUES (4, 1, 17, 3, '2010-10-12 20:0:0', '2010-10-13 20:0:0', 24, 'System', CURRENT, 'System', CURRENT, '2010-10-12 20:0:0','2010-10-13 20:0:0');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, scheduled_start_time, scheduled_end_time, duration, create_user, create_date, modify_user, modify_date, actual_start_time, actual_end_time)
VALUES (5, 1, 18, 3, '2010-10-13 20:0:0', '2010-10-14 20:0:0', 24, 'System', CURRENT, 'System', CURRENT, '2010-10-13 20:0:0','2010-10-14 20:0:0');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, scheduled_start_time, scheduled_end_time, duration, create_user, create_date, modify_user, modify_date, actual_start_time, actual_end_time)
VALUES (6, 1, 10, 3, '2010-10-14 20:0:0', '2010-10-15 20:0:0', 24, 'System', CURRENT, 'System', CURRENT, '2010-10-14 20:0:0','2010-10-15 20:0:0');

INSERT INTO resource_role_lu (resource_role_id, name, description, create_user, create_date, modify_user, modify_date)
VALUES (16, 'secondaryReviewerResourceRoleId', 'secondaryReviewerResourceRoleId', 'System', CURRENT, 'System', CURRENT);
INSERT INTO resource_role_lu (resource_role_id, name, description, create_user, create_date, modify_user, modify_date)
VALUES (17, 'primaryReviewEvaluatorResourceRoleId', 'primaryReviewEvaluatorResourceRoleId', 'System', CURRENT, 'System', CURRENT);
INSERT INTO resource_role_lu (resource_role_id, name, description, create_user, create_date, modify_user, modify_date)
VALUES (18, 'primaryReviewAppealsResponserRoleId', 'primaryReviewAppealsResponserRoleId', 'System', CURRENT, 'System', CURRENT);
INSERT INTO resource_role_lu (resource_role_id, name, description, create_user, create_date, modify_user, modify_date)
VALUES (19, 'screenerRoleId', 'screenerRoleId', 'System', CURRENT, 'System', CURRENT);
INSERT INTO resource_role_lu (resource_role_id, name, description, create_user, create_date, modify_user, modify_date)
VALUES (20, 'aggregationReviewerRoleId', 'aggregationReviewerRoleId', 'System', CURRENT, 'System', CURRENT);
INSERT INTO resource_role_lu (resource_role_id, name, description, create_user, create_date, modify_user, modify_date)
VALUES (21, 'finalReviewerRoleId', 'finalReviewerRoleId', 'System', CURRENT, 'System', CURRENT);

INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, create_user, create_date, modify_user, modify_date)
VALUES (1, 17, 1, 3, 'System', CURRENT, 'System', CURRENT);
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, create_user, create_date, modify_user, modify_date)
VALUES (2, 16, 1, 2, 'System', CURRENT, 'System', CURRENT);
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, create_user, create_date, modify_user, modify_date)
VALUES (3, 18, 1, 4, 'System', CURRENT, 'System', CURRENT);
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, create_user, create_date, modify_user, modify_date)
VALUES (4, 19, 1, 1, 'System', CURRENT, 'System', CURRENT);
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, create_user, create_date, modify_user, modify_date)
VALUES (5, 20, 1, 5, 'System', CURRENT, 'System', CURRENT);
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, create_user, create_date, modify_user, modify_date)
VALUES (6, 21, 1, 6, 'System', CURRENT, 'System', CURRENT);


INSERT INTO deliverable_lu (deliverable_id, phase_type_id, resource_role_id, name, description, submission_type_id, required, create_user, create_date, modify_user, modify_date)
VALUES (1, 3, 19, "screening_deliverable", "screening_deliverable", NULL, 1, 'System', CURRENT, 'System', CURRENT);
INSERT INTO deliverable_lu (deliverable_id, phase_type_id, resource_role_id, name, description, submission_type_id, required, create_user, create_date, modify_user, modify_date)
VALUES (2, 14, 17, "primary_review_evaluation_deliverable", "primary_review_evaluation_deliverable", NULL, 1, 'System', CURRENT, 'System', CURRENT);
INSERT INTO deliverable_lu (deliverable_id, phase_type_id, resource_role_id, name, description, submission_type_id, required, create_user, create_date, modify_user, modify_date)
VALUES (3, 17, 18, "primary_review_appeals_response_deliverable", "primary_review_appeals_response_deliverable", NULL, 1, 'System', CURRENT, 'System', CURRENT);
INSERT INTO deliverable_lu (deliverable_id, phase_type_id, resource_role_id, name, description, submission_type_id, required, create_user, create_date, modify_user, modify_date)
VALUES (4, 10, 21, "final_review_deliverable", "final_review_deliverable", NULL, 1, 'System', CURRENT, 'System', CURRENT);
INSERT INTO deliverable_lu (deliverable_id, phase_type_id, resource_role_id, name, description, submission_type_id, required, create_user, create_date, modify_user, modify_date)
VALUES (5, 18, 20, "aggregation_review_deliverable", "aggregation_review_deliverable", NULL, 1, 'System', CURRENT, 'System', CURRENT);
INSERT INTO deliverable_lu (deliverable_id, phase_type_id, resource_role_id, name, description, submission_type_id, required, create_user, create_date, modify_user, modify_date)
VALUES (6, 13, 16, "secondary_reviewer_reivew_deliverable", "secondary_reviewer_reivew_deliverable", NULL, 1, 'System', CURRENT, 'System', CURRENT);


INSERT INTO upload_type_lu (upload_type_id, name, description, create_user, create_date, modify_user, modify_date)
VALUES (1, 'upload_type_1', 'upload_type_1', 'System', CURRENT, 'System', CURRENT);
INSERT INTO upload_status_lu (upload_status_id, name, description, create_user, create_date, modify_user, modify_date)
VALUES (1, 'upload_status_1', 'upload_status_1', 'System', CURRENT, 'System', CURRENT);
INSERT INTO upload(upload_id, project_id, resource_id, upload_type_id, upload_status_id, parameter, create_user, create_date, modify_user, modify_date)
VALUES (1, 1, 1, 1, 1, '', 'System', CURRENT, 'System', CURRENT);

INSERT INTO submission_status_lu (submission_status_id, name, description, create_user, create_date, modify_user, modify_date)
VALUES (1, 'submitted', 'submitted', 'System', CURRENT, 'System', CURRENT);
INSERT INTO submission_type_lu (submission_type_id, name, description, create_user, create_date, modify_user, modify_date)
VALUES (1, 'sub_type_1', 'sub_type_1', 'System', CURRENT, 'System', CURRENT);
INSERT INTO submission (submission_id, upload_id, submission_status_id, submission_type_id, create_user, create_date, modify_user, modify_date)
VALUES (1, 1, 1, 1, 'System', CURRENT, 'System', CURRENT);

INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
VALUES (1, 1, '1', 'System', CURRENT, 'System', CURRENT);
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
VALUES (2, 1, '2', 'System', CURRENT, 'System', CURRENT);
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
VALUES (3, 1, '1', 'System', CURRENT, 'System', CURRENT);
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
VALUES (4, 1, '1', 'System', CURRENT, 'System', CURRENT);
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
VALUES (5, 1, '2', 'System', CURRENT, 'System', CURRENT);
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
VALUES (6, 1, '1', 'System', CURRENT, 'System', CURRENT);

INSERT INTO resource_submission (resource_id, submission_id, create_user, create_date, modify_user, modify_date)
VALUES (1, 1, 'System', CURRENT, 'System', CURRENT);
INSERT INTO resource_submission (resource_id, submission_id, create_user, create_date, modify_user, modify_date)
VALUES (2, 1, 'System', CURRENT, 'System', CURRENT);
INSERT INTO resource_submission (resource_id, submission_id, create_user, create_date, modify_user, modify_date)
VALUES (3, 1, 'System', CURRENT, 'System', CURRENT);
INSERT INTO resource_submission (resource_id, submission_id, create_user, create_date, modify_user, modify_date)
VALUES (4, 1, 'System', CURRENT, 'System', CURRENT);
INSERT INTO resource_submission (resource_id, submission_id, create_user, create_date, modify_user, modify_date)
VALUES (5, 1, 'System', CURRENT, 'System', CURRENT);
INSERT INTO resource_submission (resource_id, submission_id, create_user, create_date, modify_user, modify_date)
VALUES (6, 1, 'System', CURRENT, 'System', CURRENT);


INSERT INTO scorecard_type_lu (scorecard_type_id) VALUES (1);
INSERT INTO scorecard (scorecard_id, scorecard_type_id) VALUES (1, 1);

INSERT INTO review (review_id, resource_id, submission_id, scorecard_id, committed, create_user, create_date, modify_user, modify_date)
VALUES (1, 2, 1, 1, 1, 'System', CURRENT, 'System', CURRENT);
INSERT INTO review (review_id, resource_id, submission_id, scorecard_id, committed, create_user, create_date, modify_user, modify_date)
VALUES (2, 1, 1, 1, 1, 'System', CURRENT, 'System', CURRENT);
INSERT INTO review (review_id, resource_id, submission_id, scorecard_id, committed, create_user, create_date, modify_user, modify_date)
VALUES (3, 3, 1, 1, 1, 'System', CURRENT, 'System', CURRENT);
INSERT INTO review (review_id, resource_id, submission_id, scorecard_id, committed, create_user, create_date, modify_user, modify_date)
VALUES (4, 4, 1, 1, 1, 'System', CURRENT, 'System', CURRENT);
INSERT INTO review (review_id, resource_id, submission_id, scorecard_id, committed, create_user, create_date, modify_user, modify_date)
VALUES (5, 5, 1, 1, 1, 'System', CURRENT, 'System', CURRENT);
INSERT INTO review (review_id, resource_id, submission_id, scorecard_id, committed, create_user, create_date, modify_user, modify_date)
VALUES (6, 6, 1, 1, 1, 'System', CURRENT, 'System', CURRENT);

