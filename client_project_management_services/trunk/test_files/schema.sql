CREATE TABLE informix.id_sequences ( 
    name            	VARCHAR(255) NOT NULL,
    next_block_start	INT8 NOT NULL,
    block_size      	INTEGER NOT NULL,
    exhausted       	INTEGER DEFAULT 0 NOT NULL,
    PRIMARY KEY(name)
);

CREATE TABLE company (
id serial NOT NULL,
name VARCHAR(64),
passcode VARCHAR(64),
creation_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
creation_user VARCHAR(64),
modification_user VARCHAR(64),
modification_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
is_deleted SMALLINT,
PRIMARY KEY(id)
);

CREATE TABLE project_status (
id serial NOT NULL,
description VARCHAR(255),
name VARCHAR(64),
creation_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
creation_user VARCHAR(64),
modification_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
modification_user VARCHAR(64),
is_deleted SMALLINT,
PRIMARY KEY(id)
);

CREATE TABLE client_status (
id serial NOT NULL,
description VARCHAR(255),
name VARCHAR(64),
creation_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
creation_user VARCHAR(64),
modification_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
modification_user VARCHAR(64),
is_deleted SMALLINT,
PRIMARY KEY(id)
);

CREATE TABLE client (
id serial NOT NULL,
client_status_id INT NOT NULL,
name VARCHAR(64),
company_id INT,
payment_term_id INT,
salestax DECIMAL(7,3),
start_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
end_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
creation_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
creation_user VARCHAR(64),
modification_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
modification_user VARCHAR(64),
code_name VARCHAR(64),
is_deleted SMALLINT,
PRIMARY KEY(id)
);

CREATE TABLE project (
id serial NOT NULL,
project_status_id INT NOT NULL,
client_id INT NOT NULL,
company_id INT,
name VARCHAR(64),
active SMALLINT,
sales_tax DECIMAL(8,3),
po_box_number VARCHAR(20),
payment_terms_id INT,
description VARCHAR(255),
start_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
end_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
creation_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
creation_user VARCHAR(64),
modification_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
modification_user VARCHAR(64),
is_deleted SMALLINT,
parent_project_id INT,
PRIMARY KEY(id)
);

CREATE TABLE client_user_xref (
id serial NOT NULL,
client_id INTEGER NOT NULL,
user_id INT8 NOT NULL,
name VARCHAR(64),
start_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
end_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
creation_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
creation_user VARCHAR(64),
modification_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
modification_user VARCHAR(64),
is_deleted SMALLINT,
PRIMARY KEY(id)
);

CREATE TABLE project_user_xref (
id serial NOT NULL,
project_id INTEGER NOT NULL,
user_id INT8 NOT NULL,
name VARCHAR(64),
start_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
end_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
creation_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
creation_user VARCHAR(64),
modification_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
modification_user VARCHAR(64),
is_deleted SMALLINT,
PRIMARY KEY(id)
);

alter table client add constraint FOREIGN KEY(company_id)
    REFERENCES company(id)
constraint client_project_FKIndex1;

alter table client add constraint FOREIGN KEY(client_status_id)
    REFERENCES client_status(id)
constraint client_project_FKIndex2;

alter table project add constraint FOREIGN KEY(company_id)
    REFERENCES company(id)
constraint client_project_FKIndex3;

alter table project add constraint FOREIGN KEY(client_id)
    REFERENCES client(id)
constraint client_project_FKIndex4;

alter table project add constraint FOREIGN KEY(project_status_id)
    REFERENCES project_status(id)
constraint client_project_FKIndex5;

-- Please fix DAO Client Manager before uncomment below script.
-- If perform project.setParentProjectId(0), then you will get failure againts below constraint.
-- But if you leave parentProjectId as null, you will get null pointer exception.
--alter table project add constraint FOREIGN KEY(parent_project_id)
--    REFERENCES project(id)
--constraint client_project_FKIndex6;

alter table client_user_xref add constraint FOREIGN KEY(client_id)
    REFERENCES client(id)
constraint client_user_xref_FKIndex7;

alter table project_user_xref add constraint FOREIGN KEY(project_id)
    REFERENCES project(id)
constraint project_user_xref_FKIndex8;

INSERT INTO informix.id_sequences(name, next_block_start, block_size, exhausted) 
    VALUES('com.topcoder.clients.manager.dao.DAOClientManager', 1, 10, 0);
INSERT INTO informix.id_sequences(name, next_block_start, block_size, exhausted) 
    VALUES('com.topcoder.clients.manager.dao.DAOCompanyManager', 1, 10, 0);
INSERT INTO informix.id_sequences(name, next_block_start, block_size, exhausted) 
    VALUES('com.topcoder.clients.manager.dao.DAOProjectManager', 1, 10, 0);