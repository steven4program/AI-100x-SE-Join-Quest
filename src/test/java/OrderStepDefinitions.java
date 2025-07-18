import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

public class OrderStepDefinitions {
    
    private OrderService orderService;
    private Order result;
    
    @Given("no promotions are applied")
    public void noPromotionsAreApplied() {
        orderService = new OrderService();
    }
    
    @When("a customer places an order with:")
    public void aCustomerPlacesAnOrderWith(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        List<OrderItem> orderItems = new ArrayList<>();
        
        for (Map<String, String> row : rows) {
            String productName = row.get("productName");
            int quantity = Integer.parseInt(row.get("quantity"));
            int unitPrice = Integer.parseInt(row.get("unitPrice"));
            String category = row.get("category");
            
            Product product = new Product(productName, unitPrice, category);
            OrderItem orderItem = new OrderItem(product, quantity);
            orderItems.add(orderItem);
        }
        
        result = orderService.checkout(orderItems);
    }
    
    @Then("the order summary should be:")
    public void theOrderSummaryShouldBe(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        Map<String, String> expected = rows.get(0);
        
        if (expected.containsKey("totalAmount")) {
            int expectedTotal = Integer.parseInt(expected.get("totalAmount"));
            assertEquals(expectedTotal, result.getTotalAmount());
        }
        
        if (expected.containsKey("originalAmount")) {
            int expectedOriginal = Integer.parseInt(expected.get("originalAmount"));
            assertEquals(expectedOriginal, result.getOriginalAmount());
        }
        
        if (expected.containsKey("discount")) {
            int expectedDiscount = Integer.parseInt(expected.get("discount"));
            assertEquals(expectedDiscount, result.getDiscount());
        }
    }
    
    @And("the customer should receive:")
    public void theCustomerShouldReceive(DataTable dataTable) {
        List<Map<String, String>> expectedItems = dataTable.asMaps(String.class, String.class);
        
        assertEquals(expectedItems.size(), result.getItems().size());
        
        for (int i = 0; i < expectedItems.size(); i++) {
            Map<String, String> expectedItem = expectedItems.get(i);
            OrderItem actualItem = result.getItems().get(i);
            
            assertEquals(expectedItem.get("productName"), actualItem.getProduct().getName());
            assertEquals(Integer.parseInt(expectedItem.get("quantity")), actualItem.getQuantity());
        }
    }
    
    @Given("the threshold discount promotion is configured:")
    public void theThresholdDiscountPromotionIsConfigured(DataTable dataTable) {
        if (orderService == null) {
            orderService = new OrderService();
        }
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        Map<String, String> config = rows.get(0);
        
        int threshold = Integer.parseInt(config.get("threshold"));
        int discount = Integer.parseInt(config.get("discount"));
        
        orderService.setThresholdDiscount(threshold, discount);
    }
    
    @Given("the buy one get one promotion for cosmetics is active")
    public void theBuyOneGetOnePromotionForCosmeticsIsActive() {
        if (orderService == null) {
            orderService = new OrderService();
        }
        orderService.setBuyOneGetOneForCosmetics(true);
    }
    
    @Given("the Double Eleven promotion is active")
    public void theDoubleElevenPromotionIsActive() {
        orderService = new OrderService();
        orderService.setDoubleElevenPromotion(true);
    }
}