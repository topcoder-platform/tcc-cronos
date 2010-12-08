/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.review.statistics.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cronos.onlinereview.review.statistics.AccuracyCalculationException;
import com.cronos.onlinereview.review.statistics.AccuracyCalculator;
import com.cronos.onlinereview.review.statistics.StatisticsCalculatorConfigurationException;
import com.topcoder.configuration.ConfigurationAccessException;
import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.management.review.data.Comment;
import com.topcoder.management.review.data.EvaluationType;
import com.topcoder.management.review.data.Review;
import com.topcoder.util.log.Log;

/**
 * <p>
 * This class is an implementation of AccuracyCalculator that uses two configurable "evaluationType-accuratePoints"
 * and "evaluationType-inaccuratePoints" mappings and counts points for each review comment. The calculation is
 * performed for each reviewer separately (score card of other reviewers are not taken into account). The calculated
 * coefficient equals to (sum of accurate points for reviewer) / (sum of accurate and inaccurate points for reviewer).
 * </p>
 *
 * <p>
 * Additionally this calculator allows setting the minimum limit for the calculated coefficient value. This class uses
 * Logging Wrapper logger to perform logging of errors and debug information.
 * </p>
 *
 * <p>
 * <b>Thread Safety</b>: This class is immutable and thread safe assuming that configure() method will be called just
 * once right after instantiation, before calling calculateAccuracy() method. The Log instance used by this class is
 * thread safe.
 * </p>
 *
 * @author saarixx, TCSDEVELOPER
 * @version 1.0
 */
public class AccuracyCalculatorImpl extends BaseCalculator implements AccuracyCalculator {
    /**
     * Evaluation type used to retrieve points.
     */
    private static final String[] EVALUATION_TYPE = new String[] {"pointsForAccurateEvaluationType",
        "pointsForInaccurateEvaluationType"};

    /**
     * Class name for logging.
     */
    private static final String CLASS_NAME = AccuracyCalculatorImpl.class.getName();

    /**
     * <p>
     * The mapping from accurate comment evaluation type ID to the number of points (weight) associated with such
     * comments. Accurate comments are all valid (not erroneous) comments.
     * </p>
     *
     * <p>
     * Is initialized in configure() method and never changed after that. Cannot be null, cannot contain null/not
     * positive key or null/negative value after initialization. Is used in calculateAccuracy().
     * </p>
     */
    private Map<Long, Double> pointsForAccurateEvaluationType;

    /**
     * <p>
     * The mapping from inaccurate comment evaluation type ID to the number of points (weight) associated with such
     * comments. Inaccurate comments are all incorrect (erroneous) comments.
     * </p>
     *
     * <p>
     * Is initialized in configure() method and never changed after that. Cannot be null, cannot contain null/not
     * positive key or null/negative value after initialization. Is used in calculateAccuracy().
     * </p>
     */
    private Map<Long, Double> pointsForInaccurateEvaluationType;

    /**
     * Creates an instance of AccuracyCalculatorImpl.
     */
    public AccuracyCalculatorImpl() {
        // does nothing
    }

