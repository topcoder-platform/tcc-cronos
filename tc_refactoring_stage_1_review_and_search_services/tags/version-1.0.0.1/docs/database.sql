ALTER TABLE 'informix'.project_category_lu ADD project_group_category_id INT BEFORE version;

CREATE TABLE 'informix'.project_group_category_lu (
    project_group_category_id INT not null,
    name VARCHAR(64),
    description VARCHAR(254),
    create_user VARCHAR(64) not null,
    create_date DATETIME YEAR TO FRACTION not null,
    modify_user VARCHAR(64) not null,
    modify_date DATETIME YEAR TO FRACTION not null,
    display boolean(1),
    display_order INT,
    project_catalog_id INT,
    version DECIMAL(12,0) default 0 not null
)
extent size 64 next size 64
lock mode row;

REVOKE ALL ON project_group_category_lu FROM 'public';

CREATE TABLE 'informix'.project_catalog_lu (
    project_catalog_id INT not null,
    name VARCHAR(64),
    description VARCHAR(254),
    create_user VARCHAR(64) not null,
    create_date DATETIME YEAR TO FRACTION not null,
    modify_user VARCHAR(64) not null,
    modify_date DATETIME YEAR TO FRACTION not null,
    display boolean(1),
    display_order INT,
    version DECIMAL(12,0) default 0 not null
)
extent size 64 next size 64
lock mode row;

REVOKE ALL ON project_catalog_lu FROM 'public';

ALTER TABLE 'informix'.project_group_category_lu ADD CONSTRAINT PRIMARY KEY
    (project_group_category_id)
    CONSTRAINT pk_project_group_category_lu;
ALTER TABLE 'informix'.project_catalog_lu ADD CONSTRAINT PRIMARY KEY
    (project_catalog_id)
    CONSTRAINT pk_project_catalog_lu;

ALTER TABLE 'informix'.project_category_lu ADD CONSTRAINT FOREIGN KEY
    (project_group_category_id)
    REFERENCES 'informix'.project_group_category_lu
    (project_group_category_id)
    CONSTRAINT fk_projectcategorylu_projectgroupcategorylu_projectgroupcategoryid;

ALTER TABLE 'informix'.project_group_category_lu ADD CONSTRAINT FOREIGN KEY
    (project_catalog_id)
    references 'informix'.project_catalog_lu
    (project_catalog_id)
    constraint fk_projectgroupcategorylu_projectcataloglu_projectcatalogid;

GRANT SELECT, INSERT, UPDATE, DELETE ON 'informix'.project_group_category_lu TO public AS informix;
GRANT SELECT, INSERT, UPDATE, DELETE ON 'informix'.project_catalog_lu TO public AS informix;

INSERT INTO 'informix'.project_catalog_lu (project_catalog_id, name, description, create_user, create_date, modify_user, modify_date)
    VALUES (1, 'Design', 'Design', 'System', CURRENT, 'System', CURRENT);
INSERT INTO 'informix'.project_catalog_lu (project_catalog_id, name, description, create_user, create_date, modify_user, modify_date)
    VALUES (2, 'Development', 'Development', 'System', CURRENT, 'System', CURRENT);
INSERT INTO 'informix'.project_catalog_lu (project_catalog_id, name, description, create_user, create_date, modify_user, modify_date)
    VALUES (3, 'UI Development', 'UI Development', 'System', CURRENT, 'System', CURRENT);

INSERT INTO 'informix'.project_group_category_lu (project_group_category_id, project_catalog_id, name, description, create_user, create_date, modify_user, modify_date)
    VALUES (1, 1, 'Conceptualization', 'Conceptualization', 'System', CURRENT, 'System', CURRENT);
INSERT INTO 'informix'.project_group_category_lu (project_group_category_id, project_catalog_id, name, description, create_user, create_date, modify_user, modify_date)
    VALUES (2, 1, 'Architecture', 'Architecture', 'System', CURRENT, 'System', CURRENT);
INSERT INTO 'informix'.project_group_category_lu (project_group_category_id, project_catalog_id, name, description, create_user, create_date, modify_user, modify_date)
    VALUES (3, 1, 'Specification', 'Specification', 'System', CURRENT, 'System', CURRENT);
INSERT INTO 'informix'.project_group_category_lu (project_group_category_id, project_catalog_id, name, description, create_user, create_date, modify_user, modify_date)
    VALUES (4, 1, 'Component Design', 'Component Design', 'System', CURRENT, 'System', CURRENT);
INSERT INTO 'informix'.project_group_category_lu (project_group_category_id, project_catalog_id, name, description, create_user, create_date, modify_user, modify_date)
    VALUES (5, 1, 'Test Scenarios', 'Test Scenarios', 'System', CURRENT, 'System', CURRENT);
INSERT INTO 'informix'.project_group_category_lu (project_group_category_id, project_catalog_id, name, description, create_user, create_date, modify_user, modify_date)
    VALUES (6, 2, 'Assembly', 'Assembly', 'System', CURRENT, 'System', CURRENT);
INSERT INTO 'informix'.project_group_category_lu (project_group_category_id, project_catalog_id, name, description, create_user, create_date, modify_user, modify_date)
    VALUES (7, 2, 'Test Suites', 'Test Suites', 'System', CURRENT, 'System', CURRENT);
