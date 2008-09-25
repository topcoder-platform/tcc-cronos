delete from client_user_xref;;
delete from project_user_xref;;
delete from project;;
delete from client;;
delete from project_status;;
delete from client_status;;
delete from company;;
insert into client_status(id, name, is_deleted) values (9991, "client_status", 0);;
insert into project_status(id, name, is_deleted) values (9991, "project_status", 0);;

insert into client(id, client_status_id, name, is_deleted, payment_term_id, salestax) values (1, 9991, "client1", 0, 1000, 12);;
insert into client(id, client_status_id, name, is_deleted, payment_term_id, salestax) values (2, 9991, "client2", 0, 1000, 13);;
insert into client(id, client_status_id, name, is_deleted, payment_term_id, salestax) values (3, 9991, "client3", 0, 1000, 14);;
insert into client(id, client_status_id, name, is_deleted, payment_term_id, salestax) values (4, 9991, "client4", 1, 1000, 15);;
insert into client(id, client_status_id, name, is_deleted, payment_term_id, salestax) values (5, 9991, "client5", 1, 1000, 161);;

insert into project(id, client_id, project_status_id, name, is_deleted, active, sales_tax, payment_terms_id) values (1, 1, 9991, "project1", 0, 1, 45, 1000);;
insert into project(id, client_id, project_status_id, name, is_deleted, active, sales_tax, payment_terms_id) values (2, 1, 9991, "project2", 0, 1, 46, 1000);;
insert into project(id, client_id, project_status_id, name, is_deleted, active, sales_tax, payment_terms_id) values (3, 1, 9991, "project3", 1, 0, 47, 1000);;
insert into project(id, client_id, project_status_id, name, is_deleted, active, sales_tax, payment_terms_id) values (4, 1, 9991, "project4", 1, 0, 481, 1000);;
insert into project(id, client_id, project_status_id, name, is_deleted, active, sales_tax, payment_terms_id) values (5, 1, 9991, "project4", 1, 0, 48, 1000);;

insert into client_user_xref (id, client_id, user_id, is_deleted) values (1000, 1, 130, 0);;
insert into client_user_xref (id, client_id, user_id, is_deleted) values (1001, 1, 131, 0);;
insert into client_user_xref (id, client_id, user_id, is_deleted) values (1002, 1, 132, 0);;
insert into client_user_xref (id, client_id, user_id, is_deleted) values (1003, 2, 333, 0);;
insert into client_user_xref (id, client_id, user_id, is_deleted) values (1004, 3, 333, 0);;
insert into client_user_xref (id, client_id, user_id, is_deleted) values (1005, 4, 333, 0);;
insert into client_user_xref (id, client_id, user_id, is_deleted) values (1006, 5, 333, 0);;

insert into project_user_xref (id, project_id, user_id, is_deleted) values (1000, 1, 230, 0);;
insert into project_user_xref (id, project_id, user_id, is_deleted) values (1001, 1, 231, 0);;
insert into project_user_xref (id, project_id, user_id, is_deleted) values (1002, 1, 232, 0);;

insert into project_user_xref (id, project_id, user_id, is_deleted) values (1003, 22, 444, 0);;
insert into project_user_xref (id, project_id, user_id, is_deleted) values (1004, 33, 444, 0);;
insert into project_user_xref (id, project_id, user_id, is_deleted) values (1005, 44, 444, 0);;
