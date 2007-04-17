create table account_status  (
  account_status_id    INT8                            not null,
  description          VARCHAR(255),
  creation_date        DATETIME YEAR TO SECOND         not null,
  creation_user        VARCHAR(64)                     not null,
  modification_date    DATETIME YEAR TO SECOND         not null,
  modification_user    VARCHAR(64)                     not null,
primary key (account_status_id)
      constraint pk_account_status
);

create table company  (
  company_id           INT8                           not null,
  name               VARCHAR(64),
  passcode             VARCHAR(64)                     not null,
  creation_date        DATETIME YEAR TO SECOND         not null,
  creation_user        VARCHAR(64)                     not null,
  modification_date    DATETIME YEAR TO SECOND         not null,
  modification_user    VARCHAR(64)                     not null,
primary key (company_id)
      constraint pk_company
);

create table user_account  (
  user_account_id      INT8                         not null,
  company_id           INT8,
  account_status_id    INT8                         not null,
  user_name            VARCHAR(64)                     not null,
  password             VARCHAR(64)                     not null,
  creation_date        DATETIME YEAR TO SECOND         not null,
  creation_user        VARCHAR(64)                     not null,
  modification_date    DATETIME YEAR TO SECOND         not null,
  modification_user    VARCHAR(64)                     not null,
primary key (user_account_id)
      constraint pk_user_account
);
alter table user_account add constraint foreign key 
	(company_id)
	references company
	(company_id) 
	constraint fk_user_account_company;
alter table user_account add constraint foreign key 
	(account_status_id)
	references account_status
	(account_status_id) 
	constraint fk_user_account_account_status;
	

create table client  (
  client_id            INT8                         not null,
  name                 VARCHAR(64)                     not null,
  company_id           INT8                         not null,
  creation_date        DATETIME YEAR TO SECOND         not null,
  creation_user        VARCHAR(64)                     not null,
  modification_date    DATETIME YEAR TO SECOND         not null,
  modification_user    VARCHAR(64)                     not null,
primary key (client_id)
      constraint pk_client
);		
alter table client add constraint foreign key 
	(company_id)
	references company
	(company_id) 
	constraint fk_client_company;
	
create table project (
    project_id INT8 not null,
    company_id INT8 not null,
    name VARCHAR(64) not null,
    description VARCHAR(255) not null,
    start_date DATETIME YEAR TO FRACTION not null,
    end_date DATETIME YEAR TO FRACTION not null,
    creation_date DATETIME YEAR TO FRACTION not null,
    creation_user VARCHAR(64) not null,
    modification_date DATETIME YEAR TO FRACTION not null,
    modification_user VARCHAR(64) not null,
primary key (project_id)
      constraint pk_project
);
alter table project add constraint foreign key 
	(company_id)
	references company
	(company_id) 
	constraint fk_project_company;

CREATE TABLE application_area (
       app_area_id          INT8      				NOT NULL,
       description          VARCHAR(64) 			NOT NULL,
       creation_date        datetime year to second NOT NULL,
       creation_user        VARCHAR(64) 			NOT NULL,
       modification_date    datetime year to second NOT NULL,
       modification_user    VARCHAR(64) 			NOT NULL,
primary key (app_area_id)
      constraint pk_application_area
);

CREATE TABLE audit (
       audit_id             INT8 				NOT NULL,
       app_area_id          INT8 				NOT NULL,
       client_id            INT8 				NOT NULL,
       company_id           INT8 				NOT NULL,
       project_id           INT8 				NOT NULL,
       account_user_id      INT8 				NOT NULL,
       entity_id            INT8 				NOT NULL,
       table_name           VARCHAR(64) 			NOT NULL,
       action_type          INTEGER 				NOT NULL,
       creation_date        datetime year to second NOT NULL,
       creation_user        VARCHAR(64) 			NOT NULL,
primary key (audit_id)
      constraint pk_audit
);
alter table audit add constraint foreign key 
	(app_area_id)
	references application_area
	(app_area_id) 
	constraint pk_audit_application_area;
alter table audit add constraint foreign key 
	(client_id)
	references client
	(client_id) 
	constraint pk_audit_client;
alter table audit add constraint foreign key 
	(company_id)
	references company
	(company_id) 
	constraint pk_audit_company;
alter table audit add constraint foreign key 
	(project_id)
	references project
	(project_id) 
	constraint pk_audit_project;
alter table audit add constraint foreign key 
	(account_user_id)
	references user_account
	(user_account_id) 
	constraint pk_audit_user_account;

CREATE TABLE audit_detail (
       audit_detail_id      INT8 		NOT NULL,
       audit_id             INT8 		NOT NULL,
       old_value            VARCHAR(255),
       new_value            VARCHAR(255),
       column_name          VARCHAR(64) 	NOT NULL,
primary key (audit_detail_id)
      constraint pk_audit_detail
);
alter table audit_detail add constraint foreign key 
	(audit_id)
	references audit
	(audit_id) 
	constraint fk_audit_detail_audit;