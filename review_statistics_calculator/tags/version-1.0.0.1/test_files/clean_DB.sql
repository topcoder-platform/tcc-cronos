DELETE id_sequences;

DELETE evaluation_type_lu;

DELETE resource_role_lu WHERE resource_role_id in (16, 17);

DELETE comment_type_lu WHERE comment_type_id = 14;

DELETE submission_type_lu WHERE submission_type_id = 1;

DELETE submission_status_lu WHERE submission_status_id = 6;

DELETE phase_type_lu where phase_type_id IN (13, 14 ,15, 16, 17, 18);

DELETE project WHERE project_id = 1;

DELETE project_phase  WHERE project_id = 1;

DELETE resource WHERE project_id = 1;

DELETE resource_submission WHERE resource_id in (1, 2, 3, 4, 5, 6);

DELETE resource_info WHERE resource_id in (1, 2, 3, 4);

DELETE review WHERE review_id < 19;

DELETE review_comment;

DELETE review_comment where review_comment_id = 102;

DELETE review_item where review_item_id = 1;

DELETE review_item_comment where review_item_comment_id in (1, 2, 3);

DELETE scorecard WHERE scorecard_id = 1;

DELETE scorecard_type_lu WHERE scorecard_type_id = 1;

DELETE deliverable_lu where deliverable_id IN (25, 26, 27, 28, 29, 30, 31, 32, 33);

DELETE submission WHERE submission_id in (1,2,3);

DELETE upload WHERE upload_id in (1,2,3);