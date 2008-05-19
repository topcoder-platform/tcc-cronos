/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.persistence;

import com.topcoder.management.resource.NotificationType;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.resource.ResourceRole;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * <p>
 * This is a web interface that defines the contract for implementations that will allow the user
 * to work with resources.
 * </p>
 *
 * <p>
 * The methods provided will allow the user to:
 * <ul>
 *     <li>update a resource, an array of resources, resource role, notification type;</li>
 *     <li>remove a resource, resource role, notifications, notification type;</li>
 *     <li>get a resource identified by its id;</li>
 *     <li>get all resource roles, notification types;</li>
 *     <li>add or get notifications.</li>
 * </ul>
 * </p>
 *
 * <p>
 * It will be annotated with "@WebService(name="ResourceService")".
 * </p>
 *
 * <p>
 *    <strong>Thread Safety:</strong>
 *    Implementations are required to be thread safe.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 */
@WebService(name = "ResourceService")
public interface ResourceManagerService {

    /**
     * <p>
     * This web service method will update the given resource.
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
     * @param resource The resource to be updated.
     * @param operator The operator that performs the updating.
     *
     * @throws IllegalArgumentException If any parameter is null. Or if operator is empty string.
     *         Or if a required field of the given resource is not set(i.e. the resource's role is
     *         null, or the resource role has a phase type but the resource does not has a phase set).
     *         Or if the resource id is -1(which means not set).
     * @throws ResourceManagementException If any errors occur while updating.
     *
     * @see com.topcoder.management.resource.ResourceManager#updateResource(Resource, String)
     */
    @RequestWrapper(
            localName =
                "updateResourceRequest",
            className =
                "com.topcoder.registration.persistence.UpdateResourceRequest")
    void updateResource(
            @WebParam(name = "resource")
            Resource resource,
            @WebParam(name = "operator")
            String operator) throws ResourceManagementException;

    /**
     * <p>
     * This web service method will update the given resources that belong to the given project.
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
     * @throws ResourceManagementException If any errors occur while updating.
     *
     * @see com.topcoder.management.resource.ResourceManager#updateResources(Resource[], long, String)
     */
    @RequestWrapper(
            localName =
                "updateResourcesRequest",
            className =
                "com.topcoder.registration.persistence.UpdateResourcesRequest")
    void updateResources(
            @WebParam(name = "resources")
            Resource[] resources,
            @WebParam(name = "project")
            long project,
            @WebParam(name = "operator")
            String operator) throws ResourceManagementException;

    /**
     * <p>
     * This web service method will remove the given resource.
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
     * @throws ResourceManagementException If any errors occur while removing the resource.
     *
     * @see com.topcoder.management.resource.ResourceManager#removeResource(Resource, String)
     */
    @RequestWrapper(
            localName =
                "removeResourceRequest",
            className =
                "com.topcoder.registration.persistence.RemoveResourceRequest")
    void removeResource(
            @WebParam(name = "resource")
            Resource resource,
            @WebParam(name = "operator")
            String operator) throws ResourceManagementException;

    /**
     * <p>
     * This web service method will get the resource identified by the given id.
     * </p>
     *
     * <p>
     *     <strong>About the returned value:</strong>
     *     <code>Null</code> is returned if the resource is not found.
     * </p>
     *
     * @param id The id of the resource to retrieve. Must be positive(&gt; 0).
     *
     * @throws IllegalArgumentException If given id &lt;= 0.
     * @throws ResourceManagementException If any errors occur while retrieving the resource.
     *
     * @return The <code>Resource</code> identified by the given id or null if resource was not found.
     *
     * @see com.topcoder.management.resource.ResourceManager#getResource(long)
     */
    @WebResult(name = "resource")
    @ResponseWrapper(localName = "getResourceResponse",
        className = "com.topcoder.registration.persistence.GetResourceResponse")
    Resource getResource(long id) throws ResourceManagementException;

    /**
     * <p>
     * This web service method will update the given resource role.
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
     * @throws ResourceManagementException If any errors occur while updating.
     *
     * @see com.topcoder.management.resource.ResourceManager#updateResourceRole(ResourceRole, String)
     */
    void updateResourceRole(ResourceRole resourceRole, String operator) throws ResourceManagementException;

