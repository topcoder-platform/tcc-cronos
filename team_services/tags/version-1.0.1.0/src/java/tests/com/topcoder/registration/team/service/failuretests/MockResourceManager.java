package com.topcoder.registration.team.service.failuretests;

import com.topcoder.management.resource.Notification;
import com.topcoder.management.resource.NotificationType;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.resource.ResourceManager;
import com.topcoder.management.resource.ResourceRole;
import com.topcoder.management.resource.persistence.ResourcePersistenceException;
import com.topcoder.search.builder.SearchBuilderConfigurationException;
import com.topcoder.search.builder.SearchBuilderException;
import com.topcoder.search.builder.filter.Filter;

public class MockResourceManager implements ResourceManager {

    public MockResourceManager() {
        super();
        // TODO Auto-generated constructor stub
    }

    public void updateResource(Resource arg0, String arg1) throws ResourcePersistenceException {
        // TODO Auto-generated method stub
        
    }

    public void removeResource(Resource arg0, String arg1) throws ResourcePersistenceException {
        // TODO Auto-generated method stub
        
    }

    public void updateResources(Resource[] arg0, long arg1, String arg2) throws ResourcePersistenceException {
        // TODO Auto-generated method stub
        
    }

    public Resource getResource(long arg0) throws ResourcePersistenceException {
        if (arg0 == 1006) {
            throw new ResourcePersistenceException("");
        }
        if (arg0 == 1008) {
            return null;
        }
        Resource resource = new Resource();
        resource.setProperty("External reference ID", new Long(1005));
        resource.setProperty("Handle", "test");
        return resource;
    }

    public Resource[] searchResources(Filter arg0) throws ResourcePersistenceException, SearchBuilderException, SearchBuilderConfigurationException {
        // TODO Auto-generated method stub
        return null;
    }

    public void updateResourceRole(ResourceRole arg0, String arg1) throws ResourcePersistenceException {
        // TODO Auto-generated method stub
        
    }

    public void removeResourceRole(ResourceRole arg0, String arg1) throws ResourcePersistenceException {
        // TODO Auto-generated method stub
        
    }

    public ResourceRole[] getAllResourceRoles() throws ResourcePersistenceException {
        // TODO Auto-generated method stub
        return null;
    }

    public ResourceRole[] searchResourceRoles(Filter arg0) throws ResourcePersistenceException, SearchBuilderException, SearchBuilderConfigurationException {
        // TODO Auto-generated method stub
        return null;
    }

    public void addNotifications(long[] arg0, long arg1, long arg2, String arg3) throws ResourcePersistenceException {
        // TODO Auto-generated method stub
        
    }

    public void removeNotifications(long[] arg0, long arg1, long arg2, String arg3) throws ResourcePersistenceException {
        // TODO Auto-generated method stub
        
    }

    public long[] getNotifications(long arg0, long arg1) throws ResourcePersistenceException {
        // TODO Auto-generated method stub
        return null;
    }

    public Notification[] searchNotifications(Filter arg0) throws ResourcePersistenceException, SearchBuilderException, SearchBuilderConfigurationException {
        // TODO Auto-generated method stub
        return null;
    }

    public void updateNotificationType(NotificationType arg0, String arg1) throws ResourcePersistenceException {
        // TODO Auto-generated method stub
        
    }

    public void removeNotificationType(NotificationType arg0, String arg1) throws ResourcePersistenceException {
        // TODO Auto-generated method stub
        
    }

    public NotificationType[] searchNotificationTypes(Filter arg0) throws ResourcePersistenceException, SearchBuilderException, SearchBuilderConfigurationException {
        // TODO Auto-generated method stub
        return null;
    }

    public NotificationType[] getAllNotificationTypes() throws ResourcePersistenceException {
        // TODO Auto-generated method stub
        return null;
    }

}
