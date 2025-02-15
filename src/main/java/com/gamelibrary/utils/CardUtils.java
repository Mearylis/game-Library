package com.gamelibrary.utils;
import com.gamelibrary.validators.CardValidator;
import com.gamelibrary.strategies.PaymentStrategy;
import com.gamelibrary.strategies.VisaPaymentStrategy;
import com.gamelibrary.strategies.MastercardPaymentStrategy;
import com.gamelibrary.strategies.OtherPaymentStrategy;
import com.gamelibrary.enums.CardType;

public class CardUtils {
    public static CardType determineCardType(String cardNumber) {
        if (cardNumber.startsWith("4")) {
            return CardType.VISA;
        } else if (cardNumber.startsWith("5")) {
            return CardType.MASTERCARD;
        } else {
            return CardType.OTHER;
        }
    }
    public static double applyCommission(double amount) {
        // Пример: комиссия 2% от суммы
        double commissionRate = 0.02;
        return amount * (1 - commissionRate);
    }
    public static PaymentStrategy getPaymentStrategy(CardType cardType) {
        switch (cardType) {
            case VISA:
                return new VisaPaymentStrategy();
            case MASTERCARD:
                return new MastercardPaymentStrategy();
            default:
                return new OtherPaymentStrategy();
        }
    }
}