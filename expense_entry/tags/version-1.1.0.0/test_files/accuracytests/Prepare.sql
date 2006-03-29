CREATE TABLE ExpenseEntries (
       ExpenseEntriesID     integer NOT NULL,
       ExpenseTypesID       integer NOT NULL,
       ExpenseStatusesID    integer NOT NULL,
       Description          varchar(64) NOT NULL,
       EntryDate            datetime year to second NOT NULL,
       Amount               money NOT NULL,
       Billable             smallint NOT NULL,
       CreationDate         datetime year to second NOT NULL,
       CreationUser         varchar(64) NOT NULL,
       ModificationDate     datetime year to second NOT NULL,
       ModificationUser     varchar(64) NOT NULL,
       PRIMARY KEY (ExpenseEntriesID)
);


CREATE TABLE ExpenseStatuses (
       ExpenseStatusesID    integer NOT NULL,
       Description          varchar(20) NOT NULL,
       CreationDate         datetime year to second NOT NULL,
       CreationUser         varchar(64) NOT NULL,
       ModificationDate     datetime year to second NOT NULL,
       ModificationUser     varchar(64) NOT NULL,
       PRIMARY KEY (ExpenseStatusesID)
);


CREATE TABLE ExpenseTypes (
       ExpenseTypesID       integer NOT NULL,
       Description          varchar(64) NOT NULL,
       CreationDate         datetime year to second NOT NULL,
       CreationUser         varchar(64) NOT NULL,
       ModificationDate     datetime year to second NOT NULL,
       ModificationUser     varchar(64) NOT NULL,
       PRIMARY KEY (ExpenseTypesID)
);

CREATE TABLE reject_reason (
       reject_reason_id          integer NOT NULL,
       description          varchar(255) NOT NULL,
       creation_date         datetime year to second NOT NULL,
       creation_user         varchar(64) NOT NULL,
       modification_date     datetime year to second NOT NULL,
       modification_user     varchar(64) NOT NULL,
       PRIMARY KEY (reject_reason_id)
);

CREATE TABLE exp_reject_reason (
       ExpenseEntriesID          integer NOT NULL,
       reject_reason_id          integer NOT NULL,
       creation_date         datetime year to second NOT NULL,
       creation_user         varchar(64) NOT NULL,
       modification_date     datetime year to second NOT NULL,
       modification_user     varchar(64) NOT NULL,
       PRIMARY KEY (ExpenseEntriesID, reject_reason_id)
);


ALTER TABLE ExpenseEntries
       ADD CONSTRAINT FOREIGN KEY (ExpenseStatusesID)
                             REFERENCES ExpenseStatuses;


ALTER TABLE ExpenseEntries
       ADD CONSTRAINT FOREIGN KEY (ExpenseTypesID)
                             REFERENCES ExpenseTypes;

ALTER TABLE exp_reject_reason
       ADD CONSTRAINT FOREIGN KEY (ExpenseEntriesID)
                             REFERENCES ExpenseEntries;

ALTER TABLE exp_reject_reason
       ADD CONSTRAINT FOREIGN KEY (reject_reason_id)
                             REFERENCES reject_reason;


