/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.project.db;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.CreateException;
import javax.naming.Context;
import javax.naming.InitialContext;

import org.mockejb.MockContainer;
import org.mockejb.SessionBeanDescriptor;
import org.mockejb.jndi.MockContextFactory;

import com.topcoder.search.builder.filter.BetweenFilter;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.LikeFilter;
import com.topcoder.timetracker.project.ConfigurationException;
import com.topcoder.timetracker.project.Project;
import com.topcoder.timetracker.project.ProjectWorkerUtility;
import com.topcoder.timetracker.project.StringMatchType;
import com.topcoder.timetracker.project.TestHelper;
import com.topcoder.timetracker.project.ejb.MockUserTransaction;
import com.topcoder.timetracker.project.ejb.ProjectUtilityLocal;
import com.topcoder.timetracker.project.ejb.ProjectUtilityLocalHome;
import com.topcoder.timetracker.project.ejb.ProjectUtilitySessionBean;

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
     * @throws Exception to JUnit
     *
     */
    protected void setUp() throws Exception {
        TestHelper.loadXMLConfig(TestHelper.CONFIG_FILE);
        TestHelper.loadXMLConfig(TestHelper.SEARCH_CONFIG_FILE);
        TestHelper.loadXMLConfig("test_files" + File.separator + "invalid.xml");
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     * @throws Exception to JUnit
     *
     */
    protected void tearDown() throws Exception {
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
        Project[] projects = new Project[] {new Project()};
        Util.updateTimeTrackerBeanDates(projects, "name", true);
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
        Project[] projects = new Project[] {new Project(), null};
        try {
            Util.updateTimeTrackerBeanDates(projects, "name", true);
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
        MockContextFactory.setAsInitial();

        // create the initial context that will be used for binding EJBs
        Context context = new InitialContext();

        // Create an instance of the MockContainer
        MockContainer mockContainer = new MockContainer(context);

        // we use MockTransaction outside of the app server
        MockUserTransaction mockTransaction = new MockUserTransaction();
        context.rebind("javax.transaction.UserTransaction", mockTransaction);

        ProjectUtilitySessionBean bean = new ProjectUtilitySessionBean();

        /* Create deployment descriptor of our sample bean.
         * MockEjb uses it instead of XML deployment descriptors
         */
        SessionBeanDescriptor sampleServiceDescriptor = new SessionBeanDescriptor("projectDelegateLocalHome",
            ProjectUtilityLocalHome.class, ProjectUtilityLocal.class, bean);

        // Deploy operation creates Home and binds it to JNDI
        mockContainer.deploy(sampleServiceDescriptor);

        assertNotNull("Failed to create the object.", Util.createEJBLocalHome(
            "com.topcoder.timetracker.project.ejb.ProjectUtilityDelegate", ProjectUtilityLocalHome.class));
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
            Util.createEJBLocalHome(null, ProjectUtilityLocalHome.class);
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
            Util.createEJBLocalHome(" ", ProjectUtilityLocalHome.class);
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
            Util.createEJBLocalHome("invalid", ProjectUtilityLocalHome.class);
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

    /**
     * <p>
     * Tests Util#createObject(String,Class) for accuracy.
     * </p>
     *
     * <p>
     * It verifies Util#createObject(String,Class) is correct.
     * </p>
     * @throws Exception to JUnit
     */
    public void testCreateObject() throws Exception {
        assertNotNull("Failed to create object.", Util.createObject(
            "com.topcoder.timetracker.project.ejb.ProjectWorkerUtilitySessionBean", ProjectWorkerUtility.class));
    }

    /**
     * <p>
     * Tests Util#createObject(String,Class) for failure.
     * </p>
     *
     * <p>
     * It tests the case when the namespace is unknown and expects CreateException.
     * </p>
     */
    public void testCreateObject_UnknownNamespace() {
        try {
            Util.createObject("UnknownNamespace", ProjectWorkerUtility.class);
            fail("CreateException expected.");
        } catch (CreateException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests Util#createObject(String,Class) for failure.
     * </p>
     *
     * <p>
     * It tests the case when the of_namespace is missing and expects CreateException.
     * </p>
     */
    public void testCreateObject_OfNamespaceMissing() {
        try {
            Util.createObject("of_namespace_missing", ProjectWorkerUtility.class);
            fail("CreateException expected.");
        } catch (CreateException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests Util#createObject(String,Class) for failure.
     * </p>
     *
     * <p>
     * It tests the case when the of_namespace is empty and expects CreateException.
     * </p>
     */
    public void testCreateObject_OfNamespaceEmpty() {
        try {
            Util.createObject("of_namespace_empty", ProjectWorkerUtility.class);
            fail("CreateException expected.");
        } catch (CreateException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests Util#createObject(String,Class) for failure.
     * </p>
     *
     * <p>
     * It tests the case when the of_namespace is invalid and expects CreateException.
     * </p>
     */
    public void testCreateObject_OfNamespaceInvalid() {
        try {
            Util.createObject("of_namespace_invalid", ProjectWorkerUtility.class);
            fail("CreateException expected.");
        } catch (CreateException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests Util#createObject(String,Class) for failure.
     * </p>
     *
     * <p>
     * It tests the case when the of_namespace is invalid and expects CreateException.
     * </p>
     */
    public void testCreateObject_ClassWrongType() {
        try {
            Util.createObject("com.topcoder.timetracker.project.ejb.ProjectWorkerUtilitySessionBean", String.class);
            fail("CreateException expected.");
        } catch (CreateException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests Util#buildInClause(String,[J) for accuracy.
     * </p>
     *
     * <p>
     * It verifies Util#buildInClause(String,long[]) is correct.
     * </p>
     */
    public void testBuildInClause() {
        assertEquals("Failed to build the string.", "columnName IN (2, 1)", Util.buildInClause("columnName",
            new long[] {2, 1}));
    }

}