create table person
(
    id         bigserial
        primary key,
    address    varchar(50) not null,
    first_name varchar(40) not null,
    gender     varchar(10) not null,
    last_name  varchar(40) not null
);

alter table public.person
    owner to root;
