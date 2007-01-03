/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved
 */
package com.cronos.timetracker.accuracytests;

import java.util.Date;

import com.cronos.timetracker.company.CompanySearchBuilder;
import com.topcoder.search.builder.filter.AndFilter;
import com.topcoder.search.builder.filter.BetweenFilter;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.LikeFilter;
import com.topcoder.search.builder.filter.OrFilter;

import junit.framework.TestCase;

/**
 * Accuracy test cases for class <code>CompanySearchBuilder </code>.
 *
 * @author Chenhong
 * @version 2.0
 */
public class TestCompanySearchBuilderAccuracy extends TestCase {

    /**
     * Represents the CompanySearchBuilder instance for test.
     */
    private CompanySearchBuilder builder = new CompanySearchBuilder();

    /**
     * Test constructor.
     *
     */
    public void testCompanySearchBuilder() {
        assertNotNull("Should not be null.", builder);
    }

    /**
     * Test method <code>void hasCompanyName(String name) </code>.
     *
     */
    public void testHasCompanyName() {
        builder.hasCompanyName("topcoder");

        LikeFilter filter = (LikeFilter) ((AndFilter) builder.buildFilter()).getFilters().get(0);

        assertEquals("Equal is expected.", "topcoder", filter.getValue());
    }

    /**
     * Test method <code>void hasContactFirstName(String firstName)  </code>.
     *
     */
    public void testHasContactFirstName() {
        builder.hasContactFirstName("john");

        LikeFilter filter = (LikeFilter) ((AndFilter) builder.buildFilter()).getFilters().get(0);

        assertEquals("Equal is expectd.", "john", filter.getValue());
    }

    /**
     * Test method <code>void hasContactLastName(String lastName) </code>.
     *
     */
    public void testHasContactLastName() {
        builder.hasContactLastName("smith");

        LikeFilter filter = (LikeFilter) ((AndFilter) builder.buildFilter()).getFilters().get(0);

        assertEquals("Equal is expectd.", "smith", filter.getValue());
    }

    /**
     * Test method <code>void hasContactPhoneNumber(String phoneNumber) </code>.
     *
     */
    public void testHasContactPhoneNumber() {
        builder.hasContactPhoneNumber("055-111");

        EqualToFilter filter = (EqualToFilter) ((AndFilter) builder.buildFilter()).getFilters().get(0);

        assertEquals("Equal is expected.", "055-111", filter.getValue());
    }

    /**
     * Test method <code>void hasContactEmail(String email) </code>.
     *
     */
    public void testHasContactEmail() {
        builder.hasContactEmail("payments@topcoder.com");

        EqualToFilter filter = (EqualToFilter) ((AndFilter) builder.buildFilter()).getFilters().get(0);

        assertEquals("Equal is expected.", "payments@topcoder.com", filter.getValue());
    }

    /**
     * Test method <code>void hasStreetAddress(String streetAddress) </code>.
     *
     */
    public void testHasStreetAddress() {
        builder.hasStreetAddress("topcoder inc.");

        OrFilter filter = (OrFilter) ((AndFilter) builder.buildFilter()).getFilters().get(0);

        LikeFilter filter1 = (LikeFilter) filter.getFilters().get(0);

        assertEquals("Equal is expected.", "topcoder inc.", filter1.getValue());

        LikeFilter filter2 = (LikeFilter) filter.getFilters().get(1);

        assertEquals("Equal is expected.", "topcoder inc.", filter2.getValue());
    }

    /**
     * Test method <code>void hasCity(String city) </code>.
     *
     */
    public void testHasCity() {
        builder.hasCity("new york");

        EqualToFilter filter = (EqualToFilter) ((AndFilter) builder.buildFilter()).getFilters().get(0);

        assertEquals("Equal is expected.", "new york", filter.getValue());
    }

    /**
     * Test method <code> void hasState(String state) </code>.
     *
     */
    public void testHasState() {
        builder.hasState("ms");

        EqualToFilter filter = (EqualToFilter) ((AndFilter) builder.buildFilter()).getFilters().get(0);

        assertEquals("Equal is expected.", "ms", filter.getValue());
    }

    /**
     * Test method <code>void hasZipCode(String zipCode) </code>.
     *
     */
    public void testHasZipCode() {
        builder.hasZipCode("510275");

        EqualToFilter filter = (EqualToFilter) ((AndFilter) builder.buildFilter()).getFilters().get(0);

        assertEquals("Equal is expected.", "510275", filter.getValue());
    }

    /**
     * Test method <code>void createdWithinDateRange(Date startDate, Date endDate) </code>.
     *
     */
    public void testCreatedWithinDateRange() {
        Date start = new Date(System.currentTimeMillis() - 100000);
        Date end = new Date(System.currentTimeMillis() + 100000);

        builder.createdWithinDateRange(start, end);
        BetweenFilter filter = (BetweenFilter) ((AndFilter) builder.buildFilter()).getFilters().get(0);

        assertEquals("Equal is expected.", start, filter.getLowerThreshold());

        assertEquals("Equal is expected.", end, filter.getUpperThreshold());

    }

    /**
     * Test method <code>void createdByUser(String username) </code>.
     *
     */
    public void testCreatedByUser() {
        builder.createdByUser("tc_reviewer");

        EqualToFilter filter = (EqualToFilter) ((AndFilter) builder.buildFilter()).getFilters().get(0);

        assertEquals("Equal is expected.", "tc_reviewer", filter.getValue());

    }

    /**
     * Test method <code> void modifiedWithinDateRange(Date startDate, Date endDate) </code>.
     *
     */
    public void testModifiedWithinDateRange_lowBound() {
        Date start = new Date(System.currentTimeMillis() - 100);
        Date end = new Date(System.currentTimeMillis() + 100000);

        builder.modifiedWithinDateRange(start, end);

        BetweenFilter filter = (BetweenFilter) ((AndFilter) builder.buildFilter()).getFilters().get(0);

        assertEquals("Equal is expected.", start, filter.getLowerThreshold());

        assertEquals("Equal is expected.", end, filter.getUpperThreshold());
    }

    /**
     * Test method <code> void modifiedWithinDateRange(Date startDate, Date endDate) </code>.
     *
     */
    public void testModifiedWithinDateRange_upperBound() {
        Date start = new Date(System.currentTimeMillis() - 10000000);
        Date end = new Date(System.currentTimeMillis() + 10000);

        builder.modifiedWithinDateRange(start, end);

        BetweenFilter filter = (BetweenFilter) ((AndFilter) builder.buildFilter()).getFilters().get(0);

        assertEquals("Equal is expected.", start, filter.getLowerThreshold());

        assertEquals("Equal is expected.", end, filter.getUpperThreshold());
    }

    /**
     * Test method <code>void modifiedByUser(String username) </code>.
     *
     */
    public void testModifiedByUser() {
        builder.modifiedByUser("tc");

        EqualToFilter filter = (EqualToFilter) ((AndFilter) builder.buildFilter()).getFilters().get(0);

        assertEquals("Equal is expected.", "tc", filter.getValue());

    }

    /**
     * Test method <code>  Filter buildFilter() </code>.
     *
     */
    public void testBuildFilter() {
        builder.hasCompanyName("topcoder");

        assertNotNull("Should not be null.", builder.buildFilter());
    }

    /**
     * Test method <code>void reset() </code>.
     *
     */
    public void testReset() {

        builder.reset();

        try {
            builder.buildFilter();
            fail("IllegalStateException is expected.");
        } catch (IllegalStateException e) {
            // Ok.
        }
    }
}
