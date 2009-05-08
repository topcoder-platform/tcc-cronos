-- SQL file for Informix database

/* This is tabe which describes contest. It contains foreign keys for different tables like categorym, status etc
Also it contain name of content, short summary, start, end and winner announcement dates.*/
CREATE TABLE contest (
  contest_id DECIMAL(10,0) NOT NULL, -- primary key
  --foreign keys
  contest_type_id DECIMAL(3,0) NOT NULL,
  project_id INTEGER(11) NOT NULL,
  tc_direct_project_id DECIMAL(10,0) NOT NULL, -- project id for TC Direct
  contest_status_id DECIMAL(3,0) NOT NULL,
  forum_id INTEGER(11) NULL, -- should be set later by admin
  event_id DECIMAL(10,0) NULL, --should be set later by admin
  contest_category_id DECIMAL(5,0) NOT NULL,
  creator_user_id DECIMAL(10,0) NOT NULL,
  
  name VARCHAR(254) NOT NULL,
  short_summary VARCHAR(254) NOT NULL,
  start_time DATETIME YEAR TO FRACTION(3) NOT NULL,
  end_date DATETIME YEAR TO FRACTION(3) NOT NULL,
  winnder_annoucement_deadline DATETIME YEAR TO FRACTION(3) NOT NULL,
  
  -- primary key
  PRIMARY KEY(contest_id)
  -- foreign keys 
  FOREIGN KEY(contest_type_id) -- foreign key to contest_type_lu table
    REFERENCES contest_type_lu(contest_type_id)
  FOREIGN KEY(contest_category_id) -- foreign key to contest_category_lu table
    REFERENCES contest_category_lu(contest_category_id)
  FOREIGN KEY(contest_status_id) -- foreign key to contest_status_lu table
    REFERENCES contest_status_lu(contest_status_id)
  FOREIGN KEY(project_id) --foreign key to project table, defined outside of this component
    REFERENCES project(project_id) 
  FOREIGN KEY(tc_direct_project_id)--foreign key to project table, defined outside of this component
    REFERENCES project(tc_direct_project_id) 
  FOREIGN KEY(forum_id) -- foreign key to forum table, defined outside of this component
    REFERENCES forum(forum_id)
  FOREIGN KEY(event_id) -- foreign key to event table, defined outside of this component
    REFERENCES event(event_id)
  FOREIGN KEY(creator_user_id) -- foreign key to users table, defined outside of this component
    REFERENCES users(creator_user_id)
);

/*
This table describes contest registration of user for contest.
*/
CREATE TABLE contest_registration (
  contest_id DECIMAL(10,0) NOT NULL,
  user_id DECIMAL(10,0) NOT NULL,
  terms_of_use_id DECIMAL(5,0) NOT NULL,
  create_date DATETIME YEAR TO FRACTION(3) NOT NULL,
  
  -- primary key
  PRIMARY KEY(contest_id, user_id)
  FOREIGN KEY(contest_id) -- foreign key to contest table
    REFERENCES contest(contest_id)
  FOREIGN KEY(user_id) -- foreign key to users table, defined outside of this component
    REFERENCES users(user_id)
  FOREIGN KEY(terms_of_use_id) -- foreign key to terms_of_use table, defined outside of this component
    REFERENCES terms_of_use(terms_of_use_id)
);

/*
This table describes contest result. It contains score and place for each submission in contest.
*/
CREATE TABLE contest_result (
  submission_id DECIMAL(10,0) NOT NULL,
  contest_id DECIMAL(10,0) NOT NULL,
  create_date DATETIME NOT NULL,
  placed INTEGER(11) NOT NULL,
  final_score FLOAT NOT NULL
  passed_screening BOOLEAN NOT NULL,
  
  -- primary key
  PRIMARY KEY(contest_id, submission_id)
  FOREIGN KEY(contest_id) -- foreign key to contest table
    REFERENCES contest(contest_id)
  FOREIGN KEY(submission_id) -- foreign key to submission table
    REFERENCES submission(submission_id)
);

