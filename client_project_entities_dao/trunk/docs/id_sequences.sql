create table id_sequences (
    name VARCHAR(255) not null,
    next_block_start INT not null,
    block_size INT not null,
    exhausted INT not null
)
extent size 16 next size 16
lock mode row;

alter table id_sequences add constraint primary key 
	(name)
	constraint u100_1;
	
  
INSERT INTO id_sequences (name, next_block_start, block_size, exhausted)
VALUES ('com.topcoder.clients.model.User', 1, 20, 0);
INSERT INTO id_sequences (name, next_block_start, block_size, exhausted)
VALUES ('client_id', 1, 20, 0);
INSERT INTO id_sequences (name, next_block_start, block_size, exhausted)
VALUES ('client_status_id', 1, 20, 0);
INSERT INTO id_sequences (name, next_block_start, block_size, exhausted)
VALUES ('test', 1, 20, 0);
INSERT INTO id_sequences (name, next_block_start, block_size, exhausted)
       VALUES ('com.topcoder.clients.manager.dao.DAOCompanyManager', 1, 20, 0);
INSERT INTO id_sequences (name, next_block_start, block_size, exhausted)
       VALUES ('com.topcoder.clients.manager.dao.DAOClientManager', 1, 20, 0);
INSERT INTO id_sequences (name, next_block_start, block_size, exhausted)
       VALUES ('com.topcoder.clients.manager.dao.DAOProjectManager', 1, 20, 0);
