create table categories (
    id              bigserial primary key,
    title           varchar(255) unique,
    created_at      timestamp default current_timestamp,
    updated_at      timestamp default current_timestamp
);

insert into categories (title) values
('Food'),
('Others');


create table products (
    id              bigserial primary key,
    title           varchar(255) not null ,
    category_id     bigint references categories(id),
    price           numeric(8,2),
    created_at      timestamp default current_timestamp,
    updated_at      timestamp default current_timestamp
);

insert into products (title, price, category_id) values
('Bread', 32, 1),
('Banana', 55, 1),
('Milk', 120, 1),
('Butter', 320, 1),
('Water', 500, 1),
('Apple', 750, 1),
('Cheese', 900, 1);


create table orders
(
    id              bigserial primary key,
    username        varchar(255) not null,
    total_price     numeric(8,2) not null,
    address         varchar(255),
    phone           varchar(255),
    created_at      timestamp default current_timestamp,
    updated_at      timestamp default current_timestamp
);


create table order_items
(
    id                  bigserial primary key,
    product_id          bigint not null references products(id),
    order_id            bigint not null references orders(id),
    quantity            int not null,
    price_per_product   numeric(8,2) not null,
    price               numeric(8,2) not null,
    created_at          timestamp default current_timestamp,
    updated_at          timestamp default current_timestamp
);


create table users
(
    id         bigserial primary key,
    username   varchar(36) not null,
    password   varchar(80) not null,
    email      varchar(50) unique,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

insert into users (username, password, email)
values ('Bob', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'bob_johnson@gmail.com'),
       ('John', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'john_johnson@gmail.com');


create table roles
(
    id         bigserial primary key,
    name       varchar(50) not null,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

insert into roles (name)
values ('ROLE_USER'),
       ('ROLE_ADMIN');


create table users_roles
(
    user_id    bigint not null references users (id),
    role_id    bigint not null references roles (id),
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp,
    primary key (user_id, role_id)
);

insert into users_roles (user_id, role_id)
values (1, 1),
       (2, 2);