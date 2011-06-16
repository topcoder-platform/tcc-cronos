INSERT INTO project(project_id, project_status_id, project_category_id) VALUES(1, 11, 101);
INSERT INTO project(project_id, project_status_id, project_category_id) VALUES(2, 11, 102);
INSERT INTO project(project_id, project_status_id, project_category_id) VALUES(3, 11, 103);
INSERT INTO project(project_id, project_status_id, project_category_id) VALUES(4, 12, 104);
INSERT INTO project(project_id, project_status_id, project_category_id) VALUES(5, 13, 105);
INSERT INTO project(project_id, project_status_id, project_category_id) VALUES(6, 14, 106);

INSERT INTO project_category_lu(project_category_id, name, project_group_category_lu_project_group_category_id) VALUES(101, 'SubType101', 1);
INSERT INTO project_category_lu(project_category_id, name, project_group_category_lu_project_group_category_id) VALUES(102, 'SubType102', 2);
INSERT INTO project_category_lu(project_category_id, name, project_group_category_lu_project_group_category_id) VALUES(103, 'SubType103', 3);
INSERT INTO project_category_lu(project_category_id, name, project_group_category_lu_project_group_category_id) VALUES(104, 'SubType104', 4);
INSERT INTO project_category_lu(project_category_id, name, project_group_category_lu_project_group_category_id) VALUES(105, 'SubType105', 5);
INSERT INTO project_category_lu(project_category_id, name, project_group_category_lu_project_group_category_id) VALUES(106, 'SubType106', 6);
INSERT INTO project_category_lu(project_category_id, name, project_group_category_lu_project_group_category_id) VALUES(108, 'SubType108', 8);


INSERT INTO project_catalog_lu(project_catalog_id, name) VALUES(21, 'ProjectCatalog21');
INSERT INTO project_catalog_lu(project_catalog_id, name) VALUES(22, 'ProjectCatalog22');
INSERT INTO project_catalog_lu(project_catalog_id, name) VALUES(23, 'ProjectCatalog23');
INSERT INTO project_catalog_lu(project_catalog_id, name) VALUES(24, 'ProjectCatalog24');
INSERT INTO project_catalog_lu(project_catalog_id, name) VALUES(25, 'ProjectCatalog25');
INSERT INTO project_catalog_lu(project_catalog_id, name) VALUES(26, 'ProjectCatalog26');

INSERT INTO project_group_category_lu(project_group_category_id, name, project_catalog_lu_project_type_id) VALUES(1, 'Type1', 21);
INSERT INTO project_group_category_lu(project_group_category_id, name, project_catalog_lu_project_type_id) VALUES(2, 'Type2', 22);
INSERT INTO project_group_category_lu(project_group_category_id, name, project_catalog_lu_project_type_id) VALUES(3, 'Type3', 23);
INSERT INTO project_group_category_lu(project_group_category_id, name, project_catalog_lu_project_type_id) VALUES(4, 'Type4', 24);
INSERT INTO project_group_category_lu(project_group_category_id, name, project_catalog_lu_project_type_id) VALUES(5, 'Type5', 25);
INSERT INTO project_group_category_lu(project_group_category_id, name, project_catalog_lu_project_type_id) VALUES(6, 'Type6', 26);
INSERT INTO project_group_category_lu(project_group_category_id, name, project_catalog_lu_project_type_id) VALUES(7, 'Type7', 21);
INSERT INTO project_group_category_lu(project_group_category_id, name, project_catalog_lu_project_type_id) VALUES(8, 'Type1', 28);

