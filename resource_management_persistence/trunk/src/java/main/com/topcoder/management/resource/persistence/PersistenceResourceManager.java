/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource.persistence;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.topcoder.management.resource.AuditableResourceStructure;
import com.topcoder.management.resource.Helper;
import com.topcoder.management.resource.Notification;
import com.topcoder.management.resource.NotificationType;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.resource.ResourceManager;
import com.topcoder.management.resource.ResourceRole;
import com.topcoder.management.resource.search.NotificationFilterBuilder;
import com.topcoder.management.resource.search.NotificationTypeFilterBuilder;
import com.topcoder.management.resource.search.ResourceFilterBuilder;
import com.topcoder.management.resource.search.ResourceRoleFilterBuilder;

import com.topcoder.search.builder.SearchBuilderConfigurationException;
import com.topcoder.search.builder.SearchBuilderException;
import com.topcoder.search.builder.SearchBundle;
import com.topcoder.search.builder.SearchBundleManager;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.util.idgenerator.IDGenerationException;
import com.topcoder.util.idgenerator.IDGenerator;
import com.topcoder.util.idgenerator.IDGeneratorFactory;
import com.topcoder.util.sql.databaseabstraction.CustomResultSet;
import com.topcoder.util.sql.databaseabstraction.InvalidCursorStateException;

/**
 * <p>
 * The PersistenceResourceManager class implements the ResourceManager interface. It ties together a persistence
 * mechanism, several Search Builder searching instances (for searching for various types of data), and several id
 * generators (for generating ids for the various types). This class consists of a several methods styles. The first
 * method style just calls directly to a corresponding method of the persistence. The second method style first assigns
 * values to some data fields of the object before calling a persistence method. The third type of method uses a
 * SearchBundle to execute a search and then uses the persistence to load an object for each of the ids found from the
 * search.
 * </p>
 *
 * <p>
 * One point to note about all the update and remove methods in this class is that the modification/creation user/date
 * must be set on the various objects before they are passed to the persistence.
 * </p>
 *
 * <p>
 * This class is immutable and hence thread-safe.
 * </p>
 *
 * @author aubergineanode, TCSDEVELOPER
 * @version 1.0
 */
public class PersistenceResourceManager implements ResourceManager {
    /**
     * The name under which the resource search bundle should appear in the SearchBundleManager, if the
     * SearchBundleManager constructor is used. This field is public, static, and final.
     *
     */
    public static final String RESOURCE_SEARCH_BUNDLE_NAME = "Resource Search Bundle";

    /**
     * The name under which the resource role search bundle should appear in the SearchBundleManager, if the
     * SearchBundleManager constructor is used. This field is public, static, and final.
     *
     */
    public static final String RESOURCE_ROLE_SEARCH_BUNDLE_NAME = "Resource Role Search Bundle";

    /**
     * The name under which the notification search bundle should appear in the SearchBundleManager, if the
     * SearchBundleManager constructor is used. This field is public, static, and final.
     *
     */
    public static final String NOTIFICATION_SEARCH_BUNDLE_NAME = "Notification Search Bundle";

    /**
     * The name under which the notification type search bundle should appear in the SearchBundleManager, if the
     * SearchBundleManager constructor is used. This field is public, static, and final.
     *
     */
    public static final String NOTIFICATION_TYPE_SEARCH_BUNDLE_NAME = "Notification Type Search Bundle";

    /**
     * The name under which the id generator for resources should appear in the IDGeneratorFactory if the
     * IDGeneratorFactory constructor is used. This field is public, static, and final.
     *
     */
    public static final String RESOURCE_ID_GENERATOR_NAME = "Resource Id Generator";

    /**
     * The name under which the id generator for resource roles should appear in the IDGeneratorFactory if the
     * IDGeneratorFactory constructor is used. This field is public, static, and final.
     *
     */
    public static final String RESOURCE_ROLE_ID_GENERATOR_NAME = "Resource Role Id Generator";

    /**
     * The name under which the id generator for notification types should appear in the IDGeneratorFactory if the
     * IDGeneratorFactory constructor is used. This field is public, static, and final.
     *
     */
    public static final String NOTIFICATION_TYPE_ID_GENERATOR_NAME = "Notification Type Id Generator";

    /**
     * The persistence store for Resources, ResourceRoles, Notifications, and NotificationTypes.
     *
     * <P>
     * This field set in the constructor, is immutable, and can never be null.
     * </P>
     *
     * This field is used in almost all the methods of this class, as it is used either to make changes to the items in
     * the persistence, or to retrieve the items in the persistence once their ids have been found with the Search
     * Builder component.
     *
     */
    private final ResourcePersistence persistence;

