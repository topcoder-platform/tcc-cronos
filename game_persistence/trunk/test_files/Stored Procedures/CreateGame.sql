-- =============================================================
-- Author:		TCSDEVELOPER
-- Create date: 10-29-2006
-- Method:      GamePersistence.SQLServerGameDataDAO.createGame
-- Description:	It will insert a column into 'game' table	
-- ==============================================================

CREATE PROCEDURE CreateGame
	 @gameId bigint
	,@ballColorId bigint
	,@keyCount int
	,@startDate datetime
	,@ID bigint OUTPUT
AS
BEGIN
	SET NOCOUNT ON;
	-- if the gameId is not null,use it or auto increment the id.
	IF @gameId != null
	BEGIN
		INSERT INTO game(id,ball_color_id,start_date,keys_required)
		VALUES(@gameId,@ballColorId,@startDate,@keyCount);
	END
	ELSE
	BEGIN
		INSERT INTO game(ball_color_id,start_date,keys_required)
		VALUES(@ballColorId,@startDate,@keyCount);
	END

	-- return the ID
	IF @gameId !=null
		SET @ID = @gameId;
	ELSE
		SET @ID = (SELECT SCOPE_IDENTITY()) ;
END
GO
