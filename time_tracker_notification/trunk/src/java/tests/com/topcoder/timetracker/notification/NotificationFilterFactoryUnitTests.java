/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.notification;

import com.topcoder.search.builder.filter.BetweenFilter;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.search.builder.filter.GreaterThanOrEqualToFilter;
import com.topcoder.search.builder.filter.LessThanOrEqualToFilter;
import com.topcoder.search.builder.filter.LikeFilter;

import junit.framework.TestCase;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * Unit test for the <code>Notification</code> class.
 *
 * @author kzhu
 * @version 3.2
 */
public class NotificationFilterFactoryUnitTests extends TestCase {
    /** Represents the private instance used for test. */
    private NotificationFilterFactory factory = null;

    /** Represents the private instance used for name projection. */
    private Map filterNames = null;

    /**
     * Sets up the test environment.
     */
    protected void setUp() {
        // set up filter name
        filterNames = new HashMap();
        filterNames.put(NotificationFilterFactory.PROJECT_ID_NAME, "notification_projects.project_id");
        filterNames.put(NotificationFilterFactory.COMPANY_ID_NAME, "notification_projects.company_id");
        filterNames.put(NotificationFilterFactory.CLIENT_ID_NAME, "notification_clients.client_id");
        filterNames.put(NotificationFilterFactory.RESOURCE_ID_NAME, "notification_resources.notification_id");
        filterNames.put(NotificationFilterFactory.ACTIVE_NAME, "notification.status");
        filterNames.put(NotificationFilterFactory.LAST_SENT_NAME, "notification.last_time_sent");
        filterNames.put(NotificationFilterFactory.NEXT_SEND_NAME, "notification.next_time_send");
        filterNames.put(NotificationFilterFactory.FROM_LINE_NAME, "notification.from_line");
        filterNames.put(NotificationFilterFactory.MESSAGE_NAME, "notification.message");
        filterNames.put(NotificationFilterFactory.SUBJECT_NAME, "notification.subject");
        filterNames.put(NotificationFilterFactory.CREATION_USER_NAME, "notification.creation_user");
        filterNames.put(NotificationFilterFactory.MODIFICATION_USER_NAME, "notification.modification_user");
        filterNames.put(NotificationFilterFactory.CREATION_DATE_NAME, "notification.creation_date");
        filterNames.put(NotificationFilterFactory.MODIFICATION_DATE_NAME, "notification.modification_date");

        factory = new NotificationFilterFactory(filterNames);
    }

