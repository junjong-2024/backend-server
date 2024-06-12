show databases;
create database debait;
use debait;

CREATE TABLE users (
                       id varchar(36) PRIMARY KEY,
                       name VARCHAR(10) NOT NULL,
                       login_id VARCHAR(50) NOT NULL,
                       password VARCHAR(50) NOT NULL,
                       user_email VARCHAR(50) NULL,
                       payment_at DATE NULL,
                       payment_expires_at DATE NULL,
                       usage_storage INT NULL,
                       max_storage INT NULL
);

select * from users;

create table rule(
                     id varchar(36) primary key,
                     rule_name varchar(50) NULL,
                     spec text NULL,
                     user_id varchar(36) not null,
                     foreign key (user_id) references users(id)
);

select * from rule;

create table discussion_room(
                                id varchar(36) primary key,
                                name varchar(50) NULL,
                                created_at DATE NULL,
                                user_id VARCHAR(36) NOT NULL,
                                video_src VARCHAR(255) NULL,
                                thumbnail_src varchar(255) NULL,
                                script TEXT NULL,
                                rule_id VARCHAR(36) NOT NULL,
                                foreign key (user_id) references users(id),
                                foreign key (rule_id) references rule(id)
);

alter table discussion_room add column description VARCHAR(300) after name;
/* description 컬럼 추가*/

select * from discussion_room;