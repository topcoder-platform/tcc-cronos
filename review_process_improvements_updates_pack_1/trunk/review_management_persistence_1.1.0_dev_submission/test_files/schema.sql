CREATE TABLE scorecard_type_lu (
  scorecard_type_id INTEGER NOT NULL,
  PRIMARY KEY(scorecard_type_id)
);
CREATE TABLE scorecard (
  scorecard_id INTEGER NOT NULL,
  scorecard_type_id INTEGER NOT NULL,
  PRIMARY KEY(scorecard_id),
  FOREIGN KEY(scorecard_type_id)
    REFERENCES scorecard_type_lu(scorecard_type_id)
);
CREATE TABLE scorecard_question (
  scorecard_question_id INTEGER NOT NULL,
  PRIMARY KEY(scorecard_question_id)
);
CREATE TABLE resource (
  resource_id INTEGER NOT NULL,
  project_id INTEGER,
  PRIMARY KEY(resource_id)
);
CREATE TABLE upload (
  upload_id INTEGER NOT NULL,
  PRIMARY KEY(upload_id)
);
CREATE TABLE submission (
  submission_id INTEGER NOT NULL,
  PRIMARY KEY(submission_id)
);
CREATE TABLE comment_type_lu (
  comment_type_id INTEGER NOT NULL,
  name VARCHAR(64) NOT NULL,
  description VARCHAR(254) NOT NULL,
  create_user VARCHAR(64) NOT NULL,
  create_date DATETIME YEAR TO FRACTION(3) NOT NULL,
  modify_user VARCHAR(64) NOT NULL,
  modify_date DATETIME YEAR TO FRACTION(3) NOT NULL,
  PRIMARY KEY(comment_type_id)
);
CREATE TABLE review (
  review_id INTEGER NOT NULL,
  resource_id INTEGER NOT NULL,
  submission_id INTEGER NOT NULL,
  scorecard_id INTEGER NOT NULL,
  committed DECIMAL(1, 0) NOT NULL,
  score FLOAT,
  create_user VARCHAR(64) NOT NULL,
  create_date DATETIME YEAR TO FRACTION(3) NOT NULL,
  modify_user VARCHAR(64) NOT NULL,
  modify_date DATETIME YEAR TO FRACTION(3) NOT NULL,
  PRIMARY KEY(review_id),
  FOREIGN KEY(scorecard_id)
    REFERENCES scorecard(scorecard_id),
  FOREIGN KEY(submission_id)
    REFERENCES submission(submission_id),
  FOREIGN KEY(resource_id)
    REFERENCES resource(resource_id)
);
CREATE TABLE review_item (
  review_item_id INTEGER NOT NULL,
  review_id INTEGER NOT NULL,
  scorecard_question_id INTEGER NOT NULL,
  upload_id INTEGER,
  answer VARCHAR(254) NOT NULL,
  sort DECIMAL(3, 0) NOT NULL,
  create_user VARCHAR(64) NOT NULL,
  create_date DATETIME YEAR TO FRACTION(3) NOT NULL,
  modify_user VARCHAR(64) NOT NULL,
  modify_date DATETIME YEAR TO FRACTION(3) NOT NULL,
  PRIMARY KEY(review_item_id),
  FOREIGN KEY(review_id)
    REFERENCES review(review_id),
  FOREIGN KEY(scorecard_question_id)
    REFERENCES scorecard_question(scorecard_question_id),
  FOREIGN KEY(upload_id)
    REFERENCES upload(upload_id)
);
CREATE TABLE review_comment (
  review_comment_id INTEGER NOT NULL,
  resource_id INTEGER NOT NULL,
  review_id INTEGER NOT NULL,
  comment_type_id INTEGER NOT NULL,
  content LVARCHAR(4096) NOT NULL,
  extra_info VARCHAR(254),
  sort DECIMAL(3, 0) NOT NULL,
  create_user VARCHAR(64) NOT NULL,
  create_date DATETIME YEAR TO FRACTION(3) NOT NULL,
  modify_user VARCHAR(64) NOT NULL,
  modify_date DATETIME YEAR TO FRACTION(3) NOT NULL,
  PRIMARY KEY(review_comment_id),
  FOREIGN KEY(review_id)
    REFERENCES review(review_id),
  FOREIGN KEY(comment_type_id)
    REFERENCES comment_type_lu(comment_type_id),
  FOREIGN KEY(resource_id)
    REFERENCES resource(resource_id)
);
CREATE TABLE review_item_comment (
  review_item_comment_id INTEGER NOT NULL,
  resource_id INTEGER NOT NULL,
  review_item_id INTEGER NOT NULL,
  comment_type_id INTEGER NOT NULL,
  content LVARCHAR(4096) NOT NULL,
  extra_info VARCHAR(254),
  sort DECIMAL(3, 0) NOT NULL,
  create_user VARCHAR(64) NOT NULL,
  create_date DATETIME YEAR TO FRACTION(3) NOT NULL,
  modify_user VARCHAR(64) NOT NULL,
  modify_date DATETIME YEAR TO FRACTION(3) NOT NULL,
  PRIMARY KEY(review_item_comment_id),
  FOREIGN KEY(review_item_id)
    REFERENCES review_item(review_item_id),
  FOREIGN KEY(comment_type_id)
    REFERENCES comment_type_lu(comment_type_id),
  FOREIGN KEY(resource_id)
    REFERENCES resource(resource_id)
);


