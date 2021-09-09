create table password_black_list
(
    id        serial primary key,
    password  varchar(30)
);

insert into password_black_list(password)
values ('qwerty007');
insert into password_black_list(password)
values ('qwerty123');
