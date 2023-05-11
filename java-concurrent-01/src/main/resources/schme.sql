DROP TABLE IF EXISTS `teams`;
CREATE TABLE teams (
                       id bigint AUTO_INCREMENT,
                       team_token varchar(255) UNIQUE,
                       name varchar(255),
                       PRIMARY KEY (id)
);