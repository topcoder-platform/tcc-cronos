CREATE TABLE project_category_lu (
  project_category_id           INTEGER                         NOT NULL,
  PRIMARY KEY(project_category_id)
);

CREATE TABLE project (
  project_id                    INTEGER                         NOT NULL,
  project_category_id           INTEGER                         NOT NULL,
  PRIMARY KEY(project_id),
  FOREIGN KEY(project_category_id)
    REFERENCES project_category_lu(project_category_id)
);

CREATE TABLE resource (
  resource_id                   INTEGER                         NOT NULL,
  PRIMARY KEY(resource_id)
);

CREATE TABLE resource_info_type_lu (
  resource_info_type_id         INTEGER                         NOT NULL,
  name                          VARCHAR(64)                     NOT NULL,
  description                   VARCHAR(254)                    NOT NULL,
  create_user                   VARCHAR(64)                     NOT NULL,
  create_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  modify_user                   VARCHAR(64)                     NOT NULL,
  modify_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  PRIMARY KEY(resource_info_type_id)
);

CREATE TABLE resource_info (
  resource_id                   INTEGER                         NOT NULL,
  resource_info_type_id         INTEGER                         NOT NULL,
  value                         LVARCHAR(4096)                  NOT NULL,
  PRIMARY KEY(resource_id, resource_info_type_id),
  FOREIGN KEY(resource_info_type_id)
    REFERENCES resource_info_type_lu(resource_info_type_id),
  FOREIGN KEY(resource_id)
    REFERENCES resource(resource_id)
);

CREATE TABLE upload (
  upload_id                     INTEGER                         NOT NULL,
  project_id                    INTEGER                         NOT NULL,
  resource_id                   INTEGER                         NOT NULL,
  parameter 			VARCHAR(254)			NOT NULL,
  PRIMARY KEY(upload_id),
  FOREIGN KEY(resource_id)
    REFERENCES resource(resource_id),
  FOREIGN KEY(project_id)
    REFERENCES project(project_id)
);

CREATE TABLE screening_status_lu (
  screening_status_id           INTEGER                         NOT NULL,
  name                          VARCHAR(64)                     NOT NULL,
  description                   VARCHAR(254)                    NOT NULL,
  create_user                   VARCHAR(64)                     NOT NULL,
  create_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  modify_user                   VARCHAR(64)                     NOT NULL,
  modify_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  PRIMARY KEY(screening_status_id)
);

CREATE TABLE screening_task (
  screening_task_id             INTEGER                         NOT NULL,
  upload_id                     INTEGER                         NOT NULL,
  screening_status_id           INTEGER                         NOT NULL,
  screener_id                   INTEGER,
  start_timestamp               DATETIME YEAR TO FRACTION(3),
  create_user                   VARCHAR(64)                     NOT NULL,
  create_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  modify_user                   VARCHAR(64)                     NOT NULL,
  modify_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  PRIMARY KEY(screening_task_id),
  FOREIGN KEY(upload_id)
    REFERENCES upload(upload_id),
  FOREIGN KEY(screening_status_id)
    REFERENCES screening_status_lu(screening_status_id)
);

CREATE TABLE response_severity_lu (
  response_severity_id          INTEGER                         NOT NULL,
  name                          VARCHAR(64)                     NOT NULL,
  description                   VARCHAR(254)                    NOT NULL,
  create_user                   VARCHAR(64)                     NOT NULL,
  create_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  modify_user                   VARCHAR(64)                     NOT NULL,
  modify_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  PRIMARY KEY(response_severity_id)
);

CREATE TABLE screening_response_lu (
  screening_response_id         INTEGER                         NOT NULL,
  response_severity_id          INTEGER                         NOT NULL,
  response_code                 VARCHAR(16)                     NOT NULL,
  response_text                 VARCHAR(254)                    NOT NULL,
  create_user                   VARCHAR(64)                     NOT NULL,
  create_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  modify_user                   VARCHAR(64)                     NOT NULL,
  modify_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  PRIMARY KEY(screening_response_id),
  FOREIGN KEY(response_severity_id)
    REFERENCES response_severity_lu(response_severity_id)
);

