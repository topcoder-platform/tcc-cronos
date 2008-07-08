create table threads
(
    id INTEGER not null, 
    user_key varchar(255),
    primary key (id)
);

create table messages
(
    id integer not null,
    name varchar(80),
    date DATETIME YEAR TO SECOND,
    message Text,
    thread_id integer,
    primary key (id)    
);

Create table responses
(
    id INTEGER not null, 
    name Varchar(80),
    date DATETIME YEAR TO SECOND,
    message Text,
    message_id Integer,
primary key (id)    
);


Create table attributes
(
    message_id INTEGER,
    name Varchar(255),
    value Varchar(255),
primary key (message_id,name,value)    
);

