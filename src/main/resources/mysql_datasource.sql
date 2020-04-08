create table USER(
	id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
	user_name varchar(50) NOT NULL,
	password varchar(50) NOT NULL,
	active boolean NOT NULL,
	roles  varchar(50) NOT NULL
);

INSERT INTO user(user_name, PASSWORD, active, roles) VALUES ('user', 'pass', TRUE, 'ROLE_USER');