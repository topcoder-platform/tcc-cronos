CREATE TABLE reject_reason (
       reject_reason_id          integer NOT NULL,
       description          varchar(256) NOT NULL,
       creation_date         datetime year to second NOT NULL,
       creation_user         varchar(64) NOT NULL,
       modification_date     datetime year to second NOT NULL,
       modification_user     varchar(64) NOT NULL,
       PRIMARY KEY (reject_reason_id)
);
