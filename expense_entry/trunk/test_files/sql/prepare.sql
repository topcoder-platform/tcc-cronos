INSERT INTO reject_reason(reject_reason_id, description, creation_date, creation_user, modification_date, modification_user, active) VALUES (1, 'description', current, 'a', current, 'a', 0);
INSERT INTO reject_reason(reject_reason_id, description, creation_date, creation_user, modification_date, modification_user, active) VALUES (2, 'description', current, 'a', current, 'a', 0);
insert into company values(1, 'a1', 'a1', current, 'a', current, 'a');
insert into company values(2, 'a2', 'a2', current, 'a', current, 'a');
insert into company values(3, 'a3', 'a3', current, 'a', current, 'a');
insert into comp_rej_reason(company_id, reject_reason_id, creation_date, creation_user, modification_date, modification_user) values (1, 1, current, 'a', current, 'a');
insert into comp_rej_reason(company_id, reject_reason_id, creation_date, creation_user, modification_date, modification_user) values (2, 1, current, 'a', current, 'a');
insert into comp_rej_reason(company_id, reject_reason_id, creation_date, creation_user, modification_date, modification_user) values (1, 2, current, 'a', current, 'a');
insert into comp_rej_reason(company_id, reject_reason_id, creation_date, creation_user, modification_date, modification_user) values (2, 2, current, 'a', current, 'a');
insert into invoice_status(invoice_status_id, description, creation_date, creation_user, modification_date, modification_user) values (1, 'description', current, 'a', current, 'a');
insert into payment_terms(payment_terms_id, description, creation_date, creation_user, modification_date, modification_user, active, term) values (1, 'description', current, 'a', current, 'a', 0, 0);
insert into project(project_id, name, company_id, description, creation_date, creation_user, modification_date, modification_user, start_date, end_date) values (1, 'name', 1, 'description', current, 'a', current, 'a', current, current);
insert into invoice(invoice_id, project_id, creation_date, creation_user, modification_date, modification_user, salesTax, payment_terms_id, invoice_number, po_number, invoice_date, due_date, paid, company_id, invoice_status_id) values (1, 1, current, 'a', current, 'a', 0, 1, 'a', 'a', current, current, 0, 1, 1);
