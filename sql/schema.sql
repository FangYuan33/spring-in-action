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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

create table if not exists taco_ingredients (
    id bigint(20) not null auto_increment,
    taco bigint(20) not null,
    ingredient varchar(4) not null,
    primary key (id)
);

create index taco_ingredient_index on taco_ingredients (taco, ingredient);

alter table taco_ingredients add column created_at timestamp not null comment '创建时间';

create table if not exists taco_order (
    id bigint(20) not null auto_increment,
    order_name varchar(50) not null comment '订单名',
    address varchar(50) not null '配送地址',
    state tinyint(2) not null comment '状态',
    phone varchar(16) not null comment '手机号',
    created_at timestamp not null comment '创建时间',
    primary key(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf6mb4;

alter table taco_order add column userId bigint default null comment '用户ID';

create table if not exists taco_order_tacos (
    id bigint(20) not null auto_increment,
    taco_order bigint(20) not null,
    taco bigint(20) not null,
    primary key (id)
);

create index taco_order_index on taco_order_tacos (taco_order, taco);

alter table taco_order_tacos add column created_at timestamp not null comment '创建时间';

create table if not exists user (
    id bigint(20) not null auto_increment,
    user_name varchar(20) default null comment '用户名',
    password varchar(200) not null comment '密码',
    phone_number varchar(50) default null comment '手机号码',
    created_at timestamp not null comment '创建时间',
    primary key(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;