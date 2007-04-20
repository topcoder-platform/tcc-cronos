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

create table project  (
  project_id           INTEGER                         not null,
  name                 VARCHAR(64)                     not null,
  company_id           INTEGER                         not null,
  active               SMALLINT                        not null,
  sales_tax             decimal(7,3) 				   NOT NULL,
  
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

create table id_sequences  (
  name               VARCHAR(255)                      not null,
  next_block_start     INTEGER                         not null,
  block_size           INTEGER                         not null,
  exhausted            INTEGER                         not null,
primary key (name)
      constraint pk_id_sequences
);