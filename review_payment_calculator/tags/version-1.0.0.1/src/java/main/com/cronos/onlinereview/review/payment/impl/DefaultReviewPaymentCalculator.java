/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.review.payment.impl;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.cronos.onlinereview.review.payment.InvalidReviewersStatisticsException;
import com.cronos.onlinereview.review.payment.ReviewPaymentCalculator;
import com.cronos.onlinereview.review.payment.ReviewPaymentCalculatorConfigurationException;
import com.cronos.onlinereview.review.payment.ReviewerPayment;
import com.topcoder.configuration.ConfigurationAccessException;
import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.management.resource.ReviewerStatistics;
import com.topcoder.management.resource.StatisticsType;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogFactory;

/**
 * <p>
 * This class is an implementation of ReviewPaymentCalculator that uses the following logic when calculating the
 * payment amounts:
 * <ol>
 * <li>payment amount for the primary reviewer is calculated as (base primary reviewer payment * timeline reliability
 * coefficient of this reviewer); </li>
 * <li> secondary reviewer with the highest total evaluation coefficient receives a bonus equal to the configurable
 * part of the secondary payment pool (if secondary reviewers have equal total evaluation coefficients, the bonus is
 * distributed equally); </li>
 * <li> the rest of the secondary payment pool is distributed among secondary reviewers proportionally to their total
 * evaluation coefficients.</li>
 * </ol>
 * </p>
 *
 * <p>
 * <em>Sample Configuration: </em>
 * <pre>
 * &lt;?xml version=&quot;1.0&quot;?&gt;
 * &lt;CMConfig&gt;
 *   &lt;Config name=&quot;com.cronos.onlinereview.review.payment.impl.DefaultReviewPaymentCalculator&quot;&gt;
 *     &lt;Property name=&quot;loggerName&quot;&gt;
 *       &lt;Value&gt;myLogger&lt;/Value&gt;
 *     &lt;/Property&gt;
 *     &lt;Property name=&quot;bonusPercentage&quot;&gt;
 *       &lt;Value&gt;0.2&lt;/Value&gt;
 *     &lt;/Property&gt;
 *   &lt;/Config&gt;
 * &lt;/CMConfig&gt;
 * </pre>
 *
 * </p>
 *
 * <p>
 * <em>Sample Code: </em>
 * <pre>
 * // Create an instance of reviewPaymentCalculator
 * ReviewPaymentCalculator reviewPaymentCalculator = new DefaultReviewPaymentCalculator();
 *
 * // Get configuration for DefaultReviewPaymentCalculator
 * ConfigurationObject config = TestsHelper.getConfig();
 *
 * // Configure review payment calculator
 * reviewPaymentCalculator.configure(config);
 *
 * // Prepare review statistics data
 * ReviewerStatistics[] statistics = new ReviewerStatistics[3];
 * ReviewerStatistics primaryReviewerStats =
 *     TestsHelper.getReviewerStatistics(1, 1001, 0.85, -1.0, -1.0, -1.0, StatisticsType.HISTORY);
 * statistics[0] = primaryReviewerStats;
 * ReviewerStatistics firstSecondaryReviewerStats =
 *     TestsHelper.getReviewerStatistics(1, 1002, 1, 0.61, 0.85, -1.0, StatisticsType.HISTORY);
 * statistics[1] = firstSecondaryReviewerStats;
 * ReviewerStatistics secondSecondaryReviewerStats =
 *     TestsHelper.getReviewerStatistics(1, 1003, 0.74, 0.39, 1, -1.0, StatisticsType.HISTORY);
 * statistics[2] = secondSecondaryReviewerStats;
 *
 * // Compute review payment for project with ID=1
 * // Use $120 as a base primary payment
 * // Use $200 as a secondary payment pool
 * ReviewerPayment[] reviewerPayments = reviewPaymentCalculator.computeReviewPayment(1, 120, 200, statistics);
 * // reviewerPayments.length must be 3
 * // reviewerPayments[0].getProjectId() must be 1
 * // reviewerPayments[0].getReviewerId() must be 1001
 * // reviewerPayments[0].getPaymentAmount() must be 102
 * // reviewerPayments[1].getProjectId() must be 1
 * // reviewerPayments[1].getReviewerId() must be 1002
 * // reviewerPayments[1].getPaymentAmount() must be 142.78775...
 * // reviewerPayments[2].getProjectId() must be 1
 * // reviewerPayments[2].getReviewerId() must be 1003
 * // reviewerPayments[2].getPaymentAmount() must be 57.21224...
 * </pre>
 *
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> This class is immutable and thread safe assuming that configure() method will be
 * called just once right after instantiation, before calling computeReviewPayment() method. The Log instance used by
 * this class is thread safe.
 * </p>
 *
 * @author saarixx, sparemax
 * @version 1.0
 */
