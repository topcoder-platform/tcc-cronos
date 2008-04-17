CREATE TABLE contest_config (
  property_id DECIMAL(5,0) NOT NULL,
  contest_id DECIMAL(10,0) NOT NULL,
  property_value LVARCHAR(1000) NOT NULL
);

CREATE TABLE contest_document_xref (
  contest_id DECIMAL(10,0) NOT NULL,
  document_id DECIMAL(10,0) NOT NULL,
  create_date DATETIME YEAR to FRACTION(3) NOT NULL
);

CREATE TABLE contest_file_type_xref (
  contest_id      DECIMAL(10,0) NOT NULL,
  file_type_id    DECIMAL(3,0) NOT NULL,
  create_date     DATETIME YEAR to FRACTION(3) NOT NULL
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
  modify_date     DATETIME YEAR to FRACTION(3) NOT NULL
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
  create_date DATETIME YEAR to FRACTION(3) NOT NULL
);

CREATE TABLE contest_status_relation (
  from_contest_status_id DECIMAL(3,0) NOT NULL,
  to_contest_status_id DECIMAL(3,0) NOT NULL,
  contest_status_id DECIMAL(3,0) NOT NULL
);


CREATE TABLE contest_type_config (
  property_id DECIMAL(5,0) NOT NULL,
  contest_type_id DECIMAL(3,0) NOT NULL,
  property_value LVARCHAR(1000) NOT NULL,
  required BOOLEAN NOT NULL);
  
CREATE TABLE document (
  document_id DECIMAL(10,0) NOT NULL,
  path_id DECIMAL(10,0) NOT NULL,
  original_file_name VARCHAR(254) NOT NULL,
  system_file_name VARCHAR(254) NOT NULL,
  document_type_id DECIMAL(3,0) NOT NULL,
  create_date DATETIME YEAR to FRACTION(3) NOT NULL,
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
  start_time DATETIME YEAR to FRACTION(3) NOT NULL,
  end_date DATETIME YEAR to FRACTION(3) NOT NULL,
  winner_annoucement_deadline DATETIME YEAR to FRACTION(3) NOT NULL,
  creator_user_id DECIMAL(10,0) NOT NULL
);


CREATE TABLE submission (
  submission_id DECIMAL(10,0) NOT NULL,
  submission_status_id DECIMAL(3,0) NOT NULL,
  submitter_id DECIMAL(10,0) NOT NULL,
  contest_id DECIMAL(10,0) NOT NULL,
  create_date DATETIME YEAR to FRACTION(3) NOT NULL,
  original_file_name VARCHAR(254) NOT NULL,
  system_file_name VARCHAR(254) NOT NULL,
  submission_type_id DECIMAL(3,0) NOT NULL,
  mime_type_id DECIMAL(5,0) NOT NULL,
  rank DECIMAL(5,0),
  submission_date DATETIME YEAR to FRACTION(3) NOT NULL,
  height INTEGER NOT NULL,
  width INTEGER NOT NULL,
  modify_date DATETIME YEAR to FRACTION(3) NOT NULL,
  or_submission_id INTEGER NOT NULL,
  path_id DECIMAL(10, 0) NOT NULL
);


CREATE TABLE contest_registration (
  contest_id DECIMAL(10,0) NOT NULL,
  user_id DECIMAL(10,0) NOT NULL,
  terms_of_use_id DECIMAL(5,0) NOT NULL,
  create_date DATETIME YEAR to FRACTION(3) NOT NULL
);


CREATE TABLE submission_payments (
  submission_id DECIMAL(10,0) NOT NULL,
  payment_status_id DECIMAL(10,0) NOT NULL,
  price DECIMAL(10,2)
);


CREATE TABLE submission_prize_xref (
        submission_id   DECIMAL(10,0) NOT NULL,
        prize_id        DECIMAL(10,0) NOT NULL,
        create_date     DATETIME YEAR to FRACTION(3) NOT NULL
        );

CREATE TABLE submission_review (
        submission_id           DECIMAL(10,0) NOT NULL,
        reviewer_id             DECIMAL(10,0) NOT NULL,
        text                    LVARCHAR(1000) NOT NULL,
        review_status_id        DECIMAL(3,0) NOT NULL,
        modify_date             DATETIME YEAR to FRACTION(3) NOT NULL
        );

CREATE TABLE contest_prize_xref (
        contest_id      DECIMAL(10,0) NOT NULL,
        prize_id        DECIMAL(10,0) NOT NULL,
        create_date     DATETIME YEAR to FRACTION(3) NOT NULL
        );

CREATE TABLE contest_result (
  submission_id DECIMAL(10,0) NOT NULL,
  contest_id DECIMAL(10,0) NOT NULL,
  create_date DATETIME YEAR to FRACTION(3) NOT NULL,
  placed INTEGER NOT NULL,
  final_score FLOAT NOT NULL
);


ALTER TABLE document_type_lu
        ADD CONSTRAINT ( PRIMARY KEY (document_type_id) CONSTRAINT PK_document_type_lu )
