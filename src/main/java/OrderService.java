import java.util.List;
import java.util.ArrayList;

public class OrderService {
    
    private int thresholdAmount;
    private int discountAmount;
    private boolean buyOneGetOneForCosmetics;
    private boolean doubleElevenPromotion;
    
    public void setThresholdDiscount(int threshold, int discount) {
        this.thresholdAmount = threshold;
        this.discountAmount = discount;
    }
    
    public void setBuyOneGetOneForCosmetics(boolean enabled) {
        this.buyOneGetOneForCosmetics = enabled;
    }
    
    public void setDoubleElevenPromotion(boolean enabled) {
        this.doubleElevenPromotion = enabled;
    }
    
    public Order checkout(List<OrderItem> items) {
        List<OrderItem> resultItems = new ArrayList<>();
        
        int originalAmount = 0;
        int totalDiscount = 0;
        
        for (OrderItem item : items) {
            originalAmount += item.getProduct().getUnitPrice() * item.getQuantity();
            
            int resultQuantity = item.getQuantity();
            if (buyOneGetOneForCosmetics && "cosmetics".equals(item.getProduct().getCategory())) {
                resultQuantity = item.getQuantity() + 1;
            }
            
            // Double Eleven promotion: 20% off every group of 10 identical items
            if (doubleElevenPromotion) {
                int discountedGroups = item.getQuantity() / 10;
                int discountPerGroup = item.getProduct().getUnitPrice() * 10 * 20 / 100; // 20% off
                totalDiscount += discountedGroups * discountPerGroup;
            }
            
            OrderItem resultItem = new OrderItem(item.getProduct(), resultQuantity);
            resultItems.add(resultItem);
        }
        
        // Threshold discount
        if (originalAmount >= thresholdAmount) {
            totalDiscount += discountAmount;
        }
        
        int totalAmount = originalAmount - totalDiscount;
        
        return new Order(resultItems, totalAmount, originalAmount, totalDiscount);
    }
}