CREATE TABLE contest_config (
  contest_config_id DECIMAL(5,0) NOT NULL,
  property_id DECIMAL(5,0) NOT NULL,
  contest_id DECIMAL(10,0) NOT NULL,
  property_value VARCHAR(1000) NOT NULL
);

CREATE TABLE contest_document_xref (
  contest_id DECIMAL(10,0) NOT NULL,
  document_id DECIMAL(10,0) NOT NULL,
  create_date DATETIME NOT NULL
);

CREATE TABLE contest_file_type_xref (
  contest_id      DECIMAL(10,0) NOT NULL,
  file_type_id    DECIMAL(3,0) NOT NULL,
  create_date     DATETIME NOT NULL
);

CREATE TABLE contest_type_lu (
  contest_type_id         DECIMAL(3,0) NOT NULL,
  contest_type_desc       VARCHAR(100),
  require_preview_image   BOOLEAN,
  require_preview_file    BOOLEAN
);

CREATE TABLE document_type_lu (
  document_type_id        DECIMAL(3,0) NOT NULL,
  document_type_desc      VARCHAR(100) NOT NULL
);

CREATE TABLE contest_status_lu (
  contest_status_id DECIMAL(3,0) NOT NULL,
  contest_status_desc VARCHAR(100) NOT NULL,
  name VARCHAR(100)
);

CREATE TABLE prize_type_lu (
  prize_type_id DECIMAL(3,0) NOT NULL,
  prize_type_desc VARCHAR(100) NOT NULL
);

CREATE TABLE path (
  path_id         DECIMAL(10,0) NOT NULL,
  path            VARCHAR(254) NOT NULL,
  modify_date     DATETIME NOT NULL
);

CREATE TABLE payment_status (
  payment_status_id DECIMAL(10,0) NOT NULL,
  payments_status_desc VARCHAR(100)
);

CREATE TABLE file_type_lu (
  file_type_id DECIMAL(3,0) NOT NULL,
  file_type_desc VARCHAR(100) NOT NULL,
  sort DECIMAL(3,0) NOT NULL,
  image_file_ind DECIMAL(1,0) NOT NULL,
  extension VARCHAR(10) NOT NULL
);

CREATE TABLE mime_type_lu (
  mime_type_id DECIMAL(5,0) NOT NULL,
  file_type_id DECIMAL(3,0) NOT NULL,
  mime_type_desc VARCHAR(100) NOT NULL
);

CREATE TABLE review_status_lu (
  review_status_id DECIMAL(3,0) NOT NULL,
  review_status_desc VARCHAR(100) NOT NULL
);

CREATE TABLE contest_property_lu (
  property_id DECIMAL(5,0) NOT NULL,
  property_desc VARCHAR(100) NOT NULL
);

CREATE TABLE submission_status_lu (
  submission_status_id DECIMAL(3,0) NOT NULL,
  submission_status_desc VARCHAR(100) NOT NULL,
  PRIMARY KEY(submission_status_id)
);

CREATE TABLE submission_type_lu (
  submission_type_id DECIMAL(3,0) NOT NULL,
  submission_type_desc VARCHAR(100) NOT NULL,
  PRIMARY KEY(submission_type_id)
);

CREATE TABLE contest_channel_lu (
  contest_channel_id DECIMAL(5,0) NOT NULL,
  file_type_id DECIMAL(3,0) NOT NULL,
  channel_name VARCHAR(100),
  channel_description VARCHAR(254),
  parent_channel DECIMAL(5,0)
);

CREATE TABLE prize (
  prize_id DECIMAL(10,0) NOT NULL,
  place INTEGER NOT NULL,
  amount DECIMAL(10,2) NOT NULL,
  prize_type_id DECIMAL(3,0) NOT NULL,
  create_date DATETIME NOT NULL
);

CREATE TABLE contest_status_relation (
  from_contest_status_id DECIMAL(3,0) NOT NULL,
  to_contest_status_id DECIMAL(3,0) NOT NULL,
  contest_status_id DECIMAL(3,0) NOT NULL
);


