-- liquibase formatted sql

-- changeset i:createTable-qrtz_triggers
create table qrtz_triggers
(
    sched_name     VARCHAR(50) not null,
    trigger_name   VARCHAR(50) not null,
    trigger_group  VARCHAR(200) not null,
    job_name       VARCHAR(50) NOT NULL,
    job_group      VARCHAR(50) NOT NULL,
    description    VARCHAR(50) NULL,
    priority       INTEGER,
    trigger_state  VARCHAR(16)  not null,
    trigger_type   VARCHAR(8)   not null,
    start_time     BIGINT       not null,
    end_time       BIGINT,
    calendar_name  VARCHAR(200),
    job_data       BYTEA,
    primary key (sched_name, trigger_name, trigger_group),
    foreign key (sched_name, job_name, job_group) references qrtz_job_details (sched_name, job_name, job_group)
);
-- rollback drop table qrtz_triggers;