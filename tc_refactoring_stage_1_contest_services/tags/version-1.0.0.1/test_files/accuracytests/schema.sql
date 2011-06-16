create table project (
    project_id INT not null,
    project_status_id INT not null,
    project_category_id INT not null,
    project_studio_spec_id INTEGER,                        
    create_user VARCHAR(64),
    create_date DATETIME YEAR TO FRACTION,
    modify_user VARCHAR(64),
    modify_date DATETIME YEAR TO FRACTION,
    tc_direct_project_id INT,
	PRIMARY KEY (project_id) 
);

create table project_category_lu (
    project_category_id INT not null,
    project_type_id INT,
    name VARCHAR(64) not null,
    description VARCHAR(254),
    create_user VARCHAR(64),
    create_date DATETIME YEAR TO FRACTION,
    modify_user VARCHAR(64),
    modify_date DATETIME YEAR TO FRACTION,
    display boolean(1),
    display_order INT,
    version DECIMAL(12,0) default 0,
	project_group_category_lu_project_group_category_id INT NOT NULL,
	PRIMARY KEY (project_category_id) 
);

-- -----------------------------------------------------
-- Table 'project_catalog_lu'
-- -----------------------------------------------------
CREATE TABLE project_catalog_lu (
  project_catalog_id INT NOT NULL,
  name VARCHAR(64) NOT NULL,
  description VARCHAR(254),
  create_user VARCHAR(64),
  create_date DATETIME YEAR TO FRACTION,
  modify_user VARCHAR(64),
  modify_date DATETIME YEAR TO FRACTION,
  display boolean(1),
  display_order INT,
  version DECIMAL,
  PRIMARY KEY (project_catalog_id) 
);

-- -----------------------------------------------------
-- Table 'project_group_category_lu'
-- -----------------------------------------------------
CREATE TABLE project_group_category_lu (
  project_group_category_id INT NOT NULL ,
  name VARCHAR(64) not null,
  description VARCHAR(254),
  create_user VARCHAR(64) ,
  create_date DATETIME YEAR TO FRACTION,
  modify_user VARCHAR(64) ,
  modify_date DATETIME YEAR TO FRACTION,
  display boolean(1),
  display_order INT,
  version DECIMAL,
  project_catalog_lu_project_type_id INT NOT NULL,
  PRIMARY KEY (project_group_category_id)
);

create table resource_submission (
    resource_id INT not null,
    submission_id INT not null,
    create_user VARCHAR(64),
    create_date DATETIME YEAR TO FRACTION,
    modify_user VARCHAR(64),
    modify_date DATETIME YEAR TO FRACTION
);

create table project_result (
    user_id DECIMAL(10,0),
    project_id DECIMAL(10,0) not null,
    old_rating DECIMAL(5,0),
    new_rating DECIMAL(5,0),
    raw_score DECIMAL(5,2),
    final_score DECIMAL(5,2) not null,
    payment DECIMAL(10,2),
    placed DECIMAL(6,0) not null,
    rating_ind DECIMAL(1,0),
    valid_submission_ind DECIMAL(1,0),
    create_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
    modify_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
    passed_review_ind DECIMAL(1,0),
    point_adjustment FLOAT,
    rating_order INT
);

create table submission (
    submission_id INT not null,
    upload_id INT not null,
    submission_status_id INT not null,
    screening_score DECIMAL(5,2),
    initial_score DECIMAL(5,2),
    final_score DECIMAL(5,2),
    placement DECIMAL(3,0),
    submission_type_id INTEGER NOT NULL,
    create_user VARCHAR(64),
    create_date DATETIME YEAR TO FRACTION,
    modify_user VARCHAR(64),
    modify_date DATETIME YEAR TO FRACTION,
    user_rank DECIMAL(5,0),
    mark_for_purchase BOOLEAN(1),
    prize_id INTEGER,
    file_size DECIMAL(18,0),
    view_count DECIMAL(10,0),
	PRIMARY KEY (submission_id) 
);

create table resource_info (
    resource_id INT not null,
    resource_info_type_id INT not null,
    value VARCHAR(255),
    create_user VARCHAR(64),
    create_date DATETIME YEAR TO FRACTION,
    modify_user VARCHAR(64),
    modify_date DATETIME YEAR TO FRACTION
);

create table project_phase (
    project_phase_id INT not null,
    project_id INT not null,
    phase_type_id INT not null,
    phase_status_id INT not null,
    fixed_start_time DATETIME YEAR TO FRACTION,
    scheduled_start_time DATETIME YEAR TO FRACTION ,
    scheduled_end_time DATETIME YEAR TO FRACTION ,
    actual_start_time DATETIME YEAR TO FRACTION,
    actual_end_time DATETIME YEAR TO FRACTION,
    duration DECIMAL(16,0) ,
    create_user VARCHAR(64) ,
    create_date DATETIME YEAR TO FRACTION ,
    modify_user VARCHAR(64) ,
    modify_date DATETIME YEAR TO FRACTION ,
	PRIMARY KEY (project_phase_id) 
);

create table project_info (
    project_id INT not null,
    project_info_type_id INT not null,
    value VARCHAR(255),
    create_user VARCHAR(64) ,
    create_date DATETIME YEAR TO FRACTION,
    modify_user VARCHAR(64) ,
    modify_date DATETIME YEAR TO FRACTION
);

create table resource (
    resource_id INT not null,
    resource_role_id INT not null,
    project_id INT not null,
    project_phase_id INT not null,
    create_user VARCHAR(64),
    create_date DATETIME YEAR TO FRACTION,
    modify_user VARCHAR(64),
    modify_date DATETIME YEAR TO FRACTION,
	PRIMARY KEY (resource_id) 
);

create table upload (
    upload_id INT not null,
    project_id INT not null,
    resource_id INT,
    upload_type_id INT,
    upload_status_id INT,
    parameter VARCHAR(254),
    upload_desc VARCHAR(254),
    create_user VARCHAR(64),
    create_date DATETIME YEAR TO FRACTION,
    modify_user VARCHAR(64),
    modify_date DATETIME YEAR TO FRACTION,
	PRIMARY KEY (upload_id) 
);

create table contest_eligibility (
    contest_eligibility_id DECIMAL(10, 0) not null,
    contest_id DECIMAL(10,0) not null,
    is_studio SMALLINT NOT NULL,
    PRIMARY KEY (contest_eligibility_id)
);

create table phase_type_lu (
    phase_type_id INT not null,
    name VARCHAR(64) not null,
    description VARCHAR(254),
    create_user VARCHAR(64),
    create_date DATETIME YEAR TO FRACTION,
    modify_user VARCHAR(64),
    modify_date DATETIME YEAR TO FRACTION,
	PRIMARY KEY (phase_type_id)
);

create table project_status_lu (
    project_status_id INT not null,
    name VARCHAR(64) ,
    description VARCHAR(254) ,
    create_user VARCHAR(64) ,
    create_date DATETIME YEAR TO FRACTION ,
    modify_user VARCHAR(64) ,
    modify_date DATETIME YEAR TO FRACTION ,
	PRIMARY KEY (project_status_id)
);

create table project_info_type_lu (
    project_info_type_id INT not null,
    name VARCHAR(64) not null,
    description VARCHAR(25) ,
    create_user VARCHAR(64),
    create_date DATETIME YEAR TO FRACTION,
    modify_user VARCHAR(64) ,
    modify_date DATETIME YEAR TO FRACTION ,
	PRIMARY KEY(project_info_type_id)
);
