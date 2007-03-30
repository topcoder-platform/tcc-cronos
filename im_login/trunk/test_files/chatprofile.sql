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



insert into profile values (1, "admin");
insert into profile values (2, "junit_user");
insert into profile values (3, "manager");