    /**
     * The search bundle that is used when searching for resources. This field is set in the constructor, is immutable,
     * and can never be null. This field is used to search resources, in the searchResources method.
     *
     */
    private final SearchBundle resourceSearchBundle;

    /**
     * The search bundle that is used when searching for resource roles. This field is set in the constructor, is
     * immutable, and can never be null. This field is used to search resource roles, in the searchResourceRoles method.
     *
     */
    private final SearchBundle resourceRoleSearchBundle;

    /**
     * The search bundle that is used when searching for notifications. This field is set in the constructor, is
     * immutable, and can never be null. This field is used to search notifications, in the searchNotifications method.
     *
     */
    private final SearchBundle notificationSearchBundle;

    /**
     * The search bundle that is used when searching for notification types. This field is set in the constructor, is
     * immutable, and can never be null. This field is used to search notification types, in the searchNotificationTypes
     * method.
     *
     */
    private final SearchBundle notificationTypeSearchBundle;

    /**
     * The generator used to create ids for new Resources. This field is set in the constructor, is immutable, and can
     * never be null. This field is used when an id is needed for a new Resource, which occurs in the updateResource and
     * updateResources methods.
     *
     */
    private final IDGenerator resourceIdGenerator;

    /**
     * The generator used to create ids for new ResourceRoles. This field is set in the constructor, is immutable, and
     * can never be null. This field is used when an id is needed for a new ResourceRole, which occurs in the
     * updateResourceRole method.
     *
     */
    private final IDGenerator resourceRoleIdGenerator;

    /**
     * The generator used to create ids for new NotificationTypes. This field is set in the constructor, is immutable,
     * and can never be null. This field is used when an id is needed for a new NotificationType, which occurs in the
     * updateNotificationType method.
     *
     */
    private final IDGenerator notificationTypeIdGenerator;


    /**
     * Creates a new PersistenceResourceManager, initializing all fields to the given values.
     *
     * @param persistence
     *            The persistence for Resources and related objects
     * @param resourceSearchBundle
     *            The search bundle for searching resources
     * @param resourceRoleSearchBundle
     *            The search bundle for searching resource roles
     * @param notificationSearchBundle
     *            The search bundle for searching notifications
     * @param notificationTypeSearchBundle
     *            The search bundle for searching notification types
     * @param resourceIdGenerator
     *            The generator for Resource ids
     * @param resourceRoleIdGenerator
     *            The generator for ResourceRole ids
     * @param notificationTypeIDGenerator
     *            The generator for NotificationType ids
     * @throws IllegalArgumentException
     *             If any argument is null
     */
    public PersistenceResourceManager(ResourcePersistence persistence, SearchBundle resourceSearchBundle,
            SearchBundle resourceRoleSearchBundle, SearchBundle notificationSearchBundle,
            SearchBundle notificationTypeSearchBundle, IDGenerator resourceIdGenerator,
            IDGenerator resourceRoleIdGenerator, IDGenerator notificationTypeIDGenerator) {

        Helper.checkNull(persistence, "persistence");

        Helper.checkNull(resourceSearchBundle, "resourceSearchBundle");
        Helper.checkNull(resourceRoleSearchBundle, "resourceRoleSearchBundle");

        Helper.checkNull(notificationSearchBundle, "notificationSearchBundle");
        Helper.checkNull(notificationTypeSearchBundle, "notificationTypeSearchBundle");

        Helper.checkNull(resourceRoleIdGenerator, "resourceRoleIdGenerator");
        Helper.checkNull(notificationTypeIDGenerator, "notificationTypeIDGenerator");
        Helper.checkNull(resourceIdGenerator, "resourceIdGenerator");

        this.persistence = persistence;
        this.resourceSearchBundle = resourceSearchBundle;
        this.resourceRoleSearchBundle = resourceRoleSearchBundle;
        this.notificationSearchBundle = notificationSearchBundle;
        this.resourceIdGenerator = resourceIdGenerator;
        this.resourceRoleIdGenerator = resourceRoleIdGenerator;
        this.notificationTypeIdGenerator = notificationTypeIDGenerator;
        this.notificationTypeSearchBundle = notificationTypeSearchBundle;

    }

