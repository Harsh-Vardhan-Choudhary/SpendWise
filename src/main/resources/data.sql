-- Inserting data into the appuser table
INSERT INTO appuser (id, name, email) VALUES 
    (1, 'Alice', 'alice@example.com'), 
    (2, 'Bob', 'bob@example.com'), 
    (3, 'Charlie', 'charlie@example.com');

-- Inserting data into the category table
INSERT INTO category (id, name) VALUES 
    (1, 'Food'), 
    (2, 'Transport'), 
    (3, 'Utilities');

-- Inserting data into the expense table
INSERT INTO expense (id, amount, description, expense_date, is_shared, category_id, user_id) VALUES 
    (1, 50.00, 'Lunch', '2024-07-24 12:00:00', TRUE, 1, 1),
    (2, 20.00, 'Bus Ticket', '2024-07-24 14:00:00', FALSE, 2, 2),
    (3, 100.00, 'Electricity Bill', '2024-07-24 16:00:00', TRUE, 3, 3);

-- Inserting data into the expense_user table
INSERT INTO expense_user (expense_id, amount, user_id) VALUES 
    (1, 20.00, 1),
    (1, 15.00, 2),
    (1, 15.00, 3),
    (2, 20.00, 2),
    (3, 30.00, 3),
    (3, 70.00, 1);
