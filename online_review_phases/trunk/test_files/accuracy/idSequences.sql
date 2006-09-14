database online_review;
-- Phase Management
INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)
  VALUES('phase_id_seq', 1, 20, 0);
  
-- Project Management
INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)
  VALUES('project_id_seq', 1, 20, 0);
INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)
  VALUES('project_audit_id_seq', 1, 20, 0);

-- Scorecard Management
INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)
  VALUES('scorecard_id_seq', 1, 20, 0);
INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)
  VALUES('scorecard_group_id_seq', 1, 20, 0);
INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)
  VALUES('scorecard_section_id_seq', 1, 20, 0);
INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)
  VALUES('scorecard_question_id_seq', 1, 20, 0);

-- Deliverable Management
INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)
  VALUES('upload_id_seq', 1, 20, 0);
INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)
  VALUES('upload_type_id_seq', 1, 20, 0);
INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)
  VALUES('upload_status_id_seq', 1, 20, 0);
INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)
  VALUES('submission_id_seq', 1, 20, 0);
INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)
  VALUES('submission_status_id_seq', 1, 20, 0);

-- Resource Management
INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)
  VALUES('resource_id_seq', 1, 20, 0); 
INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)
  VALUES('notification_id_seq', 1, 20, 0); 
INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)
  VALUES('resource_role_id_seq', 1, 20, 0); 
  
-- Auto Screening Management
INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)
  VALUES('screening_task_id_seq', 1, 20, 0);
INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)
  VALUES('screening_result_id_seq', 1, 20, 0);

close database;