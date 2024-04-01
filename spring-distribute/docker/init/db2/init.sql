CREATE DATABASE IF NOT EXISTS `friend`;

CREATE USER user2@'%' IDENTIFIED BY 'user2';

GRANT ALL PRIVILEGES ON *.* TO 'user2'@'%';

FLUSH PRIVILEGES;