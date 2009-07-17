---- sale type lu

create table 'informix'.sale_type_lu (
    sale_type_id INTEGER not null,
    sale_type_name VARCHAR(128) not null
)

alter table 'informix'.sale_type_lu add constraint primary key 
	(sale_type_id)
	constraint sale_type_lu_pk;

insert into 'informix'.sale_type_lu values(1, 'Paypal PayFlow');
insert into 'informix'.sale_type_lu values(2, 'TC Purchase Order');

-------- contest payment

alter table contest_payment add (sale_reference_id varchar(128));

alter table contest_payment add (sale_type_id INTEGER);

alter table contest_payment add constraint foreign key 
	(sale_type_id)
	references sale_type_lu 
	(sale_type_id)
	constraint contest_payment_sale_type_lu_fk;

update contest_payment set sale_reference_id = paypal_order_id;
update contest_payment set sale_type_id = 1 where paypal_order_id not like 'PO%';
update contest_payment set sale_type_id = 2 where paypal_order_id  like 'PO%';

------ submission payment

alter table 'informix'.submission_payment add (sale_reference_id varchar(128));

alter table 'informix'.submission_payment add (sale_type_id INTEGER);

alter table 'informix'.submission_payment add constraint foreign key 
	(sale_type_id)
	references 'informix'.sale_type_lu 
	(sale_type_id)
	constraint submission_payment_sale_type_lu_fk;

update 'informix'.submission_payment set sale_reference_id = paypal_order_id;
update contest_payment set sale_type_id = 1 where paypal_order_id not like 'PO%';
update contest_payment set sale_type_id = 2 where paypal_order_id  like 'PO%';