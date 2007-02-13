
CREATE TABLE ExpenseEntries (
       ExpenseEntriesID     integer NOT NULL,
       ExpenseTypesID       integer NOT NULL,
       ExpenseStatusesID    integer NOT NULL,
       Description          varchar(64) NOT NULL,
       Date                 date NOT NULL,
       Amount               money NOT NULL,
       Billable             smallint NOT NULL,
       CreationDate         date NOT NULL,
       CreationUser         varchar(64) NOT NULL,
       ModificationDate     date NOT NULL,
       ModificationUser     varchar(64) NOT NULL,
       PRIMARY KEY (ExpenseEntriesID)
);


CREATE TABLE ExpenseStatuses (
       ExpenseStatusesID    integer NOT NULL,
       Description          varchar(20) NOT NULL,
       CreationDate         date NOT NULL,
       CreationUser         varchar(64) NOT NULL,
       ModificationDate     date NOT NULL,
       ModificationUser     varchar(64) NOT NULL,
       PRIMARY KEY (ExpenseStatusesID)
);


CREATE TABLE ExpenseTypes (
       ExpenseTypesID       integer NOT NULL,
       Description          varchar(64) NOT NULL,
       CreationDate         date NOT NULL,
       CreationUser         varchar(64) NOT NULL,
       ModificationDate     date NOT NULL,
       ModificationUser     varchar(64) NOT NULL,
       PRIMARY KEY (ExpenseTypesID)
);


ALTER TABLE ExpenseEntries
       ADD CONSTRAINT FOREIGN KEY (ExpenseStatusesID)
                             REFERENCES ExpenseStatuses;


ALTER TABLE ExpenseEntries
       ADD CONSTRAINT FOREIGN KEY (ExpenseTypesID)
                             REFERENCES ExpenseTypes;



