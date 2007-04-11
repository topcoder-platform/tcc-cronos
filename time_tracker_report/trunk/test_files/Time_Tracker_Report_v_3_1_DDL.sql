create table client  (
  client_id            INTEGER                         not null,
  name                 VARCHAR(64)                     not null,
  company_id           INTEGER                         not null,
  creation_date        DATETIME YEAR TO SECOND         not null,
  creation_user        VARCHAR(64)                     not null,
  modification_date    DATETIME YEAR TO SECOND         not null,
  modification_user    VARCHAR(64)                     not null,
primary key (client_id)
      constraint pk_client
);
alter table client
   add constraint unique (name)
      constraint unique_client_name;
	  
create table client_project  (
  client_id            INTEGER                         not null,
  project_id           INTEGER                         not null,
  creation_date        DATETIME YEAR TO SECOND         not null,
  creation_user        VARCHAR(64)                     not null,
  modification_date    DATETIME YEAR TO SECOND         not null,
  modification_user    VARCHAR(64)                     not null,
primary key (client_id, project_id)
      constraint pk_client_project
);
create table project  (
  project_id           INTEGER                         not null,
  name                 VARCHAR(64)                     not null,
  company_id           INTEGER                         not null,
  description          VARCHAR(255),
  start_date           DATETIME YEAR TO SECOND         not null,
  end_date             DATETIME YEAR TO SECOND         not null,
  creation_date        DATETIME YEAR TO SECOND         not null,
  creation_user        VARCHAR(64)                     not null,
  modification_date    DATETIME YEAR TO SECOND         not null,
  modification_user    VARCHAR(64)                     not null,
primary key (project_id)
      constraint pk_project
);

create table user_account  (
  user_account_id      INTEGER                         not null,
  company_id           INTEGER,
  account_status_id    INTEGER                         not null,
  user_name            VARCHAR(64)                     not null,
  password             VARCHAR(64)                     not null,
  creation_date        DATETIME YEAR TO SECOND         not null,
  creation_user        VARCHAR(64)                     not null,
  modification_date    DATETIME YEAR TO SECOND         not null,
  modification_user    VARCHAR(64)                     not null,
primary key (user_account_id)
      constraint pk_user_account
);



CREATE TABLE project_fix_bill (
       fix_bill_entry_id    integer 				NOT NULL,
       project_id           integer 				NOT NULL,
       creation_date        datetime year to second NOT NULL,
       creation_user        varchar(64) 			NOT NULL,
       modification_date    datetime year to second NOT NULL,
       modification_user    varchar(64) 			NOT NULL,
PRIMARY KEY (fix_bill_entry_id, project_id)
       constraint pk_project_fix_bill
);
CREATE TABLE fix_bill_entry (
       fix_bill_entry_id    INTEGER 				NOT NULL,
       company_id           INTEGER 				NOT NULL,
       invoice_id           INTEGER 				NOT NULL,
       fix_bill_type_id     INTEGER 				NOT NULL,
       fix_bill_status_id   INTEGER 				NOT NULL,
       description          varchar(255) 			NOT NULL,
       entry_date           datetime year to second NOT NULL,
       amount               decimal(17,2) 			NOT NULL,
       creation_date        datetime year to second NOT NULL,
       creation_user        varchar(64) 			NOT NULL,
       modification_date    datetime year to second NOT NULL,
       modification_user    varchar(64) 			NOT NULL,
primary key (fix_bill_entry_id)
      constraint pk_fix_bill_entry
);
CREATE TABLE fix_bill_status (
       fix_bill_status_id   INTEGER 				NOT NULL,
       description          varchar(64) 			NOT NULL,
       creation_date        datetime year to second NOT NULL,
       creation_user        varchar(64) 			NOT NULL,
       modification_date    datetime year to second NOT NULL,
       modification_user    varchar(64) 			NOT NULL,
primary key (fix_bill_status_id)
      constraint pk_fix_bill_status
);
CREATE TABLE fix_bill_type (
       fix_bill_type_id     INTEGER 				NOT NULL,
       description          varchar(64) 			NOT NULL,
       active               SMALLINT 				NOT NULL,
       creation_date        datetime year to second NOT NULL,
       creation_user        varchar(64) 			NOT NULL,
       modification_date    datetime year to second NOT NULL,
       modification_user    varchar(64) 			NOT NULL,
primary key (fix_bill_type_id)
      constraint pk_fix_bill_type
);





