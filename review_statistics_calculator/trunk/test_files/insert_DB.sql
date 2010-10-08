INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)  VALUES('Resource Id Generator', 1, 20, 0);
INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)  VALUES('Resource Role Id Generator', 1, 20, 0);
INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)  VALUES('Notification Type Id Generator', 1, 20, 0);

INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)  VALUES('upload_id_seq', 1, 20, 0);
INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)  VALUES('upload_type_id_seq', 1, 20, 0);
INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)  VALUES('upload_status_id_seq', 1, 20, 0);
INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)  VALUES('submission_id_seq', 1, 20, 0);
INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)  VALUES('submission_status_id_seq', 1, 20, 0);
INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)  VALUES('submission_type_id_seq', 1, 20, 0);

INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)  VALUES('phase_id_seq', 1, 20, 0);

INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)  VALUES('review_id_seq', 1, 20, 0);
INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)  VALUES('review_comment_id_seq', 1, 20, 0);
INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)  VALUES('review_item_id_seq', 1, 20, 0);
INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)  VALUES('review_item_comment_id_seq', 1, 20, 0);

INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)  VALUES('resource_id_seq', 1, 20, 0);
INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)  VALUES('resource_role_id_seq', 1, 20, 0);
INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)  VALUES('notification_type_id_seq', 1, 20, 0);

INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)  VALUES('project_id_seq', 1, 20, 0);
INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)  VALUES('project_audit_id_seq', 1, 20, 0);

INSERT INTO 'informix'.evaluation_type_lu(evaluation_type_id,name,description,create_user,create_date,modify_user,modify_date) VALUES
    (1, 'Serious', 'Serious - 10 points', 'System', current, 'System', current);
INSERT INTO 'informix'.evaluation_type_lu(evaluation_type_id,name,description,create_user,create_date,modify_user,modify_date) VALUES
    (2, 'Medium', 'Medium - 3 points', 'System', current, 'System', current);
INSERT INTO 'informix'.evaluation_type_lu(evaluation_type_id,name,description,create_user,create_date,modify_user,modify_date) VALUES
    (3, 'Minor', 'Minor - 1 point', 'System', current, 'System', current);
INSERT INTO 'informix'.evaluation_type_lu(evaluation_type_id,name,description,create_user,create_date,modify_user,modify_date) VALUES
    (4, 'Comment', 'Comment - 0 points', 'System', current, 'System', current);
INSERT INTO 'informix'.evaluation_type_lu(evaluation_type_id,name,description,create_user,create_date,modify_user,modify_date) VALUES
    (5, 'Combined', 'Combined - 0 points', 'System', current, 'System', current);
INSERT INTO 'informix'.evaluation_type_lu(evaluation_type_id,name,description,create_user,create_date,modify_user,modify_date) VALUES
    (6, 'Incorrect', 'Incorrect - 10 (negative) points', 'System', current, 'System', current);
 
INSERT INTO 'informix'.resource_role_lu(resource_role_id,phase_type_id,name,description,create_user,create_date,modify_user,modify_date) VALUES
    (16, 13, 'Secondary Reviewer', 'Secondary Reviewer', 'System', current, 'System', current);
INSERT INTO 'informix'.resource_role_lu(resource_role_id,phase_type_id,name,description,create_user,create_date,modify_user,modify_date) VALUES
    (17, 14, 'Primary Review Evaluator', 'Primary Review Evaluator', 'System', current, 'System', current);
    
INSERT INTO 'informix'.comment_type_lu(comment_type_id,name,description,create_user,create_date,modify_user,modify_date) VALUES
    (14, 'Primary Review Evaluation Comment', 'Primary Review Evaluation Comment', 'System', current, 'System', current);
    
INSERT INTO submission_type_lu (submission_type_id, name, description, create_user, create_date, modify_user, modify_date)
VALUES (1, "name", "description", 'System', CURRENT, 'System', CURRENT);

INSERT INTO submission_status_lu (submission_status_id, name, description, create_user, create_date, modify_user, modify_date)
VALUES (6, 'submitted', 'submitted', 'System', CURRENT, 'System', CURRENT);

INSERT INTO 'informix'.phase_type_lu(phase_type_id,name,description,create_user,create_date,modify_user,modify_date) VALUES
    (13, 'Secondary Reviewer Review', 'Secondary Reviewer Review', 'System', current, 'System', current);
INSERT INTO 'informix'.phase_type_lu(phase_type_id,name,description,create_user,create_date,modify_user,modify_date) VALUES
    (14, 'Primary Review Evaluation', 'Primary Review Evaluation', 'System', current, 'System', current);
INSERT INTO 'informix'.phase_type_lu(phase_type_id,name,description,create_user,create_date,modify_user,modify_date) VALUES
    (15, 'First Appeals', 'First Appeals', 'System', current, 'System', current);
INSERT INTO 'informix'.phase_type_lu(phase_type_id,name,description,create_user,create_date,modify_user,modify_date) VALUES
    (16, 'Second Appeals', 'Second Appeals', 'System', current, 'System', current);
INSERT INTO 'informix'.phase_type_lu(phase_type_id,name,description,create_user,create_date,modify_user,modify_date) VALUES
    (17, 'Primary Review Appeals Response', 'Primary Review Appeals Response', 'System', current, 'System', current);
