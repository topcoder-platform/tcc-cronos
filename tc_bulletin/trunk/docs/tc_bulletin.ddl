create table threads (
    id bigint identity
)

create table messages (
    id bigint identity
,   thread_id bigint
,   message_name varchar(128)
,   message_text text
,   message_date datetime
,   reply_name varchar(128)
,   reply_text text
,   reply_date datetime
,   attributes text
)