INSERT INTO project_info(project_id, project_info_type_id, value) VALUES(1, 1, 'ProjectContestName11');
INSERT INTO project_info(project_id, project_info_type_id, value) VALUES(1, 2, '100.0');
INSERT INTO project_info(project_id, project_info_type_id, value) VALUES(1, 3, '0.10');
INSERT INTO project_info(project_id, project_info_type_id, value) VALUES(1, 4, '150.0');
INSERT INTO project_info(project_id, project_info_type_id, value) VALUES(1, 5, null);
INSERT INTO project_info(project_id, project_info_type_id, value) VALUES(1, 6, 'On');
INSERT INTO project_info(project_id, project_info_type_id, value) VALUES(1, 7, 'winnerId1');
INSERT INTO project_info(project_id, project_info_type_id, value) VALUES(1, 8, 'runnerUpId1');
INSERT INTO project_info(project_id, project_info_type_id, value) VALUES(2, 1, 'ProjectContestName21');
INSERT INTO project_info(project_id, project_info_type_id, value) VALUES(2, 2, '200.0');
INSERT INTO project_info(project_id, project_info_type_id, value) VALUES(2, 3, '0.15');
INSERT INTO project_info(project_id, project_info_type_id, value) VALUES(2, 4, null);
INSERT INTO project_info(project_id, project_info_type_id, value) VALUES(2, 5, '2000.0');
INSERT INTO project_info(project_id, project_info_type_id, value) VALUES(2, 6, 'On');
INSERT INTO project_info(project_id, project_info_type_id, value) VALUES(2, 7, 'winnerId2');
INSERT INTO project_info(project_id, project_info_type_id, value) VALUES(2, 8, 'runnerUpId2');
INSERT INTO project_info(project_id, project_info_type_id, value) VALUES(3, 1, 'ProjectContestName31');
INSERT INTO project_info(project_id, project_info_type_id, value) VALUES(3, 2, '333.3');
INSERT INTO project_info(project_id, project_info_type_id, value) VALUES(3, 3, '0.20');
INSERT INTO project_info(project_id, project_info_type_id, value) VALUES(3, 4, '350.0');
INSERT INTO project_info(project_id, project_info_type_id, value) VALUES(3, 5, '3400.0');
INSERT INTO project_info(project_id, project_info_type_id, value) VALUES(3, 6, null);
INSERT INTO project_info(project_id, project_info_type_id, value) VALUES(3, 7, 'winnerId3');
INSERT INTO project_info(project_id, project_info_type_id, value) VALUES(3, 8, 'runnerUpId3');
INSERT INTO project_info(project_id, project_info_type_id, value) VALUES(4, 1, 'ProjectContestName41');
INSERT INTO project_info(project_id, project_info_type_id, value) VALUES(4, 2, '444.4');
INSERT INTO project_info(project_id, project_info_type_id, value) VALUES(4, 3, '0.2');
INSERT INTO project_info(project_id, project_info_type_id, value) VALUES(4, 4, '450.0');
INSERT INTO project_info(project_id, project_info_type_id, value) VALUES(4, 5, '43.21');
INSERT INTO project_info(project_id, project_info_type_id, value) VALUES(4, 6, 'On');
INSERT INTO project_info(project_id, project_info_type_id, value) VALUES(5, 1, 'ProjectContestName51');
INSERT INTO project_info(project_id, project_info_type_id, value) VALUES(5, 2, null);
INSERT INTO project_info(project_id, project_info_type_id, value) VALUES(5, 3, '0.2');
INSERT INTO project_info(project_id, project_info_type_id, value) VALUES(5, 4, '450.0');
INSERT INTO project_info(project_id, project_info_type_id, value) VALUES(5, 5, '43.21');
INSERT INTO project_info(project_id, project_info_type_id, value) VALUES(5, 6, 'On');
INSERT INTO project_info(project_id, project_info_type_id, value) VALUES(6, 1, 'ProjectContestName61');
INSERT INTO project_info(project_id, project_info_type_id, value) VALUES(6, 2, '444.4');
INSERT INTO project_info(project_id, project_info_type_id, value) VALUES(6, 3, 'abc');
INSERT INTO project_info(project_id, project_info_type_id, value) VALUES(6, 4, '450.0');
INSERT INTO project_info(project_id, project_info_type_id, value) VALUES(6, 5, '43.21');
INSERT INTO project_info(project_id, project_info_type_id, value) VALUES(6, 6, 'On');

