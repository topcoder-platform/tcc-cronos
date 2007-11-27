-- Tables and values required by Contact component

CREATE TABLE country_name (
  country_name_id               INTEGER                         NOT NULL,
  name                          VARCHAR(64)                     NOT NULL,
  creation_date                 DATETIME YEAR TO SECOND         NOT NULL,
  creation_user                 VARCHAR(64)                     NOT NULL,
  modification_date             DATETIME YEAR TO SECOND         NOT NULL,
  modification_user             VARCHAR(64)                     NOT NULL,
  PRIMARY KEY (country_name_id) CONSTRAINT pk_country_name
);
CREATE TABLE state_name (
  state_name_id                 INTEGER                         NOT NULL,
  name                          VARCHAR(64)                     NOT NULL,
  abbreviation                  VARCHAR(2)                      NOT NULL,
  creation_date                 DATETIME YEAR TO SECOND         NOT NULL,
  creation_user                 VARCHAR(64)                     NOT NULL,
  modification_date             DATETIME YEAR TO SECOND         NOT NULL,
  modification_user             VARCHAR(64)                     NOT NULL,
  PRIMARY KEY (state_name_id) CONSTRAINT pk_state_name
);
CREATE TABLE address (
  address_id                    INTEGER                         NOT NULL,
  line1                         VARCHAR(100)                    NOT NULL,
  line2                         VARCHAR(100),
  city                          VARCHAR(30)                     NOT NULL,
  country_name_id               INTEGER                         NOT NULL,
  state_name_id                 INTEGER                         NOT NULL,
  zip_code                      VARCHAR(10)                     NOT NULL,
  creation_date                 DATETIME YEAR TO SECOND         NOT NULL,
  creation_user                 VARCHAR(64)                     NOT NULL,
  modification_date             DATETIME YEAR TO SECOND         NOT NULL,
  modification_user             VARCHAR(64)                     NOT NULL,
  PRIMARY KEY (address_id) CONSTRAINT pk_address,
  FOREIGN KEY (country_name_id)
    REFERENCES country_name(country_name_id) CONSTRAINT fk_address_countryname_countrynameid,
  FOREIGN KEY (state_name_id)
    REFERENCES state_name(state_name_id) CONSTRAINT fk_address_statename_statenameid
);
CREATE TABLE address_type (
  address_type_id               INTEGER                         NOT NULL,
  description                   VARCHAR(64)                     NOT NULL,
  creation_date                 DATETIME YEAR TO SECOND         NOT NULL,
  creation_user                 VARCHAR(64)                     NOT NULL,
  modification_date             DATETIME YEAR TO SECOND         NOT NULL,
  modification_user             VARCHAR(64)                     NOT NULL,
  PRIMARY KEY (address_type_id) CONSTRAINT pk_address_type
);
CREATE TABLE address_relation (
  entity_id                     INTEGER                         NOT NULL,
  address_type_id               INTEGER                         NOT NULL,
  address_id                    INTEGER                         NOT NULL,
  creation_date                 DATETIME YEAR TO SECOND         NOT NULL,
  creation_user                 VARCHAR(64)                     NOT NULL,
  modification_date             DATETIME YEAR TO SECOND         NOT NULL,
  modification_user             VARCHAR(64)                     NOT NULL,
  PRIMARY KEY (address_id, address_type_id, entity_id) CONSTRAINT pk_address_relation,
  FOREIGN KEY (address_id)
    REFERENCES address(address_id) CONSTRAINT fk_addressrelation_address_addressid,
  FOREIGN KEY (address_type_id)
    REFERENCES address_type(address_type_id) CONSTRAINT fk_addressrelation_addresstype_addresstypeid
);

