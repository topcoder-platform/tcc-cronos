/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.notification;

import com.topcoder.timetracker.common.TimeTrackerBean;

import junit.framework.TestCase;

import java.util.Arrays;
import java.util.Date;


/**
 * Unit test for the <code>Notification</code> class.
 *
 * @author kzhu
 * @version 3.2
 */
public class NotificationUnitTests extends TestCase {
    /** Represents the private array of long which is valid, used for test. */
    private static final long[] validArray = { 1, 2, 3 };

    /** Represents the private array of long which is invalid ,contain non-positive element, used for test. */
    private static final long[] invalidArray1 = { 0, 2, 3 };

    /** Represents the private array of long which is invalid, contain non-positive element, used for test. */
    private static final long[] invalidArray2 = { 1, -1, 3 };

    /** Represents the private array of long which is invalid ,contain duplicate elements, used for test. */
    private static final long[] invalidArray3 = { 2, 2, 3 };

    /** Represents the private instance used for test. */
    private Notification notification = null;

    /**
     * Sets up the test environment.
     */
    protected void setUp() {
        notification = new Notification();
    }

    /**
     * Tests the inheritance of the Notification, it should inherit from TimeTrackerBean.
     */
    public void testInheritance() {
        assertTrue("Notification should inherit from TimeTrackerBean.", notification instanceof TimeTrackerBean);
    }

