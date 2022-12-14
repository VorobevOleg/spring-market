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