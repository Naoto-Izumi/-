create table user_table(
	user_id varchar2(20) 	CONSTRAINT pk_user_id PRIMARY KEY
							CHECK(user_id >= 5),
	user_pass varchar2(20)	NOT NULL
							CHECK(user_pass >= 5)
)

create table category_table(
	category_id number(2)	CONSTRAINT pk_category_id PRIMARY KEY,
	category_name varchar2(40)	CONSTRAINT uk_category_name UNIQUE
								NOT NULL
)