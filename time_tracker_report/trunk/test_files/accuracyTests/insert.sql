insert into company values(1, 'admin', 'a', current, 'a', current, 'a');
insert into company values(2, 'user', 'b', current, 'a', current, 'a');

insert into account_status values(1, 'desc', current, 'a', current,'a');
insert into account_status values(2, 'desc', current, 'a', current,'a');

INSERT INTO user_account VALUES (1, 1, 1, 'admin', 'a', '2006-01-01 00:00:00','admin','2006-01-01 00:00:00','admin');
INSERT INTO user_account VALUES (2, 1, 1, 'user', 'a', '2006-01-01 00:00:00','admin','2006-01-01 00:00:00','admin');

INSERT INTO task_type VALUES (1,'Task Type 1', 0, '2006-01-01 00:00:00','admin','2006-01-01 00:00:00','admin');
INSERT INTO task_type VALUES (2,'Task Type 2', 0, '2006-01-01 00:00:00','admin','2006-01-01 00:00:00','admin');

INSERT INTO time_status VALUES (1,'Time Status 1','2006-01-01 00:00:00','admin','2006-01-01 00:00:00','admin');
INSERT INTO time_status VALUES (2,'Time Status 2','2006-01-01 00:00:00','admin','2006-01-01 00:00:00','admin');

INSERT INTO time_entry VALUES (1,1,1,1,'Time Entry 1','2006-01-01 00:00:00',10,1,'2006-01-01 00:00:00','admin','2006-01-01 00:00:00','admin');
INSERT INTO time_entry VALUES (2,2,2,2,'Time Entry 2','2006-01-01 00:00:00',20,2,'2006-01-01 00:00:00','admin','2006-01-01 00:00:00','admin');

INSERT INTO expense_status VALUES (1,'Expense Status 1','2006-01-01 00:00:00','admin','2006-01-01 00:00:00','admin');
INSERT INTO expense_status VALUES (2,'Expense Status 2','2006-01-01 00:00:00','admin','2006-01-01 00:00:00','admin');

INSERT INTO expense_type VALUES (1,'Expense Type 1',0,'2006-01-01 00:00:00','admin','2006-01-01 00:00:00','admin');
INSERT INTO expense_type VALUES (2,'Expense Type 2',0,'2006-01-01 00:00:00','admin','2006-01-01 00:00:00','admin');

INSERT INTO expense_entry VALUES (1,1,1,1,'Expense Entry 1','2006-01-01 00:00:00',100,1,'2006-01-01 00:00:00','admin','2006-01-01 00:00:00','admin');
INSERT INTO expense_entry VALUES (2,2,2,1,'Expense Entry 2','2006-02-01 00:00:00',200,2,'2006-01-01 00:00:00','admin','2006-01-01 00:00:00','admin');

INSERT INTO project VALUES (1,1,'Project Name 1','Project 1','2006-01-01 00:00:00','2006-01-10 00:00:00','2006-01-01 00:00:00','admin','2006-01-01 00:00:00','admin');
INSERT INTO project VALUES (2,1,'Project Name 2','Project 2','2006-01-01 00:00:00','2006-01-10 00:00:00','2006-01-01 00:00:00','admin','2006-01-01 00:00:00','admin');

INSERT INTO client VALUES (1,'Client 1',1,'2006-01-01 00:00:00','admin','2006-01-01 00:00:00','admin');
INSERT INTO client VALUES (2,'Client 2',1,'2006-01-01 00:00:00','admin','2006-01-01 00:00:00','admin');

INSERT INTO client_project VALUES (1,1,'2006-01-01 00:00:00','admin','2006-01-01 00:00:00','admin');
INSERT INTO client_project VALUES (2,2,'2006-01-01 00:00:00','admin','2006-01-01 00:00:00','admin');

INSERT INTO project_expense VALUES (1,1,'2006-01-01 00:00:00','admin','2006-01-01 00:00:00','admin');
INSERT INTO project_expense VALUES (2,2,'2006-01-01 00:00:00','admin','2006-01-01 00:00:00','admin');

INSERT INTO project_manager VALUES (1,1,'2006-01-01 00:00:00','admin','2006-01-01 00:00:00','admin');
INSERT INTO project_manager VALUES (2,2,'2006-01-01 00:00:00','admin','2006-01-01 00:00:00','admin');

INSERT INTO project_time VALUES (1,1,'2006-01-01 00:00:00','admin','2006-01-01 00:00:00','admin');
INSERT INTO project_time VALUES (2,2,'2006-01-01 00:00:00','admin','2006-01-01 00:00:00','admin');

INSERT INTO project_worker VALUES (1,1,'2006-01-01 00:00:00','2006-01-10 00:00:00',1000,'2006-01-01 00:00:00','admin','2006-01-01 00:00:00','admin');
INSERT INTO project_worker VALUES (2,2,'2006-02-01 00:00:00','2006-02-10 00:00:00',2000,'2006-01-01 00:00:00','admin','2006-01-01 00:00:00','admin');
