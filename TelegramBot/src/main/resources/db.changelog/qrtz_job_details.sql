-- liquibase formatted sql

-- changeset i:createTable-qrtz_job_details
create table qrtz_job_details
(
    sched_name VARCHAR(50) NOT NULL,
    job_name VARCHAR(50) NOT NULL,
    job_group VARCHAR(50) NOT NULL,
    description VARCHAR(50) NULL,
    job_class_name VARCHAR(50) NOT NULL,
    is_durable VARCHAR(1) NOT NULL,
    is_nonconcurrent VARCHAR(1) NOT NULL,
    is_update_data VARCHAR(1) NOT NULL,
    request_recovery VARCHAR(1) NOT NULL,
    job_data bytea NULL,
    primary key (sched_name, job_name, job_group)
);
-- rollback drop table qrtz_job_details;