INSERT INTO 'informix'.phase_type_lu(phase_type_id,name,description,create_user,create_date,modify_user,modify_date) VALUES
    (18, 'New Aggregation Review', 'New Aggregation Review', 'System', current, 'System', current);    

INSERT INTO project (project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date)
VALUES (1, 7, 2, 'System', CURRENT, 'System', CURRENT);

INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, scheduled_start_time, scheduled_end_time, duration, 
create_user, create_date, modify_user, modify_date, actual_start_time, actual_end_time)
VALUES (1, 1, 3, 3, '2010-10-9 20:00:00', '2010-10-10 20:00:00', 24, 'System', CURRENT, 'System', CURRENT, '2010-10-9 20:00:00','2010-10-10 22:30:00');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, scheduled_start_time, scheduled_end_time, duration, 
create_user, create_date, modify_user, modify_date, actual_start_time, actual_end_time)
VALUES (2, 1, 13, 3, '2010-10-10 20:00:00', '2010-10-11 20:00:00', 24, 'System', CURRENT, 'System', CURRENT, '2010-10-10 20:00:00','2010-10-11 22:30:00');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, scheduled_start_time, scheduled_end_time, duration, 
create_user, create_date, modify_user, modify_date, actual_start_time, actual_end_time)
VALUES (3, 1, 14, 3, '2010-10-11 20:0:00', '2010-10-12 20:00:00', 24, 'System', CURRENT, 'System', CURRENT, '2010-10-11 20:00:00','2010-10-12 21:12:00');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, scheduled_start_time, scheduled_end_time, duration, 
create_user, create_date, modify_user, modify_date, actual_start_time, actual_end_time)
VALUES (4, 1, 17, 3, '2010-10-12 20:00:00', '2010-10-13 20:00:00', 24, 'System', CURRENT, 'System', CURRENT, '2010-10-12 20:00:00','2010-10-13 23:30:00');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, scheduled_start_time, scheduled_end_time, duration, 
create_user, create_date, modify_user, modify_date, actual_start_time, actual_end_time)
VALUES (5, 1, 18, 3, '2010-10-13 20:0:0', '2010-10-14 20:0:0', 24, 'System', CURRENT, 'System', CURRENT, '2010-10-13 20:0:0','2010-10-14 20:0:0');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, scheduled_start_time, scheduled_end_time, duration, 
create_user, create_date, modify_user, modify_date, actual_start_time, actual_end_time)
VALUES (6, 1, 10, 3, '2010-10-14 20:0:0', '2010-10-15 20:0:0', 24, 'System', CURRENT, 'System', CURRENT, '2010-10-14 20:0:0','2010-10-15 20:0:0');

INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, create_user, create_date, modify_user, modify_date)
VALUES (1, 17, 1, 3, 'System', CURRENT, 'System', CURRENT);
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, create_user, create_date, modify_user, modify_date)
VALUES (2, 16, 1, 2, 'System', CURRENT, 'System', CURRENT);
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, create_user, create_date, modify_user, modify_date)
VALUES (3, 16, 1, 2, 'System', CURRENT, 'System', CURRENT);
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, create_user, create_date, modify_user, modify_date)
VALUES (4, 17, 1, 4, 'System', CURRENT, 'System', CURRENT);
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, create_user, create_date, modify_user, modify_date)
VALUES (5, 3, 1, 1, 'System', CURRENT, 'System', CURRENT);
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, create_user, create_date, modify_user, modify_date)
VALUES (6, 9, 1, 6, 'System', CURRENT, 'System', CURRENT);
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, create_user, create_date, modify_user, modify_date)
VALUES (7, 18, 1, 5, 'System', CURRENT, 'System', CURRENT);
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, create_user, create_date, modify_user, modify_date)
VALUES (8, 18, 1, 5, 'System', CURRENT, 'System', CURRENT);