public class DefaultReviewPaymentCalculator implements ReviewPaymentCalculator {
    /**
     * <p>
     * Represents the class name.
     * </p>
     */
    private static final String CLASS_NAME = DefaultReviewPaymentCalculator.class.getName();

    /**
     * <p>
     * Represents the comma ', '.
     * </p>
     */
    private static final String COMMA = ", ";

    /**
     * <p>
     * Represents the left brace ' {'.
     * </p>
     */
    private static final String L_BRACE = " {";

    /**
     * <p>
     * Represents the right brace '}'.
     * </p>
     */
    private static final String R_BRACE = "}";

    /**
     * <p>
     * Represents the string 'null'.
     * </p>
     */
    private static final String NULL = "null";

    /**
     * <p>
     * Represents the entrance message.
     * </p>
     *
     * <p>
     * Arguments:
     * <ol>
     * <li>the method signature.</li>
     * </ol>
     * </p>
     */
    private static final String MESSAGE_ENTRANCE = "Entering method [%1$s].";

    /**
     * <p>
     * Represents the exit message.
     * </p>
     *
     * <p>
     * Arguments:
     * <ol>
     * <li>the method signature.</li>
     * <li>the call duration time.</li>
     * </ol>
     * </p>
     */
    private static final String MESSAGE_EXIT = "Exiting method [%1$s], time spent in the method: %2$s milliseconds.";

    /**
     * <p>
     * Represents the error message.
     * </p>
     *
     * <p>
     * Arguments:
     * <ol>
     * <li>the method signature.</li>
     * <li>the error message.</li>
     * </ol>
     * </p>
     */
    private static final String MESSAGE_ERROR = "Error in method [%1$s], details: %2$s";

    /**
     * <p>
     * Represents the epsilon.
     * </p>
     */
    private static final double COEFFICIENT_EPS = 0.0001;

    /**
     * <p>
     * Represents the default value for property key 'bonusPercentage'.
     * </p>
     */
    private static final double DEFAULT_BONUS_PERCENTAGE = 0.2;

    /**
     * <p>
     * Represents the property key 'loggerName'.
     * </p>
     */
    private static final String KEY_LOGGER_NAME = "loggerName";

    /**
     * <p>
     * Represents the property key 'bonusPercentage'.
     * </p>
     */
    private static final String KEY_BONUS_PERCENTAGE = "bonusPercentage";

    /**
     * <p>
     * The percentage of the secondary payment pool that is used for bonus awarded to a secondary reviewer with the
     * highest total evaluation coefficient (must be in the range [0 .. 1]).
     * </p>
     *
     * <p>
     * Is initialized in configure() method and never changed after that. Must be in the range [0 .. 1] after
     * initialization. Is used in computeReviewPayment().
     * </p>
     */
    private double bonusPercentage;

    /**
     * <p>
     * The logger used by this class for logging errors and debug information.
     * </p>
     *
     * <p>
     * Is initialized in configure() method and never changed after that. If is null after initialization, logging is
     * not performed. Is used in computeReviewPayment().
     * </p>
     */
    private Log log;

    /**
     * <p>
     * Creates an instance of DefaultReviewPaymentCalculator.
     * </p>
     */
    public DefaultReviewPaymentCalculator() {
        // Empty
    }

