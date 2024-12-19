package paymentservice.infrastructure.grpc;

import io.grpc.stub.StreamObserver;
import paymentservice.application.ProcessPaymentUseCase;
import paymentservice.domain.Transaction;
import paymentservice.PaymentServiceGrpc;
import paymentservice.PaymentServiceOuterClass.*;

public class PaymentServiceGrpcImpl extends PaymentServiceGrpc.PaymentServiceImplBase {
    private final ProcessPaymentUseCase processPaymentUseCase;

    public PaymentServiceGrpcImpl(ProcessPaymentUseCase processPaymentUseCase) {
        this.processPaymentUseCase = processPaymentUseCase;
    }

    @Override
    public void processPayment(ProcessPaymentRequest request, StreamObserver<ProcessPaymentResponse> responseObserver) {
        // Обработка платежа через Use Case
        Transaction transaction = processPaymentUseCase.execute(request.getOrderId(), request.getAmount());

        // Формирование ответа
        ProcessPaymentResponse response = ProcessPaymentResponse.newBuilder()
                .setTransactionId(transaction.getTransactionId())
                .setStatus(transaction.getStatus().name())
                .build();

        // Отправка ответа клиенту
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