INSERT INTO resource_submission (resource_id, submission_id, create_user, create_date, modify_user, modify_date)
VALUES (1, 1, 'System', CURRENT, 'System', CURRENT);
INSERT INTO resource_submission (resource_id, submission_id, create_user, create_date, modify_user, modify_date)
VALUES (2, 1, 'System', CURRENT, 'System', CURRENT);
INSERT INTO resource_submission (resource_id, submission_id, create_user, create_date, modify_user, modify_date)
VALUES (2, 2, 'System', CURRENT, 'System', CURRENT);
INSERT INTO resource_submission (resource_id, submission_id, create_user, create_date, modify_user, modify_date)
VALUES (2, 3, 'System', CURRENT, 'System', CURRENT);
INSERT INTO resource_submission (resource_id, submission_id, create_user, create_date, modify_user, modify_date)
VALUES (3, 4, 'System', CURRENT, 'System', CURRENT);
INSERT INTO resource_submission (resource_id, submission_id, create_user, create_date, modify_user, modify_date)
VALUES (3, 5, 'System', CURRENT, 'System', CURRENT);
INSERT INTO resource_submission (resource_id, submission_id, create_user, create_date, modify_user, modify_date)
VALUES (3, 6, 'System', CURRENT, 'System', CURRENT);
INSERT INTO resource_submission (resource_id, submission_id, create_user, create_date, modify_user, modify_date)
VALUES (4, 1, 'System', CURRENT, 'System', CURRENT);
INSERT INTO resource_submission (resource_id, submission_id, create_user, create_date, modify_user, modify_date)
VALUES (4, 2, 'System', CURRENT, 'System', CURRENT);
INSERT INTO resource_submission (resource_id, submission_id, create_user, create_date, modify_user, modify_date)
VALUES (4, 3, 'System', CURRENT, 'System', CURRENT);
INSERT INTO resource_submission (resource_id, submission_id, create_user, create_date, modify_user, modify_date)
VALUES (5, 1, 'System', CURRENT, 'System', CURRENT);
INSERT INTO resource_submission (resource_id, submission_id, create_user, create_date, modify_user, modify_date)
VALUES (5, 2, 'System', CURRENT, 'System', CURRENT);
INSERT INTO resource_submission (resource_id, submission_id, create_user, create_date, modify_user, modify_date)
VALUES (5, 3, 'System', CURRENT, 'System', CURRENT);
INSERT INTO resource_submission (resource_id, submission_id, create_user, create_date, modify_user, modify_date)
VALUES (6, 1, 'System', CURRENT, 'System', CURRENT);
INSERT INTO resource_submission (resource_id, submission_id, create_user, create_date, modify_user, modify_date)
VALUES (6, 2, 'System', CURRENT, 'System', CURRENT);
INSERT INTO resource_submission (resource_id, submission_id, create_user, create_date, modify_user, modify_date)
VALUES (6, 3, 'System', CURRENT, 'System', CURRENT);

INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
VALUES (1, 1, '1', 'System', CURRENT, 'System', CURRENT);
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
VALUES (2, 1, '2', 'System', CURRENT, 'System', CURRENT);
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
VALUES (3, 1, '3', 'System', CURRENT, 'System', CURRENT);
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
VALUES (4, 1, '1', 'System', CURRENT, 'System', CURRENT);
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
VALUES (5, 1, '1', 'System', CURRENT, 'System', CURRENT);
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
VALUES (6, 1, '1', 'System', CURRENT, 'System', CURRENT);
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
VALUES (7, 1, '2', 'System', CURRENT, 'System', CURRENT);
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
VALUES (8, 1, '3', 'System', CURRENT, 'System', CURRENT);

INSERT INTO review (review_id, resource_id, submission_id, scorecard_id, score, initial_score, committed, create_user, create_date, modify_user, modify_date)
VALUES (1, 1, 1, 1, 95, 80, 1, 'System', CURRENT, 'System', '2010-10-12 21:12:00');
INSERT INTO review (review_id, resource_id, submission_id, scorecard_id, score, initial_score, committed, create_user, create_date, modify_user, modify_date)
VALUES (3, 2, 1, 1, 99, 99, 1, 'System', CURRENT, 'System', '2010-10-11 22:30:00');
INSERT INTO review (review_id, resource_id, submission_id, scorecard_id, score, initial_score, committed, create_user, create_date, modify_user, modify_date)
VALUES (4, 2, 2, 1, 99, 93, 1, 'System', CURRENT, 'System', '2010-10-11 22:30:00');
INSERT INTO review (review_id, resource_id, submission_id, scorecard_id, score, initial_score, committed, create_user, create_date, modify_user, modify_date)
VALUES (5, 2, 3, 1, 95, 93, 1, 'System', CURRENT, 'System', '2010-10-11 22:30:00');
INSERT INTO review (review_id, resource_id, submission_id, scorecard_id, score, initial_score, committed, create_user, create_date, modify_user, modify_date)
VALUES (6, 3, 4, 1, 87, 80, 1, 'System', CURRENT, 'System', '2010-10-11 20:00:00');
INSERT INTO review (review_id, resource_id, submission_id, scorecard_id, score, initial_score, committed, create_user, create_date, modify_user, modify_date)
VALUES (7, 3, 5, 1, 87, 87, 1, 'System', CURRENT, 'System', '2010-10-11 20:00:00');
INSERT INTO review (review_id, resource_id, submission_id, scorecard_id, score, initial_score, committed, create_user, create_date, modify_user, modify_date)
VALUES (8, 3, 6, 1, 87, 87, 1, 'System', CURRENT, 'System', '2010-10-11 20:00:00');

INSERT INTO review (review_id, resource_id, submission_id, scorecard_id, score, initial_score, committed, create_user, create_date, modify_user, modify_date)
VALUES (9, 4, 1, 1, 87, 81, 1, 'System', CURRENT, 'System', '2010-10-12 21:12:00');
INSERT INTO review (review_id, resource_id, submission_id, scorecard_id, score, initial_score, committed, create_user, create_date, modify_user, modify_date)
VALUES (10, 4, 2, 1, 86, 83, 1, 'System', CURRENT, 'System', '2010-10-12 21:12:00');
INSERT INTO review (review_id, resource_id, submission_id, scorecard_id, score, initial_score, committed, create_user, create_date, modify_user, modify_date)
VALUES (11, 4, 3, 1, 86, 83, 1, 'System', CURRENT, 'System', '2010-10-12 21:12:00');
INSERT INTO review (review_id, resource_id, submission_id, scorecard_id, score, initial_score, committed, create_user, create_date, modify_user, modify_date)
VALUES (12, 4, 4, 1, 86, 83, 1, 'System', CURRENT, 'System', '2010-10-12 21:12:00');
INSERT INTO review (review_id, resource_id, submission_id, scorecard_id, score, initial_score, committed, create_user, create_date, modify_user, modify_date)
VALUES (13, 4, 5, 1, 86, 83, 1, 'System', CURRENT, 'System', '2010-10-12 21:12:00');
INSERT INTO review (review_id, resource_id, submission_id, scorecard_id, score, initial_score, committed, create_user, create_date, modify_user, modify_date)
VALUES (14, 4, 6, 1, 86, 83, 1, 'System', CURRENT, 'System', '2010-10-12 21:12:00');

