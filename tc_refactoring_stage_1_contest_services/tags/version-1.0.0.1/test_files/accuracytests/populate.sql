-- Common table information
-- project info types
insert into project_info_type_lu(project_info_type_id, name, description) values (1 ,'Project name', 'Project name');
insert into project_info_type_lu(project_info_type_id, name, description) values (2 ,'First Place Cost Info', 'First Place Cost Info');
insert into project_info_type_lu(project_info_type_id, name, description) values (3 ,'Reliability Bonus Info', 'Reliability Bonus Info');
insert into project_info_type_lu(project_info_type_id, name, description) values (4 ,'Digital Run Info ', 'digitalRunInfo');
insert into project_info_type_lu(project_info_type_id, name, description) values (5 ,'Payments Info', 'paymentsInfo');
insert into project_info_type_lu(project_info_type_id, name, description) values (6 ,'Digital Run Flag Info', 'digitalRunFlagInfo');

-- Phase types
INSERT INTO phase_type_lu(phase_type_id, name) VALUES(1, 'Registration');
INSERT INTO phase_type_lu(phase_type_id, name) VALUES(11, 'Submission');
INSERT INTO phase_type_lu(phase_type_id, name) VALUES(2, 'Screening');
INSERT INTO phase_type_lu(phase_type_id, name) VALUES(3, 'Final Review');
INSERT INTO phase_type_lu(phase_type_id, name) VALUES(4, 'Approval');

-- project status
insert into project_status_lu(project_status_id, name, description) values (1, 'Active', 'Active status');
insert into project_status_lu(project_status_id, name, description) values (2, 'Passed', 'Not Active status');

-- categories
-- "Architecture" project category of type "Design"
INSERT INTO project_category_lu(project_category_id, name, project_group_category_lu_project_group_category_id) VALUES(1, 'Architecture', 1);
-- "Architecture" project category of type "Design"
INSERT INTO project_category_lu(project_category_id, name, project_group_category_lu_project_group_category_id) VALUES(2, 'Component Design', 1);
-- group categories
-- "Design" from catalog "Competitions"
INSERT INTO project_group_category_lu(project_group_category_id, name, project_catalog_lu_project_type_id) VALUES(1, 'Design',  1);

-- catalogs
--  "Competitions"
INSERT INTO project_catalog_lu(project_catalog_id, name) VALUES(1, 'Competitions');


-- Projects
-- project1 with "Active" status id and  project category "Design"
INSERT INTO project(project_id, project_status_id, project_category_id) VALUES(1, 1, 1);
-- project2 with "Active" status id and  project category "Design"
INSERT INTO project(project_id, project_status_id, project_category_id) VALUES(2, 1, 2);
-- project2 with "Passed" status id and  project category "Design"
INSERT INTO project(project_id, project_status_id, project_category_id) VALUES(3, 2, 1);
-- project2 with "Passed" status id and  project category "Design"
INSERT INTO project(project_id, project_status_id, project_category_id) VALUES(4, 2, 2);




-- project info
-- project1, digital run is ON
INSERT INTO project_info(project_id, project_info_type_id, value) VALUES(1, 1, 'Project1');
INSERT INTO project_info(project_id, project_info_type_id, value) VALUES(1, 2, '500.0');
INSERT INTO project_info(project_id, project_info_type_id, value) VALUES(1, 3, '0.20');
INSERT INTO project_info(project_id, project_info_type_id, value) VALUES(1, 4, '225');
INSERT INTO project_info(project_id, project_info_type_id, value) VALUES(1, 5, '250');
INSERT INTO project_info(project_id, project_info_type_id, value) VALUES(1, 6, 'On');

-- project2, digital run is OFF
INSERT INTO project_info(project_id, project_info_type_id, value) VALUES(2, 1, 'Project2');
INSERT INTO project_info(project_id, project_info_type_id, value) VALUES(2, 2, '800.0');
INSERT INTO project_info(project_id, project_info_type_id, value) VALUES(2, 3, '0.10');
INSERT INTO project_info(project_id, project_info_type_id, value) VALUES(2, 4, '340');
INSERT INTO project_info(project_id, project_info_type_id, value) VALUES(2, 5, '300');
INSERT INTO project_info(project_id, project_info_type_id, value) VALUES(2, 6, 'Off');
INSERT INTO project_info(project_id, project_info_type_id, value) VALUES(2, 7, 'winnerProject2');

