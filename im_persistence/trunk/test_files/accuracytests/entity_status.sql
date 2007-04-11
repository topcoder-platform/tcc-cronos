CREATE TABLE entity_status (
  entity_status_id INTEGER NOT NULL,
  name VARCHAR(64) NOT NULL,
  description VARCHAR(255) NOT NULL,
  create_date DATETIME YEAR TO FRACTION(3) NOT NULL,
  create_user VARCHAR(64) NOT NULL,
  modify_date DATETIME YEAR TO FRACTION(3) NOT NULL,
  modify_user VARCHAR(64) NOT NULL,
  PRIMARY KEY(entity_status_id)
);

CREATE TABLE entity_status_history (
  entity_id INTEGER NOT NULL,
  start_date DATETIME YEAR TO FRACTION(3) NOT NULL,
  entity_status_id INTEGER NOT NULL,
  end_date DATETIME YEAR TO FRACTION(3),
  create_date DATETIME YEAR TO FRACTION(3) NOT NULL,
  create_user VARCHAR(64) NOT NULL,
  modify_date DATETIME YEAR TO FRACTION(3) NOT NULL,
  modify_user VARCHAR(64) NOT NULL,
  PRIMARY KEY(entity_id, start_date),
  FOREIGN KEY(entity_status_id)
    REFERENCES entity_status(entity_status_id)
);