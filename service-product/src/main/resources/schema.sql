DROP TABLE IF EXISTS categories;

CREATE TABLE categories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(250) NOT NULL
);

DROP TABLE IF EXISTS products;

CREATE TABLE products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(250) NOT NULL,
    description VARCHAR(250) NOT NULL,
    stock DOUBLE,
    price DOUBLE,
    status VARCHAR(250) NOT NULL,
    created_at TIMESTAMP,
    category_id BIGINT
);