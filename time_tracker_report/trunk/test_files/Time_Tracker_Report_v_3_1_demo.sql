insert into client (client_id, name, company_id, creation_date, creation_user, modification_date, modification_user) values (1,"client1",101,CURRENT - INTERVAL(1) DAY to DAY,'creationUser1',CURRENT - INTERVAL(1) DAY to DAY,'modificationUser1');


insert into project (project_id, name, company_id, description, start_date, end_date, creation_date, creation_user, modification_date, modification_user) values (1,"project1",101,"projectDesc1", CURRENT - INTERVAL(1) DAY to DAY, CURRENT + INTERVAL(1) DAY to DAY,CURRENT - INTERVAL(1) DAY to DAY,'creationUser1',CURRENT - INTERVAL(1) DAY to DAY,'modificationUser1');


insert into client_project (client_id, project_id, creation_date, creation_user, modification_date, modification_user) values (1,1,CURRENT - INTERVAL(1) DAY to DAY,'creationUser1',CURRENT - INTERVAL(1) DAY to DAY,'modificationUser1');
insert into client_project (client_id, project_id, creation_date, creation_user, modification_date, modification_user) values (1,2,CURRENT - INTERVAL(2) DAY to DAY,'creationUser1',CURRENT - INTERVAL(2) DAY to DAY,'modificationUser1');
insert into client_project (client_id, project_id, creation_date, creation_user, modification_date, modification_user) values (2,3,CURRENT - INTERVAL(3) DAY to DAY,'creationUser1',CURRENT - INTERVAL(3) DAY to DAY,'modificationUser1');





insert into user_account (user_account_id, company_id, account_status_id, user_name, password, creation_date, creation_user, modification_date, modification_user) values (1,101,1,"creationUser1","password1",CURRENT - INTERVAL(1) DAY to DAY,'creationUser1',CURRENT - INTERVAL(1) DAY to DAY,'modificationUser1');








insert into project_expense (expense_entry_id, project_id, creation_date, creation_user, modification_date, modification_user) values (1,1,CURRENT - INTERVAL(1) DAY to DAY,'creationUser1',CURRENT - INTERVAL(1) DAY to DAY,'modificationUser1');
insert into project_expense (expense_entry_id, project_id, creation_date, creation_user, modification_date, modification_user) values (2,1,CURRENT - INTERVAL(2) DAY to DAY,'creationUser1',CURRENT - INTERVAL(2) DAY to DAY,'modificationUser1');
insert into project_expense (expense_entry_id, project_id, creation_date, creation_user, modification_date, modification_user) values (3,1,CURRENT - INTERVAL(3) DAY to DAY,'creationUser1',CURRENT - INTERVAL(3) DAY to DAY,'modificationUser1');


insert into expense_entry (expense_entry_id, company_id, invoice_id, expense_type_id, expense_status_id, description, entry_date, amount, billable, mileage, creation_date, creation_user, modification_date, modification_user) values (1,7,1,1,1,"expenseentryDesc1", DATE("2006-12-31") - INTERVAL(1) DAY to DAY, 5.0,1,1, CURRENT - INTERVAL(1) DAY to DAY,'creationUser1',CURRENT - INTERVAL(1) DAY to DAY,'modificationUser1');
insert into expense_entry (expense_entry_id, company_id, invoice_id, expense_type_id, expense_status_id, description, entry_date, amount, billable, mileage, creation_date, creation_user, modification_date, modification_user) values (2,42,2,2,2,"expenseentryDesc2", DATE("2006-12-31") - INTERVAL(2) DAY to DAY, 1.0,0,2, CURRENT - INTERVAL(2) DAY to DAY,'creationUser1',CURRENT - INTERVAL(2) DAY to DAY,'modificationUser1');
insert into expense_entry (expense_entry_id, company_id, invoice_id, expense_type_id, expense_status_id, description, entry_date, amount, billable, mileage, creation_date, creation_user, modification_date, modification_user) values (3,103,3,3,3,"expenseentryDesc3", DATE("2006-12-31") - INTERVAL(3) DAY to DAY, 3.0,1,3, CURRENT - INTERVAL(3) DAY to DAY,'creationUser1',CURRENT - INTERVAL(3) DAY to DAY,'modificationUser1');


insert into expense_status (expense_status_id, description, creation_date, creation_user, modification_date, modification_user) values (1,"expensestatusDesc1", CURRENT - INTERVAL(1) DAY to DAY,'creationUser1',CURRENT - INTERVAL(1) DAY to DAY,'modificationUser1');
insert into expense_status (expense_status_id, description, creation_date, creation_user, modification_date, modification_user) values (2,"expensestatusDesc2", CURRENT - INTERVAL(2) DAY to DAY,'creationUser2',CURRENT - INTERVAL(2) DAY to DAY,'modificationUser2');
insert into expense_status (expense_status_id, description, creation_date, creation_user, modification_date, modification_user) values (3,"expensestatusDesc3", CURRENT - INTERVAL(3) DAY to DAY,'creationUser3',CURRENT - INTERVAL(3) DAY to DAY,'modificationUser3');


insert into expense_type (expense_type_id, description, active, creation_date, creation_user, modification_date, modification_user) values (1,"expensetypeDesc1", 1, CURRENT - INTERVAL(1) DAY to DAY,'creationUser1',CURRENT - INTERVAL(1) DAY to DAY,'modificationUser1');
insert into expense_type (expense_type_id, description, active, creation_date, creation_user, modification_date, modification_user) values (2,"expensetypeDesc2", 2, CURRENT - INTERVAL(2) DAY to DAY,'creationUser2',CURRENT - INTERVAL(2) DAY to DAY,'modificationUser2');
insert into expense_type (expense_type_id, description, active, creation_date, creation_user, modification_date, modification_user) values (3,"expensetypeDesc3", 3, CURRENT - INTERVAL(3) DAY to DAY,'creationUser3',CURRENT - INTERVAL(3) DAY to DAY,'modificationUser3');






