package paymentservice.application;

import paymentservice.domain.*;

import java.util.UUID;

public class ProcessPaymentUseCase {
    public Transaction execute(String orderId, double amount) {
        // Генерация уникального идентификатора транзакции
        String transactionId = UUID.randomUUID().toString();

        // Имитация логики платежа
        TransactionStatus status = (amount > 0) ? TransactionStatus.SUCCESS : TransactionStatus.FAILED;

        // Создание транзакции
        return new Transaction(transactionId, orderId, amount, status);
    }
}
