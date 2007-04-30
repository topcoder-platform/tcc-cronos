alter table comp_task_type add constraint (FOREIGN KEY (task_type_id) 
REFERENCES task_type(task_type_id));
alter table comp_task_type add constraint (FOREIGN KEY (company_id) 
REFERENCES company(company_id));


alter table time_entry add constraint (FOREIGN KEY (task_type_id) 
REFERENCES task_type(task_type_id));
alter table time_entry add constraint (FOREIGN KEY (company_id) 
REFERENCES company(company_id));
alter table time_entry add constraint (FOREIGN KEY (time_status_id) 
REFERENCES time_status(time_status_id));
alter table time_entry add constraint (FOREIGN KEY (invoice_id) 
REFERENCES invoice(invoice_id));

alter table time_reject_reason add constraint (FOREIGN KEY (time_entry_id) 
REFERENCES time_entry(time_entry_id));

alter table time_reject_reason add constraint (FOREIGN KEY (reject_reason_id) 
REFERENCES reject_reason(reject_reason_id));
