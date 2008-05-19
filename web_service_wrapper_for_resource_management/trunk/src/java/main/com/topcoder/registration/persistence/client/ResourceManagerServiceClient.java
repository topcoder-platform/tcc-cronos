/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.persistence.client;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebServiceException;

import com.topcoder.management.resource.NotificationType;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.resource.ResourceRole;
import com.topcoder.registration.persistence.CopiedResource;
import com.topcoder.registration.persistence.ArgumentChecker;
import com.topcoder.registration.persistence.ResourceManagementException;
import com.topcoder.registration.persistence.ResourceManagerService;


/**
 * <p>
 * This class represents a client to a resource manager service. The proxy to the web service will be created using
 * a url that identifies the WSDL document location; this url is provided in the constructors of this class.
 * </p>
 *
 * <p>
 * The methods defined in this class exactly match the methods of the <code>ResourceManagerService</code> interface.
 * </p>
 *
 * <p>
 *     <strong>Sample Usage:</strong>
 *     <pre>
 *        String url =
 *        "http://127.0.0.1:8080/Web_Service_Wrapper_for_Resource_Management/ResourceManagerServiceBean?wsdl";
 *
 *        //create a client
 *        ResourceManagerServiceClient client = new ResourceManagerServiceClient(url);
 *        //or create a client with URL
 *        client = new ResourceManagerServiceClient(new URL(url));
 *        //or create a client with URL and QName
 *        client = new ResourceManagerServiceClient(new URL(url),
 *            new QName("http://www.topcoder.com/ResourceService", "ResourceService"));
 *
 *        Resource resource = this.createResource(1L);
 *
 *        //update a resource
 *        client.updateResource(resource, "John");
 *
 *        //remove a resource
 *        client.removeResource(resource, "John");
 *
 *        //update resources for a project
 *        client.updateResources(new Resource[] {resource }, 1, "John");
 *
 *        //get a resource by id
 *        resource = client.getResource(2);
 *
 *        ResourceRole resourceRole = this.createResourceRole();
 *        //update a resource role
 *        client.updateResourceRole(resourceRole, "John");
 *
 *        //remove a resource role
 *        client.removeResourceRole(resourceRole, "John");
 *
 *        //get all resource roles
 *        ResourceRole[] resourceRoles = client.getAllResourceRoles();
 *
 *        //add notifications, of a given type, for users for a project
 *        client.addNotifications(new long[] {1, 2 }, 2, 2, "John");
 *
 *        //remove notifications, of a given type, from users for a project
 *        client.removeNotifications(new long[] {1, 2 }, 2, 2, "John");
 *
 *        //get users ids for all notifications for the given project and type
 *        long[] users = client.getNotifications(2, 1);
 *        NotificationType notificationType = this.createNotificationType();
 *
 *        //update a notification type
 *        client.updateNotificationType(notificationType, "John");
 *
 *        //remove a notification type
 *        client.removeNotificationType(notificationType, "John");
 *
 *        //get all notification types
 *        NotificationType[] notificationTypes = client.getAllNotificationTypes();
 *     </pre>
 * </p>
 *
 * <p>
 *     <strong>Thread Safety:</strong>
 *     This class is thread safe since the implementations of <code>ResourceManagerService</code> web service
 *     interface are expected to be thread safe.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 */
public class ResourceManagerServiceClient extends Service {

    /**
     * <p>
     * This field represents the default service name. It is initialized with {@link QName} constructor specifying
     * the Namespace URI and local part:
     * <code>new QName("http://www.topcoder.com/ResourceService", "ResourceService")</code>.
     * Will not change after initialization. It cannot be <code>null</code>.
     * </p>
     */
    private static final QName DEFAULT_SERVICE_NAME =
        new QName("http://www.topcoder.com/ResourceService", "ResourceService");

    /**
     * <p>
     * The proxy(port) to the service. It will be initialized in the constructor and never changed.
     * It cannot be null after initialization. Used in all methods.
     * </p>
     */
    private final ResourceManagerService port;

    /**
     * <p>
     * Creates a new client with given WSDL URL.
     * </p>
     *
     * <p>
     * This constructor will use the default service name represented by:<br>
     * <code>new QName("http://www.topcoder.com/ResourceService", "ResourceService")</code>
     * </p>
     *
     * @param url The WSDL url.
     *
     * @throws IllegalArgumentException If url is null or empty.
     * @throws ResourceManagerServiceClientCreationException If error occurs when creating the WSDL
     *         service or creating the SEI proxy.
     */
    public ResourceManagerServiceClient(String url) throws ResourceManagerServiceClientCreationException {
        this(getURL(url));
    }