    /**
     * creates a new PersistenceResourceManager. The SearchBundle fields should be initialized by retrieving the
     * SearchBundles from the SearchBundlerManager using the constants defined in this class. The IDGenerator fields
     * should be initialized by retrieving the IDGenerators from the IDGeneratorFactory using the constants defined in
     * this class.
     *
     * @param persistence
     *            The persistence for Resources and related objects
     * @param searchBundleManager
     *            The manager containing the various SearchBundles needed
     * @throws IllegalArgumentException
     *             If any argument is null or any search bundle or id generator is not available under the required
     *             names
     */
    public PersistenceResourceManager(ResourcePersistence persistence, SearchBundleManager searchBundleManager) {
        Helper.checkNull(persistence, "persistence");
        Helper.checkNull(searchBundleManager, " searchBundleManager");

        this.persistence = persistence;

        this.notificationSearchBundle = searchBundleManager.getSearchBundle(NOTIFICATION_SEARCH_BUNDLE_NAME);
        if (notificationSearchBundle == null) {
            throw new IllegalArgumentException("The searchbundlemanager does not contains "
                    + NOTIFICATION_SEARCH_BUNDLE_NAME);
        }

        this.notificationTypeSearchBundle = searchBundleManager.getSearchBundle(NOTIFICATION_TYPE_SEARCH_BUNDLE_NAME);
        if (notificationTypeSearchBundle == null) {
            throw new IllegalArgumentException("The searchbundlemanager does not contains "
                    + NOTIFICATION_TYPE_SEARCH_BUNDLE_NAME);
        }

        this.resourceSearchBundle = searchBundleManager.getSearchBundle(RESOURCE_SEARCH_BUNDLE_NAME);
        if (resourceSearchBundle == null) {
            throw new IllegalArgumentException("The searchbundlemanager does not contains "
                    + RESOURCE_SEARCH_BUNDLE_NAME);
        }

        this.resourceRoleSearchBundle = searchBundleManager.getSearchBundle(RESOURCE_ROLE_SEARCH_BUNDLE_NAME);
        if (resourceRoleSearchBundle == null) {
            throw new IllegalArgumentException("The searchbundlemanager does not contains "
                    + RESOURCE_ROLE_SEARCH_BUNDLE_NAME);
        }

        try {
            this.notificationTypeIdGenerator = IDGeneratorFactory.getIDGenerator(NOTIFICATION_TYPE_ID_GENERATOR_NAME);
        } catch (IDGenerationException e) {
            throw new IllegalArgumentException("The id generator name " + NOTIFICATION_TYPE_ID_GENERATOR_NAME
                    + " is not a valid one, " + e.getMessage());
        }

        try {
            this.resourceIdGenerator = IDGeneratorFactory.getIDGenerator(RESOURCE_ID_GENERATOR_NAME);
        } catch (IDGenerationException e) {
            throw new IllegalArgumentException("The id generator name " + RESOURCE_ID_GENERATOR_NAME
                    + " is not a valid one, " + e.getMessage());
        }

        try {
            this.resourceRoleIdGenerator = IDGeneratorFactory.getIDGenerator(RESOURCE_ROLE_ID_GENERATOR_NAME);
        } catch (IDGenerationException e) {
            throw new IllegalArgumentException("The id generator name " + RESOURCE_ROLE_ID_GENERATOR_NAME
                    + " is not a valid one, " + e.getMessage());
        }
    }

    /**
     * <p>
     * Updates the given resource in the persistence store. If the resource is new (id is UNSET_ID),
     * then an id should be assigned and the resource added to the persistence store. Otherwise the resource data in the
     * persistence store should be updated.
     * </p>
     *
     * <p>
     * Exception handling: Wrap any IDGenerationException in a ResourcePersistenceException, Any
     * ResourcePersistenceException coming from the persistence should be passed directly back to the caller.
     * </p>
     *
     * @param resource
     *            The resource to update
     * @param operator
     *            The operator making the update
     *
     * @throws IllegalArgumentException
     *             If resource or operator is null
     * @throws IllegalArgumentException
     *             If a required field of the resource is not set (if resource.getResourceRole() is null), or if the
     *             resource role is associated with a phase type and the resource role is not associated with a phase
     * @throws ResourcePersistenceException
     *             If there is an error updating the resource
     */
    public void updateResource(Resource resource, String operator) throws ResourcePersistenceException {
        Helper.checkNull(resource, "resource");
        Helper.checkNull(operator, "operator");

        if (resource.getId() == Resource.UNSET_ID) {
            try {
                resource.setId(resourceIdGenerator.getNextID());
            } catch (IDGenerationException e) {
                throw new ResourcePersistenceException("The id for Resource can not be generated.", e);
            }
            setAudit(resource, operator, true);
            persistence.addResource(resource);
        } else {
            setAudit(resource, operator, false);
            persistence.updateResource(resource);
        }
    }