/* 
Base table for configuration. It keeps id, id of property and value of property. In additional it keeps name and required of configuration parameter 
*/
CREATE TABLE config (
  config_id DECIMAL(5,0) NOT NULL,
  property_id DECIMAL(5,0) NOT NULL,
  property_value LVCHAR(10000) NOT NULL
  required BOOLEAN NOT NULL,
  name VARCHAR(100) NOT NULL,
  
  -- primary key
  PRIMARY KEY(config_id)
  -- foreign keys 
  FOREIGN KEY(property_id) -- foreign key to contest_property_lu table
    REFERENCES contest_property_lu(property_id)
);

/*
This table is used to connect contest and contest configuration
*/
CREATE TABLE contest_config (
  config_id DECIMAL(5,0) NOT NULL,
  contest_id DECIMAL(10,0) NOT NULL
  
  -- primary key
  PRIMARY KEY(config_id, contest_id)
  -- foreign keys 
  FOREIGN KEY(config_id) -- foreign key to config table
    REFERENCES config(config_id)
  FOREIGN KEY(contest_id) -- foreign key to contest table
    REFERENCES contest(contest_id)
);

/*
This is table to connect contest type and configuration. 
*/
CREATE TABLE contest_type_config (
  config_id DECIMAL(5,0) NOT NULL,
  contest_type_id DECIMAL(3,0) NOT NULL,
  
  -- primary key
  PRIMARY KEY(config_id, contest_type_id)
  -- foreign keys 
  FOREIGN KEY(config_id) -- foreign key to config table
    REFERENCES config(config_id)
  FOREIGN KEY(contest_type_id) -- foreign key to contest_type_lu table
    REFERENCES contest_type_lu(contest_type_id)
);

/*
This table describe property and keeps its description
*/
CREATE TABLE contest_property_lu (
  property_id DECIMAL(5,0) NOT NULL,
  property_desc VARCHAR(100) NOT NULL,
  
  -- primary key
  PRIMARY KEY(property_id)
);

/*
This table describes category of component like e.g. icon, prototype
*/
CREATE TABLE contest_category_lu (
  contest_category_id DECIMAL(5,0) NOT NULL,
  file_type_id DECIMAL(3,0) NOT NULL,
  category_name VARCHAR(100) NULL,
  category_description VARCHAR(254) NULL,
  parent_category DECIMAL(5,0) NULL,

  -- primary key
  PRIMARY KEY(contest_category_id)
  -- foreign keys 
  FOREIGN KEY(file_type_id) -- foreign key to file_type_lu table
    REFERENCES file_type_lu(file_type_id)
);

/*
Table which describes contest status. Currently posible values:
1. Unactive
2. Active
3. Inactive
*/
CREATE TABLE contest_status_lu (
  contest_status_id DECIMAL(3,0) NOT NULL,
  contest_status_icon_id INTEGER NOT NULL,
  contest_status_desc VARCHAR(100) NOT NULL,
  
  -- primary key
  PRIMARY KEY(contest_status_id)
  -- foreign keys 
  FOREIGN KEY(contest_status_icon_id) -- foreign key to contest_status_icon table
    REFERENCES contest_status_icon(contest_status_icon_id)
);

/*
Table which describes contest status icon
*/
CREATE TABLE contest_status_icon (
  contest_status_icon_id DECIMAL(3,0) NOT NULL,
  display_icon VARCHAR(254) NULL,
  
  -- primary key
  PRIMARY KEY(contest_status_icon_id)
);

/*
This table describes relation between statuses. It is possible to change status only from from_contest_status to to_contest_status
*/
CREATE TABLE contest_status_relation (
  from_contest_status_id DECIMAL(3,0) NOT NULL,
  to_contest_status_id DECIMAL(3,0) NOT NULL,
  
  -- primary key
  PRIMARY KEY(from_contest_status_id, to_contest_status_id)
);

