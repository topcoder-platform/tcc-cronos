package com.topcoder.service.review.specification;

import java.io.Serializable;
import com.topcoder.management.scorecard.data.Scorecard;
import com.topcoder.management.review.data.Review;

/**
 * This class is a container for information about a single specification review. It holds Review and
 * Scorecard instances. It is a simple JavaBean (POJO) that provides getters and setters for all private
 * attributes and performs no argument validation in the setters.
 * 
 * Thread Safety:
 * This class is mutable and not thread safe.
 */
public class SpecificationReview implements Serializable {

    /**
     * The Review instance for this specification review. Can be any value. Has getter and setter.
     */
    private Review review;

    /**
     * The Scorecard instance for this specification review. Can be any value. Has getter and setter.
     */
    private Scorecard scorecard;

    /**
     * Creates an instance of SpecificationReview.
     * 
     * Implementation Notes:
     * Do nothing.
     */
    public SpecificationReview() {
    }

    /**
     * Retrieves the Review instance for this specification review.
     * 
     * Returns:
     * the Review instance for this specification review
     * 
     * @param Return the Review instance for this specification review
     * @return the Review instance for this specification review
     */
    public Review getReview() {
        return review;
    }

    /**
     * Sets the Review instance for this specification review.
     * 
     * Parameters:
     * review - the Review instance for this specification review
     * 
     * @param Return void
     * @param review the Review instance for this specification review
     */
    public void setReview(Review review) {
        this.review = review;
    }

    /**
     * Retrieves the Scorecard instance for this specification review.
     * 
     * Returns:
     * the Scorecard instance for this specification review
     * 
     * @param Return the Scorecard instance for this specification review
     * @return the Scorecard instance for this specification review
     */
    public Scorecard getScorecard() {
        return scorecard;
    }

    /**
     * Sets the Scorecard instance for this specification review.
     * 
     * Parameters:
     * scorecard - the Scorecard instance for this specification review
     * 
     * @param scorecard the Scorecard instance for this specification review
     * @param Return void
     */
    public void setScorecard(Scorecard scorecard) {
        this.scorecard = scorecard;
    }
}
