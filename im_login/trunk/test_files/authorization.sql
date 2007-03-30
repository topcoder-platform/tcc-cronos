create table principal(
           principal_id int not null primary key, 
		   principal_name varchar(255));

create table role(
           role_id int not null primary key,
		   role_name varchar(255));


create table principal_role(
           principal_id int not null,
		   role_id int not null,
		   primary key(principal_id, role_id), 
		   foreign key (principal_id) references principal(principal_id), 
		   foreign key (role_id) references role(role_id));


create table action(
           action_id int not null primary key, 
           class_name VARCHAR(255) not null, 
		   action_name varchar(255)
);

create table action_context(
           action_context_id int not null primary key, 
	   action_context_name varchar(255),
	   action_context_parent_id int references action_context(action_context_id),
           class_name VARCHAR(255) not null);

create table role_action_context_permission(
           role_id int not null,
		   action_id int not null,
		   action_context_id int not null,
                   permission int not null,
		   primary key(role_id, action_id, action_context_id),
                   foreign key (role_id) references role(role_id),
                   foreign key (action_id) references action(action_id),
		   foreign key (action_context_id) references action_context(action_context_id));


create table principal_action_context_permission(
                   principal_id int not null,
		   action_id int not null,
		   action_context_id int not null,
                   permission int not null,
		   primary key(principal_id, action_id, action_context_id),
                   foreign key (principal_id) references principal(principal_id),
                   foreign key (action_id) references action(action_id),
		   foreign key (action_context_id) references action_context(action_context_id));

insert into action_context values (1, "Sales IM", 1, "com.topcoder.security.authorization.persistence.GeneralActionContext");

insert into principal values (1, "admin");
insert into action values (1, 'com.topcoder.security.authorization.persistence.GeneralAction', 'Admin Login');
insert into principal values (4, "dummy_admin");

insert into principal values (2, "junit_user");
insert into action values (2, 'com.topcoder.security.authorization.persistence.GeneralAction', 'Client Login');

insert into principal values (3, "manager");
insert into action values (3, 'com.topcoder.security.authorization.persistence.GeneralAction', 'Manager Login');
insert into principal values (6, "dummy_manager");

insert into principal_action_context_permission values (3, 3, 1, 1);
insert into principal_action_context_permission values (6, 3, 1, 2);
insert into principal_action_context_permission values (1, 1, 1, 1);
insert into principal_action_context_permission values (4, 1, 1, 2);