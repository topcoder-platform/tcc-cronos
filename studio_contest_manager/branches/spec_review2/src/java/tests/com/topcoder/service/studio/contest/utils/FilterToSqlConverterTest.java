/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.contest.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Map;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.search.builder.ValidationResult;
import com.topcoder.search.builder.filter.AndFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.search.builder.filter.NotFilter;

/**
 * <p>
 * Unit tests for <code>FilterToSqlConverter</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class FilterToSqlConverterTest extends TestCase {
    /**
     * <p>
     * Sets up the fixture. This method is called before a test is executed.
     * </p>
     *
     * @throws Exception if any error occurs.
     */
    protected void setUp() throws Exception {
        super.setUp();
    }

    /**
     * <p>
     * Tears down the fixture. This method is called after a test is executed.
     * </p>
     *
     * @throws Exception if any error occurs.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * <p>
     * Gets the test suite for <code>FilterToSqlConverter</code> class.
     * </p>
     *
     * @return a <code>TestSuite</code> providing the tests for <code>FilterToSqlConverter</code> class.
     */
    public static Test suite() {
        return new TestSuite(FilterToSqlConverterTest.class);
    }

    /**
     * <p>
     * Accuracy test for <code>convert(Filter)</code>.
     * </p>
     * <p>
     * Passes in valid value. No exception should be thrown.
     * </p>
     */
    public void testConvert_Accuracy1() {
        AndFilter filter = new AndFilter(ContestFilterFactory.createStudioContestFileTypeIdFilter(1),
            ContestFilterFactory.createStudioFileTypeExtensionFilter("jpeg"));
        assertSearchResult(FilterToSqlConverter.convert(filter), 2);
    }

    /**
     * <p>
     * Accuracy test for <code>convert(Filter)</code>.
     * </p>
     * <p>
     * Passes in valid value. No exception should be thrown.
     * </p>
     */
    public void testConvert_Accuracy2() {
        Filter filter = ContestFilterFactory.createStudioContestFileTypeIdFilter(1);
        assertSearchResult(FilterToSqlConverter.convert(filter), 1);
    }

    /**
     * <p>
     * Asserts search results. The first element should be a valid sql string prefix and the second should be a list
     * which contains all possible bind variable values.
     * </p>
     *
     * @param searchSQLResult result
     * @param numberOfVariables number of bind variables
     */
    private void assertSearchResult(Object[] searchSQLResult, int numberOfVariables) {
        String sqlString = (String) searchSQLResult[0];
        List bindVariables = (List) searchSQLResult[1];
        assertNotNull("SQl String should not be null.", sqlString);
        assertTrue("SQL string should start with 'SELECT'.", sqlString.startsWith("SELECT"));
        assertEquals("It should contain " + numberOfVariables + " bind variables", numberOfVariables, bindVariables
            .size());
    }

    /**
     * <p>
     * Failure test for <code>convert(Filter)</code>.
     * </p>
     * <p>
     * Passes in null value. A <code>IllegalArgumentException</code> should be thrown.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testConvert_Failure1() throws Exception {
        try {
            FilterToSqlConverter.convert(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test for <code>convert(Filter)</code>.
     * </p>
     * <p>
     * Passes in unknown filter value. A <code>IllegalArgumentException</code> should be thrown.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testConvert_Failure2() throws Exception {
        try {
            FilterToSqlConverter.convert(new MockFilter());
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test for <code>convert(Filter)</code>.
     * </p>
     * <p>
     * Passes in not filter with unknown filter value. A <code>IllegalArgumentException</code> should be thrown.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testConvert_Failure3() throws Exception {
        try {
            FilterToSqlConverter.convert(new NotFilter(new MockFilter()));
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Test to see if a private Ctor exists.
     * </p>
     *
     * @throws Exception to JUnit, indicates error
     */
    public void testPrivateCtor() throws Exception {
        try {
            Constructor privateCtor = FilterToSqlConverter.class.getDeclaredConstructors()[0];
            assertTrue(Modifier.isPrivate(privateCtor.getModifiers()));
            privateCtor.setAccessible(true);
            privateCtor.newInstance(new Object[] {});
        } catch (SecurityException e) {
            System.out.println("Skip test private constructor due to security reason.");
        }
    }

    /**
     * <p>
     * A unrecognized filter type.
     * </p>
     *
     * @author TCSDEVELOPER
     * @version 1.0
     */
    private static class MockFilter implements Filter {
        /**
         * <p>
         * serial version UID.
         * </p>
         */
        private static final long serialVersionUID = 4520590144719908375L;

        /**
         * <p>
         * Gets the type.
         * </p>
         *
         * @return type always 0
         */
        public int getFilterType() {
            return 0;
        }

        /**
         * <p>
         * Validates the filter.
         * </p>
         *
         * @param validators map
         * @param alias map
         *
         * @return validation result. always null.
         */
        public ValidationResult isValid(Map validators, Map alias) {
            return null;
        }

        /**
         * <p>
         * return a clone of the object.
         * </p>
         *
         * @return a clone of the object
         */
        public Object clone() {
            return new MockFilter();
        }
    }
}