    /**
     * <p>
     * Removes the given resource in the persistence store (by id).
     * </p>
     *
     * <p>
     * Exception Handling: Simply allow any ResourcePersistenceException thrown by the persistence to pass back to the
     * caller.
     * </p>
     *
     * @param resource
     *            The resource to remove
     * @param operator
     *            The operator making the update
     *
     * @throws IllegalArgumentException
     *             If resource or operator is null
     * @throws IllegalArgumentException
     *             If the id of the resource is UNSET_ID
     * @throws ResourcePersistenceException
     *             If there is an error updating the the persistence store.
     */
    public void removeResource(Resource resource, String operator) throws ResourcePersistenceException {
        Helper.checkNull(resource, "resource");
        Helper.checkNull(operator, "operator");

        if (resource.getId() == Resource.UNSET_ID) {
            throw new IllegalArgumentException("The id of the resource should be set, but was " + resource.getId());
        }

        setAudit(resource, operator, false);
        persistence.deleteResource(resource);
    }

    /**
     * <p>
     * Updates all resources for the given project. Any resources in the array with UNSET_ID are
     * assigned an id and updated in the persistence. Any resources with an id already assigned are updated in the
     * persistence. Any resources associated with the project in the persistence store, but not appearing in the array
     * are removed.
     * </p>
     *
     * <p>
     * Exception Handling: Simply allow any ResourcePersistenceException thrown by the other methods of this class that
     * are called back to the caller of this method.
     * </p>
     *
     * @param resources
     *            The resources associated with the project
     * @param project
     *            The project to update resources for
     * @param operator
     *            The operator making the update
     * @throws ResourcePersistenceException
     * @throws SearchBuilderException
     * @throws IllegalArgumentException
     *             If resources is null or has null entries project is <= 0 , operator is null
     * @throws IllegalArgumentException
     *             If a required field of the resource is not set (if resource.getResourceRole() is null), or if the
     *             resource role is associated with a phase type and the resource role is not associated with a phase
     * @throws ResourcePersistenceException
     *             If there is an error updating the persistence store.
     * @throws IllegalArgumentException
     *             If resources in the array have a project that is not the same as the project argument
     */
    public void updateResources(Resource[] resources, long project, String operator)
            throws ResourcePersistenceException {
        Helper.checkNull(resources, "resources");
        Helper.checkPositiveValue(project, "project");
        Helper.checkNull(operator, "operator");

        for (int i = 0; i < resources.length; i++) {
            if (resources[i] == null) {
                throw new IllegalArgumentException("resources can not have null entry.");
            }

            if (resources[i].getProject() == null) {
                throw new IllegalArgumentException(
                        "The resources array contains a Resource with no project whose index is " + i);
            }
            if (resources[i].getProject().longValue() != project) {
                throw new IllegalArgumentException(
                        "The sources in the array has a project that is not the same as the project argument.");
            }
        }

        Resource[] oldResources = null;

        try {
            oldResources = searchResources(ResourceFilterBuilder.createProjectIdFilter(project));
        } catch (SearchBuilderException e) {
            throw new ResourcePersistenceException("Failed to search resource for project " + project + " .", e);
        }

        for (int oldResIndex = 0; oldResIndex < oldResources.length; oldResIndex++) {
            boolean find = false;
            for (int index = 0; index < resources.length && !find; index++) {
                if (oldResources[oldResIndex].getId() == resources[index].getId()) {
                    find = true;
                    break;
                }
            }
            if (!find) {
                removeResource(oldResources[oldResIndex], operator);
            }
        }

        for (int i = 0; i < resources.length; i++) {
            updateResource(resources[i], operator);
        }
    }

    /**
     * <p>
     * Gets the resource with the given id from the persistence store. Returns null if there is no resource
     * with the given id.
     * </p>
     *
     * <p>
     * Exception Handling: Simply allow any ResourcePersistenceException thrown by the persistence to pass back to the
     * caller.
     * </p>
     *
     *
     * @return The loaded Resource
     * @param id
     *            The id of the resource to retrieve
     *
     * @throws IllegalArgumentException
     *             If id is <= 0
     * @throws ResourcePersistenceException
     *             if there is an error reading the persistence store
     */
    public Resource getResource(long id) throws ResourcePersistenceException {
        Helper.checkPositiveValue(id, "id");

        return persistence.loadResource(id);
    }

    /**
     * <p>
     *  Searches the resources in the persistence store using the given filter. The filter can be formed
     * using the field names and utility methods in ResourceFilterBuilder. The return will always be a non-null
     * (possibly 0 item) array.
     * </p>
     *
     * <p>
     * Exception Handling: Simply allow any ResourcePersistenceException thrown by the persistence to pass back to the
     * caller. Allow any SearchBuilderException thrown to pass back to the caller.
     * </p>
     *
     * @return The loaded resources
     * @param filter
     *            The filter to use
     * @throws SearchBuilderException
     * @throws IllegalArgumentException
     *             If filter is null
     * @throws ResourcePersistenceException
     *             If there is an error reading the persistence store
     * @throws SearchBuilderException
     *             If there is an error executing the filter.
     *
     * @throws SearchBuilderConfigurationException
     *             If the manager is not properly configured for searching
     */
    public Resource[] searchResources(Filter filter) throws SearchBuilderException, ResourcePersistenceException {
        Helper.checkNull(filter, "filter");

        Object resultSet = resourceSearchBundle.search(filter);

        // Retrieve the resource ids from the search result set.
        long[] resourceIds = retrieveIds(resultSet);

        return persistence.loadResources(resourceIds);
    }

