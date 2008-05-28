CREATE TABLE tc_direct_project (
project_id  INTEGER NOT NULL,
name        VARCHAR(200) NOT NULL,
description TEXT,
user_id     INTEGER NOT NULL,
create_date DATETIME YEAR to FRACTION(3) NOT NULL,
modify_date DATETIME YEAR to FRACTION(3), 
PRIMARY KEY(project_id) 
);
