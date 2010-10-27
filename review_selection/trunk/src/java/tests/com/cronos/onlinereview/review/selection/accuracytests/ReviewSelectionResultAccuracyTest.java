/**
 *
 */
package com.cronos.onlinereview.review.selection.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.cronos.onlinereview.review.selection.ReviewSelectionResult;
import com.topcoder.management.project.ReviewApplication;

/**
 * This class contains Accuracy tests for ReviewSelectionResult.
 * @author sokol
 * @version 1.0
 */
public class ReviewSelectionResultAccuracyTest extends TestCase {

    /**
     * Creates suite that aggregates all Accuracy test cases for ReviewSelectionResult.
     * @return Test suite that aggregates all Accuracy test cases for ReviewSelectionResult
     */
    public static Test suite() {
        return new TestSuite(ReviewSelectionResultAccuracyTest.class);
    }

    /**
     * <p>
     * Tests ReviewSelectionResult constructor.
     * </p>
     * <p>
     * ReviewSelectionResult instance should be created successfully. No exception is expected.
     * </p>
     */
    public void testConstructor() {
        ReviewApplication primaryReviewer = new ReviewApplication();
        ReviewApplication[] secondaryReviewers = new ReviewApplication[] {new ReviewApplication()};
        ReviewSelectionResult reviewSelectionResult = new ReviewSelectionResult(primaryReviewer, secondaryReviewers);
        assertNotNull("ReviewSelectionResult instance should be created successfully.", reviewSelectionResult);
        assertSame("primaryReviewer should be set successfully.", primaryReviewer, reviewSelectionResult
                .getPrimaryReviewer());
        assertEquals("secondaryReviewers should be set successfully.", 1, reviewSelectionResult
                .getSecondaryReviewers().length);
        assertNotSame("secondaryReviewers copy should be used.", secondaryReviewers, reviewSelectionResult
                .getSecondaryReviewers());
    }
}
