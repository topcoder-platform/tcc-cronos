
CREATE TABLE id_sequences (
    name                VARCHAR(255) NOT NULL,
    next_block_start    INT8 NOT NULL,
    block_size          INTEGER NOT NULL,
    exhausted		INTEGER DEFAULT 0 NOT NULL
);

alter table id_sequences add constraint primary key
    (name)
    constraint id_sequences_pk;

INSERT INTO id_sequences (name, next_block_start, block_size, exhausted) VALUES ('UnitTest_IDGenerator', 1, 10, 0);

INSERT INTO id_sequences (name, next_block_start, block_size, exhausted) VALUES ('DigitalRunPointsStatus_IDGenerator', 1, 10, 0);

INSERT INTO id_sequences (name, next_block_start, block_size, exhausted) VALUES ('DigitalRunPointsOperation_IDGenerator', 1, 10, 0);

INSERT INTO id_sequences (name, next_block_start, block_size, exhausted) VALUES ('DigitalRunPointsReferenceType_IDGenerator', 1, 10, 0);

INSERT INTO id_sequences (name, next_block_start, block_size, exhausted) VALUES ('DigitalRunPointsType_IDGenerator', 1, 10, 0);

INSERT INTO id_sequences (name, next_block_start, block_size, exhausted) VALUES ('TrackContestType_IDGenerator', 1, 10, 0);

INSERT INTO id_sequences (name, next_block_start, block_size, exhausted) VALUES ('TrackStatus_IDGenerator', 1, 10, 0);

INSERT INTO id_sequences (name, next_block_start, block_size, exhausted) VALUES ('TrackType_IDGenerator', 1, 10, 0);

INSERT INTO id_sequences (name, next_block_start, block_size, exhausted) VALUES ('PointsCalculator_IDGenerator', 1, 10, 0);

INSERT INTO id_sequences (name, next_block_start, block_size, exhausted) VALUES ('TrackContestResultCalculator_IDGenerator', 1, 10, 0);

INSERT INTO id_sequences (name, next_block_start, block_size, exhausted) VALUES ('Track_IDGenerator', 1, 10, 0);

INSERT INTO id_sequences (name, next_block_start, block_size, exhausted) VALUES ('TrackContest_IDGenerator', 1, 10, 0);

INSERT INTO id_sequences (name, next_block_start, block_size, exhausted) VALUES ('DigitalRunPoints_IDGenerator', 1, 10, 0);
