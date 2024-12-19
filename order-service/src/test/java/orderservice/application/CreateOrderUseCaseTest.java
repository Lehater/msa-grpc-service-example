package orderservice.application;

import orderservice.domain.*;
import orderservice.infrastructure.grpc.PaymentServiceClient;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CreateOrderUseCaseTest {

    @Test
    public void testCreateOrderSuccess() {
        // Мокирование PaymentServiceClient
        PaymentServiceClient paymentServiceClient = mock(PaymentServiceClient.class);
        when(paymentServiceClient.processPayment(anyString(), anyDouble())).thenReturn(true);

        // Создание Use Case
        CreateOrderUseCase createOrderUseCase = new CreateOrderUseCase(paymentServiceClient);

        // Входные данные
        String userId = "user123";
        List<OrderItem> items = List.of(new OrderItem("product1", 2, 100.0));

        // Выполнение Use Case
        Order order = createOrderUseCase.execute(userId, items);

        // Проверки
        assertNotNull(order);
        assertEquals(OrderStatus.CONFIRMED, order.getStatus());
        assertEquals(200.0, order.calculateTotalPrice());
        verify(paymentServiceClient, times(1)).processPayment(order.getOrderId(), 200.0);
    }

    @Test
    public void testCreateOrderFailure() {
        // Мокирование PaymentServiceClient
        PaymentServiceClient paymentServiceClient = mock(PaymentServiceClient.class);
        when(paymentServiceClient.processPayment(anyString(), anyDouble())).thenReturn(false);

        // Создание Use Case
        CreateOrderUseCase createOrderUseCase = new CreateOrderUseCase(paymentServiceClient);

        // Входные данные
        String userId = "user123";
        List<OrderItem> items = List.of(new OrderItem("product1", 2, 100.0));

        // Выполнение Use Case
        Order order = createOrderUseCase.execute(userId, items);

        // Проверки
        assertNotNull(order);
        assertEquals(OrderStatus.CANCELLED, order.getStatus());
        assertEquals(200.0, order.calculateTotalPrice());
        verify(paymentServiceClient, times(1)).processPayment(order.getOrderId(), 200.0);
    }
}
