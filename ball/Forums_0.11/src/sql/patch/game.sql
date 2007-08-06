--
-- This script provides the statement for altering the existing tables to apply latest changes to
-- The Ball data model
--

-- Add END_DATE column to GAME table. Locate the completed games among existing games and set their END_DATE with
-- date of approval of first winner by Administrator
ALTER TABLE game ADD end_date DATETIME;
UPDATE game SET end_date = (SELECT MIN(date) FROM plyr_won_game WHERE game.id = plyr_won_game.game_id);

-- Add BOUNCE_CALC_TYPE and PRIZE_CALC_TYPE columns to GAME table. For all existing games the BOUNCE_CALC_TYPE column
-- is set with 1 which should correspond to NO BOUNCE POINTS AWARDED schema. For all existing games the PRIZE_CALC_TYPE
-- column is set with 1 which should correspond to FIXED PRIZE AWARDED schema. For existing games with JACKPOT
-- PERCENTAGE PRIZE AWARDED schema the PRIZE_CALC_TYPE column must be set with 2 manually
ALTER TABLE game ADD bounce_calc_type INTEGER;
ALTER TABLE game ADD prize_calc_type INTEGER;
UPDATE game SET bounce_calc_type = 1;
UPDATE game SET prize_calc_type = 1;
ALTER TABLE game ALTER COLUMN bounce_calc_type INTEGER NOT NULL;
ALTER TABLE game ALTER COLUMN prize_calc_type INTEGER NOT NULL;

-- Remove NOT NULL constraint for SPONSOR_ID column from DOMAIN table
ALTER TABLE domain ALTER COLUMN sponsor_id BIGINT;

-- Add COMPLETION_TYPE column to GAME table. For all existing games the COMPLETION_TYPE
-- column is set with 1 which should correspond to LAST WINNER APPROVED schema.
ALTER TABLE game ADD completion_type INTEGER;
UPDATE game SET completion_type = 1;
ALTER TABLE game ALTER COLUMN completion_type INTEGER NOT NULL;

-- Create BID_FOR_SLOT table linking the existing hosting slots with bids and insert the records based on existing
-- records from HOSTING_SLOT table
CREATE TABLE bid_for_slot (
  bid_id BIGINT NOT NULL,
  hosting_slot_id BIGINT NOT NULL,
  FOREIGN KEY(hosting_slot_id) REFERENCES hosting_slot(id)
    ON DELETE NO ACTION,
  FOREIGN KEY(bid_id) REFERENCES bid(id)
    ON DELETE NO ACTION
);
INSERT INTO bid_for_slot (bid_id,hosting_slot_id) (SELECT bid_id,id FROM hosting_slot);

-- Drop the BID_ID colimn from HOSTING_SLOT table and add new HOSTING_BLOCK_ID, IMAGE_ID and HOSTING_PAYMENT columns
-- to that table along with respective referential constraints. For all existing records the values for newly added
-- columns are set with values from respective records from BID table
ALTER TABLE hosting_slot ADD hosting_block_id BIGINT;
ALTER TABLE hosting_slot ADD image_id BIGINT;
ALTER TABLE hosting_slot ADD hosting_payment INT;
UPDATE hosting_slot SET hosting_block_id = (SELECT auction_id FROM bid WHERE bid.id = hosting_slot.bid_id);
UPDATE hosting_slot SET image_id = (SELECT image_id FROM bid WHERE bid.id = hosting_slot.bid_id);
UPDATE hosting_slot SET hosting_payment
    = (SELECT effective_bid.current_amount FROM bid INNER JOIN effective_bid ON bid.id = effective_bid.bid_id
       WHERE bid.id = hosting_slot.bid_id);
ALTER TABLE hosting_slot ALTER COLUMN hosting_block_id BIGINT NOT NULL;
ALTER TABLE hosting_slot ALTER COLUMN image_id BIGINT NOT NULL;
ALTER TABLE hosting_slot ALTER COLUMN hosting_payment INT NOT NULL;
ALTER TABLE hosting_slot DROP CONSTRAINT UQ__hosting_slot__440B1D61;
ALTER TABLE hosting_slot DROP CONSTRAINT FK__hosting_s__bid_i__44FF419A;
ALTER TABLE hosting_slot DROP COLUMN bid_id;
ALTER TABLE hosting_slot ADD CONSTRAINT FK__hosting_block FOREIGN KEY(hosting_block_id) REFERENCES hosting_block(id)
    ON DELETE CASCADE;
ALTER TABLE hosting_slot ADD CONSTRAINT FK__image FOREIGN KEY(image_id) REFERENCES image(id) ON DELETE NO ACTION;


-- Drop CreateGame procedure and re-create it with addition of new input parameters for end_date, bounce_calc_type and
-- prize_calc_type columns  
DROP PROCEDURE CreateGame;
CREATE PROCEDURE CreateGame
     @gameId bigint
    ,@ballColorId bigint
    ,@keyCount int
    ,@startDate datetime
    ,@endDate datetime
    ,@bounceCalcType int
    ,@prizeCalcType int
    ,@completionType int
    ,@ID bigint OUTPUT
AS
BEGIN
    SET NOCOUNT ON
    -- if the gameId is not null,use it or auto increment the id.
    IF @gameId != null
    BEGIN
        INSERT INTO game(id,ball_color_id,start_date,keys_required,end_date,bounce_calc_type,prize_calc_type,completion_type)
        VALUES(@gameId,@ballColorId,@startDate,@keyCount,@endDate,@bounceCalcType,@prizeCalcType,@completionType)
    END
    ELSE
    BEGIN
        INSERT INTO game(ball_color_id,start_date,keys_required,end_date,bounce_calc_type,prize_calc_type,completion_type)
        VALUES(@ballColorId,@startDate,@keyCount,@endDate,@bounceCalcType,@prizeCalcType,@completionType)
    END

    -- return the ID
    IF @gameId !=null
        SET @ID = @gameId
    ELSE
        SET @ID = (SELECT SCOPE_IDENTITY())
END;
