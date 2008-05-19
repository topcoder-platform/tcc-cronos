/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.persistence.ejbservice;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.jws.WebService;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.persistence.ConfigurationFileManager;
import com.topcoder.management.resource.NotificationType;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.resource.ResourceManager;
import com.topcoder.management.resource.ResourceRole;
import com.topcoder.management.resource.persistence.ResourcePersistenceException;
import com.topcoder.registration.persistence.CopiedResource;
import com.topcoder.registration.persistence.ArgumentChecker;
import com.topcoder.registration.persistence.ResourceManagementException;
import com.topcoder.registration.persistence.ResourceManagerService;
import com.topcoder.util.errorhandling.BaseException;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.impl.ConfigurationObjectSpecificationFactory;


/**
 * <p>
 * This class is an EJB3 Stateless Session Bean web service end point implementation of the
 * <code>ResourceManagerService</code> interface.
 * </p>
 *
 * <p>
 * It has a field of <code>ResourceManager</code> type to which it will delegate in each method.
 * </p>
 *
 * <p>
 * It is annotated with:
 * <pre>@WebService(targetNamespace = "http://www.topcoder.com/ResourceService",
 * serviceName = "ResourceService",
 * endpointInterface="com.topcoder.registration.persistence.ResourceManagerService")</pre>
 * <pre>@Stateless</pre>
 * <pre>@TransactionManagement(TransactionManagementType.CONTAINER)</pre>
 * <pre>@TransactionAttribute(TransactionAttributeType.REQUIRED)</pre>
 * </p>
 *
 * <p>
 *     <strong>Sample configuration:</strong>
 *     <pre>
 *        &lt;session&gt;
 *            &lt;ejb-name&gt;ResourceManagerServiceBean&lt;/ejb-name&gt;
 *            &lt;ejb-class&gt;
 *            com.topcoder.registration.persistence.ejbservice.ResourceManagerServiceBean
 *            &lt;/ejb-class&gt;
 *            &lt;env-entry&gt;
 *                &lt;env-entry-name&gt;file&lt;/env-entry-name&gt;
 *                &lt;env-entry-type&gt;java.lang.String&lt;/env-entry-type&gt;
 *                &lt;env-entry-value&gt;config.properties&lt;/env-entry-value&gt;
 *            &lt;/env-entry&gt;
 *            &lt;env-entry&gt;
 *                &lt;env-entry-name&gt;namespace&lt;/env-entry-name&gt;
 *                &lt;env-entry-type&gt;java.lang.String&lt;/env-entry-type&gt;
 *                &lt;env-entry-value&gt;ResourceManager&lt;/env-entry-value&gt;
 *            &lt;/env-entry&gt;
 *            &lt;env-entry&gt;
 *                &lt;env-entry-name&gt;resourceManagerKey&lt;/env-entry-name&gt;
 *                &lt;env-entry-type&gt;java.lang.String&lt;/env-entry-type&gt;
 *                &lt;env-entry-value&gt;ResourceManagerKey&lt;/env-entry-value&gt;
 *            &lt;/env-entry&gt;
 *            &lt;env-entry&gt;
 *                &lt;env-entry-name&gt;cacheResourceRoles&lt;/env-entry-name&gt;
 *                &lt;env-entry-type&gt;java.lang.Boolean&lt;/env-entry-type&gt;
 *                &lt;env-entry-value&gt;true&lt;/env-entry-value&gt;
 *            &lt;/env-entry&gt;
 *            &lt;env-entry&gt;
 *                &lt;env-entry-name&gt;cacheNotificationTypes&lt;/env-entry-name&gt;
 *                &lt;env-entry-type&gt;java.lang.Boolean&lt;/env-entry-type&gt;
 *                &lt;env-entry-value&gt;true&lt;/env-entry-value&gt;
 *            &lt;/env-entry&gt;
 *        &lt;/session&gt;
 *     </pre>
 * </p>
 *
 * <p>
 *     <strong>Thread Safety:</strong>
 *     This class is thread safe because it will be deployed in an ejb container which will ensure thread safety.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 */
