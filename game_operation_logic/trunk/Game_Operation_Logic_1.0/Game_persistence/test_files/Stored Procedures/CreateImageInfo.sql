-- =============================================================
-- Author:		TCSDEVELOPER
-- Version:     1.0
-- Create date: 10-29-2006
-- Method:      GamePersistence.SQLServerGameDataDAO.createDomain
-- Description:	It will insert a record into 'image' table	
-- ==============================================================

CREATE PROCEDURE CreateImageInfo
	 @imageId bigint
	,@domainId bigint
	,@downloadId bigint
	,@isApproved bit
	,@description varchar(255)
	,@ID bigint OUTPUT
AS
BEGIN
	SET NOCOUNT ON;

	IF @imageId !=  null
	BEGIN
		INSERT INTO image(id,domain_id,download_obj_id,is_approved,description)
		VALUES(@imageId,@domainId,@downloadId,@isApproved,@description);
	END
	ELSE
	BEGIN
		INSERT INTO image(domain_id,download_obj_id,is_approved,description)
		VALUES(@domainId,@downloadId,@isApproved,@description);
	END

	--return the id
	IF @imageId != null
		SET @ID = @imageId;
	ELSE
		SET @ID = (SELECT SCOPE_IDENTITY()) ;
END
GO
