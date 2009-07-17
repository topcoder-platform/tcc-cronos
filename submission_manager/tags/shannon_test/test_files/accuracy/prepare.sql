INSERT INTO submission_type_lu (submission_type_id, submission_type_desc) VALUES (1, 'Final Fix');
INSERT INTO submission_status_lu (submission_status_id, submission_status_desc) VALUES (1, 'reviewed');
INSERT INTO submission_status_lu (submission_status_id, submission_status_desc) VALUES (2, 'deleted');
INSERT INTO path (path_id, path, modify_date) VALUES (1, '/root/', '2008-03-26 12:53:45');
INSERT INTO contest_status_lu (contest_status_id, contest_status_desc) VALUES (1, 'competition');
INSERT INTO file_type_lu (file_type_id, file_type_desc, sort, image_file_ind, extension) VALUES (1, 'Requirement Specification', 1, 1, ".pdf");
INSERT INTO prize_type_lu (prize_type_id, prize_type_desc) VALUES (1, 'Winner');
INSERT INTO payment_status (payment_status_id, payments_status_desc) VALUES (1, 'hold');
INSERT INTO payment_status (payment_status_id, payments_status_desc) VALUES (2, 'deleted');
INSERT INTO review_status_lu (review_status_id, review_status_desc) VALUES (1, 'hold');
INSERT INTO review_status_lu (review_status_id, review_status_desc) VALUES (2, 'deleted');
INSERT INTO contest_channel_lu (contest_channel_id, file_type_id) VALUES (1, 1);
INSERT INTO contest_type_lu (contest_type_id, contest_type_desc) VALUES (1, 'DESIGN');
INSERT INTO contest (contest_id, contest_channel_id, name, contest_type_id, project_id, tc_direct_project_id, contest_status_id, forum_id, event_id, start_time, end_date, winner_annoucement_deadline, creator_user_id) VALUES (1, 1, 'Submission Mananger', 1, 1, 1, 1, 1, 1, '2008-03-13 09:00:00', '2008-03-28 09:00:00', '2008-04-16 09:00:00', 1);