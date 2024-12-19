package orderservice;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import orderservice.application.CreateOrderUseCase;
import orderservice.infrastructure.grpc.OrderServiceGrpcImpl;
import orderservice.infrastructure.grpc.PaymentServiceClient;

public class OrderServiceApplication {
    public static void main(String[] args) throws Exception {
        // Адрес и порт Payment Service
        String paymentServiceHost = "localhost";
        int paymentServicePort = 9090;

        // Настройка клиента Payment Service
        PaymentServiceClient paymentServiceClient = new PaymentServiceClient(paymentServiceHost, paymentServicePort);

        // Создание Use Case
        CreateOrderUseCase createOrderUseCase = new CreateOrderUseCase(paymentServiceClient);

        // Настройка gRPC сервера
        Server server = ServerBuilder.forPort(8080)
                .addService(new OrderServiceGrpcImpl(createOrderUseCase))
                .build();

        System.out.println("Order Service is running on port 8080...");
        server.start();
        server.awaitTermination();
    }
}
