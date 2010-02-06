

create table third_party_library(
name varchar(50),
version varchar(50),
url varchar(50),
license varchar(50),
usage_comments varchar(50),
path varchar(50),
alias varchar(50),
notes varchar(50),
category varchar(50)
);


create table project(
project_id int,
component_name varchar(50),
version_id varchar(50),
category varchar(50),
phase_desc varchar(50),
end_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION
);


create table project_result(
user_id varchar(50),
project_id int,
submit_ind int
);


create table submission_review(
reviewer_id varchar(50),
project_id int
);



create table project_info(
project_info_type_id int,
project_id int,
value varchar(50)
);


