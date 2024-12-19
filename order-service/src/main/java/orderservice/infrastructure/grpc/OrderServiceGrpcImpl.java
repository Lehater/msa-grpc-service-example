package orderservice.infrastructure.grpc;

import io.grpc.stub.StreamObserver;
import orderservice.application.CreateOrderUseCase;
import orderservice.domain.Order;
import orderservice.domain.OrderItem;
import orderservice.OrderServiceGrpc;
import orderservice.OrderServiceOuterClass.*;

import java.util.List;
import java.util.stream.Collectors;

public class OrderServiceGrpcImpl extends OrderServiceGrpc.OrderServiceImplBase {
    private final CreateOrderUseCase createOrderUseCase;

    public OrderServiceGrpcImpl(CreateOrderUseCase createOrderUseCase) {
        this.createOrderUseCase = createOrderUseCase;
    }

    @Override
    public void createOrder(CreateOrderRequest request, StreamObserver<CreateOrderResponse> responseObserver) {
        try {
            // Преобразование gRPC-запроса в доменные объекты
            List<OrderItem> items = request.getItemsList().stream()
                    .map(item -> new OrderItem(item.getProductId(), item.getQuantity(), item.getPrice()))
                    .collect(Collectors.toList());

            // Выполнение Use Case для создания заказа
            Order order = createOrderUseCase.execute(request.getUserId(), items);

            // Формирование ответа
            CreateOrderResponse response = CreateOrderResponse.newBuilder()
                    .setOrderId(order.getOrderId())
                    .setStatus(order.getStatus().name())
                    .build();

            // Отправка ответа клиенту
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            // Обработка ошибок
            responseObserver.onError(e);
        }
    }
}