    /**
     * <p>
     *  Updates the given resource role in the persistence store. If the resource role is new (id is
     * UNSET_ID), then an id should be assigned and the resource role should be added to the persistence store.
     * Otherwise the resource role data in the persistence store should be updated.
     * </p>
     *
     * <p>
     * Exception handling: Wrap any IDGenerationException in a ResourcePersistenceException, Any
     * ResourcePersistenceException coming from the persistence should be passed directly back to the caller.
     * </p>
     *
     * @param resourceRole
     *            The resource role to update
     * @param operator
     *            The operator making the update
     * @throws ResourcePersistenceException
     * @throws IllegalArgumentException
     *             If resourceRole or operator is null
     * @throws IllegalArgumentException
     *             If a required field of the resource role is missing (i.e. name or description of the resource role is
     *             null)
     * @throws ResourcePersistenceException
     *             If there is an error updating the persistence
     */
    public void updateResourceRole(ResourceRole resourceRole, String operator) throws ResourcePersistenceException {
        Helper.checkNull(resourceRole, "resourceRole");
        Helper.checkNull(operator, "operator");

        if (resourceRole.getId() == ResourceRole.UNSET_ID) {
            /* The id of the resource role is not set, meaning to create a new one */
            try {
                resourceRole.setId(resourceRoleIdGenerator.getNextID());
            } catch (IDGenerationException e) {
                throw new ResourcePersistenceException("Failed to generate id for resourceRole.", e);
            }

            setAudit(resourceRole, operator, true);
            persistence.addResourceRole(resourceRole);
        } else {
            /* Otherwise, update it */
            setAudit(resourceRole, operator, false);
            persistence.updateResourceRole(resourceRole);
        }
    }

    /**
     * <p>
     * Removes a resource role from the persistence (by id).
     * </p>
     *
     * <p>
     * Exception Handling: Simply allow any ResourcePersistenceException thrown by the persistence to pass back to the
     * caller.
     * </p>
     *
     * @param resourceRole
     *            The resource role to remove
     * @param operator
     *            The operator making the update
     * @throws ResourcePersistenceException
     * @throws IllegalArgumentException
     *             If resourceRole or operator is null
     * @throws IllegalArgumentException
     *             If the id of the resource role is UNSET_ID
     * @throws ResourcePersistenceException
     *             If there is an error updating the the persistence store.
     */
    public void removeResourceRole(ResourceRole resourceRole, String operator) throws ResourcePersistenceException {
        Helper.checkNull(resourceRole, "resourceRole");
        Helper.checkNull(operator, "operator");

        if (resourceRole.getId() == ResourceRole.UNSET_ID) {
            throw new IllegalArgumentException("The id of the resource role is not set.");
        }

        setAudit(resourceRole, operator, false);
        persistence.deleteResourceRole(resourceRole);
    }

    /**
     * <p>
     * Gets all resource roles in the persistence store.
     * </p>
     *
     * <p>
     * Exception Handling: Simply allow any ResourcePersistenceException thrown by the persistence to pass back to the
     * caller. Wrap any SearchBuilderException into a ResourcePersistenceException.
     * </p>
     *
     *
     * @return All resource roles in the persistence store
     * @throws ResourcePersistenceException
     *             If there is an error reading the persistence store.
     */
    public ResourceRole[] getAllResourceRoles() throws ResourcePersistenceException {

        try {
            Filter filter = SearchBundle.buildGreaterThanFilter(
                    ResourceRoleFilterBuilder.RESOURCE_ROLE_ID_FIELD_NAME, new Long(0));

            return this.searchResourceRoles(filter);
        } catch (SearchBuilderException e) {
            throw new ResourcePersistenceException("Fail to get all resource roles.", e);
        }

    }