INSERT INTO 'informix'.project_group_category_lu (project_group_category_id, project_catalog_id, name, description, create_user, create_date, modify_user, modify_date)
    VALUES (8, 2, 'Component Development', 'Component Development', 'System', CURRENT, 'System', CURRENT);
INSERT INTO 'informix'.project_group_category_lu (project_group_category_id, project_catalog_id, name, description, create_user, create_date, modify_user, modify_date)
    VALUES (9, 3, 'UI Prototype', 'UI Prototype', 'System', CURRENT, 'System', CURRENT);
INSERT INTO 'informix'.project_group_category_lu (project_group_category_id, project_catalog_id, name, description, create_user, create_date, modify_user, modify_date)
    VALUES (10, 3, 'RIA Build', 'RIA Build', 'System', CURRENT, 'System', CURRENT);
INSERT INTO 'informix'.project_group_category_lu (project_group_category_id, project_catalog_id, name, description, create_user, create_date, modify_user, modify_date)
    VALUES (11, 3, 'Content Creation', 'Content Creation', 'System', CURRENT, 'System', CURRENT);

INSERT INTO 'informix'.project_category_lu (project_category_id, project_group_category_id, project_type_id, name, description, create_user, create_date, modify_user, modify_date)
    VALUES (101, 1, 1, 'Conceptualization', 'Conceptualization', 'System', CURRENT, 'System', CURRENT);
INSERT INTO 'informix'.project_category_lu (project_category_id, project_group_category_id, project_type_id, name, description, create_user, create_date, modify_user, modify_date)
    VALUES (102, 2, 1, 'Module Architecture', 'Module Architecture', 'System', CURRENT, 'System', CURRENT);
INSERT INTO 'informix'.project_category_lu (project_category_id, project_group_category_id, project_type_id, name, description, create_user, create_date, modify_user, modify_date)
    VALUES (103, 2, 1, 'System Architecture', 'System Architecture', 'System', CURRENT, 'System', CURRENT);
INSERT INTO 'informix'.project_category_lu (project_category_id, project_group_category_id, project_type_id, name, description, create_user, create_date, modify_user, modify_date)
    VALUES (104, 3, 1, 'Specification', 'Specification', 'System', CURRENT, 'System', CURRENT);
INSERT INTO 'informix'.project_category_lu (project_category_id, project_group_category_id, project_type_id, name, description, create_user, create_date, modify_user, modify_date)
    VALUES (105, 4, 1, 'Component Design', 'Component Design', 'System', CURRENT, 'System', CURRENT);
INSERT INTO 'informix'.project_category_lu (project_category_id, project_group_category_id, project_type_id, name, description, create_user, create_date, modify_user, modify_date)
    VALUES (106, 5, 1, 'Test Scenarios', 'Test Scenarios', 'System', CURRENT, 'System', CURRENT);
INSERT INTO 'informix'.project_category_lu (project_category_id, project_group_category_id, project_type_id, name, description, create_user, create_date, modify_user, modify_date)
    VALUES (107, 6, 1, 'Module Assembly', 'Module Assembly', 'System', CURRENT, 'System', CURRENT);
INSERT INTO 'informix'.project_category_lu (project_category_id, project_group_category_id, project_type_id, name, description, create_user, create_date, modify_user, modify_date)
    VALUES (108, 6, 1, 'System Assembly', 'System Assembly', 'System', CURRENT, 'System', CURRENT);
INSERT INTO 'informix'.project_category_lu (project_category_id, project_group_category_id, project_type_id, name, description, create_user, create_date, modify_user, modify_date)
    VALUES (109, 6, 1, 'Bug Fix Assembly', 'Bug Fix Assembly', 'System', CURRENT, 'System', CURRENT);
INSERT INTO 'informix'.project_category_lu (project_category_id, project_group_category_id, project_type_id, name, description, create_user, create_date, modify_user, modify_date)
    VALUES (110, 6, 1, 'Prototype Assembly', 'Prototype Assembly', 'System', CURRENT, 'System', CURRENT);
INSERT INTO 'informix'.project_category_lu (project_category_id, project_group_category_id, project_type_id, name, description, create_user, create_date, modify_user, modify_date)
    VALUES (111, 7, 1, 'Test Suites', 'Test Suites', 'System', CURRENT, 'System', CURRENT);
INSERT INTO 'informix'.project_category_lu (project_category_id, project_group_category_id, project_type_id, name, description, create_user, create_date, modify_user, modify_date)
    VALUES (112, 8, 1, 'Component Development', 'Component Development', 'System', CURRENT, 'System', CURRENT);
INSERT INTO 'informix'.project_category_lu (project_category_id, project_group_category_id, project_type_id, name, description, create_user, create_date, modify_user, modify_date)
    VALUES (113, 9, 1, 'UI Prototype', 'UI Prototype', 'System', CURRENT, 'System', CURRENT);
INSERT INTO 'informix'.project_category_lu (project_category_id, project_group_category_id, project_type_id, name, description, create_user, create_date, modify_user, modify_date)
    VALUES (114, 10, 1, 'RIA Build', 'RIA Build', 'System', CURRENT, 'System', CURRENT);
INSERT INTO 'informix'.project_category_lu (project_category_id, project_group_category_id, project_type_id, name, description, create_user, create_date, modify_user, modify_date)
    VALUES (115, 11, 1, 'Content Creation', 'Content Creation', 'System', CURRENT, 'System', CURRENT);
