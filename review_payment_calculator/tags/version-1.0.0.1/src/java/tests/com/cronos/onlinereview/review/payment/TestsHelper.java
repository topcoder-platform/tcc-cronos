/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.review.payment;

import java.io.File;
import java.lang.reflect.Field;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.persistence.XMLFilePersistence;
import com.topcoder.management.resource.ReviewerStatistics;
import com.topcoder.management.resource.StatisticsType;

/**
 * <p>
 * This class provides methods for testing this component.
 * </p>
 *
 * @author sparemax
 * @version 1.0
 */
public class TestsHelper {
    /**
     * <p>
     * Represents the empty string.
     * </p>
     */
    public static final String EMPTY_STRING = " \t ";

    /**
     * <p>
     * Represents the path of test files.
     * </p>
     */
    private static final String TEST_FILES = "test_files" + File.separator;

    /**
     * <p>
     * Represents the configuration file used in tests.
     * </p>
     */
    private static final String CONFIG_FILE = TEST_FILES + "config.xml";

    /**
     * <p>
     * Private constructor to prevent this class being instantiated.
     * </p>
     */
    private TestsHelper() {
        // empty
    }

    /**
     * <p>
     * Gets the configuration object used for tests.
     * </p>
     *
     * @return the configuration object.
     *
     * @throws Exception
     *             to JUnit.
     */
    public static ConfigurationObject getConfig() throws Exception {
        XMLFilePersistence persistence = new XMLFilePersistence();

        // Get configuration
        ConfigurationObject obj = persistence.loadFile(
            "com.cronos.onlinereview.review.payment.impl.DefaultReviewPaymentCalculator",
            new File(CONFIG_FILE));

        return obj.getChild("com.cronos.onlinereview.review.payment.impl.DefaultReviewPaymentCalculator");
    }

    /**
     * <p>
     * Creates a ReviewerStatistics instance with given values.
     * </p>
     *
     * @param projectId
     *            the project id.
     * @param reviewerId
     *            the reviewer id.
     * @param timelineReliability
     *            the timeline reliability.
     * @param coverage
     *            the coverage.
     * @param accuracy
     *            the accuracy.
     * @param totalEvaluationCoefficient
     *            the total evaluation coefficient.
     * @param statisticsType
     *            the statistics type.
     *
     * @return the created ReviewerStatistics instance.
     */
    public static ReviewerStatistics getReviewerStatistics(long projectId, long reviewerId,
        double timelineReliability, double coverage, double accuracy, double totalEvaluationCoefficient,
        StatisticsType statisticsType) {
        ReviewerStatistics reviewerStatistics = new ReviewerStatistics();
        reviewerStatistics.setProjectId(projectId);
        reviewerStatistics.setReviewerId(reviewerId);
        reviewerStatistics.setTimelineReliability(timelineReliability);
        reviewerStatistics.setCoverage(coverage);
        reviewerStatistics.setAccuracy(accuracy);
        reviewerStatistics.setTotalEvaluationCoefficient(totalEvaluationCoefficient);
        reviewerStatistics.setStatisticsType(statisticsType);

        return reviewerStatistics;
    }

    /**
     * <p>
     * Gets value for field of given object.
     * </p>
     *
     * @param obj
     *            the given object.
     * @param field
     *            the field name.
     *
     * @return the field value.
     */
    public static Object getField(Object obj, String field) {
        Object value = null;
        try {
            Field declaredField = null;
            try {
                declaredField = obj.getClass().getDeclaredField(field);
            } catch (NoSuchFieldException e) {
                // Ignore
            }

            declaredField.setAccessible(true);

            value = declaredField.get(obj);

            declaredField.setAccessible(false);
        } catch (IllegalArgumentException e) {
            // Ignore
        } catch (SecurityException e) {
            // Ignore
        } catch (IllegalAccessException e) {
            // Ignore
        }

        return value;
    }
}
