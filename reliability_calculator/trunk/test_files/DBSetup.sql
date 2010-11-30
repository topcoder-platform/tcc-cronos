create table 'informix'.project_result (
    user_id DECIMAL(10,0),
    project_id DECIMAL(10,0),
    old_rating DECIMAL(5,0),
    new_rating DECIMAL(5,0),
    old_reliability DECIMAL(5,4),
    new_reliability DECIMAL(5,4),
    raw_score DECIMAL(5,2),
    final_score DECIMAL(5,2),
    payment DECIMAL(10,2),
    placed DECIMAL(6,0),
    rating_ind DECIMAL(1,0),
    valid_submission_ind DECIMAL(1,0),
    reliability_ind DECIMAL(1,0),
    create_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
    modify_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
    reliable_submission_ind DECIMAL(1,0),
    passed_review_ind DECIMAL(1,0),
    point_adjustment FLOAT,
    current_reliability_ind DECIMAL(1,0),
    rating_order INT
)
lock mode row;

create table 'informix'.project_phase (
    project_phase_id INT not null,
    project_id INT not null,
    phase_type_id INT not null,
    phase_status_id INT not null,
    fixed_start_time DATETIME YEAR TO FRACTION,
    scheduled_start_time DATETIME YEAR TO FRACTION not null,
    scheduled_end_time DATETIME YEAR TO FRACTION not null,
    actual_start_time DATETIME YEAR TO FRACTION,
    actual_end_time DATETIME YEAR TO FRACTION,
    duration DECIMAL(16,0) not null,
    create_user VARCHAR(64) not null,
    create_date DATETIME YEAR TO FRACTION not null,
    modify_user VARCHAR(64) not null,
    modify_date DATETIME YEAR TO FRACTION not null
)
lock mode row;

create table 'informix'.project (
    project_id INT not null,
    project_status_id INT not null,
    project_category_id INT not null,
    create_user VARCHAR(64) not null,
    create_date DATETIME YEAR TO FRACTION not null,
    modify_user VARCHAR(64) not null,
    modify_date DATETIME YEAR TO FRACTION not null,
    tc_direct_project_id INT
)
lock mode row;

create table 'informix'.component_inquiry (
    component_inquiry_id DECIMAL(12,0) not null,
    component_id DECIMAL(12,0),
    user_id DECIMAL(12,0) not null,
    comment VARCHAR(254),
    agreed_to_terms DECIMAL(1) not null,
    rating DECIMAL(5) not null,
    phase DECIMAL(12,0),
    tc_user_id DECIMAL(12,0),
    version DECIMAL(12,0),
    create_time DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION not null,
    project_id DECIMAL(10,0)
)
lock mode row;

create table 'informix'.upload (
    upload_id INT not null,
    project_id INT not null,
    resource_id INT not null,
    upload_type_id INT not null,
    upload_status_id INT not null,
    parameter VARCHAR(254) not null,
    create_user VARCHAR(64) not null,
    create_date DATETIME YEAR TO FRACTION not null,
    modify_user VARCHAR(64) not null,
    modify_date DATETIME YEAR TO FRACTION not null
)
lock mode row;

create table 'informix'.submission (
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
    modify_date DATETIME YEAR TO FRACTION not null
)
lock mode row;

create table 'informix'.resource (
    resource_id INT not null,
    resource_role_id INT not null,
    project_id INT,
    project_phase_id INT,
    create_user VARCHAR(64) not null,
    create_date DATETIME YEAR TO FRACTION not null,
    modify_user VARCHAR(64) not null,
    modify_date DATETIME YEAR TO FRACTION not null
)
lock mode row;

create table 'informix'.resource_info (
    resource_id INT not null,
    resource_info_type_id INT not null,
    value VARCHAR(255) not null,
    create_user VARCHAR(64) not null,
    create_date DATETIME YEAR TO FRACTION not null,
    modify_user VARCHAR(64) not null,
    modify_date DATETIME YEAR TO FRACTION not null
)
lock mode row;

create table 'informix'.user_reliability (
    user_id DECIMAL(10,0),
    rating DECIMAL(5,4),
    modify_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
    create_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
    phase_id DECIMAL(12,0)
)
lock mode row;

create table 'informix'.project_reliability (
    project_id INT NOT NULL,
    user_id DECIMAL(10,0) NOT NULL,
    resolution_date DATETIME YEAR TO FRACTION NOT NULL,
    reliability_before_resolution DECIMAL(5,4),
    reliability_after_resolution DECIMAL(5,4) NOT NULL,
    reliability_on_registration DECIMAL(5,4),
    reliable_ind DECIMAL(1,0) NOT NULL
)
lock mode row;

create table 'informix'.phase_type_lu (
    phase_type_id INT not null,
    name VARCHAR(64) not null,
    description VARCHAR(254) not null,
    create_user VARCHAR(64) not null,
    create_date DATETIME YEAR TO FRACTION not null,
    modify_user VARCHAR(64) not null,
    modify_date DATETIME YEAR TO FRACTION not null
)
lock mode row;

