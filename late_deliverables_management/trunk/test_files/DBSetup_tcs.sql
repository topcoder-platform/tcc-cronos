
database tcs_catalog;

CREATE TABLE "informix".late_deliverable (
    late_deliverable_id serial NOT NULL,
    project_phase_id INTEGER NOT NULL,
    resource_id INTEGER NOT NULL,
    deliverable_id INTEGER NOT NULL,
    deadline DATETIME YEAR TO FRACTION NOT NULL,
    create_date DATETIME YEAR TO FRACTION NOT NULL,
    forgive_ind DECIMAL(1,0) NOT NULL,
    last_notified DATETIME YEAR TO FRACTION,
    delay DECIMAL(16,0),
    explanation lvarchar(4096),
    explanation_date DATETIME YEAR TO FRACTION,
    response lvarchar(4096),
    response_user VARCHAR(64),
    response_date DATETIME YEAR TO FRACTION
);

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
);

create table 'informix'.project (
    project_id INT not null,
    project_status_id INT not null,
    project_category_id INT not null,
    create_user VARCHAR(64) not null,
    create_date DATETIME YEAR TO FRACTION not null,
    modify_user VARCHAR(64) not null,
    modify_date DATETIME YEAR TO FRACTION not null,
    tc_direct_project_id INT
);


create table 'informix'.resource_info (
    resource_id INT not null,
    resource_info_type_id INT not null,
    value VARCHAR(255) not null,
    create_user VARCHAR(64) not null,
    create_date DATETIME YEAR TO FRACTION not null,
    modify_user VARCHAR(64) not null,
    modify_date DATETIME YEAR TO FRACTION not null
);

create table 'informix'.resource (
    resource_id INT not null,
    resource_role_id INT not null,
    project_id INT,
    project_phase_id INT,
    create_user VARCHAR(64) not null,
    create_date DATETIME YEAR TO FRACTION not null,
    modify_user VARCHAR(64) not null,
    modify_date DATETIME YEAR TO FRACTION not null
);