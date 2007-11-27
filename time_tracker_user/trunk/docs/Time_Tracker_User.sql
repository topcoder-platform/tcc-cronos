

CREATE TABLE account_status (
  account_status_id             INTEGER                         NOT NULL,
  description                   VARCHAR(255)                    NOT NULL,
  creation_date                 DATETIME YEAR TO SECOND         NOT NULL,
  creation_user                 VARCHAR(64)                     NOT NULL,
  modification_date             DATETIME YEAR TO SECOND         NOT NULL,
  modification_user             VARCHAR(64)                     NOT NULL,
  PRIMARY KEY (account_status_id) CONSTRAINT pk_account_status
);
CREATE TABLE user_status (
  user_status_id                INTEGER                         NOT NULL,
  company_id                    INTEGER                         NOT NULL,
  description                   VARCHAR(255)                    NOT NULL,
  active                        SMALLINT                        NOT NULL,
  creation_date                 DATETIME        NOT NULL,
  creation_user                 VARCHAR(64)                     NOT NULL,
  modification_date             DATETIME        NOT NULL,
  modification_user             VARCHAR(64)                     NOT NULL,
  PRIMARY KEY (user_status_id)
);
CREATE TABLE user_type (
  user_type_id                  INTEGER                         NOT NULL,
  company_id                    INTEGER                         NOT NULL,
  description                   VARCHAR(255)                    NOT NULL,
  active                        SMALLINT                        NOT NULL,
  creation_date                 DATETIME      NOT NULL,
  creation_user                 VARCHAR(64)                     NOT NULL,
  modification_date             DATETIME         NOT NULL,
  modification_user             VARCHAR(64)                     NOT NULL,
  PRIMARY KEY (user_type_id) 
);
CREATE TABLE user_account (
  user_account_id               INTEGER                         NOT NULL,
  company_id                    INTEGER                         NOT NULL,
  account_status_id             INTEGER                         NOT NULL,
  user_status_id                INTEGER,
  user_type_id                  INTEGER,
  user_name                     VARCHAR(64)                     NOT NULL,
  password                      VARCHAR(64)                     NOT NULL,
  creation_date                 DATETIME YEAR TO SECOND         NOT NULL,
  creation_user                 VARCHAR(64)                     NOT NULL,
  modification_date             DATETIME YEAR TO SECOND         NOT NULL,
  modification_user             VARCHAR(64)                     NOT NULL,
  PRIMARY KEY (user_account_id) CONSTRAINT pk_user_account,
  FOREIGN KEY (account_status_id)
    REFERENCES account_status (account_status_id) CONSTRAINT fk_useraccount_accountstatus_accountstatusid,
  FOREIGN KEY (user_status_id)
    REFERENCES user_status (user_status_id) CONSTRAINT fk_useraccount_userstatus_userstatusid,
  FOREIGN KEY (user_type_id)
    REFERENCES user_type (user_type_id) CONSTRAINT fk_useraccount_usertype_usertypeid
);



INSERT INTO account_status(account_status_id, description, creation_date, creation_user, modification_date, modification_user)
  VALUES(0, 'Inactive', CURRENT, 'System', CURRENT, 'System');
INSERT INTO account_status(account_status_id, description, creation_date, creation_user, modification_date, modification_user)
  VALUES(1, 'Active', CURRENT, 'System', CURRENT, 'System');
INSERT INTO account_status(account_status_id, description, creation_date, creation_user, modification_date, modification_user)
  VALUES(3, 'Locked', CURRENT, 'System', CURRENT, 'System');

INSERT INTO user_status(user_status_id, company_id, description, active, creation_date, creation_user, modification_date, modification_user)
  VALUES(1, 1, 'Internal Project', 1, CURRENT, 'System', CURRENT, 'System');
INSERT INTO user_status(user_status_id, company_id, description, active, creation_date, creation_user, modification_date, modification_user)
  VALUES(2, 1, 'Client Project', 1, CURRENT, 'System', CURRENT, 'System');
INSERT INTO user_status(user_status_id, company_id, description, active, creation_date, creation_user, modification_date, modification_user)
  VALUES(3, 1, 'On the Bench', 1, CURRENT, 'System', CURRENT, 'System');
INSERT INTO user_status(user_status_id, company_id, description, active, creation_date, creation_user, modification_date, modification_user)
  VALUES(4, 1, 'Training', 1, CURRENT, 'System', CURRENT, 'System');
INSERT INTO user_status(user_status_id, company_id, description, active, creation_date, creation_user, modification_date, modification_user)
  VALUES(5, 1, 'Not Started', 1, CURRENT, 'System', CURRENT, 'System');

INSERT INTO user_type(user_type_id, company_id, description, active, creation_date, creation_user, modification_date, modification_user)
  VALUES(1, 1, 'Project Manager', 1, CURRENT, 'System', CURRENT, 'System');
INSERT INTO user_type(user_type_id, company_id, description, active, creation_date, creation_user, modification_date, modification_user)
  VALUES(2, 1, 'Architect', 1, CURRENT, 'System', CURRENT, 'System');
INSERT INTO user_type(user_type_id, company_id, description, active, creation_date, creation_user, modification_date, modification_user)
  VALUES(3, 1, 'Creative', 1, CURRENT, 'System', CURRENT, 'System');
INSERT INTO user_type(user_type_id, company_id, description, active, creation_date, creation_user, modification_date, modification_user)
  VALUES(4, 1, 'Deployment Engineer', 1, CURRENT, 'System', CURRENT, 'System');
INSERT INTO user_type(user_type_id, company_id, description, active, creation_date, creation_user, modification_date, modification_user)
  VALUES(5, 1, 'Developer', 1, CURRENT, 'System', CURRENT, 'System');
INSERT INTO user_type(user_type_id, company_id, description, active, creation_date, creation_user, modification_date, modification_user)
  VALUES(6, 1, 'Account Manager', 1, CURRENT, 'System', CURRENT, 'System');
INSERT INTO user_type(user_type_id, company_id, description, active, creation_date, creation_user, modification_date, modification_user)
  VALUES(7, 1, 'Sales', 1, CURRENT, 'System', CURRENT, 'System');
INSERT INTO user_type(user_type_id, company_id, description, active, creation_date, creation_user, modification_date, modification_user)
  VALUES(8, 1, 'Operations', 1, CURRENT, 'System', CURRENT, 'System');
INSERT INTO user_type(user_type_id, company_id, description, active, creation_date, creation_user, modification_date, modification_user)
  VALUES(9, 1, 'Accounting', 1, CURRENT, 'System', CURRENT, 'System');
