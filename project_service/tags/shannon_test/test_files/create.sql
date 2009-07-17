CREATE TABLE tc_direct_project (
project_id  INTEGER NOT NULL,
name        VARCHAR(200) NOT NULL,
description LVARCHAR(10000),
user_id     INTEGER NOT NULL, -- id of user who creates project
create_date DATETIME YEAR TO FRACTION(3) NOT NULL,
modify_date DATETIME YEAR TO FRACTION(3),
PRIMARY KEY(project_id) --primary key description
);

CREATE TABLE competition (
competition_id INTEGER NOT NULL,
project_id     INTEGER NOT NULL,
PRIMARY KEY(competition_id),
FOREIGN KEY(project_id) REFERENCES tc_direct_project(project_id) -- foreign key to tc_direct_project table
);

CREATE SEQUENCE project_sequence MINVALUE 1 START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE competition_sequence MINVALUE 1 START WITH 1 INCREMENT BY 1;

