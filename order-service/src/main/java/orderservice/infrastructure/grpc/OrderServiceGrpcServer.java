package orderservice.infrastructure.grpc;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import orderservice.application.CreateOrderUseCase;
import orderservice.infrastructure.config.AppConfig;

public class OrderServiceGrpcServer {
    private final Server server;

    public OrderServiceGrpcServer(AppConfig config) {
        // Получение параметров из AppConfig
        String paymentServiceHost = config.getString("payment.service.host", true);
        int paymentServicePort = config.getInt("payment.service.port", true);

        // Настройка gRPC клиента для Payment Service
        PaymentServiceClient paymentServiceClient = new PaymentServiceClient(paymentServiceHost, paymentServicePort);

        // Use Case для Order Service
        CreateOrderUseCase createOrderUseCase = new CreateOrderUseCase(paymentServiceClient);

        // Настройка gRPC сервера
        this.server = ServerBuilder.forPort(8080)
                .addService(new OrderServiceGrpcImpl(createOrderUseCase))
                .build();
    }

    public void start() throws Exception {
        System.out.println("Order Service gRPC Server started on port 8080");
        server.start();
        server.awaitTermination();
    }
}
