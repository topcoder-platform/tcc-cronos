CREATE TABLE id_sequences (
    name                VARCHAR(255) NOT NULL  PRIMARY KEY,
    next_block_start    BIGINT NOT NULL,
    block_size          INT NOT NULL,
    exhausted    int NOT NULL default 0
);                            