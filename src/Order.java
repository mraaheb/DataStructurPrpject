import java.util.Date; 

/**
 * CSC 212 Project
 * Class: Order
 * * Represents a single customer order in the e-commerce system.
 * * DESIGN CHOICE:
 * Each order maintains its own list of products using our custom 'MyLinkedList'.
 * This encapsulates the order details within the order object itself.
 * * PHASE 2 UPDATE:
 * Implements 'Comparable<Order>' to allow sorting and storage in a BST/AVL Tree
 * based on the unique 'orderId'.
 */
public class Order implements Comparable<Order> {

    // ==========================================================
    // --- ATTRIBUTES ---
    // ==========================================================
    
    private String orderId;      // Unique identifier for the order
    private String customerId;   // Reference to the customer who placed the order
    private double totalPrice;   // Total cost of all products in the order
    private Date orderDate;      // Date when the order was placed
    private String status;       // e.g., "pending", "shipped", "canceled"

    // [PHASE 1 Requirement]: "list of products"
    // We use MyLinkedList to store the items purchased in this specific order.
    private MyLinkedList<Product> products; 

    /**
     * Constructor to create a new, pending order.
     * Initializes attributes and creates an empty list for products.
     */
    public Order(String orderId, String customerId, Date orderDate) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderDate = orderDate;
        
        this.products = new MyLinkedList<>(); // Create empty product list
        this.status = "pending"; // Default status
        this.totalPrice = 0.0;   // Initial price
    }

    // ==========================================================
    // --- GETTERS (Accessors) ---
    // ==========================================================
    
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

    // ==========================================================
    // --- CORE OPERATIONS (Business Logic) ---
    // ==========================================================

    /**
     * [PHASE 1 Requirement]: Helper for "Place a new order"
     * Adds a product to this order's internal list and updates the total price.
     * @param product The product to add.
     */
    public void addProductToOrder(Product product) {
        this.products.add(product);
        this.totalPrice += product.getPrice(); // Automatically update total
    }

    /**
     * [PHASE 1 Requirement]: "Update order status"
     * Updates the status of the order (e.g., from "pending" to "shipped").
     * @param newStatus The new status string.
     */
    public void updateStatus(String newStatus) {
        this.status = newStatus;
    }

    // ==========================================================
    // --- PHASE 2 OPERATIONS (Sorting & Trees) ---
    // ==========================================================

    /**
     * [PHASE 2 Requirement]: "Orders... stored using a BST... Keyed by orderId"
     * This method defines the natural ordering of Orders based on their ID.
     * It allows the BST to decide whether to go Left or Right during insertion/search.
     * * @param other The other order to compare against.
     * @return negative if this < other, zero if equal, positive if this > other.
     */
    @Override
    public int compareTo(Order other) {
        // Delegate comparison to the String class's compareTo method
        return this.orderId.compareTo(other.getOrderId());
    }
}