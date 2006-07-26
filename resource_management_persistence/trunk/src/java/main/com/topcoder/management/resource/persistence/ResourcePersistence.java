/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource.persistence;

import com.topcoder.management.resource.Notification;
import com.topcoder.management.resource.NotificationType;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.resource.ResourceRole;

/**
 * <p>
 * The ResourcePersistence interface defines the methods for persisting and retrieving the object model in this
 * component. This interface handles the persistence of the four classes that make up the object model �C Resources,
 * ResourceRoles, Notifications, and NotificationTypes. This interface is not responsible for searching the persistence
 * for the various entities. This is instead handled by a ResourceManager implementation.
 * </p>
 *
 * <p>
 * One large difference between this interface and the ResourceManager is that this interface expects auditing
 * information to be set in the objects before they are passed to the methods of this interfcae. Auditing information is
 * not passed explicitly as in the ResourceManager methods. ResourceManager implementations are responsible for taking
 * the parameters that are explicitly passed to them and setting the corresponding auditing properties on the objects
 * before they are passed to this interface.
 * </p>
 *
 * <p>
 * Implementations of this interface are not required to be thread-safe or immutable.
 * </p>
 *
 *
 * @author aubergineanode, TCSDEVELOPER
 * @version 1.0
 */
public interface ResourcePersistence {
    /**
     * Adds the given Resource to the persistence store. The resource must not already exist (by id) in the persistence
     * store.
     *
     * @param resource
     *            The resource to add to the persistence store
     * @throws ResourcePersistenceException
     * @throws IllegalArgumentException
     *             If resource is null or its id is UNSET_ID or its ResourceRole is null or its creation/modification
     *             user/date is null
     * @throws ResourcePersistenceException
     *             If there is a failure to persist the change or a Resource with the id is already in the persistence.
     */
    public void addResource(Resource resource) throws ResourcePersistenceException;

    /**
     * Deletes the given Resource (by id) in the persistence store. The Resource must already be present in the
     * persistence store, otherwise nothing is done.
     *
     * @param resource
     *            The resource to remove
     * @throws ResourcePersistenceException
     * @throws IllegalArgumentException
     *             If resource is null or its id is UNSET_ID or its ResourceRole is null
     * @throws ResourcePersistenceException
     *             If there is a failure to persist the change.
     */
    public void deleteResource(Resource resource) throws ResourcePersistenceException;

    /**
     * Updates the given Resource in the persistence store with its currently set information. The Resource must already
     * be present in the persistence store. The operator information should already have been put in the modification
     * date / modification user properties of the Resource.
     *
     * @param resource
     *            The resource to update
     * @throws ResourcePersistenceException
     * @throws IllegalArgumentException
     *             If resource is null or its id is UNSET_ID or its ResourceRole is null or its modification user/date
     *             is null
     * @throws ResourcePersistenceException
     *             If there is a failure to persist the Resource, or the Resource is not already in the persistence.
     */
    public void updateResource(Resource resource) throws ResourcePersistenceException;

    /**
     * Loads the resource from the persistence with the given id. Returns null if there is no resource for the given id.
     *
     * @return The loaded Resource
     * @param resourceId
     *            The id of the Resource to load
     * @throws ResourcePersistenceException
     * @throws IllegalArgumentException
     *             If resourceId is <= 0
     * @throws ResourcePersistenceException
     *             If there is an error loading the Resource
     */
    public Resource loadResource(long resourceId) throws ResourcePersistenceException;

    /**
     * Adds a notification to the persistence store. A notification type with the given ID must already exist in the
     * persistence store, as must a project.
     *
     * @param user
     *            The user id to add as a notification
     * @param project
     *            The project the notification is related to
     * @param notificationType
     *            The id of the notification type
     * @param operator
     *            The operator making the change
     * @throws ResourcePersistenceException
     * @throws IllegalArgumentException
     *             If user, project, or notificationType is <= 0, or operator is null
     * @throws ResourcePersistenceException
     *             If there is an error adding the change to the persistence store.
     */
    public void addNotification(long user, long project, long notificationType, String operator)
            throws ResourcePersistenceException;

