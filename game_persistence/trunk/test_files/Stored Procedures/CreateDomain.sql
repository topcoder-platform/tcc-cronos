-- =============================================================
-- Author:		TCSDEVELOPER
-- Version:     1.0
-- Create date: 10-29-2006
-- Method:      GamePersistence.SQLServerGameDataDAO.createDomain
-- Description:	It will insert a record into 'domain' table	
-- ==============================================================

CREATE PROCEDURE CreateDomain
	 @domainId bigint
	,@sponsorId bigint
	,@domainName varchar(255)
	,@isApproved bit
	,@ID bigint OUTPUT
AS
BEGIN
	SET NOCOUNT ON;

	IF @domainId !=  null
	BEGIN
		INSERT INTO domain(id,sponsor_id,base_url,is_approved)
		VALUES(@domainId,@sponsorId,@domainName,@isApproved);
	END
	ELSE
	BEGIN
		INSERT INTO domain(sponsor_id,base_url,is_approved)
		VALUES(@sponsorId,@domainName,@isApproved);
	END

	--return the id
	IF @domainId != null
		SET @ID = @domainId;
	ELSE
		SET @ID = (SELECT SCOPE_IDENTITY()) ;
END
GO
