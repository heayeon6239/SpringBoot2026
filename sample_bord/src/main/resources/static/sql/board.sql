create database board;
use board;

create table board(
	id int auto_increment primary key,
    title varchar(50) not null,
    content varchar(500) not null,
    writer varchar(10) not null,
    createAt datetime default now()
);