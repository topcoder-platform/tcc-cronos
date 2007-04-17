/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.contact;

import java.util.Date;

import com.topcoder.search.builder.filter.AndFilter;
import com.topcoder.search.builder.filter.BetweenFilter;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.search.builder.filter.GreaterThanOrEqualToFilter;
import com.topcoder.search.builder.filter.LessThanOrEqualToFilter;
import com.topcoder.search.builder.filter.NotFilter;
import com.topcoder.search.builder.filter.OrFilter;

import junit.framework.TestCase;

/**
 * <p>This test case contains accuracy tests for <code>ContactFilterFactory</code>.</p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class ContactFilterFactoryTestAcc extends TestCase {
    /**
     * <p>
     * Test method <code>createCreatedInFilter()</code>.
     * </p>
     */
    public void testCreateCreatedInFilter() {
        Date current = new Date();
        Filter filter = ContactFilterFactory.createCreatedInFilter(current, null);
        assertTrue("To date is null, GreaterThanOrEqualToFilter expected",
            filter instanceof GreaterThanOrEqualToFilter);
        assertEquals("LowerThreshold should be " + current, current,
            ((GreaterThanOrEqualToFilter) filter).getLowerThreshold());
        assertEquals(ContactFilterFactory.CREATION_DATE, ((GreaterThanOrEqualToFilter) filter).getName());

        filter = ContactFilterFactory.createCreatedInFilter(null, current);
        assertTrue("From date is null, LessThanOrEqualToFilter expected",
                filter instanceof LessThanOrEqualToFilter);
        assertEquals("UpperThreshold should be " + current, current,
                ((LessThanOrEqualToFilter) filter).getUpperThreshold());
        assertEquals(ContactFilterFactory.CREATION_DATE, ((LessThanOrEqualToFilter) filter).getName());

        filter = ContactFilterFactory.createCreatedInFilter(current, current);
        assertTrue("To date and From date are both not null, BetweenFilter expected",
                filter instanceof BetweenFilter);
        assertEquals("UpperThreshold should be " + current, current,
                ((BetweenFilter) filter).getUpperThreshold());
        assertEquals("LowerThreshold should be " + current, current,
                ((BetweenFilter) filter).getLowerThreshold());
        assertEquals(ContactFilterFactory.CREATION_DATE, ((BetweenFilter) filter).getName());
    }
    /**
     * <p>
     * Test method <code>createModifiedInFilter()</code>.
     * </p>
     */
    public void testCreateModifiedInFilter() {
        Date current = new Date();
        Filter filter = ContactFilterFactory.createModifiedInFilter(current, null);
        assertTrue("To date is null, GreaterThanOrEqualToFilter expected",
            filter instanceof GreaterThanOrEqualToFilter);
        assertEquals("LowerThreshold should be " + current, current,
            ((GreaterThanOrEqualToFilter) filter).getLowerThreshold());
        assertEquals(ContactFilterFactory.MODIFICATION_DATE, ((GreaterThanOrEqualToFilter) filter).getName());

        filter = ContactFilterFactory.createModifiedInFilter(null, current);
        assertTrue("From date is null, LessThanOrEqualToFilter expected",
                filter instanceof LessThanOrEqualToFilter);
        assertEquals("UpperThreshold should be " + current, current,
                ((LessThanOrEqualToFilter) filter).getUpperThreshold());
        assertEquals(ContactFilterFactory.MODIFICATION_DATE, ((LessThanOrEqualToFilter) filter).getName());

        filter = ContactFilterFactory.createModifiedInFilter(current, current);
        assertTrue("To date and From date are both not null, BetweenFilter expected",
                filter instanceof BetweenFilter);
        assertEquals("UpperThreshold should be " + current, current,
                ((BetweenFilter) filter).getUpperThreshold());
        assertEquals("LowerThreshold should be " + current, current,
                ((BetweenFilter) filter).getLowerThreshold());
        assertEquals(ContactFilterFactory.MODIFICATION_DATE, ((BetweenFilter) filter).getName());
    }
    /**
     * <p>
     * Test method <code>createCreatedByFilter()</code>.
     * </p>
     */
    public void testCreateCreatedByFilter() {
        Filter filter = ContactFilterFactory.createCreatedByFilter("user");
        assertTrue(filter instanceof EqualToFilter);
        assertEquals("user", ((EqualToFilter) filter).getValue());
        assertEquals(ContactFilterFactory.CREATION_USER, ((EqualToFilter) filter).getName());
    }
    /**
     * <p>
     * Test method <code>createModifiedByFilter()</code>.
     * </p>
     */
    public void testCreateModifiedByFilter() {
        Filter filter = ContactFilterFactory.createModifiedByFilter("user");
        assertTrue(filter instanceof EqualToFilter);
        assertEquals("user", ((EqualToFilter) filter).getValue());
        assertEquals(ContactFilterFactory.MODIFICATION_USER, ((EqualToFilter) filter).getName());
    }
    /**
     * <p>
     * Test method <code>createModifiedByFilter()</code>.
     * </p>
     */
    public void testCreateEmailAddressFilter() {
        Filter filter = ContactFilterFactory.createEmailAddressFilter("email");
        assertTrue(filter instanceof EqualToFilter);
        assertEquals("email", ((EqualToFilter) filter).getValue());
        assertEquals(ContactFilterFactory.EMAIL, ((EqualToFilter) filter).getName());
    }
    /**
     * <p>
     * Test method <code>createPhoneNumberFilter()</code>.
     * </p>
     */
    public void testCreatePhoneNumberFilter() {
        Filter filter = ContactFilterFactory.createPhoneNumberFilter("phone");
        assertTrue(filter instanceof EqualToFilter);
        assertEquals("phone", ((EqualToFilter) filter).getValue());
        assertEquals(ContactFilterFactory.PHONE, ((EqualToFilter) filter).getName());
    }
    /**
     * <p>
     * Test method <code>createTypeFilter()</code>.
     * </p>
     */
    public void testCreateTypeFilter() {
        Filter filter = ContactFilterFactory.createTypeFilter(ContactType.PROJECT);
        assertTrue(filter instanceof EqualToFilter);
        assertEquals(new Long(ContactType.PROJECT.getId()), ((EqualToFilter) filter).getValue());
        assertEquals(ContactFilterFactory.CONTACT_TYPE_ID, ((EqualToFilter) filter).getName());
    }
    /**
     * <p>
     * Test method <code>createEntityIDFilter()</code>.
     * </p>
     */
    public void testCreateEntityIDFilter() {
        Filter filter = ContactFilterFactory.createEntityIDFilter(3, ContactType.PROJECT);
        assertTrue(filter instanceof AndFilter);
        assertEquals(2, ((AndFilter) filter).getFilters().size());
        EqualToFilter filter1 = (EqualToFilter) ((AndFilter) filter).getFilters().get(0);
        EqualToFilter filter2 = (EqualToFilter) ((AndFilter) filter).getFilters().get(1);
        assertEquals(new Long(ContactType.PROJECT.getId()), filter1.getValue());
        assertEquals(ContactFilterFactory.CONTACT_TYPE_ID, filter1.getName());
        assertEquals(new Long(3), filter2.getValue());
        assertEquals(ContactFilterFactory.ENTITY_ID, filter2.getName());
    }
    /**
     * <p>
     * Test method <code>andFilter()</code>.
     * </p>
     */
    public void testAndFilter() {
        Filter filter1 = ContactFilterFactory.createCreatedByFilter("user");
        Filter filter2 = ContactFilterFactory.createModifiedByFilter("user");
        Filter filter = ContactFilterFactory.andFilter(filter1, filter2);
        assertEquals(filter1, ((AndFilter) filter).getFilters().get(0));
        assertEquals(filter2, ((AndFilter) filter).getFilters().get(1));
    }
    /**
     * <p>
     * Test method <code>orFilter()</code>.
     * </p>
     */
    public void testOrFilter() {
        Filter filter1 = ContactFilterFactory.createCreatedByFilter("user");
        Filter filter2 = ContactFilterFactory.createModifiedByFilter("user");
        Filter filter = ContactFilterFactory.orFilter(filter1, filter2);
        assertEquals(filter1, ((OrFilter) filter).getFilters().get(0));
        assertEquals(filter2, ((OrFilter) filter).getFilters().get(1));
    }
    /**
     * <p>
     * Test method <code>notFilter()</code>.
     * </p>
     */
    public void testNotFilter() {
        EqualToFilter filter = (EqualToFilter) ContactFilterFactory.createCreatedByFilter("user");
        Filter notFilter = ContactFilterFactory.notFilter(filter);
        EqualToFilter notedFilter = (EqualToFilter) ((NotFilter) notFilter).getFilter();
        assertEquals(filter.getName(), notedFilter.getName());
        assertEquals(filter.getValue(), notedFilter.getValue());
    }
}
