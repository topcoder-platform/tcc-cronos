DATABASE topcoder;

CREATE TABLE 'informix'.id_sequences (
  name                  VARCHAR(255)    NOT NULL,
  next_block_start      INTEGER         NOT NULL,
  block_size            INTEGER         NOT NULL,
  exhausted             INTEGER         NOT NULL,
  PRIMARY KEY (name)
);

CREATE TABLE 'informix'.submission_type_lu (
  submission_type_id            INTEGER                         NOT NULL,
  name                          VARCHAR(64)                     NOT NULL,
  description                   VARCHAR(254)                    NOT NULL,
  create_user                   VARCHAR(64)                     NOT NULL,
  create_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  modify_user                   VARCHAR(64)                     NOT NULL,
  modify_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  PRIMARY KEY(submission_type_id)
);

CREATE TABLE 'informix'.evaluation_type_lu (
    evaluation_type_id INT not null,
    name VARCHAR(64) not null,
    description VARCHAR(254) not null,
    create_user VARCHAR(64) not null,
    create_date DATETIME YEAR TO FRACTION not null,
    modify_user VARCHAR(64) not null,
    modify_date DATETIME YEAR TO FRACTION not null,
    PRIMARY KEY(evaluation_type_id)
);

ALTER TABLE review_comment ADD evaluation_type_id INT default 4 not null;
ALTER TABLE review_item_comment ADD evaluation_type_id INT default 4 not null;

ALTER TABLE project_type_lu ADD review_system_version INT default 1 not null;
ALTER TABLE project_info_type_lu ADD review_system_version INT default 1 not null;
ALTER TABLE submission ADD submission_type_id INT default 1 not null;