CREATE TABLE company (
    company_id INT8 NOT NULL,
    name VARCHAR(20),
    passcode VARCHAR(20) NOT NULL,
    creation_date DATE NOT NULL,
    creation_user VARCHAR(20) NOT NULL,
    modification_date DATE NOT NULL,
    modification_user VARCHAR(20) NOT NULL,
    PRIMARY KEY(company_id)
);
INSERT INTO company (company_id, name, passcode, creation_date, creation_user, modification_date, modification_user) VALUES (1, 'company1', '11111', '2007-03-27', 'creation', '2007-03-27', 'modification');
INSERT INTO company (company_id, name, passcode, creation_date, creation_user, modification_date, modification_user) VALUES (2, 'company2', '22222', '2007-03-27', 'creation', '2007-03-27', 'modification');
INSERT INTO company (company_id, name, passcode, creation_date, creation_user, modification_date, modification_user) VALUES (3, 'company3', '33333', '2007-03-27', 'creation', '2007-03-27', 'modification');
INSERT INTO company (company_id, name, passcode, creation_date, creation_user, modification_date, modification_user) VALUES (4, 'company4', '44444', '2007-03-27', 'creation', '2007-03-27', 'modification');
INSERT INTO company (company_id, name, passcode, creation_date, creation_user, modification_date, modification_user) VALUES (5, 'company5', '55555', '2007-03-27', 'creation', '2007-03-27', 'modification');

CREATE TABLE reject_email (
    reject_email_id INT8 NOT NULL,
    body VARCHAR(20) NOT NULL,
    creation_date DATE NOT NULL,
    creation_user VARCHAR(20) NOT NULL,
    modification_date DATE NOT NULL,
    modification_user VARCHAR(20) NOT NULL,
    PRIMARY KEY(reject_email_id)
);

CREATE TABLE comp_rej_email (
    company_id INT8 NOT NULL,
    reject_email_id INT8 NOT NULL,
    creation_date DATE NOT NULL,
    creation_user VARCHAR(20) NOT NULL,
    modification_date DATE NOT NULL,
    modification_user VARCHAR(20) NOT NULL,
    PRIMARY KEY(company_id, reject_email_id),
    FOREIGN KEY(company_id) REFERENCES company(company_id),
    FOREIGN KEY(reject_email_id) REFERENCES reject_email(reject_email_id)
);

CREATE TABLE reject_reason (
    reject_reason_id INT8 NOT NULL,
    description VARCHAR(20) NOT NULL,
    active SMALLINT NOT NULL,
    creation_date DATE NOT NULL,
    creation_user VARCHAR(20) NOT NULL,
    modification_date DATE NOT NULL,
    modification_user VARCHAR(20) NOT NULL,
    PRIMARY KEY(reject_reason_id)
);

CREATE TABLE comp_rej_reason (
    company_id INT8 NOT NULL,
    reject_reason_id INT8 NOT NULL,
    creation_date DATE NOT NULL,
    creation_user VARCHAR(20) NOT NULL,
    modification_date DATE NOT NULL,
    modification_user VARCHAR(20) NOT NULL,
    PRIMARY KEY(company_id, reject_reason_id),
    FOREIGN KEY(company_id) REFERENCES company(company_id),
    FOREIGN KEY(reject_reason_id) REFERENCES reject_reason(reject_reason_id)
);

CREATE TABLE id_sequences (
    name VARCHAR(255) NOT NULL,
    next_block_start INT8 NOT NULL,
    block_size INT NOT NULL,
    max_id INT8 NOT NULL,
    exhausted SMALLINT NOT NULL,
    PRIMARY KEY(name)
);
INSERT INTO id_sequences (name, next_block_start, block_size, max_id, exhausted) VALUES ('reject_reason_sequence', 1, 20, 1000, 0);
