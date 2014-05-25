# Users table
 
# --- !Ups
 
CREATE TABLE Stanari (
    id bigint(20) NOT NULL AUTO_INCREMENT,
    SAVET_ID bigint(20) NOT NULL,
    broj_Stana varchar(255) not null,
    redosled int ,
    name varchar(255) NOT NULL,
    last_name varchar(255)
);

insert into Stanari(id, SAVET_ID, broj_Stana, redosled, name, last_name) values (1, 1, 'S1', 1, 'Icabod', 'Crane');
insert into Stanari(id, SAVET_ID, broj_Stana, redosled, name, last_name) values (2, 1, 'S2', 2, 'Icabod2', 'Crane2');
insert into Stanari(id, SAVET_ID, broj_Stana, redosled, name, last_name) values (3, 1, 'S3', 3, 'Icabod3', 'Crane3');

insert into Stanari(id, SAVET_ID, broj_Stana, redosled, name, last_name) values (11, 2, 'S1', 1, 'X-Icabod', 'Crane');
insert into Stanari(id, SAVET_ID, broj_Stana, redosled, name, last_name) values (12, 2, 'S2', 2, 'X-Icabod2', 'Crane2');
insert into Stanari(id, SAVET_ID, broj_Stana, redosled, name, last_name) values (13, 2, 'S3', 3, 'X-Icabod3', 'Crane3');


# --- !Downs
 
DROP TABLE Stanari;