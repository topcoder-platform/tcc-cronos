
CREATE TABLE company ( 
    company_id       	INTEGER NOT NULL,
    name             	VARCHAR(64),
    passcode         	VARCHAR(64) NOT NULL,
    creation_date    	DATETIME YEAR to SECOND NOT NULL,
    creation_user    	VARCHAR(64) NOT NULL,
    modification_date	DATETIME YEAR to SECOND NOT NULL,
    modification_user	VARCHAR(64) NOT NULL,
    PRIMARY KEY(company_id)
)


CREATE TABLE cut_off_time ( 
    cut_off_time_id  	INTEGER NOT NULL,
    company_id       	INTEGER NOT NULL,
    cut_off_time     	DATETIME YEAR to SECOND NOT NULL,
    creation_date    	DATETIME YEAR to SECOND NOT NULL,
    creation_user    	VARCHAR(64) NOT NULL,
    modification_date	DATETIME YEAR to SECOND NOT NULL,
    modification_user	VARCHAR(64) NOT NULL,
    PRIMARY KEY(cut_off_time_id)
)

ALTER TABLE cut_off_time
    ADD CONSTRAINT ( FOREIGN KEY(company_id)
	REFERENCES company(company_id) CONSTRAINT fk_cut_off_time_company )


CREATE TABLE id_sequences ( 
    name            	VARCHAR(255) NOT NULL,
    next_block_start	INTEGER NOT NULL,
    block_size      	INTEGER NOT NULL,
    exhausted       	INTEGER NOT NULL,
    PRIMARY KEY(name)
)


INSERT INTO id_sequences(name, next_block_start, block_size, exhausted) 
    VALUES('cut_off_time', 0, 20, 0)





