INSERT INTO id_sequences (name, next_block_start, block_size, exhausted) VALUES ('authorization', 1, 10000, 0);
INSERT INTO id_sequences (name, next_block_start, block_size, exhausted) VALUES ('payment', 1, 10000, 0);
INSERT INTO id_sequences (name, next_block_start, block_size, exhausted) VALUES ('paymentOperation', 1, 10000, 0);
INSERT INTO authorizations(id, multiple_use, token_id, amount_left, fixed_amount, cancelled) VALUES(1000, 1, '100', 5000, 5000, 1);
INSERT INTO payments(id, authorization_id, amount, transaction_id, status) VALUES(1000, 1000, 5000, '1', 'COMPLETED');