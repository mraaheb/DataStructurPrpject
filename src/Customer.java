
/**
 * Represents a customer in the e-commerce system.
 * It holds customer details and their order history. 
 */
public class Customer {

    // --- Attributes --- 
    private String customerId;
    private String name;
    private String email;
    private MyLinkedList<Order> orders; // The customer's order history 

    /**
     * Constructor to create a new Customer.
     * This is used for the "Register new customer" operation. [cite: 6]
     */
    public Customer(String customerId, String name, String email) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        
        // Initialize their order history as a new, empty list
        this.orders = new MyLinkedList<>();
    }

    // --- Getters ---
    
    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    // --- Core Operations ---

    /**
     * Adds a newly placed order to this customer's history.
     * This is used by the "Place a new order" operation. [cite: 6]
     * @param order The order to add.
     */
    public void addOrderToHistory(Order order) {
        this.orders.add(order);
    }

    /**
     * Retrieves the customer's entire order history.
     * This fulfills the "View order history" operation. [cite: 6]
     * @return The list of orders.
     */
    public MyLinkedList<Order> getOrderHistory() {
        return this.orders;
    }
}