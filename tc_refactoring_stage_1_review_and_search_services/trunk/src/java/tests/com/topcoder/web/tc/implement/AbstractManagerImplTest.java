/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.tc.implement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.web.tc.SortingOrder;
import com.topcoder.web.tc.dto.AbstractContestsFilter;
import com.topcoder.web.tc.dto.DateIntervalSpecification;
import com.topcoder.web.tc.dto.DateIntervalType;

/**
 * <p>
 * Tests the AbstractManagerImpl class.
 * </p>
 * @author duxiaoyang
 * @version 1.0
 */
public class AbstractManagerImplTest {

    /**
     * Represents the manager to test.
     */
    private AbstractManagerImpl manager;

    /**
     * <p>
     * Sets up testing environment. Initializes variable.
     * </p>
     */
    @Before
    public void setUp() {
        manager = new DummyManagerImpl();
    }

    /**
     * <p>
     * Tears down testing environment. Sets variable to null.
     * </p>
     */
    @After
    public void tearDown() {
        manager = null;
    }

    /**
     * <p>
     * Tests the default constructor. It verifies nothing.
     * </p>
     */
    @Test
    public void testCtor() {
        new DummyManagerImpl();
    }

    /**
     * <p>
     * Tests the setLogger(Logger) and getLogger() methods. It verifies that the logger can be set and retrieved
     * correctly.
     * </p>
     */
    @Test
    public void testSetGetLogger() {
        // null
        manager.setLogger(null);
        assertNull("logger should be set", manager.getLogger());
        // non-null
        Logger logger = Logger.getLogger(getClass());
        manager.setLogger(logger);
        assertEquals("logger should be set", logger, manager.getLogger());
    }

    /**
     * <p>
     * Tests the setLoggerName(String) method. It verifies that the logger can be set correctly.
     * </p>
     */
    @Test
    public void testSetLoggerName() {
        manager.setLoggerName("test");
        assertEquals("logger should be set", "test", manager.getLogger().getName());
    }

    /**
     * <p>
     * Tests the setCommonFilterClause(AbstractContestsFilter) method. It uses an empty filter and verifies that no
     * where clause is returned.
     * </p>
     */
    @Test
    public void testGetCommonFilterClause_AllEmpty() {
        String result = manager.getCommonFilterClause(new AbstractContestsFilter() {
        });
        assertFalse("clause for type should not be included", result.contains(":type"));
        assertFalse("clause for subtype should not be included", result.contains(":subtype"));
        assertFalse("clause for catalog should not be included", result.contains(":catalog"));
        assertFalse("clause for registration start should not be included", result.contains(":regStart"));
        assertFalse("clause for submission end should not be included", result.contains(":subEnd"));
    }

    /**
     * <p>
     * Tests the getCommonFilterClause(AbstractContestsFilter) method. It verifies that type can be set correctly.
     * </p>
     */
    @Test
    public void testGetCommonFilterClause_Type() {
        AbstractContestsFilter filter = new AbstractContestsFilter() {
        };
        filter.setType(new ArrayList<String>());
        String result = manager.getCommonFilterClause(filter);
        assertTrue("clause for type should be included", result.contains(":type"));
    }

    /**
     * <p>
     * Tests the getCommonFilterClause(AbstractContestsFilter) method. It verifies that subtype can be set correctly.
     * </p>
     */
    @Test
    public void testGetCommonFilterClause_Subtype() {
        AbstractContestsFilter filter = new AbstractContestsFilter() {
        };
        filter.setSubtype(new ArrayList<String>());
        String result = manager.getCommonFilterClause(filter);
        assertTrue("clause for subtype should be included", result.contains(":subtype"));
    }

    /**
     * <p>
     * Tests the getCommonFilterClause(AbstractContestsFilter) method. It verifies that catalog can be set correctly.
     * </p>
     */
    @Test
    public void testGetCommonFilterClause_Catalog() {
        AbstractContestsFilter filter = new AbstractContestsFilter() {
        };
        filter.setCatalog(new ArrayList<String>());
        String result = manager.getCommonFilterClause(filter);
        assertTrue("clause for catalog should be included", result.contains(":catalog"));
    }

    /**
     * <p>
     * Tests the getCommonFilterClause(AbstractContestsFilter) method. It verifies that registration start date (before)
     * can be set correctly.
     * </p>
     */
    @Test
    public void testGetCommonFilterClause_RegStart_Before() {
        AbstractContestsFilter filter = new AbstractContestsFilter() {
        };
        DateIntervalSpecification date = new DateIntervalSpecification();
        date.setIntervalType(DateIntervalType.BEFORE);
        filter.setRegistrationStart(date);
        String result = manager.getCommonFilterClause(filter);
        assertTrue("clause for registration start should be included", result.contains("< :regStart"));
    }

