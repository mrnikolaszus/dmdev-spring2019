--liquibase formatted sql

--changeset nickz:1

ALTER TABLE users
ADD COLUMN image VARCHAR(64);

--changeset nickz:2

ALTER TABLE users_aud
ADD COLUMN image VARCHAR(64);




