drop table system_configuration;

CREATE TABLE system_configuration (
id BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY,
name VARCHAR(45) NOT NULL,
value VARCHAR(45) NOT NULL,
PRIMARY KEY(id)
);