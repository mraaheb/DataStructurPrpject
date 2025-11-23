/**
 * CSC 212 Project
 * Class: Customer
 * * Represents a single customer entity in the e-commerce system.
 * * DESIGN CHOICE (Distributed vs Centralized):
 * Each Customer object is responsible for managing its own order history.
 * This encapsulates customer-specific data within the customer object itself.
 * * PHASE 2 UPDATE:
 * Implements 'Comparable<Customer>' to allow sorting and storage in a BST/AVL Tree
 * based on the unique 'customerId'.
 */
public class Customer implements Comparable<Customer> {

    // ==========================================================
    // --- ATTRIBUTES ---
    // ==========================================================
    
    private String customerId;   // Unique identifier for the customer
    private String name;         // Customer's full name
    private String email;        // Customer's email address
    
    // [PHASE 1 Requirement]: "orders list"
    // We use our custom MyLinkedList to store the history of orders placed by this customer.
    private MyLinkedList<Order> orders; 

    /**
     * Constructor to create a new Customer.
     * Initializes attributes and creates an empty list for order history.
     */
    public Customer(String customerId, String name, String email) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        
        // Initialize the order history list
        this.orders = new MyLinkedList<>();
    }

    // ==========================================================
    // --- GETTERS (Accessors) ---
    // ==========================================================
    
    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    // ==========================================================
    // --- CORE OPERATIONS (Business Logic) ---
    // ==========================================================

    /**
     * [PHASE 1 Requirement]: Helper for "Place a new order"
     * Links a newly created order to this customer's history.
     * @param order The order object to add.
     */
    public void addOrderToHistory(Order order) {
        this.orders.add(order);
    }

    /**
     * [PHASE 1 Requirement]: "View order history"
     * Retrieves the complete list of orders placed by this customer.
     * @return The MyLinkedList containing all Order objects.
     */
    public MyLinkedList<Order> getOrderHistory() {
        return this.orders;
    }

    // ==========================================================
    // --- PHASE 2 OPERATIONS (Sorting & Trees) ---
    // ==========================================================

    /**
     * [PHASE 2 Requirement]: "Customers... stored using a BST... Keyed by customerId"
     * This method defines the natural ordering of Customers based on their ID.
     * It allows the BST to decide whether to go Left or Right during insertion/search.
     * * @param other The other customer to compare against.
     * @return negative if this < other, zero if equal, positive if this > other.
     */
    @Override
    public int compareTo(Customer other) {
        // Delegate comparison to the String class's compareTo method
        return this.customerId.compareTo(other.getCustomerId());
    }
}