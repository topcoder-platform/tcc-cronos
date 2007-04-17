package com.topcoder.timetracker.entry.time.failuretests.db;

import java.util.HashMap;
import java.util.Map;

import com.topcoder.timetracker.entry.time.StringMatchType;
import com.topcoder.timetracker.entry.time.db.DbTaskTypeFilterFactory;

import junit.framework.TestCase;
/**
 * <p>
 * Failure test cases for DbTaskTypeFilterFactory.
 * </p>
 *
 * @author KLW
 * @version 3.2
 */
public class DbTaskTypeFilterFactoryFailureTests extends TestCase {
    /**
     * <p>
     * The DbTaskTypeFilterFactory instance for testing.
     * </p>
     */
    private DbTaskTypeFilterFactory instance;

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
        columnNames.put(DbTaskTypeFilterFactory.CREATION_DATE_COLUMN_NAME, "creation_date");
        columnNames.put(DbTaskTypeFilterFactory.MODIFICATION_DATE_COLUMN_NAME, "modification_date");
        columnNames.put(DbTaskTypeFilterFactory.CREATION_USER_COLUMN_NAME, "creation_user");
        columnNames.put(DbTaskTypeFilterFactory.MODIFICATION_USER_COLUMN_NAME, "modification_user");
        columnNames.put(DbTaskTypeFilterFactory.COMPANY_ID_COLUMN_NAME, "comp_company_id");
        columnNames.put(DbTaskTypeFilterFactory.DESCRIPTION_COLUMN_NAME, "description");
        columnNames.put(DbTaskTypeFilterFactory.ACTIVE_COLUMN_NAME, "active");

        instance = new DbTaskTypeFilterFactory(columnNames);
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
     * Tests ctor DbTaskTypeFilterFactory#DbTaskTypeFilterFactory(Map) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when columnNames is null and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor1() {
        try {
            new DbTaskTypeFilterFactory(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DbTaskTypeFilterFactory#DbTaskTypeFilterFactory(Map) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when key is empty and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor2() {
        columnNames.put(" ", "modification_user");
        try {
            new DbTaskTypeFilterFactory(columnNames);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DbTaskTypeFilterFactory#DbTaskTypeFilterFactory(Map) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when key is not String type and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor3() {
        columnNames.put(new Long(8), "modification_user");
        try {
            new DbTaskTypeFilterFactory(columnNames);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DbTaskTypeFilterFactory#DbTaskTypeFilterFactory(Map) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when columnNames is missing some keys and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor4() {
        columnNames.remove(DbTaskTypeFilterFactory.MODIFICATION_USER_COLUMN_NAME);
        try {
            new DbTaskTypeFilterFactory(columnNames);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DbTaskTypeFilterFactory#DbTaskTypeFilterFactory(Map) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when value is empty and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor5() {
        columnNames.put(DbTaskTypeFilterFactory.MODIFICATION_USER_COLUMN_NAME, " ");
        try {
            new DbTaskTypeFilterFactory(columnNames);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DbTaskTypeFilterFactory#DbTaskTypeFilterFactory(Map) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when value is not String type and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor6() {
        columnNames.put(DbTaskTypeFilterFactory.MODIFICATION_USER_COLUMN_NAME, new Long(8));
        try {
            new DbTaskTypeFilterFactory(columnNames);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }
    /**
     * <p>
     * Tests DbTaskTypeFilterFactory#createDescriptionFilter(String,StringMatchType) for failure.
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
     * Tests DbTaskTypeFilterFactory#createDescriptionFilter(String,StringMatchType) for failure.
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
     * Tests DbTaskTypeFilterFactory#createDescriptionFilter(String,StringMatchType) for failure.
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
    /**
     * <p>
     * Tests DbTaskTypeFilterFactory#createCompanyIdFilter(long) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when companyId is negative and expects IllegalArgumentException.
     * </p>
     */
    public void testCreateCompanyIdFilter() {
        try {
            instance.createCompanyIdFilter(-999);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

}
