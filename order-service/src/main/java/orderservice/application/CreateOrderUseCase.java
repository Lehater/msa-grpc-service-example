package orderservice.application;

import orderservice.domain.*;
import orderservice.infrastructure.grpc.PaymentServiceClient;

import java.util.List;
import java.util.UUID;

public class CreateOrderUseCase {
    private final PaymentServiceClient paymentServiceClient;

    public CreateOrderUseCase(PaymentServiceClient paymentServiceClient) {
        this.paymentServiceClient = paymentServiceClient;
    }

    public Order execute(String userId, List<OrderItem> items) {
        // Генерация уникального идентификатора заказа
        String orderId = UUID.randomUUID().toString();

        // Создание нового заказа
        Order order = new Order(orderId, userId, items, OrderStatus.CREATED);

        // Расчёт общей стоимости
        float totalPrice = order.calculateTotalPrice();

        // Вызов Payment Service через gRPC
        boolean paymentSuccess = paymentServiceClient.processPayment(orderId, totalPrice);

        if (paymentSuccess) {
            order.setStatus(OrderStatus.CONFIRMED);
        } else {
            order.setStatus(OrderStatus.CANCELLED);
        }

        return order;
    }
}
