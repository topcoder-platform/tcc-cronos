CREATE TABLE comp_catalog (
        component_id DECIMAL(12,0) not null,
    current_version DECIMAL(12,0),
    short_desc lvarchar,
    component_name VARCHAR(254) not null,
    description lvarchar(10000),
    function_desc lvarchar,
    create_time datetime year to fraction(3)  default current year to fraction(3),
    status_id DECIMAL(12,0),
    root_category_id DECIMAL(12,0),
    modify_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION not null,
    public_ind DECIMAL(1,0) default 0 not null
);
ALTER TABLE comp_catalog ADD CONSTRAINT PRIMARY KEY
        (component_id)
        CONSTRAINT pk_comp_catalog;

CREATE TABLE comp_technology (
        comp_tech_id DECIMAL(12,0) not null,
    comp_vers_id DECIMAL(12,0),
    technology_type_id DECIMAL(12,0)
);
ALTER TABLE comp_technology ADD CONSTRAINT PRIMARY KEY
        (comp_tech_id)
        CONSTRAINT pk_comp_technology;

CREATE TABLE comp_version_dates (
        comp_version_dates_id DECIMAL(12,0) not null,
    comp_vers_id DECIMAL(12,0) not null,
    phase_id DECIMAL(12,0) not null,
    posting_date DATETIME YEAR TO DAY,
    initial_submission_date DATETIME YEAR TO DAY,
    winner_announced_date DATETIME YEAR TO DAY,
    final_submission_date DATETIME YEAR TO DAY,
    estimated_dev_date DATETIME YEAR TO DAY,
    price DECIMAL(10,2),
    total_submissions DECIMAL(12,0) default 0 not null,
    status_id DECIMAL(12,0) default 301 not null,
    create_time DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION not null,
    level_id DECIMAL(12,0),
    screening_complete_date DATETIME YEAR TO DAY,
    review_complete_date DATETIME YEAR TO DAY,
    aggregation_complete_date DATETIME YEAR TO DAY,
    phase_complete_date DATETIME YEAR TO DAY,
    production_date DATETIME YEAR TO DAY,
    aggregation_complete_date_comment VARCHAR(254),
    phase_complete_date_comment VARCHAR(254),
    review_complete_date_comment VARCHAR(254),
    winner_announced_date_comment VARCHAR(254),
    initial_submission_date_comment VARCHAR(254),
    screening_complete_date_comment VARCHAR(254),
    final_submission_date_comment VARCHAR(254),
    production_date_comment VARCHAR(254),
    modify_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION not null
);
ALTER TABLE comp_version_dates ADD CONSTRAINT PRIMARY KEY
        (comp_version_dates_id)
        CONSTRAINT pk_comp_version_dates;

CREATE TABLE comp_versions (
        comp_vers_id DECIMAL(12,0) not null,
    component_id DECIMAL(12,0),
    version DECIMAL(12,0),
    version_text CHAR(20),
    create_time datetime year to fraction(3)  default current year to fraction(3),
    phase_id DECIMAL(12,0),
    phase_time DATETIME YEAR TO FRACTION,
    price DECIMAL(10,2),
    comments lvarchar,
    modify_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION not null,
        suspended_ind DECIMAL(1,0) default 0,
        browse VARCHAR(255) default null,
        location VARCHAR(255) default null,
        issue_tracker_path VARCHAR(100) default null,
        revision VARCHAR(10) default null
);
ALTER TABLE comp_versions ADD CONSTRAINT PRIMARY KEY
        (comp_vers_id)
        CONSTRAINT pk_comp_versions;

CREATE TABLE contest_eligibility (
        contest_eligibility_id DECIMAL(10, 0) not null,
    contest_id DECIMAL(10,0) not null,
    is_studio SMALLINT NOT NULL
);
ALTER TABLE contest_eligibility ADD CONSTRAINT PRIMARY KEY
        (contest_eligibility_id)
        CONSTRAINT pk_contest_eligibility;

CREATE TABLE phase_criteria (
        project_phase_id INT not null,
    phase_criteria_type_id INT not null,
    parameter VARCHAR(254) not null,
    create_user VARCHAR(64) not null,
    create_date DATETIME YEAR TO FRACTION not null,
    modify_user VARCHAR(64) not null,
    modify_date DATETIME YEAR TO FRACTION not null
);
ALTER TABLE phase_criteria ADD CONSTRAINT PRIMARY KEY
        (project_phase_id, phase_criteria_type_id)
        CONSTRAINT pk_phase_criteria;

