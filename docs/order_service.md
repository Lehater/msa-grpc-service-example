
# Order Service

```text
order-service/
├── src/main/java/orderservice/
│   ├── domain/
│   │   ├── Order.java
│   │   ├── OrderItem.java
│   │   └── OrderStatus.java
│   ├── application/
│   │   └── CreateOrderUseCase.java
│   ├── infrastructure/
│   │   ├── grpc/
│   │   │   └── PaymentServiceClient.java
│   └── interfaces/
│       └── OrderController.java
└── src/main/resources/
    └── application.properties
```