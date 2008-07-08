Create table threads
(
    id Integer, 
    user_key Varchar(255),
Primary Key (id)
)

Create table messages
(
    id Integer,
    name Varchar(80),
    date datetime,
    message Text,
    thread_id Integer,
Primary Key (id)    
)

Create table responses
(
    id Integer,
    name Varchar(80),
    date datetime,
    message Text,
    message_id Integer,
Primary Key (id)    
)


Create table attributes
(
    message_id Integer,
    name Varchar(255),
    value Varchar(255),
Primary Key (message_id,name,value)    
)