INSERT INTO review (review_id, resource_id, submission_id, scorecard_id, score, initial_score, committed, create_user, create_date, modify_user, modify_date)
VALUES (15, 5, 1, 1, 87, 81, 1, 'System', CURRENT, 'System', '2010-10-10 22:30:00');
INSERT INTO review (review_id, resource_id, submission_id, scorecard_id, score, initial_score, committed, create_user, create_date, modify_user, modify_date)
VALUES (16, 5, 2, 1, 86, 83, 1, 'System', CURRENT, 'System', '2010-10-10 22:30:00');
INSERT INTO review (review_id, resource_id, submission_id, scorecard_id, score, initial_score, committed, create_user, create_date, modify_user, modify_date)
VALUES (17, 5, 3, 1, 86, 83, 1, 'System', CURRENT, 'System', '2010-10-10 22:30:00');
INSERT INTO review (review_id, resource_id, submission_id, scorecard_id, score, initial_score, committed, create_user, create_date, modify_user, modify_date)
VALUES (18, 6, 1, 1, 86, 83, 1, 'System', CURRENT, 'System', CURRENT);
INSERT INTO review (review_id, resource_id, submission_id, scorecard_id, score, initial_score, committed, create_user, create_date, modify_user, modify_date)
VALUES (19, 6, 2, 1, 86, 83, 1, 'System', CURRENT, 'System', CURRENT);
INSERT INTO review (review_id, resource_id, submission_id, scorecard_id, score, initial_score, committed, create_user, create_date, modify_user, modify_date)
VALUES (20, 6, 3, 1, 86, 83, 1, 'System', CURRENT, 'System', CURRENT);

INSERT INTO review_comment (review_comment_id, resource_id, review_id, comment_type_id, content, extra_info, sort, create_user, create_date, modify_user, modify_date, evaluation_type_id)
VALUES (1, 4, 9, 1, "content", "info", 1, 'System', CURRENT, 'System', CURRENT, 1);
INSERT INTO review_comment (review_comment_id, resource_id, review_id, comment_type_id, content, extra_info, sort, create_user, create_date, modify_user, modify_date, evaluation_type_id)
VALUES (2, 4, 9, 1, "content", "info", 1, 'System', CURRENT, 'System', CURRENT, 2);
INSERT INTO review_comment (review_comment_id, resource_id, review_id, comment_type_id, content, extra_info, sort, create_user, create_date, modify_user, modify_date, evaluation_type_id)
VALUES (3, 4, 9, 1, "content", "info", 1, 'System', CURRENT, 'System', CURRENT, 2);
INSERT INTO review_comment (review_comment_id, resource_id, review_id, comment_type_id, content, extra_info, sort, create_user, create_date, modify_user, modify_date, evaluation_type_id)
VALUES (4, 4, 9, 1, "content", "info", 1, 'System', CURRENT, 'System', CURRENT, 3);
INSERT INTO review_comment (review_comment_id, resource_id, review_id, comment_type_id, content, extra_info, sort, create_user, create_date, modify_user, modify_date, evaluation_type_id)
VALUES (5, 4, 9, 1, "content", "info", 1, 'System', CURRENT, 'System', CURRENT, 3);
INSERT INTO review_comment (review_comment_id, resource_id, review_id, comment_type_id, content, extra_info, sort, create_user, create_date, modify_user, modify_date, evaluation_type_id)
VALUES (6, 4, 9, 1, "content", "info", 1, 'System', CURRENT, 'System', CURRENT, 3);
INSERT INTO review_comment (review_comment_id, resource_id, review_id, comment_type_id, content, extra_info, sort, create_user, create_date, modify_user, modify_date, evaluation_type_id)
VALUES (7, 4, 9, 1, "content", "info", 1, 'System', CURRENT, 'System', CURRENT, 3);
INSERT INTO review_comment (review_comment_id, resource_id, review_id, comment_type_id, content, extra_info, sort, create_user, create_date, modify_user, modify_date, evaluation_type_id)
VALUES (8, 4, 9, 1, "content", "info", 1, 'System', CURRENT, 'System', CURRENT, 4);
INSERT INTO review_comment (review_comment_id, resource_id, review_id, comment_type_id, content, extra_info, sort, create_user, create_date, modify_user, modify_date, evaluation_type_id)
VALUES (9, 4, 9, 1, "content", "info", 1, 'System', CURRENT, 'System', CURRENT, 4);
INSERT INTO review_comment (review_comment_id, resource_id, review_id, comment_type_id, content, extra_info, sort, create_user, create_date, modify_user, modify_date, evaluation_type_id)
VALUES (10, 4, 9, 1, "content", "info", 1, 'System', CURRENT, 'System', CURRENT, 4);
INSERT INTO review_comment (review_comment_id, resource_id, review_id, comment_type_id, content, extra_info, sort, create_user, create_date, modify_user, modify_date, evaluation_type_id)
VALUES (11, 4, 9, 1, "content", "info", 1, 'System', CURRENT, 'System', CURRENT, 4);
INSERT INTO review_comment (review_comment_id, resource_id, review_id, comment_type_id, content, extra_info, sort, create_user, create_date, modify_user, modify_date, evaluation_type_id)
VALUES (12, 4, 9, 1, "content", "info", 1, 'System', CURRENT, 'System', CURRENT, 4);
INSERT INTO review_comment (review_comment_id, resource_id, review_id, comment_type_id, content, extra_info, sort, create_user, create_date, modify_user, modify_date, evaluation_type_id)
VALUES (13, 4, 9, 1, "content", "info", 1, 'System', CURRENT, 'System', CURRENT, 4);
INSERT INTO review_comment (review_comment_id, resource_id, review_id, comment_type_id, content, extra_info, sort, create_user, create_date, modify_user, modify_date, evaluation_type_id)
VALUES (14, 4, 9, 1, "content", "info", 1, 'System', CURRENT, 'System', CURRENT, 6);
INSERT INTO review_comment (review_comment_id, resource_id, review_id, comment_type_id, content, extra_info, sort, create_user, create_date, modify_user, modify_date, evaluation_type_id)
VALUES (15, 4, 9, 1, "content", "info", 1, 'System', CURRENT, 'System', CURRENT, 6);

