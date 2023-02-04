create table history
(
    id              integer primary key,
    id_user         integer,
    operation       varchar,
    date            date,
    status          varchar,
    summa_operation numeric
);

insert into history (id, id_user, operation, date, status, summa_operation)
VALUES (1, 2, 'getBalance', '2022-01-01',
        'off', 20);

create table balance
(
    id           integer primary key,
    id_user      integer,
    user_balance numeric
);

insert into balance (id, id_user, user_balance)
VALUES (1, 234, 155);

insert into balance (id, id_user, user_balance)
VALUES (2, 454, 200);

insert into balance (id, id_user, user_balance)
VALUES (3, 5, 100);

insert into balance (id, id_user, user_balance)
VALUES (4, 15, 150);

insert into history (id, id_user, operation, date, status, summa_operation)
VALUES (2, 12, 'get balance', '2023-01-06', 'done', 0);

insert into history (id, id_user, operation, date, status, summa_operation)
VALUES (3, 12, 'takeMoney', '2023-01-06', 'not done', 50);