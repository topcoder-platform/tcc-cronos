--
-- to be executed in corporate_oltp
--
create table permission_type (
    permission_type_id decimal(10) NOT NULL,
    name varchar(254) NOT NULL

);

create table user_permission_grant (
    user_permission_grant_id decimal(10) NOT NULL,
    user_id decimal(10,0) NOT NULL,
    resource_id decimal(10,0) NOT NULL,
    permission_type_id decimal(10) NOT NULL,
    is_studio smallint
);

create sequence PERMISSION_TYPE_SEQ;
create sequence PERMISSION_SEQ;
    

ALTER TABLE permission_type
        ADD CONSTRAINT ( PRIMARY KEY (permission_type_id) CONSTRAINT PK_PERMISSION_TYPE);

ALTER TABLE user_permission_grant
        ADD CONSTRAINT ( PRIMARY KEY (user_permission_grant_id) CONSTRAINT PK_USER_PERMISSION_GRANT_ID);


ALTER TABLE user_permission_grant
        ADD CONSTRAINT ( FOREIGN KEY(permission_type_id)
        REFERENCES permission_type(permission_type_id) CONSTRAINT FK_USER_PERMISSION_GRANT1);

insert into permission_type(permission_type_id, name)
select permission_type_id, name
from studio_oltp:permission_type;

insert into user_permission_grant(user_permission_grant_id, user_id, resource_id, permission_type_id, is_studio)
select user_permission_grant_id, user_id, project_id, permission_type_id, 1 as is_studio
from studio_oltp:user_permission_grant;
