create table website (id int AUTO_INCREMENT, website VARCHAR(200), PRIMARY KEY (id), UNIQUE KEY (website));
create table website_views(id int AUTO_INCREMENT, website_id int NOT NULL, count int  NOT NULL, record_date date  NOT NULL, PRIMARY KEY (id), UNIQUE KEY (website_id, record_date), FOREIGN KEY (website_id) REFERENCES website(id));
create table website_exclusions(id int AUTO_INCREMENT, host VARCHAR(200) NOT NULL, start_date date NOT NULL, end_date date, PRIMARY KEY (id), UNIQUE KEY (host));
create table users(id int AUTO_INCREMENT, user_name VARCHAR(200)  NOT NULL, user_password BINARY(32)  NOT NULL, PRIMARY KEY (id), UNIQUE KEY (user_name));


insert into users values (1,'mayank',UNHEX(SHA2('mayank_manulife',256)));