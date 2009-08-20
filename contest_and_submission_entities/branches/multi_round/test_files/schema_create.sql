CREATE TABLE  contest  (
        contest_id decimal(10) NOT NULL ,
        contest_channel_id decimal(5) NOT NULL ,
        name varchar(254) NOT NULL ,
        contest_type_id decimal(3) NOT NULL ,
        project_id int NOT NULL ,
        tc_direct_project_id decimal(10) NOT NULL ,
        contest_status_id decimal(3) NOT NULL,
        contest_detailed_status_id DECIMAL(3,0) NOT NULL,
        forum_id int,
        event_id decimal(10),
        start_time DATETIME YEAR to FRACTION(3) NOT NULL,
        end_time DATETIME YEAR to FRACTION(3) NOT NULL,
        winner_announcement_time DATETIME YEAR to FRACTION(3) NOT NULL,
        create_user_id decimal(10) NOT NULL,
        contest_milestone_prize_id DECIMAL(10,0) NOT NULL,
        is_multi_round boolean(1),
        non_winning_submissions_purchased boolean(1),
        launch_immediately boolean(1),
        deleted boolean(1),
        contest_general_info_id DECIMAL(10,0) NOT NULL,
        contest_multi_round_information_id DECIMAL(10,0) NOT NULL,
        contest_specifications_id DECIMAL(10,0) NOT NULL
);

CREATE TABLE  contest_channel_lu  (
        contest_channel_id decimal(5) NOT NULL ,
        contest_channel_desc varchar(254)
);

CREATE TABLE  contest_config  (
        property_id decimal(5) NOT NULL ,
        contest_id decimal(10) NOT NULL ,
        property_value lvarchar(1000)
);

CREATE TABLE  contest_document_xref  (
        contest_id decimal(10) NOT NULL ,
        document_id decimal(10) NOT NULL ,
        create_date DATETIME YEAR to FRACTION(3) NOT NULL
);

CREATE TABLE  contest_file_type_xref  (
        contest_id decimal(10) NOT NULL ,
        file_type_id decimal(3) NOT NULL ,
        create_date DATETIME YEAR to FRACTION(3) NOT NULL
);

CREATE TABLE  contest_prize_xref  (
        contest_id decimal(10) NOT NULL ,
        prize_id decimal(10) NOT NULL ,
        create_date DATETIME YEAR to FRACTION(3) NOT NULL
);

CREATE TABLE  contest_property_lu  (
        property_id decimal(5) NOT NULL ,
        property_desc varchar(100) NOT NULL,
        property_name varchar(100)
);

CREATE TABLE  contest_registration  (
        contest_id decimal(10) NOT NULL ,
        user_id decimal(10) NOT NULL ,
        terms_of_use_id decimal(5) NOT NULL ,
        create_date DATETIME YEAR to FRACTION(3) NOT NULL
);

CREATE TABLE  contest_result  (
        contest_id decimal(10) NOT NULL ,
        submission_id decimal(10) NOT NULL ,
        create_date DATETIME YEAR to FRACTION(3) NOT NULL,
        placed int NOT NULL ,
        final_score float NOT NULL
);

CREATE TABLE  contest_status_lu  (
        contest_status_id decimal(3) NOT NULL ,
        contest_status_desc varchar(100) NOT NULL
);

CREATE TABLE  contest_status_relation  (
        from_contest_status_id decimal(3) NOT NULL ,
        to_contest_status_id decimal(3) NOT NULL ,
        contest_status_id decimal(3) NOT NULL
);

CREATE TABLE  contest_type_config  (
        property_id decimal(5) NOT NULL ,
        contest_type_id decimal(3) NOT NULL ,
        property_value lvarchar(1000) NOT NULL ,
        required boolean(1) NOT NULL
);

CREATE TABLE  contest_type_lu  (
        contest_type_id decimal(3) NOT NULL ,
        contest_type_desc varchar(100),
        require_preview_image boolean(1),
        require_preview_file boolean(1)
);

