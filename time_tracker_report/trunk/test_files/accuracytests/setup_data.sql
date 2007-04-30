insert into client (client_id, name, company_id, creation_date, creation_user, modification_date, modification_user) values (1,"client1",101,CURRENT - INTERVAL(1) DAY to DAY,'creationUser1',CURRENT - INTERVAL(1) DAY to DAY,'modificationUser1');
insert into client (client_id, name, company_id, creation_date, creation_user, modification_date, modification_user) values (2,"client2",102,CURRENT - INTERVAL(2) DAY to DAY,'creationUser2',CURRENT - INTERVAL(2) DAY to DAY,'modificationUser2');


insert into project (project_id, name, company_id, description, start_date, end_date, creation_date, creation_user, modification_date, modification_user) values (1,"project1",101,"projectDesc1", CURRENT - INTERVAL(1) DAY to DAY, CURRENT + INTERVAL(1) DAY to DAY,CURRENT - INTERVAL(1) DAY to DAY,'creationUser1',CURRENT - INTERVAL(1) DAY to DAY,'modificationUser1');
insert into project (project_id, name, company_id, description, start_date, end_date, creation_date, creation_user, modification_date, modification_user) values (2,"project2",102,"projectDesc2", CURRENT - INTERVAL(2) DAY to DAY, CURRENT + INTERVAL(2) DAY to DAY,CURRENT - INTERVAL(2) DAY to DAY,'creationUser2',CURRENT - INTERVAL(2) DAY to DAY,'modificationUser2');
insert into project (project_id, name, company_id, description, start_date, end_date, creation_date, creation_user, modification_date, modification_user) values (3,"project3",103,"projectDesc3", CURRENT - INTERVAL(3) DAY to DAY, CURRENT + INTERVAL(3) DAY to DAY,CURRENT - INTERVAL(3) DAY to DAY,'creationUser3',CURRENT - INTERVAL(3) DAY to DAY,'modificationUser3');


insert into client_project (client_id, project_id, creation_date, creation_user, modification_date, modification_user) values (1,1,CURRENT - INTERVAL(1) DAY to DAY,'creationUser1',CURRENT - INTERVAL(1) DAY to DAY,'modificationUser1');
insert into client_project (client_id, project_id, creation_date, creation_user, modification_date, modification_user) values (1,2,CURRENT - INTERVAL(2) DAY to DAY,'creationUser2',CURRENT - INTERVAL(2) DAY to DAY,'modificationUser2');
insert into client_project (client_id, project_id, creation_date, creation_user, modification_date, modification_user) values (2,3,CURRENT - INTERVAL(3) DAY to DAY,'creationUser3',CURRENT - INTERVAL(3) DAY to DAY,'modificationUser3');





insert into user_account (user_account_id, company_id, account_status_id, user_name, password, creation_date, creation_user, modification_date, modification_user) values (1,101,1,"creationUser1","password1",CURRENT - INTERVAL(1) DAY to DAY,'creationUser1',CURRENT - INTERVAL(1) DAY to DAY,'modificationUser1');
insert into user_account (user_account_id, company_id, account_status_id, user_name, password, creation_date, creation_user, modification_date, modification_user) values (2,102,0,"creationUser2","password2",CURRENT - INTERVAL(2) DAY to DAY,'creationUser2',CURRENT - INTERVAL(2) DAY to DAY,'modificationUser2');
insert into user_account (user_account_id, company_id, account_status_id, user_name, password, creation_date, creation_user, modification_date, modification_user) values (3,103,2,"creationUser3","password3",CURRENT - INTERVAL(3) DAY to DAY,'creationUser3',CURRENT - INTERVAL(3) DAY to DAY,'modificationUser3');
insert into user_account (user_account_id, company_id, account_status_id, user_name, password, creation_date, creation_user, modification_date, modification_user) values (4,104,1,"creationUser4","password4",CURRENT - INTERVAL(4) DAY to DAY,'creationUser4',CURRENT - INTERVAL(4) DAY to DAY,'modificationUser4');
insert into user_account (user_account_id, company_id, account_status_id, user_name, password, creation_date, creation_user, modification_date, modification_user) values (5,105,1,"creationUser5","password5",CURRENT - INTERVAL(5) DAY to DAY,'creationUser5',CURRENT - INTERVAL(5) DAY to DAY,'modificationUser5');




