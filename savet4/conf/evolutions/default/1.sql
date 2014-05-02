# Users table
 
# --- !Ups
 
CREATE TABLE Users (
    id bigint(20) NOT NULL AUTO_INCREMENT,
    email varchar(255) NOT NULL,
    password varchar(255) not null,
    description varchar(255) NOT NULL,
    role varchar(255) not null default 'OPERATOR',
    PRIMARY KEY (id)
);

insert into Users(email, password, description,role) values ('a@a.a','1','Bogotac','GOD');

# --- !Downs
 
DROP TABLE Users;