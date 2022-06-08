create table if not exists ingredient (
    id varchar(4) not null,
    name varchar(25) not null,
    type varchar(10) not null,
    primary key (id)
);

create table if not exists taco (
    id bigint(20) not null auto_increment,
    name varchar(50) not null comment 'taco名',
    created_at timestamp not null comment '创建时间',
    primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf6mb4;

create table if not exists taco_ingredients (
    taco bigint not null,
    ingredient varchar(4) not null
);

create table if not exists taco_order (
    id bigint(20) not null auto_increment,
    orderName varchar(50) not null,
    street varchar(50) not null,
    city varchar(50) not null,
    state varchar(2) not null,
    zip varchar(10) not null,
    ccNumber varchar(16) not null,
    ccExpiration varchar(5) not null,
    ccCVV varchar(3) not null,
    placedAt timestamp not null,
    primary key(id)
);

create table if not exists taco_order_tacos (
    tacoOrder bigint not null,
    taco bigint not null
);

create table if not exists user (
    id bigint(20) not null auto_increment,
    user_name varchar(20) default null comment '用户名',
    password varchar(200) not null comment '密码',
    phone_number varchar(50) default null comment '手机号码',
    registration_time timestamp not null,
    primary key(id)
) engine = INnoDB;

alter table taco_order add column userId bigint default null comment '用户ID';