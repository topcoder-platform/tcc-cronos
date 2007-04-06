/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.notification.accuracytests;

import java.util.Date;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;
import com.topcoder.timetracker.notification.Notification;

/**
 * <p>
 * Accuracy Unit test cases for Notification.
 * </p>
 *
 * @author victorsam
 * @version 3.2
 */
public class NotificationAccuracyTests extends TestCase {
    /**
     * <p>Notification instance for testing.</p>
     */
    private Notification instance;

    /**
     * <p>Setup test environment.</p>
     *
     */
    protected void setUp() {
        instance = new Notification();
    }

    /**
     * <p>Tears down test environment.</p>
     *
     */
    protected void tearDown() {
        instance = null;
    }

    /**
     * <p>Return all tests.</p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(NotificationAccuracyTests.class);
    }

    /**
     * <p>Tests ctor Notification#Notification() for accuracy.</p>
     */
    public void testCtor1() {
        assertNotNull("Failed to create Notification instance.", instance);
    }

    /**
     * <p>Tests Notification#setMessage(String) for accuracy.</p>
     */
    public void testSetMessage() {
        instance.setMessage("message");
        assertEquals("Failed to set the message correctly.", "message", instance.getMessage());
    }

    /**
     * <p>Tests Notification#getCompanyId() for accuracy.</p>
     */
    public void testGetCompanyId() {
        instance.setCompnayId(8);
        assertEquals("Failed to get the company id correctly.", 8, instance.getCompanyId());
    }

    /**
     * <p>Tests Notification#setCompnayId(long) for accuracy.</p>
     */
    public void testSetCompnayId() {
        instance.setCompnayId(8);
        assertEquals("Failed to set the company id correctly.", 8, instance.getCompanyId());
    }

    /**
     * <p>Tests Notification#getSubject() for accuracy.</p>
     */
    public void testGetSubject() {
        instance.setSubject("subject");
        assertEquals("Failed to get the subject correctly.", "subject", instance.getSubject());
    }

    /**
     * <p>Tests Notification#getFromAddress() for accuracy.</p>
     */
    public void testGetFromAddress() {
        instance.setFromAddress("address");
        assertEquals("Failed to get the from address correctly.", "address", instance.getFromAddress());
    }

    /**
     * <p>Tests Notification#setFromAddress(String) for accuracy.</p>
     */
    public void testSetFromAddress() {
        instance.setFromAddress("address");
        assertEquals("Failed to set the from address correctly.", "address", instance.getFromAddress());
    }

    /**
     * <p>Tests Notification#getLastTimeSent() for accuracy.</p>
     */
    public void testGetLastTimeSent() {
        Date time = new Date();
        instance.setLastTimeSent(time);
        assertSame("Failed to get the last time send correctly.", time, instance.getLastTimeSent());
    }

    /**
     * <p>Tests Notification#setLastTimeSent(Date) for accuracy.</p>
     */
    public void testSetLastTimeSent() {
        Date time = new Date();
        instance.setLastTimeSent(time);
        assertSame("Failed to set the last time send correctly.", time, instance.getLastTimeSent());
    }

    /**
     * <p>Tests Notification#getNextTimeToSend() for accuracy.</p>
     */
    public void testGetNextTimeToSend() {
        Date time = new Date();
        instance.setNextTimeToSend(time);
        assertSame("Failed to get the next time to send correctly.", time, instance.getNextTimeToSend());
    }

    /**
     * <p>Tests Notification#setNextTimeToSend(Date) for accuracy.</p>
     */
    public void testSetNextTimeToSend() {
        Date time = new Date();
        instance.setNextTimeToSend(time);
        assertSame("Failed to set the next time to send correctly.", time, instance.getNextTimeToSend());
    }

    /**
     * <p>Tests Notification#getScheduleId() for accuracy.</p>
     */
    public void testGetScheduleId() {
        instance.setScheduleId(6);
        assertEquals("Failed to get the schedule id correctly.", 6, instance.getScheduleId());
    }

    /**
     * <p>Tests Notification#setScheduleId(long) for accuracy.</p>
     */
    public void testSetScheduleId() {
        instance.setScheduleId(6);
        assertEquals("Failed to set the schedule id correctly.", 6, instance.getScheduleId());
    }

    /**
     * <p>Tests Notification#getToProjects() for accuracy.</p>
     */
    public void testGetToProjects() {
        instance.setToProjects(new long[] {5});
        assertEquals("Failed to get to projects correctly.", 5, instance.getToProjects()[0]);
    }

    /**
     * <p>Tests Notification#setToProjects(long[]) for accuracy.</p>
     */
    public void testSetToProjects() {
        instance.setToProjects(new long[] {5});
        assertEquals("Failed to set to projects correctly.", 5, instance.getToProjects()[0]);
    }

    /**
     * <p>Tests Notification#getToClients() for accuracy.</p>
     */
    public void testGetToClients() {
        instance.setToClients(new long[] {5});
        assertEquals("Failed to get to clients correctly.", 5, instance.getToClients()[0]);
    }

    /**
     * <p>Tests Notification#setToClients(long[]) for accuracy.</p>
     */
    public void testSetToClients() {
        instance.setToClients(new long[] {5});
        assertEquals("Failed to set to clients correctly.", 5, instance.getToClients()[0]);
    }

    /**
     * <p>Tests Notification#getToResources() for accuracy.</p>
     */
    public void testGetToResources() {
    }

    /**
     * <p>Tests Notification#setToResources(long[]) for accuracy.</p>
     */
    public void testSetToResources() {
        instance.setToResources(new long[] {5});
        assertEquals("Failed to set to resources correctly.", 5, instance.getToResources()[0]);
    }

    /**
     * <p>Tests Notification#getMessage() for accuracy.</p>
     */
    public void testGetMessage() {
        instance.setMessage("message");
        assertEquals("Failed to get the message correctly.", "message", instance.getMessage());
    }

    /**
     * <p>Tests Notification#isActive() for accuracy.</p>
     */
    public void testIsActive() {
        instance.setActive(true);
        assertTrue("Failed to get the active correctly.", instance.isActive());
    }

    /**
     * <p>Tests Notification#setActive(boolean) for accuracy.</p>
     */
    public void testSetActive() {
        instance.setActive(true);
        assertTrue("Failed to set the active correctly.", instance.isActive());
    }

    /**
     * <p>Tests Notification#setSubject(String) for accuracy.</p>
     */
    public void testSetSubject() {
        instance.setSubject("subject");
        assertEquals("Failed to set the subject correctly.", "subject", instance.getSubject());
    }

}