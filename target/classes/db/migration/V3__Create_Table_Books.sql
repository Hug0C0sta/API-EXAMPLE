create table books
(
    id         bigserial
        primary key,
    author    varchar(100) not null,
    price float not null,
    title    varchar(100) not null,
    launch_date date not null
);

alter table public.books
    owner to root;
