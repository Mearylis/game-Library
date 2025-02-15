package com.gamelibrary.strategies;

public class MastercardPaymentStrategy implements PaymentStrategy {
    @Override
    public double applyCommission(double amount) {
        return amount * 1.015; // 1.5% комиссия
    }
}