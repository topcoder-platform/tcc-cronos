
CREATE TABLE account_status (
       account_status_id    integer NOT NULL,
       description          varchar(64) NOT NULL,
       creation_date        datetime year to second NOT NULL,
       creation_user        varchar(64) NOT NULL,
       modification_date    datetime year to second NOT NULL,
       modification_user    varchar(64) NOT NULL,
       PRIMARY KEY (account_status_id)
);


CREATE TABLE state_name (
       state_name_id        integer NOT NULL,
       name                 varchar(64) NOT NULL,
       abbreviation         varchar(2) NOT NULL,
       creation_date        datetime year to second NOT NULL,
       creation_user        varchar(64) NOT NULL,
       modification_date    datetime year to second NOT NULL,
       modification_user    varchar(64) NOT NULL,
       PRIMARY KEY (state_name_id)
);


CREATE TABLE reject_reason (
       reject_reason_id     integer NOT NULL,
       description          varchar(64) NOT NULL,
       active               smallint NOT NULL,
       creation_date        datetime year to second NOT NULL,
       creation_user        varchar(64) NOT NULL,
       modification_date    datetime year to second NOT NULL,
       modification_user    varchar(64) NOT NULL,
       PRIMARY KEY (reject_reason_id)
);


CREATE TABLE address (
       address_id           integer NOT NULL,
       line1                varchar(100) NOT NULL,
       line2                varchar(100),
       city                 varchar(30) NOT NULL,
       state_name_id        integer NOT NULL,
       zip_code             varchar(10) NOT NULL,
       creation_date        datetime year to second NOT NULL,
       creation_user        varchar(64) NOT NULL,
       modification_date    datetime year to second NOT NULL,
       modification_user    varchar(64) NOT NULL,
       PRIMARY KEY (address_id)
);

ALTER TABLE address ADD CONSTRAINT FOREIGN KEY (state_name_id) REFERENCES state_name CONSTRAINT fk_address_state;


CREATE TABLE company (
       company_id           integer NOT NULL,
       name                 varchar(64),
       passcode             varchar(64) NOT NULL,
       creation_date        datetime year to second NOT NULL,
       creation_user        varchar(64) NOT NULL,
       modification_date    datetime year to second NOT NULL,
       modification_user    varchar(64) NOT NULL,
       PRIMARY KEY (company_id)
--       UNIQUE ( passcode   )
);

ALTER TABLE company ADD CONSTRAINT UNIQUE (passcode) CONSTRAINT unique_passcode;


CREATE TABLE company_address (
       company_id           integer NOT NULL,
       address_id           integer NOT NULL,
       creation_date        datetime year to second NOT NULL,
       creation_user        varchar(64) NOT NULL,
       modification_date    datetime year to second NOT NULL,
       modification_user    varchar(64) NOT NULL,
       PRIMARY KEY (company_id, address_id)
       -- FOREIGN KEY (address_id) REFERENCES address,
       -- FOREIGN KEY (company_id) REFERENCES company
);
ALTER TABLE company_address ADD CONSTRAINT FOREIGN KEY (address_id) REFERENCES address CONSTRAINT fk_address;
ALTER TABLE company_address ADD CONSTRAINT FOREIGN KEY (company_id) REFERENCES company CONSTRAINT fk_company;

CREATE TABLE user_account (
       user_account_id      integer NOT NULL,
       company_id           integer,
       account_status_id    integer NOT NULL,
       user_name            varchar(64) NOT NULL,
       password             varchar(64) NOT NULL,
       creation_date        datetime year to second NOT NULL,
       creation_user        varchar(64) NOT NULL,
       modification_date    datetime year to second NOT NULL,
       modification_user    varchar(64) NOT NULL,
       PRIMARY KEY (user_account_id)
--       FOREIGN KEY (account_status_id) REFERENCES account_status,
--       FOREIGN KEY (company_id)   REFERENCES company
);

ALTER TABLE user_account ADD CONSTRAINT FOREIGN KEY (account_status_id) REFERENCES account_status CONSTRAINT fk_user_account_status;
ALTER TABLE user_account ADD CONSTRAINT FOREIGN KEY (company_id) REFERENCES company CONSTRAINT fk_user_comany;

CREATE TABLE user_address (
       user_account_id      integer NOT NULL,
       address_id           integer NOT NULL,
       creation_date        datetime year to second NOT NULL,
       creation_user        varchar(64) NOT NULL,
       modification_date    datetime year to second NOT NULL,
       modification_user    varchar(64) NOT NULL,
       PRIMARY KEY (user_account_id, address_id)
   --    FOREIGN KEY (address_id)                             REFERENCES address,
   --    FOREIGN KEY (user_account_id)                             REFERENCES user_account
);

