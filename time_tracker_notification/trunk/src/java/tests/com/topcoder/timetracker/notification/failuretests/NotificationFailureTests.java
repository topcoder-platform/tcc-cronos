/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.notification.failuretests;

import com.topcoder.timetracker.notification.Notification;

import junit.framework.TestCase;

/**
 * <p>
 * Failure test for <code>{@link Notification}</code> class
 * </p>
 * @author FireIce
 * @version 1.0
 */
public class NotificationFailureTests extends TestCase {
    /**
     * <p>
     * Represents the Notification instance used in tests.
     * </p>
     */
    private Notification notification = new Notification();

    /**
     * <p>
     * Failure test for <code>{@link Notification#setCompnayId(long)}</code> method.
     * </p>
     */
    public void testSetCompnayId_NegativeCompnayIdId() {
        try {
            notification.setCompnayId(-1);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link Notification#setCompnayId(long)}</code> method.
     * </p>
     */
    public void testSetCompnayId_ZeroCompnayIdId() {
        try {
            notification.setCompnayId(0);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link Notification#setScheduleId(long)}</code> method.
     * </p>
     */
    public void testSetScheduleId_NegativeScheduleIdId() {
        try {
            notification.setScheduleId(-1);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link Notification#setScheduleId(long)}</code> method.
     * </p>
     */
    public void testSetScheduleId_ZeroScheduleIdId() {
        try {
            notification.setScheduleId(0);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link Notification#setSubject(String)}</code> method.
     * </p>
     */
    public void testSetSubject_NullSubject() {
        try {
            notification.setSubject(null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link Notification#setSubject(String)}</code> method.
     * </p>
     */
    public void testSetSubject_EmptySubject() {
        try {
            notification.setSubject("");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link Notification#setSubject(String)}</code> method.
     * </p>
     */
    public void testSetSubject_TrimmedEmptySubject() {
        try {
            notification.setSubject("  ");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link Notification#setMessage(String)}</code> method.
     * </p>
     */
    public void testSetMessage_NullMessage() {
        try {
            notification.setMessage(null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link Notification#setMessage(String)}</code> method.
     * </p>
     */
    public void testSetMessage_EmptyMessage() {
        try {
            notification.setMessage("");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link Notification#setMessage(String)}</code> method.
     * </p>
     */
    public void testSetMessage_TrimmedEmptyMessage() {
        try {
            notification.setMessage("  ");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link Notification#setFromAddress(String)}</code> method.
     * </p>
     */
    public void testSetFromAddress_NullFromAddress() {
        try {
            notification.setFromAddress(null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link Notification#setFromAddress(String)}</code> method.
     * </p>
     */
    public void testSetFromAddress_EmptyFromAddress() {
        try {
            notification.setFromAddress("");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link Notification#setFromAddress(String)}</code> method.
     * </p>
     */
    public void testSetFromAddress_TrimmedEmptyFromAddress() {
        try {
            notification.setFromAddress("  ");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link Notification#setLastTimeSent(Date)}</code> method.
     * </p>
     */
    public void testSetLastTimeSent_NullLastTimeSent() {
        try {
            notification.setLastTimeSent(null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link Notification#setNextTimeToSend(Date)}</code> method.
     * </p>
     */
    public void testSetNextTimeToSend_NullNextTimeToSend() {
        try {
            notification.setNextTimeToSend(null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link Notification#setToProjects(long[])}</code> method.
     * </P>
     */
    public void testSetToProjects_NullToProjects() {
        try {
            notification.setToProjects(null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link Notification#setToProjects(long[])}</code> method.
     * </P>
     */
    public void testSetToProjects_NegateiveId() {
        try {
            notification.setToProjects(new long[] {-1});
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link Notification#setToProjects(long[])}</code> method.
     * </P>
     */
    public void testSetToProjects_DuplicateId() {
        try {
            notification.setToProjects(new long[] {1, 1});
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link Notification#setToClients(long[])}</code> method.
     * </P>
     */
    public void testSetToClients_NullToClients() {
        try {
            notification.setToClients(null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link Notification#setToClients(long[])}</code> method.
     * </P>
     */
    public void testSetToClients_NegateiveId() {
        try {
            notification.setToClients(new long[] {-1});
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link Notification#setToClients(long[])}</code> method.
     * </P>
     */
    public void testSetToClients_DuplicateId() {
        try {
            notification.setToClients(new long[] {1, 1});
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link Notification#setToResources(long[])}</code> method.
     * </P>
     */
    public void testSetToResources_NullToResources() {
        try {
            notification.setToResources(null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link Notification#setToResources(long[])}</code> method.
     * </P>
     */
    public void testSetToResources_NegateiveId() {
        try {
            notification.setToResources(new long[] {-1});
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link Notification#setToResources(long[])}</code> method.
     * </P>
     */
    public void testSetToResources_DuplicateId() {
        try {
            notification.setToResources(new long[] {1, 1});
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