INSERT INTO resource_info(resource_id, resource_info_type_id, value) VALUES(51, 1, 'winnerId1');
INSERT INTO resource_info(resource_id, resource_info_type_id, value) VALUES(53, 1, 'winnerId2');
INSERT INTO resource_info(resource_id, resource_info_type_id, value) VALUES(54, 1, 'winnerId3');
INSERT INTO resource_info(resource_id, resource_info_type_id, value) VALUES(54, 2, '333.3');
INSERT INTO resource_info(resource_id, resource_info_type_id, value) VALUES(55, 1, 'runnerUpId1');
INSERT INTO resource_info(resource_id, resource_info_type_id, value) VALUES(56, 1, 'runnerUpId2');
INSERT INTO resource_info(resource_id, resource_info_type_id, value) VALUES(57, 1, 'runnerUpId3');

INSERT INTO upload(upload_id, project_id) VALUES(31, 1);
INSERT INTO upload(upload_id, project_id) VALUES(32, 2);
INSERT INTO upload(upload_id, project_id) VALUES(33, 3);
INSERT INTO upload(upload_id, project_id) VALUES(34, 4);
INSERT INTO upload(upload_id, project_id) VALUES(35, 5);
INSERT INTO upload(upload_id, project_id) VALUES(36, 6);


INSERT INTO submission(submission_id, upload_id, submission_status_id, screening_score, submission_type_id) VALUES(41, 31, 1, 66.6, 1);
INSERT INTO submission(submission_id, upload_id, submission_status_id, screening_score, submission_type_id) VALUES(42, 31, 2, 77.7, 1);
INSERT INTO submission(submission_id, upload_id, submission_status_id, screening_score, submission_type_id) VALUES(43, 31, 3, 88.8, 1);
INSERT INTO submission(submission_id, upload_id, submission_status_id, screening_score, submission_type_id) VALUES(44, 31, 4, 99.9, 1);
INSERT INTO submission(submission_id, upload_id, submission_status_id, screening_score, submission_type_id) VALUES(45, 32, 1, 11.1, 1);
INSERT INTO submission(submission_id, upload_id, submission_status_id, screening_score, submission_type_id) VALUES(46, 33, 1, 22.2, 1);
INSERT INTO submission(submission_id, upload_id, submission_status_id, screening_score, submission_type_id) VALUES(47, 34, 1, 33.3, 1);

INSERT INTO resource(resource_id, resource_role_id, project_id, project_phase_id) VALUES(51, 1, 1, 61);
INSERT INTO resource(resource_id, resource_role_id, project_id, project_phase_id) VALUES(52, 1, 1, 61);
INSERT INTO resource(resource_id, resource_role_id, project_id, project_phase_id) VALUES(53, 1, 2, 63);
INSERT INTO resource(resource_id, resource_role_id, project_id, project_phase_id) VALUES(54, 1, 3, 65);
INSERT INTO resource(resource_id, resource_role_id, project_id, project_phase_id) VALUES(55, 1, 1, 65);
INSERT INTO resource(resource_id, resource_role_id, project_id, project_phase_id) VALUES(56, 1, 2, 65);
INSERT INTO resource(resource_id, resource_role_id, project_id, project_phase_id) VALUES(57, 1, 3, 65);


