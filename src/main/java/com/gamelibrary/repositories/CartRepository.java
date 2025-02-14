package com.gamelibrary.repositories;

import java.util.ArrayList;
import java.util.List;

public class CartRepository {
    private List<Cart> carts = new ArrayList<>();

    public void addToCart(Cart cart) {
        carts.add(cart);
    }

    public List<Cart> getCartByUserId(int userId) {
        return carts.stream().filter(c -> c.getUserId() == userId).toList();
    }

    public void removeFromCart(int userId, int gameId) {
        carts.removeIf(c -> c.getUserId() == userId && c.getGameId() == gameId);
    }
}