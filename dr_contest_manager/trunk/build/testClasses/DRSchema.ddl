CREATE TABLE dr_points (
  dr_points_id DECIMAL(10,0) NOT NULL,
  track_id DECIMAL(10,0) NOT NULL,
  dr_points_reference_type_id INT NOT NULL,
  dr_points_operation_id INT NOT NULL,
  dr_points_type_id INT NOT NULL,
  dr_points_status_id INT NOT NULL,
  dr_points_desc VARCHAR(100) NOT NULL,
  user_id DECIMAL(10,0) NOT NULL,
  amount DECIMAL(10,2) NOT NULL,
  application_date DATETIME YEAR TO FRACTION NOT NULL,
  award_date DATETIME YEAR TO FRACTION NOT NULL,
  reference_id DECIMAL(10,0),
  is_potential boolean default 'f',
  create_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
  modify_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION
);

CREATE TABLE dr_points_operation_lu (
  dr_points_operation_id INT NOT NULL,
  dr_points_operation_desc VARCHAR(50) NOT NULL,
  create_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
  modify_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION
);

CREATE TABLE dr_points_reference_type_lu (
  dr_points_reference_type_id INT NOT NULL,
  dr_points_reference_type_desc VARCHAR(50) NOT NULL,
  create_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
  modify_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION
);

CREATE TABLE dr_points_type_lu (
  dr_points_type_id INT NOT NULL,
  dr_points_type_desc VARCHAR(50) NOT NULL,
  create_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
  modify_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION
);

CREATE TABLE dr_points_status_lu (
  dr_points_status_id INT NOT NULL,
  dr_points_status_desc VARCHAR(50) NOT NULL,
  create_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
  modify_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION
);

CREATE TABLE points_calculator_lu (
  points_calculator_id INT NOT NULL,
  class_name VARCHAR(100) NOT NULL,
  points_calculator_desc VARCHAR(50) NOT NULL,
  create_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
  modify_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION
);

CREATE TABLE project_type_lu (
  project_type_id INT NOT NULL,
  name VARCHAR(64) NOT NULL,
  description VARCHAR(254) NOT NULL,
  create_user VARCHAR(64) NOT NULL,
  modify_user VARCHAR(64) NOT NULL,
  create_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
  modify_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION
);

CREATE TABLE track (
  track_id DECIMAL(10,0) NOT NULL,
  points_calculator_id INT NOT NULL,
  track_type_id INT NOT NULL,
  track_status_id INT NOT NULL,
  track_desc VARCHAR(50) NOT NULL,
  track_start_date DATETIME YEAR TO FRACTION NOT NULL,
  track_end_date DATETIME YEAR TO FRACTION NOT NULL,
  create_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
  modify_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION
);

CREATE TABLE track_contest (
  track_contest_id DECIMAL(10,0) NOT NULL,
  track_contest_result_calculator_id INT NOT NULL,
  track_contest_type_id INT NOT NULL,
  track_id DECIMAL(10,0) NOT NULL,
  track_contest_desc VARCHAR(128) NOT NULL,
  create_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
  modify_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION
);

CREATE TABLE track_contest_result_calculator_lu (
  track_contest_result_calculator_id INT NOT NULL,
  class_name VARCHAR(100) NOT NULL,
  track_contest_result_calculator_desc VARCHAR(50) NOT NULL,
  create_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
  modify_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION
);

CREATE TABLE track_contest_type_lu (
  track_contest_type_id INT NOT NULL,
  track_contest_type_desc VARCHAR(50) NOT NULL,
  create_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
  modify_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION
);

CREATE TABLE track_project_type_xref (
  track_id DECIMAL(10,0) NOT NULL,
  project_type_id INT NOT NULL
);

CREATE TABLE track_type_lu (
  track_type_id INT NOT NULL,
  track_type_desc VARCHAR(50) NOT NULL,
  create_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
  modify_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION
);

