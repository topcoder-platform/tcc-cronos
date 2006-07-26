/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource.search;

import com.topcoder.management.resource.Helper;
import com.topcoder.search.builder.SearchBundle;
import com.topcoder.search.builder.filter.Filter;

/**
 * <p>
 * The NotificationFilterBuilder class supports building filters for searching for Notifications.
 * </p>
 *
 * <p>
 * This class consists of 2 parts. The first part consists of static Strings that name the fields that are available for
 * searching. All ResourceManager implementations should use SearchBundles that are configured to use these names. The
 * second part of this class consists of convenience methods to create common filters based on these field names.
 * </p>
 *
 * <p>
 * This class has only final static fields/methods, so mutability is not an issue.
 * </p>
 *
 * @author aubergineanode, TCSDEVELOPER
 * @version 1.0
 */
public class NotificationFilterBuilder {

    /**
     * <p>
     * The name of the field under which SearchBuilder Filters can be built in order to filter on the external reference
     * id (example: user) when searching for notifications.
     * </p>
     *
     * <p>
     * This field is final, static, and public, and is used in the createExternalRefIdFilter method.
     * </p>
     *
     */
    public static final String EXTERNAL_REF_ID_FIELD_NAME = "external_ref_id";

    /**
     * <p>
     * The name of the field under which SearchBuilder Filters can be built in order to filter on the id of the project
     * the notification references.
     * </p>
     *
     * <p>
     * This field is final, static, and public, and is used in the createProjectIdFilter method.
     * </p>
     */
    public static final String PROJECT_ID_FIELD_NAME = "project_id";

    /**
     * <p>
     * The name of the field under which SearchBuilder Filters can be built in order to filter on the id of the type of
     * notification.
     * </p>
     *
     * <p>
     * This field is final, static, and public, and is used in the createNotificationTypeIdFilter method.
     * </p>
     *
     */
    public static final String NOTIFICATION_TYPE_ID_FIELD_NAME = "notification_type_id";

    /**
     * Private constructor to prevent instantiation.
     *
     */
    private NotificationFilterBuilder() {
        // empty.
    }

    /**
     * Creates a filter that selects notifications which have the given external reference id.
     *
     * @param externalRefId
     *            The extrenal reference id to filter on
     * @return A filter for selecting notifications which are associated with the given external reference id
     * @throws IllegalArgumentException
     *             If externalRefId is <= 0.
     */
    public static Filter createExternalRefIdFilter(long externalRefId) {
        Helper.checkPositiveValue(externalRefId, "externalRefId");

        return SearchBundle.buildEqualToFilter(EXTERNAL_REF_ID_FIELD_NAME, new Long(externalRefId));
    }

    /**
     * Creates a filter that selects notifications which have the given project id.
     *
     * @param projectId
     *            The project id to filter on
     * @return A filter for selecting notifications which are associated with the given project
     * @throws IllegalArgumentException
     *             If projectId is <= 0.
     */
    public static Filter createProjectIdFilter(long projectId) {
        Helper.checkPositiveValue(projectId, "projectId");

        return SearchBundle.buildEqualToFilter(PROJECT_ID_FIELD_NAME, new Long(projectId));
    }

    /**
     * Creates a filter that selects notifications which have the given notification type id.
     *
     * @return A filter for selecting notifications which are associated with the given notification type
     * @param notificationTypeId
     *            The notification type id to filter on
     * @throws IllegalArgumentException
     *             If notificationTypeId is <= 0.
     */
    public static Filter createNotificationTypeIdFilter(Long notificationTypeId) {
        Helper.checkLongValue(notificationTypeId, "notificationTypeId");

        return SearchBundle.buildEqualToFilter(NOTIFICATION_TYPE_ID_FIELD_NAME, notificationTypeId);
    }
}
