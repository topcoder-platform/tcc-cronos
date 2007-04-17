create table id_sequences  (
  name               VARCHAR(255)                      not null,
  next_block_start     INT8                            not null,
  block_size           INTEGER                         not null,
  exhausted            SMALLINT                         not null,
  primary key (name)
      constraint pk_id_sequences
);

create table country_name  (
  country_name_id      INT8                            not null,
  name                 VARCHAR(64)                     not null,
  creation_date        DATETIME YEAR TO SECOND         not null,
  creation_user        VARCHAR(64)                     not null,
  modification_date    DATETIME YEAR TO SECOND         not null,
  modification_user    VARCHAR(64)                     not null,
  primary key (country_name_id)
      constraint pk_country_name
);

create table state_name  (
  state_name_id        INT8                            not null,
  name                 VARCHAR(64)                     not null,
  abbreviation         VARCHAR(2)                      not null,
  creation_date        DATETIME YEAR TO SECOND         not null,
  creation_user        VARCHAR(64)                     not null,
  modification_date    DATETIME YEAR TO SECOND         not null,
  modification_user    VARCHAR(64)                     not null,
  primary key (state_name_id)
      constraint pk_state_name
);

create table address  (
  address_id           INT8                            not null,
  line1                VARCHAR(100)                    not null,
  line2                VARCHAR(100),
  city                 VARCHAR(30)                     not null,
  state_name_id        INT8                            not null,
  country_name_id      INT8                            not null,
  zip_code             VARCHAR(10)                     not null,
  creation_date        DATETIME YEAR TO SECOND         not null,
  creation_user        VARCHAR(64)                     not null,
  modification_date    DATETIME YEAR TO SECOND         not null,
  modification_user    VARCHAR(64)                     not null,
  primary key (address_id)
      constraint pk_address
);

alter table address
   add constraint foreign key (state_name_id)
      references state_name (state_name_id)
      constraint fk_address_state_name;

alter table address
   add constraint foreign key (country_name_id)
      references country_name (country_name_id)
      constraint fk_address_country_name;

CREATE TABLE address_type (
       address_type_id      INT8      				NOT NULL,
       description          varchar(64) 			NOT NULL,
       creation_date        datetime year to second NOT NULL,
       creation_user        varchar(64) 			NOT NULL,
       modification_date    datetime year to second NOT NULL,
       modification_user    varchar(64) 			NOT NULL,
       primary key (address_type_id)
           constraint pk_address_type
);

CREATE TABLE address_relation (
       entity_id            INT8    				NOT NULL,
       address_type_id      INT8    				NOT NULL,
       creation_date        datetime year to second NOT NULL,
       creation_user        varchar(64) 			NOT NULL,
       modification_date    datetime year to second NOT NULL,
       modification_user    varchar(64) 			NOT NULL,
       address_id           INT8    				NOT NULL,
       primary key (address_id, address_type_id, entity_id)
           constraint pk_address_relation
);


ALTER TABLE address_relation
       ADD CONSTRAINT FOREIGN KEY (address_id)
       REFERENCES address (address_id)
	     CONSTRAINT fk_address_relation_address;

ALTER TABLE address_relation
       ADD CONSTRAINT FOREIGN KEY (address_type_id)
       REFERENCES address_type (address_type_id)
	     CONSTRAINT fk_address_relation_address_type;

create table contact  (
  contact_id           INT8                            not null,
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

CREATE TABLE contact_type (
       contact_type_id      INT8      				NOT NULL,
       description          varchar(64) 			NOT NULL,
       creation_date        datetime year to second NOT NULL,
       creation_user        varchar(64) 			NOT NULL,
       modification_date    datetime year to second NOT NULL,
       modification_user    varchar(64) 			NOT NULL,
       primary key (contact_type_id)
            constraint pk_contact_type
);

CREATE TABLE contact_relation (
       entity_id            INT8    				NOT NULL,
       creation_date        datetime year to second NOT NULL,
       creation_user        varchar(64) 			NOT NULL,
       modification_date    datetime year to second NOT NULL,
       modification_user    varchar(64) 			NOT NULL,
       contact_id           INT8    				NOT NULL,
       contact_type_id      INT8    				NOT NULL,
       primary key (contact_id, contact_type_id, entity_id)
           constraint pk_contact_relation
);

ALTER TABLE contact_relation
       ADD CONSTRAINT FOREIGN KEY (contact_id)
       REFERENCES contact (contact_id)
       CONSTRAINT fk_contact_relation_contact;

ALTER TABLE contact_relation
       ADD CONSTRAINT FOREIGN KEY (contact_type_id)
       REFERENCES contact_type (contact_type_id)
	     CONSTRAINT fk_contact_relation_contact_type;