insert into threads(id, user_key) values (1,'userKey1');
insert into threads(id, user_key) values (2,'userKey2');
insert into threads(id, user_key) values (3,'userKey3');


insert into messages(id, name, date, thread_id) values (1, 'message1', CURRENT,  1);
insert into messages(id, name, date, thread_id) values (2, 'message2', current, 1);
insert into messages(id, name, date, thread_id) values (3, 'message3', current, 2);
insert into messages(id, name, date, thread_id) values (4, 'message4', current,  2);
insert into messages(id, name, date, thread_id) values (5, 'message5', current,  3);
insert into messages(id, name, date, thread_id) values (6, 'message6', current,  3);


insert into responses(id, name, date,  message_id) values (1, 'response1', current,  1);
insert into responses(id, name, date,  message_id) values (2, 'response2', current,  3);
insert into responses(id, name, date,  message_id) values (3, 'response3', current, 5);

insert into attributes(message_id, name, value) values (1,'attribute1', 'value of attribute1');
insert into attributes(message_id, name, value) values (2,'attribute2', 'value of attribute2');
insert into attributes(message_id, name, value) values (3,'attribute3', 'value of attribute3');
insert into attributes(message_id, name, value) values (4,'attribute4', 'value of attribute4');
insert into attributes(message_id, name, value) values (5,'attribute5', 'value of attribute5');
insert into attributes(message_id, name, value) values (6,'attribute6', 'value of attribute6');