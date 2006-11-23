-- ------------------------------------------------------------
-- Any_user entities represent the identifying and authorization
-- information for specific users.
-- ------------------------------------------------------------
CREATE TABLE any_user (
  id BIGINT NOT NULL,
  handle VARCHAR(29) NOT NULL,
  e_mail VARCHAR(255) NOT NULL,
  PRIMARY KEY(id)
)

ALTER TABLE any_user
   ADD CONSTRAINT user_handle_unique UNIQUE (handle);
   
ALTER TABLE any_user
   ADD CONSTRAINT user_email_unique UNIQUE (e_mail);
   
-- ------------------------------------------------------------
-- Game entities represent individual games managed by the application.
-- ------------------------------------------------------------
CREATE TABLE game (
  id BIGINT NOT NULL IDENTITY(1,1),
  PRIMARY KEY(id),
)

-- ------------------------------------------------------------
-- Sponsor entities represent game sponsor representatives
-- eligible to place bids.
-- ------------------------------------------------------------
CREATE TABLE sponsor (
  any_user_id BIGINT NOT NULL,
  PRIMARY KEY(any_user_id),
  FOREIGN KEY(any_user_id)
    REFERENCES any_user(id)
)

-- ------------------------------------------------------------
-- Domain entities represent web sites that (it is hoped) are
-- eligible to host the application.  Each is associated with a
-- specific sponsor
-- ------------------------------------------------------------
CREATE TABLE domain (
  id BIGINT NOT NULL IDENTITY(1,1),
  sponsor_id BIGINT NOT NULL,
  PRIMARY KEY(id),
  FOREIGN KEY(sponsor_id)
    REFERENCES sponsor(any_user_id)
)


-- ------------------------------------------------------------
-- Image entities represent the domain-specific images associated
-- with sponsored domains.
-- ------------------------------------------------------------
CREATE TABLE image (
  id BIGINT NOT NULL IDENTITY(1,1),
  domain_id BIGINT NOT NULL,
  PRIMARY KEY(id),
  FOREIGN KEY(domain_id)
    REFERENCES domain(id)
)

-- ------------------------------------------------------------
-- Hosting_block entities aggregate hosting slots, providing for
-- group-wise sequencing and provision of slots.
-- ------------------------------------------------------------
CREATE TABLE hosting_block (
  id BIGINT NOT NULL IDENTITY(1,1),
  PRIMARY KEY(id)
)

-- ------------------------------------------------------------
-- Represents an auction for slots from a hosting block.  Each auction
-- has a specific start time and end time, a minimum bid, a minimum bid
-- increment, and a count of individual items for sale.  Slots are
-- auctioned anonymously, so that the n highest bids in each auction
-- win slots (where n is the number of slots in the block).
-- ------------------------------------------------------------
CREATE TABLE auction (
  hosting_block_id BIGINT NOT NULL,
  start_time DATETIME NOT NULL,
  end_time DATETIME NOT NULL,
  min_bid INTEGER NOT NULL,
  bid_increment INTEGER,
  item_count INTEGER NOT NULL,
  PRIMARY KEY(hosting_block_id),
  FOREIGN KEY(hosting_block_id)
    REFERENCES hosting_block(id)
)


-- ------------------------------------------------------------
-- Represents a bid placed by a sponsor to host the application at a
-- specific domain using a specific domain image.  (Domain is implied
-- by image, and sponsor by domain.) The auto-incrementing id of this
-- table serves to establish a total order of bids (reception order),
-- even when two or more are received at the same time within the
-- granularity of the timestamp.  The sponsor also specifies the
-- maximum amount he is willing to pay, for the purpose of automated
-- proxy bidding.  The actual current amount that the sponsor would pay
-- if this bid won (often less than max_amount) is specified
-- separately, via an effective_bid entity.
-- ------------------------------------------------------------
CREATE TABLE bid (
  id BIGINT NOT NULL IDENTITY(1,1),
  auction_id BIGINT NOT NULL,
  image_id BIGINT NOT NULL,
  max_amount INTEGER NOT NULL,
  time DATETIME NOT NULL,
  PRIMARY KEY(id),
  FOREIGN KEY(auction_id)
    REFERENCES auction(hosting_block_id),
  FOREIGN KEY(image_id)
    REFERENCES image(id)
)

-- ------------------------------------------------------------
-- An effective_bid entity represents the amount that would be paid by
-- the bidding sponsor if the associated bid won.  This may be updated
-- periodically by the auction engine in response to automated proxy
-- bidding on behalf of the bidding sponsor.  A bid that has been
-- outbid will not have an associated effective_bid.
-- ------------------------------------------------------------
CREATE TABLE effective_bid (
  bid_id BIGINT NOT NULL,
  current_amount INTEGER NOT NULL,
  PRIMARY KEY(bid_id),
  FOREIGN KEY(bid_id)
    REFERENCES bid(id)
)