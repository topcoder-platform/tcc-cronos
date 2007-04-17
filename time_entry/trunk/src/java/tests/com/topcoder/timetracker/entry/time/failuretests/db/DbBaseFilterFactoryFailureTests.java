/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time.failuretests.db;

import com.topcoder.timetracker.entry.time.StringMatchType;
import com.topcoder.timetracker.entry.time.db.DbBaseFilterFactory;

import junit.framework.TestCase;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * <p>
 * Failure test cases for DbBaseFilterFactory.
 * </p>
 *
 * @author KLW
 * @version 3.2
 */
public class DbBaseFilterFactoryFailureTests extends TestCase {
    /**
     * <p>
     * The DbBaseFilterFactory instance for testing.
     * </p>
     */
    private DbBaseFilterFactory instance;

    /**
     * <p>
     * The columnNames map for testing.
     * </p>
     */
    private Map columnNames;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     */
    protected void setUp() {
        columnNames = new HashMap();
        columnNames.put(DbBaseFilterFactory.CREATION_DATE_COLUMN_NAME, "creation_date");
        columnNames.put(DbBaseFilterFactory.MODIFICATION_DATE_COLUMN_NAME, "modification_date");
        columnNames.put(DbBaseFilterFactory.CREATION_USER_COLUMN_NAME, "creation_user");
        columnNames.put(DbBaseFilterFactory.MODIFICATION_USER_COLUMN_NAME, "modification_user");

        instance = new DbBaseFilterFactory(columnNames);
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     */
    protected void tearDown() {
        instance = null;
        columnNames = null;
    }

    /**
     * <p>
     * Tests ctor DbBaseFilterFactory#DbBaseFilterFactory(Map) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when columnNames is null and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor1() {
        try {
            new DbBaseFilterFactory(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DbBaseFilterFactory#DbBaseFilterFactory(Map) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when key is empty and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor2() {
        columnNames.put(" ", "modification_user");

        try {
            new DbBaseFilterFactory(columnNames);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DbBaseFilterFactory#DbBaseFilterFactory(Map) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when key is not String type and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor3() {
        columnNames.put(new Long(8), "modification_user");

        try {
            new DbBaseFilterFactory(columnNames);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DbBaseFilterFactory#DbBaseFilterFactory(Map) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when columnNames is missing some keys and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor4() {
        columnNames.remove(DbBaseFilterFactory.MODIFICATION_USER_COLUMN_NAME);

        try {
            new DbBaseFilterFactory(columnNames);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DbBaseFilterFactory#DbBaseFilterFactory(Map) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when value is empty and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor5() {
        columnNames.put(DbBaseFilterFactory.MODIFICATION_USER_COLUMN_NAME, " ");

        try {
            new DbBaseFilterFactory(columnNames);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DbBaseFilterFactory#DbBaseFilterFactory(Map) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when value is not String type and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor6() {
        columnNames.put(DbBaseFilterFactory.MODIFICATION_USER_COLUMN_NAME, new Long(8));

        try {
            new DbBaseFilterFactory(columnNames);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbBaseFilterFactory#createCreationDateFilter(Date,Date) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when both arguments are null and expects IllegalArgumentException.
     * </p>
     */
    public void testCreateCreationDateFilter1() {
        try {
            instance.createCreationDateFilter(null, null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbBaseFilterFactory#createCreationDateFilter(Date,Date) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when rangeStart > rangeEnd and expects IllegalArgumentException.
     * </p>
     */
    public void testCreateCreationDateFilter2() {
        try {
            instance.createCreationDateFilter(new Date(20000), new Date(10000));
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbBaseFilterFactory#createModificationDateFilter(Date,Date) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when both arguments are null and expects IllegalArgumentException.
     * </p>
     */
    public void testCreateModificationDateFilter1() {
        try {
            instance.createModificationDateFilter(null, null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbBaseFilterFactory#createModificationDateFilter(Date,Date) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when rangeStart &gt; rangeEnd and expects IllegalArgumentException.
     * </p>
     */
    public void testCreateModificationDateFilter2() {
        try {
            instance.createModificationDateFilter(new Date(20000), new Date(10000));
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbBaseFilterFactory#createCreationUserFilter(String, StringMatchType) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when matchType is null and expects IllegalArgumentException.
     * </p>
     */
    public void testCreateCreationUserFilter1() {
        try {
            instance.createCreationUserFilter("username", null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbBaseFilterFactory#createCreationUserFilter(String, StringMatchType) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when username is null and expects IllegalArgumentException.
     * </p>
     */
    public void testCreateCreationUserFilter2() {
        try {
            instance.createCreationUserFilter(null, StringMatchType.ENDS_WITH);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbBaseFilterFactory#createCreationUserFilter(String, StringMatchType) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when username is empty and expects IllegalArgumentException.
     * </p>
     */
    public void testCreateCreationUserFilter3() {
        try {
            instance.createCreationUserFilter(" ", StringMatchType.ENDS_WITH);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbBaseFilterFactory#createModificationUserFilter(String, StringMatchType) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when matchType is null and expects IllegalArgumentException.
     * </p>
     */
    public void testCreateModificationUserFilter1() {
        try {
            instance.createModificationUserFilter("username", null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbBaseFilterFactory#createModificationUserFilter(String, StringMatchType) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when username is null and expects IllegalArgumentException.
     * </p>
     */
    public void testCreateModificationUserFilter2() {
        try {
            instance.createModificationUserFilter(null, StringMatchType.STARTS_WITH);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbBaseFilterFactory#createModificationUserFilter(String, StringMatchType) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when username is empty and expects IllegalArgumentException.
     * </p>
     */
    public void testCreateModificationUserFilter3() {
        try {
            instance.createModificationUserFilter(" ", StringMatchType.STARTS_WITH);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }
}