create table 'informix'.project_category_lu (
    project_category_id INT not null,
    project_type_id INT not null,
    name VARCHAR(64) not null,
    description VARCHAR(254) not null,
    create_user VARCHAR(64) not null,
    create_date DATETIME YEAR TO FRACTION not null,
    modify_user VARCHAR(64) not null,
    modify_date DATETIME YEAR TO FRACTION not null,
    display boolean(1),
    display_order INT
)
lock mode row;

create table 'informix'.project_status_lu (
    project_status_id INT not null,
    name VARCHAR(64) not null,
    description VARCHAR(254) not null,
    create_user VARCHAR(64) not null,
    create_date DATETIME YEAR TO FRACTION not null,
    modify_user VARCHAR(64) not null,
    modify_date DATETIME YEAR TO FRACTION not null
)
lock mode row;

create table 'informix'.upload_type_lu (
    upload_type_id INT not null,
    name VARCHAR(64) not null,
    description VARCHAR(254) not null,
    create_user VARCHAR(64) not null,
    create_date DATETIME YEAR TO FRACTION not null,
    modify_user VARCHAR(64) not null,
    modify_date DATETIME YEAR TO FRACTION not null
)
lock mode row;

create table 'informix'.upload_status_lu (
    upload_status_id INT not null,
    name VARCHAR(64) not null,
    description VARCHAR(254) not null,
    create_user VARCHAR(64) not null,
    create_date DATETIME YEAR TO FRACTION not null,
    modify_user VARCHAR(64) not null,
    modify_date DATETIME YEAR TO FRACTION not null
)
lock mode row;

CREATE TABLE 'informix'.submission_type_lu (
    submission_type_id            INTEGER                         not null,
    name                          VARCHAR(64)                     not null,
    description                   VARCHAR(254)                    not null,
    create_user                   VARCHAR(64)                     not null,
    create_date                   DATETIME YEAR TO FRACTION       not null,
    modify_user                   VARCHAR(64)                     not null,
    modify_date                   DATETIME YEAR TO FRACTION       not null
)
lock mode row;

create table 'informix'.submission_status_lu (
    submission_status_id INT not null,
    name VARCHAR(64) not null,
    description VARCHAR(254) not null,
    create_user VARCHAR(64) not null,
    create_date DATETIME YEAR TO FRACTION not null,
    modify_user VARCHAR(64) not null,
    modify_date DATETIME YEAR TO FRACTION not null
)
lock mode row;

create table 'informix'.resource_role_lu (
    resource_role_id INT not null,
    phase_type_id INT,
    name VARCHAR(64) not null,
    description VARCHAR(254) not null,
    create_user VARCHAR(64) not null,
    create_date DATETIME YEAR TO FRACTION not null,
    modify_user VARCHAR(64) not null,
    modify_date DATETIME YEAR TO FRACTION not null
)
lock mode row;

create table 'informix'.resource_info_type_lu (
    resource_info_type_id INT not null,
    name VARCHAR(64) not null,
    description VARCHAR(254) not null,
    create_user VARCHAR(64) not null,
    create_date DATETIME YEAR TO FRACTION not null,
    modify_user VARCHAR(64) not null,
    modify_date DATETIME YEAR TO FRACTION not null
)
lock mode row;

create table 'informix'.phase (
    phase_id DECIMAL(12,0) not null,
    description VARCHAR(254) not null
)
lock mode row;

create table 'informix'.project_type_lu (
    project_type_id INT not null,
    name VARCHAR(64) not null,
    description VARCHAR(254) not null,
    is_generic boolean default 'f'not null,
    create_user VARCHAR(64) not null,
    create_date DATETIME YEAR TO FRACTION not null,
    modify_user VARCHAR(64) not null,
    modify_date DATETIME YEAR TO FRACTION not null
)
lock mode row;

create table 'informix'.phase_status_lu (
    phase_status_id INT not null,
    name VARCHAR(64) not null,
    description VARCHAR(254) not null,
    create_user VARCHAR(64) not null,
    create_date DATETIME YEAR TO FRACTION not null,
    modify_user VARCHAR(64) not null,
    modify_date DATETIME YEAR TO FRACTION not null
)
lock mode row;

create table 'informix'.contest_eligibility (
    contest_eligibility_id DECIMAL(10, 0) not null,
    contest_id DECIMAL(10,0) not null,
    is_studio SMALLINT NOT NULL,
    PRIMARY KEY (contest_eligibility_id) constraint contest_eligibility_pk
)
lock mode row;

create table 'informix'.project_info (
    project_id INT not null,
    project_info_type_id INT not null,
    value VARCHAR(255) not null,
    create_user VARCHAR(64) not null,
    create_date DATETIME YEAR TO FRACTION not null,
    modify_user VARCHAR(64) not null,
    modify_date DATETIME YEAR TO FRACTION not null
)
lock mode row;

create table 'informix'.project_info_type_lu (
    project_info_type_id INT not null,
    name VARCHAR(64) not null,
    description VARCHAR(25) not null,
    create_user VARCHAR(64) not null,
    create_date DATETIME YEAR TO FRACTION not null,
    modify_user VARCHAR(64) not null,
    modify_date DATETIME YEAR TO FRACTION not null
)
lock mode row;
