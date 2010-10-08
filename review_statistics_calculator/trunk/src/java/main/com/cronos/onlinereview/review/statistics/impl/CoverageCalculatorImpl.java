/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.review.statistics.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cronos.onlinereview.review.statistics.CoverageCalculationException;
import com.cronos.onlinereview.review.statistics.CoverageCalculator;
import com.cronos.onlinereview.review.statistics.StatisticsCalculatorConfigurationException;
import com.topcoder.configuration.ConfigurationAccessException;
import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.management.review.data.Comment;
import com.topcoder.management.review.data.EvaluationType;
import com.topcoder.management.review.data.Review;
import com.topcoder.util.log.Log;

/**
 * <p>
 * This class is an implementation of CoverageCalculator that uses configurable "evaluationType-points" mapping and
 * counts points for each review comment. The calculated coefficient equals to (sum of points for specific reviewer) /
 * (sum of points for all reviewers).
 * </p>
 *
 * <p>
 * Additionally this calculator allows setting the minimum limit for the calculated coefficient value. This class uses
 * Logging Wrapper logger to perform logging of errors and debug information.
 * </p>
 *
 * <p>
 * <b>Thread Safety</b>: This class is immutable and thread safe assuming that configure() method will be called just
 * once right after instantiation, before calling calculateCoverage() method. The Log instance used by this class is
 * thread safe.
 * </p>
 *
 * @author saarixx, TCSDEVELOPER
 * @version 1.0
 */
public class CoverageCalculatorImpl extends BaseCalculator implements CoverageCalculator {
    /**
     * Class name.
     */
    private static final String CLASS_NAME = CoverageCalculatorImpl.class.getName();

    /**
     * Configuration parameter name prefix.
     */
    private static final String PARAM_NAME = "pointsForEvaluationType";

    /**
     * <p>
     * The mapping from comment evaluation type ID to the number of points (weight) associated with such comments.
     * It's assumed that these points are proportional to the importance of the comment; negative points correspond to
     * invalid (mistaken) comments.
     * </p>
     *
     * <p>
     * Is initialized in configure() method and never changed after that. Cannot be null, cannot contain null/not
     * positive key or null value after initialization. Is used in calculateCoverage().
     * </p>
     */
    private Map<Long, Double> pointsForEvaluationType;

    /**
     * Creates an instance of CoverageCalculatorImpl.
     */
    public CoverageCalculatorImpl() {
        // does nothing
    }

