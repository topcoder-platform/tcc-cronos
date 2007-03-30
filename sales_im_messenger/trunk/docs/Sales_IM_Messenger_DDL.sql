CREATE TABLE session_mode (
  session_mode_id INTEGER NOT NULL,
  name VARCHAR(64) NOT NULL,
  description VARCHAR(256) NOT NULL,
  create_date DATETIME YEAR TO FRACTION(3) NOT NULL,
  create_user VARCHAR(64) NOT NULL,
  modify_date DATETIME YEAR TO FRACTION(3) NOT NULL,
  modify_user VARCHAR(64) NOT NULL,
  PRIMARY KEY(session_mode_id)
);

CREATE TABLE all_user (
  user_id INTEGER NOT NULL,
  registered_flag VARCHAR(1) NOT NULL,
  username VARCHAR(64) NOT NULL,
  create_date DATETIME YEAR TO FRACTION(3) NOT NULL,
  create_user VARCHAR(64) NOT NULL,
  modify_date DATETIME YEAR TO FRACTION(3) NOT NULL,
  modify_user VARCHAR(64) NOT NULL,
  PRIMARY KEY(user_id)
);

CREATE TABLE session (
  session_id INTEGER NOT NULL,
  session_mode_id INTEGER NOT NULL,
  create_user_id INTEGER NOT NULL,
  create_date DATETIME YEAR TO FRACTION(3) NOT NULL,
  create_user VARCHAR(64) NOT NULL,
  modify_date DATETIME YEAR TO FRACTION(3) NOT NULL,
  modify_user VARCHAR(64) NOT NULL,
  PRIMARY KEY(session_id),
  FOREIGN KEY(session_mode_id)
    REFERENCES session_mode(session_mode_id),
  FOREIGN KEY(create_user_id)
    REFERENCES all_user(user_id)
);

CREATE TABLE session_user (
  session_user_id INTEGER NOT NULL,
  session_id INTEGER NOT NULL,
  user_id INTEGER NOT NULL,
  enter_date DATETIME YEAR TO FRACTION(3) NOT NULL,
  exit_date DATETIME YEAR TO FRACTION(3) NULL,
  create_date DATETIME YEAR TO FRACTION(3) NOT NULL,
  create_user VARCHAR(64) NOT NULL,
  modify_date DATETIME YEAR TO FRACTION(3) NOT NULL,
  modify_user VARCHAR(64) NOT NULL,
  PRIMARY KEY(session_user_id),
  FOREIGN KEY(session_id)
    REFERENCES session(session_id),
  FOREIGN KEY(user_id)
    REFERENCES all_user(user_id)
);

CREATE TABLE session_user_message (
  session_user_id INTEGER NOT NULL,
  message_text VARCHAR(4096) NOT NULL,
  create_date DATETIME YEAR TO FRACTION(3) NOT NULL,
  create_user VARCHAR(64) NOT NULL,
  modify_date DATETIME YEAR TO FRACTION(3) NOT NULL,
  modify_user VARCHAR(64) NOT NULL,
  FOREIGN KEY(session_user_id)
    REFERENCES session_user(session_user_id)
);