@WebService(targetNamespace = "http://www.topcoder.com/ResourceService",
        serviceName = "ResourceService",
        endpointInterface = "com.topcoder.registration.persistence.ResourceManagerService")
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ResourceManagerServiceBean implements ResourceManagerService {

    /**
     * <p>
     * The wrapped <code>ResourceManager</code> object.
     * </p>
     *
     * <p>
     * It will be initialized in the post-construct <code>initialize()</code> method.
     * </p>
     *
     * <p>
     * It cannot be null after initialization.
     * </p>
     *
     * <p>
     * It will be used in all methods.
     * </p>
     */
    private ResourceManager resourceManager;

    /**
     * <p>
     * The configuration file used to initialize the <code>resourceManager</code> field.
     * </p>
     *
     * <p>
     * It will be injected by the container(if present in deployment descriptor) and never changed.
     * </p>
     *
     * <p>
     * It can be null if not present in the deployment descriptor but it cannot be empty string.
     * </p>
     *
     * <p>
     * It will be used in <code>initialize()</code> method to initialize the <code>resourceManager</code> field.
     * </p>
     */
    @javax.annotation.Resource(name = "file")
    private String file = null;

    /**
     * <p>
     * The configuration namespace used to initialize the <code>resourceManager</code> field.
     * </p>
     *
     * <p>
     * It will be injected by the container(if present in deployment descriptor) and never changed.
     * </p>
     *
     * <p>
     * It can not be null or empty string.
     * </p>
     *
     * <p>
     * It will be used in <code>initialize()</code> method to initialize the <code>resourceManager</code> field.
     * </p>
     */
    @javax.annotation.Resource(name = "namespace")
    private String namespace = null;

    /**
     * <p>
     * The resource manager key. It will be used with Object Factory component to create a <code>ResourceManager</code>
     * implementation instance.
     * </p>
     *
     * <p>
     * It will be injected by the container and never changed.
     * </p>
     *
     * <p>
     * It can not be null or empty string.
     * </p>
     *
     * <p>
     * It will be used in <code>initialize()</code> method to initialize the <code>resourceManager</code> field.
     * </p>
     */
    @javax.annotation.Resource(name = "resourceManagerKey")
    private String resourceManagerKey = null;

    /**
     * <p>
     * Indicates whether the resource roles should be cached or not.
     * </p>
     *
     * <p>
     * Initially set to false.
     * </p>
     *
     * <p>
     * It will be injected by the container and never changed.
     * </p>
     *
     * <p>
     * It will be used in <code>getAllResourceRoles()</code> method.
     * </p>
     */
    @javax.annotation.Resource(name = "cacheResourceRoles")
    private boolean cacheResourceRoles = false;

    /**
     * <p>
     * Indicates whether the notification types should be cached or not.
     * </p>
     *
     * <p>
     * Initially set to false.
     * </p>
     *
     * <p>
     * It will be injected by the container and never changed.
     * </p>
     *
     * <p>
     * It will be used in <code>getAllNotificationTypes()</code> method.
     * </p>
     */
    @javax.annotation.Resource(name = "cacheNotificationTypes")
    private boolean cacheNotificationTypes = false;

    /**
     * <p>
     * The resource roles cache. Initially set to an empty list.
     * </p>
     *
     * <p>
     * The reference is final but the contents can change.
     * It cannot be null or contain null elements but it can be empty.
     * </p>
     *
     * <p>
     * If <code>cacheResourceRoles</code> is true, it will be populated in
     * <code>getAllResourceRoles()</code> method, be cleared in <code>
     * updateResourceRole()</code> and <code>removeResourceRole()</code> methods.
     * </p>
     *
     * <p>
     * After it is populated (not empty) the <code>getAllResourceRoles()</code> method
     * will return its contents in the future calls.
     * <p>
     */
    @SuppressWarnings("unchecked")
    private final List < ResourceRole > resourceRolesCache = new ArrayList();

    /**
     * <p>
     * The notification types cache. Initially set to an empty list.
     * </p>
     *
     * <p>
     * The reference is final but the contents can change.
     * It cannot be null or contain null elements but it can be empty.
     * </p>
     *
     * <p>
     * If <code>cacheNotificationTypes</code> is true, it will be populated once in
     * <code>getAllNotificationTypes()</code> method, be cleared in <code>
     * updateNotificationType()</code> and <code>removeNotificationType()</code> methods.
     * </p>
     *
     * <p>
     * After it is populated (not empty) the <code>getAllNotificationTypes()</code> method
     * will return its contents in the future calls.
     * <p>
     */
    @SuppressWarnings("unchecked")
    private final List < NotificationType > notificationTypesCache = new ArrayList();

    /**
     * <p>
     * Default empty constructor. Does nothing.
     * </p>
     */
    public ResourceManagerServiceBean() {
        //empty
    }

    /**
     * <p>
     * Called by all public methods to check the state of the EJBean.
     * </p>
     *
     * @throws IllegalStateException If the <code>resourceManager</code> field has not been instantiated.
     */
    private void checkEJBState() {
        if (this.resourceManager == null) {
            throw new IllegalStateException("ResourceManager has not been instantiated.");
        }
    }

    /**
     * <p>
     * This method will be called by the ejb container after the bean has been constructed.
     * </p>
     *
     * <p>
     * It will create a <code>ResourceManager</code> instance and assign to the <code>resourceManager</code> field.
     * </p>
     *
     * @throws ResourceManagerBeanInitializationException If errors occur while creating the
     *         <code>ResourceManager</code> instance. Or if <code>file</code> or <code>namespace</code> or
     *         <code>resourceManagerKey</code> are empty strings. Or if <code>namespace</code> or
     *         <code>resourceManagerKey</code> are null.
     */
    @PostConstruct
    protected void initialize() {
        try {
            //create a ConfigurationFileManager instance
            ConfigurationFileManager cfm = file == null ? new ConfigurationFileManager()
                : new ConfigurationFileManager(file);

            //get the ConfigurationObject to pass to Object Factory component
            ConfigurationObject config = cfm.getConfiguration(namespace);

            //create a ConfigurationObjectSpecificationFactory instance using the config
            ConfigurationObjectSpecificationFactory specification = new ConfigurationObjectSpecificationFactory(config);

            //create a ObjectFactory instance using the specification
            ObjectFactory of = new ObjectFactory(specification);

            //create a ResourceManager instance and initialize the field
            resourceManager = (ResourceManager) of.createObject(resourceManagerKey);

        } catch (IllegalArgumentException e) {
            throw new ResourceManagerBeanInitializationException(
                    "IllegalArgumentException while instantiating ResourceManagerServiceBean.", e);
        } catch (BaseException e) {
            throw new ResourceManagerBeanInitializationException(
                    "BaseException while instantiating ResourceManagerServiceBean.", e);
        } catch (IOException e) {
            throw new ResourceManagerBeanInitializationException(
                    "IOException while instantiating ResourceManagerServiceBean.", e);
        } catch (ClassCastException e) {
            throw new ResourceManagerBeanInitializationException(
                    "ClassCastException while instantiating ResourceManagerServiceBean.", e);
        }
    }

    /**
     * <p>
     * This EJB method will update the given resource.
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
     * <p>
     * This EJB method is annotated with <pre>@RolesAllowed({ "User" })</pre>.
     * </p>
     *
     * @param resource The resource to be updated.
     * @param operator The operator that performs the updating.
     *
     * @throws IllegalStateException If the wrapped <code>ResourceManager</code> has not been initialized.
     * @throws IllegalArgumentException If any parameter is null. Or if operator is empty string.
     *         Or if a required field of the given resource is not set(i.e. the resource's role is
     *         null, or the resource role has a phase type but the resource does not has a phase set).
     *         Or if the resource id is -1(which means not set).
     * @throws ResourceManagementException If any errors occur while updating.
     *
     * @see com.topcoder.management.resource.ResourceManager#updateResource(Resource, String)
     */
    @RolesAllowed({ "User" })
    public void updateResource(Resource resource, String operator) throws ResourceManagementException {
        this.checkEJBState();
        ArgumentChecker.checkResourceToBeUpdated(resource, operator);
        //Each time when EJB receives a Resource from client, synchronize the properties map
        CopiedResource.syncProperties(resource);
        try {
            this.resourceManager.updateResource(resource, operator);
        } catch (ResourcePersistenceException e) {
            throw new ResourceManagementException("Error while updating resource.", e);
        }
    }

    /**
     * <p>
     * This EJB method will update the given resources that belong to the given project.
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
     * <p>
     * This EJB method is annotated with <pre>@RolesAllowed({ "User" })</pre>.
     * </p>
     *
     * @param resources The resources to be updated and associated with given <em>project</em>.
     * @param project The project to which the resources belong. Must be positive(&gt; 0).
     * @param operator The operator that performs the updating.
     *
     * @throws IllegalStateException If the wrapped <code>ResourceManager</code> has not been initialized.
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
    @RolesAllowed({ "User" })
    public void updateResources(Resource[] resources, long project, String operator)
        throws ResourceManagementException {
        this.checkEJBState();

        ArgumentChecker.checkResourcesToBeUpdated(resources, project, operator);

        //Each time when EJB receives a Resource from client, synchronize the properties map
        for (Resource resource : resources) {
            CopiedResource.syncProperties(resource);
        }

        try {
            this.resourceManager.updateResources(resources, project, operator);
        } catch (ResourcePersistenceException e) {
            throw new ResourceManagementException("Error while updating resources.", e);
        }
    }

    /**
     * <p>
     * This EJB method will remove the given resource.
     * </p>
     *
     * <p>
     *     <strong>About the {@link Resource#getId()}:</strong>
     *     The given <code>Resource</code> should have id already been set, which means the
     *     {@link Resource#getId()} should be positive(&gt; 0).
     * </p>
     *
     * <p>
     * This EJB method is annotated with <pre>@RolesAllowed({ "User" })</pre>.
     * </p>
     *
     * @param resource The resource to be removed. Must have a positive(&gt; 0) id.
     * @param operator The operator that performs the removing.
     *
     * @throws IllegalStateException If the wrapped <code>ResourceManager</code> has not been initialized.
     * @throws IllegalArgumentException If any parameter is null. Or if operator is empty string.
     *         Or if resource id is -1(which means not set).
     * @throws ResourceManagementException If any errors occur while removing the resource.
     *
     * @see com.topcoder.management.resource.ResourceManager#removeResource(Resource, String)
     */
    @RolesAllowed({ "User" })
    public void removeResource(Resource resource, String operator) throws ResourceManagementException {
        this.checkEJBState();
        ArgumentChecker.checkResourceToBeRemoved(resource, operator);
        //Each time when EJB receives a Resource from client, synchronize the properties map
        CopiedResource.syncProperties(resource);
        try {
            this.resourceManager.removeResource(resource, operator);
        } catch (ResourcePersistenceException e) {
            throw new ResourceManagementException("Error while removing resource.", e);
        }
    }

    /**
     * <p>
     * This EJB method will get the resource identified by the given id.
     * </p>
     *
     * <p>
     *     <strong>About the returned value:</strong>
     *     <code>Null</code> is returned if the resource is not found.
     * </p>
     *
     * <p>
     * This EJB method is annotated with <pre>@RolesAllowed({ "User" })</pre>.
     * </p>
     *
     * @param id The id of the resource to retrieve. Must be positive(&gt; 0).
     *
     * @throws IllegalStateException If the wrapped <code>ResourceManager</code> has not been initialized.
     * @throws IllegalArgumentException If given id &lt;= 0.
     * @throws ResourceManagementException If any errors occur while retrieving the resource.
     *
     * @return The <code>Resource</code> identified by the given id or null if resource was not found.
     *
     * @see com.topcoder.management.resource.ResourceManager#getResource(long)
     */
    @RolesAllowed({ "User" })
    public Resource getResource(long id) throws ResourceManagementException {
        this.checkEJBState();
        ArgumentChecker.checkLongPositive(id, "The id of resource to be retrieved");
        try {
            Resource resource = this.resourceManager.getResource(id);
            //Each time when EJB sends a Resource to client, convert it to CopiedResource at first
            return CopiedResource.copyResource(resource);
        } catch (ResourcePersistenceException e) {
            throw new ResourceManagementException("Error while retrieving resource.", e);
        }
    }

    /**
     * <p>
     * This EJB method will update the given resource role.
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
     * <p>
     * This EJB method is annotated with <pre>@RolesAllowed({ "User" })</pre>.
     * </p>
     *
     * @param resourceRole The resource role to be updated.
     * @param operator The operator who performs the updating.
     *
     * @throws IllegalStateException If the wrapped <code>ResourceManager</code> has not been initialized.
     * @throws IllegalArgumentException If any parameter is null. Or if operator is empty string.
     *         Or if a required field of the resource role is missing (i.e. name or description of
     *         the resource role is null).
     *         Or if the resource role id is -1(which means not set).
     * @throws ResourceManagementException If any errors occur while updating.
     *
     * @see com.topcoder.management.resource.ResourceManager#updateResourceRole(ResourceRole, String)
     */
    @RolesAllowed({ "User" })
    public void updateResourceRole(ResourceRole resourceRole, String operator) throws ResourceManagementException {
        this.checkEJBState();
        ArgumentChecker.checkResourceRoleToBeUpdated(resourceRole, operator);
        try {
            this.resourceManager.updateResourceRole(resourceRole, operator);

            //Since the resource role is updated, the cache is stale, need clear it
            //See http://forums.topcoder.com/?module=Thread&threadID=613007&start=0
            if (this.cacheResourceRoles) {
                this.resourceRolesCache.clear();
            }
        } catch (ResourcePersistenceException e) {
            throw new ResourceManagementException("Error while updating resource role.", e);
        }
    }

    /**
     * <p>
     * This EJB method will remove the given resource role.
     * </p>
     *
     * <p>
     *     <strong>About the {@link ResourceRole#getId()}:</strong>
     *     The given <code>ResourceRole</code> should have id already been set, which means the
     *     {@link ResourceRole#getId()} should be positive(&gt; 0).
     * </p>
     *
     * <p>
     * This EJB method is annotated with <pre>@RolesAllowed({ "User" })</pre>.
     * </p>
     *
     * @param resourceRole The resource role to be removed.
     * @param operator The operator who performs the removing.
     *
     * @throws IllegalStateException If the wrapped <code>ResourceManager</code> has not been initialized.
     * @throws IllegalArgumentException If any parameter is null. Or if operator is empty string.
     *         Or if resource role id is -1(which means not set).
     * @throws ResourceManagementException If any errors occur while removing the resource role.
     *
     * @see com.topcoder.management.resource.ResourceManager#removeResourceRole(ResourceRole, String)
     */
    @RolesAllowed({ "User" })
    public void removeResourceRole(ResourceRole resourceRole, String operator) throws ResourceManagementException {
        this.checkEJBState();
        ArgumentChecker.checkResourceRoleToBeRemoved(resourceRole, operator);
        try {
            this.resourceManager.removeResourceRole(resourceRole, operator);

            //Since the resource role is removed, the cache is stale, need clear it
            //See http://forums.topcoder.com/?module=Thread&threadID=613007&start=0
            if (this.cacheResourceRoles) {
                this.resourceRolesCache.clear();
            }
        } catch (ResourcePersistenceException e) {
            throw new ResourceManagementException("Error while removing resource role.", e);
        }
    }


    /**
     * <p>
     * This EJB method will get all resource roles.
     * </p>
     *
     * <p>
     *     <strong>About the returned value:</strong>
     *     Cannot return null but if no resource role is found. Instead an empty array should be returned.
     * </p>
     *
     * <p>
     * This EJB method is annotated with <pre>@RolesAllowed({ "User" })</pre>.
     * </p>
     *
     * @return The resource roles. An empty array is returned if none was found.
     *
     * @throws IllegalStateException If the wrapped <code>ResourceManager</code> has not been initialized.
     * @throws ResourceManagementException If any errors occur while retrieving the resource roles.
     *
     * @see com.topcoder.management.resource.ResourceManager#getAllResourceRoles()
     */
    @RolesAllowed({ "User" })
    public ResourceRole[] getAllResourceRoles() throws ResourceManagementException {
        this.checkEJBState();
        try {
            if (this.cacheResourceRoles && !this.resourceRolesCache.isEmpty()) {
                //Cache is enabled AND not empty, return the cached values
                return this.resourceRolesCache.toArray(new ResourceRole[this.resourceRolesCache.size()]);
            }

            ResourceRole[] roles = this.resourceManager.getAllResourceRoles();

            if (this.cacheResourceRoles) {
                //If cache is enabled, full fill the cache.
                for (ResourceRole role : roles) {
                    this.resourceRolesCache.add(role);
                }
            }

            return roles;

        } catch (ResourcePersistenceException e) {
            throw new ResourceManagementException("Error while retrieving all resource roles.", e);
        }
    }

    /**
     * <p>
     * This EJB method will update the given notification type.
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
     * <p>
     * This EJB method is annotated with <pre>@RolesAllowed({ "User" })</pre>.
     * </p>
     *
     * @param notificationType The notification type to be updated.
     * @param operator The operator who performs the updating.
     *
     * @throws IllegalStateException If the wrapped <code>ResourceManager</code> has not been initialized.
     * @throws IllegalArgumentException If any parameter is null or if operator is empty string.
     *         Or if a required field of the notification type is missing (i.e. name or description of
     *         the notification type is null).
     *         Or if the notification type id is -1(which means not set).
     * @throws ResourceManagementException If any errors occur while updating.
     *
     * @see com.topcoder.management.resource.ResourceManager#updateNotificationType(NotificationType, String)
     */
    @RolesAllowed({ "User" })
    public void updateNotificationType(NotificationType notificationType, String operator)
        throws ResourceManagementException {
        this.checkEJBState();
        ArgumentChecker.checkNotificationTypeToBeUpdated(notificationType, operator);
        try {
            this.resourceManager.updateNotificationType(notificationType, operator);

            //Since the notification type is updated, the cache is stale, need clear it
            //See http://forums.topcoder.com/?module=Thread&threadID=613007&start=0
            if (this.cacheNotificationTypes) {
                this.notificationTypesCache.clear();
            }
        } catch (ResourcePersistenceException e) {
            throw new ResourceManagementException("Error while updating notification type.", e);
        }
    }

    /**
     * <p>
     * This EJB method will remove the given notification type.
     * </p>
     *
     * <p>
     *     <strong>About the {@link NotificationType#getId()}:</strong>
     *     The given <code>NotificationType</code> should have id already been set, which means the
     *     {@link NotificationType#getId()} should be positive(&gt; 0).
     * </p>
     *
     * <p>
     * This EJB method is annotated with <pre>@RolesAllowed({ "User" })</pre>.
     * </p>
     *
     * @param notificationType The notification type to remove.
     * @param operator The operator who performs the removing.
     *
     * @throws IllegalStateException If the wrapped <code>ResourceManager</code> has not been initialized.
     * @throws IllegalArgumentException If any parameter is null or if operator is empty string.
     *         Or if notification type id is -1(which means not set).
     * @throws ResourceManagementException If any errors occur while removing the notification type.
     *
     * @see com.topcoder.management.resource.ResourceManager#removeNotificationType(NotificationType, String)
     */
    @RolesAllowed({ "User" })
    public void removeNotificationType(NotificationType notificationType, String operator)
        throws ResourceManagementException {
        this.checkEJBState();
        ArgumentChecker.checkNotificationTypeToBeRemoved(notificationType, operator);
        try {
            this.resourceManager.removeNotificationType(notificationType, operator);

            //Since the notification type is removed, the cache is stale, need clear it
            //See http://forums.topcoder.com/?module=Thread&threadID=613007&start=0
            if (this.cacheNotificationTypes) {
                this.notificationTypesCache.clear();
            }
        } catch (ResourcePersistenceException e) {
            throw new ResourceManagementException("Error while removing notification type.", e);
        }
    }

    /**
     * <p>
     * This EJB method will get all the notification types.
     * </p>
     *
     * <p>
     *     <strong>About the returned value:</strong>
     *     Cannot return null but if no notification type is found. Instead an empty array should be returned.
     * </p>
     *
     * <p>
     * This EJB method is annotated with <pre>@RolesAllowed({ "User" })</pre>.
     * </p>
     *
     * @return The notification types. An empty array is returned if none was found.
     *
     * @throws IllegalStateException If the wrapped <code>ResourceManager</code> has not been initialized.
     * @throws ResourceManagementException If any errors occur while retrieving the notification types.
     *
     * @see com.topcoder.management.resource.ResourceManager#getAllNotificationTypes()
     */
    @RolesAllowed({ "User" })
    public NotificationType[] getAllNotificationTypes() throws ResourceManagementException {
        this.checkEJBState();
        try {
            if (this.cacheNotificationTypes && !this.notificationTypesCache.isEmpty()) {
                //Cache is enabled AND not empty, return the cached values
                return this.notificationTypesCache.toArray(new NotificationType[this.notificationTypesCache.size()]);
            }

            NotificationType[] types = this.resourceManager.getAllNotificationTypes();

            if (this.cacheNotificationTypes) {
                //If cache is enabled, full fill the cache.
                for (NotificationType type : types) {
                    this.notificationTypesCache.add(type);
                }
            }

            return types;

        } catch (ResourcePersistenceException e) {
            throw new ResourceManagementException("Error while retrieving notification types.", e);
        }
    }

    /**
     * <p>
     * This EJB method will add a list of notifications for the given user ids.
     * All of the notification are added are for the given project and type.
     * </p>
     *
     * <p>
     *     <strong>About the array of users:</strong>
     *     It does not make sense to pass in an empty array, so <code>IllegalArgumentException</code>
     *     will be thrown if given array is empty.
     * </p>
     *
     * <p>
     * This EJB method is annotated with <pre>@RolesAllowed({ "User" })</pre>.
     * </p>
     *
     * @param users The users to add notifications for.
     * @param project The project the notifications apply to.
     * @param notificationType The type of notifications to add.
     * @param operator The operation who performs the adding.
     *
     * @throws IllegalStateException If the wrapped <code>ResourceManager</code> has not been initialized.
     * @throws IllegalArgumentException If operator or users is null or if operator is empty string.
     *         Or if any item of users, or project, or notificationType is &lt;= 0.
     *         Or is the users array is empty.
     * @throws ResourceManagementException If any errors occur while adding the notifications.
     *
     * @see com.topcoder.management.resource.ResourceManager#addNotifications(long[], long, long, String)
     */
    @RolesAllowed({ "User" })
    public void addNotifications(long[] users, long project, long notificationType, String operator)
        throws ResourceManagementException {
        this.checkEJBState();
        ArgumentChecker.checkNotificationsToBeAdded(users, project, notificationType, operator);
        try {
            this.resourceManager.addNotifications(users, project, notificationType, operator);
        } catch (ResourcePersistenceException e) {
            throw new ResourceManagementException("Error while adding notifications.", e);
        }
    }

    /**
     * <p>
     * This EJB method will remove a list of notifications for the given user ids.
     * The notifications removed are for the given project and type.
     * </p>
     *
     * <p>
     *     <strong>About the array of users:</strong>
     *     It does not make sense to pass in an empty array, so <code>IllegalArgumentException</code>
     *     will be thrown if given array is empty.
     * </p>
     *
     * <p>
     * This EJB method is annotated with <pre>@RolesAllowed({ "User" })</pre>.
     * </p>
     *
     * @param users The users to remove notifications for.
     * @param project The project the notifications apply to.
     * @param notificationType The type of notifications to remove.
     * @param operator The operation who performs the removing.
     *
     * @throws IllegalStateException If the wrapped <code>ResourceManager</code> has not been initialized.
     * @throws IllegalArgumentException If operator or users is null or if operator is empty string.
     *         Or if any item of users, or project, or notificationType is &lt;= 0.
     *         Or is the users array is empty.
     * @throws ResourceManagementException If any errors occur while adding the notifications.
     *
     * @see com.topcoder.management.resource.ResourceManager#removeNotifications(long[], long, long, String)
     */
    @RolesAllowed({ "User" })
    public void removeNotifications(long[] users, long project, long notificationType, String operator)
        throws ResourceManagementException {
        this.checkEJBState();
        ArgumentChecker.checkNotificationsToBeRemoved(users, project, notificationType, operator);
        try {
            this.resourceManager.removeNotifications(users, project, notificationType, operator);
        } catch (ResourcePersistenceException e) {
            throw new ResourceManagementException("Error while removing notifications.", e);
        }
    }

    /**
     * <p>
     * This EJB method will get the users ids for all notifications for the given project and type.
     * </p>
     *
     * <p>
     *     <strong>About the returned value:</strong>
     *     Cannot return null but if no resource role is found. Instead an empty array should be returned.
     * </p>
     *
     * <p>
     * This EJB method is annotated with <pre>@RolesAllowed({ "User" })</pre>.
     * </p>
     *
     * @param project the project to get notifications for.
     * @param notificationType the type of notifications to retrieve.
     *
     * @throws IllegalStateException If the wrapped <code>ResourceManager</code> has not been initialized.
     * @throws IllegalArgumentException If project or notificationType is &lt;= 0.
     * @throws ResourceManagementException If any errors occur while getting the users ids.
     *
     * @return an array containing the users ids of the notifications for the project and type.
     *
     * @see com.topcoder.management.resource.ResourceManager#getNotifications(long, long)
     */
    @RolesAllowed({ "User" })
    public long[] getNotifications(long project, long notificationType) throws ResourceManagementException {
        this.checkEJBState();
        ArgumentChecker.checkNotificationsToBeRetrieved(project, notificationType);
        try {
            return this.resourceManager.getNotifications(project, notificationType);
        } catch (ResourcePersistenceException e) {
            throw new ResourceManagementException("Error while retrieving notifications.", e);
        }
    }
}
