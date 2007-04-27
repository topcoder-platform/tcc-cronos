
create table account_status  (
  account_status_id    INTEGER                         not null,
  description          VARCHAR(255),
  creation_date        DATETIME YEAR TO SECOND         not null,
  creation_user        VARCHAR(64)                     not null,
  modification_date    DATETIME YEAR TO SECOND         not null,
  modification_user    VARCHAR(64)                     not null,
primary key (account_status_id)
      constraint pk_account_status
);


create table action  (
  action_id            INTEGER                         not null,
  class_name           VARCHAR(255)                    not null,
  action_name          VARCHAR(255),
primary key (action_id)
      constraint pk_action
);

CREATE TABLE action_context(
       action_context_id integer NOT NULL PRIMARY KEY,
       action_context_name varchar(255),
       action_context_parent_id integer REFERENCES action_context(action_context_id),
       class_name varchar(255) NOT NULL
);

create table address  (
  address_id           INTEGER                         not null,
  line1                VARCHAR(100)                    not null,
  line2                VARCHAR(100),
  city                 VARCHAR(30)                     not null,
  state_name_id        INTEGER                         not null,
  country_name_id      INTEGER                         not null,
  zip_code             VARCHAR(10)                     not null,
  creation_date        DATETIME YEAR TO SECOND         not null,
  creation_user        VARCHAR(64)                     not null,
  modification_date    DATETIME YEAR TO SECOND         not null,
  modification_user    VARCHAR(64)                     not null,
primary key (address_id)
      constraint pk_address
);

CREATE TABLE address_relation (
       entity_id            INTEGER 				NOT NULL,
       address_type_id      INTEGER 				NOT NULL,
       creation_date        datetime year to second NOT NULL,
       creation_user        varchar(64) 			NOT NULL,
       modification_date    datetime year to second NOT NULL,
       modification_user    varchar(64) 			NOT NULL,
       address_id           INTEGER 				NOT NULL,
primary key (address_id, address_type_id, entity_id)
      constraint pk_address_relation
);

CREATE TABLE address_type (
       address_type_id      INTEGER 				NOT NULL,
       description          varchar(64) 			NOT NULL,
       creation_date        datetime year to second NOT NULL,
       creation_user        varchar(64) 			NOT NULL,
       modification_date    datetime year to second NOT NULL,
       modification_user    varchar(64) 			NOT NULL,
primary key (address_type_id)
      constraint pk_address_type
);

CREATE TABLE application_area (
       app_area_id          INTEGER 				NOT NULL,
       description          VARCHAR(64) 			NOT NULL,
       creation_date        datetime year to second NOT NULL,
       creation_user        VARCHAR(64) 			NOT NULL,
       modification_date    datetime year to second NOT NULL,
       modification_user    VARCHAR(64) 			NOT NULL,
primary key (app_area_id)
      constraint pk_application_area
);

CREATE TABLE audit (
       audit_id             integer 				NOT NULL,
       app_area_id          INTEGER 				NOT NULL,
       client_id            INTEGER 				NOT NULL,
       company_id           INTEGER 				NOT NULL,
       project_id           INTEGER 				NOT NULL,
       account_user_id      INTEGER 				NOT NULL,
       entity_id            INTEGER 				NOT NULL,
       table_name           VARCHAR(64) 			NOT NULL,
       action_type          INTEGER 				NOT NULL,
       creation_date        datetime year to second NOT NULL,
       creation_user        VARCHAR(64) 			NOT NULL,
primary key (audit_id)
      constraint pk_audit
);


CREATE TABLE audit_detail (
       audit_detail_id      integer 		NOT NULL,
       audit_id             integer 		NOT NULL,
       old_value            VARCHAR(255),
       new_value            VARCHAR(255),
       column_name          VARCHAR(64) 	NOT NULL,
primary key (audit_detail_id)
      constraint pk_audit_detail
);

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



create table comp_exp_type  (
  company_id           INTEGER                         not null,
  expense_type_id      INTEGER                         not null,
  creation_date        DATETIME YEAR TO SECOND         not null,
  creation_user        VARCHAR(64)                     not null,
  modification_date    DATETIME YEAR TO SECOND         not null,
  modification_user    VARCHAR(64)                     not null,
primary key (company_id, expense_type_id)
      constraint pk_comp_exp_type
);