/*
This table describes type of contest. It describe which images type should contain. Also it is linked to configuration and
each type can have some additional parameters. Currently possible the next types:
1 Storyboard
2 Prototype
3 Logo.
*/
CREATE TABLE contest_type_lu (
  contest_type_id DECIMAL(3,0) NOT NULL,
  contest_type_desc VARCHAR(100) NULL,
  require_preview_image BOOLEAN NULL,
  require_preview_file BOOLEAN NULL,
  
  -- primary key
  PRIMARY KEY(contest_type_id)
);

/*
This table saves document data like path, original and system name, document type, mime type and creation date.
*/
CREATE TABLE document (
  document_id DECIMAL(10,0) NOT NULL,
  path_id DECIMAL(10,0) NOT NULL,
  original_file_name VARCHAR(254) NOT NULL,
  system_file_name VARCHAR(254) NOT NULL,
  document_type_id DECIMAL(3,0) NOT NULL,
  mime_type_id DECIMAL(5,0) NOT NULL,
  create_date DATETIME DATETIME YEAR TO FRACTION(3) NOT NULL, 
  
  -- primary key
  PRIMARY KEY(document_id)
  -- foreign keys 
  FOREIGN KEY(path_id) -- foreign key to path table
    REFERENCES path(path_id)
  FOREIGN KEY(document_type_id) -- foreign key to document_type_lu table
    REFERENCES document_type_lu(document_type_id)
  FOREIGN KEY(mime_type_id) -- foreign key to mime_type_lu table
    REFERENCES mime_type_lu(mime_type_id)
);

/*
This table describes document type. Currentyl possible two types:
1 Specifiction
2 Template
*/
CREATE TABLE document_type_lu (
  document_type_id DECIMAL(3,0) NOT NULL,
  document_type_desc VARCHAR(100) NOT NULL,
  
  -- primary key
  PRIMARY KEY(document_type_id)
);

/*
This table describes file type. Currently possible only zip file type
*/
CREATE TABLE file_type_lu (
  file_type_id DECIMAL(3,0) NOT NULL,
  file_type_desc VARCHAR(100) NOT NULL,
  sort DECIMAL(3,0) NOT NULL,
  image_file_ind DECIMAL(1,0) NOT NULL,
  extension VARCHAR(10) NOT NULL,
  
  -- primary key
  PRIMARY KEY(file_type_id)
  -- foreign keys 
  FOREIGN KEY(path_id) -- foreign key to path table
    REFERENCES path(path_id)
  FOREIGN KEY(document_type_id) -- foreign key to document_type_lu table
    REFERENCES document_type_lu(document_type_id)
  FOREIGN KEY(mime_type_id) -- foreign key to mime_type_lu table
    REFERENCES mime_type_lu(mime_type_id)
);

/*
Table which links document and contest
*/
CREATE TABLE contest_document_xref (
  contest_id DECIMAL(10,0) NOT NULL,
  document_id DECIMAL(10,0) NOT NULL,
  create_date DATETIME YEAR TO FRACTION(3) NOT NULL,
  
  -- primary key
  PRIMARY KEY(document_id, contest_id)
  -- foreign keys 
  FOREIGN KEY(document_id) -- foreign key to document table
    REFERENCES document(document_id)
  FOREIGN KEY(contest_id) -- foreign key to contest table
    REFERENCES contest(contest_id)
);

/*
Table which links file type and contest
*/
CREATE TABLE contest_file_type_xref (
  contest_id DECIMAL(10,0) NOT NULL,
  file_type_id DECIMAL(3,0) NOT NULL,
  create_date DATETIME YEAR TO FRACTION(3) NOT NULL,
  
  -- primary key
  PRIMARY KEY(file_type_id, contest_id)
  -- foreign keys 
  FOREIGN KEY(document_id) -- foreign key to document table
    REFERENCES document(document_id)
  FOREIGN KEY(file_type_id) -- foreign key to file_type_lu table
    REFERENCES file_type_lu(file_type_id)
);

