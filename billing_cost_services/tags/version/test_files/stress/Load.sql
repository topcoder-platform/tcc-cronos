INSERT INTO payment_area(id,name) VALUES (1,'p1');
INSERT INTO payment_area(id,name) VALUES (2,'p2');

INSERT INTO billing_cost_export(id,accountant_name,payment_area_id,records_count,timestamp) VALUES (1,'u1',1,2,'2011-09-24');
INSERT INTO billing_cost_export(id,accountant_name,payment_area_id,records_count,timestamp) VALUES (2,'u1',2,2,'2011-09-24');

INSERT INTO billing_cost_export_detail(id, billing_cost_export_id,contest_id,customer_name,contest_name,project_info_type_id,payment_detail_id,billing_amount,payment_type, invoice_number,in_quickbooks,quickbooks_import_timestamp) VALUES (1,1,1,'c1','c1',1,1,1.1,'p1','1','t','2011-09-24');
INSERT INTO billing_cost_export_detail(id, billing_cost_export_id,contest_id,customer_name,contest_name,project_info_type_id,payment_detail_id,billing_amount,payment_type, invoice_number,in_quickbooks,quickbooks_import_timestamp) VALUES (2,1,1,'c2','c2',1,1,2.2,'p2','2','f','2011-09-24');

INSERT INTO accounting_audit_record(id,action,user_name,timestamp) VALUES (1,'update','admin','2011-09-24');
INSERT INTO accounting_audit_record(id,action,user_name,timestamp) VALUES (2,'delete','guest','2011-09-24');
