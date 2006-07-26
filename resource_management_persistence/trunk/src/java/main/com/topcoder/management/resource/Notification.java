/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource;

/**
 * <p>
 * The Notification class is the final modeling class in this component. It represents a notification, which is an
 * association between an external id (the use and meaning of this field is up to the user of the component), a project,
 * and a notification type. This class is simply a container for these data fields. All data fields (directly in this
 * class) are immutable and have only getters.
 * </p>
 *
 * <p>
 * This class is mutable because its base class is mutable. Hence it is not thread-safe.
 * </p>
 *
 * @author aubergineanode, TCSDEVELOPER
 * @version 1.0
 */
public class Notification extends AuditableResourceStructure {

    /**
     * The identifier of the project that the notification applies to This field is immutable and set in the
     * constructor.
     *
     * <p>
     * Its value must always be greater than 0.
     * This field is set in the constructor and retrieved through the getProject method.
     * </p>
     *
     */
    private final long project;

    /**
     * The type of the notification.
     * This field is immutable.
     *
     * <p>
     * Its value must always be non-null. This field is set in the constructor and retrieved through the
     * getNotificationType method.
     * </p>
     */
    private final NotificationType notificationType;

    /**
     * The identifier of the external item (for example, user) that the notification applies to.
     *
     * <p>
     * This field is immutable. Its value must always be greater than 0.
     * This field is set in the constructor and retrieved through the getExternalId method.
     * </p>
     */
    private final long externalId;

    /**
     * Creates a new Notification.
     *
     * @param project
     *            The id of the project the notification is associated with
     * @param notificationType
     *            The type of the notification
     * @param externalId
     *            The external id (for example, user) where notifications go
     * @throws IllegalArgumentException
     *             If project or externalId is no greater than 0, or notificationType is null
     */
    public Notification(long project, NotificationType notificationType, long externalId) {
        Helper.checkPositiveValue(project, "project");
        Helper.checkNull(notificationType, "notificationType");
        Helper.checkPositiveValue(externalId, "externalId");

        this.project = project;
        this.notificationType = notificationType;
        this.externalId = externalId;
    }

    /**
     * Gets the identifier of the project the notification applies to.
     *
     * <p>
     * The result of the project identifier will always be greater than 0.
     * </p>
     *
     * @return The id of the project the notification applies to.
     */
    public long getProject() {
        return project;
    }

    /**
     * Gets the notification type of the notification.
     *
     * @return The notification type of the notification
     */
    public NotificationType getNotificationType() {
        return notificationType;
    }

    /**
     * Gets the external id of the item (user for example) that is registered for a notification.
     *
     * <p>
     * The result of the external id will always be greater than 0.
     * </p>
     *
     * @return The external id registered for notification
     */
    public long getExternalId() {
        return externalId;
    }
}