    /**
     * <p>
     * Searches the resource roles in the persistence store using the given filter. The filter can
     * be formed using the field names and utility methods in ResourceRoleFilterBuilder. The return will always be a
     * non-null (possibly 0 item) array.
     * </p>
     *
     * <p>
     * Exception Handling: Simply allow any ResourcePersistenceException thrown by the persistence to pass back to the
     * caller. Allow any SearchBuilderException thrown to pass back to the caller.
     * </p>
     *
     * @return The loaded resource roles
     * @param filter
     *            The filter to use
     * @throws IllegalArgumentException
     *             If filter is null
     * @throws ResourcePersistenceException
     *             If there is an error reading the persistence store
     * @throws SearchBuilderException
     *             If there is an error executing the filter.
     * @throws SearchBuilderConfigurationException
     *             If the manager is not properly configured for searching
     */
    public ResourceRole[] searchResourceRoles(Filter filter) throws SearchBuilderException,
            ResourcePersistenceException {
        Helper.checkNull(filter, "filter");

        Object resultSet = this.resourceRoleSearchBundle.search(filter);
        // retrieve the resource role ids from the searching result set.
        long[] resourceRoleIds = retrieveIds(resultSet);

        return persistence.loadResourceRoles(resourceRoleIds);
    }

    /**
     * <p>
     * Adds a list of notifications for the given user ids to the persistence store. All of the
     * notification are added are for the given project and type.
     * </p>
     *
     * <p>
     * Exception Handling: Simply allow any ResourcePersistenceException thrown by the persistence to pass back to the
     * caller.
     * </p>
     *
     * @param users
     *            The users to add notifications for
     * @param project
     *            The project the notifications apply to
     * @param notificationType
     *            The type of notifications to add
     * @param operator
     *            The operation making the update
     * @throws IllegalArgumentException
     *             If operator or users is null or any item of users, project, or notificationType is <= 0
     * @throws ResourcePersistenceException
     *             If there is an error updating the persistence store
     */
    public void addNotifications(long[] users, long project, long notificationType, String operator)
            throws ResourcePersistenceException {
        Helper.checkNull(users, "users");
        Helper.checkNull(operator, "operator");
        Helper.checkPositiveValue(project, "project");
        Helper.checkPositiveValue(notificationType, "notificationType");

        for (int i = 0; i < users.length; i++) {
            if (users[i] <= 0) {
                throw new IllegalArgumentException("user can not have non positive id");
            }
            this.persistence.addNotification(users[i], project, notificationType, operator);
        }
    }

    /**
     * <p>
     * Removes a list of notifications for the given user ids from the persistence store. The
     * notifications removed are for the given project and type.
     * </p>
     *
     * <p>
     * Exception Handling: Simply allow any ResourcePersistenceException thrown by the persistence to pass back to the
     * caller.
     * </p>
     *
     * @param users
     *            The users to remove notifications for
     * @param project
     *            The project the notifications apply to
     * @param notificationType
     *            The type of notifications to remove
     * @param operator
     *            The operation making the update
     * @throws IllegalArgumentException
     *             If operator or users is null or any item of users, project, or notificationType is <= 0
     * @throws ResourcePersistenceException
     *             If there is an error updating the persistence store
     */
    public void removeNotifications(long[] users, long project, long notificationType, String operator)
            throws ResourcePersistenceException {
        Helper.checkNull(users, "users");
        Helper.checkNull(operator, "operator");
        Helper.checkPositiveValue(project, "project");
        Helper.checkPositiveValue(notificationType, "notificationType");

        for (int i = 0; i < users.length; i++) {
            if (users[i] <= 0) {
                throw new IllegalArgumentException("user can not have non positive id");
            }
            this.persistence.removeNotification(users[i], project, notificationType, operator);
        }
    }

    /**
     * <p>
     *  Gets the user id for all notifications for the given project and type. The return will always
     * be a non-null (possibly 0 item) array.
     * </p>
     *
     * <p>
     * Exception Handling: Simply allow any ResourcePersistenceException thrown by the persistence to pass back to the
     * caller. Wrap any SearchBuilderException into a ResourcePersistenceException.
     * </p>
     *
     *
     * @return The user ids of the notifications for the project and type
     * @param project
     *            The project to get notifications for
     * @param notificationType
     *            The type of notifications to retrieve
     * @throws IllegalArgumentException
     *             If project or notificationType is <= 0
     * @throws ResourcePersistenceException
     *             If there is an error reading the persistence store.
     */
    public long[] getNotifications(long project, long notificationType) throws ResourcePersistenceException {
        try {
            Filter filter = SearchBundle.buildAndFilter(NotificationFilterBuilder.createProjectIdFilter(project),
                    NotificationFilterBuilder.createNotificationTypeIdFilter(new Long(notificationType)));

            Notification[] notifications = searchNotifications(filter);

            long[] results = new long[notifications.length];
            for (int i = 0; i < notifications.length; i++) {
                results[i] = notifications[i].getExternalId();
            }
            return results;
        } catch (SearchBuilderException e) {
            throw new ResourcePersistenceException("Fail to get notifications", e);
        }
    }

