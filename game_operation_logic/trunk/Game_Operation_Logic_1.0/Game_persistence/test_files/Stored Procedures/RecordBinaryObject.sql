-- =============================================================
-- Author:		TCSDEVELOPER
-- Version:     1.0
-- Create date: 10-29-2006
-- Method:      GamePersistence.SQLServerGameDataDAO.recordBinaryObject
-- Description:	It will insert a record into 'download_obj' table	
-- ==============================================================

CREATE PROCEDURE RecordBinaryObject
	 @name varchar(255)
	,@mediaType varchar(255)
	,@content image
	,@ID bigint OUTPUT
AS
BEGIN
	SET NOCOUNT ON;

	
	BEGIN
		INSERT INTO download_obj(media_type,suggested_name,content)
		VALUES(@mediaType,@name,@content);
	END

	SET @ID = (SELECT SCOPE_IDENTITY()) ;
END
GO