INSERT INTO review_comment (review_comment_id, resource_id, review_id, comment_type_id, content, extra_info, sort, create_user, create_date, modify_user, modify_date, evaluation_type_id)
VALUES (16, 4, 10, 1, "content", "info", 1, 'System', CURRENT, 'System', CURRENT, 2);
INSERT INTO review_comment (review_comment_id, resource_id, review_id, comment_type_id, content, extra_info, sort, create_user, create_date, modify_user, modify_date, evaluation_type_id)
VALUES (17, 4, 10, 1, "content", "info", 1, 'System', CURRENT, 'System', CURRENT, 2);
INSERT INTO review_comment (review_comment_id, resource_id, review_id, comment_type_id, content, extra_info, sort, create_user, create_date, modify_user, modify_date, evaluation_type_id)
VALUES (18, 4, 10, 1, "content", "info", 1, 'System', CURRENT, 'System', CURRENT, 3);
INSERT INTO review_comment (review_comment_id, resource_id, review_id, comment_type_id, content, extra_info, sort, create_user, create_date, modify_user, modify_date, evaluation_type_id)
VALUES (19, 4, 10, 1, "content", "info", 1, 'System', CURRENT, 'System', CURRENT, 4);
INSERT INTO review_comment (review_comment_id, resource_id, review_id, comment_type_id, content, extra_info, sort, create_user, create_date, modify_user, modify_date, evaluation_type_id)
VALUES (20, 4, 10, 1, "content", "info", 1, 'System', CURRENT, 'System', CURRENT, 5);
INSERT INTO review_comment (review_comment_id, resource_id, review_id, comment_type_id, content, extra_info, sort, create_user, create_date, modify_user, modify_date, evaluation_type_id)
VALUES (21, 4, 10, 1, "content", "info", 1, 'System', CURRENT, 'System', CURRENT, 5);
INSERT INTO review_comment (review_comment_id, resource_id, review_id, comment_type_id, content, extra_info, sort, create_user, create_date, modify_user, modify_date, evaluation_type_id)
VALUES (22, 4, 10, 1, "content", "info", 1, 'System', CURRENT, 'System', CURRENT, 5);
INSERT INTO review_comment (review_comment_id, resource_id, review_id, comment_type_id, content, extra_info, sort, create_user, create_date, modify_user, modify_date, evaluation_type_id)
VALUES (23, 4, 10, 1, "content", "info", 1, 'System', CURRENT, 'System', CURRENT, 5);
INSERT INTO review_comment (review_comment_id, resource_id, review_id, comment_type_id, content, extra_info, sort, create_user, create_date, modify_user, modify_date, evaluation_type_id)
VALUES (24, 4, 10, 1, "content", "info", 1, 'System', CURRENT, 'System', CURRENT, 6);

INSERT INTO review_comment (review_comment_id, resource_id, review_id, comment_type_id, content, extra_info, sort, create_user, create_date, modify_user, modify_date, evaluation_type_id)
VALUES (25, 4, 11, 1, "content", "info", 1, 'System', CURRENT, 'System', CURRENT, 2);
INSERT INTO review_comment (review_comment_id, resource_id, review_id, comment_type_id, content, extra_info, sort, create_user, create_date, modify_user, modify_date, evaluation_type_id)
VALUES (26, 4, 11, 1, "content", "info", 1, 'System', CURRENT, 'System', CURRENT, 3);
INSERT INTO review_comment (review_comment_id, resource_id, review_id, comment_type_id, content, extra_info, sort, create_user, create_date, modify_user, modify_date, evaluation_type_id)
VALUES (27, 4, 11, 1, "content", "info", 1, 'System', CURRENT, 'System', CURRENT, 3);
INSERT INTO review_comment (review_comment_id, resource_id, review_id, comment_type_id, content, extra_info, sort, create_user, create_date, modify_user, modify_date, evaluation_type_id)
VALUES (28, 4, 11, 1, "content", "info", 1, 'System', CURRENT, 'System', CURRENT, 5);


