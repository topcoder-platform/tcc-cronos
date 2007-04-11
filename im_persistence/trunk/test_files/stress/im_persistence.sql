
CREATE TABLE id_sequences (
        name                    VARCHAR(255) NOT NULL,
                                PRIMARY KEY (name),
        next_block_start        INT8 NOT NULL,
        block_size              INTEGER NOT NULL,
        exhausted               INTEGER default 0
);

INSERT INTO id_sequences (name, next_block_start, block_size) values ('stress', 1, 20);

CREATE TABLE entity_status (
  entity_status_id INTEGER NOT NULL,
  name VARCHAR(64) NOT NULL,
  description VARCHAR(255) NOT NULL,
  create_date DATETIME YEAR TO FRACTION(3) NOT NULL,
  create_user VARCHAR(64) NOT NULL,
  modify_date DATETIME YEAR TO FRACTION(3) NOT NULL,
  modify_user VARCHAR(64) NOT NULL,
  PRIMARY KEY(entity_status_id)
);

CREATE TABLE entity_status_history (
  entity_id INTEGER NOT NULL,
  start_date DATETIME YEAR TO FRACTION(3) NOT NULL,
  entity_status_id INTEGER NOT NULL,
  end_date DATETIME YEAR TO FRACTION(3),
  create_date DATETIME YEAR TO FRACTION(3) NOT NULL,
  create_user VARCHAR(64) NOT NULL,
  modify_date DATETIME YEAR TO FRACTION(3) NOT NULL,
  modify_user VARCHAR(64) NOT NULL,
  PRIMARY KEY(entity_id, start_date),
  FOREIGN KEY(entity_status_id)
    REFERENCES entity_status(entity_status_id)
);

CREATE TABLE client (
  client_id INTEGER NOT NULL,
  first_name VARCHAR(64) NOT NULL,
  last_name VARCHAR(64) NOT NULL,
  company VARCHAR(255),
  title VARCHAR(255),
  email VARCHAR(255) NOT NULL,
  create_date DATETIME YEAR TO FRACTION(3) NOT NULL,
  create_user VARCHAR(64) NOT NULL,
  modify_date DATETIME YEAR TO FRACTION(3) NOT NULL,
  modify_user VARCHAR(64) NOT NULL,
  PRIMARY KEY(client_id)
);

create table user (
    user_id DECIMAL(10,0) not null,
    first_name VARCHAR(64),
    last_name VARCHAR(64),
    handle VARCHAR(50) not null
);

alter table user add constraint primary key
        (user_id)
        constraint u124_45;

create table email (
    user_id DECIMAL(10,0),
    address VARCHAR(100)
);

create table company (
    company_id DECIMAL(10,0),
    company_name VARCHAR(100)
);

alter table company add constraint primary key
        (company_id)
        constraint u171_139;

create table contact (
    contact_id DECIMAL(10,0) not null,
    company_id DECIMAL(10,0) not null,
    title VARCHAR(100)
);

alter table contact add constraint foreign key
        (company_id)
        references company
        (company_id)
        constraint contact_company_fk;

alter table contact add constraint foreign key
        (contact_id)
        references user
        (user_id)
        constraint contact_user_fk;

CREATE TABLE principal(
  principal_id integer NOT NULL,
  principal_name varchar(255),
  PRIMARY KEY (principal_id)
);

CREATE TABLE role(
  role_id integer NOT NULL,
  role_name varchar(255),
  PRIMARY KEY (role_id)
);

CREATE TABLE principal_role(
  principal_id integer NOT NULL,
  role_id integer NOT NULL,
  PRIMARY KEY (principal_id, role_id),
  FOREIGN KEY (principal_id)
    REFERENCES principal(principal_id),
  FOREIGN KEY (role_id)
    REFERENCES role(role_id)
);

CREATE TABLE category (
  category_id INTEGER NOT NULL,
  name VARCHAR(64) NOT NULL,
  description VARCHAR(255) NOT NULL,
  chattable_flag VARCHAR(1) NOT NULL,
  create_date DATETIME YEAR TO FRACTION(3) NOT NULL,
  create_user VARCHAR(64) NOT NULL,
  modify_date DATETIME YEAR TO FRACTION(3) NOT NULL,
  modify_user VARCHAR(64) NOT NULL,
  PRIMARY KEY(category_id)
);

CREATE TABLE manager_category (
  manager_id INTEGER NOT NULL,
  category_id INTEGER NOT NULL,
  create_date DATETIME YEAR TO FRACTION(3) NOT NULL,
  create_user VARCHAR(64) NOT NULL,
  modify_date DATETIME YEAR TO FRACTION(3) NOT NULL,
  modify_user VARCHAR(64) NOT NULL,
  PRIMARY KEY(manager_id, category_id),
  FOREIGN KEY(category_id)
    REFERENCES category(category_id)
);

CREATE TABLE all_user (
  user_id INTEGER NOT NULL,
  registered_flag VARCHAR(1) NOT NULL,
  username VARCHAR(64) NOT NULL,
  create_date DATETIME YEAR TO FRACTION(3) NOT NULL,
  create_user VARCHAR(64) NOT NULL,
  modify_date DATETIME YEAR TO FRACTION(3) NOT NULL,
  modify_user VARCHAR(64) NOT NULL,
  PRIMARY KEY(user_id)
);
