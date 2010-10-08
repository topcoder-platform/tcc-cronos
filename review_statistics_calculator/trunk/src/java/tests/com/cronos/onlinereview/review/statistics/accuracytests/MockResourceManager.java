package com.cronos.onlinereview.review.statistics.accuracytests;

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

	public void addNotifications(long[] arg0, long arg1, long arg2, String arg3)
			throws ResourcePersistenceException {
		

	}

	public NotificationType[] getAllNotificationTypes()
			throws ResourcePersistenceException {
		
		return null;
	}

	public ResourceRole[] getAllResourceRoles()
			throws ResourcePersistenceException {
		
		return null;
	}

	public long[] getNotifications(long arg0, long arg1)
			throws ResourcePersistenceException {
		
		return null;
	}

	public Resource getResource(long arg0) throws ResourcePersistenceException {
		
		return null;
	}

	public void removeNotificationType(NotificationType arg0, String arg1)
			throws ResourcePersistenceException {
		

	}

	public void removeNotifications(long[] arg0, long arg1, long arg2,
			String arg3) throws ResourcePersistenceException {
		

	}

	public void removeResource(Resource arg0, String arg1)
			throws ResourcePersistenceException {
		

	}

	public void removeResourceRole(ResourceRole arg0, String arg1)
			throws ResourcePersistenceException {
		

	}

	public NotificationType[] searchNotificationTypes(Filter arg0)
			throws ResourcePersistenceException, SearchBuilderException,
			SearchBuilderConfigurationException {
		
		return null;
	}

	public Notification[] searchNotifications(Filter arg0)
			throws ResourcePersistenceException, SearchBuilderException,
			SearchBuilderConfigurationException {
		
		return null;
	}

	public ResourceRole[] searchResourceRoles(Filter arg0)
			throws ResourcePersistenceException, SearchBuilderException,
			SearchBuilderConfigurationException {
		
		return null;
	}

	public Resource[] searchResources(Filter arg0)
			throws ResourcePersistenceException, SearchBuilderException,
			SearchBuilderConfigurationException {
		Resource [] result = new Resource[1];
		Resource re = new Resource();
		re.setProperty("External Reference ID", "1");
		result[0] = re;
		return result;
	}

	public void updateNotificationType(NotificationType arg0, String arg1)
			throws ResourcePersistenceException {
		

	}

	public Resource updateResource(Resource arg0, String arg1)
			throws ResourcePersistenceException {
		return null;

	}

	public void updateResourceRole(ResourceRole arg0, String arg1)
			throws ResourcePersistenceException {
		

	}

	public Resource[] updateResources(Resource[] arg0, long arg1, String arg2)
			throws ResourcePersistenceException {
		return null;

	}

	public void addNotifications(long user, long[] projects,
			long notificationType, String operator)
			throws ResourcePersistenceException {
		
	}

	public long[] getNotificationsForUser(long user, long notificationType)
			throws ResourcePersistenceException {
		return null;
	}

	public void removeNotifications(long user, long[] projects,
			long notificationType, String operator)
			throws ResourcePersistenceException {
		
	}

	public Resource[] searchResources(long projectId, long roleId)
			throws ResourcePersistenceException {
		return null;
	}

}
