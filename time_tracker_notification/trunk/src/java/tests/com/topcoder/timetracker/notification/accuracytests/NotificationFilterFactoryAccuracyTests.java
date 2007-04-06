/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.notification.accuracytests;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.GreaterThanOrEqualToFilter;
import com.topcoder.search.builder.filter.LikeFilter;
import com.topcoder.timetracker.notification.NotificationFilterFactory;
import com.topcoder.timetracker.notification.StringMatchType;

/**
 * <p>
 * Accuracy Unit test cases for NotificationFilterFactory.
 * </p>
 *
 * @author victorsam
 * @version 3.2
 */
public class NotificationFilterFactoryAccuracyTests extends TestCase {
    /**
     * <p>NotificationFilterFactory instance for testing.</p>
     */
    private NotificationFilterFactory instance;

    /**
     * <p>Setup test environment.</p>
     *
     */
    protected void setUp() {
        Map filterNames = new HashMap();
        filterNames.put("PROJECT_ID_NAME", "project_id");
        filterNames.put("COMPANY_ID_NAME", "company_id");
        filterNames.put("CLIENT_ID_NAME", "client_id");
        filterNames.put("RESOURCE_ID_NAME", "notification_id");
        filterNames.put("ACTIVE_NAME", "status");
        filterNames.put("LAST_SENT_NAME", "last_time_sent");
        filterNames.put("NEXT_SEND_NAME", "next_time_send");
        filterNames.put("FROM_LINE_NAME", "from_line");
        filterNames.put("MESSAGE_NAME", "message");
        filterNames.put("SUBJECT_NAME", "subject");
        filterNames.put("CREATION_USER_NAME", "creation_user");
        filterNames.put("MODIFICATION_USER_NAME", "modification_user");
        filterNames.put("CREATION_DATE_NAME", "creation_date");
        filterNames.put("MODIFICATION_DATE_NAME", "modification_date");

        instance = new NotificationFilterFactory(filterNames);
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
        return new TestSuite(NotificationFilterFactoryAccuracyTests.class);
    }

    /**
     * <p>Tests ctor NotificationFilterFactory#NotificationFilterFactory(Map) for accuracy.</p>
     */
    public void testCtor1() {
        assertNotNull("Failed to create NotificationFilterFactory instance.", instance);
    }

    /**
     * <p>Tests NotificationFilterFactory#createProjectIdFilter(long) for accuracy.</p>
     */
    public void testCreateProjectIdFilter() {
        EqualToFilter filter = (EqualToFilter) instance.createProjectIdFilter(8);
        assertEquals("Failed to create project id filter correctly.", "project_id", filter.getName());
    }

    /**
     * <p>Tests NotificationFilterFactory#createCompanyIdFilter(long) for accuracy.</p>
     */
    public void testCreateCompanyIdFilter() {
        EqualToFilter filter = (EqualToFilter) instance.createCompanyIdFilter(8);
        assertEquals("Failed to create company id filter correctly.", "company_id", filter.getName());
    }

    /**
     * <p>Tests NotificationFilterFactory#createClientIdFilter(long) for accuracy.</p>
     */
    public void testCreateClientIdFilter() {
        EqualToFilter filter = (EqualToFilter) instance.createClientIdFilter(8);
        assertEquals("Failed to create client id filter correctly.", "client_id", filter.getName());
    }

    /**
     * <p>Tests NotificationFilterFactory#createResourceIdFilter(long) for accuracy.</p>
     */
    public void testCreateResourceIdFilter() {
        EqualToFilter filter = (EqualToFilter) instance.createResourceIdFilter(8);
        assertEquals("Failed to create resource id filter correctly.", "notification_id", filter.getName());
    }

    /**
     * <p>Tests NotificationFilterFactory#createActiveFilter(boolean) for accuracy.</p>
     */
    public void testCreateActiveFilter() {
        EqualToFilter filter = (EqualToFilter) instance.createActiveFilter(true);
        assertEquals("Failed to create active filter correctly.", "status", filter.getName());
    }