    /**
     * <p>
     * Searches the notifications in the persistence store using the given filter. The filter can
     * be formed using the field names and utility methods in NotificationFilterBuilder. The return will always be a
     * non-null (possibly 0 item) array.
     * </p>
     *
     * <p>
     * Exception Handling: Simply allow any ResourcePersistenceException thrown by the persistence to pass back to the
     * caller. Allow any SearchBuilderException thrown to pass back to the caller.
     * </p>
     *
     * @return The loaded notifications
     * @param filter
     *            The filter to use
     * @throws IllegalArgumentException
     *             If filter is null
     * @throws ResourcePersistenceException
     *             If there is an error reading the persistence store
     * @throws SearchBuilderException
     *             If there is an error executing the filter.
     * @throws SearchBuilderConfigurationException
     *             If the manager is not properly configured for searching
     */
    public Notification[] searchNotifications(Filter filter) throws SearchBuilderException,
            ResourcePersistenceException {
        Helper.checkNull(filter, "filter");

        CustomResultSet crs = (CustomResultSet) this.notificationSearchBundle.search(filter);

        if (crs.getRecordCount() == 0) {
            return new Notification[0];
        }

        List userIds = new ArrayList();
        List projectIds = new ArrayList();
        List notificationTypeIds = new ArrayList();

        crs.beforeFirst();
        while (!crs.isLast()) {
            crs.next();
            try {
                userIds.add(new Integer(crs.getInt(1)));
                projectIds.add(new Integer(crs.getInt(2)));
                notificationTypeIds.add(new Integer(crs.getInt(3)));
            } catch (ClassCastException e) {
                throw new SearchBuilderConfigurationException("The special column it does not consist of int value",
                        e);
            } catch (InvalidCursorStateException e) {
                throw new SearchBuilderConfigurationException("The manager is not properly configured for searching",
                        e);
            }
        }

        return this.persistence.loadNotifications(collectionToLongArray(userIds), collectionToLongArray(projectIds),
                collectionToLongArray(notificationTypeIds));
    }

    /**
     * <p>
     *  Updates the given notification type in the persistence store. If the notification type is
     * new (id is UNSET_ID), then an id should be assigned and the notification type added to the persistence store.
     * Otherwise the notification type data in the persistence store should be updated.
     * </p>
     *
     * <p>
     * Exception handling: Wrap any IDGenerationException in a ResourcePersistenceException, Any
     * ResourcePersistenceException coming from the persistence should be passed directly back to the caller.
     * </p>
     *
     *
     * @param notificationType
     *            The notification type to update
     * @param operator
     *            The operator making the update
     * @throws IllegalArgumentException
     *             If notificationType or operator is null
     * @throws IllegalArgumentException
     *             If a required field of the notification type is missing (i.e. name or description of the notification
     *             type is null)
     * @throws ResourcePersistenceException
     *             If there is an error updating the persistence
     */
    public void updateNotificationType(NotificationType notificationType, String operator)
            throws ResourcePersistenceException {
        Helper.checkNull(notificationType, "notificationType");
        Helper.checkNull(operator, "operator");

        if (notificationType.getName() == null || notificationType.getDescription() == null) {
            throw new IllegalArgumentException("name or description of the notification type is missing.");
        }

        if (notificationType.getId() == NotificationType.UNSET_ID) {
            /* The notification type is a new one to be created */
            try {
                notificationType.setId(this.notificationTypeIdGenerator.getNextID());
            } catch (IDGenerationException e) {
                throw new ResourcePersistenceException("The id for the notification type can not be generated.");
            }
            setAudit(notificationType, operator, true);
            this.persistence.addNotificationType(notificationType);
        } else {
            /* Update the existing notification type */
            setAudit(notificationType, operator, false);
            this.persistence.updateNotificationType(notificationType);
        }
    }

    /**
     * <p>
     * Removes a notification type from the persistence (by id).
     * </p>
     *
     * <p>
     * Exception Handling: Simply allow any ResourcePersistenceException thrown by the persistence to pass back to the
     * caller.
     * </p>
     *
     * @param notificationType
     *            The notification type to remove
     * @param operator
     *            The operator making the update
     * @throws IllegalArgumentException
     *             If notificationType or operator is null
     * @throws IllegalArgumentException
     *             If the id of the resource role is UNSET_ID
     * @throws ResourcePersistenceException
     *             If there is an error updating the the persistence store.
     */
    public void removeNotificationType(NotificationType notificationType, String operator)
            throws ResourcePersistenceException {
        Helper.checkNull(notificationType, "notificationType");
        Helper.checkNull(operator, "operator");

        if (notificationType.getId() == NotificationType.UNSET_ID) {
            throw new IllegalArgumentException("Can not remove the notification type, as its id is unset.");
        }

        setAudit(notificationType, operator, false);
        this.persistence.deleteNotificationType(notificationType);
    }

