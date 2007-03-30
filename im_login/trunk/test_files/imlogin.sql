create table category (
	category_id INTEGER NOT NULL,
	name VARCHAR(64) NOT NULL,
	description VARCHAR(255) NOT NULL,
	chattable_flag VARCHAR(1) NOT NULL,
	create_date DATETIME YEAR TO FRACTION(3) NOT NULL,
	create_user VARCHAR(64) NOT NULL,
	modify_date DATETIME YEAR TO FRACTION(3) NOT NULL,
	modify_user VARCHAR(64) NOT NULL,
	PRIMARY KEY(category_id)
);

insert into category values (5, "chattable", "junit", "Y", today, "test", today, "test");