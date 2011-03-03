INSERT INTO image(image_id,file_name) VALUES(1,'Handle1.jpg');
INSERT INTO image(image_id,file_name) VALUES(2,'Handle2.jpg');
INSERT INTO image(image_id,file_name) VALUES(3,'Handle3.jpg');

INSERT INTO member_image(member_image_id,member_id,image_id,removed,created_by,created_date,updated_by,updated_date) VALUES(1,222222333,1,'T','admin',TO_DATE('2011-14-01 12:00:00', '%Y-%d-%m %H:%M:%S'),'admin',TO_DATE('2011-14-01 12:00:00', '%Y-%d-%m %H:%M:%S'));
INSERT INTO member_image(member_image_id,member_id,image_id,removed,created_by,created_date,updated_by,updated_date) VALUES(2,222333444,2,'F','admin',TO_DATE('2011-14-01 12:00:00', '%Y-%d-%m %H:%M:%S'),'admin',TO_DATE('2011-14-01 12:00:00', '%Y-%d-%m %H:%M:%S'));
INSERT INTO member_image(member_image_id,member_id,image_id,removed,created_by,created_date,updated_by,updated_date) VALUES(3,222444555,3,'F','admin',TO_DATE('2011-14-01 12:00:00', '%Y-%d-%m %H:%M:%S'),'admin',TO_DATE('2011-14-01 12:00:00', '%Y-%d-%m %H:%M:%S'));