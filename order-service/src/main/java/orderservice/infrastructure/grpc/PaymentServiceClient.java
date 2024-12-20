package orderservice.infrastructure.grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import paymentservice.PaymentServiceGrpc;
import paymentservice.PaymentServiceOuterClass.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PaymentServiceClient {
    private final PaymentServiceGrpc.PaymentServiceBlockingStub paymentServiceStub;
    private static final Logger logger = LoggerFactory.getLogger(PaymentServiceClient.class);

    public PaymentServiceClient(String paymentServiceHost, int paymentServicePort) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(paymentServiceHost, paymentServicePort)
                .usePlaintext()
                .build();
        paymentServiceStub = PaymentServiceGrpc.newBlockingStub(channel);
    }

    public boolean processPayment(String orderId, double amount) {
        // Создание запроса для Payment Service
        ProcessPaymentRequest request = ProcessPaymentRequest.newBuilder()
                .setOrderId(orderId)
                .setAmount(amount)
                .build();
        logger.info("Создание запроса для Payment Service");

        // Вызов gRPC метода
        ProcessPaymentResponse response = paymentServiceStub.processPayment(request);
        logger.info("Вызов gRPC метода");

        // Возвращаем результат на основе статуса
        return "SUCCESS".equals(response.getStatus());
    }
}
