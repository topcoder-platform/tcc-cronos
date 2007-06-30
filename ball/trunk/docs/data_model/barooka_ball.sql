-- ------------------------------------------------------------
-- Contact_info entities represent the information necessary to contact
-- a user by post or telephone.
-- ------------------------------------------------------------

CREATE TABLE contact_info (
  id BIGINT NOT NULL AUTO_INCREMENT,
  first_name VARCHAR(29) NOT NULL,
  last_name VARCHAR(49) NOT NULL,
  address_1 VARCHAR(127) NOT NULL,
  address_2 VARCHAR(127) NULL,
  city VARCHAR(127) NOT NULL,
  state VARCHAR(29) NOT NULL,
  postal_code VARCHAR(9) NOT NULL,
  telephone VARCHAR(15) NOT NULL,
  PRIMARY KEY(id)
)
TYPE=InnoDB;

-- ------------------------------------------------------------
-- Represents a generic message with category, update timestamp,
-- and typed content.
-- ------------------------------------------------------------

CREATE TABLE message (
  id BIGINT NOT NULL AUTO_INCREMENT,
  guid VARCHAR(255) NOT NULL,
  category VARCHAR(20) NOT NULL,
  content_type VARCHAR(255) NOT NULL,
  update_time DATETIME NOT NULL,
  content TEXT NOT NULL,
  PRIMARY KEY(id),
  UNIQUE INDEX guid_uique(guid)
)
TYPE=InnoDB;

-- ------------------------------------------------------------
-- Download_obj entities represent binary objects of specified media type.
-- They are generally intended to be provided for download from the server,
-- but can be used internally instead.
-- ------------------------------------------------------------

CREATE TABLE download_obj (
  id BIGINT NOT NULL AUTO_INCREMENT,
  media_type VARCHAR(255) NOT NULL,
  suggested_name VARCHAR(255) NOT NULL,
  content BLOB NOT NULL,
  PRIMARY KEY(id)
)
TYPE=InnoDB;

-- ------------------------------------------------------------
-- This table supports the TopCoder ID Generator component, version 3.0.
-- ------------------------------------------------------------

CREATE TABLE id_sequences (
  name VARCHAR(255) NOT NULL,
  next_block_start BIGINT UNSIGNED NOT NULL,
  block_size INTEGER UNSIGNED NOT NULL,
  PRIMARY KEY(name)
)
TYPE=InnoDB;

-- ------------------------------------------------------------
-- This table supports the E-mail Confirmation component used by Web Registration.
-- ------------------------------------------------------------

CREATE TABLE pending_email_conf (
  address VARCHAR(255) NOT NULL,
  confirmation_code VARCHAR(32) NOT NULL,
  date_sent DATETIME NOT NULL,
  message_subject VARCHAR(255) NOT NULL,
  message_body TEXT NOT NULL,
  PRIMARY KEY(address)
)
TYPE=InnoDB;

-- ------------------------------------------------------------
-- Any_user entities represent the identifying and authorization information
-- for specific players, admins, and sponsors.
-- ------------------------------------------------------------

CREATE TABLE any_user (
  id BIGINT NOT NULL,
  handle VARCHAR(29) NOT NULL,
  e_mail VARCHAR(255) NOT NULL,
  passwd VARCHAR(24) NOT NULL,
  is_active BOOL NOT NULL,
  PRIMARY KEY(id),
  UNIQUE INDEX ball_user_handle_unique(handle),
  UNIQUE INDEX ball_user_email_unique(e_mail)
)
TYPE=InnoDB;

-- ------------------------------------------------------------
-- A puzzle entity is a generic representation of a puzzle of a puzzle 
-- a brain-teaser, word puzzle, tile puzzle, jigsaw puzzle, etc.  It
-- provides a reference with which to associate any number of
-- named "attributes" having string values, and named "resources"
-- having associated MIME media types, additional string data, and
-- an associated binary object.
-- ------------------------------------------------------------

CREATE TABLE puzzle (
  id BIGINT NOT NULL AUTO_INCREMENT,
  name VARCHAR(255) NULL,
  PRIMARY KEY(id)
)
TYPE=InnoDB;

-- ------------------------------------------------------------
-- Records counts of the number of downloads of the various
-- plugins.
-- ------------------------------------------------------------

CREATE TABLE plugin_downloads (
  plugin_name VARCHAR(255) NOT NULL AUTO_INCREMENT,
  count BIGINT NOT NULL,
  PRIMARY KEY(plugin_name)
)
TYPE=InnoDB;

