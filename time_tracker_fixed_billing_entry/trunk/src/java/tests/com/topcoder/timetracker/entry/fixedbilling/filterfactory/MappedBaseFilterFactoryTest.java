/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.fixedbilling.filterfactory;

import com.topcoder.search.builder.filter.BetweenFilter;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.search.builder.filter.GreaterThanOrEqualToFilter;
import com.topcoder.search.builder.filter.LessThanOrEqualToFilter;
import com.topcoder.search.builder.filter.LikeFilter;

import com.topcoder.timetracker.entry.fixedbilling.StringMatchType;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.Date;


/**
 * Unit test cases for <code>{@link MappedBaseFilterFactory}</code> class.
 *
 * @author flytoj2ee
 * @version 1.0
 */
public class MappedBaseFilterFactoryTest extends TestCase {
    /** The MappedBaseFilterFactory instance for testing. */
    private MappedBaseFilterFactory filterFactory;

    /** The Filter instance for testing. */
    private Filter filter;

    /**
     * Set up the initial values.
     */
    public void setUp() {
        filterFactory = new MappedBaseFilterFactory();
    }

    /**
     * Test the <code>{@link MappedBaseFilterFactory#MappedBaseFilterFactory()}</code> with success process.
     */
    public void testConstructor_Map_Success() {
        filterFactory = new MappedBaseFilterFactory();
        assertNotNull("Unable to create the instance.", filterFactory);
    }

