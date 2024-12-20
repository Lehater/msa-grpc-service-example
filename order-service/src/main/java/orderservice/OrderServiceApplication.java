package orderservice;

import orderservice.infrastructure.config.AppConfig;
import orderservice.infrastructure.grpc.OrderServiceGrpcServer;

public class OrderServiceApplication {
    public static void main(String[] args) {
        try {
            // Инициализация AppConfig
            AppConfig config = new AppConfig("application.properties");

            // Запуск gRPC сервера
            OrderServiceGrpcServer grpcServer = new OrderServiceGrpcServer(config);
            grpcServer.start();
        } catch (Exception e) {
            System.err.println("Failed to start Order Service: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
