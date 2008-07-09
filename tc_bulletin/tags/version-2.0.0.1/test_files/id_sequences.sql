
CREATE TABLE id_sequences (
    name                VARCHAR(255) NOT NULL,
    next_block_start    INT8 NOT NULL,
    block_size          INTEGER NOT NULL,
    exhausted		INTEGER DEFAULT 0 NOT NULL
);

alter table id_sequences add constraint primary key
    (name)
    constraint id_sequences_pk;

INSERT INTO id_sequences (name, next_block_start, block_size, exhausted) VALUES ('tcbulletin', 1, 10, 0);