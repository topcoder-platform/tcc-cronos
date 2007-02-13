
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