create table project_expense  (
  project_id           INTEGER                         not null,
  expense_entry_id     INTEGER                         not null,
  creation_date        DATETIME YEAR TO SECOND         not null,
  creation_user        VARCHAR(64)                     not null,
  modification_date    DATETIME YEAR TO SECOND         not null,
  modification_user    VARCHAR(64)                     not null,
primary key (project_id, expense_entry_id)
      constraint pk_project_expense
);
create table expense_entry  (
  expense_entry_id     INTEGER                         not null,
  company_id           INTEGER                         not null,
  invoice_id           INTEGER                         not null,
  expense_type_id      INTEGER                         not null,
  expense_status_id    INTEGER                         not null,
  description          VARCHAR(255),
  entry_date           DATETIME YEAR TO SECOND         not null,
  amount               MONEY                           not null,
  billable             SMALLINT                        not null,
  creation_date        DATETIME YEAR TO SECOND         not null,
  creation_user        VARCHAR(64)                     not null,
  modification_date    DATETIME YEAR TO SECOND         not null,
  modification_user    VARCHAR(64)                     not null,
  mileage              INTEGER,
primary key (expense_entry_id)
      constraint pk_expense_entry
);
create table expense_status  (
  expense_status_id    INTEGER                         not null,
  description          VARCHAR(255),
  creation_date        DATETIME YEAR TO SECOND         not null,
  creation_user        VARCHAR(64)                     not null,
  modification_date    DATETIME YEAR TO SECOND         not null,
  modification_user    VARCHAR(64)                     not null,
primary key (expense_status_id)
      constraint pk_expense_status
);
create table expense_type  (
  expense_type_id      INTEGER                         not null,
  description          VARCHAR(255),
  active               SMALLINT                        not null,
  creation_date        DATETIME YEAR TO SECOND         not null,
  creation_user        VARCHAR(64)                     not null,
  modification_date    DATETIME YEAR TO SECOND         not null,
  modification_user    VARCHAR(64)                     not null,
primary key (expense_type_id)
      constraint pk_expense_type
);





create table project_time  (
  project_id           INTEGER                         not null,
  time_entry_id        INTEGER                         not null,
  creation_date        DATETIME YEAR TO SECOND         not null,
  creation_user        VARCHAR(64)                     not null,
  modification_date    DATETIME YEAR TO SECOND         not null,
  modification_user    VARCHAR(64)                     not null,
primary key (project_id, time_entry_id)
      constraint pk_project_time
);
create table project_worker  (
  project_id           INTEGER                         not null,
  user_account_id      INTEGER                         not null,
  start_date           DATETIME YEAR TO SECOND         not null,
  end_date             DATETIME YEAR TO SECOND         not null,
  pay_rate             DECIMAL(5,2)                    not null,
  creation_date        DATETIME YEAR TO SECOND         not null,
  creation_user        VARCHAR(64)                     not null,
  modification_date    DATETIME YEAR TO SECOND         not null,
  modification_user    VARCHAR(64)                     not null,
primary key (project_id, user_account_id)
      constraint pk_project_worker
);
create table time_entry  (
  time_entry_id        INTEGER                         not null,
  company_id           INTEGER                         not null,
  invoice_id           integer 				NOT NULL,
  time_status_id       INTEGER                         not null,
  task_type_id         INTEGER                         not null,
  description          VARCHAR(255),
  entry_date           DATETIME YEAR TO SECOND         not null,
  hours                FLOAT                           not null,
  billable             SMALLINT                        not null,
  creation_date        DATETIME YEAR TO SECOND         not null,
  creation_user        VARCHAR(64)                     not null,
  modification_date    DATETIME YEAR TO SECOND         not null,
  modification_user    VARCHAR(64)                     not null,
primary key (time_entry_id)
      constraint pk_time_entry
);
create table time_status  (
  time_status_id       INTEGER                         not null,
  description          VARCHAR(255),
  creation_date        DATETIME YEAR TO SECOND         not null,
  creation_user        VARCHAR(64)                     not null,
  modification_date    DATETIME YEAR TO SECOND         not null,
  modification_user    VARCHAR(64)                     not null,
primary key (time_status_id)
      constraint pk_time_status
);
create table task_type  (
  task_type_id         INTEGER                         not null,
  description          VARCHAR(255),
  active               SMALLINT                        not null,
  creation_date        DATETIME YEAR TO SECOND         not null,
  creation_user        VARCHAR(64)                     not null,
  modification_date    DATETIME YEAR TO SECOND         not null,
  modification_user    VARCHAR(64)                     not null,
primary key (task_type_id)
      constraint pk_task_type
);