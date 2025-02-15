package com.gamelibrary.strategies;

public class VisaPaymentStrategy implements PaymentStrategy {
    @Override
    public double applyCommission(double amount) {
        return amount * 1.02; // 2% комиссия
    }
}