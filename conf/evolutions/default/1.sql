# Users table
 
# --- !Ups
 
CREATE TABLE Users (
    id bigint(20) NOT NULL AUTO_INCREMENT,
    email varchar(255) NOT NULL,
    password varchar(255) not null,
    firstName varchar(255) NOT NULL,
    lastName varchar(255) NOT NULL,
    description varchar(255) NOT NULL,
    role varchar(255) not null default 'OPERATOR',
    PRIMARY KEY (id)
);

insert into Users(email, password, firstName, lastName, description,role) values ('a@a.a','1','Test','Tester','Bogotac','GOD');

CREATE TABLE Saveti (
    id bigint(20) NOT NULL AUTO_INCREMENT,
    name varchar(255) NOT NULL,
    address varchar(255) not null,
    created_at DATETIME NOT NULL default CURRENT_TIMESTAMP(),
    PRIMARY KEY (id)
);

insert into Saveti(name, address) values ('Savet 1','Testni savet br.1 na nekoj adresi');
insert into Saveti(name, address) values ('Savet 2','Testni savet br.2 na nekoj drugoj adresi');
insert into Saveti(name, address) values ('Savet 3','Testni savet br.3 opet na nekoj adresi');

# --- !Downs
 
DROP TABLE Users;