CREATE TABLE id_sequences (
      name                VARCHAR(255) NOT NULL,
      PRIMARY KEY(name),
     next_block_start    integer NOT NULL,
     block_size          INT NOT NULL,
     exhausted           INT NOT NULL
);


INSERT INTO id_sequences(name, next_block_start, block_size, exhausted) VALUES ('sysuser', 1, 20, 0);
INSERT INTO id_sequences(name, next_block_start, block_size, exhausted) VALUES ('Principal', 1, 10, 0);
INSERT INTO id_sequences(name, next_block_start, block_size, exhausted) VALUES ('SecurityRole', 1, 10, 0);
INSERT INTO id_sequences(name, next_block_start, block_size, exhausted) VALUES ('Action', 1, 10, 0);
INSERT INTO id_sequences(name, next_block_start, block_size, exhausted) VALUES ('ActionContext', 1, 10, 0);