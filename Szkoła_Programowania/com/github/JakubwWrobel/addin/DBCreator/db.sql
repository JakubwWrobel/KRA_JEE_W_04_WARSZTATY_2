CREATE DATABASE szkola_programowania
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE szkola_programowania;

CREATE TABLE user_group
(
    id int(11) PRIMARY KEY AUTO_INCREMENT NOT NULL ,
    name VARCHAR(255)
);

CREATE TABLE users
(
    id int(11) PRIMARY KEY AUTO_INCREMENT NOT NULL ,
    username VARCHAR(255),
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(245) NOT NULL ,
    user_group_id int,
    FOREIGN KEY (user_group_id) REFERENCES user_group(id)

);
CREATE TABLE exercise
(
    id int AUTO_INCREMENT PRIMARY KEY NOT NULL,
    title VARCHAR(255),
    description VARCHAR(255)
);

CREATE TABLE solution
(
    id int PRIMARY KEY auto_increment NOT NULL,
    created DATETIME DEFAULT NOW(),
    updated DATETIME,
    description TEXT,
    users_id INT,
    exercise_id INT,
    FOREIGN KEY (exercise_id) REFERENCES exercise(id),
    FOREIGN KEY (users_id) REFERENCES users(id)

);

ALTER TABLE users
ADD COLUMN is_admin boolean DEFAULT 0 not null;
