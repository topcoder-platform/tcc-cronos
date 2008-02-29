-- SQL file for Informix database

/* This is base table to save documnets. Each document contain id, create date and description. */
CREATE TABLE document (
document_id INTEGER NOT NULL, -- auto generated primary key
create_date DATETIME YEAR TO FRACTION(3) NOT NULL,
description LVARCHAR(1000) NULL,
PRIMARY KEY(document_id)
);

/* This table used to save document version. Primary key is unique auto generated version_id. Document id and version together is potential primary key. 
Tables saves also date of version, id of document name for this version and its content*/
CREATE TABLE document_version (
document_version_id INTEGER NOT NULL, -- auto generated primary key
-- combination of document id and version is potential primary key
document_id INTEGER NOT NULL,
version INTEGER NOT NULL,
document_name_id INTEGER NOT NULL, -- foreign key to name
version_date DATETIME YEAR TO FRACTION(3) NOT NULL,
content TEXT NOT NULL, -- this type should be used for content of any size
PRIMARY KEY(document_version_id)
FOREIGN KEY(document_id) -- foreign key to document table
REFERENCES document(document_id)
FOREIGN KEY(document_name_id) -- foreign key to document_name table
REFERENCES document_name(document_name_id)
);

/* This table is used to save name of document. Ususally document name will not be changed from version to version, but it such case is possible.
*/
CREATE TABLE document_name (
document_name_id INTEGER NOT NULL, -- auto generated primary key
name VARCHAR(254) NOT NULL, -- name of document
PRIMARY KEY(document_name_id)
);

/*This tables is used to relate document version, competition and role. It defines which version of document should be signed by user in specific role. 
*/
CREATE TABLE competition_document (
competition_document_id INTEGER NOT NULL, -- auto generated primary key
-- combination of 3 next fields is potential primary key
document_version_id INTEGER NOT NULL,
competition_id INTEGER NOT NULL,
role_id INTEGER NOT NULL,
PRIMARY KEY(competition_document_id),
FOREIGN KEY(document_version_id) -- foreign key to document_version table
REFERENCES document_version(document_version_id)
);

/*This table is used to save member answer on specific document in specific competition for specific role. 
It saves competition document id, member id e.g. user id, answer which can be true or false e.g.
document is accepted or rejected, date of answer.
*/
CREATE TABLE member_answer (
member_answer_id INTEGER NOT NULL,
--combination of competition_document_id and member_id is potential primary key as user can answer for the same competition document only once
competition_document_id INTEGER NOT NULL,
member_id INTEGER NOT NULL,
answer BOOLEAN NOT NULL,
answer_date DATETIME YEAR TO FRACTION(3) NOT NULL,
PRIMARY KEY(member_answer_id),
FOREIGN KEY(competition_document_id) -- foreign key to competition document table
REFERENCES competition_document(competition_document_id)
);