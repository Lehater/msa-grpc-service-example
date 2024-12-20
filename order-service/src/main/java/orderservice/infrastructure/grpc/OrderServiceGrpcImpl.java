package orderservice.infrastructure.grpc;

import io.grpc.stub.StreamObserver;
import orderservice.OrderServiceGrpc.*;
import orderservice.OrderServiceOuterClass.*;
import orderservice.application.CreateOrderUseCase;
import orderservice.domain.Order;
import orderservice.domain.OrderItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

public class OrderServiceGrpcImpl extends OrderServiceImplBase {
    private final CreateOrderUseCase createOrderUseCase;
    private static final Logger logger = LoggerFactory.getLogger(OrderServiceGrpcImpl.class);


    public OrderServiceGrpcImpl(CreateOrderUseCase createOrderUseCase) {
        this.createOrderUseCase = createOrderUseCase;
    }

    @Override
    public void createOrder(CreateOrderRequest request,
                            StreamObserver<CreateOrderResponse> responseObserver) {
        try {
            // Преобразование gRPC-запроса в доменные объекты
            List<OrderItem> items = request.getItemsList().stream()
                    .map(item -> new OrderItem(item.getProductId(), item.getQuantity(), item.getPrice()))
                    .collect(Collectors.toList());
            logger.info("Преобразован gRPC-запрос в доменные объекты");

            // Выполнение Use Case для создания заказа
            Order order = createOrderUseCase.execute(request.getUserId(), items);
            logger.info("Выполнен Use Case для создания заказа");

            // Формирование ответа
            CreateOrderResponse response = CreateOrderResponse.newBuilder()
                    .setOrderId(order.getOrderId())
                    .setStatus(order.getStatus().name())
                    .build();
            logger.info("Сформирован ответ");

            // Отправка ответа клиенту
            responseObserver.onNext(response);
            responseObserver.onCompleted();
            logger.info("Отправлено ответа клиенту");

        } catch (Exception e) {
            logger.info("Ошибка создания запроса");
            responseObserver.onError(e);
        }
    }
}
