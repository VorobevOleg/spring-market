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
    title           varchar(255),
    category_id     bigint references categories(id),
    price           numeric(8,2),
    created_at      timestamp default current_timestamp,
    updated_at      timestamp default current_timestamp
);

insert into products (title, price, category_id) values
('Bread', 32, 1),
('Milk', 120, 1),
('Butter', 320, 1),
('Cheese', 500, 1);


create table users (
    id              bigserial primary key,
    username        varchar(36) not null,
    password        varchar(80) not null,
    created_at      timestamp default current_timestamp,
    updated_at      timestamp default current_timestamp
);

insert into users(username, password) values
('Bob', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i'),
('John', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i');


create table roles (
    id              bigserial primary key,
    name            varchar(50) not null
);

insert into roles(name) values
('ROLE_USER'),
('ROLE_ADMIN');


create table users_roles (
    user_id         bigint not null references users(id),
    role_id         bigint not null  references roles(id),
    primary key     (user_id, role_id)
);

insert into users_roles(user_id, role_id) values
(1, 1),
(2, 2);


create table orders
(
    id              bigserial primary key,
    user_id         bigint not null references users(id),
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