CREATE TABLE  document  (
        document_id decimal(10) NOT NULL ,
        path_id decimal(10) NOT NULL ,
        original_file_name varchar(254) NOT NULL ,
        system_file_name varchar(254) NOT NULL ,
        document_type_id decimal(3) NOT NULL ,
        create_date DATETIME YEAR to FRACTION(3) NOT NULL,
        mime_type_id decimal(5) NOT NULL,
        description varchar(254)
);

CREATE TABLE  document_type_lu  (
        document_type_id decimal(3) NOT NULL ,
        document_type_desc varchar(100) NOT NULL
);

CREATE TABLE  file_type_lu  (
        file_type_id decimal(3) NOT NULL ,
        file_type_desc varchar(100) NOT NULL ,
        sort decimal(3) NOT NULL ,
        image_file_ind decimal(1) NOT NULL ,
        extension varchar(10) NOT NULL
);

CREATE TABLE medium_lu (
        medium_id DECIMAL(3,0) NOT NULL,
        medium_desc VARCHAR(100)
);

CREATE TABLE contest_medium_xref (
  contest_id DECIMAL(10,0) NOT NULL,
  medium_id DECIMAL(3,0),
  create_date DATETIME YEAR to FRACTION(3)
);

CREATE TABLE  mime_type_lu  (
        mime_type_id decimal(5) NOT NULL ,
        file_type_id decimal(3) NOT NULL ,
        mime_type_desc varchar(100) NOT NULL
);

CREATE TABLE  path  (
        path_id decimal(10) NOT NULL ,
        path varchar(254) NOT NULL ,
        modify_date DATETIME YEAR to FRACTION(3) NOT NULL
);

CREATE TABLE  payment_status_lu  (
        payment_status_id decimal(10) NOT NULL ,
        payments_status_desc varchar(100)
);

CREATE TABLE  prize  (
        prize_id decimal(10) NOT NULL ,
        place int NOT NULL ,
        amount decimal(10,2) NOT NULL ,
        prize_type_id decimal(3) NOT NULL ,
        create_date DATETIME YEAR to FRACTION(3) NOT NULL
);

CREATE TABLE  prize_type_lu  (
        prize_type_id decimal(3) NOT NULL ,
        prize_type_desc varchar(100) NOT NULL
);

CREATE TABLE  review_status_lu  (
        review_status_id decimal(3) NOT NULL ,
        review_status_desc varchar(100) NOT NULL
);

CREATE TABLE  submission  (
        submission_id decimal(10) NOT NULL ,
        submission_status_id decimal(3) NOT NULL ,
        submitter_id decimal(10) NOT NULL ,
        contest_id decimal(10) NOT NULL ,
        create_date DATETIME YEAR to FRACTION(3) NOT NULL,
        original_file_name varchar(254) NOT NULL ,
        system_file_name varchar(254) NOT NULL ,
        submission_type_id decimal(3) NOT NULL ,
        mime_type_id decimal(5) NOT NULL ,
        rank decimal(5),
        submission_date DATETIME YEAR to FRACTION(3) NOT NULL,
        height int NOT NULL ,
        width int NOT NULL ,
        modify_date DATETIME YEAR to FRACTION(3) NOT NULL,
        or_submission_id int NOT NULL ,
        path_id decimal(10) NOT NULL,
        feedback_text LVARCHAR(1000),
        feedback_thumb DECIMAL(3,0),
        payment_id DECIMAL(3,0),
        user_rank DECIMAL(3,0)
);

CREATE TABLE submission_image (
  submission_id DECIMAL(10,0) NOT NULL,
  image_id DECIMAL(10,0) NOT NULL,
  sort_order INT NOT NULL,
  modify_date DATETIME YEAR to FRACTION(3),
  create_date DATETIME YEAR to FRACTION(3)
);


CREATE TABLE  submission_payment  (
        submission_id decimal(10) NOT NULL ,
        payment_status_id decimal(10) NOT NULL ,
        price decimal(10,2),
        sale_reference_id varchar(128),
        sale_type_id INTEGER,
        paypal_order_id INTEGER,
        create_date DATETIME YEAR to FRACTION(3) NOT NULL
);

