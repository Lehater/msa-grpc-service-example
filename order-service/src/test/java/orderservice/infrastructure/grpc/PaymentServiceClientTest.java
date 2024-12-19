package orderservice.infrastructure.grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentServiceClientTest {
    private PaymentServiceClient paymentServiceClient;
    private ManagedChannel channel;

    @BeforeEach
    public void setUp() {
        // Подключение к локальному gRPC серверу (например, тестовый сервер)
        channel = ManagedChannelBuilder.forAddress("localhost", 9090)
                .usePlaintext()
                .build();
        paymentServiceClient = new PaymentServiceClient("localhost", 9090);
    }

    @AfterEach
    public void tearDown() {
        if (channel != null) {
            channel.shutdown();
        }
    }

    @Test
    public void testProcessPaymentSuccess() {
        // Проверка успешного платежа
        boolean result = paymentServiceClient.processPayment("order123", 100.0);
        assertTrue(result, "Payment should be successful");
    }

    @Test
    public void testProcessPaymentFailure() {
        // Проверка неуспешного платежа
        boolean result = paymentServiceClient.processPayment("order123", -100.0); // Например, некорректная сумма
        assertFalse(result, "Payment should fail");
    }
}
