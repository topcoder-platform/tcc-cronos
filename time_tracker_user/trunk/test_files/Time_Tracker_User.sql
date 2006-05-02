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
       Email                varchar(255),
       CreationDate         datetime year to second,
       CreationUser         varchar(64),
       ModificationDate     datetime year to second,
       ModificationUser     varchar(64),
       UserStore            varchar(255),
       PRIMARY KEY (UsersID)
);



CREATE TABLE id_sequences (
	name			VARCHAR(255) NOT NULL,
	next_block_start	INT8 NOT NULL,
	block_size		INT NOT NULL,
	exhausted		INT default 0,
	PRIMARY KEY (name)
);

create table principal(
           principal_id INT8 not null primary key,
		   principal_name varchar(255));


create table role(
           role_id INT8 not null primary key,
		   role_name varchar(255));


create table principal_role(
           principal_id INT8 not null,
		   role_id INT8 not null,
		   primary key(principal_id, role_id),
		   foreign key (principal_id) references principal(principal_id),
		   foreign key (role_id) references role(role_id));


create table action(
           action_id INT8 not null primary key,
           class_name VARCHAR(255) not null,
		   action_name varchar(255)
);


create table action_context(
           action_context_id INT8 not null primary key,
		   action_context_name varchar(255),
		   action_context_parent_id INT8 references action_context(action_context_id),
           class_name VARCHAR(255) not null);



create table role_action_context_permission(
           role_id INT8 not null,
		   action_id INT8 not null,
		   action_context_id INT8 not null,
                   permission INT8 not null,
		   primary key(role_id, action_id, action_context_id),
                   foreign key (role_id) references role(role_id),
                   foreign key (action_id) references action(action_id),
		   foreign key (action_context_id) references action_context(action_context_id));


create table principal_action_context_permission(
                   principal_id INT8 not null,
		   action_id INT8 not null,
		   action_context_id INT8 not null,
                   permission INT8 not null,
		   primary key(principal_id, action_id, action_context_id),
                   foreign key (principal_id) references principal(principal_id),
                   foreign key (action_id) references action(action_id),
		   foreign key (action_context_id) references action_context(action_context_id));

INSERT INTO role(role_id, role_name) VALUES(1, 'Super Administrator');
INSERT INTO role(role_id, role_name) VALUES(2, 'Human Resource');
INSERT INTO role(role_id, role_name) VALUES(3, 'Account Manager');
INSERT INTO role(role_id, role_name) VALUES(4, 'Project Manager');
INSERT INTO role(role_id, role_name) VALUES(5, 'Employee');
INSERT INTO role(role_id, role_name) VALUES(6, 'Contractor');

INSERT INTO id_sequences (name, next_block_start, block_size, exhausted)
VALUES ('TimeTrackerUser', 1, 10000000, 0);
