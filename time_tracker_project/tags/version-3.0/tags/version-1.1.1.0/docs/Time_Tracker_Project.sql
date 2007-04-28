
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


CREATE TABLE TimeEntries (
       TimeEntriesID        integer NOT NULL,
       PRIMARY KEY (TimeEntriesID)
);


CREATE TABLE Users (
       UsersID              integer NOT NULL,
       PRIMARY KEY (UsersID)
);


ALTER TABLE ClientProjects
       ADD CONSTRAINT FOREIGN KEY (ProjectsID)
                             REFERENCES Projects;


ALTER TABLE ClientProjects
       ADD CONSTRAINT FOREIGN KEY (ClientsID)
                             REFERENCES Clients;


ALTER TABLE ProjectExpenses
       ADD CONSTRAINT FOREIGN KEY (ExpenseEntriesID)
                             REFERENCES ExpenseEntries;


ALTER TABLE ProjectExpenses
       ADD CONSTRAINT FOREIGN KEY (ProjectsID)
                             REFERENCES Projects;


ALTER TABLE ProjectManagers
       ADD CONSTRAINT FOREIGN KEY (UsersID)
                             REFERENCES Users;


ALTER TABLE ProjectManagers
       ADD CONSTRAINT FOREIGN KEY (ProjectsID)
                             REFERENCES Projects;


ALTER TABLE ProjectTimes
       ADD CONSTRAINT FOREIGN KEY (TimeEntriesID)
                             REFERENCES TimeEntries;


ALTER TABLE ProjectTimes
       ADD CONSTRAINT FOREIGN KEY (ProjectsID)
                             REFERENCES Projects;


ALTER TABLE ProjectWorkers
       ADD CONSTRAINT FOREIGN KEY (UsersID)
                             REFERENCES Users;


ALTER TABLE ProjectWorkers
       ADD CONSTRAINT FOREIGN KEY (ProjectsID)
                             REFERENCES Projects;



