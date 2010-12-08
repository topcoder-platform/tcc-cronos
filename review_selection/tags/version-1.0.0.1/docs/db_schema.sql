CREATE TABLE project_type_lu (
    project_type_id               INTEGER                         NOT NULL,
    name                          VARCHAR(64)                     NOT NULL,
    description                   VARCHAR(254)                    NOT NULL,
    create_user                   VARCHAR(64)                     NOT NULL,
    create_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
    modify_user                   VARCHAR(64)                     NOT NULL,
    modify_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
    PRIMARY KEY(project_type_id)
);

CREATE TABLE project_category_lu (
    project_category_id           INTEGER                         NOT NULL,
    project_type_id               INTEGER                         NOT NULL,
    name                          VARCHAR(64)                     NOT NULL,
    description                   VARCHAR(254)                    NOT NULL,
    create_user                   VARCHAR(64)                     NOT NULL,
    create_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
    modify_user                   VARCHAR(64)                     NOT NULL,
    modify_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
    PRIMARY KEY(project_category_id),
    FOREIGN KEY(project_type_id)
    REFERENCES project_type_lu(project_type_id)
);

CREATE TABLE project_status_lu (
    project_status_id             INTEGER                         NOT NULL,
    name                          VARCHAR(64)                     NOT NULL,
    description                   VARCHAR(254)                    NOT NULL,
    create_user                   VARCHAR(64)                     NOT NULL,
    create_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
    modify_user                   VARCHAR(64)                     NOT NULL,
    modify_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
    PRIMARY KEY(project_status_id)
);

CREATE TABLE project (
    project_id                    INTEGER                         NOT NULL,
    project_status_id             INTEGER                         NOT NULL,
    project_category_id           INTEGER                         NOT NULL,
    create_user                   VARCHAR(64)                     NOT NULL,
    create_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
    modify_user                   VARCHAR(64)                     NOT NULL,
    modify_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
    PRIMARY KEY(project_id),
    FOREIGN KEY(project_category_id)
    REFERENCES project_category_lu(project_category_id),
    FOREIGN KEY(project_status_id)
    REFERENCES project_status_lu(project_status_id)
);

CREATE TABLE project_info_type_lu (
    project_info_type_id          INTEGER                         NOT NULL,
    name                          VARCHAR(64)                     NOT NULL,
    description                   VARCHAR(25)                     NOT NULL,
    create_user                   VARCHAR(64)                     NOT NULL,
    create_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
    modify_user                   VARCHAR(64)                     NOT NULL,
    modify_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
    PRIMARY KEY(project_info_type_id)
);

CREATE TABLE project_info (
    project_id                    INTEGER                         NOT NULL,
    project_info_type_id          INTEGER                         NOT NULL,
    value                         LVARCHAR(4096)                  NOT NULL,
    create_user                   VARCHAR(64)                     NOT NULL,
    create_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
    modify_user                   VARCHAR(64)                     NOT NULL,
    modify_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
    PRIMARY KEY(project_id, project_info_type_id),
    FOREIGN KEY(project_info_type_id)
    REFERENCES project_info_type_lu(project_info_type_id),
    FOREIGN KEY(project_id)
    REFERENCES project(project_id)
);

CREATE TABLE project_audit (
    project_audit_id              INTEGER                         NOT NULL,
    project_id                    INTEGER                         NOT NULL,
    update_reason                 VARCHAR(254)                    NOT NULL,
    create_user                   VARCHAR(64)                     NOT NULL,
    create_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
    modify_user                   VARCHAR(64)                     NOT NULL,
    modify_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
    PRIMARY KEY(project_audit_id),
    FOREIGN KEY(project_id)
    REFERENCES project(project_id)
);

CREATE TABLE phase_type_lu (
    phase_type_id                 INTEGER                         NOT NULL,
    name                          VARCHAR(64)                     NOT NULL,
    description                   VARCHAR(254)                    NOT NULL,
    create_user                   VARCHAR(64)                     NOT NULL,
    create_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
    modify_user                   VARCHAR(64)                     NOT NULL,
    modify_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
    PRIMARY KEY(phase_type_id)
);