    /**
     * <p>
     * This web service method will remove the given resource role.
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
     * @throws ResourceManagementException If any errors occur while removing the resource role.
     *
     * @see com.topcoder.management.resource.ResourceManager#removeResourceRole(ResourceRole, String)
     */
    void removeResourceRole(ResourceRole resourceRole, String operator) throws ResourceManagementException;

    /**
     * <p>
     * This web service method will get all resource roles.
     * </p>
     *
     * <p>
     *     <strong>About the returned value:</strong>
     *     Cannot return null but if no resource role is found. Instead an empty array should be returned.
     * </p>
     *
     * @return The resource roles. An empty array is returned if none was found.
     *
     * @throws ResourceManagementException If any errors occur while retrieving the resource roles.
     *
     * @see com.topcoder.management.resource.ResourceManager#getAllResourceRoles()
     */
    ResourceRole[] getAllResourceRoles() throws ResourceManagementException;

    /**
     * <p>
     * This web service method will update the given notification type.
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
     * @throws ResourceManagementException If any errors occur while updating.
     *
     * @see com.topcoder.management.resource.ResourceManager#updateNotificationType(NotificationType, String)
     */
    void updateNotificationType(NotificationType notificationType, String operator) throws ResourceManagementException;

    /**
     * <p>
     * This web service method will remove the given notification type.
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
     *         Or if notification type id is -1(which means not set).
     * @throws ResourceManagementException If any errors occur while removing the notification type.
     *
     * @see com.topcoder.management.resource.ResourceManager#removeNotificationType(NotificationType, String)
     */
    void removeNotificationType(NotificationType notificationType, String operator) throws ResourceManagementException;

    /**
     * <p>
     * This web service method will get all the notification types.
     * </p>
     *
     * <p>
     *     <strong>About the returned value:</strong>
     *     Cannot return null but if no notification type is found. Instead an empty array should be returned.
     * </p>
     *
     * @return The notification types. An empty array is returned if none was found.
     *
     * @throws ResourceManagementException If any errors occur while retrieving the notification types.
     *
     * @see com.topcoder.management.resource.ResourceManager#getAllNotificationTypes()
     */
    NotificationType[] getAllNotificationTypes() throws ResourceManagementException;

    /**
     * <p>
     * This web service method will add a list of notifications for the given user ids.
     * All of the notification are added are for the given project and type.
     * </p>
     *
     * <p>
     *     <strong>About the array of users:</strong>
     *     It does not make sense to pass in an empty array, so <code>IllegalArgumentException</code>
     *     will be thrown if given array is empty.
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
     * @throws ResourceManagementException If any errors occur while adding the notifications.
     *
     * @see com.topcoder.management.resource.ResourceManager#addNotifications(long[], long, long, String)
     */
    void addNotifications(long[] users, long project, long notificationType, String operator)
        throws ResourceManagementException;

    /**
     * <p>
     * This web service method will remove a list of notifications for the given user ids.
     * The notifications removed are for the given project and type.
     * </p>
     *
     * <p>
     *     <strong>About the array of users:</strong>
     *     It does not make sense to pass in an empty array, so <code>IllegalArgumentException</code>
     *     will be thrown if given array is empty.
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
     * @throws ResourceManagementException If any errors occur while adding the notifications.
     *
     * @see com.topcoder.management.resource.ResourceManager#removeNotifications(long[], long, long, String)
     */
    void removeNotifications(long[] users, long project, long notificationType, String operator)
        throws ResourceManagementException;

    /**
     * <p>
     * This web service method will get the users ids for all notifications for the given project and type.
     * </p>
     *
     * <p>
     *     <strong>About the returned value:</strong>
     *     Cannot return null but if no resource role is found. Instead an empty array should be returned.
     * </p>
     *
     * @param project the project to get notifications for.
     * @param notificationType the type of notifications to retrieve.
     *
     * @throws IllegalArgumentException If project or notificationType is &lt;= 0.
     * @throws ResourceManagementException If any errors occur while getting the users ids.
     *
     * @return an array containing the users ids of the notifications for the project and type.
     *
     * @see com.topcoder.management.resource.ResourceManager#getNotifications(long, long)
     */
    long[] getNotifications(long project, long notificationType) throws ResourceManagementException;
}