CREATE TABLE  submission_prize_xref  (
        prize_id decimal(10) NOT NULL ,
        submission_id decimal(10) NOT NULL ,
        create_date DATETIME YEAR TO FRACTION(3) default CURRENT YEAR TO FRACTION not null
);

CREATE TABLE  submission_review  (
        reviewer_id decimal(10) NOT NULL ,
        submission_id decimal(10) NOT NULL ,
        text lvarchar(1000) NOT NULL ,
        review_status_id decimal(3) NOT NULL ,
        modify_date DATETIME YEAR to FRACTION(3) NOT NULL
);

CREATE TABLE  submission_status_lu  (
        submission_status_id decimal(3) NOT NULL ,
        submission_status_desc varchar(100) NOT NULL
);

CREATE TABLE  submission_type_lu  (
        submission_type_id decimal(3) NOT NULL ,
        submission_type_desc varchar(100) NOT NULL
);


create table contest_detailed_status_lu (
    contest_detailed_status_id DECIMAL(3,0) not null,
    contest_detailed_status_desc VARCHAR(100) not null,
    name varchar(254) NOT NULL,
    contest_status_id DECIMAL(3) not null
);

create table contest_detailed_status_xref (
    contest_id DECIMAL(10) not null,
    contest_detailed_status_id DECIMAL(3,0) not null
);

CREATE TABLE permission_type (
    permission_type_id decimal(10) NOT NULL,
    name varchar(254) NOT NULL
);

CREATE TABLE user_permission_grant (
    user_permission_grant_id decimal(10) NOT NULL,
    user_id decimal(10,0) NOT NULL,
    project_id decimal(10,0) NOT NULL,
    permission_type_id decimal(10) NOT NULL
);

create table sale_type_lu (
    sale_type_id INTEGER not null,
    sale_type_name VARCHAR(128) not null
);

CREATE TABLE contest_general_info (
  contest_general_info_id DECIMAL(10,0) NOT NULL,
  goals LVARCHAR(2000),
  target_audience LVARCHAR(2000),
  branding_guidelines LVARCHAR(2000),
  disliked_designs_websites LVARCHAR(2000),
  other_instructions LVARCHAR(2000),
  winning_criteria LVARCHAR(2000)
);




CREATE TABLE contest_milestone_prize (
  contest_milestone_prize_id DECIMAL(10,0) NOT NULL,
  prize_type_id DECIMAL(3) NOT NULL,
  amount DECIMAL(10,2),
  number_of_submissions SMALLINT,
  create_date DATETIME YEAR to FRACTION(3) NOT Null
);

CREATE TABLE contest_multi_round_information (
  contest_multi_round_information_id DECIMAL(10,0) NOT NULL,
  milestone_date DATETIME YEAR to FRACTION(3),
  submitters_locked_between_rounds boolean(1),
  round_one_introduction LVARCHAR(2000),
  round_two_introduction LVARCHAR(2000)
);

CREATE TABLE contest_payment (
  contest_payment_id DECIMAL(10,0) NOT NULL,
  contest_id DECIMAL(10) NOT NULL,
  payment_status_id DECIMAL(10) NOT NULL,
  price DECIMAL(10,2),
  paypal_order_id VARCHAR(128),
  sale_reference_id varchar(128),
  sale_type_id INTEGER NOT NULL,
  create_date DATETIME YEAR to FRACTION(3) NOT Null
);

CREATE TABLE contest_resource_xref (
  contest_id DECIMAL(10) NOT NULL,
  resource_id DECIMAL(3,0) NOT NULL,
  create_date DATETIME YEAR to FRACTION(3)
);

CREATE TABLE contest_specifications (
  contest_specifications_id DECIMAL(10,0) NOT NULL,
  colors LVARCHAR(2000),
  fonts LVARCHAR(2000),
  layout_and_size LVARCHAR(2000),
  additional_requirements_and_restrictions LVARCHAR(2000)
);

CREATE TABLE resource (
  resource_id DECIMAL(3,0) NOT NULL,
  resource_name VARCHAR(128)
);



