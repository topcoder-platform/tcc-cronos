-- ------------------------------------------------------------
-- any_user entities represent the identifying and authorization
-- information for specific players, admins, and sponsors.
-- ------------------------------------------------------------

CREATE TABLE any_user (
  id BIGINT NOT NULL,
  PRIMARY KEY(id),
)

-- ------------------------------------------------------------
-- users who are sponsors
-- ------------------------------------------------------------

CREATE TABLE sponsor (
  any_user_id BIGINT NOT NULL,
  is_approved char(1) NULL,
  PRIMARY KEY(any_user_id),
  FOREIGN KEY(any_user_id)
    REFERENCES any_user(id)
)

-- ------------------------------------------------------------
-- Users who are players
-- ------------------------------------------------------------

CREATE TABLE player (
  any_user_id BIGINT NOT NULL,
  PRIMARY KEY(any_user_id),
  FOREIGN KEY(any_user_id)
    REFERENCES any_user(id)
)

-- ------------------------------------------------------------
-- Download_obj entities represent binary objects of specified media
-- type. They are generally intended to be provided for download from
-- the server, but can be used internally instead.
-- ------------------------------------------------------------

CREATE TABLE download_obj (
  id BIGINT NOT NULL IDENTITY,
  media_type VARCHAR(255) NOT NULL,
  content varbinary(8000) NOT NULL,
  PRIMARY KEY(id)
)

-- ------------------------------------------------------------
-- A puzzle entity is a generic representation of a puzzle of a puzzle: -- a brain-teaser, word puzzle, tile puzzle, jigsaw puzzle, etc.  It
-- provides a reference with which to associate any number of
-- "attributes" and "resources".
-- ------------------------------------------------------------

CREATE TABLE puzzle (
  id BIGINT NOT NULL IDENTITY,
  name VARCHAR(255) NULL,
  PRIMARY KEY(id)
)

-- ------------------------------------------------------------
-- Puzzle_attribute entities associate textual attributes of coded
-- types with specific puzzles.
-- ------------------------------------------------------------

CREATE TABLE puzzle_attribute (
  id BIGINT NOT NULL IDENTITY,
  puzzle_id BIGINT NOT NULL,
  name VARCHAR(255) NOT NULL,
  value VARCHAR(255) NOT NULL,
  PRIMARY KEY(id),
  FOREIGN KEY(puzzle_id)
    REFERENCES puzzle(id)
)
-- ------------------------------------------------------------
-- Puzzle_resource entities associate arbitrary binary objects with
-- specific puzzle entities.  They provide coded resource type
-- information, textual resource information.
-- ------------------------------------------------------------

CREATE TABLE puzzle_resource (
  id BIGINT NOT NULL IDENTITY,
  puzzle_id BIGINT NOT NULL,
  name VARCHAR(255) NOT NULL,
  download_obj_id BIGINT NOT NULL,
  PRIMARY KEY(id),
  FOREIGN KEY(puzzle_id)
    REFERENCES puzzle(id),
  FOREIGN KEY(download_obj_id)
    REFERENCES download_obj(id)
)

-- ------------------------------------------------------------
-- Game entities represent individual games provided by the
-- application.
-- ------------------------------------------------------------

CREATE TABLE game (
  id BIGINT NOT NULL IDENTITY,
  start_date DATETIME NOT NULL,
  PRIMARY KEY(id),
)

-- ------------------------------------------------------------
-- The plyr_regstrd_game relation establishes which players
-- are registered for which games.
-- ------------------------------------------------------------

CREATE TABLE plyr_regstrd_game (
  game_id BIGINT NOT NULL,
  player_id BIGINT NOT NULL,
  PRIMARY KEY(game_id, player_id),
  FOREIGN KEY(player_id)
    REFERENCES player(any_user_id),
  FOREIGN KEY(game_id)
    REFERENCES game(id)
)

-- ------------------------------------------------------------
-- The plyr_compltd_game relation establishes which players have
-- completed which games, and provides a global serial number by
-- which to determine the order in which the players achieved their
-- game completions.
-- ------------------------------------------------------------

CREATE TABLE plyr_compltd_game (
  game_id BIGINT NOT NULL,
  player_id BIGINT NOT NULL,
  sequence_number BIGINT NOT NULL IDENTITY,
  is_handled char(1) NOT NULL DEFAULT 'N',
  PRIMARY KEY(game_id, player_id),
  FOREIGN KEY(game_id)
    REFERENCES game(id),
  FOREIGN KEY(player_id)
    REFERENCES player(any_user_id)
)

-- ------------------------------------------------------------
-- Domain entities represent web sites that (it is hoped) are
-- eligible to be host sites.  Each is associated with a specific
-- sponsor, and is characterized by the base URL of the site.
-- ------------------------------------------------------------

