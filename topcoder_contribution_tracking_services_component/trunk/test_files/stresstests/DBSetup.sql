
-- -----------------------------------------------------
-- Table tc_direct_project
-- -----------------------------------------------------
create table "informix".tc_direct_project 
  (
    project_id integer not null ,
    name varchar(200) not null ,
    description lvarchar(10000),
    project_status_id INT default 1 not null,
    user_id integer not null ,
    create_date datetime year to fraction(3) not null ,
    modify_date datetime year to fraction(3),
    primary key (project_id)
  ); 


-- -----------------------------------------------------
-- Table project
-- -----------------------------------------------------
create table 'informix'.project (
    project_id INT not null,
    project_status_id INT not null,
    project_category_id INT not null,
    project_studio_spec_id INTEGER,                        
    create_user VARCHAR(64) not null,
    create_date DATETIME YEAR TO FRACTION not null,
    modify_user VARCHAR(64) not null,
    modify_date DATETIME YEAR TO FRACTION not null,
    tc_direct_project_id INT,
    primary key (project_id));


-- -----------------------------------------------------
-- Table direct_project_cp_config
-- -----------------------------------------------------
create table 'informix'.direct_project_cp_config (
    tc_direct_project_id INT NOT NULL,
    use_cp decimal(1,0) NOT NULL,
    available_immediate_budget DECIMAL(10,2) NOT NULL,
    PRIMARY KEY (tc_direct_project_id),
    FOREIGN KEY (tc_direct_project_id) REFERENCES tc_direct_project (project_id));

-- -----------------------------------------------------
-- Table project_contest_cp_config
-- -----------------------------------------------------
create table 'informix'.project_contest_cp_config (
    project_id INT NOT NULL,
    total_original_payment DECIMAL(10,2) NOT NULL,
    cp_rate DECIMAL(10,2) NOT NULL,
    PRIMARY KEY (project_id),
    FOREIGN KEY (project_id) REFERENCES project (project_id));

-- -----------------------------------------------------
-- Table member_contribution_points
-- -----------------------------------------------------
create table 'informix'.member_contribution_points (
    member_contribution_points_id SERIAL NOT NULL,
    user_id INT NOT NULL,
    project_id INT NOT NULL,
    contribution_points DECIMAL(10,2) NOT NULL,
    contribution_type VARCHAR(45) NOT NULL,
    PRIMARY KEY (member_contribution_points_id),
    FOREIGN KEY (project_id) REFERENCES project (project_id));