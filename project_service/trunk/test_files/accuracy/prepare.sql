INSERT INTO security_user (login_id, user_id, password, create_user_id) values (10001, 'myxgyy', 'password', 0);
insert into security_groups (group_id, description, create_user_id) values (1000001, 'Test Group 1', 1);
insert into security_groups (group_id, description, create_user_id) values (1000002, 'Test Group 2', 1);
insert into security_groups (group_id, description, create_user_id) values (1000003, 'Test Group 3', 1);
insert into terms_of_use_type (terms_of_use_type_id, terms_of_use_type_desc) values (10001, 'Test terms of use');
insert into terms_of_use (terms_of_use_id, terms_text, terms_of_use_type_id, title, electronically_signable, url) VALUES (10001, NULL, 10001, 'Test terms of use', 1, NULL);
