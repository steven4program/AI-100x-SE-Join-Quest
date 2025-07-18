import java.util.List;

public class Order {
    private List<OrderItem> items;
    private int totalAmount;
    private int originalAmount;
    private int discount;

    public Order(List<OrderItem> items, int totalAmount, int originalAmount, int discount) {
        this.items = items;
        this.totalAmount = totalAmount;
        this.originalAmount = originalAmount;
        this.discount = discount;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public int getOriginalAmount() {
        return originalAmount;
    }

    public int getDiscount() {
        return discount;
    }
}