    /**
     * <p>
     * Configures this instance with use of the given configuration object.
     * </p>
     *
     * @param config
     *            the configuration object.
     *
     * @throws IllegalArgumentException
     *             if config is <code>null</code>.
     * @throws ReviewPaymentCalculatorConfigurationException
     *             if some error occurred when initializing an instance using the given configuration.
     */
    public void configure(ConfigurationObject config) {
        checkNull(config, "config");

        // Get logger name from the configuration
        String loggerName = getOptionalProperty(config, KEY_LOGGER_NAME, null);
        log = loggerName == null ? null : LogFactory.getLog(loggerName);

        // Get bonus percentage string from configuration
        String bonusPercentageStr = getOptionalProperty(config, KEY_BONUS_PERCENTAGE, null);
        try {
            if (bonusPercentageStr != null) {
                bonusPercentage = Double.parseDouble(bonusPercentageStr);

                // Check range
                if ((bonusPercentage < 0) || (bonusPercentage > 1)) {
                    throw new ReviewPaymentCalculatorConfigurationException("The value '" + bonusPercentageStr
                        + "' of property 'bonusPercentage' should be in range [0, 1].");
                }
            } else {
                // Default value
                bonusPercentage = DEFAULT_BONUS_PERCENTAGE;
            }

        } catch (NumberFormatException e) {
            throw new ReviewPaymentCalculatorConfigurationException("The value '" + bonusPercentageStr
                + "' of property 'bonusPercentage' cannot be parsed as a double.", e);
        }
    }

    /**
     * <p>
     * Computes the review payments for specific project using the given calculated reviewer statistics.
     * </p>
     *
     *
     * @param primaryPayment
     *            the primary reviewer's payment amount.
     * @param projectId
     *            the ID of the project
     * @param secondaryPaymentPool
     *            the payment pool for secondary reviewers.
     * @param statistics
     *            the reviewer statistics for all reviewers.
     *
     * @return the calculated reviewer payments (not <code>null</code>, doesn't contain <code>null</code>;
     *         number of elements is equal to statistics.length).
     *
     * @throws IllegalArgumentException
     *             if projectId, primaryPayment or secondaryPaymentPool &lt;= <code>0</code>; if statistics is
     *             <code>null</code>, contains <code>null</code> or statistics.length &lt;= <code>2</code>.
     * @throws InvalidReviewersStatisticsException
     *             if the provided reviewer statistics is invalid (e.g. number of statistics for primary reviewer is
     *             not equal to 1, coefficient values are out of the expected range).
     */
    public ReviewerPayment[] computeReviewPayment(long projectId, double primaryPayment, double secondaryPaymentPool,
        ReviewerStatistics[] statistics) throws InvalidReviewersStatisticsException {
        Date enterTimestamp = new Date();
        String signature = getSignature("computeReviewPayment(long projectId, double primaryPayment,"
            + " double secondaryPaymentPool, ReviewerStatistics[] statistics)");

        // Log method entry
        logEntrance(signature,
            new String[] {"projectId", "primaryPayment", "secondaryPaymentPool", "statistics"},
            new Object[] {projectId, primaryPayment, secondaryPaymentPool, statistics});

        try {
            checkPositive(projectId, "projectId");
            checkPositive(primaryPayment, "primaryPayment");
            checkPositive(secondaryPaymentPool, "secondaryPaymentPool");
            checkNull(statistics, "statistics");
            if (statistics.length <= 2) {
                throw new IllegalArgumentException("'statistics.length' should contain at least 3 elements.");
            }
            for (ReviewerStatistics element : statistics) {
                checkNull(element, "element of statistics");
            }

            // Create a list for reviewer payments
            List<ReviewerPayment> reviewerPayments = new ArrayList<ReviewerPayment>();
            // Create a list for secondary reviewers statistics
            List<ReviewerStatistics> secondaryReviewersStatistics = new ArrayList<ReviewerStatistics>();
            for (ReviewerStatistics reviewerStatistics : statistics) {
                if (reviewerStatistics.getStatisticsType() != StatisticsType.HISTORY) {
                    throw new InvalidReviewersStatisticsException("The statistics type must be 'HISTORY'.");
                }
                // Check accuracy and coverage coefficients from the statistics
                if ((reviewerStatistics.getAccuracy() == -1) && (reviewerStatistics.getCoverage() == -1)) {
                    // Check if it's the first found primary reviewer statistics
                    if (!reviewerPayments.isEmpty()) {
                        throw new InvalidReviewersStatisticsException(
                            "More than one primary reviewer statistics found.");
                    }

                    // Get timeline reliability from the statistics
                    double timelineReliability = reviewerStatistics.getTimelineReliability();
                    checkRange(timelineReliability, "The timeline reliability must be in range [0, 1].");

                    // Create reviewer payment instance for the primary reviewer
                    ReviewerPayment primaryReviewerPayment = getReviewerPayment(projectId,
                        reviewerStatistics.getReviewerId(), primaryPayment * timelineReliability);

                    // Add reviewer payment to the list
                    reviewerPayments.add(primaryReviewerPayment);
                } else {
                    // Add secondary reviewer statistics to the list
                    secondaryReviewersStatistics.add(reviewerStatistics);
                }
            }
            // Check if no primary reviewer statistics found
            if (reviewerPayments.isEmpty()) {
                throw new InvalidReviewersStatisticsException("No primary reviewer statistics found.");
            }

            // Computes the review payments for secondary reviewers
            computeReviewPayment(projectId, secondaryPaymentPool, reviewerPayments, secondaryReviewersStatistics);

            // Convert the list to an array
            ReviewerPayment[] result = reviewerPayments.toArray(new ReviewerPayment[reviewerPayments.size()]);

            // Log method exit
            logExit(signature, result, enterTimestamp);

            return result;
        } catch (IllegalArgumentException e) {
            // Log exception
            throw logException(signature, e, "IllegalArgumentException is thrown.");
        } catch (InvalidReviewersStatisticsException e) {
            // Log exception
            throw logCustomException(signature, e,
                "InvalidReviewersStatisticsException is thrown when computing the review payments.");
        }
    }

