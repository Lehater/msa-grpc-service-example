package paymentservice.infrastructure.grpc;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import paymentservice.application.ProcessPaymentUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PaymentServiceGrpcServer {
    private static final Logger logger = LoggerFactory.getLogger(PaymentServiceGrpcServer.class);

    public static void main(String[] args) throws Exception {
        // Use Case для Payment Service
        ProcessPaymentUseCase processPaymentUseCase = new ProcessPaymentUseCase();
        logger.info("Use Case для Payment Service");

        // gRPC сервер
        Server server = ServerBuilder.forPort(9090)
                .addService(new PaymentServiceGrpcImpl(processPaymentUseCase))
                .build();
        logger.info("gRPC сервер");

        System.out.println("Payment Service gRPC Server started on port 9090");
        server.start();
        server.awaitTermination();
        logger.info("Payment Service gRPC Server started on port 9090");
    }
}
