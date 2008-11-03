CREATE TABLE tc_direct_project (
project_id  INTEGER NOT NULL,
name        VARCHAR(200) NOT NULL,
description VARCHAR(10000),
user_id     INTEGER NOT NULL, -- id of user who creates project
create_date DATETIME NOT NULL,
modify_date DATETIME, 
PRIMARY KEY(project_id) 
);