CREATE TABLE comp_fb_type (
       company_id           INTEGER 				NOT NULL,
       fix_bill_type_id     INTEGER 				NOT NULL,
       creation_date        datetime year to second NOT NULL,
       creation_user        varchar(64) 			NOT NULL,
       modification_date    datetime year to second NOT NULL,
       modification_user    varchar(64) 			NOT NULL,
primary key (company_id, fix_bill_type_id)
      constraint pk_comp_fb_type
);

create table comp_rate  (
  company_id           INTEGER                         not null,
  rate_id      		   INTEGER                         not null,
  creation_date        DATETIME YEAR TO SECOND         not null,
  creation_user        VARCHAR(64)                     not null,
  modification_date    DATETIME YEAR TO SECOND         not null,
  modification_user    VARCHAR(64)                     not null,
primary key (company_id, rate_id)
      constraint pk_comp_rate
);

create table comp_rej_reason  (
  company_id           INTEGER                         not null,
  reject_reason_id     INTEGER                         not null,
  creation_date        DATETIME YEAR TO SECOND         not null,
  creation_user        VARCHAR(64)                     not null,
  modification_date    DATETIME YEAR TO SECOND         not null,
  modification_user    VARCHAR(64)                     not null,
primary key (company_id, reject_reason_id)
      constraint pk_comp_rej_reason
);


create table comp_reject_email  (
  company_id           INTEGER                         not null,
  reject_email_id      INTEGER                         not null,
  creation_date        DATETIME YEAR TO SECOND         not null,
  creation_user        VARCHAR(64)                     not null,
  modification_date    DATETIME YEAR TO SECOND         not null,
  modification_user    VARCHAR(64)                     not null,
primary key (company_id, reject_email_id)
      constraint pk_comp_reject_reason
);


create table comp_task_type  (
  company_id           INTEGER                         not null,
  task_type_id         INTEGER                         not null,
  creation_date        DATETIME YEAR TO SECOND         not null,
  creation_user        VARCHAR(64)                     not null,
  modification_date    DATETIME YEAR TO SECOND         not null,
  modification_user    VARCHAR(64)                     not null,
primary key (company_id, task_type_id)
      constraint pk_comp_task_type
);


create table company  (
  company_id           INTEGER                         not null,
  name               VARCHAR(64),
  passcode             VARCHAR(64)                     not null,
  creation_date        DATETIME YEAR TO SECOND         not null,
  creation_user        VARCHAR(64)                     not null,
  modification_date    DATETIME YEAR TO SECOND         not null,
  modification_user    VARCHAR(64)                     not null,
primary key (company_id)
      constraint pk_company
);


alter table company
   add constraint unique (passcode)
      constraint unique_company_passcode;


create table contact  (
  contact_id           INTEGER                         not null,
  first_name           VARCHAR(64)                     not null,
  last_name            VARCHAR(64)                     not null,
  phone                VARCHAR(30)                     not null,
  email                VARCHAR(64)                     not null,
  creation_date        DATETIME YEAR TO SECOND         not null,
  creation_user        VARCHAR(64)                     not null,
  modification_date    DATETIME YEAR TO SECOND         not null,
  modification_user    VARCHAR(64)                     not null,
primary key (contact_id)
      constraint pk_contact
);

CREATE TABLE contact_relation (
       entity_id            INTEGER 				NOT NULL,
       creation_date        datetime year to second NOT NULL,
       creation_user        varchar(64) 			NOT NULL,
       modification_date    datetime year to second NOT NULL,
       modification_user    varchar(64) 			NOT NULL,
       contact_id           INTEGER 				NOT NULL,
       contact_type_id      INTEGER 				NOT NULL,
primary key (contact_id, contact_type_id, entity_id)
      constraint pk_contact_relation
);

CREATE TABLE contact_type (
       contact_type_id      INTEGER 				NOT NULL,
       description          varchar(64) 			NOT NULL,
       creation_date        datetime year to second NOT NULL,
       creation_user        varchar(64) 			NOT NULL,
       modification_date    datetime year to second NOT NULL,
       modification_user    varchar(64) 			NOT NULL,
primary key (contact_type_id)
      constraint pk_contact_type
);

create table country_name  (
  country_name_id      INTEGER                         not null,
  name                 VARCHAR(64)                     not null,
  creation_date        DATETIME YEAR TO SECOND         not null,
  creation_user        VARCHAR(64)                     not null,
  modification_date    DATETIME YEAR TO SECOND         not null,
  modification_user    VARCHAR(64)                     not null,
primary key (country_name_id)
      constraint pk_country_name
);

