/*
 * This file contains SQL statements for generating the database tables
 * required by the User Logic Persistence component. If the database tables
 * have not been created yet, please execute these SQL statements before
 * using the component.
 *
 * NOTE: 
 * 
 * 1) These statements work with SQL Server 2000
 *
 * 2) The data type of the user.is_active and sponsor.is_approved columns are
 *    set to BIT, as SQL Server does not have a BOOL or BOOLEAN data type.
 */

/**
 * The "any_user" table.
 */
CREATE TABLE any_user (
   id BIGINT NOT NULL PRIMARY KEY,
   handle VARCHAR(29) NOT NULL,
   e_mail VARCHAR(255) NOT NULL,
   passwd VARCHAR(24) NOT NULL,
   is_active BIT NOT NULL
);

/**
 * The "contact_info" table.
 */
CREATE TABLE contact_info (
   id BIGINT NOT NULL PRIMARY KEY,
   first_name VARCHAR(29) NOT NULL,
   last_name VARCHAR(49) NOT NULL,
   address_1 VARCHAR(127) NOT NULL,
   address_2 VARCHAR(127),
   city VARCHAR(127) NOT NULL,
   state VARCHAR(29) NOT NULL,
   postal_code VARCHAR(9) NOT NULL,
   telephone VARCHAR(15) NOT NULL
);

/**
 * The "player" table.
 */
CREATE TABLE player (
   any_user_id BIGINT NOT NULL,
   contact_info_id BIGINT,
   payment_pref VARCHAR(49),
   FOREIGN KEY (any_user_id) REFERENCES any_user (id),
   FOREIGN KEY (contact_info_id) REFERENCES contact_info (id)
);

/**
 * The "admin" table.
 */
CREATE TABLE admin (
   any_user_id BIGINT NOT NULL,
   FOREIGN KEY (any_user_id) REFERENCES any_user (id)
);

/**
 * The "sponsor" table.
 */
CREATE TABLE sponsor (
   any_user_id BIGINT NOT NULL,
   contact_info_id BIGINT,
   fax NUMERIC,
   payment_pref VARCHAR(49),
   is_approved BIT,
   FOREIGN KEY (any_user_id) REFERENCES any_user (id),
   FOREIGN KEY (contact_info_id) REFERENCES contact_info (id)
);

/**
 * The "pending_email_conf" table.
 */
CREATE TABLE pending_email_conf (
   address VARCHAR(255) NOT NULL PRIMARY KEY,
   confirmation_code VARCHAR(32) NOT NULL,
   date_sent DATETIME NOT NULL,
   message_subject VARCHAR(255) NOT NULL,
   message_body TEXT NOT NULL
);
