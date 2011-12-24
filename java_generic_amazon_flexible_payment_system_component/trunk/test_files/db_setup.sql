CREATE TABLE authorizations (
  id DECIMAL(19,0) NOT NULL ,
  multiple_use DECIMAL(1,0) NOT NULL,
  token_id VARCHAR(65),
  amount_left DECIMAL(10,2) NOT NULL,
  fixed_amount DECIMAL(10,2),
  cancelled DECIMAL(1,0) NOT NULL,
  create_date DATETIME YEAR TO FRACTION DEFAULT CURRENT YEAR TO FRACTION
);

CREATE TABLE payments (
  id DECIMAL(19,0) NOT NULL ,
  authorization_id DECIMAL(19,0) NOT NULL,
  amount DECIMAL(10,2) NOT NULL,
  transaction_id VARCHAR(35),
  status VARCHAR(13) NOT NULL,
  create_date DATETIME YEAR TO FRACTION DEFAULT CURRENT YEAR TO FRACTION
);

CREATE TABLE payment_parameters (
  payment_id DECIMAL(19,0) NOT NULL,
  key VARCHAR(50) NOT NULL,
  value VARCHAR(255),
  create_date DATETIME YEAR TO FRACTION DEFAULT CURRENT YEAR TO FRACTION
);

CREATE TABLE payment_operations (
  id DECIMAL(19,0) NOT NULL,
  payment_id DECIMAL(19,0) NOT NULL,
  request_id VARCHAR(64),
  type VARCHAR(7) NOT NULL,
  success DECIMAL(1,0) NOT NULL,
  create_date DATETIME YEAR TO FRACTION DEFAULT CURRENT YEAR TO FRACTION
);

CREATE TABLE id_sequences (
    name VARCHAR(254),
    next_block_start DECIMAL(12,0) not null,
    block_size DECIMAL(10,0) not null,
    exhausted DECIMAL(1,0) default 0 not null
);

INSERT INTO id_sequences (name, next_block_start, block_size) VALUES ('authorization', 10000, 20);
INSERT INTO id_sequences (name, next_block_start, block_size) VALUES ('payment', 10000, 20);
INSERT INTO id_sequences (name, next_block_start, block_size) VALUES ('paymentOperation', 10000, 20);

ALTER TABLE authorizations ADD CONSTRAINT
  PRIMARY KEY (id)
  CONSTRAINT pk_authorizations;

ALTER TABLE payments ADD CONSTRAINT
  PRIMARY KEY (id)
  CONSTRAINT pk_payments;

ALTER TABLE payment_parameters ADD CONSTRAINT
  PRIMARY KEY (payment_id, key)
  CONSTRAINT pk_payment_parameters;

ALTER TABLE payment_operations ADD CONSTRAINT
  PRIMARY KEY (id)
  CONSTRAINT pk_payment_operations;

ALTER TABLE payments ADD CONSTRAINT
  FOREIGN KEY (authorization_id)
  REFERENCES authorizations (id)
  CONSTRAINT fk_payment_authorization;

ALTER TABLE payment_parameters ADD CONSTRAINT
  FOREIGN KEY (payment_id)
  REFERENCES payments (id)
  CONSTRAINT fk_payment_parameter_payment;

ALTER TABLE payment_operations ADD CONSTRAINT
  FOREIGN KEY (payment_id)
  REFERENCES payments (id)
  CONSTRAINT fk_payment_operation_payment;
