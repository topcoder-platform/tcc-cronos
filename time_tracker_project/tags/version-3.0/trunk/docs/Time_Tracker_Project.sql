
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


CREATE TABLE expense_entry (
       expense_entry_id     integer NOT NULL,
       company_id           integer NOT NULL,
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
                             REFERENCES company
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
       creation_date        datetime year to second NOT NULL,
       creation_user        varchar(64) NOT NULL,
       modification_date    datetime year to second NOT NULL,
       modification_user    varchar(64) NOT NULL,
       expense_entry_id     integer,
       PRIMARY KEY (project_id), 
       FOREIGN KEY (expense_entry_id)
                             REFERENCES expense_entry, 
       FOREIGN KEY (project_id)
                             REFERENCES project
);


CREATE TABLE time_entry (
       time_entry_id        integer NOT NULL,
       company_id           integer NOT NULL,
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
                             REFERENCES company
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


CREATE TABLE user_account (
       user_account_id      integer NOT NULL,
       company_id           integer,
       user_name            varchar(64) NOT NULL,
       password             varchar(64) NOT NULL,
       creation_date        datetime year to second NOT NULL,
       creation_user        varchar(64) NOT NULL,
       modification_date    datetime year to second NOT NULL,
       modification_user    varchar(64) NOT NULL,
       PRIMARY KEY (user_account_id), 
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
       FOREIGN KEY (project_id)
                             REFERENCES project, 
       FOREIGN KEY (user_account_id)
                             REFERENCES user_account
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
                             REFERENCES company,
       UNIQUE (
              name
       )
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


