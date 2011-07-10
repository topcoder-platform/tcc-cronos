database common_oltp;

INSERT INTO password_recovery(password_recovery_id, user_id, recovery_address, expire_date, used_ind) VALUES(1, 20, 'recovery@topcoder.com', '2020-01-01 12:00:00', 1);
INSERT INTO password_recovery(password_recovery_id, user_id, recovery_address, expire_date, used_ind) VALUES(2, 21, null, '2020-01-01 12:00:00', 0);

INSERT INTO user(user_id, handle, status) VALUES(999, 'usename', 'X');

INSERT INTO user_profile(user_id) VALUES(999);