-- project3, digital run is ON
INSERT INTO project_info(project_id, project_info_type_id, value) VALUES(3, 1, 'Project3');
INSERT INTO project_info(project_id, project_info_type_id, value) VALUES(3, 2, '600.0');
INSERT INTO project_info(project_id, project_info_type_id, value) VALUES(3, 3, '0.20');
INSERT INTO project_info(project_id, project_info_type_id, value) VALUES(3, 4, '225');
INSERT INTO project_info(project_id, project_info_type_id, value) VALUES(3, 5, '250');
INSERT INTO project_info(project_id, project_info_type_id, value) VALUES(3, 6, 'On');
INSERT INTO project_info(project_id, project_info_type_id, value) VALUES(3, 7, 'winnerProject3');


-- project4, digital run is OFF
INSERT INTO project_info(project_id, project_info_type_id, value) VALUES(4, 1, 'Project4');
INSERT INTO project_info(project_id, project_info_type_id, value) VALUES(4, 2, '800.0');
INSERT INTO project_info(project_id, project_info_type_id, value) VALUES(4, 3, '0.10');
INSERT INTO project_info(project_id, project_info_type_id, value) VALUES(4, 4, '340');
INSERT INTO project_info(project_id, project_info_type_id, value) VALUES(4, 5, '300');
INSERT INTO project_info(project_id, project_info_type_id, value) VALUES(4, 6, 'Off');
INSERT INTO project_info(project_id, project_info_type_id, value) VALUES(4, 7, 'winnerProject4');
INSERT INTO project_info(project_id, project_info_type_id, value) VALUES(4, 8, 'secondProject4');




-- uploads
-- uploads for project 1
INSERT INTO upload(upload_id, project_id) VALUES(1, 1);
INSERT INTO upload(upload_id, project_id) VALUES(2, 1);
INSERT INTO upload(upload_id, project_id) VALUES(3, 1);
INSERT INTO upload(upload_id, project_id) VALUES(4, 1);
-- uploads for project 2
INSERT INTO upload(upload_id, project_id) VALUES(5, 2);
-- uploads for project 3
INSERT INTO upload(upload_id, project_id) VALUES(6, 3);
-- uploads for project 4
INSERT INTO upload(upload_id, project_id) VALUES(7, 4);
INSERT INTO upload(upload_id, project_id) VALUES(8, 4);

-- submissions
-- submission for project 1 that has active submission status
INSERT INTO submission(submission_id, upload_id, submission_status_id, screening_score, submission_type_id) VALUES(1, 1, 1, 88.8, 1);
-- submission for project 1 that has failed screening submission status
INSERT INTO submission(submission_id, upload_id, submission_status_id, screening_score, submission_type_id) VALUES(2, 2, 2, 55.5, 1);
-- submission for project 1 that has failed review submission status
INSERT INTO submission(submission_id, upload_id, submission_status_id, screening_score, submission_type_id) VALUES(3, 3, 3, 88.8, 1);
-- submission for project 1 that has no wining submission status
INSERT INTO submission(submission_id, upload_id, submission_status_id, screening_score, submission_type_id) VALUES(4, 4, 4, 99.99, 1);

-- submission for project 2 that has active submission status
INSERT INTO submission(submission_id, upload_id, submission_status_id, screening_score, submission_type_id) VALUES(5, 5, 1, 88.8, 1);

-- submission for project 3 that has active submission status
INSERT INTO submission(submission_id, upload_id, submission_status_id, screening_score, submission_type_id) VALUES(6, 6, 1, 88.8, 1);
-- submission for project 4 that has active submission status
INSERT INTO submission(submission_id, upload_id, submission_status_id, screening_score, submission_type_id) VALUES(7, 7, 1, 88.8, 1);
-- didn't pass screening
INSERT INTO submission(submission_id, upload_id, submission_status_id, screening_score, submission_type_id) VALUES(8, 8, 1, 55.5, 1);

-- resources
-- 3 registrants for project 1, screening phase
INSERT INTO resource(resource_id, resource_role_id, project_id, project_phase_id) VALUES(1, 1, 1, 2);
INSERT INTO resource(resource_id, resource_role_id, project_id, project_phase_id) VALUES(2, 1, 1, 2);
INSERT INTO resource(resource_id, resource_role_id, project_id, project_phase_id) VALUES(3, 1, 1, 2);

-- 1 registrant for project 2, final review phase
INSERT INTO resource(resource_id, resource_role_id, project_id, project_phase_id) VALUES(5, 1, 2, 6);
-- 1 submitter for project 3, approval phase
INSERT INTO resource(resource_id, resource_role_id, project_id, project_phase_id) VALUES(6, 1, 3, 10);
-- 2 submitters for project 4, approval phase
INSERT INTO resource(resource_id, resource_role_id, project_id, project_phase_id) VALUES(7, 1, 4, 14);
INSERT INTO resource(resource_id, resource_role_id, project_id, project_phase_id) VALUES(8, 1, 4, 14);