    /**
     * <p>
     * Computes the review payments for secondary reviewers.
     * </p>
     *
     * @param projectId
     *            the ID of the project
     * @param secondaryPaymentPool
     *            the payment pool for secondary reviewers.
     * @param reviewerPayments
     *            the list to hold the results.
     * @param secondaryReviewersStatistics
     *            the list containing secondary reviewers statistics.
     *
     * @throws InvalidReviewersStatisticsException
     *             if the provided reviewer statistics is invalid.
     */
    private void computeReviewPayment(long projectId, double secondaryPaymentPool,
        List<ReviewerPayment> reviewerPayments, List<ReviewerStatistics> secondaryReviewersStatistics)
        throws InvalidReviewersStatisticsException {
        // Get the number of secondary reviewers
        int secondaryReviewersNum = secondaryReviewersStatistics.size();
        // Will contain total evaluation coefficients of all secondary reviewers
        double[] totalEvaluationCoefficients = new double[secondaryReviewersNum];
        // Sum of total evaluation coefficient
        double totalEvaluationCoefficientSum = 0;
        // Will hold the maximum total evaluation coefficient for all secondary reviewers
        double maximumEvaluationCoefficient = -1;

        for (int i = 0; i < secondaryReviewersNum; i++) {
            // Get statistics for the next secondary reviewer
            ReviewerStatistics reviewerStatistics = secondaryReviewersStatistics.get(i);
            // Get total evaluation coefficient for this reviewer
            double totalEvaluationCoefficient = reviewerStatistics.getTotalEvaluationCoefficient();
            if (totalEvaluationCoefficient == -1) {
                // Not yet calculated

                // Get timeline reliability, coverage value and accuracy value from the statistics
                double timelineReliability = reviewerStatistics.getTimelineReliability();
                double coverage = reviewerStatistics.getCoverage();
                double accuracy = reviewerStatistics.getAccuracy();

                // Calculate the total evaluation coefficient
                totalEvaluationCoefficient =
                    checkRange(timelineReliability, "The timeline reliability must be in range [0, 1].")
                    * checkRange(coverage, "The coverage value must be in range [0, 1].")
                    * checkRange(accuracy, "The accuracy value must be in range [0, 1].");
            } else {
                checkRange(totalEvaluationCoefficient,
                    "The calculated total evaluation coefficient must be in range [0, 1].");
            }

            totalEvaluationCoefficients[i] = totalEvaluationCoefficient;
            if (maximumEvaluationCoefficient < totalEvaluationCoefficient) {
                maximumEvaluationCoefficient = totalEvaluationCoefficient;
            }
            totalEvaluationCoefficientSum += totalEvaluationCoefficient;
        }
        if (totalEvaluationCoefficientSum == 0) {
            throw new InvalidReviewersStatisticsException("Sum of total evaluation coefficient cannot be 0.");
        }
        // Create set for indices of reviewers that have total evaluation coefficient equal to the maximum one
        Set<Integer> bonusReviewerIndices = new HashSet<Integer>();
        for (int i = 0; i < secondaryReviewersNum; i++) {
            if (Math.abs(totalEvaluationCoefficients[i] - maximumEvaluationCoefficient) < COEFFICIENT_EPS) {
                // Add reviewer index to the set
                bonusReviewerIndices.add(i);
            }
        }

        // Calculate bonus payment amount
        double bonusAmount = secondaryPaymentPool * bonusPercentage;
        // Calculate amount of the rest of the secondary payment pool
        double distributedAmount = secondaryPaymentPool - bonusAmount;
        // Divide the bonus by the number of reviewers who receive it (to receive the bonus amount per reviewer)
        double bonusAmountPerReviewer = bonusAmount / bonusReviewerIndices.size();
        for (int i = 0; i < secondaryReviewersNum; i++) {
            // Calculate payment for this secondary reviewer (except bonus)
            double paymentAmount = distributedAmount * totalEvaluationCoefficients[i] / totalEvaluationCoefficientSum;
            // Check if this reviewer receives the bonus payment
            if (bonusReviewerIndices.contains(i)) {
                paymentAmount += bonusAmountPerReviewer;
            }
            // Get reviewer statistics for this secondary reviewer
            ReviewerStatistics reviewerStatistics = secondaryReviewersStatistics.get(i);

            // Create reviewer payment instance for this secondary reviewer
            reviewerPayments.add(getReviewerPayment(projectId, reviewerStatistics.getReviewerId(), paymentAmount));
        }
    }

