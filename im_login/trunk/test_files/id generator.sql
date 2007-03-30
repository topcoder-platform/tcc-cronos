CREATE TABLE id_sequences (
    name                VARCHAR(255) NOT NULL  PRIMARY KEY,
    next_block_start    INT8 NOT NULL,
    block_size          INT NOT NULL,
    exhausted		int default 0
);

INSERT INTO id_sequences (name, next_block_start, block_size, exhausted) VALUES ('profileKeyGenerator', 0, 10, 0);
INSERT INTO id_sequences (name, next_block_start, block_size, exhausted) VALUES ('propertyKeyGenerator', 0, 10, 0);
INSERT INTO id_sequences (name, next_block_start, block_size, exhausted) VALUES ('profile', 0, 10, 0);
INSERT INTO id_sequences (name, next_block_start, block_size, exhausted) VALUES ('property', 0, 10, 0);

INSERT INTO id_sequences (name, next_block_start, block_size, exhausted) 
VALUES ('com.topcoder.security.authorization.Principal', 0, 10, 0);

INSERT INTO id_sequences (name, next_block_start, block_size, exhausted) 
VALUES ('com.topcoder.security.authorization.SecurityRole', 0, 10, 0);

INSERT INTO id_sequences (name, next_block_start, block_size, exhausted) 
VALUES ('com.topcoder.security.authorization.Action', 0, 10, 0);


INSERT INTO id_sequences (name, next_block_start, block_size, exhausted) 
VALUES ('com.topcoder.security.authorization.ActionContext', 0, 10, 0);