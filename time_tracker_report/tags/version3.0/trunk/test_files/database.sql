CREATE TABLE reject_email (
       reject_email_id      integer NOT NULL,
       body                 text NOT NULL,
       creation_date        datetime year to second NOT NULL,
       creation_user        varchar(64) NOT NULL,
       modification_date    datetime year to second NOT NULL,
       modification_user    varchar(64) NOT NULL,
       PRIMARY KEY (reject_email_id)
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

CREATE TABLE comp_reject_email (
       company_id           integer NOT NULL,
       reject_email_id      integer NOT NULL,
       creation_date        datetime year to second NOT NULL,
       creation_user        varchar(64) NOT NULL,
       modification_date    datetime year to second NOT NULL,
       modification_user    varchar(64) NOT NULL,
       PRIMARY KEY (company_id, reject_email_id), 
       FOREIGN KEY (reject_email_id)
                             REFERENCES reject_email, 
       FOREIGN KEY (company_id)
                             REFERENCES company
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

CREATE TABLE project (
       project_id           integer NOT NULL,
       company_id           integer NOT NULL,
       name                 varchar(64) NOT NULL,
       description          varchar(64) NOT NULL,
       start_date           datetime year to second NOT NULL,
       end_date             datetime year to second NOT NULL,
       creation_date        datetime year to second NOT NULL,
       creation_user        varchar(64) NOT NULL,
       modification_date    datetime year to second NOT NULL,
       modification_user    varchar(64) NOT NULL,
       PRIMARY KEY (project_id), 
       FOREIGN KEY (company_id)
                             REFERENCES company
);

CREATE TABLE project_expense (
       project_id           integer NOT NULL,
       expense_entry_id     integer NOT NULL,
       creation_date        datetime year to second NOT NULL,
       creation_user        varchar(64) NOT NULL,
       modification_date    datetime year to second NOT NULL,
       modification_user    varchar(64) NOT NULL,
       PRIMARY KEY (project_id, expense_entry_id), 
       FOREIGN KEY (expense_entry_id)
                             REFERENCES expense_entry, 
       FOREIGN KEY (project_id)
                             REFERENCES project
);

CREATE TABLE time_status (
       time_status_id       integer NOT NULL,
       description          varchar(64) NOT NULL,
       creation_date        datetime year to second NOT NULL,
       creation_user        varchar(64) NOT NULL,
       modification_date    datetime year to second NOT NULL,
       modification_user    varchar(64) NOT NULL,
       PRIMARY KEY (time_status_id)
);

CREATE TABLE task_type (
       task_type_id         integer NOT NULL,
       description          varchar(64) NOT NULL,
       active               smallint NOT NULL,
       creation_date        datetime year to second NOT NULL,
       creation_user        varchar(64) NOT NULL,
       modification_date    datetime year to second NOT NULL,
       modification_user    varchar(64) NOT NULL,
       PRIMARY KEY (task_type_id)
);

CREATE TABLE time_entry (
       time_entry_id        integer NOT NULL,
       company_id           integer NOT NULL,
       time_status_id       integer NOT NULL,
       task_type_id         integer NOT NULL,
       description          varchar(64) NOT NULL,
       entry_date           datetime year to second NOT NULL,
       hours                integer NOT NULL,
       billable             smallint NOT NULL,
       creation_date        datetime year to second NOT NULL,
       creation_user        varchar(64) NOT NULL,
       modification_date    datetime year to second NOT NULL,
       modification_user    varchar(64) NOT NULL,
       PRIMARY KEY (time_entry_id), 
       FOREIGN KEY (company_id)
                             REFERENCES company, 
       FOREIGN KEY (time_status_id)
                             REFERENCES time_status, 
       FOREIGN KEY (task_type_id)
                             REFERENCES task_type
);

CREATE TABLE project_time (
       project_id           integer NOT NULL,
       time_entry_id        integer NOT NULL,
       creation_date        datetime year to second NOT NULL,
       creation_user        varchar(64) NOT NULL,
       modification_date    datetime year to second NOT NULL,
       modification_user    varchar(64) NOT NULL,
       PRIMARY KEY (project_id, time_entry_id), 
       FOREIGN KEY (time_entry_id)
                             REFERENCES time_entry, 
       FOREIGN KEY (project_id)
                             REFERENCES project
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

CREATE TABLE comp_task_type (
       company_id           integer NOT NULL,
       task_type_id         integer NOT NULL,
       creation_date        datetime year to second NOT NULL,
       creation_user        varchar(64) NOT NULL,
       modification_date    datetime year to second NOT NULL,
       modification_user    varchar(64) NOT NULL,
       PRIMARY KEY (company_id, task_type_id), 
       FOREIGN KEY (task_type_id)
                             REFERENCES task_type, 
       FOREIGN KEY (company_id)
                             REFERENCES company
);

CREATE TABLE account_status (
       account_status_id    integer NOT NULL,
       description          varchar(64) NOT NULL,
       creation_date        datetime year to second NOT NULL,
       creation_user        varchar(64) NOT NULL,
       modification_date    datetime year to second NOT NULL,
       modification_user    varchar(64) NOT NULL,
       PRIMARY KEY (account_status_id)
);

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
       PRIMARY KEY (user_account_id), 
       FOREIGN KEY (account_status_id)
                             REFERENCES account_status, 
       FOREIGN KEY (company_id)
                             REFERENCES company
);

CREATE TABLE project_manager (
       project_id           integer NOT NULL,
       user_account_id      integer NOT NULL,
       creation_date        datetime year to second NOT NULL,
       creation_user        varchar(64) NOT NULL,
       modification_date    datetime year to second NOT NULL,
       modification_user    varchar(64) NOT NULL,
       PRIMARY KEY (project_id, user_account_id), 
       FOREIGN KEY (user_account_id)
                             REFERENCES user_account, 
       FOREIGN KEY (project_id)
                             REFERENCES project
);

CREATE TABLE project_worker (
       project_id           integer NOT NULL,
       user_account_id      integer NOT NULL,
       start_date           datetime year to second NOT NULL,
       end_date             datetime year to second NOT NULL,
       pay_rate             integer NOT NULL,
       creation_date        datetime year to second NOT NULL,
       creation_user        varchar(64) NOT NULL,
       modification_date    datetime year to second NOT NULL,
       modification_user    varchar(64) NOT NULL,
       PRIMARY KEY (project_id, user_account_id), 
       FOREIGN KEY (user_account_id)
                             REFERENCES user_account, 
       FOREIGN KEY (project_id)
                             REFERENCES project
);

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

CREATE TABLE address (
       address_id           integer NOT NULL,
       line1                varchar(100) NOT NULL,
       line2                varchar(100),
       city                 varchar(30) NOT NULL,
       state_name_id        integer NOT NULL,
       zip_code             varchar(10) NOT NULL,
       creation_date        datetime year to second NOT NULL,
       createion_user       varchar(64) NOT NULL,
       modification_date    datetime year to second NOT NULL,
       modification_user    varchar(64) NOT NULL,
       PRIMARY KEY (address_id), 
       FOREIGN KEY (state_name_id)
                             REFERENCES state_name
);

CREATE TABLE company_contact (
       company_id           integer NOT NULL,
       contact_id           integer NOT NULL,
       creation_date        datetime year to second NOT NULL,
       creation_user        varchar(64) NOT NULL,
       modifiation_date     datetime year to second NOT NULL,
       modification_user    varchar(64) NOT NULL,
       PRIMARY KEY (company_id, contact_id), 
       FOREIGN KEY (contact_id)
                             REFERENCES contact, 
       FOREIGN KEY (company_id)
                             REFERENCES company
);

CREATE TABLE user_contact (
       user_account_id      integer NOT NULL,
       contact_id           integer NOT NULL,
       creation_date        datetime year to second NOT NULL,
       creation_user        varchar(64) NOT NULL,
       modification_date    datetime year to second NOT NULL,
       modification_user    varchar(64) NOT NULL,
       PRIMARY KEY (user_account_id, contact_id), 
       FOREIGN KEY (contact_id)
                             REFERENCES contact, 
       FOREIGN KEY (user_account_id)
                             REFERENCES user_account
);

CREATE TABLE user_address (
       user_account_id      integer NOT NULL,
       address_id           integer NOT NULL,
       creation_date        datetime year to second NOT NULL,
       creation_user        varchar(64) NOT NULL,
       modification_date    datetime year to second NOT NULL,
       modification_user    varchar(64) NOT NULL,
       PRIMARY KEY (user_account_id, address_id), 
       FOREIGN KEY (address_id)
                             REFERENCES address, 
       FOREIGN KEY (user_account_id)
                             REFERENCES user_account
);

CREATE TABLE company_address (
       company_id           integer NOT NULL,
       address_id           integer NOT NULL,
       creation_date        datetime year to second NOT NULL,
       creation_user        varchar(64) NOT NULL,
       modification_date    datetime year to second NOT NULL,
       modification_user    varchar(64) NOT NULL,
       PRIMARY KEY (company_id, address_id), 
       FOREIGN KEY (address_id)
                             REFERENCES address, 
       FOREIGN KEY (company_id)
                             REFERENCES company
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

CREATE TABLE time_reject_reason (
       time_entry_id        integer NOT NULL,
       reject_reason_id     integer NOT NULL,
       creation_date        datetime year to second NOT NULL,
       creation_user        varchar(64) NOT NULL,
       modification_date    datetime year to second NOT NULL,
       modification_user    varchar(64) NOT NULL,
       PRIMARY KEY (time_entry_id, reject_reason_id), 
       FOREIGN KEY (reject_reason_id)
                             REFERENCES reject_reason, 
       FOREIGN KEY (time_entry_id)
                             REFERENCES time_entry
);

CREATE TABLE client (
       client_id            integer NOT NULL,
       name                 varchar(64) NOT NULL,
       company_id           integer NOT NULL,
       creation_date        datetime year to second NOT NULL,
       creation_user        varchar(64) NOT NULL,
       modification_date    datetime year to second NOT NULL,
       modification_user    varchar(64) NOT NULL,
       PRIMARY KEY (client_id), 
       FOREIGN KEY (company_id)
                             REFERENCES company
);

CREATE TABLE client_project (
       client_id            integer NOT NULL,
       project_id           integer NOT NULL,
       creation_date        datetime year to second NOT NULL,
       creation_user        varchar(64) NOT NULL,
       modification_date    datetime year to second NOT NULL,
       modification_user    varchar(64) NOT NULL,
       PRIMARY KEY (client_id, project_id), 
       FOREIGN KEY (project_id)
                             REFERENCES project, 
       FOREIGN KEY (client_id)
                             REFERENCES client
);


drop table client_project;
drop table client;
drop table time_reject_reason;
drop table exp_reject_reason;
drop table company_address;
drop table user_address;
drop table user_contact;
drop table company_contact;
drop table address;
drop table state_name;
drop table contact;
drop table project_worker;
drop table project_manager;
drop table user_account;
drop table account_status;
drop table comp_task_type;
drop table comp_exp_type;
drop table comp_rej_reason;
drop table reject_reason;
drop table project_time;
drop table time_entry;
drop table task_type;
drop table time_status;
drop table project_expense;
drop table comp_reject_email;
drop table reject_email;
drop table company;
drop table expense_entry;
drop table expense_status;
drop table expense_type;
drop table project;