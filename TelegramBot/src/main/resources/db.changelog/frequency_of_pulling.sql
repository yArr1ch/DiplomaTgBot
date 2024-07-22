-- liquibase formatted sql

-- changeset i:createTable-frequency_of_pulling
CREATE TABLE frequency_of_pulling
(
    id      SERIAL PRIMARY KEY,
    user_id VARCHAR not null,
    frequency VARCHAR not null,
    category VARCHAR not null,
    chat_id VARCHAR not null
);
-- rollback drop table frequency_of_pulling;