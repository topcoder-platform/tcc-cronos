/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.contest.failuretests;

import java.util.Map;

import junit.framework.TestCase;

import com.topcoder.search.builder.ValidationResult;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.search.builder.filter.NotFilter;
import com.topcoder.service.studio.contest.utils.FilterToSqlConverter;

/**
 * <p>
 * Failure tests for the <code>FilterToSqlConverter</code> class.
 * </p>
 *
 * @author KSD
 * @version 1.1
 */
public class FilterToSqlConverterFailureTest extends TestCase {

    /**
     * <p>
     * Failure test for the <code>convert(Filter filter)</code> method with the filter is null,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConvertWithFilterNull() throws Exception {
        try {
            FilterToSqlConverter.convert(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>convert(Filter filter)</code> method with the filter is unknown,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConvertWithFitlerUnknown() throws Exception {
        try {
            FilterToSqlConverter.convert(new UnknownFilter());
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>convert(Filter filter)</code> method with the filter is unsupported,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConvertWithFitlerUnsupported() throws Exception {
        try {
            FilterToSqlConverter.convert(new NotFilter(new UnknownFilter()));
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Represents an unknown filter for test.
     * </p>
     *
     * @author KSD
     * @version 1.1
     */
    private static class UnknownFilter implements Filter {
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
         * @param validators
         *            map
         * @param alias
         *            map
         *
         * @return validation result. always null.
         */
        @SuppressWarnings("unchecked")
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
            return new UnknownFilter();
        }
    }
}
