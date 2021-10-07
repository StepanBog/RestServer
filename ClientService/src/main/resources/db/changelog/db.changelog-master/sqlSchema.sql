CREATE TABLE client (
                        id bigint not null primary key ,
                        name varchar(255) not null ,
                        email varchar(255) unique not null ,
                        address varchar(255),
                        phone varchar(255) not null,
                       birth_date datetime(6) not null ,
                        surname varchar(255) not null ,
                        patronymic varchar(255)
);

