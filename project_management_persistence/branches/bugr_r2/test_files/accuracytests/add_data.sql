DELETE FROM id_sequences where name = 'project_id_seq';
DELETE FROM id_sequences where name = 'project_audit_id_seq';

INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)
  VALUES('project_id_seq', 1, 20, 0);
INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)
  VALUES('project_audit_id_seq', 1, 20, 0);

INSERT INTO project_status_lu(project_status_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(1, 'Active', 'Active', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_status_lu(project_status_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(2, 'Inactive', 'Inactive', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_status_lu(project_status_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(3, 'Deleted', 'Deleted', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_status_lu(project_status_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(4, 'Cancelled - Failed Review', 'Cancelled - Failed Review', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_status_lu(project_status_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(5, 'Cancelled - Failed Screening', 'Cancelled - Failed Screening', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_status_lu(project_status_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(6, 'Cancelled - Zero Submissions', 'Cancelled - Zero Submissions', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_status_lu(project_status_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(7, 'Completed', 'Completed', 'System', CURRENT, 'System', CURRENT);

        INSERT INTO project_type_lu(project_type_id, name, description, 
        create_user, create_date, modify_user, modify_date) VALUES (1, 
        'Topcoder', 'Topcoder Component', 'topcoder', CURRENT, 'topcoder', 
        CURRENT); 
        INSERT INTO project_type_lu(project_type_id, name, 
        description, create_user, create_date, modify_user, modify_date) VALUES 
        (2, 'Customer', 'Customer Component', 'topcoder', CURRENT, 'topcoder', 
        CURRENT);


        INSERT INTO project_category_lu(project_category_id, project_type_id, name, description, create_user, create_date, modify_user, modify_date) VALUES (1, 1, '.Net', '.NET Component', 'topcoder', CURRENT, 'topcoder', CURRENT);
        INSERT INTO project_category_lu(project_category_id, project_type_id, name, description, create_user, create_date, modify_user, modify_date) VALUES (2, 1, 'Java', 'JAVA Component', 'topcoder', CURRENT, 'topcoder', CURRENT);
        INSERT INTO project_category_lu(project_category_id, project_type_id, name, description, create_user, create_date, modify_user, modify_date) VALUES (3, 2, 'Customer .Net', 'Customer .NET Component', 'topcoder', CURRENT, 'topcoder', CURRENT);
        INSERT INTO project_category_lu(project_category_id, project_type_id, name, description, create_user, create_date, modify_user, modify_date) VALUES (4, 2, 'Customer Java', 'Customer JAVA Component', 'topcoder', CURRENT, 'topcoder', CURRENT);


        INSERT INTO project_info_type_lu(project_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
                VALUES (1, 'property 1', 'project property 1', 'topcoder', CURRENT, 'topcoder', CURRENT);
        INSERT INTO project_info_type_lu (project_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
                VALUES (2, 'property 2', 'project property 2', 'topcoder', CURRENT, 'topcoder', CURRENT);
        INSERT INTO project_info_type_lu(project_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
                VALUES (3, 'property 3', 'project property 3', 'topcoder', CURRENT, 'topcoder', CURRENT);
        INSERT INTO project_info_type_lu(project_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
                VALUES (4, 'property 4', 'project property 4', 'topcoder', CURRENT, 'topcoder', CURRENT);