    /**
     * <p>
     * Tests the getCommonFilterClause(AbstractContestsFilter) method. It verifies that registration start date (after)
     * can be set correctly.
     * </p>
     */
    @Test
    public void testGetCommonFilterClause_RegStart_After() {
        AbstractContestsFilter filter = new AbstractContestsFilter() {
        };
        DateIntervalSpecification date = new DateIntervalSpecification();
        date.setIntervalType(DateIntervalType.AFTER);
        filter.setRegistrationStart(date);
        String result = manager.getCommonFilterClause(filter);
        assertTrue("clause for registration start should be included", result.contains("> :regStart"));
    }

    /**
     * <p>
     * Tests the getCommonFilterClause(AbstractContestsFilter) method. It verifies that registration start date (on) can
     * be set correctly.
     * </p>
     */
    @Test
    public void testGetCommonFilterClause_RegStart_On() {
        AbstractContestsFilter filter = new AbstractContestsFilter() {
        };
        DateIntervalSpecification date = new DateIntervalSpecification();
        date.setIntervalType(DateIntervalType.ON);
        filter.setRegistrationStart(date);
        String result = manager.getCommonFilterClause(filter);
        assertTrue("clause for registration start should be included", result.contains("= :regStart"));
    }

    /**
     * <p>
     * Tests the getCommonFilterClause(AbstractContestsFilter) method. It verifies that registration start date (before
     * current date) can be set correctly.
     * </p>
     */
    @Test
    public void testGetCommonFilterClause_RegStart_BeforeCurrent() {
        AbstractContestsFilter filter = new AbstractContestsFilter() {
        };
        DateIntervalSpecification date = new DateIntervalSpecification();
        date.setIntervalType(DateIntervalType.BEFORE_CURRENT_DATE);
        filter.setRegistrationStart(date);
        String result = manager.getCommonFilterClause(filter);
        assertTrue("clause for registration start should be included", result.contains("< CURRENT"));
    }

    /**
     * <p>
     * Tests the getCommonFilterClause(AbstractContestsFilter) method. It verifies that registration start date (after
     * current date) can be set correctly.
     * </p>
     */
    @Test
    public void testGetCommonFilterClause_RegStart_AfterCurrent() {
        AbstractContestsFilter filter = new AbstractContestsFilter() {
        };
        DateIntervalSpecification date = new DateIntervalSpecification();
        date.setIntervalType(DateIntervalType.AFTER_CURRENT_DATE);
        filter.setRegistrationStart(date);
        String result = manager.getCommonFilterClause(filter);
        assertTrue("clause for registration start should be included", result.contains("> CURRENT"));
    }

    /**
     * <p>
     * Tests the getCommonFilterClause(AbstractContestsFilter) method. It verifies that registration start date
     * (between) can be set correctly.
     * </p>
     */
    @Test
    public void testGetCommonFilterClause_RegStart_Between() {
        AbstractContestsFilter filter = new AbstractContestsFilter() {
        };
        DateIntervalSpecification date = new DateIntervalSpecification();
        date.setIntervalType(DateIntervalType.BETWEEN_DATES);
        filter.setRegistrationStart(date);
        String result = manager.getCommonFilterClause(filter);
        assertTrue("clause for registration start should be included",
                result.contains("BETWEEN :regStart1 AND :regStart2"));
    }

    /**
     * <p>
     * Tests the getCommonFilterClause(AbstractContestsFilter) method. It verifies that submission end date (before) can
     * be set correctly.
     * </p>
     */
    @Test
    public void testGetCommonFilterClause_SubEnd_Before() {
        AbstractContestsFilter filter = new AbstractContestsFilter() {
        };
        DateIntervalSpecification date = new DateIntervalSpecification();
        date.setIntervalType(DateIntervalType.BEFORE);
        filter.setSubmissionEnd(date);
        String result = manager.getCommonFilterClause(filter);
        assertTrue("clause for submission end should be included", result.contains("< :subEnd"));
    }

    /**
     * <p>
     * Tests the getCommonFilterClause(AbstractContestsFilter) method. It verifies that submission end date (after) can
     * be set correctly.
     * </p>
     */
    @Test
    public void testGetCommonFilterClause_SubEnd_After() {
        AbstractContestsFilter filter = new AbstractContestsFilter() {
        };
        DateIntervalSpecification date = new DateIntervalSpecification();
        date.setIntervalType(DateIntervalType.AFTER);
        filter.setSubmissionEnd(date);
        String result = manager.getCommonFilterClause(filter);
        assertTrue("clause for submission end should be included", result.contains("> :subEnd"));
    }