CREATE TABLE contest_type_config (
  contest_type_config_id DECIMAL(5,0) NOT NULL,
  property_id DECIMAL(5,0) NOT NULL,
  contest_type_id DECIMAL(3,0) NOT NULL,
  property_value VARCHAR(1000) NOT NULL,
  required BOOLEAN NOT NULL);
  
CREATE TABLE document (
  document_id DECIMAL(10,0) NOT NULL,
  path_id DECIMAL(10,0) NOT NULL,
  original_file_name VARCHAR(254) NOT NULL,
  system_file_name VARCHAR(254) NOT NULL,
  document_type_id DECIMAL(3,0) NOT NULL,
  create_date DATETIME NOT NULL,
  mime_type_id DECIMAL(5,0) NOT NULL
);

CREATE TABLE contest (
  contest_id DECIMAL(10,0) NOT NULL,
  contest_channel_id DECIMAL(5,0) NOT NULL,
  name VARCHAR(254) NOT NULL,
  contest_type_id DECIMAL(3,0) NOT NULL,
  project_id INTEGER NOT NULL,
  tc_direct_project_id DECIMAL(10,0) NOT NULL,
  contest_status_id DECIMAL(3,0) NOT NULL,
  forum_id INTEGER,
  event_id DECIMAL(10,0),
  start_time DATETIME NOT NULL,
  end_date DATETIME NOT NULL,
  winner_annoucement_deadline DATETIME NOT NULL,
  creator_user_id DECIMAL(10,0) NOT NULL
);


CREATE TABLE submission (
  submission_id DECIMAL(10,0) NOT NULL,
  submission_status_id DECIMAL(3,0) NOT NULL,
  submitter_id DECIMAL(10,0) NOT NULL,
  contest_id DECIMAL(10,0) NOT NULL,
  create_date DATETIME NOT NULL,
  original_file_name VARCHAR(254) NOT NULL,
  system_file_name VARCHAR(254) NOT NULL,
  submission_type_id DECIMAL(3,0) NOT NULL,
  mime_type_id DECIMAL(5,0) NOT NULL,
  rank DECIMAL(5,0),
  submission_date DATETIME NOT NULL,
  height INTEGER NOT NULL,
  width INTEGER NOT NULL,
  modify_date DATETIME NOT NULL,
  or_submission_id INTEGER NOT NULL,
  path_id DECIMAL(10, 0) NOT NULL
);


CREATE TABLE contest_registration (
  contest_id DECIMAL(10,0) NOT NULL,
  user_id DECIMAL(10,0) NOT NULL,
  terms_of_use_id DECIMAL(5,0) NOT NULL,
  create_date DATETIME NOT NULL
);


CREATE TABLE submission_payments (
  submission_id DECIMAL(10,0) NOT NULL,
  payment_status_id DECIMAL(10,0) NOT NULL,
  price DECIMAL(10,2)
);


CREATE TABLE submission_prize_xref (
        submission_id   DECIMAL(10,0) NOT NULL,
        prize_id        DECIMAL(10,0) NOT NULL,
        create_date     DATETIME NOT NULL
        );

CREATE TABLE submission_review (
        submission_id           DECIMAL(10,0) NOT NULL,
        reviewer_id             DECIMAL(10,0) NOT NULL,
        text                    VARCHAR(1000) NOT NULL,
        review_status_id        DECIMAL(3,0) NOT NULL,
        modify_date             DATETIME NOT NULL
        );

CREATE TABLE contest_prize_xref (
        contest_id      DECIMAL(10,0) NOT NULL,
        prize_id        DECIMAL(10,0) NOT NULL,
        create_date     DATETIME NOT NULL
        );

CREATE TABLE contest_result (
  submission_id DECIMAL(10,0) NOT NULL,
  contest_id DECIMAL(10,0) NOT NULL,
  create_date DATETIME NOT NULL,
  placed INTEGER NOT NULL,
  final_score FLOAT NOT NULL
);

CREATE TABLE tc_direct_project (
project_id  INTEGER NOT NULL,
name        VARCHAR(200) NOT NULL,
description VARCHAR(10000),
user_id     INTEGER NOT NULL, -- id of user who creates project
create_date DATETIME NOT NULL,
modify_date DATETIME, 
PRIMARY KEY(project_id) 
);
