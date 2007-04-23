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
    payment_terms_id         integer                      NOT NULL,
    description              varchar(64)                  NOT NULL,
    creation_date            datetime year to second      NOT NULL,
    creation_user            varchar(64)                  NOT NULL,
    modification_date        datetime year to second      NOT NULL,
    modification_user        varchar(64)                  NOT NULL,
    active                   smallint                     NOT NULL,
    term                     integer                      NOT NULL,
  primary key (payment_terms_id)
    constraint pk_payment_terms
);

CREATE TABLE invoice_status (
    invoice_status_id        integer                      NOT NULL,
    description              varchar(64)                  NOT NULL,
    creation_date            datetime year to second      NOT NULL,
    creation_user            varchar(64)                  NOT NULL,
    modification_date        datetime year to second      NOT NULL,
    modification_user        varchar(64)                  NOT NULL,
  primary key (invoice_status_id)
    constraint pk_invoice_status
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

CREATE TABLE invoice (
    invoice_id               integer                      NOT NULL,
    project_id               integer                      NOT NULL,
    creation_date            datetime year to second      NOT NULL,
    creation_user            varchar(64)                  NOT NULL,
    modification_date        datetime year to second      NOT NULL,
    modification_user        varchar(64)                  NOT NULL,
    salesTax                 decimal(7,3)                 NOT NULL,
    payment_terms_id         integer                      NOT NULL,
    invoice_number           varchar(64)                          ,
    po_number                varchar(64)                          ,
    invoice_date             date                         NOT NULL,
    due_date                 date                         NOT NULL,
    paid                     smallint                     NOT NULL,
    company_id               integer                      NOT NULL,
    invoice_status_id        integer                      NOT NULL,
  primary key (invoice_id)
    constraint pk_invoice
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

CREATE TABLE service_details (
    service_detail_id        integer                      NOT NULL,
    time_entry_id            integer                      NOT NULL,
    invoice_id               integer                      NOT NULL,
    rate                     integer                      NOT NULL,
    amount                   decimal(17,2)                NOT NULL,
    creation_date            datetime year to second      NOT NULL,
    creation_user            varchar(64)                  NOT NULL,
    modification_date        datetime year to second      NOT NULL,
    modification_user        varchar(64)                  NOT NULL,
  primary key (service_detail_id)
    constraint pk_service_details
);

create table id_sequences  (
    name               VARCHAR(255)                      not null,
    next_block_start     INTEGER                         not null,
    block_size           INTEGER                         not null,
    exhausted            INTEGER                         not null,
  primary key (name)
    constraint pk_id_sequences
);

INSERT INTO id_sequences (name,next_block_start,block_size,exhausted) VALUES('ServiceDetail',100,20,0);
INSERT INTO id_sequences(name, next_block_start, block_size, exhausted) VALUES('stress', 50000, 20, 0);
INSERT INTO id_sequences(name, next_block_start, block_size, exhausted) VALUES('com.topcoder.timetracker.invoice.servicedetail.InvoiceServiceDetail', 2361, 20, 0);