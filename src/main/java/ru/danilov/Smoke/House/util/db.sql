create table Cigarettes
(
    id              int primary key generated by default as identity,
    name            varchar(100)              not null,
    price           int check ( price > 0 ) check ( price < 1000 ),
    description     varchar(200)              not null,
    date_of_issue   date default current_date not null,
    shelf_life_year int check ( shelf_life_year > 1 ),
    additives       varchar(200),
    strength        varchar                   not null,
    user_id         int                       not null references Users (id) on DELETE SET NULL
);
create table Liquid
(
    id               int primary key generated by default as identity,
    name             varchar(100)              not null,
    price            int check ( price > 0 ) check ( price < 5000 ),
    description      varchar(200)              not null,
    date_of_issue    date default current_date not null,
    shelf_life_year  int check ( shelf_life_year > 2015 ),
    volume           int check ( volume > 200 ) check ( volume < 1000 ),
    strength         varchar                   not null,
    nicotine_content int,
    taste            varchar(100)
);
create table Disposable
(
    id               int primary key generated by default as identity,
    name             varchar(100) not null,
    price            int check ( price > 0 ) check ( price < 1000 ),
    description      varchar(200) not null,
    date_of_issue    date         not null,
    shelf_life_year  int check ( shelf_life_year > 2015 ),
    color            varchar(50)  not null,
    strength         varchar      not null,
    nicotine_content int,
    taste            varchar(100),
    number_of_rods   int check ( number_of_rods > 1 ) check ( number_of_rods < 10 )
);
create table Vapes
(
    id              int primary key generated by default as identity,
    name            varchar(100) not null,
    price           int check ( price > 0 ) check ( price < 1000 ),
    description     varchar(200) not null,
    date_of_issue   date         not null,
    shelf_life_year int check ( shelf_life_year > 2015 ),
    color           varchar(50)  not null,
    power           int check ( 100 ),
    tank_size       int check ( tank_size > 200 ) check ( tank_size < 500 )
);


insert into Cigarettes(name, price, description, date_of_issue, shelf_life_year, additives, strength, user_id)
VALUES ('Parlament', 239, 'Ультратонкие сигареты с насыщенным вкусом и приятным ароматом',
        '2016-12-17', 2, 'Ментол', 'LIGHT', 1);
insert into Cigarettes(name, price, description, date_of_issue, shelf_life_year, additives, strength, user_id)
VALUES ('Winston', 185, 'Крепкие сигареты из лучших сортов табака Гватемалы',
        '2023-01-31', 3, 'Без добавок', 'STRONG', 1);
insert into Cigarettes(name, price, description, date_of_issue, shelf_life_year, additives, strength, user_id)
VALUES ('Русский стиль', 201, 'Средней крепости сигареты для мидлов',
        '2020-02-12', 2, 'Арабский табак', 'MIDDLE', 2);
insert into Cigarettes(name, price, description, date_of_issue, shelf_life_year, additives, strength, user_id)
VALUES ('Kent Brilliance', 179, 'Аромат кофе',
        '2022-09-23', 2, 'Кофе', 'LIGHT', 3);

create table Users
(
    id            int primary key GENERATED by default as identity,
    name          varchar(100) unique       not null,
    date_of_birth date default current_date not null
);

insert into Users(name, date_of_birth)
VALUES ('Хиват Джагуфарович', '1954-04-06');
insert into Users(name, date_of_birth)
VALUES ('Рамзес Александрович', '2000-09-13');
insert into Users(name, date_of_birth)
VALUES ('Андрей Константинович', '1985-03-14');

set datestyle to European;
show datestyle;

select *
from Cigarettes;
select *
from Users;
drop table Cigarettes;
truncate table Cigarettes;

select U.name, C.name from Users U join Cigarettes C on U.id = C.user_id;