ALTER TABLE user_address ADD CONSTRAINT FOREIGN KEY (address_id) REFERENCES address CONSTRAINT fk_user_addess;
ALTER TABLE user_address ADD CONSTRAINT FOREIGN KEY (user_account_id) REFERENCES user_account CONSTRAINT fk_user_account;

CREATE TABLE contact (
       contact_id           integer NOT NULL,
       first_name           varchar(64) NOT NULL,
       last_name            varchar(64) NOT NULL,
       phone                varchar(30) NOT NULL,
       email                varchar(64) NOT NULL,
       creation_date        datetime year to second NOT NULL,
       creation_user        varchar(64) NOT NULL,
       modification_date    datetime year to second NOT NULL,
       modification_user    varchar(64) NOT NULL,
       PRIMARY KEY (contact_id)
);


CREATE TABLE user_contact (
       user_account_id      integer NOT NULL,
       contact_id           integer NOT NULL,
       creation_date        datetime year to second NOT NULL,
       creation_user        varchar(64) NOT NULL,
       modification_date    datetime year to second NOT NULL,
       modification_user    varchar(64) NOT NULL,
       PRIMARY KEY (user_account_id, contact_id)
--       FOREIGN KEY (contact_id)  REFERENCES contact,
--       FOREIGN KEY (user_account_id)  REFERENCES user_account
);

ALTER TABLE user_contact ADD CONSTRAINT FOREIGN KEY (contact_id) REFERENCES contact CONSTRAINT fk_user_contact;
ALTER TABLE user_contact ADD CONSTRAINT FOREIGN KEY (user_account_id) REFERENCES user_account CONSTRAINT fk_user_account_con;



CREATE TABLE company_contact (
       company_id           integer NOT NULL,
       contact_id           integer NOT NULL,
       creation_date        datetime year to second NOT NULL,
       creation_user        varchar(64) NOT NULL,
       modification_date    datetime year to second NOT NULL,
       modification_user    varchar(64) NOT NULL,
       PRIMARY KEY (company_id, contact_id)
--       FOREIGN KEY (contact_id) REFERENCES contact,
--       FOREIGN KEY (company_id) REFERENCES company
);

ALTER TABLE company_contact ADD CONSTRAINT FOREIGN KEY (contact_id) REFERENCES contact CONSTRAINT fk_company_contact;
ALTER TABLE company_contact ADD CONSTRAINT FOREIGN KEY (company_id) REFERENCES company CONSTRAINT fk_comp;



CREATE TABLE comp_rej_reason (
       company_id           integer NOT NULL,
       reject_reason_id     integer NOT NULL,
       creation_date        datetime year to second NOT NULL,
       creation_user        varchar(64) NOT NULL,
       modification_date    datetime year to second NOT NULL,
       modification_user    varchar(64) NOT NULL,
       PRIMARY KEY (company_id, reject_reason_id)
--       FOREIGN KEY (reject_reason_id)    REFERENCES reject_reason,
--       FOREIGN KEY (company_id)   REFERENCES company
);

ALTER TABLE comp_rej_reason ADD CONSTRAINT FOREIGN KEY (reject_reason_id) REFERENCES reject_reason CONSTRAINT fk_rej_reason;
ALTER TABLE comp_rej_reason ADD CONSTRAINT FOREIGN KEY (company_id) REFERENCES company CONSTRAINT fk_rej_comp;



CREATE TABLE reject_email (
       reject_email_id      integer NOT NULL,
       body                 text NOT NULL,
       creation_date        datetime year to second NOT NULL,
       creation_user        varchar(64) NOT NULL,
       modification_date    datetime year to second NOT NULL,
       modification_user    varchar(64) NOT NULL,
       PRIMARY KEY (reject_email_id)
);


CREATE TABLE comp_reject_email (
       company_id           integer NOT NULL,
       reject_email_id      integer NOT NULL,
       creation_date        datetime year to second NOT NULL,
       creation_user        varchar(64) NOT NULL,
       modification_date    datetime year to second NOT NULL,
       modification_user    varchar(64) NOT NULL,
       PRIMARY KEY (company_id, reject_email_id)
--       FOREIGN KEY (reject_email_id)    REFERENCES reject_email,
--       FOREIGN KEY (company_id)         REFERENCES company
);

ALTER TABLE comp_reject_email ADD CONSTRAINT FOREIGN KEY (reject_email_id) REFERENCES reject_email CONSTRAINT fk_rej_email;
ALTER TABLE comp_reject_email ADD CONSTRAINT FOREIGN KEY (company_id) REFERENCES company CONSTRAINT fk_email_comp;


