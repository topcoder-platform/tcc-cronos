/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.persistence;

import java.text.MessageFormat;
import java.util.Date;
import java.util.Map;

import com.topcoder.management.resource.AuditableResourceStructure;
import com.topcoder.management.resource.Notification;
import com.topcoder.management.resource.NotificationType;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.resource.ResourceManager;
import com.topcoder.management.resource.ResourceRole;
import com.topcoder.management.resource.persistence.ResourcePersistenceException;
import com.topcoder.search.builder.SearchBuilderException;
import com.topcoder.search.builder.filter.Filter;

/**
 * <p>
 * Mock implementation of <code>ResourceManager</code> used for Unit tests.
 * </p>
 *
 * <p>
 * It will be deployed to JBoss.
 * </p>
 *
 * <p>
 * See build.xml "deployToJBoss" task and /test_files/ResourceManagerServiceBean.xml
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockResourceManager implements ResourceManager {

    /**
     * <p>
     * The operator will trigger <code>ResourcePersistenceException</code>
     * in those methods which take an operator.
     * </p>
     */
    public static final String FAIL_OPERATOR = "fail";

    /**
     * <p>
     * The id will make <code>updateResource()</code>
     * in <code>removeResource()</code> methods to not
     * check that all the fields of resource have been
     * set. Since the converter will not set all the
     * fields.
     * </p>
     */
    public static final long CONVERTED_RESOURCE_ID = 87523;

    /**
     * <p>
     * The id will trigger <code>ResourcePersistenceException</code>
     * in <code>getResource()</code> method.
     * </p>
     */
    public static final long INVALID_RESOURCE_ID = 87523;

    /**
     * <p>
     * The notification type will trigger <code>ResourcePersistenceException</code>
     * in <code>getNotifications()</code> method.
     * </p>
     */
    public static final long INVALID_NOTIFICATION_TYPE = 87523;

    /**
     * <p>
     * Default empty constructor. Does nothing.
     * </p>
     */
    public MockResourceManager() {
        // empty.
    }

    /**
     * <p>
     * Check all the fields of audit structure are set.
     * </p>
     *
     * @param audit to be checked.
     *
     * @throws ResourcePersistenceException If any field of audit structure is not set.
     */
    private void checkAuditFields(AuditableResourceStructure audit) throws ResourcePersistenceException {
        if (audit.getCreationTimestamp() == null) {
            throw new ResourcePersistenceException("create date not set");
        }
        if (audit.getModificationTimestamp() == null) {
            throw new ResourcePersistenceException("modify date not set");
        }
        if (audit.getCreationUser() == null) {
            throw new ResourcePersistenceException("create user not set");
        }
        if (audit.getModificationUser() == null) {
            throw new ResourcePersistenceException("modify user not set");
        }
    }

    /**
     * <p>
     * Check all the fields of resource are set.
     * </p>
     *
     * @param resource to be checked.
     *
     * @throws ResourcePersistenceException If any field of resource is not set.
     */
    private void checkResource(Resource resource) throws ResourcePersistenceException {
        this.checkAuditFields(resource);
        if (resource.getId() == -1) {
            throw new ResourcePersistenceException("id not set");
        }
        if (resource.getPhase() == null) {
            throw new ResourcePersistenceException("phase not set");
        }
        if (resource.getProject() == null) {
            throw new ResourcePersistenceException("project not set");
        }
        if (resource.getResourceRole() == null) {
            throw new ResourcePersistenceException("resource role not set");
        }
        if (resource.getSubmissions().length == 0) {
            throw new ResourcePersistenceException("submissions not set");
        }
        if (resource.getAllProperties().size() == 0) {
            throw new ResourcePersistenceException("properties map not set");
        }
    }

    /**
     * <p>
     * Check all the fields of resource role are set.
     * </p>
     *
     * @param resourceRole to be checked.
     *
     * @throws ResourcePersistenceException If any field of resource role is not set.
     */
    private void checkResourceRole(ResourceRole resourceRole) throws ResourcePersistenceException {
        this.checkAuditFields(resourceRole);
        if (resourceRole.getId() == -1) {
            throw new ResourcePersistenceException("id not set");
        }
        if (resourceRole.getName() == null) {
            throw new ResourcePersistenceException("name not set");
        }
        if (resourceRole.getDescription() == null) {
            throw new ResourcePersistenceException("description not set");
        }
        if (resourceRole.getPhaseType() == null) {
            throw new ResourcePersistenceException("phase type not set");
        }
    }

    /**
     * <p>
     * Check all the fields of notification type are set.
     * </p>
     *
     * @param notificationType to be checked.
     *
     * @throws ResourcePersistenceException If any field of notification type is not set.
     */
    private void checkNotificationType(NotificationType notificationType) throws ResourcePersistenceException {
        this.checkAuditFields(notificationType);
        if (notificationType.getId() == -1) {
            throw new ResourcePersistenceException("id not set");
        }
        if (notificationType.getName() == null) {
            throw new ResourcePersistenceException("name not set");
        }
        if (notificationType.getDescription() == null) {
            throw new ResourcePersistenceException("description not set");
        }
    }

    /**
     * <p>
     * Mock implementation. Ensure all the fields are passed from client.
     * </p>
     *
     * @param resource to be updated.
     * @param operator operator.
     *
     * @throws IllegalArgumentException If given resource is null.
     * @throws ResourcePersistenceException If given operator equals <code>FAIL_OPERATOR</code>.
     *         Or if any required field of resource is not set.
     */
    public void updateResource(Resource resource, String operator) throws ResourcePersistenceException {
        if (resource == null) {
            throw new IllegalArgumentException("Resource to be updated should not be null.");
        }
        if (resource.getId() != CONVERTED_RESOURCE_ID) {
            this.checkResource(resource);
        } else {
            if (resource.getAllProperties().size() == 0) {
                throw new ResourcePersistenceException("properties map not set");
            }
        }

        if (FAIL_OPERATOR.equalsIgnoreCase(operator)) {
            throw new ResourcePersistenceException("mock exception");
        }

        Map map = resource.getAllProperties();
        System.out.println("Method: updateResource(). Size of properties map: " + map.size());
        for (Object key : map.keySet()) {
            System.out.println(
                MessageFormat.format("Method: updateResource(). Property pair: [{0}, {1}]", key, map.get(key)));
        }
    }

    /**
     * <p>
     * Mock implementation. Ensure all the fields are passed from client.
     * </p>
     *
     * @param resource to be removed.
     * @param operator operator.
     *
     * @throws IllegalArgumentException If given resource is null.
     * @throws ResourcePersistenceException If given operator equals <code>FAIL_OPERATOR</code>.
     *         Or if any required field of resource is not set.
     */
    public void removeResource(Resource resource, String operator) throws ResourcePersistenceException {
        if (resource == null) {
            throw new IllegalArgumentException("Resource to be removed should not be null.");
        }
        if (resource.getId() != CONVERTED_RESOURCE_ID) {
            this.checkResource(resource);
        } else {
            if (resource.getAllProperties().size() == 0) {
                throw new ResourcePersistenceException("properties map not set");
            }
        }

        if (FAIL_OPERATOR.equalsIgnoreCase(operator)) {
            throw new ResourcePersistenceException("mock exception");
        }

        Map map = resource.getAllProperties();
        System.out.println("Method: removeResource(). Size of properties map: " + map.size());
        for (Object key : map.keySet()) {
            System.out.println(
                MessageFormat.format("Method: removeResource(). Property pair: [{0}, {1}]", key, map.get(key)));
        }
    }

    /**
     * <p>
     * Mock implementation. Ensure all the fields are passed from client.
     * </p>
     *
     * @param resources to be updated.
     * @param project Useless argument.
     * @param operator operator.
     *
     * @throws IllegalArgumentException If array is null or contains null element.
     * @throws ResourcePersistenceException If given operator equals <code>FAIL_OPERATOR</code>.
     *         Or if any field of resource is not set.
     */
    public void updateResources(Resource[] resources, long project, String operator)
        throws ResourcePersistenceException {
        if (resources == null) {
            throw new IllegalArgumentException("Resources array to be updated should not be null.");
        }

        for (Resource resource : resources) {
            if (resource == null) {
                throw new IllegalArgumentException("Resource to be updated should not be null.");
            }
            this.checkResource(resource);
            Map map = resource.getAllProperties();
            System.out.println("Method: updateResources(). Size of properties map: " + map.size());
            for (Object key : map.keySet()) {
                System.out.println(
                    MessageFormat.format("Method: updateResources(). Property pair: [{0}, {1}]", key, map.get(key)));
            }
        }

        if (FAIL_OPERATOR.equalsIgnoreCase(operator)) {
            throw new ResourcePersistenceException("mock exception");
        }
    }

    /**
     * <p>
     * Create a new resource, set its id as given id and return.
     * If given id is <code>Long.MAX_VALUE</code>, null is returned.
     * </p>
     *
     * @return The loaded Resource. If given id is <code>Long.MAX_VALUE</code>, null is returned.
     * @param id the id of the resource to retrieve
     *
     * @throws ResourcePersistenceException If given resource id is <code>INVALID_RESOURCE_ID</code>.
     */
    public Resource getResource(long id) throws ResourcePersistenceException {
        if (id == Long.MAX_VALUE) {
            return null;
        }
        if (id == INVALID_RESOURCE_ID) {
            throw new ResourcePersistenceException("mock exception");
        }
        Resource resource = new Resource(id);
        resource.setPhase(1L);
        resource.setProject(1L);

        resource.setProperty("property1", "value1");
        resource.setProperty("property2", "value2");

        resource.setSubmissions(new Long[]{1L, 2L});

        resource.setResourceRole(new ResourceRole(2L, "designer", 1L));

        resource.setCreationUser("CreatUser");
        resource.setModificationUser("ModifyUser");
        resource.setCreationTimestamp(new Date());
        resource.setModificationTimestamp(new Date());

        return resource;
    }

    /**
     * <p>
     * Mock implementation. Ensure all the fields are passed from client.
     * </p>
     *
     * @param resourceRole resource role to be update.
     * @param operator operator.
     *
     * @throws ResourcePersistenceException If given operator equals <code>FAIL_OPERATOR</code>.
     *         Or if any field of resource role is not set.
     */
    public void updateResourceRole(ResourceRole resourceRole, String operator) throws ResourcePersistenceException {
        this.checkResourceRole(resourceRole);
        if (FAIL_OPERATOR.equalsIgnoreCase(operator)) {
            throw new ResourcePersistenceException("mock exception");
        }
    }

    /**
     * <p>
     * Mock implementation. Ensure all the fields are passed from client.
     * </p>
     *
     * @param resourceRole resource role to be removed.
     * @param operator operator.
     *
     * @throws ResourcePersistenceException If given operator equals <code>FAIL_OPERATOR</code>.
     *         Or if any field of resource role is not set.
     */
    public void removeResourceRole(ResourceRole resourceRole, String operator) throws ResourcePersistenceException {
        this.checkResourceRole(resourceRole);
        if (FAIL_OPERATOR.equalsIgnoreCase(operator)) {
            throw new ResourcePersistenceException("mock exception");
        }
    }

    /**
     * <p>
     * Return the "developer" role and "designer" role.
     * </p>
     *
     * @return the "developer" role and "designer" role.
     * @throws ResourcePersistenceException Never
     */
    public ResourceRole[] getAllResourceRoles() throws ResourcePersistenceException {
        ResourceRole resourceRole1 = new ResourceRole(1L);
        resourceRole1.setName("developer");
        resourceRole1.setDescription("description");
        resourceRole1.setCreationUser("CreatUser");
        resourceRole1.setModificationUser("ModifyUser");
        resourceRole1.setCreationTimestamp(new Date());
        resourceRole1.setModificationTimestamp(new Date());

        ResourceRole resourceRole2 = new ResourceRole(2L);
        resourceRole2.setName("designer");
        resourceRole2.setDescription("description");
        resourceRole2.setCreationUser("CreatUser");
        resourceRole2.setModificationUser("ModifyUser");
        resourceRole2.setCreationTimestamp(new Date());
        resourceRole2.setModificationTimestamp(new Date());
        return new ResourceRole[]{resourceRole1, resourceRole2};
    }

    /**
     * <p>
     * Empty method. Does nothing.
     * </p>
     *
     * @param users Useless argument.
     * @param project Useless argument.
     * @param notificationType Useless argument.
     * @param operator operator.
     *
     * @throws ResourcePersistenceException If given operator equals <code>FAIL_OPERATOR</code>.
     */
    public void addNotifications(long[] users, long project, long notificationType, String operator)
        throws ResourcePersistenceException {
        if (FAIL_OPERATOR.equalsIgnoreCase(operator)) {
            throw new ResourcePersistenceException("mock exception");
        }
    }

    /**
     * <p>
     * Empty method. Does nothing.
     * </p>
     *
     * @param users Useless argument.
     * @param project Useless argument.
     * @param notificationType Useless argument.
     * @param operator operator.
     *
     * @throws ResourcePersistenceException If given operator equals <code>FAIL_OPERATOR</code>.
     */
    public void removeNotifications(long[] users, long project, long notificationType, String operator)
        throws ResourcePersistenceException {
        if (FAIL_OPERATOR.equalsIgnoreCase(operator)) {
            throw new ResourcePersistenceException("mock exception");
        }
    }

    /**
     * <p>
     * Return <code>long[]{1, 2}</code> always.
     * </p>
     *
     * @param project project.
     * @param notificationType notification type.
     *
     * @return <code>long[]{1, 2}</code> always.
     *
     * @throws ResourcePersistenceException If given notification type is <code>INVALID_NOTIFICATION_TYPE</code>.
     */
    public long[] getNotifications(long project, long notificationType) throws ResourcePersistenceException {
        if (notificationType == INVALID_NOTIFICATION_TYPE) {
            throw new ResourcePersistenceException("mock exception");
        }
        return new long[]{1, 2};
    }

    /**
     * <p>
     * Mock implementation. Ensure all the fields are passed from client.
     * </p>
     *
     * @param notificationType notification type to be update.
     * @param operator operator.
     *
     * @throws ResourcePersistenceException If given operator equals <code>FAIL_OPERATOR</code>.
     *         Or if any field of notification type is not set.
     */
    public void updateNotificationType(NotificationType notificationType,
            String operator) throws ResourcePersistenceException {
        this.checkNotificationType(notificationType);
        if (FAIL_OPERATOR.equalsIgnoreCase(operator)) {
            throw new ResourcePersistenceException("mock exception");
        }
    }

    /**
     * <p>
     * Mock implementation. Ensure all the fields are passed from client.
     * </p>
     *
     * @param notificationType notification type to be removed.
     * @param operator operator.
     *
     * @throws ResourcePersistenceException If given operator equals <code>FAIL_OPERATOR</code>.
     *         Or if any field of notification type is not set.
     */
    public void removeNotificationType(NotificationType notificationType,
            String operator) throws ResourcePersistenceException {
        this.checkNotificationType(notificationType);
        if (FAIL_OPERATOR.equalsIgnoreCase(operator)) {
            throw new ResourcePersistenceException("mock exception");
        }
    }

    /**
     * <p>
     * Return the "email" type and "message" type.
     * </p>
     *
     * @return the "email" type and "message" type.
     *
     * @throws ResourcePersistenceException Never
     */
    public NotificationType[] getAllNotificationTypes() throws ResourcePersistenceException {
        NotificationType type1 = new NotificationType(1L);
        type1.setName("email");
        type1.setDescription("description");
        type1.setCreationUser("CreatUser");
        type1.setModificationUser("ModifyUser");
        type1.setCreationTimestamp(new Date());
        type1.setModificationTimestamp(new Date());

        NotificationType type2 = new NotificationType(2L);
        type2.setName("message");
        type2.setDescription("description");
        type2.setCreationUser("CreatUser");
        type2.setModificationUser("ModifyUser");
        type2.setCreationTimestamp(new Date());
        type2.setModificationTimestamp(new Date());
        return new NotificationType[]{type1, type2};
    }

    /**
     * <p>
     * Return an empty array always.
     * </p>
     *
     * @return An empty array.
     *
     * @param filter Useless argument.
     *
     * @throws ResourcePersistenceException Never
     * @throws SearchBuilderException Never
     */
    public ResourceRole[] searchResourceRoles(Filter filter)
        throws ResourcePersistenceException, SearchBuilderException {
        return new ResourceRole[0];
    }

    /**
     * <p>
     * Return an empty array always.
     * </p>
     *
     * @return An empty array.
     *
     * @param filter Useless argument.
     *
     * @throws ResourcePersistenceException Never
     * @throws SearchBuilderException Never
     */
    public Notification[] searchNotifications(Filter filter)
        throws ResourcePersistenceException, SearchBuilderException {
        return new Notification[0];
    }

    /**
     * <p>
     * Return an empty array always.
     * </p>
     *
     * @return An empty array.
     *
     * @param filter Useless argument.
     *
     * @throws ResourcePersistenceException Never
     * @throws SearchBuilderException Never
     */
    public NotificationType[] searchNotificationTypes(Filter filter)
        throws ResourcePersistenceException, SearchBuilderException {
        return new NotificationType[0];
    }

    /**
     * <p>
     * Return an empty array always.
     * </p>
     *
     * @return An empty array.
     *
     * @param filter Useless argument.
     *
     * @throws ResourcePersistenceException Never
     * @throws SearchBuilderException Never
     */
    public Resource[] searchResources(Filter filter) throws ResourcePersistenceException, SearchBuilderException {
        return new Resource[0];
    }

}
