INSERT INTO invoices (id, number_invoice, description, customer_id, create_at, state) VALUES(1, '0001', 'invoice office items', 1, NOW(),'CREATED');

INSERT INTO invoce_items (product_id, quantity, price ) VALUES(1 , 1, 178.89);
INSERT INTO invoce_items (product_id, quantity, price)  VALUES(2 , 2, 12.5);
INSERT INTO invoce_items (product_id, quantity, price)  VALUES(3 , 1, 40.06);