    /**
     * Configures this instance with use of the given configuration object.
     *
     * <pre>
     * Configuration example:
     * &lt;Property name="accuracyCalculatorConfig"&gt;
     *    &lt;Property name="loggerName"&gt;
     *       &lt;Value&gt;myLogger&lt;/Value&gt;
     *    &lt;/Property&gt;
     *    &lt;Property name="minimumCoefficient"&gt;
     *       &lt;Value&gt;0.1&lt;/Value&gt;
     *    &lt;/Property&gt;
     *    &lt;Property name="pointsForAccurateEvaluationType1"&gt;
     *       &lt;Value&gt;10&lt;/Value&gt;
     *    &lt;/Property&gt;
     *    &lt;Property name="pointsForAccurateEvaluationType2"&gt;
     *       &lt;Value&gt;3&lt;/Value&gt;
     *    &lt;/Property&gt;
     *    &lt;Property name="pointsForAccurateEvaluationType3"&gt;
     *       &lt;Value&gt;1&lt;/Value&gt;
     *    &lt;/Property&gt;
     *    &lt;Property name="pointsForInaccurateEvaluationType6"&gt;
     *       &lt;Value&gt;10&lt;/Value&gt;
     *    &lt;/Property&gt;
     * &lt;/Property&gt;
     * </pre>
     *
     * @param config the configuration object
     *
     * @throws IllegalArgumentException if the configuration object is null
     * @throws StatisticsCalculatorConfigurationException if some error occurred when initializing an instance using
     *             the given configuration
     */
    public void configure(ConfigurationObject config) {
        super.configure(config);

        pointsForAccurateEvaluationType = new HashMap<Long, Double>();
        pointsForInaccurateEvaluationType = new HashMap<Long, Double>();
        try {
            String[] configParamNames = config.getAllPropertyKeys();
            for (String configParamName : configParamNames) {
                for (int i = 0; i < EVALUATION_TYPE.length; i++) {
                    // Check if property is AccurateEvaluationType or InaccurateEvaluationType
                    if (configParamName.startsWith(EVALUATION_TYPE[i])) {
                        // Get the id of the evaluation type property
                        long evaluationTypeId = Long
                            .parseLong(configParamName.substring(EVALUATION_TYPE[i].length()));
                        Helper.checkNegative(evaluationTypeId, "evaluationTypeId");

                        // Parse the value of the property
                        double points = Double.parseDouble((String) config.getPropertyValue(configParamName));
                        Helper.checkNegative(points, "points");

                        if (i == 0) {
                            pointsForAccurateEvaluationType.put(evaluationTypeId, points);
                        } else {
                            pointsForInaccurateEvaluationType.put(evaluationTypeId, points);
                        }
                    }
                }
            }

            // Check if at least one for each of evaluation type was found
            if (pointsForAccurateEvaluationType.isEmpty() || pointsForInaccurateEvaluationType.isEmpty()) {
                throw new StatisticsCalculatorConfigurationException("'points' not found, accurateFound = "
                    + !pointsForAccurateEvaluationType.isEmpty() + ", inaccurateFound = "
                    + !pointsForInaccurateEvaluationType.isEmpty());
            }
        } catch (ClassCastException e) {
            throw new StatisticsCalculatorConfigurationException(
                "Error during configuration of AccuracyCalculatorImpl class.", e);
        } catch (IllegalArgumentException e) {
            throw new StatisticsCalculatorConfigurationException(
                "Error during configuration of AccuracyCalculatorImpl class.", e);
        } catch (ConfigurationAccessException e) {
            throw new StatisticsCalculatorConfigurationException(
                "Error during configuration of AccuracyCalculatorImpl class.", e);
        }
    }

    /**
     * Calculates the accuracy coefficients for all secondary reviewers of a single contest.
     *
     * @param reviews the reviews with evaluation details (outer array - reviews for each reviewer, inner array -
     *            reviews of one reviewer for all submissions)
     *
     * @return the calculated accuracy coefficients (not null; number of elements is equal to reviews.length; i-th
     *         element corresponds to i-th reviewer from the input; each coefficient is in the range [0 .. 1])
     *
     * @throws IllegalArgumentException if reviews is null or contains null, lengths of all inner arrays are not
     *             equal, inner array contains null
     * @throws IllegalStateException if calculator was not configured properly via configure() method
     *             (pointsForAccurateEvaluationType is null)
     * @throws AccuracyCalculationException if some other error occurred
     */
    public double[] calculateAccuracy(Review[][] reviews) throws AccuracyCalculationException {
        Date start = new Date();
        String signature = CLASS_NAME + ".calculateAccuracy(Review[][] reviews)";
        Log log = getLog();

        Helper.checkReview(log, signature, reviews);

        // Log entrance
        Helper.logEntrance(log, signature, new String[] {"reviews"}, new Object[] {reviews});

        Helper.checkState(log, signature, pointsForAccurateEvaluationType, "pointsForAccurateEvaluationType");

        int reviewersNum = reviews.length;
        int submissionsNum = reviews[0].length;

        double[] result = new double[reviewersNum];
        for (int i = 0; i < reviewersNum; i++) {
            double accurateScore = 0;
            double inaccurateScore = 0;

            for (int j = 0; j < submissionsNum; j++) {
                Review review = reviews[i][j];

                // Create list for all comments for this review (it will include comments of all review items)
                List<Comment> allComments = getComments(review);

                for (Comment comment : allComments) {
                    // Get review evaluation type of the comment
                    EvaluationType evaluationType = comment.getEvaluationType();

                    // Get evaluation id
                    long evaluationTypeId = evaluationType.getId();
                    if (pointsForAccurateEvaluationType.containsKey(evaluationTypeId)) {
                        // Get the number of accurate points associated with this evaluation type
                        double points = pointsForAccurateEvaluationType.get(evaluationTypeId);
                        accurateScore += points;
                    } else if (pointsForInaccurateEvaluationType.containsKey(evaluationTypeId)) {
                        // Get the number of inaccurate points associated with this evaluation type
                        double points = pointsForInaccurateEvaluationType.get(evaluationTypeId);
                        inaccurateScore += points;
                    }
                }
            }

            double accuracy = (accurateScore == 0 && inaccurateScore == 0) ? 1 : accurateScore
                / (accurateScore + inaccurateScore);

            double minimumAccuracy = getMinimumCoefficient();
            accuracy = (accuracy < minimumAccuracy) ? minimumAccuracy : accuracy;

            result[i] = accuracy;
        }

        Helper.logExit(log, signature, new Object[] {result}, new Date(start.getTime()));

        return result;
    }
}