INSERT INTO project_phase(project_phase_id, project_id, phase_type_id, phase_status_id, scheduled_start_time, scheduled_end_time, actual_end_time) VALUES(61, 1, 1, 1, '2011-05-05 12:00:00', '2011-05-15 12:00:00', CURRENT);
INSERT INTO project_phase(project_phase_id, project_id, phase_type_id, phase_status_id, scheduled_start_time, scheduled_end_time, actual_end_time) VALUES(62, 1, 2, 2, '2011-05-05 12:00:00', '2011-05-15 12:00:00', CURRENT);
INSERT INTO project_phase(project_phase_id, project_id, phase_type_id, phase_status_id, scheduled_start_time, scheduled_end_time, actual_end_time) VALUES(63, 1, 3, 3, '2011-05-05 12:00:00', '2011-05-15 12:00:00', CURRENT);
INSERT INTO project_phase(project_phase_id, project_id, phase_type_id, phase_status_id, scheduled_start_time, scheduled_end_time, actual_end_time) VALUES(64, 2, 1, 1, '2011-05-10 12:00:00', '2011-05-20 12:00:00', CURRENT);
INSERT INTO project_phase(project_phase_id, project_id, phase_type_id, phase_status_id, scheduled_start_time, scheduled_end_time, actual_end_time) VALUES(65, 2, 2, 2, '2011-05-10 12:00:00', '2011-05-20 12:00:00', CURRENT);
INSERT INTO project_phase(project_phase_id, project_id, phase_type_id, phase_status_id, scheduled_start_time, scheduled_end_time, actual_end_time) VALUES(66, 2, 3, 3, '2011-05-10 12:00:00', '2011-05-20 12:00:00', CURRENT);
INSERT INTO project_phase(project_phase_id, project_id, phase_type_id, phase_status_id, scheduled_start_time, scheduled_end_time, actual_end_time) VALUES(67, 3, 1, 1, '2011-05-25 12:00:00', '2099-01-01 12:00:00', CURRENT);
INSERT INTO project_phase(project_phase_id, project_id, phase_type_id, phase_status_id, scheduled_start_time, scheduled_end_time, actual_end_time) VALUES(68, 3, 2, 2, '2011-05-25 12:00:00', '2099-01-01 12:00:00', CURRENT);
INSERT INTO project_phase(project_phase_id, project_id, phase_type_id, phase_status_id, scheduled_start_time, scheduled_end_time, actual_end_time) VALUES(69, 4, 1, 1, '2011-01-01 12:00:00', '2099-01-01 12:00:00', CURRENT);
INSERT INTO project_phase(project_phase_id, project_id, phase_type_id, phase_status_id, scheduled_start_time, scheduled_end_time, actual_end_time) VALUES(70, 4, 2, 2, '2011-01-01 12:00:00', '2099-01-01 12:00:00', CURRENT);
INSERT INTO project_phase(project_phase_id, project_id, phase_type_id, phase_status_id, scheduled_start_time, scheduled_end_time, actual_end_time) VALUES(71, 5, 1, 1, '2011-01-01 12:00:00', '2099-01-01 12:00:00', CURRENT);
INSERT INTO project_phase(project_phase_id, project_id, phase_type_id, phase_status_id, scheduled_start_time, scheduled_end_time, actual_end_time) VALUES(72, 5, 2, 2, '2011-01-01 12:00:00', '2099-01-01 12:00:00', CURRENT);
INSERT INTO project_phase(project_phase_id, project_id, phase_type_id, phase_status_id, scheduled_start_time, scheduled_end_time, actual_end_time) VALUES(73, 6, 1, 1, '2011-01-01 12:00:00', '2099-01-01 12:00:00', CURRENT);
INSERT INTO project_phase(project_phase_id, project_id, phase_type_id, phase_status_id, scheduled_start_time, scheduled_end_time, actual_end_time) VALUES(74, 6, 2, 2, '2011-01-01 12:00:00', '2099-01-01 12:00:00', CURRENT);


INSERT INTO contest_eligibility(contest_eligibility_id, contest_id, is_studio) VALUES(81, 1, 0);
INSERT INTO contest_eligibility(contest_eligibility_id, contest_id, is_studio) VALUES(82, 2, 0);

INSERT INTO resource_submission(resource_id, submission_id) VALUES(51, 41);
INSERT INTO resource_submission(resource_id, submission_id) VALUES(52, 42);
INSERT INTO resource_submission(resource_id, submission_id) VALUES(53, 43);

INSERT INTO project_result(project_id, placed, final_score) VALUES(1, 1, 99.0);
INSERT INTO project_result(project_id, placed, final_score) VALUES(1, 2, 95.0);
INSERT INTO project_result(project_id, placed, final_score) VALUES(1, 3, 90.0);
INSERT INTO project_result(project_id, placed, final_score) VALUES(2, 1, 85.0);
INSERT INTO project_result(project_id, placed, final_score) VALUES(2, 2, 80.0);
INSERT INTO project_result(project_id, placed, final_score) VALUES(3, 1, 80.0);

INSERT INTO phase_type_lu(phase_type_id, name) VALUES(1, 'phaseType1');
INSERT INTO phase_type_lu(phase_type_id, name) VALUES(2, 'phaseType2');
INSERT INTO phase_type_lu(phase_type_id, name) VALUES(3, 'phaseType3');