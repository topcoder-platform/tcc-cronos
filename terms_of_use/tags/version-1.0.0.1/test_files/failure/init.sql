INSERT INTO 'informix'.terms_of_use_type (terms_of_use_type_id, terms_of_use_type_desc) VALUES(1, 'type1');
INSERT INTO 'informix'.terms_of_use_type (terms_of_use_type_id, terms_of_use_type_desc) VALUES(2, 'type2');
INSERT INTO 'informix'.terms_of_use (terms_of_use_id,  terms_of_use_type_id, title, electronically_signable, member_agreeable) VALUES(1,  1, 'title1', 0, 0);
INSERT INTO 'informix'.terms_of_use (terms_of_use_id, terms_of_use_type_id, title, electronically_signable, member_agreeable) VALUES(2, 2, 'title2', 0, 0);
INSERT INTO 'informix'.user (user_id, handle, status) VALUES(1, 'handle1', '');
INSERT INTO 'informix'.user (user_id, handle, status) VALUES(2, 'handle2', '');
INSERT INTO 'informix'.user_terms_of_use_ban_xref (user_id, terms_of_use_id) VALUES(2, 1);
INSERT INTO 'informix'.user_terms_of_use_xref (user_id, terms_of_use_id) VALUES(2, 2);
INSERT INTO 'informix'.id_sequences (name, next_block_start, block_size, exhausted) VALUES ('idGenerator', 1000, 10000, 0);