    /**
     * <p>
     * Accuracy test for Constructor.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testnameAccuracy() throws Exception {
        assertNotNull("Instance should be created.", notification);
    }

    /**
     * <p>
     * Accuracy test for method <code>getCompanyId</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testgetCompanyIdAccuracy() throws Exception {
        notification.setCompnayId(2);

        assertEquals("The company should be 2.",
            ((Long) UnitTestHelper.getPrivateField(Notification.class, notification, "companyId")).longValue(),
            notification.getCompanyId());
    }

    /**
     * <p>
     * Failure test for method <code>setCompanyId</code>. Set with zero is illegal. IllegalArgumentException is
     * expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testsetCompanyIdWithZero() throws Exception {
        try {
            notification.setCompnayId(0);
            fail("Set with zero is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Failure test for method <code>setCompanyId</code>. Set with negative is illegal. IllegalArgumentException is
     * expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testsetCompanyIdWithNegative() throws Exception {
        try {
            notification.setCompnayId(-1);
            fail("Set with negative is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Accuracy test for method <code>setCompanId</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testsetCompanIdAccuracy() throws Exception {
        notification.setCompnayId(1);

        assertEquals("Company id should be set.", 1,
            ((Long) UnitTestHelper.getPrivateField(Notification.class, notification, "companyId")).longValue());
    }

    /**
     * <p>
     * Accuracy test for method <code>getSubject</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testgetSubjectAccuracy() throws Exception {
        notification.setSubject("subject");

        assertEquals("Subject should be set.",
            UnitTestHelper.getPrivateField(Notification.class, notification, "subject"), notification.getSubject());
    }

    /**
     * <p>
     * Failure test for method <code>setSubject</code>. Set with null is illegal. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testsetSubjectWithNull() throws Exception {
        try {
            notification.setSubject(null);
            fail("Set with null is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Accuracy test for method <code>setSubject</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testsetSubjectAccuracy() throws Exception {
        notification.setSubject("subject");

        assertEquals("Subject should be set.", "subject",
            UnitTestHelper.getPrivateField(Notification.class, notification, "subject"));
    }

    /**
     * <p>
     * Accuracy test for method <code>getMessage</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testgetMessageAccuracy() throws Exception {
        notification.setMessage("message");

        assertEquals("Message should be set.",
            UnitTestHelper.getPrivateField(Notification.class, notification, "message"), notification.getMessage());
    }

    /**
     * <p>
     * Failure test for method <code>setMessage</code>. Set with null is illegal. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testsetSubjectWith() throws Exception {
        try {
            notification.setMessage(null);
            fail("Set with null is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Accuracy test for method <code>setMessage</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testsetMessageAccuracy() throws Exception {
        notification.setMessage("message");

        assertEquals("Message should be set.", "message",
            UnitTestHelper.getPrivateField(Notification.class, notification, "message"));
    }

    /**
     * <p>
     * Accuracy test for method <code>getFromAddress</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testgetFromAddressAccuracy() throws Exception {
        notification.setFromAddress("fromAddress");

        assertEquals("From address should be set.",
            UnitTestHelper.getPrivateField(Notification.class, notification, "fromAddress"),
            notification.getFromAddress());
    }

    /**
     * <p>
     * Failure test for method <code>setFromAddress</code>. Set with null is illegal. IllegalArgumentException is
     * expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testsetFromAddressWithNull() throws Exception {
        try {
            notification.setFromAddress(null);
            fail("Set with null is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Accuracy test for method <code>setFromAddress</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testsetFromAddrAccuracy() throws Exception {
        notification.setFromAddress("fromAddress");

        assertEquals("Address should be set.", "fromAddress",
            UnitTestHelper.getPrivateField(Notification.class, notification, "fromAddress"));
    }

    /**
     * <p>
     * Accuracy test for method <code>getLastTimeSent</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testgetLastTimeSentAccuracy() throws Exception {
        notification.setLastTimeSent(new Date());

        assertEquals("Current date should be get.",
            UnitTestHelper.getPrivateField(Notification.class, notification, "lastTimeSent"),
            notification.getLastTimeSent());
    }

    /**
     * <p>
     * Failure test for method <code>setLastTimeSent</code>. Set with null is illegal. IllegalArgumentException is
     * expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testsetLastTimeSentWithNull() throws Exception {
        try {
            notification.setLastTimeSent(null);
            fail("Set with null is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Accuracy test for method <code>setLastTimeSent</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testsetLastTimeSentAccuracy() throws Exception {
        Date currentDate = new Date();

        notification.setLastTimeSent(currentDate);

        assertEquals("Should set with current date.", currentDate,
            UnitTestHelper.getPrivateField(Notification.class, notification, "lastTimeSent"));
    }

    /**
     * <p>
     * Accuracy test for method <code>getNextTimeToSend</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testgetNextTimeToSendAccuracy() throws Exception {
        notification.setNextTimeToSend(new Date());

        assertEquals("Current date should be get.",
            UnitTestHelper.getPrivateField(Notification.class, notification, "nextTimeToSend"),
            notification.getNextTimeToSend());
    }

    /**
     * <p>
     * Failure test for method <code>setNextTimeToSend</code>. Set with null is illegal. IllegalArgumentException is
     * expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testsetNextTimeToSendWithNull() throws Exception {
        try {
            notification.setNextTimeToSend(null);
            fail("Set with null is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Accuracy test for method <code>setNextTimeToSend</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testsetNextTimeToSendAccuracy() throws Exception {
        Date currentDate = new Date();

        notification.setNextTimeToSend(currentDate);

        assertEquals("Should set with current date.", currentDate,
            UnitTestHelper.getPrivateField(Notification.class, notification, "nextTimeToSend"));
    }

    /**
     * <p>
     * Accuracy test for method <code>getScheduleId</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testgetScheduleIdAccuracy() throws Exception {
        notification.setScheduleId(2);

        assertEquals("The shedule id should be 2.",
            ((Long) UnitTestHelper.getPrivateField(Notification.class, notification, "scheduleId")).longValue(),
            notification.getScheduleId());
    }

    /**
     * <p>
     * Failure test for method <code>setScheduleId</code>. Set with zero is illegal. IllegalArgumentException is
     * expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testsetScheduleIdWithZero() throws Exception {
        try {
            notification.setScheduleId(0);
            fail("Set with zero is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Failure test for method <code>setScheduleId</code>. Set with negative is illegal. IllegalArgumentException is
     * expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testsetScheduleIdWithNegative() throws Exception {
        try {
            notification.setScheduleId(-1);
            fail("Set with negative is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Accuracy test for method <code>setScheduleId</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testsetScheduleIdAccuracy() throws Exception {
        notification.setScheduleId(1);

        assertEquals("Schedule id should be set.", 1,
            ((Long) UnitTestHelper.getPrivateField(Notification.class, notification, "scheduleId")).longValue());
    }

    /**
     * <p>
     * Accuracy test for method <code>isActive</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testisActiveAccuracy() throws Exception {
        notification.setActive(false);

        assertEquals("active should be set.",
            ((Boolean) UnitTestHelper.getPrivateField(Notification.class, notification, "active")).booleanValue(),
            notification.isActive());
    }

    /**
     * <p>
     * Accuracy test for method <code>setActive</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testsetActiveAccuracy1() throws Exception {
        notification.setActive(false);

        assertFalse("active should be false.",
            ((Boolean) UnitTestHelper.getPrivateField(Notification.class, notification, "active")).booleanValue());
    }

    /**
     * <p>
     * Accuracy test for method <code>setActive</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testsetActiveAccuracy2() throws Exception {
        notification.setActive(true);

        assertTrue("active should be false.",
            ((Boolean) UnitTestHelper.getPrivateField(Notification.class, notification, "active")).booleanValue());
    }

    /**
     * <p>
     * Accuracy test for method <code>getToProjects</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testgetToProjectsAccuracy() throws Exception {
        notification.setToProjects(validArray);

        assertTrue("Valid array should be get.",
            Arrays.equals((long[]) UnitTestHelper.getPrivateField(Notification.class, notification, "toProjects"),
                validArray));
    }

    /**
     * <p>
     * Failure test for method <code>setToProjects</code>. Set with null is illegal. IllegalArgumentException is
     * expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testsetToProjectsWithNull() throws Exception {
        try {
            notification.setToProjects(null);
            fail("Set with null is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Failure test for method <code>setToProjects</code>. Set with non-positive element is illegal.
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testsetToProjectsWithInvalid1() throws Exception {
        try {
            notification.setToProjects(invalidArray1);
            fail("Set with non-positive element is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Failure test for method <code>setToProjects</code>. Set with non-positive element is illegal.
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testsetToProjectsWithInvalid2() throws Exception {
        try {
            notification.setToProjects(invalidArray2);
            fail("Set with non-positive element is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Failure test for method <code>setToProjects</code>. Set with duplicate elements is illegal.
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testsetToProjectsWithInvalid3() throws Exception {
        try {
            notification.setToProjects(invalidArray3);
            fail("Set with duplicate elements is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Accuracy test for method <code>setToProjects</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testsetToProjectsAccuracy() throws Exception {
        notification.setToProjects(validArray);

        assertTrue("The array should be set.",
            Arrays.equals(validArray,
                (long[]) UnitTestHelper.getPrivateField(Notification.class, notification, "toProjects")));
    }

    /**
     * <p>
     * Accuracy test for method <code>getToClients</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testgetToClientsAccuracy() throws Exception {
        notification.setToClients(validArray);

        assertTrue("Valid array should be get.",
            Arrays.equals((long[]) UnitTestHelper.getPrivateField(Notification.class, notification, "toClients"),
                validArray));
    }

    /**
     * <p>
     * Failure test for method <code>setToClients</code>. Set with null is illegal. IllegalArgumentException is
     * expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testsetToClientsWithNull() throws Exception {
        try {
            notification.setToClients(null);
            fail("Set with null is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Failure test for method <code>setToClients</code>. Set with non-positive element is illegal.
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testsetToClientsWithInvalid1() throws Exception {
        try {
            notification.setToClients(invalidArray1);
            fail("Set with non-positive element is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Failure test for method <code>setToClients</code>. Set with non-positive element is illegal.
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testsetToClientsWithInvalid2() throws Exception {
        try {
            notification.setToClients(invalidArray2);
            fail("Set with non-positive element is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Failure test for method <code>setToClients</code>. Set with duplicate elements is illegal.
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testsetToClientsWithInvalid3() throws Exception {
        try {
            notification.setToClients(invalidArray3);
            fail("Set with duplicate elements is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Accuracy test for method <code>setToClients</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testsetToClientsAccuracy() throws Exception {
        notification.setToClients(validArray);

        assertTrue("The array should be set.",
            Arrays.equals(validArray,
                (long[]) UnitTestHelper.getPrivateField(Notification.class, notification, "toClients")));
    }

    /**
     * <p>
     * Accuracy test for method <code>getToResources</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testgetToResourcesAccuracy() throws Exception {
        notification.setToResources(validArray);

        assertTrue("Valid array should be get.",
            Arrays.equals((long[]) UnitTestHelper.getPrivateField(Notification.class, notification, "toResources"),
                validArray));
    }

    /**
     * <p>
     * Failure test for method <code>setToResources</code>. Set with null is illegal. IllegalArgumentException is
     * expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testsetToResourcesWithNull() throws Exception {
        try {
            notification.setToResources(null);
            fail("Set with null is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Failure test for method <code>setToResources</code>. Set with non-positive element is illegal.
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testsetToResourcesWithInvalid1() throws Exception {
        try {
            notification.setToResources(invalidArray1);
            fail("Set with non-positive element is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Failure test for method <code>setToResources</code>. Set with non-positive element is illegal.
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testsetToResourcesWithInvalid2() throws Exception {
        try {
            notification.setToResources(invalidArray2);
            fail("Set with non-positive element is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Failure test for method <code>setToResources</code>. Set with duplicate elements is illegal.
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testsetToResourcesWithInvalid3() throws Exception {
        try {
            notification.setToResources(invalidArray3);
            fail("Set with duplicate elements is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Accuracy test for method <code>setToResources</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testsetToResourcesAccuracy() throws Exception {
        notification.setToResources(validArray);

        assertTrue("The array should be set.",
            Arrays.equals(validArray,
                (long[]) UnitTestHelper.getPrivateField(Notification.class, notification, "toResources")));
    }
}