;
ALTER TABLE prize_type_lu
        ADD CONSTRAINT ( PRIMARY KEY (prize_type_id) CONSTRAINT PK_prize_type_lu )
;
ALTER TABLE payment_status
        ADD CONSTRAINT ( PRIMARY KEY (payment_status_id) CONSTRAINT PK_payment_status )
;
ALTER TABLE file_type_lu
        ADD CONSTRAINT ( PRIMARY KEY (file_type_id) CONSTRAINT PK_file_type_lu )
;
ALTER TABLE mime_type_lu
        ADD CONSTRAINT ( PRIMARY KEY (mime_type_id) CONSTRAINT PK_mime_type_lu )
;
ALTER TABLE review_status_lu
        ADD CONSTRAINT ( PRIMARY KEY (review_status_id) CONSTRAINT PK_review_status_lu )
;
ALTER TABLE prize
        ADD CONSTRAINT ( PRIMARY KEY (prize_id) CONSTRAINT PK_prize )
;
ALTER TABLE contest_status_relation
        ADD CONSTRAINT ( PRIMARY KEY(from_contest_status_id, to_contest_status_id, contest_status_id)
		CONSTRAINT PK_contest_status_relation )
;
ALTER TABLE document
        ADD CONSTRAINT ( PRIMARY KEY (document_id) CONSTRAINT PK_document )
;
ALTER TABLE submission
        ADD CONSTRAINT ( PRIMARY KEY (submission_id) CONSTRAINT PK_submission )
;
ALTER TABLE contest
        ADD CONSTRAINT ( PRIMARY KEY (contest_id) CONSTRAINT PK_contest )
;
ALTER TABLE contest_property_lu
        ADD CONSTRAINT ( PRIMARY KEY (property_id) CONSTRAINT PK_contest_property_lu )
;
ALTER TABLE contest_channel_lu
		ADD CONSTRAINT ( PRIMARY KEY (contest_channel_id) CONSTRAINT PK_contest_channel_lu )
;
ALTER TABLE submission_review
        ADD CONSTRAINT ( PRIMARY KEY (reviewer_id, submission_id) CONSTRAINT PK_submission_review )
;
ALTER TABLE contest_prize_xref
        ADD CONSTRAINT ( PRIMARY KEY (contest_id, prize_id) CONSTRAINT PK_contest_prize_xref )
;
ALTER TABLE contest_result
        ADD CONSTRAINT ( PRIMARY KEY (contest_id, submission_id) CONSTRAINT PK_contest_result )
;
ALTER TABLE path
        ADD CONSTRAINT ( PRIMARY KEY (path_id) CONSTRAINT PK_path )
;
ALTER TABLE contest_status_lu
        ADD CONSTRAINT ( PRIMARY KEY (contest_status_id) CONSTRAINT PK_contest_status_lu )
;
ALTER TABLE contest_type_lu
        ADD CONSTRAINT ( PRIMARY KEY (contest_type_id) CONSTRAINT PK_contest_type_lu )
;
ALTER TABLE contest_document_xref
        ADD CONSTRAINT ( PRIMARY KEY (contest_id, document_id) CONSTRAINT PK_contest_document_xref )
;
ALTER TABLE submission_prize_xref
        ADD CONSTRAINT ( PRIMARY KEY (prize_id, submission_id) CONSTRAINT PK_submission_prize_xref )
;
ALTER TABLE submission_payments
        ADD CONSTRAINT ( PRIMARY KEY (submission_id) CONSTRAINT PK_submission_payments )
;
ALTER TABLE contest_registration
        ADD CONSTRAINT ( PRIMARY KEY (contest_id, user_id) CONSTRAINT PK_contest_registration )
;
ALTER TABLE contest_prize_xref
        ADD CONSTRAINT ( FOREIGN KEY(contest_id)
        REFERENCES contest(contest_id) CONSTRAINT FK_contest_prize_xref_1 )
;
ALTER TABLE contest_prize_xref
        ADD CONSTRAINT ( FOREIGN KEY(prize_id)
        REFERENCES prize(prize_id) CONSTRAINT FK_contest_prize_xref_2 )
;
ALTER TABLE contest_config
        ADD CONSTRAINT ( FOREIGN KEY(contest_id)
        REFERENCES contest(contest_id) CONSTRAINT FK_contest_config_1 )
;
ALTER TABLE contest_config
        ADD CONSTRAINT ( FOREIGN KEY(property_id)
        REFERENCES contest_property_lu(property_id) CONSTRAINT FK_contest_config_2 )
;
ALTER TABLE contest_document_xref
        ADD CONSTRAINT ( FOREIGN KEY(contest_id)
        REFERENCES contest(contest_id) CONSTRAINT FK_contest_document_xref_1 )
;
ALTER TABLE contest_document_xref
        ADD CONSTRAINT ( FOREIGN KEY(document_id)
        REFERENCES document(document_id) CONSTRAINT FK_contest_document_xref_2 )
;
ALTER TABLE contest_file_type_xref
        ADD CONSTRAINT ( FOREIGN KEY(file_type_id)
        REFERENCES file_type_lu(file_type_id) CONSTRAINT FK_contest_file_type_xref_1 )
