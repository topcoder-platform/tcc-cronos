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

CREATE TABLE reject_reason (
       reject_reason_id          integer NOT NULL,
       description          varchar(255) NOT NULL,
       creation_date         datetime year to second NOT NULL,
       creation_user         varchar(64) NOT NULL,
       modification_date     datetime year to second NOT NULL,
       modification_user     varchar(64) NOT NULL,
       PRIMARY KEY (reject_reason_id)
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


ALTER TABLE TimeEntries ADD CONSTRAINT FOREIGN KEY (TimeStatusesID) REFERENCES TimeStatuses;
ALTER TABLE TimeEntries ADD CONSTRAINT FOREIGN KEY (TaskTypesID) REFERENCES TaskTypes;
ALTER TABLE time_reject_reason ADD CONSTRAINT FOREIGN KEY (TimeEntriesID)  REFERENCES TimeEntries;
ALTER TABLE time_reject_reason ADD CONSTRAINT FOREIGN KEY (reject_reason_id) REFERENCES reject_reason;

CREATE TABLE ClientProjects (
       ClientsID            integer NOT NULL,
       ProjectsID           integer NOT NULL,
       CreationDate         datetime year to second NOT NULL,
       CreationUser         varchar(64) NOT NULL,
       ModificationDate     datetime year to second NOT NULL,
       ModificationUser     varchar(64) NOT NULL,
       PRIMARY KEY (ClientsID, ProjectsID)
);


CREATE TABLE Clients (
       ClientsID            integer NOT NULL,
       Name                 varchar(64) NOT NULL,
       CreationDate         datetime year to second NOT NULL,
       Creationuser         varchar(64) NOT NULL,
       ModificationDate     datetime year to second NOT NULL,
       ModificationUser     varchar(64) NOT NULL,
       PRIMARY KEY (ClientsID)
);


CREATE TABLE ExpenseEntries (
       ExpenseEntriesID     integer NOT NULL,
       PRIMARY KEY (ExpenseEntriesID)
);


CREATE TABLE ProjectExpenses (
       ProjectsID           integer NOT NULL,
       ExpenseEntriesID     integer NOT NULL,
       CreationDate         datetime year to second NOT NULL,
       CreationUser         varchar(64) NOT NULL,
       ModificationDate     datetime year to second NOT NULL,
       ModificationUser     varchar(64) NOT NULL,
       PRIMARY KEY (ProjectsID, ExpenseEntriesID)
);


CREATE TABLE ProjectManagers (
       ProjectsID           integer NOT NULL,
       UsersID              integer NOT NULL,
       CreationDate         datetime year to second NOT NULL,
       CreationUser         varchar(64) NOT NULL,
       ModificationDate     datetime year to second NOT NULL,
       ModificationUser     varchar(64) NOT NULL,
       PRIMARY KEY (ProjectsID, UsersID)
);


CREATE TABLE Projects (
       ProjectsID           integer NOT NULL,
       Name                 varchar(64) NOT NULL,
       Description          varchar(64) NOT NULL,
       StartDate            datetime year to second NOT NULL,
       EndDate              datetime year to second NOT NULL,
       CreationDate         datetime year to second NOT NULL,
       CreationUser         varchar(64) NOT NULL,
       ModificationDate     datetime year to second NOT NULL,
       ModificationUser     varchar(64) NOT NULL,
       PRIMARY KEY (ProjectsID)
);


CREATE TABLE ProjectTimes (
       ProjectsID           integer NOT NULL,
       TimeEntriesID        integer NOT NULL,
       CreationDate         datetime year to second NOT NULL,
       CreationUser         varchar(64) NOT NULL,
       ModificationDate     datetime year to second NOT NULL,
       ModificationUser     varchar(64) NOT NULL,
       PRIMARY KEY (ProjectsID, TimeEntriesID)
);


CREATE TABLE ProjectWorkers (
       ProjectsID           integer NOT NULL,
       UsersID              integer NOT NULL,
       StartDate            datetime year to second NOT NULL,
       EndDate              datetime year to second NOT NULL,
       PayRate              money NOT NULL,
       CreationDate         datetime year to second NOT NULL,
       CreationUser         varchar(64) NOT NULL,
       ModificationDate     datetime year to second NOT NULL,
       ModificationUser     varchar(64) NOT NULL,
       PRIMARY KEY (ProjectsID, UsersID)
);


CREATE TABLE Users (
       UsersID              integer NOT NULL,
       PRIMARY KEY (UsersID)
);

ALTER TABLE ClientProjects ADD CONSTRAINT FOREIGN KEY (ProjectsID) REFERENCES Projects;
ALTER TABLE ClientProjects ADD CONSTRAINT FOREIGN KEY (ClientsID) REFERENCES Clients;
ALTER TABLE ProjectExpenses ADD CONSTRAINT FOREIGN KEY (ExpenseEntriesID) REFERENCES ExpenseEntries;
ALTER TABLE ProjectExpenses ADD CONSTRAINT FOREIGN KEY (ProjectsID) REFERENCES Projects;
ALTER TABLE ProjectManagers ADD CONSTRAINT FOREIGN KEY (UsersID) REFERENCES Users;
ALTER TABLE ProjectManagers ADD CONSTRAINT FOREIGN KEY (ProjectsID) REFERENCES Projects;
ALTER TABLE ProjectTimes ADD CONSTRAINT FOREIGN KEY (TimeEntriesID) REFERENCES TimeEntries;
ALTER TABLE ProjectTimes ADD CONSTRAINT FOREIGN KEY (ProjectsID) REFERENCES Projects;
ALTER TABLE ProjectWorkers ADD CONSTRAINT FOREIGN KEY (UsersID) REFERENCES Users;
ALTER TABLE ProjectWorkers ADD CONSTRAINT FOREIGN KEY (ProjectsID) REFERENCES Projects;





--------------Test data for Time Tracker Project ----

INSERT INTO Users VALUES (0);
INSERT INTO Users VALUES (1);
INSERT INTO Users VALUES (2);
INSERT INTO Users VALUES (3);
INSERT INTO Users VALUES (300);
INSERT INTO Users VALUES (400);
INSERT INTO Users VALUES (401);
INSERT INTO Users VALUES (402);

Insert into tasktypes(TaskTypesID, Description, CreationDate, CreationUser, ModificationDate, ModificationUser)
values(1, "1", today, "tcs", today, "tcs");
Insert into tasktypes(TaskTypesID, Description, CreationDate, CreationUser, ModificationDate, ModificationUser)
values(2, "2", today, "tcs", today, "tcs");

insert into TimeStatuses(TimeStatusesID, Description, CreationDate, CreationUser, ModificationDate, ModificationUser)
values(1, "1", today, "tcs", today, "tcs");
insert into TimeStatuses(TimeStatusesID, Description, CreationDate, CreationUser, ModificationDate, ModificationUser)
values(2, "2", today, "tcs", today, "tcs");

INSERT INTO TimeEntries(TimeEntriesID, TaskTypesID, TimeStatusesID, Description, EntryDate, Hours, Billable, CreationDate, CreationUser, ModificationDate, ModificationUser) 
VALUES (0, 1, 1, "desc", today, 1, 1, today, "user", today, "modifier");
INSERT INTO TimeEntries(TimeEntriesID, TaskTypesID, TimeStatusesID, Description, EntryDate, Hours, Billable, CreationDate, CreationUser, ModificationDate, ModificationUser) 
VALUES (1, 1, 1, "desc", today, 1, 1, today, "user", today, "modifier");
INSERT INTO TimeEntries(TimeEntriesID, TaskTypesID, TimeStatusesID, Description, EntryDate, Hours, Billable, CreationDate, CreationUser, ModificationDate, ModificationUser) 
VALUES (2, 1, 1, "desc", today, 1, 1, today, "user", today, "modifier");
INSERT INTO TimeEntries(TimeEntriesID, TaskTypesID, TimeStatusesID, Description, EntryDate, Hours, Billable, CreationDate, CreationUser, ModificationDate, ModificationUser) 
VALUES (3, 1, 1, "desc", today, 1, 1, today, "user", today, "modifier");
INSERT INTO TimeEntries(TimeEntriesID, TaskTypesID, TimeStatusesID, Description, EntryDate, Hours, Billable, CreationDate, CreationUser, ModificationDate, ModificationUser) 
VALUES (500, 1, 1, "desc", today, 1, 1, today, "user", today, "modifier");
INSERT INTO TimeEntries(TimeEntriesID, TaskTypesID, TimeStatusesID, Description, EntryDate, Hours, Billable, CreationDate, CreationUser, ModificationDate, ModificationUser) 
VALUES (501, 1, 1, "desc", today, 1, 1, today, "user", today, "modifier");
INSERT INTO TimeEntries(TimeEntriesID, TaskTypesID, TimeStatusesID, Description, EntryDate, Hours, Billable, CreationDate, CreationUser, ModificationDate, ModificationUser) 
VALUES (502, 1, 1, "desc", today, 1, 1, today, "user", today, "modifier");

INSERT INTO ExpenseEntries VALUES (0);
INSERT INTO ExpenseEntries VALUES (1);
INSERT INTO ExpenseEntries VALUES (2);
INSERT INTO ExpenseEntries VALUES (3);
INSERT INTO ExpenseEntries VALUES (500);
INSERT INTO ExpenseEntries VALUES (501);
INSERT INTO ExpenseEntries VALUES (502);