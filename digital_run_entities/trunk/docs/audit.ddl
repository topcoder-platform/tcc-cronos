##the operation name should be one of the following string values:
##create -- indicate that the row is created.
##delete -- indicate that the row is deleted
##before_update -- indicate that the old row before update
##after_update -- indicate that the new row after update
##
##The other columns just duplicate the coressponding table columns 
##
CREATE TABLE dr_points_audit (
  id INT PRIMARY KEY,
  audit_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
  audit_user VARCHAR(20) NOT NULL,
  operation_name VARCHAR(20) NOT NULL
  dr_points_id INT NOT NULL,
  track_id INT NOT NULL,
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

##dr_points_audit_update table wire the before update and after update operations by the id.
##The auditor component can use this table to look up the old and new entry for the update operation
CREATE TABLE dr_points_audit_update (
  before_update INT NOT NULL,
  after_update INT NOT NULL,
  FOREIGN KEY (before_update) REFERENCES dr_points_audit (id),
  FOREIGN KEY (after_update) REFERENCES dr_points_audit (id)
);


##Please note that all the following tables follow the same pattern
CREATE TABLE dr_points_operation_lu_audit (
  id INT PRIMARY KEY,
  audit_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
  audit_user VARCHAR(20) NOT NULL,
  operation_name VARCHAR(20) NOT NULL
  dr_points_operation_id INT NOT NULL,
  dr_points_operation_desc VARCHAR(50) NOT NULL,
  create_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
  modify_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION
);
CREATE TABLE dr_points_operation_lu_audit_update (
  before_update INT NOT NULL,
  after_update INT NOT NULL,
  FOREIGN KEY (before_update) REFERENCES dr_points_operation_lu_audit (id),
  FOREIGN KEY (after_update) REFERENCES dr_points_operation_lu_audit (id)
);


CREATE TABLE dr_points_reference_type_lu_audit (
  id INT PRIMARY KEY,
  audit_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
  audit_user VARCHAR(20) NOT NULL,
  operation_name VARCHAR(20) NOT NULL
  dr_points_reference_type_id INT NOT NULL,
  dr_points_reference_type_desc VARCHAR(50) NOT NULL,
  create_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
  modify_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION
);
CREATE TABLE dr_points_reference_type_lu_audit_update (
  before_update INT NOT NULL,
  after_update INT NOT NULL,
  FOREIGN KEY (before_update) REFERENCES dr_points_reference_type_lu_audit (id),
  FOREIGN KEY (after_update) REFERENCES dr_points_reference_type_lu_audit (id)
);


CREATE TABLE dr_points_type_lu_audit (
  id INT PRIMARY KEY,
  audit_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
  audit_user VARCHAR(20) NOT NULL,
  operation_name VARCHAR(20) NOT NULL
  dr_points_type_id INT NOT NULL,
  dr_points_type_desc VARCHAR(50) NOT NULL,
  create_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
  modify_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION
);
CREATE TABLE dr_points_type_lu_audit_update (
  before_update INT NOT NULL,
  after_update INT NOT NULL,
  FOREIGN KEY (before_update) REFERENCES dr_points_type_lu_audit (id),
  FOREIGN KEY (after_update) REFERENCES dr_points_type_lu_audit (id)
);

CREATE TABLE dr_points_status_lu_audit (
  id INT PRIMARY KEY,
  audit_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
  audit_user VARCHAR(20) NOT NULL,
  operation_name VARCHAR(20) NOT NULL
  dr_points_status_id INT NOT NULL,
  dr_points_status_desc VARCHAR(50) NOT NULL,
  create_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
  modify_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION
);
CREATE TABLE dr_points_status_lu_audit_update (
  before_update INT NOT NULL,
  after_update INT NOT NULL,
  FOREIGN KEY (before_update) REFERENCES dr_points_status_lu_audit (id),
  FOREIGN KEY (after_update) REFERENCES dr_points_status_lu_audit (id)
);


CREATE TABLE points_calculator_lu_audit (
  id INT PRIMARY KEY,
  audit_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
  audit_user VARCHAR(20) NOT NULL,
  operation_name VARCHAR(20) NOT NULL
  points_calculator_id INT NOT NULL,
  class_name VARCHAR(100) NOT NULL,
  create_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
  modify_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION
);
CREATE TABLE points_calculator_lu_audit_update (
  before_update INT NOT NULL,
  after_update INT NOT NULL,
  FOREIGN KEY (before_update) REFERENCES points_calculator_lu_audit (id),
  FOREIGN KEY (after_update) REFERENCES points_calculator_lu_audit (id)
);


CREATE TABLE track_audit (
  id INT PRIMARY KEY,
  audit_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
  audit_user VARCHAR(20) NOT NULL,
  operation_name VARCHAR(20) NOT NULL
  track_id INT NOT NULL,
  points_calculator_id INT NOT NULL,
  track_type_id INT NOT NULL,
  track_status_id INT NOT NULL,
  track_desc VARCHAR(50) NOT NULL,
  track_start_date DATETIME YEAR TO FRACTION NOT NULL,
  track_end_date DATETIME YEAR TO FRACTION NOT NULL,
  create_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
  modify_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION
);
CREATE TABLE track_audit_update (
  before_update INT NOT NULL,
  after_update INT NOT NULL,
  FOREIGN KEY (before_update) REFERENCES track_audit (id),
  FOREIGN KEY (after_update) REFERENCES track_audit (id)
);


CREATE TABLE track_contest_audit (
  id INT PRIMARY KEY,
  audit_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
  audit_user VARCHAR(20) NOT NULL,
  operation_name VARCHAR(20) NOT NULL
  track_contest_id INT NOT NULL,
  track_contest_result_calculator_id INT NOT NULL,
  track_contest_type_id INT NOT NULL,
  track_id INT NOT NULL,
  track_contest_desc VARCHAR(128) NOT NULL,
  create_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
  modify_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION
);
CREATE TABLE track_contest_audit_update (
  before_update INT NOT NULL,
  after_update INT NOT NULL,
  FOREIGN KEY (before_update) REFERENCES track_contest_audit (id),
  FOREIGN KEY (after_update) REFERENCES track_contest_audit (id)
);


CREATE TABLE track_contest_result_calculator_lu_audit (
  id INT PRIMARY KEY,
  audit_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
  audit_user VARCHAR(20) NOT NULL,
  operation_name VARCHAR(20) NOT NULL
  track_contest_result_calculator_id INT NOT NULL,
  class_name VARCHAR(100) NOT NULL,
  create_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
  modify_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION
);
CREATE TABLE track_contest_result_calculator_lu_audit_update (
  before_update INT NOT NULL,
  after_update INT NOT NULL,
  FOREIGN KEY (before_update) REFERENCES track_contest_result_calculator_lu_audit (id),
  FOREIGN KEY (after_update) REFERENCES track_contest_result_calculator_lu_audit (id)
);


CREATE TABLE track_contest_type_lu_audit (
  id INT PRIMARY KEY,
  audit_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
  audit_user VARCHAR(20) NOT NULL,
  operation_name VARCHAR(20) NOT NULL
  track_contest_type_id INT NOT NULL,
  track_contest_type_desc VARCHAR(50) NOT NULL,
  create_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
  modify_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION
);
CREATE TABLE track_contest_type_lu_audit_update (
  before_update INT NOT NULL,
  after_update INT NOT NULL,
  FOREIGN KEY (before_update) REFERENCES track_contest_type_lu_audit (id),
  FOREIGN KEY (after_update) REFERENCES track_contest_type_lu_audit (id)
);


CREATE TABLE track_project_type_xref_audit (
  id INT PRIMARY KEY,
  audit_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
  audit_user VARCHAR(20) NOT NULL,
  operation_name VARCHAR(20) NOT NULL
  track_id INT NOT NULL,
  project_type_id INT NOT NULL
);
CREATE TABLE track_project_type_xref_audit_update (
  before_update INT NOT NULL,
  after_update INT NOT NULL,
  FOREIGN KEY (before_update) REFERENCES track_project_type_xref_audit (id),
  FOREIGN KEY (after_update) REFERENCES track_project_type_xref_audit (id)
);


CREATE TABLE track_type_lu_audit (
  id INT PRIMARY KEY,
  audit_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
  audit_user VARCHAR(20) NOT NULL,
  operation_name VARCHAR(20) NOT NULL
  track_type_id INT NOT NULL,
  track_type_desc VARCHAR(50) NOT NULL,
  create_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
  modify_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION
);
CREATE TABLE track_type_lu_audit_update (
  before_update INT NOT NULL,
  after_update INT NOT NULL,
  FOREIGN KEY (before_update) REFERENCES track_type_lu_audit (id),
  FOREIGN KEY (after_update) REFERENCES track_type_lu_audit (id)
);


CREATE TABLE track_status_lu_audit (
  id INT PRIMARY KEY,
  audit_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
  audit_user VARCHAR(20) NOT NULL,
  operation_name VARCHAR(20) NOT NULL
  track_status_id INT NOT NULL,
  track_status_desc VARCHAR(50) NOT NULL,
  create_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
  modify_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION
);
CREATE TABLE track_status_lu_audit_update (
  before_update INT NOT NULL,
  after_update INT NOT NULL,
  FOREIGN KEY (before_update) REFERENCES track_status_lu_audit (id),
  FOREIGN KEY (after_update) REFERENCES track_status_lu_audit (id)
);