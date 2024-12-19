package orderservice.integration;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import orderservice.infrastructure.grpc.PaymentServiceClient;
import orderservice.application.CreateOrderUseCase;
import orderservice.domain.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class OrderServiceIntegrationTest {
    private CreateOrderUseCase createOrderUseCase;
    private ManagedChannel channel;

    @BeforeEach
    public void setUp() {
        // Подключение к Payment Service через gRPC
        channel = ManagedChannelBuilder.forAddress("localhost", 9090)
                .usePlaintext()
                .build();
        PaymentServiceClient paymentServiceClient = new PaymentServiceClient("localhost", 9090);
        createOrderUseCase = new CreateOrderUseCase(paymentServiceClient);
    }

    @AfterEach
    public void tearDown() {
        if (channel != null) {
            channel.shutdown();
        }
    }

    @Test
    public void testCreateOrderWithPaymentIntegration() {
        // Входные данные
        String userId = "user123";
        List<OrderItem> items = List.of(new OrderItem("product1", 2, 100.0));

        // Выполнение Use Case
        Order order = createOrderUseCase.execute(userId, items);

        // Проверки
        assertNotNull(order);
        assertEquals(OrderStatus.CONFIRMED, order.getStatus());
        assertEquals(200.0, order.calculateTotalPrice());
    }
}