    /**
     * Removes a notification from the persistence store. The given notification tuple identifier (user, project, and
     * notificationType) should already exists in the persistence store, otherwise nothing will be done.
     *
     * @param user
     *            The user id of the notification to remove
     * @param project
     *            The project id of the notification to remove
     * @param notificationType
     *            The notification type id of the notification to remove
     * @param operator
     *            The operator making the change
     * @throws ResourcePersistenceException
     * @throws IllegalArgumentException
     *             If user, project, or notificationType is <= 0
     * @throws IllegalArgumentException
     *             If operator is null
     * @throws ResourcePersistenceException
     *             If there is an error updating the persistence store.
     */
    public void removeNotification(long user, long project, long notificationType, String operator)
            throws ResourcePersistenceException;

    /**
     * Load the Notification for the given "id" triple from the persistence store. Returns null if no entry in the
     * persistence has the given user, project, and notificationType.
     *
     * @return The loaded notification
     * @param user
     *            The id of the user
     * @param project
     *            The id of the project
     * @param notificationType
     *            The id of the notificationType
     * @throws ResourcePersistenceException
     * @throws IllegalArgumentException
     *             If user, project, or notificationType is <= 0
     * @throws ResourcePersistenceException
     *             If there is an error loading from the persistence
     */
    public Notification loadNotification(long user, long project, long notificationType)
            throws ResourcePersistenceException;

    /**
     * Adds a notification type to the persistence store. The id of the notification type must already be assigned to
     * the NotificationType object passed to this method, and not already exist in the persistence source.
     *
     * @param notificationType
     *            The notification type to add.
     * @throws ResourcePersistenceException
     * @throws IllegalArgumentException
     *             If notificationType is null or its id is NotificationType.UNSET_ID or its name/description is null or
     *             its creation/modification user/date are null
     * @throws ResourcePersistenceException
     *             If there is an error updating the persistence
     */
    public void addNotificationType(NotificationType notificationType) throws ResourcePersistenceException;

    /**
     * Removes a notification type from the persistence (by id) store. If no notification type exists with the id of the
     * notification type, nothing is done.
     *
     * @param notificationType
     *            The notification type to delete.
     * @throws ResourcePersistenceException
     * @throws IllegalArgumentException
     *             If notificationType is null or its id is UNSET_ID.
     * @throws ResourcePersistenceException
     *             If there is an error updating the persistence
     */
    public void deleteNotificationType(NotificationType notificationType) throws ResourcePersistenceException;

    /**
     * Updates the notification type in the persistence store. The notification type (by id) must exist in the
     * persistence store.
     *
     * @param notificationType
     *            The notification type to update
     * @throws ResourcePersistenceException
     * @throws IllegalArgumentException
     *             If notificationType is null or its id is UNSET_ID or its name/description is null or its modification
     *             user/date is null
     * @throws ResourcePersistenceException
     *             if there is an error updating the persistence
     */
    public void updateNotificationType(NotificationType notificationType) throws ResourcePersistenceException;

    /**
     * Loads the notification type from the persistence with the given id. Returns null if there is no notification type
     * with the given id.
     *
     * @return The loaded notification type
     * @param notificationTypeId
     *            The id of the notification type to load
     * @throws ResourcePersistenceException
     * @throws IllegalArgumentException
     *             If notificationTypeId is <= 0
     * @throws ResourcePersistenceException
     *             If there is an error loading the notification type
     */
    public NotificationType loadNotificationType(long notificationTypeId) throws ResourcePersistenceException;

    /**
     * Adds a resource role to the persistence store. The id of the resource role must already be assigned to the
     * notificationType object passed to this method, and not already exist in the persistence source.
     *
     * @param resourceRole
     *            The resource role to add.
     * @throws ResourcePersistenceException
     * @throws IllegalArgumentException
     *             If resourceRole is null or its id is UNSET_ID or its name/description is null or its
     *             creation/modification date/user is null
     * @throws ResourcePersistenceException
     *             If there is an error updating the persistence
     */
    public void addResourceRole(ResourceRole resourceRole) throws ResourcePersistenceException;

