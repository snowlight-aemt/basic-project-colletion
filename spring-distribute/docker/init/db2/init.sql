CREATE DATABASE IF NOT EXISTS `friend`;
CREATE TABLE IF NOT EXISTS `friend` (
    id BIGINT PRIMARY KEY ,
    name VARCHAR(100)
);

CREATE USER user2@'%' IDENTIFIED BY 'user2';

GRANT ALL PRIVILEGES ON *.* TO 'user2'@'%';

FLUSH PRIVILEGES;
