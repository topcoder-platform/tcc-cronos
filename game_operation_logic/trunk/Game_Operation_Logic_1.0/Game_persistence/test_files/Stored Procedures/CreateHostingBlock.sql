-- =============================================================
-- Author:		TCSDEVELOPER
-- Version:     1.0
-- Create date: 10-29-2006
-- Method:      GamePersistence.SQLServerGameDataDAO.createGame
-- Description:	It will insert a column into 'hosting_block' table	
-- ==============================================================

CREATE PROCEDURE CreateHostingBlock
	 @gameId bigint
	,@sequenceNumber int
	,@slotMaxHostingTime int
	,@ID bigint OUTPUT
AS
BEGIN
	SET NOCOUNT ON;
	BEGIN
		INSERT INTO hosting_block(game_id,sequence_number,max_time_per_slot)
		VALUES(@gameId,@sequenceNumber,@slotMaxHostingTime);
	END
	
	SET @ID = (SELECT SCOPE_IDENTITY()) ;
END
GO
