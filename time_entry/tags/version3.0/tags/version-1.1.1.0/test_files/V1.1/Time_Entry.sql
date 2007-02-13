
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


CREATE TABLE time_reject_reason (
       TimeEntriesID          integer NOT NULL,
       reject_reason_id          integer NOT NULL,
       creation_date         datetime year to second NOT NULL,
       creation_user         varchar(64) NOT NULL,
       modification_date     datetime year to second NOT NULL,
       modification_user     varchar(64) NOT NULL,
       PRIMARY KEY (TimeEntriesID, reject_reason_id)
);

CREATE TABLE reject_reason (
       reject_reason_id          integer NOT NULL,
       description          varchar(64) NOT NULL,
       creation_date         datetime year to second NOT NULL,
       creation_user         varchar(64) NOT NULL,
       modification_date     datetime year to second NOT NULL,
       modification_user     varchar(64) NOT NULL,
       PRIMARY KEY (reject_reason_id)
);


ALTER TABLE TimeEntries
       ADD CONSTRAINT FOREIGN KEY (TimeStatusesID)
                             REFERENCES TimeStatuses;


ALTER TABLE TimeEntries
       ADD CONSTRAINT FOREIGN KEY (TaskTypesID)
                             REFERENCES TaskTypes;

ALTER TABLE time_reject_reason
       ADD CONSTRAINT FOREIGN KEY (TimeEntriesID)
                             REFERENCES TimeEntries;

ALTER TABLE time_reject_reason
       ADD CONSTRAINT FOREIGN KEY (reject_reason_id)
                             REFERENCES reject_reason;
        
CREATE TABLE id_sequences (
	name			VARCHAR(255) NOT NULL,
				PRIMARY KEY (name),
	next_block_start	DEC(18) NOT NULL,
	block_size		INT NOT NULL,
	exhausted		DEC(1) default 0
);                     
INSERT INTO id_sequences (name, next_block_start, block_size) VALUES ("com.cronos.timetracker.entry.time.RejectReason", 1, 20);
INSERT INTO id_sequences (name, next_block_start, block_size) VALUES ("com.cronos.timetracker.entry.time.TimeEntry", 1, 20);

INSERT INTO id_sequences (name, next_block_start, block_size) VALUES ('com.cronos.timetracker.entry.time.TimeStatus', 1, 20);
INSERT INTO id_sequences (name, next_block_start, block_size) VALUES ('com.cronos.timetracker.entry.time.TaskType', 10000, 20);
