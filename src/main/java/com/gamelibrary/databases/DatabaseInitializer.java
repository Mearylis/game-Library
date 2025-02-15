package com.gamelibrary.databases;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {
    public static void initialize(Connection connection) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            DatabaseMetaData dbMetaData = connection.getMetaData();

            ResultSet tables = dbMetaData.getTables(null, null, "users", null);
            if (!tables.next()) {
                stmt.execute("CREATE TABLE users (" +
                        "id SERIAL PRIMARY KEY, " +
                        "username VARCHAR(50) NOT NULL, " +
                        "password VARCHAR(50) NOT NULL, " +
                        "role VARCHAR(20) NOT NULL, " +
                        "balance DOUBLE PRECISION DEFAULT 0.0, " +
                        "age INT NOT NULL)");
            } else {
                ResultSet columns = dbMetaData.getColumns(null, null, "users", "age");
                if (!columns.next()) {
                    stmt.execute("ALTER TABLE users ADD COLUMN age INT NOT NULL DEFAULT 18");
                }
            }

            // Создание таблицы games, если она не существует
            stmt.execute("CREATE TABLE IF NOT EXISTS games (" +
                    "id SERIAL PRIMARY KEY, " +
                    "name VARCHAR(100) NOT NULL, " +
                    "developer_id INT NOT NULL, " +
                    "price DOUBLE PRECISION NOT NULL, " +
                    "size_gb DOUBLE PRECISION NOT NULL, " +
                    "age_restriction INT NOT NULL, " +
                    "genre VARCHAR(50), " +
                    "description TEXT, " +
                    "approved BOOLEAN DEFAULT FALSE, " +
                    "FOREIGN KEY (developer_id) REFERENCES users(id))");

            // Создание таблицы reviews, если она не существует
            stmt.execute("CREATE TABLE IF NOT EXISTS reviews (" +
                    "id SERIAL PRIMARY KEY, " +
                    "game_id INT NOT NULL, " +
                    "user_id INT NOT NULL, " +
                    "review TEXT, " +
                    "rating INT, " +
                    "timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                    "FOREIGN KEY (game_id) REFERENCES games(id), " +
                    "FOREIGN KEY (user_id) REFERENCES users(id))");

            // Проверка и добавление столбца timestamp, если он отсутствует
            ResultSet reviewColumns = dbMetaData.getColumns(null, null, "reviews", "timestamp");
            if (!reviewColumns.next()) {
                stmt.execute("ALTER TABLE reviews ADD COLUMN timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP");
            }

            // Создание таблицы cart, если она не существует
            stmt.execute("CREATE TABLE IF NOT EXISTS cart (" +
                    "user_id INT NOT NULL, " +
                    "game_id INT NOT NULL, " +
                    "FOREIGN KEY (user_id) REFERENCES users(id), " +
                    "FOREIGN KEY (game_id) REFERENCES games(id), " +
                    "PRIMARY KEY (user_id, game_id))");

            // Создание таблицы orders, если она не существует
            stmt.execute("CREATE TABLE IF NOT EXISTS orders (" +
                    "id SERIAL PRIMARY KEY, " +
                    "user_id INT NOT NULL, " +
                    "order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                    "FOREIGN KEY (user_id) REFERENCES users(id))");

            // Создание таблицы order_items, если она не существует
            stmt.execute("CREATE TABLE IF NOT EXISTS order_items (" +
                    "id SERIAL PRIMARY KEY, " +
                    "order_id INT NOT NULL, " +
                    "game_id INT NOT NULL, " +
                    "quantity INT NOT NULL, " +
                    "FOREIGN KEY (order_id) REFERENCES orders(id), " +
                    "FOREIGN KEY (game_id) REFERENCES games(id))");

            // Создание таблицы messages, если она не существует
            stmt.execute("CREATE TABLE IF NOT EXISTS messages (" +
                    "id SERIAL PRIMARY KEY, " +
                    "sender_id INT NOT NULL, " +
                    "game_id INT NOT NULL, " +
                    "text TEXT, " +
                    "timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                    "FOREIGN KEY (sender_id) REFERENCES users(id), " +
                    "FOREIGN KEY (game_id) REFERENCES games(id))");

            // Создание таблицы friends, если она не существует
            stmt.execute("CREATE TABLE IF NOT EXISTS friends (" +
                    "id SERIAL PRIMARY KEY, " +
                    "sender_id INT NOT NULL, " +
                    "receiver_id INT NOT NULL, " +
                    "status VARCHAR(20) DEFAULT 'pending', " +
                    "FOREIGN KEY (sender_id) REFERENCES users(id) ON DELETE CASCADE, " +
                    "FOREIGN KEY (receiver_id) REFERENCES users(id) ON DELETE CASCADE)");
        }
    }
}