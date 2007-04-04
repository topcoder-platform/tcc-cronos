CREATE TABLE id_sequences (
	name VARCHAR(255) NOT NULL,
	PRIMARY KEY (name),
	next_block_start DEC(18) NOT NULL,
	block_size INT NOT NULL,
	exhausted DEC(1) default 0
); 


INSERT INTO id_sequences (name, next_block_start, block_size) VALUES ("AuditPersistence", 1, 20);