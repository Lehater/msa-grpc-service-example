package orderservice;

import orderservice.infrastructure.config.AppConfig;
import orderservice.infrastructure.grpc.OrderServiceGrpcServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OrderServiceApplication {
    private static final Logger logger = LoggerFactory.getLogger(OrderServiceApplication.class);

    public static void main(String[] args) {
        try {
            // Инициализация AppConfig
            AppConfig config = new AppConfig("application.properties");
            logger.info("Инициализация AppConfig");

            // Запуск gRPC сервера
            OrderServiceGrpcServer grpcServer = new OrderServiceGrpcServer(config);
            grpcServer.start();
            logger.info("Запуск gRPC сервера");
        } catch (Exception e) {
            System.err.println("Failed to start Order Service: " + e.getMessage());
            logger.info("Failed to start Order Service");
            e.printStackTrace();
        }
    }
}