    /**
     * <p>
     * Creates a new client with given WSDL URL.
     * </p>
     *
     * <p>
     * This constructor will use the default service name represented by:<br>
     * <code>new QName("http://www.topcoder.com/ResourceService", "ResourceService")</code>
     * </p>
     *
     * @param url The WSDL url.
     *
     * @throws IllegalArgumentException If url is null.
     * @throws ResourceManagerServiceClientCreationException If error occurs when creating the WSDL
     *         service or creating the SEI proxy.
     */
    public ResourceManagerServiceClient(URL url) throws ResourceManagerServiceClientCreationException {
        this(url, DEFAULT_SERVICE_NAME);
    }

    /**
     * <p>
     * Creates a new client with given WSDL URL and service name.
     * </p>
     *
     * @param url The WSDL url.
     * @param serviceName The <code>QName</code> represents service name.
     *
     * @throws IllegalArgumentException If any argument is null.
     * @throws ResourceManagerServiceClientCreationException If error occurs when creating the WSDL
     *         service or creating the SEI proxy.
     */
    public ResourceManagerServiceClient(URL url, QName serviceName)
        throws ResourceManagerServiceClientCreationException {
        super(validateArguments(url, serviceName), serviceName);

        try {
            this.port = super.getPort(ResourceManagerService.class);
        } catch (WebServiceException e) {
            throw new ResourceManagerServiceClientCreationException(
                "Error while instantiating ResourceManagerServiceClient.", e);
        }
    }

    /**
     * <p>
     * Validate the given <code>URL</code> and <code>QName</code>.
     * </p>
     *
     * @param url The WSDL url.
     * @param serviceName The <code>QName</code> represents service name.
     *
     * @return The given url.
     *
     * @throws IllegalArgumentException If given url or service name is null.
     * @throws ResourceManagerServiceClientCreationException If given arguments do not represent a valid service.
     */
    private static final URL validateArguments(URL url, QName serviceName)
        throws ResourceManagerServiceClientCreationException {
        ArgumentChecker.checkNull(url, "WSDL url");
        ArgumentChecker.checkNull(serviceName, "Service QName");
        try {
            Service.create(url, serviceName);
        } catch (Exception e) {
            throw new ResourceManagerServiceClientCreationException(
                "Error while instantiating ResourceManagerServiceClient.", e);
        }
        return url;
    }

    /**
     * <p>
     * Get <code>URL</code> from given url string.
     * </p>
     *
     * @param url The WSDL url.
     *
     * @return <code>URL</code> from given url string.
     *
     * @throws IllegalArgumentException If given url string is null or empty.
     * @throws ResourceManagerServiceClientCreationException If error occurs when creating <code>URL</code>
     *         from given url string.
     */
    private static final URL getURL(String url) throws ResourceManagerServiceClientCreationException {
        ArgumentChecker.checkNullOrEmpty(url, "WSDL URL");

        try {
            return new URL(url);
        } catch (MalformedURLException e) {
            throw new ResourceManagerServiceClientCreationException("Error while creating URL.", e);
        }
    }

    /**
     * <p>
     * This client method will update the given resource.
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
     * @see ResourceManagerService#updateResource(Resource, String)
     */
    public void updateResource(Resource resource, String operator) throws ResourceManagementException {
        ArgumentChecker.checkResourceToBeUpdated(resource, operator);
        //Each time when client sends a Resource to server, convert it to CopiedResource at first
        this.port.updateResource(CopiedResource.copyResource(resource), operator);
    }

    /**
     * <p>
     * This client method will update the given resources that belong to the given project.
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
     * @see ResourceManagerService#updateResources(Resource[], long, String)
     */
    @SuppressWarnings("unchecked")
    public void updateResources(Resource[] resources, long project, String operator)
        throws ResourceManagementException {
        ArgumentChecker.checkResourcesToBeUpdated(resources, project, operator);
        //Each time when client sends a Resource to server, convert it to CopiedResource at first
        List < CopiedResource > copiedResources = new ArrayList();
        for (Resource resource : resources) {
            copiedResources.add(CopiedResource.copyResource(resource));
        }
        this.port.updateResources(copiedResources.toArray(new CopiedResource[resources.length]), project, operator);
    }