INSERT INTO review_comment (review_comment_id, resource_id, review_id, comment_type_id, content, extra_info, sort, create_user, create_date, modify_user, modify_date, evaluation_type_id)
VALUES (29, 4, 12, 1, "content", "info", 1, 'System', CURRENT, 'System', CURRENT, 1);
INSERT INTO review_comment (review_comment_id, resource_id, review_id, comment_type_id, content, extra_info, sort, create_user, create_date, modify_user, modify_date, evaluation_type_id)
VALUES (30, 4, 12, 1, "content", "info", 1, 'System', CURRENT, 'System', CURRENT, 2);
INSERT INTO review_comment (review_comment_id, resource_id, review_id, comment_type_id, content, extra_info, sort, create_user, create_date, modify_user, modify_date, evaluation_type_id)
VALUES (31, 4, 12, 1, "content", "info", 1, 'System', CURRENT, 'System', CURRENT, 3);
INSERT INTO review_comment (review_comment_id, resource_id, review_id, comment_type_id, content, extra_info, sort, create_user, create_date, modify_user, modify_date, evaluation_type_id)
VALUES (32, 4, 12, 1, "content", "info", 1, 'System', CURRENT, 'System', CURRENT, 4);
INSERT INTO review_comment (review_comment_id, resource_id, review_id, comment_type_id, content, extra_info, sort, create_user, create_date, modify_user, modify_date, evaluation_type_id)
VALUES (33, 4, 12, 1, "content", "info", 1, 'System', CURRENT, 'System', CURRENT, 5);
INSERT INTO review_comment (review_comment_id, resource_id, review_id, comment_type_id, content, extra_info, sort, create_user, create_date, modify_user, modify_date, evaluation_type_id)
VALUES (34, 4, 12, 1, "content", "info", 1, 'System', CURRENT, 'System', CURRENT, 5);
INSERT INTO review_comment (review_comment_id, resource_id, review_id, comment_type_id, content, extra_info, sort, create_user, create_date, modify_user, modify_date, evaluation_type_id)
VALUES (35, 4, 12, 1, "content", "info", 1, 'System', CURRENT, 'System', CURRENT, 5);
INSERT INTO review_comment (review_comment_id, resource_id, review_id, comment_type_id, content, extra_info, sort, create_user, create_date, modify_user, modify_date, evaluation_type_id)
VALUES (36, 4, 12, 1, "content", "info", 1, 'System', CURRENT, 'System', CURRENT, 5);
INSERT INTO review_comment (review_comment_id, resource_id, review_id, comment_type_id, content, extra_info, sort, create_user, create_date, modify_user, modify_date, evaluation_type_id)
VALUES (37, 4, 12, 1, "content", "info", 1, 'System', CURRENT, 'System', CURRENT, 5);
INSERT INTO review_comment (review_comment_id, resource_id, review_id, comment_type_id, content, extra_info, sort, create_user, create_date, modify_user, modify_date, evaluation_type_id)
VALUES (38, 4, 12, 1, "content", "info", 1, 'System', CURRENT, 'System', CURRENT, 6);
INSERT INTO review_comment (review_comment_id, resource_id, review_id, comment_type_id, content, extra_info, sort, create_user, create_date, modify_user, modify_date, evaluation_type_id)
VALUES (39, 4, 12, 1, "content", "info", 1, 'System', CURRENT, 'System', CURRENT, 6);
INSERT INTO review_comment (review_comment_id, resource_id, review_id, comment_type_id, content, extra_info, sort, create_user, create_date, modify_user, modify_date, evaluation_type_id)
VALUES (40, 4, 12, 1, "content", "info", 1, 'System', CURRENT, 'System', CURRENT, 6);

