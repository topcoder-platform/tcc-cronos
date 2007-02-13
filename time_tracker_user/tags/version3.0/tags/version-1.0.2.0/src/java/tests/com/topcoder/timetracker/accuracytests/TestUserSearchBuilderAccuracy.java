/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved
 */
package com.cronos.timetracker.accuracytests;

import java.util.Date;

import com.cronos.timetracker.common.EncryptionRepository;
import com.cronos.timetracker.user.UserSearchBuilder;
import com.topcoder.search.builder.filter.AndFilter;
import com.topcoder.search.builder.filter.BetweenFilter;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.LikeFilter;
import com.topcoder.search.builder.filter.OrFilter;

import junit.framework.TestCase;

/**
 * Accuracy test for class <code>UserSearchBuilder</code>.
 *
 * @author Chenhong
 * @version 2.0
 */
public class TestUserSearchBuilderAccuracy extends TestCase {

    /**
     * Represents the UserSearchBuilder instance for test.
     */
    private UserSearchBuilder builder = null;

    /**
     * Set up the enviroment.
     */
    public void setUp() {
        EncryptionRepository.getInstance().registerAlgorithm("simple", new SimpleAlgorithm());
        builder = new UserSearchBuilder("simple");
    }

    /**
     * Test constructor.
     */
    public void testUserSearchBuilder() {
        builder = new UserSearchBuilder();
        assertNotNull("Should not be null.", builder);
    }

    /**
     * Test for construct <code> UserSearchBuilder(String) </code>.
     */
    public void testUserSearchBuilderString() {
        builder = new UserSearchBuilder("simple");

        assertNotNull("Should not be null.", builder);
    }

    /**
     * Test method hasUserName.
     *
     */
    public void testHasUsername() {
        builder.hasUsername("user");

        EqualToFilter filter = (EqualToFilter) ((AndFilter) builder.buildFilter()).getFilters().get(0);

        assertEquals("Equal is expected.", "user", filter.getValue());
    }

    /**
     * Test method hasPassword.
     *
     */
    public void testHasPassword() {
        builder.hasPassword("psw");

        EqualToFilter filter = (EqualToFilter) ((AndFilter) builder.buildFilter()).getFilters().get(0);

        assertEquals("Equal is expected.", "psw", filter.getValue());
    }

    /**
     * Test method hasFirstName.
     *
     */
    public void testHasFirstName() {
        builder.hasFirstName("john");

        LikeFilter filter = (LikeFilter) ((AndFilter) builder.buildFilter()).getFilters().get(0);

        assertEquals("Equal is expected.", "john", filter.getValue());
    }

    /**
     * Test method hasLastName.
     *
     */
    public void testHasLastName() {
        builder.hasLastName("smith");

        LikeFilter filter = (LikeFilter) ((AndFilter) builder.buildFilter()).getFilters().get(0);

        assertEquals("Equal is expected.", "smith", filter.getValue());

    }

    /**
     * Test method hasPhoneNumber.
     *
     */
    public void testHasPhoneNumber() {
        builder.hasPhoneNumber("020-115");
        EqualToFilter filter = (EqualToFilter) ((AndFilter) builder.buildFilter()).getFilters().get(0);

        assertEquals("Equal is expected.", "020-115", filter.getValue());

    }

    /**
     * Test method hasEmail.
     *
     */
    public void testHasEmail() {
        builder.hasEmail("payments@topcoder.com");

        EqualToFilter filter = (EqualToFilter) ((AndFilter) builder.buildFilter()).getFilters().get(0);

        assertEquals("Equal is expected.", "payments@topcoder.com", filter.getValue());

    }

    /**
     * Test method hasStreetAddress.
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
     * Test method hasCity.
     *
     */
    public void testHasCity() {
        builder.hasCity("new york");

        EqualToFilter filter = (EqualToFilter) ((AndFilter) builder.buildFilter()).getFilters().get(0);

        assertEquals("Equal is expected.", "new york", filter.getValue());
    }

    /**
     * Test method hasState.
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
        builder.hasUsername("topcoder");

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