    /**
     * <p>Tests NotificationFilterFactory#createLastSentFilter(Date,Date) for accuracy.</p>
     */
    public void testCreateLastSentFilter() {
        GreaterThanOrEqualToFilter filter = (GreaterThanOrEqualToFilter) instance.createLastSentFilter(new Date(1000),
            null);
        assertEquals("Failed to create last sent filter correctly.", "last_time_sent", filter.getName());
    }

    /**
     * <p>Tests NotificationFilterFactory#createNextSendFilter(Date,Date) for accuracy.</p>
     */
    public void testCreateNextSendFilter() {
        GreaterThanOrEqualToFilter filter = (GreaterThanOrEqualToFilter) instance.createNextSendFilter(new Date(1000),
            null);
        assertEquals("Failed to create last sent filter correctly.", "next_time_send", filter.getName());
    }

    /**
     * <p>Tests NotificationFilterFactory#createFromLineFilter(String,StringMatchType) for accuracy.</p>
     */
    public void testCreateFromLineFilter() {
        LikeFilter filter = (LikeFilter) instance.createFromLineFilter("searchString", StringMatchType.ENDS_WITH);
        assertEquals("Failed to create from line filter correctly.", "from_line", filter.getName());
    }

    /**
     * <p>Tests NotificationFilterFactory#createMessageFilter(String,StringMatchType) for accuracy.</p>
     */
    public void testCreateMessageFilter() {
        EqualToFilter filter = (EqualToFilter) instance.createMessageFilter("searchString",
            StringMatchType.EXACT_MATCH);
        assertEquals("Failed to create message filter correctly.", "message", filter.getName());
    }

    /**
     * <p>Tests NotificationFilterFactory#createSubjectFilter(String,StringMatchType) for accuracy.</p>
     */
    public void testCreateSubjectFilter() {
        EqualToFilter filter = (EqualToFilter) instance.createSubjectFilter("searchString",
            StringMatchType.EXACT_MATCH);
        assertEquals("Failed to create subject filter correctly.", "subject", filter.getName());
    }

    /**
     * <p>Tests NotificationFilterFactory#createCreationUserFilter(String,StringMatchType) for accuracy.</p>
     */
    public void testCreateCreationUserFilter() {
        EqualToFilter filter = (EqualToFilter) instance.createCreationUserFilter("searchString",
            StringMatchType.EXACT_MATCH);
        assertEquals("Failed to create creation user filter correctly.", "creation_user", filter.getName());
    }

    /**
     * <p>Tests NotificationFilterFactory#createModificationUserFilter(String,StringMatchType) for accuracy.</p>
     */
    public void testCreateModificationUserFilter() {
        EqualToFilter filter = (EqualToFilter) instance.createModificationUserFilter("searchString",
            StringMatchType.EXACT_MATCH);
        assertEquals("Failed to create modification user filter correctly.", "modification_user", filter.getName());
    }

    /**
     * <p>Tests NotificationFilterFactory#createCreationDateFilter(Date,Date) for accuracy.</p>
     */
    public void testCreateCreationDateFilter() {
        GreaterThanOrEqualToFilter filter = (GreaterThanOrEqualToFilter) instance.createCreationDateFilter(new Date(
            1000), null);
        assertEquals("Failed to create creation date filter correctly.", "creation_date", filter.getName());
    }

    /**
     * <p>Tests NotificationFilterFactory#createModificationDateFilter(Date,Date) for accuracy.</p>
     */
    public void testCreateModificationDateFilter() {
        GreaterThanOrEqualToFilter filter = (GreaterThanOrEqualToFilter) instance.createModificationDateFilter(
            new Date(1000), null);
        assertEquals("Failed to create modification date filter correctly.", "modification_date", filter.getName());
    }

}