insert into project_fix_bill (fix_bill_entry_id, project_id, creation_date, creation_user, modification_date, modification_user) values (1,1,CURRENT - INTERVAL(1) DAY to DAY,'creationUser1',CURRENT - INTERVAL(1) DAY to DAY,'modificationUser1');
insert into project_fix_bill (fix_bill_entry_id, project_id, creation_date, creation_user, modification_date, modification_user) values (2,1,CURRENT - INTERVAL(2) DAY to DAY,'creationUser2',CURRENT - INTERVAL(2) DAY to DAY,'modificationUser2');
insert into project_fix_bill (fix_bill_entry_id, project_id, creation_date, creation_user, modification_date, modification_user) values (3,2,CURRENT - INTERVAL(3) DAY to DAY,'creationUser3',CURRENT - INTERVAL(3) DAY to DAY,'modificationUser3');
insert into project_fix_bill (fix_bill_entry_id, project_id, creation_date, creation_user, modification_date, modification_user) values (4,2,CURRENT - INTERVAL(4) DAY to DAY,'creationUser4',CURRENT - INTERVAL(4) DAY to DAY,'modificationUser4');
insert into project_fix_bill (fix_bill_entry_id, project_id, creation_date, creation_user, modification_date, modification_user) values (5,3,CURRENT - INTERVAL(5) DAY to DAY,'creationUser5',CURRENT - INTERVAL(5) DAY to DAY,'modificationUser5');


insert into fix_bill_entry (fix_bill_entry_id, company_id, invoice_id, fix_bill_type_id, fix_bill_status_id, description, entry_date, amount, creation_date, creation_user, modification_date, modification_user) values (1,101,1,1,1,"fixbillentryDesc1", 
DATE("2006-12-31") - INTERVAL(1) DAY to DAY, 1.0, CURRENT - INTERVAL(1) DAY to DAY,'creationUser1',CURRENT - INTERVAL(1) DAY to DAY,'modificationUser1');
insert into fix_bill_entry (fix_bill_entry_id, company_id, invoice_id, fix_bill_type_id, fix_bill_status_id, description, entry_date, amount, creation_date, creation_user, modification_date, modification_user) values (2,102,2,2,2,"fixbillentryDesc2", DATE("2006-12-31") - INTERVAL(2) DAY to DAY, 2.0, CURRENT - INTERVAL(2) DAY to DAY,'creationUser2',CURRENT - INTERVAL(2) DAY to DAY,'modificationUser2');
insert into fix_bill_entry (fix_bill_entry_id, company_id, invoice_id, fix_bill_type_id, fix_bill_status_id, description, entry_date, amount, creation_date, creation_user, modification_date, modification_user) values (3,103,3,3,3,"fixbillentryDesc3", DATE("2006-12-31") - INTERVAL(3) DAY to DAY, 3.0, CURRENT - INTERVAL(3) DAY to DAY,'creationUser3',CURRENT - INTERVAL(3) DAY to DAY,'modificationUser3');
insert into fix_bill_entry (fix_bill_entry_id, company_id, invoice_id, fix_bill_type_id, fix_bill_status_id, description, entry_date, amount, creation_date, creation_user, modification_date, modification_user) values (4,104,4,4,4,"fixbillentryDesc4", DATE("2006-12-31") - INTERVAL(4) DAY to DAY, 4.0, CURRENT - INTERVAL(4) DAY to DAY,'creationUser4',CURRENT - INTERVAL(4) DAY to DAY,'modificationUser4');
insert into fix_bill_entry (fix_bill_entry_id, company_id, invoice_id, fix_bill_type_id, fix_bill_status_id, description, entry_date, amount, creation_date, creation_user, modification_date, modification_user) values (5,105,5,5,5,"fixbillentryDesc5", DATE("2006-12-31") - INTERVAL(5) DAY to DAY, 5.0, CURRENT - INTERVAL(5) DAY to DAY,'creationUser5',CURRENT - INTERVAL(5) DAY to DAY,'modificationUser5');