/*
This table describes mime type
*/
CREATE TABLE mime_type_lu (
  mime_type_id DECIMAL(5,0) NOT NULL,
  file_type_id DECIMAL(3,0) NOT NULL,
  mime_type_desc VARCHAR(100) NOT NULL,
  
  -- primary key
  PRIMARY KEY(mime_type_id)
  -- foreign keys 
  FOREIGN KEY(file_type_id) -- foreign key to file_type_lu table
    REFERENCES file_type_lu(file_type_id)
);

/*
This table describe URL path to file.
*/
CREATE TABLE path (
  path_id DECIMAL(10,0) NOT NULL,
  path VARCHAR(254) NOT NULL,
  modify_date DATETIME YEAR TO FRACTION(3) NOT NULL,
  
  -- primary key
  PRIMARY KEY(path_id)
);

/*
This table describes prize for contest. It contains amount of money for each place.
*/
CREATE TABLE prize (
  prize_id DECIMAL(10,0) NOT NULL,
  place INTEGER(11) NOT NULL,
  amount DECIMAL(10,2) NOT NULL,
  prize_type_id DECIMAL(3,0) NOT NULL,
  create_date DATETIME YEAR TO FRACTION(3) NOT NULL,
  
  -- primary key
  PRIMARY KEY(prize_id)
  -- foreign keys 
  FOREIGN KEY(prize_type_id) -- foreign key to prize_type_lu table
    REFERENCES prize_type_lu(prize_type_id)
);

/*
This tables describes prize type. Currently possible:
1 Contest
2 Bonus
*/
CREATE TABLE prize_type_lu (
  prize_type_id DECIMAL(3,0) NOT NULL,
  prize_type_desc VARCHAR(100) NOT NULL,
  
  -- primary key
  PRIMARY KEY(prize_type_id)
);

/*
Table which links file prize and contest
*/
CREATE TABLE contest_prize_xref (
  contest_id DECIMAL(10,0) NOT NULL,
  prize_id DECIMAL(10,0) NOT NULL,
  create_date DATETIME YEAR TO FRACTION(3) NOT NULL,
  
  -- primary key
  PRIMARY KEY(prize_id, contest_id)
  -- foreign keys 
  FOREIGN KEY(prize_id) -- foreign key to prize table
    REFERENCES prize(prize_id)
  FOREIGN KEY(contest_id) -- foreign key to contest table
    REFERENCES contest(contest_id)
);

/*
This tables describes submission instance. It contains all information about submission.
*/
CREATE TABLE submission (
  submission_id DECIMAL(10,0) NOT NULL, -- primary key
  
  -- foreign keys 
  payment_status_id DECIMAL(3,0) NOT NULL,
  submitter_id DECIMAL(10,0) NOT NULL,
  contest_id DECIMAL(10,0) NOT NULL,
  mime_type_id DECIMAL(5,0) NOT NULL,
  submission_type_id DECIMAL(3,0) NOT NULL,
  or_submission_id INTEGER(11) NOT NULL,
  
  -- foreign keys for submission links, watermarked and full version
  watermarked_path DECIMAL(10,0) NOT NULL,
  full_path DECIMAL(10,0) NOT NULL,
  
  -- parameters of submission
  original_file_name VARCHAR(254) NOT NULL,
  system_file_name VARCHAR(254) NOT NULL,  
  create_date DATETIME NOT NULL,
  rank DECIMAL(5,0) NULL,
  height INTEGER(11) NOT NULL,
  width INTEGER(11) NOT NULL,
  modify_date DATETIME YEAR TO FRACTION(3) NOT NULL, 
  submission_date DATETIME YEAR TO FRACTION(3) NOT NULL,
  
  -- primary key
  PRIMARY KEY(submission_id)
  -- foreign keys 
  FOREIGN KEY(payment_status_id) -- foreign key to payment_status_lu table
    REFERENCES payment_status_lu(payment_status_id)
  FOREIGN KEY(submitter_id) -- foreign key to users table, defined outside of this component
    REFERENCES users(submitter_id)
  FOREIGN KEY(contest_id) -- foreign key to contest table
    REFERENCES contest(contest_id)
  FOREIGN KEY(mime_type_id) --foreign key to mime_type_lu table, defined outside of this component
    REFERENCES mime_type_lu(mime_type_id) 
  FOREIGN KEY(submission_type_id)--foreign key to submission_type_lu table, defined outside of this component
    REFERENCES submission_type_lu(submission_type_id) 
  FOREIGN KEY(or_submission_id) -- foreign key to or_submission table, defined outside of this component
    REFERENCES or_submission(or_submission_id)
);

