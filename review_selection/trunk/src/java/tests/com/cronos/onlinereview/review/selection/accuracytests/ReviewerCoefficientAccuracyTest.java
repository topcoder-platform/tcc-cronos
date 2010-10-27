/**
 *
 */
package com.cronos.onlinereview.review.selection.accuracytests;

import java.util.Date;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.cronos.onlinereview.review.selection.impl.ReviewerCoefficient;
import com.topcoder.management.project.ReviewApplication;

/**
 * This class contains Accuracy tests for ReviewerCoefficient.
 * @author sokol
 * @version 1.0
 */
public class ReviewerCoefficientAccuracyTest extends TestCase {

    /**
     * <p>
     * Represents constant less for testing.
     * </p>
     */
    private static final int LESS = -1;

    /**
     * <p>
     * Represents constant equal for testing.
     * </p>
     */
    private static final int EQUAL = 0;

    /**
     * <p>
     * Represents constant greater for testing.
     * </p>
     */
    private static final int GREATER = 1;

    /**
     * <p>
     * Represents ReviewerCoefficient instance for testing.
     * </p>
     */
    private ReviewerCoefficient reviewerCoefficient;

    /**
     * <p>
     * Represents ReviewApplication instance for testing.
     * </p>
     */
    private ReviewApplication reviewApplication;

    /**
     * <p>
     * Represents coefficientValue for testing.
     * </p>
     */
    private double coefficientValue;

    /**
     * Creates suite that aggregates all Accuracy test cases for ReviewerCoefficient.
     * @return Test suite that aggregates all Accuracy test cases for ReviewerCoefficient
     */
    public static Test suite() {
        return new TestSuite(ReviewerCoefficientAccuracyTest.class);
    }

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     * @throws Exception if any error occurs
     */
    protected void setUp() throws Exception {
        reviewApplication = createReviewApplication();
        coefficientValue = 2;
        reviewerCoefficient = new ReviewerCoefficient(reviewApplication, coefficientValue);
    }

    /**
     * <p>
     * Creates ReviewApplication instance with default values.
     * </p>
     * @return ReviewApplication instance with default values
     */
    private ReviewApplication createReviewApplication() {
        ReviewApplication reviewApplication = new ReviewApplication();
        reviewApplication.setAcceptPrimary(true);
        reviewApplication.setApplicationDate(new Date());
        reviewApplication.setId(1);
        reviewApplication.setProjectId(1);
        reviewApplication.setReviewerId(1);
        return reviewApplication;
    }

    /**
     * <p>
     * Tears down the test environment.
     * </p>
     * @throws Exception if any error occurs
     */
    protected void tearDown() throws Exception {
        reviewerCoefficient = null;
        reviewApplication = null;
    }

    /**
     * <p>
     * Tests ReviewerCoefficient constructor.
     * </p>
     * <p>
     * ReviewerCoefficient instance should be created successfully. No exception is expected.
     * </p>
     */
    public void testConstructor() {
        assertNotNull("ReviewerCoefficient instance should be created successfully.", reviewerCoefficient);
    }

    /**
     * <p>
     * Tests {@link ReviewerCoefficient#compareTo(ReviewerCoefficient)} with ReviewerCoefficient passed with greater
     * coefficient.
     * </p>
     * <p>
     * <code>1</code> is expected. No exception is expected.
     * </p>
     */
    public void testCompare_GreaterCoef() {
        ReviewApplication opponentApplication = createReviewApplication();
        double opponentCoef = coefficientValue + 1;
        ReviewerCoefficient opponentReviewerCoefficient = new ReviewerCoefficient(opponentApplication, opponentCoef);
        assertEquals("Reviewer value should be GREATER.", GREATER, reviewerCoefficient
                .compareTo(opponentReviewerCoefficient));
    }

