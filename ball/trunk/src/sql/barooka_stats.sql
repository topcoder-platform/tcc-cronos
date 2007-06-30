
-- --------------------------------------------------------------
-- log_parameter table provides a lookup lists of distinct names
-- HTTP request parameters providing the details for log entry
-- --------------------------------------------------------------

CREATE TABLE log_parameter (
  id BIGINT NOT NULL,
  name VARCHAR(29) NOT NULL UNIQUE,
  PRIMARY KEY(id)
);

-- --------------------------------------------------------------------
-- log_entry_type table provides a lookup lists of types of logged
-- entries. Each type provides a template to be used for producing the
-- content of the logged entry 
-- --------------------------------------------------------------------

CREATE TABLE log_entry_type (
  id BIGINT NOT NULL,
  text VARCHAR(256) NOT NULL UNIQUE,
  PRIMARY KEY(id)
);

-- ----------------------------------------------------------------------
-- log_entry_type_param table links the log_entry_type and log_parameter
-- tables and specifies which log entry type depends on which parameters
-- ----------------------------------------------------------------------

CREATE TABLE log_entry_type_param (
  type_id BIGINT NOT NULL,
  sequence_number INTEGER NOT NULL,
  param_id BIGINT NOT NULL,
  PRIMARY KEY(type_id, sequence_number),
  FOREIGN KEY(type_id) REFERENCES log_entry_type(id)
    ON DELETE CASCADE,
  FOREIGN KEY(param_id) REFERENCES log_parameter(id)
    ON DELETE CASCADE
);

-- ----------------------------------------------------------------------
-- session entities provide details for user sessions,
-- e.g. sesion ID and IP-address. Each event is associated with a session
-- ----------------------------------------------------------------------
CREATE TABLE session (
  id BIGINT NOT NULL IDENTITY,
  user_id BIGINT NOT NULL,
  session_id VARCHAR(64) NOT NULL,
  ip_address VARCHAR(64) NOT NULL,
  PRIMARY KEY(id)
);

-- ----------------------------------------------------------------------
-- log_event entities provide the details for the event logged in the
-- course of user's interaction with the web site or plugin
-- ----------------------------------------------------------------------
CREATE TABLE log_event (
  id BIGINT NOT NULL IDENTITY,
  type_id BIGINT NOT NULL,
  session_id BIGINT NOT NULL,
  time DATETIME NOT NULL,
  PRIMARY KEY(id),
  FOREIGN KEY(type_id) REFERENCES log_entry_type(id)
    ON DELETE CASCADE,
  FOREIGN KEY(session_id) REFERENCES session(id)
    ON DELETE CASCADE
);

-- ----------------------------------------------------------------------
-- log_event_parameter entities provide the values of the request parameters
-- for the event logged in the course of user's interaction with the web
-- site or plugin
-- ----------------------------------------------------------------------
CREATE TABLE log_event_parameter (
  event_id BIGINT NOT NULL,
  param_id BIGINT NOT NULL,
  value VARCHAR(4096),
  FOREIGN KEY(event_id) REFERENCES log_event(id)
    ON DELETE CASCADE,
  FOREIGN KEY(param_id) REFERENCES log_parameter(id)
    ON DELETE CASCADE
);
