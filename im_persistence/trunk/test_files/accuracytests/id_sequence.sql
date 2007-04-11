CREATE TABLE id_sequences (
        name                    VARCHAR(255) NOT NULL,
                                PRIMARY KEY (name),
        next_block_start        INT8 NOT NULL,
        block_size              INTEGER NOT NULL,
        exhausted               INTEGER default 0
);

INSERT INTO id_sequences (name, next_block_start, block_size) values ('generator', 1, 20);