insert into fix_bill_status (fix_bill_status_id, description, creation_date, creation_user, modification_date, modification_user) values (1,"fixbillstatusDesc1", CURRENT - INTERVAL(1) DAY to DAY,'creationUser1',CURRENT - INTERVAL(1) DAY to DAY,'modificationUser1');
insert into fix_bill_status (fix_bill_status_id, description, creation_date, creation_user, modification_date, modification_user) values (2,"fixbillstatusDesc2", CURRENT - INTERVAL(2) DAY to DAY,'creationUser2',CURRENT - INTERVAL(2) DAY to DAY,'modificationUser2');
insert into fix_bill_status (fix_bill_status_id, description, creation_date, creation_user, modification_date, modification_user) values (3,"fixbillstatusDesc3", CURRENT - INTERVAL(3) DAY to DAY,'creationUser3',CURRENT - INTERVAL(3) DAY to DAY,'modificationUser3');
insert into fix_bill_status (fix_bill_status_id, description, creation_date, creation_user, modification_date, modification_user) values (4,"fixbillstatusDesc4", CURRENT - INTERVAL(4) DAY to DAY,'creationUser4',CURRENT - INTERVAL(4) DAY to DAY,'modificationUser4');
insert into fix_bill_status (fix_bill_status_id, description, creation_date, creation_user, modification_date, modification_user) values (5,"fixbillstatusDesc5", CURRENT - INTERVAL(5) DAY to DAY,'creationUser5',CURRENT - INTERVAL(5) DAY to DAY,'modificationUser5');

insert into fix_bill_type (fix_bill_type_id, description, active, creation_date, creation_user, modification_date, modification_user) values (1,"fixbilltypeDesc1", 1, CURRENT - INTERVAL(1) DAY to DAY,'creationUser1',CURRENT - INTERVAL(1) DAY to DAY,'modificationUser1');
insert into fix_bill_type (fix_bill_type_id, description, active, creation_date, creation_user, modification_date, modification_user) values (2,"fixbilltypeDesc2", 2, CURRENT - INTERVAL(2) DAY to DAY,'creationUser2',CURRENT - INTERVAL(2) DAY to DAY,'modificationUser2');
insert into fix_bill_type (fix_bill_type_id, description, active, creation_date, creation_user, modification_date, modification_user) values (3,"fixbilltypeDesc3", 3, CURRENT - INTERVAL(3) DAY to DAY,'creationUser3',CURRENT - INTERVAL(3) DAY to DAY,'modificationUser3');
insert into fix_bill_type (fix_bill_type_id, description, active, creation_date, creation_user, modification_date, modification_user) values (4,"fixbilltypeDesc4", 4, CURRENT - INTERVAL(4) DAY to DAY,'creationUser4',CURRENT - INTERVAL(4) DAY to DAY,'modificationUser4');
insert into fix_bill_type (fix_bill_type_id, description, active, creation_date, creation_user, modification_date, modification_user) values (5,"fixbilltypeDesc5", 5, CURRENT - INTERVAL(5) DAY to DAY,'creationUser5',CURRENT - INTERVAL(5) DAY to DAY,'modificationUser5');







