DROP TABLE IF EXISTS invoices;

CREATE TABLE invoices (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    number_invoice VARCHAR(250) NOT NULL,
    description VARCHAR(250) NOT NULL,
    customer_id BIGINT NOT NULL,
    create_at DATE NOT NULL,
    items VARCHAR(250),
    state VARCHAR(250)
);

DROP TABLE IF EXISTS invoce_items;

CREATE TABLE invoce_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    quantity DOUBLE NOT NULL,
    price DOUBLE NOT NULL,
    product_id BIGINT
);