CREATE TABLE track_status_lu (
  track_status_id INT NOT NULL,
  track_status_desc VARCHAR(50) NOT NULL,
  create_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
  modify_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION
);

alter table dr_points add constraint primary key 
	(dr_points_id)
	constraint pk_dr_points;

alter table dr_points_operation_lu add constraint primary key 
	(dr_points_operation_id)
	constraint pk_dr_points_operation_lu;

alter table dr_points_reference_type_lu add constraint primary key 
	(dr_points_reference_type_id)
	constraint pk_dr_points_reference_type_lu;

alter table dr_points_type_lu add constraint primary key 
	(dr_points_type_id)
	constraint pk_dr_points_type_lu;

alter table dr_points_status_lu add constraint primary key 
	(dr_points_status_id)
	constraint pk_dr_points_status_lu;

alter table points_calculator_lu add constraint primary key 
	(points_calculator_id)
	constraint pk_points_calculator_lu;

alter table project_type_lu add constraint primary key 
	(project_type_id)
	constraint pk_project_type_lu;

alter table track add constraint primary key 
	(track_id)
	constraint pk_track;

alter table track_contest add constraint primary key 
	(track_contest_id)
	constraint pk_track_contest;

alter table track_contest_result_calculator_lu add constraint primary key 
	(track_contest_result_calculator_id)
	constraint pk_track_contest_result_calculator_lu;

alter table track_contest_type_lu add constraint primary key 
	(track_contest_type_id)
	constraint pk_track_contest_type_lu;

alter table track_project_type_xref add constraint primary key 
	(track_id, project_type_id)
	constraint pk_track_project_type_xref;

alter table track_type_lu add constraint primary key 
	(track_type_id)
	constraint pk_track_type_lu;

alter table track_status_lu add constraint primary key 
	(track_status_id)
	constraint pk_track_status_lu;

alter table dr_points add constraint foreign key 
	(track_id)
	references track
	(track_id) 
	constraint dr_points_FKIndex1;

alter table dr_points add constraint foreign key 
	(dr_points_type_id)
	references dr_points_type_lu
	(dr_points_type_id) 
	constraint dr_points_FKIndex2;

alter table dr_points add constraint foreign key 
	(dr_points_operation_id)
	references dr_points_operation_lu
	(dr_points_operation_id) 
	constraint dr_points_FKIndex3;

alter table dr_points add constraint foreign key 
	(dr_points_reference_type_id)
	references dr_points_reference_type_lu
	(dr_points_reference_type_id) 
	constraint dr_points_FKIndex4;

alter table dr_points add constraint foreign key 
	(dr_points_status_id)
	references dr_points_status_lu
	(dr_points_status_id) 
	constraint dr_points_FKIndex5;

alter table track add constraint foreign key 
	(points_calculator_id)
	references points_calculator_lu
	(points_calculator_id) 
	constraint track_FKIndex1;

alter table track add constraint foreign key 
	(track_type_id)
	references track_type_lu
	(track_type_id) 
	constraint track_FKIndex2;

alter table track add constraint foreign key 
	(track_status_id)
	references track_status_lu
	(track_status_id) 
	constraint track_FKIndex3;

alter table track_contest add constraint foreign key 
	(track_id)
	references track
	(track_id) 
	constraint track_contest_FKIndex1;

alter table track_contest add constraint foreign key 
	(track_contest_type_id)
	references track_contest_type_lu
	(track_contest_type_id) 
	constraint track_contest_FKIndex2;

alter table track_contest add constraint foreign key 
	(track_contest_result_calculator_id)
	references track_contest_result_calculator_lu
	(track_contest_result_calculator_id) 
	constraint track_contest_FKIndex3;

alter table track_project_type_xref add constraint foreign key 
	(track_id)
	references track
	(track_id) 
	constraint track_project_type_xref_FKIndex1;

alter table track_project_type_xref add constraint foreign key 
	(project_type_id)
	references project_type_lu
	(project_type_id) 
	constraint track_project_type_xref_FKIndex2;