CREATE TABLE phase_status_lu (
    phase_status_id               INTEGER                         NOT NULL,
    name                          VARCHAR(64)                     NOT NULL,
    description                   VARCHAR(254)                    NOT NULL,
    create_user                   VARCHAR(64)                     NOT NULL,
    create_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
    modify_user                   VARCHAR(64)                     NOT NULL,
    modify_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
    PRIMARY KEY(phase_status_id)
);

CREATE TABLE project_phase (
    project_phase_id              INTEGER                         NOT NULL,
    project_id                    INTEGER                         NOT NULL,
    phase_type_id                 INTEGER                         NOT NULL,
    phase_status_id               INTEGER                         NOT NULL,
    fixed_start_time              DATETIME YEAR TO FRACTION(3),
    scheduled_start_time          DATETIME YEAR TO FRACTION(3)    NOT NULL,
    scheduled_end_time            DATETIME YEAR TO FRACTION(3)    NOT NULL,
    actual_start_time             DATETIME YEAR TO FRACTION(3),
    actual_end_time               DATETIME YEAR TO FRACTION(3),
    duration                      DECIMAL(16, 0)     							NOT NULL,
    create_user                   VARCHAR(64)                     NOT NULL,
    create_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
    modify_user                   VARCHAR(64)                     NOT NULL,
    modify_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
    PRIMARY KEY(project_phase_id),
    FOREIGN KEY(phase_type_id)
    REFERENCES phase_type_lu(phase_type_id),
    FOREIGN KEY(project_id)
    REFERENCES project(project_id),
    FOREIGN KEY(phase_status_id)
    REFERENCES phase_status_lu(phase_status_id)
);

CREATE TABLE project_phase_audit (
    project_phase_id              INTEGER                         NOT NULL,
    scheduled_start_time          DATETIME YEAR TO FRACTION(3),
    scheduled_end_time            DATETIME YEAR TO FRACTION(3),
    audit_action_type_id          INTEGER,
    action_date                   DATETIME YEAR TO FRACTION(3),
    action_user_id                INTEGER,
    FOREIGN KEY(project_phase_id)
    REFERENCES project_phase(project_phase_id)
);

CREATE TABLE phase_dependency (
    dependency_phase_id           INTEGER                         NOT NULL,
    dependent_phase_id            INTEGER                         NOT NULL,
    dependency_start              DECIMAL(1, 0)                   NOT NULL,
    dependent_start               DECIMAL(1, 0)                   NOT NULL,
    lag_time                      DECIMAL(16, 0)                  NOT NULL,
    create_user                   VARCHAR(64)                     NOT NULL,
    create_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
    modify_user                   VARCHAR(64)                     NOT NULL,
    modify_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
    PRIMARY KEY(dependency_phase_id, dependent_phase_id),
    FOREIGN KEY(dependency_phase_id)
    REFERENCES project_phase(project_phase_id),
    FOREIGN KEY(dependent_phase_id)
    REFERENCES project_phase(project_phase_id)
);
CREATE TABLE phase_criteria_type_lu (
    phase_criteria_type_id        INTEGER                         NOT NULL,
    name                          VARCHAR(64)                     NOT NULL,
    description                   VARCHAR(254)                    NOT NULL,
    create_user                   VARCHAR(64)                     NOT NULL,
    create_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
    modify_user                   VARCHAR(64)                     NOT NULL,
    modify_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
    PRIMARY KEY(phase_criteria_type_id)
);
CREATE TABLE phase_criteria (
    project_phase_id              INTEGER                         NOT NULL,
    phase_criteria_type_id        INTEGER                         NOT NULL,
    parameter                     VARCHAR(254)                    NOT NULL,
    create_user                   VARCHAR(64)                     NOT NULL,
    create_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
    modify_user                   VARCHAR(64)                     NOT NULL,
    modify_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
    PRIMARY KEY(project_phase_id, phase_criteria_type_id),
    FOREIGN KEY(project_phase_id)
    REFERENCES project_phase(project_phase_id),
    FOREIGN KEY(phase_criteria_type_id)
    REFERENCES phase_criteria_type_lu(phase_criteria_type_id)
);

