package orderservice.domain;

public class OrderItem {
    private final String productId; // Идентификатор товара
    private final int quantity;     // Количество
    private final double price;     // Цена за единицу товара

    public OrderItem(String productId, int quantity, double price) {
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }

    // Геттеры
    public String getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }
}
