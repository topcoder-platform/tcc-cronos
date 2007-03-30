DROP TABLE id_sequences;
DROP TABLE profile_property;
DROP TABLE profile;
DROP TABLE property;
DROP TABLE user_profile_property;
DROP TABLE user_profile;
DROP TABLE user_property;

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

CREATE TABLE profile (
       profile_id DEC(18) NOT NULL,
       username       VARCHAR(64) NOT NULL UNIQUE
);
ALTER TABLE profile ADD CONSTRAINT PRIMARY KEY (profile_id);

CREATE TABLE property (
       property_id         DEC(18) NOT NULL,
       name                VARCHAR(64) NOT NULL
);
ALTER TABLE property ADD CONSTRAINT PRIMARY KEY (property_id);

CREATE TABLE profile_property (
       profile_id          DEC(18) NOT NULL,
       property_id         DEC(18) NOT NULL,
       value               VARCHAR(64) NOT NULL
);
ALTER TABLE profile_property ADD CONSTRAINT FOREIGN KEY (profile_id) REFERENCES profile;
ALTER TABLE profile_property ADD CONSTRAINT FOREIGN KEY (property_id) REFERENCES property;

CREATE TABLE user_profile (
       user_profile_id         DEC(18) NOT NULL,
       user_profile_name       VARCHAR(64) NOT NULL UNIQUE
);
ALTER TABLE user_profile ADD CONSTRAINT PRIMARY KEY (user_profile_id);

CREATE TABLE user_property (
       user_property_id         DEC(18) NOT NULL,
       user_property_name                VARCHAR(64) NOT NULL
);
ALTER TABLE user_property ADD CONSTRAINT PRIMARY KEY (user_property_id);

CREATE TABLE user_profile_property (
       profile_property_profile_id          DEC(18) NOT NULL,
       profile_property_property_id         DEC(18) NOT NULL,
       user_value               VARCHAR(64) NOT NULL
);
ALTER TABLE user_profile_property ADD CONSTRAINT FOREIGN KEY (profile_property_profile_id) REFERENCES user_profile;
ALTER TABLE user_profile_property ADD CONSTRAINT FOREIGN KEY (profile_property_property_id) REFERENCES user_property;