-- ------------------------------------------------------------
-- Puzzle_attribute entities associate named attribute strings with
-- specific puzzles.
-- ------------------------------------------------------------

CREATE TABLE puzzle_attribute (
  id BIGINT NOT NULL AUTO_INCREMENT,
  puzzle_id BIGINT NOT NULL,
  name VARCHAR(255) NOT NULL,
  value VARCHAR(255) NOT NULL,
  PRIMARY KEY(id),
  INDEX puzzle_attribute_FKIndex1(puzzle_id),
  UNIQUE INDEX attribute_names_unique(puzzle_id, name),
  FOREIGN KEY(puzzle_id)
    REFERENCES puzzle(id)
      ON DELETE CASCADE
      ON UPDATE NO ACTION
)
TYPE=InnoDB;

-- ------------------------------------------------------------
-- Ball_color entities represent the available Ball colors, providing a color
-- name and having an associated representative image in the form of a
-- download_obj.
-- ------------------------------------------------------------

CREATE TABLE ball_color (
  id BIGINT NOT NULL AUTO_INCREMENT,
  name VARCHAR(15) NOT NULL,
  download_obj_id BIGINT NOT NULL,
  PRIMARY KEY(id),
  INDEX ball_color_FKIndex1(download_obj_id),
  UNIQUE INDEX ball_color_name_unique(name),
  FOREIGN KEY(download_obj_id)
    REFERENCES download_obj(id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
)
TYPE=InnoDB;

-- ------------------------------------------------------------
-- Admin entities represent Barooka Ball administrators.  They
-- have associated user credentials, but nothing else (in this
-- version).
-- ------------------------------------------------------------

CREATE TABLE admin (
  any_user_id BIGINT NOT NULL,
  PRIMARY KEY(any_user_id),
  INDEX admin_FKIndex1(any_user_id),
  FOREIGN KEY(any_user_id)
    REFERENCES any_user(id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
)
TYPE=InnoDB;

-- ------------------------------------------------------------
-- Game entities represent individual Barooka Ball games.  They have
-- a serial number (in the form of their ID), a ball color, and nullable start
-- and end dates.
-- ------------------------------------------------------------

CREATE TABLE game (
  id BIGINT NOT NULL AUTO_INCREMENT,
  ball_color_id BIGINT NOT NULL,
  start_date DATETIME NOT NULL,
  keys_required INTEGER UNSIGNED NOT NULL,
  PRIMARY KEY(id),
  INDEX game_FKIndex1(ball_color_id),
  FOREIGN KEY(ball_color_id)
    REFERENCES ball_color(id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
)
TYPE=InnoDB;

-- ------------------------------------------------------------
-- Sponsor entities represent game sponsor representatives
-- eligible to place bids to host the Ball.  They have user credentials,
-- standard contact info, plus an optional (nullable) FAX number, and
-- a payment method preference (for making payments).
-- ------------------------------------------------------------

CREATE TABLE sponsor (
  any_user_id BIGINT NOT NULL,
  contact_info_id BIGINT NOT NULL,
  fax NUMERIC NULL,
  payment_pref VARCHAR(49) NULL,
  is_approved BOOL NULL,
  PRIMARY KEY(any_user_id),
  INDEX sponsor_FKIndex2(contact_info_id),
  INDEX sponsor_FKIndex2(any_user_id),
  FOREIGN KEY(contact_info_id)
    REFERENCES contact_info(id)
      ON DELETE CASCADE
      ON UPDATE NO ACTION,
  FOREIGN KEY(any_user_id)
    REFERENCES any_user(id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
)
TYPE=InnoDB;

-- ------------------------------------------------------------
-- Puzzle_resource entities associate arbitrary binary objects with
-- specific puzzle entities.  They associate a name with MIME media
-- type information, and a corresponding binary object
-- ------------------------------------------------------------

CREATE TABLE puzzle_resource (
  id BIGINT NOT NULL AUTO_INCREMENT,
  puzzle_id BIGINT NOT NULL,
  name VARCHAR(255) NOT NULL,
  download_obj_id BIGINT NOT NULL,
  PRIMARY KEY(id),
  INDEX puzzle_resource_FKIndex1(puzzle_id),
  INDEX puzzle_resource_FKIndex2(download_obj_id),
  UNIQUE INDEX resource_name_unique(puzzle_id, name),
  FOREIGN KEY(puzzle_id)
    REFERENCES puzzle(id)
      ON DELETE CASCADE
      ON UPDATE NO ACTION,
  FOREIGN KEY(download_obj_id)
    REFERENCES download_obj(id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
)
TYPE=InnoDB;

-- ------------------------------------------------------------
-- Player entities represent players.  They have user credentials,
-- and may have associated contact information and payment preference
-- (for being paid prize money).
-- ------------------------------------------------------------

CREATE TABLE player (
  any_user_id BIGINT NOT NULL,
  contact_info_id BIGINT NULL,
  payment_pref VARCHAR(49) NULL,
  PRIMARY KEY(any_user_id),
  INDEX player_FKIndex2(contact_info_id),
  INDEX player_FKIndex3(any_user_id),
  FOREIGN KEY(contact_info_id)
    REFERENCES contact_info(id)
      ON DELETE CASCADE
      ON UPDATE NO ACTION,
  FOREIGN KEY(any_user_id)
    REFERENCES any_user(id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
)
TYPE=InnoDB;

-- ------------------------------------------------------------
-- The plyr_regstrd_game relation establishes which players
-- are registered for which games.
-- ------------------------------------------------------------

CREATE TABLE plyr_regstrd_game (
  game_id BIGINT NOT NULL,
  player_id BIGINT NOT NULL,
  PRIMARY KEY(game_id, player_id),
  INDEX game_registration_FKIndex1(player_id),
  INDEX game_registration_FKIndex2(game_id),
  FOREIGN KEY(player_id)
    REFERENCES player(any_user_id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
  FOREIGN KEY(game_id)
    REFERENCES game(id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
)
TYPE=InnoDB;

-- ------------------------------------------------------------
-- The plyr_compltd_game relation establishes which players have
-- completed which games, and provides a global serial number by
-- which to determine the order in which the players achieved their
-- game completions.
-- ------------------------------------------------------------

CREATE TABLE plyr_compltd_game (
  game_id BIGINT NOT NULL,
  player_id BIGINT NOT NULL,
  sequence_number BIGINT NOT NULL AUTO_INCREMENT,
  is_handled BOOL NOT NULL DEFAULT false,
  PRIMARY KEY(game_id, player_id),
  INDEX provisional_winner_FKIndex1(game_id),
  INDEX provisional_winner_FKIndex2(player_id),
  FOREIGN KEY(game_id)
    REFERENCES game(id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
  FOREIGN KEY(player_id)
    REFERENCES player(any_user_id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
)
TYPE=InnoDB;

-- ------------------------------------------------------------
-- Hosting_block entities aggregate hosting slots, providing for group-wise
-- sequencing and provision of slots.
-- ------------------------------------------------------------

CREATE TABLE hosting_block (
  id BIGINT NOT NULL AUTO_INCREMENT,
  game_id BIGINT NOT NULL,
  sequence_number INTEGER UNSIGNED NOT NULL,
  max_time_per_slot INTEGER UNSIGNED NOT NULL,
  PRIMARY KEY(id),
  INDEX hosting_block_FKIndex2(game_id),
  UNIQUE INDEX unique_game_block_seq(game_id, sequence_number),
  FOREIGN KEY(game_id)
    REFERENCES game(id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
)
TYPE=InnoDB;

-- ------------------------------------------------------------
-- Represents an auction for slots from a hosting block.  Each auction has a specific
-- start time and end time, a minimum bid, a minimum bid increment, and a count of
-- individual items for sale.  Slots are auctioned anonymously, so that the n highest
-- bids in each auction win slots (where n is the number of slots in the block).
-- ------------------------------------------------------------

CREATE TABLE auction (
  hosting_block_id BIGINT NOT NULL,
  start_time DATETIME NOT NULL,
  end_time DATETIME NOT NULL,
  min_bid INTEGER UNSIGNED NOT NULL,
  bid_increment INTEGER UNSIGNED NOT NULL,
  item_count INTEGER UNSIGNED NOT NULL,
  PRIMARY KEY(hosting_block_id),
  INDEX auction_FKIndex1(hosting_block_id),
  FOREIGN KEY(hosting_block_id)
    REFERENCES hosting_block(id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
)
TYPE=InnoDB;

-- ------------------------------------------------------------
-- Domain entities represent web sites that (it is hoped) are
-- eligible to host the Ball.  Each is associated with a specific sponsor,
-- and is characterized by the base URL of the site.
-- ------------------------------------------------------------

CREATE TABLE domain (
  id BIGINT NOT NULL AUTO_INCREMENT,
  sponsor_id BIGINT NOT NULL,
  base_url VARCHAR(255) NOT NULL,
  is_approved BOOL NULL,
  PRIMARY KEY(id),
  INDEX domain_FKIndex1(sponsor_id),
  UNIQUE INDEX dmn_spn_url_unique(sponsor_id, base_url),
  FOREIGN KEY(sponsor_id)
    REFERENCES sponsor(any_user_id)
      ON DELETE CASCADE
      ON UPDATE NO ACTION
)
TYPE=InnoDB;

-- ------------------------------------------------------------
-- Records game-win events, identifying the winner, win approval
-- timestamp, and payout amount.  Presence of a row in this table
-- for a specific game ID signals that that game is over.
-- ------------------------------------------------------------

CREATE TABLE plyr_won_game (
  game_id BIGINT NOT NULL,
  player_id BIGINT NOT NULL,
  date DATETIME NOT NULL,
  payout INTEGER UNSIGNED NOT NULL,
  PRIMARY KEY(game_id),
  INDEX plyr_won_game_FKIndex1(player_id),
  INDEX plyr_won_game_FKIndex2(game_id),
  FOREIGN KEY(player_id)
    REFERENCES player(any_user_id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
  FOREIGN KEY(game_id)
    REFERENCES game(id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
)
TYPE=InnoDB;

-- ------------------------------------------------------------
-- Image entities represent the domain-specific images associated
-- with sponsored domains.  In addition to a specific domain, each is
-- associated with a download_obj containing the binary image data.  Each
-- bears a flag indicating whether the image has been approved or rejected,
-- if any such decision has yet been made.
-- ------------------------------------------------------------

CREATE TABLE image (
  id BIGINT NOT NULL AUTO_INCREMENT,
  domain_id BIGINT NOT NULL,
  download_obj_id BIGINT NOT NULL,
  is_approved BOOL NULL,
  description VARCHAR(255) NOT NULL,
  PRIMARY KEY(id),
  INDEX image_FKIndex1(domain_id),
  INDEX image_FKIndex2(download_obj_id),
  FOREIGN KEY(domain_id)
    REFERENCES domain(id)
      ON DELETE CASCADE
      ON UPDATE NO ACTION,
  FOREIGN KEY(download_obj_id)
    REFERENCES download_obj(id)
      ON DELETE CASCADE
      ON UPDATE NO ACTION
)
TYPE=InnoDB;

-- ------------------------------------------------------------
-- Represents a bid placed by a sponsor to host the Ball at a specific domain using
-- a specific domain image.  (Domain is implied by image, and sponsor by domain.)
-- The auto-incrementing id of this table serves to establish a total order of bids
-- (reception order), even when two or more are received at the same time within
-- the granularity of the timestamp.  The sponsor also specifies the maximum amount
-- he is willing to pay, for the purpose of automated proxy bidding.  The actual
-- current amount that the sponsor would pay if this bid won (often less than
-- max_amount) is specified separately, via an effective_bid entity.
-- ------------------------------------------------------------

CREATE TABLE bid (
  id BIGINT NOT NULL AUTO_INCREMENT,
  auction_id BIGINT NOT NULL,
  image_id BIGINT NOT NULL,
  max_amount INTEGER UNSIGNED NOT NULL,
  time DATETIME NOT NULL,
  PRIMARY KEY(id),
  INDEX bid_FKIndex2(image_id),
  INDEX bid_FKIndex2(auction_id),
  FOREIGN KEY(auction_id)
    REFERENCES auction(hosting_block_id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
  FOREIGN KEY(image_id)
    REFERENCES image(id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
)
TYPE=InnoDB;

-- ------------------------------------------------------------
-- Hosting_slot entities represent specific appearances of the Ball on particular
-- domains.  They are associated with particular hosting blocks indirectly through
-- bids, and through blocks with specific games.  Each has an associated collection
-- of related brain-teasers, a chain of target objects to find, key details, and a
-- game-win puzzle.  Each is ordered relative to the other slots in its block by a
-- sequence number, and has associated times when it started and stopped
-- hosting the Ball, along with a limiting number of minutes that the Ball may remain
-- in this slot.
-- ------------------------------------------------------------

CREATE TABLE hosting_slot (
  id BIGINT NOT NULL AUTO_INCREMENT,
  bid_id BIGINT NOT NULL,
  sequence_number INTEGER UNSIGNED NOT NULL,
  hosting_start DATETIME NULL,
  hosting_end DATETIME NULL,
  PRIMARY KEY(id),
  UNIQUE INDEX hosting_slot_unique_bid(bid_id),
  FOREIGN KEY(bid_id)
    REFERENCES bid(id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
)
TYPE=InnoDB;

-- ------------------------------------------------------------
-- An effective_bid entity represents the amount that would be paid by the
-- bidding sponsor if the associated bid won.  This may be updated periodically
-- by the auction engine in response to automated proxy bidding on behalf of the
-- bidding sponsor.  A bid that has been outbid will not have an associated
-- effective_bid.
-- ------------------------------------------------------------

CREATE TABLE effective_bid (
  bid_id BIGINT NOT NULL,
  current_amount INTEGER UNSIGNED NOT NULL,
  PRIMARY KEY(bid_id),
  INDEX Table_27_FKIndex1(bid_id),
  FOREIGN KEY(bid_id)
    REFERENCES bid(id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
)
TYPE=InnoDB;

-- ------------------------------------------------------------
-- The puzzle_for_slot relation associates the appropriate game-win puzzle with
-- a particular hosting slot.
-- ------------------------------------------------------------

CREATE TABLE puzzle_for_slot (
  hosting_slot_id BIGINT NOT NULL,
  puzzle_id BIGINT NOT NULL,
  PRIMARY KEY(hosting_slot_id),
  INDEX game_win_puzzle_FKIndex1(hosting_slot_id),
  INDEX game_win_puzzle_FKIndex2(puzzle_id),
  FOREIGN KEY(hosting_slot_id)
    REFERENCES hosting_slot(id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
  FOREIGN KEY(puzzle_id)
    REFERENCES puzzle(id)
      ON DELETE CASCADE
      ON UPDATE NO ACTION
)
TYPE=InnoDB;

-- ------------------------------------------------------------
-- Target_object entities represent the identifiers of the target
-- objects in the chain of links associated with a specific hosting
-- slot.  Each one has a sequence number within the chain and a
-- hex-encoded hash of the target identifier, plus a path (relative
-- to the host domain) to the location of the target and the ID of
-- the clue image to provide for this target
-- ------------------------------------------------------------

CREATE TABLE target_object (
  id BIGINT NOT NULL AUTO_INCREMENT,
  hosting_slot_id BIGINT NOT NULL,
  sequence_number INTEGER UNSIGNED NOT NULL,
  uri_path VARCHAR(255) NOT NULL,
  identifier_text VARCHAR(20) NULL,
  identifier_hash VARCHAR(40) NOT NULL,
  clue_img_id BIGINT NOT NULL,
  PRIMARY KEY(id),
  INDEX target_object_FKIndex1(hosting_slot_id),
  UNIQUE INDEX slot_seq_unique(hosting_slot_id, sequence_number),
  INDEX target_object_FKIndex2(clue_img_id),
  FOREIGN KEY(hosting_slot_id)
    REFERENCES hosting_slot(id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
  FOREIGN KEY(clue_img_id)
    REFERENCES download_obj(id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
)
TYPE=InnoDB;

-- ------------------------------------------------------------
-- The brn_tsr_for_slot relation associates hosting slots with their
-- brain teasers.
-- ------------------------------------------------------------

CREATE TABLE brn_tsr_for_slot (
  hosting_slot_id BIGINT NOT NULL,
  sequence_number INTEGER UNSIGNED NOT NULL,
  puzzle_id BIGINT NOT NULL,
  PRIMARY KEY(hosting_slot_id, sequence_number),
  INDEX brain_teaser_FKIndex1(hosting_slot_id),
  INDEX brain_teaser_FKIndex2(puzzle_id),
  FOREIGN KEY(hosting_slot_id)
    REFERENCES hosting_slot(id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
  FOREIGN KEY(puzzle_id)
    REFERENCES puzzle(id)
      ON DELETE CASCADE
      ON UPDATE NO ACTION
)
TYPE=InnoDB;

-- ------------------------------------------------------------
-- The plyr_compltd_slot relation establishes which players have
-- 'completed' (i.e. found a key or the Ball associated with)  which
-- hosting slots, and records a timestamp of when they did so.
-- ------------------------------------------------------------

CREATE TABLE plyr_compltd_slot (
  hosting_slot_id BIGINT NOT NULL,
  player_id BIGINT NOT NULL,
  timestamp DATETIME NOT NULL,
  key_text VARCHAR(8) NOT NULL,
  key_image_id BIGINT NOT NULL,
  PRIMARY KEY(hosting_slot_id, player_id),
  INDEX slot_completion_FKIndex1(player_id),
  INDEX slot_completion_FKIndex2(hosting_slot_id),
  INDEX plyr_compltd_slot_FKIndex3(key_image_id),
  FOREIGN KEY(player_id)
    REFERENCES player(any_user_id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
  FOREIGN KEY(hosting_slot_id)
    REFERENCES hosting_slot(id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
  FOREIGN KEY(key_image_id)
    REFERENCES download_obj(id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
)
TYPE=InnoDB;


