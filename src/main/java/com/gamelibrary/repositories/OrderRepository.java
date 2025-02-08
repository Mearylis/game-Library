package com.gamelibrary.repositories;

import com.gamelibrary.models.Order;
import com.gamelibrary.models.OrderItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderRepository {
    private static final String URL = "jdbc:postgresql://localhost:5432/gamelibrary";
    private static final String USER = "postgres";
    private static final String PASSWORD = "1234";

    public void addOrder(Order order) {
        String orderQuery = "INSERT INTO orders (user_id, total_price) VALUES (?, ?)";
        String orderItemQuery = "INSERT INTO order_items (order_id, game_id, game_name, game_price) VALUES (?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            try (PreparedStatement orderStatement = connection.prepareStatement(orderQuery, Statement.RETURN_GENERATED_KEYS)) {
                orderStatement.setInt(1, order.getUserId());
                orderStatement.setDouble(2, order.getTotalPrice());
                orderStatement.executeUpdate();

                try (ResultSet generatedKeys = orderStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int orderId = generatedKeys.getInt(1);
                        try (PreparedStatement orderItemStatement = connection.prepareStatement(orderItemQuery)) {
                            for (OrderItem item : order.getOrderItems()) {
                                orderItemStatement.setInt(1, orderId);
                                orderItemStatement.setInt(2, item.getGameId());
                                orderItemStatement.setString(3, item.getGameName());
                                orderItemStatement.setDouble(4, item.getGamePrice());
                                orderItemStatement.executeUpdate();
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Optional<Order> getOrderById(int orderId) {
        String orderQuery = "SELECT * FROM orders WHERE order_id = ?";
        String orderItemQuery = "SELECT * FROM order_items WHERE order_id = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement orderStatement = connection.prepareStatement(orderQuery);
             PreparedStatement orderItemStatement = connection.prepareStatement(orderItemQuery)) {
            orderStatement.setInt(1, orderId);
            try (ResultSet orderResultSet = orderStatement.executeQuery()) {
                if (orderResultSet.next()) {
                    int userId = orderResultSet.getInt("user_id");
                    double totalPrice = orderResultSet.getDouble("total_price");

                    List<OrderItem> orderItems = new ArrayList<>();
                    orderItemStatement.setInt(1, orderId);
                    try (ResultSet orderItemResultSet = orderItemStatement.executeQuery()) {
                        while (orderItemResultSet.next()) {
                            int gameId = orderItemResultSet.getInt("game_id");
                            String gameName = orderItemResultSet.getString("game_name");
                            double gamePrice = orderItemResultSet.getDouble("game_price");
                            orderItems.add(new OrderItem(gameId, gameName, gamePrice));
                        }
                    }
                    return Optional.of(new Order(orderId, userId, orderItems, totalPrice));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}