INSERT INTO review_comment (review_comment_id, resource_id, review_id, comment_type_id, content, extra_info, sort, create_user, create_date, modify_user, modify_date, evaluation_type_id)
VALUES (41, 4, 13, 1, "content", "info", 1, 'System', CURRENT, 'System', CURRENT, 1);
INSERT INTO review_comment (review_comment_id, resource_id, review_id, comment_type_id, content, extra_info, sort, create_user, create_date, modify_user, modify_date, evaluation_type_id)
VALUES (42, 4, 13, 1, "content", "info", 1, 'System', CURRENT, 'System', CURRENT, 2);
INSERT INTO review_comment (review_comment_id, resource_id, review_id, comment_type_id, content, extra_info, sort, create_user, create_date, modify_user, modify_date, evaluation_type_id)
VALUES (43, 4, 13, 1, "content", "info", 1, 'System', CURRENT, 'System', CURRENT, 2);
INSERT INTO review_comment (review_comment_id, resource_id, review_id, comment_type_id, content, extra_info, sort, create_user, create_date, modify_user, modify_date, evaluation_type_id)
VALUES (44, 4, 13, 1, "content", "info", 1, 'System', CURRENT, 'System', CURRENT, 3);
INSERT INTO review_comment (review_comment_id, resource_id, review_id, comment_type_id, content, extra_info, sort, create_user, create_date, modify_user, modify_date, evaluation_type_id)
VALUES (45, 4, 13, 1, "content", "info", 1, 'System', CURRENT, 'System', CURRENT, 3);
INSERT INTO review_comment (review_comment_id, resource_id, review_id, comment_type_id, content, extra_info, sort, create_user, create_date, modify_user, modify_date, evaluation_type_id)
VALUES (46, 4, 13, 1, "content", "info", 1, 'System', CURRENT, 'System', CURRENT, 3);
INSERT INTO review_comment (review_comment_id, resource_id, review_id, comment_type_id, content, extra_info, sort, create_user, create_date, modify_user, modify_date, evaluation_type_id)
VALUES (47, 4, 13, 1, "content", "info", 1, 'System', CURRENT, 'System', CURRENT, 3);
INSERT INTO review_comment (review_comment_id, resource_id, review_id, comment_type_id, content, extra_info, sort, create_user, create_date, modify_user, modify_date, evaluation_type_id)
VALUES (48, 4, 13, 1, "content", "info", 1, 'System', CURRENT, 'System', CURRENT, 5);
INSERT INTO review_comment (review_comment_id, resource_id, review_id, comment_type_id, content, extra_info, sort, create_user, create_date, modify_user, modify_date, evaluation_type_id)
VALUES (49, 4, 13, 1, "content", "info", 1, 'System', CURRENT, 'System', CURRENT, 5);
INSERT INTO review_comment (review_comment_id, resource_id, review_id, comment_type_id, content, extra_info, sort, create_user, create_date, modify_user, modify_date, evaluation_type_id)
VALUES (50, 4, 13, 1, "content", "info", 1, 'System', CURRENT, 'System', CURRENT, 5);
INSERT INTO review_comment (review_comment_id, resource_id, review_id, comment_type_id, content, extra_info, sort, create_user, create_date, modify_user, modify_date, evaluation_type_id)
VALUES (51, 4, 13, 1, "content", "info", 1, 'System', CURRENT, 'System', CURRENT, 6);

INSERT INTO review_comment (review_comment_id, resource_id, review_id, comment_type_id, content, extra_info, sort, create_user, create_date, modify_user, modify_date, evaluation_type_id)
VALUES (52, 4, 14, 1, "content", "info", 1, 'System', CURRENT, 'System', CURRENT, 1);
INSERT INTO review_comment (review_comment_id, resource_id, review_id, comment_type_id, content, extra_info, sort, create_user, create_date, modify_user, modify_date, evaluation_type_id)
VALUES (53, 4, 14, 1, "content", "info", 1, 'System', CURRENT, 'System', CURRENT, 3);
INSERT INTO review_comment (review_comment_id, resource_id, review_id, comment_type_id, content, extra_info, sort, create_user, create_date, modify_user, modify_date, evaluation_type_id)
VALUES (54, 4, 14, 1, "content", "info", 1, 'System', CURRENT, 'System', CURRENT, 3);
INSERT INTO review_comment (review_comment_id, resource_id, review_id, comment_type_id, content, extra_info, sort, create_user, create_date, modify_user, modify_date, evaluation_type_id)
VALUES (55, 4, 14, 1, "content", "info", 1, 'System', CURRENT, 'System', CURRENT, 4);
INSERT INTO review_comment (review_comment_id, resource_id, review_id, comment_type_id, content, extra_info, sort, create_user, create_date, modify_user, modify_date, evaluation_type_id)
VALUES (56, 4, 14, 1, "content", "info", 1, 'System', CURRENT, 'System', CURRENT, 4);
INSERT INTO review_comment (review_comment_id, resource_id, review_id, comment_type_id, content, extra_info, sort, create_user, create_date, modify_user, modify_date, evaluation_type_id)
VALUES (57, 4, 14, 1, "content", "info", 1, 'System', CURRENT, 'System', CURRENT, 5);
INSERT INTO review_comment (review_comment_id, resource_id, review_id, comment_type_id, content, extra_info, sort, create_user, create_date, modify_user, modify_date, evaluation_type_id)
VALUES (58, 4, 14, 1, "content", "info", 1, 'System', CURRENT, 'System', CURRENT, 5);
INSERT INTO review_comment (review_comment_id, resource_id, review_id, comment_type_id, content, extra_info, sort, create_user, create_date, modify_user, modify_date, evaluation_type_id)
VALUES (59, 4, 14, 1, "content", "info", 1, 'System', CURRENT, 'System', CURRENT, 5);

INSERT INTO review_comment (review_comment_id, resource_id, review_id, comment_type_id, content, extra_info, sort, create_user, create_date, modify_user, modify_date, evaluation_type_id)
VALUES (102, 6, 16, 10, "content", "Approved", 1, 'System', CURRENT, 'System', '2010-10-15 20:0:00', 2);

INSERT 	INTO review_item (review_item_id, review_id, scorecard_question_id, answer, sort, create_user, create_date, modify_user, modify_date)
VALUES (1, 9, 1, 'answer', 1, 'System', current, 'System', current);

