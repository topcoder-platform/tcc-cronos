/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user.filterfactory;

import java.util.Calendar;

import com.topcoder.search.builder.filter.BetweenFilter;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.GreaterThanOrEqualToFilter;
import com.topcoder.search.builder.filter.LessThanOrEqualToFilter;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for <code>UserStatusFilterFactory</code>.
 *
 * @author enefem21
 * @version 3.2.1
 * @since 3.2.1
 */
public class UserStatusFilterFactoryTest extends TestCase {

    /**
     * <p>
     * Returns the suite for this unit test.
     * </p>
     *
     * @return the suite
     */
    public static Test suite() {
        return new TestSuite(UserStatusFilterFactoryTest.class);
    }

    /**
     * Test <code>createCompanyIdFilter</code> for accuracy. Condition: normal. Expect: the filter is returned
     * properly.
     */
    public void testCreateCompanyIdFilterAccuracy() {
        EqualToFilter filter = (EqualToFilter) UserStatusFilterFactory.createDescriptionFilter("description");
        assertEquals("Failed to create the description filter correctly.", "DESCRIPTION", filter.getName());
    }

    /**
     * Test <code>createCompanyIdFilter</code> for failure. Condition: companyId is negative. Expect:
     * <code>IllegalArgumentException</code>.
     */
    public void testCreateCompanyIdFilterNegative() {
        try {
            UserStatusFilterFactory.createCompanyIdFilter(-3);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>createDescriptionFilter</code> for accuracy. Condition: normal. Expect: the filter is returned
     * properly.
     */
    public void testCreateDescriptionFilterAccuracy() {
        EqualToFilter descriptionFilter =
            (EqualToFilter) UserStatusFilterFactory.createDescriptionFilter("testDescription");
        assertEquals("The column name is not as expected", "DESCRIPTION", descriptionFilter.getName());
        assertEquals("The value is not as expected", "testDescription", descriptionFilter.getValue());
    }

    /**
     * Test <code>createDescriptionFilter</code> for failure. Condition: description is null. Expect:
     * <code>IllegalArgumentException</code>.
     */
    public void testCreateDescriptionFilterNull() {
        try {
            UserStatusFilterFactory.createDescriptionFilter(null);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>createDescriptionFilter</code> for failure. Condition: description is empty. Expect:
     * <code>IllegalArgumentException</code>.
     */
    public void testCreateDescriptionFilterEmpty() {
        try {
            UserStatusFilterFactory.createDescriptionFilter("  ");
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>createActiveFilter</code> for accuracy. Condition: normal. Expect: the filter is returned
     * properly.
     */
    public void testCreateActiveFilterAccuracy() {
        EqualToFilter activeFilter = (EqualToFilter) UserStatusFilterFactory.createActiveFilter(true);
        assertEquals("The column name is not as expected", "ACTIVE", activeFilter.getName());
        assertEquals("The value is not as expected", new Integer(1), activeFilter.getValue());
    }

    /**
     * Test <code>createCreationDateFilter</code> for accuracy. Condition: normal. Expect: the filter is created
     * properly.
     */
    public void testCreateCreationDateFilterAccuracy1() {
        Calendar instance1 = Calendar.getInstance();
        instance1.set(1981, Calendar.JUNE, 15);

        Calendar instance2 = Calendar.getInstance();
        instance2.set(1981, Calendar.JUNE, 20);

        BetweenFilter filter =
            (BetweenFilter) UserStatusFilterFactory.createCreationDateFilter(instance1.getTime(), instance2
                .getTime());

        assertEquals("the column name is not as expected", "CREATION_DATE", filter.getName());
        assertEquals("the range start is not as expected", instance1.getTime(), filter.getLowerThreshold());
        assertEquals("the range end is not as expected", instance2.getTime(), filter.getUpperThreshold());
    }

    /**
     * Test <code>createCreationDateFilter</code> for accuracy. Condition: normal. Expect: the filter is created
     * properly.
     */
    public void testCreateCreationDateFilterAccuracy2() {
        Calendar instance1 = Calendar.getInstance();
        instance1.set(1981, Calendar.JUNE, 15);

        GreaterThanOrEqualToFilter filter =
            (GreaterThanOrEqualToFilter) UserStatusFilterFactory.createCreationDateFilter(
                instance1.getTime(), null);

        assertEquals("the column name is not as expected", "CREATION_DATE", filter.getName());
        assertEquals("the range start is not as expected", instance1.getTime(), filter.getValue());
    }

    /**
     * Test <code>createCreationDateFilter</code> for accuracy. Condition: normal. Expect: the filter is created
     * properly.
     */
    public void testCreateCreationDateFilterAccuracy3() {
        Calendar instance1 = Calendar.getInstance();
        instance1.set(1981, Calendar.JUNE, 15);

        LessThanOrEqualToFilter filter =
            (LessThanOrEqualToFilter) UserStatusFilterFactory.createCreationDateFilter(null, instance1
                .getTime());

        assertEquals("the column name is not as expected", "CREATION_DATE", filter.getName());
        assertEquals("the range end is not as expected", instance1.getTime(), filter.getValue());
    }

    /**
     * Test <code>createCreationDateFilter</code> for failure. Condition: both parameters are null. Expect:
     * <code>IllegalArgumentException</code>.
     */
    public void testCreateCreationDateFilterBothNull() {
        try {
            UserStatusFilterFactory.createCreationDateFilter(null, null);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>createModificationDateFilter</code> for accuracy. Condition: normal. Expect: the filter is
     * created properly.
     */
    public void testCreateModificationDateFilterAccuracy1() {
        Calendar instance1 = Calendar.getInstance();
        instance1.set(1981, Calendar.JUNE, 15);

        Calendar instance2 = Calendar.getInstance();
        instance2.set(1981, Calendar.JUNE, 20);

        BetweenFilter filter =
            (BetweenFilter) UserStatusFilterFactory.createModificationDateFilter(instance1.getTime(),
                instance2.getTime());

        assertEquals("the column name is not as expected", "MODIFICATION_DATE", filter.getName());
        assertEquals("the range start is not as expected", instance1.getTime(), filter.getLowerThreshold());
        assertEquals("the range end is not as expected", instance2.getTime(), filter.getUpperThreshold());
    }

    /**
     * Test <code>createModificationDateFilter</code> for accuracy. Condition: normal. Expect: the filter is
     * created properly.
     */
    public void testCreateModificationDateFilterAccuracy2() {
        Calendar instance1 = Calendar.getInstance();
        instance1.set(1981, Calendar.JUNE, 15);

        GreaterThanOrEqualToFilter filter =
            (GreaterThanOrEqualToFilter) UserStatusFilterFactory.createModificationDateFilter(instance1
                .getTime(), null);

        assertEquals("the column name is not as expected", "MODIFICATION_DATE", filter.getName());
        assertEquals("the range start is not as expected", instance1.getTime(), filter.getValue());
    }

    /**
     * Test <code>createModificationDateFilter</code> for accuracy. Condition: normal. Expect: the filter is
     * created properly.
     */
    public void testCreateModificationDateFilterAccuracy3() {
        Calendar instance1 = Calendar.getInstance();
        instance1.set(1981, Calendar.JUNE, 15);

        LessThanOrEqualToFilter filter =
            (LessThanOrEqualToFilter) UserStatusFilterFactory.createModificationDateFilter(null, instance1
                .getTime());

        assertEquals("the column name is not as expected", "MODIFICATION_DATE", filter.getName());
        assertEquals("the range end is not as expected", instance1.getTime(), filter.getValue());
    }

    /**
     * Test <code>createModificationDateFilter</code> for failure. Condition: both parameters are null. Expect:
     * <code>IllegalArgumentException</code>.
     */
    public void testCreateModificationDateFilterBothNull() {
        try {
            UserStatusFilterFactory.createModificationDateFilter(null, null);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>createCreationUserFilter</code> for accuracy. Condition: normal. Expect: the filter is returned
     * properly.
     */
    public void testCreateCreationUserFilterAccuracy() {
        EqualToFilter creationUserFilter =
            (EqualToFilter) UserStatusFilterFactory.createCreationUserFilter("testCreationUser");
        assertEquals("The column name is not as expected", "CREATION_USER", creationUserFilter.getName());
        assertEquals("The value is not as expected", "testCreationUser", creationUserFilter.getValue());
    }

    /**
     * Test <code>createCreationUserFilter</code> for failure. Condition: username is null. Expect:
     * <code>IllegalArgumentException</code>.
     */
    public void testCreateCreationUserFilterNull() {
        try {
            UserStatusFilterFactory.createCreationUserFilter(null);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>createCreationUserFilter</code> for failure. Condition: username is empty. Expect:
     * <code>IllegalArgumentException</code>.
     */
    public void testCreateCreationUserFilterEmpty() {
        try {
            UserStatusFilterFactory.createCreationUserFilter("  ");
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>createModificationUserFilter</code> for accuracy. Condition: normal. Expect: the filter is
     * returned properly.
     */
    public void testCreateModificationUserFilterAccuracy() {
        EqualToFilter modificationUserFilter =
            (EqualToFilter) UserStatusFilterFactory.createModificationUserFilter("testModificationUser");
        assertEquals("The column name is not as expected", "MODIFICATION_USER", modificationUserFilter
            .getName());
        assertEquals("The value is not as expected", "testModificationUser", modificationUserFilter
            .getValue());
    }

    /**
     * Test <code>createModificationUserFilter</code> for failure. Condition: username is null. Expect:
     * <code>IllegalArgumentException</code>.
     */
    public void testCreateModificationUserFilterNull() {
        try {
            UserStatusFilterFactory.createModificationUserFilter(null);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>createModificationUserFilter</code> for failure. Condition: username is empty. Expect:
     * <code>IllegalArgumentException</code>.
     */
    public void testCreateModificationUserFilterEmpty() {
        try {
            UserStatusFilterFactory.createModificationUserFilter("  ");
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

}
