

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


insert into third_party_library values(
"log4net","1.2.9","http://www.log4net.org","GPL","do logging","the path","the alias","the note",".NET");


create table project(
project_id int,
component_name varchar(50),
version_id varchar(50),
category varchar(50),
phase_desc varchar(50),
end_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION
);
insert into project values(13,'HBS Singleton Process Manager','1.0.0','Java','development',CURRENT);
insert into project values(14,'HBS Singleton Process Manager','1.0.0','Java','design',CURRENT);


create table project_result(
user_id varchar(50),
project_id int,
submit_ind int
);
insert into project_result  values('sparemax',13,1);
insert into project_result  values('EveningSun',13,2);

insert into project_result  values('Luca',14,1);
insert into project_result  values('AleaActaEst',14,2);


create table submission_review(
reviewer_id varchar(50),
project_id int
);

insert into submission_review values('bbxiong',13);
insert into submission_review values('Veloerien',13);
insert into submission_review values('FireIce',13);

insert into submission_review values('Rica',14);
insert into submission_review values('Telly12',14);
insert into submission_review values('linwe',14);


create table project_info(
project_info_type_id int,
project_id int,
value varchar(50)
);

insert into project_info values(8,13,"http://localhost:8888/build-dependencies.xml");
insert into project_info values(8,14,"http://localhost:8888/build-dependencies.xml");
