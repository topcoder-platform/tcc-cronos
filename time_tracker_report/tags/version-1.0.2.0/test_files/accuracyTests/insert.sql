INSERT INTO users VALUES (1,'admin','2006-01-01 00:00:00','admin','2006-01-01 00:00:00','admin','User Store 1');
INSERT INTO users VALUES (2,'user','2006-01-01 00:00:00','admin','2006-01-01 00:00:00','admin','User Store 2');

INSERT INTO tasktypes VALUES (1,'Task Type 1','2006-01-01 00:00:00','admin','2006-01-01 00:00:00','admin');
INSERT INTO tasktypes VALUES (2,'Task Type 2','2006-01-01 00:00:00','admin','2006-01-01 00:00:00','admin');

INSERT INTO timestatuses VALUES (1,'Time Status 1','2006-01-01 00:00:00','admin','2006-01-01 00:00:00','admin');
INSERT INTO timestatuses VALUES (2,'Time Status 2','2006-01-01 00:00:00','admin','2006-01-01 00:00:00','admin');

INSERT INTO timeentries VALUES (1,1,1,'Time Entry 1','2006-01-01 00:00:00',10,1,'2006-01-01 00:00:00','admin','2006-01-01 00:00:00','admin');
INSERT INTO timeentries VALUES (2,2,2,'Time Entry 2','2006-01-01 00:00:00',20,2,'2006-01-01 00:00:00','admin','2006-01-01 00:00:00','admin');

INSERT INTO expensestatuses VALUES (1,'Expense Status 1','2006-01-01 00:00:00','admin','2006-01-01 00:00:00','admin');
INSERT INTO expensestatuses VALUES (2,'Expense Status 2','2006-01-01 00:00:00','admin','2006-01-01 00:00:00','admin');

INSERT INTO expensetypes VALUES (1,'Expense Type 1','2006-01-01 00:00:00','admin','2006-01-01 00:00:00','admin');
INSERT INTO expensetypes VALUES (2,'Expense Type 2','2006-01-01 00:00:00','admin','2006-01-01 00:00:00','admin');

INSERT INTO expenseentries VALUES (1,1,1,'Expense Entry 1','2006-01-01 00:00:00',100,1,'2006-01-01 00:00:00','admin','2006-01-01 00:00:00','admin');
INSERT INTO expenseentries VALUES (2,2,2,'Expense Entry 2','2006-02-01 00:00:00',200,2,'2006-01-01 00:00:00','admin','2006-01-01 00:00:00','admin');

INSERT INTO projects VALUES (1,'Project Name 1','Project 1','2006-01-01 00:00:00','2006-01-10 00:00:00','2006-01-01 00:00:00','admin','2006-01-01 00:00:00','admin');
INSERT INTO projects VALUES (2,'Project Name 2','Project 2','2006-01-01 00:00:00','2006-01-10 00:00:00','2006-01-01 00:00:00','admin','2006-01-01 00:00:00','admin');

INSERT INTO clients VALUES (1,'Client 1','2006-01-01 00:00:00','admin','2006-01-01 00:00:00','admin');
INSERT INTO clients VALUES (2,'Client 2','2006-01-01 00:00:00','admin','2006-01-01 00:00:00','admin');

INSERT INTO clientprojects VALUES (1,1,'2006-01-01 00:00:00','admin','2006-01-01 00:00:00','admin');
INSERT INTO clientprojects VALUES (2,2,'2006-01-01 00:00:00','admin','2006-01-01 00:00:00','admin');

INSERT INTO projectexpenses VALUES (1,1,'2006-01-01 00:00:00','admin','2006-01-01 00:00:00','admin');
INSERT INTO projectexpenses VALUES (2,2,'2006-01-01 00:00:00','admin','2006-01-01 00:00:00','admin');

INSERT INTO projectmanagers VALUES (1,1,'2006-01-01 00:00:00','admin','2006-01-01 00:00:00','admin');
INSERT INTO projectmanagers VALUES (2,2,'2006-01-01 00:00:00','admin','2006-01-01 00:00:00','admin');

INSERT INTO projecttimes VALUES (1,1,'2006-01-01 00:00:00','admin','2006-01-01 00:00:00','admin');
INSERT INTO projecttimes VALUES (2,2,'2006-01-01 00:00:00','admin','2006-01-01 00:00:00','admin');

INSERT INTO projectworkers VALUES (1,1,'2006-01-01 00:00:00','2006-01-10 00:00:00','1000','2006-01-01 00:00:00','admin','2006-01-01 00:00:00','admin');
INSERT INTO projectworkers VALUES (2,2,'2006-02-01 00:00:00','2006-02-10 00:00:00','2000','2006-01-01 00:00:00','admin','2006-01-01 00:00:00','admin');
