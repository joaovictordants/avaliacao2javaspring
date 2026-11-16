create table usuarios(

    id bigint not null autoincrement,
    login varchar(100) not null,
    senha varchar(255) not null,

    primary key(id)
);