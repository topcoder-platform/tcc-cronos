
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
;


-- ------------------------------------------------------------
-- A stored procedures that inserts a download object and
-- returns the auto-generated ID.
-- ------------------------------------------------------------

CREATE PROCEDURE InsertDownloadObject
  @media_type VARCHAR(255),
  @content varbinary(max),
  @id BIGINT OUTPUT
AS
  INSERT INTO download_obj(media_type, content) VALUES (@media_type, @content)
  SET @id = @@IDENTITY
;


