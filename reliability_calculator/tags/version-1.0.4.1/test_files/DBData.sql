-- create 8 projects
insert into project values (1, 1, 2, 132456, current, 132456, current, 1);
insert into project values (2, 1, 2, 132456, current, 132456, current, 2);
insert into project values (3, 1, 2, 132456, current, 132456, current, 3);
insert into project values (4, 1, 2, 132456, current, 132456, current, 4);
insert into project values (5, 1, 2, 132456, current, 132456, current, 5);
insert into project values (6, 1, 2, 132456, current, 132456, current, 6);
insert into project values (7, 1, 2, 132456, current, 132456, current, 7);
-- project of other category
insert into project values (8, 1, 3, 132456, current, 132456, current, 8);

insert into contest_eligibility values (9, 9, 0);

-- set the rated flag
insert into project_info values (1, 13, 'Yes', 132456, current, 132456, current);
insert into project_info values (2, 13, 'Yes', 132456, current, 132456, current);
insert into project_info values (3, 13, 'Yes', 132456, current, 132456, current);
insert into project_info values (4, 13, 'Yes', 132456, current, 132456, current);
insert into project_info values (5, 13, 'Yes', 132456, current, 132456, current);
insert into project_info values (6, 13, 'Yes', 132456, current, 132456, current);
insert into project_info values (7, 13, 'Yes', 132456, current, 132456, current);
insert into project_info values (8, 13, 'Yes', 132456, current, 132456, current);


-- user 132456 has registered for all 8 projects
insert into component_inquiry values (1, 1, 132456, 'test 1', 1, 999, 1, 132456, 1, TO_DATE('2010-01-01 09:01', '%Y-%m-%d %H:%M'), 1);
insert into component_inquiry values (2, 1, 132456, 'test 2', 1, 999, 1, 132456, 1, TO_DATE('2010-01-01 09:02', '%Y-%m-%d %H:%M'), 2);
insert into component_inquiry values (3, 1, 132456, 'test 3', 1, 999, 1, 132456, 1, TO_DATE('2010-01-01 09:03', '%Y-%m-%d %H:%M'), 3);
insert into component_inquiry values (4, 1, 132456, 'test 4', 1, 999, 1, 132456, 1, TO_DATE('2010-01-01 09:04', '%Y-%m-%d %H:%M'), 4);
insert into component_inquiry values (5, 1, 132456, 'test 5', 1, 999, 1, 132456, 1, TO_DATE('2010-01-01 09:05', '%Y-%m-%d %H:%M'), 5);
insert into component_inquiry values (6, 1, 132456, 'test 6', 1, 999, 1, 132456, 1, TO_DATE('2010-01-01 09:06', '%Y-%m-%d %H:%M'), 6);
insert into component_inquiry values (7, 1, 132456, 'test 7', 1, 999, 1, 132456, 1, TO_DATE('2010-01-01 09:07', '%Y-%m-%d %H:%M'), 7);
insert into component_inquiry values (8, 1, 132456, 'test 8', 1, 999, 1, 132456, 1, TO_DATE('2010-01-01 09:08', '%Y-%m-%d %H:%M'), 8);

-- set up the 'resolution dates for the six projects'

