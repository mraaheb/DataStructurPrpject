/**
 * CSC 212 Project
 * Class: Product
 * * Represents a single product in the e-commerce system.
 * * DESIGN CHOICE (Distributed vs Centralized):
 *   We adopted a "Distributed" design where each Product object is responsible 
 *   for managing its own list of reviews. This improves encapsulation and 
 *   efficiency for product-specific operations.
 * * PHASE 2 UPDATE:
 *   Implements 'Comparable<Product>' to allow sorting and storage in a BST/AVL Tree.
 * * PHASE 2 OPTIMIZATION:
 *   We cache the aggregate rating (ratingSum, ratingCount) to compute the
 *   average rating in O(1) instead of re-scanning the review list each time.
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

    // PHASE 2 Optimization:
    // Instead of recomputing the average rating by scanning the whole reviews
    // list every time, we maintain cached aggregate values:
    //  - ratingSum   = sum of all rating scores for this product
    //  - ratingCount = number of reviews that contributed to the sum
    // This allows getAverageRating() to be O(1).
    private int ratingSum;    // مجموع التقييمات
    private int ratingCount;  // عدد التقييمات

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
        
        // Initialize rating aggregate
        this.ratingSum = 0;
        this.ratingCount = 0;
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
     * [PHASE 2 Optimization]:
     * Also updates ratingSum and ratingCount to keep a cached aggregate
     * for O(1) average rating computation.
     * @param review The review object to add.
     */
    public void addReview(Review review) {
        this.reviews.add(review);
        
        // Update cached aggregate values
        this.ratingSum += review.getRatingScore();
        this.ratingCount++;
    }

    /**
     * [PHASE 1 Requirement]: "Edit review"
     * Searches for a review by a specific customer and updates it.
     * [PHASE 2 Optimization]:
     * Adjusts ratingSum by removing the old score and adding the new score,
     * keeping ratingCount unchanged.
     * @param customerId The ID of the customer who wrote the review.
     * @param newComment The updated comment text.
     * @param newRating The updated rating score.
     * @return true if found and updated, false otherwise.
     */
    public boolean editReview(String customerId, String newComment, int newRating) {
        // We must iterate through our MyLinkedList to find the matching review
        for (int i = 0; i < reviews.size(); i++) {
            Review r = reviews.get(i); // Using .get() from MyLinkedList
            
            if (r.getCustomerId().equals(customerId)) {
                // Adjust cached ratingSum: subtract old score, add new score
                this.ratingSum -= r.getRatingScore();
                this.ratingSum += newRating;
                
                // ratingCount remains the same (we are editing, not adding/removing)
                
                // Update review fields
                r.setTextComment(newComment);
                r.setRatingScore(newRating); 
                return true; // Successfully updated
            }
        }
        return false; // Review from this customer not found
    }

    /**
     * [PHASE 1 Requirement]: "Get an average rating for product"
     * [PHASE 2 Optimization]:
     * Uses the cached aggregate values (ratingSum, ratingCount) instead of
     * re-scanning the reviews list every time.
     * Time Complexity: O(1).
     * @return The average rating as a double (0.0 if no reviews).
     */
    public double getAverageRating() {
        if (this.ratingCount == 0) {
            return 0.0; // Avoid division by zero
        }

        return (double) this.ratingSum / this.ratingCount;
    }

    // ==========================================================
    // --- PHASE 2 OPERATIONS (Sorting & Trees) ---
    // ==========================================================

    /**
     * [PHASE 2 Requirement]: "Products... stored using a BST... Keyed by productId"
     * This method defines the natural ordering of Products based on their ID.
     * It allows the BST/AVL to decide whether to go Left or Right during insertion/search.
     * @param other The other product to compare against.
     * @return negative if this < other, zero if equal, positive if this > other.
     */
    @Override
    public int compareTo(Product other) {
        // We delegate the comparison to the String class's compareTo method
        return this.productId.compareTo(other.getProductId());
    }
}
