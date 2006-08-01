/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource;

import com.topcoder.search.builder.filter.Filter;

/**
 * Mock implementation of <code>ResourceManager</code>.
 *
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockResourceManager implements ResourceManager {

    /**
     * Add notifications.
     * @param users the users
     * @param project the project
     * @param notificationType the type
     * @param operator the operator
     */
    public void addNotifications(long[] users, long project, long notificationType, String operator) {
    }

    /**
     * Get all the notification types.
     * @return all the notification types.
     */
    public NotificationType[] getAllNotificationTypes() {
        NotificationType type = new MockNotificationType();
        type.setId(1);
        type.setName("Timeline Notification");
        return new NotificationType[] {type};
    }

    /**
     * Get all the resource roles.
     * @return all the resource roles.
     */
    public ResourceRole[] getAllResourceRoles() {
        ResourceRole[] ret = new MockResourceRole[1];
        ret[0] = new MockResourceRole();
        ret[0].setName("Manager");
        ret[0].setId(1);
        return ret;
    }

    /**
     * Get notifications with project.
     * @param project the project id
     * @param notificationType the notification type
     * @return ids of the notifications
     */
    public long[] getNotifications(long project, long notificationType) {
        return null;
    }

    /**
     * Get resource by id.
     * @param id the resource id
     * @return the resource object
     */
    public Resource getResource(long id) {
        Resource resource = new MockResource();
        resource.setId(2);
        resource.setProject(new Long(1));
        ResourceRole resourceRole = new MockResourceRole();
        resourceRole.setId(id);
        resourceRole.setName("Manager");
        resource.setResourceRole(resourceRole);

        if (id == 1) {
            return resource;
        } else if (id == 2) {
            resourceRole.setName("Submitter");
        } else if (id == 3) {
            resourceRole.setName("Reviewer");
            resource.setPhase(new Long(1));
        }
        return resource;
    }

    /**
     * Remove the notifications.
     * @param users the users
     * @param project the project
     * @param notificationType the notification type
     * @param operator the operator
     */
    public void removeNotifications(long[] users, long project, long notificationType, String operator) {
    }

    /**
     * Search the resource by filter.
     * @param filter the filter
     * @return the resources found
     */
    public Resource[] searchResource(Filter filter) {
        return null;
    }

    /**
     * Update the resource.
     * @param resource the resource
     * @param operator the operator
     */
    public void updateResource(Resource resource, String operator) {
    }

    /**
     * Update resources.
     * @param resources the resources to update
     * @param project the project
     * @param operator the operator
     */
    public void updateResources(Resource[] resources, long project, String operator) {
    }

}
