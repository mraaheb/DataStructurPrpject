
/**
 * Represents a single product in the e-commerce system.
 * It manages product details and a list of its reviews. 
 */
public class Product {

    // --- Attributes --- 
    private String productId;
    private String name;
    private double price;
    private int stock;
    
    // Here we use our *own* data structure
    private MyLinkedList<Review> reviews; // A list of reviews 

    /**
     * Constructor to create a new Product.
     */
    public Product(String productId, String name, double price, int stock) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.stock = stock;
        
        // Initialize our custom list
        this.reviews = new MyLinkedList<>();
    }

    // --- Getters ---
    
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
    
    // --- Setters ---
    // Needed for the "update products" operation 

    public void setPrice(double price) {
        this.price = price;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
    
    public void setName(String name) {
        this.name = name;
    }


    // --- Core Operations as required ---

    /**
     * Adds a new review to this product's review list.
     * This fulfills the "Add/edit review" operation. [cite: 10, 13]
     * @param review The review object to add.
     */
    public void addReview(Review review) {
        this.reviews.add(review);
    }

    /**
     * Edits an existing review written by a specific customer.
     * This fulfills the "Add/edit review" operation. 
     */
    public void editReview(String customerId, String newComment, int newRating) {
        for (int i = 0; i < reviews.size(); i++) {
            Review r = reviews.get(i); // Using the .get() from MyLinkedList
            if (r.getCustomerId().equals(customerId)) {
                r.setTextComment(newComment);
                r.setRatingScore(newRating);
                break; // Exit the loop once found and updated
            }
        }
    }

    /**
     * Calculates and returns the average rating for this product.
     * This fulfills the "Get an average rating for product" operation. 
     * (Time Complexity: O(R) where R is the number of reviews for *this* product)
     * @return The average rating as a double.
     */
    public double getAverageRating() {
        if (reviews.isEmpty()) {
            return 0.0; // Avoid division by zero
        }

        double sum = 0;
        // Loop through all reviews using our MyLinkedList's .get() and .size()
        for (int i = 0; i < reviews.size(); i++) {
            sum += reviews.get(i).getRatingScore();
        }

        return sum / reviews.size();
    }
}