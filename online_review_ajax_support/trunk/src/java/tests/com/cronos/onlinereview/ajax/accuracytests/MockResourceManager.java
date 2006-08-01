/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 * 
 */
package com.cronos.onlinereview.ajax.accuracytests;

import com.topcoder.management.resource.NotificationType;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.resource.ResourceManager;
import com.topcoder.management.resource.ResourceRole;
import com.topcoder.search.builder.filter.Filter;


/**
 * Mock class implements ResourceManager.
 * 
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockResourceManager implements ResourceManager {

    public void updateResource(Resource resource, String operator) {
    }

    public void updateResources(Resource[] resources, long project, String operator) {
    }

    public Resource getResource(long id) {
        if (id == -1) {
            return null;
        } else {
            return new MockResource(id);
        }
        
    }

    public Resource[] searchResource(Filter filter) {
        return null;
    }

    public ResourceRole[] getAllResourceRoles() {
        return new ResourceRole[] {new MockResourceRole()};
    }

    public void addNotifications(long[] users, long project, long notificationType, String operator) {
    }

    public void removeNotifications(long[] users, long project, long notificationType, String opertator) {
    }

    public long[] getNotifications(long project, long notificationType) {
        return null;
    }

    public NotificationType[] getAllNotificationTypes() {
        return new NotificationType[] {new MockNotificationType()};
    }

}