CREATE TABLE submission_milestone_prize_xref (
  submission_id DECIMAL(10,0) NOT NULL,
  contest_milestone_prize_id DECIMAL(10,0) NOT NULL,
  create_date DATETIME YEAR to FRACTION(3)
);

create table contest_change_history
(
contest_id decimal(10,0) not null ,
transaction_id decimal(10,0) not null ,
timestamp datetime year to fraction(3) default current year to fraction(3),
username varchar(32) not null ,
is_user_admin decimal(1,0) not null ,
field_name varchar(64) not null ,
old_data lvarchar(1024) not null ,
new_data lvarchar(1024) not null
);

ALTER TABLE contest_change_history
        ADD CONSTRAINT ( PRIMARY KEY (contest_id, field_name) CONSTRAINT PK_contest_change_history )
;

ALTER TABLE contest_general_info
        ADD CONSTRAINT ( PRIMARY KEY (contest_general_info_id) CONSTRAINT PK_contest_general_info )
;
ALTER TABLE contest_milestone_prize
        ADD CONSTRAINT ( PRIMARY KEY (contest_milestone_prize_id) CONSTRAINT PK_contest_milestone_prize )
;
ALTER TABLE contest_multi_round_information
        ADD CONSTRAINT ( PRIMARY KEY (contest_multi_round_information_id) CONSTRAINT PK_contest_multi_round_information )
;

ALTER TABLE contest_payment
        ADD CONSTRAINT ( PRIMARY KEY (contest_payment_id) CONSTRAINT PK_contest_payment )
;

ALTER TABLE contest_resource_xref
        ADD CONSTRAINT ( PRIMARY KEY (contest_id, resource_id) CONSTRAINT PK_contest_resource_xref )
;


ALTER TABLE contest_specifications
        ADD CONSTRAINT ( PRIMARY KEY (contest_specifications_id) CONSTRAINT PK_contest_specifications )
;

ALTER TABLE resource
        ADD CONSTRAINT ( PRIMARY KEY (resource_id) CONSTRAINT PK_resource )
;

ALTER TABLE submission_milestone_prize_xref
        ADD CONSTRAINT ( PRIMARY KEY (submission_id, contest_milestone_prize_id) CONSTRAINT PK_submission_milestone_prize_xref )
;

ALTER TABLE contest_config
        ADD CONSTRAINT ( PRIMARY KEY (contest_id, property_id) CONSTRAINT PK_contest_config )
;
ALTER TABLE contest_type_config
        ADD CONSTRAINT ( PRIMARY KEY (contest_type_id, property_id) CONSTRAINT PK_contest_type_config )
;
ALTER TABLE document_type_lu
        ADD CONSTRAINT ( PRIMARY KEY (document_type_id) CONSTRAINT PK_document_type_lu )
;
ALTER TABLE prize_type_lu
        ADD CONSTRAINT ( PRIMARY KEY (prize_type_id) CONSTRAINT PK_prize_type_lu )
;
ALTER TABLE payment_status_lu
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
ALTER TABLE submission_type_lu
        ADD CONSTRAINT ( PRIMARY KEY (submission_type_id) CONSTRAINT PK_submission_type_lu )
;
ALTER TABLE sale_type_lu
        ADD CONSTRAINT ( PRIMARY KEY (sale_type_id) CONSTRAINT PK_sale_type_lu )
;
ALTER TABLE prize
        ADD CONSTRAINT ( PRIMARY KEY (prize_id) CONSTRAINT PK_prize )
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
ALTER TABLE submission_status_lu
        ADD CONSTRAINT ( PRIMARY KEY (submission_status_id) CONSTRAINT PK_submission_status_lu )
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
ALTER TABLE submission_payment
        ADD CONSTRAINT ( PRIMARY KEY (submission_id) CONSTRAINT PK_submission_payment )
;
ALTER TABLE contest_registration
        ADD CONSTRAINT ( PRIMARY KEY (contest_id, user_id) CONSTRAINT PK_contest_registration )
;
ALTER TABLE contest_detailed_status_lu
        ADD CONSTRAINT ( PRIMARY KEY (contest_detailed_status_id) CONSTRAINT PK_contest_detailed_status_lu )
