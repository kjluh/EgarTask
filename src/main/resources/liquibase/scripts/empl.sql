-- liquibase formatted sql

--changeSet Anatoly:1
CREATE TABLE employee
(
    id          bigint generated by default as identity primary key,
    name        text,
    family      text,
    second_name text,
    hiring_date date,
    dismissed   BOOLEAN default false
);

--changeSet Anatoly:2
CREATE TABLE work_time
(
    id          bigint generated by default as identity primary key,
    come_time   time,
    out_time    time,
    now         date,
    employee_id bigint
);

--changeSet Anatoly:3
insert into employee (name, family, second_name, dismissed)
values ('ivan','ivanov','ivanovish', 'tru');
insert into employee (name, family, second_name, dismissed)
values ('petr','petrovich','petrov', 'tru');