/*
Table which links file prize and submission
*/
CREATE TABLE submission_prize_xref (
  submission_id DECIMAL(10,0) NOT NULL,
  prize_id DECIMAL(10,0) NOT NULL,
  create_date DATETIME YEAR TO FRACTION(3) NOT NULL,
  
  -- primary key
  PRIMARY KEY(prize_id, submission_id)
  -- foreign keys 
  FOREIGN KEY(prize_id) -- foreign key to prize table
    REFERENCES prize(prize_id)
  FOREIGN KEY(submission_id) -- foreign key to submission table
    REFERENCES submission(submission_id)
);

/*
Tables which describe submission status. Currently possible:
1 Active
2 Deleted
*/
CREATE TABLE submission_status_lu (
  submission_status_id DECIMAL(3,0) NOT NULL,
  submission_status_desc VARCHAR(100) NOT NULL,
  
  -- primary key
  PRIMARY KEY(submission_id)
);

/*
Tables which describe submission type. Currently possible:
1 INITIAL_CONTEST_SUBMISSION_TYPE
2 FINAL_SUBMISSION_TYPE
*/
CREATE TABLE submission_type_lu (
  submission_type_id DECIMAL(3,0) NOT NULL,
  submission_type_desc VARCHAR(100) NOT NULL,
  
  -- primary key
  PRIMARY KEY(submission_type_id)
);

/*
This table describe submision payment. It contain payments status and price. Each submission strongly bounded to one submission.
*/
CREATE TABLE submission_payments (
  submission_id DECIMAL(10,0) NOT NULL,
  payment_status_id DECIMAL(3,0) NOT NULL,
  price DECIMAL(10,2) NOT NULL
  
  -- primary key
  PRIMARY KEY(submission_id)
  -- foreign keys 
  FOREIGN KEY(payment_status_id) -- foreign key to payment_status_lu table
    REFERENCES payment_status_lu(payment_status_id)
);

/*
Tables which describe payment status. Currently possible:
1 PAID
2 UNPAID
3 MARKED_FOR_PURCHASE
*/
CREATE TABLE payment_status (
  payment_status_id DECIMAL(3,0) NOT NULL,
  payments_status_desc VARCHAR(100) NULL,
  
  -- primary key
  PRIMARY KEY(payment_status_id)
);

/*
This table describe submission review. It contain reviwer id, review text, status and modify date
*/
CREATE TABLE submission_review (
  submission_id DECIMAL(10,0) NOT NULL,
  reviewer_id DECIMAL(10,0) NOT NULL,
  text LVCHAR(10000) NOT NULL,
  review_status_id DECIMAL(3,0) NOT NULL,
  modify_date DATETIME YEAR TO FRACTION(3) NOT NULL,
  
  -- primary key
  PRIMARY KEY(submission_id, reviewer_id)
  -- foreign keys 
  FOREIGN KEY(review_status_id) -- foreign key to review_status_lu table
    REFERENCES review_status_lu(review_status_id)
  FOREIGN KEY(reviewer_id) -- foreign key to users table, defined outside of this component
    REFERENCES users(reviewer_id)
  FOREIGN KEY(submission_id) -- foreign key to submission table
    REFERENCES submission(submission_id)
);

/*
Tables which describe review status. Currently possible:
1 Passed
2 Failed
3 Cheated
*/
CREATE TABLE review_status_lu (
  review_status_id DECIMAL(3,0) NOT NULL,
  review_status_desc VARCHAR(100) NOT NULL,
  
  -- primary key
  PRIMARY KEY(review_status_id)
);



