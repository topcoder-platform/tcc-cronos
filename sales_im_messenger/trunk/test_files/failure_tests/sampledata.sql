INSERT INTO all_user (user_id, registered_flag, username, create_date, create_user, modify_date, modify_user) 
VALUES (1, 'Y', 'mittu', today, 'ADMIN', today, 'ADMIN');
INSERT INTO all_user (user_id, registered_flag, username, create_date, create_user, modify_date, modify_user) 
VALUES (2, 'Y', 'handle2', today, 'NORMAL', today, 'NORMAL');
INSERT INTO all_user (user_id, registered_flag, username, create_date, create_user, modify_date, modify_user) 
VALUES (3, 'Y', 'handle3', today, 'NORMAL', today, 'NORMAL');
INSERT INTO all_user (user_id, registered_flag, username, create_date, create_user, modify_date, modify_user) 
VALUES (4, 'Y', 'handle4', today, 'NORMAL', today, 'NORMAL');
INSERT INTO all_user (user_id, registered_flag, username, create_date, create_user, modify_date, modify_user) 
VALUES (5, 'Y', 'handle5', today, 'NORMAL', today, 'NORMAL');

INSERT INTO session_mode (session_mode_id, name, description, create_date,create_user, modify_date, modify_user)
VALUES (1, 'session1', 'First session', today, 'ADMIN', today, 'ADMIN');
INSERT INTO session_mode (session_mode_id, name, description, create_date,create_user, modify_date, modify_user)
VALUES (2, 'session2', 'Second session', today, 'NORMAL', today, 'NORMAL');
INSERT INTO session_mode (session_mode_id, name, description, create_date,create_user, modify_date, modify_user)
VALUES (3, 'session3', 'Third session', today, 'NORMAL', today, 'NORMAL');
INSERT INTO session_mode (session_mode_id, name, description, create_date,create_user, modify_date, modify_user)
VALUES (4, 'session4', 'Fourth session', today, 'NORMAL', today, 'NORMAL');
INSERT INTO session_mode (session_mode_id, name, description, create_date,create_user, modify_date, modify_user)
VALUES (5, 'session5', 'Fifth session', today, 'NORMAL', today, 'NORMAL');

INSERT INTO session (session_id, session_mode_id, create_user_id, create_date,create_user, modify_date,modify_user) 
VALUES (0, 1, 1, today, 'ADMIN', today, 'ADMIN');
INSERT INTO session (session_id, session_mode_id, create_user_id, create_date,create_user, modify_date,modify_user) 
VALUES (1, 2, 1, today, 'NORMAL', today, 'ADMIN');
INSERT INTO session (session_id, session_mode_id, create_user_id, create_date,create_user, modify_date,modify_user) 
VALUES (2, 3, 1, today, 'NORMAL', today, 'ADMIN');
INSERT INTO session (session_id, session_mode_id, create_user_id, create_date,create_user, modify_date,modify_user) 
VALUES (3, 1, 1, today, 'NORMAL', today, 'ADMIN');
INSERT INTO session (session_id, session_mode_id, create_user_id, create_date,create_user, modify_date,modify_user) 
VALUES (4, 2, 1, today, 'ADMIN', today, 'NORMAL');

INSERT INTO session_user (session_user_id, session_id, user_id, enter_date, exit_date, create_date, create_user, modify_date, modify_user) 
VALUES (1, 0, 1, today, NULL, today, 'ADMIN', today, 'ADMIN');
INSERT INTO session_user (session_user_id, session_id, user_id, enter_date, exit_date, create_date, create_user, modify_date, modify_user) 
VALUES (2, 3, 3, today, NULL, today, 'NORMAL', today, 'NORMAL');
INSERT INTO session_user (session_user_id, session_id, user_id, enter_date, exit_date, create_date, create_user, modify_date, modify_user) 
VALUES (3, 4, 4, today, NULL, today, 'NORMAL', today, 'NORMAL');
INSERT INTO session_user (session_user_id, session_id, user_id, enter_date, exit_date, create_date, create_user, modify_date, modify_user) 
VALUES (4, 2, 5, today, NULL, today, 'NORMAL', today, 'NORMAL');
INSERT INTO session_user (session_user_id, session_id, user_id, enter_date, exit_date, create_date, create_user, modify_date, modify_user) 
VALUES (5, 1, 2, today, NULL, today, 'NORMAL', today, 'NORMAL');