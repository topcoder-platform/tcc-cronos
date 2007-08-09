-- =============================================================
-- Author:        TCSDEVELOPER
-- Version:     1.0
-- Create date: 10-29-2006
-- Method:      GamePersistence.SQLServerGameDataDAO.createDomain
-- Description:    It will insert a record into 'domain' table    
-- ==============================================================

CREATE PROCEDURE CreateDomain
     @domainId bigint
    ,@sponsorId bigint
    ,@domainName varchar(255)
    ,@isApproved bit
    ,@ID bigint OUTPUT
AS
BEGIN
    SET NOCOUNT ON

    IF @domainId !=  null
    BEGIN
        INSERT INTO domain(id,sponsor_id,base_url,is_approved) VALUES(@domainId,@sponsorId,@domainName,@isApproved)
    END
    ELSE
    BEGIN
        INSERT INTO domain(sponsor_id,base_url,is_approved) VALUES(@sponsorId,@domainName,@isApproved)
    END

    --return the id
    IF @domainId != null
        SET @ID = @domainId
    ELSE
        SET @ID = (SELECT SCOPE_IDENTITY())
END;


-- =============================================================
-- Author:        TCSDEVELOPER
-- Create date: 10-29-2006
-- Method:      GamePersistence.SQLServerGameDataDAO.updateSlots
-- Description:    It will insert a column into 'target_object' table    
-- ==============================================================
CREATE PROCEDURE CreateDomainTarget
     @domainTargetId bigint
    ,@slotId bigint
    ,@sequenceNumber int
    ,@uriPath varchar(255)
    ,@identifierText nvarchar(20)
    ,@identifierHash varchar(40)
    ,@clueImageId bigint
    ,@ID bigint OUTPUT
AS
BEGIN
    SET NOCOUNT ON
    IF @domainTargetId IS NOT NULL BEGIN
        UPDATE target_object 
        SET hosting_slot_id=@slotId, sequence_number=@sequenceNumber, uri_path=@uriPath,
                    identifier_text=@identifierText, identifier_hash=@identifierHash, clue_img_id=@clueImageId
        WHERE id=@domainTargetId 
    END
    ELSE BEGIN
        INSERT INTO target_object(hosting_slot_id, sequence_number, uri_path,
                    identifier_text, identifier_hash, clue_img_id)
        VALUES(@slotId, @sequenceNumber, @uriPath, @identifierText, @identifierHash, @clueImageId)
    END
    IF @domainTargetId IS NOT NULL
        SET @ID = @domainTargetId
    ELSE
        SET @ID = (SELECT SCOPE_IDENTITY())
END;


-- =============================================================
-- Author:        TCSDEVELOPER
-- Create date: 10-29-2006
-- Method:      GamePersistence.SQLServerGameDataDAO.createGame
-- Description:    It will insert a column into 'game' table    
-- ==============================================================

CREATE PROCEDURE CreateGame
     @gameId bigint
    ,@ballColorId bigint
    ,@keyCount int
    ,@startDate datetime
    ,@ID bigint OUTPUT
AS
BEGIN
    SET NOCOUNT ON
    -- if the gameId is not null,use it or auto increment the id.
    IF @gameId != null
    BEGIN
        INSERT INTO game(id,ball_color_id,start_date,keys_required)
        VALUES(@gameId,@ballColorId,@startDate,@keyCount)
    END
    ELSE
    BEGIN
        INSERT INTO game(ball_color_id,start_date,keys_required)
        VALUES(@ballColorId,@startDate,@keyCount)
    END

    -- return the ID
    IF @gameId !=null
        SET @ID = @gameId
    ELSE
        SET @ID = (SELECT SCOPE_IDENTITY())
END;



-- =============================================================
-- Author:        TCSDEVELOPER
-- Version:     1.0
-- Create date: 10-29-2006
-- Method:      GamePersistence.SQLServerGameDataDAO.createGame
-- Description:    It will insert a column into 'hosting_block' table    
-- ==============================================================

CREATE PROCEDURE CreateHostingBlock
     @gameId bigint
    ,@sequenceNumber int
    ,@slotMaxHostingTime int
    ,@ID bigint OUTPUT
AS
BEGIN
    SET NOCOUNT ON
    BEGIN
        INSERT INTO hosting_block(game_id,sequence_number,max_time_per_slot)
        VALUES(@gameId,@sequenceNumber,@slotMaxHostingTime)
    END
    
    SET @ID = (SELECT SCOPE_IDENTITY())
END;



-- =============================================================
-- Author:        TCSDEVELOPER
-- Version:     1.0
-- Create date: 10-29-2006
-- Method:      GamePersistence.SQLServerGameDataDAO.createDomain
-- Description:    It will insert a record into 'image' table    
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
    SET NOCOUNT ON

    IF @imageId !=  null
    BEGIN
        INSERT INTO image(id,domain_id,download_obj_id,is_approved,description)
        VALUES(@imageId,@domainId,@downloadId,@isApproved,@description)
    END
    ELSE
    BEGIN
        INSERT INTO image(domain_id,download_obj_id,is_approved,description)
        VALUES(@domainId,@downloadId,@isApproved,@description)
    END

    --return the id
    IF @imageId != null
        SET @ID = @imageId
    ELSE
        SET @ID = (SELECT SCOPE_IDENTITY())
END;



-- =============================================================
-- Author:        TCSDEVELOPER
-- Version:     1.0
-- Create date: 10-29-2006
-- Method:      GamePersistence.SQLServerGameDataDAO.recordBinaryObject
-- Description:    It will insert a record into 'download_obj' table    
-- ==============================================================

CREATE PROCEDURE RecordBinaryObject
     @name varchar(255)
    ,@mediaType varchar(255)
    ,@content image
    ,@ID bigint OUTPUT
AS
BEGIN
    SET NOCOUNT ON
    BEGIN
        INSERT INTO download_obj(media_type,suggested_name,content)
        VALUES(@mediaType,@name,@content)
    END

    SET @ID = (SELECT SCOPE_IDENTITY())
END;