CREATE TABLE contact (
  contact_id                    INTEGER                         NOT NULL,
  first_name                    VARCHAR(64)                     NOT NULL,
  last_name                     VARCHAR(64)                     NOT NULL,
  phone                         VARCHAR(30)                     NOT NULL,
  email                         VARCHAR(64)                     NOT NULL,
  creation_date                 DATETIME YEAR TO SECOND         NOT NULL,
  creation_user                 VARCHAR(64)                     NOT NULL,
  modification_date             DATETIME YEAR TO SECOND         NOT NULL,
  modification_user             VARCHAR(64)                     NOT NULL,
  PRIMARY KEY (contact_id) CONSTRAINT pk_contact
);
CREATE TABLE contact_type (
  contact_type_id               INTEGER                         NOT NULL,
  description                   VARCHAR(64)                     NOT NULL,
  creation_date                 DATETIME YEAR TO SECOND         NOT NULL,
  creation_user                 VARCHAR(64)                     NOT NULL,
  modification_date             DATETIME YEAR TO SECOND         NOT NULL,
  modification_user             VARCHAR(64)                     NOT NULL,
  PRIMARY KEY (contact_type_id) CONSTRAINT pk_contact_type
);
CREATE TABLE contact_relation (
  entity_id                     INTEGER                         NOT NULL,
  contact_type_id               INTEGER                         NOT NULL,
  contact_id                    INTEGER                         NOT NULL,
  creation_date                 DATETIME YEAR TO SECOND         NOT NULL,
  creation_user                 VARCHAR(64)                     NOT NULL,
  modification_date             DATETIME YEAR TO SECOND         NOT NULL,
  modification_user             VARCHAR(64)                     NOT NULL,
  PRIMARY KEY (contact_id, contact_type_id, entity_id) CONSTRAINT pk_contact_relation,
  FOREIGN KEY (contact_type_id)
    REFERENCES contact_type(contact_type_id) CONSTRAINT fk_contactrelation_contacttype_contacttypeid,
  FOREIGN KEY (contact_id)
    REFERENCES contact(contact_id) CONSTRAINT fk_contactrelation_contact_contactid
);


-- Country and state names are just sample ones, the lists are not complete
INSERT INTO country_name(country_name_id, name, creation_date, creation_user, modification_date, modification_user)
  VALUES(840, 'United States', CURRENT, 'System', CURRENT, 'System');
INSERT INTO country_name(country_name_id, name, creation_date, creation_user, modification_date, modification_user)
  VALUES(2, 'Canada', CURRENT, 'System', CURRENT, 'System');

INSERT INTO state_name(state_name_id, name, abbreviation, creation_date, creation_user, modification_date, modification_user)
  VALUES(1, 'Connecticut', 'CT', CURRENT, 'System', CURRENT, 'System');
INSERT INTO state_name(state_name_id, name, abbreviation, creation_date, creation_user, modification_date, modification_user)
  VALUES(2, 'New York', 'NY', CURRENT, 'System', CURRENT, 'System');
INSERT INTO state_name(state_name_id, name, abbreviation, creation_date, creation_user, modification_date, modification_user)
  VALUES(3, 'Virginia', 'VA', CURRENT, 'System', CURRENT, 'System');
INSERT INTO state_name(state_name_id, name, abbreviation, creation_date, creation_user, modification_date, modification_user)
  VALUES(4, 'Washington', 'WA', CURRENT, 'System', CURRENT, 'System');
INSERT INTO state_name(state_name_id, name, abbreviation, creation_date, creation_user, modification_date, modification_user)
  VALUES(100, 'N/A', 'NA', CURRENT, 'System', CURRENT, 'System');



INSERT INTO address_type(address_type_id, description, creation_date, creation_user, modification_date, modification_user)
  VALUES(1, 'PROJECT', CURRENT, 'System', CURRENT, 'System');
INSERT INTO address_type(address_type_id, description, creation_date, creation_user, modification_date, modification_user)
  VALUES(2, 'CLIENT', CURRENT, 'System', CURRENT, 'System');
INSERT INTO address_type(address_type_id, description, creation_date, creation_user, modification_date, modification_user)
  VALUES(3, 'COMPANY', CURRENT, 'System', CURRENT, 'System');
INSERT INTO address_type(address_type_id, description, creation_date, creation_user, modification_date, modification_user)
  VALUES(4, 'USER', CURRENT, 'System', CURRENT, 'System');

INSERT INTO contact_type(contact_type_id, description, creation_date, creation_user, modification_date, modification_user)
  VALUES(1, 'PROJECT', CURRENT, 'System', CURRENT, 'System');
INSERT INTO contact_type(contact_type_id, description, creation_date, creation_user, modification_date, modification_user)
  VALUES(2, 'CLIENT', CURRENT, 'System', CURRENT, 'System');
INSERT INTO contact_type(contact_type_id, description, creation_date, creation_user, modification_date, modification_user)
  VALUES(3, 'COMPANY', CURRENT, 'System', CURRENT, 'System');
INSERT INTO contact_type(contact_type_id, description, creation_date, creation_user, modification_date, modification_user)
  VALUES(4, 'USER', CURRENT, 'System', CURRENT, 'System');
