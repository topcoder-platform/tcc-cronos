DELETE FROM resource_info WHERE resource_id IN (1, 2);

DELETE FROM resource WHERE resource_id IN (1, 2);

DELETE FROM resource_info_type_lu WHERE resource_info_type_id = 1;

DELETE FROM project_info WHERE project_info_type_id IN (1, 2, 3);

DELETE FROM project_info_type_lu WHERE project_info_type_id IN (1, 2, 3);

DELETE FROM project; 

DELETE FROM project_status_lu WHERE project_status_id IN (1, 2, 3, 4, 5, 6, 7);

DELETE FROM project_category_lu WHERE project_category_id IN (1, 2, 3, 4);

DELETE FROM project_type_lu WHERE project_type_id IN (1, 2, 3);