    /**
     * <p>
     * Accuracy test for <code>PROJECT_ID_NAME</code> which should be set according to the design.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testPROJECT_ID_NAMEAccuracy() throws Exception {
        assertNotNull("PROJECT_ID_NAME should not be set.", NotificationFilterFactory.PROJECT_ID_NAME);
    }

    /**
     * <p>
     * Accuracy test for <code>COMPANY_ID_NAME</code> which should be set according to the design.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testCOMPANY_ID_NAMEAccuracy() throws Exception {
        assertNotNull("COMPANY_ID_NAME should not be set.", NotificationFilterFactory.COMPANY_ID_NAME);
    }

    /**
     * <p>
     * Accuracy test for <code>CLIENT_ID_NAME</code> which should be set according to the design.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testCLIENT_ID_NAMEAccuracy() throws Exception {
        assertNotNull("CLIENT_ID_NAME should not be set.", NotificationFilterFactory.CLIENT_ID_NAME);
    }

    /**
     * <p>
     * Accuracy test for <code>RESOURCE_ID_NAME</code> which should be set according to the design.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testRESOURCE_ID_NAMEAccuracy() throws Exception {
        assertNotNull("RESOURCE_ID_NAME should not be set.", NotificationFilterFactory.RESOURCE_ID_NAME);
    }

    /**
     * <p>
     * Accuracy test for <code>ACTIVE_NAME</code> which should be set according to the design.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testACTIVE_NAMEAccuracy() throws Exception {
        assertNotNull("ACTIVE_NAME should not be set.", NotificationFilterFactory.ACTIVE_NAME);
    }

    /**
     * <p>
     * Accuracy test for <code>LAST_SENT_NAME</code> which should be set according to the design.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testLAST_SENT_NAMEAccuracy() throws Exception {
        assertNotNull("LAST_SENT_NAME should not be set.", NotificationFilterFactory.LAST_SENT_NAME);
    }

    /**
     * <p>
     * Accuracy test for <code>NEXT_SEND_NAME</code> which should be set according to the design.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testNEXT_SEND_NAMEAccuracy() throws Exception {
        assertNotNull("NEXT_SEND_NAME should not be set.", NotificationFilterFactory.NEXT_SEND_NAME);
    }

    /**
     * <p>
     * Accuracy test for <code>FROM_LINE_NAME</code> which should be set according to the design.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testFROM_LINE_NAMEAccuracy() throws Exception {
        assertNotNull("FROM_LINE_NAME should not be set.", NotificationFilterFactory.FROM_LINE_NAME);
    }

    /**
     * <p>
     * Accuracy test for <code>MESSAGE_NAME</code> which should be set according to the design.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testMESSAGE_NAMEAccuracy() throws Exception {
        assertNotNull("MESSAGE_NAME should not be set.", NotificationFilterFactory.MESSAGE_NAME);
    }

    /**
     * <p>
     * Accuracy test for <code>SUBJECT_NAME</code> which should be set according to the design.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testSUBJECT_NAMEAccuracy() throws Exception {
        assertNotNull("SUBJECT_NAME should not be set.", NotificationFilterFactory.SUBJECT_NAME);
    }

    /**
     * <p>
     * Accuracy test for <code>CREATION_USER_NAME</code> which should be set according to the design.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testCREATION_USER_NAMEAccuracy() throws Exception {
        assertNotNull("CREATION_USER_NAME should not be set.", NotificationFilterFactory.CREATION_USER_NAME);
    }

    /**
     * <p>
     * Accuracy test for <code>MODIFICATION_USER_NAME</code> which should be set according to the design.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testMODIFICATION_USER_NAMEAccuracy() throws Exception {
        assertNotNull("MODIFICATION_USER_NAME should not be set.", NotificationFilterFactory.MODIFICATION_USER_NAME);
    }

    /**
     * <p>
     * Accuracy test for <code>CREATION_DATE_NAME</code> which should be set according to the design.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testCREATION_DATE_NAMEAccuracy() throws Exception {
        assertNotNull("CREATION_DATE_NAME should not be set.", NotificationFilterFactory.CREATION_DATE_NAME);
    }

    /**
     * <p>
     * Accuracy test for <code>MODIFICATION_DATE_NAME</code> which should be set according to the design.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testMODIFICATION_DATE_NAMEAccuracy() throws Exception {
        assertNotNull("MODIFICATION_DATE_NAME should not be set.", NotificationFilterFactory.MODIFICATION_DATE_NAME);
    }

    /**
     * <p>
     * Failure test for method <code>NotificationFilterFactory(Map filterNames)</code>. Set with null is illegal.
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testConstructorWithNull() throws Exception {
        try {
            new NotificationFilterFactory(null);
            fail("Set with null is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Failure test for method <code>NotificationFilterFactory(Map filterNames)</code>. Set with map with invalid key
     * is illegal. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testConstructorWithInvalidKey1() throws Exception {
        try {
            filterNames.put(new Integer(1), "string");
            new NotificationFilterFactory(filterNames);
            fail("Set with map with invalid key is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Failure test for method <code>NotificationFilterFactory(Map filterNames)</code>. Set with map with invalid key
     * is illegal. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testConstructorWithInvalidKey2() throws Exception {
        try {
            filterNames.put("     ", "string");
            new NotificationFilterFactory(filterNames);
            fail("Set with map with invalid key is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Failure test for method <code>NotificationFilterFactory(Map filterNames)</code>. Set with invalid value is
     * illegal. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testConstructorWithInvalidValue1() throws Exception {
        try {
            filterNames.remove("CREATION_DATE_NAME");
            filterNames.put("CREATION_DATE_NAME", new Integer(1));
            new NotificationFilterFactory(filterNames);
            fail("Set with invalid value is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Failure test for method <code>NotificationFilterFactory(Map filterNames)</code>. Set with invalid value is
     * illegal. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testConstructorWithInvalidValue2() throws Exception {
        try {
            filterNames.remove("CREATION_DATE_NAME");
            filterNames.put("CREATION_DATE_NAME", "       ");
            new NotificationFilterFactory(filterNames);
            fail("Set with invalid value is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Failure test for method <code>NotificationFilterFactory(Map filterNames)</code>. Set with not enough feature is
     * illegal. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testConstructorWithIncomplete() throws Exception {
        try {
            filterNames.remove("CREATION_DATE_NAME");
            new NotificationFilterFactory(filterNames);
            fail("Set with not enough feature is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Accuracy test for method <code>Constructor</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testConstructorAccuracy() throws Exception {
        assertNotNull("Instance should be created.", factory);
    }

    /**
     * <p>
     * Failure test for method <code>createProjectIdFilter</code>. Create with zero id is illegal.
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testcreateProjectIdFilterWithZero() throws Exception {
        try {
            factory.createProjectIdFilter(0);
            fail("Create with zero id is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Failure test for method <code>createProjectIdFilter</code>. Create with negative id is illegal.
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testcreateProjectIdFilterWithNegative()
        throws Exception {
        try {
            factory.createProjectIdFilter(-1);
            fail("Create with negative id is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Accuracy test for method <code>createProjectIdFilter</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testcreateProjectIdFilterAccuracy1() throws Exception {
        Filter filter = factory.createProjectIdFilter(1);

        assertTrue("The filter should of type EqualToFilter", filter instanceof EqualToFilter);
    }

    /**
     * <p>
     * Failure test for method <code>createCompanyIdFilter</code>. Create with zero id is illegal.
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testcreateCompanyIdFilterWithZero() throws Exception {
        try {
            factory.createCompanyIdFilter(0);
            fail("Create with zero id is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Failure test for method <code>createCompanyIdFilter</code>. Create with negative id is illegal.
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testcreateCompanyIdFilterWithNegative()
        throws Exception {
        try {
            factory.createCompanyIdFilter(-1);
            fail("Create with negative id is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Accuracy test for method <code>createCompanyIdFilter</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testcreateCompanyIdFilterAccuracy1() throws Exception {
        Filter filter = factory.createCompanyIdFilter(1);

        assertTrue("The filter should of type EqualToFilter", filter instanceof EqualToFilter);
    }

    /**
     * <p>
     * Failure test for method <code>createClientIdFilter</code>. Create with zero id is illegal.
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testcreateClientIdFilterWithZero() throws Exception {
        try {
            factory.createClientIdFilter(0);
            fail("Create with zero id is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Failure test for method <code>createClientIdFilter</code>. Create with negative id is illegal.
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testcreateClientIdFilterWithNegative()
        throws Exception {
        try {
            factory.createClientIdFilter(-1);
            fail("Create with negative id is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Accuracy test for method <code>createClientIdFilter</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testcreateClientIdFilterAccuracy1() throws Exception {
        Filter filter = factory.createClientIdFilter(1);

        assertTrue("The filter should of type EqualToFilter", filter instanceof EqualToFilter);
    }

    /**
     * <p>
     * Failure test for method <code>createResourceIdFilter</code>. Create with zero id is illegal.
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testcreateResourceIdFilterWithZero() throws Exception {
        try {
            factory.createResourceIdFilter(0);
            fail("Create with zero id is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Failure test for method <code>createResourceIdFilter</code>. Create with negative id is illegal.
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testcreateResourceIdFilterWithNegative()
        throws Exception {
        try {
            factory.createResourceIdFilter(-1);
            fail("Create with negative id is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Accuracy test for method <code>createResourceIdFilter</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testcreateResourceIdFilterAccuracy1() throws Exception {
        Filter filter = factory.createResourceIdFilter(1);

        assertTrue("The filter should of type EqualToFilter", filter instanceof EqualToFilter);
    }

    /**
     * <p>
     * Accuracy test for method <code>createActiveFilter</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testcreateActiveFilterAccuracy() throws Exception {
        Filter filter = factory.createActiveFilter(true);

        assertTrue("The filter should of type EqualToFilter", filter instanceof EqualToFilter);
    }

    /**
     * <p>
     * Failure test for method <code>createLastSentFilter</code>. Set with both null is illegal.
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testcreateLastSentFilterWithBothNull()
        throws Exception {
        try {
            factory.createLastSentFilter(null, null);
            fail("Set with both null is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Failure test for method <code>createLastSentFilter</code>. Set with illegal date pair is illegal.
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testcreateLastSentFilterWithIllegal() throws Exception {
        try {
            Date date1 = new Date(1000);
            Date date2 = new Date(10000);

            factory.createLastSentFilter(date2, date1);
            fail("Set with illegal date pair is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Accuracy test for method <code>createLastSentFilter</code>. With both bounds available. The filter create should
     * be BetweenFilter.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testcreateLastSentFilterAccuracy1() throws Exception {
        Date date1 = new Date(1000);
        Date date2 = new Date(10000);

        Filter filter = factory.createLastSentFilter(date1, date2);

        assertTrue("The filter should of type BetweenFilter", filter instanceof BetweenFilter);
    }

    /**
     * <p>
     * Accuracy test for method <code>createLastSentFilter</code>. With lower bounds available. The filter create
     * should be GreaterThanOrEqualToFilter.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testcreateLastSentFilterAccuracy2() throws Exception {
        Date date1 = new Date(1000);

        Filter filter = factory.createLastSentFilter(date1, null);

        assertTrue("The filter should of type BetweenFilter", filter instanceof GreaterThanOrEqualToFilter);
    }

    /**
     * <p>
     * Accuracy test for method <code>createLastSentFilter</code>. With upper bounds available. The filter create
     * should be LessThanOrEqualToFilter.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testcreateLastSentFilterAccuracy3() throws Exception {
        Date date2 = new Date(10000);

        Filter filter = factory.createLastSentFilter(null, date2);

        assertTrue("The filter should of type BetweenFilter", filter instanceof LessThanOrEqualToFilter);
    }

    /**
     * <p>
     * Failure test for method <code>createNextSendFilter</code>. Set with both null is illegal.
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testcreateNextSendFilterWithBothNull()
        throws Exception {
        try {
            factory.createNextSendFilter(null, null);
            fail("Set with both null is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Failure test for method <code>createNextSendFilter</code>. Set with illegal date pair is illegal.
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testcreateNextSendFilterWithIllegal() throws Exception {
        try {
            Date date1 = new Date(1000);
            Date date2 = new Date(10000);

            factory.createNextSendFilter(date2, date1);
            fail("Set with illegal date pair is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Accuracy test for method <code>createNextSendFilter</code>. With both bounds available. The filter create should
     * be BetweenFilter.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testcreateNextSendFilterAccuracy1() throws Exception {
        Date date1 = new Date(1000);
        Date date2 = new Date(10000);

        Filter filter = factory.createNextSendFilter(date1, date2);

        assertTrue("The filter should of type BetweenFilter", filter instanceof BetweenFilter);
    }

    /**
     * <p>
     * Accuracy test for method <code>createNextSendFilter</code>. With lower bounds available. The filter create
     * should be GreaterThanOrEqualToFilter.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testcreateNextSendFilterAccuracy2() throws Exception {
        Date date1 = new Date(1000);

        Filter filter = factory.createNextSendFilter(date1, null);

        assertTrue("The filter should of type BetweenFilter", filter instanceof GreaterThanOrEqualToFilter);
    }

    /**
     * <p>
     * Accuracy test for method <code>createNextSendFilter</code>. With upper bounds available. The filter create
     * should be LessThanOrEqualToFilter.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testcreateNextSendFilterAccuracy3() throws Exception {
        Date date2 = new Date(10000);

        Filter filter = factory.createNextSendFilter(null, date2);

        assertTrue("The filter should of type BetweenFilter", filter instanceof LessThanOrEqualToFilter);
    }

    /**
     * <p>
     * Failure test for method <code>createCreationDateFilter</code>. Set with both null is illegal.
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testcreateCreationDateFilterWithBothNull()
        throws Exception {
        try {
            factory.createCreationDateFilter(null, null);
            fail("Set with both null is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Failure test for method <code>createCreationDateFilter</code>. Set with illegal date pair is illegal.
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testcreateCreationDateFilterWithIllegal()
        throws Exception {
        try {
            Date date1 = new Date(1000);
            Date date2 = new Date(10000);

            factory.createCreationDateFilter(date2, date1);
            fail("Set with illegal date pair is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Accuracy test for method <code>createCreationDateFilter</code>. With both bounds available. The filter create
     * should be BetweenFilter.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testcreateCreationDateFilterAccuracy1()
        throws Exception {
        Date date1 = new Date(1000);
        Date date2 = new Date(10000);

        Filter filter = factory.createCreationDateFilter(date1, date2);

        assertTrue("The filter should of type BetweenFilter", filter instanceof BetweenFilter);
    }

    /**
     * <p>
     * Accuracy test for method <code>createCreationDateFilter</code>. With lower bounds available. The filter create
     * should be GreaterThanOrEqualToFilter.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testcreateCreationDateFilterAccuracy2()
        throws Exception {
        Date date1 = new Date(1000);

        Filter filter = factory.createCreationDateFilter(date1, null);

        assertTrue("The filter should of type BetweenFilter", filter instanceof GreaterThanOrEqualToFilter);
    }

    /**
     * <p>
     * Accuracy test for method <code>createCreationDateFilter</code>. With upper bounds available. The filter create
     * should be LessThanOrEqualToFilter.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testcreateCreationDateFilterAccuracy3()
        throws Exception {
        Date date2 = new Date(10000);

        Filter filter = factory.createCreationDateFilter(null, date2);

        assertTrue("The filter should of type BetweenFilter", filter instanceof LessThanOrEqualToFilter);
    }

    /**
     * <p>
     * Failure test for method <code>createModificationDateFilter</code>. Set with both null is illegal.
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testcreateModificationDateFilterWithBothNull()
        throws Exception {
        try {
            factory.createModificationDateFilter(null, null);
            fail("Set with both null is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Failure test for method <code>createModificationDateFilter</code>. Set with illegal date pair is illegal.
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testcreateModificationDateFilterWithIllegal()
        throws Exception {
        try {
            Date date1 = new Date(1000);
            Date date2 = new Date(10000);

            factory.createModificationDateFilter(date2, date1);
            fail("Set with illegal date pair is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Accuracy test for method <code>createModificationDateFilter</code>. With both bounds available. The filter
     * create should be BetweenFilter.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testcreateModificationDateFilterAccuracy1()
        throws Exception {
        Date date1 = new Date(1000);
        Date date2 = new Date(10000);

        Filter filter = factory.createModificationDateFilter(date1, date2);

        assertTrue("The filter should of type BetweenFilter", filter instanceof BetweenFilter);
    }

    /**
     * <p>
     * Accuracy test for method <code>createModificationDateFilter</code>. With lower bounds available. The filter
     * create should be GreaterThanOrEqualToFilter.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testcreateModificationDateFilterAccuracy2()
        throws Exception {
        Date date1 = new Date(1000);

        Filter filter = factory.createModificationDateFilter(date1, null);

        assertTrue("The filter should of type BetweenFilter", filter instanceof GreaterThanOrEqualToFilter);
    }

    /**
     * <p>
     * Accuracy test for method <code>createModificationDateFilter</code>. With upper bounds available. The filter
     * create should be LessThanOrEqualToFilter.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testcreateModificationDateFilterAccuracy3()
        throws Exception {
        Date date2 = new Date(10000);

        Filter filter = factory.createModificationDateFilter(null, date2);

        assertTrue("The filter should of type BetweenFilter", filter instanceof LessThanOrEqualToFilter);
    }

    /**
     * <p>
     * Failure test for method <code>createFromLineFilter</code>. Create with null is illegal. IllegalArgumentException
     * is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testcreateFromLineFilterWithNull() throws Exception {
        try {
            factory.createFromLineFilter(null, StringMatchType.ENDS_WITH);
            fail("Create with null is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Failure test for method <code>createFromLineFilter</code>. Create with empty is illegal.
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testcreateFromLineFilterWithEmpty() throws Exception {
        try {
            factory.createFromLineFilter("    ", StringMatchType.ENDS_WITH);
            fail("Create with empty is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Accuracy test for method <code>createFromLineFilter</code>. When EXACT_MATCH is used, EqualToFilter should
     * created.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testcreateFromLineFilterAccuracy1() throws Exception {
        Filter filter = factory.createFromLineFilter("string", StringMatchType.EXACT_MATCH);

        assertTrue("The filter should be of type EqualToFilter.", filter instanceof EqualToFilter);
    }

    /**
     * <p>
     * Accuracy test for method <code>createFromLineFilter</code>. When EXACT_MATCH is used, LikeFilter should created.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testcreateFromLineFilterAccuracy2() throws Exception {
        Filter filter = factory.createFromLineFilter("string", StringMatchType.ENDS_WITH);

        assertTrue("The filter should be of type EqualToFilter.", filter instanceof LikeFilter);
    }

    /**
     * <p>
     * Failure test for method <code>createMessageFilter</code>. Create with null is illegal. IllegalArgumentException
     * is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testcreateMessageFilterWithNull() throws Exception {
        try {
            factory.createMessageFilter(null, StringMatchType.ENDS_WITH);
            fail("Create with null is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Failure test for method <code>createMessageFilter</code>. Create with empty is illegal. IllegalArgumentException
     * is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testcreateMessageFilterWithEmpty() throws Exception {
        try {
            factory.createMessageFilter("    ", StringMatchType.ENDS_WITH);
            fail("Create with empty is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Accuracy test for method <code>createMessageFilter</code>. When EXACT_MATCH is used, EqualToFilter should
     * created.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testcreateMessageFilterAccuracy1() throws Exception {
        Filter filter = factory.createMessageFilter("string", StringMatchType.EXACT_MATCH);

        assertTrue("The filter should be of type EqualToFilter.", filter instanceof EqualToFilter);
    }

    /**
     * <p>
     * Accuracy test for method <code>createMessageFilter</code>. When EXACT_MATCH is used, LikeFilter should created.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testcreateMessageFilterAccuracy2() throws Exception {
        Filter filter = factory.createMessageFilter("string", StringMatchType.ENDS_WITH);

        assertTrue("The filter should be of type EqualToFilter.", filter instanceof LikeFilter);
    }

    /**
     * <p>
     * Failure test for method <code>createSubjectFilter</code>. Create with null is illegal. IllegalArgumentException
     * is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testcreateSubjectFilterWithNull() throws Exception {
        try {
            factory.createSubjectFilter(null, StringMatchType.ENDS_WITH);
            fail("Create with null is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Failure test for method <code>createSubjectFilter</code>. Create with empty is illegal. IllegalArgumentException
     * is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testcreateSubjectFilterWithEmpty() throws Exception {
        try {
            factory.createSubjectFilter("    ", StringMatchType.ENDS_WITH);
            fail("Create with empty is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Accuracy test for method <code>createSubjectFilter</code>. When EXACT_MATCH is used, EqualToFilter should
     * created.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testcreateSubjectFilterAccuracy1() throws Exception {
        Filter filter = factory.createSubjectFilter("string", StringMatchType.EXACT_MATCH);

        assertTrue("The filter should be of type EqualToFilter.", filter instanceof EqualToFilter);
    }

    /**
     * <p>
     * Accuracy test for method <code>createSubjectFilter</code>. When EXACT_MATCH is used, LikeFilter should created.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testcreateSubjectFilterAccuracy2() throws Exception {
        Filter filter = factory.createSubjectFilter("string", StringMatchType.ENDS_WITH);

        assertTrue("The filter should be of type EqualToFilter.", filter instanceof LikeFilter);
    }

    /**
     * <p>
     * Failure test for method <code>createCreationUserFilter</code>. Create with null is illegal.
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testcreateCreationUserFilterWithNull()
        throws Exception {
        try {
            factory.createCreationUserFilter(null, StringMatchType.ENDS_WITH);
            fail("Create with null is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Failure test for method <code>createCreationUserFilter</code>. Create with empty is illegal.
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testcreateCreationUserFilterWithEmpty()
        throws Exception {
        try {
            factory.createCreationUserFilter("    ", StringMatchType.ENDS_WITH);
            fail("Create with empty is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Accuracy test for method <code>createCreationUserFilter</code>. When EXACT_MATCH is used, EqualToFilter should
     * created.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testcreateCreationUserFilterAccuracy1()
        throws Exception {
        Filter filter = factory.createCreationUserFilter("string", StringMatchType.EXACT_MATCH);

        assertTrue("The filter should be of type EqualToFilter.", filter instanceof EqualToFilter);
    }

    /**
     * <p>
     * Accuracy test for method <code>createCreationUserFilter</code>. When EXACT_MATCH is used, LikeFilter should
     * created.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testcreateCreationUserFilterAccuracy2()
        throws Exception {
        Filter filter = factory.createCreationUserFilter("string", StringMatchType.ENDS_WITH);

        assertTrue("The filter should be of type EqualToFilter.", filter instanceof LikeFilter);
    }

    /**
     * <p>
     * Failure test for method <code>createModificationUserFilter</code>. Create with null is illegal.
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testcreateModificationUserFilterWithNull()
        throws Exception {
        try {
            factory.createModificationUserFilter(null, StringMatchType.ENDS_WITH);
            fail("Create with null is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Failure test for method <code>createModificationUserFilter</code>. Create with empty is illegal.
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testcreateModificationUserFilterWithEmpty()
        throws Exception {
        try {
            factory.createModificationUserFilter("    ", StringMatchType.ENDS_WITH);
            fail("Create with empty is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Accuracy test for method <code>createModificationUserFilter</code>. When EXACT_MATCH is used, EqualToFilter
     * should created.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testcreateModificationUserFilterAccuracy1()
        throws Exception {
        Filter filter = factory.createModificationUserFilter("string", StringMatchType.EXACT_MATCH);

        assertTrue("The filter should be of type EqualToFilter.", filter instanceof EqualToFilter);
    }

    /**
     * <p>
     * Accuracy test for method <code>createModificationUserFilter</code>. When EXACT_MATCH is used, LikeFilter should
     * created.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testcreateModificationUserFilterAccuracy2()
        throws Exception {
        Filter filter = factory.createModificationUserFilter("string", StringMatchType.ENDS_WITH);

        assertTrue("The filter should be of type EqualToFilter.", filter instanceof LikeFilter);
    }
}
