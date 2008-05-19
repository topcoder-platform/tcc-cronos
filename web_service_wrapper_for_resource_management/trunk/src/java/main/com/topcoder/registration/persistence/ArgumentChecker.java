/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.persistence;

import com.topcoder.management.resource.NotificationType;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.resource.ResourceRole;
import com.topcoder.util.errorhandling.ExceptionUtils;

/**
 * <p>
 * This is a helper class used to validate arguments.
 * </p>
 *
 * <p>
 * It will be used by both <code>ResourceManagerServiceClient</code>
 * and <code>ResourceManagerServiceBean</code>.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 */
public final class ArgumentChecker {

    /**
     * <p>The private constructor used to avoid creating instances.</p>
     */
    private ArgumentChecker() {
        // do nothing
    }

    /**
     * <p>
     * Checks if the value of an object is null.
     * </p>
     *
     * @param obj The object to check.
     * @param usage The usage of the object.
     *
     * @throws IllegalArgumentException If given object is null.
     */
    public static final void checkNull(Object obj, String usage) {
        ExceptionUtils.checkNull(obj, null, null, usage + " should not be null.");
    }

    /**
     * <p>
     * Checks if the value of a string is null or empty.
     * </p>
     *
     * @param str The string to check.
     * @param usage The usage of the string.
     *
     * @throws IllegalArgumentException If given string is null or empty.
     */
    public static final void checkNullOrEmpty(String str, String usage) {
        ExceptionUtils.checkNullOrEmpty(str, null, null, usage + " should not be null or empty.");
    }

    /**
     * <p>
     * Checks if the value of long is positive.
     * </p>
     *
     * @param value The long value.
     * @param usage The usage of long value.
     *
     * @throws IllegalArgumentException If the value is less than or equal to 0.
     */
    public static final void checkLongPositive(long value, String usage) {
        if (value <= 0) {
            throw new IllegalArgumentException(usage + " must be positive.");
        }
    }

    /**
     * <p>
     * Validates an instance of <code>Resource</code>. <code>IllegalArgumentException</code> would be thrown
     * if it is invalid.
     * </p>
     *
     * @param resource The <code>Resource</code> to validate.
     * @param usage The usage of <code>Resource</code>.
     *
     * @throws IllegalArgumentException if a required field of the resource is not set (if resource.getResourceRole()
     *         is null), or if the resource role is associated with a phase type and the resource is not associated
     *         with a phase, or if resource is null. Or if the resource id has not been set.
     */
    private static final void validateResourceToBeUpdated(Resource resource, String usage) {
        // check null
        checkNull(resource, usage);

        // check id has been set
        checkLongPositive(resource.getId(), "Id of " + usage);

        // if the getReourceRole() is null
        checkNull(resource.getResourceRole(), "The resource role of " + usage);

        // if the resource role is associated with a phase type but the resource is not associated
        // with a phase
        if (resource.getResourceRole().getPhaseType() != null && resource.getPhase() == null) {
            throw new IllegalArgumentException("The resource role is associated with a phase type but the " + usage
                    + " is not associated with a phase.");
        }
    }

    /**
     * <p>
     * The resource is going to be updated by given operator. Validate them.
     * </p>
     *
     * <p>
     *     <strong>The given <code>Resource</code> must have following fields set:</strong>
     *     <ul>
     *         <li>{@link Resource#getId()} must be set(&gt;0);</li>
     *         <li>{@link Resource#getResourceRole()} is not null;</li>
     *         <li>If the {@link Resource#getResourceRole()} is associated with a phase type,
     *         then the <code>Resource</code> must be associated with a phase.</li>
     *     </ul>
     * </p>
     *
     * @param resource The <code>Resource</code> to validate.
     * @param operator The operator who performs updating.
     *
     * @throws IllegalArgumentException If any parameter is null. Or if operator is empty string.
     *         Or if a required field of the given resource is not set(i.e. the resource's role is
     *         null, or the resource role has a phase type but the resource does not has a phase set).
     *         Or if the resource id is -1(which means not set).
     */
    public static final void checkResourceToBeUpdated(Resource resource, String operator) {

        validateResourceToBeUpdated(resource, "Resource to be updated");
        checkNullOrEmpty(operator, "Operator who updates resource");
    }