CREATE TABLE domain (
  id BIGINT NOT NULL IDENTITY,
  sponsor_id BIGINT NOT NULL,
  base_url VARCHAR(255) NOT NULL,
  is_approved char(1) NULL,
  PRIMARY KEY(id),
  FOREIGN KEY(sponsor_id)
    REFERENCES sponsor(any_user_id)
)

-- ------------------------------------------------------------
-- Records game-win events, identifying the winner, win approval
-- timestamp, and payout amount.  Presence of a row in this table
-- for a specific game ID signals that that game is over.
-- ------------------------------------------------------------

CREATE TABLE plyr_won_game (
  game_id BIGINT NOT NULL,
  player_id BIGINT NOT NULL,
  date DATETIME NOT NULL,
  payout BIGINT NOT NULL,
  PRIMARY KEY(game_id),
  FOREIGN KEY(player_id)
    REFERENCES player(any_user_id),
  FOREIGN KEY(game_id)
    REFERENCES game(id)
)

-- ------------------------------------------------------------
-- Hosting_block entities aggregate hosting slots, providing for
-- group-wise sequencing and provision of slots.
-- ------------------------------------------------------------
CREATE TABLE hosting_block (
  id BIGINT NOT NULL IDENTITY,
  game_id BIGINT NOT NULL,
  PRIMARY KEY(id),
  FOREIGN KEY(game_id)
    REFERENCES game(id)
)

-- ------------------------------------------------------------
-- Represents an auction for slots from a hosting block. Each auction
-- has a specific start time and end time, a minimum bid, a minimum bid
-- increment, and a count of individual items for sale. Slots are
-- auctioned anonymously, so that the n highest bids in each auction
-- win slots (where n is the number of slots in the block).
-- ------------------------------------------------------------
CREATE TABLE auction (
hosting_block_id BIGINT NOT NULL,
start_time DATETIME NOT NULL,
end_time DATETIME NOT NULL,
min_bid BIGINT NOT NULL,
bid_increment BIGINT NOT NULL,
item_count BIGINT NOT NULL,
PRIMARY KEY(hosting_block_id),
FOREIGN KEY(hosting_block_id)
REFERENCES hosting_block(id)
)

-- ------------------------------------------------------------
-- Image entities represent the domain-specific images associated
-- with sponsored domains.  In addition to a specific domain, each is
-- associated with a download_obj containing the binary image data.
-- Each bears a flag indicating whether the image has been approved or
-- rejected, if any such decision has yet been made.
-- ------------------------------------------------------------

CREATE TABLE image (
  id BIGINT NOT NULL IDENTITY,
  domain_id BIGINT NOT NULL,
  download_obj_id BIGINT NOT NULL,
  is_approved char(1) NULL,
  description VARCHAR(255) NOT NULL,
  PRIMARY KEY(id),
  FOREIGN KEY(domain_id)
    REFERENCES domain(id),
  FOREIGN KEY(download_obj_id)
    REFERENCES download_obj(id)
)

-- ------------------------------------------------------------
-- Represents a bid placed by a sponsor to host the application at a
-- specific domain using a specific domain image.  (Domain is implied
-- by image, and sponsor by domain.)
-- The auto-incrementing id of this table serves to establish a total
-- order of bids (reception order), even when two or more are received
-- at the same time within the granularity of the timestamp. The actual
-- current amount that the sponsor would pay if this bid won is
-- specified separately, via an effective_bid entity.
-- ------------------------------------------------------------

CREATE TABLE bid (
  id BIGINT NOT NULL IDENTITY,
  image_id BIGINT NOT NULL,
  auction_id BIGINT NOT NULL,
  PRIMARY KEY(id),
  FOREIGN KEY(image_id)
    REFERENCES image(id),
  FOREIGN KEY(auction_id)
    REFERENCES auction(hosting_block_id)
)

-- ------------------------------------------------------------
-- An effective_bid entity represents the amount that would be paid by
-- the bidding sponsor if the associated bid won.
-- ------------------------------------------------------------

CREATE TABLE effective_bid (
  bid_id BIGINT NOT NULL,
  current_amount BIGINT NOT NULL,
  PRIMARY KEY(bid_id),
  FOREIGN KEY(bid_id)
    REFERENCES bid(id)
)

-- ------------------------------------------------------------
-- Hosting_slot entities represent specific application appearances
-- on particular domains.  They are associated with particular hosting
-- blocks indirectly through bids, and through blocks with specific
-- games.  Each has an associated collection of related brain-teasers,
-- a chain of target objects to find, key details, and a game-win
-- puzzle.  Each is ordered relative to the other slots in its block by
-- a sequence number, and has associated times when it started and
-- stopped hosting the Ball, along with a limiting number of minutes
-- that the Ball may remain in this slot.
-- ------------------------------------------------------------

