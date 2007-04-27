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

import com.topcoder.timetracker.entry.fixedbilling.FixedBillingStatus;
import com.topcoder.timetracker.entry.fixedbilling.StringMatchType;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.Date;


/**
 * Unit test cases for <code>{@link MappedFixedBillingEntryFilterFactory}</code> class.
 *
 * @author flytoj2ee
 * @version 1.0
 */
public class MappedFixedBillingEntryFilterFactoryTest extends TestCase {
    /** The MappedFixedBillingEntryFilterFactory instance for testing. */
    private MappedFixedBillingEntryFilterFactory filterFactory;

    /** The Filter instance for testing. */
    private Filter filter;

    /**
     * Set up the initial values.
     */
    public void setUp() {
        filterFactory = new MappedFixedBillingEntryFilterFactory();
    }

    /**
     * Test the <code>{@link MappedFixedBillingEntryFilterFactory#MappedFixedBillingEntryFilterFactory()}</code>
     * with success process.
     */
    public void testConstructor_Map_Success() {
        filterFactory = new MappedFixedBillingEntryFilterFactory();
        assertNotNull("Unable to create the instance.", filterFactory);
    }

    /**
     * Test the <code>{@link MappedFixedBillingEntryFilterFactory#createInvoiceIdFilter(long)}</code> with wrong value.
     * Should throw an IllegalArgumentException here.
     */
    public void testCreateInvoiceIdFilter_long_WrongValue() {
        try {
            filterFactory.createInvoiceIdFilter(-2);
            fail("Should throw an IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link MappedFixedBillingEntryFilterFactory#createInvoiceIdFilter(long)}</code> with success
     * process.
     */
    public void testCreateInvoiceIdFilter_long_Success() {
        filter = filterFactory.createInvoiceIdFilter(1);
        assertTrue("The return result should be true.", filter instanceof EqualToFilter);
    }

    /**
     * Test the <code>{@link MappedFixedBillingEntryFilterFactory#createDescriptionFilter(String,
     * StringMatchType)}</code> with null String. Should throw an IllegalArgumentException here.
     */
    public void testCreateDescriptionFilter_StringStringMatchType_NullString() {
        try {
            filterFactory.createDescriptionFilter(null, StringMatchType.ENDS_WITH);
            fail("Should throw an IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link MappedFixedBillingEntryFilterFactory#createDescriptionFilter(String,
     * StringMatchType)}</code> with empty String. Should throw an IllegalArgumentException here.
     */
    public void testCreateDescriptionFilter_StringStringMatchType_EmptyString() {
        try {
            filterFactory.createDescriptionFilter(" ", StringMatchType.ENDS_WITH);
            fail("Should throw an IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link MappedFixedBillingEntryFilterFactory#createDescriptionFilter(String,
     * StringMatchType)}</code> with null StringMatchType. Should throw an IllegalArgumentException here.
     */
    public void testCreateDescriptionFilter_StringStringMatchType_NullStringMatchType() {
        try {
            filterFactory.createDescriptionFilter("value", null);
            fail("Should throw an IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link MappedFixedBillingEntryFilterFactory#createDescriptionFilter(String,
     * StringMatchType)}</code> with success process.
     */
    public void testCreateDescriptionFilter_StringStringMatchType_Success1() {
        filter = filterFactory.createDescriptionFilter("value", StringMatchType.EXACT_MATCH);
        assertTrue("The return result should be true.", filter instanceof EqualToFilter);
    }

    /**
     * Test the <code>{@link MappedFixedBillingEntryFilterFactory#createDescriptionFilter(String,
     * StringMatchType)}</code> with success process.
     */
    public void testCreateDescriptionFilter_StringStringMatchType_Success2() {
        filter = filterFactory.createDescriptionFilter("value", StringMatchType.ENDS_WITH);
        assertTrue("The return result should be true.", filter instanceof LikeFilter);
    }

    /**
     * Test the <code>{@link MappedFixedBillingEntryFilterFactory#createEntryDateFilter(Date, Date)}</code> with null
     * date value. Should throw an IllegalArgumentException here.
     */
    public void testCreateEntryDateFilter_DateDate_BothNull() {
        try {
            filterFactory.createEntryDateFilter(null, null);
            fail("Should throw an IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link MappedFixedBillingEntryFilterFactory#createEntryDateFilter(Date, Date)}</code> with
     * invalid date value. Should throw an IllegalArgumentException here.
     */
    public void testCreateEntryDateFilter_DateDate_InvalidRange() {
        try {
            filterFactory.createEntryDateFilter(new Date(20000), new Date(10000));
            fail("Should throw an IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link MappedFixedBillingEntryFilterFactory#createEntryDateFilter(Date, Date)}</code> with
     * success process.
     */
    public void testCreateEntryDateFilter_DateDate_Success1() {
        filter = filterFactory.createEntryDateFilter(new Date(), null);
        assertTrue("The return result should be true.", filter instanceof GreaterThanOrEqualToFilter);
    }

    /**
     * Test the <code>{@link MappedFixedBillingEntryFilterFactory#createEntryDateFilter(Date, Date)}</code> with
     * success process.
     */
    public void testCreateEntryDateFilter_DateDate_Success2() {
        filter = filterFactory.createEntryDateFilter(null, new Date());
        assertTrue("The return result should be true.", filter instanceof LessThanOrEqualToFilter);
    }

    /**
     * Test the <code>{@link MappedFixedBillingEntryFilterFactory#createEntryDateFilter(Date, Date)}</code> with
     * success process.
     */
    public void testCreateEntryDateFilter_DateDate_Success3() {
        filter = filterFactory.createEntryDateFilter(new Date(10000), new Date(20000));
        assertTrue("The return result should be true.", filter instanceof BetweenFilter);
    }

    /**
     * Test the <code>{@link MappedFixedBillingEntryFilterFactory#createAmountFilter(double, double)}</code> with
     * invalid double value. Should throw an IllegalArgumentException here.
     */
    public void testCreateAmountFilter_doubledouble_InvalidRange1() {
        try {
            filterFactory.createAmountFilter(Double.MIN_VALUE, Double.MAX_VALUE);
            fail("Should throw an IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link MappedFixedBillingEntryFilterFactory#createAmountFilter(double, double)}</code> with
     * invalid double value. Should throw an IllegalArgumentException here.
     */
    public void testCreateAmountFilter_doubledouble_InvalidRange2() {
        try {
            filterFactory.createAmountFilter(10, 0);
            fail("Should throw an IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link MappedFixedBillingEntryFilterFactory#createAmountFilter(double, double)}</code> with
     * success process.
     */
    public void testCreateAmountFilter_doubledouble_Success1() {
        filter = filterFactory.createAmountFilter(10, Double.MAX_VALUE);
        assertTrue("The return result should be true.", filter instanceof GreaterThanOrEqualToFilter);
    }

    /**
     * Test the <code>{@link MappedFixedBillingEntryFilterFactory#createAmountFilter(double, double)}</code> with
     * success process.
     */
    public void testCreateAmountFilter_doubledouble_Success2() {
        filter = filterFactory.createAmountFilter(Double.MIN_VALUE, 10);
        assertTrue("The return result should be true.", filter instanceof LessThanOrEqualToFilter);
    }

    /**
     * Test the <code>{@link MappedFixedBillingEntryFilterFactory#createAmountFilter(double, double)}</code> with
     * success process.
     */
    public void testCreateAmountFilter_doubledouble_Success3() {
        filter = filterFactory.createAmountFilter(0, 10);
        assertTrue("The return result should be true.", filter instanceof BetweenFilter);
    }

    /**
     * Test the <code>{@link
     * MappedFixedBillingEntryFilterFactory#createFixedBillingStatusFilter(FixedBillingStatus)}</code> with null
     * FixedBillingStatus. Should throw an IllegalArgumentException here.
     */
    public void testCreateFixedBillingStatusFilter_FixedBillingStatus_Null() {
        try {
            filterFactory.createFixedBillingStatusFilter(null);
            fail("Should throw an IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link
     * MappedFixedBillingEntryFilterFactory#createFixedBillingStatusFilter(FixedBillingStatus)}</code> with success
     * process.
     */
    public void testCreateFixedBillingStatusFilter_FixedBillingStatus_Success() {
        filter = filterFactory.createFixedBillingStatusFilter(new FixedBillingStatus());
        assertTrue("The return result should be true.", filter instanceof EqualToFilter);
    }

    /**
     * Test the <code>{@link MappedFixedBillingEntryFilterFactory#createRejectReasonFilter(long)}</code> with invalid
     * long value. Should throw an IllegalArgumentException here.
     */
    public void testCreateRejectReasonFilter_long_Invallid() {
        try {
            filterFactory.createRejectReasonFilter(-1);
            fail("Should throw an IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link MappedFixedBillingEntryFilterFactory#createRejectReasonFilter(long)}</code> with success
     * process.
     */
    public void testCreateRejectReasonFilter_long_Success() {
        filter = filterFactory.createRejectReasonFilter(10);
        assertTrue("The return result should be true.", filter instanceof EqualToFilter);
    }

    /**
     * Test the <code>{@link MappedFixedBillingEntryFilterFactory#createCompanyIdFilter(long)}</code> with invalid long
     * value. Should throw an IllegalArgumentException here.
     */
    public void testCreateCompanyIdFilter_long_Invalid() {
        try {
            filterFactory.createCompanyIdFilter(-1);
            fail("Should throw an IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link MappedFixedBillingEntryFilterFactory#createCompanyIdFilter(long)}</code> with success
     * process.
     */
    public void testCreateCompanyIdFilter_long_Success() {
        filter = filterFactory.createCompanyIdFilter(10);
        assertTrue("The return result should be true.", filter instanceof EqualToFilter);
    }

    /**
     * Test the <code>{@link MappedFixedBillingEntryFilterFactory#createCreationDateFilter(Date, Date)}</code> with
     * wrong date values. Should throw an IllegalArgumentException here.
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
     * Test the <code>{@link MappedFixedBillingEntryFilterFactory#createCreationDateFilter(Date, Date)}</code> with
     * invalid range dates. Should throw an IllegalArgumentException here.
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
     * Test the <code>{@link MappedFixedBillingEntryFilterFactory#createCreationDateFilter(Date, Date)}</code> with
     * success process.
     */
    public void testCreateCreationDateFilter_DateDate_Success1() {
        filter = filterFactory.createCreationDateFilter(new Date(), null);
        assertTrue("The return result should be true.", filter instanceof GreaterThanOrEqualToFilter);
    }

    /**
     * Test the <code>{@link MappedFixedBillingEntryFilterFactory#createCreationDateFilter(Date, Date)}</code> with
     * success process.
     */
    public void testCreateCreationDateFilter_DateDate_Success2() {
        filter = filterFactory.createCreationDateFilter(null, new Date());
        assertTrue("The return result should be true.", filter instanceof LessThanOrEqualToFilter);
    }

    /**
     * Test the <code>{@link MappedFixedBillingEntryFilterFactory#createCreationDateFilter(Date, Date)}</code> with
     * success process.
     */
    public void testCreateCreationDateFilter_DateDate_Success3() {
        filter = filterFactory.createCreationDateFilter(new Date(10000), new Date(20000));
        assertTrue("The return result should be true.", filter instanceof BetweenFilter);
    }

    /**
     * Test the <code>{@link MappedFixedBillingEntryFilterFactory#createModificationDateFilter(Date, Date)}</code> with
     * wrong date values. Should throw an IllegalArgumentException here.
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
     * Test the <code>{@link MappedFixedBillingEntryFilterFactory#createModificationDateFilter(Date, Date)}</code> with
     * invalid date ranges. Should throw an IllegalArgumentException here.
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
     * Test the <code>{@link MappedFixedBillingEntryFilterFactory#createModificationDateFilter(Date, Date)}</code> with
     * success process.
     */
    public void testCreateModificationDateFilter_DateDate_Success1() {
        filter = filterFactory.createModificationDateFilter(new Date(), null);
        assertTrue("The return result should be true.", filter instanceof GreaterThanOrEqualToFilter);
    }

    /**
     * Test the <code>{@link MappedFixedBillingEntryFilterFactory#createModificationDateFilter(Date, Date)}</code> with
     * success process.
     */
    public void testCreateModificationDateFilter_DateDate_Success2() {
        filter = filterFactory.createModificationDateFilter(null, new Date());
        assertTrue("The return result should be true.", filter instanceof LessThanOrEqualToFilter);
    }

    /**
     * Test the <code>{@link MappedFixedBillingEntryFilterFactory#createModificationDateFilter(Date, Date)}</code> with
     * success process.
     */
    public void testCreateModificationDateFilter_DateDate_Success3() {
        filter = filterFactory.createModificationDateFilter(new Date(10000), new Date(20000));
        assertTrue("The return result should be true.", filter instanceof BetweenFilter);
    }

    /**
     * Test the <code>{@link MappedFixedBillingEntryFilterFactory#createCreationUserFilter(String,
     * StringMatchType)}</code> with null user name. Should throw an IllegalArgumentException here.
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
     * Test the <code>{@link MappedFixedBillingEntryFilterFactory#createCreationUserFilter(String,
     * StringMatchType)}</code> with empty user name. Should throw an IllegalArgumentException here.
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
     * Test the <code>{@link MappedFixedBillingEntryFilterFactory#createCreationUserFilter(String,
     * StringMatchType)}</code> with null StringMatchType. Should throw an IllegalArgumentException here.
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
     * Test the <code>{@link MappedFixedBillingEntryFilterFactory#createCreationUserFilter(String,
     * StringMatchType)}</code> with success process.
     */
    public void testCreateCreationUserFilter_StringStringMatchType_Success1() {
        filter = filterFactory.createCreationUserFilter("value", StringMatchType.EXACT_MATCH);
        assertTrue("The return result should be true.", filter instanceof EqualToFilter);
    }

    /**
     * Test the <code>{@link MappedFixedBillingEntryFilterFactory#createCreationUserFilter(String,
     * StringMatchType)}</code> with success process.
     */
    public void testCreateCreationUserFilter_StringStringMatchType_Success2() {
        filter = filterFactory.createCreationUserFilter("value", StringMatchType.ENDS_WITH);
        assertTrue("The return result should be true.", filter instanceof LikeFilter);
    }

    /**
     * Test the <code>{@link MappedFixedBillingEntryFilterFactory#createModificationUserFilter(String,
     * StringMatchType)}</code> with null user name. Should throw an IllegalArgumentException here.
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
     * Test the <code>{@link MappedFixedBillingEntryFilterFactory#createModificationUserFilter(String,
     * StringMatchType)}</code> with empty user name. Should throw an IllegalArgumentException here.
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
     * Test the <code>{@link MappedFixedBillingEntryFilterFactory#createModificationUserFilter(String,
     * StringMatchType)}</code> with null StringMatchType. Should throw an IllegalArgumentException here.
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
     * Test the <code>{@link MappedFixedBillingEntryFilterFactory#createModificationUserFilter(String,
     * StringMatchType)}</code> with success process.
     */
    public void testCreateModificationUserFilter_StringStringMatchType_Success1() {
        filter = filterFactory.createModificationUserFilter("value", StringMatchType.EXACT_MATCH);
        assertTrue("The return result should be true.", filter instanceof EqualToFilter);
    }

    /**
     * Test the <code>{@link MappedFixedBillingEntryFilterFactory#createModificationUserFilter(String,
     * StringMatchType)}</code> with success process.
     */
    public void testCreateModificationUserFilter_StringStringMatchType_Success2() {
        filter = filterFactory.createModificationUserFilter("value", StringMatchType.ENDS_WITH);
        assertTrue("The return result should be true.", filter instanceof LikeFilter);
    }

    /**
     * Test the <code>{@link MappedFixedBillingEntryFilterFactory#getColumnNames()}</code> with success process.
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
        return new TestSuite(MappedFixedBillingEntryFilterFactoryTest.class);
    }
}
