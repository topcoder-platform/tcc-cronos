-- create table for IDGenerator 3.0
CREATE TABLE id_sequences (
	name VARCHAR(255) NOT NULL,
	PRIMARY KEY (name),
	next_block_start integer NOT NULL,
	block_size INT NOT NULL,
	exhausted int not null
);

-- this is the required row for Time Tracker User component.
INSERT INTO id_sequences (name, next_block_start, block_size, exhausted)
VALUES ('TimeTrackerUser', 1, 20, 0)