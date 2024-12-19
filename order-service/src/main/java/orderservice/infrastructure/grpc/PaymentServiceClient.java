package orderservice.infrastructure.grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import paymentservice.PaymentServiceGrpc;
import paymentservice.PaymentServiceOuterClass.*;

public class PaymentServiceClient {
    private final PaymentServiceGrpc.PaymentServiceBlockingStub paymentServiceStub;

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

        // Вызов gRPC метода
        ProcessPaymentResponse response = paymentServiceStub.processPayment(request);

        // Возвращаем результат на основе статуса
        return "SUCCESS".equals(response.getStatus());
    }
}
