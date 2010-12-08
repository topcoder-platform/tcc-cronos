/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.review.statistics.accuracytests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.cronos.onlinereview.review.statistics.impl.CoverageCalculatorImpl;
import com.topcoder.management.review.data.Comment;
import com.topcoder.management.review.data.EvaluationType;
import com.topcoder.management.review.data.Review;


/**
 * Accuracy tests for CoverageCalculatorImpl.
 *
 * @author onsky
 * @version 1.0
 */
public class CoverageCalculatorImplAccTests extends AccuracyHelper {
    /** Represents the CoverageCalculatorImpl instance to test. */
    private CoverageCalculatorImpl instance;

    /**
     * <p>Sets up the unit tests.</p>
     *
     * @throws Exception to junit
     */
    @Before
    public void setUp() throws Exception {
        instance = new CoverageCalculatorImpl();
    }

    /**
     * <p>Cleans up the unit tests.</p>
     *
     * @throws Exception to junit
     */
    @After
    public void tearDown() throws Exception {
        instance = null;
    }

    /**
     * Accuracy test for method CoverageCalculatorImpl.
     */
    @Test
    public void test_CoverageCalculatorImpl() {
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
        instance.configure(getConfigurationObject("accuracytests/CoverageCalculatorImpl.xml",
                "com.cronos.onlinereview.review.statistics.impl.CoverageCalculatorImpl"));
        assertEquals("field is not set", 4,
            ((Map<Long, Double>) getPrivateField(instance, "pointsForEvaluationType")).size());
    }

    /**
     * Accuracy test for method calculateReliability.
     *
     * @throws Exception to junit
     */
    @SuppressWarnings("unchecked")
    @Test
    public void test_calculateReliability() throws Exception {
        instance.configure(getConfigurationObject("accuracytests/CoverageCalculatorImpl.xml",
                "com.cronos.onlinereview.review.statistics.impl.CoverageCalculatorImpl"));
        Review [][] reviews = new Review[2][2];
        Review r1 = new Review();
        Comment c1 = new Comment();
        EvaluationType t1 = new EvaluationType();
        t1.setId(1);
        c1.setEvaluationType(t1);
        r1.addComment(c1);
        reviews[0][0] = r1;
        Review r2 = new Review();
        Comment c2 = new Comment();
        EvaluationType t2 = new EvaluationType();
        t2.setId(2);
        c2.setEvaluationType(t2);
        r2.addComment(c2);
        reviews[0][1] = r2;
        Review r3 = new Review();
        Comment c3 = new Comment();
        EvaluationType t3 = new EvaluationType();
        t3.setId(3);
        c3.setEvaluationType(t3);
        r3.addComment(c3);
        reviews[1][0] = r3;
        Review r4 = new Review();
        Comment c4 = new Comment();
        EvaluationType t4 = new EvaluationType();
        t4.setId(4);
        c4.setEvaluationType(t4);
        r4.addComment(c4);
        reviews[1][1] = r4;
        double[] result = instance.calculateCoverage(reviews);
        assertTrue("", 0.3333333333333333 == result[0]);
        assertTrue("", 0.6666666666666666 == result[1]);
    }
}
