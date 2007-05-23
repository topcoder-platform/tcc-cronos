CREATE TABLE notification (
  notification_id               INTEGER                         NOT NULL,
  company_id                    INTEGER                         NOT NULL,
  from_address                  VARCHAR(255)                    NOT NULL,
  subject                       VARCHAR(255)                    NOT NULL,
  message                       VARCHAR(255)                    NOT NULL,
  last_time_sent                DATE,
  next_time_send                DATE,
  status                        SMALLINT                        NOT NULL,
  job_name                      VARCHAR(40)                     NOT NULL,
  scheduleId                    INTEGER                         NOT NULL,
  creation_date                 DATETIME YEAR TO SECOND         NOT NULL,
  creation_user                 VARCHAR(64)                     NOT NULL,
  modification_date             DATETIME YEAR TO SECOND         NOT NULL,
  modification_user             VARCHAR(64)                     NOT NULL,
  PRIMARY KEY (notification_id) CONSTRAINT pk_notification
);

CREATE TABLE notify_clients (
       creation_user        varchar(64) 			NOT NULL,
       creation_date        datetime year to second NOT NULL,
       modification_user    varchar(64) 			NOT NULL,
       modification_date    datetime year to second NOT NULL,
       notification_id      integer 				NOT NULL,
       client_id            integer 				NOT NULL,
primary key (notification_id, client_id)
      constraint pk_notify_clients
);

CREATE TABLE notify_projects (
       creation_user        varchar(64) 			NOT NULL,
       creation_date        datetime year to second NOT NULL,
       modification_user    varchar(64) 			NOT NULL,
       modification_date    datetime year to second NOT NULL,
       notification_id      integer 				NOT NULL,
       project_id           integer 				NOT NULL,
primary key (notification_id, project_id)
      constraint pk_notify_projects
);

CREATE TABLE notify_resources (
       notification_id      integer 				NOT NULL,
       user_account_id      integer 				NOT NULL,
       creation_user        varchar(64) 			NOT NULL,
       creation_date        datetime year to second NOT NULL,
       modification_user    varchar(64) 			NOT NULL,
       modification_date    datetime year to second NOT NULL,
primary key (notification_id, user_account_id)
      constraint pk_notify_resources
);
CREATE TABLE id_sequences (
    name                VARCHAR(255) NOT NULL  PRIMARY KEY,
    next_block_start    INT NOT NULL,
    block_size          INT NOT NULL,
    exhausted		int NOT NULL
);

INSERT INTO id_sequences (name, next_block_start, block_size, exhausted)
VALUES ('unit_test_id_sequence', 1, 10, 0);