    /**
     * <p>
     * This resources that belong to the given project are going to be updated by given operator. Validate them.
     * </p>
     *
     * <p>
     *     <strong>Each <code>Resource</code> in the given array must have following fields set:</strong>
     *     <ul>
     *         <li>{@link Resource#getId()} must be set(&gt;0);</li>
     *         <li>{@link Resource#getProject()} is not null and should be same as given <em>project</em>;</li>
     *         <li>{@link Resource#getResourceRole()} is not null;</li>
     *         <li>If the {@link Resource#getResourceRole()} is associated with a phase type,
     *         then the <code>Resource</code> must be associated with a phase.</li>
     *     </ul>
     * </p>
     *
     * @param resources The resources to be updated and associated with given <em>project</em>.
     * @param project The project to which the resources belong. Must be positive(&gt; 0).
     * @param operator The operator that performs the updating.
     *
     * @throws IllegalArgumentException If any parameter is null. Or if operator is empty string.
     *         Or if the given array contains null elements.
     *         Or if the given <em>project</em> &lt;= 0.
     *         Or if a required field of each resource in the given array is not set(i.e. the resource's role
     *         is null, or the resource role has a phase type but the resource does not has a phase set).
     *         Or if the resource id is -1(which means not set).
     */
    public static final void checkResourcesToBeUpdated(Resource[] resources, long project, String operator) {

        checkNull(resources, "Array of resources to be updated");

        checkLongPositive(project, "The project to which the resources belong");

        for (int i = 0; i < resources.length; i++) {
            Resource resource = resources[i];
            validateResourceToBeUpdated(resource, "The resource to be updated at location " + i);
            Long pProject = resource.getProject();
            checkNull(pProject, "The project of resource to be updated at location " + i);
            if (pProject.longValue() != project) {
                throw new IllegalArgumentException("The resource to be updated at location " + i
                        + " contains a project id which is not the same "
                        + "as the project argument: " + pProject + " != " + project);
            }
        }


        checkNullOrEmpty(operator, "Operator who updates resources");
    }

    /**
     * <p>
     * The resource is going to be removed by given operator. Validate them.
     * </p>
     *
     * <p>
     *     <strong>About the {@link Resource#getId()}:</strong>
     *     The given <code>Resource</code> should have id already been set, which means the
     *     {@link Resource#getId()} should be positive(&gt; 0).
     * </p>
     *
     * @param resource The resource to be removed. Must have a positive(&gt; 0) id.
     * @param operator The operator that performs the removing.
     *
     * @throws IllegalArgumentException If any parameter is null. Or if operator is empty string.
     *         Or if resource id is -1(which means not set).
     */
    public static final void checkResourceToBeRemoved(Resource resource, String operator) {
        checkNull(resource, "Resource to be removed");
        checkLongPositive(resource.getId(), "Id of resource to be removed");
        checkNullOrEmpty(operator, "Operator who removes resource");
    }

    /**
     * <p>
     * The resource role is going to be updated by given operator. Validate them.
     * </p>
     *
     * <p>
     *     <strong>The given <code>ResourceRole</code> must have following fields set:</strong>
     *     <ul>
     *         <li>{@link ResourceRole#getId()} must be set(&gt;0);</li>
     *         <li>{@link ResourceRole#getName()} is not null;</li>
     *         <li>{@link ResourceRole#getDescription()} is not null.</li>
     *     </ul>
     * </p>
     *
     * @param resourceRole The resource role to be updated.
     * @param operator The operator who performs the updating.
     *
     * @throws IllegalArgumentException If any parameter is null. Or if operator is empty string.
     *         Or if a required field of the resource role is missing (i.e. name or description of
     *         the resource role is null).
     *         Or if the resource role id is -1(which means not set).
     */
    public static final void checkResourceRoleToBeUpdated(ResourceRole resourceRole, String operator) {

        String usage = "ResourceRole to be updated";
        checkNull(resourceRole, usage);
        checkLongPositive(resourceRole.getId(), "Id of resource role to be removed");
        checkNull(resourceRole.getName(), "Name of " + usage);
        checkNull(resourceRole.getDescription(), "Description of " + usage);
        checkNullOrEmpty(operator, "Operator who updates resource role");
    }

    /**
     * <p>
     * The resource role is going to be removed by given operator. Validate them.
     * </p>
     *
     * <p>
     *     <strong>About the {@link ResourceRole#getId()}:</strong>
     *     The given <code>ResourceRole</code> should have id already been set, which means the
     *     {@link ResourceRole#getId()} should be positive(&gt; 0).
     * </p>
     *
     * @param resourceRole The resource role to be removed.
     * @param operator The operator who performs the removing.
     *
     * @throws IllegalArgumentException If any parameter is null. Or if operator is empty string.
     *         Or if resource role id is -1(which means not set).
     */
    public static final void checkResourceRoleToBeRemoved(ResourceRole resourceRole, String operator) {

        checkNull(resourceRole, "ResourceRole to be removed");
        checkLongPositive(resourceRole.getId(), "Id of ResourceRole to be removed");
        checkNullOrEmpty(operator, "Operator who removes resource role");
    }

