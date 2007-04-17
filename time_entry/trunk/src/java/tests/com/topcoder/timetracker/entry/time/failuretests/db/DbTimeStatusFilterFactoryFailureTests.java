/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time.failuretests.db;

import com.topcoder.timetracker.entry.time.StringMatchType;
import com.topcoder.timetracker.entry.time.db.DbTimeStatusFilterFactory;

import junit.framework.TestCase;

import java.util.HashMap;
import java.util.Map;


/**
 * <p>
 * Failure test cases for DbTimeStatusFilterFactory.
 * </p>
 *
 * @author KLW
 * @version 3.2
 */
public class DbTimeStatusFilterFactoryFailureTests extends TestCase {
    /**
     * <p>
     * The DbTimeStatusFilterFactory instance for testing.
     * </p>
     */
    private DbTimeStatusFilterFactory instance;

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
        columnNames.put(DbTimeStatusFilterFactory.CREATION_DATE_COLUMN_NAME, "creation_date");
        columnNames.put(DbTimeStatusFilterFactory.MODIFICATION_DATE_COLUMN_NAME, "modification_date");
        columnNames.put(DbTimeStatusFilterFactory.CREATION_USER_COLUMN_NAME, "creation_user");
        columnNames.put(DbTimeStatusFilterFactory.MODIFICATION_USER_COLUMN_NAME, "modification_user");
        columnNames.put(DbTimeStatusFilterFactory.DESCRIPTION_COLUMN_NAME, "description");

        instance = new DbTimeStatusFilterFactory(columnNames);
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
     * Tests ctor DbTimeStatusFilterFactory#DbTimeStatusFilterFactory(Map) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when columnNames is null and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor1() {
        try {
            new DbTimeStatusFilterFactory(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DbTimeStatusFilterFactory#DbTimeStatusFilterFactory(Map) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when key is empty and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor2() {
        columnNames.put(" ", "modification_user");

        try {
            new DbTimeStatusFilterFactory(columnNames);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DbTimeStatusFilterFactory#DbTimeStatusFilterFactory(Map) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when key is not String type and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor3() {
        columnNames.put(new Long(8), "modification_user");

        try {
            new DbTimeStatusFilterFactory(columnNames);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DbTimeStatusFilterFactory#DbTimeStatusFilterFactory(Map) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when columnNames is missing some keys and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor4() {
        columnNames.remove(DbTimeStatusFilterFactory.MODIFICATION_USER_COLUMN_NAME);

        try {
            new DbTimeStatusFilterFactory(columnNames);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DbTimeStatusFilterFactory#DbTimeStatusFilterFactory(Map) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when value is empty and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor5() {
        columnNames.put(DbTimeStatusFilterFactory.MODIFICATION_USER_COLUMN_NAME, " ");

        try {
            new DbTimeStatusFilterFactory(columnNames);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DbTimeStatusFilterFactory#DbTimeStatusFilterFactory(Map) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when value is not String type and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor6() {
        columnNames.put(DbTimeStatusFilterFactory.MODIFICATION_USER_COLUMN_NAME, new Long(8));

        try {
            new DbTimeStatusFilterFactory(columnNames);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTimeStatusFilterFactory#createDescriptionFilter(String,StringMatchType) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when description is null and expects IllegalArgumentException.
     * </p>
     */
    public void testCreateDescriptionFilter1() {
        try {
            instance.createDescriptionFilter(null, StringMatchType.ENDS_WITH);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTimeStatusFilterFactory#createDescriptionFilter(String,StringMatchType) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when description is empty and expects IllegalArgumentException.
     * </p>
     */
    public void testCreateDescriptionFilter2() {
        try {
            instance.createDescriptionFilter(" ", StringMatchType.ENDS_WITH);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTimeStatusFilterFactory#createDescriptionFilter(String,StringMatchType) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when matchType is null and expects IllegalArgumentException.
     * </p>
     */
    public void testCreateDescriptionFilter3() {
        try {
            instance.createDescriptionFilter("description", null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }
}
