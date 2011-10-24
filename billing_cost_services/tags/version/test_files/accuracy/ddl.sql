-- -----------------------------------------------------
-- Table payment_area
-- -----------------------------------------------------
CREATE  TABLE payment_area (
  id SERIAL,
  name VARCHAR(100) NOT NULL ,
  PRIMARY KEY (id) )
;


-- -----------------------------------------------------
-- Table billing_cost_export
-- -----------------------------------------------------
CREATE  TABLE billing_cost_export (
  id SERIAL ,
  accountant_name VARCHAR(50) NOT NULL ,
  payment_area_id INTEGER NOT NULL ,
  records_count DECIMAL(12,0) NOT NULL ,
  timestamp DATETIME YEAR TO DAY ,
  PRIMARY KEY (id))
;

alter table billing_cost_export add constraint foreign key
    (payment_area_id)
    references payment_area(id)
    constraint fk_billing_cost_export_payment_area1;


-- -----------------------------------------------------
-- Table billing_cost_export_detail
-- -----------------------------------------------------
CREATE  TABLE billing_cost_export_detail (
  id SERIAL ,
  billing_cost_export_id INTEGER NOT NULL ,
  contest_id DECIMAL(12,0) NOT NULL ,
  customer_name VARCHAR(100) NOT NULL ,
  contest_name VARCHAR(255) NOT NULL ,
  project_info_type_id DECIMAL(12,0) ,
  payment_detail_id DECIMAL(12,0) ,
  billing_amount FLOAT NOT NULL ,
  payment_type VARCHAR(25) NOT NULL ,
  invoice_number VARCHAR(45) NOT NULL ,
  in_quickbooks BOOLEAN NOT NULL ,
  quickbooks_import_timestamp DATETIME YEAR TO DAY ,
  PRIMARY KEY (id))
;

alter table billing_cost_export_detail add constraint foreign key
    (billing_cost_export_id)
    references billing_cost_export(id)
    constraint fk_billing_cost_export_detail_billing_cost_export;


-- -----------------------------------------------------
-- Table accounting_audit_record
-- -----------------------------------------------------
CREATE  TABLE accounting_audit_record (
  id SERIAL ,
  action VARCHAR(50) NOT NULL ,
  user_name VARCHAR(50) NOT NULL ,
  timestamp  DATETIME YEAR TO DAY ,
  PRIMARY KEY (id) )
;