insert into project_expense (expense_entry_id, project_id, creation_date, creation_user, modification_date, modification_user) values (1,1,CURRENT - INTERVAL(1) DAY to DAY,'creationUser1',CURRENT - INTERVAL(1) DAY to DAY,'modificationUser1');
insert into project_expense (expense_entry_id, project_id, creation_date, creation_user, modification_date, modification_user) values (2,1,CURRENT - INTERVAL(2) DAY to DAY,'creationUser2',CURRENT - INTERVAL(2) DAY to DAY,'modificationUser2');
insert into project_expense (expense_entry_id, project_id, creation_date, creation_user, modification_date, modification_user) values (3,2,CURRENT - INTERVAL(3) DAY to DAY,'creationUser3',CURRENT - INTERVAL(3) DAY to DAY,'modificationUser3');
insert into project_expense (expense_entry_id, project_id, creation_date, creation_user, modification_date, modification_user) values (4,2,CURRENT - INTERVAL(4) DAY to DAY,'creationUser4',CURRENT - INTERVAL(4) DAY to DAY,'modificationUser4');
insert into project_expense (expense_entry_id, project_id, creation_date, creation_user, modification_date, modification_user) values (5,3,CURRENT - INTERVAL(5) DAY to DAY,'creationUser5',CURRENT - INTERVAL(5) DAY to DAY,'modificationUser5');


insert into expense_entry (expense_entry_id, company_id, invoice_id, expense_type_id, expense_status_id, description, entry_date, amount, billable, mileage, creation_date, creation_user, modification_date, modification_user) values (1,101,1,1,1,"expenseentryDesc1", DATE("2006-12-31") - INTERVAL(1) DAY to DAY, 1.0,1,1, CURRENT - INTERVAL(1) DAY to DAY,'creationUser1',CURRENT - INTERVAL(1) DAY to DAY,'modificationUser1');
insert into expense_entry (expense_entry_id, company_id, invoice_id, expense_type_id, expense_status_id, description, entry_date, amount, billable, mileage, creation_date, creation_user, modification_date, modification_user) values (2,102,2,2,2,"expenseentryDesc2", DATE("2006-12-31") - INTERVAL(2) DAY to DAY, 2.0,0,2, CURRENT - INTERVAL(2) DAY to DAY,'creationUser2',CURRENT - INTERVAL(2) DAY to DAY,'modificationUser2');
insert into expense_entry (expense_entry_id, company_id, invoice_id, expense_type_id, expense_status_id, description, entry_date, amount, billable, mileage, creation_date, creation_user, modification_date, modification_user) values (3,103,3,3,3,"expenseentryDesc3", DATE("2006-12-31") - INTERVAL(3) DAY to DAY, 3.0,1,3, CURRENT - INTERVAL(3) DAY to DAY,'creationUser3',CURRENT - INTERVAL(3) DAY to DAY,'modificationUser3');
insert into expense_entry (expense_entry_id, company_id, invoice_id, expense_type_id, expense_status_id, description, entry_date, amount, billable, mileage, creation_date, creation_user, modification_date, modification_user) values (4,104,4,4,4,"expenseentryDesc4", DATE("2006-12-31") - INTERVAL(4) DAY to DAY, 4.0,0,4, CURRENT - INTERVAL(4) DAY to DAY,'creationUser4',CURRENT - INTERVAL(4) DAY to DAY,'modificationUser4');
insert into expense_entry (expense_entry_id, company_id, invoice_id, expense_type_id, expense_status_id, description, entry_date, amount, billable, mileage, creation_date, creation_user, modification_date, modification_user) values (5,105,5,5,5,"expenseentryDesc5", DATE("2006-12-31") - INTERVAL(5) DAY to DAY, 5.0,1,5, CURRENT - INTERVAL(5) DAY to DAY,'creationUser5',CURRENT - INTERVAL(5) DAY to DAY,'modificationUser5');


