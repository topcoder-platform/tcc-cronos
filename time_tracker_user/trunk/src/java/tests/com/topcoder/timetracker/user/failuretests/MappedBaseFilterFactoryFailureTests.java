/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user.failuretests;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.topcoder.timetracker.user.StringMatchType;
import com.topcoder.timetracker.user.filterfactory.MappedBaseFilterFactory;

import junit.framework.TestCase;

/**
 * <p>
 * Failure test for <code>{@link MappedBaseFilterFactory}</code> class.
 * </p>
 * @author FireIce
 * @version 1.0
 */
public class MappedBaseFilterFactoryFailureTests extends TestCase {
    /**
     * <p>
     * The columnNames map for testing.
     * </p>
     */
    private Map columnNames;

    /**
     * <p>
     * Represents the MappedBaseFilterFactory instance.
     * </p>
     */
    private MappedBaseFilterFactory mappedBaseFilterFactory;

    /**
     * <p>
     * </p>
     */
    protected void setUp() throws Exception {
        super.setUp();

        columnNames = new HashMap();
        columnNames.put("CREATION_DATE_COLUMN_NAME", "creationdate");
        columnNames.put("MODIFICATION_DATE_COLUMN_NAME", "modificationdate");
        columnNames.put("CREATION_USER_COLUMN_NAME", "creationuser");
        columnNames.put("MODIFICATION_USER_COLUMN_NAME", "modificationuser");

        mappedBaseFilterFactory = new MappedBaseFilterFactory(columnNames);
    }

    /**
     * <p>
     * Failure test for <code>{@link MappedBaseFilterFactory#MappedBaseFilterFactory(Map)}</code> method.
     * </p>
     */
    public void testMappedBaseFilterFactory_NullMap() {
        try {
            new MappedBaseFilterFactory(null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link MappedBaseFilterFactory#MappedBaseFilterFactory(Map)}</code> method.
     * </p>
     */
    public void testMappedBaseFilterFactory_MapContainsNullKey() {
        columnNames.put(null, "column");
        try {
            new MappedBaseFilterFactory(columnNames);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link MappedBaseFilterFactory#MappedBaseFilterFactory(Map)}</code> method.
     * </p>
     */
    public void testMappedBaseFilterFactory_MapContainsEmptyKey() {
        columnNames.put("", "column");
        try {
            new MappedBaseFilterFactory(columnNames);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link MappedBaseFilterFactory#MappedBaseFilterFactory(Map)}</code> method.
     * </p>
     */
    public void testMappedBaseFilterFactory_MapContainsTrimmedEmptyKey() {
        columnNames.put("  ", "column");
        try {
            new MappedBaseFilterFactory(columnNames);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link MappedBaseFilterFactory#MappedBaseFilterFactory(Map)}</code> method.
     * </p>
     */
    public void testMappedBaseFilterFactory_MapContainsNullValue() {
        columnNames.put("key", null);
        try {
            new MappedBaseFilterFactory(columnNames);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link MappedBaseFilterFactory#MappedBaseFilterFactory(Map)}</code> method.
     * </p>
     */
    public void testMappedBaseFilterFactory_MapContainsEmptyValue() {
        columnNames.put("key", "");
        try {
            new MappedBaseFilterFactory(columnNames);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link MappedBaseFilterFactory#MappedBaseFilterFactory(Map)}</code> method.
     * </p>
     */
    public void testMappedBaseFilterFactory_MapContainsTrimmedEmptyValue() {
        columnNames.put("key", " ");
        try {
            new MappedBaseFilterFactory(columnNames);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link MappedBaseFilterFactory#MappedBaseFilterFactory(Map)}</code> method.
     * </p>
     */
    public void testMappedBaseFilterFactory_MissingRequiredAttribute() {
        columnNames.remove(MappedBaseFilterFactory.CREATION_DATE_COLUMN_NAME);
        try {
            new MappedBaseFilterFactory(columnNames);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link MappedBaseFilterFactory#createCreationDateFilter(Date, Date)}</code> method.
     * </p>
     */
    public void testCreateCreationDateFilter_BothNull() {
        try {
            mappedBaseFilterFactory.createCreationDateFilter(null, null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link MappedBaseFilterFactory#createCreationDateFilter(Date, Date)}</code> method.
     * </p>
     */
    public void testCreateCreationDateFilter_EndDateBeforeStartDate() {
        try {
            mappedBaseFilterFactory.createCreationDateFilter(new Date(System.currentTimeMillis()), new Date(System
                .currentTimeMillis() - 1000));
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link MappedBaseFilterFactory#createModificationDateFilter(Date, Date)}</code> method.
     * </p>
     */
    public void testCreateModificationDateFilter_BothNull() {
        try {
            mappedBaseFilterFactory.createModificationDateFilter(null, null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link MappedBaseFilterFactory#createModificationDateFilter(Date, Date)}</code> method.
     * </p>
     */
    public void testCreateModificationDateFilter_EndDateBeforeStartDate() {
        try {
            mappedBaseFilterFactory.createModificationDateFilter(new Date(System.currentTimeMillis()), new Date(System
                .currentTimeMillis() - 1000));
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link MappedBaseFilterFactory#createCreationUserFilter(StringMatchType, String)}</code>
     * method.
     * </p>
     */
    public void testCreateCreationUserFilter_NullStringMatchType() {
        try {
            mappedBaseFilterFactory.createCreationUserFilter(null, "username");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link MappedBaseFilterFactory#createCreationUserFilter(StringMatchType, String)}</code>
     * method.
     * </p>
     */
    public void testCreateCreationUserFilter_NullUserName() {
        try {
            mappedBaseFilterFactory.createCreationUserFilter(StringMatchType.EXACT_MATCH, null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link MappedBaseFilterFactory#createCreationUserFilter(StringMatchType, String)}</code>
     * method.
     * </p>
     */
    public void testCreateCreationUserFilter_EmptyUserName() {
        try {
            mappedBaseFilterFactory.createCreationUserFilter(StringMatchType.EXACT_MATCH, "");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link MappedBaseFilterFactory#createCreationUserFilter(StringMatchType, String)}</code>
     * method.
     * </p>
     */
    public void testCreateCreationUserFilter_TrimmedEmptyUserName() {
        try {
            mappedBaseFilterFactory.createCreationUserFilter(StringMatchType.EXACT_MATCH, " ");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link MappedBaseFilterFactory#createModificationUserFilter(StringMatchType, String)}</code> method.
     * </p>
     */
    public void testCreateModificationUserFilter_NullUserName() {
        try {
            mappedBaseFilterFactory.createModificationUserFilter(StringMatchType.EXACT_MATCH, null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link MappedBaseFilterFactory#createModificationUserFilter(StringMatchType, String)}</code> method.
     * </p>
     */
    public void testCreateModificationUserFilter_EmptyUserName() {
        try {
            mappedBaseFilterFactory.createModificationUserFilter(StringMatchType.EXACT_MATCH, "");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link MappedBaseFilterFactory#createModificationUserFilter(StringMatchType, String)}</code> method.
     * </p>
     */
    public void testCreateModificationUserFilter_TrimmedEmptyUserName() {
        try {
            mappedBaseFilterFactory.createModificationUserFilter(StringMatchType.EXACT_MATCH, " ");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
