
CREATE table principal(
           principal_id int not null primary key, 
		   principal_name varchar(255));

CREATE table role(
           role_id int not null primary key,
		   role_name varchar(255));


CREATE table principal_role(
           principal_id int not null,
		   role_id int not null,
		   primary key(principal_id, role_id), 
		   foreign key (principal_id) references principal(principal_id), 
		   foreign key (role_id) references role(role_id));


CREATE table action(
           action_id int not null primary key, 
           class_name VARCHAR(255) not null, 
		   action_name varchar(255)
);

CREATE table action_context(
           action_context_id int not null primary key, 
	   action_context_name varchar(255),
	   action_context_parent_id int references action_context(action_context_id),
           class_name VARCHAR(255) not null);

CREATE table role_action_context_permission(
           role_id int not null,
		   action_id int not null,
		   action_context_id int not null,
                   permission int not null,
		   primary key(role_id, action_id, action_context_id),
                   foreign key (role_id) references role(role_id),
                   foreign key (action_id) references action(action_id),
		   foreign key (action_context_id) references action_context(action_context_id));


CREATE table principal_action_context_permission(
                   principal_id int not null,
		   action_id int not null,
		   action_context_id int not null,
                   permission int not null,
		   primary key(principal_id, action_id, action_context_id),
                   foreign key (principal_id) references principal(principal_id),
                   foreign key (action_id) references action(action_id),
		   foreign key (action_context_id) references action_context(action_context_id));
		 
CREATE table category (
	category_id INTEGER NOT NULL,
	name VARCHAR(64) NOT NULL,
	description VARCHAR(255) NOT NULL,
	chattable_flag VARCHAR(1) NOT NULL,
	create_date DATETIME YEAR TO FRACTION(3) NOT NULL,
	create_user VARCHAR(64) NOT NULL,
	modify_date DATETIME YEAR TO FRACTION(3) NOT NULL,
	modify_user VARCHAR(64) NOT NULL,
	PRIMARY KEY(category_id)
);

CREATE TABLE id_sequences (
    name                VARCHAR(255) NOT NULL  PRIMARY KEY,
    next_block_start    INT8 NOT NULL,
    block_size          INT NOT NULL,
    exhausted		int default 0
);

CREATE TABLE profile (
       profile_id DEC(18) NOT NULL,
       username       VARCHAR(64) NOT NULL UNIQUE
);
ALTER TABLE profile ADD CONSTRAINT PRIMARY KEY (profile_id);

CREATE TABLE property (
       property_id         DEC(18) NOT NULL,
       name                VARCHAR(64) NOT NULL
);
ALTER TABLE property ADD CONSTRAINT PRIMARY KEY (property_id);

CREATE TABLE profile_property (
       profile_id          DEC(18) NOT NULL,
       property_id         DEC(18) NOT NULL,
       value               VARCHAR(64) NOT NULL
);
ALTER TABLE profile_property ADD CONSTRAINT FOREIGN KEY (profile_id) REFERENCES profile;
ALTER TABLE profile_property ADD CONSTRAINT FOREIGN KEY (property_id) REFERENCES property;

CREATE TABLE user_profile (
       user_profile_id         DEC(18) NOT NULL,
       user_profile_name       VARCHAR(64) NOT NULL UNIQUE
);
ALTER TABLE user_profile ADD CONSTRAINT PRIMARY KEY (user_profile_id);

CREATE TABLE user_property (
       user_property_id         DEC(18) NOT NULL,
       user_property_name                VARCHAR(64) NOT NULL
);
ALTER TABLE user_property ADD CONSTRAINT PRIMARY KEY (user_property_id);

CREATE TABLE user_profile_property (
       profile_property_profile_id          DEC(18) NOT NULL,
       profile_property_property_id         DEC(18) NOT NULL,
       user_value               VARCHAR(64) NOT NULL
);
ALTER TABLE user_profile_property ADD CONSTRAINT FOREIGN KEY (profile_property_profile_id) REFERENCES user_profile;
ALTER TABLE user_profile_property ADD CONSTRAINT FOREIGN KEY (profile_property_property_id) REFERENCES user_property;

INSERT into action_context values (1, "Sales IM", 1, "com.topcoder.security.authorization.persistence.GeneralActionContext");


INSERT into principal values (1, "Administrator");
INSERT into action values (1, 'com.topcoder.security.authorization.persistence.GeneralAction', 'Admin Login');

INSERT into principal values (2, "Manager");
INSERT into action values (2, 'com.topcoder.security.authorization.persistence.GeneralAction', 'Manager Login');

INSERT into principal values (3, "Client");
INSERT into action values (3, 'com.topcoder.security.authorization.persistence.GeneralAction', 'Client Login');

INSERT into principal values (4, "UnAuthorized_Administrator");
INSERT into principal values (5, "UnAuthorized_Manager");


INSERT into principal_action_context_permission values (1, 1, 1, 1);
INSERT into principal_action_context_permission values (2, 2, 1, 1);
INSERT into principal_action_context_permission values (4, 1, 1, 2);
INSERT into principal_action_context_permission values (5, 2, 1, 2);

INSERT INTO id_sequences (name,next_block_start,block_size,exhausted) VALUES ('com.topcoder.security.authorization.Action',0,10,0);
INSERT INTO id_sequences (name,next_block_start,block_size,exhausted) VALUES ('com.topcoder.security.authorization.ActionContext',0,10,0);
INSERT INTO id_sequences (name,next_block_start,block_size,exhausted) VALUES ('com.topcoder.security.authorization.Principal',0,10,0);
INSERT INTO id_sequences (name,next_block_start,block_size,exhausted) VALUES ('com.topcoder.security.authorization.SecurityRole',0,10,0);

INSERT into category values (1, "chattable", "chat regarding failure test cases", "Y", today, "mittu", today, "mittu");

INSERT into profile values (1, "Administrator");
INSERT into profile values (2, "Manager");
INSERT into profile values (3, "Client");