    /**
     * <p>
     * This client method will remove the given resource.
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
     * @see ResourceManagerService#removeResource(Resource, String)
     */
    public void removeResource(Resource resource, String operator) throws ResourceManagementException {
        ArgumentChecker.checkResourceToBeRemoved(resource, operator);
        //Each time when client sends a Resource to server, convert it to CopiedResource at first
        this.port.removeResource(CopiedResource.copyResource(resource), operator);
    }

    /**
     * <p>
     * This client method will get the resource identified by the given id.
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
     * @see ResourceManagerService#getResource(long)
     */
    public Resource getResource(long id) throws ResourceManagementException {
        ArgumentChecker.checkLongPositive(id, "The id of resource to be retrieved");
        Resource resource = this.port.getResource(id);
        //Each time when client receives a Resource from server, synchronize the properties map
        CopiedResource.syncProperties(resource);
        return resource;
    }

    /**
     * <p>
     * This client method will update the given resource role.
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
     * @see ResourceManagerService#updateResourceRole(ResourceRole, String)
     */
    public void updateResourceRole(ResourceRole resourceRole, String operator) throws ResourceManagementException {
        ArgumentChecker.checkResourceRoleToBeUpdated(resourceRole, operator);
        this.port.updateResourceRole(resourceRole, operator);
    }

    /**
     * <p>
     * This client method will remove the given resource role.
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
     * @see ResourceManagerService#removeResourceRole(ResourceRole, String)
     */
    public void removeResourceRole(ResourceRole resourceRole, String operator) throws ResourceManagementException {
        ArgumentChecker.checkResourceRoleToBeRemoved(resourceRole, operator);
        this.port.removeResourceRole(resourceRole, operator);
    }


    /**
     * <p>
     * This client method will get all resource roles.
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
     * @see ResourceManagerService#getAllResourceRoles()
     */
    public ResourceRole[] getAllResourceRoles() throws ResourceManagementException {
        return this.port.getAllResourceRoles();
    }

    /**
     * <p>
     * This client method will update the given notification type.
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
     * @see ResourceManagerService#updateNotificationType(NotificationType, String)
     */
    public void updateNotificationType(NotificationType notificationType, String operator)
        throws ResourceManagementException {
        ArgumentChecker.checkNotificationTypeToBeUpdated(notificationType, operator);
        this.port.updateNotificationType(notificationType, operator);
    }

    /**
     * <p>
     * This client method will remove the given notification type.
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
     * @see ResourceManagerService#removeNotificationType(NotificationType, String)
     */
    public void removeNotificationType(NotificationType notificationType, String operator)
        throws ResourceManagementException {
        ArgumentChecker.checkNotificationTypeToBeRemoved(notificationType, operator);
        this.port.removeNotificationType(notificationType, operator);
    }

    /**
     * <p>
     * This client method will get all the notification types.
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
     * @see ResourceManagerService#getAllNotificationTypes()
     */
    public NotificationType[] getAllNotificationTypes() throws ResourceManagementException {
        return this.port.getAllNotificationTypes();
    }

    /**
     * <p>
     * This client method will add a list of notifications for the given user ids.
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
     * @see ResourceManagerService#addNotifications(long[], long, long, String)
     */
    public void addNotifications(long[] users, long project, long notificationType, String operator)
        throws ResourceManagementException {
        ArgumentChecker.checkNotificationsToBeAdded(users, project, notificationType, operator);
        this.port.addNotifications(users, project, notificationType, operator);
    }

    /**
     * <p>
     * This client method will remove a list of notifications for the given user ids.
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
     * @see ResourceManagerService#removeNotifications(long[], long, long, String)
     */
    public void removeNotifications(long[] users, long project, long notificationType, String operator)
        throws ResourceManagementException {
        ArgumentChecker.checkNotificationsToBeRemoved(users, project, notificationType, operator);
        this.port.removeNotifications(users, project, notificationType, operator);
    }

    /**
     * <p>
     * This client method will get the users ids for all notifications for the given project and type.
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
     * @see ResourceManagerService#getNotifications(long, long)
     */
    public long[] getNotifications(long project, long notificationType) throws ResourceManagementException {
        ArgumentChecker.checkNotificationsToBeRetrieved(project, notificationType);
        return this.port.getNotifications(project, notificationType);
    }

    /**
     * <p>
     * Get the port for service end point.
     * </p>
     *
     * @return The port. Will never be null.
     */
    public ResourceManagerService getPort() {
        return this.port;
    }
}