CREATE TABLE project (
        project_id INT not null,
    project_status_id INT not null,
    project_category_id INT not null,
    project_studio_spec_id INTEGER,
    create_user VARCHAR(64) not null,
    create_date DATETIME YEAR TO FRACTION not null,
    modify_user VARCHAR(64) not null,
    modify_date DATETIME YEAR TO FRACTION not null,
    tc_direct_project_id INT
);
ALTER TABLE project ADD CONSTRAINT PRIMARY KEY
        (project_id)
        CONSTRAINT pk_project;

CREATE TABLE project_catalog_lu (
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
);
ALTER TABLE project_catalog_lu ADD CONSTRAINT PRIMARY KEY
        (project_catalog_id)
        CONSTRAINT pk_project_catalog_lu;

CREATE TABLE project_category_lu (
        project_category_id INT not null,
    project_type_id INT,
    name VARCHAR(64),
    description VARCHAR(254),
    create_user VARCHAR(64) not null,
    create_date DATETIME YEAR TO FRACTION not null,
    modify_user VARCHAR(64) not null,
    modify_date DATETIME YEAR TO FRACTION not null,
    display boolean(1),
    display_order INT,
    project_group_category_id INT,
    version DECIMAL(12,0) default 0 not null
);
ALTER TABLE project_category_lu ADD CONSTRAINT PRIMARY KEY
        (project_category_id)
        CONSTRAINT pk_project_category_lu;

CREATE TABLE project_group_category_lu (
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
);
ALTER TABLE project_group_category_lu ADD CONSTRAINT PRIMARY KEY
        (project_group_category_id)
        CONSTRAINT pk_project_group_category_lu;

CREATE TABLE project_info (
        project_id INT not null,
    project_info_type_id INT not null,
    value VARCHAR(255) not null,
    create_user VARCHAR(64) not null,
    create_date DATETIME YEAR TO FRACTION not null,
    modify_user VARCHAR(64) not null,
    modify_date DATETIME YEAR TO FRACTION not null
);
ALTER TABLE project_info ADD CONSTRAINT PRIMARY KEY
        (project_id, project_info_type_id)
        CONSTRAINT pk_project_info;

CREATE TABLE project_phase (
        project_phase_id INT not null,
    project_id INT not null,
    phase_type_id INT not null,
    phase_status_id INT not null,
    fixed_start_time DATETIME YEAR TO FRACTION,
    scheduled_start_time DATETIME YEAR TO FRACTION not null,
    scheduled_end_time DATETIME YEAR TO FRACTION not null,
    actual_start_time DATETIME YEAR TO FRACTION,
    actual_end_time DATETIME YEAR TO FRACTION,
    duration DECIMAL(16,0),
    create_user VARCHAR(64) not null,
    create_date DATETIME YEAR TO FRACTION not null,
    modify_user VARCHAR(64) not null,
    modify_date DATETIME YEAR TO FRACTION not null
);
ALTER TABLE project_phase ADD CONSTRAINT PRIMARY KEY
        (project_phase_id)
        CONSTRAINT pk_project_phase;

CREATE TABLE project_result (
        user_id DECIMAL(10,0),
    project_id DECIMAL(10,0),
    old_rating DECIMAL(5,0),
    new_rating DECIMAL(5,0),
    raw_score DECIMAL(5,2),
    final_score DECIMAL(5,2),
    payment DECIMAL(10,2),
    placed DECIMAL(6,0),
    rating_ind DECIMAL(1,0),
    valid_submission_ind DECIMAL(1,0),
    create_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
    modify_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
    passed_review_ind DECIMAL(1,0),
    point_adjustment FLOAT,
    rating_order INT
);
ALTER TABLE project_result ADD CONSTRAINT PRIMARY KEY
        (user_id, project_id)
        CONSTRAINT pk_project_result;

CREATE TABLE project_status_lu (
        project_status_id INT not null,
    name VARCHAR(64) not null,
    description VARCHAR(254),
    create_user VARCHAR(64) not null,
    create_date DATETIME YEAR TO FRACTION not null,
    modify_user VARCHAR(64) not null,
    modify_date DATETIME YEAR TO FRACTION not null
);
ALTER TABLE project_status_lu ADD CONSTRAINT PRIMARY KEY
        (project_status_id)
        CONSTRAINT pk_project_status_lu;