    /**
     * <p>
     * Searches the notification types in the persistence store using the given filter. The
     * filter can be formed using the field names and utility methods in NotificationTypeFilterBuilder. The return will
     * always be a non-null (possibly 0 item) array.
     * </p>
     *
     * <p>
     * Exception Handling: Simply allow any ResourcePersistenceException thrown by the persistence to pass back to the
     * caller. Allow any SearchBuilderException thrown to pass back to the caller.
     * </p>
     *
     * @param filter
     *            The filter to use
     * @return The loaded notification types
     * @throws IllegalArgumentException
     *             If filter is null
     * @throws ResourcePersistenceException
     *             If there is an error reading the persistence store
     * @throws SearchBuilderException
     *             If there is an error executing the filter.
     * @throws SearchBuilderConfigurationException
     *             If the manager is not properly configured for searching
     */
    public NotificationType[] searchNotificationTypes(Filter filter) throws SearchBuilderException,
            ResourcePersistenceException {
        Helper.checkNull(filter, "filter");

        Object resultSet = this.notificationTypeSearchBundle.search(filter);

        // retrieve the notification type ids from the searching result set.
        long[] notificationTypeIds = retrieveIds(resultSet);

        return this.persistence.loadNotificationTypes(notificationTypeIds);
    }

    /**
     * <p>
     * Gets all notification types in the persistence store.
     * </p>
     *
     * <p>
     * Exception Handling: Simply allow any ResourcePersistenceException thrown by the persistence to pass back to the
     * caller. Wrap any SearchBuilderException into a ResourcePersistenceException.
     * </p>
     *
     *
     * @return All notification types in the persistence store
     * @throws ResourcePersistenceException
     *             If there is an error reading the persistence store.
     */
    public NotificationType[] getAllNotificationTypes() throws ResourcePersistenceException {
        try {
            Filter filter = SearchBundle.buildGreaterThanFilter(
                    NotificationTypeFilterBuilder.NOTIFICATION_TYPE_ID_FIELD_NAME, new Long(0));

            return this.searchNotificationTypes(filter);
        } catch (SearchBuilderException e) {
            throw new ResourcePersistenceException("Fail to get all the notification types.", e);
        }
    }

    /**
     * Fill the AuditableResourceStructure instance with proper values.
     *
     * @param auditableBe
     *            the AuditableResourceStructure instance to be filled with proper values
     * @param operator
     *            the operation making the change
     * @param isCreate
     *            identify if it change is made.
     */
    private static void setAudit(AuditableResourceStructure auditableBe, String operator, boolean isCreate) {
        Date currentDate = new Date();
        if (isCreate) {
            auditableBe.setCreationUser(operator);
            auditableBe.setCreationTimestamp(currentDate);
        }

        // If update, left the creationUser and CreationDate unchanged.

        auditableBe.setModificationUser(operator);
        auditableBe.setModificationTimestamp(currentDate);
    }

    /**
     * Extract an array of long ids from the custom ResultSet.
     *
     * @param customResultSet
     *            it should be an CustomResultSet instance and its first column should have the id field to be
     *            retrieved.
     *
     * @return an array of long containing the retrieved ids.
     *
     * @throws SearchBuilderConfigurationException
     *             if the argument is not of CustomResultSet instance, or the search failed for various reason.
     */
    private long[] retrieveIds(Object customResultSet) throws SearchBuilderConfigurationException {
        if (!(customResultSet instanceof CustomResultSet)) {
            throw new SearchBuilderConfigurationException("The return should be instanceof CustomResultSet");
        }

        CustomResultSet crs = (CustomResultSet) customResultSet;

        if (crs.getRecordCount() == 0) {
            return new long[0];
        }

        Set ids = new HashSet();

        crs.beforeFirst();

        while (!crs.isLast()) {
            crs.next();

            try {
                ids.add(new Integer(crs.getInt(1)));
            } catch (ClassCastException e) {
                throw new SearchBuilderConfigurationException("The special column it does not consist of int value",
                        e);
            } catch (InvalidCursorStateException e) {
                throw new SearchBuilderConfigurationException("The manager is not properly configured for searching",
                        e);
            }
        }

        return collectionToLongArray(ids);
    }

    /**
     * Convert a collection of Long objects into a long array containing the ids..
     *
     * @param ids
     *            the collection of Long id instances.
     * @return an array of long containing the ids.
     */
    private long[] collectionToLongArray(Collection ids) {
        long[] resultIds = new long[ids.size()];
        int index = 0;
        for (Iterator iter = ids.iterator(); iter.hasNext();) {
            resultIds[index++] = ((Integer) iter.next()).longValue();
        }
        return resultIds;
    }
}
