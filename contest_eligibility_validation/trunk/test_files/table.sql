create table 'informix'.user_group_xref (
    user_group_id DECIMAL(12,0) not null,
    login_id DECIMAL(12,0),
    group_id DECIMAL(12,0),
    create_user_id DECIMAL(12,0),
    security_status_id DECIMAL(3,0),
    create_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION
);

CREATE SEQUENCE CONTEST_ELIGIBILITY_SEQ INCREMENT BY 1 START WITH 1 MINVALUE 1;

create table 'informix'.contest_eligibility (
    id INT8 not null,
    contest_id INT8,
    is_studio SMALLINT
);

alter table 'informix'.contest_eligibility add constraint primary key 
	(id)
	constraint contest_eligibility_pk;
	
create table 'informix'.group_contest_eligibility (
    id INT8 not null,
    group_id INT8
);
alter table 'informix'.group_contest_eligibility add constraint primary key 
	(id)
	constraint group_contest_eligibility_pk;

alter table 'informix'.group_contest_eligibility add constraint foreign key 
	(id)
	references 'informix'.contest_eligibility
	(id) 
	constraint  contest_eligibility_fk;