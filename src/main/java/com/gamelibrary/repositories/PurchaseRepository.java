package com.gamelibrary.repositories;

import com.gamelibrary.models.Purchase;

import java.util.ArrayList;
import java.util.List;

public class PurchaseRepository {
    private List<Purchase> purchases = new ArrayList<>();

    public void addPurchase(Purchase purchase) {
        purchases.add(purchase);
    }

    public List<Purchase> getAllPurchases() {
        return purchases;
    }

    public List<Purchase> getPurchasesByUserId(int userId) {
        return purchases.stream().filter(p -> p.getUserId() == userId).toList();
    }
}