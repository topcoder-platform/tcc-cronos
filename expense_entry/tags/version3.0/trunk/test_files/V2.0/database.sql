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


CREATE TABLE company (
       company_id           integer NOT NULL,
       name                 varchar(64),
       passcode             varchar(64) NOT NULL,
       creation_date        datetime year to second NOT NULL,
       creation_user        varchar(64) NOT NULL,
       modification_date    datetime year to second NOT NULL,
       modification_user    varchar(64) NOT NULL,
       PRIMARY KEY (company_id),
       UNIQUE (
              passcode
       )
);


CREATE TABLE expense_status (
       expense_status_id    integer NOT NULL,
       description          varchar(64) NOT NULL,
       creation_date        datetime year to second NOT NULL,
       creation_user        varchar(64) NOT NULL,
       modification_date    datetime year to second NOT NULL,
       modification_user    varchar(64) NOT NULL,
       PRIMARY KEY (expense_status_id)
);


CREATE TABLE expense_type (
       expense_type_id      integer NOT NULL,
       description          varchar(64) NOT NULL,
       active               smallint NOT NULL,
       creation_date        datetime year to second NOT NULL,
       creation_user        varchar(64) NOT NULL,
       modification_date    datetime year to second NOT NULL,
       modification_user    varchar(64) NOT NULL,
       PRIMARY KEY (expense_type_id)
);


CREATE TABLE expense_entry (
       expense_entry_id     integer NOT NULL,
       company_id           integer NOT NULL,
       expense_type_id      integer NOT NULL,
       expense_status_id    integer NOT NULL,
       description          varchar(64) NOT NULL,
       entry_date           datetime year to second NOT NULL,
       amount               integer NOT NULL,
       billable             smallint NOT NULL,
       creation_date        datetime year to second NOT NULL,
       creation_user        varchar(64) NOT NULL,
       modification_date    datetime year to second NOT NULL,
       modification_user    varchar(64) NOT NULL,
       PRIMARY KEY (expense_entry_id), 
       FOREIGN KEY (company_id)
                             REFERENCES company, 
       FOREIGN KEY (expense_status_id)
                             REFERENCES expense_status, 
       FOREIGN KEY (expense_type_id)
                             REFERENCES expense_type
);


CREATE TABLE exp_reject_reason (
       expense_entry_id     integer NOT NULL,
       reject_reason_id     integer NOT NULL,
       creation_date        datetime year to second NOT NULL,
       creation_user        varchar(64) NOT NULL,
       modification_date    datetime year to second NOT NULL,
       modification_user    varchar(64) NOT NULL,
       PRIMARY KEY (expense_entry_id, reject_reason_id), 
       FOREIGN KEY (reject_reason_id)
                             REFERENCES reject_reason, 
       FOREIGN KEY (expense_entry_id)
                             REFERENCES expense_entry
);


CREATE TABLE comp_exp_type (
       company_id           integer NOT NULL,
       expense_type_id      integer NOT NULL,
       creation_date        datetime year to second NOT NULL,
       creation_user        varchar(64) NOT NULL,
       modification_date    datetime year to second NOT NULL,
       modification_user    varchar(64) NOT NULL,
       PRIMARY KEY (company_id, expense_type_id), 
       FOREIGN KEY (expense_type_id)
                             REFERENCES expense_type, 
       FOREIGN KEY (company_id)
                             REFERENCES company
);

CREATE TABLE comp_rej_reason (
       company_id           integer NOT NULL,
       reject_reason_id     integer NOT NULL,
       creation_date        datetime year to second NOT NULL,
       creation_user        varchar(64) NOT NULL,
       modification_date    datetime year to second NOT NULL,
       modification_user    varchar(64) NOT NULL,
       PRIMARY KEY (company_id, reject_reason_id), 
       FOREIGN KEY (reject_reason_id)
                             REFERENCES reject_reason, 
       FOREIGN KEY (company_id)
                             REFERENCES company
);

drop table comp_rej_reason;
drop table comp_exp_type;
drop table exp_reject_reason;
drop table expense_entry;
drop table expense_type;
drop table expense_status;
drop table company;
drop table reject_reason;