    /**
     * Test the <code>{@link MappedBaseFilterFactory#createCreationDateFilter(Date, Date)}</code> with wrong date
     * values. Should throw an IllegalArgumentException here.
     */
    public void testCreateCreationDateFilter_DateDate_BothNull() {
        try {
            filterFactory.createCreationDateFilter(null, null);
            fail("Should throw an IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link MappedBaseFilterFactory#createCreationDateFilter(Date, Date)}</code> with invalid range
     * dates. Should throw an IllegalArgumentException here.
     */
    public void testCreateCreationDateFilter_DateDate_InvalidRange() {
        try {
            filterFactory.createCreationDateFilter(new Date(20000), new Date(10000));
            fail("Should throw an IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link MappedBaseFilterFactory#createCreationDateFilter(Date, Date)}</code> with success process.
     */
    public void testCreateCreationDateFilter_DateDate_Success1() {
        filter = filterFactory.createCreationDateFilter(new Date(), null);
        assertTrue("The return result should be true.", filter instanceof GreaterThanOrEqualToFilter);
    }

    /**
     * Test the <code>{@link MappedBaseFilterFactory#createCreationDateFilter(Date, Date)}</code> with success process.
     */
    public void testCreateCreationDateFilter_DateDate_Success2() {
        filter = filterFactory.createCreationDateFilter(null, new Date());
        assertTrue("The return result should be true.", filter instanceof LessThanOrEqualToFilter);
    }

    /**
     * Test the <code>{@link MappedBaseFilterFactory#createCreationDateFilter(Date, Date)}</code> with success process.
     */
    public void testCreateCreationDateFilter_DateDate_Success3() {
        filter = filterFactory.createCreationDateFilter(new Date(10000), new Date(20000));
        assertTrue("The return result should be true.", filter instanceof BetweenFilter);
    }

    /**
     * Test the <code>{@link MappedBaseFilterFactory#createModificationDateFilter(Date, Date)}</code> with wrong date
     * values. Should throw an IllegalArgumentException here.
     */
    public void testCreateModificationDateFilter_DateDate_BothNull() {
        try {
            filterFactory.createModificationDateFilter(null, null);
            fail("Should throw an IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link MappedBaseFilterFactory#createModificationDateFilter(Date, Date)}</code> with invalid date
     * ranges. Should throw an IllegalArgumentException here.
     */
    public void testCreateModificationDateFilter_DateDate_InvalidRange() {
        try {
            filterFactory.createModificationDateFilter(new Date(20000), new Date(10000));
            fail("Should throw an IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link MappedBaseFilterFactory#createModificationDateFilter(Date, Date)}</code> with success
     * process.
     */
    public void testCreateModificationDateFilter_DateDate_Success1() {
        filter = filterFactory.createModificationDateFilter(new Date(), null);
        assertTrue("The return result should be true.", filter instanceof GreaterThanOrEqualToFilter);
    }

    /**
     * Test the <code>{@link MappedBaseFilterFactory#createModificationDateFilter(Date, Date)}</code> with success
     * process.
     */
    public void testCreateModificationDateFilter_DateDate_Success2() {
        filter = filterFactory.createModificationDateFilter(null, new Date());
        assertTrue("The return result should be true.", filter instanceof LessThanOrEqualToFilter);
    }

    /**
     * Test the <code>{@link MappedBaseFilterFactory#createModificationDateFilter(Date, Date)}</code> with success
     * process.
     */
    public void testCreateModificationDateFilter_DateDate_Success3() {
        filter = filterFactory.createModificationDateFilter(new Date(10000), new Date(20000));
        assertTrue("The return result should be true.", filter instanceof BetweenFilter);
    }

    /**
     * Test the <code>{@link MappedBaseFilterFactory#createCreationUserFilter(String, StringMatchType)}</code> with
     * null user name. Should throw an IllegalArgumentException here.
     */
    public void testCreateCreationUserFilter_StringStringMatchType_NullString() {
        try {
            filterFactory.createCreationUserFilter(null, StringMatchType.ENDS_WITH);
            fail("Should throw an IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link MappedBaseFilterFactory#createCreationUserFilter(String, StringMatchType)}</code> with
     * empty user name. Should throw an IllegalArgumentException here.
     */
    public void testCreateCreationUserFilter_StringStringMatchType_EmptyString() {
        try {
            filterFactory.createCreationUserFilter(" ", StringMatchType.ENDS_WITH);
            fail("Should throw an IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link MappedBaseFilterFactory#createCreationUserFilter(String, StringMatchType)}</code> with
     * null StringMatchType. Should throw an IllegalArgumentException here.
     */
    public void testCreateCreationUserFilter_StringStringMatchType_NullStringMatchType() {
        try {
            filterFactory.createCreationUserFilter("value", null);
            fail("Should throw an IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link MappedBaseFilterFactory#createCreationUserFilter(String, StringMatchType)}</code> with
     * success process.
     */
    public void testCreateCreationUserFilter_StringStringMatchType_Success1() {
        filter = filterFactory.createCreationUserFilter("value", StringMatchType.EXACT_MATCH);
        assertTrue("The return result should be true.", filter instanceof EqualToFilter);
    }

    /**
     * Test the <code>{@link MappedBaseFilterFactory#createCreationUserFilter(String, StringMatchType)}</code> with
     * success process.
     */
    public void testCreateCreationUserFilter_StringStringMatchType_Success2() {
        filter = filterFactory.createCreationUserFilter("value", StringMatchType.ENDS_WITH);
        assertTrue("The return result should be true.", filter instanceof LikeFilter);
    }

    /**
     * Test the <code>{@link MappedBaseFilterFactory#createModificationUserFilter(String, StringMatchType)}</code> with
     * null user name. Should throw an IllegalArgumentException here.
     */
    public void testCreateModificationUserFilter_StringStringMatchType_NullString() {
        try {
            filterFactory.createModificationUserFilter(null, StringMatchType.ENDS_WITH);
            fail("Should throw an IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link MappedBaseFilterFactory#createModificationUserFilter(String, StringMatchType)}</code> with
     * empty user name. Should throw an IllegalArgumentException here.
     */
    public void testCreateModificationUserFilter_StringStringMatchType_EmptyString() {
        try {
            filterFactory.createModificationUserFilter(" ", StringMatchType.ENDS_WITH);
            fail("Should throw an IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link MappedBaseFilterFactory#createModificationUserFilter(String, StringMatchType)}</code> with
     * null StringMatchType. Should throw an IllegalArgumentException here.
     */
    public void testCreateModificationUserFilter_StringStringMatchType_NullStringMatchType() {
        try {
            filterFactory.createModificationUserFilter("value", null);
            fail("Should throw an IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link MappedBaseFilterFactory#createModificationUserFilter(String, StringMatchType)}</code> with
     * success process.
     */
    public void testCreateModificationUserFilter_StringStringMatchType_Success1() {
        filter = filterFactory.createModificationUserFilter("value", StringMatchType.EXACT_MATCH);
        assertTrue("The return result should be true.", filter instanceof EqualToFilter);
    }

    /**
     * Test the <code>{@link MappedBaseFilterFactory#createModificationUserFilter(String, StringMatchType)}</code> with
     * success process.
     */
    public void testCreateModificationUserFilter_StringStringMatchType_Success2() {
        filter = filterFactory.createModificationUserFilter("value", StringMatchType.ENDS_WITH);
        assertTrue("The return result should be true.", filter instanceof LikeFilter);
    }

    /**
     * Test the <code>{@link MappedBaseFilterFactory#getColumnNames()}</code> with success process.
     */
    public void testGetColumnNames_Success() {
        testConstructor_Map_Success();
    }

    /**
     * Returns the test suite of this test case.
     *
     * @return the test suite of this test case.
     */
    public static Test suite() {
        return new TestSuite(MappedBaseFilterFactoryTest.class);
    }
}