;
ALTER TABLE contest_result
        ADD CONSTRAINT ( FOREIGN KEY(submission_id)
        REFERENCES submission(submission_id) CONSTRAINT FK_contest_result_1 )
;
ALTER TABLE contest_result
        ADD CONSTRAINT ( FOREIGN KEY(contest_id)
        REFERENCES contest(contest_id) CONSTRAINT FK_contest_result_2 )
;
ALTER TABLE submission_review
        ADD CONSTRAINT ( FOREIGN KEY(submission_id)
        REFERENCES submission(submission_id) CONSTRAINT FK_submission_review_1 )
;
ALTER TABLE submission_review
        ADD CONSTRAINT ( FOREIGN KEY(review_status_id)
        REFERENCES review_status_lu(review_status_id) CONSTRAINT FK_submission_review_2 )
;
ALTER TABLE submission_prize_xref
        ADD CONSTRAINT ( FOREIGN KEY(submission_id)
        REFERENCES submission(submission_id) CONSTRAINT FK_submission_prize_xref_1 )
;
ALTER TABLE submission_prize_xref
        ADD CONSTRAINT ( FOREIGN KEY(prize_id)
        REFERENCES prize(prize_id) CONSTRAINT FK_submission_prize_xref_2 )
;
ALTER TABLE submission_payments
        ADD CONSTRAINT ( FOREIGN KEY(payment_status_id)
        REFERENCES payment_status(payment_status_id) CONSTRAINT FK_submission_payments_1 )
;
ALTER TABLE submission_payments
        ADD CONSTRAINT ( FOREIGN KEY(submission_id)
        REFERENCES submission(submission_id) CONSTRAINT FK_submission_payments_2 )
;
ALTER TABLE contest_registration
        ADD CONSTRAINT ( FOREIGN KEY(contest_id)
        REFERENCES contest(contest_id) CONSTRAINT FK_contest_registration_1 )
;
ALTER TABLE contest_channel_lu
		ADD CONSTRAINT ( FOREIGN KEY(file_type_id)
    	REFERENCES file_type_lu(file_type_id) CONSTRAINT FK_contest_channel_lu_1)
;
ALTER TABLE prize
        ADD CONSTRAINT ( FOREIGN KEY(prize_type_id)
        REFERENCES prize_type_lu(prize_type_id) CONSTRAINT FK_prize_1 )
;
ALTER TABLE contest_status_relation
        ADD CONSTRAINT ( FOREIGN KEY(contest_status_id)
    	REFERENCES contest_status_lu(contest_status_id) CONSTRAINT FK_contest_status_relation_1)
;
ALTER TABLE contest_type_config
        ADD CONSTRAINT ( FOREIGN KEY(contest_type_id)
    	REFERENCES contest_type_lu(contest_type_id) CONSTRAINT FK_contest_type_config_1)
;
ALTER TABLE contest_type_config
        ADD CONSTRAINT ( FOREIGN KEY(property_id)
    	REFERENCES contest_property_lu(property_id) CONSTRAINT FK_contest_type_config_2)
;
ALTER TABLE document
        ADD CONSTRAINT ( FOREIGN KEY(mime_type_id)
        REFERENCES mime_type_lu(mime_type_id) CONSTRAINT FK_document_1 )
;
ALTER TABLE document
        ADD CONSTRAINT ( FOREIGN KEY(document_type_id)
        REFERENCES document_type_lu(document_type_id) CONSTRAINT FK_document_2 )
;
ALTER TABLE document
        ADD CONSTRAINT ( FOREIGN KEY(path_id)
        REFERENCES path(path_id) CONSTRAINT FK_document_3 )
;
ALTER TABLE contest
        ADD CONSTRAINT ( FOREIGN KEY(contest_status_id)
        REFERENCES contest_status_lu(contest_status_id) CONSTRAINT FK_contest_1 )
;
ALTER TABLE contest
        ADD CONSTRAINT ( FOREIGN KEY(contest_channel_id)
        REFERENCES contest_channel_lu(contest_channel_id) CONSTRAINT FK_contest_2 )
;
ALTER TABLE contest
        ADD CONSTRAINT ( FOREIGN KEY(contest_type_id)
        REFERENCES contest_type_lu(contest_type_id) CONSTRAINT FK_contest_3 )
;
ALTER TABLE submission
        ADD CONSTRAINT ( FOREIGN KEY(submission_type_id)
        REFERENCES submission_type_lu(submission_type_id) CONSTRAINT FK_submission_1 )
;
ALTER TABLE submission
        ADD CONSTRAINT ( FOREIGN KEY(contest_id)
        REFERENCES contest(contest_id) CONSTRAINT FK_submission_2 )
;
ALTER TABLE submission
        ADD CONSTRAINT ( FOREIGN KEY(submission_status_id)
        REFERENCES submission_status_lu(submission_status_id) CONSTRAINT FK_submission_3 )
;
ALTER TABLE submission
        ADD CONSTRAINT ( FOREIGN KEY(path_id)
        REFERENCES path(path_id) CONSTRAINT FK_submission_4 )
;