/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.review.statistics.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.cronos.onlinereview.review.statistics.StatisticsCalculatorConfigurationException;
import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.management.review.data.Comment;
import com.topcoder.management.review.data.EvaluationType;
import com.topcoder.management.review.data.Review;

import junit.framework.TestCase;

/**
 * <p>
 * Unit test cases for <code>AccuracyCalculatorImpl</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AccuracyCalculatorImplUnitTests extends TestCase {
    /**
     * <code>AccuracyCalculatorImpl</code> class instance for unit test.
     */
    private AccuracyCalculatorImpl instance;

    /**
     * <code>ConfigurationObject</code> class instance for unit test.
     */
    private ConfigurationObject cfgObject;

    /**
     * <p>
     * Set up.
     * </p>
     *
     * @throws Exception for JUnit.
     */
    public void setUp() throws Exception {
        instance = new AccuracyCalculatorImpl();
        cfgObject = TestsHelper.loadConfig("config.xml").getChild(TestsHelper.NAMESPACE).getChild(
            "accuracyCalculatorConfig");
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>AccuracyCalculatorImpl()</code>.<br>
     * Instance should be correctly created.
     * </p>
     */
    public void testCtor_AccuracyCalculatorImpl() {
        assertNotNull("Instance should be correctly created.", instance);
    }

    /**
     * <p>
     * Accuracy test for the method <code>configure(ConfigurationObject)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    @SuppressWarnings("unchecked")
    public void testAccuracyCalculatorImpl_configure() throws Exception {
        instance.configure(cfgObject);

        Map map = (Map) TestsHelper.getField(instance, "pointsForAccurateEvaluationType", instance.getClass());
        assertEquals(3, map.size());

        map = (Map) TestsHelper.getField(instance, "pointsForInaccurateEvaluationType", instance.getClass());
        assertEquals(1, map.size());
    }

    /**
     * <p>
     * Tests failure of <code>configure(ConfigurationObject)()</code> method with no pointsForAccurateEvaluationType.
     * <br>
     * <code>StatisticsCalculatorConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testAccuracyCalculatorImpl_configure_No_pointsForAccurateEvaluationType() throws Exception {
        try {
            ConfigurationObject config = TestsHelper.loadConfig("configError.xml").getChild(
                "pointsForAccurateEvaluationTypeError").getChild("noPoints");
            instance.configure(config);

            fail("StatisticsCalculatorConfigurationException is expected.");
        } catch (StatisticsCalculatorConfigurationException e) {
            // Good
            assertEquals("'points' not found, accurateFound = false, inaccurateFound = false", e.getMessage());
        }
    }

    /**
     * <p>
     * Tests failure of <code>configure(ConfigurationObject)()</code> method with invalid
     * pointsForAccurateEvaluationType. <br>
     * <code>StatisticsCalculatorConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testAccuracyCalculatorImpl_configure_Invalid_pointsForAccurateEvaluationType() throws Exception {
        try {
            ConfigurationObject config = TestsHelper.loadConfig("configError.xml").getChild(
                "pointsForAccurateEvaluationTypeError").getChild("invalidPoints");
            instance.configure(config);

            fail("StatisticsCalculatorConfigurationException is expected.");
        } catch (StatisticsCalculatorConfigurationException e) {
            // Good
            assertEquals("Error during configuration of AccuracyCalculatorImpl class.", e.getMessage());
        }
    }

    /**
     * <p>
     * Tests failure of <code>configure(ConfigurationObject)()</code> method with no
     * pointsForInAccurateEvaluationType. <br>
     * <code>StatisticsCalculatorConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testAccuracyCalculatorImpl_configure_No_pointsForInAccurateEvaluationType() throws Exception {
        try {
            ConfigurationObject config = TestsHelper.loadConfig("configError.xml").getChild(
                "pointsForInAccurateEvaluationTypeError").getChild("noPoints");
            instance.configure(config);

            fail("StatisticsCalculatorConfigurationException is expected.");
        } catch (StatisticsCalculatorConfigurationException e) {
            // Good
            assertEquals("'points' not found, accurateFound = true, inaccurateFound = false", e.getMessage());
        }
    }

    /**
     * <p>
     * Tests failure of <code>configure(ConfigurationObject)()</code> method with invalid
     * pointsForInAccurateEvaluationType. <br>
     * <code>StatisticsCalculatorConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testAccuracyCalculatorImpl_configure_Invalid_pointsForInAccurateEvaluationType() throws Exception {
        try {
            ConfigurationObject config = TestsHelper.loadConfig("configError.xml").getChild(
                "pointsForInAccurateEvaluationTypeError").getChild("invalidPoints");
            instance.configure(config);

            fail("StatisticsCalculatorConfigurationException is expected.");
        } catch (StatisticsCalculatorConfigurationException e) {
            // Good
            assertEquals("Error during configuration of AccuracyCalculatorImpl class.", e.getMessage());
        }
    }

    /**
     * <p>
     * Accuracy test for the method <code>calculateAccuracy(Review[][])</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testAccuracyCalculatorImpl_calculateAccuracy_1() throws Exception {
        instance.configure(cfgObject);

        // Set comment
        Review[][] reviews = new Review[2][3];
        for (int nReview = 0; nReview < 2; nReview++) {
            for (int nSubmission = 0; nSubmission < 3; nSubmission++) {
                reviews[nReview][nSubmission] = createReview(nReview, nSubmission);
            }
        }

        double[] result = instance.calculateAccuracy(reviews);

        assertEquals(32.0 / 62.0, result[0]);
        assertEquals(46.0 / 86.0, result[1]);
    }

    /**
     * <p>
     * Accuracy test for the method <code>calculateAccuracy(Review[][])</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testAccuracyCalculatorImpl_calculateAccuracy_2() throws Exception {
        instance.configure(cfgObject);

        // Set comment
        Review[][] reviews = new Review[1][2];

        Review review = new Review();

        // 1st submission
        Comment[] comment = new Comment[] {new Comment()};
        EvaluationType evaluationType = new EvaluationType();
        evaluationType.setId(3L);
        comment[0].setEvaluationType(evaluationType);

        review.addComments(comment);
        reviews[0][0] = review;

        review = new Review();

        // 2nd submission
        comment = new Comment[] {new Comment(), new Comment()};
        evaluationType = new EvaluationType();
        evaluationType.setId(6L);
        comment[0].setEvaluationType(evaluationType);
        evaluationType = new EvaluationType();
        evaluationType.setId(6L);
        comment[1].setEvaluationType(evaluationType);

        review.addComments(comment);
        reviews[0][1] = review;

        double[] result = instance.calculateAccuracy(reviews);

        assertEquals(0.1, result[0]);
    }

    /**
     * <p>
     * Accuracy test for the method <code>calculateAccuracy(Review[][])</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testAccuracyCalculatorImpl_calculateAccuracy_3() throws Exception {
        instance.configure(cfgObject);

        // Set comment
        Review[][] reviews = new Review[1][1];
        reviews[0][0] = new Review();

        double[] result = instance.calculateAccuracy(reviews);

        assertEquals(1.0, result[0]);
    }

    /**
     * Create review.
     *
     * @param nSubmission the number of submission
     *
     * @return the created review with comments
     */
    private Review createReview(int nReview, int nSubmission) {
        // Map of comments
        int[][][] values = new int[][][] { { {1, 2, 4, 2}, {0, 2, 1, 1}, {0, 1, 2, 0}},
            { {1, 1, 1, 3}, {1, 2, 4, 1}, {1, 0, 2, 0}}};

        Review review = new Review();
        for (int nRow = 0; nRow < 4; nRow++) {
            List<Comment> list = new ArrayList<Comment>();
            for (int c = 0; c < values[nReview][nSubmission][nRow]; c++) {
                Comment comment = new Comment();
                EvaluationType evaluationType = new EvaluationType();
                if (nRow == 3) {
                    evaluationType.setId(6);
                } else {
                    evaluationType.setId(nRow + 1);
                }
                comment.setEvaluationType(evaluationType);

                list.add(comment);
            }
            review.addComments(list.toArray(new Comment[list.size()]));
        }
        return review;
    }

    /**
     * <p>
     * Tests failure of <code>calculateAccuracy(Review[][])</code> method with reviews is <code>null</code>.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception for JUnit.
     */
    public void testAccuracyCalculatorImpl_calculateAccuracy_Null() throws Exception {
        try {
            instance.configure(cfgObject);

            instance.calculateAccuracy(null);

            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // Good
            assertTrue(e.getMessage().contains("reviews"));
        }
    }

    /**
     * <p>
     * Tests failure of <code>calculateAccuracy(Review[][])</code> method with pointsForAccurateEvaluationType is
     * <code>null</code>.<br>
     * <code>IllegalStateException</code> is expected.
     * </p>
     *
     * @throws Exception for JUnit.
     */
    public void testAccuracyCalculatorImpl_pointsForAccurateEvaluationType_Null() throws Exception {
        try {
            Review[][] reviews = new Review[1][1];
            reviews[0][0] = new Review();

            instance.calculateAccuracy(reviews);

            fail("IllegalStateException is expected.");
        } catch (IllegalStateException e) {
            // Good
            assertTrue(e.getMessage().contains("pointsForAccurateEvaluationType"));
        }
    }
}
