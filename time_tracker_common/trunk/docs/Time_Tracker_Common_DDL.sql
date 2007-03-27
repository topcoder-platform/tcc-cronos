CREATE TABLE payment_terms (
  payment_terms_id INT8 NOT NULL PRIMARY KEY,
  description VARCHAR(64) NOT NULL,
  creation_date DATETIME YEAR TO second NOT NULL,
  creation_user VARCHAR(64) NOT NULL,
  modification_date DATETIME YEAR TO second NOT NULL,
  modification_user VARCHAR(64) NOT NULL,
  active SMALLINT NOT NULL,
  term INT NOT NULL
);

CREATE TABLE id_sequences (
    name                VARCHAR(255) NOT NULL  PRIMARY KEY,
    next_block_start    INT8 NOT NULL,
    block_size          INT NOT NULL,
    exhausted		SMALLINT default 0
);