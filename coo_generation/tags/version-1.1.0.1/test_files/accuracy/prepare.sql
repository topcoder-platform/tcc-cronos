insert into third_party_library values(
"Antlr","description", "2.7.6","www.antlr.org","Antlr 3 License (BSD Style)","Parser Generator","path","alias","note","JAVA");
insert into third_party_library values(
"junit","description", "3.8","www.junit.org","CPL","Unit testing","path","alias","note","JAVA");


insert into user (user_id, handle, status) values (20, "sparemax", "A");
insert into user (user_id, handle, status) values (21, "EveningSun", "A");
insert into categories values(10, 20, "Java", "desc", 1, 1, 'f');
insert into project values (1, 1, 10, 10, "a", CURRENT, "a", CURRENT);
insert into project_category_lu values (10, 10, "Development", "a", "a", CURRENT, "a", CURRENT);
insert into comp_catalog values (20, 1.0, "c", "abc", "abc", "abc", CURRENT, 1.0, 10, CURRENT, 0);
insert into project_phase values (1, 1, 10, 2, null, CURRENT, CURRENT, CURRENT, CURRENT, 12, "a", CURRENT, "a", CURRENT);

insert into project_info values (1, 1, "20", "a", CURRENT, "a", CURRENT);
insert into project_info values (1, 2, "20", "a", CURRENT, "a", CURRENT);
insert into project_info values (1, 3, "20", "a", CURRENT, "a", CURRENT);
insert into project_info values (1, 6, "HBS Singleton Process Manager", "a", CURRENT, "a", CURRENT);
insert into project_info values (1, 7, "1.0.0", "a", CURRENT, "a", CURRENT);
insert into project_info values (1, 8, "http://localhost:8080", "a", CURRENT, "a", CURRENT);
insert into project_info values (1, 23, "20", "a", CURRENT, "a", CURRENT);
insert into project_info values (1, 24, "21", "a", CURRENT, "a", CURRENT);


insert into resource values(2, 4, 1, 10, "a", CURRENT, "a", CURRENT);
insert into resource values(3, 5, 1, 10, "a", CURRENT, "a", CURRENT);
insert into resource values(4, 6, 1, 10, "a", CURRENT, "a", CURRENT);

insert into resource values(1, 13, null, null, "132456", CURRENT, "132456", CURRENT);

insert into resource_info values(1, 1, "", "132456", CURRENT, "132456", CURRENT);
insert into resource_info values(1, 2, "", "a", CURRENT, "a", CURRENT);
insert into resource_info values(2, 2, "FireIce", "a", CURRENT, "a", CURRENT);
insert into resource_info values(3, 2, "bbxiong", "a", CURRENT, "a", CURRENT);
insert into resource_info values(4, 2, "Veloerien", "a", CURRENT, "a", CURRENT);