    /**
     * <p>
     * Tests {@link ReviewerCoefficient#compareTo(ReviewerCoefficient)} with ReviewerCoefficient passed with greater
     * date.
     * </p>
     * <p>
     * <code>-1</code> is expected. No exception is expected.
     * </p>
     */
    public void testCompare_GreaterDate() {
        ReviewApplication opponentApplication = createReviewApplication();
        opponentApplication.setApplicationDate(new Date(reviewApplication.getApplicationDate().getTime() + 1));
        double opponentCoef = coefficientValue;
        ReviewerCoefficient opponentReviewerCoefficient = new ReviewerCoefficient(opponentApplication, opponentCoef);
        assertEquals("Reviewer value should be less.", LESS, reviewerCoefficient
                .compareTo(opponentReviewerCoefficient));
    }

    /**
     * <p>
     * Tests {@link ReviewerCoefficient#compareTo(ReviewerCoefficient)} with ReviewerCoefficient passed with greater
     * id.
     * </p>
     * <p>
     * <code>-1</code> is expected. No exception is expected.
     * </p>
     */
    public void testCompare_GreaterId() {
        ReviewApplication opponentApplication = createReviewApplication();
        opponentApplication.setApplicationDate(reviewApplication.getApplicationDate());
        opponentApplication.setReviewerId(2);
        double opponentCoef = coefficientValue;
        ReviewerCoefficient opponentReviewerCoefficient = new ReviewerCoefficient(opponentApplication, opponentCoef);
        assertEquals("Reviewer value should be less.", LESS, reviewerCoefficient
                .compareTo(opponentReviewerCoefficient));
    }

    /**
     * <p>
     * Tests {@link ReviewerCoefficient#compareTo(ReviewerCoefficient)} with ReviewerCoefficient passed same values.
     * </p>
     * <p>
     * <code>0</code> is expected. No exception is expected.
     * </p>
     */
    public void testCompare_Same() {
        ReviewerCoefficient opponentReviewerCoefficient = new ReviewerCoefficient(reviewApplication, coefficientValue);
        assertEquals("Reviewer value should be the same.", EQUAL, reviewerCoefficient
                .compareTo(opponentReviewerCoefficient));
    }

    /**
     * <p>
     * Tests {@link ReviewerCoefficient#compareTo(ReviewerCoefficient)} with ReviewerCoefficient passed with less
     * coefficient.
     * </p>
     * <p>
     * <code>1</code> is expected. No exception is expected.
     * </p>
     */
    public void testCompare_LessCoef() {
        ReviewApplication opponentApplication = createReviewApplication();
        double opponentCoef = coefficientValue - 1;
        ReviewerCoefficient opponentReviewerCoefficient = new ReviewerCoefficient(opponentApplication, opponentCoef);
        assertEquals("Reviewer value should be LESS.", LESS, reviewerCoefficient
                .compareTo(opponentReviewerCoefficient));
    }

    /**
     * <p>
     * Tests {@link ReviewerCoefficient#compareTo(ReviewerCoefficient)} with ReviewerCoefficient passed with less date.
     * </p>
     * <p>
     * <code>-1</code> is expected. No exception is expected.
     * </p>
     */
    public void testCompare_LessDate() {
        ReviewApplication opponentApplication = createReviewApplication();
        opponentApplication.setApplicationDate(new Date(reviewApplication.getApplicationDate().getTime()-1));
        double opponentCoef = coefficientValue;
        ReviewerCoefficient opponentReviewerCoefficient = new ReviewerCoefficient(opponentApplication, opponentCoef);
        assertEquals("Reviewer value should be GREATER.", GREATER, reviewerCoefficient
                .compareTo(opponentReviewerCoefficient));
    }

    /**
     * <p>
     * Tests {@link ReviewerCoefficient#compareTo(ReviewerCoefficient)} with ReviewerCoefficient passed with less
     * id.
     * </p>
     * <p>
     * <code>1</code> is expected. No exception is expected.
     * </p>
     */
    public void testCompare_LessId() {
        ReviewApplication opponentApplication = createReviewApplication();
        opponentApplication.setApplicationDate(reviewApplication.getApplicationDate());
        opponentApplication.setReviewerId(0);
        double opponentCoef = coefficientValue;
        ReviewerCoefficient opponentReviewerCoefficient = new ReviewerCoefficient(opponentApplication, opponentCoef);
        assertEquals("Reviewer value should be GREATER.", GREATER, reviewerCoefficient
                .compareTo(opponentReviewerCoefficient));
    }
}