insert into expense_status (expense_status_id, description, creation_date, creation_user, modification_date, modification_user) values (1,"expensestatusDesc1", CURRENT - INTERVAL(1) DAY to DAY,'creationUser1',CURRENT - INTERVAL(1) DAY to DAY,'modificationUser1');
insert into expense_status (expense_status_id, description, creation_date, creation_user, modification_date, modification_user) values (2,"expensestatusDesc2", CURRENT - INTERVAL(2) DAY to DAY,'creationUser2',CURRENT - INTERVAL(2) DAY to DAY,'modificationUser2');
insert into expense_status (expense_status_id, description, creation_date, creation_user, modification_date, modification_user) values (3,"expensestatusDesc3", CURRENT - INTERVAL(3) DAY to DAY,'creationUser3',CURRENT - INTERVAL(3) DAY to DAY,'modificationUser3');
insert into expense_status (expense_status_id, description, creation_date, creation_user, modification_date, modification_user) values (4,"expensestatusDesc4", CURRENT - INTERVAL(4) DAY to DAY,'creationUser4',CURRENT - INTERVAL(4) DAY to DAY,'modificationUser4');
insert into expense_status (expense_status_id, description, creation_date, creation_user, modification_date, modification_user) values (5,"expensestatusDesc5", CURRENT - INTERVAL(5) DAY to DAY,'creationUser5',CURRENT - INTERVAL(5) DAY to DAY,'modificationUser5');


insert into expense_type (expense_type_id, description, active, creation_date, creation_user, modification_date, modification_user) values (1,"expensetypeDesc1", 1, CURRENT - INTERVAL(1) DAY to DAY,'creationUser1',CURRENT - INTERVAL(1) DAY to DAY,'modificationUser1');
insert into expense_type (expense_type_id, description, active, creation_date, creation_user, modification_date, modification_user) values (2,"expensetypeDesc2", 2, CURRENT - INTERVAL(2) DAY to DAY,'creationUser2',CURRENT - INTERVAL(2) DAY to DAY,'modificationUser2');
insert into expense_type (expense_type_id, description, active, creation_date, creation_user, modification_date, modification_user) values (3,"expensetypeDesc3", 3, CURRENT - INTERVAL(3) DAY to DAY,'creationUser3',CURRENT - INTERVAL(3) DAY to DAY,'modificationUser3');
insert into expense_type (expense_type_id, description, active, creation_date, creation_user, modification_date, modification_user) values (4,"expensetypeDesc4", 4, CURRENT - INTERVAL(4) DAY to DAY,'creationUser4',CURRENT - INTERVAL(4) DAY to DAY,'modificationUser4');
insert into expense_type (expense_type_id, description, active, creation_date, creation_user, modification_date, modification_user) values (5,"expensetypeDesc5", 5, CURRENT - INTERVAL(5) DAY to DAY,'creationUser5',CURRENT - INTERVAL(5) DAY to DAY,'modificationUser5');









insert into project_time (time_entry_id, project_id, creation_date, creation_user, modification_date, modification_user) values (1,1,CURRENT - INTERVAL(1) DAY to DAY,'creationUser1',CURRENT - INTERVAL(1) DAY to DAY,'modificationUser1');
insert into project_time (time_entry_id, project_id, creation_date, creation_user, modification_date, modification_user) values (2,1,CURRENT - INTERVAL(2) DAY to DAY,'creationUser2',CURRENT - INTERVAL(2) DAY to DAY,'modificationUser2');
insert into project_time (time_entry_id, project_id, creation_date, creation_user, modification_date, modification_user) values (3,2,CURRENT - INTERVAL(3) DAY to DAY,'creationUser3',CURRENT - INTERVAL(3) DAY to DAY,'modificationUser3');
insert into project_time (time_entry_id, project_id, creation_date, creation_user, modification_date, modification_user) values (4,2,CURRENT - INTERVAL(4) DAY to DAY,'creationUser4',CURRENT - INTERVAL(4) DAY to DAY,'modificationUser4');
insert into project_time (time_entry_id, project_id, creation_date, creation_user, modification_date, modification_user) values (5,3,CURRENT - INTERVAL(5) DAY to DAY,'creationUser5',CURRENT - INTERVAL(5) DAY to DAY,'modificationUser5');