-- the project 1 is in aggregation phase, passed review
insert into project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, scheduled_start_time, scheduled_end_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date) values(13, 1, 2, 3, TO_DATE('2010-01-01 09:00', '%Y-%m-%d %H:%M'), TO_DATE('2010-01-01 09:00', '%Y-%m-%d %H:%M'), TO_DATE('2010-01-01 09:00', '%Y-%m-%d %H:%M'), 4, 132456, current, 132456, current);
insert into project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, scheduled_start_time, scheduled_end_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date) values(14, 1, 3, 3, TO_DATE('2010-01-01 09:00', '%Y-%m-%d %H:%M'), TO_DATE('2010-01-01 09:00', '%Y-%m-%d %H:%M'), TO_DATE('2010-01-01 09:00', '%Y-%m-%d %H:%M'), 4, 132456, current, 132456, current);
insert into project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, scheduled_start_time, scheduled_end_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date) values(11, 1, 6, 3, TO_DATE('2010-01-01 09:00', '%Y-%m-%d %H:%M'), TO_DATE('2010-01-01 09:00', '%Y-%m-%d %H:%M'), TO_DATE('2010-01-01 09:00', '%Y-%m-%d %H:%M'), 4, 132456, current, 132456, current);
insert into project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, scheduled_start_time, scheduled_end_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date) values(12, 1, 7, 2, TO_DATE('2010-01-01 09:00', '%Y-%m-%d %H:%M'), TO_DATE('2010-01-01 09:00', '%Y-%m-%d %H:%M'), null, 4, 132456, current, 132456, current);
-- the project 2 is in submission phase
insert into project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, scheduled_start_time, scheduled_end_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date) values(21, 2, 1, 3, TO_DATE('2010-01-02 09:00', '%Y-%m-%d %H:%M'), TO_DATE('2010-01-03 09:00', '%Y-%m-%d %H:%M'), TO_DATE('2010-01-03 09:00', '%Y-%m-%d %H:%M'), 4, 132456, current, 132456, current);
insert into project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, scheduled_start_time, scheduled_end_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date) values(22, 2, 2, 2, TO_DATE('2010-01-03 09:00', '%Y-%m-%d %H:%M'), TO_DATE('2010-01-04 09:00', '%Y-%m-%d %H:%M'), null, 4, 132456, current, 132456, current);
-- the project 3 is in screening phase, 132456 submitted
insert into project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, scheduled_start_time, scheduled_end_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date) values(31, 3, 2, 3, TO_DATE('2010-01-03 09:00', '%Y-%m-%d %H:%M'), TO_DATE('2010-01-04 09:00', '%Y-%m-%d %H:%M'), TO_DATE('2010-01-04 09:00', '%Y-%m-%d %H:%M'), 4, 132456, current, 132456, current);
insert into project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, scheduled_start_time, scheduled_end_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date) values(32, 3, 3, 2, TO_DATE('2010-01-04 09:00', '%Y-%m-%d %H:%M'), TO_DATE('2010-01-05 09:00', '%Y-%m-%d %H:%M'), null, 4, 132456, current, 132456, current);
-- the project 4 is in aggregation phase, 132456 not submitted
insert into project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, scheduled_start_time, scheduled_end_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date) values(41, 4, 2, 3, TO_DATE('2010-01-03 09:00', '%Y-%m-%d %H:%M'), TO_DATE('2010-01-03 09:00', '%Y-%m-%d %H:%M'), TO_DATE('2010-01-03 09:00', '%Y-%m-%d %H:%M'), 4, 132456, current, 132456, current);
insert into project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, scheduled_start_time, scheduled_end_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date) values(42, 4, 3, 3, TO_DATE('2010-01-03 09:00', '%Y-%m-%d %H:%M'), TO_DATE('2010-01-03 09:00', '%Y-%m-%d %H:%M'), TO_DATE('2010-01-03 09:00', '%Y-%m-%d %H:%M'), 4, 132456, current, 132456, current);
insert into project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, scheduled_start_time, scheduled_end_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date) values(43, 4, 6, 3, TO_DATE('2010-01-03 09:00', '%Y-%m-%d %H:%M'), TO_DATE('2010-01-03 09:00', '%Y-%m-%d %H:%M'), TO_DATE('2010-01-03 09:00', '%Y-%m-%d %H:%M'), 4, 132456, current, 132456, current);
insert into project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, scheduled_start_time, scheduled_end_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date) values(44, 4, 7, 2, TO_DATE('2010-01-06 09:00', '%Y-%m-%d %H:%M'), TO_DATE('2010-01-06 09:00', '%Y-%m-%d %H:%M'), null, 4, 132456, current, 132456, current);
-- the project 5 is in review phase, 132456 passed screening
insert into project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, scheduled_start_time, scheduled_end_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date) values(53, 5, 2, 3, TO_DATE('2010-01-04 09:00', '%Y-%m-%d %H:%M'), TO_DATE('2010-01-05 09:00', '%Y-%m-%d %H:%M'), TO_DATE('2010-01-05 09:00', '%Y-%m-%d %H:%M'), 4, 132456, current, 132456, current);
insert into project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, scheduled_start_time, scheduled_end_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date) values(51, 5, 3, 3, TO_DATE('2010-01-04 09:00', '%Y-%m-%d %H:%M'), TO_DATE('2010-01-05 09:00', '%Y-%m-%d %H:%M'), TO_DATE('2010-01-05 09:00', '%Y-%m-%d %H:%M'), 4, 132456, current, 132456, current);
insert into project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, scheduled_start_time, scheduled_end_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date) values(52, 5, 4, 2, TO_DATE('2010-01-05 09:00', '%Y-%m-%d %H:%M'), TO_DATE('2010-01-06 09:00', '%Y-%m-%d %H:%M'), null, 4, 132456, current, 132456, current);
-- the project 6 is in aggregation phase, 132456 failed screening
insert into project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, scheduled_start_time, scheduled_end_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date) values(63, 6, 2, 3, TO_DATE('2010-01-04 09:00', '%Y-%m-%d %H:%M'), TO_DATE('2010-01-04 09:00', '%Y-%m-%d %H:%M'), TO_DATE('2010-01-04 09:00', '%Y-%m-%d %H:%M'), 4, 132456, current, 132456, current);
insert into project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, scheduled_start_time, scheduled_end_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date) values(61, 6, 3, 3, TO_DATE('2010-01-04 09:00', '%Y-%m-%d %H:%M'), TO_DATE('2010-01-04 09:00', '%Y-%m-%d %H:%M'), TO_DATE('2010-01-04 09:00', '%Y-%m-%d %H:%M'), 4, 132456, current, 132456, current);
insert into project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, scheduled_start_time, scheduled_end_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date) values(62, 6, 4, 3, TO_DATE('2010-01-05 09:00', '%Y-%m-%d %H:%M'), TO_DATE('2010-01-05 09:00', '%Y-%m-%d %H:%M'), null, 4, 132456, current, 132456, current);
insert into project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, scheduled_start_time, scheduled_end_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date) values(64, 6, 6, 3, TO_DATE('2010-01-05 09:00', '%Y-%m-%d %H:%M'), TO_DATE('2010-01-05 09:00', '%Y-%m-%d %H:%M'), TO_DATE('2010-01-05 09:00', '%Y-%m-%d %H:%M'), 4, 132456, current, 132456, current);
insert into project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, scheduled_start_time, scheduled_end_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date) values(65, 6, 7, 2, TO_DATE('2010-01-06 09:00', '%Y-%m-%d %H:%M'), TO_DATE('2010-01-06 09:00', '%Y-%m-%d %H:%M'), null, 4, 132456, current, 132456, current);
-- the project 7 is in aggregation phase, 132456 passed review
insert into project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, scheduled_start_time, scheduled_end_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date) values(73, 7, 2, 3, TO_DATE('2010-01-04 09:00', '%Y-%m-%d %H:%M'), TO_DATE('2010-01-05 09:00', '%Y-%m-%d %H:%M'), TO_DATE('2010-01-05 09:00', '%Y-%m-%d %H:%M'), 4, 132456, current, 132456, current);
insert into project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, scheduled_start_time, scheduled_end_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date) values(74, 7, 3, 3, TO_DATE('2010-01-04 09:00', '%Y-%m-%d %H:%M'), TO_DATE('2010-01-05 09:00', '%Y-%m-%d %H:%M'), TO_DATE('2010-01-05 09:00', '%Y-%m-%d %H:%M'), 4, 132456, current, 132456, current);
insert into project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, scheduled_start_time, scheduled_end_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date) values(71, 7, 6, 3, TO_DATE('2010-01-05 09:00', '%Y-%m-%d %H:%M'), TO_DATE('2010-01-05 09:00', '%Y-%m-%d %H:%M'), TO_DATE('2010-01-05 09:00', '%Y-%m-%d %H:%M'), 4, 132456, current, 132456, current);
insert into project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, scheduled_start_time, scheduled_end_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date) values(72, 7, 7, 2, TO_DATE('2010-01-06 09:00', '%Y-%m-%d %H:%M'), TO_DATE('2010-01-06 09:00', '%Y-%m-%d %H:%M'), null, 4, 132456, current, 132456, current);
-- the project 8 is in aggregation phase, 132456 failed review
insert into project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, scheduled_start_time, scheduled_end_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date) values(83, 8, 2, 3, TO_DATE('2010-01-04 09:00', '%Y-%m-%d %H:%M'), TO_DATE('2010-01-05 09:00', '%Y-%m-%d %H:%M'), TO_DATE('2010-01-05 09:00', '%Y-%m-%d %H:%M'), 4, 132456, current, 132456, current);
insert into project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, scheduled_start_time, scheduled_end_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date) values(84, 8, 3, 3, TO_DATE('2010-01-04 09:00', '%Y-%m-%d %H:%M'), TO_DATE('2010-01-05 09:00', '%Y-%m-%d %H:%M'), TO_DATE('2010-01-05 09:00', '%Y-%m-%d %H:%M'), 4, 132456, current, 132456, current);
insert into project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, scheduled_start_time, scheduled_end_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date) values(81, 8, 6, 3, TO_DATE('2010-01-07 09:00', '%Y-%m-%d %H:%M'), TO_DATE('2010-01-07 09:00', '%Y-%m-%d %H:%M'), TO_DATE('2010-01-07 09:01', '%Y-%m-%d %H:%M'), 4, 132456, current, 132456, current);
insert into project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, scheduled_start_time, scheduled_end_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date) values(82, 8, 7, 2, TO_DATE('2010-01-08 09:00', '%Y-%m-%d %H:%M'), TO_DATE('2010-01-08 09:00', '%Y-%m-%d %H:%M'), null, 4, 132456, current, 132456, current);

