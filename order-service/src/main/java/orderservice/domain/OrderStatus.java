package orderservice.domain;

public enum OrderStatus {
    CREATED,      // Заказ создан
    CONFIRMED,    // Заказ подтверждён
    COMPLETED,    // Заказ завершён
    CANCELLED     // Заказ отменён
}