insert into time_entry (time_entry_id, company_id, invoice_id, task_type_id, time_status_id, description, entry_date, hours, billable, creation_date, creation_user, modification_date, modification_user) values (1,101,1,1,1,"timeentryDesc1", DATE("2006-12-31") - INTERVAL(1) DAY to DAY, 1,1, CURRENT - INTERVAL(1) DAY to DAY,'creationUser1',CURRENT - INTERVAL(1) DAY to DAY,'modificationUser1');
insert into time_entry (time_entry_id, company_id, invoice_id, task_type_id, time_status_id, description, entry_date, hours, billable, creation_date, creation_user, modification_date, modification_user) values (2,102,2,2,2,"timeentryDesc2", DATE("2006-12-31") - INTERVAL(2) DAY to DAY, 2,0, CURRENT - INTERVAL(2) DAY to DAY,'creationUser2',CURRENT - INTERVAL(2) DAY to DAY,'modificationUser2');
insert into time_entry (time_entry_id, company_id, invoice_id, task_type_id, time_status_id, description, entry_date, hours, billable, creation_date, creation_user, modification_date, modification_user) values (3,103,3,3,3,"timeentryDesc3", DATE("2006-12-31") - INTERVAL(3) DAY to DAY, 3,1, CURRENT - INTERVAL(3) DAY to DAY,'creationUser3',CURRENT - INTERVAL(3) DAY to DAY,'modificationUser3');
insert into time_entry (time_entry_id, company_id, invoice_id, task_type_id, time_status_id, description, entry_date, hours, billable, creation_date, creation_user, modification_date, modification_user) values (4,104,4,4,4,"timeentryDesc4", DATE("2006-12-31") - INTERVAL(4) DAY to DAY, 4,0, CURRENT - INTERVAL(4) DAY to DAY,'creationUser4',CURRENT - INTERVAL(4) DAY to DAY,'modificationUser4');
insert into time_entry (time_entry_id, company_id, invoice_id, task_type_id, time_status_id, description, entry_date, hours, billable, creation_date, creation_user, modification_date, modification_user) values (5,105,5,5,5,"timeentryDesc5", DATE("2006-12-31") - INTERVAL(5) DAY to DAY, 5,1, CURRENT - INTERVAL(5) DAY to DAY,'creationUser5',CURRENT - INTERVAL(5) DAY to DAY,'modificationUser5');




insert into time_status (time_status_id, description, creation_date, creation_user, modification_date, modification_user) values (1,"timestatusDesc1", CURRENT - INTERVAL(1) DAY to DAY,'creationUser1',CURRENT - INTERVAL(1) DAY to DAY,'modificationUser1');
insert into time_status (time_status_id, description, creation_date, creation_user, modification_date, modification_user) values (2,"timestatusDesc2", CURRENT - INTERVAL(2) DAY to DAY,'creationUser2',CURRENT - INTERVAL(2) DAY to DAY,'modificationUser2');
insert into time_status (time_status_id, description, creation_date, creation_user, modification_date, modification_user) values (3,"timestatusDesc3", CURRENT - INTERVAL(3) DAY to DAY,'creationUser3',CURRENT - INTERVAL(3) DAY to DAY,'modificationUser3');
insert into time_status (time_status_id, description, creation_date, creation_user, modification_date, modification_user) values (4,"timestatusDesc4", CURRENT - INTERVAL(4) DAY to DAY,'creationUser4',CURRENT - INTERVAL(4) DAY to DAY,'modificationUser4');
insert into time_status (time_status_id, description, creation_date, creation_user, modification_date, modification_user) values (5,"timestatusDesc5", CURRENT - INTERVAL(5) DAY to DAY,'creationUser5',CURRENT - INTERVAL(5) DAY to DAY,'modificationUser5');