;
ALTER TABLE contest_detailed_status_xref
        ADD CONSTRAINT ( PRIMARY KEY (contest_id, contest_detailed_status_id) CONSTRAINT PK_contest_detailed_status_xref )
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
ALTER TABLE submission_payment
        ADD CONSTRAINT ( FOREIGN KEY(payment_status_id)
        REFERENCES payment_status_lu(payment_status_id) CONSTRAINT FK_submission_payment_1 )
;
ALTER TABLE submission_payment
        ADD CONSTRAINT ( FOREIGN KEY(submission_id)
        REFERENCES submission(submission_id) CONSTRAINT FK_submission_payment_2 )
;
ALTER TABLE contest_payment
        ADD CONSTRAINT ( FOREIGN KEY(payment_status_id)
        REFERENCES payment_status_lu(payment_status_id) CONSTRAINT FK_contest_payment_1 )
;
ALTER TABLE contest_payment
        ADD CONSTRAINT ( FOREIGN KEY(sale_type_id)
        REFERENCES sale_type_lu(sale_type_id) CONSTRAINT FK_contest_payment_2 )
;
ALTER TABLE contest_registration
        ADD CONSTRAINT ( FOREIGN KEY(contest_id)
        REFERENCES contest(contest_id) CONSTRAINT FK_contest_registration_1 )
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
ALTER TABLE contest
        ADD CONSTRAINT ( FOREIGN KEY(contest_general_info_id)
        REFERENCES contest_general_info(contest_general_info_id) CONSTRAINT FK_contest_4 )
;
ALTER TABLE contest
        ADD CONSTRAINT ( FOREIGN KEY(contest_milestone_prize_id)
        REFERENCES contest_milestone_prize(contest_milestone_prize_id) CONSTRAINT FK_contest_5 )
;
ALTER TABLE contest
        ADD CONSTRAINT ( FOREIGN KEY(contest_multi_round_information_id)
        REFERENCES contest_multi_round_information(contest_multi_round_information_id) CONSTRAINT FK_contest_6 )
;
ALTER TABLE contest
        ADD CONSTRAINT ( FOREIGN KEY(contest_specifications_id)
        REFERENCES contest_specifications(contest_specifications_id) CONSTRAINT FK_contest_7 )
;
ALTER TABLE contest
        ADD CONSTRAINT ( FOREIGN KEY(contest_detailed_status_id)
        REFERENCES contest_detailed_status_lu(contest_detailed_status_id) CONSTRAINT FK_contest_8)
;
ALTER TABLE contest_milestone_prize
        ADD CONSTRAINT ( FOREIGN KEY(prize_type_id)
        REFERENCES prize_type_lu(prize_type_id) CONSTRAINT FK_contest_milestone_prize_2 )
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

ALTER TABLE contest_detailed_status_lu
        ADD CONSTRAINT ( FOREIGN KEY(contest_status_id)
        REFERENCES contest_status_lu(contest_status_id) CONSTRAINT FK_contest_detailed_status_lu_contest_status_lu )
;

ALTER TABLE contest_detailed_status_xref
        ADD CONSTRAINT ( FOREIGN KEY(contest_id)
        REFERENCES contest(contest_id) CONSTRAINT FK_contest_detailed_status_xref_1 )
;

ALTER TABLE contest_detailed_status_xref
        ADD CONSTRAINT ( FOREIGN KEY(contest_detailed_status_id)
        REFERENCES contest_detailed_status_lu(contest_detailed_status_id) CONSTRAINT FK_contest_detailed_status_xref_2 )
;

ALTER TABLE contest_resource_xref
        ADD CONSTRAINT ( FOREIGN KEY(contest_id)
        REFERENCES contest(contest_id) CONSTRAINT FK_contest_resource_xref_1 )
;
ALTER TABLE contest_resource_xref
        ADD CONSTRAINT ( FOREIGN KEY(resource_id)
        REFERENCES resource(resource_id) CONSTRAINT FK_contest_resource_xref_2 )
;