    /**
     * Removes a resource role from the persistence store. If no resource role exists with the given id, nothing is
     * done.
     *
     * @param resourceRole
     *            The notification type to delete.
     * @throws ResourcePersistenceException
     * @throws IllegalArgumentException
     *             If notificationType is null or its id is UNSET_ID.
     * @throws ResourcePersistenceException
     *             If there is an error updating the persistence
     */
    public void deleteResourceRole(ResourceRole resourceRole) throws ResourcePersistenceException;

    /**
     * Updates the resource role in the persistence store. The resource role (by id) must exist in the persistence
     * store.
     *
     * @param resourceRole
     *            The resource role to update
     * @throws ResourcePersistenceException
     * @throws IllegalArgumentException
     *             If resourceRole is null or its id is UNSET_ID or its name/description is null or its modification
     *             user/date is null
     * @throws ResourcePersistenceException
     *             if there is an error updating the persistence
     */
    public void updateResourceRole(ResourceRole resourceRole) throws ResourcePersistenceException;

    /**
     * Loads the resource role from the persistence with the given id. Returns null if there is no resource role with
     * the given id.
     *
     * @return The loaded resource role
     * @param resourceId
     *            The id of the resource role to load
     * @throws ResourcePersistenceException
     * @throws IllegalArgumentException
     *             If resourceRoleId is <= 0
     * @throws ResourcePersistenceException
     *             If there is an error loading the resource role
     */
    public ResourceRole loadResourceRole(long resourceId) throws ResourcePersistenceException;

    /**
     * <p>
     * Loads the resources from the persistence with the given ids. May return a zero-length array. This method is
     * designed to keep the amount of SQL queries to a minimum when searching resources.
     * </p>
     *
     * @param resourceIds
     *            The ids of resources to load
     * @return Theloaded resources
     * @throws ResourcePersistenceException
     * @throws IllegalArgumentException
     *             If any id is <= 0 or the array is null
     * @throws ResourcePersistenceException
     *             If there is an error loading the Resources
     */
    public Resource[] loadResources(long[] resourceIds) throws ResourcePersistenceException;

    /**
     * Loads the notification types from the persistence with the given ids. May return a 0-length array.
     *
     * @param notificationTypeIds
     *            The ids of notification types to load
     * @return The loaded notification types
     * @throws ResourcePersistenceException
     * @throws IllegalArgumentException
     *             If any id is <= 0 or the array is null
     * @throws ResourcePersistenceException
     *             If there is an error loading from persistence
     */
    public NotificationType[] loadNotificationTypes(long[] notificationTypeIds) throws ResourcePersistenceException;

    /**
     * <p>
     * Loads the resource roles from the persistence with the given ids. May return a 0-length array.
     * </p>
     *
     * @param resourceRoleIds
     *            The ids of resource roles to load
     * @return The loaded resource roles
     * @throws ResourcePersistenceException
     * @throws IllegalArgumentException
     *             If any id is <= 0 or the array is null
     * @throws ResourcePersistenceException
     *             If there is an error loading from persistence
     */
    public ResourceRole[] loadResourceRoles(long[] resourceRoleIds) throws ResourcePersistenceException;

    /**
     * Load the Notifications for the given "id" triples from the persistence store. May return a 0-length array
     *
     * @param userIds
     *            The ids of the users
     * @param projectId
     *            The ids of the projects
     * @param notificationTypes
     *            The ids of the notification types
     * @return The loaded notifications
     * @throws ResourcePersistenceException
     * @throws IllegalArgumentException
     *             Any array is null, all three arrays do not have the same length, any id is <= 0
     * @throws ResourcePersistenceException
     *             If there is an error loading from the persistence
     */
    public Notification[] loadNotifications(long[] userIds, long[] projectId, long[] notificationTypes)
            throws ResourcePersistenceException;
}