insert into task_type (task_type_id, description, active, creation_date, creation_user, modification_date, modification_user) values (1,"tasktypeDesc1", 1, CURRENT - INTERVAL(1) DAY to DAY,'creationUser1',CURRENT - INTERVAL(1) DAY to DAY,'modificationUser1');
insert into task_type (task_type_id, description, active, creation_date, creation_user, modification_date, modification_user) values (2,"tasktypeDesc2", 2, CURRENT - INTERVAL(2) DAY to DAY,'creationUser2',CURRENT - INTERVAL(2) DAY to DAY,'modificationUser2');
insert into task_type (task_type_id, description, active, creation_date, creation_user, modification_date, modification_user) values (3,"tasktypeDesc3", 3, CURRENT - INTERVAL(3) DAY to DAY,'creationUser3',CURRENT - INTERVAL(3) DAY to DAY,'modificationUser3');
insert into task_type (task_type_id, description, active, creation_date, creation_user, modification_date, modification_user) values (4,"tasktypeDesc4", 4, CURRENT - INTERVAL(4) DAY to DAY,'creationUser4',CURRENT - INTERVAL(4) DAY to DAY,'modificationUser4');
insert into task_type (task_type_id, description, active, creation_date, creation_user, modification_date, modification_user) values (5,"tasktypeDesc5", 5, CURRENT - INTERVAL(5) DAY to DAY,'creationUser5',CURRENT - INTERVAL(5) DAY to DAY,'modificationUser5');




insert into project_worker (project_id, user_account_id, start_date, end_date, pay_rate, creation_date, creation_user, modification_date, modification_user) values (1,1, DATE("2006-12-31") - INTERVAL(1) DAY to DAY, DATE("2006-12-31") + INTERVAL(1) DAY to DAY, 1.0, CURRENT - INTERVAL(1) DAY to DAY,'creationUser1',CURRENT - INTERVAL(1) DAY to DAY,'modificationUser1');
insert into project_worker (project_id, user_account_id, start_date, end_date, pay_rate, creation_date, creation_user, modification_date, modification_user) values (1,2, DATE("2006-12-31") - INTERVAL(2) DAY to DAY, DATE("2006-12-31") + INTERVAL(2) DAY to DAY, 2.0, CURRENT - INTERVAL(2) DAY to DAY,'creationUser2',CURRENT - INTERVAL(2) DAY to DAY,'modificationUser2');
insert into project_worker (project_id, user_account_id, start_date, end_date, pay_rate, creation_date, creation_user, modification_date, modification_user) values (2,3, DATE("2006-12-31") - INTERVAL(3) DAY to DAY, DATE("2006-12-31") + INTERVAL(3) DAY to DAY, 3.0, CURRENT - INTERVAL(3) DAY to DAY,'creationUser3',CURRENT - INTERVAL(3) DAY to DAY,'modificationUser3');
insert into project_worker (project_id, user_account_id, start_date, end_date, pay_rate, creation_date, creation_user, modification_date, modification_user) values (2,4, DATE("2006-12-31") - INTERVAL(4) DAY to DAY, DATE("2006-12-31") + INTERVAL(4) DAY to DAY, 4.0, CURRENT - INTERVAL(4) DAY to DAY,'creationUser4',CURRENT - INTERVAL(4) DAY to DAY,'modificationUser4');
insert into project_worker (project_id, user_account_id, start_date, end_date, pay_rate, creation_date, creation_user, modification_date, modification_user) values (3,5, DATE("2006-12-31") - INTERVAL(5) DAY to DAY, DATE("2006-12-31") + INTERVAL(5) DAY to DAY, 5.0, CURRENT - INTERVAL(5) DAY to DAY,'creationUser5',CURRENT - INTERVAL(5) DAY to DAY,'modificationUser5');

