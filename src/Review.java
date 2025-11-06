

/**
 *
 * @author mraah
 */
/**
 * Represents a single product review.
 * This class holds the rating score and text comment.
 */
public class Review {

    // Attributes as specified 
    private int ratingScore; // Score from 1-5
    private String textComment;
    
    // We add this attribute to know *who* wrote the review.
    // This is essential for the requirement: "Extract reviews from a specific customer" 
    private String customerId; 

    /**
     * Constructor to create a new Review.
     * @param customerId The ID of the customer who wrote the review.
     * @param ratingScore The score (1-5).
     * @param textComment The text of the review.
     */
    public Review(String customerId, int ratingScore, String textComment) {
        this.customerId = customerId;
        this.ratingScore = ratingScore;
        this.textComment = textComment;
    }

    // --- Getters ---
    // We need getters to read the data later.

    public int getRatingScore() {
        return ratingScore;
    }

    public String getTextComment() {
        return textComment;
    }

    public String getCustomerId() {
        return customerId;
    }

    // --- Setters ---
    // We need setters for the "edit review" operation 
    
    public void setRatingScore(int ratingScore) {
        this.ratingScore = ratingScore;
    }

    public void setTextComment(String textComment) {
        this.textComment = textComment;
    }
}