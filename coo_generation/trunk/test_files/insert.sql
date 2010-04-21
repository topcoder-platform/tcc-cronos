insert into user (user_id, handle, status) values (20, "dok", "A");
insert into categories values(10, 20, "Java", "desc", 1, 1, 'f');
insert into project values (1, 1, 10, 10, "a", CURRENT, "a", CURRENT);
insert into project_category_lu values (10, 10, "a", "a", "a", CURRENT, "a", CURRENT);
insert into comp_catalog values (20, 1.0, "c", "abc", "abc", "abc", CURRENT, 1.0, 10, CURRENT, 0);
insert into project_phase values (1, 1, 10, 2, null, CURRENT, CURRENT, CURRENT, CURRENT, 12, "a", CURRENT, "a", CURRENT);

insert into project_info values (1, 1, "20", "a", CURRENT, "a", CURRENT);
insert into project_info values (1, 2, "20", "a", CURRENT, "a", CURRENT);
insert into project_info values (1, 3, "20", "a", CURRENT, "a", CURRENT);
insert into project_info values (1, 6, "20", "a", CURRENT, "a", CURRENT);
insert into project_info values (1, 7, "20", "a", CURRENT, "a", CURRENT);
insert into project_info values (1, 8, "http://localhost:8080", "a", CURRENT, "a", CURRENT);
insert into project_info values (1, 23, "20", "a", CURRENT, "a", CURRENT);
insert into project_info values (1, 24, "20", "a", CURRENT, "a", CURRENT);


insert into resource values(2, 4, 1, 10, "a", CURRENT, "a", CURRENT);
insert into resource values(1, 13, null, null, "132456", CURRENT, "132456", CURRENT);

insert into resource_info values(1, 1, "132456", "132456", CURRENT, "132456", CURRENT);
insert into resource_info values(1, 2, "132456", "a", CURRENT, "a", CURRENT);
insert into resource_info values(2, 2, "132456", "a", CURRENT, "a", CURRENT);