-- set up the project_result table
insert into project_result (user_id, project_id, passed_review_ind) values (132456, 1, 1);
insert into project_result (user_id, project_id, passed_review_ind) values (132456, 2, NULL);
insert into project_result (user_id, project_id, passed_review_ind) values (132456, 3, NULL);
insert into project_result (user_id, project_id, passed_review_ind) values (132456, 4, 0);
insert into project_result (user_id, project_id, passed_review_ind) values (132456, 5, NULL);
insert into project_result (user_id, project_id, passed_review_ind) values (132456, 6, 0);
insert into project_result (user_id, project_id, passed_review_ind) values (132456, 7, 1);
insert into project_result (user_id, project_id, passed_review_ind) values (132456, 8, 1);

-- creates submissions for the submitted projects
insert into resource (project_id, resource_id, resource_role_id, create_user, create_date, modify_user, modify_date) values (1, 1, 1, 132456, current, 132456, current);
insert into resource (project_id, resource_id, resource_role_id, create_user, create_date, modify_user, modify_date) values (3, 3, 1, 132456, current, 132456, current);
insert into resource (project_id, resource_id, resource_role_id, create_user, create_date, modify_user, modify_date) values (5, 5, 1, 132456, current, 132456, current);
insert into resource (project_id, resource_id, resource_role_id, create_user, create_date, modify_user, modify_date) values (6, 6, 1, 132456, current, 132456, current);
insert into resource (project_id, resource_id, resource_role_id, create_user, create_date, modify_user, modify_date) values (7, 7, 1, 132456, current, 132456, current);
insert into resource (project_id, resource_id, resource_role_id, create_user, create_date, modify_user, modify_date) values (8, 8, 1, 132456, current, 132456, current);
	