    /**
     * <p>
     * The notification type is going to be updated by given operator. Validate them.
     * </p>
     *
     * <p>
     *     <strong>The given <code>NotificationType</code> must have following fields set:</strong>
     *     <ul>
     *         <li>{@link NotificationType#getId()} must be set(&gt;0);</li>
     *         <li>{@link NotificationType#getName()} is not null;</li>
     *         <li>{@link NotificationType#getDescription()} is not null.</li>
     *     </ul>
     * </p>
     *
     * @param notificationType The notification type to be updated.
     * @param operator The operator who performs the updating.
     *
     * @throws IllegalArgumentException If any parameter is null or if operator is empty string.
     *         Or if a required field of the notification type is missing (i.e. name or description of
     *         the notification type is null).
     *         Or if the notification type id is -1(which means not set).
     */
    public static final void checkNotificationTypeToBeUpdated(NotificationType notificationType, String operator) {
        String usage = "NotificationType to be updated";
        checkNull(notificationType, usage);
        checkLongPositive(notificationType.getId(), "Id of notification type to be removed");
        checkNull(notificationType.getName(), "Name of " + usage);
        checkNull(notificationType.getDescription(), "Description of " + usage);
        checkNullOrEmpty(operator, "Operator who updates notification type");
    }

    /**
     * <p>
     * The notification type is going to be removed by given operator. Validate them.
     * </p>
     *
     * <p>
     *     <strong>About the {@link NotificationType#getId()}:</strong>
     *     The given <code>NotificationType</code> should have id already been set, which means the
     *     {@link NotificationType#getId()} should be positive(&gt; 0).
     * </p>
     *
     * @param notificationType The notification type to remove.
     * @param operator The operator who performs the removing.
     *
     * @throws IllegalArgumentException If any parameter is null or if operator is empty string.
     *         Or if notification type is -1(which means not set).
     */
    public static final void checkNotificationTypeToBeRemoved(NotificationType notificationType, String operator) {
        checkNull(notificationType, "NotificationType to be removed");
        checkLongPositive(notificationType.getId(), "Id of NotificationType to be removed");
        checkNullOrEmpty(operator, "Operator who removes notification type");
    }

    /**
     * <p>
     * The notifications are going to be added for users by given operator. Validate them.
     * </p>
     *
     * @param users The users to add notifications for.
     * @param project The project the notifications apply to.
     * @param notificationType The type of notifications to add.
     * @param operator The operation who performs the adding.
     *
     * @throws IllegalArgumentException If operator or users is null or if operator is empty string.
     *         Or if any item of users, or project, or notificationType is &lt;= 0.
     *         Or is the users array is empty.
     */
    public static final void checkNotificationsToBeAdded(long[] users, long project, long notificationType,
        String operator) {
        checkForNotifications(users, project, notificationType, operator, "add");
    }

    /**
     * <p>
     * The notifications are going to be added/removed for users by given operator. Validate them.
     * </p>
     *
     * @param users The users to add/remove notifications for.
     * @param project The project the notifications apply to.
     * @param notificationType The type of notifications to add/remove.
     * @param operator The operation who performs the adding/removing.
     * @param action Indicates whether the action is to add or to removed.
     *
     * @throws IllegalArgumentException If operator or users is null or if operator is empty string.
     *         Or if any item of users, or project, or notificationType is &lt;= 0.
     *         Or is the users array is empty.
     */
    private static final void checkForNotifications(long[] users, long project, long notificationType,
        String operator, String action) {
        checkNull(users, "Array of user ids to " + action + " notifications");
        if (users.length == 0) {
            throw new IllegalArgumentException(
                "Array of user ids to " + action + " notifications should not be empty.");
        }
        for (int i = 0; i < users.length; i++) {
            checkLongPositive(users[i], "The user id to " + action + " notifications for at location [" + i + "]");
        }

        checkLongPositive(project, "The project the notifications to " + action);
        checkLongPositive(notificationType, "The type of notifications to " + action);
        checkNullOrEmpty(operator, "Operator who " + action + "s notifications");
    }

    /**
     * <p>
     * The notifications are going to be removed for users by given operator. Validate them.
     * </p>
     *
     * @param users The users to remove notifications for.
     * @param project The project the notifications apply to.
     * @param notificationType The type of notifications to remove.
     * @param operator The operation who performs the removing.
     *
     * @throws IllegalArgumentException If operator or users is null or if operator is empty string.
     *         Or if any item of users, or project, or notificationType is &lt;= 0.
     *         Or is the users array is empty.
     */
    public static final void checkNotificationsToBeRemoved(long[] users, long project, long notificationType,
        String operator) {
        checkForNotifications(users, project, notificationType, operator, "remove");
    }

    /**
     * <p>
     * The notifications are going to be retrieved by given project and type. Validate them.
     * </p>
     *
     * @param project the project to get notifications for.
     * @param notificationType the type of notifications to retrieve.
     *
     * @throws IllegalArgumentException If project or notificationType is &lt;= 0.
     */
    public static final void checkNotificationsToBeRetrieved(long project, long notificationType) {
        checkLongPositive(project, "The project to get notifications for");
        checkLongPositive(notificationType, "The type of notifications to retrieve");
    }
}
