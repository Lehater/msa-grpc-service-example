package orderservice.application;

import orderservice.domain.*;
import orderservice.infrastructure.grpc.PaymentServiceClient;

import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreateOrderUseCase {
    private static final Logger logger = LoggerFactory.getLogger(CreateOrderUseCase.class);

    private final PaymentServiceClient paymentServiceClient;

    public CreateOrderUseCase(PaymentServiceClient paymentServiceClient) {
        this.paymentServiceClient = paymentServiceClient;
    }

    public Order execute(String userId, List<OrderItem> items) {
        // Генерация уникального идентификатора заказа
        String orderId = UUID.randomUUID().toString();
        logger.info("Генерация уникального идентификатора заказа");

        // Создание нового заказа
        Order order = new Order(orderId, userId, items, OrderStatus.CREATED);
        logger.info("Создание нового заказа");

        // Расчёт общей стоимости
        float totalPrice = order.calculateTotalPrice();
        logger.info("Расчёт общей стоимости");

        // Вызов Payment Service через gRPC
        boolean paymentSuccess = paymentServiceClient.processPayment(orderId, totalPrice);
        logger.info("Вызов Payment Service через gRPC");

        if (paymentSuccess) {
            order.setStatus(OrderStatus.CONFIRMED);
        } else {
            order.setStatus(OrderStatus.CANCELLED);
        }

        return order;
    }
}