-- resource info
-- -- resource_info_type_id :
-- -- 2 - externalReference
-- -- 3 - handleInfo
-- project 2 on final review
INSERT INTO resource_info(resource_id, resource_info_type_id, value) VALUES(5, 2, 'winnerProject2');
INSERT INTO resource_info(resource_id, resource_info_type_id, value) VALUES(5, 3, 'winnerHandle11');
-- resource info for pasted contestests
-- project 3
INSERT INTO resource_info(resource_id, resource_info_type_id, value) VALUES(6, 2, 'winnerProject3');
INSERT INTO resource_info(resource_id, resource_info_type_id, value) VALUES(6, 3, 'winnerHandle1');
-- project 4
INSERT INTO resource_info(resource_id, resource_info_type_id, value) VALUES(7, 2, 'winnerProject4');
INSERT INTO resource_info(resource_id, resource_info_type_id, value) VALUES(7, 3, 'winnerHandle2');
INSERT INTO resource_info(resource_id, resource_info_type_id, value) VALUES(8, 2, 'secondProject4');
INSERT INTO resource_info(resource_id, resource_info_type_id, value) VALUES(8, 3, 'secondHandle2');


-- project phases for project 1
-- registration phase
INSERT INTO project_phase(project_phase_id, project_id, phase_type_id, phase_status_id, scheduled_start_time, scheduled_end_time, actual_end_time) VALUES(1, 1, 1, 2, '2010-01-01 12:00:00', '2011-05-15 12:00:00', CURRENT);
-- submission phase
INSERT INTO project_phase(project_phase_id, project_id, phase_type_id, phase_status_id, scheduled_start_time, scheduled_end_time, actual_end_time) VALUES(51, 1, 11, 1, '2010-01-01 12:00:00', '2011-05-15 12:00:00', CURRENT);
-- screening phase
INSERT INTO project_phase(project_phase_id, project_id, phase_type_id, phase_status_id, scheduled_start_time, scheduled_end_time, actual_end_time) VALUES(2, 1, 2, 2, '2011-05-05 12:00:00', '2011-05-15 12:00:00', CURRENT);
-- final review phase
INSERT INTO project_phase(project_phase_id, project_id, phase_type_id, phase_status_id, scheduled_start_time, scheduled_end_time, actual_end_time) VALUES(3, 1, 3, 2, '2011-05-05 12:00:00', '2011-05-15 12:00:00', CURRENT);
-- project phases for project 2
-- registration phase
INSERT INTO project_phase(project_phase_id, project_id, phase_type_id, phase_status_id, scheduled_start_time, scheduled_end_time, actual_end_time) VALUES(4, 2, 1, 1, '2011-01-01 12:00:00', '2011-05-15 12:00:00', CURRENT);
-- submission phase
INSERT INTO project_phase(project_phase_id, project_id, phase_type_id, phase_status_id, scheduled_start_time, scheduled_end_time, actual_end_time) VALUES(21, 2, 11, 2, '2010-01-01 12:00:00', '2011-05-15 12:00:00', CURRENT);
-- screening phase
INSERT INTO project_phase(project_phase_id, project_id, phase_type_id, phase_status_id, scheduled_start_time, scheduled_end_time, actual_end_time) VALUES(5, 2, 2, 2, '2011-05-05 12:00:00', '2011-05-15 12:00:00', '2011-05-05 12:00:00');
-- final review phase
INSERT INTO project_phase(project_phase_id, project_id, phase_type_id, phase_status_id, scheduled_start_time, scheduled_end_time, actual_end_time) VALUES(6, 2, 3, 2, '2011-05-05 12:00:00', '2011-05-15 12:00:00', '2011-05-15 12:00:00');
-- project phases for project 3
-- registration phase
INSERT INTO project_phase(project_phase_id, project_id, phase_type_id, phase_status_id, scheduled_start_time, scheduled_end_time, actual_end_time) VALUES(7, 3, 1, 2, '2011-01-01 12:00:00', '2011-05-15 12:00:00', CURRENT);
-- submission phase
INSERT INTO project_phase(project_phase_id, project_id, phase_type_id, phase_status_id, scheduled_start_time, scheduled_end_time, actual_end_time) VALUES(31, 3, 11, 2, '2010-01-01 12:00:00', '2011-05-15 12:00:00', CURRENT);
-- screening phase
INSERT INTO project_phase(project_phase_id, project_id, phase_type_id, phase_status_id, scheduled_start_time, scheduled_end_time, actual_end_time) VALUES(8, 3, 2, 2, '2011-05-05 12:00:00', '2011-05-15 12:00:00', '2011-05-05 12:00:00');
-- final review phase
INSERT INTO project_phase(project_phase_id, project_id, phase_type_id, phase_status_id, scheduled_start_time, scheduled_end_time, actual_end_time) VALUES(9, 3, 3, 2, '2011-05-05 12:00:00', '2011-05-15 12:00:00', '2011-05-15 12:00:00');
-- approval
INSERT INTO project_phase(project_phase_id, project_id, phase_type_id, phase_status_id, scheduled_start_time, scheduled_end_time, actual_end_time) VALUES(10, 3, 4, 1, '2011-05-05 12:00:00', '2011-05-15 12:00:00', '2010-05-15 12:00:00');
-- project phases for project 4
-- registration phase
INSERT INTO project_phase(project_phase_id, project_id, phase_type_id, phase_status_id, scheduled_start_time, scheduled_end_time, actual_end_time) VALUES(11, 4, 1, 2, '2011-01-01 12:00:00', '2011-05-15 12:00:00', CURRENT);
-- submission phase
INSERT INTO project_phase(project_phase_id, project_id, phase_type_id, phase_status_id, scheduled_start_time, scheduled_end_time, actual_end_time) VALUES(41, 4, 11, 2, '2010-01-01 12:00:00', '2011-05-15 12:00:00', CURRENT);
-- screening phase
INSERT INTO project_phase(project_phase_id, project_id, phase_type_id, phase_status_id, scheduled_start_time, scheduled_end_time, actual_end_time) VALUES(12, 4, 2, 2, '2011-05-05 12:00:00', '2011-05-15 12:00:00', '2011-05-05 12:00:00');
-- final review phase
INSERT INTO project_phase(project_phase_id, project_id, phase_type_id, phase_status_id, scheduled_start_time, scheduled_end_time, actual_end_time) VALUES(13, 4, 3, 2, '2011-05-05 12:00:00', '2011-05-15 12:00:00', '2011-05-15 12:00:00');
-- approval
INSERT INTO project_phase(project_phase_id, project_id, phase_type_id, phase_status_id, scheduled_start_time, scheduled_end_time, actual_end_time) VALUES(14, 4, 4, 1, '2011-05-05 12:00:00', '2011-05-15 12:00:00', '2011-05-15 12:00:00');

