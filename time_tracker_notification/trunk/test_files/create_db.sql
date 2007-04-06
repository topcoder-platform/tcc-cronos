CREATE TABLE notification (
       notification_id      integer 				NOT NULL,
       company_id           integer                 NOT NULL,
       from_address         varchar(255) 			NOT NULL,
       subject              varchar(255) 			NOT NULL,
       message              varchar(255)			NOT NULL,
       last_time_sent       date  				        ,
       next_time_send       date  				        ,
       status               smallint 				NOT NULL,
       scheduleId           integer 				NOT NULL,
       creation_user        varchar(64) 			NOT NULL,
       creation_date        datetime year to second NOT NULL,
       modification_user    varchar(64) 			NOT NULL,
       modification_date    datetime year to second NOT NULL,
	   JobId                integer  				    ,
primary key (notification_id)
      constraint pk_notification
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
    next_block_start    BIGINT NOT NULL,
    block_size          INT NOT NULL,
    exhausted		int NOT NULL default 0
);

INSERT INTO id_sequences (name, next_block_start, block_size, exhausted) 
VALUES ('unit_test_id_sequence', 1, 10, 0);
