/**
 * CSC 212 Project
 * Class: Review
 * * Represents a single review posted by a customer for a specific product.
 * * DESIGN CHOICE:
 * We include 'customerId' here to link the review back to its author.
 * This enables the "Find all reviews by a customer" requirement efficiently.
 */
public class Review {

    // ==========================================================
    // --- ATTRIBUTES ---
    // ==========================================================

    private String customerId;   // The ID of the customer who wrote this review
    private int ratingScore;     // Rating score (1-5)
    private String textComment;  // The text content of the review

    /**
     * Constructor to create a new Review.
     * @param customerId The ID of the author (Customer).
     * @param ratingScore The score given (must be 1-5).
     * @param textComment The review text.
     */
    public Review(String customerId, int ratingScore, String textComment) {
        this.customerId = customerId;
        this.ratingScore = ratingScore;
        this.textComment = textComment;
    }

    // ==========================================================
    // --- GETTERS (Accessors) ---
    // ==========================================================

    public String getCustomerId() {
        return customerId;
    }

    public int getRatingScore() {
        return ratingScore;
    }

    public String getTextComment() {
        return textComment;
    }

    // ==========================================================
    // --- SETTERS (Mutators) ---
    // [PHASE 1 Requirement]: "Edit review"
    // These allow updating the review content and rating.
    // ==========================================================

    public void setRatingScore(int ratingScore) {
        this.ratingScore = ratingScore;
    }

    public void setTextComment(String textComment) {
        this.textComment = textComment;
    }
}