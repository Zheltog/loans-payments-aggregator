drop table if exists loans;

create table loans(
    payments text not null,
    start_date date not null
);

comment on table loans is 'Информация о кредитах';
comment on column loans.payments is 'Строка платежей';
comment on column loans.start_date is 'Дата первого платежа';