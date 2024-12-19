package paymentservice.infrastructure.grpc;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import paymentservice.application.ProcessPaymentUseCase;

public class PaymentServiceGrpcServer {
    public static void main(String[] args) throws Exception {
        // Use Case для Payment Service
        ProcessPaymentUseCase processPaymentUseCase = new ProcessPaymentUseCase();

        // gRPC сервер
        Server server = ServerBuilder.forPort(9090)
                .addService(new PaymentServiceGrpcImpl(processPaymentUseCase))
                .build();

        System.out.println("Payment Service gRPC Server started on port 9090");
        server.start();
        server.awaitTermination();
    }
}