    /**
     * <p>
     * Checks if the value is a positive number.
     * </p>
     *
     * @param value
     *            the value to check.
     * @param name
     *            the name.
     *
     * @throws IllegalArgumentException
     *             if value &lt;= <code>0</code>.
     */
    private static void checkPositive(long value, String name) {
        if (value <= 0) {
            throw new IllegalArgumentException("'" + name + "' should be a positive number.");
        }
    }

    /**
     * <p>
     * Checks if the value is a positive number.
     * </p>
     *
     * @param value
     *            the value to check.
     * @param name
     *            the name.
     *
     * @throws IllegalArgumentException
     *             if value &lt;= <code>0</code>.
     */
    private static void checkPositive(double value, String name) {
        if (value <= 0) {
            throw new IllegalArgumentException("'" + name + "' should be a positive number.");
        }
    }

    /**
     * <p>
     * Checks if the value in range [0, 1].
     * </p>
     *
     * @param value
     *            the value to check.
     * @param message
     *            the error message.
     *
     * @return the passed in value.
     *
     * @throws InvalidReviewersStatisticsException
     *             if the value is not in range [0, 1].
     */
    private static double checkRange(double value, String message) throws InvalidReviewersStatisticsException {
        if ((value < 0) || (value > 1)) {
            throw new InvalidReviewersStatisticsException(message);
        }

        return value;
    }

    /**
     * <p>
     * Creates a ReviewerPayment instance with given values.
     * </p>
     *
     * @param projectId
     *            the ID of the project.
     * @param reviewerId
     *            the ID of the reviewer.
     * @param paymentAmount
     *            the payment amount.
     *
     * @return the created ReviewerPayment instance
     */
    private static ReviewerPayment getReviewerPayment(long projectId, long reviewerId, double paymentAmount) {
        ReviewerPayment reviewerPayment = new ReviewerPayment();
        reviewerPayment.setProjectId(projectId);
        reviewerPayment.setReviewerId(reviewerId);
        reviewerPayment.setPaymentAmount(paymentAmount);

        return reviewerPayment;
    }

    /**
     * <p>
     * Gets an optional property value from given configuration object.
     * </p>
     *
     * @param config
     *            the given configuration object.
     * @param key
     *            the property key.
     * @param defaultValue
     *            the default value.
     *
     * @return the retrieved property value or the default value if the property is not present.
     *
     * @throws ReviewPaymentCalculatorConfigurationException
     *             if the property value is not a string, an empty string or some error occurred.
     */
    private static String getOptionalProperty(ConfigurationObject config, String key, String defaultValue) {
        try {
            if (!config.containsProperty(key)) {
                // Return the default value
                return defaultValue;
            }

            Object valueObj = config.getPropertyValue(key);

            if (!(valueObj instanceof String)) {
                throw new ReviewPaymentCalculatorConfigurationException("The property '" + key
                    + "' should be a String.");
            }

            String valueStr = (String) valueObj;

            if (valueStr.trim().length() == 0) {
                // The value is empty
                throw new ReviewPaymentCalculatorConfigurationException("The property '" + key
                    + "' can not be empty.");
            }

            return valueStr;
        } catch (ConfigurationAccessException e) {
            throw new ReviewPaymentCalculatorConfigurationException(
                "An error occurred while accessing the configuration.", e);
        }
    }

