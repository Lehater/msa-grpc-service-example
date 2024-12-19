package paymentservice.domain;

public class Transaction {
    private final String transactionId;  // Уникальный идентификатор транзакции
    private final String orderId;        // ID связанного заказа
    private final double amount;         // Сумма транзакции
    private TransactionStatus status;    // Статус транзакции

    public Transaction(String transactionId, String orderId, double amount, TransactionStatus status) {
        this.transactionId = transactionId;
        this.orderId = orderId;
        this.amount = amount;
        this.status = status;
    }

    // Геттеры
    public String getTransactionId() {
        return transactionId;
    }

    public String getOrderId() {
        return orderId;
    }

    public double getAmount() {
        return amount;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    // Установка нового статуса транзакции
    public void setStatus(TransactionStatus status) {
        this.status = status;
    }
}