CREATE TABLE resource_role_lu (
    resource_role_id              INTEGER                         NOT NULL,
    phase_type_id                 INTEGER,
    name                          VARCHAR(64)                     NOT NULL,
    description                   VARCHAR(254)                    NOT NULL,
    create_user                   VARCHAR(64)                     NOT NULL,
    create_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
    modify_user                   VARCHAR(64)                     NOT NULL,
    modify_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
    PRIMARY KEY(resource_role_id),
    FOREIGN KEY(phase_type_id)
    REFERENCES phase_type_lu(phase_type_id)
);
CREATE TABLE resource (
    resource_id                   INTEGER                         NOT NULL,
    resource_role_id              INTEGER                         NOT NULL,
    project_id                    INTEGER,
    project_phase_id              INTEGER,
    create_user                   VARCHAR(64)                     NOT NULL,
    create_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
    modify_user                   VARCHAR(64)                     NOT NULL,
    modify_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
    PRIMARY KEY(resource_id),
    FOREIGN KEY(project_id)
    REFERENCES project(project_id),
    FOREIGN KEY(resource_role_id)
    REFERENCES resource_role_lu(resource_role_id),
    FOREIGN KEY(project_phase_id)
    REFERENCES project_phase(project_phase_id)
);

CREATE TABLE resource_info_type_lu (
    resource_info_type_id         INTEGER                         NOT NULL,
    name                          VARCHAR(64)                     NOT NULL,
    description                   VARCHAR(254)                    NOT NULL,
    create_user                   VARCHAR(64)                     NOT NULL,
    create_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
    modify_user                   VARCHAR(64)                     NOT NULL,
    modify_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
    PRIMARY KEY(resource_info_type_id)
);
CREATE TABLE resource_info (
    resource_id                   INTEGER                         NOT NULL,
    resource_info_type_id         INTEGER                         NOT NULL,
    value                         LVARCHAR(4096)                  NOT NULL,
    create_user                   VARCHAR(64)                     NOT NULL,
    create_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
    modify_user                   VARCHAR(64)                     NOT NULL,
    modify_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
    PRIMARY KEY(resource_id, resource_info_type_id),
    FOREIGN KEY(resource_info_type_id)
    REFERENCES resource_info_type_lu(resource_info_type_id),
    FOREIGN KEY(resource_id)
    REFERENCES resource(resource_id)
);

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

CREATE TABLE upload_type_lu (
    upload_type_id                INTEGER                         NOT NULL,
    name                          VARCHAR(64)                     NOT NULL,
    description                   VARCHAR(254)                    NOT NULL,
    create_user                   VARCHAR(64)                     NOT NULL,
    create_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
    modify_user                   VARCHAR(64)                     NOT NULL,
    modify_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
    PRIMARY KEY(upload_type_id)
);

CREATE TABLE upload_status_lu (
    upload_status_id              INTEGER                         NOT NULL,
    name                          VARCHAR(64)                     NOT NULL,
    description                   VARCHAR(254)                    NOT NULL,
    create_user                   VARCHAR(64)                     NOT NULL,
    create_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
    modify_user                   VARCHAR(64)                     NOT NULL,
    modify_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
    PRIMARY KEY(upload_status_id)
);

