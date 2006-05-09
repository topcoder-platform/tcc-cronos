
CREATE TABLE TaskTypes (
       TaskTypesID          integer NOT NULL,
       Description          varchar(64) NOT NULL,
       CreationDate         datetime year to second NOT NULL,
       CreationUser         varchar(64) NOT NULL,
       ModificationDate     datetime year to second NOT NULL,
       ModificationUser     varchar(64) NOT NULL,
       PRIMARY KEY (TaskTypesID)
);


CREATE TABLE TimeEntries (
       TimeEntriesID        integer NOT NULL,
       TaskTypesID          integer NOT NULL,
       TimeStatusesID       integer NOT NULL,
       Description          varchar(64) NOT NULL,
       EntryDate            datetime year to second NOT NULL,
       Hours                float NOT NULL,
       Billable             smallint NOT NULL,
       CreationDate         datetime year to second NOT NULL,
       CreationUser         varchar(64) NOT NULL,
       ModificationDate     datetime year to second NOT NULL,
       ModificationUser     varchar(64) NOT NULL,
       PRIMARY KEY (TimeEntriesID)
);


CREATE TABLE TimeStatuses (
       TimeStatusesID       integer NOT NULL,
       Description          varchar(64) NOT NULL,
       CreationDate         datetime year to second NOT NULL,
       CreationUser         varchar(64) NOT NULL,
       ModificationDate     datetime year to second NOT NULL,
       ModificationUser     varchar(64) NOT NULL,
       PRIMARY KEY (TimeStatusesID)
);


ALTER TABLE TimeEntries
       ADD CONSTRAINT FOREIGN KEY (TimeStatusesID)
                             REFERENCES TimeStatuses;


ALTER TABLE TimeEntries
       ADD CONSTRAINT FOREIGN KEY (TaskTypesID)
                             REFERENCES TaskTypes;