CREATE TABLE id_sequences (
  name VARCHAR(255) NOT NULL,
  next_block_start INT8 NOT NULL,
  block_size INTEGER NOT NULL,
  exhausted INTEGER NOT NULL,
  PRIMARY KEY (name)
);

INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)
  VALUES('review_id_seq', 1, 20, 0);
INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)
  VALUES('review_item_id_seq', 1, 20, 0);
INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)
  VALUES('review_comment_id_seq', 1, 20, 0);
INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)
  VALUES('review_item_comment_id_seq', 1, 20, 0);


CREATE TABLE evaluation_type_lu (
    evaluation_type_id INTEGER NOT NULL,
    name VARCHAR(64) NOT NULL,
    description VARCHAR(254) NOT NULL,
    create_user VARCHAR(64) NOT NULL,
    create_date DATETIME YEAR TO FRACTION(3) NOT NULL,
    modify_user VARCHAR(64) NOT NULL,
    modify_date DATETIME YEAR TO FRACTION(3) NOT NULL,
    PRIMARY KEY(evaluation_type_id)
);


INSERT INTO 'informix'.evaluation_type_lu(evaluation_type_id,name,description,create_user,create_date,modify_user,modify_date) VALUES
    (1, 'Serious', 'Serious - 10 points', 'System', current, 'System', current);
INSERT INTO 'informix'.evaluation_type_lu(evaluation_type_id,name,description,create_user,create_date,modify_user,modify_date) VALUES
    (2, 'Medium', 'Medium - 3 points', 'System', current, 'System', current);
INSERT INTO 'informix'.evaluation_type_lu(evaluation_type_id,name,description,create_user,create_date,modify_user,modify_date) VALUES
    (3, 'Minor', 'Minor - 1 point', 'System', current, 'System', current);
INSERT INTO 'informix'.evaluation_type_lu(evaluation_type_id,name,description,create_user,create_date,modify_user,modify_date) VALUES
    (4, 'Comment', 'Comment - 0 points', 'System', current, 'System', current);
INSERT INTO 'informix'.evaluation_type_lu(evaluation_type_id,name,description,create_user,create_date,modify_user,modify_date) VALUES
    (5, 'Combined', 'Combined - 0 points', 'System', current, 'System', current);
INSERT INTO 'informix'.evaluation_type_lu(evaluation_type_id,name,description,create_user,create_date,modify_user,modify_date) VALUES
    (6, 'Incorrect', 'Incorrect - 10 (negative) points', 'System', current, 'System', current);

ALTER TABLE review_comment ADD evaluation_type_id INTEGER NOT NULL;
ALTER TABLE review_item_comment ADD evaluation_type_id INTEGER NOT NULL;
UPDATE review_comment SET evaluation_type_id=4;
ALTER TABLE review ADD initial_score FLOAT;
