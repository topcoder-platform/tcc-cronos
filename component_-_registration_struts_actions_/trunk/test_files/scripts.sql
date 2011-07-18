database common_oltp;
alter table user drop first_name;
alter table user drop last_name;
alter table user drop middle_name;
alter table user drop timezone_id;

create table user_profile (
user_id DECIMAL(10,0) not null,
first_name VARCHAR(64),
last_name VARCHAR(64),
middle_name VARCHAR(64),
timezone_id DECIMAL(5,0)
);

create table audit_record (
   id SERIAL8 NOT NULL,
   operation_type VARCHAR(100) NOT NULL,
   handle VARCHAR(20) NOT NULL,
   ip_address VARCHAR(20) NOT NULL, 
   previous_value LVARCHAR(4096),
   new_value LVARCHAR(4096), 
   timestamp DATETIME YEAR TO FRACTION NOT NULL
);

alter table user_profile add constraint primary key
	(user_id) constraint pk_user_profile;
	
alter table user_profile add constraint foreign key
	(user_id)
	references user
	(user_id)
	on delete cascade
	constraint fk_user_userprofile;
	
insert into user_profile(user_id,first_name,last_name,middle_name,timezone_id)
values (20, 'first_name', 'last_name', 'middle_name', 128);

grant index on user_profile to 'public' as 'informix';

grant delete on user_profile to 'public' as 'informix';

grant select on user_profile to 'public' as 'informix';

grant update on user_profile to 'public' as 'informix';

grant insert on user_profile to 'public' as 'informix';

drop trigger trig_audit_user;

database informixoltp;
create synonym user_profile for common_oltp:user_profile;
create synonym audit_record for common_oltp:audit_record;

database common_oltp;

-- change the view of email user
drop view email_user;

create view email_user (user_id, create_date,modify_date,handle,last_login,
status,activation_code,email) as select x0.user_id, x0.create_date,
x0.modify_date,
x0.handle, x0.last_login, x0.status, x0.activation_code, x1.address 
   from user x0 ,email x1 
   where ((((x0.user_id = x1.user_id ) 
   AND (x1.primary_ind = 1. ) ) AND (x1.status_id = 1. ) )
   AND (x0.status = 'A' ) ) ;
revoke all on email_user from 'public';

-- change the procedure proc_user_update
drop procedure proc_user_update(decimal,varchar,varchar,varchar,varchar,
varchar,varchar,varchar,varchar,varchar,varchar,varchar,varchar,decimal
,decimal);

create procedure proc_user_update(
user_id DECIMAL(10,0),
old_handle VARCHAR(50),
new_handle VARCHAR(50),
old_status VARCHAR(3),
new_status VARCHAR(3),
old_activation_code VARCHAR(32),
new_activation_code VARCHAR(32)
)
 
      
      if (old_handle != new_handle) then 
         insert into audit_user (column_name, old_value, new_value, user_id)
         values ('HANDLE', old_handle, new_handle, user_id);
      End If;

      if (old_status != new_status) then 
         insert into audit_user (column_name, old_value, new_value, user_id)
         values ('STATUS', old_status, new_status, user_id);
      End If;

      if (old_activation_code != new_activation_code) then 
         insert into audit_user (column_name, old_value, new_value, user_id)
         values ('ACTIVATION_CODE', old_activation_code, new_activation_code, user_id);
      End If;

      UPDATE user SET handle_lower = lower(new_handle), modify_date
      = current WHERE user.user_id = user_id;

end procedure;
revoke execute on procedure proc_user_update(decimal,varchar,varchar,
varchar,varchar,varchar,varchar) from 'public';

create trigger trig_audit_user update of handle,last_login,status,
activation_code,
last_site_hit_date on user referencing old as old new as new  for each row
    (
    execute procedure proc_user_update(old.user_id ,old.handle ,new.handle ,
    old.status,
    new.status ,old.activation_code ,new.activation_code ));



database tcs_catalog;

-- change the view user_customer
drop view user_customer;

create view user_customer (user_customer_id,login_id,
       company,address,city,postal_code,
       country_code,telephone_country,telephone_area,telephone_nbr,
       use_components,use_consultants,receive_tcsnews,receive_newshtml,
       company_size_id,tier_id,activation_code,email_address) as
   select x0.user_id ,x0.user_id ,
       x6.company_name ,((x1.address1 || ' ' ) || x1.address2 ) ,
       x1.city ,x1.zip ,x1.country_code ,'' ,'' ,x4.phone_number ,
       0 ,0 ,0 ,0 ,1 ,0 ,x0.activation_code ,x3.address 
   from common_oltp:user x0 ,common_oltp:address x1 ,
   common_oltp:user_address_xref x2 ,common_oltp:email x3 ,outer
   (common_oltp:phone x4 ,common_oltp:contact x5 ,common_oltp:company x6 ) 
   where ((((((((x0.user_id = x2.user_id ) AND (x1.address_id = x2.address_id))
   AND (x3.primary_ind = 1. ) ) AND (x3.user_id = x0.user_id ) ) AND 
   (x4.user_id = x0.user_id ) ) AND (x4.primary_ind = 1. ) ) AND 
   (x5.contact_id = x0.user_id ) ) AND (x5.company_id = x6.company_id ) ) ;
revoke all on user_customer from 'public';

grant update(activation_code,city,company,country_code,email_address,login_id,
postal_code,telephone_nbr,user_customer_id) on user_customer to 'informix'
with grant option ;

