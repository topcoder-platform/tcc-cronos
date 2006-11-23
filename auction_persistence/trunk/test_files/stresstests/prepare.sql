# create users
INSERT INTO any_user(id, handle, e_mail) values (1, 'handle1', 'email1');
INSERT INTO any_user(id, handle, e_mail) values (2, 'handle2', 'email2');
INSERT INTO any_user(id, handle, e_mail) values (3, 'handle3', 'email3');
INSERT INTO any_user(id, handle, e_mail) values (4, 'handle4', 'email4');

# set the users as sponsors
INSERT INTO sponsor(any_user_id) values (1);
INSERT INTO sponsor(any_user_id) values (2);
INSERT INTO sponsor(any_user_id) values (3);
INSERT INTO sponsor(any_user_id) values (4);

# create domains
SET IDENTITY_INSERT domain ON
INSERT INTO domain(id, sponsor_id) values (1, 1);
INSERT INTO domain(id, sponsor_id) values (2, 2);
INSERT INTO domain(id, sponsor_id) values (3, 3);
INSERT INTO domain(id, sponsor_id) values (4, 4);
SET IDENTITY_INSERT domain OFF

# create images
SET IDENTITY_INSERT image ON
INSERT INTO image(id, domain_id) values (1, 1);
INSERT INTO image(id, domain_id) values (2, 2);
INSERT INTO image(id, domain_id) values (3, 1);
INSERT INTO image(id, domain_id) values (4, 4);
SET IDENTITY_INSERT image OFF

# create hosting blocks
SET IDENTITY_INSERT hosting_block ON
INSERT INTO hosting_block(id) values (1);
INSERT INTO hosting_block(id) values (2);
INSERT INTO hosting_block(id) values (3);
INSERT INTO hosting_block(id) values (4);
INSERT INTO hosting_block(id) values (5);
INSERT INTO hosting_block(id) values (6);
INSERT INTO hosting_block(id) values (7);
INSERT INTO hosting_block(id) values (8);
INSERT INTO hosting_block(id) values (9);
INSERT INTO hosting_block(id) values (10);
INSERT INTO hosting_block(id) values (11);
INSERT INTO hosting_block(id) values (12);
INSERT INTO hosting_block(id) values (13);
INSERT INTO hosting_block(id) values (14);
INSERT INTO hosting_block(id) values (15);
INSERT INTO hosting_block(id) values (16);
INSERT INTO hosting_block(id) values (17);
INSERT INTO hosting_block(id) values (18);
INSERT INTO hosting_block(id) values (19);
INSERT INTO hosting_block(id) values (20);
INSERT INTO hosting_block(id) values (21);
INSERT INTO hosting_block(id) values (22);
INSERT INTO hosting_block(id) values (23);
INSERT INTO hosting_block(id) values (24);
INSERT INTO hosting_block(id) values (25);
INSERT INTO hosting_block(id) values (26);
INSERT INTO hosting_block(id) values (27);
INSERT INTO hosting_block(id) values (28);
INSERT INTO hosting_block(id) values (29);
INSERT INTO hosting_block(id) values (30);
INSERT INTO hosting_block(id) values (31);
INSERT INTO hosting_block(id) values (32);
INSERT INTO hosting_block(id) values (33);
INSERT INTO hosting_block(id) values (34);
INSERT INTO hosting_block(id) values (35);
INSERT INTO hosting_block(id) values (36);
INSERT INTO hosting_block(id) values (37);
INSERT INTO hosting_block(id) values (38);
INSERT INTO hosting_block(id) values (39);
INSERT INTO hosting_block(id) values (40);
INSERT INTO hosting_block(id) values (41);
INSERT INTO hosting_block(id) values (42);
INSERT INTO hosting_block(id) values (43);
INSERT INTO hosting_block(id) values (44);
INSERT INTO hosting_block(id) values (45);
INSERT INTO hosting_block(id) values (46);
INSERT INTO hosting_block(id) values (47);
INSERT INTO hosting_block(id) values (48);
INSERT INTO hosting_block(id) values (49);
INSERT INTO hosting_block(id) values (50);
INSERT INTO hosting_block(id) values (51);
INSERT INTO hosting_block(id) values (52);
INSERT INTO hosting_block(id) values (53);
INSERT INTO hosting_block(id) values (54);
INSERT INTO hosting_block(id) values (55);
INSERT INTO hosting_block(id) values (56);
INSERT INTO hosting_block(id) values (57);
INSERT INTO hosting_block(id) values (58);
INSERT INTO hosting_block(id) values (59);
INSERT INTO hosting_block(id) values (60);
INSERT INTO hosting_block(id) values (61);
INSERT INTO hosting_block(id) values (62);
INSERT INTO hosting_block(id) values (63);
INSERT INTO hosting_block(id) values (64);
INSERT INTO hosting_block(id) values (65);
INSERT INTO hosting_block(id) values (66);
INSERT INTO hosting_block(id) values (67);
INSERT INTO hosting_block(id) values (68);
INSERT INTO hosting_block(id) values (69);
INSERT INTO hosting_block(id) values (70);
INSERT INTO hosting_block(id) values (71);
INSERT INTO hosting_block(id) values (72);
INSERT INTO hosting_block(id) values (73);
INSERT INTO hosting_block(id) values (74);
INSERT INTO hosting_block(id) values (75);
INSERT INTO hosting_block(id) values (76);
INSERT INTO hosting_block(id) values (77);
INSERT INTO hosting_block(id) values (78);
INSERT INTO hosting_block(id) values (79);
INSERT INTO hosting_block(id) values (80);
INSERT INTO hosting_block(id) values (81);
INSERT INTO hosting_block(id) values (82);
INSERT INTO hosting_block(id) values (83);
INSERT INTO hosting_block(id) values (84);
INSERT INTO hosting_block(id) values (85);
INSERT INTO hosting_block(id) values (86);
INSERT INTO hosting_block(id) values (87);
INSERT INTO hosting_block(id) values (88);
INSERT INTO hosting_block(id) values (89);
INSERT INTO hosting_block(id) values (90);
INSERT INTO hosting_block(id) values (91);
INSERT INTO hosting_block(id) values (92);
INSERT INTO hosting_block(id) values (93);
INSERT INTO hosting_block(id) values (94);
INSERT INTO hosting_block(id) values (95);
INSERT INTO hosting_block(id) values (96);
INSERT INTO hosting_block(id) values (97);
INSERT INTO hosting_block(id) values (98);
INSERT INTO hosting_block(id) values (99);
INSERT INTO hosting_block(id) values (100);
INSERT INTO hosting_block(id) values (101);
INSERT INTO hosting_block(id) values (102);
SET IDENTITY_INSERT hosting_block OFF

# add two auctions
INSERT INTO auction(hosting_block_id, start_time, end_time, min_bid, item_count) values (1, '2006-11-01', '2006-11-07', 10, 10)
INSERT INTO auction(hosting_block_id, start_time, end_time, min_bid, item_count) values (2, '2006-11-01', '2006-11-04', 10, 10)

# create bids
SET IDENTITY_INSERT bid ON
INSERT INTO bid(id, auction_id, image_id, max_amount, time) values (1, 1, 1, 12, '2006-11-02');
INSERT INTO bid(id, auction_id, image_id, max_amount, time) values (2, 1, 2, 14, '2006-11-03');
INSERT INTO bid(id, auction_id, image_id, max_amount, time) values (3, 2, 1, 12, '2006-11-05');

# create effective bids
INSERT INTO effective_bid(bid_id, current_amount) values (2, 12);
INSERT INTO effective_bid(bid_id, current_amount) values (3, 15);