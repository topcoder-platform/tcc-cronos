
insert into state_name(state_name_id, name, abbreviation, creation_date, creation_user, modification_date, modification_user) values (1, 'stateName1', 'abbreviation1', '2005-01-01 12:00:00', 'creation_user', '2005-01-01 12:00:00', 'modification_user')
insert into state_name(state_name_id, name, abbreviation, creation_date, creation_user, modification_date, modification_user) values (2, 'stateName2', 'abbreviation2', '2005-01-01 12:00:00', 'creation_user', '2005-01-01 12:00:00', 'modification_user')

insert into country_name(country_name_id, name, creation_date, creation_user, modification_date, modification_user) values (1, 'name1', '2005-01-01 12:00:00', 'creation_user', '2005-01-01 12:00:00', 'modification_user')
insert into country_name(country_name_id, name, creation_date, creation_user, modification_date, modification_user) values (2, 'name2', '2005-01-01 12:00:00', 'creation_user', '2005-01-01 12:00:00', 'modification_user')

insert into contact_type(contact_type_id, description, creation_date, creation_user, modification_date, modification_user) values (1, 'des1', '2005-01-01 12:00:00', 'creation_user', '2005-01-01 12:00:00', 'modification_user')
insert into contact_type(contact_type_id, description, creation_date, creation_user, modification_date, modification_user) values (2, 'des2', '2005-01-01 12:00:00', 'creation_user', '2005-01-01 12:00:00', 'modification_user')
insert into contact_type(contact_type_id, description, creation_date, creation_user, modification_date, modification_user) values (3, 'des3', '2005-01-01 12:00:00', 'creation_user', '2005-01-01 12:00:00', 'modification_user')
insert into contact_type(contact_type_id, description, creation_date, creation_user, modification_date, modification_user) values (4, 'des4', '2005-01-01 12:00:00', 'creation_user', '2005-01-01 12:00:00', 'modification_user')
insert into contact_type(contact_type_id, description, creation_date, creation_user, modification_date, modification_user) values (5, 'des5', '2005-01-01 12:00:00', 'creation_user', '2005-01-01 12:00:00', 'modification_user')

insert into address_type(address_type_id, description, creation_date, creation_user, modification_date, modification_user) values (1, 'des1', '2005-01-01 12:00:00', 'creation_user', '2005-01-01 12:00:00', 'modification_user')
insert into address_type(address_type_id, description, creation_date, creation_user, modification_date, modification_user) values (2, 'des2', '2005-01-01 12:00:00', 'creation_user', '2005-01-01 12:00:00', 'modification_user')
insert into address_type(address_type_id, description, creation_date, creation_user, modification_date, modification_user) values (3, 'des3', '2005-01-01 12:00:00', 'creation_user', '2005-01-01 12:00:00', 'modification_user')
insert into address_type(address_type_id, description, creation_date, creation_user, modification_date, modification_user) values (4, 'des4', '2005-01-01 12:00:00', 'creation_user', '2005-01-01 12:00:00', 'modification_user')
insert into address_type(address_type_id, description, creation_date, creation_user, modification_date, modification_user) values (5, 'des5', '2005-01-01 12:00:00', 'creation_user', '2005-01-01 12:00:00', 'modification_user')

insert into contact(contact_id, first_name, last_name, phone, email, creation_date, creation_user, modification_date, modification_user) values (1, 'first_name1', 'last_name1', 'phone1', 'email1', '2005-01-01 12:00:00', 'creation_user', '2005-01-01 12:00:00', 'modification_user')
insert into contact(contact_id, first_name, last_name, phone, email, creation_date, creation_user, modification_date, modification_user) values (2, 'first_name2', 'last_name2', 'phone2', 'email2', '2005-01-01 12:00:00', 'creation_user', '2005-01-01 12:00:00', 'modification_user')

insert into address(address_id, line1, line2, city, state_name_id, country_name_id, zip_code, creation_date, creation_user, modification_date, modification_user) values (1, 'line11', 'line21', 'city1', 1, 1, 'zip_code1', '2005-01-01 12:00:00', 'creation_user', '2005-01-01 12:00:00', 'modification_user')
insert into address(address_id, line1, line2, city, state_name_id, country_name_id, zip_code, creation_date, creation_user, modification_date, modification_user) values (2, 'line12', 'line22', 'city2', 2, 2, 'zip_code2', '2005-01-01 12:00:00', 'creation_user', '2005-01-01 12:00:00', 'modification_user')

insert into contact_relation(entity_id, creation_date, creation_user, modification_date, modification_user, contact_id, contact_type_id) values (1, '2005-01-01 12:00:00', 'creation_user', '2005-01-01 12:00:00', 'modification_user', 2, 3);
insert into contact_relation(entity_id, creation_date, creation_user, modification_date, modification_user, contact_id, contact_type_id) values (2, '2005-01-01 12:00:00', 'creation_user', '2005-01-01 12:00:00', 'modification_user', 2, 3);
insert into contact_relation(entity_id, creation_date, creation_user, modification_date, modification_user, contact_id, contact_type_id) values (3, '2005-01-01 12:00:00', 'creation_user', '2005-01-01 12:00:00', 'modification_user', 2, 3);
insert into contact_relation(entity_id, creation_date, creation_user, modification_date, modification_user, contact_id, contact_type_id) values (4, '2005-01-01 12:00:00', 'creation_user', '2005-01-01 12:00:00', 'modification_user', 2, 3);
insert into contact_relation(entity_id, creation_date, creation_user, modification_date, modification_user, contact_id, contact_type_id) values (5, '2005-01-01 12:00:00', 'creation_user', '2005-01-01 12:00:00', 'modification_user', 2, 3);

insert into address_relation(entity_id, creation_date, creation_user, modification_date, modification_user, address_id, address_type_id) values (1, '2005-01-01 12:00:00', 'creation_user', '2005-01-01 12:00:00', 'modification_user', 1, 3);
insert into address_relation(entity_id, creation_date, creation_user, modification_date, modification_user, address_id, address_type_id) values (2, '2005-01-01 12:00:00', 'creation_user', '2005-01-01 12:00:00', 'modification_user', 1, 3);
insert into address_relation(entity_id, creation_date, creation_user, modification_date, modification_user, address_id, address_type_id) values (3, '2005-01-01 12:00:00', 'creation_user', '2005-01-01 12:00:00', 'modification_user', 1, 3);
insert into address_relation(entity_id, creation_date, creation_user, modification_date, modification_user, address_id, address_type_id) values (4, '2005-01-01 12:00:00', 'creation_user', '2005-01-01 12:00:00', 'modification_user', 1, 3);
insert into address_relation(entity_id, creation_date, creation_user, modification_date, modification_user, address_id, address_type_id) values (5, '2005-01-01 12:00:00', 'creation_user', '2005-01-01 12:00:00', 'modification_user', 1, 3);