CREATE TABLE all_user (
  user_id INTEGER NOT NULL,
  registered_flag VARCHAR(1) NOT NULL,
  username VARCHAR(64) NOT NULL,
  create_date DATETIME YEAR TO FRACTION(3) DEFAULT CURRENT NOT NULL,
  create_user VARCHAR(64) DEFAULT USER NOT NULL,
  modify_date DATETIME YEAR TO FRACTION(3) DEFAULT CURRENT NOT NULL,
  modify_user VARCHAR(64) DEFAULT USER NOT NULL,
  PRIMARY KEY(user_id)
);

CREATE TABLE buddy_user (
  user_id INTEGER NOT NULL,
  buddy_user_id INTEGER NOT NULL,
  create_date DATETIME YEAR TO FRACTION(3) DEFAULT CURRENT NOT NULL,
  create_user VARCHAR(64) DEFAULT USER NOT NULL,
  modify_date DATETIME YEAR TO FRACTION(3) DEFAULT CURRENT NOT NULL,
  modify_user VARCHAR(64) DEFAULT USER NOT NULL,
  PRIMARY KEY(user_id, buddy_user_id),
  FOREIGN KEY(user_id)
    REFERENCES all_user(user_id),
  FOREIGN KEY(buddy_user_id)
    REFERENCES all_user(user_id)
);

CREATE TABLE blocked_user (
  user_id INTEGER NOT NULL,
  blocked_user_id INTEGER NOT NULL,
  unblocked_flag VARCHAR(1) NOT NULL,
  create_date DATETIME YEAR TO FRACTION(3) DEFAULT CURRENT NOT NULL,
  create_user VARCHAR(64) DEFAULT USER NOT NULL,
  modify_date DATETIME YEAR TO FRACTION(3) DEFAULT CURRENT NOT NULL,
  modify_user VARCHAR(64) DEFAULT USER NOT NULL,
  PRIMARY KEY(user_id, blocked_user_id),
  FOREIGN KEY(user_id)
    REFERENCES all_user(user_id),
  FOREIGN KEY(blocked_user_id)
    REFERENCES all_user(user_id)
);
