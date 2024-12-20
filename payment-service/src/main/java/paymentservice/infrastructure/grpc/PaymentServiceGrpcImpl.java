package paymentservice.infrastructure.grpc;

import io.grpc.stub.StreamObserver;
import paymentservice.application.ProcessPaymentUseCase;
import paymentservice.domain.Transaction;
import paymentservice.PaymentServiceGrpc;
import paymentservice.PaymentServiceOuterClass.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PaymentServiceGrpcImpl extends PaymentServiceGrpc.PaymentServiceImplBase {
    private final ProcessPaymentUseCase processPaymentUseCase;
    private static final Logger logger = LoggerFactory.getLogger(PaymentServiceGrpcImpl.class);

    public PaymentServiceGrpcImpl(ProcessPaymentUseCase processPaymentUseCase) {
        this.processPaymentUseCase = processPaymentUseCase;
    }

    @Override
    public void processPayment(ProcessPaymentRequest request, StreamObserver<ProcessPaymentResponse> responseObserver) {

        // Обработка платежа через Use Case
        Transaction transaction = processPaymentUseCase.execute(request.getOrderId(), request.getAmount());
        logger.info("Обработан платеж через Use Case");

        // Формирование ответа
        ProcessPaymentResponse response = ProcessPaymentResponse.newBuilder()
                .setTransactionId(transaction.getTransactionId())
                .setStatus(transaction.getStatus().name())
                .build();
        logger.info("Формирован ответ");

        // Отправка ответа клиенту
        responseObserver.onNext(response);
        responseObserver.onCompleted();
        logger.info("Отправлен ответ клиенту");
    }
}