CREATE TABLE rboard_application (
        user_id DECIMAL(10,0) not null,
    project_id DECIMAL(12,0) not null,
    phase_id DECIMAL(5,0) not null,
    review_resp_id DECIMAL(3,0),
    primary_ind DECIMAL(1,0),
    create_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
    modify_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION
);
ALTER TABLE rboard_application ADD CONSTRAINT PRIMARY KEY
        (user_id, project_id, phase_id)
        CONSTRAINT pk_rboard_application;

CREATE TABLE rboard_payment (
        project_id DECIMAL(10,0) not null,
    phase_id DECIMAL(5,0) not null,
    primary_ind DECIMAL(1,0) not null,
    amount DECIMAL(7,2) not null,
    modify_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
    create_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION
);
ALTER TABLE rboard_payment ADD CONSTRAINT PRIMARY KEY
        (project_id, phase_id, primary_ind)
        CONSTRAINT pk_rboard_payment;

CREATE TABLE resource (
        resource_id INT not null,
    resource_role_id INT not null,
    project_id INT,
    project_phase_id INT,
    create_user VARCHAR(64) not null,
    create_date DATETIME YEAR TO FRACTION not null,
    modify_user VARCHAR(64) not null,
    modify_date DATETIME YEAR TO FRACTION not null
);
ALTER TABLE resource ADD CONSTRAINT PRIMARY KEY
        (resource_id)
        CONSTRAINT pk_resource;

CREATE TABLE resource_info (
        resource_id INT not null,
    resource_info_type_id INT not null,
    value VARCHAR(255) not null,
    create_user VARCHAR(64) not null,
    create_date DATETIME YEAR TO FRACTION not null,
    modify_user VARCHAR(64) not null,
    modify_date DATETIME YEAR TO FRACTION not null
);
ALTER TABLE resource_info ADD CONSTRAINT PRIMARY KEY
        (resource_id, resource_info_type_id)
        CONSTRAINT pk_resource_info;

CREATE TABLE resource_submission (
        resource_id INT not null,
    submission_id INT not null,
    create_user VARCHAR(64) not null,
    create_date DATETIME YEAR TO FRACTION not null,
    modify_user VARCHAR(64) not null,
    modify_date DATETIME YEAR TO FRACTION not null
);
ALTER TABLE resource_submission ADD CONSTRAINT PRIMARY KEY
        (resource_id, submission_id)
        CONSTRAINT pk_resource_submission;

CREATE TABLE technology_types (
        technology_type_id DECIMAL(12,0) not null,
    technology_name VARCHAR(100) not null,
    description VARCHAR(254) not null,
    status_id DECIMAL(12,0) not null
);
ALTER TABLE technology_types ADD CONSTRAINT PRIMARY KEY
        (technology_type_id)
        CONSTRAINT pk_technology_types;

CREATE TABLE submission (
        submission_id INT not null,
    upload_id INT not null,
    submission_status_id INT not null,
    screening_score DECIMAL(5,2),
    initial_score DECIMAL(5,2),
    final_score DECIMAL(5,2),
    placement DECIMAL(3,0),
    submission_type_id INTEGER NOT NULL,
    create_user VARCHAR(64) not null,
    create_date DATETIME YEAR TO FRACTION not null,
    modify_user VARCHAR(64) not null,
    modify_date DATETIME YEAR TO FRACTION not null,
    user_rank DECIMAL(5,0),
    mark_for_purchase BOOLEAN(1),
    prize_id INTEGER,
    file_size DECIMAL(18,0),
    view_count DECIMAL(10,0)
);
ALTER TABLE submission ADD CONSTRAINT PRIMARY KEY
        (submission_id)
        CONSTRAINT pk_submission;

CREATE TABLE upload (
        upload_id INT not null,
    project_id INT,
    resource_id INT,
    upload_type_id INT,
    upload_status_id INT,
    parameter VARCHAR(254),
    upload_desc VARCHAR(254),
    create_user VARCHAR(64) not null,
    create_date DATETIME YEAR TO FRACTION not null,
    modify_user VARCHAR(64) not null,
    modify_date DATETIME YEAR TO FRACTION not null
);
ALTER TABLE upload ADD CONSTRAINT PRIMARY KEY
        (upload_id)
        CONSTRAINT pk_upload;
