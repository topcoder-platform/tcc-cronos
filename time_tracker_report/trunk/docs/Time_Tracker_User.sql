
CREATE TABLE DefaultUsers (
       DefaultUsersID       integer NOT NULL,
       UserName             varchar(64) NOT NULL,
       Password             varchar(64) NOT NULL,
       FirstName            varchar(64),
       LastName             varchar(64),
       Phone                varchar(20),
       Email                varchar(64),
       CreationDate         datetime year to second NOT NULL,
       CreationUser         varchar(64) NOT NULL,
       ModificationDate     datetime year to second NOT NULL,
       ModificationUser     varchar(64) NOT NULL,
       PRIMARY KEY (DefaultUsersID)
);


CREATE TABLE Users (
       UsersID              integer NOT NULL,
       Name                 varchar(64) NOT NULL,
       CreationDate         datetime year to second,	  	-- NOT NULL flag is removed, because this field is out of scope for this component
       CreationUser         varchar(64),	  	-- NOT NULL flag is removed, because this field is out of scope for this component
       ModificationDate     datetime year to second,	  	-- NOT NULL flag is removed, because this field is out of scope for this component
       ModificationUser     varchar(64),	  	-- NOT NULL flag is removed, because this field is out of scope for this component
       UserStore            varchar(255) NOT NULL -- field added by designer to store link to original user store
       PRIMARY KEY (UsersID)
);