-- resource submissions for project 1
INSERT INTO resource_submission(resource_id, submission_id) VALUES(1, 1);
INSERT INTO resource_submission(resource_id, submission_id) VALUES(2, 2);
INSERT INTO resource_submission(resource_id, submission_id) VALUES(3, 3);
INSERT INTO resource_submission(resource_id, submission_id) VALUES(4, 4);
-- resource submissions for project 2
INSERT INTO resource_submission(resource_id, submission_id) VALUES(5, 5);
-- resource submissions for project 3
INSERT INTO resource_submission(resource_id, submission_id) VALUES(6, 6);
-- resource submissions for project 4
INSERT INTO resource_submission(resource_id, submission_id) VALUES(7, 7);
INSERT INTO resource_submission(resource_id, submission_id) VALUES(8, 8);



-- project 1 results
INSERT INTO project_result(project_id, placed, final_score) VALUES(1, 1, 99.0);
INSERT INTO project_result(project_id, placed, final_score) VALUES(1, 2, 95.0);
INSERT INTO project_result(project_id, placed, final_score) VALUES(1, 3, 90.0);
-- project 2 results
INSERT INTO project_result(project_id, placed, final_score) VALUES(2, 1, 85.0);
INSERT INTO project_result(project_id, placed, final_score) VALUES(2, 2, 80.0);
-- project 3 results
INSERT INTO project_result(project_id, placed, final_score) VALUES(3, 1, 85.0);
INSERT INTO project_result(project_id, placed, final_score) VALUES(3, 2, 80.0);
-- project 4 results
INSERT INTO project_result(project_id, placed, final_score) VALUES(4, 1, 90.0);
INSERT INTO project_result(project_id, placed, final_score) VALUES(4, 2, 80.0);

-- project 1 is not studio
INSERT INTO contest_eligibility(contest_eligibility_id, contest_id, is_studio) VALUES(1, 1, 0);
-- project 2 is not studio
INSERT INTO contest_eligibility(contest_eligibility_id, contest_id, is_studio) VALUES(2, 2, 0);
-- project 3 is not studio
INSERT INTO contest_eligibility(contest_eligibility_id, contest_id, is_studio) VALUES(3, 3, 0);
-- project 4 is not studio
INSERT INTO contest_eligibility(contest_eligibility_id, contest_id, is_studio) VALUES(4, 4, 0);