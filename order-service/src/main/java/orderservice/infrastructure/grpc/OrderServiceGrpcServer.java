package orderservice.infrastructure.grpc;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import orderservice.application.CreateOrderUseCase;


public class OrderServiceGrpcServer {
    public static void main(String[] args) throws Exception {
        // Настройка gRPC клиента для Payment Service
        PaymentServiceClient paymentServiceClient = new PaymentServiceClient("localhost", 9090);

        // Use Case для Order Service
        CreateOrderUseCase createOrderUseCase = new CreateOrderUseCase(paymentServiceClient);

        // gRPC сервер
        Server server = ServerBuilder.forPort(8080)
                .addService(new OrderServiceGrpcImpl(createOrderUseCase))
                .build();

        System.out.println("Order Service gRPC Server started on port 8080");
        server.start();
        server.awaitTermination();
    }
}
