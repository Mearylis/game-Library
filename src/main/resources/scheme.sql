-- Drop existing tables if they exist
DROP TABLE IF EXISTS order_items CASCADE;
DROP TABLE IF EXISTS orders CASCADE;
DROP TABLE IF EXISTS games CASCADE;
DROP TABLE IF EXISTS users CASCADE;

-- Create the users table
CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       username VARCHAR(255) NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       role VARCHAR(50) NOT NULL,
                       balance DOUBLE PRECISION NOT NULL DEFAULT 0.0,
                       banned BOOLEAN NOT NULL
);

-- Create the games table
CREATE TABLE games (
                       id SERIAL PRIMARY KEY,
                       name VARCHAR(255) NOT NULL,
                       price DOUBLE PRECISION NOT NULL,
                       approved BOOLEAN NOT NULL,
                       developer_id INT NOT NULL,
                       category VARCHAR(255) NOT NULL,
                       FOREIGN KEY (developer_id) REFERENCES users(id)
);

-- Create the orders table
CREATE TABLE orders (
                        order_id SERIAL PRIMARY KEY,
                        user_id INT NOT NULL,
                        total_price DOUBLE PRECISION NOT NULL,
                        FOREIGN KEY (user_id) REFERENCES users(id)
);

