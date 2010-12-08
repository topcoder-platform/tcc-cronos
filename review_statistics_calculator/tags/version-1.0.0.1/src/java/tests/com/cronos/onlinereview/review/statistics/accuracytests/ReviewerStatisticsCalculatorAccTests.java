/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.review.statistics.accuracytests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.cronos.onlinereview.review.statistics.impl.ReviewerStatisticsCalculator;
import com.cronos.onlinereview.review.statistics.impl.TestsHelper;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.management.resource.ReviewerStatistics;


/**
 * Accuracy tests for ReviewerStatisticsCalculator.
 *
 * @author onsky
 * @version 1.0
 */
public class ReviewerStatisticsCalculatorAccTests extends AccuracyHelper {
    /** Represents the ReviewerStatisticsCalculator instance to test. */
    private ReviewerStatisticsCalculator instance;

    /**
     * <p>Sets up the unit tests.</p>
     *
     * @throws Exception to junit
     */
    @Before
    public void setUp() throws Exception {
        instance = new ReviewerStatisticsCalculator();
        buildConfig();

        TestsHelper.updateData(new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName()),
            "test_files/clean_DB.sql");
        TestsHelper.updateData(new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName()),
            "test_files/insert_DB.sql");
    }

    /**
     * <p>Cleans up the unit tests.</p>
     *
     * @throws Exception to junit
     */
    @After
    public void tearDown() throws Exception {
        TestsHelper.updateData(new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName()),
        "test_files/clean_DB.sql");
        instance = null;
        clearConfig();
    }

    /**
     * Accuracy test for method ReviewerStatisticsCalculator.
     */
    @Test
    public void test_ReviewerStatisticsCalculator() {
        assertNotNull(instance);
    }

    /**
     * Accuracy test for method configure.
     *
     * @throws Exception to junit
     */
    @SuppressWarnings("unchecked")
    @Test
    public void test_configure() throws Exception {
        instance.configure(getConfigurationObject("accuracytests/ReviewerStatisticsCalculator.xml",
                "com.cronos.onlinereview.review.statistics.impl.ReviewerStatisticsCalculator"));
        assertNotNull("field is not set", getPrivateField(instance, "timelineReliabilityCalculator"));
        assertNotNull("field is not set", getPrivateField(instance, "coverageCalculator"));
        assertNotNull("field is not set", getPrivateField(instance, "accuracyCalculator"));
        assertNotNull("field is not set", getPrivateField(instance, "resourceManager"));
    }

    /**
     * Accuracy test for method calculateReliability.
     *
     * @throws Exception to junit
     */
    @SuppressWarnings("unchecked")
    @Test
    public void test_calculateReliability() throws Exception {
        instance.configure(getConfigurationObject("accuracytests/ReviewerStatisticsCalculator.xml",
                "com.cronos.onlinereview.review.statistics.impl.ReviewerStatisticsCalculator"));
        setPrivateField(instance, "deliverableManager", new MockDeliverableManager());
        ReviewerStatistics[] result = instance.calculateStatistics(2, 1);
        assertEquals("2 results available", 2, result.length);
    }
}
