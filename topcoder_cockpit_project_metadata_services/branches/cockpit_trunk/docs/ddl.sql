CREATE  TABLE IF NOT EXISTS 'application'.direct_project_metadata (
  direct_project_metadata_id DECIMAL(10,0) ,
  tc_direct_project_id DECIMAL(10,0) NOT NULL ,
  project_metadata_key_id DECIMAL(10,0) NOT NULL ,
  metadata_value VARCHAR(500) NOT NULL ,
  PRIMARY KEY (direct_project_metadata_id),
    FOREIGN KEY (project_metadata_key_id )
    REFERENCES 'application'.direct_project_metadata_key (direct_project_metadata_key_id )
)


CREATE  TABLE IF NOT EXISTS 'application'.direct_project_metadata_key (
  direct_project_metadata_key_id DECIMAL(10,0) ,
  name VARCHAR(45) NOT NULL ,
  description VARCHAR(255) NULL ,
  grouping CHAR(1) NULL ,
  client_id DECIMAL(10,0) NULL ,
  single_value CHAR(1) NOT NULL ,
  PRIMARY KEY (direct_project_metadata_key_id_id),
)


CREATE  TABLE IF NOT EXISTS 'application'.direct_project_metadata_predefined_value (
  direct_project_metadata_predefined_value_id DECIMAL(10,0) ,
  project_metadata_key_id DECIMAL(10,0) NOT NULL ,
  predefined_metadata_value VARCHAR(500) NOT NULL ,
  value_position INT NOT NULL ,
    PRIMARY KEY (direct_project_metadata_predefined_value_id),
    FOREIGN KEY (project_metadata_key_id )
    REFERENCES 'application'.direct_project_metadata_key (direct_project_metadata_key_id )
)


CREATE  TABLE IF NOT EXISTS 'application'.direct_project_metadata_audit (
  direct_project_metadata_audit_id DECIMAL(10,0) ,
  project_metadata_id DECIMAL(10,0) NOT NULL ,
  tc_direct_project_id DECIMAL(10,0) NOT NULL ,
  project_metadata_key_id DECIMAL(10,0) NOT NULL ,
  metadata_value VARCHAR(500) NOT NULL ,
  audit_action_type_id INT NOT NULL ,
  action_date DATETIME YEAR TO FRACTION(3),
  action_user_id DECIMAL(12,0) NOT NULL ,
    PRIMARY KEY (direct_project_metadata_audit_id),
    FOREIGN KEY (audit_action_type_id )
    REFERENCES 'common_oltp'.audit_action_type_lu (audit_action_type_id )
)


CREATE  TABLE IF NOT EXISTS 'application'.direct_project_metadata_key_audit (
  direct_project_metadata_key_audit_id DECIMAL(10,0) ,
  project_metadata_key_id DECIMAL(10,0) NOT NULL ,
  name VARCHAR(45) NOT NULL ,
  description VARCHAR(255) NULL ,
  grouping CHAR(1) NULL ,
  client_id DECIMAL(10,0) NULL ,
  audit_action_type_id INT NOT NULL ,
  action_date DATETIME YEAR TO FRACTION(3) ,
  action_user_id DECIMAL(12,0) NOT NULL ,
  single_value CHAR(1) NOT NULL ,
    PRIMARY KEY (direct_project_metadata_key_audit_id),
    FOREIGN KEY (audit_action_type_id )
    REFERENCES 'common_oltp'.audit_action_type_lu (audit_action_type_id )
)


CREATE  TABLE IF NOT EXISTS 'application'.direct_project_metadata_predefined_value_audit (
  direct_project_metadata_predefined_value_audit_id DECIMAL(10,0) ,
  project_metadata_predefined_value_id DECIMAL(10,0) NOT NULL ,
  project_metadata_key_id DECIMAL(10,0) NOT NULL ,
  predefined_metadata_value VARCHAR(500) NOT NULL ,
  value_position INT NOT NULL ,
  audit_action_type_id INT NOT NULL ,
  action_date DATETIME YEAR TO FRACTION(3) ,
  action_user_id DECIMAL(12,0) NOT NULL ,
    PRIMARY KEY (direct_project_metadata_predefined_value_audit_id),
    FOREIGN KEY (audit_action_type_id )
    REFERENCES 'commonOltp'.audit_action_type_lu (audit_action_type_id )
)