CREATE TABLE upload (
    upload_id                     INTEGER                         NOT NULL,
    project_id                    INTEGER                         NOT NULL,
    resource_id                   INTEGER                         NOT NULL,
    upload_type_id                INTEGER                         NOT NULL,
    upload_status_id              INTEGER                         NOT NULL,
    parameter                     VARCHAR(254)                    NOT NULL,
    create_user                   VARCHAR(64)                     NOT NULL,
    create_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
    modify_user                   VARCHAR(64)                     NOT NULL,
    modify_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
    PRIMARY KEY(upload_id),
    FOREIGN KEY(upload_type_id)
    REFERENCES upload_type_lu(upload_type_id),
    FOREIGN KEY(upload_status_id)
    REFERENCES upload_status_lu(upload_status_id),
    FOREIGN KEY(resource_id)
    REFERENCES resource(resource_id),
    FOREIGN KEY(project_id)
    REFERENCES project(project_id)
);

CREATE TABLE submission_status_lu (
    submission_status_id          INTEGER                         NOT NULL,
    name                          VARCHAR(64)                     NOT NULL,
    description                   VARCHAR(254)                    NOT NULL,
    create_user                   VARCHAR(64)                     NOT NULL,
    create_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
    modify_user                   VARCHAR(64)                     NOT NULL,
    modify_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
    PRIMARY KEY(submission_status_id)
);

CREATE TABLE submission_type_lu (
    submission_type_id            INTEGER                         NOT NULL,
    name                          VARCHAR(64)                     NOT NULL,
    description                   VARCHAR(254)                    NOT NULL,
    create_user                   VARCHAR(64)                     NOT NULL,
    create_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
    modify_user                   VARCHAR(64)                     NOT NULL,
    modify_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
    PRIMARY KEY(submission_type_id)
);

CREATE TABLE submission (
    submission_id                 INTEGER                         NOT NULL,
    upload_id                     INTEGER                         NOT NULL,
    submission_status_id          INTEGER                         NOT NULL,
    submission_type_id            INTEGER                         NOT NULL,
    screening_score		DECIMAL(5,2),
    initial_score 		DECIMAL(5,2),
    final_score 			DECIMAL(5,2),
    placement 			DECIMAL(3,0),
    create_user                   VARCHAR(64)                     NOT NULL,
    create_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
    modify_user                   VARCHAR(64)                     NOT NULL,
    modify_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
    PRIMARY KEY(submission_id),
    FOREIGN KEY(submission_status_id)
    REFERENCES submission_status_lu(submission_status_id),
    FOREIGN KEY(submission_type_id)
    REFERENCES submission_type_lu(submission_type_id),
    FOREIGN KEY(upload_id)
    REFERENCES upload(upload_id)
);