CREATE TABLE hosting_slot (
  id BIGINT NOT NULL IDENTITY,
  bid_id BIGINT NOT NULL,
  sequence_number BIGINT NOT NULL,
  hosting_start DATETIME NULL,
  hosting_end DATETIME NULL,
  PRIMARY KEY(id),
  FOREIGN KEY(bid_id)
    REFERENCES bid(id)
)

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
  FOREIGN KEY(player_id)
    REFERENCES player(any_user_id),
  FOREIGN KEY(hosting_slot_id)
    REFERENCES hosting_slot(id),
  FOREIGN KEY(key_image_id)
    REFERENCES download_obj(id)
)

-- ------------------------------------------------------------
-- Represents a generic message with category, update timestamp,
-- and typed content.
-- ------------------------------------------------------------

CREATE TABLE message (
  id BIGINT NOT NULL IDENTITY,
  guid VARCHAR(255) NOT NULL,
  category VARCHAR(20) NOT NULL,
  content_type VARCHAR(255) NOT NULL,
  update_time DATETIME NOT NULL,
  content TEXT NOT NULL,
  PRIMARY KEY(id)
)

-- ------------------------------------------------------------
-- A stored procedures that inserts a puzzle name and returns
-- the auto-generated ID.
-- ------------------------------------------------------------

CREATE PROCEDURE InsertPuzzle
  @name varchar(255),
  @id BIGINT OUTPUT
AS
  INSERT INTO puzzle(name) VALUES (@name)
  SET @id = @@IDENTITY
GO

-- ------------------------------------------------------------
-- A stored procedures that inserts a game and returns the
-- auto-generated ID.
-- ------------------------------------------------------------

CREATE PROCEDURE InsertGame
  @start_date DATETIME,
  @id BIGINT OUTPUT
AS
  INSERT INTO game(start_date) VALUES (@start_date)
  SET @id = @@IDENTITY
GO

-- ------------------------------------------------------------
-- A stored procedures that inserts a domain and returns the
-- auto-generated ID.
-- ------------------------------------------------------------

CREATE PROCEDURE InsertDomain
  @sponsor_id BIGINT,
  @base_url VARCHAR(255),
  @id BIGINT OUTPUT
AS
  INSERT INTO domain(sponsor_id, base_url) VALUES (@sponsor_id, @base_url)
  SET @id = @@IDENTITY
GO


-- ------------------------------------------------------------
-- A stored procedures that inserts a download object and
-- returns the auto-generated ID.
-- ------------------------------------------------------------

CREATE PROCEDURE InsertDownloadObject
  @media_type VARCHAR(255),
  @content varbinary(8000),
  @id BIGINT OUTPUT
AS
  INSERT INTO download_obj(media_type, content) VALUES (@media_type, @content)
  SET @id = @@IDENTITY
GO


-- ------------------------------------------------------------
-- A stored procedures that inserts an image and returns the
-- auto-generated ID.
-- ------------------------------------------------------------

CREATE PROCEDURE InsertImage
  @domain_id BIGINT,
  @download_obj_id BIGINT,
  @description VARCHAR(255),
  @id BIGINT OUTPUT
AS
  INSERT INTO image(domain_id, download_obj_id, description) VALUES (@domain_id, @download_obj_id, @description)
  SET @id = @@IDENTITY
GO

-- ------------------------------------------------------------
-- A stored procedures that inserts a hosting block and returns
-- the auto-generated ID.
-- ------------------------------------------------------------

CREATE PROCEDURE InsertHostingBlock
  @game_id BIGINT,
  @id BIGINT OUTPUT
AS
  INSERT INTO hosting_block (game_id) VALUES (@game_id)
  SET @id = @@IDENTITY
GO

-- ------------------------------------------------------------
-- A stored procedures that inserts a bid and returns
-- the auto-generated ID.
-- ------------------------------------------------------------

CREATE PROCEDURE InsertBid
  @image_id BIGINT,
  @auction_id BIGINT,
  @id BIGINT OUTPUT
AS
  INSERT INTO bid (image_id, auction_id) VALUES (@image_id, @auction_id)
  SET @id = @@IDENTITY
GO

-- ------------------------------------------------------------
-- A stored procedures that inserts a hosting slot and returns
-- the auto-generated ID.
-- ------------------------------------------------------------

CREATE PROCEDURE InsertHostingSlot
  @bid_id BIGINT,
  @sequence_number BIGINT,
  @hosting_start DATETIME,
  @id BIGINT OUTPUT
AS
  INSERT INTO hosting_slot (bid_id, sequence_number, hosting_start) VALUES (@bid_id, @sequence_number, @hosting_start)
  SET @id = @@IDENTITY
GO
