-- Tables required by the Authorization component

CREATE TABLE principal(
           principal_id integer NOT NULL, 
           principal_name varchar(255),
           PRIMARY KEY (principal_id));


CREATE TABLE role(
           role_id integer NOT NULL,
           role_name varchar(255),
           PRIMARY KEY (role_id));


CREATE TABLE principal_role(
           principal_id integer NOT NULL,
           role_id integer NOT NULL,
           PRIMARY KEY (principal_id, role_id), 
           FOREIGN KEY (principal_id) REFERENCES principal(principal_id), 
           FOREIGN KEY (role_id) REFERENCES role(role_id));


CREATE TABLE action(
           action_id integer NOT NULL, 
           class_name varchar(255) NOT NULL, 
           action_name varchar(255),
           PRIMARY KEY (action_id));


CREATE TABLE action_context(
           action_context_id integer NOT NULL PRIMARY KEY,
           action_context_name varchar(255),
           action_context_parent_id integer REFERENCES action_context(action_context_id),
           class_name varchar(255) NOT NULL);


CREATE TABLE role_action_context_permission(
           role_id integer NOT NULL,
           action_id integer NOT NULL,
           action_context_id integer NOT NULL,
           permission integer NOT NULL,
           PRIMARY KEY (role_id, action_id, action_context_id),
           FOREIGN KEY (role_id) REFERENCES role(role_id),
           FOREIGN KEY (action_id) REFERENCES action(action_id),
           FOREIGN KEY (action_context_id) REFERENCES action_context(action_context_id));


CREATE TABLE principal_action_context_permission(
           principal_id integer NOT NULL,
           action_id integer NOT NULL,
           action_context_id integer NOT NULL,
           permission integer NOT NULL,
           PRIMARY KEY (principal_id, action_id, action_context_id),
           FOREIGN KEY (principal_id) REFERENCES principal(principal_id),
           FOREIGN KEY (action_id) REFERENCES action(action_id),
           FOREIGN KEY (action_context_id) REFERENCES action_context(action_context_id));