CREATE TABLE cut_off_time (
       cut_off_time_id      integer 					NOT NULL,
       company_id           integer 					NOT NULL,
       cut_off_time         datetime  YEAR TO SECOND	NOT NULL,
       creation_date        datetime year to second 	NOT NULL,
       creation_user        varchar(64) 				NOT NULL,
       modification_date    datetime year to second 	NOT NULL,
       modification_user    varchar(64) 				NOT NULL,
primary key (cut_off_time_id)
      constraint pk_cut_off_time
);

create table exp_reject_reason  (
  expense_entry_id     INTEGER                         not null,
  reject_reason_id     INTEGER                         not null,
  creation_date        DATETIME YEAR TO SECOND         not null,
  creation_user        VARCHAR(64)                     not null,
  modification_date    DATETIME YEAR TO SECOND         not null,
  modification_user    VARCHAR(64)                     not null,
primary key (expense_entry_id, reject_reason_id)
      constraint pk_exp_reject_reason
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

CREATE TABLE fb_reject_reason (
       fix_bill_entry_id    INTEGER 				NOT NULL,
       reject_reason_id     INTEGER 				NOT NULL,
       creation_date        datetime year to second NOT NULL,
       creation_user        varchar(64) 			NOT NULL,
       modification_date    datetime year to second NOT NULL,
       modification_user    varchar(64) 			NOT NULL,
primary key (fix_bill_entry_id,reject_reason_id)
      constraint pk_fb_reject_reason
);

CREATE TABLE fix_bill_entry (
fix_bill_entry_id INTEGER NOT NULL,
company_id INTEGER NOT NULL,
invoice_id INTEGER ,
fix_bill_status_id INTEGER NOT NULL,
description varchar(255) NOT NULL,
entry_date date NOT NULL,
amount decimal(17,2) NOT NULL,
creation_date datetime year to second NOT NULL,
creation_user varchar(64) NOT NULL,
modification_date datetime year to second NOT NULL,
modification_user varchar(64) NOT NULL,
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


create table id_sequences  (
  name               VARCHAR(255)                      not null,
  next_block_start     INTEGER                         not null,
  block_size           INTEGER                         not null,
  exhausted            INTEGER                         not null,
primary key (name)
      constraint pk_id_sequences
);

CREATE TABLE invoice (
       invoice_id           integer 				NOT NULL,
       project_id           integer 				NOT NULL,
       creation_date        datetime year to second NOT NULL,
       creation_user        varchar(64) 			NOT NULL,
       modification_date    datetime year to second NOT NULL,
       modification_user    varchar(64) 			NOT NULL,
       salesTax             decimal(7,3) 			NOT NULL,
       payment_terms_id     integer 				NOT NULL,
       invoice_number       varchar(64)					,
       po_number            varchar(64)					,
       invoice_date         date 					NOT NULL,
       due_date             date 					NOT NULL,
       paid                 smallint	 			NOT NULL,
       company_id           integer 				NOT NULL,
       invoice_status_id    integer 				NOT NULL,
primary key (invoice_id)
      constraint pk_invoice
);

CREATE TABLE invoice_status (
       invoice_status_id    integer 				NOT NULL,
       description          varchar(64) 			NOT NULL,
       creation_date        datetime year to second NOT NULL,
       creation_user        varchar(64) 			NOT NULL,
       modification_date    datetime year to second NOT NULL,
       modification_user    varchar(64) 			NOT NULL,
primary key (invoice_status_id)
      constraint pk_invoice_status
);

CREATE TABLE notification (
       notification_id      integer 				NOT NULL,
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

CREATE TABLE payment_terms (
       payment_terms_id     integer 				NOT NULL,
       description          varchar(64) 			NOT NULL,
       creation_date        datetime year to second NOT NULL,
       creation_user        varchar(64) 			NOT NULL,
       modification_date    datetime year to second NOT NULL,
       modification_user    varchar(64) 			NOT NULL,
       active               smallint 				NOT NULL,
       term                 integer 				NOT NULL,
primary key (payment_terms_id)
      constraint pk_payment_terms
);


create table rate  (
  rate_id              INTEGER                         not null,
  description          VARCHAR(255),
  rate                 MONEY (16,3)                    not null,
  creation_date        DATETIME YEAR TO SECOND         not null,
  creation_user        VARCHAR(64)                     not null,
  modification_date    DATETIME YEAR TO SECOND         not null,
  modification_user    VARCHAR(64)                     not null,
primary key (rate_id)
      constraint pk_rate
);

create table principal  (
  principal_id         INTEGER                         not null,
  principal_name       VARCHAR(255),
primary key (principal_id)
      constraint pk_principal
);

    create table   principal_action_context_permission (
         principal_id  int not null,
        action_id  int not null,
        action_context_id  int not null,
        permission  int not null,
       PRIMARY KEY (principal_id, action_id, action_context_id)
	       constraint pk_pacp,
       FOREIGN KEY (principal_id) REFERENCES principal(principal_id),
       FOREIGN KEY (action_id) REFERENCES action(action_id),
       FOREIGN KEY (action_context_id) REFERENCES action_context(action_context_id)
    );

create table principal_role  (
  principal_id         INTEGER                         not null,
  role_id              INTEGER                         not null,
primary key (principal_id, role_id)
      constraint pk_principal_role
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

CREATE TABLE project_fix_bill (
       fix_bill_entry_id    integer 				NOT NULL,
       project_id           integer 				NOT NULL,
       creation_date        datetime year to second NOT NULL,
       create_user          varchar(64) 			NOT NULL,
       modification_date    datetime year to second NOT NULL,
       modification_user    varchar(64) 			NOT NULL,
PRIMARY KEY (fix_bill_entry_id, project_id)
       constraint pk_project_fix_bill
);

create table project_manager  (
  project_id           INTEGER                         not null,
  user_account_id      INTEGER                         not null,
  creation_date        DATETIME YEAR TO SECOND         not null,
  creation_user        VARCHAR(64)                     not null,
  modification_date    DATETIME YEAR TO SECOND         not null,
  modification_user    VARCHAR(64)                     not null,
primary key (project_id, user_account_id)
      constraint pk_project_manager
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


create table reject_email  (
  reject_email_id      INTEGER                         not null,
  body                 LVARCHAR                        not null,
  creation_date        DATETIME YEAR TO SECOND         not null,
  creation_user        VARCHAR(64)                     not null,
  modification_date    DATETIME YEAR TO SECOND         not null,
  modification_user    VARCHAR(64)                     not null,
primary key (reject_email_id)
      constraint pk_reject_email
);


create table reject_reason  (
  reject_reason_id     INTEGER                         not null,
  description          VARCHAR(255),
  active               SMALLINT                        not null,
  creation_date        DATETIME YEAR TO SECOND         not null,
  creation_user        VARCHAR(64)                     not null,
  modification_date    DATETIME YEAR TO SECOND         not null,
  modification_user    VARCHAR(64)                     not null,
primary key (reject_reason_id)
      constraint pk_reject_reason
);


create table role  (
  role_id              INTEGER                         not null,
  role_name            VARCHAR(255),
primary key (role_id)
      constraint pk_role
);

CREATE TABLE role_action_context_permission(
       role_id integer NOT NULL,
       action_id integer NOT NULL,
       action_context_id integer NOT NULL,
       permission integer NOT NULL,
       PRIMARY KEY (role_id, action_id, action_context_id),
       FOREIGN KEY (role_id) REFERENCES role(role_id),
       FOREIGN KEY (action_id) REFERENCES action(action_id),
       FOREIGN KEY (action_context_id) REFERENCES action_context(action_context_id)
);

CREATE TABLE service_details (
       service_detail_id    integer 				NOT NULL,
       time_entry_id        integer					NOT NULL,
       invoice_id           integer 				NOT NULL,
       rate                 integer 				NOT NULL,
       amount               decimal(16,2) 			NOT NULL,
       creation_date        datetime year to second NOT NULL,
       creation_user        varchar(64) 			NOT NULL,
       modification_date    datetime year to second NOT NULL,
       modification_user    varchar(64)			 	NOT NULL,
primary key (service_detail_id)
      constraint pk_service_details
);

create table state_name  (
  state_name_id        INTEGER                         not null,
  name                 VARCHAR(64)                     not null,
  abbreviation         VARCHAR(2)                      not null,
  creation_date        DATETIME YEAR TO SECOND         not null,
  creation_user        VARCHAR(64)                     not null,
  modification_date    DATETIME YEAR TO SECOND         not null,
  modification_user    VARCHAR(64)                     not null,
primary key (state_name_id)
      constraint pk_state_name
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


create table time_reject_reason  (
  time_entry_id        INTEGER                         not null,
  reject_reason_id     INTEGER                         not null,
  creation_date        DATETIME YEAR TO SECOND         not null,
  creation_user        VARCHAR(64)                     not null,
  modification_date    DATETIME YEAR TO SECOND         not null,
  modification_user    VARCHAR(64)                     not null,
primary key (time_entry_id, reject_reason_id)
      constraint pk_time_reject_reason
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

-- Schedular Component DDL -- START

CREATE TABLE JOB (
	JobId INTEGER,
	Name VARCHAR(40),
	StartDate DATE,
	StartTime INTEGER,
	EndDate DATE,
	DateUnit VARCHAR(60),
	DateUnitDays VARCHAR(20),
	DateUnitWeek VARCHAR(2),
	DateUnitMonth VARCHAR(2),
	Interval INTEGER,
	Recurrence INTEGER,
	Active CHAR(1),
	JobType VARCHAR(20),
	JobCommand VARCHAR(40),
	DependenceJobName VARCHAR(60),
	DependenceJobStatus VARCHAR(20),
	DependenceJobDelay VARCHAR(20),
	primary key (JobId)
      constraint pk_job
);

CREATE TABLE Message (
	MessageId INTEGER,
	OwnerId INTEGER,
	Name VARCHAR(40),
	FromAddress VARCHAR(40),
	Subject VARCHAR(40),
	TemplateFileName VARCHAR(40),
	Recipients Text,
	primary key (MessageId)
      constraint pk_Message);

CREATE TABLE Group (
	GroupId INTEGER,
	Name VARCHAR(40),
	primary key (GroupId)
      constraint pk_Group
);

CREATE TABLE GroupJob (
	GroupId INTEGER,
	JobId INTEGER,
	primary key (GroupId,JobId)
      constraint pk_GroupJob
);
-- Schedular Component DDL -- STOP

alter table address
   add constraint foreign key (state_name_id)
      references state_name (state_name_id) 
      constraint r109_279;

alter table address
   add constraint foreign key (country_name_id)
      references country_name (country_name_id) 
      constraint fk_address_country_name;

alter table client
   add constraint foreign key (company_id)
      references company (company_id) 
      constraint r128_310;

alter table client_project
   add constraint foreign key (project_id)
      references project (project_id) 
      constraint r129_311;

alter table client_project
   add constraint foreign key (client_id)
      references client (client_id) 
      constraint r129_312;

alter table comp_exp_type
   add constraint foreign key (expense_type_id)
      references expense_type (expense_type_id) 
      constraint r121_296;

alter table comp_exp_type
   add constraint foreign key (company_id)
      references company (company_id) 
      constraint r121_297;

alter table comp_rej_reason
   add constraint foreign key (reject_reason_id)
      references reject_reason (reject_reason_id) 
      constraint r117_290;

alter table comp_rej_reason
   add constraint foreign key (company_id)
      references company (company_id) 
      constraint r117_291;

alter table comp_reject_email
   add constraint foreign key (reject_email_id)
      references reject_email (reject_email_id) 
      constraint r119_292;

alter table comp_reject_email
   add constraint foreign key (company_id)
      references company (company_id) 
      constraint r119_293;

alter table comp_task_type
   add constraint foreign key (task_type_id)
      references task_type (task_type_id) 
      constraint r124_302;

alter table comp_task_type
   add constraint foreign key (company_id)
      references company (company_id) 
      constraint r124_303;

alter table exp_reject_reason
   add constraint foreign key (reject_reason_id)
      references reject_reason (reject_reason_id) 
      constraint r120_294;

alter table exp_reject_reason
   add constraint foreign key (expense_entry_id)
      references expense_entry (expense_entry_id) 
      constraint r120_295;

alter table expense_entry
   add constraint foreign key (company_id)
      references company (company_id) 
      constraint r134_314;

alter table expense_entry
   add constraint foreign key (expense_status_id)
      references expense_status (expense_status_id) 
      constraint r134_315;

alter table expense_entry
   add constraint foreign key (expense_type_id)
      references expense_type (expense_type_id) 
      constraint r134_316;

alter table principal_role
   add constraint foreign key (principal_id)
      references principal (principal_id) 
      constraint r103_270;

alter table principal_role
   add constraint foreign key (role_id)
      references role (role_id) 
      constraint r103_271;

alter table project
   add constraint foreign key (company_id)
      references company (company_id) 
      constraint r131_313;

alter table project_expense
   add constraint foreign key (expense_entry_id)
      references expense_entry (expense_entry_id) 
      constraint r122_298;

alter table project_expense
   add constraint foreign key (project_id)
      references project (project_id) 
      constraint r122_299;

alter table project_manager
   add constraint foreign key (user_account_id)
      references user_account (user_account_id) 
      constraint r126_306;

alter table project_manager
   add constraint foreign key (project_id)
      references project (project_id) 
      constraint r126_307;

alter table project_time
   add constraint foreign key (time_entry_id)
      references time_entry (time_entry_id) 
      constraint r125_304;

alter table project_time
   add constraint foreign key (project_id)
      references project (project_id) 
      constraint r125_305;

alter table project_worker
   add constraint foreign key (project_id)
      references project (project_id) 
      constraint r127_308;

alter table project_worker
   add constraint foreign key (user_account_id)
      references user_account (user_account_id) 
      constraint r127_309;

alter table time_entry
   add constraint foreign key (company_id)
      references company (company_id) 
      constraint r137_317;

alter table time_entry
   add constraint foreign key (time_status_id)
      references time_status (time_status_id) 
      constraint r137_318;

alter table time_entry
   add constraint foreign key (task_type_id)
      references task_type (task_type_id) 
      constraint r137_319;

alter table time_reject_reason
   add constraint foreign key (reject_reason_id)
      references reject_reason (reject_reason_id) 
      constraint r123_300;

alter table time_reject_reason
   add constraint foreign key (time_entry_id)
      references time_entry (time_entry_id) 
      constraint r123_301;

alter table user_account
   add constraint foreign key (account_status_id)
      references account_status (account_status_id) 
      constraint r112_282;

alter table user_account
   add constraint foreign key (company_id)
      references company (company_id) 
      constraint r112_283;

alter table user_account
   add constraint foreign key (user_account_id)
      references user_account (user_account_id) 
      constraint r115_287;  

ALTER TABLE comp_fb_type
       ADD CONSTRAINT FOREIGN KEY (fix_bill_type_id)
       REFERENCES fix_bill_type
       constraint fk_comp_fb_type_fix_bill_type;

ALTER TABLE comp_fb_type
       ADD CONSTRAINT FOREIGN KEY (company_id)
       REFERENCES company
       constraint fk_comp_fb_type_company;

ALTER TABLE fb_reject_reason
       ADD CONSTRAINT FOREIGN KEY (reject_reason_id)
       REFERENCES reject_reason
       constraint fk_fb_reject_reason_reject_reason;

ALTER TABLE fb_reject_reason
       ADD CONSTRAINT FOREIGN KEY (fix_bill_entry_id)
       REFERENCES fix_bill_entry
       constraint fk_fb_reject_reason_fix_bill_entry;

ALTER TABLE fix_bill_entry
       ADD CONSTRAINT FOREIGN KEY (invoice_id)
       REFERENCES invoice
       constraint fk_fix_bill_entry_invoice;

ALTER TABLE fix_bill_entry
       ADD CONSTRAINT FOREIGN KEY (company_id)
       REFERENCES company
       constraint fk_fix_bill_entry_company;

ALTER TABLE fix_bill_entry
       ADD CONSTRAINT FOREIGN KEY (fix_bill_status_id)
       REFERENCES fix_bill_status
       constraint fk_fix_bill_entry_fix_bill_status;

ALTER TABLE fix_bill_entry
       ADD CONSTRAINT FOREIGN KEY (fix_bill_type_id)
       REFERENCES fix_bill_type
       constraint fk_fix_bill_entry_fix_bill_type;

ALTER TABLE expense_entry
       ADD CONSTRAINT FOREIGN KEY (invoice_id)
       REFERENCES invoice
       constraint fk_expense_entry_invoice;

ALTER TABLE invoice
       ADD CONSTRAINT FOREIGN KEY (invoice_status_id)
       REFERENCES invoice_status
       constraint fk_invoice_invoice_status;

ALTER TABLE invoice
       ADD CONSTRAINT FOREIGN KEY (company_id)
       REFERENCES company
       constraint fk_invoice_company;

ALTER TABLE invoice
       ADD CONSTRAINT FOREIGN KEY (payment_terms_id)
       REFERENCES payment_terms
       constraint fk_invoice_payment_terms;

ALTER TABLE invoice
       ADD CONSTRAINT FOREIGN KEY (project_id)
       REFERENCES project
       constraint fk_invoice_project;



ALTER TABLE service_details
       ADD CONSTRAINT FOREIGN KEY (invoice_id)
       REFERENCES Invoice
       constraint fk_service_details_invoice;

ALTER TABLE service_details
       ADD CONSTRAINT FOREIGN KEY (time_entry_id)
       REFERENCES time_entry
       constraint fk_service_details_time_entry;
	   
ALTER TABLE time_entry
       ADD CONSTRAINT FOREIGN KEY (invoice_id)
       REFERENCES invoice
       constraint fk_time_entry_invoice;

ALTER TABLE notification
       ADD CONSTRAINT FOREIGN KEY (JobId)
       REFERENCES Job
       constraint fk_notification_Job;

ALTER TABLE notify_clients
       ADD CONSTRAINT FOREIGN KEY (client_id)
       REFERENCES client
       constraint fk_notify_clients_client;

ALTER TABLE notify_clients
       ADD CONSTRAINT FOREIGN KEY (notification_id)
       REFERENCES notification
       constraint fk_notify_clients_notification;

ALTER TABLE notify_projects
       ADD CONSTRAINT FOREIGN KEY (project_id)
       REFERENCES project
       constraint fk_notify_projects_project;

ALTER TABLE notify_projects
       ADD CONSTRAINT FOREIGN KEY (notification_id)
       REFERENCES notification
       constraint fk_notify_projects_notification;

ALTER TABLE notify_resources
       ADD CONSTRAINT FOREIGN KEY (user_account_id)
       REFERENCES user_account
       constraint fk_notify_resources_user_account;

ALTER TABLE notify_resources
       ADD CONSTRAINT FOREIGN KEY (notification_id)
       REFERENCES notification
       constraint fk_notify_resources_notification;
	   
ALTER TABLE comp_rate
		ADD CONSTRAINT FOREIGN KEY (company_id)
		REFERENCES company
		CONSTRAINT fk_comp_rate_company;

ALTER TABLE comp_rate
		ADD CONSTRAINT FOREIGN KEY (rate_id)
		REFERENCES rate
		CONSTRAINT fk_comp_rate_rate;
		
ALTER TABLE audit
       ADD CONSTRAINT FOREIGN KEY (account_user_id)
       REFERENCES user_account
		CONSTRAINT fk_audit;

ALTER TABLE audit
       ADD CONSTRAINT FOREIGN KEY (project_id)
       REFERENCES project
		CONSTRAINT fk_audit_project;

ALTER TABLE audit
       ADD CONSTRAINT FOREIGN KEY (company_id)
       REFERENCES company
		CONSTRAINT fk_audit_company;

ALTER TABLE audit
       ADD CONSTRAINT FOREIGN KEY (client_id)
       REFERENCES client
		CONSTRAINT fk_audit_client;

ALTER TABLE audit
       ADD CONSTRAINT FOREIGN KEY (app_area_id)
       REFERENCES application_area
		CONSTRAINT fk_audit_application_area;

ALTER TABLE audit_detail
       ADD CONSTRAINT FOREIGN KEY (audit_id)
       REFERENCES audit
		CONSTRAINT fk_audit_detail_audit;

ALTER TABLE cut_off_time
       ADD CONSTRAINT FOREIGN KEY (company_id)
       REFERENCES company
		CONSTRAINT fk_cut_off_time_company;


ALTER TABLE address_relation
       ADD CONSTRAINT FOREIGN KEY (address_id)
       REFERENCES address
	   CONSTRAINT fk_address_relation_address;

ALTER TABLE address_relation
       ADD CONSTRAINT FOREIGN KEY (address_type_id)
       REFERENCES address_type
	   CONSTRAINT fk_address_relation_address_type;

ALTER TABLE contact_relation
       ADD CONSTRAINT FOREIGN KEY (contact_type_id)
       REFERENCES contact_type
	   CONSTRAINT fk_contact_relation_contact_type;

ALTER TABLE contact_relation
       ADD CONSTRAINT FOREIGN KEY (contact_id)
       REFERENCES contact
	   CONSTRAINT fk_contact_relation_contact;

		