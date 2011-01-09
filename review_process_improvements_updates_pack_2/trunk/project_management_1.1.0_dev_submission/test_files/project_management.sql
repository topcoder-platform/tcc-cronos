CREATE TABLE project_type_lu (
  project_type_id               DECIMAL(10,0)                   NOT NULL,
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

CREATE TABLE project_category_lu (
  project_category_id           DECIMAL(10,0)                   NOT NULL,
  project_type_id               DECIMAL(10,0)                   NOT NULL,
  name                          VARCHAR(64)                     NOT NULL,
  description                   VARCHAR(254)                    NOT NULL,
  create_user                   VARCHAR(64)                     NOT NULL,
  create_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  modify_user                   VARCHAR(64)                     NOT NULL,
  modify_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  PRIMARY KEY(project_category_id)
);

CREATE TABLE project_status_lu (
  project_status_id             DECIMAL(10,0)                   NOT NULL,
  name                          VARCHAR(64)                     NOT NULL,
  description                   VARCHAR(254)                    NOT NULL,
  create_user                   VARCHAR(64)                     NOT NULL,
  create_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  modify_user                   VARCHAR(64)                     NOT NULL,
  modify_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  PRIMARY KEY(project_status_id)
);

CREATE TABLE project (
  project_id                    DECIMAL(10,0)                   NOT NULL,
  project_status_id             DECIMAL(10,0)                   NOT NULL,
  project_category_id           DECIMAL(10,0)                   NOT NULL,
  create_user                   VARCHAR(64)                     NOT NULL,
  create_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  modify_user                   VARCHAR(64)                     NOT NULL,
  modify_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  PRIMARY KEY(project_id),
  FOREIGN KEY(project_category_id)
    REFERENCES project_category_lu(project_category_id),
  FOREIGN KEY(project_status_id)
    REFERENCES project_status_lu(project_status_id)
);

CREATE TABLE rboard_user (
    user_id DECIMAL(10,0) not null,
    project_type_id DECIMAL(12,0) not null,
    catalog_id DECIMAL(12,0) not null,
    status_id DECIMAL(3,0) not null,
    immune_ind DECIMAL(1,0) not null,
    PRIMARY KEY(user_id)
);

CREATE TABLE review_applications(
	id DECIMAL(10,0) NOT NULL,
	reviewer_id DECIMAL(10,0) NOT NULL,
	project_id DECIMAL(10,0) NOT NULL,
	application_date  DATETIME YEAR TO FRACTION(3)    NOT NULL,
	is_primary CHAR(1) NOT NULL,
	PRIMARY KEY(id)
);

CREATE TABLE project_info_type_lu (
  project_info_type_id          DECIMAL(10,0)                   NOT NULL,
  name                          VARCHAR(64)                     NOT NULL,
  description                   VARCHAR(25)                     NOT NULL,
  create_user                   VARCHAR(64)                     NOT NULL,
  create_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  modify_user                   VARCHAR(64)                     NOT NULL,
  modify_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  review_system_version         INTEGER NOT NULL,
  PRIMARY KEY(project_info_type_id)
);

CREATE TABLE project_info (
  project_id                    DECIMAL(10,0)                   NOT NULL,
  project_info_type_id          DECIMAL(10,0)                   NOT NULL,
  value                         LVARCHAR(4096)                  NOT NULL,
  create_user                   VARCHAR(64)                     NOT NULL,
  create_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  modify_user                   VARCHAR(64)                     NOT NULL,
  modify_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  PRIMARY KEY(project_id, project_info_type_id)
);

CREATE TABLE project_audit (
  project_audit_id              DECIMAL(10,0)                   NOT NULL,
  project_id                    DECIMAL(10,0)                   NOT NULL,
  update_reason                 VARCHAR(254)                    NOT NULL,
  create_user                   VARCHAR(64)                     NOT NULL,
  create_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  modify_user                   VARCHAR(64)                     NOT NULL,
  modify_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  PRIMARY KEY(project_audit_id)
);

CREATE TABLE project_info_audit (
	project_id int not null,
	project_info_type_id int not null,
	value varchar(255),
	audit_action_type_id int not null,
	action_date datetime year to fraction not null,
	action_user_id decimal(12,0) not null
);

CREATE TABLE resource (
  resource_id                   DECIMAL(10,0) NOT NULL,
  project_id                    DECIMAL(10,0) NOT NULL,
  PRIMARY KEY(resource_id)
);

CREATE TABLE resource_info_type_lu (
  resource_info_type_id         DECIMAL(10,0)                   NOT NULL,
  name                          VARCHAR(64)                     NOT NULL,
  description                   VARCHAR(254)                    NOT NULL,
  create_user                   VARCHAR(64)                     NOT NULL,
  create_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  modify_user                   VARCHAR(64)                     NOT NULL,
  modify_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  PRIMARY KEY(resource_info_type_id)
);

CREATE TABLE resource_info (
  resource_id                   DECIMAL(10,0)                   NOT NULL,
  resource_info_type_id         DECIMAL(10,0)                   NOT NULL,
  value                         LVARCHAR(4096)                  NOT NULL,
  PRIMARY KEY(resource_id, resource_info_type_id)
);

CREATE TABLE id_sequences (
  name VARCHAR(255) NOT NULL,
  next_block_start DECIMAL(10,0) NOT NULL,
  block_size INTEGER NOT NULL,
  exhausted INTEGER NOT NULL,
  PRIMARY KEY (name)
);

INSERT INTO id_sequences(name, next_block_start, block_size, exhausted) VALUES('project_id_seq', 20, 20, 0);
INSERT INTO id_sequences(name, next_block_start, block_size, exhausted) VALUES('project_audit_id_seq', 20, 20, 0);
INSERT INTO id_sequences(name, next_block_start, block_size, exhausted) VALUES('review_application_id_seq', 20, 20, 0);
