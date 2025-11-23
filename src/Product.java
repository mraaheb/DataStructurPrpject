/**
 * CSC 212 Project
 * Class: Product
 * * Represents a single product in the e-commerce system.
 * * DESIGN CHOICE (Distributed vs Centralized):
 * We adopted a "Distributed" design where each Product object is responsible 
 * for managing its own list of reviews. This improves encapsulation and 
 * efficiency for product-specific operations.
 * * PHASE 2 UPDATE:
 * Implements 'Comparable<Product>' to allow sorting and storage in a BST/AVL Tree.
 */
public class Product implements Comparable<Product> {

    // ==========================================================
    // --- ATTRIBUTES ---
    // ==========================================================
    private String productId;
    private String name;
    private double price;
    private int stock;
    
    // [PHASE 1 Requirement]: "list of reviews"
    // We use our custom MyLinkedList to store reviews specific to this product.
    private MyLinkedList<Review> reviews; 

    /**
     * Constructor to create a new Product.
     * Initializes attributes and creates an empty list for reviews.
     */
    public Product(String productId, String name, double price, int stock) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.stock = stock;
        
        // Initialize our custom list for reviews
        this.reviews = new MyLinkedList<>();
    }

    // ==========================================================
    // --- GETTERS (Accessors) ---
    // ==========================================================
    public String getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }
    
    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public MyLinkedList<Review> getReviews() {
        return reviews;
    }
    
    // ==========================================================
    // --- SETTERS (Mutators) ---
    // [PHASE 1 Requirement]: "update products"
    // These methods allow modifying product details.
    // ==========================================================

    public void setPrice(double price) {
        this.price = price;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
    
    public void setName(String name) {
        this.name = name;
    }


    // ==========================================================
    // --- CORE OPERATIONS (Business Logic) ---
    // ==========================================================

    /**
     * [PHASE 1 Requirement]: "Add review"
     * Adds a new review object to this product's internal review list.
     * @param review The review object to add.
     */
    public void addReview(Review review) {
        this.reviews.add(review);
    }

    /**
     * [PHASE 1 Requirement]: "Edit review"
     * Searches for a review by a specific customer and updates it.
     * * @param customerId The ID of the customer who wrote the review.
     * @param newComment The updated comment text.
     * @param newRating The updated rating score.
     * @return true if found and updated, false otherwise.
     */
    public boolean editReview(String customerId, String newComment, int newRating) {
        // We must iterate through our MyLinkedList to find the matching review
        for (int i = 0; i < reviews.size(); i++) {
            Review r = reviews.get(i); // Using .get() from MyLinkedList
            
            if (r.getCustomerId().equals(customerId)) {
                // Found it. Now update it.
                r.setTextComment(newComment);
                r.setRatingScore(newRating); 
                return true; // Successfully updated
            }
        }
        return false; // Review from this customer not found
    }

    /**
     * [PHASE 1 Requirement]: "Get an average rating for product"
     * Calculates the average rating dynamically based on current reviews.
     * * Performance Note: This operation iterates over the reviews list.
     * Time Complexity: O(R) where R is the number of reviews for THIS product.
     * (This is much faster than searching a global reviews list).
     * * @return The average rating as a double (0.0 if no reviews).
     */
    public double getAverageRating() {
        if (reviews.isEmpty()) {
            return 0.0; // Avoid division by zero
        }

        double sum = 0;
        // Iterate through all reviews to calculate the sum
        for (int i = 0; i < reviews.size(); i++) {
            sum += reviews.get(i).getRatingScore();
        }

        return sum / reviews.size();
    }

    // ==========================================================
    // --- PHASE 2 OPERATIONS (Sorting & Trees) ---
    // ==========================================================

    /**
     * [PHASE 2 Requirement]: "Products... stored using a BST... Keyed by productId"
     * This method defines the natural ordering of Products based on their ID.
     * It allows the BST to decide whether to go Left or Right during insertion/search.
     * * @param other The other product to compare against.
     * @return negative if this < other, zero if equal, positive if this > other.
     */
    @Override
    public int compareTo(Product other) {
        // We delegate the comparison to the String class's compareTo method
        return this.productId.compareTo(other.getProductId());
    }
}