    /**
     * <p>
     * Validates the value of a variable. The value can not be <code>null</code>.
     * </p>
     *
     * @param value
     *            the value of the variable to be validated.
     * @param name
     *            the name of the variable to be validated.
     *
     * @throws IllegalArgumentException
     *             if the value of the variable is <code>null</code>.
     */
    private static void checkNull(Object value, String name) {
        if (value == null) {
            throw new IllegalArgumentException("'" + name + "' should not be null.");
        }
    }

    /**
     * <p>
     * Converts the array to string.
     * </p>
     *
     * @param <T>
     *            the element type.
     * @param array
     *            the array.
     * @param isReviewerPayment
     *            <code>true</code> for ReviewerPayment array; <code>false</code> for ReviewerStatistics array.
     *
     * @return the converted string.
     */
    private static <T> String toString(T[] array, boolean isReviewerPayment) {
        if (array == null) {
            return NULL;
        }

        StringBuilder sb = new StringBuilder("[");

        boolean first = true;
        for (T element : array) {
            if (!first) {
                // Append a comma
                sb.append(COMMA);
            }
            first = false;
            if (isReviewerPayment) {
                // ReviewerPayment
                sb.append(toString((ReviewerPayment) element));
            } else {
                // ReviewerStatistics
                sb.append(toString((ReviewerStatistics) element));
            }
        }
        sb.append("]");

        return sb.toString();
    }

    /**
     * <p>
     * Converts the ReviewerStatistics object to string.
     * </p>
     *
     * @param obj
     *            the ReviewerStatistics object.
     *
     * @return the converted string.
     */
    private static String toString(ReviewerStatistics obj) {
        if (obj == null) {
            return NULL;
        }

        StringBuilder sb = new StringBuilder();

        sb.append(obj.getClass().getName()).append(L_BRACE)
            // creationUser
            .append("creationUser=").append(obj.getCreationUser()).append(COMMA)
            // creationTimestamp
            .append("creationTimestamp=").append(obj.getCreationTimestamp()).append(COMMA)
            // modificationUser
            .append("modificationUser=").append(obj.getModificationUser()).append(COMMA)
            // modificationTimestamp
            .append("modificationTimestamp=").append(obj.getModificationTimestamp()).append(COMMA)
            // id
            .append("id=").append(obj.getId()).append(COMMA)
            // accuracy
            .append("accuracy=").append(obj.getAccuracy()).append(COMMA)
            // coverage
            .append("coverage=").append(obj.getCoverage()).append(COMMA)
            // timelineReliability
            .append("timelineReliability=").append(obj.getTimelineReliability()).append(COMMA)
            // totalEvaluationCoefficient
            .append("totalEvaluationCoefficient=").append(obj.getTotalEvaluationCoefficient()).append(COMMA)
            // reviewerId
            .append("reviewerId=").append(obj.getReviewerId()).append(COMMA)
            // competitionTypeId
            .append("competitionTypeId=").append(obj.getCompetitionTypeId()).append(COMMA)
            // projectId
            .append("projectId=").append(obj.getProjectId()).append(COMMA)
            // statisticsType
            .append("statisticsType=").append(toString(obj.getStatisticsType())).append(COMMA)
            // eligibilityPoints
            .append("eligibilityPoints=").append(obj.getEligibilityPoints()).append(R_BRACE);

        return sb.toString();
    }

    /**
     * <p>
     * Converts the StatisticsType object to string.
     * </p>
     *
     * @param obj
     *            the StatisticsType object.
     *
     * @return the converted string.
     */
    private static String toString(StatisticsType obj) {
        if (obj == StatisticsType.AVERGAE) {
            return "AVERAGE";
        } else if (obj == StatisticsType.HISTORY) {
            return "HISTORY";
        }

        return NULL;
    }

    /**
     * <p>
     * Converts the ReviewerPayment object to string.
     * </p>
     *
     * @param obj
     *            the ReviewerPayment object (not <code>null</code>).
     *
     * @return the converted string.
     */
    private static String toString(ReviewerPayment obj) {
        StringBuilder sb = new StringBuilder();

        sb.append(obj.getClass().getName()).append(L_BRACE)
            // reviewerId
            .append("reviewerId=").append(obj.getReviewerId()).append(COMMA)
            // projectId
            .append("projectId=").append(obj.getProjectId()).append(COMMA)
            // paymentAmount
            .append("paymentAmount=").append(obj.getPaymentAmount()).append(R_BRACE);

        return sb.toString();
    }

