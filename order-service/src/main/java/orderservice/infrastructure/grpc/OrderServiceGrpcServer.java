package orderservice.infrastructure.grpc;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import orderservice.application.CreateOrderUseCase;
import orderservice.infrastructure.config.AppConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OrderServiceGrpcServer {
    private final Server server;
    private static final Logger logger = LoggerFactory.getLogger(OrderServiceGrpcServer.class);

    public OrderServiceGrpcServer(AppConfig config) {
        // Получение параметров из AppConfig
        String paymentServiceHost = config.getString("payment.service.host", true);
        int paymentServicePort = config.getInt("payment.service.port", true);
        logger.info("Получение параметров из AppConfig");

        // Настройка gRPC клиента для Payment Service
        PaymentServiceClient paymentServiceClient = new PaymentServiceClient(paymentServiceHost, paymentServicePort);
        logger.info("Настройка gRPC клиента для Payment Service");

        // Use Case для Order Service
        CreateOrderUseCase createOrderUseCase = new CreateOrderUseCase(paymentServiceClient);
        logger.info("Use Case для Order Service");

        // Настройка gRPC сервера
        this.server = ServerBuilder.forPort(8080)
                .addService(new OrderServiceGrpcImpl(createOrderUseCase))
                .build();
        logger.info("Настройка gRPC сервера");
    }

    public void start() throws Exception {
        server.start();
        logger.info("Order Service gRPC Server started on port 8080");
        server.awaitTermination();
    }
}
