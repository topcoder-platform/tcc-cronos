/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable.late.search;

import static org.junit.Assert.assertEquals;
import junit.framework.JUnit4TestAdapter;

import org.junit.Test;

import com.topcoder.management.deliverable.late.TestsHelper;
import com.topcoder.search.builder.filter.EqualToFilter;

/**
 * <p>
 * Unit tests for {@link LateDeliverableFilterBuilder} class.
 * </p>
 *
 * @author sparemax
 * @version 1.0.1
 */
public class LateDeliverableFilterBuilderUnitTests {
    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(LateDeliverableFilterBuilderUnitTests.class);
    }

    /**
     * <p>
     * Accuracy test for the method <code>createProjectIdFilter(long projectId)</code>.<br>
     * The result should be correct.
     * </p>
     */
    @Test
    public void test_createProjectIdFilter() {
        long value = 1;
        EqualToFilter filter = (EqualToFilter) LateDeliverableFilterBuilder.createProjectIdFilter(value);

        assertEquals("'createProjectIdFilter' should be correct.", "projectId", filter.getName());
        assertEquals("'createProjectIdFilter' should be correct.", value, filter.getValue());
    }

    /**
     * <p>
     * Failure test for the method <code>createProjectIdFilter(long projectId)</code> with projectId is negative.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_createProjectIdFilter_projectIdNegative() {
        long value = -1;

        LateDeliverableFilterBuilder.createProjectIdFilter(value);
    }

    /**
     * <p>
     * Failure test for the method <code>createProjectIdFilter(long projectId)</code> with projectId is zero.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_createProjectIdFilter_projectIdZero() {
        long value = 0;

        LateDeliverableFilterBuilder.createProjectIdFilter(value);
    }

    /**
     * <p>
     * Accuracy test for the method <code>createProjectStatusIdFilter(long projectStatusId)</code>.<br>
     * The result should be correct.
     * </p>
     */
    @Test
    public void test_createProjectStatusIdFilter() {
        long value = 1;
        EqualToFilter filter = (EqualToFilter) LateDeliverableFilterBuilder.createProjectStatusIdFilter(value);

        assertEquals("'createProjectStatusIdFilter' should be correct.", "projectStatusId", filter.getName());
        assertEquals("'createProjectStatusIdFilter' should be correct.", value, filter.getValue());
    }

    /**
     * <p>
     * Failure test for the method <code>createProjectStatusIdFilter(long projectStatusId)</code> with projectStatusId
     * is negative.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_createProjectStatusIdFilter_projectStatusIdNegative() {
        long value = -1;

        LateDeliverableFilterBuilder.createProjectStatusIdFilter(value);
    }

    /**
     * <p>
     * Failure test for the method <code>createProjectStatusIdFilter(long projectStatusId)</code> with projectStatusId
     * is zero.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_createProjectStatusIdFilter_projectStatusIdZero() {
        long value = 0;

        LateDeliverableFilterBuilder.createProjectStatusIdFilter(value);
    }

    /**
     * <p>
     * Accuracy test for the method <code>createProjectCategoryIdFilter(long projectCategoryId)</code>.<br>
     * The result should be correct.
     * </p>
     */
    @Test
    public void test_createProjectCategoryIdFilter() {
        long value = 1;
        EqualToFilter filter = (EqualToFilter) LateDeliverableFilterBuilder.createProjectCategoryIdFilter(value);

        assertEquals("'createProjectCategoryIdFilter' should be correct.", "projectCategoryId", filter.getName());
        assertEquals("'createProjectCategoryIdFilter' should be correct.", value, filter.getValue());
    }

    /**
     * <p>
     * Failure test for the method <code>createProjectCategoryIdFilter(long projectCategoryId)</code> with
     * projectCategoryId is negative.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_createProjectCategoryIdFilter_projectCategoryIdNegative() {
        long value = -1;

        LateDeliverableFilterBuilder.createProjectCategoryIdFilter(value);
    }

    /**
     * <p>
     * Failure test for the method <code>createProjectCategoryIdFilter(long projectCategoryId)</code> with
     * projectCategoryId is zero.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_createProjectCategoryIdFilter_projectCategoryIdZero() {
        long value = 0;

        LateDeliverableFilterBuilder.createProjectCategoryIdFilter(value);
    }

    /**
     * <p>
     * Accuracy test for the method <code>createDeliverableIdFilter(long deliverableId)</code>.<br>
     * The result should be correct.
     * </p>
     */
    @Test
    public void test_createDeliverableIdFilter() {
        long value = 1;
        EqualToFilter filter = (EqualToFilter) LateDeliverableFilterBuilder.createDeliverableIdFilter(value);

        assertEquals("'createDeliverableIdFilter' should be correct.", "deliverableId", filter.getName());
        assertEquals("'createDeliverableIdFilter' should be correct.", value, filter.getValue());
    }

    /**
     * <p>
     * Failure test for the method <code>createDeliverableIdFilter(long deliverableId)</code> with deliverableId is
     * negative.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_createDeliverableIdFilter_deliverableIdNegative() {
        long value = -1;

        LateDeliverableFilterBuilder.createDeliverableIdFilter(value);
    }

    /**
     * <p>
     * Failure test for the method <code>createDeliverableIdFilter(long deliverableId)</code> with deliverableId is
     * zero.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_createDeliverableIdFilter_deliverableIdZero() {
        long value = 0;

        LateDeliverableFilterBuilder.createDeliverableIdFilter(value);
    }

    /**
     * <p>
     * Accuracy test for the method <code>createForgivenFilter(boolean forgiven)</code>.<br>
     * The result should be correct.
     * </p>
     */
    @Test
    public void test_createForgivenFilter_1() {
        boolean value = true;
        EqualToFilter filter = (EqualToFilter) LateDeliverableFilterBuilder.createForgivenFilter(value);

        assertEquals("'createForgivenFilter' should be correct.", "forgiven", filter.getName());
        assertEquals("'createForgivenFilter' should be correct.", 1, filter.getValue());
    }

    /**
     * <p>
     * Accuracy test for the method <code>createForgivenFilter(boolean forgiven)</code>.<br>
     * The result should be correct.
     * </p>
     */
    @Test
    public void test_createForgivenFilter_2() {
        boolean value = false;
        EqualToFilter filter = (EqualToFilter) LateDeliverableFilterBuilder.createForgivenFilter(value);

        assertEquals("'createForgivenFilter' should be correct.", "forgiven", filter.getName());
        assertEquals("'createForgivenFilter' should be correct.", 0, filter.getValue());
    }

    /**
     * <p>
     * Accuracy test for the method <code>createUserHandleFilter(String userHandle)</code>.<br>
     * The result should be correct.
     * </p>
     */
    @Test
    public void test_createUserHandleFilter() {
        String value = "user";
        EqualToFilter filter = (EqualToFilter) LateDeliverableFilterBuilder.createUserHandleFilter(value);

        assertEquals("'createUserHandleFilter' should be correct.", "userHandle", filter.getName());
        assertEquals("'createUserHandleFilter' should be correct.", value, filter.getValue());
    }

    /**
     * <p>
     * Failure test for the method <code>createUserHandleFilter(String userHandle)</code> with userHandle is null.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_createUserHandleFilter_userHandleNull() {
        String value = null;

        LateDeliverableFilterBuilder.createUserHandleFilter(value);
    }

    /**
     * <p>
     * Failure test for the method <code>createUserHandleFilter(String userHandle)</code> with userHandle is empty.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_createUserHandleFilter_userHandleEmpty() {
        String value = TestsHelper.EMPTY_STRING;

        LateDeliverableFilterBuilder.createUserHandleFilter(value);
    }
}