    /**
     * <p>
     * Logs for entrance into public methods and parameter at <code>DEBUG</code> level.
     * </p>
     *
     * <p>
     * <em>NOTE:</em> Logging is NOT performed if log is <code>null</code>.
     * </p>
     *
     * @param signature
     *            the signature of the method to log.
     * @param paramNames
     *            the names of parameters to log.
     * @param paramValues
     *            the values of parameters to log.
     */
    private void logEntrance(String signature, String[] paramNames, Object[] paramValues) {
        if (log == null) {
            // No logging
            return;
        }

        log.log(Level.DEBUG, String.format(MESSAGE_ENTRANCE, signature));

        // Log parameters
        StringBuilder sb = new StringBuilder("The parameters: {");
        boolean first = true;
        int paramNamesLen = paramNames.length;
        for (int i = 0; i < paramNamesLen; i++) {
            if (!first) {
                // Append a comma
                sb.append(COMMA);
            }
            first = false;
            Object paramValue = paramValues[i];
            sb.append(paramNames[i]).append("=").append(
                (i == paramNamesLen - 1) ? toString((ReviewerStatistics[]) paramValue, false) : paramValue);
        }
        sb.append(R_BRACE);

        log.log(Level.DEBUG, sb.toString());
    }

    /**
     * <p>
     * Logs for exit from public methods at <code>DEBUG</code> level.
     * </p>
     *
     * <p>
     * <em>NOTE:</em> Logging is NOT performed if log is <code>null</code>.
     * </p>
     *
     * @param signature
     *            the signature of the method to log.
     * @param value
     *            the return value to log (not <code>null</code>).
     * @param enterTimestamp
     *            the timestamp while entering the method.
     */
    private void logExit(String signature, ReviewerPayment[] value, Date enterTimestamp) {
        if (log == null) {
            // No logging
            return;
        }

        log.log(Level.DEBUG, String.format(MESSAGE_EXIT, signature, new Date().getTime() - enterTimestamp.getTime()));

        // Log return value

        log.log(Level.DEBUG, "The return value: " + toString(value, true));
    }

    /**
     * <p>
     * Logs the given exception at <code>ERROR</code> level.
     * </p>
     *
     * <p>
     * <em>NOTE:</em> Logging is NOT performed if log is <code>null</code>.
     * </p>
     *
     * @param signature
     *            the signature of the method to log.
     * @param exception
     *            the custom exception to log.
     * @param message
     *            the message to log.
     *
     * @return the passed in exception.
     */
    private InvalidReviewersStatisticsException logCustomException(String signature,
        InvalidReviewersStatisticsException exception, String message) {
        if (log != null) {
            // Do logging
            logException(signature, exception, message);

            // Set the custom exception is logged
            exception.setLogged(true);
        }

        return exception;
    }

    /**
     * <p>
     * Logs the given exception and message at <code> ERROR</code> level.
     * </p>
     *
     * <p>
     * <em>NOTE:</em> Logging is NOT performed if log is <code>null</code>.
     * </p>
     *
     * @param <T>
     *            the exception type.
     * @param signature
     *            the signature of the method to log.
     * @param exception
     *            the exception to log.
     * @param message
     *            the message to log.
     *
     * @return the passed in exception.
     */
    private <T extends Throwable> T logException(String signature, T exception, String message) {
        if (log != null) {
            // Log exception at ERROR level
            log.log(Level.ERROR, String.format(MESSAGE_ERROR, signature, message) + getStackTrace(exception));
        }

        return exception;
    }

    /**
     * <p>
     * Returns the exception stack trace string.
     * </p>
     *
     * @param cause
     *            the exception to be recorded.
     *
     * @return the exception stack trace string.
     */
    private static String getStackTrace(Throwable cause) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out);

        // Print a new line
        ps.println();
        cause.printStackTrace(ps);

        return out.toString();
    }

    /**
     * <p>
     * Gets the signature for given method for logging.
     * </p>
     *
     * @param method
     *            the method name.
     *
     * @return the signature for given method.
     */
    private static String getSignature(String method) {
        return CLASS_NAME + "." + method;
    }
}