    /**
     * <p>
     * Tests the getCommonFilterClause(AbstractContestsFilter) method. It verifies that submission end date (on) can be
     * set correctly.
     * </p>
     */
    @Test
    public void testGetCommonFilterClause_SubEnd_On() {
        AbstractContestsFilter filter = new AbstractContestsFilter() {
        };
        DateIntervalSpecification date = new DateIntervalSpecification();
        date.setIntervalType(DateIntervalType.ON);
        filter.setSubmissionEnd(date);
        String result = manager.getCommonFilterClause(filter);
        assertTrue("clause for submission end should be included", result.contains("= :subEnd"));
    }

    /**
     * <p>
     * Tests the getCommonFilterClause(AbstractContestsFilter) method. It verifies that submission end date (before
     * current date) can be set correctly.
     * </p>
     */
    @Test
    public void testGetCommonFilterClause_SubEnd_BeforeCurrent() {
        AbstractContestsFilter filter = new AbstractContestsFilter() {
        };
        DateIntervalSpecification date = new DateIntervalSpecification();
        date.setIntervalType(DateIntervalType.BEFORE_CURRENT_DATE);
        filter.setSubmissionEnd(date);
        String result = manager.getCommonFilterClause(filter);
        assertTrue("clause for submission end should be included", result.contains("< CURRENT"));
    }

    /**
     * <p>
     * Tests the getCommonFilterClause(AbstractContestsFilter) method. It verifies that submission end date (after
     * current date) can be set correctly.
     * </p>
     */
    @Test
    public void testGetCommonFilterClause_SubEnd_AfterCurrent() {
        AbstractContestsFilter filter = new AbstractContestsFilter() {
        };
        DateIntervalSpecification date = new DateIntervalSpecification();
        date.setIntervalType(DateIntervalType.AFTER_CURRENT_DATE);
        filter.setSubmissionEnd(date);
        String result = manager.getCommonFilterClause(filter);
        assertTrue("clause for submission end should be included", result.contains("> CURRENT"));
    }

    /**
     * <p>
     * Tests the getCommonFilterClause(AbstractContestsFilter) method. It verifies that submission end date (between)
     * can be set correctly.
     * </p>
     */
    @Test
    public void testGetCommonFilterClause_SubEnd_Between() {
        AbstractContestsFilter filter = new AbstractContestsFilter() {
        };
        DateIntervalSpecification date = new DateIntervalSpecification();
        date.setIntervalType(DateIntervalType.BETWEEN_DATES);
        filter.setSubmissionEnd(date);
        String result = manager.getCommonFilterClause(filter);
        assertTrue("clause for submission end should be included", result.contains("BETWEEN :subEnd1 AND :subEnd2"));
    }

    /**
     * <p>
     * Tests the getSortingClause(String, SortingOrder) method. It verifies that default sorting is used if column name
     * is null.
     * </p>
     */
    @Test
    public void testGetSortingClause_NullColumnName() {
        String result = manager.getSortingClause(null, SortingOrder.ASCENDING);
        assertTrue("default sorting should be used", result.contains("ORDER BY contestStartTime DESC"));
    }

    /**
     * <p>
     * Tests the getSortingClause(String, SortingOrder) method. It verifies that default sorting is used if column name
     * is empty.
     * </p>
     */
    @Test
    public void testGetSortingClause_EmptyColumnName() {
        String result = manager.getSortingClause("", SortingOrder.ASCENDING);
        assertTrue("default sorting should be used", result.contains("ORDER BY contestStartTime DESC"));
    }

    /**
     * <p>
     * Tests the getSortingClause(String, SortingOrder) method. It verifies that default sorting is used if sorting
     * order is null.
     * </p>
     */
    @Test
    public void testGetSortingClause_NullSortingOrder() {
        String result = manager.getSortingClause("column", null);
        assertTrue("default sorting should be used", result.contains("ORDER BY contestStartTime DESC"));
    }

    /**
     * <p>
     * Tests the getSortingClause(String, SortingOrder) method. It verifies that sorting clause is returned if column
     * name and sorting order are set.
     * </p>
     */
    @Test
    public void testGetSortingClause() {
        String result = manager.getSortingClause("column", SortingOrder.ASCENDING);
        assertTrue("sorting clause should be returned", result.contains("ORDER BY column ASC"));
    }

    /**
     * <p>
     * This is a dummy implementation of AbstractManagerImpl class.
     * </p>
     * @author TCSDEVELOPER
     * @version 1.0
     */
    private class DummyManagerImpl extends AbstractManagerImpl {

        /**
         * <p>
         * Creates an instance of this class. It does nothing.
         * </p>
         */
        public DummyManagerImpl() {
        }
    }
}