INSERT 	INTO review_item_comment (review_item_comment_id, resource_id, review_item_id, comment_type_id, content, extra_info, sort, create_user, create_date, modify_user, modify_date)
VALUES (1, 4, 1, 4, 'content', 'extra', 1, 'System', current, 'System', '2010-10-13 23:30:00');
INSERT 	INTO review_item_comment (review_item_comment_id, resource_id, review_item_id, comment_type_id, content, extra_info, sort, create_user, create_date, modify_user, modify_date)
VALUES (2, 4, 1, 5, 'content', 'extra', 1, 'System', current, 'System', '2010-10-13 23:30:00');

INSERT INTO scorecard (scorecard_id, scorecard_status_id, scorecard_type_id, project_category_id, name, 
version, min_score, max_score, create_user, create_date, modify_user, modify_date) 
VALUES (1, 1, 1, 7, "System", 1, 0.0, 100.0, 'System', CURRENT, 'System', CURRENT);

INSERT INTO scorecard_type_lu (name, description, scorecard_type_id, create_user, create_date, modify_user, modify_date)
VALUES ("name", "description", 1, 'System', CURRENT, 'System', CURRENT);

INSERT INTO 'informix'.deliverable_lu(deliverable_id,phase_type_id,resource_role_id,name,description,per_submission,required,create_user,create_date,modify_user,modify_date) VALUES
    (25, 13, 16, 'New Review Scorecard', 'New Review Scorecard', 1, 1, 'System', current, 'System', current);
INSERT INTO 'informix'.deliverable_lu(deliverable_id,phase_type_id,resource_role_id,name,description,per_submission,required,create_user,create_date,modify_user,modify_date) VALUES
    (26, 14, 17, 'Review Evaluation Scorecard', 'Review Evaluation Scorecard', 1, 1, 'System', current, 'System', current);
INSERT INTO 'informix'.deliverable_lu(deliverable_id,phase_type_id,resource_role_id,name,description,per_submission,required,create_user,create_date,modify_user,modify_date) VALUES
    (27, 15, 16, 'First Appeals', 'First Appeals', 1, 0, 'System', current, 'System', current);
INSERT INTO 'informix'.deliverable_lu(deliverable_id,phase_type_id,resource_role_id,name,description,per_submission,required,create_user,create_date,modify_user,modify_date) VALUES
    (28, 15, 1, 'First Appeals', 'First Appeals', 0, 0, 'System', current, 'System', current);
INSERT INTO 'informix'.deliverable_lu(deliverable_id,phase_type_id,resource_role_id,name,description,per_submission,required,create_user,create_date,modify_user,modify_date) VALUES
    (29, 16, 16, 'Second Appeals', 'Second Appeals', 1, 0, 'System', current, 'System', current);
INSERT INTO 'informix'.deliverable_lu(deliverable_id,phase_type_id,resource_role_id,name,description,per_submission,required,create_user,create_date,modify_user,modify_date) VALUES
    (30, 16, 1, 'Second Appeals', 'Second Appeals', 0, 0, 'System', current, 'System', current);
INSERT INTO 'informix'.deliverable_lu(deliverable_id,phase_type_id,resource_role_id,name,description,per_submission,required,create_user,create_date,modify_user,modify_date) VALUES
    (31, 17, 17, 'Appeals Response', 'Appeals Response', 1, 1, 'System', current, 'System', current);
INSERT INTO 'informix'.deliverable_lu(deliverable_id,phase_type_id,resource_role_id,name,description,per_submission,required,create_user,create_date,modify_user,modify_date) VALUES
    (32, 18, 16, 'Aggregation Review', 'Aggregation Review', 1, 0, 'System', current, 'System', current);
INSERT INTO 'informix'.deliverable_lu(deliverable_id,phase_type_id,resource_role_id,name,description,per_submission,required,create_user,create_date,modify_user,modify_date) VALUES
    (33, 18, 1, 'Aggregation Review', 'Aggregation Review', 0, 0, 'System', current, 'System', current);

INSERT INTO submission (submission_id, upload_id, submission_status_id, submission_type_id, create_user, create_date, modify_user, modify_date)
VALUES (1, 1, 1, 1, 'System', CURRENT, 'System', CURRENT);
INSERT INTO submission (submission_id, upload_id, submission_status_id, submission_type_id, create_user, create_date, modify_user, modify_date)
VALUES (2, 2, 1, 1, 'System', CURRENT, 'System', CURRENT);
INSERT INTO submission (submission_id, upload_id, submission_status_id, submission_type_id, create_user, create_date, modify_user, modify_date)
VALUES (3, 3, 1, 1, 'System', CURRENT, 'System', CURRENT);

INSERT INTO upload (upload_id, project_id, resource_id, upload_type_id, upload_status_id, parameter, create_user, create_date, modify_user, modify_date)
VALUES (1, 1, 1, 1, 1, "parameter", 'System', current, 'System', current);
INSERT INTO upload (upload_id, project_id, resource_id, upload_type_id, upload_status_id, parameter, create_user, create_date, modify_user, modify_date)
VALUES (2, 1, 1, 1, 1, "parameter", 'System', current, 'System', current);
INSERT INTO upload (upload_id, project_id, resource_id, upload_type_id, upload_status_id, parameter, create_user, create_date, modify_user, modify_date)
VALUES (3, 1, 1, 1, 1, "parameter", 'System', current, 'System', current);