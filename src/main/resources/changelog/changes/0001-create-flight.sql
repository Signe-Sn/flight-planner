--liquibase formatted sql

--changeset signe:1

CREATE TABLE airport
(
    country varchar(255)             NOT NULL,
    city    varchar(255)             NOT NULL,
    airport varchar(255) PRIMARY KEY NOT NULL
)

--liquibase formatted sql

--changeset signe:2

CREATE TABLE flight
(
    id             SERIAL PRIMARY KEY,
    airport_from   varchar(255) NOT NULL,
    airport_to     varchar(255) NOT NULL,
    carrier        varchar(255) NOT NULL,
    departure_time TIMESTAMP    NOT NULL,
    arrival_time   TIMESTAMP    NOT NULL,
    FOREIGN KEY (airport_from) REFERENCES airport (airport),
    FOREIGN KEY (airport_to) REFERENCES airport (airport)
)