CREATE TABLE screening_result (
  screening_result_id           INTEGER                         NOT NULL,
  screening_task_id             INTEGER                         NOT NULL,
  screening_response_id         INTEGER                         NOT NULL,
  dynamic_response_text         LVARCHAR(4096)                  NOT NULL,
  create_user                   VARCHAR(64)                     NOT NULL,
  create_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  modify_user                   VARCHAR(64)                     NOT NULL,
  modify_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  PRIMARY KEY(screening_result_id),
  FOREIGN KEY(screening_task_id)
    REFERENCES screening_task(screening_task_id),
  FOREIGN KEY(screening_response_id)
    REFERENCES screening_response_lu(screening_response_id)
);

INSERT INTO resource_info_type_lu(resource_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(1, 'External Reference ID', 'External Reference ID', 'System', CURRENT, 'System', CURRENT);

CREATE TABLE id_sequences (
  name                  VARCHAR(255)    NOT NULL,
  next_block_start      INTEGER         NOT NULL,
  block_size            INTEGER         NOT NULL,
  exhausted             INTEGER         NOT NULL,
  PRIMARY KEY (name)
);

INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)
  VALUES('screening_task_id_seq', 1, 20, 0);
INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)
  VALUES('screening_result_id_seq', 1, 20, 0);

CREATE TABLE user (
	user_id        	DECIMAL(10,0) NOT NULL,
	first_name     	VARCHAR(64),
	last_name      	VARCHAR(64),
	create_date    	DATETIME YEAR to FRACTION(3) DEFAULT CURRENT YEAR to FRACTION(3),
	modify_date    	DATETIME YEAR to FRACTION(3) DEFAULT CURRENT YEAR to FRACTION(3),
	handle         	VARCHAR(50) NOT NULL,
	last_login     	DATETIME YEAR to FRACTION(3),
	status         	VARCHAR(3) NOT NULL,
	password       	VARCHAR(15),
	activation_code	VARCHAR(32),
	middle_name    	VARCHAR(64),
	handle_lower   	VARCHAR(50),
	timezone_id    	DECIMAL(5,0),
	PRIMARY KEY (user_id)
);

CREATE TABLE user_reliability (
	user_id    	DECIMAL(10,0),
	rating     	DECIMAL(5,4),
	modify_date	DATETIME YEAR to FRACTION(3) DEFAULT CURRENT YEAR to FRACTION(3),
	create_date	DATETIME YEAR to FRACTION(3) DEFAULT CURRENT YEAR to FRACTION(3),
	phase_id   	DECIMAL(12,0),
	PRIMARY KEY (phase_id, user_id)
);

CREATE TABLE user_rating (
	user_id              	DECIMAL(10) NOT NULL,
	rating               	DECIMAL(10) DEFAULT 0.0000000000000000 NOT NULL,
	phase_id             	DECIMAL(3) NOT NULL,
	vol                  	DECIMAL(10) DEFAULT 0.0000000000000000 NOT NULL,
	rating_no_vol        	DECIMAL(10) DEFAULT 0.0000000000000000 NOT NULL,
	num_ratings          	DECIMAL(5) DEFAULT 0.0000000000000000 NOT NULL,
	mod_date_time        	DATETIME YEAR to FRACTION(3) DEFAULT CURRENT YEAR to FRACTION(3) NOT NULL,
	create_date_time     	DATETIME YEAR to FRACTION(3) DEFAULT CURRENT YEAR to FRACTION(3) NOT NULL,
	last_rated_project_id	DECIMAL(12,0),
	PRIMARY KEY (phase_id, user_id)
);

CREATE TABLE email (
	user_id      	DECIMAL(10,0),
	email_id     	DECIMAL(10,0),
	email_type_id	DECIMAL(5,0),
	address      	VARCHAR(100),
	create_date  	DATETIME YEAR to FRACTION(3) DEFAULT CURRENT YEAR to FRACTION(3),
	modify_date  	DATETIME YEAR to FRACTION(3) DEFAULT CURRENT YEAR to FRACTION(3),
	primary_ind  	DECIMAL(1,0),
	status_id    	DECIMAL(3,0),
	PRIMARY KEY (email_id)
);
