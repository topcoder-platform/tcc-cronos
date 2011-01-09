CREATE TABLE project_type_lu (
  project_type_id               INTEGER                        NOT NULL,
  name                          VARCHAR(64)                     NOT NULL,
  description                   VARCHAR(254)                    NOT NULL,
  create_user                   VARCHAR(64)                     NOT NULL,
  create_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  modify_user                   VARCHAR(64)                     NOT NULL,
  modify_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  is_generic 			CHAR(1) NOT NULL,
  review_system_version         INTEGER NOT NULL,
  PRIMARY KEY(project_type_id)
);

CREATE TABLE project (
  project_id                    DECIMAL(10,0)                   NOT NULL,
  PRIMARY KEY(project_id)
);
CREATE TABLE phase_type_lu (
  phase_type_id                 INTEGER                         NOT NULL,
  PRIMARY KEY(phase_type_id)
);
CREATE TABLE project_phase (
  project_phase_id              INTEGER                         NOT NULL,
  project_id                    DECIMAL(10,0)                   NOT NULL,
  phase_type_id                 INTEGER                         NOT NULL,
  PRIMARY KEY(project_phase_id),
  FOREIGN KEY(phase_type_id)
    REFERENCES phase_type_lu(phase_type_id),
  FOREIGN KEY(project_id)
    REFERENCES project(project_id)
);
CREATE TABLE submission (
  submission_id                 INTEGER                         NOT NULL,
  PRIMARY KEY(submission_id)
);
CREATE TABLE resource_role_lu (
  resource_role_id              INTEGER                         NOT NULL,
  phase_type_id                 INTEGER,
  name                          VARCHAR(64)                     NOT NULL,
  description                   VARCHAR(254)                    NOT NULL,
  create_user                   VARCHAR(64)                     NOT NULL,
  create_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  modify_user                   VARCHAR(64)                     NOT NULL,
  modify_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  PRIMARY KEY(resource_role_id),
  FOREIGN KEY(phase_type_id)
    REFERENCES phase_type_lu(phase_type_id)
);
CREATE TABLE resource (
  resource_id                   INTEGER                         NOT NULL,
  resource_role_id              INTEGER                         NOT NULL,
  project_id                    DECIMAL(10,0),
  project_phase_id              INTEGER,
  create_user                   VARCHAR(64)                     NOT NULL,
  create_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  modify_user                   VARCHAR(64)                     NOT NULL,
  modify_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  PRIMARY KEY(resource_id),
  FOREIGN KEY(project_id)
    REFERENCES project(project_id),
  FOREIGN KEY(resource_role_id)
    REFERENCES resource_role_lu(resource_role_id),
  FOREIGN KEY(project_phase_id)
    REFERENCES project_phase(project_phase_id)
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
  create_user                   VARCHAR(64)                     NOT NULL,
  create_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  modify_user                   VARCHAR(64)                     NOT NULL,
  modify_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  PRIMARY KEY(resource_id, resource_info_type_id),
  FOREIGN KEY(resource_info_type_id)
    REFERENCES resource_info_type_lu(resource_info_type_id),
  FOREIGN KEY(resource_id)
    REFERENCES resource(resource_id)
);
CREATE TABLE resource_submission (
  resource_id                   INTEGER                         NOT NULL,
  submission_id                 INTEGER                         NOT NULL,
  create_user                   VARCHAR(64)                     NOT NULL,
  create_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  modify_user                   VARCHAR(64)                     NOT NULL,
  modify_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  PRIMARY KEY(resource_id, submission_id),
  FOREIGN KEY(submission_id)
    REFERENCES submission(submission_id),
  FOREIGN KEY(resource_id)
    REFERENCES resource(resource_id)
);
CREATE TABLE notification_type_lu (
  notification_type_id          INTEGER                         NOT NULL,
  name                          VARCHAR(64)                     NOT NULL,
  description                   VARCHAR(254)                    NOT NULL,
  create_user                   VARCHAR(64)                     NOT NULL,
  create_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  modify_user                   VARCHAR(64)                     NOT NULL,
  modify_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  PRIMARY KEY(notification_type_id)
);
CREATE TABLE notification (
  project_id                    DECIMAL(10,0)                   NOT NULL,
  external_ref_id               INTEGER                         NOT NULL,
  notification_type_id          INTEGER                         NOT NULL,
  create_user                   VARCHAR(64)                     NOT NULL,
  create_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  modify_user                   VARCHAR(64)                     NOT NULL,
  modify_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  PRIMARY KEY(project_id, external_ref_id, notification_type_id),
  FOREIGN KEY(project_id)
    REFERENCES project(project_id),
  FOREIGN KEY(notification_type_id)
    REFERENCES notification_type_lu(notification_type_id)
);