CREATE TABLE resource_submission (
    resource_id                   INTEGER                         NOT NULL,
    submission_id                 INTEGER                         NOT NULL,
    create_user                   VARCHAR(64)                     NOT NULL,
    create_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
    modify_user                   VARCHAR(64)                     NOT NULL,
    modify_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
    PRIMARY KEY(resource_id, submission_id),
    FOREIGN KEY(submission_id)
    REFERENCES submission(submission_id),
    FOREIGN KEY(resource_id)
    REFERENCES resource(resource_id)
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

CREATE TABLE deliverable_lu (
    deliverable_id                INTEGER                         NOT NULL,
    phase_type_id                 INTEGER                         NOT NULL,
    resource_role_id              INTEGER                         NOT NULL,
    name                          VARCHAR(64)                     NOT NULL,
    description                   VARCHAR(64)                     NOT NULL,
    submission_type_id            INTEGER,
    required                      DECIMAL(1, 0)                   NOT NULL,
    create_user                   VARCHAR(64)                     NOT NULL,
    create_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
    modify_user                   VARCHAR(64)                     NOT NULL,
    modify_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
    PRIMARY KEY(deliverable_id),
    FOREIGN KEY(phase_type_id)
    REFERENCES phase_type_lu(phase_type_id),
    FOREIGN KEY(resource_role_id)
    REFERENCES resource_role_lu(resource_role_id),
    FOREIGN KEY(submission_type_id)
    REFERENCES submission_type_lu(submission_type_id)
);

CREATE TABLE notification_type_lu (
    notification_type_id          INTEGER                         NOT NULL,
    name                          VARCHAR(64)                     NOT NULL,
    description                   VARCHAR(254)                    NOT NULL,
    create_user                   VARCHAR(64)                     NOT NULL,
    create_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
    modify_user                   VARCHAR(64)                     NOT NULL,
    modify_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
    PRIMARY KEY(notification_type_id)
);
CREATE TABLE notification (
    project_id                    INTEGER                         NOT NULL,
    external_ref_id               INTEGER                         NOT NULL,
    notification_type_id          INTEGER                         NOT NULL,
    create_user                   VARCHAR(64)                     NOT NULL,
    create_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
    modify_user                   VARCHAR(64)                     NOT NULL,
    modify_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
    PRIMARY KEY(project_id, external_ref_id, notification_type_id),
    FOREIGN KEY(project_id)
    REFERENCES project(project_id),
    FOREIGN KEY(notification_type_id)
    REFERENCES notification_type_lu(notification_type_id)
);

CREATE TABLE id_sequences (
    name                  VARCHAR(255)    NOT NULL,
    next_block_start      INTEGER         NOT NULL,
    block_size            INTEGER         NOT NULL,
    exhausted             INTEGER         NOT NULL,
    PRIMARY KEY (name)
);

INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)
VALUES('project_id_seq', 1, 20, 0);
INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)
VALUES('project_audit_id_seq', 1, 20, 0);
INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)
VALUES('resource_id_seq', 1, 20, 0);
INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)
VALUES('resource_role_id_seq', 1, 20, 0);
INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)
VALUES('notification_type_id_seq', 1, 20, 0);
INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)
VALUES('phase_id_seq', 1, 20, 0);
INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)
VALUES('review_id_seq', 1, 20, 0);
INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)
VALUES('review_item_id_seq', 1, 20, 0);
INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)
VALUES('review_comment_id_seq', 1, 20, 0);
INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)
VALUES('review_item_comment_id_seq', 1, 20, 0);

INSERT INTO phase_status_lu(phase_status_id, name, description, create_user, create_date, modify_user, modify_date)
VALUES(1, 'Scheduled', 'Scheduled', 'System', CURRENT, 'System', CURRENT);
INSERT INTO phase_status_lu(phase_status_id, name, description, create_user, create_date, modify_user, modify_date)
VALUES(2, 'Open', 'Open', 'System', CURRENT, 'System', CURRENT);
INSERT INTO phase_status_lu(phase_status_id, name, description, create_user, create_date, modify_user, modify_date)
VALUES(3, 'Closed', 'Closed', 'System', CURRENT, 'System', CURRENT);

INSERT INTO project_status_lu(project_status_id, name, description, create_user, create_date, modify_user, modify_date)
VALUES(1, 'Active', 'Active', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_status_lu(project_status_id, name, description, create_user, create_date, modify_user, modify_date)
VALUES(2, 'Inactive', 'Inactive', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_status_lu(project_status_id, name, description, create_user, create_date, modify_user, modify_date)
VALUES(3, 'Deleted', 'Deleted', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_status_lu(project_status_id, name, description, create_user, create_date, modify_user, modify_date)
VALUES(4, 'Cancelled - Failed Review', 'Cancelled - Failed Review', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_status_lu(project_status_id, name, description, create_user, create_date, modify_user, modify_date)
VALUES(5, 'Cancelled - Failed Screening', 'Cancelled - Failed Screening', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_status_lu(project_status_id, name, description, create_user, create_date, modify_user, modify_date)
VALUES(6, 'Cancelled - Zero Submissions', 'Cancelled - Zero Submissions', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_status_lu(project_status_id, name, description, create_user, create_date, modify_user, modify_date)
VALUES(7, 'Completed', 'Completed', 'System', CURRENT, 'System', CURRENT);

INSERT INTO resource_info_type_lu(resource_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
VALUES(1, 'External Reference ID', 'External Reference ID', 'System', CURRENT, 'System', CURRENT); 

