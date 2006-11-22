SET IDENTITY_INSERT contact_info ON;
INSERT INTO contact_info (id, first_name, last_name, address_1,city, state, postal_code, telephone)
VALUES(1, 'First', 'Last', 'Addr', 'City', 'State', '12345', '12345-6789');
INSERT INTO contact_info (id, first_name, last_name, address_1,city, state, postal_code, telephone)
VALUES(2, 'First', 'Last', 'Addr', 'City', 'State', '12345', '12345-6789');
SET IDENTITY_INSERT contact_info OFF;

INSERT INTO any_user(id, handle, e_mail, passwd, is_active) 
VALUES(1, 'player', 'email1@topcoder.com', 'pass', 1);

INSERT INTO any_user(id, handle, e_mail, passwd, is_active) 
VALUES(2, 'sponsor', 'email2@topcoder.com', 'pass', 1);

INSERT INTO player(any_user_id, contact_info_id, payment_pref) 
VALUES(1, 1, 'wire transfer');

INSERT INTO sponsor(any_user_id, contact_info_id)
VALUES(2, 2);

SET IDENTITY_INSERT download_obj ON;
INSERT INTO download_obj(id, media_type, suggested_name, content) 
VALUES(1, 'html/text', 'name', 'content');
SET IDENTITY_INSERT download_obj OFF;

SET IDENTITY_INSERT ball_color ON;
INSERT INTO ball_color(id, name, download_obj_id) 
VALUES(1, 'color_name', 1)
SET IDENTITY_INSERT ball_color OFF;

SET IDENTITY_INSERT domain ON;
INSERT INTO domain(id, sponsor_id, base_url, is_approved) 
VALUES(1, 2, 'url', 1)
SET IDENTITY_INSERT domain OFF;

SET IDENTITY_INSERT game ON;
INSERT INTO game(id, ball_color_id, start_date, keys_required) 
VALUES(1, 1, '1/1/2006', 1);
INSERT INTO game(id, ball_color_id, start_date, keys_required) 
VALUES(2, 1, '2/2/2006', 1);
SET IDENTITY_INSERT game OFF;

SET IDENTITY_INSERT hosting_block ON;
INSERT INTO hosting_block(id, game_id, sequence_number, max_time_per_slot) 
VALUES(1, 1, 1, 1)
SET IDENTITY_INSERT hosting_block OFF;

INSERT INTO plyr_won_game(game_id, player_id, date, payout) 
VALUES(1, 1, '1/1/2006', 100);

SET IDENTITY_INSERT puzzle ON;
INSERT INTO puzzle(id, name) 
VALUES(1, 'puzzle')
SET IDENTITY_INSERT puzzle OFF;

SET IDENTITY_INSERT puzzle_resource ON;
INSERT INTO puzzle_resource(id, puzzle_id, name, download_obj_id) 
VALUES(1, 1, 'name', 1);
SET IDENTITY_INSERT puzzle_resource OFF;

INSERT INTO auction(hosting_block_id, start_time, end_time, min_bid, bid_increment, item_count) 
VALUES(1, '1/1/2006', '2/2/2006', 1, 1, 1)

SET IDENTITY_INSERT image ON;
INSERT INTO image(id, domain_id, download_obj_id, is_approved, description) 
VALUES(1, 1, 1, 1, 'desc')
SET IDENTITY_INSERT image OFF;

SET IDENTITY_INSERT bid ON;
INSERT INTO bid(id, auction_id, image_id, max_amount, time) 
VALUES(1, 1, 1, 10, '1/1/2006')
INSERT INTO bid(id, auction_id, image_id, max_amount, time) 
VALUES(2, 1, 1, 20, '2/2/2006')
SET IDENTITY_INSERT bid OFF;

SET IDENTITY_INSERT hosting_slot ON;
INSERT INTO hosting_slot(id, bid_id, sequence_number, hosting_start, hosting_end) 
VALUES(1, 1, 1, '1/1/2006', '2/2/2006')
SET IDENTITY_INSERT hosting_slot OFF;

INSERT INTO effective_bid(bid_id, current_amount) 
VALUES(1, 100)

INSERT INTO puzzle_for_slot(hosting_slot_id, puzzle_id)
VALUES(1, 1)

INSERT INTO plyr_compltd_slot(hosting_slot_id, player_id, timestamp, key_text, key_image_id)
VALUES(1, 1, '1/1/2006', 'key', 1)
