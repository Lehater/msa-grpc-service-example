package orderservice.domain;

import java.util.List;

public class Order {
    private final String orderId;       // Уникальный идентификатор заказа
    private final String userId;        // Пользователь, создавший заказ
    private final List<OrderItem> items; // Список товаров в заказе
    private OrderStatus status;         // Текущий статус заказа

    public Order(String orderId, String userId, List<OrderItem> items, OrderStatus status) {
        this.orderId = orderId;
        this.userId = userId;
        this.items = items;
        this.status = status;
    }

    // Геттеры
    public String getOrderId() {
        return orderId;
    }

    public String getUserId() {
        return userId;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public OrderStatus getStatus() {
        return status;
    }

    // Установка нового статуса заказа
    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    // Расчёт общей стоимости заказа
    public float calculateTotalPrice() {
        return (float) items.stream()
                .mapToDouble(item -> item.getQuantity() * item.getPrice())
                .sum();
    }
}
