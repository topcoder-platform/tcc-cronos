CREATE TABLE image (
  image_id              SERIAL8,
  file_name             VARCHAR(50) NOT NULL
);

CREATE TABLE member_image (
  member_image_id       SERIAL8,
  member_id             INT8 NOT NULL,
  image_id              INT8 NOT NULL,
  removed               BOOLEAN NOT NULL,
  created_by            VARCHAR(50) NOT NULL,
  created_date          DATETIME YEAR TO FRACTION NOT NULL,
  updated_by            VARCHAR(50) NOT NULL,
  updated_date          DATETIME YEAR TO FRACTION NOT NULL
);

ALTER TABLE image ADD CONSTRAINT ( PRIMARY KEY (image_id) CONSTRAINT PK_image );
ALTER TABLE member_image ADD CONSTRAINT ( PRIMARY KEY (member_image_id) CONSTRAINT PK_member_image );
ALTER TABLE member_image ADD CONSTRAINT ( FOREIGN KEY(image_id) REFERENCES image(image_id) CONSTRAINT FK_member_image_1 );