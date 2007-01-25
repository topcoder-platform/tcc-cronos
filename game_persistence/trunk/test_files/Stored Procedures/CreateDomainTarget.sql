-- =============================================================
-- Author:		TCSDEVELOPER
-- Create date: 10-29-2006
-- Method:      GamePersistence.SQLServerGameDataDAO.updateSlots
-- Description:	It will insert a column into 'target_object' table	
-- ==============================================================

CREATE PROCEDURE CreateDomainTarget
	 @domainTargetId bigint
	,@slotId bigint
	,@sequenceNumber int
	,@uriPath varchar(255)
	,@inentifierText varchar(20)
	,@inentifierHash varchar(40)
	,@clueImageId bigint
	,@ID bigint OUTPUT
AS
BEGIN
	SET NOCOUNT ON
	-- if the gameId is not null,use it or auto increment the id.
	IF @domainTargetId is not null
	BEGIN
		UPDATE target_object 
		SET hosting_slot_id=@slotId, sequence_number = @sequenceNumber, uri_path = @uriPath,identifier_text = @inentifierText, identifier_hash = @inentifierHash, clue_img_id=@clueImageId
		WHERE id = @domainTargetId 
	END
	ELSE
	BEGIN
		INSERT INTO target_object(hosting_slot_id,sequence_number,uri_path,
					identifier_text,identifier_hash,clue_img_id)
		VALUES(@slotId,@sequenceNumber,@uriPath,@inentifierText,@inentifierHash,@clueImageId)
	END

	-- return the ID
	IF @domainTargetId is not null
		SET @ID = @domainTargetId
	ELSE
		SET @ID = (SELECT SCOPE_IDENTITY())
END;