CREATE TABLE id_sequences (
  name                  VARCHAR(255)    NOT NULL,
  next_block_start      DECIMAL(10,0)   NOT NULL,
  block_size            DECIMAL(10,0)   NOT NULL,
  exhausted             DECIMAL(10,0)   NOT NULL,
  PRIMARY KEY (name)
);

CREATE TABLE project_user_audit  (
    project_user_audit_id DECIMAL(12,0) not null,
    project_id INT not null,
    resource_user_id DECIMAL(12,0) not null,
    resource_role_id INT not null,
    audit_action_type_id INT not null,
    action_date DATETIME YEAR TO FRACTION not null,
    action_user_id DECIMAL(12,0) not null
);

CREATE TABLE rboard_user (
    user_id INTEGER not null,
    project_type_id DECIMAL(12,0) not null,
    catalog_id DECIMAL(12,0) not null,
    status_id DECIMAL(3,0) not null,
    immune_ind DECIMAL(1,0) not null,
    PRIMARY KEY(user_id)
);

CREATE SEQUENCE PROJECT_USER_AUDIT_SEQ;

INSERT INTO id_sequences(name, next_block_start, block_size, exhausted) VALUES('resource_id_seq', 1, 20, 0);
INSERT INTO id_sequences(name, next_block_start, block_size, exhausted) VALUES('resource_role_id_seq', 1, 20, 0);
INSERT INTO id_sequences(name, next_block_start, block_size, exhausted) VALUES('notification_type_id_seq', 1, 20, 0);
INSERT INTO id_sequences(name, next_block_start, block_size, exhausted) VALUES('project_user_audit_seq', 1, 20, 0);
INSERT INTO id_sequences(name, next_block_start, block_size, exhausted) VALUES('review_statistics_id_seq', 20, 20, 0);
INSERT INTO id_sequences(name, next_block_start, block_size, exhausted) VALUES('reviewer_statistics_id_seq', 20, 20, 0);

CREATE TABLE average_review_statistics(
	id DECIMAL(10,0) NOT NULL,
	accuracy FLOAT NOT NULL,
	coverage FLOAT NOT NULL,
	timeline_reliability FLOAT NOT NULL,
	total_evaluation_coefficient FLOAT NOT NULL,
	reviewer_id INTEGER NOT NULL,
	eligibility_points FLOAT NOT NULL,
	project_id DECIMAL(10,0) NOT NULL,
	competition_type_id INTEGER NOT NULL,
  	create_user VARCHAR(64) NOT NULL,
  	create_date DATETIME YEAR TO FRACTION(3) NOT NULL,
  	modify_user VARCHAR(64) NOT NULL,
  	modify_date DATETIME YEAR TO FRACTION(3) NOT NULL,
    PRIMARY KEY(id, competition_type_id),
    FOREIGN KEY(project_id)
       REFERENCES project(project_id),
    FOREIGN KEY(reviewer_id)
      REFERENCES rboard_user(user_id),
    FOREIGN KEY(competition_type_id)
      REFERENCES project_type_lu(project_type_id)
);

CREATE TABLE history_statistics(
	id DECIMAL(10,0) NOT NULL,
	accuracy FLOAT NOT NULL,
	coverage FLOAT NOT NULL,
	timeline_reliability FLOAT NOT NULL,
	total_evaluation_coefficient FLOAT NOT NULL,
	reviewer_id INTEGER NOT NULL,
	eligibility_points FLOAT NOT NULL,
	project_id DECIMAL(10,0) NOT NULL,
	competition_type_id INTEGER NOT NULL,
  	create_user VARCHAR(64) NOT NULL,
  	create_date DATETIME YEAR TO FRACTION(3) NOT NULL,
  	modify_user VARCHAR(64) NOT NULL,
  	modify_date DATETIME YEAR TO FRACTION(3) NOT NULL,
    PRIMARY KEY(id, competition_type_id),
    FOREIGN KEY(project_id)
       REFERENCES project(project_id),
    FOREIGN KEY(reviewer_id)
      REFERENCES rboard_user(user_id),
    FOREIGN KEY(competition_type_id)
      REFERENCES project_type_lu(project_type_id)
);
