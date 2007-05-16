create table player (
   any_user_id BIGINT NOT NULL,
   contact_info_id BIGINT,
   payment_pref VARCHAR(49),
   FOREIGN KEY (any_user_id) REFERENCES any_user (id),
   FOREIGN KEY (contact_info_id) REFERENCES contact_info (id)
);

create table admin (
   any_user_id BIGINT NOT NULL,
   FOREIGN KEY (any_user_id) REFERENCES any_user (id)
);

create table sponsor (
   any_user_id BIGINT NOT NULL,
   contact_info_id BIGINT NOT NULL,
   fax NUMERIC,
   payment_pref VARCHAR(49),
   is_approved BOOL,
   FOREIGN KEY (any_user_id) REFERENCES any_user (id),
   FOREIGN KEY (contact_info_id) REFERENCES contact_info (id)
);

create table any_user (
   id BIGINT NOT NULL PRIMARY KEY,
   handle VARCHAR(29) NOT NULL,
   e_mail VARCHAR(255) NOT NULL,
   passwd VARCHAR(24) NOT NULL,
   is_active BOOL NOT NULL
);

create table contact_info (
   id BIGINT NOT NULL PRIMARY KEY,
   first_name VARCHAR(29) NOT NULL,
   last_name VARCHAR(49) NOT NULL,
   address_1 VARCHAR(127) NOT NULL,
   address_2 VARCHAR(127),
   city VARCHAR(127) NOT NULL,
   state VARCHAR(29),
   postal_code VARCHAR(9) NOT NULL,
   telephone VARCHAR(15) NOT NULL,
   country VARCHAR(40) NOT NULL
);
