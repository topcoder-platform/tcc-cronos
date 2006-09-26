DELETE screening_result;
DELETE screening_response_lu;
DELETE response_severity_lu;
DELETE screening_task;
DELETE screening_status_lu;
DELETE upload;
DELETE resource_info;
DELETE resource_info_type_lu;
DELETE resource;
DELETE project;
DELETE project_category_lu;
DELETE id_sequences;

DELETE email;
DELETE user_rating;
DELETE user_reliability;
DELETE user;

INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)
  VALUES('screening_task_id_seq', 1, 20, 0);
INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)
  VALUES('screening_result_id_seq', 1, 20, 0);