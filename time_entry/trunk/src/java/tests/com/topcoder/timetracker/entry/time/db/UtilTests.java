/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time.db;

import java.util.HashMap;
import java.util.Map;

import com.topcoder.search.builder.filter.BetweenFilter;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.LikeFilter;
import com.topcoder.timetracker.entry.time.BatchOperationException;
import com.topcoder.timetracker.entry.time.ConfigurationException;
import com.topcoder.timetracker.entry.time.DataAccessException;
import com.topcoder.timetracker.entry.time.StringMatchType;
import com.topcoder.timetracker.entry.time.TaskType;
import com.topcoder.timetracker.entry.time.TestHelper;
import com.topcoder.timetracker.entry.time.ejb.TaskTypeManagerLocalHome;
import com.topcoder.timetracker.entry.time.ejb.TaskTypeManagerSessionBean;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

/**
 * <p>
 * Unit test cases for Util.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class UtilTests extends TestCase {

    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        TestHelper.clearConfig();
        TestHelper.loadXMLConfig(TestHelper.CONFIG_FILE);
        TestHelper.loadXMLConfig(TestHelper.AUDIT_CONFIG_FILE);
        TestHelper.setUpDataBase();
        TestHelper.setUpEJBEnvironment(new TaskTypeManagerSessionBean(), null, null);
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        TestHelper.tearDownDataBase();
        TestHelper.clearConfig();
    }

    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(UtilTests.class);
    }

    /**
     * <p>
     * Tests Util#extractBatchOperationException(BatchOperationException,String) for failure.
     * </p>
     *
     * <p>
     * Expects DataAccessException.
     * </p>
     */
    public void testExtractBatchOperationException_DataAccessException() {
        Throwable[] causes = new Throwable[] {new DataAccessException("test")};
        BatchOperationException batch = new BatchOperationException("test", causes);
        try {
            Util.extractBatchOperationException(batch, "test");
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests Util#checkStepResult(Throwable[]) for accuracy.
     * </p>
     *
     * <p>
     * It verifies Util#checkStepResult(Throwable[]) is correct.
     * </p>
     * @throws Exception to jUnit
     */
    public void testCheckStepResult() throws Exception {
        Throwable[] causes = new Throwable[] {null};
        Util.checkStepResult(causes);
    }

    /**
     * <p>
     * Tests Util#checkStepResult(Throwable[]) for failure.
     * </p>
     *
     * <p>
     * Expects BatchOperationException.
     * </p>
     */
    public void testCheckStepResult_BatchOperationException() {
        Throwable[] causes = new Throwable[] {new DataAccessException("test")};
        try {
            Util.checkStepResult(causes);
            fail("BatchOperationException expected.");
        } catch (BatchOperationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests Util#updateCauses(Throwable[],BatchOperationException) for accuracy.
     * </p>
     *
     * <p>
     * It verifies Util#updateCauses(Throwable[],BatchOperationException) is correct.
     * </p>
     */
    public void testUpdateCauses() {
        Throwable[] causes = new Throwable[] {new DataAccessException("test")};
        BatchOperationException batch = new BatchOperationException("test", causes);
        Util.updateCauses(causes, batch);
    }

    /**
     * <p>
     * Tests Util#finishBatchOperation(Throwable[]) for accuracy.
     * </p>
     *
     * <p>
     * It verifies Util#finishBatchOperation(Throwable[]) is correct.
     * </p>
     * @throws Exception to JUnit
     */
    public void testFinishBatchOperation() throws Exception {
        Throwable[] causes = new Throwable[] {null};
        Util.finishBatchOperation(causes);
    }

    /**
     * <p>
     * Tests Util#finishBatchOperation(Throwable[]) for failure.
     * </p>
     *
     * <p>
     * Expects BatchOperationException.
     * </p>
     */
    public void testFinishBatchOperation_BatchOperationException() {
        Throwable[] causes = new Throwable[] {new DataAccessException("test")};

        try {
            Util.finishBatchOperation(causes);
            fail("BatchOperationException expected.");
        } catch (BatchOperationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests Util#checkNull(Object,String) method.
     * It test the case when a not null object is passed in and expects success.
     * </p>
     */
    public void testCheckNull_NotNullObject() {
        Util.checkNull("", "test");
    }

    /**
     * <p>
     * Tests Util#checkNull(Object,String) method.
     * It test the case when a null object is passed in and expects IllegalArgumentException.
     * </p>
     */
    public void testCheckNull_NullObject() {
        try {
            Util.checkNull(null, "test");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests Util#checkString(String,String) method.
     * It test the case when a not null and not empty string is passed in and expects success.
     * </p>
     */
    public void testCheckString_NotNullNotEmptyString() {
        Util.checkString("test", "test");
    }

    /**
     * <p>
     * Tests Util#checkString(String,String) method.
     * It test the case when a null string is passed in and expects IllegalArgumentException.
     * </p>
     */
    public void testCheckString_NullString() {
        try {
            Util.checkString(null, "test");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests Util#checkString(String,String) method.
     * It test the case when an empty string is passed in and expects IllegalArgumentException.
     * </p>
     */
    public void testCheckString_EmptyString() {
        try {
            Util.checkString("", "test");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests Util#checkMapForKeys(Map,String[]) for accuracy.
     * </p>
     *
     * <p>
     * It verifies Util#checkMapForKeys(Map,String[]) is correct.
     * </p>
     */
    public void testCheckMapForKeys() {
        Map map = new HashMap();
        map.put("one", "first");
        map.put("two", "secodn");
        Util.checkMapForKeys(map, new String[] {"one", "two"});
    }

    /**
     * <p>
     * Tests Util#checkMapForKeys(Map,String[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when the given map instance doesn't contains any key in the given
     * string array and expects IllegalArgumentException.
     * </p>
     */
    public void testCheckMapForKeys_NotContainKey() {
        Map map = new HashMap();
        map.put("one", "first");
        try {
            Util.checkMapForKeys(map, new String[] {"one", "two"});
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests Util#createFilter(StringMatchType,String,String) for accuracy.
     * </p>
     *
     * <p>
     * It verifies Util#createFilter(StringMatchType,String,String) is correct.
     * </p>
     */
    public void testCreateFilter() {
        LikeFilter filter = (LikeFilter) Util.createFilter(StringMatchType.ENDS_WITH, "name", "value");
        assertEquals("Failed to create filter correctly.", "name", filter.getName());

        EqualToFilter equalToFilter = (EqualToFilter) Util.createFilter(StringMatchType.EXACT_MATCH, "new", "value");
        assertEquals("Failed to create filter correctly.", "new", equalToFilter.getName());
    }

    /**
     * <p>
     * Tests Util#createFilter(StringMatchType,String,String) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when matchType is null and expects IllegalArgumentException.
     * </p>
     */
    public void testCreateFilter_NullMatchType() {
        try {
            Util.createFilter(null, "name", "value");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests Util#updateTimeTrackerBeanDates(TimeTrackerBean[],String,boolean) for accuracy.
     * </p>
     *
     * <p>
     * It verifies Util#updateTimeTrackerBeanDates(TimeTrackerBean[],String,boolean) is correct.
     * </p>
     */
    public void testUpdateTimeTrackerBeanDates() {
        TaskType[] taskTypes = new TaskType[] {new TaskType()};
        Util.updateTimeTrackerBeanDates(taskTypes, "name", true);
    }

    /**
     * <p>
     * Tests Util#updateTimeTrackerBeanDates(TimeTrackerBean[],String,boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when beans is null and expects IllegalArgumentException.
     * </p>
     */
    public void testUpdateTimeTrackerBeanDates_NullBeans() {
        try {
            Util.updateTimeTrackerBeanDates(null, "name", true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests Util#updateTimeTrackerBeanDates(TimeTrackerBean[],String,boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when beans contains null element and expects IllegalArgumentException.
     * </p>
     */
    public void testUpdateTimeTrackerBeanDates_NullInBeans() {
        TaskType[] taskTypes = new TaskType[] {new TaskType(), null};
        try {
            Util.updateTimeTrackerBeanDates(taskTypes, "name", true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests Util#createEJBLocalHome(String,Class) for accuracy.
     * </p>
     *
     * <p>
     * It verifies Util#createEJBLocalHome(String,Class) is correct.
     * </p>
     * @throws Exception to JUnit
     */
    public void testCreateEJBLocalHome() throws Exception {
        assertNotNull("Failed to create the object.", Util.createEJBLocalHome(
            "com.topcoder.timetracker.entry.time.delegate.TaskTypeManagerDelegate", TaskTypeManagerLocalHome.class));
    }

    /**
     * <p>
     * Tests Util#createEJBLocalHome(String,Class) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when namespace is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testCreateEJBLocalHome_NullNamespace() throws Exception {
        try {
            Util.createEJBLocalHome(null, TaskTypeManagerLocalHome.class);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests Util#createEJBLocalHome(String,Class) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when namespace is empty and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testCreateEJBLocalHome_EmptyNamespace() throws Exception {
        try {
            Util.createEJBLocalHome(" ", TaskTypeManagerLocalHome.class);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests Util#createEJBLocalHome(String,Class) for failure.
     * </p>
     *
     * <p>
     * It tests the case when namespace is unknown and expects ConfigurationException.
     * </p>
     */
    public void testCreateEJBLocalHome_UnknownNamespace() {
        try {
            Util.createEJBLocalHome("invalid", TaskTypeManagerLocalHome.class);
            fail("ConfigurationException expected.");
        } catch (ConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests Util#createEJBLocalHome(String,Class) for failure.
     * </p>
     *
     * <p>
     * It tests the case when localHomeClass is not the expected type and expects ConfigurationException.
     * </p>
     */
    public void testCreateEJBLocalHome_WrongClassType() {
        try {
            Util.createEJBLocalHome("com.topcoder.timetracker.project.ejb.ProjectUtilityDelegate", String.class);
            fail("ConfigurationException expected.");
        } catch (ConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests Util#checkIdValue(long,String) for accuracy.
     * </p>
     *
     * <p>
     * It verifies Util#checkIdValue(long,String) is correct.
     * </p>
     */
    public void testCheckIdValue() {
        Util.checkIdValue(5, "test");
    }

    /**
     * <p>
     * Tests Util#checkIdValue(long,String) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when idValue is negative and expects IllegalArgumentException.
     * </p>
     */
    public void testCheckIdValue_Negative() {
        try {
            Util.checkIdValue(-5, "test");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests Util#createRangeFilter(String,Comparable,Comparable) for accuracy.
     * </p>
     *
     * <p>
     * It verifies Util#createRangeFilter(String,Comparable,Comparable) is correct.
     * </p>
     */
    public void testCreateRangeFilter() {
        BetweenFilter filter = (BetweenFilter) Util.createRangeFilter("columnName", new Long(5), new Long(7));
        assertEquals("Failed to create the range filter.", "columnName", filter.getName());
    }

    /**
     * <p>
     * Tests Util#createAuditDetail(String,String,String) for accuracy.
     * </p>
     *
     * <p>
     * It verifies Util#createAuditDetail(String,String,String) is correct.
     * </p>
     */
    public void testCreateAuditDetail() {
        assertNotNull("Failed to create the audit detail.",
            Util.createAuditDetail("columnName", "oldValue", "newValue"));
    }

}