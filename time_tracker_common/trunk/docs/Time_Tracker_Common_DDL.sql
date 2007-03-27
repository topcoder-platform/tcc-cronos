CREATE TABLE payment_terms (
  payment_terms_id INTEGER NOT NULL,
  description VARCHAR(64) NOT NULL,
  creation_date DATE NOT NULL,
  creation_user VARCHAR(64) NOT NULL,
  modification_date DATET NOT NULL,
  modification_user VARCHAR(64) NOT NULL,
  active SMALLINT NOT NULL,
  term INTEGER NOT NULL,
  PRIMARY KEY(payment_terms_id)
);
