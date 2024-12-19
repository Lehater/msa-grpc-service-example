package orderservice.interfaces;

import orderservice.application.CreateOrderUseCase;
import orderservice.domain.Order;
import orderservice.domain.OrderItem;
import orderservice.domain.OrderStatus;

import java.util.List;
import java.util.stream.Collectors;

public class OrderController {
    private final CreateOrderUseCase createOrderUseCase;

    public OrderController(CreateOrderUseCase createOrderUseCase) {
        this.createOrderUseCase = createOrderUseCase;
    }

    /**
     * Метод для обработки HTTP POST-запроса на создание заказа.
     *
     * @param userId ID пользователя
     * @param items  Список товаров в заказе
     * @return OrderDTO с ID заказа и статусом
     */
    public OrderDTO createOrder(String userId, List<OrderItemDTO> items) {
        // Преобразование DTO в доменные объекты
        List<OrderItem> domainItems = items.stream()
                .map(item -> new OrderItem(item.getProductId(), item.getQuantity(), item.getPrice()))
                .collect(Collectors.toList());

        // Выполнение Use Case для создания заказа
        Order order = createOrderUseCase.execute(userId, domainItems);

        // Преобразование результата в DTO
        return toDTO(order);
    }

    /**
     * Преобразование доменного объекта Order в DTO для передачи клиенту.
     *
     * @param order Доменный объект заказа
     * @return OrderDTO для передачи клиенту
     */
    private OrderDTO toDTO(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setOrderId(order.getOrderId());
        dto.setUserId(order.getUserId());
        dto.setItems(order.getItems().stream()
                .map(item -> {
                    OrderItemDTO itemDTO = new OrderItemDTO();
                    itemDTO.setProductId(item.getProductId());
                    itemDTO.setQuantity(item.getQuantity());
                    itemDTO.setPrice(item.getPrice());
                    return itemDTO;
                })
                .collect(Collectors.toList()));
        dto.setStatus(order.getStatus().name());
        return dto;
    }
}
