CREATE TABLE  contest  (
        contest_id decimal(10) NOT NULL ,
        contest_channel_id decimal(5) NOT NULL ,
        name varchar(254) NOT NULL ,
        contest_type_id decimal(3) NOT NULL ,
        project_id int NOT NULL ,
        tc_direct_project_id decimal(10) NOT NULL ,
        contest_status_id decimal(3) NOT NULL ,
        forum_id int,
        event_id decimal(10),
        start_time DATETIME YEAR to FRACTION(3) NOT NULL, 
        end_time DATETIME YEAR to FRACTION(3) NOT NULL, 
        winner_annoucement_time DATETIME YEAR to FRACTION(3) NOT NULL,
        create_user_id decimal(10) NOT NULL 
);

CREATE TABLE  contest_channel_lu  (
        contest_channel_id decimal(5) NOT NULL ,
        contest_channel_desc varchar(254)
);

CREATE TABLE  contest_config  (
        property_id decimal(5) NOT NULL ,
        contest_id decimal(10) NOT NULL ,
        property_value lvarchar(1000) NOT NULL 
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
        property_desc varchar(100) NOT NULL 
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
        contest_status_desc varchar(100) NOT NULL ,
        name varchar(100)
);

CREATE TABLE  contest_status_relation  (
        from_contest_status_id decimal(3) NOT NULL ,
        to_contest_status_id decimal(3) NOT NULL ,
        contest_status_id decimal(3) NOT NULL 
);

CREATE TABLE  contest_type_config  (
        contest_type_config_id decimal(5) NOT NULL ,
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
        mime_type_id decimal(5) NOT NULL 
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

CREATE TABLE  payment_status  (
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
        path_id decimal(10) NOT NULL 
);

CREATE TABLE  submission_payments  (
        submission_id decimal(10) NOT NULL ,
        payment_status_id decimal(10) NOT NULL ,
        price decimal(10,2)
);

CREATE TABLE  submission_prize_xref  (
        prize_id decimal(10) NOT NULL ,
        submission_id decimal(10) NOT NULL ,
        create_date DATETIME YEAR to FRACTION(3) NOT NULL
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
    contest_status_id DECIMAL(3,0) not null 
) 

create table contest_detailed_status_xref ( 
    contest_id DECIMAL(10,0) not null, 
    contest_detailed_status_id DECIMAL(3,0) not null 
) 

alter table contest_detailed_status_xref add constraint foreign key 
    (contest_detailed_status_id) 
    references contest_detailed_status_lu 
    (contest_detailed_status_id) 
    constraint contestdetailedstatusxref_contestdetailedstatuslu_fk; 


ALTER TABLE contest_config
        ADD CONSTRAINT ( PRIMARY KEY (contest_config_id) CONSTRAINT PK_contest_config )
;
ALTER TABLE contest_type_config
        ADD CONSTRAINT ( PRIMARY KEY (contest_type_config_id) CONSTRAINT PK_contest_type_config )
;
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


alter table contest_detailed_status_lu add constraint primary key 
    (contest_detailed_status_id) 
    constraint contestdetailedstatuslu_pk; 

alter table contest_detailed_status_lu add constraint foreign key 
    (contest_status_id) 
    references contest_status_lu 
    (contest_status_id) 
    constraint contestdetailedstatuslu_conteststatuslu_fk; 

alter table contest_detailed_status_xref add constraint primary key 
    (contest_id, contest_detailed_status_id) 
    constraint contestdetailedstatusxref_pk; 

alter table contest_detailed_status_xref add constraint foreign key 
    (contest_id) 
    references contest 
    (contest_id) 
    constraint contestdetailedstatusxref_contest_fk;