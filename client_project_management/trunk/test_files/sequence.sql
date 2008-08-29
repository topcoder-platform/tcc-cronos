
CREATE TABLE id_sequences (
        name                    VARCHAR(255) NOT NULL,
                                PRIMARY KEY (name),
        next_block_start        INT8 NOT NULL,
        block_size              INTEGER NOT NULL,
        exhausted               INTEGER default 0
);

INSERT INTO id_sequences (name, next_block_start, block_size) values ('client_id', 1, 20);
INSERT INTO id_sequences (name, next_block_start, block_size) values ('client_status_id', 1, 20);

INSERT INTO id_sequences (name, next_block_start, block_size) values ('test', 1, 20);

INSERT INTO id_sequences (name, next_block_start, block_size) values ('com.topcoder.clients.manager.dao.DAOCompanyManager', 1, 20);
INSERT INTO id_sequences (name, next_block_start, block_size) values ('com.topcoder.clients.manager.dao.DAOClientManager', 1, 20);
INSERT INTO id_sequences (name, next_block_start, block_size) values ('com.topcoder.clients.manager.dao.DAOProjectManager', 1, 20);
