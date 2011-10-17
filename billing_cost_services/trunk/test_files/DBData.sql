INSERT INTO payment_area(id,name) VALUES (1,'payarea1');
INSERT INTO payment_area(id,name) VALUES (2,'payarea2');
INSERT INTO payment_area(id,name) VALUES (3,'payarea3');

INSERT INTO billing_cost_export(id,accountant_name,payment_area_id,records_count,timestamp) VALUES (1,'user1',1,2,'2011-09-02');
INSERT INTO billing_cost_export(id,accountant_name,payment_area_id,records_count,timestamp) VALUES (2,'user1',2,3,'2011-09-03');
INSERT INTO billing_cost_export(id,accountant_name,payment_area_id,records_count,timestamp) VALUES (3,'user3',3,4,'2011-09-04');

INSERT INTO billing_cost_export_detail(id, billing_cost_export_id,contest_id,customer_name,contest_name,project_info_type_id,payment_detail_id,billing_amount,payment_type, invoice_number,in_quickbooks,quickbooks_import_timestamp) VALUES (1,1,1,'customer3','contest1',1,1,1.1,'paytype1','10','t','2011-09-02');
INSERT INTO billing_cost_export_detail(id, billing_cost_export_id,contest_id,customer_name,contest_name,project_info_type_id,payment_detail_id,billing_amount,payment_type, invoice_number,in_quickbooks,quickbooks_import_timestamp) VALUES (2,1,1,'customer2','contest2',1,1,2.2,'paytype1','20','f','2011-09-03');
INSERT INTO billing_cost_export_detail(id, billing_cost_export_id,contest_id,customer_name,contest_name,project_info_type_id,payment_detail_id,billing_amount,payment_type, invoice_number,in_quickbooks,quickbooks_import_timestamp) VALUES (3,1,1,'customer1','contest3',1,1,3.3,'paytype1','30','t','2011-09-04');


INSERT INTO accounting_audit_record(id,action,user_name,timestamp) VALUES (1,'add','admin','2011-09-02');
INSERT INTO accounting_audit_record(id,action,user_name,timestamp) VALUES (2,'get','user','2011-09-03');
INSERT INTO accounting_audit_record(id,action,user_name,timestamp) VALUES (3,'del','admin','2011-09-04');
