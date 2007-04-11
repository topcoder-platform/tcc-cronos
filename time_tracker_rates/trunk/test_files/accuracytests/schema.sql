create table rate  (
  rate_id              INTEGER                         not null,
  description          VARCHAR(255),
  creation_date        DATETIME YEAR TO SECOND         not null,
  creation_user        VARCHAR(64)                     not null,
  modification_date    DATETIME YEAR TO SECOND         not null,
  modification_user    VARCHAR(64)                     not null,
primary key (rate_id)
      constraint pk_rate
);

create table comp_rate  (
  company_id           INTEGER                         not null,
  rate_id      		   INTEGER                         not null,
  rate                 MONEY (16,3)                    not null,
  creation_date        DATETIME YEAR TO SECOND         not null,
  creation_user        VARCHAR(64)                     not null,
  modification_date    DATETIME YEAR TO SECOND         not null,
  modification_user    VARCHAR(64)                     not null,
primary key (company_id, rate_id)
      constraint pk_comp_rate
);