    /**
     * Configures this instance with use of the given configuration object.
     *
     * <pre>
     * Configuration example:
     * &lt;Property name="coverageCalculatorConfig"&gt;
     *    &lt;Property name="loggerName"&gt;
     *       &lt;Value&gt;myLogger&lt;/Value&gt;
     *    &lt;/Property&gt;
     *    &lt;Property name="minimumCoefficient"&gt;
     *       &lt;Value&gt;0.1&lt;/Value&gt;
     *    &lt;/Property&gt;
     *    &lt;Property name="pointsForEvaluationType1"&gt;
     *       &lt;Value&gt;10&lt;/Value&gt;
     *    &lt;/Property&gt;
     *    &lt;Property name="pointsForEvaluationType2"&gt;
     *       &lt;Value&gt;3&lt;/Value&gt;
     *    &lt;/Property&gt;
     *    &lt;Property name="pointsForEvaluationType3"&gt;
     *       &lt;Value&gt;1&lt;/Value&gt;
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

        pointsForEvaluationType = new HashMap<Long, Double>();
        try {
            String[] configParamNames = config.getAllPropertyKeys();
            for (String configParamName : configParamNames) {
                // Check if property is of Evaluation Type
                if (configParamName.startsWith(PARAM_NAME)) {
                    // Get the id of the evaluation type property
                    long evaluationTypeId = Long.parseLong(configParamName.substring(PARAM_NAME.length()));
                    Helper.checkNegative(evaluationTypeId, "evaluationTypeId");

                    // Parse the value of the property
                    double points = Double.parseDouble((String) config.getPropertyValue(configParamName));
                    Helper.checkNegative(points, "points");

                    pointsForEvaluationType.put(evaluationTypeId, points);
                }
            }

            // Check if at least one evaluation type was found
            if (pointsForEvaluationType.isEmpty()) {
                throw new StatisticsCalculatorConfigurationException("'points' not found.");
            }
        } catch (ClassCastException e) {
            throw new StatisticsCalculatorConfigurationException(
                "Error during configuration of CoverageCalculatorImpl class.", e);
        } catch (IllegalArgumentException e) {
            throw new StatisticsCalculatorConfigurationException(
                "Error during configuration of CoverageCalculatorImpl class.", e);
        } catch (ConfigurationAccessException e) {
            throw new StatisticsCalculatorConfigurationException(
                "Error during configuration of CoverageCalculatorImpl class.", e);
        }
    }

    /**
     * Calculates the coverage coefficients for all secondary reviewers of a single contest.
     *
     * @param reviews the reviews with evaluation details (outer array - reviews for each reviewer, inner array -
     *            reviews of one reviewer for all submissions)
     *
     * @return the calculated coverage coefficients (not null; number of elements is equal to reviews.length; i-th
     *         element corresponds to i-th reviewer from the input; each coefficient is in the range [0 .. 1])
     *
     * @throws IllegalArgumentException if reviews is null or contains null, lengths of all inner arrays are not
     *             equal, inner array contains null
     * @throws IllegalStateException if calculator was not configured properly via configure() method
     *             (pointsForEvaluationType is null)
     * @throws CoverageCalculationException if some other error occurred
     */
    public double[] calculateCoverage(Review[][] reviews) throws CoverageCalculationException {
        Date start = new Date();
        String signature = CLASS_NAME + ".calculateCoverage(Review[][] reviews)";
        Log log = getLog();

        Helper.checkReview(log, signature, reviews);

        // Log entrance
        Helper.logEntrance(log, signature, new String[] {"reviews"}, new Object[] {reviews});

        Helper.checkState(log, signature, pointsForEvaluationType, "pointsForEvaluationType");

        int reviewersNum = reviews.length;
        int submissionsNum = reviews[0].length;

        double[] rawScores = new double[reviewersNum]; // raw scores for each reviewer
        double rawScoreSum = 0;
        for (int i = 0; i < reviewersNum; i++) {
            double rawScore = 0;

            for (int j = 0; j < submissionsNum; j++) {
                Review review = reviews[i][j];

                // Create list for all comments for this review (it will include comments of all review items)
                List<Comment> allComments = getComments(review);

                for (Comment comment : allComments) {
                    // Get review evaluation type of the commentGet the number of accurate points associated with this
                    // evaluation type
                    EvaluationType evaluationType = comment.getEvaluationType();

                    // Get evaluation type ID
                    long evaluationTypeId = evaluationType.getId();

                    if (pointsForEvaluationType.containsKey(evaluationTypeId)) {
                        // Get points number associated with this evaluation type
                        rawScore += pointsForEvaluationType.get(evaluationTypeId);
                    }
                }
            }

            if (rawScore < 0) {
                rawScore = 0;
            }

            rawScores[i] = rawScore;
            rawScoreSum += rawScore;
        }

        double[] result = new double[reviewersNum];
        for (int i = 0; i < reviewersNum; i++) {
            double coverage = (rawScoreSum == 0) ? 1 : rawScores[i] / rawScoreSum;

            double minimumCoverage = getMinimumCoefficient();
            coverage = (coverage < minimumCoverage) ? minimumCoverage : coverage;

            result[i] = coverage;
        }

        Helper.logExit(log, signature, new Object[] {result}, new Date(start.getTime()));

        return result;
    }
}
