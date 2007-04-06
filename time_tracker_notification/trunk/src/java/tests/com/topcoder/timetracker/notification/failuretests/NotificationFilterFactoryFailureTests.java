/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.notification.failuretests;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.topcoder.timetracker.notification.NotificationFilterFactory;
import com.topcoder.timetracker.notification.StringMatchType;

import junit.framework.TestCase;

/**
 * <p>
 * Failure test for <code>{@link NotificationFilterFactory}</code> class.
 * </p>
 * @author FireIce
 * @version 1.0
 */
public class NotificationFilterFactoryFailureTests extends TestCase {

    /**
     * <p>
     * Represents the NotificationFilterFactory instance.
     * </p>
     */
    private NotificationFilterFactory notificationFilterFactory;

    /**
     * <p>
     * Represents the filterNames map.
     * </p>
     */
    private Map filterNames;

    /**
     * <p>
     * Setup the testing environment.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();

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

        notificationFilterFactory = new NotificationFilterFactory(filterNames);
    }

    /**
     * <p>
     * Failure test for <code>{@link NotificationFilterFactory#NotificationFilterFactory(java.util.Map)}</code>
     * method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testNotificationFilterFactory_NullFilterNames() {
        try {
            new NotificationFilterFactory(null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link NotificationFilterFactory#NotificationFilterFactory(java.util.Map)}</code>
     * method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testNotficationFilterFactory_FilterNamesContainsNullKey() {

        filterNames.put(null, "table_name");
        try {
            new NotificationFilterFactory(filterNames);
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link NotificationFilterFactory#NotificationFilterFactory(java.util.Map)}</code>
     * method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testNotficationFilterFactory_FilterNamesContainsEmptyKey() {

        filterNames.put("", "table_name");
        try {
            new NotificationFilterFactory(filterNames);
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link NotificationFilterFactory#NotificationFilterFactory(java.util.Map)}</code>
     * method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testNotficationFilterFactory_FilterNamesContainsTrimmedEmptyKey() {

        filterNames.put("  ", "table_name");
        try {
            new NotificationFilterFactory(filterNames);
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link NotificationFilterFactory#NotificationFilterFactory(java.util.Map)}</code>
     * method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testNotficationFilterFactory_FilterNamesContainsNullValue() {

        filterNames.put(NotificationFilterFactory.CLIENT_ID_NAME, null);
        try {
            new NotificationFilterFactory(filterNames);
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link NotificationFilterFactory#NotificationFilterFactory(java.util.Map)}</code>
     * method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testNotficationFilterFactory_FilterNamesContainsEmptyValue() {

        filterNames.put(NotificationFilterFactory.CLIENT_ID_NAME, "");
        try {
            new NotificationFilterFactory(filterNames);
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link NotificationFilterFactory#NotificationFilterFactory(java.util.Map)}</code>
     * method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testNotficationFilterFactory_FilterNamesContainsTrimmedEmptyValue() {
        filterNames.put(NotificationFilterFactory.CLIENT_ID_NAME, "  ");
        try {
            new NotificationFilterFactory(filterNames);
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link NotificationFilterFactory#NotificationFilterFactory(java.util.Map)}</code>
     * method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testNotficationFilterFactory_MissingRequiredFilterName() {
        filterNames.remove(NotificationFilterFactory.ACTIVE_NAME);

        try {
            new NotificationFilterFactory(filterNames);
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link NotificationFilterFactory#createProjectIdFilter(long)}</code> method.
     * </p>
     */
    public void testCreateProjectIdFilter_ZeroId() {
        try {
            notificationFilterFactory.createProjectIdFilter(0);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link NotificationFilterFactory#createProjectIdFilter(long)}</code> method.
     * </p>
     */
    public void testCreateProjectIdFilter_NegativeId() {
        try {
            notificationFilterFactory.createProjectIdFilter(-1);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link NotificationFilterFactory#createCompanyIdFilter(long)}</code> method.
     * </p>
     */
    public void testCreateCompanyIdFilter_ZeroId() {
        try {
            notificationFilterFactory.createCompanyIdFilter(0);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link NotificationFilterFactory#createCompanyIdFilter(long)}</code> method.
     * </p>
     */
    public void testCreateCompanyIdFilter_NegativeId() {
        try {
            notificationFilterFactory.createCompanyIdFilter(-1);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link NotificationFilterFactory#createClientIdFilter(long)}</code> method.
     * </p>
     */
    public void testCreateClientIdFilter_ZeroId() {
        try {
            notificationFilterFactory.createClientIdFilter(0);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link NotificationFilterFactory#createClientIdFilter(long)}</code> method.
     * </p>
     */
    public void testCreateClientIdFilter_NegativeId() {
        try {
            notificationFilterFactory.createClientIdFilter(-1);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link NotificationFilterFactory#createResourceIdFilter(long)}</code> method.
     * </p>
     */
    public void testCreateResourceIdFilter_ZeroId() {
        try {
            notificationFilterFactory.createResourceIdFilter(0);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link NotificationFilterFactory#createResourceIdFilter(long)}</code> method.
     * </p>
     */
    public void testCreateResourceIdFilter_NegativeId() {
        try {
            notificationFilterFactory.createResourceIdFilter(-1);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link NotificationFilterFactory#createLastSentFilter(Date, Date)}</code> method.
     * </p>
     */
    public void testCreateLastSentFilter_BothNull() {
        try {
            notificationFilterFactory.createLastSentFilter(null, null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link NotificationFilterFactory#createLastSentFilter(Date, Date)}</code> method.
     * </p>
     */
    public void testCreateLastSentFilter_StartAfterEnd() {
        try {
            notificationFilterFactory.createLastSentFilter(new Date(System.currentTimeMillis()), new Date(System
                .currentTimeMillis() - 10000));
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link NotificationFilterFactory#createNextSendFilter(Date, Date)}</code> method.
     * </p>
     */
    public void testCreateNextSendFilter_BothNull() {
        try {
            notificationFilterFactory.createNextSendFilter(null, null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link NotificationFilterFactory#createNextSendFilter(Date, Date)}</code> method.
     * </p>
     */
    public void testCreateNextSendFilter_StartAfterEnd() {
        try {
            notificationFilterFactory.createNextSendFilter(new Date(System.currentTimeMillis()), new Date(System
                .currentTimeMillis() - 10000));
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link NotificationFilterFactory#createFromLineFilter(String, StringMatchType)}</code>
     * method.
     * </p>
     */
    public void testCreateFromLineFilter_NullSearchString() {
        try {
            notificationFilterFactory.createFromLineFilter(null, StringMatchType.EXACT_MATCH);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link NotificationFilterFactory#createFromLineFilter(String, StringMatchType)}</code>
     * method.
     * </p>
     */
    public void testCreateFromLineFilter_EmptySearchString() {
        try {
            notificationFilterFactory.createFromLineFilter("", StringMatchType.EXACT_MATCH);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link NotificationFilterFactory#createFromLineFilter(String, StringMatchType)}</code>
     * method.
     * </p>
     */
    public void testCreateFromLineFilter_TrimmedEmptySearchString() {
        try {
            notificationFilterFactory.createFromLineFilter("  ", StringMatchType.EXACT_MATCH);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link NotificationFilterFactory#createFromLineFilter(String, StringMatchType)}</code>
     * method.
     * </p>
     */
    public void testCreateFromLineFilter_NullStringMatchType() {
        try {
            notificationFilterFactory.createFromLineFilter("Hello", null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link NotificationFilterFactory#createMessageFilter(String, StringMatchType)}</code>
     * method.
     * </p>
     */
    public void testCreateMessageFilter_NullSearchString() {
        try {
            notificationFilterFactory.createMessageFilter(null, StringMatchType.EXACT_MATCH);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link NotificationFilterFactory#createMessageFilter(String, StringMatchType)}</code>
     * method.
     * </p>
     */
    public void testCreateMessageFilter_EmptySearchString() {
        try {
            notificationFilterFactory.createMessageFilter("", StringMatchType.EXACT_MATCH);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link NotificationFilterFactory#createMessageFilter(String, StringMatchType)}</code>
     * method.
     * </p>
     */
    public void testCreateMessageFilter_TrimmedEmptySearchString() {
        try {
            notificationFilterFactory.createMessageFilter("  ", StringMatchType.EXACT_MATCH);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link NotificationFilterFactory#createMessageFilter(String, StringMatchType)}</code>
     * method.
     * </p>
     */
    public void testCreateMessageFilter_NullStringMatchType() {
        try {
            notificationFilterFactory.createMessageFilter("Hello", null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link NotificationFilterFactory#createSubjectFilter(String, StringMatchType)}</code>
     * method.
     * </p>
     */
    public void testCreateSubjectFilter_NullSearchString() {
        try {
            notificationFilterFactory.createSubjectFilter(null, StringMatchType.EXACT_MATCH);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link NotificationFilterFactory#createSubjectFilter(String, StringMatchType)}</code>
     * method.
     * </p>
     */
    public void testCreateSubjectFilter_EmptySearchString() {
        try {
            notificationFilterFactory.createSubjectFilter("", StringMatchType.EXACT_MATCH);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link NotificationFilterFactory#createSubjectFilter(String, StringMatchType)}</code>
     * method.
     * </p>
     */
    public void testCreateSubjectFilter_TrimmedEmptySearchString() {
        try {
            notificationFilterFactory.createSubjectFilter("  ", StringMatchType.EXACT_MATCH);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link NotificationFilterFactory#createSubjectFilter(String, StringMatchType)}</code>
     * method.
     * </p>
     */
    public void testCreateSubjectFilter_NullStringMatchType() {
        try {
            notificationFilterFactory.createSubjectFilter("Hello", null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
    
    /**
     * <p>
     * Failure test for <code>{@link NotificationFilterFactory#createCreationUserFilter(String, StringMatchType)}</code>
     * method.
     * </p>
     */
    public void testCreateCreationUserFilter_NullSearchString() {
        try {
            notificationFilterFactory.createCreationUserFilter(null, StringMatchType.EXACT_MATCH);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link NotificationFilterFactory#createCreationUserFilter(String, StringMatchType)}</code>
     * method.
     * </p>
     */
    public void testCreateCreationUserFilter_EmptySearchString() {
        try {
            notificationFilterFactory.createCreationUserFilter("", StringMatchType.EXACT_MATCH);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link NotificationFilterFactory#createCreationUserFilter(String, StringMatchType)}</code>
     * method.
     * </p>
     */
    public void testCreateCreationUserFilter_TrimmedEmptySearchString() {
        try {
            notificationFilterFactory.createCreationUserFilter("  ", StringMatchType.EXACT_MATCH);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link NotificationFilterFactory#createCreationUserFilter(String, StringMatchType)}</code>
     * method.
     * </p>
     */
    public void testCreateCreationUserFilter_NullStringMatchType() {
        try {
            notificationFilterFactory.createCreationUserFilter("Hello", null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
    
    /**
     * <p>
     * Failure test for <code>{@link NotificationFilterFactory#createModificationUserFilter(String, StringMatchType)}</code>
     * method.
     * </p>
     */
    public void testCreateModificationUserFilter_NullSearchString() {
        try {
            notificationFilterFactory.createModificationUserFilter(null, StringMatchType.EXACT_MATCH);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link NotificationFilterFactory#createModificationUserFilter(String, StringMatchType)}</code>
     * method.
     * </p>
     */
    public void testCreateModificationUserFilter_EmptySearchString() {
        try {
            notificationFilterFactory.createModificationUserFilter("", StringMatchType.EXACT_MATCH);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link NotificationFilterFactory#createModificationUserFilter(String, StringMatchType)}</code>
     * method.
     * </p>
     */
    public void testCreateModificationUserFilter_TrimmedEmptySearchString() {
        try {
            notificationFilterFactory.createModificationUserFilter("  ", StringMatchType.EXACT_MATCH);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link NotificationFilterFactory#createModificationUserFilter(String, StringMatchType)}</code>
     * method.
     * </p>
     */
    public void testCreateModificationUserFilter_NullStringMatchType() {
        try {
            notificationFilterFactory.createModificationUserFilter("Hello", null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
    
    
    /**
     * <p>
     * Failure test for <code>{@link NotificationFilterFactory#createCreationDateFilter(Date, Date)}</code> method.
     * </p>
     */
    public void testCreateCreationDateFilter_BothNull() {
        try {
            notificationFilterFactory.createCreationDateFilter(null, null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link NotificationFilterFactory#createCreationDateFilter(Date, Date)}</code> method.
     * </p>
     */
    public void testCreateCreationDateFilter_StartAfterEnd() {
        try {
            notificationFilterFactory.createCreationDateFilter(new Date(System.currentTimeMillis()), new Date(System
                .currentTimeMillis() - 10000));
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
    
    /**
     * <p>
     * Failure test for <code>{@link NotificationFilterFactory#createModificationDateFilter(Date, Date)}</code> method.
     * </p>
     */
    public void testCreateModificationDateFilter_BothNull() {
        try {
            notificationFilterFactory.createModificationDateFilter(null, null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link NotificationFilterFactory#createModificationDateFilter(Date, Date)}</code> method.
     * </p>
     */
    public void testCreateModificationDateFilter_StartAfterEnd() {
        try {
            notificationFilterFactory.createModificationDateFilter(new Date(System.currentTimeMillis()), new Date(System
                .currentTimeMillis() - 10000));
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
