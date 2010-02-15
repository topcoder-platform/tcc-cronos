

create table 'informix'.third_party_library(
third_party_library_id DECIMAL(10,0) not null,
name varchar(50),
description varchar(100),
version varchar(50),
url varchar(50),
license varchar(50),
usage_comments varchar(150),
path varchar(200),
alias varchar(50),
notes varchar(150),
category varchar(50)
);


alter table 'informix'.third_party_library add constraint primary key 
	(third_party_library_id)
	constraint pk_third_party_library;


