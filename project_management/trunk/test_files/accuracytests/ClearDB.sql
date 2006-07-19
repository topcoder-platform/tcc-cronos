DELETE FROM resource_info WHERE resource_id IN (1, 2);
DELETE FROM resource WHERE resource_id IN (1, 2);
DELETE FROM project_info WHERE project_info_type_id IN (1, 2, 3);
DELETE FROM project WHERE project_id IN (1, 2, 3, 4, 5);
DELETE FROM project_info_type_lu WHERE project_info_type_id IN (1, 2, 3);
DELETE FROM project_category_lu WHERE project_category_id IN (1, 2, 3, 4);
DELETE FROM project_type_lu WHERE project_type_id IN (1, 2, 3);