insert into resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date) values (1, 1, 132456, 132456, current, 132456, current);
insert into resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date) values (3, 1, 132456, 132456, current, 132456, current);
insert into resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date) values (5, 1, 132456, 132456, current, 132456, current);
insert into resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date) values (6, 1, 132456, 132456, current, 132456, current);
insert into resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date) values (7, 1, 132456, 132456, current, 132456, current);
insert into resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date) values (8, 1, 132456, 132456, current, 132456, current);
	
insert into upload (upload_id, project_id, resource_id, upload_type_id, upload_status_id, parameter, create_user, create_date, modify_user, modify_date) values (1, 1, 1, 1, 1, '', 132456, current, 132456, current);
insert into upload (upload_id, project_id, resource_id, upload_type_id, upload_status_id, parameter, create_user, create_date, modify_user, modify_date) values (3, 3, 3, 1, 1, '', 132456, current, 132456, current);
insert into upload (upload_id, project_id, resource_id, upload_type_id, upload_status_id, parameter, create_user, create_date, modify_user, modify_date) values (5, 5, 5, 1, 1, '', 132456, current, 132456, current);
insert into upload (upload_id, project_id, resource_id, upload_type_id, upload_status_id, parameter, create_user, create_date, modify_user, modify_date) values (6, 6, 6, 1, 1, '', 132456, current, 132456, current);
insert into upload (upload_id, project_id, resource_id, upload_type_id, upload_status_id, parameter, create_user, create_date, modify_user, modify_date) values (7, 7, 7, 1, 1, '', 132456, current, 132456, current);
insert into upload (upload_id, project_id, resource_id, upload_type_id, upload_status_id, parameter, create_user, create_date, modify_user, modify_date) values (8, 8, 8, 1, 1, '', 132456, current, 132456, current);

