package com.gamelibrary.strategies;

public class OtherPaymentStrategy implements PaymentStrategy {
    @Override
    public double applyCommission(double amount) {
        return amount * 1.03; // 3% комиссия
    }
}