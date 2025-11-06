// We need this for the 'orderDate' attribute
import java.util.Date; 

/**
 * Represents a single order placed by a customer.
 * It contains a list of products, the total price, 
 * date, and status. 
 */
public class Order {

    // --- Attributes --- 
    private String orderId;
    private String customerId; // Reference to the customer 
    private MyLinkedList<Product> products; // The list of products in this order 
    private double totalPrice; // The total price of the order 
    private Date orderDate; // The date the order was placed 
    private String status; // e.g., "pending", "shipped", "delivered" 

    /**
     * Constructor to create a new, pending order.
     */
    public Order(String orderId, String customerId, Date orderDate) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderDate = orderDate;
        
        this.products = new MyLinkedList<>(); // Initialize our custom list
        this.status = "pending"; // Default status
        this.totalPrice = 0.0;
    }

    /**
     * Adds a product to this order and updates the total price.
     * @param product The product to add.
     */
    public void addProductToOrder(Product product) {
        this.products.add(product);
        this.totalPrice += product.getPrice(); // Update the total
    }

    // --- Getters ---
    
    public String getOrderId() {
        return orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public MyLinkedList<Product> getProducts() {
        return products;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public String getStatus() {
        return status;
    }

    // --- Core Operations ---

    /**
     * Updates the status of the order.
     * This fulfills the "Update order status" operation. [cite: 8]
     * @param newStatus The new status (e.g., "shipped").
     */
    public void updateStatus(String newStatus) {
        this.status = newStatus;
    }
}