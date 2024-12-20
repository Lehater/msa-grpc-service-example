package paymentservice;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import paymentservice.application.ProcessPaymentUseCase;
import paymentservice.infrastructure.grpc.PaymentServiceGrpcImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PaymentServiceApplication {
    private static final Logger logger = LoggerFactory.getLogger(PaymentServiceApplication.class);

    public static void main(String[] args) throws Exception {
        // Создание Use Case для обработки платежей
        ProcessPaymentUseCase processPaymentUseCase = new ProcessPaymentUseCase();
        logger.info("Создание Use Case для обработки платежей");

        // Настройка gRPC сервера
        Server server = ServerBuilder.forPort(9090)
                .addService(new PaymentServiceGrpcImpl(processPaymentUseCase))
                .build();
        logger.info("Настройка gRPC сервера");

        server.start();
        logger.info("Payment Service is running on port 9090");
        server.awaitTermination();
    }
}

