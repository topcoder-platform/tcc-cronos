create table client  (
  client_id            INTEGER                         not null,
  name                 VARCHAR(64)                     not null,
  company_id           INTEGER                         not null,
  creation_date        DATETIME YEAR TO SECOND         not null,
  creation_user        VARCHAR(64)                     not null,
  modification_date    DATETIME YEAR TO SECOND         not null,
  modification_user    VARCHAR(64)                     not null,
  payment_term_id      INTEGER                         not null,
  status               smallint                        not null,
  salesTax             decimal(7, 3)                   not null,
  start_date           DATETIME YEAR TO SECOND         not null,
  end_date             DATETIME YEAR TO SECOND         not null,
  greek_name           VARCHAR(64),
primary key (client_id)
      constraint pk_client
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

create table id_sequences  (
  name               VARCHAR(255)                      not null,
  next_block_start     INTEGER                         not null,
  block_size           INTEGER                         not null,
  exhausted            INTEGER                         not null,
primary key (name)
      constraint pk_id_sequences
);