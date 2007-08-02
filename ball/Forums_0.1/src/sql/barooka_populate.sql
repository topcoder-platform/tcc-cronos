-- Create an ID sequence for Web Registration component
INSERT INTO id_sequences VALUES ('web_registration_generator', 100, 10, 0);
INSERT INTO id_sequences VALUES ('UserProfile', 100, 10, 0);

-- Insert records for supported plugin downloads tracking
INSERT INTO plugin_downloads (plugin_name, count) VALUES ('IE', 0);
INSERT INTO plugin_downloads (plugin_name, count) VALUES ('FireFox', 0);

-- SET IDENTITY_INSERT download_obj ON;
-- INSERT INTO download_obj (id, media_type, suggested_name, content) VALUES (20002, 'text/plain', 'super bowl picture', '61');
-- SET IDENTITY_INSERT download_obj OFF;

-- SET IDENTITY_INSERT ball_color ON;
-- INSERT INTO ball_color (id, name, download_obj_id) VALUES (20002, 'Super Bowl', 20002);
-- SET IDENTITY_INSERT ball_color OFF;

-- Create admin account
-- INSERT INTO any_user (id, handle, e_mail, passwd, is_active)
--        VALUES (10000, 'admin', 'admin@topcoder.com', 'topcoder', 1);
-- INSERT INTO admin (any_user_id) VALUES (10000);
