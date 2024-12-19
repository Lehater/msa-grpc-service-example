package paymentservice;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import paymentservice.application.ProcessPaymentUseCase;
import paymentservice.infrastructure.grpc.PaymentServiceGrpcImpl;

public class PaymentServiceApplication {
    public static void main(String[] args) throws Exception {
        // Создание Use Case для обработки платежей
        ProcessPaymentUseCase processPaymentUseCase = new ProcessPaymentUseCase();

        // Настройка gRPC сервера
        Server server = ServerBuilder.forPort(9090)
                .addService(new PaymentServiceGrpcImpl(processPaymentUseCase))
                .build();

        System.out.println("Payment Service is running on port 9090...");
        server.start();
        server.awaitTermination();
    }
}