insert into submission (submission_id, upload_id, submission_status_id, final_score, submission_type_id, create_user, create_date, modify_user, modify_date) values (1, 1, 1, NULL, 1, 132456, current, 132456, current);
insert into submission (submission_id, upload_id, submission_status_id, final_score, submission_type_id, create_user, create_date, modify_user, modify_date) values (3, 3, 1, NULL, 1, 132456, current, 132456, current);
insert into submission (submission_id, upload_id, submission_status_id, final_score, submission_type_id, create_user, create_date, modify_user, modify_date) values (5, 5, 1, NULL, 1, 132456, current, 132456, current);
insert into submission (submission_id, upload_id, submission_status_id, final_score, submission_type_id, create_user, create_date, modify_user, modify_date) values (6, 6, 2, NULL, 1, 132456, current, 132456, current);
insert into submission (submission_id, upload_id, submission_status_id, final_score, submission_type_id, create_user, create_date, modify_user, modify_date) values (7, 7, 1, 100, 1, 132456, current, 132456, current);
insert into submission (submission_id, upload_id, submission_status_id, final_score, submission_type_id, create_user, create_date, modify_user, modify_date) values (8, 8, 3, 50, 1, 132456, current, 132456, current);

insert into project_reliability(project_id , user_id ,resolution_date ,reliability_before_resolution , reliability_after_resolution ,reliability_on_registration ,reliable_ind) values (1, 132456, current, 1.0, 0.5, 0.3, 1);
insert into project_reliability(project_id , user_id ,resolution_date ,reliability_before_resolution , reliability_after_resolution ,reliability_on_registration ,reliable_ind) values (2, 132456, current, 0.7, 0.5, 0.4, 1);
insert into project_reliability(project_id , user_id ,resolution_date ,reliability_before_resolution , reliability_after_resolution ,reliability_on_registration ,reliable_ind) values (3, 132456, current, 0.7, 0.5, 0.4, 1);
insert into project_reliability(project_id , user_id ,resolution_date ,reliability_before_resolution , reliability_after_resolution ,reliability_on_registration ,reliable_ind) values (4, 132456, current, 0.7, 0.5, 0.4, 1);
insert into project_reliability(project_id , user_id ,resolution_date ,reliability_before_resolution , reliability_after_resolution ,reliability_on_registration ,reliable_ind) values (5, 132456, current, 0.7, 0.5, 0.4, 1);
insert into project_reliability(project_id , user_id ,resolution_date ,reliability_before_resolution , reliability_after_resolution ,reliability_on_registration ,reliable_ind) values (6, 132456, current, 0.7, 0.5, 0.4, 1);
insert into project_reliability(project_id , user_id ,resolution_date ,reliability_before_resolution , reliability_after_resolution ,reliability_on_registration ,reliable_ind) values (7, 132456, current, 0.7, 0.5, 0.4, 1);
-- project of other category
insert into project_reliability(project_id , user_id ,resolution_date ,reliability_before_resolution , reliability_after_resolution ,reliability_on_registration ,reliable_ind) values (8, 132456, current, 0.4, 0.4, 0.4, 1);
