
CREATE TABLE item (
    id bigint primary key auto_increment,
    name nvarchar(100) not null ,
    type nvarchar(50) not null ,
    manufacturer nvarchar(100) not null ,
    availability boolean not null ,
    price double not null
)