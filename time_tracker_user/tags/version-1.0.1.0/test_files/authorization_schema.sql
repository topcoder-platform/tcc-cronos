-- note, for principal, role, action, context, null name is allowed.
drop table principal_action_context_permission;
drop table role_action_context_permission;
drop table action_context;
drop table action;
drop table principal_role;

drop table role;
drop table principal;


create table principal(
           principal_id integer not null primary key, 
		   principal_name varchar(255));


create table role(
           role_id integer not null primary key,
		   role_name varchar(255));


create table principal_role(
           principal_id integer not null,
		   role_id integer not null,
		   primary key(principal_id, role_id), 
		   foreign key (principal_id) references principal(principal_id), 
		   foreign key (role_id) references role(role_id));


create table action(
           action_id integer not null primary key, 
           class_name VARCHAR(255) not null, 
		   action_name varchar(255)
);


create table action_context(
           action_context_id integer not null primary key, 
		   action_context_name varchar(255),
		   action_context_parent_id integer references action_context(action_context_id),
           class_name VARCHAR(255) not null);



create table role_action_context_permission(
           role_id integer not null,
		   action_id integer not null,
		   action_context_id integer not null,
           permission integer not null,
		   primary key(role_id, action_id, action_context_id),
           foreign key (role_id) references role(role_id),
           foreign key (action_id) references action(action_id),
		   foreign key (action_context_id) references action_context(action_context_id));


create table principal_action_context_permission(
           principal_id integer not null,
		   action_id integer not null,
		   action_context_id integer not null,
           permission integer not null,
		   primary key(principal_id, action_id, action_context_id),
           foreign key (principal_id) references principal(principal_id),
           foreign key (action_id) references action(action_id),
		   foreign key (action_context_id) references action_context(action_context_id));

-- define required roles for Time Tracker User component.  
insert into role (role_id, role_name) values (1, 'Super Administrator');

insert into role (role_id, role_name) values (2, 'Human Resource');

insert into role (role_id, role_name) values (3, 'Account Manager');

insert into role (role_id, role_name) values (4, 'Project Manager');

insert into role (role_id, role_name) values (5, 'Employee');

insert into role (role_id, role_name) values (6, 'Contractor');
