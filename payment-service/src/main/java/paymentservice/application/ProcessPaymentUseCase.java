package paymentservice.application;

import paymentservice.domain.*;

import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProcessPaymentUseCase {
    private static final Logger logger = LoggerFactory.getLogger(ProcessPaymentUseCase.class);

    public Transaction execute(String orderId, double amount) {
        // Генерация уникального идентификатора транзакции
        String transactionId = UUID.randomUUID().toString();
        logger.info("Генерация уникального идентификатора транзакции");

        // Имитация логики платежа
        TransactionStatus status = (amount > 0) ? TransactionStatus.SUCCESS : TransactionStatus.FAILED;
        logger.info("Имитация логики платежа");

        // Создание транзакции
        return new Transaction(transactionId, orderId, amount, status);
    }
}
