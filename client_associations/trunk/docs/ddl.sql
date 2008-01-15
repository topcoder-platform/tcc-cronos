create table client (
    client_id INT not null,
    name VARCHAR(64) not null
);
alter table client add constraint primary key (client_id) constraint client_pk;


create table user_client (
    user_id DECIMAL(10,0) not null,
    client_id INT not null,
    admin_ind DECIMAL(1,0)
);
alter table user_client add constraint primary key (user_id, client_id) constraint user_client_pk;
alter table user_client add constraint foreign key (client_id) references client (client_id) constraint user_client_client_fk;


create table comp_client (
    component_id DECIMAL(12,0) not null,
    client_id INT not null
);
alter table comp_client add constraint primary key (component_id, client_id) constraint comp_client_pk;
alter table comp_client add constraint foreign key (client_id) references client (client_id) constraint comp_client_client_fk;