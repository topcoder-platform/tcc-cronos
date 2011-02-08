/*
 * Copyright (C) 2008, 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.memberphoto.manager;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Tests the functionality of {@link PagedResult} class.
 * </p>
 *
 * @author sparemax
 * @version 2.0
 * @since 2.0
 */
public class PagedResultTest extends TestCase {

    /**
     * Represents the <code>PagedResult</code> instance to test.
     */
    private PagedResult<String> instance = null;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     * @throws Exception
     *             throws exception if any.
     */
    @Override
    protected void setUp() throws Exception {
        instance = new PagedResult<String>();
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     * @throws Exception
     *             throws exception if any.
     */
    @Override
    protected void tearDown() throws Exception {
        instance = null;
    }

    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return Test suite of all tests of this class.
     */
    public static Test suite() {
        return new TestSuite(PagedResultTest.class);
    }

    /**
     * <p>
     * Accuracy test for {@link PagedResult#PagedResult()} constructor.
     * </p>
     * <p>
     * Creates an instance and checks its instantiation.
     * </p>
     *
     */
    public void test_accuracy_PagedResult() {
        // check for null
        assertNotNull("PagedResult creation failed", instance);
    }

    /**
     * <p>
     * Accuracy test for the method <code>getRecords()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    public void test_getRecords() {
        List<String> value = new ArrayList<String>();
        value.add("value1");
        value.add("value2");
        instance.setRecords(value);

        assertSame("'getRecords' should be correct.", value, instance.getRecords());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setRecords(List&lt;String&gt; records)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    public void test_setRecords() {
        List<String> value = new ArrayList<String>();
        value.add("value1");
        value.add("value2");
        instance.setRecords(value);

        assertEquals("'setRecords' should be correct.", value, TestHelper.getField(instance, "records"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getTotalRecords()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    public void test_getTotalRecords() {
        int value = 1;
        instance.setTotalRecords(value);

        assertEquals("'getTotalRecords' should be correct.", value, instance.getTotalRecords());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setTotalRecords(int totalRecords)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    public void test_setTotalRecords() {
        int value = 1;
        instance.setTotalRecords(value);

        assertEquals("'setTotalRecords' should be correct.", value, TestHelper.getField(instance, "totalRecords"));
    }
}
